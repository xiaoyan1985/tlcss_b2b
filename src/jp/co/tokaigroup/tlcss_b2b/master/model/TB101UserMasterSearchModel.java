package jp.co.tokaigroup.tlcss_b2b.master.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB101UserMasterSearchCondition;

/**
 * ユーザーマスタ検索画面モデル。
 *
 * @author k002849
 * @version 1.0 2014/05/26
 */
public class TB101UserMasterSearchModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "ユーザーマスタ検索";

	/** 検索条件 */
	private TB101UserMasterSearchCondition condition = new TB101UserMasterSearchCondition();

	/** 検索結果リスト */
	private List<TbMUser> resultList;

	/** 権限リスト */
	private List<RcpMComCd> roleList;

	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public TB101UserMasterSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(TB101UserMasterSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 検索結果リストを取得します。
	 *
	 * @return 検索結果リスト
	 */
	public List<TbMUser> getResultList() {
		return resultList;
	}
	/**
	 * 検索結果リストを設定します。
	 *
	 * @param resultList 検索結果リスト
	 */
	public void setResultList(List<TbMUser> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 権限リストを取得します。
	 *
	 * @return 権限リスト
	 */
	public List<RcpMComCd> getRoleList() {
		return roleList;
	}
	/**
	 * 権限リストを設定します。
	 *
	 * @param roleList 権限リスト
	 */
	public void setRoleList(List<RcpMComCd> roleList) {
		this.roleList = roleList;
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
