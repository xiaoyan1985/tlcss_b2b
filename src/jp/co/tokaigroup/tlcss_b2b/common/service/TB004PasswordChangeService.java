package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB004PasswordChangeModel;

/**
 * パスワード変更サービスクラス。
 *
 * @author k002849
 * @version 1.0 2014/04/28
 */
public interface TB004PasswordChangeService {
	/**
	 * パスワード更新処理を行います。
	 *
	 * @param model パスワード変更画面モデル
	 */
	public void updatePassword(TB004PasswordChangeModel model);
}
