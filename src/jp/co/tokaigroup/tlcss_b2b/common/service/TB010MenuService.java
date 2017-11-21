package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.Map;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010DocumentFileDownloadModel;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010MenuModel;

/**
 * メニューサービスクラス。
 *
 * @author k002849
 * @version 4.0 2014/06/05
 * @version 4.1 2015/10/28 C.Kobayashi 委託関連会社チェックの追加
 */
public interface TB010MenuService {
	/**
	 * 初期表示処理を行います。
	 *
	 * @param model メニュー画面モデル
	 * @return メニュー画面モデル
	 */
	public TB010MenuModel getInitInfo(TB010MenuModel model);

	/**
	 * アクセス可能ＵＲＬMap取得処理を行います。
	 *
	 * @param role 権限
	 * @return アクセス可能ＵＲＬMap
	 */
	public Map<String,TbMRoleUrl> getAccessUrl(String role);

	/**
	 * 顧客マスタから顧客情報を取得します。
	 * 
	 * @param selectedKokyakuId 選択した顧客情報
	 * @return 顧客マスタ情報
	 */
	public RcpMKokyaku getKokyakuInfo(String selectedKokyakuId);
	
	/**
	 * 委託会社関連チェックを行います。
	 *
	 * @param modelメニューダウンロード画面モデル
	 * @return チェック結果 true:チェックOK
	 */
	public boolean isValidDocumentDownload(TB010DocumentFileDownloadModel model);
}
