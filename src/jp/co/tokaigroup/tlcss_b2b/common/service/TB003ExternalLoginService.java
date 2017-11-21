package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB003ExternalLoginModel;

/**
 * 外部ログインサービスクラス。
 *
 * @author k002849
 * @version 1.0 2014/04/25
 */
public interface TB003ExternalLoginService {
	/**
	 * パラメータチェック処理を実行します。
	 *
	 * @param model 外部ログイン画面モデル
	 */
	public void validateParameter(TB003ExternalLoginModel model);
}
