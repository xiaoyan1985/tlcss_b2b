package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintModel;

/**
 * �񍐏�����T�[�r�X�N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/05
 */
public interface TB015ReportPrintService {

	/**
	 * �������擾�������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB015ReportPrintModel getInitInfo(TB015ReportPrintModel model);
	
	/**
	 * ���[�𐶐����܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB015ReportPrintModel createPdf(TB015ReportPrintModel model);
	
	/**
	 * CSV�o�͏����擾���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB015ReportPrintModel createCsvData(TB015ReportPrintModel model);
}
