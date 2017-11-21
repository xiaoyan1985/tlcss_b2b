package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.sql.Timestamp;

public class TB023InquiryEntryTestModel {
	public TB023InquiryEntryTestModel() {
		super();
	}

	/** ˆÚ“®Œ³–â‚¢‡‚í‚¹‚m‚n */
	private String toiawaseNo;

	/** “ü—Í–â‚¢‡‚í‚¹‚m‚n */
	private String newToiawaseNo;

	/** –â‚¢‡‚í‚¹XV“ú */
	private Timestamp toiawaseUpdDt;
	
	/** •ÏX‘OŒÚ‹qID */
	private String oldKokyakuId;
	
	/** “ü—ÍŒÚ‹qID */
	private String newKokyakuId;

	/** ‘JˆÚŒ³‰æ–Ê‹æ•ª */
	private String dispKbn;

	/**
	 * ˆÚ“®Œ³–â‚¢‡‚í‚¹‚m‚n‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ˆÚ“®Œ³–â‚¢‡‚í‚¹‚m‚n
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * ˆÚ“®Œ³–â‚¢‡‚í‚¹‚m‚n‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param toiawaseNo ˆÚ“®Œ³–â‚¢‡‚í‚¹‚m‚n
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * “ü—Í–â‚¢‡‚í‚¹‚m‚n‚ğæ“¾‚µ‚Ü‚·
	 *
	 * @return “ü—Í–â‚¢‡‚í‚¹‚m‚n
	 */
	public String getNewToiawaseNo() {
		return newToiawaseNo;
	}

	/**
	 * “ü—Í–â‚¢‡‚í‚¹‚m‚n‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param newToiawaseNo “ü—Í–â‚¢‡‚í‚¹‚m‚n
	 */
	public void setNewToiawaseNo(String newToiawaseNo) {
		this.newToiawaseNo = newToiawaseNo;
	}

	/**
	 * –â‚¢‡‚í‚¹XV“ú‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return –â‚¢‡‚í‚¹XV“ú
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}

	/**
	 * –â‚¢‡‚í‚¹XV“ú‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param toiawaseUpdDt –â‚¢‡‚í‚¹XV“ú
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}

	/**
	 * ‘JˆÚŒ³‰æ–Ê‹æ•ª‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ‘JˆÚŒ³‰æ–Ê‹æ•ª
	 */
	public String getDispKbn() {
		return dispKbn;
	}

	/**
	 * ‘JˆÚŒ³‰æ–Ê‹æ•ª‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param dispKbn ‘JˆÚŒ³‰æ–Ê‹æ•ª
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}
	
	/**
	 * •ÏX‘OŒÚ‹qID‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return •ÏX‘OŒÚ‹qID
	 */
	public String getOldKokyakuId() {
		return oldKokyakuId;
	}

	/**
	 * •ÏX‘OŒÚ‹qID‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param oldKokyakuId •ÏX‘OŒÚ‹qID
	 */
	public void setOldKokyakuId(String oldKokyakuId) {
		this.oldKokyakuId = oldKokyakuId;
	}

	/**
	 * “ü—ÍŒÚ‹qID‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return “ü—ÍŒÚ‹qID
	 */
	public String getNewKokyakuId() {
		return newKokyakuId;
	}

	/**
	 * “ü—ÍŒÚ‹qID‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param newKokyakuId “ü—ÍŒÚ‹qID
	 */
	public void setNewKokyakuId(String newKokyakuId) {
		this.newKokyakuId = newKokyakuId;
	}
}
