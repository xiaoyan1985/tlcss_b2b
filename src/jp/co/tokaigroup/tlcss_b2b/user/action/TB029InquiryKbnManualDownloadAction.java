package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB029InquiryKbnManualModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB029InquiryKbnManualService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �₢���킹�敪�}�j���A���ꗗ�_�E�����[�h�A�N�V�����N���X�B
 *
 * @author H.Yamamura
 * @version 1.0 2015/11/20
 */
@Controller
@Scope("prototype")
public class TB029InquiryKbnManualDownloadAction extends DownloadActionSupport
	implements ModelDriven<TB029InquiryKbnManualModel>{

	/** �A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_INQUIRY_KBN_MANUAL = ResourceFactory.getResource().getString("UPLOAD_PATH_INQUIRY_KBN_MANUAL");

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB029InquiryKbnManualModel model = new TB029InquiryKbnManualModel();

	/** �T�[�r�X */
	@Autowired
	private TB029InquiryKbnManualService service;

	/**
	 * �}�j���A���t�@�C���_�E�����[�h�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("inquiryKbnManualDownload")
	public String download() throws Exception {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �Z�b�V�����̌������uTOKAI�Ǘ��ҁv�܂��́uTOKAI��ʎҁv�܂��́u�ϑ����SV�v�܂��́u�ϑ����OP�v�ȊO�̏ꍇ403�G���[
		if (!(userContext.isAdministrativeInhouse()
			|| userContext.isGeneralInhouse() 
			|| userContext.isOutsourcerSv() 
			|| userContext.isOutsourcerOp())) {
			throw new ForbiddenException("�A�N�Z�X���������݂��܂���B");
		}
		try {
			// �Ώۂ̃A�b�v���[�h�t�@�C�����擾
			String uploadFileNm = service.getFileNm(model);
			// �t�@�C���������݂��Ȃ��ꍇ
			if (StringUtils.isBlank(uploadFileNm)) {
				if (userContext.isIPad()) {
					// 404�G���[����ʂɕ\��
					return PAGE_NOTFOUND_ERROR;
				} else {
					// �A���[�g�ɃG���[���b�Z�[�W��\��
					addActionError("MSG0024");
					return MESSAGE_AND_CLOSE;
				}
			}
			// �`�F�b�NOK�̏ꍇ�́A�t�@�C���̃_�E�����[�h
			File uploadFile = new File(UPLOAD_PATH_INQUIRY_KBN_MANUAL + System.getProperty("file.separator") + uploadFileNm);
			// �t�@�C�������݂��Ȃ��ꍇ
			if (!uploadFile.exists()) {
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
			setFileName(uploadFileNm);

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
	 * @return �₢���킹�敪�}�j���A���ꗗ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB029InquiryKbnManualModel getModel() {
		return model;
	}
}
