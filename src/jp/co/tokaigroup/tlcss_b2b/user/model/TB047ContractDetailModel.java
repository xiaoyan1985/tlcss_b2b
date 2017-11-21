package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.dto.RC020KokyakuShosaiKeiyakuShosaiDto;
import jp.co.tokaigroup.reception.entity.RcpMKeiyakuTel;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKBill;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKLife;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKRcp;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKeiyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;

/**
 * 顧客詳細契約詳細情報画面モデル。
 *
 * @author k003858
 * @version 1.0
 * @version 1.1 J.Matsuba 2016/03/24 ビル管理画面表示による追加
 * @version 1.2 2016/07/14 C.Kobayashi ビル管理情報取得のロジック化対応
 */
public class TB047ContractDetailModel extends TB040CustomerCommonInfoModel {
	/** 画面名 */
	public static final String GAMEN_NM = "顧客詳細契約詳細情報";
	/** 管理費振替フラグ - 請求先顧客の管理費で振替 */
	public static final String FURIKAE_KBN_KOKYAKU = "1";
	/** 管理費振替フラグ - 個別管理費で振替 */
	public static final String FURIKAE_KBN_KOBETSU = "2";

	/** 契約顧客ID */
	private String keiyakuKokyakuId;
	/** 顧客契約情報 */
	private RcpMKokyakuKeiyaku keiyakuInfo;
	/** 顧客契約リセプション情報 */
	private RcpMKokyakuKRcp rcpInfo;
	/** 顧客契約ライフサポート情報 */
	private RcpMKokyakuKLife lifeInfo;
	/** 顧客契約ビル管理情報 */
	private RcpMKokyakuKBill billInfo;
	/** サービスマスタ情報 */
	private RcpMService serviceInfo;
	/** 契約電話番号マスタ情報 */
	private RcpMKeiyakuTel keiyakuTelInfo;
	/** ユーザコンテキスト */
	private TLCSSB2BUserContext context;

	/** ビル管理情報取得ＤＴＯ */
	private RC020KokyakuShosaiKeiyakuShosaiDto keiyakuShosaiDto;

	// 画面パラメータ
	/** 契約ＮＯ */
	private BigDecimal keiyakuNo;

	
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
	 * 契約顧客IDを取得します。
	 * @return 契約顧客ID
	 */
	public String getKeiyakuKokyakuId() {
		return keiyakuKokyakuId;
	}

	/**
	 * 契約顧客IDを設定します。
	 * @param keiyakuKokyakuId 契約顧客ID
	 */
	public void setKeiyakuKokyakuId(String keiyakuKokyakuId) {
		this.keiyakuKokyakuId = keiyakuKokyakuId;
	}
	/**
	 * 顧客契約情報を取得します。
	 *
	 * @return 顧客契約情報
	 */
	public RcpMKokyakuKeiyaku getKeiyakuInfo() {
		return keiyakuInfo;
	}
	/**
	 * 顧客契約情報を設定します。
	 *
	 * @param keiyakuInfo 顧客契約情報
	 */
	public void setKeiyakuInfo(RcpMKokyakuKeiyaku keiyakuInfo) {
		this.keiyakuInfo = keiyakuInfo;
	}

	/**
	 * 顧客契約リセプション情報を取得します。
	 *
	 * @return 顧客契約リセプション情報
	 */
	public RcpMKokyakuKRcp getRcpInfo() {
		return rcpInfo;
	}
	/**
	 * 顧客契約リセプション情報を設定します。
	 *
	 * @param rcpInfo 顧客契約リセプション情報
	 */
	public void setRcpInfo(RcpMKokyakuKRcp rcpInfo) {
		this.rcpInfo = rcpInfo;
	}

	/**
	 * 顧客契約ライフサポート情報を取得します。
	 *
	 * @return 顧客契約ライフサポート情報
	 */
	public RcpMKokyakuKLife getLifeInfo() {
		return lifeInfo;
	}
	/**
	 * 顧客契約ライフサポート情報を設定します。
	 *
	 * @param lifeInfo 顧客契約ライフサポート情報
	 */
	public void setLifeInfo(RcpMKokyakuKLife lifeInfo) {
		this.lifeInfo = lifeInfo;
	}

	/**
	 * 顧客契約ビル管理情報を取得します。
	 *
	 * @return 顧客契約ビル管理情報
	 */
	public RcpMKokyakuKBill getBillInfo() {
		return billInfo;
	}
	/**
	 * 顧客契約ビル管理情報を設定します。
	 *
	 * @param billInfo 顧客契約ビル管理情報
	 */
	public void setBillInfo(RcpMKokyakuKBill billInfo) {
		this.billInfo = billInfo;
	}

	/**
	 * ビル管理情報取得ＤＴＯを取得します。
	 * @return ビル管理情報取得ＤＴＯ
	 */
	public RC020KokyakuShosaiKeiyakuShosaiDto getKeiyakuShosaiDto() {
		return keiyakuShosaiDto;
	}
	/**
	 * ビル管理情報取得ＤＴＯを設定します。
	 * @param keiyakuShosaiDto ビル管理情報取得ＤＴＯ
	 */
	public void setKeiyakuShosaiDto(RC020KokyakuShosaiKeiyakuShosaiDto keiyakuShosaiDto) {
		this.keiyakuShosaiDto = keiyakuShosaiDto;
	}

	/**
	 * 契約ＮＯを取得します。
	 *
	 * @return 契約ＮＯ
	 */
	public BigDecimal getKeiyakuNo() {
		return keiyakuNo;
	}
	/**
	 * 契約ＮＯを設定します。
	 *
	 * @param keiyakuNo 契約ＮＯ
	 */
	public void setKeiyakuNo(BigDecimal keiyakuNo) {
		this.keiyakuNo = keiyakuNo;
	}

	/**
	 * ユーザコンテキストを取得します。
	 *
	 * @return ユーザコンテキスト
	 */
	public TLCSSB2BUserContext getContext() {
		return context;
	}
	/**
	 * ユーザコンテキストを設定します。
	 *
	 * @param context ユーザコンテキスト
	 */
	public void setContext(TLCSSB2BUserContext context) {
		this.context = context;
	}

	/**
	 * サービスマスタ情報を取得します。
	 *
	 * @return サービスマスタ情報
	 */
	public RcpMService getServiceInfo() {
		return serviceInfo;
	}
	/**
	 * サービスマスタ情報を設定します。
	 *
	 * @param serviceInfo サービスマスタ情報
	 */
	public void setServiceInfo(RcpMService serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	/**
	 * 契約電話番号マスタ情報を取得します。
	 *
	 * @return 契約電話番号マスタ情報
	 */
	public RcpMKeiyakuTel getKeiyakuTelInfo() {
		return keiyakuTelInfo;
	}
	/**
	 * 契約電話番号マスタ情報を設定します。
	 *
	 * @param keiyakuTelInfo 契約電話番号マスタ情報
	 */
	public void setKeiyakuTelInfo(RcpMKeiyakuTel keiyakuTelInfo) {
		this.keiyakuTelInfo = keiyakuTelInfo;
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
	 * hidden出力除外項目をカンマ区切りで取得します。
	 *
	 * @return hidden出力除外項目（カンマ区切り）
	 */
	public String getExcludeField() {
		return StringUtils.join(EXCLUDE_FIELD_NAME, ",");
	}

	/**
	 * サービスがリセプションかを判定します。
	 *
	 * @return true:リセプション
	 */
	public boolean isReception() {
		if (serviceInfo == null) {
			return false;
		}

		return serviceInfo.isReception();
	}

	/**
	 * サービスがライフサポート２４かを判定します。
	 *
	 * @return true:ライフサポート２４
	 */
	public boolean isLifeSupport24() {
		if (serviceInfo == null) {
			return false;
		}

		return serviceInfo.isLifeSupport24();
	}

	/**
	 * サービスがビル管理かを判定します。
	 *
	 * @return true:ビル管理
	 */
	public boolean isBuildingManagement() {
		if (serviceInfo == null) {
			return false;
		}

		return serviceInfo.isBuildingManagement();
	}

	/**
	 * WELBOXフラグに表示する文字列を取得します。
	 *
	 * @return  WELBOXフラグに表示する文字列
	 */
	public String getWelboxFlgForDisplay() {
		if (this.lifeInfo == null) {
			return "";
		}

		StringBuilder builder = new StringBuilder("");

		if (RcpMKokyakuKLife.WELBOX_FLG_ON.equals(this.lifeInfo.getWelboxFlg())) {
			builder.append("WELBOX（8044＋会員番号）有り");
		}

		return builder.toString();
	}

	/**
	 * 請求方法の表示を画面に表示するかを判定します。
	 *
	 * @return true:表示する
	 */
	public boolean hasAuthority() {
		if (this.context == null) {
			return false;
		}
		if (this.context.isAdministrativeInhouse() || context.isOutsourcerSv()) {
			return true;
		} else {
			return false;
		}
	}
	
}
