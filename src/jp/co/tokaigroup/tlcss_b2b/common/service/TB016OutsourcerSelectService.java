package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB016OutsourcerSelectModel;

/**
 * 委託会社選択サービスクラス。
 *
 * @author k002785
 * @version 1.0 2015/09/08
 */
public interface TB016OutsourcerSelectService {

	/**
	 * 検索処理を実行します。
	 *
	 * @param model 委託会社選択画面モデル
	 * @return 委託会社選択画面モデル
	 */
	public TB016OutsourcerSelectModel executeSearch(TB016OutsourcerSelectModel model);
}
