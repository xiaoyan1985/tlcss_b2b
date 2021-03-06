package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.sql.Timestamp;

public class TB023InquiryEntryTestModel {
	public TB023InquiryEntryTestModel() {
		super();
	}

	/** 移動元問い合わせＮＯ */
	private String toiawaseNo;

	/** 入力問い合わせＮＯ */
	private String newToiawaseNo;

	/** 問い合わせ更新日 */
	private Timestamp toiawaseUpdDt;
	
	/** 変更前顧客ID */
	private String oldKokyakuId;
	
	/** 入力顧客ID */
	private String newKokyakuId;

	/** 遷移元画面区分 */
	private String dispKbn;

	/**
	 * 移動元問い合わせＮＯを取得します。
	 *
	 * @return 移動元問い合わせＮＯ
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * 移動元問い合わせＮＯを設定します。
	 *
	 * @param toiawaseNo 移動元問い合わせＮＯ
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * 入力問い合わせＮＯを取得します
	 *
	 * @return 入力問い合わせＮＯ
	 */
	public String getNewToiawaseNo() {
		return newToiawaseNo;
	}

	/**
	 * 入力問い合わせＮＯを設定します。
	 *
	 * @param newToiawaseNo 入力問い合わせＮＯ
	 */
	public void setNewToiawaseNo(String newToiawaseNo) {
		this.newToiawaseNo = newToiawaseNo;
	}

	/**
	 * 問い合わせ更新日を取得します。
	 *
	 * @return 問い合わせ更新日
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}

	/**
	 * 問い合わせ更新日を設定します。
	 *
	 * @param toiawaseUpdDt 問い合わせ更新日
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}

	/**
	 * 遷移元画面区分を取得します。
	 *
	 * @return 遷移元画面区分
	 */
	public String getDispKbn() {
		return dispKbn;
	}

	/**
	 * 遷移元画面区分を設定します。
	 *
	 * @param dispKbn 遷移元画面区分
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}
	
	/**
	 * 変更前顧客IDを取得します。
	 *
	 * @return 変更前顧客ID
	 */
	public String getOldKokyakuId() {
		return oldKokyakuId;
	}

	/**
	 * 変更前顧客IDを設定します。
	 *
	 * @param oldKokyakuId 変更前顧客ID
	 */
	public void setOldKokyakuId(String oldKokyakuId) {
		this.oldKokyakuId = oldKokyakuId;
	}

	/**
	 * 入力顧客IDを取得します。
	 *
	 * @return 入力顧客ID
	 */
	public String getNewKokyakuId() {
		return newKokyakuId;
	}

	/**
	 * 入力顧客IDを設定します。
	 *
	 * @param newKokyakuId 入力顧客ID
	 */
	public void setNewKokyakuId(String newKokyakuId) {
		this.newKokyakuId = newKokyakuId;
	}
}
