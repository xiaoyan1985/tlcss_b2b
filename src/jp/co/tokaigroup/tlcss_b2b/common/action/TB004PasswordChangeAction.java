package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.Map;
import java.util.Set;

import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.filter.CsrfPreventionFilter;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB001LoginModel;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB004PasswordChangeModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB004PasswordChangeService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

/**
 * パスワード変更アクションクラス。
 *
 * @author k002849
 * @version 1.0 2014/04/28
 */
@Results({
	@Result(name=SUCCESS, location="tb004_password_change.jsp"),
	@Result(name=INPUT, location="tb004_password_change.jsp")
})
public class TB004PasswordChangeAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB004PasswordChangeModel>, ServiceValidatable {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 外部ログイン時に除外するパラメータ */
	private final String[] EXCLUDE_PARAMS = {TB001LoginModel.PARAM_NM_ACTION_URL, "currentPassword", "newPassword", "confirmNewPassword", CsrfPreventionFilter.CSRF_NONCE_REQUEST_PARAM, "dispMode"};

	/** 画面モデル */
	private TB004PasswordChangeModel model = new TB004PasswordChangeModel();

	/** サービス */
	@Autowired
	private TB004PasswordChangeService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return
	 * @throws Exception
	 */
	@Action("passwordChangeInit")
	public String init() throws Exception {
		// 初期化処理実行
		// ユーザコンテキストの取得
		model.setUserContext(getUserContext());

		return SUCCESS;
	}

	/**
	 * パスワード更新処理を行います。
	 *
	 * @return
	 * @throws Exception
	 */
	@Action(
			value="passwordChangeUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb004_password_change.jsp"),
					@Result(name=MENU, type="redirectAction", location="menuInit"),
					@Result(name=EXTERNAL_LOGIN, type="redirectAction", location="${nextPageUrl}${nextPageParam}")
			}
	)
	public String update() throws Exception {
		// パスワード更新処理
		service.updatePassword(model);

		// 完了メッセージ
		addActionMessage("MSG0001", "パスワードの変更");

		if (model.isExternalLogin()) {
			// 外部ログインからの遷移の場合、パラメータを作成し、遷移先画面に遷移する
			model.setNextPageUrl(model.getActionURL().replaceAll("/", ""));
			model.setNextPageParam(createNextPageParam());

			return EXTERNAL_LOGIN;
		}

		if (!model.isFromMenu()) {
			// メニューからの遷移以外の場合は、メニュー画面を表示
			return MENU;
		}

		return SUCCESS;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {

	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB004PasswordChangeModel getModel() {
		return model;
	}

	/**
	 * 次ページ画面へのパラメータを作成します。
	 *
	 * @return 次ページ画面へのパラメータ
	 */
	private String createNextPageParam() {
		StringBuilder nextPageParam = new StringBuilder("");

		Map<String, String[]> requestParamMap =
			ServletActionContext.getRequest().getParameterMap();
		if (requestParamMap == null || requestParamMap.isEmpty()) {
			// リクエストがない場合は、空文字を返す
			return nextPageParam.toString();
		}

		Set<String> keySet = requestParamMap.keySet();
		for (String key : keySet) {
			boolean isExclude = false;

			for (String excludeParam : EXCLUDE_PARAMS) {
				if (key.equals(excludeParam)) {
					isExclude = true;
					break;
				}
			}

			if (!isExclude) {
				if (StringUtils.isBlank(nextPageParam.toString())) {
					nextPageParam.append("?");
				} else {
					nextPageParam.append("&");
				}

				nextPageParam.append(key);
				nextPageParam.append("=");

				StringBuilder valueParam = new StringBuilder("");
				for (String value : requestParamMap.get(key)) {
					if (StringUtils.isNotBlank(valueParam.toString())) {
						valueParam.append(",");
					}
					valueParam.append(value);
				}
				nextPageParam.append(valueParam.toString());
			}
		}

		return nextPageParam.toString();
	}
}
