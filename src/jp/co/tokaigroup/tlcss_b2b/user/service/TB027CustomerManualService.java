package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB027CustomerManualModel;

/**
 * �ڋq�}�j���A���ꗗ�T�[�r�X�N���X�B
 *
 * @author ���t
 * @version 1.0 2015/08/05
 */
public interface TB027CustomerManualService {
	/**
	 * �����\���������s���܂��B
	 *
	 * @param model �ڋq�}�j���A���ꗗ��ʃ��f��
	 * @return �ڋq�}�j���A���ꗗ��ʃ��f��
	 */
	public TB027CustomerManualModel getInitInfo(TB027CustomerManualModel model);

	/**
	 * �}�j���A���t�@�C���_�E�����[�h�̃`�F�b�N���s���܂��B
	 *
	 * @param model �ڋq�}�j���A���ꗗ��ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean isValidKokyakuManualInfo(TB027CustomerManualModel model);
}
