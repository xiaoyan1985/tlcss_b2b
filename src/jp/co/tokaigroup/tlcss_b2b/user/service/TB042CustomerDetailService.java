package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB042CustomerDetailModel;


/**
 * 物件・入居者詳細サービスクラス。
 *
 * @author v138130
 * @version 4.0 2014/06/06
 */
public interface TB042CustomerDetailService {

	/**
	 * 初期表示を行います。
	 *
	 * @param model 物件・入居者詳細画面モデル
	 * @return 物件・入居者詳細画面モデル
	 */
	public TB042CustomerDetailModel getInitInfo(TB042CustomerDetailModel model);
}
