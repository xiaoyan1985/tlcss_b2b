package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB012ContractorSelectTestModel;

/**
 * �ƎґI����ʁi�X�^�u�j�T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/29
 */
public interface TB012ContractorSelectTestService {
	/**
	 * �Ǝ҃��X�g���擾���܂��B
	 *
	 * @param model �ƎґI����ʁi�X�^�u�j���f��
	 * @return �ƎґI����ʁi�X�^�u�j���f��
	 */
	public TB012ContractorSelectTestModel getGyoshaList(TB012ContractorSelectTestModel model);
}
