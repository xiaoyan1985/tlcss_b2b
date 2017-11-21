package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB029InquiryKbnManualModel;

/**
 * 問い合わせ区分マニュアル一覧サービスクラス。
 *
 * @author H.Yamamura
 * @version 1.0 2015/11/20
 */
public interface TB029InquiryKbnManualService {

	/**
	 * ダウンロードするファイル名を取得します。
	 *
	 * @param model 問い合わせ区分マニュアル一覧画面モデル
	 * @return ファイル名
	 */
	public String getFileNm(TB029InquiryKbnManualModel model);
}
