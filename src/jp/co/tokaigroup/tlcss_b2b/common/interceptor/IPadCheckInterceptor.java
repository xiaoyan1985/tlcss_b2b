package jp.co.tokaigroup.tlcss_b2b.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.context.UserContextSupport;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * iPadチェックインターセプター。
 *
 * @author 松葉
 * @version 1.0 2015/08/07
 */
public class IPadCheckInterceptor extends AbstractInterceptor {

	/**
	 * インターセプター処理。
	 * iPadかどうかをチェックします。
	 *
	 * @param invocation 画面処理呼び出し情報
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		// リクエスト取得
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		// ユーザー情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) session.getAttribute(UserContextSupport.KEY);
		if (userContext != null) {
			// ユーザーエージェント設定
			userContext.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));
		}

		return invocation.invoke();
	}
}
