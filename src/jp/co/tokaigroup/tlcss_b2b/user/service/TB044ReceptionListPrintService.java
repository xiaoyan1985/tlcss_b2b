package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB044ReceptionListPrintModel;/**

 * ��t�ꗗ����T�[�r�X�N���X�B
 *
 * @author v140546
 * @version 1.0 2014/07/08
 */
public interface TB044ReceptionListPrintService {
	/**
	 * �����\�����s���܂��B
	 *
	 * @param model ��t�ꗗ�����ʃ��f��
	 * @return ��t�ꗗ�����ʃ��f��
	 */
	public TB044ReceptionListPrintModel getInitInfo(TB044ReceptionListPrintModel model);

	/**
	 * ����������s���܂��B
	 *
	 * @param model ��t�ꗗ�����ʃ��f��
	 * @return PDF�o�̓t�@�C���p�X
	 */
	public String createPdf(TB044ReceptionListPrintModel model);

}
