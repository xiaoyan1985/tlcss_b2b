package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.sql.Timestamp;

public class TB023InquiryEntryTestModel {
	public TB023InquiryEntryTestModel() {
		super();
	}

	/** �ړ����₢���킹�m�n */
	private String toiawaseNo;

	/** ���͖₢���킹�m�n */
	private String newToiawaseNo;

	/** �₢���킹�X�V�� */
	private Timestamp toiawaseUpdDt;
	
	/** �ύX�O�ڋqID */
	private String oldKokyakuId;
	
	/** ���͌ڋqID */
	private String newKokyakuId;

	/** �J�ڌ���ʋ敪 */
	private String dispKbn;

	/**
	 * �ړ����₢���킹�m�n���擾���܂��B
	 *
	 * @return �ړ����₢���킹�m�n
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * �ړ����₢���킹�m�n��ݒ肵�܂��B
	 *
	 * @param toiawaseNo �ړ����₢���킹�m�n
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * ���͖₢���킹�m�n���擾���܂�
	 *
	 * @return ���͖₢���킹�m�n
	 */
	public String getNewToiawaseNo() {
		return newToiawaseNo;
	}

	/**
	 * ���͖₢���킹�m�n��ݒ肵�܂��B
	 *
	 * @param newToiawaseNo ���͖₢���킹�m�n
	 */
	public void setNewToiawaseNo(String newToiawaseNo) {
		this.newToiawaseNo = newToiawaseNo;
	}

	/**
	 * �₢���킹�X�V�����擾���܂��B
	 *
	 * @return �₢���킹�X�V��
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}

	/**
	 * �₢���킹�X�V����ݒ肵�܂��B
	 *
	 * @param toiawaseUpdDt �₢���킹�X�V��
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}

	/**
	 * �J�ڌ���ʋ敪���擾���܂��B
	 *
	 * @return �J�ڌ���ʋ敪
	 */
	public String getDispKbn() {
		return dispKbn;
	}

	/**
	 * �J�ڌ���ʋ敪��ݒ肵�܂��B
	 *
	 * @param dispKbn �J�ڌ���ʋ敪
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}
	
	/**
	 * �ύX�O�ڋqID���擾���܂��B
	 *
	 * @return �ύX�O�ڋqID
	 */
	public String getOldKokyakuId() {
		return oldKokyakuId;
	}

	/**
	 * �ύX�O�ڋqID��ݒ肵�܂��B
	 *
	 * @param oldKokyakuId �ύX�O�ڋqID
	 */
	public void setOldKokyakuId(String oldKokyakuId) {
		this.oldKokyakuId = oldKokyakuId;
	}

	/**
	 * ���͌ڋqID���擾���܂��B
	 *
	 * @return ���͌ڋqID
	 */
	public String getNewKokyakuId() {
		return newKokyakuId;
	}

	/**
	 * ���͌ڋqID��ݒ肵�܂��B
	 *
	 * @param newKokyakuId ���͌ڋqID
	 */
	public void setNewKokyakuId(String newKokyakuId) {
		this.newKokyakuId = newKokyakuId;
	}
}
