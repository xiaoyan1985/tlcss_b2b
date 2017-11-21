package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.sql.Timestamp;
import java.util.List;

import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.reception.context.ReceptionUserContext;
import jp.co.tokaigroup.reception.dto.RC905NyudenHokokuHyoDto;
import jp.co.tokaigroup.reception.dto.RC906SagyoHokokuHyoDto;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSashidashinin;
import jp.co.tokaigroup.reception.entity.RcpTSagyohi;
import jp.co.tokaigroup.reception.kokyaku.model.RC016KokyakuKanrenEntrySearchCondition;

/**
 * 報告書印刷画面モデル。
 *
 * @author k002785
 * @version 1.0 2015/08/05
 * @version 1.1 2016/07/08 H.Hirai 複数請求先対応
 */
public class TB015ReportPrintModel {

	/** 送付元コード：TOKAI */
	public static final String SENDER_CD_TOKAI = "0";

	/** 画面名：入電報告書印刷 */
	public static final String GAMEN_NM_INCOMING_CALL_REPORT = "入電報告書";

	/** 画面名：作業報告書印刷 */
	public static final String GAMEN_NM_WORK_REPORT = "作業報告書";
	
	/** CSV名：入電報告書印刷 */
	public static final String CSV_NM_INCOMING_CALL_REPORT = "incoming_call_report_csv";
	
	/** CSV名：作業報告書印刷 */
	public static final String CSV_NM_WORK_REPORT = "work_report_csv";
	
	/** 帳票区分：入電報告書 */
	public static final String PRINT_KBN_INCOMING_CALL_REPORT = "1";
	
	/** 帳票区分：作業報告書 */
	public static final String PRINT_KBN_WORK_REPORT = "2";

	/** 問い合わせNO */
	private String toiawaseNo;

	/** 顧客ID */
	private String kokyakuId;

	/** 画面区分 */
	private String dispKbn;

	/** 問い合わせ履歴NO */
	private String toiawaseRirekiNo;

	/** 検索条件 */
	private RC016KokyakuKanrenEntrySearchCondition condition;

	/** 関連付け顧客 */
	private List<RcpMKokyaku> kanrenList;

	/** 差出人情報 */
	private RcpMSashidashinin sashidashinin;

	/** 請求先顧客情報 */
	private RcpMKokyaku seikyusakiKokyaku;

	/** 請求先顧客情報リスト */
	private List<RcpMKokyaku> seikyusakiKokyakuList;

	/** 作業費情報 */
	private RcpTSagyohi sagyohi;

	/** ラジオ選択値（関連付け一覧） */
	private String rdoKokyakuIdListRelevance;
	/** ラジオ選択値（送付元） */
	private String rdoSender;

	/** 問い合わせ更新日 */
	private Timestamp toiawaseUpdDt;
	/** 作業状況更新日 */
	private Timestamp sagyoJokyoUpdDt;

	/** ユーザコンテキスト */
	private ReceptionUserContext context;
	
	/** 検索結果(入電報告書 CSV処理用) */
	private List<RC905NyudenHokokuHyoDto> nyudenHokokushoResultList;
	
	/** 検索結果(作業報告書 CSV処理用) */
	private List<RC906SagyoHokokuHyoDto> sagyoHokokushoResultList;

	/** 送付元名称１ */
	private String senderNm1;

	/** 送付元名称２ */
	private String senderNm2;

	/** 送付元住所 */
	private String senderAddress;

	/** 送付元電話番号 */
	private String senderTelNo;

	/** 送付元FAX番号 */
	private String senderFaxNo;

	/** 帳票区分 */
	private String printKbn;
	
	/** 帳票パス */
	private String makePdfPath;
	
	/** 帳票名 */
	private String pdfNm;

	/**
	 * @return toiawaseNo
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * @param toiawaseNo セットする toiawaseNo
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * @return kokyakuId
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}

	/**
	 * @param kokyakuId セットする kokyakuId
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}

	/**
	 * @return dispKbn
	 */
	public String getDispKbn() {
		return dispKbn;
	}

	/**
	 * @param dispKbn セットする dispKbn
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}

	/**
	 * @return toiawaseRirekiNo
	 */
	public String getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}

	/**
	 * @param toiawaseRirekiNo セットする toiawaseRirekiNo
	 */
	public void setToiawaseRirekiNo(String toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}

	/**
	 * @return condition
	 */
	public RC016KokyakuKanrenEntrySearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition セットする condition
	 */
	public void setCondition(RC016KokyakuKanrenEntrySearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * @return kanrenList
	 */
	public List<RcpMKokyaku> getKanrenList() {
		return kanrenList;
	}

	/**
	 * @param kanrenList セットする kanrenList
	 */
	public void setKanrenList(List<RcpMKokyaku> kanrenList) {
		this.kanrenList = kanrenList;
	}

	/**
	 * @return sashidashinin
	 */
	public RcpMSashidashinin getSashidashinin() {
		return sashidashinin;
	}

	/**
	 * @param sashidashinin セットする sashidashinin
	 */
	public void setSashidashinin(RcpMSashidashinin sashidashinin) {
		this.sashidashinin = sashidashinin;
	}

	/**
	 * @return seikyusakiKokyaku
	 */
	public RcpMKokyaku getSeikyusakiKokyaku() {
		return seikyusakiKokyaku;
	}

	/**
	 * @param seikyusakiKokyaku セットする seikyusakiKokyaku
	 */
	public void setSeikyusakiKokyaku(RcpMKokyaku seikyusakiKokyaku) {
		this.seikyusakiKokyaku = seikyusakiKokyaku;
	}

	/**
	 * 請求先顧客情報リストを取得します。
	 * 
	 * @return 請求先顧客情報リスト
	 */
	public List<RcpMKokyaku> getSeikyusakiKokyakuList() {
		return seikyusakiKokyakuList;
	}

	/**
	 * 請求先顧客情報リストを設定します。
	 * 
	 * @param seikyusakiKokyakuList 請求先顧客情報リスト
	 */
	public void setSeikyusakiKokyakuList(List<RcpMKokyaku> seikyusakiKokyakuList) {
		this.seikyusakiKokyakuList = seikyusakiKokyakuList;
	}

	/**
	 * @return sagyohi
	 */
	public RcpTSagyohi getSagyohi() {
		return sagyohi;
	}

	/**
	 * @param sagyohi セットする sagyohi
	 */
	public void setSagyohi(RcpTSagyohi sagyohi) {
		this.sagyohi = sagyohi;
	}

	/**
	 * ラジオ選択値（関連付け一覧）を取得します。
	 *
	 * @return ラジオ選択値（関連付け一覧）
	 */
	public String getRdoKokyakuIdListRelevance() {
		return rdoKokyakuIdListRelevance;
	}
	/**
	 * ラジオ選択値（関連付け一覧）を設定します。
	 *
	 * @param rdoKokyakuIdListRelevance ラジオ選択値（関連付け一覧）
	 */
	public void setRdoKokyakuIdListRelevance(String rdoKokyakuIdListRelevance) {
		this.rdoKokyakuIdListRelevance = rdoKokyakuIdListRelevance;
	}

	/**
	 * ラジオ選択値（送付元）を取得します。
	 *
	 * @return ラジオ選択値（送付元）
	 */
	public String getRdoSender() {
		return rdoSender;
	}
	/**
	 * ラジオ選択値（送付元）を設定します。
	 *
	 * @param rdoSender ラジオ選択値（送付元）
	 */
	public void setRdoSender(String rdoSender) {
		this.rdoSender = rdoSender;
	}

	/**
	 * 問い合わせ更新日を取得します。
	 *
	 * @return 問い合わせ更新日
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}
	/**
	 * 問い合わせ更新日を設定します。
	 *
	 * @param toiawaseUpdDt 問い合わせ更新日
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}

	/**
	 * 作業状況更新日を取得します。
	 *
	 * @return 依頼更新日
	 */
	public Timestamp getSagyoJokyoUpdDt() {
		return sagyoJokyoUpdDt;
	}
	/**
	 * 作業状況更新日を設定します。
	 *
	 * @param iraiUpdDt 依頼更新日
	 */
	public void setSagyoJokyoUpdDt(Timestamp sagyoJokyoUpdDt) {
		this.sagyoJokyoUpdDt = sagyoJokyoUpdDt;
	}

	/**
	 * ユーザコンテキストを取得します。
	 *
	 * @return ユーザコンテキスト
	 */
	public ReceptionUserContext getContext() {
		return context;
	}
	/**
	 * ユーザコンテキストを設定します。
	 *
	 * @param context ユーザコンテキスト
	 */
	public void setContext(ReceptionUserContext context) {
		this.context = context;
	}
	
	/**
	 * 検索結果(入電報告書 CSV処理用)を取得します。
	 *
	 * @return 検索結果(入電報告書 CSV処理用)
	 */
	public List<RC905NyudenHokokuHyoDto> getNyudenHokokushoResultList() {
		return nyudenHokokushoResultList;
	}

	/**
	 * 検索結果(入電報告書 CSV処理用)を設定します。
	 *
	 * @param nyudenHokokushoResultList 検索結果(入電報告書 CSV処理用)
	 */
	public void setNyudenHokokushoResultList(
			List<RC905NyudenHokokuHyoDto> nyudenHokokushoResultList) {
		this.nyudenHokokushoResultList = nyudenHokokushoResultList;
	}
	
	/**
	 * 検索結果(作業報告書 CSV処理用)を取得します。
	 *
	 * @return 検索結果(作業報告書 CSV処理用)
	 */
	public List<RC906SagyoHokokuHyoDto> getSagyoHokokushoResultList() {
		return sagyoHokokushoResultList;
	}

	/**
	 * 検索結果(作業報告書 CSV処理用)を設定します。
	 *
	 * @param sagyoHokokushoResultList 検索結果(作業報告書 CSV処理用)
	 */
	public void setSagyoHokokushoResultList(
			List<RC906SagyoHokokuHyoDto> sagyoHokokushoResultList) {
		this.sagyoHokokushoResultList = sagyoHokokushoResultList;
	}

	/**
	 * 送付元名称１を取得します。
	 *
	 * @return 送付元名称１
	 */
	public String getSenderNm1() {
		return senderNm1;
	}

	/**
	 * 送付元名称１を設定します。
	 *
	 * @param senderNm1 送付元名称１
	 */
	public void setSenderNm1(String senderNm1) {
		this.senderNm1 = senderNm1;
	}

	/**
	 * 送付元名称２を取得します。
	 *
	 * @return 送付元名称２
	 */
	public String getSenderNm2() {
		return senderNm2;
	}

	/**
	 * 送付元名称２を設定します。
	 *
	 * @param senderNm2 送付元名称２
	 */
	public void setSenderNm2(String senderNm2) {
		this.senderNm2 = senderNm2;
	}

	/**
	 * 送付元住所を取得します。
	 *
	 * @return 送付元住所
	 */
	public String getSenderAddress() {
		return senderAddress;
	}

	/**
	 * 送付元住所を設定します。
	 *
	 * @param senderAddress 送付元住所
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	/**
	 * 送付元電話番号を取得します。
	 *
	 * @return 送付元電話番号
	 */
	public String getSenderTelNo() {
		return senderTelNo;
	}

	/**
	 * 送付元電話番号を設定します。
	 *
	 * @param senderTelNo 送付元電話番号
	 */
	public void setSenderTelNo(String senderTelNo) {
		this.senderTelNo = senderTelNo;
	}

	/**
	 * 送付元FAX番号を取得します。
	 *
	 * @return 送付元FAX番号
	 */
	public String getSenderFaxNo() {
		return senderFaxNo;
	}

	/**
	 * 送付元FAX番号を設定します。
	 *
	 * @param senderFaxNo 送付元FAX番号
	 */
	public void setSenderFaxNo(String senderFaxNo) {
		this.senderFaxNo = senderFaxNo;
	}

	/**
	 * 帳票区分を取得します。
	 *
	 * @return 帳票区分
	 */
	public String getPrintKbn() {
		return printKbn;
	}

	/**
	 * 帳票区分を設定します。
	 *
	 * @param printKbn 帳票区分
	 */
	public void setPrintKbn(String printKbn) {
		this.printKbn = printKbn;
	}

	/**
	 * 帳票パスを取得します。
	 *
	 * @return 帳票パス
	 */
	public String getMakePdfPath() {
		return makePdfPath;
	}

	/**
	 * 帳票パスを設定します。
	 *
	 * @param makePdfPath 帳票パス
	 */
	public void setMakePdfPath(String makePdfPath) {
		this.makePdfPath = makePdfPath;
	}
	
	/**
	 * 帳票名を取得します。
	 *
	 * @return 帳票名
	 */
	public String getPdfNm() {
		return pdfNm;
	}

	/**
	 * 帳票名を設定します。
	 *
	 * @param pdfNm 帳票名
	 */
	public void setPdfNm(String pdfNm) {
		this.pdfNm = pdfNm;
	}

	/**
	 * 問い合わせ登録画面からの遷移か判定します。
	 *
	 * @return true: 問い合わせ登録画面からの遷移、false:それ以外からの遷移
	 */
	public boolean isFromInquiryEntry() {
		return Constants.GAMEN_KBN_INQUIRY_ENTRY.equals(this.dispKbn);
	}

	/**
	 * 依頼登録画面からの遷移か判定します。
	 *
	 * @return true: 依頼登録画面からの遷移、false:それ以外からの遷移
	 */
	public boolean isFromRequestEntry() {
		return Constants.GAMEN_KBN_REQUEST_ENTRY.equals(this.dispKbn);
	}
	
	/**
	 * 帳票区分が「1：入電報告書」か判定します。
	 *
	 * @return true: 帳票区分が「1：入電報告書」の場合、false：それ以外の場合
	 */
	public boolean isPrintableIncomingCallReport() {
		return PRINT_KBN_INCOMING_CALL_REPORT.equals(this.printKbn);
	}

	/**
	 * 帳票区分が「2：作業報告書」か判定します。
	 *
	 * @return true: 帳票区分が「2：作業報告書」の場合、false：それ以外の場合
	 */
	public boolean isPrintableWorkReport() {
		return PRINT_KBN_WORK_REPORT.equals(this.printKbn);
	}

	/**
	 * @return 画面名称
	 */
	public String getGamenNm() {
		String gamenNm = "";

		if (Constants.GAMEN_KBN_INQUIRY_ENTRY.equals(dispKbn)) {
			gamenNm = GAMEN_NM_INCOMING_CALL_REPORT;
		} else if(Constants.GAMEN_KBN_REQUEST_ENTRY.equals(dispKbn)) {
			gamenNm = GAMEN_NM_WORK_REPORT;
		}

		return gamenNm;
	}

	/**
	 * 関連付け一覧で選択チェックを初期値設定する顧客ＩＤを取得します。
	 *
	 * @param idx 関連付け一覧内のインデックス
	 * @return 初期値設定する顧客ＩＤ。
	 *         また、以下の場合は空文字を返します。
	 *         ・該当行の顧客区分が「管理会社(大家含む)」以外の場合
	 *         ・関連付け一覧内に、「顧客区分：管理会社(大家含む)」の件数が０件、または、２件以上の場合
	 */
	public String getInitKanrenKokyakuId(int idx) {
		RcpMKokyaku kanrenKokyaku = this.kanrenList.get(idx);
		if (!RcpMKokyaku.KOKYAKU_KBN_FUDOSAN.equals(kanrenKokyaku.getKokyakuKbn())) {
			// 該当行の顧客区分が「顧客区分：管理会社(大家含む)」以外の場合、全てチェックOFF
			return "";
		}

		// 関連付け一覧内の「顧客区分：管理会社(大家含む)」の件数を取得
		String initKokyakuId = "";
		int targetCount = 0;
		for (RcpMKokyaku kokyaku : this.kanrenList) {
			if (RcpMKokyaku.KOKYAKU_KBN_FUDOSAN.equals(kokyaku.getKokyakuKbn())) {
				// 対象件数をカウントアップし、顧客IDをセット
				targetCount++;
				initKokyakuId = kokyaku.getKokyakuId();
			}

			if (targetCount > 1) {
				// 対象件数が２件以上になったら、全てチェックOFF
				return "";
			}
		}

		// 対象件数が１件の場合は、チェックON
		return initKokyakuId;
	}
	
	/**
	 * 帳票区分ごとのリストを取得します。
	 * 
	 * @return CSVリスト
	 */
	public List<?> getCsvList(){
		// 帳票区分が「入電報告書」の場合
		if (isPrintableIncomingCallReport()) {
			return nyudenHokokushoResultList;
		// 帳票区分が「作業報告書」の場合
		} else if (isPrintableWorkReport()) {
			return sagyoHokokushoResultList;
		} else {
			return null;
		}
	}
	
	/**
	 * CSV名称を取得します。
	 * 
	 * @return CSV名称
	 */
	public String getCsvNm() {
		// 帳票区分が「入電報告書」の場合
		if (isPrintableIncomingCallReport()) {
			return CSV_NM_INCOMING_CALL_REPORT;
		// 帳票区分が「作業報告書」の場合
		} else if (isPrintableWorkReport()) {
			return CSV_NM_WORK_REPORT;
		} else {
			return null;
		}
	}

	/**
	 * 請求先リストを表示するかを判定します。
	 * 
	 * @return true:表示する、false:表示しない
	 */
	public boolean isSeikyusakiLstDisplay() {
		if (this.seikyusakiKokyakuList != null
				&& !this.seikyusakiKokyakuList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
