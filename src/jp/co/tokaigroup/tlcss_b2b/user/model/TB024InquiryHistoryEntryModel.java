package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseManual;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;

/**
 * 問い合わせ履歴登録画面モデル。
 *
 * @author v145527
 * @version 1.0 2015/08/28
 * @version 1.1 2016/07/13 H.Yamamura 問い合わせ検索画面の文字化け対策
 *
 */
public class TB024InquiryHistoryEntryModel extends TB040CustomerCommonInfoModel{
	/** 画面名 */
	public static final String GAMEN_NM = "問い合わせ履歴登録";

	/** 問い合わせ情報 */
	private RcpTToiawase toiawaseInfo;
	/** 問い合わせ履歴情報 */
	private RcpTToiawaseRireki toiawaseRirekiInfo;
	/** 最終更新者情報 */
	private NatosMPassword lastUpdInfo;
	/** ID無し顧客情報 */
	private RcpTKokyakuWithNoId kokyakuInfoWithoutId;
	
	/** 問い合わせ検索条件 */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();
	
	/** 検索条件hidden出力除外項目 */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** 問い合わせ更新日 */
	private Timestamp toiawaseUpdDt;
	
	/** 問い合わせNo */
	private String toiawaseNo;
	/** 問い合わせ履歴No */
	private BigDecimal toiawaseRirekiNo;
	/** 公開メール送信履歴情報取得用No */
	private String mailRirekiNo;
	
	/** 更新エラーフラグ */
	private boolean updateError;
	/** 初期処理エラーフラグ */
	private boolean initError;
	/** 削除完了フラグ */
	private boolean isDeleteCompleted = false;
	
	/** 問い合わせ履歴公開フラグ(変更前) */
	private String beforeToiawaseRirekiKokaiFlg;
	
	/** 問い合わせマニュアル情報 */
	private RcpMToiawaseManual toiawaseManual;
	/** 公開メール送信履歴情報 */
	private RcpTMailRireki mailRireki;

	/** ユーザ情報 */
	private TLCSSB2BUserContext userContext;
	/** 対象顧客ＩＤ */
	private String targetKokyakuId;
	
	/** ユーザマスタからのユーザ情報 */
	private TbMUser userInfo;

	/** エンコード済み住所１ */
	private String encodedJusho1;
	/** エンコード済み住所２ */
	private String encodedJusho2;
	/** エンコード済み住所３ */
	private String encodedJusho3;
	/** エンコード済み住所４ */
	private String encodedJusho4;
	/** エンコード済み住所５ */
	private String encodedJusho5;
	/** エンコード済み部屋番号 */
	private String encodedRoomNo;
	/** エンコード済みカナ氏名１ */
	private String encodedKanaNm1;
	/** エンコード済みカナ氏名２ */
	private String encodedKanaNm2;
	/** エンコード済み漢字氏名１ */
	private String encodedKanjiNm1;
	/** エンコード済み漢字氏名２ */
	private String encodedKanjiNm2;
	
	// リスト情報
	/** 依頼者区分リスト（顧客基本） */
	private List<RcpMComCd> kokyakuIraishaKbnList;
	/** 依頼者区分リスト（問い合わせ） */
	private List<RcpMComCd> toiawaseIraishaKbnList;
	/** 依頼者フラグリスト */
	private List<RcpMComCd> iraishaFlgList;
	/** 問い合わせ区分１リスト */
	private List<RcpMToiawaseKbn1> toiawaseKbn1List;
	/** 問い合わせ区分２リスト */
	private List<RcpMToiawaseKbn2> toiawaseKbn2List;
	/** 問い合わせ区分３リスト */
	private List<RcpMToiawaseKbn3> toiawaseKbn3List;
	/** 問い合わせ区分４リスト */
	private List<RcpMToiawaseKbn4> toiawaseKbn4List;
	/** 担当者リスト */
	private List<NatosMPassword> tantoshaList;
	/** 状況区分リスト */
	private List<RcpMJokyoKbn> jokyoKbnList;
	/** 依頼有無リスト */
	private List<RcpMComCd> iraiUmuList;
	/** 受付形態リスト */
	private List<RcpMComCd> uketsukeKeitaiKbnList;
	

	/**
	 * 問い合わせ情報を取得します。
	 *
	 * @return 問い合わせ情報
	 */
	public RcpTToiawase getToiawaseInfo() {
		return toiawaseInfo;
	}
	/**
	 * 問い合わせ情報を設定します。
	 *
	 * @param toiawaseInfo 問い合わせ情報
	 */
	public void setToiawaseInfo(RcpTToiawase toiawaseInfo) {
		this.toiawaseInfo = toiawaseInfo;
	}

	/**
	 * 問い合わせ履歴情報を取得します。
	 *
	 * @return 問い合わせ履歴情報
	 */
	public RcpTToiawaseRireki getToiawaseRirekiInfo() {
		return toiawaseRirekiInfo;
	}
	/**
	 * 問い合わせ履歴情報を設定します。
	 *
	 * @param toiawaseRireki 問い合わせ履歴情報
	 */
	public void setToiawaseRirekiInfo(RcpTToiawaseRireki toiawaseRirekiInfo) {
		this.toiawaseRirekiInfo = toiawaseRirekiInfo;
	}

	/**
	 * 最終更新者情報を取得します。
	 *
	 * @return 最終更新者情報
	 */
	public NatosMPassword getLastUpdInfo() {
		return lastUpdInfo;
	}
	/**
	 * 最終更新者情報を設定します。
	 *
	 * @param lastUpdInfo 最終更新者情報
	 */
	public void setLastUpdInfo(NatosMPassword lastUpdInfo) {
		this.lastUpdInfo = lastUpdInfo;
	}

	/**
	 * 問い合わせ検索条件を取得します。
	 * 
	 * @return condition 問い合わせ検索条件
	 */
	public RC031ToiawaseSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 問い合わせ検索条件を設定します。
	 * 
	 * @param condition セットする 問い合わせ検索条件
	 */
	public void setCondition(RC031ToiawaseSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * hidden出力除外項目をカンマ区切りで取得します。
	 *
	 * @return hidden出力除外項目（カンマ区切り）
	 */
	public String getExcludeField() {
		return StringUtils.join(EXCLUDE_FIELD_NAME, ",");
	}
	
	/**
	 * 問い合わせ更新日を取得します。
	 * 
	 * @return toiawaseUpdDt 問い合わせ更新日
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}
	/**
	 * 問い合わせ更新日を設定します。
	 * 
	 * @param toiawaseUpdDt セットする 問い合わせ更新日
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}

	/**
	 * 問い合わせNoを取得します。
	 *
	 * @return 問い合わせNo
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}
	/**
	 * 問い合わせNoを設定します。
	 *
	 * @param toiawaseNo 問い合わせNo
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * 問い合わせ履歴Noを取得します。
	 *
	 * @return 問い合わせ履歴No
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}
	/**
	 * 問い合わせ履歴Noを設定します。
	 *
	 * @param toiawaseRirekiNo 問い合わせ履歴情報
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}
	
	/**
	 * メール送信履歴情報取得用Noを取得します。
	 *
	 * @return メール送信履歴情報取得用No
	 */
	public String getMailRirekiNo() {
		return mailRirekiNo;
	}
	/**
	 * メール送信履歴情報取得用Noを設定します。
	 *
	 * @param mailRirekiNo メール送信履歴情報
	 */
	public void setMailRirekiNo(String mailRirekiNo) {
		this.mailRirekiNo = mailRirekiNo;
	}
	
	/**
	 * ID無し顧客情報を取得します。
	 * 
	 * @return kokyakuInfoWithoutId ID無し顧客情報
	 */
	public RcpTKokyakuWithNoId getKokyakuInfoWithoutId() {
		return kokyakuInfoWithoutId;
	}
	/**
	 * ID無し顧客情報を設定します。
	 * 
	 * @param kokyakuInfoWithoutId セットする ID無し顧客情報
	 */
	public void setKokyakuInfoWithoutId(RcpTKokyakuWithNoId kokyakuInfoWithoutId) {
		this.kokyakuInfoWithoutId = kokyakuInfoWithoutId;
	}

	/**
	 * 更新エラーフラグを取得します。
	 *
	 * @return 更新エラーフラグ
	 */
	public boolean isUpdateError() {
		return updateError;
	}
	/**
	 * 更新エラーフラグを設定します。
	 *
	 * @param updateError 更新エラーフラグ
	 */
	public void setUpdateError(boolean updateError) {
		this.updateError = updateError;
	}

	/**
	 * 初期処理エラーフラグを取得します。
	 *
	 * @return 初期処理エラーフラグ
	 */
	public boolean isInitError() {
		return initError;
	}
	/**
	 * 初期処理エラーフラグを設定します。
	 *
	 * @param initError 初期処理エラーフラグ
	 */
	public void setInitError(boolean initError) {
		this.initError = initError;
	}
	
	/**
	 * 削除完了フラグを取得します。
	 * 
	 * @return isDeleteCompleted 削除完了フラグ
	 */
	public boolean isDeleteCompleted() {
		return isDeleteCompleted;
	}
	/**
	 * 削除完了フラグを設定します。
	 * 
	 * @param isDeleteCompleted セットする 削除完了フラグ
	 */
	public void setDeleteCompleted(boolean isDeleteCompleted) {
		this.isDeleteCompleted = isDeleteCompleted;
	}
	
	/**
	 * 問い合わせ履歴公開フラグ(変更前)を取得します。
	 *
	 * @return 問い合わせ履歴公開フラグ(変更前)
	 */
	public String getBeforeToiawaseRirekiKokaiFlg() {
		return beforeToiawaseRirekiKokaiFlg;
	}
	/**
	 * 問い合わせ履歴公開フラグ(変更前)を設定します。
	 *
	 * @param beforeToiawaseRirekiKokaiFlg 問い合わせ履歴公開フラグ(変更前)
	 */
	public void setBeforeToiawaseRirekiKokaiFlg(String beforeToiawaseRirekiKokaiFlg) {
		this.beforeToiawaseRirekiKokaiFlg = beforeToiawaseRirekiKokaiFlg;
	}
	
	/**
	 * 問い合わせマニュアル情報を取得します。
	 *
	 * @return 問い合わせマニュアル情報
	 */
	public RcpMToiawaseManual getToiawaseManual() {
		return toiawaseManual;
	}
	/**
	 * 問い合わせマニュアル情報を設定します。
	 *
	 * @param toiawaseManual 問い合わせマニュアル情報
	 */
	public void setToiawaseManual(RcpMToiawaseManual toiawaseManual) {
		this.toiawaseManual = toiawaseManual;
	}

	/**
	 * 公開メール送信履歴情報を取得します。
	 *
	 * @return 公開メール送信履歴情報
	 */
	public RcpTMailRireki getMailRireki() {
		return mailRireki;
	}
	/**
	 * 公開メール送信履歴情報を設定します。
	 *
	 * @param mailRireki 公開メール送信履歴情報
	 */
	public void setMailRireki(RcpTMailRireki mailRireki) {
		this.mailRireki = mailRireki;
	}

	/**
	 * ユーザ情報を取得します。
	 *
	 * @return ユーザ情報
	 */
	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}
	/**
	 * ユーザ情報を設定します。
	 *
	 * @param userContext ユーザ情報
	 */
	public void TLCSSB2BUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * 対象顧客IDを取得します。
	 * 
	 * @return targetKokyakuId 対象顧客ID
	 */
	public String getTargetKokyakuId() {
		return targetKokyakuId;
	}
	/**
	 * 対象顧客IDを設定します。
	 * 
	 * @param targetKokyakuId セットする 対象顧客ID
	 */
	public void setTargetKokyakuId(String targetKokyakuId) {
		this.targetKokyakuId = targetKokyakuId;
	}
	
	/**
	 * ユーザマスタからユーザ情報を取得します。
	 * 
	 * @return userInfo ユーザ情報
	 */
	public TbMUser getUserInfo() {
		return userInfo;
	}
	/**
	 * ユーザマスタからのユーザ情報を設定します。
	 * 
	 * @param userInfo セットする 対象顧客ID
	 */
	public void setUserInfo(TbMUser userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * エンコード済み住所１を取得します。
	 *
	 * @return エンコード済み住所１
	 */
	public String getEncodedJusho1() {
		return encodedJusho1;
	}
	/**
	 * エンコード済み住所１を設定します。
	 *
	 * @param encodedJusho1 エンコード済み住所１
	 */
	public void setEncodedJusho1(String encodedJusho1) {
		this.encodedJusho1 = createEncodeString(encodedJusho1);
	}

	/**
	 * エンコード済み住所２を取得します。
	 *
	 * @return エンコード済み住所２
	 */
	public String getEncodedJusho2() {
		return encodedJusho2;
	}
	/**
	 * エンコード済み住所２を設定します。
	 *
	 * @param encodedJusho2 エンコード済み住所２
	 */
	public void setEncodedJusho2(String encodedJusho2) {
		this.encodedJusho2 = createEncodeString(encodedJusho2);
	}

	/**
	 * エンコード済み住所３を取得します。
	 *
	 * @return エンコード済み住所３
	 */
	public String getEncodedJusho3() {
		return encodedJusho3;
	}
	/**
	 * エンコード済み住所３を設定します。
	 *
	 * @param encodedJusho3 エンコード済み住所３
	 */
	public void setEncodedJusho3(String encodedJusho3) {
		this.encodedJusho3 = createEncodeString(encodedJusho3);
	}

	/**
	 * エンコード済み住所４を取得します。
	 *
	 * @return エンコード済み住所４
	 */
	public String getEncodedJusho4() {
		return encodedJusho4;
	}
	/**
	 * エンコード済み住所４を設定します。
	 *
	 * @param encodedJusho4 エンコード済み住所４
	 */
	public void setEncodedJusho4(String encodedJusho4) {
		this.encodedJusho4 = createEncodeString(encodedJusho4);
	}

	/**
	 * エンコード済み住所５を取得します。
	 *
	 * @return エンコード済み住所５
	 */
	public String getEncodedJusho5() {
		return encodedJusho5;
	}
	/**
	 * エンコード済み住所５を設定します。
	 *
	 * @param encodedJusho5 エンコード済み住所５
	 */
	public void setEncodedJusho5(String encodedJusho5) {
		this.encodedJusho5 = createEncodeString(encodedJusho5);
	}

	/**
	 * エンコード済みカナ氏名１を取得します。
	 *
	 * @return エンコード済みカナ氏名１
	 */
	public String getEncodedKanaNm1() {
		return encodedKanaNm1;
	}
	/**
	 * エンコード済みカナ氏名１を設定します。
	 *
	 * @param encodedKanaNm1 エンコード済みカナ氏名１
	 */
	public void setEncodedKanaNm1(String encodedKanaNm1) {
		this.encodedKanaNm1 = createEncodeString(encodedKanaNm1);
	}

	/**
	 * エンコード済みカナ氏名２を取得します。
	 *
	 * @return エンコード済みカナ氏名２
	 */
	public String getEncodedKanaNm2() {
		return encodedKanaNm2;
	}
	/**
	 * エンコード済みカナ氏名２を設定します。
	 *
	 * @param encodedKanaNm2 エンコード済みカナ氏名２
	 */
	public void setEncodedKanaNm2(String encodedKanaNm2) {
		this.encodedKanaNm2 = createEncodeString(encodedKanaNm2);
	}

	/**
	 * エンコード済み漢字氏名１を取得します。
	 *
	 * @return エンコード済み漢字氏名１
	 */
	public String getEncodedKanjiNm1() {
		return encodedKanjiNm1;
	}
	/**
	 * エンコード済み漢字氏名１を設定します。
	 *
	 * @param encodedKanjiNm1 エンコード済み漢字氏名１
	 */
	public void setEncodedKanjiNm1(String encodedKanjiNm1) {
		this.encodedKanjiNm1 = createEncodeString(encodedKanjiNm1);
	}

	/**
	 * エンコード済み漢字氏名２を取得します。
	 *
	 * @return エンコード済み漢字氏名２
	 */
	public String getEncodedKanjiNm2() {
		return encodedKanjiNm2;
	}
	/**
	 * エンコード済み漢字氏名２を設定します。
	 *
	 * @param encodedKanjiNm2 エンコード済み漢字氏名２
	 */
	public void setEncodedKanjiNm2(String encodedKanjiNm2) {
		this.encodedKanjiNm2 = createEncodeString(encodedKanjiNm2);
	}

	/**
	 * エンコード済み部屋番号を取得します。
	 * @return エンコード済み部屋番号
	 */
	public String getEncodedRoomNo() {
		return encodedRoomNo;
	}

	/**
	 * エンコード済み部屋番号を設定します。
	 * @param encodedRoomNo エンコード済み部屋番号
	 */
	public void setEncodedRoomNo(String encodedRoomNo) {
		this.encodedRoomNo = createEncodeString(encodedRoomNo);
	}
	
	/**
	 * 依頼者区分リスト（顧客基本）を取得します。
	 * 
	 * @return kokyakuIraishaKbnList 依頼者区分リスト（顧客基本）
	 */
	public List<RcpMComCd> getKokyakuIraishaKbnList() {
		return kokyakuIraishaKbnList;
	}
	/**
	 * 依頼者区分リスト（顧客基本）を設定します。
	 * 
	 * @param kokyakuIraishaKbnList セットする 依頼者区分リスト（顧客基本）
	 */
	public void setKokyakuIraishaKbnList(List<RcpMComCd> kokyakuIraishaKbnList) {
		this.kokyakuIraishaKbnList = kokyakuIraishaKbnList;
	}
	/**
	 * 依頼者区分リスト（問い合わせ）を取得します。
	 * 
	 * @return toiawaseIraishaKbnList 依頼者区分リスト（問い合わせ）
	 */
	public List<RcpMComCd> getToiawaseIraishaKbnList() {
		return toiawaseIraishaKbnList;
	}

	/**
	 * 依頼者区分リスト（問い合わせ）を設定します。
	 * 
	 * @param toiawaseIraishaKbnList セットする 依頼者区分リスト（問い合わせ）
	 */
	public void setToiawaseIraishaKbnList(List<RcpMComCd> toiawaseIraishaKbnList) {
		this.toiawaseIraishaKbnList = toiawaseIraishaKbnList;
	}

	/**
	 * 依頼者フラグリストを取得します。
	 * 
	 * @return iraishaFlgList 依頼者フラグリスト
	 */
	public List<RcpMComCd> getIraishaFlgList() {
		return iraishaFlgList;
	}
	/**
	 * 依頼者フラグリストを設定します。
	 * 
	 * @param iraishaFlgList セットする 依頼者フラグリスト
	 */
	public void setIraishaFlgList(List<RcpMComCd> iraishaFlgList) {
		this.iraishaFlgList = iraishaFlgList;
	}

	
	/**
	 * 問い合わせ区分１リストを取得します。
	 *
	 * @return 問い合わせ区分１リスト
	 */
	public List<RcpMToiawaseKbn1> getToiawaseKbn1List() {
		return toiawaseKbn1List;
	}
	/**
	 * 問い合わせ区分１リストを設定します。
	 *
	 * @param toiawaseKbn1List 問い合わせ区分１リスト
	 */
	public void setToiawaseKbn1List(List<RcpMToiawaseKbn1> toiawaseKbn1List) {
		this.toiawaseKbn1List = toiawaseKbn1List;
	}

	/**
	 * 問い合わせ区分２リストを取得します。
	 *
	 * @return 問い合わせ区分２リスト
	 */
	public List<RcpMToiawaseKbn2> getToiawaseKbn2List() {
		return toiawaseKbn2List;
	}
	/**
	 * 問い合わせ区分２リストを設定します。
	 *
	 * @param toiawaseKbn2List 問い合わせ区分２リスト
	 */
	public void setToiawaseKbn2List(List<RcpMToiawaseKbn2> toiawaseKbn2List) {
		this.toiawaseKbn2List = toiawaseKbn2List;
	}

	/**
	 * 問い合わせ区分３リストを取得します。
	 *
	 * @return 問い合わせ区分３リスト
	 */
	public List<RcpMToiawaseKbn3> getToiawaseKbn3List() {
		return toiawaseKbn3List;
	}
	/**
	 * 問い合わせ区分３リストを設定します。
	 *
	 * @param toiawaseKbn3List 問い合わせ区分３リスト
	 */
	public void setToiawaseKbn3List(List<RcpMToiawaseKbn3> toiawaseKbn3List) {
		this.toiawaseKbn3List = toiawaseKbn3List;
	}

	/**
	 * 問い合わせ区分４リストを取得します。
	 *
	 * @return 問い合わせ区分４リスト
	 */
	public List<RcpMToiawaseKbn4> getToiawaseKbn4List() {
		return toiawaseKbn4List;
	}
	/**
	 * 問い合わせ区分４リストを設定します。
	 *
	 * @param toiawaseKbn4List 問い合わせ区分４リスト
	 */
	public void setToiawaseKbn4List(List<RcpMToiawaseKbn4> toiawaseKbn4List) {
		this.toiawaseKbn4List = toiawaseKbn4List;
	}

	/**
	 * 担当者リストを取得します。
	 *
	 * @return 担当者リスト
	 */
	public List<NatosMPassword> getTantoshaList() {
		return tantoshaList;
	}
	/**
	 * 担当者リストを設定します。
	 *
	 * @param tantoshaList 担当者リスト
	 */
	public void setTantoshaList(List<NatosMPassword> tantoshaList) {
		this.tantoshaList = tantoshaList;
	}

	/**
	 * 状況区分リストを取得します。
	 *
	 * @return 状況区分リスト
	 */
	public List<RcpMJokyoKbn> getJokyoKbnList() {
		return jokyoKbnList;
	}
	/**
	 * 状況区分リストを設定します。
	 *
	 * @param jokyoKbnList 状況区分リスト
	 */
	public void setJokyoKbnList(List<RcpMJokyoKbn> jokyoKbnList) {
		this.jokyoKbnList = jokyoKbnList;
	}

	/**
	 * 依頼有無リストを取得します。
	 * 
	 * @return iraiUmuList 依頼有無リスト
	 */
	public List<RcpMComCd> getIraiUmuList() {
		return iraiUmuList;
	}
	/**
	 * 依頼有無リストを設定します。
	 * 
	 * @param iraiUmuList セットする 依頼有無リスト
	 */
	public void setIraiUmuList(List<RcpMComCd> iraiUmuList) {
		this.iraiUmuList = iraiUmuList;
	}

	/**
	 * 受付形態リストを取得します。
	 * 
	 * @return uketsukeKeitaiKbnList 受付形態リスト
	 */
	public List<RcpMComCd> getUketsukeKeitaiKbnList() {
		return uketsukeKeitaiKbnList;
	}
	/**
	 * 受付形態リストを設定します。
	 * 
	 * @param uketsukeKeitaiKbnList セットする 受付形態リスト
	 */
	public void setUketsukeKeitaiKbnList(List<RcpMComCd> uketsukeKeitaiKbnList) {
		this.uketsukeKeitaiKbnList = uketsukeKeitaiKbnList;
	}

	/**
	 * 報告書に印字するに含むチェックがチェックされているかを判定します。
	 *
	 * @return true:チェックされている、false:チェックされていない
	 */
	public boolean isHoukokuPrintFlgChecked() {
		if (this.toiawaseRirekiInfo == null) {
			// デフォルトは、チェックON
			return true;
		}

		if (StringUtils.isBlank(this.toiawaseRirekiInfo.getHoukokuPrintFlg())) {
			// デフォルトは、チェックON
			return true;
		}

		return RcpTToiawaseRireki.HOUKOKU_PRINT_FLG_ON.equals(this.toiawaseRirekiInfo.getHoukokuPrintFlg());
	}

	/**
	 * 削除ボタンが表示するか判定します。
	 *
	 * @return true:表示、false:非表示
	 */
	public boolean isDeleteButtonVisible() {
		if (this.toiawaseRirekiInfo == null || this.toiawaseRirekiInfo.getToiawaseRirekiNo() == null) {
			// 問い合わせ履歴情報がなければ、削除不可
			return false;
		}
		
		if (!isUpdate()) {
			// 更新モードでなければ、削除不可
			return false;
		}

		if (this.userContext == null) {
			// ユーザ情報が取得できなれば、削除不可
			return false;
		}

		if (!(this.userContext instanceof TLCSSB2BUserContext)) {
			// TORESのユーザ情報でなければ、削除不可
			return false;
		}

		if (!this.userContext.isAdministrativeInhouse() && !this.userContext.isOutsourcerSv()) {
			// セッションの権限がＴＯＫＡＩ管理者、委託会社ＳＶでなければ、削除不可
			return false;
		}

		// 問い合わせ履歴ＮＯ＝１以外の履歴のみ削除可能
		return this.toiawaseRirekiInfo.getToiawaseRirekiNo().intValue() != 1;
	}

	/**
	 * 問い合わせ履歴公開フラグに含むチェックがチェックされているかを判定します。
	 *
	 * @return true:チェックされている、false:チェックされていない
	 */
	public boolean isToiawaseRirekiKokaiFlgChecked() {
		if (this.toiawaseRirekiInfo == null) {
			// デフォルトは、チェックON
			return false;
		}

		if (StringUtils.isBlank(this.toiawaseRirekiInfo.getToiawaseRirekiKokaiFlg())) {
			// デフォルトは、チェックON
			return false;
		}

		return RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_KOKAI.equals(this.toiawaseRirekiInfo.getToiawaseRirekiKokaiFlg());
	}

	/**
	 * ID無し顧客かを判定します。
	 *
	 * @return true:ID無し顧客である、false:ID有りの顧客である
	 */
	public boolean isKokyakuWithNoIdInfoDisplay() {
		return StringUtils.isBlank(this.toiawaseInfo.getKokyakuId());
	}
	
	/**
	 * 最終更新者を判定します。
	 * 
	 * @return 最終更新者
	 */
	public String getLastUpdateName() {
		
		if (this.toiawaseRirekiInfo == null) {
			return "";
		}
		
		// 問い合わせテーブル.最終更新者名が設定されていない場合は、ＩＤから変換したものを使用する。
		if (StringUtils.isNotBlank(this.toiawaseRirekiInfo.getLastUpdNm())) {
			return this.toiawaseRirekiInfo.getLastUpdNm();
		} else {
			return this.toiawaseRirekiInfo.getLastUpdIdToNm();
		}
	}
	
	/**
	 * 担当者名を表示するか判定します。
	 * 
	 * @return true:表示する
	 */
	public boolean isTantoshaNmVisible(){
		// 問い合わせ履歴情報が存在しない場合は担当者名を表示しない
		if (this.toiawaseRirekiInfo == null) {
			return false;
		}

		// 問い合わせ履歴情報の登録区分が「TLCSS」の場合は担当者名を表示しない
		return !this.toiawaseRirekiInfo.isRegistKbnToTlcss();
	}
	
	/**
	 * 画面に表示する担当者名を取得します。
	 * 
	 * @return 画面に表示する担当者名
	 */
	public String getTantoshaNmForDisplay(){
		if (StringUtils.isNotBlank(this.toiawaseRirekiInfo.getTantoshaNm())) {
			return this.toiawaseRirekiInfo.getTantoshaNm();
		} else {
			return this.toiawaseRirekiInfo.getTantoshaNmToTantoshaId();
		}
	}
	
	/**
	 * 編集可能か判定する。
	 * 
	 * @return true：編集可能
	 */
	public boolean canEditToiawaseRireki(){
		return (!this.toiawaseRirekiInfo.isRegistKbnToExternalCooperationData() && !isShimeYmExists());
	}

	/**
	 * 締め日が存在するか判定します。
	 * 
	 * @return true:存在
	 */
	public boolean isShimeYmExists() {
		
		if (this.toiawaseInfo == null) {
			return false;
		}
		
		return StringUtils.isNotBlank(this.toiawaseInfo.getShimeYm());
	}
	
	/**
	 * 公開メール送信ボタンの表示可否を判定します。
	 * 
	 * @return true：表示する、false：表示しない
	 */
	public boolean isPublishMailButtonVisible() {
		if (!isUpdate()) {
			// 画面が更新表示でなければ、表示しない
			return false;
		}
		
		if (!RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_KOKAI.equals(this.beforeToiawaseRirekiKokaiFlg)) {
			// 問い合わせ履歴公開フラグが「公開済」でなければ、表示しない
			// 更新値（チェックON⇒チェックOFF）によって、本来表示されるはずの
			// 公開メール送信ボタンが消えてしまうので、常に変更前の問い合わせ公開フラグを見る
			return false;
		}
		
		RcpMKokyaku kokyaku = getKokyakuEntity();
		if (kokyaku == null) {
			// 顧客情報がない場合は表示しない
			return false;
		}
		
		// 顧客マスタの顧客区分が「管理会社(大家含む)」「物件」「入居者・個人」のいずれかの場合、表示
		return (kokyaku.isKokyakuKbnFudosan() || kokyaku.isKokyakuKbnBukken() || kokyaku.isKokyakuKbnNyukyosha());
	}

	/**
	 * "UTF-8"のエンコーディング結果を取得します。
	 *
	 * @param value エンコーディング対象
	 * @return エンコーディング結果（valueがnullの場合、""を返す）
	 */
	public String encode(String value) {
		try {
			return StringUtils.isNotBlank(value) ? (URLEncoder.encode(value, CharEncoding.UTF_8)) : "";
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}

	/**
	 * エンコードされた文字列を取得します。
	 *
	 * @param orgString エンコード前文字列
	 * @return エンコード後文字列
	 */
	private String createEncodeString(String orgString) {
		if (StringUtils.isBlank(orgString)) {
			return "";
		}

		try {
			byte[] data = orgString.getBytes(CharEncoding.ISO_8859_1);

			return new String(data, CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

			return "";
		}
	}
}
