package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB010ManagedTargetListModel;

/**
 * �Ǘ��Ώۈꗗ�T�[�r�X�N���X�B
 *
 * @author v140546
 * @version 4.0 2014/09/25
 */
public interface TB010ManagedTargetListService {

	/**
	 * �����������s���܂��B
	 *
	 * @param model �Ǘ��Ώۈꗗ���f��
	 */
	public TB010ManagedTargetListModel getInitInfo(TB010ManagedTargetListModel model);

	/**
	 * �ڋq�I���������s���܂��B
	 *
	 * @param model �Ǘ��Ώۈꗗ���f��
	 */
	public TB010ManagedTargetListModel selectKokyakuId(TB010ManagedTargetListModel model);
}
