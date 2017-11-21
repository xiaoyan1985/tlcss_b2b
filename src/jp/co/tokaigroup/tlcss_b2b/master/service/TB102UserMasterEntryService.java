package jp.co.tokaigroup.tlcss_b2b.master.service;

import jp.co.tokaigroup.tlcss_b2b.master.model.TB102UserMasterEntryModel;

/**
 * ���[�U�[�}�X�^�o�^�T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
public interface TB102UserMasterEntryService {
	/**
	 * �����\���������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 * @return ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public TB102UserMasterEntryModel getInitInfo(TB102UserMasterEntryModel model);

	/**
	 * ���[�U�[���擾�������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 * @return ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public TB102UserMasterEntryModel getUserInfo(TB102UserMasterEntryModel model);

	/**
	 * ���[�U�[���o�^�������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public void insertUserInfo(TB102UserMasterEntryModel model);

	/**
	 * ���[�U�[���X�V�������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public void updateUserInfo(TB102UserMasterEntryModel model);

	/**
	 * �p�X���[�h�Ĕ��s�������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public void reissuePasswordInfo(TB102UserMasterEntryModel model);

	/**
	 * �Q�ƌڋq��ݒ肵�܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public TB102UserMasterEntryModel setRefKokyaku(TB102UserMasterEntryModel model);
}
