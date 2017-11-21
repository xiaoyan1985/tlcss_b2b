package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB016OutsourcerSelectCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB016OutsourcerSelectModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB016OutsourcerSelectService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �ϑ���БI���A�N�V�����N���X�B
 *
 * @author k002785
 * @version 1.0 2015/09/08
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb016_outsourcer_select.jsp")
})
public class TB016OutsourcerSelectAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB016OutsourcerSelectModel> {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB016OutsourcerSelectModel model = new TB016OutsourcerSelectModel();

	/** �T�[�r�X */
	@Autowired
	private TB016OutsourcerSelectService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("outsourcerInit")
	public String init() throws Exception {
		
		// �e��ʂ̉�Ђh�c���͗���name�̒l�����݂��Ȃ��ꍇ
		if (StringUtils.isBlank(model.getKaishaIdResultNm())) {
			throw new ApplicationException("�e��ʂ̉�Ђh�c���͗���name�s���F�p�����[�^�̐e��ʂ̉�Ђh�c���͗���name" );
		}
		
		// �e��ʂ̉�Ж����͗���name�̒l�����݂��Ȃ��ꍇ
		if (StringUtils.isBlank(model.getKaishaNmResultNm())) {
			throw new ApplicationException("�e��ʂ̉�Ж����͗���name�s���F�p�����[�^�̐e��ʂ̉�Ж����͗���name" );
		}

		// �����\�����A�R���f�V�����������l�Őݒ�
		model.setCondition(null);

		// ����I��
		return SUCCESS;
	}
	
	/**
	 * �����������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("outsourcerSearch")
	public String search() throws Exception {

		// ��ʂ̓��̓p�����[�^�ɕR�Â������������擾
		TB016OutsourcerSelectCondition condition = model.getCondition();

		// �V�X�e���}�X�^�萔Map����ő匟���\�����A�ő�\���������擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �ő匟���\�����擾
		int searchMax = userContext.getSystgemContstantAsInt(
			RcpMSystem.RCP_M_SYSTEM_B2B_CONSIGNMENT_COMPANY_MAX_COUNT);

		// �ő匟�������̐ݒ�
		condition.setMaxCount(searchMax);

		// ���������̐ݒ�
		model.setCondition(condition);

		// �����������s
		model = service.executeSearch(model);

		// �������ʂ̔���i�������ʂ̕ԋp�l��null�̏ꍇ�͂Ȃ�����empty�ōs���j
		if (model.getResultList().isEmpty()) {
			// �������ʂ�0���̏ꍇ
			addActionMessage("MSG0002");
		} else {
			// �������ʂ�����𒴂��Ă��Ȃ��ꍇ
			if (condition.isCompleted()) {
				addActionMessage("MSG0004", String.valueOf(model.getResultList().size()));
			} else {
				// ��������𒴂����ꍇ
				addActionMessage("MSG0006");
			}
		}

		// ����I��
		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB016OutsourcerSelectModel getModel() {
		return model;
	}
}
