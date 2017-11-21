package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB033RequestFullEntryModel;

/**
 * �˗��o�^�T�[�r�X�N���X�B
 * 
 * @author S.Nakano
 * @version 1.0 2015/10/21
 */
public interface TB033RequestFullEntryService {
	/**
	 * �����\���������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	public TB033RequestFullEntryModel getInitInfo(TB033RequestFullEntryModel model);
	
	/**
	 * �T�[�o�[�T�C�h�G���[�������́A�����\���������s���܂��B
	 *
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	public TB033RequestFullEntryModel parepareInitInfo(TB033RequestFullEntryModel model);
	
	/**
	 * ��ƈ˗����擾�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	public TB033RequestFullEntryModel getSagyoIraiInfo(TB033RequestFullEntryModel model);
	
	/**
	 * �T�[�o�[�T�C�h�G���[�������́A�X�V�����\���������s���܂��B
	 *
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	public TB033RequestFullEntryModel parepareInitInfoForUpdate(TB033RequestFullEntryModel model);
	
	/**
	 * ��ƈ˗����o�^�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	public void insertSagyoIraiInfo(TB033RequestFullEntryModel model);
	
	/**
	 * ��ƈ˗����X�V�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	public void updateSagyoIraiInfo(TB033RequestFullEntryModel model);
	
	/**
	 * ��ƈ˗����폜�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	public void deleteSagyoIraiInfo(TB033RequestFullEntryModel model);
	
	/**
	 * �Ǝ҈˗����[�����M�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	public void sendGyoshaIraiMail(TB033RequestFullEntryModel model);
	
	/**
	 * ��Ə󋵉摜�t�@�C���폜�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	public void deleteSagyoJokyoImageFile(TB033RequestFullEntryModel model);
	
	/**
	 * ���̑��t�@�C���폜�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	public void deleteOtherFile(TB033RequestFullEntryModel model);
	
	/**
	 * �摜�_�E�����[�h�����̑Ó����`�F�b�N���s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	public void validateImageFileDownlod(TB033RequestFullEntryModel model);
	
	/**
	 * PDF�쐬�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	public TB033RequestFullEntryModel createPdf(TB033RequestFullEntryModel model);
}
