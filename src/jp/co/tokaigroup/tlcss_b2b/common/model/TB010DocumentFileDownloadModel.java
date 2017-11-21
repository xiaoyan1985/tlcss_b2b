package jp.co.tokaigroup.tlcss_b2b.common.model;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * 文書ライブラリダウンロードモデル。
 *
 * @author v140546
 * @version 1.0 2014/08/29
 */
public class TB010DocumentFileDownloadModel extends TB000CommonModel {

	/** 実ファイル名 **/
	private String realFileNm;

	/** ユーザーエージェント */
	private String userAgent;

	/** ユーザーコンテキスト */
	private TLCSSB2BUserContext userContext;

	/**
	 * 実ファイル名を取得します。
	 *
	 * @return 実ファイル名
	 */
	public String getRealFileNm() {
		return realFileNm;
	}
	/**
	 * 実ファイル名を設定します。
	 *
	 * @param toiawaseNo 実ファイル名
	 */
	public void setRealFileNm(String realFileNm) {
		this.realFileNm = realFileNm;
	}

	/**
	 * ユーザーエージェントを取得します。
	 *
	 * @return ユーザーエージェント
	 */
	public String getUserAgent() {
		return userAgent;
	}
	/**
	 * ユーザーエージェントを設定します。
	 *
	 * @param userAgent ユーザーエージェント
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * ユーザーコンテキストを取得します。
	 *
	 * @return ユーザーコンテキスト
	 */
	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}
	/**
	 * ユーザーコンテキストを設定します。
	 *
	 * @param userContext ユーザーコンテキスト
	 */
	public void setUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * iPadでログインしているかを判定します。
	 *
	 * @return true:iPadでログイン、false:それ以外
	 */
	public boolean isIPad() {
		if (StringUtils.isBlank(getUserAgent())) {
			return false;
		}

		return getUserAgent().indexOf(Constants.USER_AGENT_KEY_IPAD) != -1;
	}

}