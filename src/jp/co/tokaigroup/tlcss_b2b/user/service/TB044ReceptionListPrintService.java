package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB044ReceptionListPrintModel;/**

 * 受付一覧印刷サービスクラス。
 *
 * @author v140546
 * @version 1.0 2014/07/08
 */
public interface TB044ReceptionListPrintService {
	/**
	 * 初期表示を行います。
	 *
	 * @param model 受付一覧印刷画面モデル
	 * @return 受付一覧印刷画面モデル
	 */
	public TB044ReceptionListPrintModel getInitInfo(TB044ReceptionListPrintModel model);

	/**
	 * 印刷処理を行います。
	 *
	 * @param model 受付一覧印刷画面モデル
	 * @return PDF出力ファイルパス
	 */
	public String createPdf(TB044ReceptionListPrintModel model);

}
