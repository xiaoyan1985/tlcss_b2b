package jp.co.tokaigroup.tlcss_b2b.master.model;

import java.math.BigDecimal;
import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * お知らせ検索画面モデル。
 *
 * @author v138130
 * @version 4.0 2014/06/18
 */
public class TB103InformationSearchModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "お知らせ検索";

	/** 検索条件 */
	private TB103InformationSearchCondition condition = new TB103InformationSearchCondition();

	/** 検索結果リスト */
	private List<TbTInformation> resultList;

	/** 表示対象用リスト */
	private List<RcpMComCd> targetList;

	/** 連番 */
	private BigDecimal seqNo;

	/**
	 * setter & getter
	 */

	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public TB103InformationSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(TB103InformationSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 検索結果リストを取得します。
	 *
	 * @return 検索結果リスト
	 */
	public List<TbTInformation> getResultList() {
		return resultList;
	}
	/**
	 * 検索結果リストを設定します。
	 *
	 * @param resultList 検索結果リスト
	 */
	public void setResultList(List<TbTInformation> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 表示対象用リストを取得します。
	 *
	 * @return 表示対象用リスト
	 */
	public List<RcpMComCd> getTargetList() {
		return targetList;
	}
	/**
	 * 表示対象用リストを設定します。
	 *
	 * @param serviceList 表示対象用リスト
	 */
	public void setTargetList(List<RcpMComCd> targetList) {
		this.targetList = targetList;
	}

	/**
	 * 連番を取得します。
	 *
	 * @return 連番
	 */
	public BigDecimal getSeqNo() {
		return seqNo;
	}
	/**
	 * 連番を設定します。
	 *
	 * @param seqNo 連番
	 */
	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}


	/**
	 * 検索結果を表示するかを判定します。
	 *
	 * @return true:表示する、false:表示しない
	 */
	public boolean isResultDisplay() {
		return !(this.resultList == null || this.resultList.isEmpty());
	}

}
