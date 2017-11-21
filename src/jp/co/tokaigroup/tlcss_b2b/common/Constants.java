package jp.co.tokaigroup.tlcss_b2b.common;


/**
 * 共通の定数クラス。
 * 全体で共通の定数を定義します。
 * コード定数等の、テーブルに固有する値はエンティティに定数を定義して下さい。
 *
 * @author N.Akahori
 * @version 1.0 2012/08/21
 * @version 1.1 2015/10/26 S.Nakano 定数「JOKYO_GAMEN_KBN_～」追加
 * @version 1.2 2015/11/16 S.Nakano 定数「ACTION_TYPE_RESTORE」追加
 */
public class Constants {
	/** デフォルトの検索画面での最大検索表示件数 */
	public static final int MAX_COUNT  = 300;

	/** 1ページあたりの表示件数 */
	public static final int LIMIT = 5;

	/** 1ページ目を表示する */
	public static final int FIRST_PAGE_NO = 1;

	/** 処理区分 insert */
	public static final String ACTION_TYPE_INSERT = "insert";

	/** 処理区分 update */
	public static final String ACTION_TYPE_UPDATE = "update";

	/** 処理区分 delete */
	public static final String ACTION_TYPE_DELETE = "delete";

	/** 処理区分 clear */
	public static final String ACTION_TYPE_CLEAR = "clear";

	/** 処理区分 restore */
	public static final String ACTION_TYPE_RESTORE = "restore";
	
	/** アクセスログに出力しない項目 */
	public static final String[] EXCLUDE_FIELD_NAMES = {"offset", "limit", "maxCount", "count"};

	// ボタン名(よく使用するものを列挙)
	/** ボタン名 登録 */
	public static final String BUTTON_NM_INSERT = "登録";
	/** ボタン名 更新 */
	public static final String BUTTON_NM_UPDATE = "更新";
	/** ボタン名 削除 */
	public static final String BUTTON_NM_DELETE = "削除";
	/** ボタン名 復活 */
	public static final String BUTTON_NM_RESTORE = "復活";
	/** ボタン名 検索 */
	public static final String BUTTON_NM_SEARCH = "検索";
	/** ボタン名 変更 */
	public static final String BUTTON_NM_CHANGE = "変更";
	/** ボタン名 印刷 */
	public static final String BUTTON_NM_PRINT = "印刷";

	/** 状況区分 画面区分 01:問い合わせ登録 */
	public static final String JOKYO_GAMEN_KBN_TOIAWASE_ENTRY = "01";
	/** 状況区分 画面区分 02:依頼登録 */
	public static final String JOKYO_GAMEN_KBN_IRAI_ENTRY = "02";
	/** 状況区分 画面区分 03:問い合わせ履歴登録 */
	public static final String JOKYO_GAMEN_KBN_INQUIRY_HISTORY_ENTRY ="03";
	/** 状況区分 画面区分 99:全画面 */
	public static final String JOKYO_GAMEN_KBN_ZENGAMEN = "99";
	
	/** 画面区分 tb021:問い合わせ検索 */
	public static final String GAMEN_KBN_INQUIRY_SEARCH = "tb021";
	
	/** 画面区分 tb022:問い合わせ内容詳細 */
	public static final String GAMEN_KBN_INQUIRY_DETAIL = "tb022";
	
	/** 画面区分 tb023:問い合わせ登録 */
	public static final String GAMEN_KBN_INQUIRY_ENTRY = "tb023";

	/** 画面区分 tb024:問い合わせ履歴登録 */
	public static final String GAMEN_KBN_INQUIRY_HISTORY_ENTRY = "tb024";
	
	/** 画面区分 tb031:依頼検索 */
	public static final String GAMEN_KBN_REQUEST_SEARCH = "tb031";
	
	/** 画面区分 tb032:依頼内容詳細・作業状況登録 */
	public static final String GAMEN_KBN_REQUEST_DETAIL = "tb032";
	
	/** 画面区分 tb033:依頼登録 */
	public static final String GAMEN_KBN_REQUEST_ENTRY = "tb033";

	/** 画面区分 tb041:顧客（物件・入居者）検索 */
	public static final String GAMEN_KBN_CUSTOMER_SEARCH = "tb041";

	/** 画面区分 tb042:顧客（物件・入居者）詳細 */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL = "tb042";
	
	/** 画面区分 tb045:顧客詳細 付随情報参照 */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL_ACCOMPANYING = "tb045";

	/** 画面区分 tb046:顧客詳細 契約情報参照 */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL_CONTRACT = "tb046";
	
	/** 画面区分 tb047:顧客詳細 契約情報詳細参照 */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL_CONTRACT_DETAIL = "tb047";
	
	/** 画面区分 tb049:顧客詳細 問い合わせ情報参照 */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL_INQUIRY_HISTORY = "tb049";
	
	/** 画面区分 tb050:顧客詳細 依頼履歴情報参照 */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL_REQUEST_HISTORY = "tb050";

	/** ユーザーエージェント 検索キー：iPad */
	public static final String USER_AGENT_KEY_IPAD = "iPad";
	/** ユーザーエージェント 検索キー：Macintosh */
	public static final String USER_AGENT_KEY_MACINTOSH = "Macintosh";

	/** リソース：画像ファイルパス */
	public static final String RESOURCE_IMAGE_PATH = "/images";

	/** 画像ファイル名 アクセス権限エラー */
	public static final String IMAGE_FILENAME_ACCESS_ERROR = "403.png";
	/** 画像ファイル名 ファイルなしエラー */
	public static final String IMAGE_FILENAME_FILE_NOTFOUND = "404.png";
	/** 画像ファイル名 サーバーエラー */
	public static final String IMAGE_FILENAME_SERVER_ERROR = "500.png";

	/** 状況区分 01:問い合わせ登録 */
	public static final String JOKYO_KBN_INQUIRY_ENTRY = "01";
	
	/** TORES ID（パスワードＭ．ログインＩＤ）*/
	public static final String TORES_APL_ID = "TORES_APL";

}