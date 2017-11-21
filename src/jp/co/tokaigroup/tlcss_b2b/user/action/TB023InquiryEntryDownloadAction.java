package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB023InquiryEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB023InquiryEntryService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ���d�񍐏��_�E�����[�h�A�N�V�����N���X�B
 *
 * @author ����
 * @version 1.0 2015/08/20
 * @version 1.1 2015/12/11 S.Nakano �_�E�����[�h�����ꂼ��̃t�@�C���g���q�ōs����悤�ɏC��
 */
@Controller
@Scope("prototype")
public class TB023InquiryEntryDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB023InquiryEntryModel> {

	/** ���d�񍐏��A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_HOKOKUSHO_KOKAI = ResourceFactory.getResource().getString("UPLOAD_PATH_HOKOKUSHO_KOKAI");

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB023InquiryEntryModel model = new TB023InquiryEntryModel();

	/** �T�[�r�X */
	@Autowired
	private TB023InquiryEntryService service;

	/**
	 * ���d�񍐏��_�E�����[�h�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("inquiryEntryDownload")
	public String download() throws Exception {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		try {
			// ���[�_�E�����[�h���擾
			model = service.getPrintDownloadInfo(model);
			
			if (!model.isDownloadable()) {
				// �_�E�����[�h�G���[�̏ꍇ
				if (userContext.isIPad()) {
					// 403�G���[����ʂɕ\��
					return FORBIDDEN_ERROR;
				} else {
					// �A���[�g�ɃG���[���b�Z�[�W��\��
					addActionError("MSG0022");
					return MESSAGE_AND_CLOSE;
				}
			}
			
			// �t�@�C����
			String uploadFileName = "";
			
			if (TB023InquiryEntryModel.SHORI_KBN_INCOMING_CALL_REPORT.equals(model.getShoriKbn())) {
				// ���d�񍐏��_�E�����[�h�̏ꍇ
				if (StringUtils.isBlank(model.getToiawaseInfo().getExtNyudenHokokushoFileNm())) {
					// �₢���킹���̊O���A�g�Q���d�񍐏��t�@�C�������ݒ肳��Ă��Ȃ��ꍇ
					uploadFileName = UPLOAD_PATH_HOKOKUSHO_KOKAI +
						System.getProperty("file.separator") + model.getToiawaseNo() + ".pdf";
				} else {
					// �₢���킹���̊O���A�g�Q���d�񍐏��t�@�C�������ݒ肳��Ă���ꍇ
					String extension = FilenameUtils.getExtension(model.getToiawaseInfo().getExtNyudenHokokushoFileNm());
					if (StringUtils.isNotBlank(extension)) {
						// �g���q�Ȃ��łȂ��ꍇ�́A�u.�v��t������
						extension = "." + extension;
					}
					uploadFileName = UPLOAD_PATH_HOKOKUSHO_KOKAI +
						System.getProperty("file.separator") + model.getToiawaseNo() + extension;
				}
			} else if (TB023InquiryEntryModel.SHORI_KBN_WORK_REPORT.equals(model.getShoriKbn())) {
				// ��ƕ񍐏��_�E�����[�h�̏ꍇ
				if (StringUtils.isBlank(model.getToiawaseInfo().getExtHokokushoFileNm())) {
					// �₢���킹���̊O���A�g�Q��ƕ񍐏��t�@�C�������ݒ肳��Ă��Ȃ��ꍇ
					uploadFileName = UPLOAD_PATH_HOKOKUSHO_KOKAI +
						System.getProperty("file.separator") + model.getToiawaseNo() + "-" +
						model.getToiawaseRirekiNo().toPlainString() + ".pdf";
				} else {
					// �₢���킹���̊O���A�g�Q��ƕ񍐏��t�@�C�������ݒ肳��Ă���ꍇ
					String extension = FilenameUtils.getExtension(model.getToiawaseInfo().getExtHokokushoFileNm());
					if (StringUtils.isNotBlank(extension)) {
						// �g���q�Ȃ��łȂ��ꍇ�́A�u.�v��t������
						extension = "." + extension;
					}
					uploadFileName = UPLOAD_PATH_HOKOKUSHO_KOKAI +
						System.getProperty("file.separator") + model.getToiawaseNo() + "-" +
						model.getToiawaseRirekiNo().toPlainString() + extension;
				}
			} else {
				// ����ȊO�̏ꍇ�́A�G���[
				if (userContext.isIPad()) {
					// �ʃE�B���h�E�ɕ\������̂ł��̂܂�
					return ERROR;
				} else {
					// �A���[�g�ɃG���[���b�Z�[�W��\��
					addActionError("MSG9001");
					return MESSAGE_AND_CLOSE;
				}
			}
			
			// �`�F�b�NOK�̏ꍇ�́A�t�@�C���̃_�E�����[�h
			File uploadFile = new File(uploadFileName);

			if (!uploadFile.exists()) {
				// �t�@�C�������݂��Ȃ��ꍇ
				if (userContext.isIPad()) {
					// 404�G���[����ʂɕ\��
					return PAGE_NOTFOUND_ERROR;
				} else {
					// �A���[�g�ɃG���[���b�Z�[�W��\��
					addActionError("MSG0024");
					return MESSAGE_AND_CLOSE;
				}
			}

			// �_�E�����[�h�����擾����
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(uploadFile)));
			setContentLength(uploadFile.length());
			setFileName(FilenameUtils.getName(uploadFileName));

			return DOWNLOAD;
		} catch (Exception e) {
			log.error(getText("MSG9001"), e);

			if (userContext.isIPad()) {
				// �ʃE�B���h�E�ɕ\������̂ł��̂܂�
				return ERROR;
			} else {
				// �A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG9001");
				return MESSAGE_AND_CLOSE;
			}
		}
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return �₢���킹�o�^��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB023InquiryEntryModel getModel() {
		return model;
	}
}
