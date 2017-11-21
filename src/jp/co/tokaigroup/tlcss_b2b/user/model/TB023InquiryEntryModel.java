package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseManual;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseFile;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * 問い合わせ登録画面モデル。
 *
 * @author k003316
 * @version 1.0 2015/08/11
 * @version 1.1 2016/07/13 H.Yamamura 問い合わせ検索画面の文字化け対策
 * @version 1.2 2016/07/15 H.Yamamura サービス種別リスト追加
 */
public class TB023InquiryEntryModel extends TB040CustomerCommonInfoModel {
	
	/** 画面名 */
	public static final String GAMEN_NM = "問い合わせ登録";
	
	/** 処理区分 1：入電報告書 */
	public static final String SHORI_KBN_INCOMING_CALL_REPORT = "1";
	/** 処理区分 2：作業報告書 */
	public static final String SHORI_KBN_WORK_REPORT = "2";
	
	/** コール数請求年月=空欄時のメッセージ */
	private static final String MESSAGE_CALL_SEIKYU_YM_EMPTY_DISPLAY = "請求前";
	/** 締め年月=空欄時のメッセージ */
	private static final String MESSAGE_SHIME_YM_EMPTY_DISPLAY = "締め処理前";
	
	/** 問い合わせ検索条件 */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();
	
	/** 検索条件hidden出力除外項目 */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};
	
	/** 問い合わせNO */
	private String toiawaseNo;
	
	/** 問い合わせ更新日 */
	private Timestamp toiawaseUpdDt;
	
	/** ファイルインデックス */
	private BigDecimal fileIndex;
	
	/** アップロードファイル名 */
	private String uploadFileNm;
	
	/** ID無し顧客情報 */
	private RcpTKokyakuWithNoId kokyakuInfoWithoutId;
	
	/** 問い合わせ基本情報 */
	private RcpTToiawase toiawaseInfo;
	
	/** パスワードＭ情報 */
	private NatosMPassword passwordInfo;
	
	/** 問い合わせ履歴リスト */
	private List<RcpTToiawaseRireki> toiawaseRirekiList;
	
	/** 依頼有無区分(変更前) */
	private String iraiUmuKbn;
	
	/** 問い合わせ公開フラグ(変更前) */
	private String befereToiawaseKokaiFlg;
	
	/** 報告書公開中止フラグ(1:公開中止,0:公開継続) */
	private String kokaiTyushiFlg;
	
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
	/** 依頼有無リスト */
	private List<RcpMComCd> iraiUmuList;
	/** 依頼者性別リスト */
	private List<RcpMComCd> iraishaSexKbnList;
	/** 受付形態リスト */
	private List<RcpMComCd> uketsukeKeitaiKbnList;
	/** サービス種別リスト */
	private List<RcpMComCd> serviceShubetsuList;
	
	/** 対象顧客ＩＤ */
	private String targetKokyakuId;
	
	/** 初期表示エラーフラグ */
	private boolean isInitError = false;
	/** 削除完了フラグ */
	private boolean isDeleteCompleted = false;
	/** ファイル削除完了フラグ */
	private boolean isFileDeleteSuccess = false;
	/** ダウンロード可能フラグ */
	private boolean isDownloadable = false;

	/** 問い合わせマニュアル情報 */
	private RcpMToiawaseManual toiawaseManual;
	/** 公開メール送信履歴情報 */
	private RcpTMailRireki mailRireki;

	/** アップロード済み問い合わせ添付ファイル */
	private RcpTToiawaseFile[] uploadedFiles;
	/** 問い合わせ添付ファイル */
	private File[] toiawaseFiles;
	/** 問い合わせ添付ファイル名(カンマ区切り) */
	private String toiawaseFileNm;
	
	/** 問い合わせ履歴ＮＯ */
	private BigDecimal toiawaseRirekiNo;
	/** 処理区分 */
	private String shoriKbn;

	/** 完了メッセージID */
	private String completeMessageId;
	
	/** 完了メッセージ文言 */
	private String completeMessageStr;

	/** 変更後顧客ＩＤ */
	private String changeKokyakuId;
	/** 移動後問い合わせＮＯ */
	private String newToiawaseNo;

	/** 変更前報告書公開フラグ */
	private String beforeHokokushoKokaiFlg;

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

	/** サービス種別初期値 */
	private String initServiceShubetsu;

	/**
	 * デフォルトコンストラクタ。
	 */
	public TB023InquiryEntryModel() {
		super();
	}

	/**
	 * 引数付きコンストラクタ。
	 *
	 * @param uploadMax アップロード最大件数
	 */
	public TB023InquiryEntryModel(int uploadMax) {
		super();

		this.toiawaseFiles = new File[uploadMax];
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
	 * 問い合わせNOを取得します。
	 * 
	 * @return toiawaseNo 問い合わせNO
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * 問い合わせNOを設定します。
	 * 
	 * @param toiawaseNo セットする 問い合わせNO
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
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
	 * ファイルインデックスを取得します。
	 *
	 * @return ファイルインデックス
	 */
	public BigDecimal getFileIndex() {
		return fileIndex;
	}
	/**
	 * ファイルインデックスを設定します。
	 *
	 * @param fileIndex ファイルインデックス
	 */
	public void setFileIndex(BigDecimal fileIndex) {
		this.fileIndex = fileIndex;
	}

	/**
	 * アップロードファイル名を取得します。
	 *
	 * @return アップロードファイル名
	 */
	public String getUploadFileNm() {
		return uploadFileNm;
	}
	/**
	 * アップロードファイル名を設定します。
	 *
	 * @param uploadFileNm アップロードファイル名
	 */
	public void setUploadFileNm(String uploadFileNm) {
		this.uploadFileNm = uploadFileNm;
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
	 * 問い合わせ基本情報を取得します。
	 *
	 * @return 問い合わせ基本情報
	 */
	public RcpTToiawase getToiawaseInfo() {
		return toiawaseInfo;
	}
	/**
	 * 問い合わせ基本情報を設定します。
	 *
	 * @param toiawaseInfo 問い合わせ基本情報
	 */
	public void setToiawaseInfo(RcpTToiawase toiawaseInfo) {
		this.toiawaseInfo = toiawaseInfo;
	}
	
	/**
	 * パスワードＭ情報を取得します。
	 * 
	 * @return passwordInfo パスワードＭ情報
	 */
	public NatosMPassword getPasswordInfo() {
		return passwordInfo;
	}

	/**
	 * パスワードＭ情報を設定します。
	 * 
	 * @param passwordInfo セットする パスワードＭ情報
	 */
	public void setPasswordInfo(NatosMPassword passwordInfo) {
		this.passwordInfo = passwordInfo;
	}

	/**
	 * 問い合わせ履歴リストを取得します。
	 * 
	 * @return toiawaseRirekiList
	 */
	public List<RcpTToiawaseRireki> getToiawaseRirekiList() {
		return toiawaseRirekiList;
	}

	/**
	 * 問い合わせ履歴リストを設定します。
	 * 
	 * @param toiawaseRirekiList セットする 問い合わせ履歴リスト
	 */
	public void setToiawaseRirekiList(List<RcpTToiawaseRireki> toiawaseRirekiList) {
		this.toiawaseRirekiList = toiawaseRirekiList;
	}
	
	/**
	 * 依頼有無区分(変更前)を取得します。
	 * 
	 * @return iraiUmuKbn
	 */
	public String getIraiUmuKbn() {
		return iraiUmuKbn;
	}

	/**
	 * 依頼有無区分(変更前)を設定します。
	 * 
	 * @param iraiUmuKbn セットする 依頼有無区分(変更前)
	 */
	public void setIraiUmuKbn(String iraiUmuKbn) {
		this.iraiUmuKbn = iraiUmuKbn;
	}
	
	/**
	 * 問い合わせ公開フラグ(変更前)を取得します。
	 *
	 * @return 問い合わせ公開フラグ(変更前)
	 */
	public String getBefereToiawaseKokaiFlg() {
		return befereToiawaseKokaiFlg;
	}
	/**
	 * 問い合わせ公開フラグ(変更前)を設定します。
	 *
	 * @param befereToiawaseKokaiFlg 問い合わせ公開フラグ(変更前)
	 */
	public void setBefereToiawaseKokaiFlg(String befereToiawaseKokaiFlg) {
		this.befereToiawaseKokaiFlg = befereToiawaseKokaiFlg;
	}

	/**
	 * 報告書公開中止フラグを取得します。
	 *
	 * @return 報告書公開中止フラグ
	 */
	public String getKokaiTyushiFlg() {
		return kokaiTyushiFlg;
	}

	/**
	 * 報告書公開中止フラグを設定します。
	 *
	 * @param kokaiTyushiFlg 報告書公開中止フラグ
	 */
	public void setKokaiTyushiFlg(String kokaiTyushiFlg) {
		this.kokaiTyushiFlg = kokaiTyushiFlg;
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
	 * @return toiawaseKbn1List 問い合わせ区分１リスト
	 */
	public List<RcpMToiawaseKbn1> getToiawaseKbn1List() {
		return toiawaseKbn1List;
	}

	/**
	 * 問い合わせ区分１リストを設定します。
	 * 
	 * @param toiawaseKbn1List セットする 問い合わせ区分１リスト
	 */
	public void setToiawaseKbn1List(List<RcpMToiawaseKbn1> toiawaseKbn1List) {
		this.toiawaseKbn1List = toiawaseKbn1List;
	}

	/**
	 * 問い合わせ区分２リストを取得します。
	 * 
	 * @return toiawaseKbn2List 問い合わせ区分２
	 */
	public List<RcpMToiawaseKbn2> getToiawaseKbn2List() {
		return toiawaseKbn2List;
	}

	/**
	 * 問い合わせ区分２リストを設定します。
	 * 
	 * @param toiawaseKbn2List セットする 問い合わせ区分２
	 */
	public void setToiawaseKbn2List(List<RcpMToiawaseKbn2> toiawaseKbn2List) {
		this.toiawaseKbn2List = toiawaseKbn2List;
	}

	/**
	 * 問い合わせ区分３リストを取得します。
	 * 
	 * @return toiawaseKbn3List 問い合わせ区分３
	 */
	public List<RcpMToiawaseKbn3> getToiawaseKbn3List() {
		return toiawaseKbn3List;
	}

	/**
	 * 問い合わせ区分３リストを設定します。
	 * 
	 * @param toiawaseKbn3List セットする 問い合わせ区分３
	 */
	public void setToiawaseKbn3List(List<RcpMToiawaseKbn3> toiawaseKbn3List) {
		this.toiawaseKbn3List = toiawaseKbn3List;
	}

	/**
	 * 問い合わせ区分４リストを取得します。
	 * 
	 * @return toiawaseKbn4List 問い合わせ区分４リスト
	 */
	public List<RcpMToiawaseKbn4> getToiawaseKbn4List() {
		return toiawaseKbn4List;
	}

	/**
	 * 問い合わせ区分４リストを設定します。
	 * 
	 * @param toiawaseKbn4List セットする 問い合わせ区分４リスト
	 */
	public void setToiawaseKbn4List(List<RcpMToiawaseKbn4> toiawaseKbn4List) {
		this.toiawaseKbn4List = toiawaseKbn4List;
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
	 * 依頼者性別リストを取得します。
	 * 
	 * @return iraishaSexKbnList 依頼者性別リスト
	 */
	public List<RcpMComCd> getIraishaSexKbnList() {
		return iraishaSexKbnList;
	}

	/**
	 * サービス種別リストを取得します。
	 * @return サービス種別リスト
	 */
	public List<RcpMComCd> getServiceShubetsuList() {
		return serviceShubetsuList;
	}

	/**
	 * サービス種別リストを設定します。
	 * @param serviceShubetsuList サービス種別リスト
	 */
	public void setServiceShubetsuList(List<RcpMComCd> serviceShubetsuList) {
		this.serviceShubetsuList = serviceShubetsuList;
	}

	/**
	 * 依頼者性別リストを設定します。
	 * 
	 * @param iraishaSexKbnList セットする 依頼者性別リスト
	 */
	public void setIraishaSexKbnList(List<RcpMComCd> iraishaSexKbnList) {
		this.iraishaSexKbnList = iraishaSexKbnList;
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
	 * 初期表示エラーフラグを取得します。
	 * 
	 * @return isInitError 初期表示エラーフラグ
	 */
	public boolean isInitError() {
		return isInitError;
	}

	/**
	 * 初期表示エラーフラグを設定します。
	 * 
	 * @param isInitError セットする 初期表示エラーフラグ
	 */
	public void setInitError(boolean isInitError) {
		this.isInitError = isInitError;
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
	 * ファイル削除完了フラグを取得します。
	 *
	 * @return ファイル削除完了フラグ
	 */
	public boolean isFileDeleteSuccess() {
		return isFileDeleteSuccess;
	}
	/**
	 * ファイル削除完了フラグを設定します。
	 *
	 * @param isFileDeleteSuccess ファイル削除完了フラグ
	 */
	public void setFileDeleteSuccess(boolean isFileDeleteSuccess) {
		this.isFileDeleteSuccess = isFileDeleteSuccess;
	}

	/**
	 * ダウンロード可能フラグを取得します。
	 * @return ダウンロード可能フラグ
	 */
	public boolean isDownloadable() {
		return isDownloadable;
	}
	/**
	 * ダウンロード可能フラグを設定します。
	 * @param isDownloadable ダウンロード可能フラグ
	 */
	public void setDownloadable(boolean isDownloadable) {
		this.isDownloadable = isDownloadable;
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
	 * アップロード済み問い合わせ添付ファイルを取得します。
	 *
	 * @return アップロード済み問い合わせ添付ファイル
	 */
	public RcpTToiawaseFile[] getUploadedFiles() {
		return uploadedFiles;
	}
	/**
	 * アップロード済み問い合わせ添付ファイルを設定します。
	 *
	 * @param uploadedFiles アップロード済み問い合わせ添付ファイル
	 */
	public void setUploadedFiles(RcpTToiawaseFile[] uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	/**
	 * 問い合わせ添付ファイルを取得します。
	 *
	 * @return 問い合わせ添付ファイル
	 */
	public File[] getToiawaseFiles() {
		return toiawaseFiles;
	}
	/**
	 * 問い合わせ添付ファイルを設定します。
	 *
	 * @param toiawaseFiles 問い合わせ添付ファイル
	 */
	public void setToiawaseFiles(File[] toiawaseFiles) {
		this.toiawaseFiles = toiawaseFiles;
	}

	/**
	 * 問い合わせ添付ファイル名(カンマ区切り)を取得します。
	 *
	 * @return 問い合わせ添付ファイル名(カンマ区切り)
	 */
	public String getToiawaseFileNm() {
		return toiawaseFileNm;
	}
	/**
	 * 問い合わせ添付ファイル名(カンマ区切り)を設定します。
	 *
	 * @param toiawaseFileNm 問い合わせ添付ファイル名(カンマ区切り)
	 */
	public void setToiawaseFileNm(String toiawaseFileNm) {
		this.toiawaseFileNm = toiawaseFileNm;
	}
	
	/**
	 * 問い合わせ履歴ＮＯを取得します。
	 *
	 * @return 問い合わせ履歴ＮＯ
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}
	/**
	 * 問い合わせ履歴ＮＯを設定します。
	 *
	 * @param toiawaseRirekiNo 問い合わせ履歴ＮＯ
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}

	/**
	 * 処理区分を取得します。
	 *
	 * @return 処理区分
	 */
	public String getShoriKbn() {
		return shoriKbn;
	}
	/**
	 * 処理区分を設定します。
	 *
	 * @param shoriKbn 処理区分
	 */
	public void setShoriKbn(String shoriKbn) {
		this.shoriKbn = shoriKbn;
	}

	/**
	 * 完了メッセージIDを取得します。
	 * @return 完了メッセージID
	 */
	public String getCompleteMessageId() {
		return completeMessageId;
	}

	/**
	 * 完了メッセージIDを設定します。
	 * @param completeMessageId 完了メッセージID
	 */
	public void setCompleteMessageId(String completeMessageId) {
		this.completeMessageId = completeMessageId;
	}

	/**
	 * 完了メッセージ文言を取得します。
	 * @return 完了メッセージ文言
	 */
	public String getCompleteMessageStr() {
		return completeMessageStr;
	}

	/**
	 * 完了メッセージ文言を取得します。
	 * @param completeMessageStr 完了メッセージ文言
	 */
	public void setCompleteMessageStr(String completeMessageStr) {
		this.completeMessageStr = completeMessageStr;
	}

	/**
	 * 変更後顧客ＩＤを取得します。
	 * 
	 * @return 変更後顧客ＩＤ
	 */
	public String getChangeKokyakuId() {
		return changeKokyakuId;
	}
	/**
	 * 変更後顧客ＩＤを設定します。
	 * 
	 * @param changeKokyakuId 変更後顧客ＩＤ
	 */
	public void setChangeKokyakuId(String changeKokyakuId) {
		this.changeKokyakuId = changeKokyakuId;
	}

	/**
	 * 移動後問い合わせＮＯを取得します。
	 * 
	 * @return 移動後問い合わせＮＯ
	 */
	public String getNewToiawaseNo() {
		return newToiawaseNo;
	}
	/**
	 * 移動後問い合わせＮＯを設定します。
	 * 
	 * @param newToiawaseNo 移動後問い合わせＮＯ
	 */
	public void setNewToiawaseNo(String newToiawaseNo) {
		this.newToiawaseNo = newToiawaseNo;
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
	 * 変更前報告書公開フラグを取得します。
	 * @return 変更前報告書公開フラグ
	 */
	public String getBeforeHokokushoKokaiFlg() {
		return beforeHokokushoKokaiFlg;
	}
	/**
	 * 変更前報告書公開フラグを設定します。
	 * @param beforeHokokushoKokaiFlg 変更前報告書公開フラグ
	 */
	public void setBeforeHokokushoKokaiFlg(String beforeHokokushoKokaiFlg) {
		this.beforeHokokushoKokaiFlg = beforeHokokushoKokaiFlg;
	}

	/**
	 * サービス種別初期値を取得します。
	 * @return サービス種別初期値
	 */
	public String getInitServiceShubetsu() {
		return initServiceShubetsu;
	}

	/**
	 * サービス種別初期値を設定します。
	 * @param initServiceShubetsu サービス種別初期値
	 */
	public void setInitServiceShubetsu(String initServiceShubetsu) {
		this.initServiceShubetsu = initServiceShubetsu;
	}

	/**
	 * 遷移元画面が問い合わせ検索画面であるか判定します。
	 * 
	 * @return true:問い合わせ検索画面
	 */
	public boolean isFromInquirySearch() {
		return Constants.GAMEN_KBN_INQUIRY_SEARCH.equals(getDispKbn());
	}
	
	/**
	 * 遷移元画面が依頼登録画面であるか判定します。
	 * 
	 * @return true:遷移元が依頼登録画面
	 */
	public boolean isFromRequestEntry() {
		return Constants.GAMEN_KBN_REQUEST_ENTRY.equals(getDispKbn());
	}
	
	/**
	 * 依頼有無区分が変更されたか判定します。
	 *
	 * @return true:変更済 false:未変更
	 */
	public boolean isIraiUmuKbnChanged() {
		if (StringUtils.isBlank(toiawaseInfo.getIraiUmuKbn())) {
			return false;
		}

		return !toiawaseInfo.getIraiUmuKbn().equals(iraiUmuKbn);
	}

	/**
	 * 依頼有無区分が「無し」かを判定します。
	 *
	 * @return true:依頼有無区分「無し」
	 */
	public boolean isIraiKbnNashi() {
		return RcpTToiawase.IRAI_UMU_KBN_NASHI.equals(toiawaseInfo.getIraiUmuKbn());
	}
	
	/**
	 * 更新ボタンの使用可否判定を行います。
	 * 
	 * @return true:使用可能
	 */
	public boolean isUpdateButtonAvailable() {
		
		if (this.toiawaseInfo == null || isInsert()) {
			return false;
		}
		
		// 問い合わせテーブルの登録区分チェック
		if (RcpTToiawase.REGIST_KBN_EXTERNAL_COOPERATION_DATA.equals(this.toiawaseInfo.getRegistKbn())) {
			return false;
		}
		
		// 締め年月がNULLならば使用可能
		return StringUtils.isBlank(this.toiawaseInfo.getShimeYm());
	}
	
	/**
	 * 問い合わせ公開フラグに含むチェックがチェックされているかを判定します。
	 *
	 * @return true:チェックされている、false:チェックされていない
	 */
	public boolean isToiawaseKokaiFlgChecked() {

		if (this.toiawaseInfo == null) {
			// デフォルトは、チェックOFF
			return false;
		}

		if (StringUtils.isBlank(this.toiawaseInfo.getToiawaseKokaiFlg())) {
			// デフォルトは、チェックOFF
			return false;
		}
		
		if (isKokyakuInfoWithoutIdVisible()) {
			// ＩＤ無し顧客の場合は、チェックOFF
			return false;
		}
		
		return RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI.equals(this.toiawaseInfo.getToiawaseKokaiFlg());
	}
	
	/**
	 * 報告書公開フラグに含むチェックがチェックされているかを判定します。
	 *
	 * @return true:チェックされている、false:チェックされていない
	 */
	public boolean isHoukokushoKokaiFlgChecked() {
		
		if (this.toiawaseInfo == null) {
			// デフォルトは、チェックOFF
			return false;
		}

		if (StringUtils.isBlank(this.toiawaseInfo.getHokokushoKokaiFlg())) {
			// デフォルトは、チェックOFF
			return false;
		}
		if (isKokyakuInfoWithoutIdVisible()) {
			// ＩＤ無し顧客の場合は、チェックOFF
			return false;
		}

		return RcpTToiawase.HOKOKUSHO_KOKAI_FLG_KOKAI.equals(this.toiawaseInfo.getHokokushoKokaiFlg());
	}
	
	/**
	 * 問い合わせ履歴一覧から依頼登録画面に遷移できるか判定します。
	 *
	 * @return ture:遷移可能、false:遷移不可
	 */
	public boolean canMoveToIraiEntry() {
		
		if (this.toiawaseInfo == null || isInsert()) {
			return false;
		}
		
		// 依頼有無区分が「依頼有り」「手配のみ」以外は不可
		if (!RcpTToiawase.IRAI_UMU_KBN_ARI.equals(this.iraiUmuKbn)
				&& !RcpTToiawase.IRAI_UMU_KBN_TEHAI_NOMI.equals(this.iraiUmuKbn)) {
			return false;
		}
		
		// 締め日がNULLの場合は可能
		return StringUtils.isBlank(this.toiawaseInfo.getShimeYm());
	}
	
	/**
	 * 入電報告書印刷ボタンが使用不可か判定します。
	 *
	 * @return true: 入電報告書印刷ボタンが使用不可
	 */
	public boolean isNyudenHoukokuPrintButtonDisabled() {
		return (isInsert() || StringUtils.isBlank(getKokyakuId()));
	}
	
	/**
	 * 入電報告件数に含むチェックがチェックされているかを判定します。
	 *
	 * @return true:チェックされている、false:チェックされていない
	 */
	public boolean isHoukokuTargetFlgChecked() {
		if (this.toiawaseInfo == null) {
			// デフォルトは、チェックON
			return true;
		}

		if (StringUtils.isBlank(this.toiawaseInfo.getHoukokuTargetFlg())) {
			// デフォルトは、チェックON
			return true;
		}

		return RcpTToiawase.HOKOKU_TARGET_FLG_ON.equals(this.toiawaseInfo.getHoukokuTargetFlg());
	}
	
	/**
	 * ID無し顧客情報画面を表示するかを判定します。
	 *
	 * @return true:表示する、false:表示しない
	 */
	public boolean isKokyakuInfoWithoutIdVisible() {
		return StringUtils.isBlank(getKokyakuId());
	}
	
	/**
	 * 最終更新者を判定して返却します。
	 * 
	 * @return 最終更新者
	 */
	public String getLastUpdateName() {
		
		if (this.toiawaseInfo == null) {
			return "";
		}
		
		// 問い合わせテーブル.最終更新者名が設定されていない場合は、ＩＤから変換したものを使用する。
		return StringUtils.isBlank(this.toiawaseInfo.getLastUpdNm()) ?
				this.toiawaseInfo.getLastUpdIdToNm() : this.toiawaseInfo.getLastUpdNm();
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
	 * 画面表示する、コール数請求年月を取得します。
	 * 
	 * @return コール数請求年月（画面表示用）
	 */
	public String getCallSeikyuYmForDisplay() {
		if (this.toiawaseInfo == null) {
			// 問い合わせ情報が取得できない場合は、空欄
			return "";
		}
		
		if (StringUtils.isNotBlank(this.toiawaseInfo.getCallSeikyuYm())) {
			// コール数請求年月が取得できた場合
			return DateUtil.yyyymmPlusSlash(this.toiawaseInfo.getCallSeikyuYm());
		} else {
			// コール数請求年月が取得できない場合
			return MESSAGE_CALL_SEIKYU_YM_EMPTY_DISPLAY;
		}
	}
	
	/**
	 * 画面表示する、締め年月を取得します。
	 * 
	 * @return 締め年月（画面表示用）
	 */
	public String getShimeYmForDisplay() {
		if (this.toiawaseInfo == null) {
			// 問い合わせ情報が取得できない場合は、空欄
			return "";
		}
		
		if (StringUtils.isNotBlank(this.toiawaseInfo.getShimeYm())) {
			// 締め年月が取得できた場合
			return DateUtil.yyyymmPlusSlash(this.toiawaseInfo.getShimeYm());
		} else {
			// 締め年月が取得できない場合
			return MESSAGE_SHIME_YM_EMPTY_DISPLAY;
		}
	}
	
	/**
	 * 画面表示する、受付者名を取得します。
	 * 
	 * @return 受付者名（画面表示用）
	 */
	public String getUketsukeshaNmForDisplay() {
		if (this.toiawaseInfo == null) {
			// 問い合わせ情報が取得できない場合は、空欄
			return "";
		}
		
		if (StringUtils.isNotBlank(this.toiawaseInfo.getUketsukeshaNm())) {
			// 受付者名が存在する場合は、受付者名を表示
			return this.toiawaseInfo.getUketsukeshaNm();
		} else {
			// 受付者名が存在しない場合は、受付者ＩＤを和名変換した値を表示
			return this.toiawaseInfo.getUketsukeNm();
		}
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
		
		if (this.toiawaseInfo == null) {
			// 問い合わせ情報がない場合は、表示しない
			return false;
		}
		
		RcpMKokyaku kokyaku = getKokyakuEntity();
		if (kokyaku == null) {
			// 顧客情報がない場合は表示しない
			return false;
		}
		
		if (!RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI.equals(this.befereToiawaseKokaiFlg)) {
			// 問い合わせ公開フラグが「公開済」でなければ、表示しない
			// 更新値（チェックON⇒チェックOFF）によって、本来表示されるはずの
			// 公開メール送信ボタンが消えてしまうので、常に変更前の問い合わせ公開フラグを見る
			return false;
		}
		
		// 顧客マスタの顧客区分が「管理会社(大家含む)」「物件」「入居者・個人」のいずれかの場合、表示
		return (kokyaku.isKokyakuKbnFudosan() || kokyaku.isKokyakuKbnBukken() || kokyaku.isKokyakuKbnNyukyosha());
	}
	
	/**
	 * 問い合わせ添付ファイル名を配列にして返します。
	 *
	 * @return 問い合わせ添付ファイル名配列
	 */
	public String[] getToiawaseFileNmByArray() {
		if (StringUtils.isBlank(this.toiawaseFileNm)) {
			return new String[0];
		}

		return this.toiawaseFileNm.split(",");
	}
	
	/**
	 * ファイルアップロード処理を実行するかを判定します。
	 * 
	 * @return true：処理を実行する
	 */
	public boolean isExecuteFileUpload() {
		if (this.toiawaseFiles == null || this.toiawaseFiles.length == 0) {
			return false;
		}

		for (File file : this.toiawaseFiles) {
			if (file != null) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 作業報告書印刷ボタンが表示可能かを判定します。
	 * 
	 * @return true：表示可能
	 */
	public boolean isWorkReportButtonVisible() {
		if (this.toiawaseInfo == null) {
			// 問い合わせ情報が存在しない場合は、表示しない
			return false;
		}
		
		return StringUtils.isNotBlank(this.toiawaseInfo.getExtHokokushoFileNm());
	}
	
	/**
	 * 変更前問い合わせ公開フラグが公開済かを判定します。
	 * 
	 * @return true：公開済
	 */
	public boolean isBeforeToiawasePublished() {
		return RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI.equals(this.befereToiawaseKokaiFlg);
	}
	
	/**
	 * 変更前報告書フラグが公開済かを判定します。
	 * 
	 * @return true：公開済
	 */
	public boolean isBeforeHokokushoPublished() {
		return RcpTToiawase.HOKOKUSHO_KOKAI_FLG_KOKAI.equals(this.beforeHokokushoKokaiFlg);
	}
	
	/**
	 * 公開を止めるチェックがチェックＯＮかを判定します。
	 * 
	 * @return true：チェックＯＮ
	 */
	public boolean isStopPublishChecked() {
		return RcpTToiawase.KOKAI_TYUSHI_FLG.equals(this.kokaiTyushiFlg);
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
