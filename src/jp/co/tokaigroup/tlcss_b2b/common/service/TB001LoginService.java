package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB001LoginModel;

/**
 * ログインサービスクラス。
 *
 * @author k002849
 * @version 1.0 2014/04/22
 */
public interface TB001LoginService {
	/**
	 * ログイン処理を行います。
	 *
	 * @param model ログイン画面モデル
	 * @return ログイン画面モデル
	 */
	public TB001LoginModel executeLogin(TB001LoginModel model);
}
