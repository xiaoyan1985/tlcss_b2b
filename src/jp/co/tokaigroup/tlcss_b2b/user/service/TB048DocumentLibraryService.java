package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB048DocumentLibraryModel;


/**
 * 文書ライブラリ一覧サービスクラス。
 *
 * @author C.Kobayashi
 * @version 1.0 2015/10/28
 */
public interface TB048DocumentLibraryService {

	/**
	 * 初期表示処理を行います。
	 *
	 * @return TORES文書ライブラリ一覧画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	public TB048DocumentLibraryModel getInitInfo(TB048DocumentLibraryModel model);
}
