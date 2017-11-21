package jp.co.tokaigroup.tlcss_b2b.common.model;


/**
 * 外部ログイン画面モデル。
 *
 * @author k002849
 * @version 1.0 2014/04/25
 */
public class TB003ExternalLoginModel extends TB000CommonModel {
	/** 遷移先URL */
	private String actionURL;

	/** 次ページURL */
	private String nextPageUrl;
	/** 次ページへのパラメータ */
	private String nextPageParam;

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
}
