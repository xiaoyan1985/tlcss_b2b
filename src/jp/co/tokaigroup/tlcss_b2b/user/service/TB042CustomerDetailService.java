package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB042CustomerDetailModel;


/**
 * �����E�����ҏڍ׃T�[�r�X�N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/06
 */
public interface TB042CustomerDetailService {

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �����E�����ҏڍ׉�ʃ��f��
	 * @return �����E�����ҏڍ׉�ʃ��f��
	 */
	public TB042CustomerDetailModel getInitInfo(TB042CustomerDetailModel model);
}
