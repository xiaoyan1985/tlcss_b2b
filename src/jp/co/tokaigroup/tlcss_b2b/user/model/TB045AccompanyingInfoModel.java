package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyakuFBukken;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKanri;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKojin;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFOoya;
import jp.co.tokaigroup.reception.entity.RcpTBukkenShiteiGyosha;

/**
 * 顧客詳細付随情報モデル。
 *
 * @author k003316
 * @version 1.0 2015/08/03
 * @version 1.1 2015/11/04 J.Matsuba 物件指定業者追加
 * @version 1.2 2016/03/28 J.Matsuba 階数、地上階、地下階null判定メソッド追加
 */
public class TB045AccompanyingInfoModel extends TB040CustomerCommonInfoModel {
	
	/** 顧客付随管理会社情報 */
	private RcpMKokyakuFKanri fuzuiKanriInfo;
	/** 顧客付随物件情報 */
	private RcpMKokyakuFBukken fuzuiBukkenInfo;
	/** 物件指定業者テーブルEntityリスト */
	private List<RcpTBukkenShiteiGyosha> bukkenShiteiGyoshaTableList;
	/** 顧客付随大家情報 */
	private RcpMKokyakuFOoya fuzuiOoyaInfo;
	/** 顧客付随個人情報 */
	private RcpMKokyakuFKojin fuzuiKojinInfo;
	
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
	 * 顧客付随管理会社情報を取得します。
	 *
	 * @return 顧客付随管理会社情報
	 */
	public RcpMKokyakuFKanri getFuzuiKanriInfo() {
		return fuzuiKanriInfo;
	}
	/**
	 * 顧客付随管理会社情報を設定します。
	 *
	 * @param fuzuiKanriInfo 顧客付随管理会社情報
	 */
	public void setFuzuiKanriInfo(RcpMKokyakuFKanri fuzuiKanriInfo) {
		this.fuzuiKanriInfo = fuzuiKanriInfo;
	}

	/**
	 * 顧客付随物件情報を取得します。
	 *
	 * @return 顧客付随物件情報
	 */
	public RcpMKokyakuFBukken getFuzuiBukkenInfo() {
		return fuzuiBukkenInfo;
	}
	/**
	 * 顧客付随物件情報を設定します。
	 *
	 * @param fuzuiBukkenInfo 顧客付随物件情報
	 */
	public void setFuzuiBukkenInfo(RcpMKokyakuFBukken fuzuiBukkenInfo) {
		this.fuzuiBukkenInfo = fuzuiBukkenInfo;
	}

	/**
	 * 物件指定業者テーブルEntityリストを取得します。
	 *
	 * @return 物件指定業者テーブルEntityリスト
	 */
	public List<RcpTBukkenShiteiGyosha> getBukkenShiteiGyoshaTableList() {
		return bukkenShiteiGyoshaTableList;
	}
	/**
	 * 物件指定業者テーブルEntityリストを設定します。
	 *
	 * @param bukkenShiteiGyoshaTableList 物件指定業者テーブルEntityリスト
	 */
	public void setBukkenShiteiGyoshaTableList(List<RcpTBukkenShiteiGyosha> bukkenShiteiGyoshaTableList) {
		this.bukkenShiteiGyoshaTableList = bukkenShiteiGyoshaTableList;
	}

	/**
	 * 顧客付随大家情報を取得します。
	 *
	 * @return 顧客付随大家情報
	 */
	public RcpMKokyakuFOoya getFuzuiOoyaInfo() {
		return fuzuiOoyaInfo;
	}
	/**
	 * 顧客付随大家情報を設定します。
	 *
	 * @param fuzuiOoyaInfo 顧客付随大家情報
	 */
	public void setFuzuiOoyaInfo(RcpMKokyakuFOoya fuzuiOoyaInfo) {
		this.fuzuiOoyaInfo = fuzuiOoyaInfo;
	}

	/**
	 * 顧客付随個人情報を取得します。
	 *
	 * @return 顧客付随個人情報
	 */
	public RcpMKokyakuFKojin getFuzuiKojinInfo() {
		return fuzuiKojinInfo;
	}
	/**
	 * 顧客付随個人情報を設定します。
	 *
	 * @param fuzuiKojinInfo 顧客付随個人情報
	 */
	public void setFuzuiKojinInfo(RcpMKokyakuFKojin fuzuiKojinInfo) {
		this.fuzuiKojinInfo = fuzuiKojinInfo;
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

	/**
	 * 物件指定業者テーブルEntityリストが空か判定する。
	 *
	 * @return true：空である
	 */
	public boolean isEmptyBukkenShiteiGyoshaTableList() {
		return bukkenShiteiGyoshaTableList == null || bukkenShiteiGyoshaTableList.isEmpty();
	}

	/**
	 * 階数が空か否かを判定する。
	 *
	 * @return true:空である
	 */
	public boolean isEmptyKaisu() {
		if (fuzuiBukkenInfo == null) {
			return true;
		}

		return fuzuiBukkenInfo.getKaisu() == null ? true : false;
	}

	/**
	 * 階数　地上階が空か否かを判定する。
	 *
	 * @return true:空である
	 */
	public boolean isEmptyChijoKaisu() {
		if (fuzuiBukkenInfo == null) {
			return true;
		}

		return fuzuiBukkenInfo.getChijoKaisu() == null ? true : false;
	}

	/**
	 * 階数　地下階が空か否かを判定する。
	 *
	 * @return true:空である
	 */
	public boolean isEmptyChikaKaisu() {
		if (fuzuiBukkenInfo == null) {
			return true;
		}

		return fuzuiBukkenInfo.getChikaKaisu() == null ? true : false;
	}
}
