package jp.co.tokaigroup.tlcss_b2b.common.view.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.components.TokenCheck;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * トークンセッションチェックタグ.。
 *
 * @author k002849
 * @version 4.0 2014/06/23
 */
public class TokenCheckTag extends AbstractUITag {
	/** 画面ＩＤ */
	private String displayId;

	/**
	 * コンポーネント情報を取得します。
	 *
	 * @param stack 画面情報
	 * @param req リクエスト情報
	 * @param res レスポンス情報
	 * @return コンポーネント情報
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new TokenCheck(stack, req, res);
	}

	/**
	 * パラメータ情報を設定します。
	 */
	protected void populateParams() {
		super.populateParams();

		TokenCheck tokenCheck = (TokenCheck) component;
		tokenCheck.setDisplayId(getDisplayId() + "_" + DateUtil.getSysDateString("yyyyMMddHHmmssSSS"));
	}

	/**
	 * 画面ＩＤを取得します。
	 *
	 * @return 画面ＩＤ
	 */
	public String getDisplayId() {
		return this.displayId;
	}

	/**
	 * 画面ＩＤを設定します。
	 *
	 * @param displayId 画面ＩＤ
	 */
	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}
}
