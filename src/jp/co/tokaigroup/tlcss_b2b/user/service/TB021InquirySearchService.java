package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB021InquirySearchModel;
/**

 * �₢���킹�����T�[�r�X�N���X�B
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 1.1 2015/12/21 H.Yamamura CSV�_�E�����[�h�t���O�̒ǉ�
 */
public interface TB021InquirySearchService {
	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �₢���킹������ʃ��f��
	 * @return �₢���킹������ʃ��f��
	 */
	public TB021InquirySearchModel getInitInfo(TB021InquirySearchModel model);

	/**
	 * �������������s���܂��B
	 *
	 * @param model �₢���킹������ʃ��f��
	 * @param csvDownloadFlg CSV�_�E�����[�h�t���O�itrue�FCSV�_�E�����[�h�j
	 * @return �₢���킹������ʃ��f��
	 */
	public TB021InquirySearchModel executeSearch(TB021InquirySearchModel model, boolean csvDownloadFlg);
}
