package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB029InquiryKbnManualModel;

/**
 * �₢���킹�敪�}�j���A���ꗗ�T�[�r�X�N���X�B
 *
 * @author H.Yamamura
 * @version 1.0 2015/11/20
 */
public interface TB029InquiryKbnManualService {

	/**
	 * �_�E�����[�h����t�@�C�������擾���܂��B
	 *
	 * @param model �₢���킹�敪�}�j���A���ꗗ��ʃ��f��
	 * @return �t�@�C����
	 */
	public String getFileNm(TB029InquiryKbnManualModel model);
}
