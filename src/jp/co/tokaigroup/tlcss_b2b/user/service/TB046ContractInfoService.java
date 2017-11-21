package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB046ContractInfoModel;

/**
 * 契約情報サービスクラス。
 *
 * @author v145527
 * @version 1.0 2015/08/04
 *
 */
public interface TB046ContractInfoService {

	/**
	 * 初期表示処理を行います。
	 *
	 * @param model 契約情報モデル
	 * @return 契約情報モデル
	 */
	public TB046ContractInfoModel getInitInfo(TB046ContractInfoModel model);

}
