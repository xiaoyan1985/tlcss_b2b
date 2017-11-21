package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB031RequestSearchModel;

/**
 * 依頼検索サービスクラス。
 *
 * @author k002849
 * @version 1.0 2014/05/09
 */
public interface TB031RequestSearchService {
	/**
	 * 初期表示を行います。
	 *
	 * @param model 依頼検索画面モデル
	 * @return 依頼検索画面モデル
	 */
	public TB031RequestSearchModel getInitInfo(TB031RequestSearchModel model);

	/**
	 * 検索処理を実行します。
	 *
	 * @param model 依頼検索画面モデル
	 * @return 依頼検索画面モデル
	 */
	public TB031RequestSearchModel executeSearch(TB031RequestSearchModel model);
}
