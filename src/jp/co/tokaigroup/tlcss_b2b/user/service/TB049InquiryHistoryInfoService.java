package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB049InquiryHistoryInfoModel;

/**
 * �ڋq�ڍז₢���킹�����T�[�r�X�N���X�B
 *
 * @author v145527
 * @version 1.0 2015/07/24
 *
 */

public interface TB049InquiryHistoryInfoService {
	
	/**
	 * �����\���������s���܂��B
	 *
	 * @param model �ڋq�ڍז₢���킹�������f��
	 * @return �ڋq�ڍז₢���킹�������f��
	 */
	public TB049InquiryHistoryInfoModel getInitInfo(TB049InquiryHistoryInfoModel model);

}
