package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB026InquiryHistoryMoveModel;


/**
 * �₢���킹�����ړ���ʃT�[�r�X�N���X�B
 *
 * @author ���t
 *
 */
public interface TB026InquiryHistoryMoveService {
	/**
	 * �����\���������s���܂��B
	 *
	 * @param model �₢���킹�����ړ���ʃ��f��
	 * @return �₢���킹�����ړ���ʃ��f��
	 */
	public TB026InquiryHistoryMoveModel getInitInfo(TB026InquiryHistoryMoveModel model);

	/**
	 * �₢���킹�����ړ��������s���܂��B
	 *
	 * @param model�₢���킹�����ړ���ʃ��f��
	 */
	public void updateInquiryHistoryMoveInfo(TB026InquiryHistoryMoveModel model);
}
