package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB013IncomingCallReportModel;

/**
 * ���d�񍐏�����T�[�r�X�N���X�B
 *
 * @author v140546
 * @version 1.0 2014/05/29
 * @version 1.1 2015/12/11 S.Nakano isOwn���\�b�h�폜�AgetDownloadInfo���\�b�h�ǉ�
 */
public interface TB013IncomingCallReportService {

	/**
	 * �_�E�����[�h���̎擾���s���܂��B
	 * 
	 * @param model ���d�񍐏�������f��
	 * @return ���d�񍐏�������f��
	 */
	public TB013IncomingCallReportModel getDowloadInfo(TB013IncomingCallReportModel model);
}
