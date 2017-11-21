package jp.co.tokaigroup.tlcss_b2b.common.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.tokaigroup.tlcss_b2b.common.util.TokenSessionHelper;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.TokenInterceptor;
import org.apache.struts2.util.InvocationSessionStore;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * トークンセッションインターセプタークラス。
 * （Strutsソースより流用）
 *
 * @author k002849
 * @version 4.0 2014/06/23
 */
public class TokenSessionInterceptor extends TokenInterceptor {
	/**
	 * トークンセッションチェック処理。
	 *
	 * @param invocation 画面処理呼び出し情報
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Override
	protected String handleToken(ActionInvocation invocation) throws Exception {
		//see WW-2902: we need to use the real HttpSession here, as opposed to the map
		//that wraps the session, because a new wrap is created on every request
		HttpSession session = ServletActionContext.getRequest().getSession(true);
		synchronized (session) {
			if (!TokenSessionHelper.validToken()) {
				return handleInvalidToken(invocation);
			}
		}
		return handleValidToken(invocation);
	}

	/**
	 * トークンセッションチェックエラー時処理。
	 *
	 * @param invocation 画面処理呼び出し情報
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Override
	protected String handleInvalidToken(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();

		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ac.get(ServletActionContext.HTTP_RESPONSE);
		String tokenName = TokenSessionHelper.getTokenName();
		String token = TokenSessionHelper.getToken(tokenName);
		String displayId = TokenSessionHelper.getDisplayId();

		if ((tokenName != null) && (token != null)) {
			Map<String, Object> params = ac.getParameters();
			params.remove(tokenName);
			params.remove(TokenSessionHelper.TOKEN_NAME_FIELD);

			String sessionTokenName = TokenSessionHelper.buildTokenSessionAttributeName(tokenName, displayId);
			ActionInvocation savedInvocation = InvocationSessionStore.loadInvocation(sessionTokenName, token);

			if (savedInvocation != null) {
				// set the valuestack to the request scope
				ValueStack stack = savedInvocation.getStack();
				request.setAttribute(ServletActionContext.STRUTS_VALUESTACK_KEY, stack);

				ActionContext savedContext = savedInvocation.getInvocationContext();
				savedContext.getContextMap().put(ServletActionContext.HTTP_REQUEST, request);
				savedContext.getContextMap().put(ServletActionContext.HTTP_RESPONSE, response);
				Result result = savedInvocation.getResult();

				if ((result != null) && (savedInvocation.getProxy().getExecuteResult())) {
					result.execute(savedInvocation);
				}

				// turn off execution of this invocations result
				invocation.getProxy().setExecuteResult(false);

				return savedInvocation.getResultCode();
			}
		}

		return INVALID_TOKEN_CODE;
	}

	/**
	 * トークンセッションチェック正常時処理。
	 *
	 * @param invocation 画面処理呼び出し情報
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Override
	protected String handleValidToken(ActionInvocation invocation) throws Exception {
		// we know the token name and token must be there
		String key = TokenSessionHelper.getTokenName();
		String token = TokenSessionHelper.getToken(key);
		String displayId = TokenSessionHelper.getDisplayId();
		String sessionTokenName = TokenSessionHelper.buildTokenSessionAttributeName(key, displayId);
		InvocationSessionStore.storeInvocation(sessionTokenName, token, invocation);

		return invocation.invoke();
	}
}
