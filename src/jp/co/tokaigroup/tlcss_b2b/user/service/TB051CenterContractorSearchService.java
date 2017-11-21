package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB051CenterContractorSearchModel;

/**
 * センター業者検索サービスクラス。
 *
 * @author v145527
 * @version 1.0 2015/09/29
 */
public interface TB051CenterContractorSearchService {
	/**
	 * 初期表示を行います。
	 *
	 * @param model 顧客検索画面モデル
	 * @return 顧客検索画面モデル
	 */
	public TB051CenterContractorSearchModel getInitInfo(TB051CenterContractorSearchModel model);

	/**
	 * 業者情報を取得します。
	 *
	 * @param model 業者検索画面モデル
	 * @return 業者検索画面モデル
	 */
	public TB051CenterContractorSearchModel search(TB051CenterContractorSearchModel model);

}
