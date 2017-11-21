package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.Map;
import java.util.Set;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB001LoginModel;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB003ExternalLoginModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB003ExternalLoginService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 外部ログインアクションクラス。
 *
 * @author k002849
 * @version 1.0 2014/04/25
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb001_login.jsp"),
	@Result(name=INPUT, location="tb001_login.jsp")
})
public class TB003ExternalLoginAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB003ExternalLoginModel> {
	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB003ExternalLoginModel model = new TB003ExternalLoginModel();

	/** サービス */
	@Autowired
	private TB003ExternalLoginService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return ログイン画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
		value="externalLogin",
		results = {
			@Result(name=SUCCESS, type="redirectAction", location="${nextPageUrl}${nextPageParam}")
		}
	)
	public String execute() throws Exception {
		// パラメータチェック
		service.validateParameter(model);

		// ログイン画面表示フラグ
		boolean isLoginDisplay = (getUserContext() == null);

		// 次ページのセット
		if (isLoginDisplay) {
			// ユーザコンテキストがない（ログインしていない）場合は、ログイン画面に遷移
			model.setNextPageUrl(TB001LoginModel.ACTION_NM_LOGIN_INIT);
		} else {
			// ユーザコンテキストがある（ログインしている）場合は、遷移先URLの画面に遷移
			model.setNextPageUrl(model.getActionURL().replaceAll("/", ""));
		}

		// 次ページへのパラメータ設定
		model.setNextPageParam(createNextPageParam(isLoginDisplay));

		return SUCCESS;
	}

	/**
	 * 次ページ画面へのパラメータを作成します。
	 *
	 * @param isLoginDisplay ログイン画面表示フラグ
	 * @return 次ページ画面へのパラメータ
	 */
	private String createNextPageParam(boolean isLoginDisplay) {
		StringBuilder nextPageParam = new StringBuilder("");

		Map<String, String[]> requestParamMap =
			ServletActionContext.getRequest().getParameterMap();
		if (requestParamMap == null || requestParamMap.isEmpty()) {
			// リクエストがない場合は、空文字を返す
			return nextPageParam.toString();
		}

		Set<String> keySet = requestParamMap.keySet();
		for (String key : keySet) {
			if (!isLoginDisplay && TB001LoginModel.PARAM_NM_ACTION_URL.equals(key)) {
				// 次ページがログイン画面でない場合は、actionURLは作成しない
				continue;
			}

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

		return nextPageParam.toString();
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB003ExternalLoginModel getModel() {
		return model;
	}
}
