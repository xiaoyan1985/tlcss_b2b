package jp.co.tokaigroup.tlcss_b2b.user.model;


import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbTUploadRireki;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;


public class TB043CustomerUploadModel {

	/** ��ʖ� */
	public static final String GAMEN_NM = "�Ǘ����A�b�v���[�h";
	/** �{�^���� �A�b�v���[�h */
	public static final String BUTTON_NM_UPLOAD = "�A�b�v���[�h";
	/** �{�^���� �A�b�v���[�h */
	public static final String BUTTON_NM_DELETE = "�폜";
	/** CSV�A�b�v���[�h���� */
	public static final String CSV_UPLOAD_FLG_NORMAL = "0";
	/** CSV�A�b�v���[�h�G���[ */
	public static final String CSV_UPLOAD_FLG_ILLEGAL = "1";
	/** �t�@�C����ށF������� */
	public static final String FILE_TYPE_BUKKEN = "1";
	/** �t�@�C����ށF�����ҏ�� */
	public static final String FILE_TYPE_NYUKYOSHA = "2";
	/** �t�@�C����ށF���̑� */
	public static final String FILE_TYPE_SONOTA = "9";
	/** �t�@�C�����-���� */
	public static final String FILE_DOWNLOAD_TYPE_NOEMAL = "0";
	/** �t�@�C�����-�G���[ */
	public static final String FILE_DOWNLOAD_TYPE_ERROR = "1";

	/** �t�@�C����� */
	private String fileType;

	/** �t�@�C���R�����g */
	private String fileComment;

	/** �A�b�v���[�h�t�@�C�� */
	private File uploadFile;

	/** �A�b�v���[�h�t�@�C���� */
	private String uploadFileFileName;

	/** �A�b�v���[�h�t�@�C���p�X */
	private String uploadFilePath;

	/** ���[�U�[�R���e�L�X�g */
	private TLCSSB2BUserContext userContext;

	/** �A�b�v���[�h�������X�g */
	private List<TbTUploadRireki> resultList;

	/** �G���[�t�@�C�� */
	private File errorFile;

	/** �G���[�t�@�C���� */
	private String errorFileFileName;

	/** �G���[�t�@�C���p�X */
	private String errorFilePath;

	/** �G���[�t�@�C����� */
	private ValidationPack errorInfo;

	/** CSV�A�b�v���[�h���ۃt���O */
	private String csvUploadFlg;

	/** �폜SEQ�ԍ� */
	private BigDecimal deleteSeqNo;

	/** ���[�U�[�G�[�W�F���g */
	private String userAgent;

	/** �I���t�@�C���p�X */
	private String selectFileNm;

	/** �I���t�@�C����� */
	private String fileDownloadType;

	/**
	 * @return ���[�U�[�R���e�L�X�g
	 */
	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}

	/**
	 * @return fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType �Z�b�g���� fileType
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return fileComment
	 */
	public String getFileComment() {
		return fileComment;
	}

	/**
	 * @param fileComment �Z�b�g���� fileComment
	 */
	public void setFileComment(String fileComment) {
		this.fileComment = fileComment;
	}

	/**
	 * @param userContext
	 */
	public void setUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * @return resultList
	 */
	public List<TbTUploadRireki> getResultList() {
		return resultList;
	}

	/**
	 * @param resultList �Z�b�g���� resultList
	 */
	public void setResultList(List<TbTUploadRireki> resultList) {
		this.resultList = resultList;
	}

	/**
	 * @return uploadFile
	 */
	public File getUploadFile() {
		return uploadFile;
	}

	/**
	 * @param uploadFile �Z�b�g���� uploadFile
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * @return uploadFileFileName
	 */
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	/**
	 * @param uploadFileFileName �Z�b�g���� uploadFileFileName
	 */
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	/**
	 * @return errorFileFileName
	 */
	public String getErrorFileFileName() {
		return errorFileFileName;
	}

	/**
	 * @param errorFileFileName �Z�b�g���� errorFileFileName
	 */
	public void setErrorFileFileName(String errorFileFileName) {
		this.errorFileFileName = errorFileFileName;
	}

	/**
	 * @return errorFileInfo
	 */
	public ValidationPack getErrorInfo() {
		return errorInfo;
	}

	/**
	 * @param errorFileInfo �Z�b�g���� errorFileInfo
	 */
	public void setErrorInfo(ValidationPack errorInfo) {
		this.errorInfo = errorInfo;
	}

	/**
	 * @return csvUploadFlg
	 */
	public String getCsvUploadFlg() {
		return csvUploadFlg;
	}

	/**
	 * @param csvUploadFlg �Z�b�g���� csvUploadFlg
	 */
	public void setCsvUploadFlg(String csvUploadFlg) {
		this.csvUploadFlg = csvUploadFlg;
	}

	/**
	 * CSV�捞���ʂ�����I���������𔻒肵�܂��B
	 *
	 * @return true:����I���Afalse:�ُ�I��
	 */
	public boolean isSuccessCsvUpload() {
		return (StringUtils.isBlank(this.csvUploadFlg) ||
				CSV_UPLOAD_FLG_NORMAL.equals(this.csvUploadFlg));
	}

	/**
	 * @return deleteSeqNo
	 */
	public BigDecimal getDeleteSeqNo() {
		return deleteSeqNo;
	}

	/**
	 * @param deleteSeqNo �Z�b�g���� deleteSeqNo
	 */
	public void setDeleteSeqNo(BigDecimal deleteSeqNo) {
		this.deleteSeqNo = deleteSeqNo;
	}

	/**
	 * @return uploadFilePath
	 */
	public String getUploadFilePath() {
		return uploadFilePath;
	}

	/**
	 * @param uploadFilePath �Z�b�g���� uploadFilePath
	 */
	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	/**
	 * @return errorFile
	 */
	public File getErrorFile() {
		return errorFile;
	}

	/**
	 * @param errorFile �Z�b�g���� errorFile
	 */
	public void setErrorFile(File errorFile) {
		this.errorFile = errorFile;
	}

	/**
	 * @return errorFilePath
	 */
	public String getErrorFilePath() {
		return errorFilePath;
	}

	/**
	 * @param errorFilePath �Z�b�g���� errorFilePath
	 */
	public void setErrorFilePath(String errorFilePath) {
		this.errorFilePath = errorFilePath;
	}

	/**
	 * @return userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent �Z�b�g���� userAgent
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * iPad�Ń��O�C�����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:iPad�Ń��O�C���Afalse:����ȊO
	 */
	public boolean isIPad() {
		if (StringUtils.isBlank(getUserAgent())) {
			return false;
		}

		return getUserAgent().indexOf(Constants.USER_AGENT_KEY_IPAD) != -1;
	}

	/**
	 * @return selectFileNm
	 */
	public String getSelectFileNm() {
		return selectFileNm;
	}

	/**
	 * @param selectFileNm �Z�b�g���� selectFileNm
	 */
	public void setSelectFileNm(String selectFileNm) {
		this.selectFileNm = selectFileNm;
	}

	/**
	 * @return fileDownloadType
	 */
	public String getFileDownloadType() {
		return fileDownloadType;
	}

	/**
	 * @param fileDownloadType �Z�b�g���� fileDownloadType
	 */
	public void setFileDownloadType(String fileDownloadType) {
		this.fileDownloadType = fileDownloadType;
	}

	/**
	 * �w���v��ʂ�URL���擾���܂��B
	 *
	 * @return �w���v��ʂ�URL
	 */
	public String getHelpUrl() {
		return this.userContext.getSystgemContstantAsString(
				RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_UPLOAD_TEMPLATE_URL);
	}

	/**
	 * Macintosh�̋@��Ń��O�C�����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:Macintosh�̋@��Afalse:����ȊO
	 */
	public boolean isMacintosh() {
		if (StringUtils.isBlank(getUserAgent())) {
			return false;
		}

		return getUserAgent().indexOf(Constants.USER_AGENT_KEY_MACINTOSH) != -1;
	}
}
