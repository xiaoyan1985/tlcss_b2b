package jp.co.tokaigroup.tlcss_b2b.master.service;

import jp.co.tokaigroup.tlcss_b2b.master.model.TB101UserMasterSearchModel;

/**
 * ���[�U�[�}�X�^�����T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/26
 */
public interface TB101UserMasterSearchService {
	/**
	 * �����\���������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^������ʃ��f��
	 * @return ���[�U�[�}�X�^������ʃ��f��
	 */
	public TB101UserMasterSearchModel getInitInfo(TB101UserMasterSearchModel model);

	/**
	 * �����������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^������ʃ��f��
	 * @return ���[�U�[�}�X�^������ʃ��f��
	 */
	public TB101UserMasterSearchModel executeSearch(TB101UserMasterSearchModel model);
}
