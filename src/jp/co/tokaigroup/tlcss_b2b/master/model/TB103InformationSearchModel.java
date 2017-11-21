package jp.co.tokaigroup.tlcss_b2b.master.model;

import java.math.BigDecimal;
import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * ���m�点������ʃ��f���B
 *
 * @author v138130
 * @version 4.0 2014/06/18
 */
public class TB103InformationSearchModel extends TB000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "���m�点����";

	/** �������� */
	private TB103InformationSearchCondition condition = new TB103InformationSearchCondition();

	/** �������ʃ��X�g */
	private List<TbTInformation> resultList;

	/** �\���Ώۗp���X�g */
	private List<RcpMComCd> targetList;

	/** �A�� */
	private BigDecimal seqNo;

	/**
	 * setter & getter
	 */

	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public TB103InformationSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(TB103InformationSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �������ʃ��X�g���擾���܂��B
	 *
	 * @return �������ʃ��X�g
	 */
	public List<TbTInformation> getResultList() {
		return resultList;
	}
	/**
	 * �������ʃ��X�g��ݒ肵�܂��B
	 *
	 * @param resultList �������ʃ��X�g
	 */
	public void setResultList(List<TbTInformation> resultList) {
		this.resultList = resultList;
	}

	/**
	 * �\���Ώۗp���X�g���擾���܂��B
	 *
	 * @return �\���Ώۗp���X�g
	 */
	public List<RcpMComCd> getTargetList() {
		return targetList;
	}
	/**
	 * �\���Ώۗp���X�g��ݒ肵�܂��B
	 *
	 * @param serviceList �\���Ώۗp���X�g
	 */
	public void setTargetList(List<RcpMComCd> targetList) {
		this.targetList = targetList;
	}

	/**
	 * �A�Ԃ��擾���܂��B
	 *
	 * @return �A��
	 */
	public BigDecimal getSeqNo() {
		return seqNo;
	}
	/**
	 * �A�Ԃ�ݒ肵�܂��B
	 *
	 * @param seqNo �A��
	 */
	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}


	/**
	 * �������ʂ�\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isResultDisplay() {
		return !(this.resultList == null || this.resultList.isEmpty());
	}

}
