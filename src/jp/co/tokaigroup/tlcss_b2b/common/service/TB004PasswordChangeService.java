package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB004PasswordChangeModel;

/**
 * �p�X���[�h�ύX�T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 1.0 2014/04/28
 */
public interface TB004PasswordChangeService {
	/**
	 * �p�X���[�h�X�V�������s���܂��B
	 *
	 * @param model �p�X���[�h�ύX��ʃ��f��
	 */
	public void updatePassword(TB004PasswordChangeModel model);
}
