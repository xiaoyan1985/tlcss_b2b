package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.io.File;
import java.util.List;

public class TB999FileListTestModel {
	public TB999FileListTestModel() {
		super();

		this.deployFileList = new File[3];
	}

	/** アップロードファイルリスト */
	private List<File> uploadFileList;

	/** 配備ファイルリスト */
	private File[] deployFileList;
	/** 配備ファイル名リスト */
	private String[] deployFileListFileName = new String[3];

	/** 配備ファイル名 */
	private String deployFileNames;

	/**
	 * アップロードファイルリストを取得します。
	 *
	 * @return アップロードファイルリスト
	 */
	public List<File> getUploadFileList() {
		return uploadFileList;
	}
	/**
	 * アップロードファイルリストを設定します。
	 *
	 * @param uploadFileList アップロードファイルリスト
	 */
	public void setUploadFileList(List<File> uploadFileList) {
		this.uploadFileList = uploadFileList;
	}

	/**
	 * 配備ファイルリストを取得します。
	 *
	 * @return 配備ファイルリスト
	 */
	public File[] getDeployFileList() {
		return deployFileList;
	}
	/**
	 * 配備ファイルリストを設定します。
	 *
	 * @param deployFileList 配備ファイルリスト
	 */
	public void setDeployFileList(File[] deployFileList) {
		this.deployFileList = deployFileList;
	}

	/**
	 * 配備ファイル名リストを取得します。
	 *
	 * @return 配備ファイル名リスト
	 */
	public String[] getDeployFileListFileName() {
		return deployFileListFileName;
	}
	/**
	 * 配備ファイル名リストを設定します。
	 *
	 * @param deployFileListFileName 配備ファイル名リスト
	 */
	public void setDeployFileListFileName(String[] deployFileListFileName) {
		this.deployFileListFileName = deployFileListFileName;
	}

	/**
	 * 配備ファイル名を取得します。
	 *
	 * @return 配備ファイル名
	 */
	public String getDeployFileNames() {
		return deployFileNames;
	}
	/**
	 * 配備ファイル名を設定します。
	 *
	 * @param deployFileNames 配備ファイル名
	 */
	public void setDeployFileNames(String deployFileNames) {
		this.deployFileNames = deployFileNames;
	}
}
