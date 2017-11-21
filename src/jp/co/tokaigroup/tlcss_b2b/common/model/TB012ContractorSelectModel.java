package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMGyosha;

/**
 * �ƎґI����ʃ��f���B
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
public class TB012ContractorSelectModel extends TB000CommonModel {

	/** ��ʖ� */
	public static final String GAMEN_NM = "�ƎґI��";

	/** �������� */
	private TB012ContractorSelectCondition condition = new TB012ContractorSelectCondition();

	/** �������ʃ��X�g */
	private List<RcpMGyosha> resultList;

	/** �Ǝ҃R�[�h���x��name������ */
	private String gyoshaCdResultNm;
	/** �ƎҖ����x��name������ */
	private String gyoshaNmResultNm;


	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public TB012ContractorSelectCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(TB012ContractorSelectCondition condition) {
		this.condition = condition;
	}

	/**
	 * �������ʃ��X�g���擾���܂��B
	 *
	 * @return �������ʃ��X�g
	 */
	public List<RcpMGyosha> getResultList() {
		return resultList;
	}
	/**
	 * �������ʃ��X�g��ݒ肵�܂��B
	 *
	 * @param resultList �������ʃ��X�g
	 */
	public void setResultList(List<RcpMGyosha> resultList) {
		this.resultList = resultList;
	}

	/**
	 * �Ǝ҃R�[�h���x��name���������擾���܂��B
	 *
	 * @return �Ǝ҃R�[�h���x��name������
	 */
	public String getGyoshaCdResultNm() {
		return gyoshaCdResultNm;
	}
	/**
	 * �Ǝ҃R�[�h���x��name��������ݒ肵�܂��B
	 *
	 * @param gyoshaCdResultNm �Ǝ҃R�[�h���x��name������
	 */
	public void setGyoshaCdResultNm(String gyoshaCdResultNm) {
		this.gyoshaCdResultNm = gyoshaCdResultNm;
	}

	/**
	 * �ƎҖ����x��name���������擾���܂��B
	 *
	 * @return �ƎҖ����x��name������
	 */
	public String getGyoshaNmResultNm() {
		return gyoshaNmResultNm;
	}
	/**
	 * �ƎҖ����x��name��������ݒ肵�܂��B
	 *
	 * @param gyoshaNmResultNm �ƎҖ����x��name������
	 */
	public void setGyoshaNmResultNm(String gyoshaNmResultNm) {
		this.gyoshaNmResultNm = gyoshaNmResultNm;
	}
}
