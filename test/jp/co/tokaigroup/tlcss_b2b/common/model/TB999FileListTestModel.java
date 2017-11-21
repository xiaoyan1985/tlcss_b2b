package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.io.File;
import java.util.List;

public class TB999FileListTestModel {
	public TB999FileListTestModel() {
		super();

		this.deployFileList = new File[3];
	}

	/** �A�b�v���[�h�t�@�C�����X�g */
	private List<File> uploadFileList;

	/** �z���t�@�C�����X�g */
	private File[] deployFileList;
	/** �z���t�@�C�������X�g */
	private String[] deployFileListFileName = new String[3];

	/** �z���t�@�C���� */
	private String deployFileNames;

	/**
	 * �A�b�v���[�h�t�@�C�����X�g���擾���܂��B
	 *
	 * @return �A�b�v���[�h�t�@�C�����X�g
	 */
	public List<File> getUploadFileList() {
		return uploadFileList;
	}
	/**
	 * �A�b�v���[�h�t�@�C�����X�g��ݒ肵�܂��B
	 *
	 * @param uploadFileList �A�b�v���[�h�t�@�C�����X�g
	 */
	public void setUploadFileList(List<File> uploadFileList) {
		this.uploadFileList = uploadFileList;
	}

	/**
	 * �z���t�@�C�����X�g���擾���܂��B
	 *
	 * @return �z���t�@�C�����X�g
	 */
	public File[] getDeployFileList() {
		return deployFileList;
	}
	/**
	 * �z���t�@�C�����X�g��ݒ肵�܂��B
	 *
	 * @param deployFileList �z���t�@�C�����X�g
	 */
	public void setDeployFileList(File[] deployFileList) {
		this.deployFileList = deployFileList;
	}

	/**
	 * �z���t�@�C�������X�g���擾���܂��B
	 *
	 * @return �z���t�@�C�������X�g
	 */
	public String[] getDeployFileListFileName() {
		return deployFileListFileName;
	}
	/**
	 * �z���t�@�C�������X�g��ݒ肵�܂��B
	 *
	 * @param deployFileListFileName �z���t�@�C�������X�g
	 */
	public void setDeployFileListFileName(String[] deployFileListFileName) {
		this.deployFileListFileName = deployFileListFileName;
	}

	/**
	 * �z���t�@�C�������擾���܂��B
	 *
	 * @return �z���t�@�C����
	 */
	public String getDeployFileNames() {
		return deployFileNames;
	}
	/**
	 * �z���t�@�C������ݒ肵�܂��B
	 *
	 * @param deployFileNames �z���t�@�C����
	 */
	public void setDeployFileNames(String deployFileNames) {
		this.deployFileNames = deployFileNames;
	}
}
