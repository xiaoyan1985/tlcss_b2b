package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import javax.servlet.http.HttpServletRequest;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * �_�~�[���O�C���A�N�V�����N���X�B
 *
 * @author N.Akahori
 * @version 1.0 2012/08/07
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=INPUT, location="dummy_login.jsp"),
	@Result(name=SUCCESS, location="dummy_menu.jsp")
})
public class DummyLoginAction extends TLCSSB2BBaseActionSupport implements
		ServletRequestAware {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** ��ʂ���̈��p����� */
	private TLCSSB2BUserContext userContext = new TLCSSB2BUserContext();

	/** ���N�G�X�g��� */
	private HttpServletRequest request;

	/**
	 * �R���X�g���N�^
	 *
	 * @param request ���N�G�X�g���
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * ���O�C���������s���܂��B
	 *
	 */
	@Action(
		value = "dummyLoginExecute",
		results = {
			@Result(name=SUCCESS, type="redirectAction",
					location="dummyMenu")
		})
	public String execute() throws Exception {
		//���O�C���h�c�����M����Ă��Ȃ�������G���[��ʂ֑J��
		if (StringUtils.isBlank(userContext.getLoginId())) {
			return LOGIN_ERROR;
		}

		//���O�C�����[�U��IP�A�h���X���Z�b�g
		userContext.setLoginIpAdress(request.getRemoteAddr());

		// �Z�b�V�����ɕۑ�
		setUserContextToSession(userContext);

		return SUCCESS;
	}

	/**
	 * �J���p���O�C����ʂ֑J�ڂ��܂��B
	 *
	 */
	@Action("dummyMenu")
	public String dummy() throws Exception {
		return SUCCESS;
	}

	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}

	public void setUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
	}
}
