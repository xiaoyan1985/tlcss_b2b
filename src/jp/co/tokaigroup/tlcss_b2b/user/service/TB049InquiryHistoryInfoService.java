package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB049InquiryHistoryInfoModel;

/**
 * 顧客詳細問い合わせ履歴サービスクラス。
 *
 * @author v145527
 * @version 1.0 2015/07/24
 *
 */

public interface TB049InquiryHistoryInfoService {
	
	/**
	 * 初期表示処理を行います。
	 *
	 * @param model 顧客詳細問い合わせ履歴モデル
	 * @return 顧客詳細問い合わせ履歴モデル
	 */
	public TB049InquiryHistoryInfoModel getInitInfo(TB049InquiryHistoryInfoModel model);

}
