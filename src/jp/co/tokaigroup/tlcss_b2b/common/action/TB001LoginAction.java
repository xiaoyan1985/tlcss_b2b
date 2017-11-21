package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.context.UserContextSupport;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.filter.CsrfPreventionFilter;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB001LoginModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB001LoginService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ���O�C���A�N�V�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/04/22
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb001_login.jsp"),
	@Result(name=INPUT, location="tb001_login.jsp")
})
public class TB001LoginAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB001LoginModel>, ServiceValidatable {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** �p�����[�^�� �J�ڐ�URL */
	private final String PARAM_NM_ACTION_URL = "actionURL";

	/** �O�����O�C�����ɏ��O����p�����[�^ */
	private final String[] EXCLUDE_PARAMS = {PARAM_NM_ACTION_URL, "loginId", "passwd", CsrfPreventionFilter.CSRF_NONCE_REQUEST_PARAM, "dispMode"};

	/** ��ʃ��f�� */
	private TB001LoginModel model = new TB001LoginModel();

	/** �T�[�r�X */
	@Autowired
	private TB001LoginService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ���O�C����ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("loginInit")
	public String init() throws Exception {
		return SUCCESS;
	}

	/**
	 * ���O�C���������s���܂��B
	 *
	 * @return ���O�C����ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="login",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction", location="menuInit"),
					@Result(name=LOGIN_ERROR, location="tb001_login.jsp"),
					@Result(name=PASSWORD_CHANGE, type="redirectAction", location="passwordChangeInit${actionParam}"),
					@Result(name=EXTERNAL_LOGIN, type="redirectAction", location="${actionURL}${actionParam}")
			}
	)
	public String login() throws Exception {
		// �Z�b�V�������̃N���A
		session.remove(UserContextSupport.KEY);

		// ���O�C�����[�U���̐V�K�쐬
		TLCSSB2BUserContext userContext = new TLCSSB2BUserContext();

		// ���O�C������
		model = service.executeLogin(model);

		// �Z�b�V�������̐ݒ�
		HttpServletRequest request = ServletActionContext.getRequest();
		// ���O�C�����[�U��IP�A�h���X�̐ݒ�
		userContext.setLoginIpAdress(request.getRemoteAddr());
		// �V�X�e���萔Map�̐ݒ�
		userContext.setSystemConstants(model.getSystemConstants());

		// �ڑ��敪�̐ݒ�
		// �J�������[�U���̎擾
		String devUser = ResourceFactory.getResource().getString("RCP_USER");
		// �{�Ԋ����[�U���̎擾
		String honbanUser = ResourceFactory.getResource().getString("HONBAN_RCP_USER");
		if (devUser.equals(honbanUser)) {
			// �J�����Ɩ{�Ԋ��̃��[�U�������ꍇ�A�{�Ԋ�
			userContext.setPlcon(TLCSSB2BUserContext.PL_CON_PRODUCTION);
		} else {
			// �J�����Ɩ{�Ԋ��̃��[�U�������ꍇ�A�J����
			userContext.setPlcon(TLCSSB2BUserContext.PL_CON_DEVELOPMENT);
		}

		// ���O�C���h�c�̐ݒ�
		userContext.setLoginId(model.getUserInfo().getLoginId());
		// ���O�C�����[�U���̐ݒ�
		userContext.setUserName(model.getUserInfo().getUserNm());
		// �ڋq�h�c�̐ݒ�
		userContext.setRefKokyakuId(model.getUserInfo().getKokyakuId());

		// �Q�ƌڋq���̐ݒ�
		if (model.getUserInfo().isRealEstate()) {
			if (model.getRefKokyakuList().size() == 1 &&
					TbMRefKokyaku.LOWER_DSP_FLG_SHOWING.equals(model.getRefKokyakuList().get(0).getLowerDspFlg())) {
				// �擾�����ڋq�h�c��1�����A���ʑw�\���t���O���u1�F�\���v�̏ꍇ
				userContext.setKokyakuIdSelected(true);
				userContext.setSingleFlg(true);
			} else {
				userContext.setKokyakuIdSelected(false);
				userContext.setSingleFlg(false);
			}
			userContext.setKokyakuId(model.getUserInfo().getKokyakuId());
			userContext.setKokyakuName(model.getKokyakuInfo().getKanjiNm());
		}

		// �ϑ���Ђh�c�̐ݒ�
		userContext.setKaishaId(model.getUserInfo().getKaishaId());
		// �Ǝ҃R�[�h�̐ݒ�
		userContext.setGyoshaCd(model.getUserInfo().getGyoshaCd());
		// �����̐ݒ�
		userContext.setRole(model.getUserInfo().getRole());
		userContext.setLoginRole(model.getUserInfo().getRole());
		// �A�N�Z�X�\URLMap�̐ݒ�
		userContext.setAccessibleMap(model.getAccessibleMap());

		setUserContextToSession(userContext);

		TbMUser user = model.getUserInfo();
		user.setUserContext(userContext);
		model.setUserInfo(user);

		// �J�ڐ�̌���
		if (model.getUserInfo().isNormalResult()) {
			// ���O�C�����ʏ퐬���̏ꍇ
			if (StringUtils.isNotBlank(model.getActionURL())) {
				// �J�ڐ�URL�ɑ���p�����[�^�쐬
				model.setActionParam(createDirectUrlParam(request, false));
				model.setActionURL(model.getActionURL().replaceAll("/", ""));

				// �J�ڐ�URL�w��̏ꍇ�́A�J�ڐ�URL�ɑJ��
				return EXTERNAL_LOGIN;
			} else {
				// ����ȊO�̏ꍇ�́A���j���[��ʂɑJ��
				return SUCCESS;
			}
		} else {
			// ���O�C�����ʏ퐬���ȊO�̏ꍇ
			if (model.getUserInfo().isTempPasswordLimit()) {
				if (StringUtils.isBlank(model.getSystemConstants().get(
						RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_TOIAWASE_TEL_NO))) {
					throw new ApplicationException("�V�X�e���₢���킹��d�b�ԍ��擾���s�B");
				}
				if (StringUtils.isBlank(model.getSystemConstants().get(
						RcpMSystem.RCP_M_SYSTEM_B2B_SUPPORT_RECEPTION_TIME))) {
					throw new ApplicationException("�T�|�[�g�Z���^�[��t���Ԏ擾���s�B");
				}
				// �b��p�X���[�h�����؂�̏ꍇ
				addActionError("MSG0009", model.getSystemConstants().get(RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_TOIAWASE_TEL_NO),
						model.getSystemConstants().get(RcpMSystem.RCP_M_SYSTEM_B2B_SUPPORT_RECEPTION_TIME));

				return LOGIN_ERROR;
			} else if (model.getUserInfo().isTempPasswordChange()) {
				// �b��p�X���[�h�ύX�ʒm�̏ꍇ
				addActionError("MSG0010");

				// �p�����[�^����
				model.setActionParam(createUrlParamForPasswordChange(
						model, request, TB001LoginModel.DISP_MODE_PASSWORD_LIMIT));

				return PASSWORD_CHANGE;
			} else if (model.getUserInfo().isPasswordLimit()) {
				// �����p�X���[�h�����؂�̏ꍇ
				addActionError("MSG0012");

				// �p�����[�^����
				model.setActionParam(createUrlParamForPasswordChange(
						model, request, TB001LoginModel.DISP_MODE_PASSWORD_LIMIT));

				return PASSWORD_CHANGE;
			} else {
				// �����p�X���[�h�����؂�ԋ߂̏ꍇ
				String paramMsg = "";
				// ���b�Z�[�W�̐ݒ�
				if (model.getValidDateCount() == 0) {
					paramMsg = "�{��";
				} else {
					paramMsg = "����" + model.getValidDateCount() + "��";
				}
				addActionError("MSG0011", paramMsg);

				// �p�����[�^����
				model.setActionParam(createUrlParamForPasswordChange(
						model, request, TB001LoginModel.DISP_MODE_PASSWORD_LIMIT_NEAR));

				return PASSWORD_CHANGE;
			}
		}
	}

	/**
	 * �J�ڐ�URL���ڎw�莞�̃p�����[�^���쐬���܂��B
	 *
	 * @param request ���N�G�X�g
	 * @return �J�ڐ�URL���ڎw�莞�̃p�����[�^
	 */
	private String createDirectUrlParam(HttpServletRequest request, boolean isPasswordChange) {
		StringBuilder sendPrams = new StringBuilder("");

		Map<String, String[]> paramMap = request.getParameterMap();
		Set<String> keySet = paramMap.keySet();
		for (String key : keySet) {
			boolean isExclude = false;

			for (String excludeParam : EXCLUDE_PARAMS) {
				if (key.equals(excludeParam)) {
					isExclude = true;
					break;
				}
			}

			if (isPasswordChange && key.equals(PARAM_NM_ACTION_URL)) {
				// �p�X���[�h�ύX��ʂ̏ꍇ�́A�J�ڐ�URL���K�v�Ȃ��߁A�p�����[�^�Ɋ܂߂�
				isExclude = false;
			}

			if (!isExclude) {
				if (StringUtils.isBlank(sendPrams.toString())) {
					sendPrams.append("?");
				} else {
					sendPrams.append("&");
				}

				sendPrams.append(key);
				sendPrams.append("=");

				StringBuilder valueParam = new StringBuilder("");
				for (String value : paramMap.get(key)) {
					if (StringUtils.isNotBlank(valueParam.toString())) {
						valueParam.append(",");
					}
					valueParam.append(value);
				}
				sendPrams.append(valueParam.toString());
			}
		}

		return sendPrams.toString();
	}

	/**
	 * �p�X���[�h�ύX��ʂɑ���p�����[�^�𐶐����܂��B
	 *
	 * @param model ���O�C�����
	 * @param request ���N�G�X�g���
	 * @param dispMode ��ʃ��[�h
	 * @return �p�X���[�h�ύX��ʂɑ���p�����[�^
	 */
	private String createUrlParamForPasswordChange(TB001LoginModel model, HttpServletRequest request, int dispMode) {
		StringBuilder sendPrams = new StringBuilder("");

		if (StringUtils.isNotBlank(model.getActionURL())) {
			// �J�ڐ�URL�ɑ���p�����[�^�쐬
			sendPrams.append(createDirectUrlParam(request, true));
			if (StringUtils.isBlank(sendPrams.toString())) {
				sendPrams.append("?");
			} else {
				sendPrams.append("&");
			}
			sendPrams.append("dispMode=" + Integer.toString(dispMode));
		} else {
			sendPrams.append("?dispMode=" + Integer.toString(dispMode));
		}

		return sendPrams.toString();
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB001LoginModel getModel() {
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
