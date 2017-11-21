package jp.co.tokaigroup.tlcss_b2b.common.util;

import java.math.BigInteger;
import java.util.Map;
import java.util.Random;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * トークンセッションヘルパークラス。
 * （Struts2ソースより流用）
 *
 * @author k002849
 * @version 4.0 2014/06/23
 */
public class TokenSessionHelper {
	/**
	 * The default namespace for storing token session values
	 */
	public static final String TOKEN_NAMESPACE = "struts.tokens";

	/**
	 * The default name to map the token value
	 */
	public static final String DEFAULT_TOKEN_NAME = "token";

	/**
	 * The name of the field which will hold the token name
	 */
	public static final String TOKEN_NAME_FIELD = "struts.token.name";

	/** 画面ＩＤ */
	public static final String DISPLAY_ID_FIELD = "displayId";

	/** ロガー */
	private static final Logger LOG = LoggerFactory.getLogger(TokenSessionHelper.class);
	/** 乱数値生成クラス */
	private static final Random RANDOM = new Random();


	/**
	 * Sets a transaction token into the session using the default token name.
	 *
	 * @param displayId displayId.
	 * @return the token string
	 */
	public static String setToken(String displayId) {
		return setToken(DEFAULT_TOKEN_NAME, displayId);
	}

	/**
	 * Sets a transaction token into the session based on the provided token name.
	 *
	 * @param tokenName the token name based on which a generated token value is stored into session; for actual session
	 *                  store, this name will be prefixed by a namespace.
	 * @param displayId displayId.
	 * @return the token string
	 */
	public static String setToken( String tokenName, String displayId ) {
		String token = generateGUID();
		setSessionToken(tokenName, displayId, token);
		return token;
	}

	/**
	 * Put a given named token into the session map. The token will be stored with a namespace prefix prepended.
	 *
	 * @param tokenName the token name based on which given token value is stored into session; for actual session store,
	 *                  this name will be prefixed by a namespace.
	 * @param displayId displayId.
	 * @param token     the token value to store
	 */
	public static void setSessionToken( String tokenName, String displayId, String token ) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		try {
			session.put(buildTokenSessionAttributeName(tokenName, displayId), token);
		} catch ( IllegalStateException e ) {
			// WW-1182 explain to user what the problem is
			String msg = "Error creating HttpSession due response is commited to client. You can use the CreateSessionInterceptor or create the HttpSession from your action before the result is rendered to the client: " + e.getMessage();
			if (LOG.isErrorEnabled()) {
				LOG.error(msg, e);
			}
			throw new IllegalArgumentException(msg);
		}
	}


	/**
	 * Build a name-spaced token session attribute name based on the given token name.
	 *
	 * @param tokenName the token name to prefix
	 * @param displayId displayId.
	 *
	 * @return the name space prefixed session token name
	 */
	public static String buildTokenSessionAttributeName(String tokenName, String displayId) {
		return TOKEN_NAMESPACE + "." + displayId + "." + tokenName;
	}

	/**
	 * Gets a transaction token from the params in the ServletActionContext using the default token name.
	 *
	 * @return token
	 */
	public static String getToken() {
		return getToken(DEFAULT_TOKEN_NAME);
	}

	/**
	 * Gets the Token value from the params in the ServletActionContext using the given name
	 *
	 * @param tokenName the name of the parameter which holds the token value
	 * @return the token String or null, if the token could not be found
	 */
	public static String getToken(String tokenName) {
		if (tokenName == null ) {
			return null;
		}
		Map<String, Object> params = ActionContext.getContext().getParameters();
		String[] tokens = (String[]) params.get(tokenName);
		String token;

		if ((tokens == null) || (tokens.length < 1)) {
			if (LOG.isWarnEnabled()) {
				LOG.warn("Could not find token mapped to token name " + tokenName);
			}

			return null;
		}

		token = tokens[0];

		return token;
	}

	/**
	 * Gets the token name from the Parameters in the ServletActionContext
	 *
	 * @return the token name found in the params, or null if it could not be found
	 */
	public static String getTokenName() {
		Map<String, Object> params = ActionContext.getContext().getParameters();

		if (!params.containsKey(TOKEN_NAME_FIELD)) {
			if (LOG.isWarnEnabled()) {
				LOG.warn("Could not find token name in params.");
			}

			return null;
		}

		String[] tokenNames = (String[]) params.get(TOKEN_NAME_FIELD);
		String tokenName;

		if ((tokenNames == null) || (tokenNames.length < 1)) {
			if (LOG.isWarnEnabled()) {
				LOG.warn("Got a null or empty token name.");
			}

			return null;
		}

		tokenName = tokenNames[0];

		return tokenName;
	}

	/**
	 * Checks for a valid transaction token in the current request params. If a valid token is found, it is
	 * removed so the it is not valid again.
	 *
	 * @return false if there was no token set into the params (check by looking for {@link #TOKEN_NAME_FIELD}), true if a valid token is found
	 */
	public static boolean validToken() {
		String tokenName = getTokenName();

		if (tokenName == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("no token name found -> Invalid token ");
			}
			return false;
		}

		String token = getToken(tokenName);

		if (token == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("no token found for token name "+tokenName+" -> Invalid token ");
			}
			return false;
		}

		Map<String, Object> session = ActionContext.getContext().getSession();
		String tokenSessionName = buildTokenSessionAttributeName(tokenName, getDisplayId());
		String sessionToken = (String) session.get(tokenSessionName);

		if (!token.equals(sessionToken)) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(LocalizedTextUtil.findText(TokenSessionHelper.class, "struts.internal.invalid.token", ActionContext.getContext().getLocale(), "Form token {0} does not match the session token {1}.", new Object[]{
					token, sessionToken
				}));
			}

			return false;
		}

		return true;
	}

	/**
	 * 乱数値を生成します。
	 *
	 * @return 乱数値
	 */
	public static String generateGUID() {
		return new BigInteger(165, RANDOM).toString(36).toUpperCase();
	}

	/**
	 * 画面ＩＤを取得します。
	 *
	 * @return 画面ＩＤ
	 */
	public static String getDisplayId() {
		Map<String, Object> params = ActionContext.getContext().getParameters();

		if (!params.containsKey(DISPLAY_ID_FIELD)) {
			if (LOG.isWarnEnabled()) {
				LOG.warn("Could not find displayId in params.");
			}

			return null;
		}

		String[] displayIdList = (String[]) params.get(DISPLAY_ID_FIELD);
		String displayId;

		if ((displayIdList == null) || (displayIdList.length < 1)) {
			if (LOG.isWarnEnabled()) {
				LOG.warn("Got a null or empty displayId.");
			}

			return null;
		}

		displayId = displayIdList[0];

		return displayId;
	}
}
