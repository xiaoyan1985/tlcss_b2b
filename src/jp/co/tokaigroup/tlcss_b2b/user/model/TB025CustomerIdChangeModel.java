package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;

/**
 * 顧客ＩＤ変更画面モデル。
 *
 * @author k002785
 * @version 1.0 2015/08/19
 */
public class TB025CustomerIdChangeModel {
	
	/** 画面名 */
	public static final String GAMAN_NM = "顧客ＩＤ変更";

	/** ボタン名：変更 */
	public static final String BUTTON_NM_CHANGE = "変更";
	
	/** 検索条件hidden出力除外項目 */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};
	
	/** 問い合わせ検索条件 */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();
	
	/** 問い合わせNO */
	private String toiawaseNo;
	
	/** 問い合わせ更新日 */
	private Timestamp toiawaseUpdDt;
	
	/** 変更前顧客ID */
	private String oldKokyakuId;
	
	/** 入力顧客ID */
	private String newKokyakuId;
	
	/** 変更前顧客情報 */
	private RcpMKokyaku oldKokyakuInfo;
	
	/** 変更前顧客ID無し情報 */
	private RcpTKokyakuWithNoId oldKokyakuInfoWithoutId;

	/** 入力顧客顧客情報 */
	private RcpMKokyaku newKokyakuInfo;
	
	/** 問い合わせ登録画面のウィンドウ名 */
	private String toiawaseWindowName;
	
	/** 遷移元画面区分 */
	private String dispKbn;
	
	/** 顧客ID */
	private String kokyakuId;
	
	/** 初期処理エラーフラグ */
	private boolean initError;
	
	/** 完了メッセージID */
	private String completeMessageId;
	
	/** 完了メッセージ文言 */
	private String completeMessageStr;
	
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
	 * @return 問い合わせNO
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * 問い合わせNOを設定します。
	 *
	 * @param toiawaseNo 問い合わせNO
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
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
	 * 変更前顧客IDを取得します。
	 *
	 * @return 変更前顧客ID
	 */
	public String getOldKokyakuId() {
		return oldKokyakuId;
	}

	/**
	 * 変更前顧客IDを設定します。
	 *
	 * @param oldKokyakuId 変更前顧客ID
	 */
	public void setOldKokyakuId(String oldKokyakuId) {
		this.oldKokyakuId = oldKokyakuId;
	}

	/**
	 * 入力顧客IDを取得します。
	 *
	 * @return 入力顧客ID
	 */
	public String getNewKokyakuId() {
		return newKokyakuId;
	}

	/**
	 * 入力顧客IDを設定します。
	 *
	 * @param newKokyakuId 入力顧客ID
	 */
	public void setNewKokyakuId(String newKokyakuId) {
		this.newKokyakuId = newKokyakuId;
	}

	/**
	 * 変更前顧客情報を取得します。
	 *
	 * @return 変更前顧客情報
	 */
	public RcpMKokyaku getOldKokyakuInfo() {
		return oldKokyakuInfo;
	}

	/**
	 * 変更前顧客情報を設定します。
	 *
	 * @param oldKokyakuInfo 変更前顧客情報
	 */
	public void setOldKokyakuInfo(RcpMKokyaku oldKokyakuInfo) {
		this.oldKokyakuInfo = oldKokyakuInfo;
	}

	/**
	 * 変更前顧客ID無し情報を取得します。
	 *
	 * @return 変更前顧客ID無し情報
	 */
	public RcpTKokyakuWithNoId getOldKokyakuInfoWithoutId() {
		return oldKokyakuInfoWithoutId;
	}

	/**
	 * 変更前顧客ID無し情報を設定します。
	 *
	 * @param oldKokyakuInfoWithoutId 変更前顧客ID無し情報
	 */
	public void setOldKokyakuInfoWithoutId(RcpTKokyakuWithNoId oldKokyakuInfoWithoutId) {
		this.oldKokyakuInfoWithoutId = oldKokyakuInfoWithoutId;
	}

	/**
	 * 入力顧客情報を取得します。
	 *
	 * @return 入力顧客情報
	 */
	public RcpMKokyaku getNewKokyakuInfo() {
		return newKokyakuInfo;
	}

	/**
	 * 入力顧客情報を設定します。
	 *
	 * @param newKokyakuInfo 入力顧客情報
	 */
	public void setNewKokyakuInfo(RcpMKokyaku newKokyakuInfo) {
		this.newKokyakuInfo = newKokyakuInfo;
	}
	
	/**
	 * 問い合わせ登録画面のウィンドウ名を取得します。
	 *
	 * @return 問い合わせ登録画面のウィンドウ名
	 */
	public String getToiawaseWindowName() {
		return toiawaseWindowName;
	}

	/**
	 * 問い合わせ登録画面のウィンドウ名を設定します。
	 *
	 * @param toiawaseWindowName 問い合わせ登録画面のウィンドウ名
	 */
	public void setToiawaseWindowName(String toiawaseWindowName) {
		this.toiawaseWindowName = toiawaseWindowName;
	}
	
	/**
	 * 遷移元画面区分を取得します。
	 *
	 * @return 遷移元画面区分
	 */
	public String getDispKbn() {
		return dispKbn;
	}

	/**
	 * 遷移元画面区分を設定します。
	 *
	 * @param dispKbn 遷移元画面区分
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}
	
	/**
	 * 顧客IDを取得します。
	 *
	 * @return 顧客ID
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}
	/**
	 * 顧客IDを設定します。
	 *
	 * @param kokyakuId 顧客ID
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
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
	 * 変更前顧客前IDが存在するか判定
	 *
	 * @return true:存在する
	 */
	public boolean isOldKokyakuIdExsits() {
		return StringUtils.isNotBlank(this.oldKokyakuId);
	}
}
