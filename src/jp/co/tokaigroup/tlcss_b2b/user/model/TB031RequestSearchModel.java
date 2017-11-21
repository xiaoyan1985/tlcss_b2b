package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import jp.co.tokaigroup.reception.dto.RC041IraiSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * �˗�������ʃ��f���B
 *
 * @author k002849
 * @version 1.0 2014/05/09
 */
public class TB031RequestSearchModel extends TB000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "�˗�����";

	/** �������� */
	private RC041IraiSearchCondition condition = new RC041IraiSearchCondition();

	/** �������ʃ��X�g */
	private List<RC041IraiSearchDto> resultList;
	/** �T�[�r�X���X�g */
	private List<RcpMService> serviceList;

	/** �O����ߔN�� */
	private String zenkaiShoriYm;

	/** �󋵃��X�g */
	private List<RcpMComCd> jokyoList;

	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public RC041IraiSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(RC041IraiSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �������ʃ��X�g���擾���܂��B
	 *
	 * @return �������ʃ��X�g
	 */
	public List<RC041IraiSearchDto> getResultList() {
		return resultList;
	}
	/**
	 * �������ʃ��X�g��ݒ肵�܂��B
	 *
	 * @param resultList �������ʃ��X�g
	 */
	public void setResultList(List<RC041IraiSearchDto> resultList) {
		this.resultList = resultList;
	}

	/**
	 * �T�[�r�X���X�g���擾���܂��B
	 *
	 * @return �T�[�r�X���X�g
	 */
	public List<RcpMService> getServiceList() {
		return serviceList;
	}
	/**
	 * �T�[�r�X���X�g��ݒ肵�܂��B
	 *
	 * @param serviceList �T�[�r�X���X�g
	 */
	public void setServiceList(List<RcpMService> serviceList) {
		this.serviceList = serviceList;
	}

	/**
	 * �O����ߔN�����擾���܂��B
	 *
	 * @return �O����ߔN��
	 */
	public String getZenkaiShoriYm() {
		return zenkaiShoriYm;
	}
	/**
	 * �O����ߔN����ݒ肵�܂��B
	 *
	 * @param zenkaiShoriYm �O����ߔN��
	 */
	public void setZenkaiShoriYm(String zenkaiShoriYm) {
		this.zenkaiShoriYm = zenkaiShoriYm;
	}

	/**
	 * �󋵃��X�g���擾���܂��B
	 *
	 * @return �󋵃��X�g
	 */
	public List<RcpMComCd> getJokyoList() {
		return jokyoList;
	}
	/**
	 * �󋵃��X�g��ݒ肵�܂��B
	 *
	 * @param jokyoList �󋵃��X�g
	 */
	public void setJokyoList(List<RcpMComCd> jokyoList) {
		this.jokyoList = jokyoList;
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
