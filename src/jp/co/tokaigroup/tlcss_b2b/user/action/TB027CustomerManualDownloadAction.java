package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB027CustomerManualModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB027CustomerManualService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �ڋq�}�j���A���_�E�����[�h�A�N�V�����N���X�B
 *
 * @author ���t
 * @version 1.0 2015/08/06
 */
@Controller
@Scope("prototype")
public class TB027CustomerManualDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB027CustomerManualModel> {
	// �v���p�e�B�t�@�C������擾
	/** �ڋq�}�j���A���t�@�C��(TLCSS)�A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_TLCSS_KOKYAKU_MANUAL = ResourceFactory.getResource().getString("UPLOAD_PATH_KOKYAKU_MANUAL");

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB027CustomerManualModel model = new TB027CustomerManualModel();

	/** �T�[�r�X */
	@Autowired
	private TB027CustomerManualService service;

	/**
	 * �}�j���A���t�@�C���_�E�����[�h�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("customerManualDownload")
	public String download() throws Exception {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		try {
			// �Z�L�����e�B�`�F�b�N�i�ϑ���Њ֘A�`�F�b�N�j
			if (!service.isValidKokyakuManualInfo(model)) {
				if (userContext.isIPad()) {
					// 403�G���[����ʂɕ\��
					return FORBIDDEN_ERROR;
				} else {
					// �A���[�g�ɃG���[���b�Z�[�W��\��
					addActionError("MSG0022");
					return MESSAGE_AND_CLOSE;
				}
			}

			// �`�F�b�NOK�̏ꍇ�́A�t�@�C���̃_�E�����[�h
			File uploadFile = new File(UPLOAD_PATH_TLCSS_KOKYAKU_MANUAL + System.getProperty("file.separator") + model.getUploadFileNm());

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
			setFileName(model.getUploadFileNm());

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
	 * @return �ڋq�}�j���A���ꗗ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB027CustomerManualModel getModel() {
		return model;
	}
}
