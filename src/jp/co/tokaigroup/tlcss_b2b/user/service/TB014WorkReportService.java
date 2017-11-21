package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB014WorkReportModel;

/**
 * 作業報告書印刷サービスクラス。
 *
 * @author k002849
 * @version 4.0 2014/07/22
 */
public interface TB014WorkReportService {
	/**
	 * 作業報告書のダウンロードチェックを行います。
	 *
	 * @param model 作業報告書印刷モデル
	 * @return true:チェックOK、false:チェックNG
	 */
	public boolean isOwn(TB014WorkReportModel model);
}
