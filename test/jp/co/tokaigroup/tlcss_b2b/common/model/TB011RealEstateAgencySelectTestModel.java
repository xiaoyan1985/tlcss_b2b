package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;

/**
 * 不動産・管理会社選択画面（スタブ）モデル。
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
public class TB011RealEstateAgencySelectTestModel {
	/** 検索条件 */
	private RC011KokyakuSearchCondition condition = new RC011KokyakuSearchCondition();
	/** 顧客情報リスト */
	private List<RcpMKokyaku> kokyakuList;

	// 選択用パラメータ
	/** 顧客ＩＤラベルname属性名 */
	private String kokyakuIdResultNm;
	/** 顧客名ラベルname属性名 */
	private String kokyakuNmResultNm;

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
	 * 顧客情報リストを取得します。
	 *
	 * @return 顧客情報リスト
	 */
	public List<RcpMKokyaku> getKokyakuList() {
		return kokyakuList;
	}
	/**
	 * 顧客情報リストを設定します。
	 *
	 * @param kokyakuList 顧客情報リスト
	 */
	public void setKokyakuList(List<RcpMKokyaku> kokyakuList) {
		this.kokyakuList = kokyakuList;
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
}
