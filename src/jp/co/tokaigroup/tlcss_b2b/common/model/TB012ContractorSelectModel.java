package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMGyosha;

/**
 * 業者選択画面モデル。
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
public class TB012ContractorSelectModel extends TB000CommonModel {

	/** 画面名 */
	public static final String GAMEN_NM = "業者選択";

	/** 検索条件 */
	private TB012ContractorSelectCondition condition = new TB012ContractorSelectCondition();

	/** 検索結果リスト */
	private List<RcpMGyosha> resultList;

	/** 業者コードラベルname属性名 */
	private String gyoshaCdResultNm;
	/** 業者名ラベルname属性名 */
	private String gyoshaNmResultNm;


	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public TB012ContractorSelectCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(TB012ContractorSelectCondition condition) {
		this.condition = condition;
	}

	/**
	 * 検索結果リストを取得します。
	 *
	 * @return 検索結果リスト
	 */
	public List<RcpMGyosha> getResultList() {
		return resultList;
	}
	/**
	 * 検索結果リストを設定します。
	 *
	 * @param resultList 検索結果リスト
	 */
	public void setResultList(List<RcpMGyosha> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 業者コードラベルname属性名を取得します。
	 *
	 * @return 業者コードラベルname属性名
	 */
	public String getGyoshaCdResultNm() {
		return gyoshaCdResultNm;
	}
	/**
	 * 業者コードラベルname属性名を設定します。
	 *
	 * @param gyoshaCdResultNm 業者コードラベルname属性名
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
}
