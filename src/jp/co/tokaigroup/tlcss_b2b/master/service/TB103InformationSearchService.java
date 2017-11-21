package jp.co.tokaigroup.tlcss_b2b.master.service;

import jp.co.tokaigroup.tlcss_b2b.master.model.TB103InformationSearchModel;

/**
 * お知らせ検索画面サービスクラス。
 *
 * @author v138130
 * @version 4.0 2014/06/18
 */
public interface TB103InformationSearchService {

	/**
	 * 初期情報取得処理を行います。
	 *
	 * @param model お知らせ検索画面モデル
	 * @return お知らせ検索画面モデル
	 */
	public TB103InformationSearchModel getInitInfo(TB103InformationSearchModel model);

	/**
	 * 検索処理を行います。
	 *
	 * @param model お知らせ検索画面モデル
	 * @return お知らせ検索画面モデル
	 */
	public TB103InformationSearchModel search(TB103InformationSearchModel model);

	/**
	 * 削除処理を行います。
	 *
	 * @param model お知らせ検索画面モデル
	 * @return お知らせ検索画面モデル
	 */
	public void delete(TB103InformationSearchModel model);

}
