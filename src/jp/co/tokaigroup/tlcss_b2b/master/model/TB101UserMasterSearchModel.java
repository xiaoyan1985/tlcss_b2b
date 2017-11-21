package jp.co.tokaigroup.tlcss_b2b.master.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB101UserMasterSearchCondition;

/**
 * ���[�U�[�}�X�^������ʃ��f���B
 *
 * @author k002849
 * @version 1.0 2014/05/26
 */
public class TB101UserMasterSearchModel extends TB000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "���[�U�[�}�X�^����";

	/** �������� */
	private TB101UserMasterSearchCondition condition = new TB101UserMasterSearchCondition();

	/** �������ʃ��X�g */
	private List<TbMUser> resultList;

	/** �������X�g */
	private List<RcpMComCd> roleList;

	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public TB101UserMasterSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(TB101UserMasterSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �������ʃ��X�g���擾���܂��B
	 *
	 * @return �������ʃ��X�g
	 */
	public List<TbMUser> getResultList() {
		return resultList;
	}
	/**
	 * �������ʃ��X�g��ݒ肵�܂��B
	 *
	 * @param resultList �������ʃ��X�g
	 */
	public void setResultList(List<TbMUser> resultList) {
		this.resultList = resultList;
	}

	/**
	 * �������X�g���擾���܂��B
	 *
	 * @return �������X�g
	 */
	public List<RcpMComCd> getRoleList() {
		return roleList;
	}
	/**
	 * �������X�g��ݒ肵�܂��B
	 *
	 * @param roleList �������X�g
	 */
	public void setRoleList(List<RcpMComCd> roleList) {
		this.roleList = roleList;
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
