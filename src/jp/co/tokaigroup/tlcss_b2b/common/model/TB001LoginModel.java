package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.reception.entity.TbMUser;

/**
 * ���O�C����ʃ��f���B
 *
 * @author k002849
 * @version 2014/04/22
 * @version 1.1 2015/08/25 v145527 �ϑ���Ђh�c�ǉ��Ή�
 */
public class TB001LoginModel extends TB000CommonModel {
	/** ���O�C����� �����\�������A�N�V������ */
	public static final String ACTION_NM_LOGIN_INIT = "loginInit";
	/** �p�����[�^�� �J�ڐ�URL */
	public static final String PARAM_NM_ACTION_URL = "actionURL";

	/** ��ʃ��[�h 0:���j���[����̑J�� */
	public static final int DISP_MODE_FROM_MENU = 0;
	/** ��ʃ��[�h 1:�p�X���[�h�����؂�ԋ� */
	public static final int DISP_MODE_PASSWORD_LIMIT_NEAR = 1;
	/** ��ʃ��[�h 2:�p�X���[�h�����؂� */
	public static final int DISP_MODE_PASSWORD_LIMIT = 2;

	/** ���O�C���h�c */
	private String loginId;
	/** �p�X���[�h */
	private String passwd;
	/** �J�ڐ�URL */
	private String actionURL;

	/** ���[�U��� */
	private TbMUser userInfo;
	/** �V�X�e���萔Map */
	private Map<String, String> systemConstants;
	/** �p�X���[�h�c��L������ */
	private int validDateCount;

	/** �J�ڐ�p�����[�^ */
	private String actionParam;

	/** �A�N�Z�X�\URLMap */
	private Map<String, TbMRoleUrl> accessibleMap;

	/** �Q�ƌڋq���X�g */
	private List<TbMRefKokyaku> refKokyakuList;

	/** �ڋq�}�X�^��� */
	private RcpMKokyaku kokyakuInfo;

	/** �ϑ���Ђh�c */
	private String kaishaId;


	/**
	 * ���O�C���h�c���擾���܂��B
	 *
	 * @return ���O�C���h�c
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * ���O�C���h�c��ݒ肵�܂��B
	 *
	 * @param loginId ���O�C���h�c
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * �p�X���[�h���擾���܂��B
	 *
	 * @return �p�X���[�h
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * �p�X���[�h��ݒ肵�܂��B
	 *
	 * @param passwd �p�X���[�h
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
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
	 * ���[�U�����擾���܂��B
	 *
	 * @return ���[�U���
	 */
	public TbMUser getUserInfo() {
		return userInfo;
	}
	/**
	 * ���[�U����ݒ肵�܂��B
	 *
	 * @param userInfo ���[�U���
	 */
	public void setUserInfo(TbMUser userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * �V�X�e���萔Map���擾���܂��B
	 *
	 * @return �V�X�e���萔Map
	 */
	public Map<String, String> getSystemConstants() {
		return systemConstants;
	}
	/**
	 * �V�X�e���萔Map��ݒ肵�܂��B
	 *
	 * @param systemConstants �V�X�e���萔Map
	 */
	public void setSystemConstants(Map<String, String> systemConstants) {
		this.systemConstants = systemConstants;
	}

	/**
	 * �p�X���[�h�c��L���������擾���܂��B
	 *
	 * @return �p�X���[�h�c��L������
	 */
	public int getValidDateCount() {
		return validDateCount;
	}
	/**
	 * �p�X���[�h�c��L��������ݒ肵�܂��B
	 *
	 * @param validDateCount �p�X���[�h�c��L������
	 */
	public void setValidDateCount(int validDateCount) {
		this.validDateCount = validDateCount;
	}

	/**
	 * �J�ڐ�p�����[�^���擾���܂��B
	 *
	 * @return �J�ڐ�p�����[�^
	 */
	public String getActionParam() {
		return actionParam;
	}
	/**
	 * �J�ڐ�p�����[�^��ݒ肵�܂��B
	 *
	 * @param actionParam �J�ڐ�p�����[�^
	 */
	public void setActionParam(String actionParam) {
		this.actionParam = actionParam;
	}

	/**
	 * �A�N�Z�X�\URLMap���擾���܂��B
	 *
	 * @return �A�N�Z�X�\URLMap
	 */
	public Map<String, TbMRoleUrl> getAccessibleMap() {
		return accessibleMap;
	}
	/**
	 * �A�N�Z�X�\URLMap��ݒ肵�܂��B
	 *
	 * @param accessibleMap �A�N�Z�X�\URLMap
	 */
	public void setAccessibleMap(Map<String, TbMRoleUrl> accessibleMap) {
		this.accessibleMap = accessibleMap;
	}

	/**
	 * �Q�ƌڋq���X�g���擾���܂��B
	 *
	 * @return refKokyakuList
	 */
	public List<TbMRefKokyaku> getRefKokyakuList() {
		return refKokyakuList;
	}
	/**
	 * �Q�ƌڋq���X�g��ݒ肵�܂��B
	 *
	 * @param refKokyakuList �Z�b�g���� refKokyakuList
	 */
	public void setRefKokyakuList(List<TbMRefKokyaku> refKokyakuList) {
		this.refKokyakuList = refKokyakuList;
	}
	
	/**
	 * �ڋq�}�X�^�����擾���܂��B
	 *
	 * @return �ڋq�}�X�^���
	 */
	public RcpMKokyaku getKokyakuInfo() {
		return kokyakuInfo;
	}
	/**
	 * �ڋq�}�X�^����ݒ肵�܂��B
	 *
	 * @param kokyakuInfo �ڋq�}�X�^���
	 */
	public void setKokyakuInfo(RcpMKokyaku kokyakuInfo) {
		this.kokyakuInfo = kokyakuInfo;
	}
	
	/**
	 * �ϑ���Ђh�c���擾���܂��B
	 * 
	 * @return kaishaId
	 */
	public String getKaishaId() {
		return kaishaId;
	}

	/**
	 * �ϑ���Ђh�c��ݒ肵�܂��B
	 * 
	 * @param kaishaId �Z�b�g���� kaishaId
	 */
	public void setKaishaId(String kaishaId) {
		this.kaishaId = kaishaId;
	}
}
