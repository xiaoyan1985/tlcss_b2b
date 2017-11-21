package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.dto.RC014KeiyakuListDto;
import jp.co.tokaigroup.reception.dto.RC014KeiyakuTargetListDto;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.kokyaku.model.RC014KokyakuKeiyakuListCondition;

/**
 * 契約情報モデル。
 *
 * @author v145527 小林
 * @version 1.0 2015/08/04
 * @version 1.1 2016/08/05 H.Yamamura 契約対象一覧文言を追加
 */

public class TB046ContractInfoModel extends TB040CustomerCommonInfoModel {


	/** 契約一覧 */
	private List<RC014KeiyakuListDto> keiyakuList;
	/** 契約対象一覧 */
	private List<RC014KeiyakuTargetListDto> keiyakuTargetList;

	// 画面パラメータ
	/** 契約ＮＯ */
	private Integer keiyakuNo;
	/** 契約更新日 */
	private Timestamp keiyakuUpdDt;
	/** 契約対象取得用顧客ID */
	private String keiyakuKokyakuId;

	/** 契約者情報 */
	private RcpMKokyaku keiyakuKokyakuInfo;
	
	/** 契約対象一覧 文言 */
	private String keiyakuTaisho;
	
	/**
	 *  画面の検索条件
	 */
	private RC014KokyakuKeiyakuListCondition condition = new RC014KokyakuKeiyakuListCondition();

	/**
	 *  検索結果
	 */
	private List<RC014KeiyakuListDto> result;
	
	/** 選択リンク表示フラグ */
	private String viewSelectLinkFlg;
	
	/** 顧客IDname属性名 */
	private String kokyakuIdResultNm;

	/** 顧客会社名name属性名 */
	private String kokyakuKaishaNmResultNm;

	/** 顧客名name属性名 */
	private String kokyakuNmResultNm;

	/** 顧客住所name属性名 */
	private String kokyakuJushoResultNm;

	/** 電話番号name属性名 */
	private String kokyakuTelResultNm;

	/** FAX番号name属性名 */
	private String kokyakuFaxResultNm;

	/** hidden出力除外項目 */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/**
	 * 契約一覧を取得します。
	 *
	 * @return 契約一覧
	 */
	public List<RC014KeiyakuListDto> getKeiyakuList() {
		return keiyakuList;
	}
	/**
	 * 契約一覧を設定します。
	 *
	 * @param keiyakuList 契約一覧
	 */
	public void setKeiyakuList(List<RC014KeiyakuListDto> keiyakuList) {
		this.keiyakuList = keiyakuList;
	}

	/**
	 * 契約対象一覧を取得します。
	 *
	 * @return 契約対象一覧
	 */
	public List<RC014KeiyakuTargetListDto> getKeiyakuTargetList() {
		return keiyakuTargetList;
	}
	/**
	 * 契約対象一覧を設定します。
	 *
	 * @param keiyakuTargetList 契約対象一覧
	 */
	public void setKeiyakuTargetList(
			List<RC014KeiyakuTargetListDto> keiyakuTargetList) {
		this.keiyakuTargetList = keiyakuTargetList;
	}

	/**
	 * 契約ＮＯを取得します。
	 *
	 * @return 契約ＮＯ
	 */
	public Integer getKeiyakuNo() {
		return keiyakuNo;
	}
	/**
	 * 契約ＮＯを設定します。
	 *
	 * @param keiyakuNo 契約ＮＯ
	 */
	public void setKeiyakuNo(Integer keiyakuNo) {
		this.keiyakuNo = keiyakuNo;
	}

	/**
	 * 契約更新日を取得します。
	 *
	 * @return 契約更新日
	 */
	public Timestamp getKeiyakuUpdDt() {
		return keiyakuUpdDt;
	}
	/**
	 * 契約更新日を設定します。
	 *
	 * @param keiyakuUpdDt 契約更新日
	 */
	public void setKeiyakuUpdDt(Timestamp keiyakuUpdDt) {
		this.keiyakuUpdDt = keiyakuUpdDt;
	}

	/**
	 * 契約顧客IDを取得します。
	 *
	 * @return 契約顧客ID
	 */
	public String getKeiyakuKokyakuId() {
		return keiyakuKokyakuId;
	}
	/**
	 * 契約顧客IDを設定します。
	 *
	 * @param keiyakuKokyakuId 契約顧客ID
	 */
	public void setKeiyakuKokyakuId(String keiyakuKokyakuId) {
		this.keiyakuKokyakuId = keiyakuKokyakuId;
	}

	/**
	 * 契約者情報を取得します。
	 *
	 * @return 契約者情報
	 */
	public RcpMKokyaku getKeiyakuKokyakuInfo() {
		return keiyakuKokyakuInfo;
	}
	/**
	 * 契約者情報を設定します。
	 *
	 * @param keiyakuKokyakuInfo 契約者情報
	 */
	public void setKeiyakuKokyakuInfo(RcpMKokyaku keiyakuKokyakuInfo) {
		this.keiyakuKokyakuInfo = keiyakuKokyakuInfo;
	}

	/**
	 * 契約対象一覧 文言を取得します。
	 * 
	 * @return 契約対象一覧 文言
	 */
	public String getKeiyakuTaisho() {
		return keiyakuTaisho;
	}

	/**
	 * 契約対象一覧 文言を設定します。
	 * 
	 * @param keiyakuTaisho 契約対象一覧 文言
	 */
	public void setKeiyakuTaisho(String keiyakuTaisho) {
		this.keiyakuTaisho = keiyakuTaisho;
	}

	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public RC014KokyakuKeiyakuListCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(RC014KokyakuKeiyakuListCondition condition) {
		this.condition = condition;
	}

	/**
	 * 検索結果を取得します。
	 *
	 * @return 検索結果
	 */
	public List<RC014KeiyakuListDto> getResult() {
		return result;
	}
	/**
	 * 検索結果を設定します。
	 *
	 * @param result 検索結果
	 */
	public void setResult(List<RC014KeiyakuListDto> result) {
		this.result = result;
	}

	/**
	 * 契約対象一覧を表示するかを判定します。
	 *
	 * @return true:表示する、false:表示しない
	 */
	public boolean isDisplayKeiyakuTargetList() {
		return this.keiyakuNo != null;
	}

	/**
	 * 管理開始終了日登録ボタンを表示するかを判定します。
	 *
	 * @return true:表示する、false:表示しない
	 */
	public boolean isDisplayButtonKanriDateEntry() {
		if (!isDisplayKeiyakuTargetList()) {
			// 契約対象一覧が非表示の場合は、表示しない
			return false;
		}

		return (this.keiyakuTargetList != null && this.keiyakuTargetList.isEmpty());
	}

	/**
	 * 契約者情報を表示するかを判定します。
	 *
	 * @return true:契約者情報を表示する、false:契約者情報を表示しない
	 */
	public boolean isKokyakuKeiyakuInfoDisplay() {
		return this.keiyakuKokyakuInfo != null;
	}
	
	/**
	 * hidden出力除外項目をカンマ区切りで取得します。
	 *
	 * @return hidden出力除外項目（カンマ区切り）
	 */
	public String getExcludeField() {
		return StringUtils.join(EXCLUDE_FIELD_NAME, ",");
	}

	/**
	 * 選択リンク表示フラグを取得します。
	 * @return 選択リンク表示フラグ
	 */
	public String getViewSelectLinkFlg() {
		return viewSelectLinkFlg;
	}

	/**
	 * 選択リンク表示フラグを設定します。
	 * @param viewSelectLinkFlg　選択リンク表示フラグ
	 */
	public void setViewSelectLinkFlg(String viewSelectLinkFlg) {
		this.viewSelectLinkFlg = viewSelectLinkFlg;
	}

	/**
	 * @return kokyakuIdResultNm
	 */
	public String getKokyakuIdResultNm() {
		return kokyakuIdResultNm;
	}
	/**
	 * @param kokyakuIdResultNm セットする kokyakuIdResultNm
	 */
	public void setKokyakuIdResultNm(String kokyakuIdResultNm) {
		this.kokyakuIdResultNm = kokyakuIdResultNm;
	}

	/**
	 * @return kokyakuKaishaNmResultNm
	 */
	public String getKokyakuKaishaNmResultNm() {
		return kokyakuKaishaNmResultNm;
	}
	/**
	 * @param kokyakuKaishaNmResultNm セットする kokyakuKaishaNmResultNm
	 */
	public void setKokyakuKaishaNmResultNm(String kokyakuKaishaNmResultNm) {
		this.kokyakuKaishaNmResultNm = kokyakuKaishaNmResultNm;
	}

	/**
	 * @return kokyakuNmResultNm
	 */
	public String getKokyakuNmResultNm() {
		return kokyakuNmResultNm;
	}
	/**
	 * @param kokyakuNmResultNm セットする kokyakuNmResultNm
	 */
	public void setKokyakuNmResultNm(String kokyakuNmResultNm) {
		this.kokyakuNmResultNm = kokyakuNmResultNm;
	}

	/**
	 * @return kokyakuJushoResultNm
	 */
	public String getKokyakuJushoResultNm() {
		return kokyakuJushoResultNm;
	}
	/**
	 * @param kokyakuJushoResultNm セットする kokyakuJushoResultNm
	 */
	public void setKokyakuJushoResultNm(String kokyakuJushoResultNm) {
		this.kokyakuJushoResultNm = kokyakuJushoResultNm;
	}
	/**
	 * @return kokyakuTelResultNm
	 */
	public String getKokyakuTelResultNm() {
		return kokyakuTelResultNm;
	}
	/**
	 * @param kokyakuTelResultNm セットする kokyakuTelResultNm
	 */
	public void setKokyakuTelResultNm(String kokyakuTelResultNm) {
		this.kokyakuTelResultNm = kokyakuTelResultNm;
	}
	/**
	 * @return kokyakuFaxResultNm
	 */
	public String getKokyakuFaxResultNm() {
		return kokyakuFaxResultNm;
	}
	/**
	 * @param kokyakuFaxResultNm セットする kokyakuFaxResultNm
	 */
	public void setKokyakuFaxResultNm(String kokyakuFaxResultNm) {
		this.kokyakuFaxResultNm = kokyakuFaxResultNm;
	}
}