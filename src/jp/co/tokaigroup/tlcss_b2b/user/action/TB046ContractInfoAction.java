package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.kokyaku.model.RC014KokyakuKeiyakuListCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB046ContractInfoModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB046ContractInfoService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �_����A�N�V�����N���X�B
 *
 * @author v145527
 * @version 1.0 2015/08/04
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb046_contract_info.jsp"),
	@Result(name=INPUT, location="tb046_contract_info.jsp")
})

public class TB046ContractInfoAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB046ContractInfoModel>, ServiceValidatable{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB046ContractInfoModel model = new TB046ContractInfoModel();

	/** �T�[�r�X */
	@Autowired
	private TB046ContractInfoService service;
	
	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("contractInfoInit")
	public String init() throws Exception {

		// �����\������
		try {
			RC014KokyakuKeiyakuListCondition condition = model.getCondition();

			// �����\���p�����[�^�`�F�b�N
			if (StringUtils.isBlank(model.getKokyakuId())) {
				// �p�����[�^�̌ڋq�h�c���擾�ł��Ȃ��ꍇ�G���[
				throw new ApplicationException("�ڋqID�s���F�p�����[�^�̌ڋqID" );
			}
			if (!Constants.GAMEN_KBN_CUSTOMER_DETAIL_CONTRACT.equals(model.getGamenKbn())) {
				// �p�����[�^�̉�ʋ敪��TB046:�ڋq�ڍ׌_����Q�ƂłȂ��ꍇ�G���[
				throw new ApplicationException("��ʋ敪�s���F�p�����[�^�̉�ʋ敪" );
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

		model.setGamenKbn(Constants.GAMEN_KBN_CUSTOMER_DETAIL_CONTRACT );

		return SUCCESS;
	}
	

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	@Override
	public TB046ContractInfoModel getModel() {
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
