package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB011RealEstateAgencySelectModel;

/**

 * �s���Y��Ǘ���БI���T�[�r�X�N���X�B
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
public interface TB011RealEstateAgencySelectService {

	/**
	 * �������������s���܂��B
	 *
	 * @param model �s���Y��Ǘ���БI����ʃ��f��
	 * @return �s���Y��Ǘ���БI����ʃ��f��
	 */
	public TB011RealEstateAgencySelectModel executeSearch(TB011RealEstateAgencySelectModel model);
}
