package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.reception.entity.TbMUser;

/**
 * ログイン画面モデル。
 *
 * @author k002849
 * @version 2014/04/22
 * @version 1.1 2015/08/25 v145527 委託会社ＩＤ追加対応
 */
public class TB001LoginModel extends TB000CommonModel {
	/** ログイン画面 初期表示処理アクション名 */
	public static final String ACTION_NM_LOGIN_INIT = "loginInit";
	/** パラメータ名 遷移先URL */
	public static final String PARAM_NM_ACTION_URL = "actionURL";

	/** 画面モード 0:メニューからの遷移 */
	public static final int DISP_MODE_FROM_MENU = 0;
	/** 画面モード 1:パスワード期限切れ間近 */
	public static final int DISP_MODE_PASSWORD_LIMIT_NEAR = 1;
	/** 画面モード 2:パスワード期限切れ */
	public static final int DISP_MODE_PASSWORD_LIMIT = 2;

	/** ログインＩＤ */
	private String loginId;
	/** パスワード */
	private String passwd;
	/** 遷移先URL */
	private String actionURL;

	/** ユーザ情報 */
	private TbMUser userInfo;
	/** システム定数Map */
	private Map<String, String> systemConstants;
	/** パスワード残り有効日数 */
	private int validDateCount;

	/** 遷移先パラメータ */
	private String actionParam;

	/** アクセス可能URLMap */
	private Map<String, TbMRoleUrl> accessibleMap;

	/** 参照顧客リスト */
	private List<TbMRefKokyaku> refKokyakuList;

	/** 顧客マスタ情報 */
	private RcpMKokyaku kokyakuInfo;

	/** 委託会社ＩＤ */
	private String kaishaId;


	/**
	 * ログインＩＤを取得します。
	 *
	 * @return ログインＩＤ
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * ログインＩＤを設定します。
	 *
	 * @param loginId ログインＩＤ
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * パスワードを取得します。
	 *
	 * @return パスワード
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * パスワードを設定します。
	 *
	 * @param passwd パスワード
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * 遷移先URLを取得します。
	 *
	 * @return 遷移先URL
	 */
	public String getActionURL() {
		return actionURL;
	}
	/**
	 * 遷移先URLを設定します。
	 *
	 * @param actionURL 遷移先URL
	 */
	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	/**
	 * ユーザ情報を取得します。
	 *
	 * @return ユーザ情報
	 */
	public TbMUser getUserInfo() {
		return userInfo;
	}
	/**
	 * ユーザ情報を設定します。
	 *
	 * @param userInfo ユーザ情報
	 */
	public void setUserInfo(TbMUser userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * システム定数Mapを取得します。
	 *
	 * @return システム定数Map
	 */
	public Map<String, String> getSystemConstants() {
		return systemConstants;
	}
	/**
	 * システム定数Mapを設定します。
	 *
	 * @param systemConstants システム定数Map
	 */
	public void setSystemConstants(Map<String, String> systemConstants) {
		this.systemConstants = systemConstants;
	}

	/**
	 * パスワード残り有効日数を取得します。
	 *
	 * @return パスワード残り有効日数
	 */
	public int getValidDateCount() {
		return validDateCount;
	}
	/**
	 * パスワード残り有効日数を設定します。
	 *
	 * @param validDateCount パスワード残り有効日数
	 */
	public void setValidDateCount(int validDateCount) {
		this.validDateCount = validDateCount;
	}

	/**
	 * 遷移先パラメータを取得します。
	 *
	 * @return 遷移先パラメータ
	 */
	public String getActionParam() {
		return actionParam;
	}
	/**
	 * 遷移先パラメータを設定します。
	 *
	 * @param actionParam 遷移先パラメータ
	 */
	public void setActionParam(String actionParam) {
		this.actionParam = actionParam;
	}

	/**
	 * アクセス可能URLMapを取得します。
	 *
	 * @return アクセス可能URLMap
	 */
	public Map<String, TbMRoleUrl> getAccessibleMap() {
		return accessibleMap;
	}
	/**
	 * アクセス可能URLMapを設定します。
	 *
	 * @param accessibleMap アクセス可能URLMap
	 */
	public void setAccessibleMap(Map<String, TbMRoleUrl> accessibleMap) {
		this.accessibleMap = accessibleMap;
	}

	/**
	 * 参照顧客リストを取得します。
	 *
	 * @return refKokyakuList
	 */
	public List<TbMRefKokyaku> getRefKokyakuList() {
		return refKokyakuList;
	}
	/**
	 * 参照顧客リストを設定します。
	 *
	 * @param refKokyakuList セットする refKokyakuList
	 */
	public void setRefKokyakuList(List<TbMRefKokyaku> refKokyakuList) {
		this.refKokyakuList = refKokyakuList;
	}
	
	/**
	 * 顧客マスタ情報を取得します。
	 *
	 * @return 顧客マスタ情報
	 */
	public RcpMKokyaku getKokyakuInfo() {
		return kokyakuInfo;
	}
	/**
	 * 顧客マスタ情報を設定します。
	 *
	 * @param kokyakuInfo 顧客マスタ情報
	 */
	public void setKokyakuInfo(RcpMKokyaku kokyakuInfo) {
		this.kokyakuInfo = kokyakuInfo;
	}
	
	/**
	 * 委託会社ＩＤを取得します。
	 * 
	 * @return kaishaId
	 */
	public String getKaishaId() {
		return kaishaId;
	}

	/**
	 * 委託会社ＩＤを設定します。
	 * 
	 * @param kaishaId セットする kaishaId
	 */
	public void setKaishaId(String kaishaId) {
		this.kaishaId = kaishaId;
	}
}
