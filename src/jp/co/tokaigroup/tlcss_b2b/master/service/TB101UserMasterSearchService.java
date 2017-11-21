package jp.co.tokaigroup.tlcss_b2b.master.service;

import jp.co.tokaigroup.tlcss_b2b.master.model.TB101UserMasterSearchModel;

/**
 * ユーザーマスタ検索サービスクラス。
 *
 * @author k002849
 * @version 1.0 2014/05/26
 */
public interface TB101UserMasterSearchService {
	/**
	 * 初期表示処理を行います。
	 *
	 * @param model ユーザーマスタ検索画面モデル
	 * @return ユーザーマスタ検索画面モデル
	 */
	public TB101UserMasterSearchModel getInitInfo(TB101UserMasterSearchModel model);

	/**
	 * 検索処理を行います。
	 *
	 * @param model ユーザーマスタ検索画面モデル
	 * @return ユーザーマスタ検索画面モデル
	 */
	public TB101UserMasterSearchModel executeSearch(TB101UserMasterSearchModel model);
}
