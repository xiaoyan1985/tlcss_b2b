package jp.co.tokaigroup.tlcss_b2b.user.action;


import java.io.ByteArrayInputStream;
import java.io.File;


import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;


import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �Ǘ����A�b�v���[�h�_�E�����[�h�A�N�V�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/08/05
 */
@Controller
@Scope("prototype")

public class TB043CustomerFileDownloadAction  extends DownloadActionSupport
	implements ModelDriven<TB043CustomerUploadModel> {

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB043CustomerUploadModel model = new TB043CustomerUploadModel();

	/** �捞�t�@�C���p�X */
	private static final String UPLOAD_PATH_CUSTOMER = ResourceFactory.getResource().getString("UPLOAD_PATH_CUSTOMER");

	/** �捞�G���[�t�@�C���p�X */
	private static final String UPLOAD_PATH_CUSTOMER_ERROR = ResourceFactory.getResource().getString("UPLOAD_PATH_CUSTOMER_ERROR");

	/**
	 * �I�������t�@�C�����J���܂��B
	 *
	 * @return �Ǘ����A�b�v���[�h��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="selectFileDownload",
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


			//�t�@�C���p�X��ݒ肷��
			String selectFileNm = model.getSelectFileNm();
			String filyType = model.getFileDownloadType();
			String selectFilePath = null;

			//�Z�L�����e�B�`�F�b�N
			if (!selectFileNm.substring(0,10).equals(model.getUserContext().getKokyakuId())) {
				if (model.isIPad()) {
					// iPad�̏ꍇ�́A403�G���[����ʂɕ\��
					return FORBIDDEN_ERROR;
				}
				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}

			//�_�E�����[�h���̐ݒ�
			if (TB043CustomerUploadModel.FILE_DOWNLOAD_TYPE_NOEMAL.equals(filyType)) {
				selectFilePath = UPLOAD_PATH_CUSTOMER + "/" + selectFileNm;
			} else if (TB043CustomerUploadModel.FILE_DOWNLOAD_TYPE_ERROR.equals(filyType)) {
				selectFilePath = UPLOAD_PATH_CUSTOMER_ERROR + "/" + selectFileNm;
			} else {
				if (model.isIPad()) {
					// iPad�̏ꍇ�́A403�G���[����ʂɕ\��
					return FORBIDDEN_ERROR;
				}
				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}

			// �t�@�C���I�u�W�F�N�g���擾����
			File selectFile = new File(selectFilePath);

			if(!selectFile.exists()) {
				if (model.isIPad()) {
					// iPad�̏ꍇ�́A404�G���[����ʂɕ\��
					return PAGE_NOTFOUND_ERROR;
				}
				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0024");
				return MESSAGE_AND_CLOSE;
			}

			//�_�E�����[�h�����擾����
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(selectFile)));
			setContentLength(selectFile.length());
			setFileName(selectFileNm);

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
	public TB043CustomerUploadModel getModel() {
		return model;
	}
}


