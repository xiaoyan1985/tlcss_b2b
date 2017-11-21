package jp.co.tokaigroup.tlcss_b2b.user.logic;

import java.sql.Timestamp;
import java.util.List;

/**
 * 受付一覧印刷ロジッククラス。
 *
 * @author v140546
 *
 */
public interface TB044ReceptionListPrintLogic {

	/**
	 * PDF出力を行います。
	 *
	 * @param allDataList PDF出力するデータリスト
	 * @return PDF出力URL
	 */
	public String outputPdf(List<String[]> allDataList);

	/**
	 * 受付一覧出力データを作成します。
	 *
	 * @param targetDtFrom 対象期間From
	 * @param targetDtTo 対象期間To
	 * @param seikyusakiKokyakuId 請求先顧客ＩＤ
	 * @param serviceKbn サービス区分
	 * @return  PDF出力するデータリスト
	 */
	public List<String[]> getReceiptListDataList(Timestamp targetDtFrom, Timestamp targetDtTo, String seikyusakiKokyakuId, String serviceKbn);
}
