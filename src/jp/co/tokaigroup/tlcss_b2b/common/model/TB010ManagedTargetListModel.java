package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMGrpKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;

/**
 * �Ǘ��Ώۈꗗ���f���B
 *
 * @author v140546
 * @version 4.0 2014/09/25
 */
public class TB010ManagedTargetListModel {

	/** �I�����ꂽ�ڋqID */
	String selectedKokyakuId;

	/** �O���[�v�ڋq�}�X�^���X�g */
	List<TbMGrpKokyaku> grpKokyakuList;

	/** �Q�ƌڋq�}�X�^���X�g */
	private  List<TbMRefKokyaku> refKokyakuList;

	/** �A�N�Z�X�\URLMap */
	private Map<String, TbMRoleUrl> accessibleMap;

	/** �ڋq�}�X�^��� */
	private RcpMKokyaku kokyakuInfo;
	
	/**
	 * �I�����ꂽ�ڋqID���擾���܂��B
	 *
	 * @return selectedKokyakuId
	 */
	public String getSelectedKokyakuId() {
		return selectedKokyakuId;
	}
	/**
	 * �I�����ꂽ�ڋqID��ݒ肵�܂��B
	 *
	 * @param selectedKokyakuId �Z�b�g���� selectedKokyakuId
	 */
	public void setSelectedKokyakuId(String selectedKokyakuId) {
		this.selectedKokyakuId = selectedKokyakuId;
	}

	/**
	 * �O���[�v�ڋq�}�X�^���X�g���擾���܂��B
	 *
	 * @return grpKokyakuList
	 */
	public List<TbMGrpKokyaku> getGrpKokyakuList() {
		return grpKokyakuList;
	}

	/**
	 * �O���[�v�ڋq�}�X�^���X�g��ݒ肵�܂��B
	 *
	 * @param grpKokyakuList �Z�b�g���� grpKokyakuList
	 */
	public void setGrpKokyakuList(List<TbMGrpKokyaku> grpKokyakuList) {
		this.grpKokyakuList = grpKokyakuList;
	}

	/**
	 * �Q�ƌڋq�}�X�^���X�g���擾���܂��B
	 *
	 * @return refKokyakuList
	 */
	public List<TbMRefKokyaku> getRefKokyakuList() {
		return refKokyakuList;
	}
	/**
	 * �Q�ƌڋq�}�X�^���X�g��ݒ肵�܂��B
	 *
	 * @param refKokyakuList �Z�b�g���� refKokyakuList
	 */
	public void setRefKokyakuList(List<TbMRefKokyaku> refKokyakuList) {
		this.refKokyakuList = refKokyakuList;
	}

	/**
	 * �A�N�Z�X�\URLMap���擾���܂��B
	 *
	 * @return accessibleMap
	 */
	public Map<String, TbMRoleUrl> getAccessibleMap() {
		return accessibleMap;
	}
	/**
	 * �A�N�Z�X�\URLMap��ݒ肵�܂��B
	 *
	 * @param accessibleMap �Z�b�g���� accessibleMap
	 */
	public void setAccessibleMap(Map<String, TbMRoleUrl> accessibleMap) {
		this.accessibleMap = accessibleMap;
	}

	/**
	 * �ڋq�}�X�^�����擾���܂��B
	 *
	 * @return �ڋq�}�X�^���
	 */
	public RcpMKokyaku getKokyakuInfo() {
		return kokyakuInfo;
	}
	/**
	 * �ڋq�}�X�^����ݒ肵�܂��B
	 *
	 * @param kokyakuInfo �ڋq�}�X�^���
	 */
	public void setKokyakuInfo(RcpMKokyaku kokyakuInfo) {
		this.kokyakuInfo = kokyakuInfo;
	}
}
