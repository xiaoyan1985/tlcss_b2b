package jp.co.tokaigroup.tlcss_b2b.user.logic;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;


/**
 * �Ǘ����A�b�v���[�h���W�b�N�N���X�B
 *
 * @author v140546
 *
 */
public interface TB043CustomerUploadLogic {

	/**
	 * �Ǘ����A�b�v���[�h��CSV�捞�������s���܂��B
	 *
	 * @param TB043CustomerUploadModel
	 * @return TB043CustomerUploadModel
	 */
	public TB043CustomerUploadModel executeCsvImport(TB043CustomerUploadModel model) ;


	/**
	 * �A�b�v���[�h�����e�[�u���o�^�����A�T�[�o�[�ւ̃t�@�C���R�s�[���s���܂��B
	 *
	 * @param TB043CustomerUploadModel
	 */
	public void insertTbTUploadRirekiAndCopyFile(TB043CustomerUploadModel model);

}