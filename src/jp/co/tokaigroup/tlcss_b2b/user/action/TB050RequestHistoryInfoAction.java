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
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB050RequestHistoryInfoModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB050RequestHistoryInfoService;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �ڋq�ڍ׈˗������A�N�V�����N���X�B
 *
 * @author v145527
 * @version 1.0 2015/07/30
 */

@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb050_request_history_info.jsp"),
	@Result(name=INPUT, location="tb050_request_history_info.jsp")
})
public class TB050RequestHistoryInfoAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB050RequestHistoryInfoModel>, ServiceValidatable {
	
	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB050RequestHistoryInfoModel model = new TB050RequestHistoryInfoModel();

	/** �T�[�r�X */
	@Autowired
	private TB050RequestHistoryInfoService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestHistoryInfoInit")
	public String init() throws Exception {

		// �����\������
		try {
			// �����\���p�����[�^�`�F�b�N
			if (StringUtils.isBlank(model.getKokyakuId())) {
				// �p�����[�^�̌ڋq�h�c���擾�ł��Ȃ��ꍇ�G���[
				throw new ApplicationException("�ڋqID�s���F�p�����[�^�̌ڋqID" );
			}
			if (! Constants.GAMEN_KBN_CUSTOMER_DETAIL_REQUEST_HISTORY.equals(model.getGamenKbn())) {
				// �p�����[�^�̉�ʋ敪��TB050�F�ڋq�ڍ׈˗������Q�Ɖ�ʂłȂ��ꍇ�G���[
				throw new ApplicationException("��ʋ敪�s���F�p�����[�^�̉�ʋ敪" );
			}
			
			RC041IraiSearchCondition condition = model.getCondition();
			if (condition == null) {
				condition = new RC041IraiSearchCondition();
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

		model.setGamenKbn(Constants.GAMEN_KBN_CUSTOMER_DETAIL_REQUEST_HISTORY );

		return SUCCESS;
	}
	
	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	@Override
	public TB050RequestHistoryInfoModel getModel() {
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
