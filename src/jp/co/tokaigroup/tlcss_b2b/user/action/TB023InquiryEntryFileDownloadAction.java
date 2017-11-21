package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB023InquiryEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB023InquiryEntryService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �₢���킹�t�@�C���_�E�����[�h�A�N�V�����N���X�B
 *
 * @author k002849
 * @version 1.0 2015/09/10
 */
@Controller
@Scope("prototype")
public class TB023InquiryEntryFileDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB023InquiryEntryModel> {

	/** �₢���킹�t�@�C���A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_INQUIRY_FILE = ResourceFactory.getResource().getString("UPLOAD_PATH_INQUIRY_FILE");

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB023InquiryEntryModel model = new TB023InquiryEntryModel();

	/** �T�[�r�X */
	@Autowired
	private TB023InquiryEntryService service;

	/**
	 * �₢���킹�t�@�C���_�E�����[�h�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("inquiryEntryFileDownload")
	public String download() throws Exception {
		// ���O�C�����[�U�[���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		try {
			// �₢���킹�t�@�C���_�E�����[�h�`�F�b�N
			if (!service.isDownloableToiawaseFile(model)) {
				// �˗��`�F�b�NNG�̏ꍇ
				if (userContext.isIPad()) {
					// iPad�̏ꍇ�́A403�G���[����ʂɕ\��
					return FORBIDDEN_ERROR;
				}

				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}
			
			// �t�@�C����
			String uploadFileName = 
				UPLOAD_PATH_INQUIRY_FILE + System.getProperty("file.separator") + model.getUploadFileNm();
			
			// �`�F�b�NOK�̏ꍇ�́A�t�@�C���̃_�E�����[�h
			File uploadFile = new File(uploadFileName);

			if (!uploadFile.exists()) {
				// �t�@�C�������݂��Ȃ��ꍇ
				if (userContext.isIPad()) {
					// iPad�̏ꍇ�́A404�G���[����ʂɕ\��
					return PAGE_NOTFOUND_ERROR;
				}

				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0024");
				return MESSAGE_AND_CLOSE;
			}

			//�_�E�����[�h�����擾����
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(uploadFile)));
			setContentLength(uploadFile.length());
			setFileName(model.getUploadFileNm());
			
		} catch (Exception e) {
			log.error(getText("MSG9001"), e);

			if (userContext.isIPad()) {
				// iPad�̏ꍇ�́A�ʃE�B���h�E�ɕ\������̂ł��̂܂�
				return ERROR;
			}

			// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
			addActionError("MSG9001");
			return MESSAGE_AND_CLOSE;
		}
		
		return DOWNLOAD;
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
