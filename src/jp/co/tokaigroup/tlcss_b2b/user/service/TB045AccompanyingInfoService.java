package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB045AccompanyingInfoModel;

/**
 * 顧客詳細付随情報サービスクラス。
 *
 * @author k003316
 * @version 1.0 2015/08/03
 */
public interface TB045AccompanyingInfoService {
	
	/**
	 * 初期表示を行います。
	 *
	 * @param model 顧客詳細付随情報モデル
	 * @return 顧客詳細付随情報モデル
	 */
	public TB045AccompanyingInfoModel getInitInfo(TB045AccompanyingInfoModel model);
	
}
