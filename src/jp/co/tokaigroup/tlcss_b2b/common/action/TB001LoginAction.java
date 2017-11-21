package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.context.UserContextSupport;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.filter.CsrfPreventionFilter;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB001LoginModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB001LoginService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ログインアクションクラス。
 *
 * @author k002849
 * @version 1.0 2014/04/22
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb001_login.jsp"),
	@Result(name=INPUT, location="tb001_login.jsp")
})
public class TB001LoginAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB001LoginModel>, ServiceValidatable {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** パラメータ名 遷移先URL */
	private final String PARAM_NM_ACTION_URL = "actionURL";

	/** 外部ログイン時に除外するパラメータ */
	private final String[] EXCLUDE_PARAMS = {PARAM_NM_ACTION_URL, "loginId", "passwd", CsrfPreventionFilter.CSRF_NONCE_REQUEST_PARAM, "dispMode"};

	/** 画面モデル */
	private TB001LoginModel model = new TB001LoginModel();

	/** サービス */
	@Autowired
	private TB001LoginService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return ログイン画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("loginInit")
	public String init() throws Exception {
		return SUCCESS;
	}

	/**
	 * ログイン処理を行います。
	 *
	 * @return ログイン画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="login",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction", location="menuInit"),
					@Result(name=LOGIN_ERROR, location="tb001_login.jsp"),
					@Result(name=PASSWORD_CHANGE, type="redirectAction", location="passwordChangeInit${actionParam}"),
					@Result(name=EXTERNAL_LOGIN, type="redirectAction", location="${actionURL}${actionParam}")
			}
	)
	public String login() throws Exception {
		// セッション情報のクリア
		session.remove(UserContextSupport.KEY);

		// ログインユーザ情報の新規作成
		TLCSSB2BUserContext userContext = new TLCSSB2BUserContext();

		// ログイン処理
		model = service.executeLogin(model);

		// セッション情報の設定
		HttpServletRequest request = ServletActionContext.getRequest();
		// ログインユーザのIPアドレスの設定
		userContext.setLoginIpAdress(request.getRemoteAddr());
		// システム定数Mapの設定
		userContext.setSystemConstants(model.getSystemConstants());

		// 接続区分の設定
		// 開発環境ユーザ名の取得
		String devUser = ResourceFactory.getResource().getString("RCP_USER");
		// 本番環境ユーザ名の取得
		String honbanUser = ResourceFactory.getResource().getString("HONBAN_RCP_USER");
		if (devUser.equals(honbanUser)) {
			// 開発環境と本番環境のユーザが同じ場合、本番環境
			userContext.setPlcon(TLCSSB2BUserContext.PL_CON_PRODUCTION);
		} else {
			// 開発環境と本番環境のユーザが同じ場合、開発環境
			userContext.setPlcon(TLCSSB2BUserContext.PL_CON_DEVELOPMENT);
		}

		// ログインＩＤの設定
		userContext.setLoginId(model.getUserInfo().getLoginId());
		// ログインユーザ名の設定
		userContext.setUserName(model.getUserInfo().getUserNm());
		// 顧客ＩＤの設定
		userContext.setRefKokyakuId(model.getUserInfo().getKokyakuId());

		// 参照顧客情報の設定
		if (model.getUserInfo().isRealEstate()) {
			if (model.getRefKokyakuList().size() == 1 &&
					TbMRefKokyaku.LOWER_DSP_FLG_SHOWING.equals(model.getRefKokyakuList().get(0).getLowerDspFlg())) {
				// 取得した顧客ＩＤが1件かつ、下位層表示フラグが「1：表示」の場合
				userContext.setKokyakuIdSelected(true);
				userContext.setSingleFlg(true);
			} else {
				userContext.setKokyakuIdSelected(false);
				userContext.setSingleFlg(false);
			}
			userContext.setKokyakuId(model.getUserInfo().getKokyakuId());
			userContext.setKokyakuName(model.getKokyakuInfo().getKanjiNm());
		}

		// 委託会社ＩＤの設定
		userContext.setKaishaId(model.getUserInfo().getKaishaId());
		// 業者コードの設定
		userContext.setGyoshaCd(model.getUserInfo().getGyoshaCd());
		// 権限の設定
		userContext.setRole(model.getUserInfo().getRole());
		userContext.setLoginRole(model.getUserInfo().getRole());
		// アクセス可能URLMapの設定
		userContext.setAccessibleMap(model.getAccessibleMap());

		setUserContextToSession(userContext);

		TbMUser user = model.getUserInfo();
		user.setUserContext(userContext);
		model.setUserInfo(user);

		// 遷移先の決定
		if (model.getUserInfo().isNormalResult()) {
			// ログインが通常成功の場合
			if (StringUtils.isNotBlank(model.getActionURL())) {
				// 遷移先URLに送るパラメータ作成
				model.setActionParam(createDirectUrlParam(request, false));
				model.setActionURL(model.getActionURL().replaceAll("/", ""));

				// 遷移先URL指定の場合は、遷移先URLに遷移
				return EXTERNAL_LOGIN;
			} else {
				// それ以外の場合は、メニュー画面に遷移
				return SUCCESS;
			}
		} else {
			// ログインが通常成功以外の場合
			if (model.getUserInfo().isTempPasswordLimit()) {
				if (StringUtils.isBlank(model.getSystemConstants().get(
						RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_TOIAWASE_TEL_NO))) {
					throw new ApplicationException("システム問い合わせ先電話番号取得失敗。");
				}
				if (StringUtils.isBlank(model.getSystemConstants().get(
						RcpMSystem.RCP_M_SYSTEM_B2B_SUPPORT_RECEPTION_TIME))) {
					throw new ApplicationException("サポートセンター受付時間取得失敗。");
				}
				// 暫定パスワード期限切れの場合
				addActionError("MSG0009", model.getSystemConstants().get(RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_TOIAWASE_TEL_NO),
						model.getSystemConstants().get(RcpMSystem.RCP_M_SYSTEM_B2B_SUPPORT_RECEPTION_TIME));

				return LOGIN_ERROR;
			} else if (model.getUserInfo().isTempPasswordChange()) {
				// 暫定パスワード変更通知の場合
				addActionError("MSG0010");

				// パラメータ生成
				model.setActionParam(createUrlParamForPasswordChange(
						model, request, TB001LoginModel.DISP_MODE_PASSWORD_LIMIT));

				return PASSWORD_CHANGE;
			} else if (model.getUserInfo().isPasswordLimit()) {
				// 正式パスワード期限切れの場合
				addActionError("MSG0012");

				// パラメータ生成
				model.setActionParam(createUrlParamForPasswordChange(
						model, request, TB001LoginModel.DISP_MODE_PASSWORD_LIMIT));

				return PASSWORD_CHANGE;
			} else {
				// 正式パスワード期限切れ間近の場合
				String paramMsg = "";
				// メッセージの設定
				if (model.getValidDateCount() == 0) {
					paramMsg = "本日";
				} else {
					paramMsg = "あと" + model.getValidDateCount() + "日";
				}
				addActionError("MSG0011", paramMsg);

				// パラメータ生成
				model.setActionParam(createUrlParamForPasswordChange(
						model, request, TB001LoginModel.DISP_MODE_PASSWORD_LIMIT_NEAR));

				return PASSWORD_CHANGE;
			}
		}
	}

	/**
	 * 遷移先URL直接指定時のパラメータを作成します。
	 *
	 * @param request リクエスト
	 * @return 遷移先URL直接指定時のパラメータ
	 */
	private String createDirectUrlParam(HttpServletRequest request, boolean isPasswordChange) {
		StringBuilder sendPrams = new StringBuilder("");

		Map<String, String[]> paramMap = request.getParameterMap();
		Set<String> keySet = paramMap.keySet();
		for (String key : keySet) {
			boolean isExclude = false;

			for (String excludeParam : EXCLUDE_PARAMS) {
				if (key.equals(excludeParam)) {
					isExclude = true;
					break;
				}
			}

			if (isPasswordChange && key.equals(PARAM_NM_ACTION_URL)) {
				// パスワード変更画面の場合は、遷移先URLが必要なため、パラメータに含める
				isExclude = false;
			}

			if (!isExclude) {
				if (StringUtils.isBlank(sendPrams.toString())) {
					sendPrams.append("?");
				} else {
					sendPrams.append("&");
				}

				sendPrams.append(key);
				sendPrams.append("=");

				StringBuilder valueParam = new StringBuilder("");
				for (String value : paramMap.get(key)) {
					if (StringUtils.isNotBlank(valueParam.toString())) {
						valueParam.append(",");
					}
					valueParam.append(value);
				}
				sendPrams.append(valueParam.toString());
			}
		}

		return sendPrams.toString();
	}

	/**
	 * パスワード変更画面に送るパラメータを生成します。
	 *
	 * @param model ログイン画面
	 * @param request リクエスト情報
	 * @param dispMode 画面モード
	 * @return パスワード変更画面に送るパラメータ
	 */
	private String createUrlParamForPasswordChange(TB001LoginModel model, HttpServletRequest request, int dispMode) {
		StringBuilder sendPrams = new StringBuilder("");

		if (StringUtils.isNotBlank(model.getActionURL())) {
			// 遷移先URLに送るパラメータ作成
			sendPrams.append(createDirectUrlParam(request, true));
			if (StringUtils.isBlank(sendPrams.toString())) {
				sendPrams.append("?");
			} else {
				sendPrams.append("&");
			}
			sendPrams.append("dispMode=" + Integer.toString(dispMode));
		} else {
			sendPrams.append("?dispMode=" + Integer.toString(dispMode));
		}

		return sendPrams.toString();
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB001LoginModel getModel() {
		return model;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
	}
}
