package jp.co.tokaigroup.tlcss_b2b.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.context.UserContextSupport;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * iPad�`�F�b�N�C���^�[�Z�v�^�[�B
 *
 * @author ���t
 * @version 1.0 2015/08/07
 */
public class IPadCheckInterceptor extends AbstractInterceptor {

	/**
	 * �C���^�[�Z�v�^�[�����B
	 * iPad���ǂ������`�F�b�N���܂��B
	 *
	 * @param invocation ��ʏ����Ăяo�����
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		// ���N�G�X�g�擾
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		// ���[�U�[���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) session.getAttribute(UserContextSupport.KEY);
		if (userContext != null) {
			// ���[�U�[�G�[�W�F���g�ݒ�
			userContext.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));
		}

		return invocation.invoke();
	}
}
