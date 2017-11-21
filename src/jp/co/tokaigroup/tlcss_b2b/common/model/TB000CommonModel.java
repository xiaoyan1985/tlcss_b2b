package jp.co.tokaigroup.tlcss_b2b.common.model;

import jp.co.tokaigroup.reception.common.Constants;

import org.apache.commons.lang.StringUtils;

/**
 * 全画面共通モデル。
 *
 * @author k002849
 * @version 1.0 2014/04/22
 */
public class TB000CommonModel {
	/** アクションタイプ */
	private String actionType;

	/**
	 * アクションタイプを取得します。
	 *
	 * @return アクションタイプ
	 */
	public String getActionType() {
		return actionType;
	}
	/**
	 * アクションタイプを設定します。
	 *
	 * @param actionType アクションタイプ
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * アクションタイプが新規登録モードなのかを判定します。
	 *
	 * @return true:新規登録モード、false:それ以外
	 */
	public boolean isInsert() {
		if (StringUtils.isBlank(this.actionType)) {
			return true;
		}

		return (Constants.ACTION_TYPE_INSERT.equals(this.actionType));
	}

	/**
	 * アクションタイプが更新モードなのかを判定します。
	 *
	 * @return true:更新モード、false:それ以外
	 */
	public boolean isUpdate() {
		return (Constants.ACTION_TYPE_UPDATE.equals(this.actionType));
	}

	/**
	 * アクションタイプが削除モードなのかを判定します。
	 *
	 * @return true:削除モード、false:それ以外
	 */
	public boolean isDelete() {
		return (Constants.ACTION_TYPE_DELETE.equals(this.actionType));
	}
}
