package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMGrpKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;

/**
 * 管理対象一覧モデル。
 *
 * @author v140546
 * @version 4.0 2014/09/25
 */
public class TB010ManagedTargetListModel {

	/** 選択された顧客ID */
	String selectedKokyakuId;

	/** グループ顧客マスタリスト */
	List<TbMGrpKokyaku> grpKokyakuList;

	/** 参照顧客マスタリスト */
	private  List<TbMRefKokyaku> refKokyakuList;

	/** アクセス可能URLMap */
	private Map<String, TbMRoleUrl> accessibleMap;

	/** 顧客マスタ情報 */
	private RcpMKokyaku kokyakuInfo;
	
	/**
	 * 選択された顧客IDを取得します。
	 *
	 * @return selectedKokyakuId
	 */
	public String getSelectedKokyakuId() {
		return selectedKokyakuId;
	}
	/**
	 * 選択された顧客IDを設定します。
	 *
	 * @param selectedKokyakuId セットする selectedKokyakuId
	 */
	public void setSelectedKokyakuId(String selectedKokyakuId) {
		this.selectedKokyakuId = selectedKokyakuId;
	}

	/**
	 * グループ顧客マスタリストを取得します。
	 *
	 * @return grpKokyakuList
	 */
	public List<TbMGrpKokyaku> getGrpKokyakuList() {
		return grpKokyakuList;
	}

	/**
	 * グループ顧客マスタリストを設定します。
	 *
	 * @param grpKokyakuList セットする grpKokyakuList
	 */
	public void setGrpKokyakuList(List<TbMGrpKokyaku> grpKokyakuList) {
		this.grpKokyakuList = grpKokyakuList;
	}

	/**
	 * 参照顧客マスタリストを取得します。
	 *
	 * @return refKokyakuList
	 */
	public List<TbMRefKokyaku> getRefKokyakuList() {
		return refKokyakuList;
	}
	/**
	 * 参照顧客マスタリストを設定します。
	 *
	 * @param refKokyakuList セットする refKokyakuList
	 */
	public void setRefKokyakuList(List<TbMRefKokyaku> refKokyakuList) {
		this.refKokyakuList = refKokyakuList;
	}

	/**
	 * アクセス可能URLMapを取得します。
	 *
	 * @return accessibleMap
	 */
	public Map<String, TbMRoleUrl> getAccessibleMap() {
		return accessibleMap;
	}
	/**
	 * アクセス可能URLMapを設定します。
	 *
	 * @param accessibleMap セットする accessibleMap
	 */
	public void setAccessibleMap(Map<String, TbMRoleUrl> accessibleMap) {
		this.accessibleMap = accessibleMap;
	}

	/**
	 * 顧客マスタ情報を取得します。
	 *
	 * @return 顧客マスタ情報
	 */
	public RcpMKokyaku getKokyakuInfo() {
		return kokyakuInfo;
	}
	/**
	 * 顧客マスタ情報を設定します。
	 *
	 * @param kokyakuInfo 顧客マスタ情報
	 */
	public void setKokyakuInfo(RcpMKokyaku kokyakuInfo) {
		this.kokyakuInfo = kokyakuInfo;
	}
}
