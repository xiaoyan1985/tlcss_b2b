package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintTestModel;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="../test/jsp/test_tb015_report_print_test.jsp"),
	@Result(name=INPUT, location="../test/jsp/test_tb015_report_print_test.jsp")
})
public class TB015ReportPrintTestAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB015ReportPrintTestModel> {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB015ReportPrintTestModel model = new TB015ReportPrintTestModel();

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ���O�C����ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("reportPrintTestInit")
	public String init() throws Exception {

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB015ReportPrintTestModel getModel() {
		return model;
	}
}
