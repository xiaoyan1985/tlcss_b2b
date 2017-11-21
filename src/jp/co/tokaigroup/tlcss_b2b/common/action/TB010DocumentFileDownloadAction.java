package jp.co.tokaigroup.tlcss_b2b.common.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010DocumentFileDownloadModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB010MenuService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �������C�u�����_�E�����[�h�A�N�V�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/08/29
 * @version 1.1 2015/10/28 C.Kobayashi �Z�L�����e�B�`�F�b�N�̒ǉ�
 */
@Controller
@Scope("prototype")

public class TB010DocumentFileDownloadAction extends DownloadActionSupport
	implements ModelDriven<TB010DocumentFileDownloadModel> {
	/** PDF���J�t�@�C���p�X */
	private final static  String UPLOAD_PATH_KOKYAKU_DOCUMENT = ResourceFactory.getResource().getString("UPLOAD_PATH_KOKYAKU_DOCUMENT");

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB010DocumentFileDownloadModel model = new TB010DocumentFileDownloadModel();
	
	/** �T�[�r�X */
	@Autowired
	private TB010MenuService service;
	

	/**
	 * �_�E���[�h�������s���܂��B
	 *
	 * @return
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="documentFileDownload",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			}
	)
	public String download() throws Exception {

		try {

			// ���[�U�[�G�[�W�F���g�ݒ�
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));
			// ���[�U�[�R���e�L�X�g�̐ݒ�
			model.setUserContext((TLCSSB2BUserContext) getUserContext());

			//�Z�L�����e�B�`�F�b�N
			if (model.getUserContext().isOutsourcerSv() || model.getUserContext().isOutsourcerOp()) {
				// �Z�b�V�����̌������u40:�ϑ����SV�v�܂��́u41:�ϑ����OP�v�̏ꍇ
				// �ϑ���Њ֘A�`�F�b�N���s��
				if (!service.isValidDocumentDownload(model)) {
					// �ϑ���Њ֘A�`�F�b�N��NG�̏ꍇ�́A�A�N�Z�X�G���[
					if (model.isIPad()) {
						// iPad�̏ꍇ�́A403�G���[����ʂɕ\��
						return FORBIDDEN_ERROR;
					}
					// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
					addActionError("MSG0022");
					return MESSAGE_AND_CLOSE;
				}
			} else if (model.getUserContext().isRealEstate()) {
				// �Z�b�V�����̌������u20:�Ǘ���Ёv�̏ꍇ
				if (!model.getRealFileNm().substring(0,10).equals(model.getUserContext().getKokyakuId())) {
					if (model.isIPad()) {
						// iPad�̏ꍇ�́A403�G���[����ʂɕ\��
						return FORBIDDEN_ERROR;
					}
					// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
					addActionError("MSG0022");
					return MESSAGE_AND_CLOSE;
				}
			}

			//�t�@�C���p�X��ݒ肷��
			String downloadFilePath = UPLOAD_PATH_KOKYAKU_DOCUMENT + "/" + model.getRealFileNm();
			// �t�@�C���I�u�W�F�N�g���擾����
			File downloadFile = new File(downloadFilePath);

			if (!downloadFile.exists()) {
				if (model.isIPad()) {
					// iPad�̏ꍇ�́A404�G���[����ʂɕ\��
					return PAGE_NOTFOUND_ERROR;
				}

				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0024");
				return MESSAGE_AND_CLOSE;
			}

			//�_�E�����[�h�����擾����
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(downloadFile)));
			setContentLength(downloadFile.length());
			setFileName(model.getRealFileNm());

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
	public TB010DocumentFileDownloadModel getModel() {
		return model;
	}
}


