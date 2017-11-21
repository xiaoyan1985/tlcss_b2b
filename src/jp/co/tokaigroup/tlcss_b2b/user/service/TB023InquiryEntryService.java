package jp.co.tokaigroup.tlcss_b2b.user.service;


import jp.co.tokaigroup.tlcss_b2b.user.model.TB023InquiryEntryModel;

/**
 * �₢���킹�o�^�T�[�r�X�N���X�B
 *
 * @author k003316
 * @version 1.0 2015/08/11
 * @version 1.1 2015/12/11 S.Nakano isDownloadble���\�b�h�폜�AgetDownloadInfo���\�b�h�ǉ�
 */
public interface TB023InquiryEntryService {
	
	/**
	 * �����\�����擾�������s���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel getInitInfo(TB023InquiryEntryModel model);
	
	/**
	 * �����\�����擾���s���܂��B�i�X�V��ʕ\���p�j
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel getInitInfoForUpdate(TB023InquiryEntryModel model);
	
	/**
	 * �T�[�o�[�T�C�h�G���[�������́A�����\������p�ӂ��܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel parepareInitInfo(TB023InquiryEntryModel model);
	
	/**
	 * �T�[�o�[�T�C�h�G���[�������́A�����\�����擾���s���܂��B�i�X�V��ʕ\���p�j
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel parepareInitInfoForUpdate(TB023InquiryEntryModel model);
	
	/**
	 * �₢���킹����V�K�o�^���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 */
	public void insertToiawaseInfo(TB023InquiryEntryModel model);
	
	/**
	 * �₢���킹�����X�V���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 */
	public void updateToiawaseInfo(TB023InquiryEntryModel model);
	
	/**
	 * �₢���킹�����폜���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 */
	public void deleteToiawaseInfo(TB023InquiryEntryModel model);
	
	/**
	 * ���[�_�E�����[�h���̎擾���s���܂��B
	 * 
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel getPrintDownloadInfo(TB023InquiryEntryModel model);
	
	/**
	 * �₢���킹�t�@�C�������폜���܂��B
	 * 
	 * @param model �₢���킹�o�^��ʃ��f��
	 */
	public void deleteToiawaseFileInfo(TB023InquiryEntryModel model);
	
	/**
	 * �₢���킹�t�@�C�����̃_�E�����[�h�`�F�b�N���s���܂��B
	 * 
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �`�F�b�N���� true�F�`�F�b�NOK�Afalse�F�`�F�b�NNG
	 */
	public boolean isDownloableToiawaseFile(TB023InquiryEntryModel model);
}
