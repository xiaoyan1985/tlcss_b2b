package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB031RequestSearchModel;

/**
 * �˗������T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/09
 */
public interface TB031RequestSearchService {
	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �˗�������ʃ��f��
	 * @return �˗�������ʃ��f��
	 */
	public TB031RequestSearchModel getInitInfo(TB031RequestSearchModel model);

	/**
	 * �������������s���܂��B
	 *
	 * @param model �˗�������ʃ��f��
	 * @return �˗�������ʃ��f��
	 */
	public TB031RequestSearchModel executeSearch(TB031RequestSearchModel model);
}
