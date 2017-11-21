package jp.co.tokaigroup.tlcss_b2b.common.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.si.fw.context.UserContextSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * �A�N�Z�X�����`�F�b�N�C���^�[�Z�v�^�[�B
 *
 * @author k002849
 * @version 4.0 2014/07/07
 */
public class RoleCheckInterceptor extends AbstractInterceptor {
	/** �Z�b�V�����^�C���A�E�g���̃t�H���[�h�� */
	private static final String FORWARD_IN_SESSION_TIMEOUT = "notLoggedIn";
	/** �A�N�Z�X�s���̃t�H���[�h�� */
	private static final String FORWARD_IN_NOT_ACCESSABLE = "forbidden";

	/** ���OURL */
	private String excludeURLs;

	/**
	 * �C���^�[�Z�v�^�[�����B
	 * �A�N�Z�X�������`�F�b�N���܂��B
	 *
	 * @param invocation ��ʏ����Ăяo�����
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		// ���N�G�X�g�擾
		HttpServletRequest request = ServletActionContext.getRequest();
		// ���N�G�X�gURL�擾
		String requestUrl = getRequestUrl(request);

		// ���OURL�Ɋ܂܂�Ă��邩�`�F�b�N
		String[] excludeUrlArray = StringUtils.split(getExcludeURLs(), ",");
		for (String excludeUrl : excludeUrlArray) {
			if (excludeUrl.equals(requestUrl)) {
				// ���OURL�Ɋ܂܂�Ă����ꍇ�́A�����I��
				return invocation.invoke();
			}
		}

		HttpSession session = request.getSession();
		// ���[�U�[���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) session.getAttribute(UserContextSupport.KEY);
		if (userContext == null) {
			// ���[�U�[��񂪎擾�ł��Ȃ������ꍇ�́A�Z�b�V�����^�C���A�E�g
			return FORWARD_IN_SESSION_TIMEOUT;
		}

		// �A�N�Z�X�\URLMap���擾
		Map<String, TbMRoleUrl> accessibleMap = userContext.getAccessibleMap();

		// �A�N�Z�X�\URL�̏ꍇ�́A���̃C���^�[�Z�v�^�[�������{�A�A�N�Z�X�s�̏ꍇ�́A403�G���[
		return accessibleMap.containsKey(requestUrl) ? invocation.invoke() : FORWARD_IN_NOT_ACCESSABLE;
	}

	/**
	 * ���OURL���擾���܂��B
	 *
	 * @return ���OURL
	 */
	public String getExcludeURLs() {
		return excludeURLs;
	}
	/**
	 * ���OURL��ݒ肵�܂��B
	 *
	 * @param excludeURLs ���OURL
	 */
	public void setExcludeURLs(String excludeURLs) {
		this.excludeURLs = excludeURLs;
	}

	/**
	 * ���N�G�X�gURL���擾���܂��B
	 *
	 * @param ���N�G�X�g���
	 * @return ���N�G�X�gURL
	 */
	private String getRequestUrl(HttpServletRequest request) {
		// URL�����OURL�Ɋ܂܂�邩�`�F�b�N
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		String actionURL = StringUtils.remove(requestURI, contextPath);

		// xxx.action;jsessionid=xxxx�@�� ;jsessionid�ȍ~����菜��
		int jsessionIdStart = actionURL.indexOf(";");
		if (jsessionIdStart > 0) {
			actionURL = actionURL.substring(0, jsessionIdStart);
		}

		// xxx.action��.action����菜��
		int actionStart = actionURL.indexOf(".action");
		if (actionStart > 0) {
			actionURL = actionURL.substring(0, actionStart);
		}

		return actionURL;
	}
}
