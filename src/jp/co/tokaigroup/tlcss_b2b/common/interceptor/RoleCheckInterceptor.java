package jp.co.tokaigroup.tlcss_b2b.common.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.si.fw.context.UserContextSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * アクセス権限チェックインターセプター。
 *
 * @author k002849
 * @version 4.0 2014/07/07
 */
public class RoleCheckInterceptor extends AbstractInterceptor {
	/** セッションタイムアウト時のフォワード先 */
	private static final String FORWARD_IN_SESSION_TIMEOUT = "notLoggedIn";
	/** アクセス不可時のフォワード先 */
	private static final String FORWARD_IN_NOT_ACCESSABLE = "forbidden";

	/** 除外URL */
	private String excludeURLs;

	/**
	 * インターセプター処理。
	 * アクセス権限をチェックします。
	 *
	 * @param invocation 画面処理呼び出し情報
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		// リクエスト取得
		HttpServletRequest request = ServletActionContext.getRequest();
		// リクエストURL取得
		String requestUrl = getRequestUrl(request);

		// 除外URLに含まれているかチェック
		String[] excludeUrlArray = StringUtils.split(getExcludeURLs(), ",");
		for (String excludeUrl : excludeUrlArray) {
			if (excludeUrl.equals(requestUrl)) {
				// 除外URLに含まれていた場合は、処理終了
				return invocation.invoke();
			}
		}

		HttpSession session = request.getSession();
		// ユーザー情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) session.getAttribute(UserContextSupport.KEY);
		if (userContext == null) {
			// ユーザー情報が取得できなかった場合は、セッションタイムアウト
			return FORWARD_IN_SESSION_TIMEOUT;
		}

		// アクセス可能URLMapを取得
		Map<String, TbMRoleUrl> accessibleMap = userContext.getAccessibleMap();

		// アクセス可能URLの場合は、次のインターセプター処理実施、アクセス不可の場合は、403エラー
		return accessibleMap.containsKey(requestUrl) ? invocation.invoke() : FORWARD_IN_NOT_ACCESSABLE;
	}

	/**
	 * 除外URLを取得します。
	 *
	 * @return 除外URL
	 */
	public String getExcludeURLs() {
		return excludeURLs;
	}
	/**
	 * 除外URLを設定します。
	 *
	 * @param excludeURLs 除外URL
	 */
	public void setExcludeURLs(String excludeURLs) {
		this.excludeURLs = excludeURLs;
	}

	/**
	 * リクエストURLを取得します。
	 *
	 * @param リクエスト情報
	 * @return リクエストURL
	 */
	private String getRequestUrl(HttpServletRequest request) {
		// URLが除外URLに含まれるかチェック
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		String actionURL = StringUtils.remove(requestURI, contextPath);

		// xxx.action;jsessionid=xxxx　の ;jsessionid以降を取り除く
		int jsessionIdStart = actionURL.indexOf(";");
		if (jsessionIdStart > 0) {
			actionURL = actionURL.substring(0, jsessionIdStart);
		}

		// xxx.actionの.actionを取り除く
		int actionStart = actionURL.indexOf(".action");
		if (actionStart > 0) {
			actionURL = actionURL.substring(0, actionStart);
		}

		return actionURL;
	}
}
