package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB013DisclosureMailSendModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB013DisclosureMailSendService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * TORES���J���[�����M�A�N�V�����N���X�B
 *
 * @author k003856
 * @version 5.0 2015/09/08
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb013_disclosure_mail_send.jsp"),
	@Result(name=INPUT, location="tb013_disclosure_mail_send.jsp")
})
public class TB013DisclosureMailSendAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB013DisclosureMailSendModel>, ServiceValidatable {

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB013DisclosureMailSendModel model = new TB013DisclosureMailSendModel();

	/** �T�[�r�X */
	@Autowired
	private TB013DisclosureMailSendService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return TORES���J���[�����M��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("disclosureMailSendInit")
	public String init() throws Exception {
		// �����\������
		model.setActionType("");
		
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			throw new ApplicationException("�₢���킹NO�s���F�p�����[�^�̖₢���킹NO");
		}

		// �����敪�`�F�b�N
		if (!model.isFromToiawaseEntry() && !model.isFromIraiEntry() 
				&& !model.isFromToiawaseRirekiEntry()) {
			throw new ApplicationException("�����敪�F�p�����[�^�̏����敪");
		}

		// �₢���킹����NO�`�F�b�N
		if ((model.isFromIraiEntry() || model.isFromToiawaseRirekiEntry()) && model.getToiawaseRirekiNo() == null) {
			throw new ApplicationException("�₢���킹����NO�s���F�p�����[�^�̖₢���킹����NO");
		}

		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * ���[�����M�������s���܂��B
	 *
	 * @return TORES���J���[�����M��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("disclosureMailSendSendMail")
	public String sendMail() throws Exception {
		try {
			// ���[�����M����
			service.executeSendMail(model);

			// �������b�Z�[�W
			addActionMessage("MSG0001", "���J���[�����M");
			
			model = service.getInitInfo(model);

			return SUCCESS;
		} catch (ApplicationException e) {
			log.error("���[�����M�Ɏ��s���܂����B", e);
			model = service.getInitInfo(model);
			// �������s���b�Z�[�W
			throw new ValidationException(new ValidationPack().addError(
					"MSG0040", "���J���[�����M"));
		}
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model TORES���J���[�����M��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB013DisclosureMailSendModel getModel() {
		return model;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
	}
}
