package jp.co.tokaigroup.tlcss_b2b.dto;

import java.util.List;

import jp.co.tokaigroup.reception.dto.RC000CommonDto;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuManual;

/**
 * 顧客共通マニュアル情報ＤＴＯ。
 *
 * @author H.Hirai
 * @version 1.0 2016/07/07
 * 
 */
public class TB027CommonManualListDto extends RC000CommonDto {

	/** 顧客ＩＤ */
	private String kokyakuId;

	/** 共通マニュアルリスト */
	private List<RcpTKokyakuManual> commonManualList;

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
	 * 共通マニュアルリストを取得します。
	 * 
	 * @return 共通マニュアルリスト
	 */
	public List<RcpTKokyakuManual> getCommonManualList() {
		return commonManualList;
	}

	/**
	 * 共通マニュアルリストを設定します。
	 * 
	 * @param commonManualList 共通マニュアルリスト
	 */
	public void setCommonManualList(List<RcpTKokyakuManual> commonManualList) {
		this.commonManualList = commonManualList;
	}
}
