package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB050RequestHistoryInfoModel;

/**
 * �ڋq�ڍ׈˗������T�[�r�X�N���X�B
 *
 * @author v145527
 * @version 1.0 2015/07/30
 *
 */

public interface TB050RequestHistoryInfoService {

	/**
	 * �����\���������s���܂��B
	 *
	 * @param model �ڋq�ڍ׈˗��������f��
	 * @return �ڋq�ڍ׈˗��������f��
	 */
	public TB050RequestHistoryInfoModel getInitInfo(TB050RequestHistoryInfoModel model);

}
