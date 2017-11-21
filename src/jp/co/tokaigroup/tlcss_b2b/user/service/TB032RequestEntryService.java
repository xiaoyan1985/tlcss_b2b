package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB032RequestEntryModel;

/**
 * �˗����e�ڍׁE��Ə󋵓o�^�T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 1.0 2014/07/14
 */
public interface TB032RequestEntryService {
	/**
	 * �����\���i�ڍו\���j���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	public TB032RequestEntryModel getDetailInitInfo(TB032RequestEntryModel model);

	/**
	 * �����\���i�o�^�\���j���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	public TB032RequestEntryModel getEntryInitInfo(TB032RequestEntryModel model);

	/**
	 * �����\���i�o�^�\���E�T�[�o�G���[���j���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	public TB032RequestEntryModel getEntryPrepareInitInfo(TB032RequestEntryModel model);

	/**
	 * �摜�폜�������s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	public void deleteImageInfo(TB032RequestEntryModel model);

	/**
	 * �Ǝ҉񓚍�Ə󋵓o�^�������s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	public void insertGyoshaSagyoJokyoInfo(TB032RequestEntryModel model);

	/**
	 * �Ǝ҉񓚍�Ə󋵍X�V�������s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	public void updateGyoshaSagyoJokyoInfo(TB032RequestEntryModel model);

	/**
	 * �摜�_�E�����[�h�̃`�F�b�N���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean checkDownloadInfo(TB032RequestEntryModel model);

	/**
	 * ���̑��t�@�C���_�E�����[�h�̃Z�L�����e�B�`�F�b�N���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean checkOtherFileDownloadInfo(TB032RequestEntryModel model);
}
