package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB016OutsourcerSelectModel;

/**
 * �ϑ���БI���T�[�r�X�N���X�B
 *
 * @author k002785
 * @version 1.0 2015/09/08
 */
public interface TB016OutsourcerSelectService {

	/**
	 * �������������s���܂��B
	 *
	 * @param model �ϑ���БI����ʃ��f��
	 * @return �ϑ���БI����ʃ��f��
	 */
	public TB016OutsourcerSelectModel executeSearch(TB016OutsourcerSelectModel model);
}
