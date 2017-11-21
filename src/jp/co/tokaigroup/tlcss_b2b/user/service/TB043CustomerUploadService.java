package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;


/**
 * 管理情報アップロードサービスクラス。
 *
 * @author v140546
 * @version 1.0 2014/08/04
 */
public interface TB043CustomerUploadService {

	/**
	 * 初期表示を行います。
	 *
	 * @param model 管理情報アップロード画面モデル
	 * @return 管理情報アップロード画面モデル
	 */
	public TB043CustomerUploadModel getInitInfo(TB043CustomerUploadModel model);

	/**
	 * 管理情報アップロードのCSV取込処理を行います。
	 *
	 * @param model 管理情報アップロード画面モデル
	 */
	public TB043CustomerUploadModel executeCsvUpload(TB043CustomerUploadModel model);

	/**
	 * アップロード履歴の削除処理を行います。
	 *
	 * @param model 管理情報アップロード画面モデル
	 */
	public TB043CustomerUploadModel deleteUploadRireki(TB043CustomerUploadModel model);
}