package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB003ExternalLoginModel;

/**
 * �O�����O�C���T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 1.0 2014/04/25
 */
public interface TB003ExternalLoginService {
	/**
	 * �p�����[�^�`�F�b�N���������s���܂��B
	 *
	 * @param model �O�����O�C����ʃ��f��
	 */
	public void validateParameter(TB003ExternalLoginModel model);
}
