package jp.co.tokaigroup.tlcss_b2b.user.logic;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;

/**

 * 顧客基本情報詳細ロジッククラス
 *
 * @author v140546
 * @version 1.0 2014/05/28
 */
public interface TB040CustomerCommonInfoLogic {


	/**
	 * 顧客基本情報の取得を行います。
	 *
	 * @param kokyakuId 顧客ID
	 * @return 顧客マスタ
	 */
	public RcpMKokyaku getKokyakuInfo(String kokyakuId);

}
