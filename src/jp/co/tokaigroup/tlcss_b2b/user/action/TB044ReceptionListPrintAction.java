package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB044ReceptionListPrintModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB044ReceptionListPrintService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ��t�ꗗ����A�N�V�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/07/08
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name= SUCCESS, location="tb044_reception_list_print.jsp"),
	@Result(name= INPUT, location="tb044_reception_list_print.jsp")
})
public class TB044ReceptionListPrintAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB044ReceptionListPrintModel>{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB044ReceptionListPrintModel model = new TB044ReceptionListPrintModel();

	/** �T�[�r�X */
	@Autowired
	private TB044ReceptionListPrintService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("receptionListInit")
	public String init() throws Exception {

		// �����\������
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB044ReceptionListPrintModel getModel() {
		return model;
	}
}
