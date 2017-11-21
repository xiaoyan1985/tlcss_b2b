package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;

/**
 * 受付一覧印刷画面モデル。
 *
 * @author v140546
 * @version 1.0 2014/07/09
 */
public class TB044ReceptionListPrintModel extends TB040CustomerCommonInfoModel {
	/** 画面名 */
	public static final String GAMEN_NM = "受付一覧印刷";

	/** ユーザーエージェント 検索キー：iPad */
	private static final String USER_AGENT_KEY_IPAD = "iPad";

	/** 顧客ID */
	private String kokyakuId;

	/** 顧客名 */
	private String kokyakuNm;

	/** サービス区分 */
	private String serviceKbn;

	/** 対象期間(FROM) */
	private Timestamp targetDtFrom;

	/** 対象期間(TO) */
	private Timestamp targetDtTo;

	/** 請求先顧客ID */
	private String seikyusakiKokyakuId;

	/** 作成したPDFのURL */
	private String pdfUrl;

	/** サービスＭリスト */
	private List<RcpMService> serviceMEntityList;

	/** 顧客Ｍ */
	private RcpMKokyaku kokyakuMEntity;

	/** ユーザーエージェント */
	private String userAgent;

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
	 * 顧客名を取得します。
	 *
	 * @return 顧客名
	 */
	public String getKokyakuNm() {
		return kokyakuNm;
	}
	/**
	 * 顧客名を設定します。
	 *
	 * @param kokyakuNm 顧客名
	 */
	public void setKokyakuNm(String kokyakuNm) {
		this.kokyakuNm = kokyakuNm;
	}

	/**
	 * サービス区分を取得します。
	 *
	 * @return 顧客名
	 */
	public String getServiceKbn() {
		return serviceKbn;
	}
	/**
	 * サービス区分を設定します。
	 *
	 * @param serviceKbn サービス区分
	 */
	public void setServiceKbn(String serviceKbn) {
		this.serviceKbn = serviceKbn;
	}

	/**
	 * 対象期間(FROM)を取得します。
	 *
	 * @return 対象期間(FROM)
	 */
	public Timestamp getTargetDtFrom() {
		return targetDtFrom;
	}
	/**
	 * 対象期間(FROM)を設定します。
	 *
	 * @param targetDtFrom 対象期間(FROM)
	 */
	public void setTargetDtFrom(Timestamp targetDtFrom) {
		this.targetDtFrom = targetDtFrom;
	}

	/**
	 * 対象期間(TO)を取得します。
	 *
	 * @return 対象期間(TO)
	 */
	public Timestamp getTargetDtTo() {
		return targetDtTo;
	}
	/**
	 * 対象期間(TO)を設定します。
	 *
	 * @param targetDtTo 対象期間(TO)
	 */
	public void setTargetDtTo(Timestamp targetDtTo) {
		this.targetDtTo = targetDtTo;
	}

	/**
	 * 請求先顧客IDを取得します。
	 *
	 * @return 請求先顧客ID
	 */
	public String getSeikyusakiKokyakuId() {
		return seikyusakiKokyakuId;
	}
	/**
	 * 請求先顧客IDを設定します。
	 *
	 * @param seikyusakiKokyakuId 請求先顧客ID
	 */
	public void setSeikyusakiKokyakuId(String seikyusakiKokyakuId) {
		this.seikyusakiKokyakuId = seikyusakiKokyakuId;
	}

	/**
	 * 作成したPDFのURLを設定します。
	 *
	 * @return pdfUrl 作成したPDFのURL
	 */
	public String getPdfUrl() {
		return pdfUrl;
	}

	/**
	 * 作成したPDFのURLを設定します。
	 *
	 * @param pdfUrl 作成したPDFのURL
	 */
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	/**
	 * サービスＭリストを取得します。
	 *
	 * @return サービスＭリスト
	 */
	public List<RcpMService> getServiceMEntityList() {
		return serviceMEntityList;
	}
	/**
	 * サービスＭリストを設定します。
	 *
	 * @param serviceMEntityList サービスＭリスト
	 */
	public void setServiceMEntityList(List<RcpMService> serviceMEntityList) {
		this.serviceMEntityList = serviceMEntityList;
	}

	/**
	 * 顧客Ｍを取得します。
	 *
	 * @return 顧客Ｍ
	 */
	public RcpMKokyaku getKokyakuMEntity() {
		return kokyakuMEntity;
	}
	/**
	 * 顧客Ｍを設定します。
	 *
	 * @param kokyakuMEntity 顧客Ｍ
	 */
	public void setKokyakuMEntity(RcpMKokyaku kokyakuMEntity) {
		this.kokyakuMEntity = kokyakuMEntity;
	}

	/**
	 * ユーザーエージェントを取得します。
	 *
	 * @return ユーザーエージェント
	 */
	public String getUserAgent() {
		return userAgent;
	}
	/**
	 * ユーザーエージェントを設定します。
	 *
	 * @param userAgent ユーザーエージェント
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * iPadでログインしているかを判定します。
	 *
	 * @return true:iPadでログイン、false:それ以外
	 */
	public boolean isIPad() {
		if (StringUtils.isBlank(getUserAgent())) {
			return false;
		}

		return getUserAgent().indexOf(USER_AGENT_KEY_IPAD) != -1;
	}

}
