package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB001LoginModel;

/**
 * ���O�C���T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 1.0 2014/04/22
 */
public interface TB001LoginService {
	/**
	 * ���O�C���������s���܂��B
	 *
	 * @param model ���O�C����ʃ��f��
	 * @return ���O�C����ʃ��f��
	 */
	public TB001LoginModel executeLogin(TB001LoginModel model);
}
