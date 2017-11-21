package jp.co.tokaigroup.tlcss_b2b.dto;


import jp.co.tokaigroup.reception.dto.RC000CommonDto;
import jp.co.tokaigroup.si.fw.message.ValidationPack;

/**
 * 顧客情報CSV一括取込結果DTO。
 *
 * @author k002849
 * @version 1.0 2014/02/27
 */
public class TB043CSVUploadResultDto extends RC000CommonDto {
	/** 処理成功フラグ */
	private boolean isSuccess;
	/** エラーファイルパス */
	private String errorFile;
	/** エラーファイル名 */
	private String errorFileNm;
	/** エラー情報 */
	private ValidationPack errorInfo;

	/**
	 * 処理成功フラグを取得します。
	 *
	 * @return 処理成功フラグ
	 */
	public boolean isSuccess() {
		return isSuccess;
	}
	/**
	 * 処理成功フラグを設定します。
	 *
	 * @param isSuccess 処理成功フラグ
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * エラーファイルパスを取得します。
	 *
	 * @return エラーファイルパス
	 */
	public String getErrorFile() {
		return errorFile;
	}
	/**
	 * エラーファイルパスを設定します。
	 *
	 * @param errorFile エラーファイルパス
	 */
	public void setErrorFile(String errorFile) {
		this.errorFile = errorFile;
	}

	/**
	 * エラー情報を取得します。
	 *
	 * @return エラー情報
	 */
	public ValidationPack getErrorInfo() {
		return errorInfo;
	}
	/**
	 * エラー情報を設定します。
	 *
	 * @param errorInfo エラー情報
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
	 * @param errorFileNm セットする errorFileNm
	 */
	public void setErrorFileNm(String errorFileNm) {
		this.errorFileNm = errorFileNm;
	}
}
