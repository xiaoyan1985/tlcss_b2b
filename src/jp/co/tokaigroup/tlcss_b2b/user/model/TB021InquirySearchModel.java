package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.dto.RC031ToiawaseSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * 問い合わせ検索画面モデル。
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 1.1 2016/07/11 H.Yamamura 問い合わせ区分１～４リストを追加
 */
public class TB021InquirySearchModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "問い合わせ検索";

	/** 検索条件 */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();

	/** 検索結果リスト */
	private List<RC031ToiawaseSearchDto> resultList;

	/** 前回締め年月 */
	private String zenkaiShoriYm;

	/** サービスＭリスト */
	private List<RcpMService> serviceMEntityList;

	/** 状況リスト */
	private List<RcpMComCd> jokyoList;
	
	/** 閲覧状況リスト */
	private List<RcpMComCd> browseStatusList;

	/** 問い合わせ区分1リスト */
	private List<RcpMToiawaseKbn1> toiawase1List;

	/** 問い合わせ区分2リスト */
	private List<RcpMToiawaseKbn2> toiawase2List;

	/** 問い合わせ区分3リスト */
	private List<RcpMToiawaseKbn3> toiawase3List;

	/** 問い合わせ区分4リスト */
	private List<RcpMToiawaseKbn4> toiawase4List;

	/** ユーザーエージェント */
	private String userAgent;

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
	 * 検索結果リストを取得します。
	 *
	 * @return 検索結果リスト
	 */
	public List<RC031ToiawaseSearchDto> getResultList() {
		return resultList;
	}
	/**
	 * 検索結果リストを設定します。
	 *
	 * @param resultList 検索結果リスト
	 */
	public void setResultList(List<RC031ToiawaseSearchDto> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 前回締め年月を取得します。
	 *
	 * @return 前回締め年月
	 */
	public String getZenkaiShoriYm() {
		return zenkaiShoriYm;
	}
	/**
	 * 前回締め年月を設定します。
	 *
	 * @param zenkaiShoriYm 前回締め年月
	 */
	public void setZenkaiShoriYm(String zenkaiShoriYm) {
		this.zenkaiShoriYm = zenkaiShoriYm;
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
	 * 状況リストを取得します。
	 *
	 * @return 状況リスト
	 */
	public List<RcpMComCd> getJokyoList() {
		return jokyoList;
	}
	/**
	 * 状況リストを設定します。
	 *
	 * @param jokyoList 状況リスト
	 */
	public void setJokyoList(List<RcpMComCd> jokyoList) {
		this.jokyoList = jokyoList;
	}

	/**
	 * 閲覧状況リストを取得します。
	 *
	 * @return 閲覧状況リスト
	 */
	public List<RcpMComCd> getBrowseStatusList() {
		return browseStatusList;
	}
	/**
	 * 閲覧状況リストを設定します。
	 *
	 * @param browseStatusList 閲覧状況リスト
	 */
	public void setBrowseStatusList(List<RcpMComCd> browseStatusList) {
		this.browseStatusList = browseStatusList;
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
	 * 検索結果を表示するかを判定します。
	 *
	 * @return true:表示する、false:表示しない
	 */
	public boolean isResultDisplay() {
		return !(this.resultList == null || this.resultList.isEmpty());
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

		return getUserAgent().indexOf(Constants.USER_AGENT_KEY_IPAD) != -1;
	}


}
