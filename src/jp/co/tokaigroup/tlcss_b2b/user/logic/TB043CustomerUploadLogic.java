package jp.co.tokaigroup.tlcss_b2b.user.logic;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;


/**
 * 管理情報アップロードロジッククラス。
 *
 * @author v140546
 *
 */
public interface TB043CustomerUploadLogic {

	/**
	 * 管理情報アップロードのCSV取込処理を行います。
	 *
	 * @param TB043CustomerUploadModel
	 * @return TB043CustomerUploadModel
	 */
	public TB043CustomerUploadModel executeCsvImport(TB043CustomerUploadModel model) ;


	/**
	 * アップロード履歴テーブル登録処理、サーバーへのファイルコピーを行います。
	 *
	 * @param TB043CustomerUploadModel
	 */
	public void insertTbTUploadRirekiAndCopyFile(TB043CustomerUploadModel model);

}