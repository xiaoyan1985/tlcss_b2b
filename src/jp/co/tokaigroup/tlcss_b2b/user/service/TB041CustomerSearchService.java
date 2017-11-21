package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB041CustomerSearchModel;

/**
 * 物件・入居者検索サービスクラス。
 *
 * @author v138130
 * @version 4.0 2014/06/03
 */
public interface TB041CustomerSearchService {
	/**
	 * 初期情報取得処理を行います。
	 *
	 * @param model 物件・入居者検索画面モデル
	 * @return 物件・入居者検索画面モデル
	 */
	public TB041CustomerSearchModel getInitInfo(TB041CustomerSearchModel model);

	/**
	 * 検索処理を行います。
	 *
	 * @param model 物件・入居者検索画面モデル
	 * @return 物件・入居者検索画面モデル
	 */
	public TB041CustomerSearchModel search(TB041CustomerSearchModel model);
}
