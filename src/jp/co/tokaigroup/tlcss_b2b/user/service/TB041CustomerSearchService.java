package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB041CustomerSearchModel;

/**
 * �����E�����Ҍ����T�[�r�X�N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/03
 */
public interface TB041CustomerSearchService {
	/**
	 * �������擾�������s���܂��B
	 *
	 * @param model �����E�����Ҍ�����ʃ��f��
	 * @return �����E�����Ҍ�����ʃ��f��
	 */
	public TB041CustomerSearchModel getInitInfo(TB041CustomerSearchModel model);

	/**
	 * �����������s���܂��B
	 *
	 * @param model �����E�����Ҍ�����ʃ��f��
	 * @return �����E�����Ҍ�����ʃ��f��
	 */
	public TB041CustomerSearchModel search(TB041CustomerSearchModel model);
}
