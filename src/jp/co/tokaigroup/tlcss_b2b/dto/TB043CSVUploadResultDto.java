package jp.co.tokaigroup.tlcss_b2b.dto;


import jp.co.tokaigroup.reception.dto.RC000CommonDto;
import jp.co.tokaigroup.si.fw.message.ValidationPack;

/**
 * �ڋq���CSV�ꊇ�捞����DTO�B
 *
 * @author k002849
 * @version 1.0 2014/02/27
 */
public class TB043CSVUploadResultDto extends RC000CommonDto {
	/** ���������t���O */
	private boolean isSuccess;
	/** �G���[�t�@�C���p�X */
	private String errorFile;
	/** �G���[�t�@�C���� */
	private String errorFileNm;
	/** �G���[��� */
	private ValidationPack errorInfo;

	/**
	 * ���������t���O���擾���܂��B
	 *
	 * @return ���������t���O
	 */
	public boolean isSuccess() {
		return isSuccess;
	}
	/**
	 * ���������t���O��ݒ肵�܂��B
	 *
	 * @param isSuccess ���������t���O
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * �G���[�t�@�C���p�X���擾���܂��B
	 *
	 * @return �G���[�t�@�C���p�X
	 */
	public String getErrorFile() {
		return errorFile;
	}
	/**
	 * �G���[�t�@�C���p�X��ݒ肵�܂��B
	 *
	 * @param errorFile �G���[�t�@�C���p�X
	 */
	public void setErrorFile(String errorFile) {
		this.errorFile = errorFile;
	}

	/**
	 * �G���[�����擾���܂��B
	 *
	 * @return �G���[���
	 */
	public ValidationPack getErrorInfo() {
		return errorInfo;
	}
	/**
	 * �G���[����ݒ肵�܂��B
	 *
	 * @param errorInfo �G���[���
	 */
	public void setErrorInfo(ValidationPack errorInfo) {
		this.errorInfo = errorInfo;
	}
	/**
	 * @return errorFileNm
	 */
	public String getErrorFileNm() {
		return errorFileNm;
	}
	/**
	 * @param errorFileNm �Z�b�g���� errorFileNm
	 */
	public void setErrorFileNm(String errorFileNm) {
		this.errorFileNm = errorFileNm;
	}
}
