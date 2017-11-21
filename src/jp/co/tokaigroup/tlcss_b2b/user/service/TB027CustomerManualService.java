package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB027CustomerManualModel;

/**
 * 顧客マニュアル一覧サービスクラス。
 *
 * @author 松葉
 * @version 1.0 2015/08/05
 */
public interface TB027CustomerManualService {
	/**
	 * 初期表示処理を行います。
	 *
	 * @param model 顧客マニュアル一覧画面モデル
	 * @return 顧客マニュアル一覧画面モデル
	 */
	public TB027CustomerManualModel getInitInfo(TB027CustomerManualModel model);

	/**
	 * マニュアルファイルダウンロードのチェックを行います。
	 *
	 * @param model 顧客マニュアル一覧画面モデル
	 * @return チェック結果 true:チェックOK、false:チェックNG
	 */
	public boolean isValidKokyakuManualInfo(TB027CustomerManualModel model);
}
