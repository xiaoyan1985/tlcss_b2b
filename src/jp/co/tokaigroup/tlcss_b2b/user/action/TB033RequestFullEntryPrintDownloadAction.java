package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB033RequestFullEntryModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �˗��o�^���[�_�E�����[�h�A�N�V�����N���X�B
 *
 * @author S.Nakano
 * @version 1.0 2015/10/27
 */
@Controller
@Scope("prototype")
public class TB033RequestFullEntryPrintDownloadAction extends DownloadActionSupport
	implements ModelDriven<TB033RequestFullEntryModel> {

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/** ��ʃ��f�� */
	private TB033RequestFullEntryModel model = new TB033RequestFullEntryModel();
	
	/**
	 * ���[�o�͏������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestFullEntryPrintDownload")
	public String download() throws Exception {
		// �Z�b�V�������̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		try {
			if (StringUtils.isBlank(model.getMakePdfPath())) {
				// PDF�t�@�C�����쐬����Ă��Ȃ��ꍇ�́A
				// �������G���[�Ƃ��āA�A�N�Z�X�����G���[�Ƃ���
				if (userContext.isIPad()) {
					// iPad�̏ꍇ�́A403�G���[����ʂɕ\��
					return FORBIDDEN_ERROR;
				} else {
					// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
					addActionError("MSG0022");
					return MESSAGE_AND_CLOSE;
				}
			}
			// �t�@�C���I�u�W�F�N�g���擾����
			File printFile = new File(model.getMakePdfPath());

			// �_�E�����[�h�����擾����
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(printFile)));
			setContentLength(printFile.length());
			setFileName(model.getPdfNm());

			return DOWNLOAD;
		} catch (Exception e) {
			log.error(e.getMessage());
			if (userContext.isIPad()) {
				// iPad�̏ꍇ�́A500�G���[����ʂɕ\��
				return ERROR;
			} else {
				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG9001");
				return MESSAGE_AND_CLOSE;
			}
		}
	}
	
	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB033RequestFullEntryModel getModel() {
		return model;
	}
}
