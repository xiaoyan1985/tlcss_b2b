package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;

import jp.co.tokaigroup.reception.dto.RC061GyoshaSearchDto;
import jp.co.tokaigroup.reception.gyosha.model.RC061GyoshaSearchCondition;

/**
 * 業者選択画面（スタブ）モデル。
 *
 * @author k002849
 * @version 1.0 2014/05/29
 */
public class TB012ContractorSelectTestModel {
	/** 検索条件 */
	private RC061GyoshaSearchCondition condition;

	/** 業者リスト */
	private List<RC061GyoshaSearchDto> gyoshaList;

	/** 業者コードラベルname属性名 */
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
	 * 業者リストを取得します。
	 *
	 * @return 業者リスト
	 */
	public List<RC061GyoshaSearchDto> getGyoshaList() {
		return gyoshaList;
	}
	/**
	 * 業者リストを設定します。
	 *
	 * @param gyoshaList 業者リスト
	 */
	public void setGyoshaList(List<RC061GyoshaSearchDto> gyoshaList) {
		this.gyoshaList = gyoshaList;
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
