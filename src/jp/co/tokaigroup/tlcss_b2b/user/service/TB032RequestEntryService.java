package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB032RequestEntryModel;

/**
 * 依頼内容詳細・作業状況登録サービスクラス。
 *
 * @author k002849
 * @version 1.0 2014/07/14
 */
public interface TB032RequestEntryService {
	/**
	 * 初期表示（詳細表示）を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return 依頼内容詳細・作業状況登録画面モデル
	 */
	public TB032RequestEntryModel getDetailInitInfo(TB032RequestEntryModel model);

	/**
	 * 初期表示（登録表示）を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return 依頼内容詳細・作業状況登録画面モデル
	 */
	public TB032RequestEntryModel getEntryInitInfo(TB032RequestEntryModel model);

	/**
	 * 初期表示（登録表示・サーバエラー時）を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return 依頼内容詳細・作業状況登録画面モデル
	 */
	public TB032RequestEntryModel getEntryPrepareInitInfo(TB032RequestEntryModel model);

	/**
	 * 画像削除処理を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 */
	public void deleteImageInfo(TB032RequestEntryModel model);

	/**
	 * 業者回答作業状況登録処理を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 */
	public void insertGyoshaSagyoJokyoInfo(TB032RequestEntryModel model);

	/**
	 * 業者回答作業状況更新処理を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 */
	public void updateGyoshaSagyoJokyoInfo(TB032RequestEntryModel model);

	/**
	 * 画像ダウンロードのチェックを行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return チェック結果 true:チェックOK、false:チェックNG
	 */
	public boolean checkDownloadInfo(TB032RequestEntryModel model);

	/**
	 * その他ファイルダウンロードのセキュリティチェックを行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return チェック結果 true:チェックOK、false:チェックNG
	 */
	public boolean checkOtherFileDownloadInfo(TB032RequestEntryModel model);
}
