package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB022InquiryDetailModel;
/**

 * 問い合わせ詳細サービスクラス。
 *
 * @author v140546
 * @version 1.0 2014/05/27
 * @version 1.1 2015/10/23 C.Kobayashi 既読未読機能追加対応
 */
public interface TB022InquiryDetailService {
	/**
	 * 初期表示を行います。
	 *
	 * @param model 問い合わせ詳細画面モデル
	 * @return 問い合わせ詳細画面モデル
	 */
	public TB022InquiryDetailModel getInitInfo(TB022InquiryDetailModel model);

	/**
	 * 問い合わせ履歴・問い合わせ情報を更新します。
	 *
	 * @param model 問い合わせ詳細画面モデル
	 */
	public void updateInquiryHistoryDetailInfo(TB022InquiryDetailModel model);

	/**
	 * 問い合わせファイル情報のダウンロードチェックを行います。
	 *
	 * @param model 問い合わせ詳細画面モデル
	 * @return チェック結果 true:チェックOK、false:チェックNG
	 */
	public boolean canWorkReportDownload(TB022InquiryDetailModel model);

}
