package jp.co.tokaigroup.tlcss_b2b.common.model;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.si.fw.context.UserContextSupport;

/**
 * パスワード変更画面モデル。
 *
 * @author k002849
 * @version 1.0 2014/04/28
 */
public class TB004PasswordChangeModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "パスワード変更";

	/** 現在のパスワード */
	private String currentPassword;
	/** 新パスワード */
	private String newPassword;
	/** 新パスワード(確認) */
	private String confirmNewPassword;

	/** 画面モード */
	private int dispMode;

	/** ユーザコンテキスト */
	private UserContextSupport userContext;

	// 外部ログインの際に使用
	/** 遷移先URL */
	private String actionURL;
	/** 次ページURL */
	private String nextPageUrl;
	/** 次ページへのパラメータ */
	private String nextPageParam;

	/**
	 * 現在のパスワードを取得します。
	 *
	 * @return 現在のパスワード
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}
	/**
	 * 現在のパスワードを設定します。
	 *
	 * @param currentPassword 現在のパスワード
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	/**
	 * 新パスワードを取得します。
	 *
	 * @return 新パスワード
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * 新パスワードを設定します。
	 *
	 * @param newPassword 新パスワード
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * 新パスワード(確認)を取得します。
	 *
	 * @return 新パスワード(確認)
	 */
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	/**
	 * 新パスワード(確認)を設定します。
	 *
	 * @param confirmNewPassword 新パスワード(確認)
	 */
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	/**
	 * 画面モードを取得します。
	 *
	 * @return 画面モード
	 */
	public int getDispMode() {
		return dispMode;
	}
	/**
	 * 画面モードを設定します。
	 *
	 * @param dispMode 画面モード
	 */
	public void setDispMode(int dispMode) {
		this.dispMode = dispMode;
	}

	/**
	 * ユーザコンテキストを取得します。
	 *
	 * @return ユーザコンテキスト
	 */
	public UserContextSupport getUserContext() {
		return userContext;
	}
	/**
	 * ユーザコンテキストを設定します。
	 *
	 * @param userContext ユーザコンテキスト
	 */
	public void setUserContext(UserContextSupport userContext) {
		this.userContext = userContext;
	}

	/**
	 * 遷移先URLを取得します。
	 *
	 * @return 遷移先URL
	 */
	public String getActionURL() {
		return actionURL;
	}
	/**
	 * 遷移先URLを設定します。
	 *
	 * @param actionURL 遷移先URL
	 */
	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	/**
	 * 次ページURLを取得します。
	 *
	 * @return 次ページURL
	 */
	public String getNextPageUrl() {
		return nextPageUrl;
	}
	/**
	 * 次ページURLを設定します。
	 *
	 * @param nextPageUrl 次ページURL
	 */
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}

	/**
	 * 次ページへのパラメータを取得します。
	 *
	 * @return 次ページへのパラメータ
	 */
	public String getNextPageParam() {
		return nextPageParam;
	}
	/**
	 * 次ページへのパラメータを設定します。
	 *
	 * @param nextPageParam 次ページへのパラメータ
	 */
	public void setNextPageParam(String nextPageParam) {
		this.nextPageParam = nextPageParam;
	}

	/**
	 * メニューからの遷移かを判定します。
	 *
	 * @return true:メニューからの遷移、false:それ以外
	 */
	public boolean isFromMenu() {
		// アクションエラーメッセージがなければ、メニューからの遷移
		return (this.dispMode == TB001LoginModel.DISP_MODE_FROM_MENU);
	}

	/**
	 * パスワード期限切れ間近かを判定します。
	 *
	 * @return true:パスワード期限切れ間近、false:それ以外
	 */
	public boolean isPasswordLimitNear() {
		return (this.dispMode == TB001LoginModel.DISP_MODE_PASSWORD_LIMIT_NEAR);
	}

	/**
	 * パスワード期限切れかを判定します。
	 *
	 * @return true:パスワード期限切れ、false:それ以外
	 */
	public boolean isPasswordLimit() {
		return (this.dispMode == TB001LoginModel.DISP_MODE_PASSWORD_LIMIT);
	}

	/**
	 * 外部ログインからの遷移かを判定します。
	 *
	 * @return true:外部ログイン、false:それ以外
	 */
	public boolean isExternalLogin() {
		return (StringUtils.isNotBlank(this.actionURL));
	}
}
