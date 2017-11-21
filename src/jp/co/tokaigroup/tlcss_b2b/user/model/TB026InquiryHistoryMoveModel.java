package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.toiawase.model.RC030ToiawaseCommonModel;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;

/**
 * 問い合わせ履歴移動画面モデル。
 *
 * @author 松葉
 *
 */
public class TB026InquiryHistoryMoveModel extends RC030ToiawaseCommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "問い合わせ履歴移動";

	/** ボタン名 移動 */
	public static final String BUTTON_NM_MOVE = "移動";

	/** 問い合わせ検索条件 */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();

	/** 変更前問い合わせ情報 */
	private RcpTToiawase oldToiawaseInfo;
	/** 変更後問い合わせ情報 */
	private RcpTToiawase newToiawaseInfo;

	/** 変更前顧客情報 */
	private RcpMKokyaku oldKokyakuInfo;
	/** 変更後顧客情報 */
	private RcpMKokyaku newKokyakuInfo;

	/** 変更前ID無し顧客情報 */
	private RcpTKokyakuWithNoId oldKokyakuInfoWithoutId;
	/** 変更後ID無し顧客情報 */
	private RcpTKokyakuWithNoId newKokyakuInfoWithoutId;

	/** 初期処理エラーフラグ */
	private boolean initError;

	/** 登録履歴NOリスト */
	private List<BigDecimal> entryRirekiNoList;

	/** 問い合わせ更新日 */
	private Timestamp toiawaseUpdDt;

	/** 遷移元画面区分 */
	private String dispKbn;

	/** 問い合わせ登録画面のウィンドウ名 */
	private String toiawaseWindowName;

	// パラメータ
	/** 変更後問い合わせNO */
	private String newToiawaseNo;

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
	 * 変更前問い合わせ情報を取得します。
	 *
	 * @return 変更前問い合わせ情報
	 */
	public RcpTToiawase getOldToiawaseInfo() {
		return oldToiawaseInfo;
	}
	/**
	 * 変更前問い合わせ情報を設定します。
	 *
	 * @param oldToiawaseInfo 変更前問い合わせ情報
	 */
	public void setOldToiawaseInfo(RcpTToiawase oldToiawaseInfo) {
		this.oldToiawaseInfo = oldToiawaseInfo;
	}

	/**
	 * 変更後問い合わせ情報を取得します。
	 *
	 * @return 変更後問い合わせ情報
	 */
	public RcpTToiawase getNewToiawaseInfo() {
		return newToiawaseInfo;
	}
	/**
	 * 変更後問い合わせ情報を設定します。
	 *
	 * @param newToiawaseInfo 変更後問い合わせ情報
	 */
	public void setNewToiawaseInfo(RcpTToiawase newToiawaseInfo) {
		this.newToiawaseInfo = newToiawaseInfo;
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
	 * 変更後顧客情報を取得します。
	 *
	 * @return 変更後顧客情報
	 */
	public RcpMKokyaku getNewKokyakuInfo() {
		return newKokyakuInfo;
	}
	/**
	 * 変更後顧客情報を設定します。
	 *
	 * @param newKokyakuInfo 変更後顧客情報
	 */
	public void setNewKokyakuInfo(RcpMKokyaku newKokyakuInfo) {
		this.newKokyakuInfo = newKokyakuInfo;
	}

	/**
	 * 変更前ID無し顧客情報を取得します。
	 *
	 * @return 変更前ID無し顧客情報
	 */
	public RcpTKokyakuWithNoId getOldKokyakuInfoWithoutId() {
		return oldKokyakuInfoWithoutId;
	}
	/**
	 * 変更前ID無し顧客情報を設定します。
	 *
	 * @param oldKokyakuInfoWithoutId 変更前ID無し顧客情報
	 */
	public void setOldKokyakuInfoWithoutId(RcpTKokyakuWithNoId oldKokyakuInfoWithoutId) {
		this.oldKokyakuInfoWithoutId = oldKokyakuInfoWithoutId;
	}

	/**
	 * 変更後ID無し顧客情報を取得します。
	 *
	 * @return 変更後ID無し顧客情報
	 */
	public RcpTKokyakuWithNoId getNewKokyakuInfoWithoutId() {
		return newKokyakuInfoWithoutId;
	}
	/**
	 * 変更後ID無し顧客情報を設定します。
	 *
	 * @param newKokyakuInfoWithoutId 変更後ID無し顧客情報
	 */
	public void setNewKokyakuInfoWithoutId(RcpTKokyakuWithNoId newKokyakuInfoWithoutId) {
		this.newKokyakuInfoWithoutId = newKokyakuInfoWithoutId;
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
	 * 登録履歴NOリストを取得します。
	 *
	 * @return 登録履歴NOリスト
	 */
	public List<BigDecimal> getEntryRirekiNoList() {
		return entryRirekiNoList;
	}
	/**
	 * 登録履歴NOリストを設定します。
	 *
	 * @param entryRirekiNoList 登録履歴NOリスト
	 */
	public void setEntryRirekiNoList(List<BigDecimal> entryRirekiNoList) {
		this.entryRirekiNoList = entryRirekiNoList;
	}

	/**
	 * 変更後問い合わせNOを取得します。
	 *
	 * @return 変更後問い合わせNO
	 */
	public String getNewToiawaseNo() {
		return newToiawaseNo;
	}
	/**
	 * 変更後問い合わせNOを設定します。
	 *
	 * @param newToiawaseNo 変更後問い合わせNO
	 */
	public void setNewToiawaseNo(String newToiawaseNo) {
		this.newToiawaseNo = newToiawaseNo;
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
	 * 変更前情報がID無し顧客情報かを判定します。
	 *
	 * @return true:ID無し顧客情報、false:それ以外
	 */
	public boolean isOldKokyakuWithoutId() {
		if (this.oldToiawaseInfo == null) {
			return false;
		}

		return StringUtils.isBlank(this.oldToiawaseInfo.getKokyakuId());
	}

	/**
	 * 変更後情報がID無し顧客情報かを判定します。
	 *
	 * @return true:ID無し顧客情報、false:それ以外
	 */
	public boolean isNewKokyakuWithoutId() {
		if (this.newToiawaseInfo == null) {
			return false;
		}

		return StringUtils.isBlank(this.newToiawaseInfo.getKokyakuId());
	}
}
