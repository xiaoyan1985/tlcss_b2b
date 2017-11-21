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

	/** 画面名 */
	public static final String GAMEN_NM = "管理情報アップロード";
	/** ボタン名 アップロード */
	public static final String BUTTON_NM_UPLOAD = "アップロード";
	/** ボタン名 アップロード */
	public static final String BUTTON_NM_DELETE = "削除";
	/** CSVアップロード正常 */
	public static final String CSV_UPLOAD_FLG_NORMAL = "0";
	/** CSVアップロードエラー */
	public static final String CSV_UPLOAD_FLG_ILLEGAL = "1";
	/** ファイル種類：物件情報 */
	public static final String FILE_TYPE_BUKKEN = "1";
	/** ファイル種類：入居者情報 */
	public static final String FILE_TYPE_NYUKYOSHA = "2";
	/** ファイル種類：その他 */
	public static final String FILE_TYPE_SONOTA = "9";
	/** ファイル種類-正常 */
	public static final String FILE_DOWNLOAD_TYPE_NOEMAL = "0";
	/** ファイル種類-エラー */
	public static final String FILE_DOWNLOAD_TYPE_ERROR = "1";

	/** ファイル種別 */
	private String fileType;

	/** ファイルコメント */
	private String fileComment;

	/** アップロードファイル */
	private File uploadFile;

	/** アップロードファイル名 */
	private String uploadFileFileName;

	/** アップロードファイルパス */
	private String uploadFilePath;

	/** ユーザーコンテキスト */
	private TLCSSB2BUserContext userContext;

	/** アップロード履歴リスト */
	private List<TbTUploadRireki> resultList;

	/** エラーファイル */
	private File errorFile;

	/** エラーファイル名 */
	private String errorFileFileName;

	/** エラーファイルパス */
	private String errorFilePath;

	/** エラーファイル情報 */
	private ValidationPack errorInfo;

	/** CSVアップロード成否フラグ */
	private String csvUploadFlg;

	/** 削除SEQ番号 */
	private BigDecimal deleteSeqNo;

	/** ユーザーエージェント */
	private String userAgent;

	/** 選択ファイルパス */
	private String selectFileNm;

	/** 選択ファイル種類 */
	private String fileDownloadType;

	/**
	 * @return ユーザーコンテキスト
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
	 * @param fileType セットする fileType
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
	 * @param fileComment セットする fileComment
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
	 * @param resultList セットする resultList
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
	 * @param uploadFile セットする uploadFile
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
	 * @param uploadFileFileName セットする uploadFileFileName
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
	 * @param errorFileFileName セットする errorFileFileName
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
	 * @param errorFileInfo セットする errorFileInfo
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
	 * @param csvUploadFlg セットする csvUploadFlg
	 */
	public void setCsvUploadFlg(String csvUploadFlg) {
		this.csvUploadFlg = csvUploadFlg;
	}

	/**
	 * CSV取込結果が正常終了したかを判定します。
	 *
	 * @return true:正常終了、false:異常終了
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
	 * @param deleteSeqNo セットする deleteSeqNo
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
	 * @param uploadFilePath セットする uploadFilePath
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
	 * @param errorFile セットする errorFile
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
	 * @param errorFilePath セットする errorFilePath
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
	 * @param userAgent セットする userAgent
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * iPadでログインしているかを判定します。
	 *
	 * @return true:iPadでログイン、false:それ以外
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
	 * @param selectFileNm セットする selectFileNm
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
	 * @param fileDownloadType セットする fileDownloadType
	 */
	public void setFileDownloadType(String fileDownloadType) {
		this.fileDownloadType = fileDownloadType;
	}

	/**
	 * ヘルプ画面のURLを取得します。
	 *
	 * @return ヘルプ画面のURL
	 */
	public String getHelpUrl() {
		return this.userContext.getSystgemContstantAsString(
				RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_UPLOAD_TEMPLATE_URL);
	}

	/**
	 * Macintoshの機種でログインしているかを判定します。
	 *
	 * @return true:Macintoshの機種、false:それ以外
	 */
	public boolean isMacintosh() {
		if (StringUtils.isBlank(getUserAgent())) {
			return false;
		}

		return getUserAgent().indexOf(Constants.USER_AGENT_KEY_MACINTOSH) != -1;
	}
}
