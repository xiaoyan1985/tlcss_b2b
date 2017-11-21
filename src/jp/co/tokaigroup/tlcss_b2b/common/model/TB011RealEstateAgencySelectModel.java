package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;

/**
 * �Ǘ���БI����ʃ��f���B
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
public class TB011RealEstateAgencySelectModel extends TB000CommonModel {

	/** ��ʖ� */
	public static final String GAMEN_NM = "�Ǘ���БI��";

	/** �������� */
	private TB011RealEstateAgencySelectCondition condition = new TB011RealEstateAgencySelectCondition();

	/** �������ʃ��X�g */
	private List<RcpMKokyaku> resultList;

	// �I��p�p�����[�^
	/** �ڋq�h�c���x��name������ */
	private String kokyakuIdResultNm;
	/** �ڋq�����x��name������ */
	private String kokyakuNmResultNm;
	/** ���X�g�ڋq�����x��name������ */
	private String kokyakuListNmResultNm;

	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public TB011RealEstateAgencySelectCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(TB011RealEstateAgencySelectCondition condition) {
		this.condition = condition;
	}

	/**
	 * �������ʃ��X�g���擾���܂��B
	 *
	 * @return �������ʃ��X�g
	 */
	public List<RcpMKokyaku> getResultList() {
		return resultList;
	}
	/**
	 * �������ʃ��X�g��ݒ肵�܂��B
	 *
	 * @param resultList �������ʃ��X�g
	 */
	public void setResultList(List<RcpMKokyaku> resultList) {
		this.resultList = resultList;
	}

	/**
	 * �ڋq�h�c���x��name���������擾���܂��B
	 *
	 * @return �ڋq�h�c���x��name������
	 */
	public String getKokyakuIdResultNm() {
		return kokyakuIdResultNm;
	}
	/**
	 * �ڋq�h�c���x��name��������ݒ肵�܂��B
	 *
	 * @param kokyakuIdResultNm �ڋq�h�c���x��name������
	 */
	public void setKokyakuIdResultNm(String kokyakuIdResultNm) {
		this.kokyakuIdResultNm = kokyakuIdResultNm;
	}

	/**
	 * �ڋq�����x��name���������擾���܂��B
	 *
	 * @return �ڋq�����x��name������
	 */
	public String getKokyakuNmResultNm() {
		return kokyakuNmResultNm;
	}
	/**
	 * �ڋq�����x��name��������ݒ肵�܂��B
	 *
	 * @param kokyakuNmResultNm �ڋq�����x��name������
	 */
	public void setKokyakuNmResultNm(String kokyakuNmResultNm) {
		this.kokyakuNmResultNm = kokyakuNmResultNm;
	}

	/**
	 * ���X�g�ڋq�����x��name���������擾���܂��B
	 *
	 * @return kokyakuListNmResultNm
	 */
	public String getKokyakuListNmResultNm() {
		return kokyakuListNmResultNm;
	}
	/**
	 * ���X�g�ڋq�����x��name��������ݒ肵�܂��B
	 *
	 * @param kokyakuListNmResultNm �Z�b�g���� kokyakuListNmResultNm
	 */
	public void setKokyakuListNmResultNm(String kokyakuListNmResultNm) {
		this.kokyakuListNmResultNm = kokyakuListNmResultNm;
	}

}
