package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.gyosha.model.RC061GyoshaSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * センター業者詳細画面モデル。
 *
 * @author v145527
 * @version 1.0 2015/10/08
 */
public class TB052CenterContractorDetailModel extends TB000CommonModel {

	/** 業者情報 */
	private RcpMGyosha gyosha;
	
	/** 検索条件 */
	private RC061GyoshaSearchCondition condition = new RC061GyoshaSearchCondition();

	/** 都道府県（地域）リスト */
	private List<RcpMComCd> todofukenAreaList;
	/** 都道府県（地域）マップ */
	private Map<String, String> todofukenAreaMap;

	/** 業者コード */
	private String gyoshaCd;

	/** 遷移元画面区分(他画面から) */
	private String dispKbn;
	/** 業者コードname属性名 */
	private String gyoshaCdResultNm;
	/** 業者名ラベルname属性名 */
	private String gyoshaNmResultNm;
	
	/** 初期表示エラーフラグ */
	private boolean isInitError = false;


	/**
	 * 業者情報を取得します。
	 *
	 * @return 業者情報
	 */
	public RcpMGyosha getGyosha() {
		return gyosha;
	}
	/**
	 * 業者情報を設定します。
	 *
	 * @param gyosha 業者情報
	 */
	public void setGyosha(RcpMGyosha gyosha) {
		this.gyosha = gyosha;
	}
	
	/**
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public RC061GyoshaSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(RC061GyoshaSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 都道府県（地域）リストを取得します。
	 *
	 * @return todofukenAreaList
	 */
	public List<RcpMComCd> getTodofukenAreaList() {
		return todofukenAreaList;
	}
	/**
	 * 都道府県（地域）リストを設定します。
	 *
	 * @param todofukenAreaList 都道府県（地域）リスト
	 */
	public void setTodofukenAreaList(List<RcpMComCd> todofukenAreaList) {
		this.todofukenAreaList = todofukenAreaList;
	}

	/**
	 * 都道府県（地域）マップを取得します。
	 *
	 * @return todofukenAreaMap
	 */
	public Map<String, String> getTodofukenAreaMap() {
		return todofukenAreaMap;
	}
	/**
	 * 都道府県（地域）マップを設定します。
	 *
	 * @param todofukenAreaMap 都道府県（地域）マップ
	 */
	public void setTodofukenAreaMap(Map<String, String> todofukenAreaMap) {
		this.todofukenAreaMap = todofukenAreaMap;
	}

	/**
	 * 業者コードを取得します。
	 *
	 * @return gyoshaCd
	 */
	public String getGyoshaCd() {
		return gyoshaCd;
	}
	/**
	 * 業者コードを設定します。
	 *
	 * @param gyoshaCd 業者コード
	 */
	public void setGyoshaCd(String gyoshaCd) {
		this.gyoshaCd = gyoshaCd;
	}

	/**
	 * 初期表示エラーフラグを取得します。
	 * 
	 * @return isInitError 初期表示エラーフラグ
	 */
	public boolean isInitError() {
		return isInitError;
	}
	/**
	 * 初期表示エラーフラグを設定します。
	 * 
	 * @param isInitError セットする 初期表示エラーフラグ
	 */
	public void setInitError(boolean isInitError) {
		this.isInitError = isInitError;
	}

	/**
	 * 遷移元画面区分を取得します。
	 *
	 * @return 遷移元画面区分
	 */
	public String getDispKbn() {
		return dispKbn;
	}
	/**
	 * 遷移元画面区分を設定します。
	 *
	 * @param dispKbn 遷移元画面区分
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}

	/**
	 * 業者コードname属性名を取得します。
	 *
	 * @return 業者コードname属性名
	 */
	public String getGyoshaCdResultNm() {
		return gyoshaCdResultNm;
	}
	/**
	 * 業者コードname属性名を設定します。
	 *
	 * @param gyoshaCdResultNm 業者コードname属性名
	 */
	public void setGyoshaCdResultNm(String gyoshaCdResultNm) {
		this.gyoshaCdResultNm = gyoshaCdResultNm;
	}

	/**
	 * 業者名ラベルname属性名を取得します。
	 *
	 * @return 業者名ラベルname属性名
	 */
	public String getGyoshaNmResultNm() {
		return gyoshaNmResultNm;
	}
	/**
	 * 業者名ラベルname属性名を設定します。
	 *
	 * @param gyoshaNmResultNm 業者名ラベルname属性名
	 */
	public void setGyoshaNmResultNm(String gyoshaNmResultNm) {
		this.gyoshaNmResultNm = gyoshaNmResultNm;
	}
}
