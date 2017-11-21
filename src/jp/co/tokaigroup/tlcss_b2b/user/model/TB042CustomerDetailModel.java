package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMKokyakuFBukken;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKojin;
import jp.co.tokaigroup.reception.entity.RcpTBukkenShiteiGyosha;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;

import org.apache.commons.lang.StringUtils;

/**
 * 物件・入居者詳細モデル。
 *
 * @author v138130
 * @version 4.0 2014/06/06
 * @version 4.1 2015/11/06 J.Matsuba 物件指定業者追加
 * @version 4.2 2016/04/07 J.Matsuba 階数、地上階、地下階null判定メソッド追加
 */
public class TB042CustomerDetailModel extends TB040CustomerCommonInfoModel {
	/** 画面名 */
	public static final String GAMEN_NM = "物件・入居者詳細";

	/** 顧客ID（検索画面からのパラメータ） */
	private String kokyakuId;

	/** hidden出力除外項目 */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** 検索条件（検索画面からの保存用パラメータ） */
	private RC011KokyakuSearchCondition condition = new RC011KokyakuSearchCondition();

	/** 顧客付随物件情報マスタEntity */
	private RcpMKokyakuFBukken kokyakuBukkenEntity = new RcpMKokyakuFBukken();

	/** 物件指定業者テーブルEntityリスト */
	private List<RcpTBukkenShiteiGyosha> bukkenShiteiGyoshaTableList;

	/** 顧客付随個人情報マスタEntity */
	private RcpMKokyakuFKojin kokyakuKojinEntity = new RcpMKokyakuFKojin();

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

	/**
	 * setter & getter
	 */

	/**
	 * 顧客IDを取得します。
	 *
	 * @return 顧客ID
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}
	/**
	 * 顧客IDを設定します。
	 *
	 * @param kokyakuId 顧客ID
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}
	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public RC011KokyakuSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(RC011KokyakuSearchCondition condition) {
		this.condition = condition;
	}
	/**
	 * 顧客付随物件情報マスタEntityを取得します。
	 *
	 * @return 顧客付随物件情報マスタEntity
	 */
	public RcpMKokyakuFBukken getKokyakuBukkenEntity() {
		return kokyakuBukkenEntity;
	}
	/**
	 * 顧客付随物件情報マスタEntityを設定します。
	 *
	 * @param bukkenEntity 顧客付随物件情報マスタEntity
	 */
	public void setKokyakuBukkenEntity(RcpMKokyakuFBukken kokyakuBukkenEntity) {
		this.kokyakuBukkenEntity = kokyakuBukkenEntity;
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
	 * 顧客付随個人情報マスタEntityを取得します。
	 *
	 * @return 顧客付随個人情報マスタEntity
	 */
	public RcpMKokyakuFKojin getKokyakuKojinEntity() {
		return kokyakuKojinEntity;
	}
	/**
	 * 顧客付随個人情報マスタEntityを設定します。
	 *
	 * @param kojinEntity 顧客付随個人情報マスタEntity
	 */
	public void setKokyakuKojinEntity(RcpMKokyakuFKojin kokyakuKojinEntity) {
		this.kokyakuKojinEntity = kokyakuKojinEntity;
	}

	/**
	 * 画面用メソッド
	 */

	/**
	 * タイトル名称取得メソッド
	 *
	 * @return
	 *   顧客マスタEntityの顧客区分が「3：物件」の場合:			"物件"
	 *   顧客マスタEntityの顧客区分が「4：入居者・個人」の場合:	"入居者"
	 */
	public String getTitileNm() {
		if (getKokyakuEntity().isKokyakuKbnBukken()) {
			return "物件";
		} else if (getKokyakuEntity().isKokyakuKbnNyukyosha()) {
			return "入居者";
		}
		return "";
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
	 * 階数が空か判定する。
	 *
	 * @return true：空である
	 */
	public boolean isEmptyKaisu() {
		return (kokyakuBukkenEntity == null || kokyakuBukkenEntity.getKaisu() == null);
	}

	/**
	 * 階数　地上階が空か判定する。
	 *
	 * @return true：空である
	 */
	public boolean isEmptyChijoKaisu() {
		return (kokyakuBukkenEntity == null || kokyakuBukkenEntity.getChijoKaisu() == null);
	}

	/**
	 * 階数　地下階が空か判定する。
	 *
	 * @return true：空である
	 */
	public boolean isEmptyChikaKaisu() {
		return (kokyakuBukkenEntity == null || kokyakuBukkenEntity.getChikaKaisu() == null);
	}
}
