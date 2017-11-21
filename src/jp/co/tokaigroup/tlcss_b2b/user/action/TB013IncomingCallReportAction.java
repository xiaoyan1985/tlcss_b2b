package jp.co.tokaigroup.tlcss_b2b.user.action;


import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB013IncomingCallReportModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB013IncomingCallReportService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ���d�񍐏�����A�N�V�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/05/27
 * @version 1.1 2015/12/11 S.Nakano �_�E�����[�h�����ꂼ��̃t�@�C���g���q�ōs����悤�ɏC��
 */
@Controller
@Scope("prototype")

public class TB013IncomingCallReportAction extends DownloadActionSupport
	implements ModelDriven<TB013IncomingCallReportModel> {
	/** PDF���J�t�@�C���p�X */
	private final static  String UPLOAD_PATH_HOKOKUSHO_KOKAI = ResourceFactory.getResource().getString("UPLOAD_PATH_HOKOKUSHO_KOKAI");

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB013IncomingCallReportModel model = new TB013IncomingCallReportModel();

	/** �T�[�r�X */
	@Autowired
	private TB013IncomingCallReportService service;

	/**
	 * �_�E���[�h�������s���܂��B
	 *
	 * @return �ڋq���CSV�ꊇ�捞��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="incomingCallReportDownload",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			}
	)
	public String download() throws Exception {

		try {
			// ���[�U�[�G�[�W�F���g�ݒ�
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			// �_�E�����[�h���擾
			model = service.getDowloadInfo(model);
			
			if (!model.isDownloadable()) {
				// �_�E�����[�h�G���[�̏ꍇ��
				if (model.isIPad()) {
					// iPad�̏ꍇ�́A403�G���[����ʂɕ\��
					return FORBIDDEN_ERROR;
				}

				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}
			
			String nyudenFilePath = "";
			String extension = "";
			if (StringUtils.isBlank(model.getToiawase().getExtNyudenHokokushoFileNm())) {
				// �₢���킹���̊O���A�g�Q���d�񍐏��t�@�C�������ݒ肳��Ă��Ȃ��ꍇ
				nyudenFilePath = UPLOAD_PATH_HOKOKUSHO_KOKAI + "/" + model.getToiawaseNo() + ".pdf";
				extension = ".pdf";
			} else {
				// �₢���킹���̊O���A�g�Q���d�񍐏��t�@�C�������ݒ肳��Ă���ꍇ
				extension = FilenameUtils.getExtension(model.getToiawase().getExtNyudenHokokushoFileNm());
				if (StringUtils.isNotBlank(extension)) {
					// �g���q�Ȃ��łȂ��ꍇ�́A�u.�v��t������
					extension = "." + extension;
				}
				
				nyudenFilePath = UPLOAD_PATH_HOKOKUSHO_KOKAI + "/" + model.getToiawaseNo() + extension;
			}

			// �t�@�C���I�u�W�F�N�g���擾����
			File nyudenFile = new File(nyudenFilePath);

			if (!nyudenFile.exists()) {
				if (model.isIPad()) {
					// iPad�̏ꍇ�́A404�G���[����ʂɕ\��
					return PAGE_NOTFOUND_ERROR;
				}

				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0024");
				return MESSAGE_AND_CLOSE;
			}

			//�_�E�����[�h�����擾����
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(nyudenFile)));
			setContentLength(nyudenFile.length());
			setFileName(model.getToiawaseNo() + extension);

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
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB013IncomingCallReportModel getModel() {
		return model;
	}
}


