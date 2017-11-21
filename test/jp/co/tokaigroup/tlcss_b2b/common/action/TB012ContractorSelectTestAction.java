package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.gyosha.model.RC061GyoshaSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB012ContractorSelectTestModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB012ContractorSelectTestService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �ƎґI����ʁi�X�^�u�j�A�N�V�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/29
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="../test/jsp/test_tb012_contractor_select.jsp"),
	@Result(name=INPUT, location="../test/jsp/test_tb012_contractor_select.jsp")
})
public class TB012ContractorSelectTestAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB012ContractorSelectTestModel> {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB012ContractorSelectTestModel model = new TB012ContractorSelectTestModel();

	/** �T�[�r�X */
	@Autowired
	private TB012ContractorSelectTestService service;

	@Action("contractorSelectTestInit")
	public String init() throws Exception {
		RC061GyoshaSearchCondition condition = new RC061GyoshaSearchCondition();

		condition.setOffset(1);
		condition.setLimit(-1);
		condition.setDisplayToMax(true);

		model.setCondition(condition);

		model = service.getGyoshaList(model);

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB012ContractorSelectTestModel getModel() {
		return model;
	}
}
