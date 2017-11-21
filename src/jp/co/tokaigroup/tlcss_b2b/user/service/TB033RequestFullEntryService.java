package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB033RequestFullEntryModel;

/**
 * 依頼登録サービスクラス。
 * 
 * @author S.Nakano
 * @version 1.0 2015/10/21
 */
public interface TB033RequestFullEntryService {
	/**
	 * 初期表示処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	public TB033RequestFullEntryModel getInitInfo(TB033RequestFullEntryModel model);
	
	/**
	 * サーバーサイドエラー発生時の、初期表示処理を行います。
	 *
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	public TB033RequestFullEntryModel parepareInitInfo(TB033RequestFullEntryModel model);
	
	/**
	 * 作業依頼情報取得処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	public TB033RequestFullEntryModel getSagyoIraiInfo(TB033RequestFullEntryModel model);
	
	/**
	 * サーバーサイドエラー発生時の、更新初期表示処理を行います。
	 *
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	public TB033RequestFullEntryModel parepareInitInfoForUpdate(TB033RequestFullEntryModel model);
	
	/**
	 * 作業依頼情報登録処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	public void insertSagyoIraiInfo(TB033RequestFullEntryModel model);
	
	/**
	 * 作業依頼情報更新処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	public void updateSagyoIraiInfo(TB033RequestFullEntryModel model);
	
	/**
	 * 作業依頼情報削除処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	public void deleteSagyoIraiInfo(TB033RequestFullEntryModel model);
	
	/**
	 * 業者依頼メール送信処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	public void sendGyoshaIraiMail(TB033RequestFullEntryModel model);
	
	/**
	 * 作業状況画像ファイル削除処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	public void deleteSagyoJokyoImageFile(TB033RequestFullEntryModel model);
	
	/**
	 * その他ファイル削除処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	public void deleteOtherFile(TB033RequestFullEntryModel model);
	
	/**
	 * 画像ダウンロード処理の妥当性チェックを行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	public void validateImageFileDownlod(TB033RequestFullEntryModel model);
	
	/**
	 * PDF作成処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	public TB033RequestFullEntryModel createPdf(TB033RequestFullEntryModel model);
}
