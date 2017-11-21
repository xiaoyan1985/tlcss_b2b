package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB011RealEstateAgencySelectTestModel;

/**
 * 不動産・管理会社選択画面（スタブ）サービスクラス。
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
public interface TB011RealEstateAgencySelectTestService {
	/**
	 * 顧客情報リストを取得します。
	 *
	 * @param model 不動産・管理会社選択画面（スタブ）モデル
	 * @return 不動産・管理会社選択画面（スタブ）モデル
	 */
	public TB011RealEstateAgencySelectTestModel getKokyakuList(TB011RealEstateAgencySelectTestModel model);
}
