package jp.co.tokaigroup.tlcss_b2b.user.model;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;

/**
 * 顧客基本情報共通モデル。
 *
 * @author v140546
 * @version 1.0 2014/05/29
 * @version 1.1 2015/07/24 v145527
 * @version 1.2 2015/11/04 J.Matsuba 注意事項表示判定メソッド追加
 * 
 */
public class TB040CustomerCommonInfoModel extends TB000CommonModel {
	
	/** タイトル接尾辞 */
	private static final String TITLE_SUFFIX = "詳細";
	/** サブタイトル接尾辞 */
	private static final String SUB_TITLE_SUFFIX = "基本情報";
	
	/** タイトル 顧客区分 1:管理会社(大家含む) */
	private static final String TITLE_REAL_ESTATE = "管理会社";
	/** タイトル 顧客区分 2:大家（情報のみ） */
	private static final String TITLE_LANDLORD = "大家";
	/** タイトル 顧客区分 3:物件 */
	private static final String TITLE_PROPERTY = "物件";
	/** タイトル 顧客区分 4:入居者・個人 */
	private static final String TITLE_TENANT = "入居者・個人";
	
	/** 画面区分 */
	private String gamenKbn;
	/** 顧客ID */
	private String kokyakuId;
	/** 詳細画面区分 */
	private String detailKbn;
	/** 画面表示区分（遷移元画面区分） */
	private String dispKbn;

	/** 顧客テーブルEntity */
	private RcpMKokyaku kokyakuEntity;
	
	/** 検索条件（顧客検索画面） */
	private RC011KokyakuSearchCondition customerCondition = new RC011KokyakuSearchCondition();
	
	/** 遷移元画面区分 */
	private String fromDispKbn;
	
	/**
	 * 画面区分を取得します。
	 *
	 * @return 画面区分
	 */
	public String getGamenKbn() {
		return gamenKbn;
	}
	/**
	 * 画面区分を設定します。
	 *
	 * @param actionType 画面区分
	 */
	public void setGamenKbn(String gamenKbn) {
		this.gamenKbn = gamenKbn;
	}

	/**
	 * 顧客ＩＤを取得します。
	 *
	 * @return 顧客ＩＤ
	 */
	 public String getKokyakuId() {
		return kokyakuId;
	}
	/**
	 * 顧客ＩＤを設定します。
	 *
	 * @param kokyakuId 顧客ＩＤ
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}

	/**
	 * 遷移先画面区分を取得します。
	 *
	 * @return 遷移先画面区分
	 */
	public String getDetailKbn() {
		return detailKbn;
	}
	/**
	 * 遷移先画面区分を設定します。
	 *
	 * @param detailKbn 遷移先画面区分
	 */
	public void setDetailKbn(String detailKbn) {
		this.detailKbn = detailKbn;
	}
	
	/**
	 * 画面表示区分（遷移元画面区分）を取得します。
	 *
	 * @return 画面表示区分（遷移元画面区分）
	 */
	public String getDispKbn() {
		return dispKbn;
	}
	/**
	 * 画面表示区分（遷移元画面区分）を設定します。
	 *
	 * @param dispKbn 画面表示区分（遷移元画面区分）
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}
	
	/**
	 * 顧客テーブルEntityを取得します。
	 * 
	 * @return 顧客テーブルEntity
	 */
	public RcpMKokyaku getKokyakuEntity() {
		return kokyakuEntity;
	}
	
	/**
	 * 顧客テーブルEntityを設定します。
	 * 
	 * @param kokyakuEntity 顧客テーブルEntity
	 */
	public void setKokyakuEntity(RcpMKokyaku kokyakuEntity) {
		this.kokyakuEntity = kokyakuEntity;
	}
	
	/**
	 * 表示する画面が問い合わせ登録かを判定します。
	 *
	 * @return true:問い合わせ登録、false:それ以外
	 */
	public boolean isInquiryEntryDisplay() {
		return Constants.GAMEN_KBN_INQUIRY_ENTRY.equals(gamenKbn);
	}
	
	/**
	 * 表示する画面が物件・入居者詳細かを判定します。
	 *
	 * @return true:物件・入居者詳細、false:それ以外
	 */
	public boolean isCustomerDetailDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL.equals(gamenKbn);
	}
	
	/**
	 * 表示する画面が付随情報かを判定します。
	 *
	 * @return true:付随情報、false:それ以外
	 */
	public boolean isAccompanyingDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL_ACCOMPANYING.equals(gamenKbn);
	}

	/**
	 * 表示する画面が契約情報かを判定します。
	 *
	 * @return true:契約情報、false:それ以外
	 */
	public boolean isContractDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL_CONTRACT.equals(gamenKbn);
	}

	/**
	 * 表示する画面が契約情報詳細参照かを判定します。
	 *
	 * @return true:契約情報、false:それ以外
	 */
	public boolean isContractDetailDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL_CONTRACT_DETAIL.equals(gamenKbn);
	}

	/**
	 * 表示する画面が問い合わせ詳細履歴情報かを判定します。
	 *
	 * @return true:問い合わせ情報、false:それ以外
	 */
	public boolean isInquiryHistoryDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL_INQUIRY_HISTORY.equals(gamenKbn);
	}

	/**
	 * 表示する画面が依頼履歴情報かを判定します。
	 *
	 * @return true:依頼履歴情報、false:それ以外
	 */
	public boolean isRequestHistoryDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL_REQUEST_HISTORY.equals(gamenKbn);
	}
	
	/**
	 * 表示する画面が依頼内容詳細・作業状況登録かを判定します。
	 * 
	 * @return true：依頼内容詳細・作業状況登録
	 */
	public boolean isRequestDetailDisplay() {
		return Constants.GAMEN_KBN_REQUEST_DETAIL.equals(gamenKbn);
	}
	
	/**
	 * 表示する画面が問い合わせ内容詳細かを判定します。
	 * 
	 * @return true：問い合わせ内容詳細
	 */
	public boolean isInquiryDetailDisplay() {
		return Constants.GAMEN_KBN_INQUIRY_DETAIL.equals(gamenKbn);
	}
	
	/**
	 * 顧客区分を判定し、タイトルを返却します。
	 * 
	 * @return タイトル文字列
	 */
	public String getTitle() {
		
		// 顧客情報が取得できない場合
		if (this.kokyakuEntity == null) {
			return TITLE_SUFFIX;	// タイトル接尾辞のみ
		}
		
		String title = "";
		
		if (isRealEstate()) {
			title = TITLE_REAL_ESTATE;
		} else if (isLandlord()) {
			title = TITLE_LANDLORD;
		} else if (isProperty()) {
			title = TITLE_PROPERTY;
		} else if (isTenant()) {
			title = TITLE_TENANT;
		}
		
		// タイトル接尾辞を付与して返却
		return title + TITLE_SUFFIX;
	}
	
	/**
	 * 顧客区分を判定し、サブタイトルを返却します。
	 * 
	 * @return タイトル文字列
	 */
	public String getSubTitle() {
		// 顧客情報が取得できない場合
		if (this.kokyakuEntity == null) {
			return SUB_TITLE_SUFFIX;	// タイトル接尾辞のみ
		}
		
		String subTitle = "";
		
		if (isRealEstate()) {
			subTitle = TITLE_REAL_ESTATE;
		} else if (isLandlord()) {
			subTitle = TITLE_LANDLORD;
		} else if (isProperty()) {
			subTitle = TITLE_PROPERTY;
		} else if (isTenant()) {
			subTitle = TITLE_TENANT;
		}
		
		// サブタイトル接尾辞を付与して返却
		return subTitle + SUB_TITLE_SUFFIX;
	}
	
	/**
	 * 顧客検索欄を表示するか判定します。
	 * 
	 * @return true : 表示、false : 非表示
	 */
	public boolean isSearchInputDisplay() {
		
		return isAccompanyingDisplay()
			|| isContractDisplay()
			|| isContractDetailDisplay()
			|| isInquiryHistoryDisplay()
			|| isRequestHistoryDisplay();
	}
	
	/**
	 * 契約情報参照リンクを表示するか判定します。
	 * 
	 * @return true : 表示、false : 非表示
	 */
	public boolean isContractReferenceDisplay() {
		
		if (this.kokyakuEntity == null) {
			// 顧客情報が取得できない場合は、非表示
			return false;
		}
		
		 return ! (isCustomerDetailDisplay()
				|| isAccompanyingDisplay()
				|| isContractDisplay()
				|| isContractDetailDisplay()
				|| isInquiryHistoryDisplay()
				|| isRequestHistoryDisplay());
	}

	/**
	 * 顧客区分が不動産・管理会社に属しているかを判定します。
	 *
	 * @return true:不動産・管理会社、false:それ以外
	 */
	public boolean isRealEstate() {
		
		if (this.kokyakuEntity == null) {
			return false;
		}

		return this.kokyakuEntity.isKokyakuKbnFudosan();
	}

	/**
	 * 顧客区分が物件に属しているかを判定します。
	 *
	 * @return true:物件、false:それ以外
	 */
	public boolean isProperty() {
		
		if (this.kokyakuEntity == null) {
			return false;
		}

		return this.kokyakuEntity.isKokyakuKbnBukken();
	}

	/**
	 * 顧客区分が大家に属しているかを判定します。
	 *
	 * @return true:大家、false:それ以外
	 */
	public boolean isLandlord() {
		
		if (this.kokyakuEntity == null) {
			return false;
		}

		return this.kokyakuEntity.isKokyakuKbnOoya();
	}

	/**
	 * 顧客区分が入居者・個人に属しているかを判定します。
	 *
	 * @return true:入居者・個人、false:それ以外
	 */
	public boolean isTenant() {
		
		if (this.kokyakuEntity == null) {
			return false;
		}

		return this.kokyakuEntity.isKokyakuKbnNyukyosha();
	}
	
	/**
	 * 顧客IDが存在するか判定します。
	 * 
	 * @return true:存在する
	 */
	public boolean isKokyakuIdExists() {
		return StringUtils.isNotBlank(this.kokyakuId);
	}
	
	/**
	 * 検索条件（顧客検索画面）を取得します。
	 *
	 * @return 検索条件
	 */
	public RC011KokyakuSearchCondition getCustomerCondition() {
		return customerCondition;
	}

	/**
	 * 検索条件（顧客検索画面）を設定します。
	 *
	 * @param customerCondition 検索条件（顧客検索画面）
	 */
	public void setCustomerCondition(RC011KokyakuSearchCondition customerCondition) {
		this.customerCondition = customerCondition;
	}
	
	/**
	 * 遷移元画面区分を取得します。
	 *
	 * @return 遷移元画面区分
	 */
	public String getFromDispKbn() {
		return fromDispKbn;
	}
	
	/**
	 * 遷移元画面区分を設定します。
	 *
	 * @param fromDispKbn 遷移元画面区分
	 */
	public void setFromDispKbn(String fromDispKbn) {
		this.fromDispKbn = fromDispKbn;
	}
	
	/**
	 * 「戻る」ボタンを表示するか判定
	 * 
	 * @return true（遷移元画面区分が「tb041」）：「戻る」ボタン表示
	 */
	public boolean isBackButtonView(){
		return Constants.GAMEN_KBN_CUSTOMER_SEARCH.equals(this.fromDispKbn);
	}
	
	/**
	 * 遷移元画面が問い合わせ検索画面かを判定します。
	 * 
	 * @return true：問い合わせ検索画面
	 */
	public boolean isFromInquirySearch() {
		return Constants.GAMEN_KBN_INQUIRY_SEARCH.equals(this.dispKbn);
	}
}