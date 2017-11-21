package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB012ContractorSelectTestModel;

/**
 * 業者選択画面（スタブ）サービスクラス。
 *
 * @author k002849
 * @version 1.0 2014/05/29
 */
public interface TB012ContractorSelectTestService {
	/**
	 * 業者リストを取得します。
	 *
	 * @param model 業者選択画面（スタブ）モデル
	 * @return 業者選択画面（スタブ）モデル
	 */
	public TB012ContractorSelectTestModel getGyoshaList(TB012ContractorSelectTestModel model);
}
