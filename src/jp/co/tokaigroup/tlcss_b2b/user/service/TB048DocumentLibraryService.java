package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB048DocumentLibraryModel;


/**
 * �������C�u�����ꗗ�T�[�r�X�N���X�B
 *
 * @author C.Kobayashi
 * @version 1.0 2015/10/28
 */
public interface TB048DocumentLibraryService {

	/**
	 * �����\���������s���܂��B
	 *
	 * @return TORES�������C�u�����ꗗ��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	public TB048DocumentLibraryModel getInitInfo(TB048DocumentLibraryModel model);
}
