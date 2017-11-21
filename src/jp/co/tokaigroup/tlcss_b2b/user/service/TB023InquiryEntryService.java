package jp.co.tokaigroup.tlcss_b2b.user.service;


import jp.co.tokaigroup.tlcss_b2b.user.model.TB023InquiryEntryModel;

/**
 * 問い合わせ登録サービスクラス。
 *
 * @author k003316
 * @version 1.0 2015/08/11
 * @version 1.1 2015/12/11 S.Nakano isDownloadbleメソッド削除、getDownloadInfoメソッド追加
 */
public interface TB023InquiryEntryService {
	
	/**
	 * 初期表示情報取得処理を行います。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel getInitInfo(TB023InquiryEntryModel model);
	
	/**
	 * 初期表示情報取得を行います。（更新画面表示用）
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel getInitInfoForUpdate(TB023InquiryEntryModel model);
	
	/**
	 * サーバーサイドエラー発生時の、初期表示情報を用意します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel parepareInitInfo(TB023InquiryEntryModel model);
	
	/**
	 * サーバーサイドエラー発生時の、初期表示情報取得を行います。（更新画面表示用）
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel parepareInitInfoForUpdate(TB023InquiryEntryModel model);
	
	/**
	 * 問い合わせ情報を新規登録します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 */
	public void insertToiawaseInfo(TB023InquiryEntryModel model);
	
	/**
	 * 問い合わせ情報を更新します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 */
	public void updateToiawaseInfo(TB023InquiryEntryModel model);
	
	/**
	 * 問い合わせ情報を削除します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 */
	public void deleteToiawaseInfo(TB023InquiryEntryModel model);
	
	/**
	 * 帳票ダウンロード情報の取得を行います。
	 * 
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel getPrintDownloadInfo(TB023InquiryEntryModel model);
	
	/**
	 * 問い合わせファイル情報を削除します。
	 * 
	 * @param model 問い合わせ登録画面モデル
	 */
	public void deleteToiawaseFileInfo(TB023InquiryEntryModel model);
	
	/**
	 * 問い合わせファイル情報のダウンロードチェックを行います。
	 * 
	 * @param model 問い合わせ登録画面モデル
	 * @return チェック結果 true：チェックOK、false：チェックNG
	 */
	public boolean isDownloableToiawaseFile(TB023InquiryEntryModel model);
}
