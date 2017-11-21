package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB048DocumentLibraryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB048DocumentLibraryService;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �������C�u�����ꗗ�A�N�V�����N���X�B
 *
 * @author C.Kobayashi
 * @version 1.0 2015/10/28
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb048_document_library.jsp")
})
public class TB048DocumentLibraryAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB048DocumentLibraryModel>{
	
	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB048DocumentLibraryModel model = new TB048DocumentLibraryModel();

	/** �T�[�r�X */
	@Autowired
	private TB048DocumentLibraryService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return �������C�u�����ꗗ��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("documentLibraryInit")
	public String init() throws Exception {

		// �����\������
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return �������C�u�����ꗗ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB048DocumentLibraryModel getModel() {
		return model;
	}

}
