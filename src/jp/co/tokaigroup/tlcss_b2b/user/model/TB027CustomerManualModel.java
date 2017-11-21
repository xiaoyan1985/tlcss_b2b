package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import jp.co.tokaigroup.reception.common.model.RC000CommonModel;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuManual;
import jp.co.tokaigroup.tlcss_b2b.dto.TB027CommonManualListDto;

/**
 * 顧客マニュアル一覧画面モデル。
 *
 * @author k002849
 * @version 1.0 2014/03/20
 * @version 1.1 2016/07/07 H.Hirai 複数請求先対応
 */
public class TB027CustomerManualModel extends RC000CommonModel {
	/** 顧客ＩＤ */
	private String kokyakuId;
	/** アップロードファイル名 */
	private String uploadFileNm;

	/** 対象顧客ＩＤ */
	private String targetKokyakuId;

	/** 個別マニュアルリスト */
	private List<RcpTKokyakuManual> kobetsuManualList;
	/** 共通マニュアルＤＴＯリスト */
	private List<TB027CommonManualListDto> commonManualDtoList;

	/** 顧客情報 */
	private RcpMKokyaku kokyaku;
	/** 請求先顧客情報 */
	private List<RcpMKokyaku> seikyusakiKokyakuList;

	/**
	 * 顧客ＩＤを取得します。
	 *
	 * @return 顧客ＩＤ
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}
	/**
	 * 顧客ＩＤを設定します。
	 *
	 * @param kokyakuId 顧客ＩＤ
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}

	/**
	 * アップロードファイル名を取得します。
	 *
	 * @return アップロードファイル名
	 */
	public String getUploadFileNm() {
		return uploadFileNm;
	}
	/**
	 * アップロードファイル名を設定します。
	 *
	 * @param uploadFileNm アップロードファイル名
	 */
	public void setUploadFileNm(String uploadFileNm) {
		this.uploadFileNm = uploadFileNm;
	}

	/**
	 * 対象顧客ＩＤを取得します。
	 *
	 * @return 対象顧客ＩＤ
	 */
	public String getTargetKokyakuId() {
		return targetKokyakuId;
	}

	/**
	 * 対象顧客ＩＤを設定します。
	 *
	 * @param targetKokyakuId 対象顧客ＩＤ
	 */
	public void setTargetKokyakuId(String targetKokyakuId) {
		this.targetKokyakuId = targetKokyakuId;
	}

	/**
	 * 個別マニュアルリストを取得します。
	 *
	 * @return 個別マニュアルリスト
	 */
	public List<RcpTKokyakuManual> getKobetsuManualList() {
		return kobetsuManualList;
	}
	/**
	 * 個別マニュアルリストを設定します。
	 *
	 * @param kobetsuManualList 個別マニュアルリスト
	 */
	public void setKobetsuManualList(List<RcpTKokyakuManual> kobetsuManualList) {
		this.kobetsuManualList = kobetsuManualList;
	}

	/**
	 * 共通マニュアルＤＴＯリストを取得します。
	 * 
	 * @return 共通マニュアルＤＴＯリスト
	 */
	public List<TB027CommonManualListDto> getCommonManualDtoList() {
		return commonManualDtoList;
	}

	/**
	 * 共通マニュアルＤＴＯリストを設定します。
	 * 
	 * @param commonManualDtoList 共通マニュアルＤＴＯリスト
	 */
	public void setCommonManualDtoList(
			List<TB027CommonManualListDto> commonManualDtoList) {
		this.commonManualDtoList = commonManualDtoList;
	}

	/**
	 * 顧客情報を取得します。
	 *
	 * @return 顧客情報
	 */
	public RcpMKokyaku getKokyaku() {
		return kokyaku;
	}
	/**
	 * 顧客情報を設定します。
	 *
	 * @param kokyaku 顧客情報
	 */
	public void setKokyaku(RcpMKokyaku kokyaku) {
		this.kokyaku = kokyaku;
	}

	/**
	 * 請求先顧客情報を取得します。
	 * 
	 * @return 請求先顧客情報
	 */
	public List<RcpMKokyaku> getSeikyusakiKokyakuList() {
		return seikyusakiKokyakuList;
	}

	/**
	 * 請求先顧客情報を設定します。
	 * 
	 * @param seikyusakiKokyakuList 請求先顧客情報
	 */
	public void setSeikyusakiKokyakuList(List<RcpMKokyaku> seikyusakiKokyakuList) {
		this.seikyusakiKokyakuList = seikyusakiKokyakuList;
	}

	/**
	 * 顧客マニュアルリストのデータ件数を取得します。
	 *
	 * @return 顧客マニュアルリストのデータ件数
	 */
	public int getKokyakuManualListSize() {
		if (this.kobetsuManualList == null) {
			return 0;
		}

		return this.kobetsuManualList.size();
	}

	/**
	 * 共通マニュアルを表示するかを判定します。
	 *
	 * @return true:表示する、false:表示しない
	 */
	public boolean isCommonManualDisplay() {
		// 請求先顧客情報の顧客ＩＤがパラメータの顧客ＩＤと異なる場合は、表示
		boolean ret = false;
		for (RcpMKokyaku kokyakuInfo : this.seikyusakiKokyakuList) {
			if (!kokyakuInfo.getKokyakuId().equals(this.kokyakuId)) {
				ret = true;
				break;
			}
		}
		return ret;
	}
}
