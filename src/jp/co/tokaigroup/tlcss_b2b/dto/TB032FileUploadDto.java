package jp.co.tokaigroup.tlcss_b2b.dto;

import jp.co.tokaigroup.reception.entity.RcpTFileUpload;
import jp.co.tokaigroup.reception.entity.TbTFileUpload;
import jp.co.tokaigroup.si.fw.util.CommonUtil;

/**
 * �t�@�C���A�b�v���[�hDTO�B
 *
 * @author k002849
 * @version 4.0 2014/07/15
 */
public class TB032FileUploadDto extends TbTFileUpload {
	/**
	 * �f�t�H���g�R���X�g���N�^�B
	 */
	public TB032FileUploadDto() {
		super();
	}

	/**
	 * �R���X�g���N�^�B
	 * �˗��t�@�C���A�b�v���[�h�e�[�u���̏�����ɐݒ肵�܂��B
	 *
	 * @param fileUpload �˗��t�@�C���A�b�v���[�h�e�[�u��Entity
	 */
	public TB032FileUploadDto(RcpTFileUpload fileUpload) {
		CommonUtil.copyProperties(fileUpload, this);
	}

	/**
	 * �R���X�g���N�^�B
	 * �Ǝ҉񓚈˗��t�@�C���A�b�v���[�h�e�[�u���̏�����ɐݒ肵�܂��B
	 *
	 * @param fileUpload �Ǝ҉񓚈˗��t�@�C���A�b�v���[�h�e�[�u��Entity
	 */
	public TB032FileUploadDto(TbTFileUpload fileUpload) {
		CommonUtil.copyProperties(fileUpload, this);
	}

	/**
	 * �Ǝ҉񓚈˗��t�@�C���A�b�v���[�h�e�[�u���̏��ɕϊ��������ʂ��擾���܂��B
	 *
	 * @return �Ǝ҉񓚈˗��t�@�C���A�b�v���[�h�e�[�u��Entity
	 */
	public TbTFileUpload toTbTFileUpload() {
		TbTFileUpload fileUpload = new TbTFileUpload();

		CommonUtil.copyProperties(this, fileUpload);

		return fileUpload;
	}
}
