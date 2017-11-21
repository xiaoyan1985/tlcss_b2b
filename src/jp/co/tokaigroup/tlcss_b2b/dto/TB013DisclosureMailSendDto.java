package jp.co.tokaigroup.tlcss_b2b.dto;

import jp.co.tokaigroup.reception.dto.RC000CommonDto;

/**
 * TORES公開メール送信画面ＤＴＯ。
 *
 * @author H.Hirai
 * @version 1.0 2016/07/14
 * 
 */
public class TB013DisclosureMailSendDto extends RC000CommonDto {

	/** 請求先顧客ＩＤ */
	private String seikyusakiKokyakuId;
	/** 請求先顧客名 */
	private String seikyusakiKokyakuNm;
	/** 対応報告メールアドレス（カンマ区切り） */
	private String taioMailAddress;
	/** メール本文 */
	private String mailText;

	/**
	 * 請求先顧客ＩＤを取得します。
	 * 
	 * @return 請求先顧客ＩＤ
	 */
	public String getSeikyusakiKokyakuId() {
		return seikyusakiKokyakuId;
	}

	/**
	 * 請求先顧客ＩＤを設定します。
	 * 
	 * @param seikyusakiKokyakuId 請求先顧客ＩＤ
	 */
	public void setSeikyusakiKokyakuId(String seikyusakiKokyakuId) {
		this.seikyusakiKokyakuId = seikyusakiKokyakuId;
	}

	/**
	 * 請求先顧客名を取得します。
	 * 
	 * @return 請求先顧客名
	 */
	public String getSeikyusakiKokyakuNm() {
		return seikyusakiKokyakuNm;
	}

	/**
	 * 請求先顧客名を設定します。
	 * 
	 * @param seikyusakiKokyakuNm 請求先顧客名
	 */
	public void setSeikyusakiKokyakuNm(String seikyusakiKokyakuNm) {
		this.seikyusakiKokyakuNm = seikyusakiKokyakuNm;
	}

	/**
	 * 対応報告メールアドレス（カンマ区切り）を取得します。
	 * 
	 * @return 対応報告メールアドレス（カンマ区切り）
	 */
	public String getTaioMailAddress() {
		return taioMailAddress;
	}

	/**
	 * 対応報告メールアドレス（カンマ区切り）を設定します。
	 * 
	 * @param taioMailAddress 対応報告メールアドレス（カンマ区切り）
	 */
	public void setTaioMailAddress(String taioMailAddress) {
		this.taioMailAddress = taioMailAddress;
	}

	/**
	 * メール本文を取得します。
	 * 
	 * @return メール本文
	 */
	public String getMailText() {
		return mailText;
	}

	/**
	 * メール本文を設定します。
	 * 
	 * @param mailText メール本文
	 */
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}
}
