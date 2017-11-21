package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;

import jp.co.tokaigroup.reception.dto.RC061GyoshaSearchDto;
import jp.co.tokaigroup.reception.gyosha.model.RC061GyoshaSearchCondition;

/**
 * �ƎґI����ʁi�X�^�u�j���f���B
 *
 * @author k002849
 * @version 1.0 2014/05/29
 */
public class TB012ContractorSelectTestModel {
	/** �������� */
	private RC061GyoshaSearchCondition condition;

	/** �Ǝ҃��X�g */
	private List<RC061GyoshaSearchDto> gyoshaList;

	/** �Ǝ҃R�[�h���x��name������ */
	private String gyoshaCdResultNm;
	/** �ƎҖ����x��name������ */
	private String gyoshaNmResultNm;

	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public RC061GyoshaSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(RC061GyoshaSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �Ǝ҃��X�g���擾���܂��B
	 *
	 * @return �Ǝ҃��X�g
	 */
	public List<RC061GyoshaSearchDto> getGyoshaList() {
		return gyoshaList;
	}
	/**
	 * �Ǝ҃��X�g��ݒ肵�܂��B
	 *
	 * @param gyoshaList �Ǝ҃��X�g
	 */
	public void setGyoshaList(List<RC061GyoshaSearchDto> gyoshaList) {
		this.gyoshaList = gyoshaList;
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
