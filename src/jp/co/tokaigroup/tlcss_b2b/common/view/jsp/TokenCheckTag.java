package jp.co.tokaigroup.tlcss_b2b.common.view.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.components.TokenCheck;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * �g�[�N���Z�b�V�����`�F�b�N�^�O.�B
 *
 * @author k002849
 * @version 4.0 2014/06/23
 */
public class TokenCheckTag extends AbstractUITag {
	/** ��ʂh�c */
	private String displayId;

	/**
	 * �R���|�[�l���g�����擾���܂��B
	 *
	 * @param stack ��ʏ��
	 * @param req ���N�G�X�g���
	 * @param res ���X�|���X���
	 * @return �R���|�[�l���g���
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new TokenCheck(stack, req, res);
	}

	/**
	 * �p�����[�^����ݒ肵�܂��B
	 */
	protected void populateParams() {
		super.populateParams();

		TokenCheck tokenCheck = (TokenCheck) component;
		tokenCheck.setDisplayId(getDisplayId() + "_" + DateUtil.getSysDateString("yyyyMMddHHmmssSSS"));
	}

	/**
	 * ��ʂh�c���擾���܂��B
	 *
	 * @return ��ʂh�c
	 */
	public String getDisplayId() {
		return this.displayId;
	}

	/**
	 * ��ʂh�c��ݒ肵�܂��B
	 *
	 * @param displayId ��ʂh�c
	 */
	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}
}
