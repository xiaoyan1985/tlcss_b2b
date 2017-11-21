package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB012ContractorSelectModel;

/**
 * 業者選択サービスクラス。
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
public interface TB012ContractorSelectService {

	/**
	 * 検索処理を実行します。
	 *
	 * @param model 不動産･管理会社選択画面モデル
	 * @return 不動産･管理会社選択画面モデル
	 */
	public TB012ContractorSelectModel executeSearch(TB012ContractorSelectModel model);
}

