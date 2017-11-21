package jp.co.tokaigroup.tlcss_b2b.common.model;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.si.fw.context.UserContextSupport;

/**
 * �p�X���[�h�ύX��ʃ��f���B
 *
 * @author k002849
 * @version 1.0 2014/04/28
 */
public class TB004PasswordChangeModel extends TB000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "�p�X���[�h�ύX";

	/** ���݂̃p�X���[�h */
	private String currentPassword;
	/** �V�p�X���[�h */
	private String newPassword;
	/** �V�p�X���[�h(�m�F) */
	private String confirmNewPassword;

	/** ��ʃ��[�h */
	private int dispMode;

	/** ���[�U�R���e�L�X�g */
	private UserContextSupport userContext;

	// �O�����O�C���̍ۂɎg�p
	/** �J�ڐ�URL */
	private String actionURL;
	/** ���y�[�WURL */
	private String nextPageUrl;
	/** ���y�[�W�ւ̃p�����[�^ */
	private String nextPageParam;

	/**
	 * ���݂̃p�X���[�h���擾���܂��B
	 *
	 * @return ���݂̃p�X���[�h
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}
	/**
	 * ���݂̃p�X���[�h��ݒ肵�܂��B
	 *
	 * @param currentPassword ���݂̃p�X���[�h
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	/**
	 * �V�p�X���[�h���擾���܂��B
	 *
	 * @return �V�p�X���[�h
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * �V�p�X���[�h��ݒ肵�܂��B
	 *
	 * @param newPassword �V�p�X���[�h
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * �V�p�X���[�h(�m�F)���擾���܂��B
	 *
	 * @return �V�p�X���[�h(�m�F)
	 */
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	/**
	 * �V�p�X���[�h(�m�F)��ݒ肵�܂��B
	 *
	 * @param confirmNewPassword �V�p�X���[�h(�m�F)
	 */
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	/**
	 * ��ʃ��[�h���擾���܂��B
	 *
	 * @return ��ʃ��[�h
	 */
	public int getDispMode() {
		return dispMode;
	}
	/**
	 * ��ʃ��[�h��ݒ肵�܂��B
	 *
	 * @param dispMode ��ʃ��[�h
	 */
	public void setDispMode(int dispMode) {
		this.dispMode = dispMode;
	}

	/**
	 * ���[�U�R���e�L�X�g���擾���܂��B
	 *
	 * @return ���[�U�R���e�L�X�g
	 */
	public UserContextSupport getUserContext() {
		return userContext;
	}
	/**
	 * ���[�U�R���e�L�X�g��ݒ肵�܂��B
	 *
	 * @param userContext ���[�U�R���e�L�X�g
	 */
	public void setUserContext(UserContextSupport userContext) {
		this.userContext = userContext;
	}

	/**
	 * �J�ڐ�URL���擾���܂��B
	 *
	 * @return �J�ڐ�URL
	 */
	public String getActionURL() {
		return actionURL;
	}
	/**
	 * �J�ڐ�URL��ݒ肵�܂��B
	 *
	 * @param actionURL �J�ڐ�URL
	 */
	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	/**
	 * ���y�[�WURL���擾���܂��B
	 *
	 * @return ���y�[�WURL
	 */
	public String getNextPageUrl() {
		return nextPageUrl;
	}
	/**
	 * ���y�[�WURL��ݒ肵�܂��B
	 *
	 * @param nextPageUrl ���y�[�WURL
	 */
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}

	/**
	 * ���y�[�W�ւ̃p�����[�^���擾���܂��B
	 *
	 * @return ���y�[�W�ւ̃p�����[�^
	 */
	public String getNextPageParam() {
		return nextPageParam;
	}
	/**
	 * ���y�[�W�ւ̃p�����[�^��ݒ肵�܂��B
	 *
	 * @param nextPageParam ���y�[�W�ւ̃p�����[�^
	 */
	public void setNextPageParam(String nextPageParam) {
		this.nextPageParam = nextPageParam;
	}

	/**
	 * ���j���[����̑J�ڂ��𔻒肵�܂��B
	 *
	 * @return true:���j���[����̑J�ځAfalse:����ȊO
	 */
	public boolean isFromMenu() {
		// �A�N�V�����G���[���b�Z�[�W���Ȃ���΁A���j���[����̑J��
		return (this.dispMode == TB001LoginModel.DISP_MODE_FROM_MENU);
	}

	/**
	 * �p�X���[�h�����؂�ԋ߂��𔻒肵�܂��B
	 *
	 * @return true:�p�X���[�h�����؂�ԋ߁Afalse:����ȊO
	 */
	public boolean isPasswordLimitNear() {
		return (this.dispMode == TB001LoginModel.DISP_MODE_PASSWORD_LIMIT_NEAR);
	}

	/**
	 * �p�X���[�h�����؂ꂩ�𔻒肵�܂��B
	 *
	 * @return true:�p�X���[�h�����؂�Afalse:����ȊO
	 */
	public boolean isPasswordLimit() {
		return (this.dispMode == TB001LoginModel.DISP_MODE_PASSWORD_LIMIT);
	}

	/**
	 * �O�����O�C������̑J�ڂ��𔻒肵�܂��B
	 *
	 * @return true:�O�����O�C���Afalse:����ȊO
	 */
	public boolean isExternalLogin() {
		return (StringUtils.isNotBlank(this.actionURL));
	}
}
