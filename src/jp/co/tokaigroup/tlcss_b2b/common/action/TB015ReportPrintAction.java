package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB015ReportPrintService;
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
 * �񍐏�����A�N�V�����N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/05
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb015_report_print.jsp")
})
public class TB015ReportPrintAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB015ReportPrintModel>{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB015ReportPrintModel model = new TB015ReportPrintModel();
	
	/** �T�[�r�X */
	@Autowired
	private TB015ReportPrintService service;
	
	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("reportPrintInit")
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
			
			// �J�ڌ���ʋ敪���u�˗��o�^��ʁv�̏ꍇ
			if (model.isFromRequestEntry()) {
				// �p�����[�^�̖₢���킹����NO�ɒl�����݂��Ȃ��ꍇ�A�����𒆒f
				if (StringUtils.isBlank(model.getToiawaseRirekiNo())) {
					throw new ApplicationException("�₢���킹����NO�s���F�p�����[�^�̖₢���킹����NO" );
				}
				
				// �p�����[�^�̍�Ə󋵍X�V���ɒl�����݂��Ȃ��ꍇ�A�����𒆒f
				if (model.getSagyoJokyoUpdDt() == null) {
					throw new ApplicationException("��Ə󋵍X�V���s���F�p�����[�^�̍�Ə󋵍X�V��" );
				}
			}
			
			// �J�ڌ���ʋ敪���u�₢���킹�o�^��ʁv�u�˗��o�^��ʁv�ȊO�̏ꍇ�A�����𒆒f
			if (!model.isFromInquiryEntry() && !model.isFromRequestEntry()) {
				throw new ApplicationException("�J�ڌ���ʋ敪�s���F�p�����[�^�̑J�ڌ���ʋ敪" );
			}
			
			// �����\���擾���������{
			model = service.getInitInfo(model);
			// ���������łȂ��ꍇ
			if (!model.getCondition().isCompleted()) {
				// �G���[���b�Z�[�W�̐ݒ�
				addActionError("MSG0034");
			}
			// ���t��(TOKAI���Z�b�g)
			model.setRdoSender(TB015ReportPrintModel.SENDER_CD_TOKAI);
			// ����I��
			return SUCCESS;
		} catch (ForbiddenException e) {
			// 403�G���[
			return FORBIDDEN_ERROR;
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
