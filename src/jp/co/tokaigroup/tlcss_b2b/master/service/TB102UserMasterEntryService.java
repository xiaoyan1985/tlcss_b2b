package jp.co.tokaigroup.tlcss_b2b.master.service;

import jp.co.tokaigroup.tlcss_b2b.master.model.TB102UserMasterEntryModel;

/**
 * ユーザーマスタ登録サービスクラス。
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
public interface TB102UserMasterEntryService {
	/**
	 * 初期表示処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 * @return ユーザーマスタ登録画面モデル
	 */
	public TB102UserMasterEntryModel getInitInfo(TB102UserMasterEntryModel model);

	/**
	 * ユーザー情報取得処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 * @return ユーザーマスタ登録画面モデル
	 */
	public TB102UserMasterEntryModel getUserInfo(TB102UserMasterEntryModel model);

	/**
	 * ユーザー情報登録処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 */
	public void insertUserInfo(TB102UserMasterEntryModel model);

	/**
	 * ユーザー情報更新処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 */
	public void updateUserInfo(TB102UserMasterEntryModel model);

	/**
	 * パスワード再発行処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 */
	public void reissuePasswordInfo(TB102UserMasterEntryModel model);

	/**
	 * 参照顧客を設定します。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 */
	public TB102UserMasterEntryModel setRefKokyaku(TB102UserMasterEntryModel model);
}
