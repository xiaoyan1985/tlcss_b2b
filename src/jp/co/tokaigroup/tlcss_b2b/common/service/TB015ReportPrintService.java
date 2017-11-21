package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintModel;

/**
 * 報告書印刷サービスクラス。
 *
 * @author k002785
 * @version 1.0 2015/08/05
 */
public interface TB015ReportPrintService {

	/**
	 * 初期情報取得処理を行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB015ReportPrintModel getInitInfo(TB015ReportPrintModel model);
	
	/**
	 * 帳票を生成します。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB015ReportPrintModel createPdf(TB015ReportPrintModel model);
	
	/**
	 * CSV出力情報を取得します。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB015ReportPrintModel createCsvData(TB015ReportPrintModel model);
}
