package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB013DisclosureMailSendModel;

/**
 * TORES���J���[�����M��ʃT�[�r�X�N���X�B
 *
 * @author k003856
 * @version 5.0 2015/09/08
 */
public interface TB013DisclosureMailSendService {
	/**
	 * �����\���������s���܂��B
	 *
	 * @param model TORES���J���[�����M��ʃ��f��
	 * @return TORES���J���[�����M��ʃ��f��
	 */
	public TB013DisclosureMailSendModel getInitInfo(TB013DisclosureMailSendModel model);

	/**
	 * ���[�����M�������s���܂��B
	 *
	 * @param model TORES���J���[�����M��ʃ��f��
	 */
	public void executeSendMail(TB013DisclosureMailSendModel model);
}
