package jp.co.tokaigroup.tlcss_b2b.exception;

import jp.co.tokaigroup.si.fw.exception.ApplicationException;

/**
 * �Z�L�����e�B�`�F�b�N�G���[�N���X�B
 * 
 * @author k002849
 * @version 1.0 2015/07/28
 */
public class ForbiddenException extends ApplicationException {
	/**
	 * �R���X�g���N�^�B
	 */
	public ForbiddenException() {
		super();
	}
	
	/**
	 * �R���X�g���N�^�B
	 *
	 * @param caulse ������O
	 */
	public ForbiddenException(Throwable caulse) {
		super(caulse);
	}

	/**
	 * �R���X�g���N�^�B
	 *
	 * @param errMsg �G���[���b�Z�[�W
	 */
	public ForbiddenException(String errMsg) {
		super(errMsg);
	}

	/**
	 * �R���X�g���N�^�B
	 *
	 * @param msg ������O���b�Z�[�W
	 * @param caulse ������O
	 */
	public ForbiddenException(String msg, Throwable caulse) {
		super(msg, caulse);
	}
}
