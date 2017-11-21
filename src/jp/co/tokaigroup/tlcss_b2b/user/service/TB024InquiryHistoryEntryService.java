package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB024InquiryHistoryEntryModel;

/**
 * 問い合わせ履歴登録サービスクラス。
 *
 * @author v145527
 * @version 1.0 2015/08/28
 *
 */
public interface TB024InquiryHistoryEntryService {

	/**
	 * 初期表示を行います。
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 * @return 問い合わせ履歴登録画面モデル
	 */
	public TB024InquiryHistoryEntryModel getInitInfo(TB024InquiryHistoryEntryModel model);
	
	/**
	 * 初期表示情報取得を行います。（更新画面表示用）
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB024InquiryHistoryEntryModel getInitInfoForUpdate(TB024InquiryHistoryEntryModel model);	

	/**
	 * 問い合わせ履歴情報登録を行います。
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 */
	public void insertToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model);

	/**
	 * 問い合わせ履歴情報更新を行います。
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 */
	public void updateToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model);

	/**
	 * 問い合わせ履歴情報削除を行います。
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 */
	public void deleteToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model);

}
