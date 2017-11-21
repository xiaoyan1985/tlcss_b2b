package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.TbMKaisha;

/**
 * 委託会社選択画面モデル。
 *
 * @author k002785
 * @version 1.0 2015/09/08
 */
public class TB016OutsourcerSelectModel extends TB000CommonModel {

	/** 画面名 */
	public static final String GAMEN_NM = "委託会社選択";

	/** 検索条件 */
	private TB016OutsourcerSelectCondition condition = new TB016OutsourcerSelectCondition();

	/** 検索結果リスト */
	private List<TbMKaisha> resultList;

	/** 会社IDラベルname属性名 */
	private String kaishaIdResultNm;

	/** 会社名ラベルname属性名 */
	private String kaishaNmResultNm;

	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public TB016OutsourcerSelectCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(TB016OutsourcerSelectCondition condition) {
		this.condition = condition;
	}

	/**
	 * 検索結果リストを取得します。
	 *
	 * @return 検索結果リスト
	 */
	public List<TbMKaisha> getResultList() {
		return resultList;
	}
	/**
	 * 検索結果リストを設定します。
	 *
	 * @param resultList 検索結果リスト
	 */
	public void setResultList(List<TbMKaisha> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 会社IDラベルname属性名を取得します。
	 *
	 * @return 会社IDラベルname属性名
	 */
	public String getKaishaIdResultNm() {
		return kaishaIdResultNm;
	}
	/**
	 * 会社IDラベルname属性名を設定します。
	 *
	 * @param kaishaIdResultNm 会社IDラベルname属性名
	 */
	public void setKaishaIdResultNm(String kaishaIdResultNm) {
		this.kaishaIdResultNm = kaishaIdResultNm;
	}

	/**
	 * 会社名ラベルname属性名を取得します。
	 *
	 * @return 会社名ラベルname属性名
	 */
	public String getKaishaNmResultNm() {
		return kaishaNmResultNm;
	}
	/**
	 * 会社名ラベルname属性名を設定します。
	 *
	 * @param kaishaNmResultNm 会社名ラベルname属性名
	 */
	public void setKaishaNmResultNm(String kaishaNmResultNm) {
		this.kaishaNmResultNm = kaishaNmResultNm;
	}
}
