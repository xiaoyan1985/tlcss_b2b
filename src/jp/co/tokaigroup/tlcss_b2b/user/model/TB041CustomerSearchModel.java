package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * 物件・入居者検索画面モデル。
 *
 * @author v138130
 * @version 4.0 2014/06/03
 */
public class TB041CustomerSearchModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "物件・入居者検索";

	/** 検索条件 */
	private RC011KokyakuSearchCondition condition = new RC011KokyakuSearchCondition();

	/** 検索結果リスト */
	private List<RcpMKokyaku> resultList;

	/** サービスリスト */
	private List<RcpMService> serviceList;

	/** 郵便番号検索URL */
	private String yubinNoSearchURL;
	
	/** 選択リンク表示フラグ */
	private String viewSelectLinkFlg;
	
	/** 顧客IDname属性名 */
	private String kokyakuIdResultNm;

	/** 顧客会社名name属性名 */
	private String kokyakuKaishaNmResultNm;

	/** 顧客名name属性名 */
	private String kokyakuNmResultNm;

	/** 顧客住所name属性名 */
	private String kokyakuJushoResultNm;

	/** 電話番号name属性名 */
	private String kokyakuTelResultNm;

	/** FAX番号name属性名 */
	private String kokyakuFaxResultNm;

	/**
	 * setter & getter
	 */

	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public RC011KokyakuSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(RC011KokyakuSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 検索結果リストを取得します。
	 *
	 * @return 検索結果リスト
	 */
	public List<RcpMKokyaku> getResultList() {
		return resultList;
	}
	/**
	 * 検索結果リストを設定します。
	 *
	 * @param resultList 検索結果リスト
	 */
	public void setResultList(List<RcpMKokyaku> resultList) {
		this.resultList = resultList;
	}

	/**
	 * サービスリストを取得します。
	 *
	 * @return 権限リスト
	 */
	public List<RcpMService> getServiceList() {
		return serviceList;
	}
	/**
	 * サービスリストを設定します。
	 *
	 * @param serviceList 権限リスト
	 */
	public void setServiceList(List<RcpMService> serviceList) {
		this.serviceList = serviceList;
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
	 * 選択リンク表示フラグを取得します。
	 * @return 選択リンク表示フラグ
	 */
	public String getViewSelectLinkFlg() {
		return viewSelectLinkFlg;
	}

	/**
	 * 選択リンク表示フラグを設定します。
	 * @param viewSelectLinkFlg　選択リンク表示フラグ
	 */
	public void setViewSelectLinkFlg(String viewSelectLinkFlg) {
		this.viewSelectLinkFlg = viewSelectLinkFlg;
	}

	/**
	 * 選択リンクが表示可能か判定します。
	 * @return true（1の場合、表示可能）
	 */
	public boolean isSelectLinkView(){
		return "1".equals(this.viewSelectLinkFlg);
	}

	/**
	 * @return kokyakuIdResultNm
	 */
	public String getKokyakuIdResultNm() {
		return kokyakuIdResultNm;
	}
	/**
	 * @param kokyakuIdResultNm セットする kokyakuIdResultNm
	 */
	public void setKokyakuIdResultNm(String kokyakuIdResultNm) {
		this.kokyakuIdResultNm = kokyakuIdResultNm;
	}

	/**
	 * @return kokyakuKaishaNmResultNm
	 */
	public String getKokyakuKaishaNmResultNm() {
		return kokyakuKaishaNmResultNm;
	}
	/**
	 * @param kokyakuKaishaNmResultNm セットする kokyakuKaishaNmResultNm
	 */
	public void setKokyakuKaishaNmResultNm(String kokyakuKaishaNmResultNm) {
		this.kokyakuKaishaNmResultNm = kokyakuKaishaNmResultNm;
	}

	/**
	 * @return kokyakuNmResultNm
	 */
	public String getKokyakuNmResultNm() {
		return kokyakuNmResultNm;
	}
	/**
	 * @param kokyakuNmResultNm セットする kokyakuNmResultNm
	 */
	public void setKokyakuNmResultNm(String kokyakuNmResultNm) {
		this.kokyakuNmResultNm = kokyakuNmResultNm;
	}

	/**
	 * @return kokyakuJushoResultNm
	 */
	public String getKokyakuJushoResultNm() {
		return kokyakuJushoResultNm;
	}
	/**
	 * @param kokyakuJushoResultNm セットする kokyakuJushoResultNm
	 */
	public void setKokyakuJushoResultNm(String kokyakuJushoResultNm) {
		this.kokyakuJushoResultNm = kokyakuJushoResultNm;
	}
	/**
	 * @return kokyakuTelResultNm
	 */
	public String getKokyakuTelResultNm() {
		return kokyakuTelResultNm;
	}
	/**
	 * @param kokyakuTelResultNm セットする kokyakuTelResultNm
	 */
	public void setKokyakuTelResultNm(String kokyakuTelResultNm) {
		this.kokyakuTelResultNm = kokyakuTelResultNm;
	}
	/**
	 * @return kokyakuFaxResultNm
	 */
	public String getKokyakuFaxResultNm() {
		return kokyakuFaxResultNm;
	}
	/**
	 * @param kokyakuFaxResultNm セットする kokyakuFaxResultNm
	 */
	public void setKokyakuFaxResultNm(String kokyakuFaxResultNm) {
		this.kokyakuFaxResultNm = kokyakuFaxResultNm;
	}
}
