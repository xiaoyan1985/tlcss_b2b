package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB049InquiryHistoryInfoModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB049InquiryHistoryInfoService;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �ڋq�ڍז₢���킹�����A�N�V�����N���X�B
 *
 * @author v145527
 * @version 1.0 2015/07/24
 */

@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb049_inquiry_history_info.jsp"),
	@Result(name=INPUT, location="tb049_inquiry_history_info.jsp")
})

public class TB049InquiryHistoryInfoAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB049InquiryHistoryInfoModel>, ServiceValidatable {
	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB049InquiryHistoryInfoModel model = new TB049InquiryHistoryInfoModel();

	/** �T�[�r�X */
	@Autowired
	private TB049InquiryHistoryInfoService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("inquiryHistoryInfoInit")
	public String init() throws Exception {

		// �����\������
		try {
			// �����\���p�����[�^�`�F�b�N
			if (StringUtils.isBlank(model.getKokyakuId())) {
				// �p�����[�^�̌ڋq�h�c���擾�ł��Ȃ��ꍇ�G���[
				throw new ApplicationException("�ڋqID�s���F�p�����[�^�̌ڋqID" );
			}
			if (! Constants.GAMEN_KBN_CUSTOMER_DETAIL_INQUIRY_HISTORY.equals(model.getGamenKbn())) {
				// �p�����[�^�̉�ʋ敪��TB049�F�ڋq�ڍז₢���킹�����Q�Ɖ�ʂłȂ��ꍇ�G���[
				throw new ApplicationException("��ʋ敪�s���F�p�����[�^�̉�ʋ敪" );
			}
			
			RC031ToiawaseSearchCondition condition = model.getCondition();
			if (condition == null) {
				condition = new RC031ToiawaseSearchCondition();
			}
			condition.setOffset(1);
			
			condition.setDisplayToMax(true);
			
			if (StringUtils.isNotBlank(model.getKokyakuId()) &&
				model.getKokyakuId().length() < RcpMKokyaku.KOKYAKU_ID_COLUMN_LENGTH) {
				// �ڋq�h�c��MAX�l�ɖ����Ȃ��ꍇ�ɂ́AC+�O0�l�߂��ĕ\��
				model.setKokyakuId("C" + StringUtils.leftPad(model.getKokyakuId(), RcpMKokyaku.KOKYAKU_ID_COLUMN_LENGTH - 1, '0'));
			}
			
			model = service.getInitInfo(model);
		
		} catch (ForbiddenException e) {
			// �Z�L�����e�B�G���[�̏ꍇ�́A403�G���[����ʂɕ\��
			return FORBIDDEN_ERROR;
		}

		model.setGamenKbn(Constants.GAMEN_KBN_CUSTOMER_DETAIL_INQUIRY_HISTORY);

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB049InquiryHistoryInfoModel getModel() {
		return model;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	@Override
	public void prepareModel() {
		
	}
}