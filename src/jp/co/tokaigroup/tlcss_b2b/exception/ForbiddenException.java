package jp.co.tokaigroup.tlcss_b2b.exception;

import jp.co.tokaigroup.si.fw.exception.ApplicationException;

/**
 * セキュリティチェックエラークラス。
 * 
 * @author k002849
 * @version 1.0 2015/07/28
 */
public class ForbiddenException extends ApplicationException {
	/**
	 * コンストラクタ。
	 */
	public ForbiddenException() {
		super();
	}
	
	/**
	 * コンストラクタ。
	 *
	 * @param caulse 発生例外
	 */
	public ForbiddenException(Throwable caulse) {
		super(caulse);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param errMsg エラーメッセージ
	 */
	public ForbiddenException(String errMsg) {
		super(errMsg);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param msg 発生例外メッセージ
	 * @param caulse 発生例外
	 */
	public ForbiddenException(String msg, Throwable caulse) {
		super(msg, caulse);
	}
}
