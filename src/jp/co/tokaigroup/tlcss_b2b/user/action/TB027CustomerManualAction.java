package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB027CustomerManualModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB027CustomerManualService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �ڋq�}�j���A���ꗗ�A�N�V�����N���X�B
 *
 * @author ���t
 * @version 1.0 2015/08/05
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb027_customer_manual.jsp")
})
public class TB027CustomerManualAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB027CustomerManualModel> {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB027CustomerManualModel model = new TB027CustomerManualModel();

	/** �T�[�r�X */
	@Autowired
	private TB027CustomerManualService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return �ڋq�}�j���A���ꗗ��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("customerManualInit")
	public String init() throws Exception {
		if (StringUtils.isBlank(model.getKokyakuId())) {
			// �p�����[�^�̌ڋq�h�c���擾�ł��Ȃ��ꍇ�́A�p�����[�^�G���[
			throw new ApplicationException("�ڋq�h�c�s���F�p�����[�^�̌ڋq�h�c");
		}

		// �����\������
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return �ڋq�}�j���A���ꗗ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB027CustomerManualModel getModel() {
		return model;
	}
}
