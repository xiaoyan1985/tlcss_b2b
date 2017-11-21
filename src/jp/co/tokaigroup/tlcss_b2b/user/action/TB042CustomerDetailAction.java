package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import static com.opensymphony.xwork2.Action.INPUT;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB042CustomerDetailModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB042CustomerDetailService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �����E�����ҏڍ׃A�N�V�����N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/06
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb042_customer_detail.jsp"),
	@Result(name=INPUT, location="tb042_customer_detail.jsp")
})
public class TB042CustomerDetailAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB042CustomerDetailModel> , ServiceValidatable{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB042CustomerDetailModel model = new TB042CustomerDetailModel();

	/** �T�[�r�X */
	@Autowired
	private TB042CustomerDetailService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("customerDetailInit")
	public String init() throws Exception {
		// �������擾����
		model = service.getInitInfo(model);
		// ��ʋ敪�̐ݒ�
		model.setGamenKbn(Constants.GAMEN_KBN_CUSTOMER_DETAIL);

		return SUCCESS;
	}


	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB042CustomerDetailModel getModel() {
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

