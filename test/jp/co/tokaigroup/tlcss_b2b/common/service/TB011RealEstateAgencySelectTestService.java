package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB011RealEstateAgencySelectTestModel;

/**
 * �s���Y�E�Ǘ���БI����ʁi�X�^�u�j�T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
public interface TB011RealEstateAgencySelectTestService {
	/**
	 * �ڋq��񃊃X�g���擾���܂��B
	 *
	 * @param model �s���Y�E�Ǘ���БI����ʁi�X�^�u�j���f��
	 * @return �s���Y�E�Ǘ���БI����ʁi�X�^�u�j���f��
	 */
	public TB011RealEstateAgencySelectTestModel getKokyakuList(TB011RealEstateAgencySelectTestModel model);
}
