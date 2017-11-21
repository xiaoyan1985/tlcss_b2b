package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB010ManagedTargetListModel;

/**
 * 管理対象一覧サービスクラス。
 *
 * @author v140546
 * @version 4.0 2014/09/25
 */
public interface TB010ManagedTargetListService {

	/**
	 * 初期処理を行います。
	 *
	 * @param model 管理対象一覧モデル
	 */
	public TB010ManagedTargetListModel getInitInfo(TB010ManagedTargetListModel model);

	/**
	 * 顧客選択処理を行います。
	 *
	 * @param model 管理対象一覧モデル
	 */
	public TB010ManagedTargetListModel selectKokyakuId(TB010ManagedTargetListModel model);
}
