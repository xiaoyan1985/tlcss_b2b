package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import jp.co.tokaigroup.reception.dto.RC041IraiSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * 依頼検索画面モデル。
 *
 * @author k002849
 * @version 1.0 2014/05/09
 */
public class TB031RequestSearchModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "依頼検索";

	/** 検索条件 */
	private RC041IraiSearchCondition condition = new RC041IraiSearchCondition();

	/** 検索結果リスト */
	private List<RC041IraiSearchDto> resultList;
	/** サービスリスト */
	private List<RcpMService> serviceList;

	/** 前回締め年月 */
	private String zenkaiShoriYm;

	/** 状況リスト */
	private List<RcpMComCd> jokyoList;

	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public RC041IraiSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(RC041IraiSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 検索結果リストを取得します。
	 *
	 * @return 検索結果リスト
	 */
	public List<RC041IraiSearchDto> getResultList() {
		return resultList;
	}
	/**
	 * 検索結果リストを設定します。
	 *
	 * @param resultList 検索結果リスト
	 */
	public void setResultList(List<RC041IraiSearchDto> resultList) {
		this.resultList = resultList;
	}

	/**
	 * サービスリストを取得します。
	 *
	 * @return サービスリスト
	 */
	public List<RcpMService> getServiceList() {
		return serviceList;
	}
	/**
	 * サービスリストを設定します。
	 *
	 * @param serviceList サービスリスト
	 */
	public void setServiceList(List<RcpMService> serviceList) {
		this.serviceList = serviceList;
	}

	/**
	 * 前回締め年月を取得します。
	 *
	 * @return 前回締め年月
	 */
	public String getZenkaiShoriYm() {
		return zenkaiShoriYm;
	}
	/**
	 * 前回締め年月を設定します。
	 *
	 * @param zenkaiShoriYm 前回締め年月
	 */
	public void setZenkaiShoriYm(String zenkaiShoriYm) {
		this.zenkaiShoriYm = zenkaiShoriYm;
	}

	/**
	 * 状況リストを取得します。
	 *
	 * @return 状況リスト
	 */
	public List<RcpMComCd> getJokyoList() {
		return jokyoList;
	}
	/**
	 * 状況リストを設定します。
	 *
	 * @param jokyoList 状況リスト
	 */
	public void setJokyoList(List<RcpMComCd> jokyoList) {
		this.jokyoList = jokyoList;
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
