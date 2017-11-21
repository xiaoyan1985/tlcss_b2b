package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;

/**
 * �s���Y�E�Ǘ���БI����ʁi�X�^�u�j���f���B
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
public class TB011RealEstateAgencySelectTestModel {
	/** �������� */
	private RC011KokyakuSearchCondition condition = new RC011KokyakuSearchCondition();
	/** �ڋq��񃊃X�g */
	private List<RcpMKokyaku> kokyakuList;

	// �I��p�p�����[�^
	/** �ڋq�h�c���x��name������ */
	private String kokyakuIdResultNm;
	/** �ڋq�����x��name������ */
	private String kokyakuNmResultNm;

	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public RC011KokyakuSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(RC011KokyakuSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �ڋq��񃊃X�g���擾���܂��B
	 *
	 * @return �ڋq��񃊃X�g
	 */
	public List<RcpMKokyaku> getKokyakuList() {
		return kokyakuList;
	}
	/**
	 * �ڋq��񃊃X�g��ݒ肵�܂��B
	 *
	 * @param kokyakuList �ڋq��񃊃X�g
	 */
	public void setKokyakuList(List<RcpMKokyaku> kokyakuList) {
		this.kokyakuList = kokyakuList;
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
}
