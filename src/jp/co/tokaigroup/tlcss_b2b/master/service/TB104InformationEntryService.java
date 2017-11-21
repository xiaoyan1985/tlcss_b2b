package jp.co.tokaigroup.tlcss_b2b.master.service;

import jp.co.tokaigroup.tlcss_b2b.master.model.TB104InformationEntryModel;

/**
 * お知らせ登録サービスクラス。
 *
 * @author v138130
 * @version 4.0 2014/06/20
 */
public interface TB104InformationEntryService {
	/**
	 * 初期表示処理を行います。
	 *
	 * @param model お知らせ登録画面モデル
	 * @return お知らせ登録画面モデル
	 */
	public TB104InformationEntryModel getInitInfo(TB104InformationEntryModel model);

	/**
	 * お知らせ情報取得処理を行います。
	 *
	 * @param model お知らせ登録画面モデル
	 * @return お知らせ登録画面モデル
	 */
	public TB104InformationEntryModel getUpdateInitInfo(TB104InformationEntryModel model);

	/**
	 * お知らせ情報登録処理を行います。
	 *
	 * @param model お知らせ登録画面モデル
	 */
	public TB104InformationEntryModel insertInfo(TB104InformationEntryModel model);

	/**
	 * お知らせ情報更新処理を行います。
	 *
	 * @param model お知らせ登録画面モデル
	 */
	public void updateInfo(TB104InformationEntryModel model);
}
