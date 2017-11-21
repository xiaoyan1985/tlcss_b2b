package jp.co.tokaigroup.tlcss_b2b.common.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB015ReportPrintService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �񍐏�����_�E�����[�h�A�N�V�����N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/07
 */
@Controller
@Scope("prototype")
public class TB015ReportPrintDownloadAction extends DownloadActionSupport
implements ModelDriven<TB015ReportPrintModel>{

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/** ��ʃ��f�� */
	private TB015ReportPrintModel model = new TB015ReportPrintModel();
	
	/** �T�[�r�X */
	@Autowired
	private TB015ReportPrintService service;

	/**
	 * ���[�o�͏������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("reportPrintDownload")
	public String download() throws Exception {
		// �Z�b�V�������̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		try {
			// ���[���쐬
			model = service.createPdf(model);

			// �t�@�C���I�u�W�F�N�g���擾����
			File printFile = new File(model.getMakePdfPath());

			// �_�E�����[�h�����擾����
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(printFile)));
			setContentLength(printFile.length());
			setFileName(model.getPdfNm());

			return DOWNLOAD;
		} catch (ForbiddenException e) {
			if (userContext.isIPad()) {
				// iPad�̏ꍇ�́A403�G���[����ʂɕ\��
				return FORBIDDEN_ERROR;
			} else {
				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}
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
	public TB015ReportPrintModel getModel() {
		return model;
	}
}
