package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import static com.opensymphony.xwork2.Action.INPUT;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB045AccompanyingInfoModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB045AccompanyingInfoService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �ڋq�ڍוt�����A�N�V�����N���X�B
 *
 * @author k003316
 * @version 1.0 2015/08/03
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb045_accompanying_info.jsp"),
	@Result(name=INPUT, location="tb045_accompanying_info.jsp")
})
public class TB045AccompanyingInfoAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB045AccompanyingInfoModel>, ServiceValidatable {
	
	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB045AccompanyingInfoModel model = new TB045AccompanyingInfoModel();

	/** �T�[�r�X */
	@Autowired
	private TB045AccompanyingInfoService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return �ڋq�ڍוt����񃂃f��	
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("accompanyingInfoInit")
	public String init() throws Exception {

		try {
			// �p�����[�^�̌ڋqID��NULL�A�܂��́A�󗓂̏ꍇ�A�����𒆒f
			if (StringUtils.isBlank(model.getKokyakuId())) {
				throw new ApplicationException("�ڋqID�s���F�p�����[�^�̌ڋqID" );
			}
			
			// �p�����[�^�̉�ʋ敪���utb045�F�ڋq�ڍוt�����Q�Ɓv�ȊO�̏ꍇ�A�����𒆒f
			if (! Constants.GAMEN_KBN_CUSTOMER_DETAIL_ACCOMPANYING.equals(model.getGamenKbn())) {
				throw new ApplicationException("��ʋ敪�s���F�p�����[�^�̉�ʋ敪");
			}
			
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

		model.setGamenKbn(Constants.GAMEN_KBN_CUSTOMER_DETAIL_ACCOMPANYING);
		
		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB045AccompanyingInfoModel getModel() {
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