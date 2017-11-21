package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB021InquirySearchModel;
/**

 * 問い合わせ検索サービスクラス。
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 1.1 2015/12/21 H.Yamamura CSVダウンロードフラグの追加
 */
public interface TB021InquirySearchService {
	/**
	 * 初期表示を行います。
	 *
	 * @param model 問い合わせ検索画面モデル
	 * @return 問い合わせ検索画面モデル
	 */
	public TB021InquirySearchModel getInitInfo(TB021InquirySearchModel model);

	/**
	 * 検索処理を実行します。
	 *
	 * @param model 問い合わせ検索画面モデル
	 * @param csvDownloadFlg CSVダウンロードフラグ（true：CSVダウンロード）
	 * @return 問い合わせ検索画面モデル
	 */
	public TB021InquirySearchModel executeSearch(TB021InquirySearchModel model, boolean csvDownloadFlg);
}
