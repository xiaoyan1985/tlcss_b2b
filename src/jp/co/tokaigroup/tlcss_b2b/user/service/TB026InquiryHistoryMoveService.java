package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB026InquiryHistoryMoveModel;


/**
 * 問い合わせ履歴移動画面サービスクラス。
 *
 * @author 松葉
 *
 */
public interface TB026InquiryHistoryMoveService {
	/**
	 * 初期表示処理を行います。
	 *
	 * @param model 問い合わせ履歴移動画面モデル
	 * @return 問い合わせ履歴移動画面モデル
	 */
	public TB026InquiryHistoryMoveModel getInitInfo(TB026InquiryHistoryMoveModel model);

	/**
	 * 問い合わせ履歴移動処理を行います。
	 *
	 * @param model問い合わせ履歴移動画面モデル
	 */
	public void updateInquiryHistoryMoveInfo(TB026InquiryHistoryMoveModel model);
}
