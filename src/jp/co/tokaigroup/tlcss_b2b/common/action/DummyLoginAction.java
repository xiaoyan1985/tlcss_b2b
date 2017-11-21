package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import javax.servlet.http.HttpServletRequest;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * ダミーログインアクションクラス。
 *
 * @author N.Akahori
 * @version 1.0 2012/08/07
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=INPUT, location="dummy_login.jsp"),
	@Result(name=SUCCESS, location="dummy_menu.jsp")
})
public class DummyLoginAction extends TLCSSB2BBaseActionSupport implements
		ServletRequestAware {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 画面からの引継ぎ情報 */
	private TLCSSB2BUserContext userContext = new TLCSSB2BUserContext();

	/** リクエスト情報 */
	private HttpServletRequest request;

	/**
	 * コンストラクタ
	 *
	 * @param request リクエスト情報
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * ログイン処理を行います。
	 *
	 */
	@Action(
		value = "dummyLoginExecute",
		results = {
			@Result(name=SUCCESS, type="redirectAction",
					location="dummyMenu")
		})
	public String execute() throws Exception {
		//ログインＩＤが送信されていなかったらエラー画面へ遷移
		if (StringUtils.isBlank(userContext.getLoginId())) {
			return LOGIN_ERROR;
		}

		//ログインユーザのIPアドレスをセット
		userContext.setLoginIpAdress(request.getRemoteAddr());

		// セッションに保存
		setUserContextToSession(userContext);

		return SUCCESS;
	}

	/**
	 * 開発用ログイン画面へ遷移します。
	 *
	 */
	@Action("dummyMenu")
	public String dummy() throws Exception {
		return SUCCESS;
	}

	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}

	public void setUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
	}
}
