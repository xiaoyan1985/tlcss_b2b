package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.Map;
import java.util.Set;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB001LoginModel;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB003ExternalLoginModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB003ExternalLoginService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �O�����O�C���A�N�V�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/04/25
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb001_login.jsp"),
	@Result(name=INPUT, location="tb001_login.jsp")
})
public class TB003ExternalLoginAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB003ExternalLoginModel> {
	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB003ExternalLoginModel model = new TB003ExternalLoginModel();

	/** �T�[�r�X */
	@Autowired
	private TB003ExternalLoginService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ���O�C����ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
		value="externalLogin",
		results = {
			@Result(name=SUCCESS, type="redirectAction", location="${nextPageUrl}${nextPageParam}")
		}
	)
	public String execute() throws Exception {
		// �p�����[�^�`�F�b�N
		service.validateParameter(model);

		// ���O�C����ʕ\���t���O
		boolean isLoginDisplay = (getUserContext() == null);

		// ���y�[�W�̃Z�b�g
		if (isLoginDisplay) {
			// ���[�U�R���e�L�X�g���Ȃ��i���O�C�����Ă��Ȃ��j�ꍇ�́A���O�C����ʂɑJ��
			model.setNextPageUrl(TB001LoginModel.ACTION_NM_LOGIN_INIT);
		} else {
			// ���[�U�R���e�L�X�g������i���O�C�����Ă���j�ꍇ�́A�J�ڐ�URL�̉�ʂɑJ��
			model.setNextPageUrl(model.getActionURL().replaceAll("/", ""));
		}

		// ���y�[�W�ւ̃p�����[�^�ݒ�
		model.setNextPageParam(createNextPageParam(isLoginDisplay));

		return SUCCESS;
	}

	/**
	 * ���y�[�W��ʂւ̃p�����[�^���쐬���܂��B
	 *
	 * @param isLoginDisplay ���O�C����ʕ\���t���O
	 * @return ���y�[�W��ʂւ̃p�����[�^
	 */
	private String createNextPageParam(boolean isLoginDisplay) {
		StringBuilder nextPageParam = new StringBuilder("");

		Map<String, String[]> requestParamMap =
			ServletActionContext.getRequest().getParameterMap();
		if (requestParamMap == null || requestParamMap.isEmpty()) {
			// ���N�G�X�g���Ȃ��ꍇ�́A�󕶎���Ԃ�
			return nextPageParam.toString();
		}

		Set<String> keySet = requestParamMap.keySet();
		for (String key : keySet) {
			if (!isLoginDisplay && TB001LoginModel.PARAM_NM_ACTION_URL.equals(key)) {
				// ���y�[�W�����O�C����ʂłȂ��ꍇ�́AactionURL�͍쐬���Ȃ�
				continue;
			}

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

		return nextPageParam.toString();
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB003ExternalLoginModel getModel() {
		return model;
	}
}
