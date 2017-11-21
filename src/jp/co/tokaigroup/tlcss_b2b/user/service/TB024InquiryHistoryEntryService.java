package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB024InquiryHistoryEntryModel;

/**
 * �₢���킹����o�^�T�[�r�X�N���X�B
 *
 * @author v145527
 * @version 1.0 2015/08/28
 *
 */
public interface TB024InquiryHistoryEntryService {

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 * @return �₢���킹����o�^��ʃ��f��
	 */
	public TB024InquiryHistoryEntryModel getInitInfo(TB024InquiryHistoryEntryModel model);
	
	/**
	 * �����\�����擾���s���܂��B�i�X�V��ʕ\���p�j
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB024InquiryHistoryEntryModel getInitInfoForUpdate(TB024InquiryHistoryEntryModel model);	

	/**
	 * �₢���킹�������o�^���s���܂��B
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 */
	public void insertToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model);

	/**
	 * �₢���킹�������X�V���s���܂��B
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 */
	public void updateToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model);

	/**
	 * �₢���킹�������폜���s���܂��B
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 */
	public void deleteToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model);

}
