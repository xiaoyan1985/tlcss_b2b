package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.math.BigDecimal;

import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

import org.apache.commons.lang.StringUtils;

/**
 * 作業報告書印刷モデル。
 *
 * @author k002849
 * @version 4.0 2014/07/22
 */
public class TB014WorkReportModel extends TB000CommonModel {
	/** 問い合わせNO */
	private String toiawaseNo;
	/** 問い合わせ履歴NO */
	private BigDecimal toiawaseRirekiNo;
	/** 顧客ID */
	private String kokyakuId;
	/** 業者コード */
	private String gyoshaCd;

	/** ユーザーエージェント */
	private String userAgent;

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
	 * 問い合わせ履歴NOを取得します。
	 *
	 * @return 問い合わせ履歴NO
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}
	/**
	 * 問い合わせ履歴NOを設定します。
	 *
	 * @param toiawaseRirekiNo 問い合わせ履歴NO
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}

	/**
	 * 顧客IDを取得します。
	 *
	 * @return 顧客ID
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}
	/**
	 * 顧客IDを設定します。
	 *
	 * @param kokyakuId 顧客ID
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}

	/**
	 * 業者コードを取得します。
	 *
	 * @return 業者コード
	 */
	public String getGyoshaCd() {
		return gyoshaCd;
	}
	/**
	 * 業者コードを設定します。
	 *
	 * @param gyoshaCd 業者コード
	 */
	public void setGyoshaCd(String gyoshaCd) {
		this.gyoshaCd = gyoshaCd;
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
