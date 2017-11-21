package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB025CustomerIdChangeModel;

/**
 * 顧客ＩＤ変更画面サービスクラス。
 *
 * @author k002785
 * @version 1.0 2015/08/19
 */
public interface TB025CustomerIdChangeService {

	/**
	 * 初期表示処理を行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB025CustomerIdChangeModel getInitInfo(TB025CustomerIdChangeModel model);

	/**
	 * 問い合わせ顧客更新処理を行います。
	 *
	 * @param model 画面モデル
	 */
	public void updateToiawaseKokyakuInfo(TB025CustomerIdChangeModel model);
}
