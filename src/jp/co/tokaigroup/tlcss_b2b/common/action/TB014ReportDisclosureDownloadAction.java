package jp.co.tokaigroup.tlcss_b2b.common.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB014ReportDisclosureModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB014ReportDisclosureService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �񍐏����J�ݒ�_�E�����[�h�A�N�V�����N���X�B
 *
 * @author k002849
 * @version 1.0 2015/09/24
 */
@Controller
@Scope("prototype")
public class TB014ReportDisclosureDownloadAction extends DownloadActionSupport
implements ModelDriven<TB014ReportDisclosureModel> {

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/** ��ʃ��f�� */
	private TB014ReportDisclosureModel model = new TB014ReportDisclosureModel();
	
	/** �T�[�r�X */
	@Autowired
	private TB014ReportDisclosureService service;

	/**
	 * �񍐏��_�E�����[�h�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="reportDisclosureDownload",
			results = {
					@Result(name=DOWNLOAD, type="stream", 
							params={
							"inputName", "inputStream",
							"contentType", "application/pdf; charset=UTF-8",
							"contentLength", "${ contentLength }",
							"contentDisposition", "inline; filename = ${fileName}"
					})
			}
	)
	public String download() throws Exception {
		// �Z�b�V�������̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		try {
			// �G���R�[�h����Ă��镶������C��
			model.setSenderNm1(model.createEncodeString(model.getSenderNm1()));
			model.setSenderNm2(model.createEncodeString(model.getSenderNm2()));
			model.setSenderAddress(model.createEncodeString(model.getSenderAddress()));
			model.setSenderTelNo(model.createEncodeString(model.getSenderTelNo()));
			model.setSenderFaxNo(model.createEncodeString(model.getSenderFaxNo()));
			
			// ���[���쐬
			model = service.downloadReport(model);

			// �t�@�C���I�u�W�F�N�g���擾����
			File printFile = new File(model.getOutputPdfPath());

			// �_�E�����[�h�����擾����
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(printFile)));
			setContentLength(printFile.length());
			setFileName(FilenameUtils.getName(model.getOutputPdfPath()));

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
	public TB014ReportDisclosureModel getModel() {
		return model;
	}
}
