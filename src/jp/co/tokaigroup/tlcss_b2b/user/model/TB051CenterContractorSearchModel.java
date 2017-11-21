package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;
import jp.co.tokaigroup.reception.dto.RC061GyoshaSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMTodofuken;
import jp.co.tokaigroup.reception.gyosha.model.RC061GyoshaSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * センター業者検索画面モデル。
 *
 * @author v145527
 * @version 1.0 2015/09/29
 */
public class TB051CenterContractorSearchModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "センター業者検索";
	
	/** 検索条件 */
	private RC061GyoshaSearchCondition condition = new RC061GyoshaSearchCondition();
	
	/** 検索結果リスト */
	private List<RC061GyoshaSearchDto> resultList;
	
	/** 都道府県リスト */
	private List<RcpMTodofuken> todofukenList;
	/** 業種リスト */
	private List<RcpMComCd> gyoshuList;
	
	/** 郵便番号検索URL */
	private String yubinNoSearchURL;

	/** 遷移元画面区分(他画面から) */
	private String dispKbn;
	/** 業者コードname属性名 */
	private String gyoshaCdResultNm;
	/** 業者名ラベルname属性名 */
	private String gyoshaNmResultNm;

	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public RC061GyoshaSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(RC061GyoshaSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 検索結果を取得します。
	 *
	 * @return 検索結果
	 */
	public List<RC061GyoshaSearchDto> getResultList() {
		return resultList;
	}
	/**
	 * 検索結果を設定します。
	 *
	 * @param resultList 検索結果
	 */
	public void setResultList(List<RC061GyoshaSearchDto> resultList) {
		this.resultList = resultList;
	}
	
	/**
	 * 都道府県リストを取得します。
	 *
	 * @return 都道府県リスト
	 */
	public List<RcpMTodofuken> getTodofukenList() {
		return todofukenList;
	}
	/**
	 * 都道府県リストを設定します。
	 *
	 * @param todofukenList 都道府県リスト
	 */
	public void setTodofukenList(List<RcpMTodofuken> todofukenList) {
		this.todofukenList = todofukenList;
	}

	/**
	 * 業種リストを取得します。
	 *
	 * @return 業種リスト
	 */
	public List<RcpMComCd> getGyoshuList() {
		return gyoshuList;
	}
	/**
	 * 業種リストを設定します。
	 *
	 * @param gyoshuList 業種リスト
	 */
	public void setGyoshuList(List<RcpMComCd> gyoshuList) {
		this.gyoshuList = gyoshuList;
	}
	
	/**
	 * 郵便番号検索URLを取得します。
	 *
	 * @return 郵便番号検索URL
	 */
	public String getYubinNoSearchURL() {
		return yubinNoSearchURL;
	}
	/**
	 * 郵便番号検索URLを設定します。
	 *
	 * @param yubinNoSearchURL 郵便番号検索URL
	 */
	public void setYubinNoSearchURL(String yubinNoSearchURL) {
		this.yubinNoSearchURL = yubinNoSearchURL;
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
	 * 業者コードname属性名を取得します。
	 *
	 * @return 業者コードname属性名
	 */
	public String getGyoshaCdResultNm() {
		return gyoshaCdResultNm;
	}
	/**
	 * 業者コードname属性名を設定します。
	 *
	 * @param gyoshaCdResultNm 業者コードname属性名
	 */
	public void setGyoshaCdResultNm(String gyoshaCdResultNm) {
		this.gyoshaCdResultNm = gyoshaCdResultNm;
	}

	/**
	 * 業者名ラベルname属性名を取得します。
	 *
	 * @return 業者名ラベルname属性名
	 */
	public String getGyoshaNmResultNm() {
		return gyoshaNmResultNm;
	}
	/**
	 * 業者名ラベルname属性名を設定します。
	 *
	 * @param gyoshaNmResultNm 業者名ラベルname属性名
	 */
	public void setGyoshaNmResultNm(String gyoshaNmResultNm) {
		this.gyoshaNmResultNm = gyoshaNmResultNm;
	}

	/**
	 * 検索結果を表示するかを判定します。
	 *
	 * @return true:表示する、false:表示しない
	 */
	public boolean isResultDisplay() {
		return !(this.resultList == null || this.resultList.isEmpty());
	}

	/**
	 * 依頼登録画面からの遷移か判定します。
	 *
	 * @return true:依頼登録画面からの遷移
	 */
	public boolean isFromRequestEntry() {
		return Constants.GAMEN_KBN_REQUEST_ENTRY.equals(this.dispKbn);
	}

	/**
	 * 月曜日チェックボックスの値を判定します。
	 * 
	 * @return true:チェックON
	 */
	public boolean isMondayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiMonday());
		
	}
	
	/**
	 * 火曜日チェックボックスの値を判定します。
	 * 
	 * @return true:チェックON
	 */
	public boolean isTuesdayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiTuesday());
		
	}
	
	/**
	 * 水曜日チェックボックスの値を判定します。
	 * 
	 * @return true:チェックON
	 */
	public boolean isWednesdayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiWednesday());
		
	}
	
	/**
	 * 木曜日チェックボックスの値を判定します。
	 * 
	 * @return true:チェックON
	 */
	public boolean isThursdayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiThursday());
		
	}
	
	/**
	 * 金曜日チェックボックスの値を判定します。
	 * 
	 * @return true:チェックON
	 */
	public boolean isFridayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiFriday());
		
	}
	
	/**
	 * 土曜日チェックボックスの値を判定します。
	 * 
	 * @return true:チェックON
	 */
	public boolean isSaturdayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiSaturday());
		
	}
	
	/**
	 * 日曜日チェックボックスの値を判定します。
	 * 
	 * @return true:チェックON
	 */
	public boolean isSundayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiSunday());
		
	}
	
	/**
	 * 業種チェックボックスの値を判定します。
	 * 
	 * @param idx
	 * @return ture:チェックON
	 */
	public boolean isGyoshuListSelected(int idx) {
		if (this.gyoshuList == null || this.gyoshuList.isEmpty()) {
			return false;
		}
		
		if (this.gyoshuList.size() < idx) {
			return false;
		}
		
		if (this.condition.getGyoshuList() == null || this.condition.getGyoshuList().isEmpty()) {
			return false;
		}
		
		RcpMComCd comCd = this.gyoshuList.get(idx);
		
		return this.condition.getGyoshuList().contains(comCd.getComCd());
	}
	
	/**
	 * 削除済みの業者を含むチェックボックスの値を判定します。
	 * 
	 * @return true:チェックON
	 */
	public boolean isYukoseiChecked() {
		return RC061GyoshaSearchCondition.YUKOSEI_FLG_ON.equals(condition.getYukosei());
	}
		
}
