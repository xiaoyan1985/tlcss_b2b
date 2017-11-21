package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB032RequestEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB032RequestEntryService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �˗����e�ڍׁE��Ə󋵓o�^���̑��t�@�C���_�E�����[�h�A�N�V�����N���X�B
 *
 * @author k002849
 * @version 4.0 2014/09/04
 */
@Controller
@Scope("prototype")
public class TB032RequestDetailDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB032RequestEntryModel> {
	// �v���p�e�B�t�@�C������擾
	/** �˗����A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_IRAI_FILE = ResourceFactory.getResource().getString("UPLOAD_PATH_IRAI_FILE");

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB032RequestEntryModel model = new TB032RequestEntryModel();

	/** �T�[�r�X */
	@Autowired
	private TB032RequestEntryService service;

	/**
	 * ���̑��t�@�C���_�E�����[�h�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestDetailDownload")
	public String download() throws Exception {
		try {
			// ���O�C�����[�U�[���擾
			model.setUserContext((TLCSSB2BUserContext) getUserContext());
			// ���[�U�[�G�[�W�F���g�ݒ�
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			// �˗����̃`�F�b�N
			if (!service.checkOtherFileDownloadInfo(model)) {
				// �˗��`�F�b�NNG�̏ꍇ
				if (model.isIPad()) {
					// iPad�̏ꍇ�́A403�G���[����ʂɕ\��
					return FORBIDDEN_ERROR;
				}

				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}

			// �`�F�b�NOK�̏ꍇ�́A�t�@�C���̃_�E�����[�h
			File uploadFile = new File(UPLOAD_PATH_IRAI_FILE +
								System.getProperty("file.separator") +
								model.getUploadFileNm());

			if (!(uploadFile.exists())) {
				// �t�@�C�������݂��Ȃ��ꍇ
				if (model.isIPad()) {
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

			return DOWNLOAD;
		} catch (Exception e) {
			log.error(getText("MSG9001"), e);

			if (model.isIPad()) {
				// iPad�̏ꍇ�́A�ʃE�B���h�E�ɕ\������̂ł��̂܂�
				return ERROR;
			}

			// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
			addActionError("MSG9001");
			return MESSAGE_AND_CLOSE;
		}

	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB032RequestEntryModel getModel() {
		return model;
	}
}
