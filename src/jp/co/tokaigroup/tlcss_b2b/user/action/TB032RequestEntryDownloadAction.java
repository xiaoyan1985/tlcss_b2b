package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB032RequestEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB032RequestEntryService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �˗����e�ڍׁE��Ə󋵓o�^�摜�_�E�����[�h�A�N�V�����N���X�B
 *
 * @author k002849
 * @version 4.0 2014/07/18
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb032_request_entry.jsp")
})
public class TB032RequestEntryDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB032RequestEntryModel> {
	// �v���p�e�B�t�@�C������擾
	/** ��Ə󋵃t�@�C���A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_SAGYO_JOKYO = ResourceFactory.getResource().getString("UPLOAD_PATH_SAGYO_JOKYO");
	/** ��Ə󋵃t�@�C��(TLCSS)�A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_TLCSS_SAGYO_JOKYO = ResourceFactory.getResource().getString("UPLOAD_PATH_TLCSS_SAGYO_JOKYO");

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB032RequestEntryModel model = new TB032RequestEntryModel();

	/** �T�[�r�X */
	@Autowired
	private TB032RequestEntryService service;

	/**
	 * �摜�_�E�����[�h�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestEntryDownload")
	public String download() throws Exception {
		try {
			// ���O�C�����[�U�[���擾
			model.setUserContext((TLCSSB2BUserContext) getUserContext());

			// ���[�U�[�G�[�W�F���g�ݒ�
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			// �˗����`�F�b�N
			if (!service.checkDownloadInfo(model)) {
				// �˗����`�F�b�NNG�̏ꍇ�A403�G���[�摜����ʂɕ\���iswipebox�g�p�ɂāA��ʃG���[�\�����ł��Ȃ����߁j
				downloadImageFile(getResourceImageFile(Constants.IMAGE_FILENAME_ACCESS_ERROR));

				return DOWNLOAD;
			}

			// �`�F�b�NOK�̏ꍇ�́A�t�@�C���̃_�E�����[�h
			// ��ʂ���Ə󋵓o�^�̏ꍇ�́ATORES���̃p�X���g�p�A��ʂ��˗����e�ڍׂ̏ꍇ�́ATLCSS���̃p�X���g�p
			String uploadPath = model.isContractorAnswerFileDisplay() ?
					UPLOAD_PATH_SAGYO_JOKYO : UPLOAD_PATH_TLCSS_SAGYO_JOKYO;
			File uploadFile = new File(uploadPath + System.getProperty("file.separator") + model.getUploadFileNm());

			if (uploadFile.exists()) {
				// �t�@�C�������݂���ꍇ�A�Ώۂ̉摜��\��
				downloadImageFile(uploadFile);
			} else {
				// �t�@�C�������݂��Ȃ��ꍇ�A404�G���[�摜����ʂɕ\���iswipebox�g�p�ɂāA��ʃG���[�\�����ł��Ȃ����߁j
				downloadImageFile(getResourceImageFile(Constants.IMAGE_FILENAME_FILE_NOTFOUND));
			}
		} catch (Exception e) {
			log.error(e.getMessage());

			// �G���[�������A500�G���[�摜����ʂɕ\���iswipebox�g�p�ɂāA��ʃG���[�\�����ł��Ȃ����߁j
			downloadImageFile(getResourceImageFile(Constants.IMAGE_FILENAME_SERVER_ERROR));
		}

		return DOWNLOAD;
	}

	/**
	 * ���\�[�X����摜�t�@�C�����擾���܂��B
	 *
	 * @param imageFileNm �摜�t�@�C����
	 * @return �摜�t�@�C���I�u�W�F�N�g
	 */
	private File getResourceImageFile(String imageFileNm) {
		URL imageFileUrl = this.getClass().getResource(
				Constants.RESOURCE_IMAGE_PATH + "/" + imageFileNm);

		return new File(imageFileUrl.getFile());
	}

	/**
	 * �C���[�W�摜�t�@�C�����_�E�����[�h���܂��B
	 *
	 * @param imageFile �C���[�W�摜�t�@�C��
	 * @throws IOException ���o�͗�O�����������ꍇ
	 */
	private void downloadImageFile(File imageFile) throws IOException {
		//�_�E�����[�h�����擾����
		setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(imageFile)));
		setContentLength(imageFile.length());
		setFileName(model.getUploadFileNm());
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
