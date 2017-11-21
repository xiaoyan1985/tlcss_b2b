package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB050RequestHistoryInfoModel;

/**
 * 顧客詳細依頼履歴サービスクラス。
 *
 * @author v145527
 * @version 1.0 2015/07/30
 *
 */

public interface TB050RequestHistoryInfoService {

	/**
	 * 初期表示処理を行います。
	 *
	 * @param model 顧客詳細依頼履歴モデル
	 * @return 顧客詳細依頼履歴モデル
	 */
	public TB050RequestHistoryInfoModel getInitInfo(TB050RequestHistoryInfoModel model);

}
