package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB022InquiryDetailModel;
/**

 * �₢���킹�ڍ׃T�[�r�X�N���X�B
 *
 * @author v140546
 * @version 1.0 2014/05/27
 * @version 1.1 2015/10/23 C.Kobayashi ���ǖ��ǋ@�\�ǉ��Ή�
 */
public interface TB022InquiryDetailService {
	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �₢���킹�ڍ׉�ʃ��f��
	 * @return �₢���킹�ڍ׉�ʃ��f��
	 */
	public TB022InquiryDetailModel getInitInfo(TB022InquiryDetailModel model);

	/**
	 * �₢���킹�����E�₢���킹�����X�V���܂��B
	 *
	 * @param model �₢���킹�ڍ׉�ʃ��f��
	 */
	public void updateInquiryHistoryDetailInfo(TB022InquiryDetailModel model);

	/**
	 * �₢���킹�t�@�C�����̃_�E�����[�h�`�F�b�N���s���܂��B
	 *
	 * @param model �₢���킹�ڍ׉�ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean canWorkReportDownload(TB022InquiryDetailModel model);

}
