package jp.co.tokaigroup.tlcss_b2b.common.action;

import jp.co.tokaigroup.si.fw.action.BaseActionSupport;
import jp.co.tokaigroup.si.fw.tags.Functions;

/**
 * �A�N�V�������N���X�B
 *
 * @author N.Akahori
 * @version 1.0 2012/09/19
 */
public abstract class TLCSSB2BBaseActionSupport extends BaseActionSupport {

	/** ���O�C���G���[ */
	public static final String LOGIN_ERROR = "loginError";
	/** �p�X���[�h�ύX */
	public static final String PASSWORD_CHANGE = "passwordChange";
	/** �O�����O�C�� */
	public static final String EXTERNAL_LOGIN = "externalLogin";
	/** ���j���[ */
	public static final String MENU = "menu";
	/** �A�N�Z�X���ۃG���[ */
	public static final String FORBIDDEN_ERROR = "forbidden";
	/** �폜������(�Ǝ��Ƀ��\�b�h���s����ꍇ) */
	public static final String DELETE = "delete";

	// ���̃N���X�i����ьp���N���X�j�̃��\�b�h�́Ajsp����OGNL�����g���ČĂяo���\�ɂȂ�܂��B

	public String decimalFormat(Number value) {
		return Functions.decimalFormat(value);
	}

}
