package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.TbMKaisha;

/**
 * �ϑ���БI����ʃ��f���B
 *
 * @author k002785
 * @version 1.0 2015/09/08
 */
public class TB016OutsourcerSelectModel extends TB000CommonModel {

	/** ��ʖ� */
	public static final String GAMEN_NM = "�ϑ���БI��";

	/** �������� */
	private TB016OutsourcerSelectCondition condition = new TB016OutsourcerSelectCondition();

	/** �������ʃ��X�g */
	private List<TbMKaisha> resultList;

	/** ���ID���x��name������ */
	private String kaishaIdResultNm;

	/** ��Ж����x��name������ */
	private String kaishaNmResultNm;

	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public TB016OutsourcerSelectCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(TB016OutsourcerSelectCondition condition) {
		this.condition = condition;
	}

	/**
	 * �������ʃ��X�g���擾���܂��B
	 *
	 * @return �������ʃ��X�g
	 */
	public List<TbMKaisha> getResultList() {
		return resultList;
	}
	/**
	 * �������ʃ��X�g��ݒ肵�܂��B
	 *
	 * @param resultList �������ʃ��X�g
	 */
	public void setResultList(List<TbMKaisha> resultList) {
		this.resultList = resultList;
	}

	/**
	 * ���ID���x��name���������擾���܂��B
	 *
	 * @return ���ID���x��name������
	 */
	public String getKaishaIdResultNm() {
		return kaishaIdResultNm;
	}
	/**
	 * ���ID���x��name��������ݒ肵�܂��B
	 *
	 * @param kaishaIdResultNm ���ID���x��name������
	 */
	public void setKaishaIdResultNm(String kaishaIdResultNm) {
		this.kaishaIdResultNm = kaishaIdResultNm;
	}

	/**
	 * ��Ж����x��name���������擾���܂��B
	 *
	 * @return ��Ж����x��name������
	 */
	public String getKaishaNmResultNm() {
		return kaishaNmResultNm;
	}
	/**
	 * ��Ж����x��name��������ݒ肵�܂��B
	 *
	 * @param kaishaNmResultNm ��Ж����x��name������
	 */
	public void setKaishaNmResultNm(String kaishaNmResultNm) {
		this.kaishaNmResultNm = kaishaNmResultNm;
	}
}
