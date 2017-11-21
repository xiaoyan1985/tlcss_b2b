package jp.co.tokaigroup.tlcss_b2b.common.action;

import jp.co.tokaigroup.si.fw.action.BaseActionSupport;
import jp.co.tokaigroup.si.fw.tags.Functions;

/**
 * アクション基底クラス。
 *
 * @author N.Akahori
 * @version 1.0 2012/09/19
 */
public abstract class TLCSSB2BBaseActionSupport extends BaseActionSupport {

	/** ログインエラー */
	public static final String LOGIN_ERROR = "loginError";
	/** パスワード変更 */
	public static final String PASSWORD_CHANGE = "passwordChange";
	/** 外部ログイン */
	public static final String EXTERNAL_LOGIN = "externalLogin";
	/** メニュー */
	public static final String MENU = "menu";
	/** アクセス拒否エラー */
	public static final String FORBIDDEN_ERROR = "forbidden";
	/** 削除完了時(独自にメソッド実行する場合) */
	public static final String DELETE = "delete";

	// このクラス（および継承クラス）のメソッドは、jspからOGNL式を使って呼び出し可能になります。

	public String decimalFormat(Number value) {
		return Functions.decimalFormat(value);
	}

}
