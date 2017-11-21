package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.Map;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010DocumentFileDownloadModel;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010MenuModel;

/**
 * ���j���[�T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 4.0 2014/06/05
 * @version 4.1 2015/10/28 C.Kobayashi �ϑ��֘A��Ѓ`�F�b�N�̒ǉ�
 */
public interface TB010MenuService {
	/**
	 * �����\���������s���܂��B
	 *
	 * @param model ���j���[��ʃ��f��
	 * @return ���j���[��ʃ��f��
	 */
	public TB010MenuModel getInitInfo(TB010MenuModel model);

	/**
	 * �A�N�Z�X�\�t�q�kMap�擾�������s���܂��B
	 *
	 * @param role ����
	 * @return �A�N�Z�X�\�t�q�kMap
	 */
	public Map<String,TbMRoleUrl> getAccessUrl(String role);

	/**
	 * �ڋq�}�X�^����ڋq�����擾���܂��B
	 * 
	 * @param selectedKokyakuId �I�������ڋq���
	 * @return �ڋq�}�X�^���
	 */
	public RcpMKokyaku getKokyakuInfo(String selectedKokyakuId);
	
	/**
	 * �ϑ���Њ֘A�`�F�b�N���s���܂��B
	 *
	 * @param model���j���[�_�E�����[�h��ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK
	 */
	public boolean isValidDocumentDownload(TB010DocumentFileDownloadModel model);
}
