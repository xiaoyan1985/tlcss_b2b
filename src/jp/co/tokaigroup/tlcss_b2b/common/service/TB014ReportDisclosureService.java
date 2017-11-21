package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB014ReportDisclosureModel;

/**
 * �񍐏����J�ݒ�T�[�r�X�N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/11
 */
public interface TB014ReportDisclosureService {

	/**
	 * �������擾�������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB014ReportDisclosureModel getInitInfo(TB014ReportDisclosureModel model);
	
	/**
	 * ���J�������s���܂��B
	 * 
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB014ReportDisclosureModel discloseReport(TB014ReportDisclosureModel model);
	
	/**
	 * ���[�_�E�����[�h�������s���܂��B
	 * 
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB014ReportDisclosureModel downloadReport(TB014ReportDisclosureModel model);
}
