package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB012ContractorSelectCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB012ContractorSelectModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB012ContractorSelectService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �ƎґI���A�N�V�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb012_contractor_select.jsp")
})
public class TB012ContractorSelectAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB012ContractorSelectModel> , ServiceValidatable{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB012ContractorSelectModel model = new TB012ContractorSelectModel();

	/** �T�[�r�X */
	@Autowired
	private TB012ContractorSelectService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("contractorInit")
	public String init() throws Exception {
		//�����\�����A�R���f�V�����������l�ɖ߂�
		model.setCondition(null);

		return SUCCESS;
	}

	/**
	 * �����������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("contractorSearch")
	public String search() throws Exception {

			TB012ContractorSelectCondition condition = model.getCondition();

			// �V�X�e���}�X�^�萔Map����ő匟���\�����A�ő�\���������擾
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// �ő匟���\�����擾
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_CONTRACTOR_MAX_COUNT);

			condition.setMaxCount(searchMax);

			model.setCondition(condition);

		try {
			// �����������s
			model = service.executeSearch(model);

		} catch (PagerMaxCountException e) {
			// ��������̏ꍇ
			addActionMessage("MSG0005", String.valueOf(searchMax));
		}

		// �������ʂ̔���
		if (model.getResultList() != null && model.getResultList().isEmpty()) {
			// �������ʂ�0���̏ꍇ
			addActionMessage("MSG0002");
		} else if (model.getResultList() != null && ! model.getResultList().isEmpty()) {
			// �������ʂ��擾�ł����ꍇ
			if (condition.isCompleted()) {
				addActionMessage("MSG0004", String.valueOf(model.getResultList().size()));
			}else{
				// ��������𒴂����ꍇ
				addActionMessage("MSG0006");
			}
		}

		return SUCCESS;
	}


	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB012ContractorSelectModel getModel() {
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
