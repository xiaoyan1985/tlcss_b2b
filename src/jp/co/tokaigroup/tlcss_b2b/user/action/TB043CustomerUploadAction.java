package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.message.MessageBean;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB043CustomerUploadService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;


/**
 * �Ǘ����A�b�v���[�h�A�N�V�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/08/04
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb043_customer_upload.jsp"),
	@Result(name=INPUT, location="tb043_customer_upload.jsp")
})
public class TB043CustomerUploadAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB043CustomerUploadModel> , ServiceValidatable{

	// �v���p�e�B�t�@�C������ǂݍ���
	/** �b�r�u�e���v���[�g�t�H���_PATH */
	private static final String CSV_TEMPLATE_PATH = ResourceFactory.getResource().getString("CSV_TEMPLATE_PATH");
	/** �e���v���[�g�t�@�C���� �w��Ǝ� */
	private static final String TEMPLATE_FILE_NM_ERROR_FILE_HEADER = "tb043_customer_upload_error_header_template.csv";
	/** �G���[�t�@�C���G���R�[�f�B���O */
	private static final String ERROR_FILE_ENCODING = "Windows-31J";

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB043CustomerUploadModel model = new TB043CustomerUploadModel();

	/** �T�[�r�X */
	@Autowired
	private TB043CustomerUploadService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("customerUploadInit")
	public String init() throws Exception {

		// ���[�U�[�G�[�W�F���g�ݒ�
		model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

		// ���[�U�[�R���e�L�X�g�̐ݒ�
		model.setUserContext((TLCSSB2BUserContext) getUserContext());

		// �����\������
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * CSV�捞�������s���܂��B
	 *
	 * @return �Ǘ����A�b�v���[�h��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("customerUploadUpload")
	public String upload() throws Exception {

		// ���[�U�[�G�[�W�F���g�ݒ�
		model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

		// ���[�U�[�R���e�L�X�g�̐ݒ�
		model.setUserContext((TLCSSB2BUserContext) getUserContext());

		// ���[�U�[��macintosh�̏ꍇ�A���������������
		if (model.isMacintosh()) {
			model.setUploadFileFileName(
					Normalizer.normalize(model.getUploadFileFileName(), Form.NFKC));
		}

		// CSV�捞����
		service.executeCsvUpload(model);

		if (model.isSuccessCsvUpload()) {
			// ���������������ꍇ
			addActionMessage("MSG0001", "�t�@�C���̃A�b�v���[�h");
		} else {
			// ���������s�����ꍇ
			// �G���[���̃\�[�g
			model.setErrorInfo(sortErrorInfo(model.getErrorInfo()));
			// �G���[�t�@�C���ւ̏�������
			writeErrFile(model.getUploadFileFileName(), model.getErrorFilePath(), model.getErrorInfo());

			addActionError("MSG0023");
		}

		// �����\������
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * �폜�������s���܂��B
	 *
	 * @return �Ǘ����A�b�v���[�h��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="customerUploadDelete",
			results = {
					@Result(name=SUCCESS, type="redirectAction",
					location="customerUploadInit")
	})
	public String delete() throws Exception {

		// ���[�U�[�R���e�L�X�g�̐ݒ�
		model.setUserContext((TLCSSB2BUserContext) getUserContext());

		// �폜����
		model = service.deleteUploadRireki(model);

		// ���������������ꍇ
		addActionMessage("MSG0001", "�t�@�C���̍폜");

		return SUCCESS;
	}

	/**
	 * �G���[�t�@�C���ɃG���[���e���������݂܂��B
	 *
	 * @param uploadFileNm �A�b�v���[�h�t�@�C����
	 * @param errFilePath �G���[�t�@�C���p�X�i��΃p�X�j
	 * @param errorInfo �G���[���
	 */
	private void writeErrFile(String uploadFileNm, String errFilePath, ValidationPack errorInfo) {
		// �G���[�t�@�C��
		File errFile = new File(errFilePath);

		PrintWriter writer = null;
		BufferedReader reader = null;
		try {
			// �G���[�t�@�C�������o��
			writer = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(errFile), ERROR_FILE_ENCODING));
			// �w�b�_�t�@�C���ǂݍ���
			reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(CSV_TEMPLATE_PATH + System.getProperty("file.separator") + TEMPLATE_FILE_NM_ERROR_FILE_HEADER), ERROR_FILE_ENCODING));

			// �w�b�_���̓ǂݍ���
			String line = "";
			List<String> headerList = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				headerList.add(line);
			}

			// �w�b�_���̑O�ɎQ�ƃt�@�C��������������
			writer.println(uploadFileNm);

			// �w�b�_���̏�������
			for (String header : headerList) {
				writer.println(header);
			}

			// �G���[���e�̏�������
			for (MessageBean mb : errorInfo.getActionErrors()) {
				String errMsg = null;
				if (mb.isMessageId()) {
					errMsg = getText(mb.getMessageId(), mb.getParams());
				} else {
					errMsg = mb.getMessageId();
				}

				writer.println(errMsg);
			}
		} catch (IOException e) {
			throw new ApplicationException(e);
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					// close�̍ۂ�IOException�͖���
				}
			}
		}
	}

	/**
	 * �G���[�����\�[�g���܂��B
	 *
	 * @param orgVp �G���[���
	 * @return �\�[�g�����G���[���
	 */
	private ValidationPack sortErrorInfo(ValidationPack orgVp) {
		ValidationPack vp = new ValidationPack();
		List<String> errorMessageList = new ArrayList<String>();

		// ���݂̍s�ԍ�
		String nowRowData = "";
		// �s�ԍ����̃G���[���X�g
		List<String> rowErrorMessageList = new ArrayList<String>();

		// �G���[���b�Z�[�W���A���[�v
		for (MessageBean mb : orgVp.getActionErrors()) {
			String errMsg = getText(mb.getMessageId(), mb.getParams());
			String[] errMsgArray = StringUtils.split(errMsg, ",");

			if (StringUtils.isBlank(nowRowData) || nowRowData.equals(errMsgArray[0])) {
				// ����A�܂��́A�s�ԍ��������ꍇ�́A���X�g�ɂ߂�
				rowErrorMessageList.add(errMsg);

				if (StringUtils.isBlank(nowRowData)) {
					// ����̏ꍇ�́A���݂̍s�ԍ��̍X�V
					nowRowData = errMsgArray[0];
				}
			} else {
				// �s�ԍ����Ⴄ�ꍇ�́A���܂ł̃��X�g���\�[�g
				Collections.sort(rowErrorMessageList);

				// �\�[�g���ʂ�S�̂̃G���[���b�Z�[�W���X�g�ɂ߂�
				errorMessageList.addAll(rowErrorMessageList);

				// �s�ԍ��G���[���X�g�̏�����
				rowErrorMessageList.clear();
				rowErrorMessageList.add(errMsg);

				// ���݂̍s�ԍ��̍X�V
				nowRowData = errMsgArray[0];
			}
		}

		// �ŏI����
		if (!(rowErrorMessageList == null || rowErrorMessageList.isEmpty())) {
			// �s�ԍ����Ⴄ�ꍇ�́A���܂ł̃��X�g���\�[�g
			Collections.sort(rowErrorMessageList);

			// �\�[�g���ʂ�S�̂̃G���[���b�Z�[�W���X�g�ɂ߂�
			errorMessageList.addAll(rowErrorMessageList);
		}

		// ValidationPack�ɋl�ߒ���
		for (String errorMessage : errorMessageList) {
			vp.addErrorAsMessage(errorMessage);
		}

		return vp;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB043CustomerUploadModel getModel() {
		return model;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		model = service.getInitInfo(model);
	}
}
