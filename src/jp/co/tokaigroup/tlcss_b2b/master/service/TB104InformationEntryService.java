package jp.co.tokaigroup.tlcss_b2b.master.service;

import jp.co.tokaigroup.tlcss_b2b.master.model.TB104InformationEntryModel;

/**
 * ���m�点�o�^�T�[�r�X�N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/20
 */
public interface TB104InformationEntryService {
	/**
	 * �����\���������s���܂��B
	 *
	 * @param model ���m�点�o�^��ʃ��f��
	 * @return ���m�点�o�^��ʃ��f��
	 */
	public TB104InformationEntryModel getInitInfo(TB104InformationEntryModel model);

	/**
	 * ���m�点���擾�������s���܂��B
	 *
	 * @param model ���m�点�o�^��ʃ��f��
	 * @return ���m�点�o�^��ʃ��f��
	 */
	public TB104InformationEntryModel getUpdateInitInfo(TB104InformationEntryModel model);

	/**
	 * ���m�点���o�^�������s���܂��B
	 *
	 * @param model ���m�点�o�^��ʃ��f��
	 */
	public TB104InformationEntryModel insertInfo(TB104InformationEntryModel model);

	/**
	 * ���m�点���X�V�������s���܂��B
	 *
	 * @param model ���m�点�o�^��ʃ��f��
	 */
	public void updateInfo(TB104InformationEntryModel model);
}
