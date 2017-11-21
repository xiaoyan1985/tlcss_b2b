package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dto.InquiryStatus;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.TbMHoliday;
import jp.co.tokaigroup.reception.entity.TbMItakuRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.reception.entity.TbTPublishFile;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.dto.TB010InquiryStatusDto;

import org.apache.commons.lang.StringUtils;

/**
 * メニュー画面モデル。
 *
 * @author k002849
 * @version 4.0 2014/06/04
 * @version 4.1 2016/09/13 J.Matsuba 委託会社参照顧客情報リストの追加
 */
public class TB010MenuModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "メニュー";

	/** 問い合わせ状況リスト */
	private List<TB010InquiryStatusDto> inqueryStatusList;
	/** 対応中合計件数 */
	private BigDecimal sumIncompleteCount;
	/** 対応済合計件数 */
	private BigDecimal sumCompleteCount;

	/** 未読件数 */
	private BigDecimal unreadCount;

	/** 集計開始日 */
	private Timestamp summaryStartDt;
	/** 集計終了日 */
	private Timestamp summaryEndDt;

	/** 顧客情報 */
	private RcpMKokyaku kokyaku;

	/** 問い合わせ受付履歴リスト */
	private List<InquiryStatus> inquiryHistoryList;

	/** 対応中案件リスト */
	private List<RcpTIrai> requestList;

	/** 業者情報 */
	private RcpMGyosha gyosha;

	/** お知らせリスト */
	private List<TbTInformation> infomationList;

	/** 祝日リスト */
	private List<TbMHoliday> holidayList;

	/** ユーザーコンテキスト */
	private TLCSSB2BUserContext userContext;

	/** 問い合わせ検索条件 */
	private RC031ToiawaseSearchCondition condition;

	/** ユーザーエージェント */
	private String userAgent;

	/** 選択した顧客ID */
	private String selectedKokyakuId;

	/** 公開ファイルEntityリスト */
	private List<TbTPublishFile> publishFileList;

	/** 会社情報 */
	private TbMKaisha kaisha;

	/** 委託会社参照顧客情報リスト */
	private List<TbMItakuRefKokyaku> itakuRefKokyakuList;

	/** 再表示タイマー */
	private String reloadTime;
	
	/**
	 * 問い合わせ状況リストを取得します。
	 *
	 * @return 問い合わせ状況リスト
	 */
	public List<TB010InquiryStatusDto> getInqueryStatusList() {
		return inqueryStatusList;
	}
	/**
	 * 問い合わせ状況リストを設定します。
	 *
	 * @param inqueryStatusList 問い合わせ状況リスト
	 */
	public void setInqueryStatusList(List<TB010InquiryStatusDto> inqueryStatusList) {
		this.inqueryStatusList = inqueryStatusList;
	}

	/**
	 * 対応中合計件数を取得します。
	 *
	 * @return 対応中合計件数
	 */
	public BigDecimal getSumIncompleteCount() {
		return sumIncompleteCount;
	}
	/**
	 * 対応中合計件数を設定します。
	 *
	 * @param sumIncomplateCount 対応中合計件数
	 */
	public void setSumIncompleteCount(BigDecimal sumIncompleteCount) {
		this.sumIncompleteCount = sumIncompleteCount;
	}

	/**
	 * 対応済合計件数を取得します。
	 *
	 * @return 対応済合計件数
	 */
	public BigDecimal getSumCompleteCount() {
		return sumCompleteCount;
	}
	/**
	 * 対応済合計件数を設定します。
	 *
	 * @param sumCompleteCount 対応済合計件数
	 */
	public void setSumCompleteCount(BigDecimal sumCompleteCount) {
		this.sumCompleteCount = sumCompleteCount;
	}

	/**
	 * 未読件数を取得します。
	 *
	 * @return 未読件数
	 */
	public BigDecimal getUnreadCount() {
		return unreadCount;
	}
	/**
	 * 未読件数を設定します。
	 *
	 * @param unreadCount 未読件数
	 */
	public void setUnreadCount(BigDecimal unreadCount) {
		this.unreadCount = unreadCount;
	}

	/**
	 * 集計開始日を取得します。
	 *
	 * @return 集計開始日
	 */
	public Date getSummaryStartDt() {
		return summaryStartDt;
	}
	/**
	 * 集計開始日を設定します。
	 *
	 * @param summaryStartDt 集計開始日
	 */
	public void setSummaryStartDt(Timestamp summaryStartDt) {
		this.summaryStartDt = summaryStartDt;
	}

	/**
	 * 集計終了日を取得します。
	 *
	 * @return 集計終了日
	 */
	public Timestamp getSummaryEndDt() {
		return summaryEndDt;
	}
	/**
	 * 集計終了日を設定します。
	 *
	 * @param summaryEndDt 集計終了日
	 */
	public void setSummaryEndDt(Timestamp summaryEndDt) {
		this.summaryEndDt = summaryEndDt;
	}

	/**
	 * 顧客情報を取得します。
	 *
	 * @return 顧客情報
	 */
	public RcpMKokyaku getKokyaku() {
		return kokyaku;
	}
	/**
	 * 顧客情報を設定します。
	 *
	 * @param kokyaku 顧客情報
	 */
	public void setKokyaku(RcpMKokyaku kokyaku) {
		this.kokyaku = kokyaku;
	}

	/**
	 * 問い合わせ受付履歴リストを取得します。
	 *
	 * @return 問い合わせ受付履歴リスト
	 */
	public List<InquiryStatus> getInquiryHistoryList() {
		return inquiryHistoryList;
	}
	/**
	 * 問い合わせ受付履歴リストを設定します。
	 *
	 * @param inquiryHistoryList 問い合わせ受付履歴リスト
	 */
	public void setInquiryHistoryList(List<InquiryStatus> inquiryHistoryList) {
		this.inquiryHistoryList = inquiryHistoryList;
	}

	/**
	 * 対応中案件リストを取得します。
	 *
	 * @return 対応中案件リスト
	 */
	public List<RcpTIrai> getRequestList() {
		return requestList;
	}
	/**
	 * 対応中案件リストを設定します。
	 *
	 * @param requestList 対応中案件リスト
	 */
	public void setRequestList(List<RcpTIrai> requestList) {
		this.requestList = requestList;
	}

	/**
	 * 業者情報を取得します。
	 *
	 * @return 業者情報
	 */
	public RcpMGyosha getGyosha() {
		return gyosha;
	}
	/**
	 * 業者情報を設定します。
	 *
	 * @param gyosha 業者情報
	 */
	public void setGyosha(RcpMGyosha gyosha) {
		this.gyosha = gyosha;
	}

	/**
	 * お知らせリストを取得します。
	 *
	 * @return お知らせリスト
	 */
	public List<TbTInformation> getInfomationList() {
		return infomationList;
	}
	/**
	 * お知らせリストを設定します。
	 *
	 * @param infomationList お知らせリスト
	 */
	public void setInfomationList(List<TbTInformation> infomationList) {
		this.infomationList = infomationList;
	}

	/**
	 * 祝日リストを取得します。
	 *
	 * @return 祝日リスト
	 */
	public List<TbMHoliday> getHolidayList() {
		return holidayList;
	}
	/**
	 * 祝日リストを設定します。
	 *
	 * @param holidayList 祝日リスト
	 */
	public void setHolidayList(List<TbMHoliday> holidayList) {
		this.holidayList = holidayList;
	}

	/**
	 * ユーザーコンテキストを取得します。
	 *
	 * @return ユーザーコンテキスト
	 */
	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}
	/**
	 * ユーザーコンテキストを設定します。
	 *
	 * @param userContext ユーザーコンテキスト
	 */
	public void setUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * 問い合わせ検索条件を取得します。
	 *
	 * @return 問い合わせ検索条件
	 */
	public RC031ToiawaseSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 問い合わせ検索条件を設定します。
	 *
	 * @param condition 問い合わせ検索条件
	 */
	public void setCondition(RC031ToiawaseSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * ユーザーエージェントを取得します。
	 *
	 * @return ユーザーエージェント
	 */
	public String getUserAgent() {
		return userAgent;
	}
	/**
	 * ユーザーエージェントを設定します。
	 *
	 * @param userAgent ユーザーエージェント
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * 選択した顧客IDを取得します。
	 *
	 * @return 選択した顧客ID
	 */
	public String getSelectedKokyakuId() {
		return selectedKokyakuId;
	}
	/**
	 * 選択した顧客IDを設定します。
	 *
	 * @param selectedKokyakuId 選択した顧客ID
	 */
	public void setSelectedKokyakuId(String selectedKokyakuId) {
		this.selectedKokyakuId = selectedKokyakuId;
	}

	/**
	 * 公開ファイルEntityリストを取得します。
	 *
	 * @return publishFileList
	 */
	public List<TbTPublishFile> getPublishFileList() {
		return publishFileList;
	}
	/**
	 * 公開ファイルEntityリストを設定します。
	 *
	 * @param publishFileList セットする publishFileList
	 */
	public void setPublishFileList(List<TbTPublishFile> publishFileList) {
		this.publishFileList = publishFileList;
	}

	/**
	 * 会社情報を取得します。
	 *
	 * @return 会社情報
	 */
	public TbMKaisha getKaisha() {
		return kaisha;
	}
	/**
	 * 会社情報を設定します。
	 *
	 * @param kaisha 会社情報
	 */
	public void setKaisha(TbMKaisha kaisha) {
		this.kaisha = kaisha;
	}

	/**
	 * 委託会社参照顧客情報リストを取得します。
	 *
	 * @return 委託会社参照顧客情報リスト
	 */
	public List<TbMItakuRefKokyaku> getItakuRefKokyakuList() {
		return itakuRefKokyakuList;
	}
	/**
	 * 委託会社参照顧客情報リストを設定します。
	 *
	 * @param itakuRefKokyakuList 委託会社参照顧客情報リスト
	 */
	public void setItakuRefKokyakuList(List<TbMItakuRefKokyaku> itakuRefKokyakuList) {
		this.itakuRefKokyakuList = itakuRefKokyakuList;
	}

	/**
	 * 再表示タイマーを取得します。
	 * @return 再表示タイマー
	 */
	public String getReloadTime() {
		return reloadTime;
	}

	/**
	 * 再表示タイマーを設定します。
	 * @param reloadTime 再表示タイマー
	 */
	public void setReloadTime(String reloadTime) {
		this.reloadTime = reloadTime;
	}
	/**
	 * 全対応件数の合計を取得します。
	 *
	 * @return 全対応件数の合計
	 */
	public BigDecimal getSumCorrespondence() {
		return this.sumIncompleteCount.add(this.sumCompleteCount);
	}

	/**
	 * お知らせの最新表示日数を取得します。
	 *
	 * @return お知らせの最新表示日数
	 */
	public Integer getNewArrivalNoticeDate() {
		return this.userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_NEW_INFORMATION_NOTICE_DATE);
	}

	/**
	 * ヘルプ画面のURLを取得します。
	 *
	 * @return ヘルプ画面のURL
	 */
	public String getHelpUrl() throws Exception {
		try {
			if (this.userContext.isInhouse()) {
				// ログインユーザーの権限が社内の場合
				return this.userContext.getSystgemContstantAsString(
						RcpMSystem.RCP_M_SYSTEM_B2B_ADMINISTRATOR_HELP_URL);
			} else if (this.userContext.isRealEstate()) {
				// ログインユーザーの権限が管理会社の場合
				return this.userContext.getSystgemContstantAsString(
						RcpMSystem.RCP_M_SYSTEM_B2B_REAL_ESTATE_HELP_URL);
			} else if (this.userContext.isConstractor()) {
				// ログインユーザーの権限が業者の場合
				return this.userContext.getSystgemContstantAsString(
						RcpMSystem.RCP_M_SYSTEM_B2B_CONTRACTOR_HELP_URL);
			} else if (this.userContext.isOutsourcerSv() || this.userContext.isOutsourcerOp()) {
				// ログインユーザーの権限が委託会社SV、委託会社OPの場合
				return this.userContext.getSystgemContstantAsString(
						RcpMSystem.RCP_M_SYSTEM_B2B_OUTSOURCER_HELP_URL);
			} else {
				// 上記以外の場合
				throw new ApplicationException("ログインユーザーの権限が不正です。");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * システム時間を取得します。
	 *
	 * @return システム時間
	 */
	public String getSystemTime() {
		return DateUtil.getSysDateString("yyyy/MM/dd HH:mm");
	}

	/**
	 * お知らせ情報に表示する連絡先ユーザー名を取得します。
	 *
	 * @return 連絡先ユーザー名
	 */
	public String getInformationUserName() {
		if (this.userContext == null) {
			return "";
		}

		if (this.userContext.isInhouse()) {
			// ログインユーザーの権限が社内の場合
			return this.userContext.getUserName();
		}

		if (this.userContext.isRealEstate()) {
			// ログインユーザーの権限が不動産・管理会社の場合
			return (this.kokyaku != null) ? kokyaku.getKanjiNm() : "";
		}

		if (this.userContext.isOutsourcerSv() || this.userContext.isOutsourcerOp()) {
			// ログインユーザーの権限が委託会社SV、委託会社OPの場合
			return (this.kaisha != null) ? this.kaisha.getKaishaNm() : "";
		}
		
		// ログインユーザーの権限が依頼業者の場合
		return (this.gyosha != null) ? this.gyosha.getGyoshaNm() : "";
	}

	/**
	 * 委託会社参照顧客情報が存在するかを判定します。
	 *
	 * @return true:存在する
	 */
	public boolean existsItakuRefKokyaku() {
		if (itakuRefKokyakuList == null || itakuRefKokyakuList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * iPadでログインしているかを判定します。
	 *
	 * @return true:iPadでログイン、false:それ以外
	 */
	public boolean isIPad() {
		if (StringUtils.isBlank(getUserAgent())) {
			return false;
		}

		return getUserAgent().indexOf(Constants.USER_AGENT_KEY_IPAD) != -1;
	}

}
