package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.Map;
import java.util.Set;

import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.filter.CsrfPreventionFilter;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB001LoginModel;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB004PasswordChangeModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB004PasswordChangeService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �p�X���[�h�ύX�A�N�V�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/04/28
 */
@Results({
	@Result(name=SUCCESS, location="tb004_password_change.jsp"),
	@Result(name=INPUT, location="tb004_password_change.jsp")
})
public class TB004PasswordChangeAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB004PasswordChangeModel>, ServiceValidatable {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** �O�����O�C�����ɏ��O����p�����[�^ */
	private final String[] EXCLUDE_PARAMS = {TB001LoginModel.PARAM_NM_ACTION_URL, "currentPassword", "newPassword", "confirmNewPassword", CsrfPreventionFilter.CSRF_NONCE_REQUEST_PARAM, "dispMode"};

	/** ��ʃ��f�� */
	private TB004PasswordChangeModel model = new TB004PasswordChangeModel();

	/** �T�[�r�X */
	@Autowired
	private TB004PasswordChangeService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return
	 * @throws Exception
	 */
	@Action("passwordChangeInit")
	public String init() throws Exception {
		// �������������s
		// ���[�U�R���e�L�X�g�̎擾
		model.setUserContext(getUserContext());

		return SUCCESS;
	}

	/**
	 * �p�X���[�h�X�V�������s���܂��B
	 *
	 * @return
	 * @throws Exception
	 */
	@Action(
			value="passwordChangeUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb004_password_change.jsp"),
					@Result(name=MENU, type="redirectAction", location="menuInit"),
					@Result(name=EXTERNAL_LOGIN, type="redirectAction", location="${nextPageUrl}${nextPageParam}")
			}
	)
	public String update() throws Exception {
		// �p�X���[�h�X�V����
		service.updatePassword(model);

		// �������b�Z�[�W
		addActionMessage("MSG0001", "�p�X���[�h�̕ύX");

		if (model.isExternalLogin()) {
			// �O�����O�C������̑J�ڂ̏ꍇ�A�p�����[�^���쐬���A�J�ڐ��ʂɑJ�ڂ���
			model.setNextPageUrl(model.getActionURL().replaceAll("/", ""));
			model.setNextPageParam(createNextPageParam());

			return EXTERNAL_LOGIN;
		}

		if (!model.isFromMenu()) {
			// ���j���[����̑J�ڈȊO�̏ꍇ�́A���j���[��ʂ�\��
			return MENU;
		}

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {

	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB004PasswordChangeModel getModel() {
		return model;
	}

	/**
	 * ���y�[�W��ʂւ̃p�����[�^���쐬���܂��B
	 *
	 * @return ���y�[�W��ʂւ̃p�����[�^
	 */
	private String createNextPageParam() {
		StringBuilder nextPageParam = new StringBuilder("");

		Map<String, String[]> requestParamMap =
			ServletActionContext.getRequest().getParameterMap();
		if (requestParamMap == null || requestParamMap.isEmpty()) {
			// ���N�G�X�g���Ȃ��ꍇ�́A�󕶎���Ԃ�
			return nextPageParam.toString();
		}

		Set<String> keySet = requestParamMap.keySet();
		for (String key : keySet) {
			boolean isExclude = false;

			for (String excludeParam : EXCLUDE_PARAMS) {
				if (key.equals(excludeParam)) {
					isExclude = true;
					break;
				}
			}

			if (!isExclude) {
				if (StringUtils.isBlank(nextPageParam.toString())) {
					nextPageParam.append("?");
				} else {
					nextPageParam.append("&");
				}

				nextPageParam.append(key);
				nextPageParam.append("=");

				StringBuilder valueParam = new StringBuilder("");
				for (String value : requestParamMap.get(key)) {
					if (StringUtils.isNotBlank(valueParam.toString())) {
						valueParam.append(",");
					}
					valueParam.append(value);
				}
				nextPageParam.append(valueParam.toString());
			}
		}

		return nextPageParam.toString();
	}
}
