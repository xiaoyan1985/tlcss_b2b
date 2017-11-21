package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB025CustomerIdChangeModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB025CustomerIdChangeService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �ڋq�h�c�ύX��ʃA�N�V�����N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/19
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb025_customer_id_change.jsp"),
	@Result(name=INPUT, location="tb025_customer_id_change.jsp")
 })
public class TB025CustomerIdChangeAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB025CustomerIdChangeModel>, ServiceValidatable {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB025CustomerIdChangeModel model = new TB025CustomerIdChangeModel();

	/** �T�[�r�X */
	@Autowired
	private TB025CustomerIdChangeService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("customerIdChangeInit")
	public String init() throws Exception {
	
		// �p�����[�^�̖₢���킹NO�ɒl�����݂��Ȃ��ꍇ�A�����𒆒f
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			throw new ApplicationException("�₢���킹�m�n�s���F�p�����[�^�̖₢���킹�m�n" );
		}
	
		// �p�����[�^�̖₢���킹�X�V���ɒl�����݂��Ȃ��ꍇ�A�����𒆒f
		if (model.getToiawaseUpdDt() == null) {
			throw new ApplicationException("�₢���킹�X�V���s���F�p�����[�^�̖₢���킹�X�V��" );
		}
	
		// �p�����[�^�̓��͌ڋqID�ɒl�����݂��Ȃ��ꍇ�A�����𒆒f
		if (StringUtils.isBlank(model.getNewKokyakuId())) {
			throw new ApplicationException("���͌ڋq�h�c�s���F�p�����[�^�̓��͌ڋq�h�c" );
		}

		// �����\���������{
		model = service.getInitInfo(model);
	
		// ����I��
		return SUCCESS;
	}
	
	/**
	 * �X�V�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="customerIdChangeUpdate")
	public String update() throws Exception {
		// �X�V�����̎��{
		service.updateToiawaseKokyakuInfo(model);

		// ����I�����b�Z�[�W�̐ݒ�
		addActionMessage("MSG0001", "�ڋqID�̕ύX");

		// ����I�����b�Z�[�WID�̐ݒ�
		model.setCompleteMessageId("MSG0001");

		// ����I�����b�Z�[�W�����̐ݒ�
		model.setCompleteMessageStr("�ڋqID�̕ύX");

		// ����I��
		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB025CustomerIdChangeModel getModel() {
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
