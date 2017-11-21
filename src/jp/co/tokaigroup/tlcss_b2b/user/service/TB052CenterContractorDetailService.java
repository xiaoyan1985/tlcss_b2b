package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB052CenterContractorDetailModel;

/**
 * センター業者詳細サービスクラス。
 *
 * @author v145527
 * @version 1.0 2015/10/08
 */
public interface TB052CenterContractorDetailService {
	
	/**
	 * 初期表示を行います。
	 *
	 * @param model センター業者詳細画面モデル
	 * @return センター業者詳細画面モデル
	 */
	public TB052CenterContractorDetailModel getInitInfo(TB052CenterContractorDetailModel model);


}
