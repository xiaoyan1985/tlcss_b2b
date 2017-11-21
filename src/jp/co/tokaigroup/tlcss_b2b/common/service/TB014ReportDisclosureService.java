package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB014ReportDisclosureModel;

/**
 * 報告書公開設定サービスクラス。
 *
 * @author k002785
 * @version 1.0 2015/08/11
 */
public interface TB014ReportDisclosureService {

	/**
	 * 初期情報取得処理を行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB014ReportDisclosureModel getInitInfo(TB014ReportDisclosureModel model);
	
	/**
	 * 公開処理を行います。
	 * 
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB014ReportDisclosureModel discloseReport(TB014ReportDisclosureModel model);
	
	/**
	 * 帳票ダウンロード処理を行います。
	 * 
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB014ReportDisclosureModel downloadReport(TB014ReportDisclosureModel model);
}
