package jp.co.tokaigroup.tlcss_b2b.dto;

import jp.co.tokaigroup.reception.dto.RC000CommonDto;

/**
 * TORES���J���[�����M��ʂc�s�n�B
 *
 * @author H.Hirai
 * @version 1.0 2016/07/14
 * 
 */
public class TB013DisclosureMailSendDto extends RC000CommonDto {

	/** ������ڋq�h�c */
	private String seikyusakiKokyakuId;
	/** ������ڋq�� */
	private String seikyusakiKokyakuNm;
	/** �Ή��񍐃��[���A�h���X�i�J���}��؂�j */
	private String taioMailAddress;
	/** ���[���{�� */
	private String mailText;

	/**
	 * ������ڋq�h�c���擾���܂��B
	 * 
	 * @return ������ڋq�h�c
	 */
	public String getSeikyusakiKokyakuId() {
		return seikyusakiKokyakuId;
	}

	/**
	 * ������ڋq�h�c��ݒ肵�܂��B
	 * 
	 * @param seikyusakiKokyakuId ������ڋq�h�c
	 */
	public void setSeikyusakiKokyakuId(String seikyusakiKokyakuId) {
		this.seikyusakiKokyakuId = seikyusakiKokyakuId;
	}

	/**
	 * ������ڋq�����擾���܂��B
	 * 
	 * @return ������ڋq��
	 */
	public String getSeikyusakiKokyakuNm() {
		return seikyusakiKokyakuNm;
	}

	/**
	 * ������ڋq����ݒ肵�܂��B
	 * 
	 * @param seikyusakiKokyakuNm ������ڋq��
	 */
	public void setSeikyusakiKokyakuNm(String seikyusakiKokyakuNm) {
		this.seikyusakiKokyakuNm = seikyusakiKokyakuNm;
	}

	/**
	 * �Ή��񍐃��[���A�h���X�i�J���}��؂�j���擾���܂��B
	 * 
	 * @return �Ή��񍐃��[���A�h���X�i�J���}��؂�j
	 */
	public String getTaioMailAddress() {
		return taioMailAddress;
	}

	/**
	 * �Ή��񍐃��[���A�h���X�i�J���}��؂�j��ݒ肵�܂��B
	 * 
	 * @param taioMailAddress �Ή��񍐃��[���A�h���X�i�J���}��؂�j
	 */
	public void setTaioMailAddress(String taioMailAddress) {
		this.taioMailAddress = taioMailAddress;
	}

	/**
	 * ���[���{�����擾���܂��B
	 * 
	 * @return ���[���{��
	 */
	public String getMailText() {
		return mailText;
	}

	/**
	 * ���[���{����ݒ肵�܂��B
	 * 
	 * @param mailText ���[���{��
	 */
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}
}
