package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.MessageBean;
import jp.co.tokaigroup.tlcss_b2b.dto.TB044ReceptionListPrintDto;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB044ReceptionListPrintModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB044ReceptionListPrintService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ��t�ꗗ����_�E�����[�h�A�N�V�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/07/08
 */
@Controller
@Scope("prototype")

public class TB044ReceptionListPrintDownloadAction extends DownloadActionSupport
	implements ModelDriven<TB044ReceptionListPrintModel> {

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB044ReceptionListPrintModel model = new TB044ReceptionListPrintModel();

	/** �T�[�r�X */
	@Autowired
	private TB044ReceptionListPrintService service;


	/**
	 * ����������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("receptionListPrint")
	public String download() throws Exception {
		try {
			// �������
			model.setPdfUrl(service.createPdf(model));

			// �t�@�C���I�u�W�F�N�g���擾����
			File printFile = new File(model.getPdfUrl());
			//�_�E�����[�h�����擾����
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(printFile)));
			setContentLength(printFile.length());
			setFileName(model.getPdfUrl().substring(model.getPdfUrl().lastIndexOf(TB044ReceptionListPrintDto.REPORT_NM)));

			return DOWNLOAD;

		} catch (ValidationException e) {
			List<MessageBean> beanList =  e.getValidationPack().getActionErrors();
			for (MessageBean mb : beanList) {
				addActionError(mb.getMessageId());
			}
			return (model.isIPad() ? MESSAGE : MESSAGE_AND_CLOSE);

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
	public TB044ReceptionListPrintModel getModel() {
		return model;
	}
}
