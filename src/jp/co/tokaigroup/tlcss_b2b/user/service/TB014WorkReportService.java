package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB014WorkReportModel;

/**
 * ��ƕ񍐏�����T�[�r�X�N���X�B
 *
 * @author k002849
 * @version 4.0 2014/07/22
 */
public interface TB014WorkReportService {
	/**
	 * ��ƕ񍐏��̃_�E�����[�h�`�F�b�N���s���܂��B
	 *
	 * @param model ��ƕ񍐏�������f��
	 * @return true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean isOwn(TB014WorkReportModel model);
}
