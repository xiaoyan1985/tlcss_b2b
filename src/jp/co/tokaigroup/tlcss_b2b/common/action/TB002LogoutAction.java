package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.SUCCESS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * ���O�A�E�g�A�N�V�����N���X�B
 *
 * @author N.Akahori
 * @version 1.0 2012/10/03
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, type="redirectAction", location="loginInit")
})
public class TB002LogoutAction extends TLCSSB2BBaseActionSupport implements
		ServletRequestAware {

	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * ���O�A�E�g�������s���܂��B
	 *
	 */
	@Action("logout")
	public String execute() throws Exception {

		//�Z�b�V�����I�u�W�F�N�g�J��
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		session = request.getSession(true);

		return SUCCESS;
	}


}
