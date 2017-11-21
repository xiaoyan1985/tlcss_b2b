package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB033RequestEntryTestModel;

public interface TB033RequestEntryTestService {

	/**
	 * 初期表示を行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB033RequestEntryTestModel getInitInfo(TB033RequestEntryTestModel model);


	/**
	 * 依頼情報取得処理を行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB033RequestEntryTestModel getIraiInfo(TB033RequestEntryTestModel model);

}
