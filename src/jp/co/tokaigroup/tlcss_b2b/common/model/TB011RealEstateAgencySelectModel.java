package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;

/**
 * 管理会社選択画面モデル。
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
public class TB011RealEstateAgencySelectModel extends TB000CommonModel {

	/** 画面名 */
	public static final String GAMEN_NM = "管理会社選択";

	/** 検索条件 */
	private TB011RealEstateAgencySelectCondition condition = new TB011RealEstateAgencySelectCondition();

	/** 検索結果リスト */
	private List<RcpMKokyaku> resultList;

	// 選択用パラメータ
	/** 顧客ＩＤラベルname属性名 */
	private String kokyakuIdResultNm;
	/** 顧客名ラベルname属性名 */
	private String kokyakuNmResultNm;
	/** リスト顧客名ラベルname属性名 */
	private String kokyakuListNmResultNm;

	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public TB011RealEstateAgencySelectCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(TB011RealEstateAgencySelectCondition condition) {
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
	 * 顧客ＩＤラベルname属性名を取得します。
	 *
	 * @return 顧客ＩＤラベルname属性名
	 */
	public String getKokyakuIdResultNm() {
		return kokyakuIdResultNm;
	}
	/**
	 * 顧客ＩＤラベルname属性名を設定します。
	 *
	 * @param kokyakuIdResultNm 顧客ＩＤラベルname属性名
	 */
	public void setKokyakuIdResultNm(String kokyakuIdResultNm) {
		this.kokyakuIdResultNm = kokyakuIdResultNm;
	}

	/**
	 * 顧客名ラベルname属性名を取得します。
	 *
	 * @return 顧客名ラベルname属性名
	 */
	public String getKokyakuNmResultNm() {
		return kokyakuNmResultNm;
	}
	/**
	 * 顧客名ラベルname属性名を設定します。
	 *
	 * @param kokyakuNmResultNm 顧客名ラベルname属性名
	 */
	public void setKokyakuNmResultNm(String kokyakuNmResultNm) {
		this.kokyakuNmResultNm = kokyakuNmResultNm;
	}

	/**
	 * リスト顧客名ラベルname属性名を取得します。
	 *
	 * @return kokyakuListNmResultNm
	 */
	public String getKokyakuListNmResultNm() {
		return kokyakuListNmResultNm;
	}
	/**
	 * リスト顧客名ラベルname属性名を設定します。
	 *
	 * @param kokyakuListNmResultNm セットする kokyakuListNmResultNm
	 */
	public void setKokyakuListNmResultNm(String kokyakuListNmResultNm) {
		this.kokyakuListNmResultNm = kokyakuListNmResultNm;
	}

}
