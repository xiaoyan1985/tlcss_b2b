package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB051CenterContractorSearchModel;

/**
 * �Z���^�[�ƎҌ����T�[�r�X�N���X�B
 *
 * @author v145527
 * @version 1.0 2015/09/29
 */
public interface TB051CenterContractorSearchService {
	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �ڋq������ʃ��f��
	 * @return �ڋq������ʃ��f��
	 */
	public TB051CenterContractorSearchModel getInitInfo(TB051CenterContractorSearchModel model);

	/**
	 * �Ǝҏ����擾���܂��B
	 *
	 * @param model �ƎҌ�����ʃ��f��
	 * @return �ƎҌ�����ʃ��f��
	 */
	public TB051CenterContractorSearchModel search(TB051CenterContractorSearchModel model);

}
