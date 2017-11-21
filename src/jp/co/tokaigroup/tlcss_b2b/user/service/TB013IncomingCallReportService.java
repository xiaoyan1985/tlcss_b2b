package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB013IncomingCallReportModel;

/**
 * 入電報告書印刷サービスクラス。
 *
 * @author v140546
 * @version 1.0 2014/05/29
 * @version 1.1 2015/12/11 S.Nakano isOwnメソッド削除、getDownloadInfoメソッド追加
 */
public interface TB013IncomingCallReportService {

	/**
	 * ダウンロード情報の取得を行います。
	 * 
	 * @param model 入電報告書印刷モデル
	 * @return 入電報告書印刷モデル
	 */
	public TB013IncomingCallReportModel getDowloadInfo(TB013IncomingCallReportModel model);
}
