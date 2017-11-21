package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB014ReportDisclosureModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB014ReportDisclosureService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �񍐏����J�ݒ�A�N�V�����N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/11
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb014_report_disclosure.jsp"),
	@Result(name=INPUT, location="tb014_report_disclosure.jsp")
})
public class TB014ReportDisclosureAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB014ReportDisclosureModel>, ServiceValidatable{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB014ReportDisclosureModel model = new TB014ReportDisclosureModel();
	
	/** �T�[�r�X */
	@Autowired
	private TB014ReportDisclosureService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("reportDisclosureInit")
	public String init() throws Exception {
		
		try{
			// �p�����[�^�̖₢���킹NO�ɒl�����݂��Ȃ��ꍇ�A�����𒆒f
			if (StringUtils.isBlank(model.getToiawaseNo())) {
				throw new ApplicationException("�₢���킹NO�s���F�p�����[�^�̖₢���킹NO" );
			}
			
			// �p�����[�^�̖₢���킹�X�V���ɒl�����݂��Ȃ��ꍇ�A�����𒆒f
			if (model.getToiawaseUpdDt() == null) {
				throw new ApplicationException("�₢���킹�X�V���s���F�p�����[�^�̖₢���킹�X�V��" );
			}
			
			// ���[�敪���u��ƕ񍐏��v�̏ꍇ
			if (model.isPrintableWorkReport()) {
				// �p�����[�^�̖₢���킹����NO�ɒl�����݂��Ȃ��ꍇ�A�����𒆒f
				if (StringUtils.isBlank(model.getToiawaseRirekiNo())) {
					throw new ApplicationException("�₢���킹����NO�s���F�p�����[�^�̖₢���킹����NO" );
				}
				
				// �p�����[�^�̍�Ə󋵍X�V���ɒl�����݂��Ȃ��ꍇ�A�����𒆒f
				if (model.getSagyoJokyoUpdDt() == null) {
					throw new ApplicationException("��Ə󋵍X�V���s���F�p�����[�^�̍�Ə󋵍X�V��" );
				}
			}
			
			// �p�����[�^�̌ڋqID�ɒl�����݂��Ȃ��ꍇ�A�����𒆒f
			if (StringUtils.isBlank(model.getKokyakuId())) {
				throw new ApplicationException("�ڋq�h�c�s���F�p�����[�^�̌ڋq�h�c" );
			}
			
			// ���[�敪���u���d�񍐏��v�u��ƕ񍐏��v�ȊO�̏ꍇ�A�����𒆒f
			if (!model.isPrintableIncomingCallReport() && !model.isPrintableWorkReport()) {
				throw new ApplicationException("���[�敪�s���F�p�����[�^�̒��[�敪" );
			}
			
			// �������擾�̎��{
			model = service.getInitInfo(model);
			// ����I��
			return SUCCESS;
		} catch (ForbiddenException e) {
			// 403�G���[
			return FORBIDDEN_ERROR;
		}
	}
	
	/**
	 * �X�V�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("reportDisclosureUpdate")
	public String update() throws Exception {
		
		// ���J�������s���܂��B
		model = service.discloseReport(model);
		// �������b�Z�[�W
		addActionMessage("MSG0001", model.getGamenNm() + "�̌��J");
		// ����I��
		return SUCCESS;
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

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
	}
}
