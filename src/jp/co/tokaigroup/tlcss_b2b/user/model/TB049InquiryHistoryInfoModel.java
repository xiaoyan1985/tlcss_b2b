package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.dto.RC031ToiawaseSearchDto;
import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;

/**
 * 顧客詳細問い合わせ履歴モデル。
 *
 * @author v145527 小林
 * @version 1.0 2015/07/24
 */

public class TB049InquiryHistoryInfoModel extends TB040CustomerCommonInfoModel {
	/**
	 *  画面の検索条件
	 */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();
	
	/** 顧客ID（検索画面からのパラメータ） */
	private String kokyakuId;
	/** 問い合わせNo */
	private String toiawaseNo;

	/**
	 *  検索結果
	 */
	private List<RC031ToiawaseSearchDto> result;

	/** 受付者リスト */
	private List<NatosMPassword> uketsukeshaList;
	/** 問い合わせ区分1リスト */
	private List<RcpMToiawaseKbn1> toiawase1List;
	/** 問い合わせ区分2リスト */
	private List<RcpMToiawaseKbn2> toiawase2List;
	/** 問い合わせ区分3リスト */
	private List<RcpMToiawaseKbn3> toiawase3List;
	/** 問い合わせ区分4リスト */
	private List<RcpMToiawaseKbn4> toiawase4List;
	/** 最終履歴リスト */
	private List<RcpMJokyoKbn> lastRirekiList;
	/** 顧客区分リスト */
	private List<RcpMComCd> kokyakuKbnList;
	/** 依頼有無リスト */
	private List<RcpMComCd> iraiUmuList;
	
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
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public RC031ToiawaseSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(RC031ToiawaseSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 問い合わせNoを取得します。
	 * 
	 * @return 問い合わせNo
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * 問い合わせNoを設定します。
	 * 
	 * @param toiawaseNo 問い合わせNo
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * 検索結果を取得します。
	 *
	 * @return 検索結果
	 */
	public List<RC031ToiawaseSearchDto> getResult() {
		return result;
	}
	/**
	 * 検索結果を設定します。
	 *
	 * @param result 検索結果
	 */
	public void setResult(List<RC031ToiawaseSearchDto> result) {
		this.result = result;
	}

	/**
	 * 受付者リストを取得します。
	 *
	 * @return 受付者リスト
	 */
	public List<NatosMPassword> getUketsukeshaList() {
		return uketsukeshaList;
	}
	/**
	 * 受付者リストを設定します。
	 *
	 * @param uketsukeshaList 受付者リスト
	 */
	public void setUketsukeshaList(List<NatosMPassword> uketsukeshaList) {
		this.uketsukeshaList = uketsukeshaList;
	}

	/**
	 * 問い合わせ区分1リストを取得します。
	 *
	 * @return 問い合わせ区分1リスト
	 */
	public List<RcpMToiawaseKbn1> getToiawase1List() {
		return toiawase1List;
	}
	/**
	 * 問い合わせ区分1リストを設定します。
	 *
	 * @param kokyakuKbnList 問い合わせ区分1リスト
	 */
	public void setToiawase1List(List<RcpMToiawaseKbn1> toiawase1List) {
		this.toiawase1List = toiawase1List;
	}

	/**
	 * 問い合わせ区分2リストを取得します。
	 *
	 * @return 問い合わせ区分2リスト
	 */
	public List<RcpMToiawaseKbn2> getToiawase2List() {
		return toiawase2List;
	}
	/**
	 * 問い合わせ区分2リストを設定します。
	 *
	 * @param toiawase2List 問い合わせ区分2リスト
	 */
	public void setToiawase2List(List<RcpMToiawaseKbn2> toiawase2List) {
		this.toiawase2List = toiawase2List;
	}

	/**
	 * 問い合わせ区分3リストを取得します。
	 *
	 * @return 問い合わせ区分3リスト
	 */
	public List<RcpMToiawaseKbn3> getToiawase3List() {
		return toiawase3List;
	}
	/**
	 * 問い合わせ区分3リストを設定します。
	 *
	 * @param toiawase3List 問い合わせ区分3リスト
	 */
	public void setToiawase3List(List<RcpMToiawaseKbn3> toiawase3List) {
		this.toiawase3List = toiawase3List;
	}

	/**
	 * 問い合わせ区分4リストを取得します。
	 *
	 * @return 問い合わせ区分4リスト
	 */
	public List<RcpMToiawaseKbn4> getToiawase4List() {
		return toiawase4List;
	}
	/**
	 * 問い合わせ区分4リストを設定します。
	 *
	 * @param toiawase4List 問い合わせ区分4リスト
	 */
	public void setToiawase4List(List<RcpMToiawaseKbn4> toiawase4List) {
		this.toiawase4List = toiawase4List;
	}

	/**
	 * 最終履歴リストを取得します。
	 *
	 * @return 最終履歴リスト
	 */
	public List<RcpMJokyoKbn> getLastRirekiList() {
		return lastRirekiList;
	}
	/**
	 * 最終履歴リストを設定します。
	 *
	 * @param lastRirekiList 最終履歴リスト
	 */
	public void setLastRirekiList(List<RcpMJokyoKbn> lastRirekiList) {
		this.lastRirekiList = lastRirekiList;
	}

	/**
	 * 顧客区分リストを取得します。
	 *
	 * @return 顧客区分リスト
	 */
	public List<RcpMComCd> getKokyakuKbnList() {
		return kokyakuKbnList;
	}
	/**
	 * 顧客区分リストを設定します。
	 *
	 * @param kokyakuKbnList 顧客区分リスト
	 */
	public void setKokyakuKbnList(List<RcpMComCd> kokyakuKbnList) {
		this.kokyakuKbnList = kokyakuKbnList;
	}

	/**
	 * 依頼有無リストを取得します。
	 *
	 * @return 依頼有無リスト
	 */
	public List<RcpMComCd> getIraiUmuList() {
		return iraiUmuList;
	}
	/**
	 * 依頼有無リストを設定します。
	 *
	 * @param iraiUmuList 依頼有無リスト
	 */
	public void setIraiUmuList(List<RcpMComCd> iraiUmuList) {
		this.iraiUmuList = iraiUmuList;
	}

	/**
	 * 報告対象フラグが「1：報告する」かを判定します。
	 *
	 * @return true:報告する、false:報告する以外
	 */
	public boolean isHoukokuTarget(int idx) {
		return (RcpTToiawase.HOKOKU_TARGET_FLG_ON.equals(this.getResult().get(idx).getHoukokuTargetFlg().toString()) ? true : false);
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
