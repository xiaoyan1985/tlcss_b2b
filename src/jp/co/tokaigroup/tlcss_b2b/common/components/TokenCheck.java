package jp.co.tokaigroup.tlcss_b2b.common.components;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.tokaigroup.tlcss_b2b.common.util.TokenSessionHelper;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * トークンセッションチェックコンポーネントクラス。
 * （Struts2ソースより流用）
 *
 * @author k002849
 * @version 4.0 2014/06/23
 */
@StrutsTag(name="tokenCheck", tldTagClass="jp.co.tokaigroup.tlcss_b2b.common.view.jsp.TokenCheckTag", description="Stop double-submission of forms")
public class TokenCheck extends UIBean {

	/** タグテンプレート名 */
	public static final String TEMPLATE = "tokenCheck";

	/** 画面ＩＤ */
	private String displayId;

	/**
	 * コンストラクタ。
	 *
	 * @param stack 画面情報
	 * @param request リクエスト情報
	 * @param response レスポンス情報
	 */
	public TokenCheck(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	/**
	 * タグテンプレート名を取得します。
	 *
	 * @return タグテンプレート名
	 */
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	/**
	 * First looks for the token in the PageContext using the supplied name (or {@link org.apache.struts2.util.TokenHelper#DEFAULT_TOKEN_NAME}
	 * if no name is provided) so that the same token can be re-used for the scope of a request for the same name. If
	 * the token is not in the PageContext, a new Token is created and set into the Session and the PageContext with
	 * the name.
	 */
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();

		String tokenName;
		Map parameters = getParameters();

		if (parameters.containsKey("name")) {
			tokenName = (String) parameters.get("name");
		} else {
			if (name == null) {
				tokenName = TokenSessionHelper.DEFAULT_TOKEN_NAME;
			} else {
				tokenName = findString(name);

				if (tokenName == null) {
					tokenName = name;
				}
			}

			addParameter("name", tokenName);
		}

		String token = buildToken(tokenName, getDisplayId());
		addParameter("token", token);
		addParameter("tokenNameField", TokenSessionHelper.TOKEN_NAME_FIELD);
		addParameter("displayIdFiled", TokenSessionHelper.DISPLAY_ID_FIELD);
		addParameter("displayId", getDisplayId());
	}

	/**
	 * 画面ＩＤを取得します。
	 *
	 * @return 画面ＩＤ
	 */
	public String getDisplayId() {
		return this.displayId;
	}

	/**
	 * 画面ＩＤを設定します。
	 *
	 * @param displayId 画面ＩＤ
	 */
	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	/**
	 * トークンを生成します。
	 *
	 * @param name トークン名
	 * @param displayId 画面ＩＤ
	 * @return トークン値
	 */
	private String buildToken(String name, String displayId) {
		Map<String, Object> context = stack.getContext();
		Object myToken = context.get(name);

		if (myToken == null) {
			myToken = TokenSessionHelper.setToken(name, displayId);
			context.put(name, myToken);
		}

		return myToken.toString();
	}

}
