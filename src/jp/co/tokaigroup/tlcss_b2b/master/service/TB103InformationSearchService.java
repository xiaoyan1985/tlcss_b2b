package jp.co.tokaigroup.tlcss_b2b.master.service;

import jp.co.tokaigroup.tlcss_b2b.master.model.TB103InformationSearchModel;

/**
 * ���m�点������ʃT�[�r�X�N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/18
 */
public interface TB103InformationSearchService {

	/**
	 * �������擾�������s���܂��B
	 *
	 * @param model ���m�点������ʃ��f��
	 * @return ���m�点������ʃ��f��
	 */
	public TB103InformationSearchModel getInitInfo(TB103InformationSearchModel model);

	/**
	 * �����������s���܂��B
	 *
	 * @param model ���m�点������ʃ��f��
	 * @return ���m�点������ʃ��f��
	 */
	public TB103InformationSearchModel search(TB103InformationSearchModel model);

	/**
	 * �폜�������s���܂��B
	 *
	 * @param model ���m�点������ʃ��f��
	 * @return ���m�点������ʃ��f��
	 */
	public void delete(TB103InformationSearchModel model);

}
