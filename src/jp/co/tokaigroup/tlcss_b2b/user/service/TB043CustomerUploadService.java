package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;


/**
 * �Ǘ����A�b�v���[�h�T�[�r�X�N���X�B
 *
 * @author v140546
 * @version 1.0 2014/08/04
 */
public interface TB043CustomerUploadService {

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �Ǘ����A�b�v���[�h��ʃ��f��
	 * @return �Ǘ����A�b�v���[�h��ʃ��f��
	 */
	public TB043CustomerUploadModel getInitInfo(TB043CustomerUploadModel model);

	/**
	 * �Ǘ����A�b�v���[�h��CSV�捞�������s���܂��B
	 *
	 * @param model �Ǘ����A�b�v���[�h��ʃ��f��
	 */
	public TB043CustomerUploadModel executeCsvUpload(TB043CustomerUploadModel model);

	/**
	 * �A�b�v���[�h�����̍폜�������s���܂��B
	 *
	 * @param model �Ǘ����A�b�v���[�h��ʃ��f��
	 */
	public TB043CustomerUploadModel deleteUploadRireki(TB043CustomerUploadModel model);
}