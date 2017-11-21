package jp.co.tokaigroup.tlcss_b2b.user.model;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * 入電報告書印刷モデル。
 *
 * @author v140546
 * @version 1.0 2014/05/27
 * @version 1.1 2015/12/11 S.Nakano 問い合わせ情報、ダウンロード可能フラグ追加
 */
public class TB013IncomingCallReportModel extends TB000CommonModel {
	/** 問い合わせNO **/
	private String toiawaseNo;

	/** ユーザーエージェント */
	private String userAgent;

	/** 問い合わせ情報 */
	private RcpTToiawase toiawase;

	/** ダウンロード可能フラグ */
	private boolean isDowanloadable;

	/**
	 * 問い合わせNOを取得します。
	 *
	 * @return 問い合わせNO
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}
	/**
	 * 問い合わせNOを設定します。
	 *
	 * @param toiawaseNo 問い合わせNO
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
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
	 * 問い合わせ情報を取得します。
	 * @return 問い合わせ情報
	 */
	public RcpTToiawase getToiawase() {
		return toiawase;
	}
	/**
	 * 問い合わせ情報を設定します。
	 * @param toiawase 問い合わせ情報
	 */
	public void setToiawase(RcpTToiawase toiawase) {
		this.toiawase = toiawase;
	}

	/**
	 * ダウンロード可能フラグを取得します。
	 * @return ダウンロード可能フラグ
	 */
	public boolean isDownloadable() {
		return isDowanloadable;
	}
	/**
	 * ダウンロード可能フラグを設定します。
	 * @param isDowanloadable ダウンロード可能フラグ
	 */
	public void setDownloadable(boolean isDowanloadable) {
		this.isDowanloadable = isDowanloadable;
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