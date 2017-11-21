package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB026InquiryHistoryMoveModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB026InquiryHistoryMoveService;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �₢���킹�����ړ���ʃA�N�V�����N���X�B
 *
 * @author ���t
 * @version 1.0 2015/08/11
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb026_inquiry_history_move.jsp"),
	@Result(name=INPUT, location="tb026_inquiry_history_move.jsp")
 })
public class TB026InquiryHistoryMoveAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB026InquiryHistoryMoveModel>, ServiceValidatable {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB026InquiryHistoryMoveModel model = new TB026InquiryHistoryMoveModel();

	/** �T�[�r�X */
	@Autowired
	private TB026InquiryHistoryMoveService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("inquiryHistoryMoveInit")
	public String init() throws Exception {
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			// �p�����[�^�̈ړ����₢���킹�m�n���擾�ł��Ȃ��ꍇ�́A�p�����[�^�G���[
			throw new ApplicationException("�ړ����₢���킹�m�n�s���F�p�����[�^�̈ړ����₢���킹�m�n");
		}

		if (StringUtils.isBlank(model.getNewToiawaseNo())) {
			// �p�����[�^�̓��͖₢���킹�m�n���擾�ł��Ȃ��ꍇ�́A�p�����[�^�G���[
			throw new ApplicationException("���͖₢���킹�m�n�s���F�p�����[�^�̓��͖₢���킹�m�n");
		}

		if (model.getToiawaseUpdDt() == null) {
			// �p�����[�^�̖₢���킹�X�V�����擾�ł��Ȃ��ꍇ�́A�p�����[�^�G���[
			throw new ApplicationException("�₢���킹�X�V���s���F�p�����[�^�̖₢���킹�X�V��");
		}

		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * �₢���킹���̍X�V���s���܂��B
	 *
	 * @return �ڋq��{���o�^��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="inquiryHistoryMoveUpdate")
	public String update() throws Exception {
		// �X�V����
		service.updateInquiryHistoryMoveInfo(model);

		addActionMessage("MSG0001", "�₢���킹�����̈ړ�");

		// ����I�����b�Z�[�WID�̐ݒ�
		model.setCompleteMessageId("MSG0001");

		// ����I�����b�Z�[�W�����̐ݒ�
		model.setCompleteMessageStr("�₢���킹�����̈ړ�");

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB026InquiryHistoryMoveModel getModel() {
		return model;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		if (!model.isInitError()) {
			model = service.getInitInfo(model);
		}
	}
}