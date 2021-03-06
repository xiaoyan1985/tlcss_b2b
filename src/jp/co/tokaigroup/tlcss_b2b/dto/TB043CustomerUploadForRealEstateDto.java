package jp.co.tokaigroup.tlcss_b2b.dto;

import javax.validation.constraints.AssertTrue;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.si.fw.validation.constraints.KanaName;
import jp.co.tokaigroup.si.fw.validation.constraints.MailAddress;
import jp.co.tokaigroup.si.fw.validation.constraints.MaxLength;
import jp.co.tokaigroup.si.fw.validation.constraints.Num;
import jp.co.tokaigroup.si.fw.validation.constraints.Time;
import jp.co.tokaigroup.si.fw.validation.constraints.ZenNumMinus;
import jp.co.tokaigroup.si.fw.validation.constraints.Zenhankaku;
import jp.co.tokaigroup.si.fw.validation.constraints.Zenkaku;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 管理情報アップロード(物件)DTO。
 *
 * @author v140546
 * @version 1.0 2014/08/04
 * @version 1.1 2015/10/28 J.Matsuba 項目追加による修正。
 * @version 1.2 2015/11/10 J.Matsuba 項目追加・削除による修正。
 * @version 1.3 2016/02/12 H.Yamamura 部屋番号の入力チェックを全角数字→全角文字に変更
 */
public class TB043CustomerUploadForRealEstateDto extends TB043CSVUploadCommonDto{

	/** 項目数 */
	public static final int ITEM_COUNT = 139;

	/** CSVカラム列（キー） */
	public static final String[] CSV_COLUMNS_KEY = new String[] {
		"kokyakuKbn",
		"kokyakuShubetsu",
		"kanaNm1",
		"kanaNm2",
		"kanjiNm1",
		"kanjiNm2",
		"yubinNo",
		"jusho1",
		"jusho2",
		"jusho3",
		"jusho4",
		"jusho5",
		"roomNo",
		"telNo1",
		"telNo2",
		"faxNo",
		"attention1",
		"attention2",
		"attention3",
		"attention4",
		"attention4StartDt",
		"attention4EndDt",
		"attention5",
		"attention5StartDt",
		"attention5EndDt",
		"attention6",
		"attention6StartDt",
		"attention6EndDt",
		"shimeDay",
		"kozo",
		"kaisu",
		"chikuNengetsu",
		"kosu",
		"tantoshaNm1",
		"tantoshaNm2",
		"kanriKeitaiKbn",
		"renrakusaki1",
		"renrakusaki2",
		"pompMemo",
		"autoLockMemo",
		"mailBox",
		"biko",
		"ooyaNm",
		"ooyaTel",
		"ooyaJusho",
		"ooyaBiko",
		"kyoyoKanriNm",
		"kyoyoKanriEigyobi",
		"kyoyoKanriEigyoJikan",
		"kyoyoKanriTel",
		"kyoyoKanriJikangaiTel",
		"kyoyoKanriMailAddress",
		"kyoyoKanriBiko",
		"kanrininNm",
		"kanrininEigyobi",
		"kanrininEigyoJikan",
		"kanrininTel",
		"kanrininMailAddress",
		"kanrininBiko",
		"keibiGaisha",
		"keibiGaishaTel",
		"keibiGaishaFax",
		"keibiGaishaMailAddress",
		"keibiGaishaBiko",
		"suidoGyosha",
		"suidoGyoshaTel",
		"suidoGyoshaFax",
		"suidoGyoshaMailAddress",
		"suidoGyoshaBiko",
		"kyuHaisuiGyosha",
		"kyuHaisuiGyoshaTel",
		"kyuHaisuiGyoshaFax",
		"kyuHaisuiGyoshaMailAddress",
		"kyuHaisuiGyoshaBiko",
		"gasGaisha",
		"gasGaishaTel",
		"gasGaishaFax",
		"gasGaishaMailAddress",
		"gasGaishaBiko",
		"kyutokiHoshuGyosha",
		"kyutokiHoshuGyoshaTel",
		"kyutokiHoshuGyoshaFax",
		"kyutokiHoshuGyoshaMailAddress",
		"kyutokiHoshuGyoshaBiko",
		"airConHoshuGyosha",
		"airConHoshuGyoshaTel",
		"airConHoshuGyoshaFax",
		"airConHoshuGyoshaMailAddress",
		"airConHoshuGyoshaBiko",
		"denkiHoshuGyosha",
		"denkiHoshuGyoshaTel",
		"denkiHoshuGyoshaFax",
		"denkiHoshuGyoshaMailAddress",
		"denkiHoshuGyoshaBiko",
		"evGaisha",
		"evGaishaTel",
		"evGaishaFax",
		"evGaishaMailAddress",
		"evGaishaBiko",
		"kagiGyosha",
		"kagiGyoshaTel",
		"kagiGyoshaFax",
		"kagiGyoshaMailAddress",
		"kagiGyoshaBiko",
		"shoboHoshu",
		"shoboHoshuTel",
		"shoboHoshuFax",
		"shoboHoshuMailAddress",
		"shoboHoshuBiko",
		"catvGaisha",
		"catvGaishaTel",
		"catvGaishaFax",
		"catvGaishaMailAddress",
		"catvGaishaBiko",
		"shoshuZen",
		"shoshuZenTel",
		"shoshuZenFax",
		"shoshuZenMailAddress",
		"shoshuZenBiko",
		"glassGyosha",
		"glassGyoshaTel",
		"glassGyoshaFax",
		"glassGyoshaMailAddress",
		"glassGyoshaBiko",
		"etc1",
		"etc1Tel",
		"etc1Fax",
		"etc1MailAddress",
		"etc1Biko",
		"etc2",
		"etc2Tel",
		"etc2Fax",
		"etc2MailAddress",
		"etc2Biko",
		"etc3",
		"etc3Tel",
		"etc3Fax",
		"etc3MailAddress",
		"etc3Biko"
	};

	/** CSVカラム列（名前） */
	public static final String[] CSV_COLUMNS_NAME = new String[] {
		"顧客区分",
		"顧客種別（個人/法人）",
		"カナ物件名",
		"（超過した場合のエリア）",
		"物件名",
		"（超過した場合のエリア）",
		"郵便番号",
		"住所１　都道府県",
		"住所２　市区町村",
		"住所３　町/大字",
		"住所４　番地",
		"物件名",
		"部屋番号",
		"電話番号１",
		"電話番号２",
		"ＦＡＸ番号",
		"注意事項１",
		"注意事項２",
		"注意事項３",
		"注意事項４",
		"注意事項４表示開始日",
		"注意事項４表示終了日",
		"注意事項５",
		"注意事項５表示開始日",
		"注意事項５表示終了日",
		"注意事項６",
		"注意事項６表示開始日",
		"注意事項６表示終了日",
		"締め日",
		"構造",
		"階数",
		"築年月",
		"戸数",
		"担当者名１",
		"担当者名２",
		"管理形態",
		"連絡先１",
		"連絡先２",
		"ポンプ室メモ",
		"オートロックメモ",
		"メールＢＯＸ",
		"備考",
		"オーナー名",
		"オーナー電話番号",
		"オーナー住所",
		"オーナー備考",
		"共用部管理",
		"共用部管理営業日",
		"共用部管理営業時間",
		"共用部管理ＴＥＬ",
		"共用部管理時間外ＴＥＬ",
		"共用部管理E-mail",
		"共用部管理備考",
		"管理人名",
		"管理人営業日",
		"管理人営業時間",
		"管理人ＴＥＬ",
		"管理人E-mail",
		"管理人備考",
		"警備会社",
		"警備会社ＴＥＬ",
		"警備会社ＦＡＸ",
		"警備会社E-mail",
		"警備会社備考",
		"水道業者�@",
		"水道業者�@ＴＥＬ",
		"水道業者�@ＦＡＸ",
		"水道業者�@E-mail",
		"水道業者�@備考",
		"給排水業者�Aポンプ",
		"給排水業者�AポンプＴＥＬ",
		"給排水業者�AポンプＦＡＸ",
		"給排水業者�AポンプE-mail",
		"給排水業者�Aポンプ備考",
		"ガス会社",
		"ガス会社ＴＥＬ",
		"ガス会社ＦＡＸ",
		"ガス会社E-mail",
		"ガス会社備考",
		"給湯器保守業者",
		"給湯器保守業者ＴＥＬ",
		"給湯器保守業者ＦＡＸ",
		"給湯器保守業者E-mail",
		"給湯器保守業者備考",
		"エアコン保守業者",
		"エアコン保守業者ＴＥＬ",
		"エアコン保守業者ＦＡＸ",
		"エアコン保守業者E-mail",
		"エアコン保守業者備考",
		"電気保守業者",
		"電気保守業者ＴＥＬ",
		"電気保守業者ＦＡＸ",
		"電気保守業者E-mail",
		"電気保守業者備考",
		"ＥＶ会社",
		"ＥＶ会社ＴＥＬ",
		"ＥＶ会社ＦＡＸ",
		"ＥＶ会社E-mail",
		"ＥＶ会社備考",
		"鍵業者",
		"鍵業者ＴＥＬ",
		"鍵業者ＦＡＸ",
		"鍵業者E-mail",
		"鍵業者備考",
		"消防保守",
		"消防保守ＴＥＬ",
		"消防保守ＦＡＸ",
		"消防保守E-mail",
		"消防保守備考",
		"ＣＡＴＶ会社",
		"ＣＡＴＶ会社ＴＥＬ",
		"ＣＡＴＶ会社ＦＡＸ",
		"ＣＡＴＶ会社E-mail",
		"ＣＡＴＶ会社備考",
		"小修繕会社",
		"小修繕会社ＴＥＬ",
		"小修繕会社ＦＡＸ",
		"小修繕会社E-mail",
		"小修繕会社備考",
		"ガラス業者",
		"ガラス業者ＴＥＬ",
		"ガラス業者ＦＡＸ",
		"ガラス業者E-mail",
		"ガラス業者備考",
		"その他１",
		"その他１ＴＥＬ",
		"その他１ＦＡＸ",
		"その他１E-mail",
		"その他１備考",
		"その他２",
		"その他２ＴＥＬ",
		"その他２ＦＡＸ",
		"その他２E-mail",
		"その他２備考",
		"その他３",
		"その他３ＴＥＬ",
		"その他３ＦＡＸ",
		"その他３E-mail",
		"その他３備考"
	};

	/** 顧客区分：「3：物件」 */
	private static final String KOKYAKU_KBN_BUKKEN = "3：物件";

	/** 顧客種別：「1：法人」 */
	private static final String KOKYAKU_SHUBETSU_HOJIN = "1：法人";

	/**
	 * 顧客区分
	 */
	@NotEmpty
	@MaxLength(max=15)
	private String kokyakuKbn;

	/**
	 * 顧客種別（個人/法人）
	 */
	@NotEmpty
	@MaxLength(max=7)
	private String kokyakuShubetsu;

	/**
	 * カナ物件名
	 */
	@NotEmpty
	@KanaName
	@MaxLength(max=40)
	private String kanaNm1;

	/**
	 * （超過した場合のエリア）
	 */
	@KanaName
	@MaxLength(max=40)
	private String kanaNm2;

	/**
	 * 漢字物件名
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=40)
	private String kanjiNm1;

	/**
	 * （超過した場合のエリア）
	 */
	@Zenkaku
	@MaxLength(max=40)
	private String kanjiNm2;

	/**
	 * 郵便番号
	 */
	@NotEmpty
	@Num
	@MaxLength(max=7)
	private String yubinNo;

	/**
	 * 住所１　都道府県
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=10)
	private String jusho1;

	/**
	 * 住所２　市区町村
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=30)
	private String jusho2;

	/**
	 * 住所３　町/大字
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=30)
	private String jusho3;

	/**
	 * 住所４　番地
	 */
	@NotEmpty
	@ZenNumMinus
	@MaxLength(max=30)
	private String jusho4;

	/**
	 * 物件名
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=40)
	private String jusho5;

	/**
	 * 部屋番号
	 */
	@Zenkaku
	@MaxLength(max=20)
	private String roomNo;

	/**
	 * 電話番号１
	 */
	private String telNo1;

	/**
	 * 電話番号２
	 */
	private String telNo2;

	/**
	 * ＦＡＸ番号
	 */
	private String faxNo;

	/**
	 * 注意事項１
	 */
	private String attention1;

	/**
	 * 注意事項２
	 */
	private String attention2;

	/**
	 * 注意事項３
	 */
	private String attention3;

	/**
	 * 注意事項４
	 */
	private String attention4;

	/**
	 * 注意事項４表示開始日
	 */
	private String attention4StartDt;

	/**
	 * 注意事項４表示終了日
	 */
	private String attention4EndDt;

	/**
	 * 注意事項５
	 */
	private String attention5;

	/**
	 * 注意事項５表示開始日
	 */
	private String attention5StartDt;

	/**
	 * 注意事項５表示終了日
	 */
	private String attention5EndDt;

	/**
	 * 注意事項６
	 */
	private String attention6;

	/**
	 * 注意事項６表示開始日
	 */
	private String attention6StartDt;

	/**
	 * 注意事項６表示終了日
	 */
	private String attention6EndDt;

	/**
	 * 締め日
	 */
	private String shimeDay;

	/**
	 * 構造
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kozo;

	/**
	 * 階数
	 */
	@Num
	@MaxLength(max=3)
	private String kaisu;

	/**
	 * 築年月
	 */
	@Time(pattern="yyyyMM")
	@MaxLength(max=6)
	private String chikuNengetsu;

	/**
	 * 戸数
	 */
	@Num
	@MaxLength(max=5)
	private String kosu;

	/**
	 * 担当者名１
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String tantoshaNm1;

	/**
	 * 担当者名２
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String tantoshaNm2;

	/**
	 * 管理形態区分
	 */
	@MaxLength(max=15)
	private String kanriKeitaiKbn;

	/**
	 * 連絡先１
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String renrakusaki1;

	/**
	 * 連絡先２
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String renrakusaki2;

	/**
	 * ポンプ室メモ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String pompMemo;

	/**
	 * オートロックメモ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String autoLockMemo;

	/**
	 * メールＢＯＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String mailBox;

	/**
	 * 管理形態備考
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String biko;

	/**
	 * オーナー名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String ooyaNm;

	/**
	 * オーナー電話番号
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String ooyaTel;

	/**
	 * オーナー住所
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String ooyaJusho;

	/**
	 * オーナー備考
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String ooyaBiko;

	/**
	 * 共用部管理
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriNm;

	/**
	 * 共用部管理営業日
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriEigyobi;

	/**
	 * 共用部管理営業時間
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriEigyoJikan;

	/**
	 * 共用部管理ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriTel;

	/**
	 * 共用部管理時間外ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriJikangaiTel;

	/**
	 * 共用部管理E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kyoyoKanriMailAddress;

	/**
	 * 共用部管理備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kyoyoKanriBiko;

	/**
	 * 管理人名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininNm;

	/**
	 * 管理人営業日
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininEigyobi;

	/**
	 * 管理人営業時間
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininEigyoJikan;

	/**
	 * 管理人名ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininTel;

	/**
	 * 管理人E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kanrininMailAddress;

	/**
	 * 管理人備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kanrininBiko;

	/**
	 * 警備会社名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String keibiGaisha;

	/**
	 * 警備会社ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String keibiGaishaTel;

	/**
	 * 警備会社ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String keibiGaishaFax;

	/**
	 * 警備会社E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String keibiGaishaMailAddress;

	/**
	 * 警備会社備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String keibiGaishaBiko;

	/**
	 * 水道業者�@
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String suidoGyosha;

	/**
	 * 水道業者�@ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String suidoGyoshaTel;

	/**
	 * 水道業者�@ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String suidoGyoshaFax;

	/**
	 * 水道業者�@E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String suidoGyoshaMailAddress;

	/**
	 * 水道業者�@備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String suidoGyoshaBiko;

	/**
	 * 給排水業者�A
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyuHaisuiGyosha;

	/**
	 * 給排水業者�AＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyuHaisuiGyoshaTel;

	/**
	 * 給排水業者�AＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyuHaisuiGyoshaFax;

	/**
	 * 給排水業者�AE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kyuHaisuiGyoshaMailAddress;

	/**
	 * 給排水業者�A備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kyuHaisuiGyoshaBiko;

	/**
	 * ガス会社名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String gasGaisha;

	/**
	 * ガス会社名ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String gasGaishaTel;

	/**
	 * ガス会社名ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String gasGaishaFax;

	/**
	 * ガス会社名E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String gasGaishaMailAddress;

	/**
	 * ガス会社名備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String gasGaishaBiko;

	/**
	 * 給湯器保守業者名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyutokiHoshuGyosha;

	/**
	 * 給湯器保守業者ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyutokiHoshuGyoshaTel;

	/**
	 * 給湯器保守業者ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyutokiHoshuGyoshaFax;

	/**
	 * 給湯器保守業者E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kyutokiHoshuGyoshaMailAddress;

	/**
	 * 給湯器保守業者備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kyutokiHoshuGyoshaBiko;

	/**
	 * エアコン保守業者名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String airConHoshuGyosha;

	/**
	 * エアコン保守業者ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String airConHoshuGyoshaTel;

	/**
	 * エアコン保守業者ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String airConHoshuGyoshaFax;

	/**
	 * エアコン保守業者E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String airConHoshuGyoshaMailAddress;

	/**
	 * エアコン保守業者備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String airConHoshuGyoshaBiko;

	/**
	 * 電気保守業者名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String denkiHoshuGyosha;

	/**
	 * 電気保守業者ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String denkiHoshuGyoshaTel;

	/**
	 * 電気保守業者ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String denkiHoshuGyoshaFax;

	/**
	 * 電気保守業者E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String denkiHoshuGyoshaMailAddress;

	/**
	 * 電気保守業者備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String denkiHoshuGyoshaBiko;

	/**
	 * ＥＶ会社名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String evGaisha;

	/**
	 * ＥＶ会社名ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String evGaishaTel;

	/**
	 * ＥＶ会社名ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String evGaishaFax;

	/**
	 * ＥＶ会社名E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String evGaishaMailAddress;

	/**
	 * ＥＶ会社名備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String evGaishaBiko;

	/**
	 * 鍵業者名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kagiGyosha;

	/**
	 * 鍵業者名ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kagiGyoshaTel;

	/**
	 * 鍵業者名ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kagiGyoshaFax;

	/**
	 * 鍵業者名E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kagiGyoshaMailAddress;

	/**
	 * 鍵業者名備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kagiGyoshaBiko;

	/**
	 * 消防保守
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoboHoshu;

	/**
	 * 消防保守ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoboHoshuTel;

	/**
	 * 消防保守ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoboHoshuFax;

	/**
	 * 消防保守E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String shoboHoshuMailAddress;

	/**
	 * 消防保守備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String shoboHoshuBiko;

	/**
	 * ＣＡＴＶ会社名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String catvGaisha;

	/**
	 * ＣＡＴＶ会社名ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String catvGaishaTel;

	/**
	 * ＣＡＴＶ会社名ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String catvGaishaFax;

	/**
	 * ＣＡＴＶ会社名E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String catvGaishaMailAddress;

	/**
	 * ＣＡＴＶ会社名備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String catvGaishaBiko;

	/**
	 * 小修繕会社
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoshuZen;

	/**
	 * 小修繕会社ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoshuZenTel;

	/**
	 * 小修繕会社ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoshuZenFax;

	/**
	 * 小修繕会社E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String shoshuZenMailAddress;

	/**
	 * 小修繕会社備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String shoshuZenBiko;

	/**
	 * ガラス業者
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String glassGyosha;

	/**
	 * ガラス業者ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String glassGyoshaTel;

	/**
	 * ガラス業者ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String glassGyoshaFax;

	/**
	 * ガラス業者E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String glassGyoshaMailAddress;

	/**
	 * ガラス業者備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String glassGyoshaBiko;

	/**
	 * その他１
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc1;

	/**
	 * その他１ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc1Tel;

	/**
	 * その他１ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc1Fax;

	/**
	 * その他１E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String etc1MailAddress;

	/**
	 * その他１備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String etc1Biko;

	/**
	 * その他２
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc2;

	/**
	 * その他２ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc2Tel;

	/**
	 * その他２ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc2Fax;

	/**
	 * その他２E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String etc2MailAddress;

	/**
	 * その他２備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String etc2Biko;

	/**
	 * その他３
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc3;

	/**
	 * その他３ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc3Tel;

	/**
	 * その他３ＦＡＸ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc3Fax;

	/**
	 * その他３E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String etc3MailAddress;

	/**
	 * その他３備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String etc3Biko;

	/**
	 * 顧客区分を取得します。
	 *
	 * @return 顧客区分
	 */
	public String getKokyakuKbn() {
		return kokyakuKbn;
	}
	/**
	 * 顧客区分を設定します。
	 *
	 * @param kokyakuKbn 顧客区分
	 */
	public void setKokyakuKbn(String kokyakuKbn) {
		this.kokyakuKbn = kokyakuKbn;
	}

	/**
	 * 顧客種別（個人/法人）を取得します。
	 *
	 * @return 顧客種別（個人/法人）
	 */
	public String getKokyakuShubetsu() {
		return kokyakuShubetsu;
	}
	/**
	 * 顧客種別（個人/法人）を設定します。
	 *
	 * @param kokyakuShubetsu 顧客種別（個人/法人）
	 */
	public void setKokyakuShubetsu(String kokyakuShubetsu) {
		this.kokyakuShubetsu = kokyakuShubetsu;
	}

	/**
	 * カナ物件名を取得します。
	 *
	 * @return カナ物件名
	 */
	public String getKanaNm1() {
		return kanaNm1;
	}
	/**
	 * カナ物件名を設定します。
	 *
	 * @param kanaNm1 カナ物件名
	 */
	public void setKanaNm1(String kanaNm1) {
		this.kanaNm1 = kanaNm1;
	}

	/**
	 * （超過した場合のエリア）を取得します。
	 *
	 * @return （超過した場合のエリア）
	 */
	public String getKanaNm2() {
		return kanaNm2;
	}
	/**
	 * （超過した場合のエリア）を設定します。
	 *
	 * @param kanaNm2 （超過した場合のエリア）
	 */
	public void setKanaNm2(String kanaNm2) {
		this.kanaNm2 = kanaNm2;
	}

	/**
	 * 物件名を取得します。
	 *
	 * @return 物件名
	 */
	public String getKanjiNm1() {
		return kanjiNm1;
	}
	/**
	 * 物件名を設定します。
	 *
	 * @param kanjiNm1 物件名
	 */
	public void setKanjiNm1(String kanjiNm1) {
		this.kanjiNm1 = kanjiNm1;
	}

	/**
	 * （超過した場合のエリア）を取得します。
	 *
	 * @return （超過した場合のエリア）
	 */
	public String getKanjiNm2() {
		return kanjiNm2;
	}
	/**
	 * （超過した場合のエリア）を設定します。
	 *
	 * @param kanjiNm2 （超過した場合のエリア）
	 */
	public void setKanjiNm2(String kanjiNm2) {
		this.kanjiNm2 = kanjiNm2;
	}

	/**
	 * 郵便番号を取得します。
	 *
	 * @return 郵便番号
	 */
	public String getYubinNo() {
		return yubinNo;
	}
	/**
	 * 郵便番号を設定します。
	 *
	 * @param yubinNo 郵便番号
	 */
	public void setYubinNo(String yubinNo) {
		this.yubinNo = yubinNo;
	}

	/**
	 * 住所１　都道府県を取得します。
	 *
	 * @return 住所１　都道府県
	 */
	public String getJusho1() {
		return jusho1;
	}
	/**
	 * 住所１　都道府県を設定します。
	 *
	 * @param jusho1 住所１　都道府県
	 */
	public void setJusho1(String jusho1) {
		this.jusho1 = jusho1;
	}

	/**
	 * 住所２　市区町村を取得します。
	 *
	 * @return 住所２　市区町村
	 */
	public String getJusho2() {
		return jusho2;
	}
	/**
	 * 住所２　市区町村を設定します。
	 *
	 * @param jusho2 住所２　市区町村
	 */
	public void setJusho2(String jusho2) {
		this.jusho2 = jusho2;
	}

	/**
	 * 住所３　町/大字を取得します。
	 *
	 * @return 住所３　町/大字
	 */
	public String getJusho3() {
		return jusho3;
	}
	/**
	 * 住所３　町/大字を設定します。
	 *
	 * @param jusho3 住所３　町/大字
	 */
	public void setJusho3(String jusho3) {
		this.jusho3 = jusho3;
	}

	/**
	 * 住所４　番地を取得します。
	 *
	 * @return 住所４　番地
	 */
	public String getJusho4() {
		return jusho4;
	}
	/**
	 * 住所４　番地を設定します。
	 *
	 * @param jusho4 住所４　番地
	 */
	public void setJusho4(String jusho4) {
		this.jusho4 = jusho4;
	}

	/**
	 * 物件名を取得します。
	 *
	 * @return 物件名
	 */
	public String getJusho5() {
		return jusho5;
	}
	/**
	 * 物件名を設定します。
	 *
	 * @param jusho5 物件名
	 */
	public void setJusho5(String jusho5) {
		this.jusho5 = jusho5;
	}

	/**
	 * 部屋番号を取得します。
	 *
	 * @return 部屋番号
	 */
	public String getRoomNo() {
		return roomNo;
	}

	/**
	 * 部屋番号を設定します。
	 *
	 * @param roomNo 部屋番号
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	/**
	 * 電話番号１を取得します。
	 *
	 * @return 電話番号１
	 */
	public String getTelNo1() {
		return telNo1;
	}
	/**
	 * 電話番号１を設定します。
	 *
	 * @param telNo1 電話番号１
	 */
	public void setTelNo1(String telNo1) {
		this.telNo1 = telNo1;
	}

	/**
	 * 電話番号２を取得します。
	 *
	 * @return 電話番号２
	 */
	public String getTelNo2() {
		return telNo2;
	}
	/**
	 * 電話番号２を設定します。
	 *
	 * @param telNo2 電話番号２
	 */
	public void setTelNo2(String telNo2) {
		this.telNo2 = telNo2;
	}

	/**
	 * ＦＡＸ番号を取得します。
	 *
	 * @return ＦＡＸ番号
	 */
	public String getFaxNo() {
		return faxNo;
	}
	/**
	 * ＦＡＸ番号を設定します。
	 *
	 * @param faxNo ＦＡＸ番号
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * 注意事項１を取得します。
	 *
	 * @return 注意事項１
	 */
	public String getAttention1() {
		return attention1;
	}
	/**
	 * 注意事項１を設定します。
	 *
	 * @param attention1 注意事項１
	 */
	public void setAttention1(String attention1) {
		this.attention1 = attention1;
	}

	/**
	 * 注意事項２を取得します。
	 *
	 * @return 注意事項２
	 */
	public String getAttention2() {
		return attention2;
	}
	/**
	 * 注意事項２を設定します。
	 *
	 * @param attention2 注意事項２
	 */
	public void setAttention2(String attention2) {
		this.attention2 = attention2;
	}

	/**
	 * 注意事項３を取得します。
	 *
	 * @return 注意事項３
	 */
	public String getAttention3() {
		return attention3;
	}
	/**
	 * 注意事項３を設定します。
	 *
	 * @param attention3 注意事項３
	 */
	public void setAttention3(String attention3) {
		this.attention3 = attention3;
	}

	/**
	 * 注意事項４を取得します。
	 *
	 * @return 注意事項４
	 */
	public String getAttention4() {
		return attention4;
	}

	/**
	 * 注意事項４を設定します。
	 *
	 * @param attention4 注意事項４
	 */
	public void setAttention4(String attention4) {
		this.attention4 = attention4;
	}

	/**
	 * 注意事項４表示開始日を取得します。
	 *
	 * @return 注意事項４表示開始日
	 */
	public String getAttention4StartDt() {
		return attention4StartDt;
	}

	/**
	 * 注意事項４表示開始日を設定します。
	 *
	 * @param attention4StartDt 注意事項４表示開始日
	 */
	public void setAttention4StartDt(String attention4StartDt) {
		this.attention4StartDt = attention4StartDt;
	}

	/**
	 * 注意事項４表示終了日を取得します。
	 *
	 * @return 注意事項４表示終了日
	 */
	public String getAttention4EndDt() {
		return attention4EndDt;
	}

	/**
	 * 注意事項４表示終了日を設定します。
	 *
	 * @param attention4EndDt 注意事項４表示終了日
	 */
	public void setAttention4EndDt(String attention4EndDt) {
		this.attention4EndDt = attention4EndDt;
	}

	/**
	 * 注意事項５を取得します。
	 *
	 * @return 注意事項５
	 */
	public String getAttention5() {
		return attention5;
	}

	/**
	 * 注意事項５を設定します。
	 *
	 * @param attention5 注意事項５
	 */
	public void setAttention5(String attention5) {
		this.attention5 = attention5;
	}

	/**
	 * 注意事項５表示開始日を取得します。
	 *
	 * @return 注意事項５表示開始日
	 */
	public String getAttention5StartDt() {
		return attention5StartDt;
	}

	/**
	 * 注意事項５表示開始日を設定します。
	 *
	 * @param attention5StartDt ５表示開始日
	 */
	public void setAttention5StartDt(String attention5StartDt) {
		this.attention5StartDt = attention5StartDt;
	}

	/**
	 * 注意事項５表示終了日を取得します。
	 *
	 * @return 注意事項５表示終了日
	 */
	public String getAttention5EndDt() {
		return attention5EndDt;
	}

	/**
	 * 注意事項５表示終了日を設定します。
	 *
	 * @param attention5EndDt 注意事項５表示終了日
	 */
	public void setAttention5EndDt(String attention5EndDt) {
		this.attention5EndDt = attention5EndDt;
	}

	/**
	 * 注意事項６を取得します。
	 *
	 * @return 注意事項６
	 */
	public String getAttention6() {
		return attention6;
	}

	/**
	 * 注意事項６を設定します。
	 *
	 * @param attention6 注意事項６
	 */
	public void setAttention6(String attention6) {
		this.attention6 = attention6;
	}

	/**
	 * 注意事項６表示開始日を取得します。
	 *
	 * @return 注意事項６表示開始日
	 */
	public String getAttention6StartDt() {
		return attention6StartDt;
	}

	/**
	 * 注意事項６表示開始日を設定します。
	 *
	 * @param attention6StartDt 注意事項６表示開始日
	 */
	public void setAttention6StartDt(String attention6StartDt) {
		this.attention6StartDt = attention6StartDt;
	}

	/**
	 * 注意事項６表示終了日を取得します。
	 *
	 * @return 注意事項６表示終了日
	 */
	public String getAttention6EndDt() {
		return attention6EndDt;
	}

	/**
	 * 注意事項６表示終了日を設定します。
	 *
	 * @param attention6EndDt 注意事項６表示終了日
	 */
	public void setAttention6EndDt(String attention6EndDt) {
		this.attention6EndDt = attention6EndDt;
	}

	/**
	 * 締め日を取得します。
	 *
	 * @return 締め日
	 */
	public String getShimeDay() {
		return shimeDay;
	}
	/**
	 * 締め日を設定します。
	 *
	 * @param shimeDay 締め日
	 */
	public void setShimeDay(String shimeDay) {
		this.shimeDay = shimeDay;
	}

	/**
	 * 構造を取得します。
	 *
	 * @return 構造
	 */
	public String getKozo() {
		return kozo;
	}
	/**
	 * 構造を設定します。
	 *
	 * @param kozo 構造
	 */
	public void setKozo(String kozo) {
		this.kozo = kozo;
	}

	/**
	 * 階数を取得します。
	 *
	 * @return 階数
	 */
	public String getKaisu() {
		return kaisu;
	}
	/**
	 * 階数を設定します。
	 *
	 * @param kaisu 階数
	 */
	public void setKaisu(String kaisu) {
		this.kaisu = kaisu;
	}

	/**
	 * 築年月を取得します。
	 *
	 * @return 築年月
	 */
	public String getChikuNengetsu() {
		return chikuNengetsu;
	}
	/**
	 * 築年月を設定します。
	 *
	 * @param chikuNengetsu 築年月
	 */
	public void setChikuNengetsu(String chikuNengetsu) {
		this.chikuNengetsu = chikuNengetsu;
	}

	/**
	 * 戸数を取得します。
	 *
	 * @return 戸数
	 */
	public String getKosu() {
		return kosu;
	}
	/**
	 * 戸数を設定します。
	 *
	 * @param kosu 戸数
	 */
	public void setKosu(String kosu) {
		this.kosu = kosu;
	}

	/**
	 * 担当者名１を取得します。
	 *
	 * @return 担当者名１
	 */
	public String getTantoshaNm1() {
		return tantoshaNm1;
	}
	/**
	 * 担当者名１を設定します。
	 *
	 * @param tantoshaNm1 担当者名１
	 */
	public void setTantoshaNm1(String tantoshaNm1) {
		this.tantoshaNm1 = tantoshaNm1;
	}

	/**
	 * 担当者名２を取得します。
	 *
	 * @return 担当者名２
	 */
	public String getTantoshaNm2() {
		return tantoshaNm2;
	}
	/**
	 * 担当者名２を設定します。
	 *
	 * @param tantoshaNm2 担当者名２
	 */
	public void setTantoshaNm2(String tantoshaNm2) {
		this.tantoshaNm2 = tantoshaNm2;
	}

	/**
	 * 管理形態区分を取得します。
	 *
	 * @return 管理形態区分
	 */
	public String getKanriKeitaiKbn() {
		return kanriKeitaiKbn;
	}
	/**
	 * 管理形態区分を設定します。
	 *
	 * @param kanriKeitaiKbn 管理形態区分
	 */
	public void setKanriKeitaiKbn(String kanriKeitaiKbn) {
		this.kanriKeitaiKbn = kanriKeitaiKbn;
	}

	/**
	 * 連絡先１を取得します。
	 *
	 * @return 連絡先
	 */
	public String getRenrakusaki1() {
		return renrakusaki1;
	}
	/**
	 * 連絡先１を設定します。
	 *
	 * @param renrakusaki1 連絡先１
	 */
	public void setRenrakusaki1(String renrakusaki1) {
		this.renrakusaki1 = renrakusaki1;
	}

	/**
	 * 連絡先２を取得します。
	 *
	 * @return 連絡先
	 */
	public String getRenrakusaki2() {
		return renrakusaki2;
	}
	/**
	 * 連絡先２を設定します。
	 *
	 * @param renrakusaki2 連絡先２
	 */
	public void setRenrakusaki2(String renrakusaki2) {
		this.renrakusaki2 = renrakusaki2;
	}

	/**
	 * ポンプ室メモを取得します。
	 *
	 * @return ポンプ室メモ
	 */
	public String getPompMemo() {
		return pompMemo;
	}
	/**
	 * ポンプ室メモを設定します。
	 *
	 * @param pompMemo ポンプ室メモ
	 */
	public void setPompMemo(String pompMemo) {
		this.pompMemo = pompMemo;
	}

	/**
	 * オートロックメモを取得します。
	 *
	 * @return オートロックメモ
	 */
	public String getAutoLockMemo() {
		return autoLockMemo;
	}
	/**
	 * オートロックメモを設定します。
	 *
	 * @param autoLockMemo オートロックメモ
	 */
	public void setAutoLockMemo(String autoLockMemo) {
		this.autoLockMemo = autoLockMemo;
	}

	/**
	 * メールＢＯＸを取得します。
	 * @return メールＢＯＸ
	 */
	public String getMailBox() {
		return mailBox;
	}

	/**
	 * メールＢＯＸを設定します。
	 * @param mailBox メールＢＯＸ
	 */
	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}

	/**
	 * 管理形態備考を取得します。
	 *
	 * @return 管理形態備考
	 */
	public String getBiko() {
		return biko;
	}
	/**
	 * 管理形態備考を設定します。
	 *
	 * @param biko 管理形態備考
	 */
	public void setBiko(String biko) {
		this.biko = biko;
	}

	/**
	 * オーナー名を取得します。
	 *
	 * @return オーナー名
	 */
	public String getOoyaNm() {
		return ooyaNm;
	}
	/**
	 * オーナー名を設定します。
	 *
	 * @param ooyaNm オーナー名
	 */
	public void setOoyaNm(String ooyaNm) {
		this.ooyaNm = ooyaNm;
	}

	/**
	 * オーナー電話番号を取得します。
	 *
	 * @return オーナー電話番号
	 */
	public String getOoyaTel() {
		return ooyaTel;
	}
	/**
	 * オーナー電話番号を設定します。
	 *
	 * @param ooyaTel オーナー電話番号
	 */
	public void setOoyaTel(String ooyaTel) {
		this.ooyaTel = ooyaTel;
	}

	/**
	 * オーナー住所を取得します。
	 *
	 * @return オーナー住所
	 */
	public String getOoyaJusho() {
		return ooyaJusho;
	}
	/**
	 * オーナー住所を設定します。
	 *
	 * @param ooyaJusho オーナー住所
	 */
	public void setOoyaJusho(String ooyaJusho) {
		this.ooyaJusho = ooyaJusho;
	}

	/**
	 * オーナー備考を取得します。
	 *
	 * @return オーナー備考
	 */
	public String getOoyaBiko() {
		return ooyaBiko;
	}
	/**
	 * オーナー備考を設定します。
	 *
	 * @param ooyaBiko オーナー備考
	 */
	public void setOoyaBiko(String ooyaBiko) {
		this.ooyaBiko = ooyaBiko;
	}

	/**
	 * 共用部管理を取得します。
	 * 
	 * @return 共用部管理
	 */
	public String getKyoyoKanriNm() {
		return kyoyoKanriNm;
	}

	/**
	 * 共用部管理を設定します。
	 * 
	 * @param kyoyoKanriNm 共用部管理
	 */
	public void setKyoyoKanriNm(String kyoyoKanriNm) {
		this.kyoyoKanriNm = kyoyoKanriNm;
	}

	/**
	 * 共用部管理営業日を取得します。
	 * 
	 * @return 共用部管理営業日
	 */
	public String getKyoyoKanriEigyobi() {
		return kyoyoKanriEigyobi;
	}

	/**
	 * 共用部管理営業日を設定します。
	 * 
	 * @param kyoyoKanriEigyobi 共用部管理営業日
	 */
	public void setKyoyoKanriEigyobi(String kyoyoKanriEigyobi) {
		this.kyoyoKanriEigyobi = kyoyoKanriEigyobi;
	}

	/**
	 * 共用部管理営業時間を取得します。
	 * 
	 * @return 共用部管理営業時間
	 */
	public String getKyoyoKanriEigyoJikan() {
		return kyoyoKanriEigyoJikan;
	}

	/**
	 * 共用部管理営業時間を設定します。
	 * 
	 * @param kyoyoKanriEigyoJikan 共用部管理営業時間
	 */
	public void setKyoyoKanriEigyoJikan(String kyoyoKanriEigyoJikan) {
		this.kyoyoKanriEigyoJikan = kyoyoKanriEigyoJikan;
	}

	/**
	 * 共用部管理ＴＥＬを取得します。
	 * 
	 * @return 共用部管理ＴＥＬ
	 */
	public String getKyoyoKanriTel() {
		return kyoyoKanriTel;
	}

	/**
	 * 共用部管理ＴＥＬを設定します。
	 * 
	 * @param kyoyoKanriTel 共用部管理ＴＥＬ
	 */
	public void setKyoyoKanriTel(String kyoyoKanriTel) {
		this.kyoyoKanriTel = kyoyoKanriTel;
	}

	/**
	 * 共用部管理時間外ＴＥＬを取得します。
	 * 
	 * @return 共用部管理時間外ＴＥＬ
	 */
	public String getKyoyoKanriJikangaiTel() {
		return kyoyoKanriJikangaiTel;
	}

	/**
	 * 共用部管理時間外ＴＥＬを設定します。
	 * 
	 * @param kyoyoKanriJikangaiTel 共用部管理時間外ＴＥＬ
	 */
	public void setKyoyoKanriJikangaiTel(String kyoyoKanriJikangaiTel) {
		this.kyoyoKanriJikangaiTel = kyoyoKanriJikangaiTel;
	}

	/**
	 * 共用部管理E-mailを取得します。
	 * 
	 * @return 共用部管理E-mail
	 */
	public String getKyoyoKanriMailAddress() {
		return kyoyoKanriMailAddress;
	}

	/**
	 * 共用部管理E-mailを設定します。
	 * 
	 * @param kyoyoKanriMailAddress 共用部管理E-mail
	 */
	public void setKyoyoKanriMailAddress(String kyoyoKanriMailAddress) {
		this.kyoyoKanriMailAddress = kyoyoKanriMailAddress;
	}

	/**
	 * 共用部管理備考を取得します。
	 * 
	 * @return 共用部管理備考
	 */
	public String getKyoyoKanriBiko() {
		return kyoyoKanriBiko;
	}

	/**
	 * 共用部管理備考を設定します。
	 * 
	 * @param kyoyoKanriBiko 共用部管理備考
	 */
	public void setKyoyoKanriBiko(String kyoyoKanriBiko) {
		this.kyoyoKanriBiko = kyoyoKanriBiko;
	}

	/**
	 * 管理人名を取得します。
	 * 
	 * @return 管理人名
	 */
	public String getKanrininNm() {
		return kanrininNm;
	}

	/**
	 * 管理人名を設定します。
	 * 
	 * @param kanrininNm 管理人名
	 */
	public void setKanrininNm(String kanrininNm) {
		this.kanrininNm = kanrininNm;
	}

	/**
	 * 管理人営業日を取得します。
	 * 
	 * @return 管理人営業日
	 */
	public String getKanrininEigyobi() {
		return kanrininEigyobi;
	}

	/**
	 * 管理人営業日を設定します。
	 * 
	 * @param kanrininEigyobi 管理人営業日
	 */
	public void setKanrininEigyobi(String kanrininEigyobi) {
		this.kanrininEigyobi = kanrininEigyobi;
	}

	/**
	 * 管理人営業時間を取得します。
	 * 
	 * @return 管理人営業時間
	 */
	public String getKanrininEigyoJikan() {
		return kanrininEigyoJikan;
	}

	/**
	 * 管理人営業時間を設定します。
	 * 
	 * @param kanrininEigyoJikan 管理人営業時間
	 */
	public void setKanrininEigyoJikan(String kanrininEigyoJikan) {
		this.kanrininEigyoJikan = kanrininEigyoJikan;
	}

	/**
	 * 管理人名ＴＥＬを取得します。
	 * 
	 * @return 管理人名ＴＥＬ
	 */
	public String getKanrininTel() {
		return kanrininTel;
	}

	/**
	 * 管理人名ＴＥＬを設定します。
	 * 
	 * @param kanrininTel 管理人名ＴＥＬ
	 */
	public void setKanrininTel(String kanrininTel) {
		this.kanrininTel = kanrininTel;
	}

	/**
	 * 管理人E-mailを取得します。
	 * 
	 * @return 管理人E-mail
	 */
	public String getKanrininMailAddress() {
		return kanrininMailAddress;
	}

	/**
	 * 管理人E-mailを設定します。
	 * 
	 * @param kanrininMailAddress 管理人E-mail
	 */
	public void setKanrininMailAddress(String kanrininMailAddress) {
		this.kanrininMailAddress = kanrininMailAddress;
	}

	/**
	 * 管理人備考を取得します。
	 * 
	 * @return 管理人備考
	 */
	public String getKanrininBiko() {
		return kanrininBiko;
	}

	/**
	 * 管理人備考を設定します。
	 * 
	 * @param kanrininBiko 管理人備考
	 */
	public void setKanrininBiko(String kanrininBiko) {
		this.kanrininBiko = kanrininBiko;
	}

	/**
	 * 警備会社名を取得します。
	 * 
	 * @return 警備会社名
	 */
	public String getKeibiGaisha() {
		return keibiGaisha;
	}

	/**
	 * 警備会社名を設定します。
	 * 
	 * @param keibiGaisha 警備会社名
	 */
	public void setKeibiGaisha(String keibiGaisha) {
		this.keibiGaisha = keibiGaisha;
	}

	/**
	 * 警備会社ＴＥＬを取得します。
	 * 
	 * @return 警備会社ＴＥＬ
	 */
	public String getKeibiGaishaTel() {
		return keibiGaishaTel;
	}

	/**
	 * 警備会社ＴＥＬを設定します。
	 * 
	 * @param keibiGaishaTel 警備会社ＴＥＬ
	 */
	public void setKeibiGaishaTel(String keibiGaishaTel) {
		this.keibiGaishaTel = keibiGaishaTel;
	}

	/**
	 * 警備会社ＦＡＸを取得します。
	 * 
	 * @return 警備会社ＦＡＸ
	 */
	public String getKeibiGaishaFax() {
		return keibiGaishaFax;
	}

	/**
	 * 警備会社ＦＡＸを設定します。
	 * 
	 * @param keibiGaishaFax 警備会社ＦＡＸ
	 */
	public void setKeibiGaishaFax(String keibiGaishaFax) {
		this.keibiGaishaFax = keibiGaishaFax;
	}

	/**
	 * 警備会社E-mailを取得します。
	 * 
	 * @return 警備会社E-mail
	 */
	public String getKeibiGaishaMailAddress() {
		return keibiGaishaMailAddress;
	}

	/**
	 * 警備会社E-mailを設定します。
	 * 
	 * @param keibiGaishaMailAddress 警備会社E-mail
	 */
	public void setKeibiGaishaMailAddress(String keibiGaishaMailAddress) {
		this.keibiGaishaMailAddress = keibiGaishaMailAddress;
	}

	/**
	 * 警備会社備考を取得します。
	 * 
	 * @return 警備会社備考
	 */
	public String getKeibiGaishaBiko() {
		return keibiGaishaBiko;
	}

	/**
	 * 警備会社備考を設定します。
	 * 
	 * @param keibiGaishaBiko 警備会社備考
	 */
	public void setKeibiGaishaBiko(String keibiGaishaBiko) {
		this.keibiGaishaBiko = keibiGaishaBiko;
	}

	/**
	 * 水道業者�@を取得します。
	 * 
	 * @return 水道業者�@
	 */
	public String getSuidoGyosha() {
		return suidoGyosha;
	}

	/**
	 * 水道業者�@を設定します。
	 * 
	 * @param suidoGyosha 水道業者�@
	 */
	public void setSuidoGyosha(String suidoGyosha) {
		this.suidoGyosha = suidoGyosha;
	}

	/**
	 * 水道業者�@ＴＥＬを取得します。
	 * 
	 * @return 水道業者�@ＴＥＬ
	 */
	public String getSuidoGyoshaTel() {
		return suidoGyoshaTel;
	}

	/**
	 * 水道業者�@ＴＥＬを設定します。
	 * 
	 * @param suidoGyoshaTel 水道業者�@ＴＥＬ
	 */
	public void setSuidoGyoshaTel(String suidoGyoshaTel) {
		this.suidoGyoshaTel = suidoGyoshaTel;
	}

	/**
	 * 水道業者�@ＦＡＸを取得します。
	 * 
	 * @return 水道業者�@ＦＡＸ
	 */
	public String getSuidoGyoshaFax() {
		return suidoGyoshaFax;
	}

	/**
	 * 水道業者�@ＦＡＸを設定します。
	 * 
	 * @param suidoGyoshaFax 水道業者�@ＦＡＸ
	 */
	public void setSuidoGyoshaFax(String suidoGyoshaFax) {
		this.suidoGyoshaFax = suidoGyoshaFax;
	}

	/**
	 * 水道業者�@E-mailを取得します。
	 * 
	 * @return 水道業者�@E-mail
	 */
	public String getSuidoGyoshaMailAddress() {
		return suidoGyoshaMailAddress;
	}

	/**
	 * 水道業者�@E-mailを設定します。
	 * 
	 * @param suidoGyoshaMailAddress 水道業者�@E-mail
	 */
	public void setSuidoGyoshaMailAddress(String suidoGyoshaMailAddress) {
		this.suidoGyoshaMailAddress = suidoGyoshaMailAddress;
	}

	/**
	 * 水道業者�@備考を取得します。
	 * 
	 * @return 水道業者�@備考
	 */
	public String getSuidoGyoshaBiko() {
		return suidoGyoshaBiko;
	}

	/**
	 * 水道業者�@備考を設定します。
	 * 
	 * @param suidoGyoshaBiko 水道業者�@備考
	 */
	public void setSuidoGyoshaBiko(String suidoGyoshaBiko) {
		this.suidoGyoshaBiko = suidoGyoshaBiko;
	}

	/**
	 * 給排水業者�Aを取得します。
	 * 
	 * @return 給排水業者�A
	 */
	public String getKyuHaisuiGyosha() {
		return kyuHaisuiGyosha;
	}

	/**
	 * 給排水業者�Aを設定します。
	 * 
	 * @param kyuHaisuiGyosha 給排水業者�A
	 */
	public void setKyuHaisuiGyosha(String kyuHaisuiGyosha) {
		this.kyuHaisuiGyosha = kyuHaisuiGyosha;
	}

	/**
	 * 給排水業者�AＴＥＬを取得します。
	 * 
	 * @return 給排水業者�AＴＥＬ
	 */
	public String getKyuHaisuiGyoshaTel() {
		return kyuHaisuiGyoshaTel;
	}

	/**
	 * 給排水業者�AＴＥＬを設定します。
	 * 
	 * @param kyuHaisuiGyoshaTel 給排水業者�AＴＥＬ
	 */
	public void setKyuHaisuiGyoshaTel(String kyuHaisuiGyoshaTel) {
		this.kyuHaisuiGyoshaTel = kyuHaisuiGyoshaTel;
	}

	/**
	 * 給排水業者�AＦＡＸを取得します。
	 * 
	 * @return 給排水業者�AＦＡＸ
	 */
	public String getKyuHaisuiGyoshaFax() {
		return kyuHaisuiGyoshaFax;
	}

	/**
	 * 給排水業者�AＦＡＸを設定します。
	 * 
	 * @param kyuHaisuiGyoshaFax 給排水業者�AＦＡＸ
	 */
	public void setKyuHaisuiGyoshaFax(String kyuHaisuiGyoshaFax) {
		this.kyuHaisuiGyoshaFax = kyuHaisuiGyoshaFax;
	}

	/**
	 * 給排水業者�AE-mailを取得します。
	 * 
	 * @return 給排水業者�AE-mail
	 */
	public String getKyuHaisuiGyoshaMailAddress() {
		return kyuHaisuiGyoshaMailAddress;
	}

	/**
	 * 給排水業者�AE-mailを設定します。
	 * 
	 * @param kyuHaisuiGyoshaMailAddress 給排水業者�AE-mail
	 */
	public void setKyuHaisuiGyoshaMailAddress(String kyuHaisuiGyoshaMailAddress) {
		this.kyuHaisuiGyoshaMailAddress = kyuHaisuiGyoshaMailAddress;
	}

	/**
	 * 給排水業者�A備考を取得します。
	 * 
	 * @return 給排水業者�A備考
	 */
	public String getKyuHaisuiGyoshaBiko() {
		return kyuHaisuiGyoshaBiko;
	}

	/**
	 * 給排水業者�A備考を設定します。
	 * 
	 * @param kyuHaisuiGyoshaBiko 給排水業者�A備考
	 */
	public void setKyuHaisuiGyoshaBiko(String kyuHaisuiGyoshaBiko) {
		this.kyuHaisuiGyoshaBiko = kyuHaisuiGyoshaBiko;
	}

	/**
	 * ガス会社名を取得します。
	 * 
	 * @return ガス会社名
	 */
	public String getGasGaisha() {
		return gasGaisha;
	}

	/**
	 * ガス会社名を設定します。
	 * 
	 * @param gasGaisha ガス会社名
	 */
	public void setGasGaisha(String gasGaisha) {
		this.gasGaisha = gasGaisha;
	}

	/**
	 * ガス会社名ＴＥＬを取得します。
	 * 
	 * @return ガス会社名ＴＥＬ
	 */
	public String getGasGaishaTel() {
		return gasGaishaTel;
	}

	/**
	 * ガス会社名ＴＥＬを設定します。
	 * 
	 * @param gasGaishaTel ガス会社名ＴＥＬ
	 */
	public void setGasGaishaTel(String gasGaishaTel) {
		this.gasGaishaTel = gasGaishaTel;
	}

	/**
	 * ガス会社名ＦＡＸを取得します。
	 * 
	 * @return ガス会社名ＦＡＸ
	 */
	public String getGasGaishaFax() {
		return gasGaishaFax;
	}

	/**
	 * ガス会社名ＦＡＸを設定します。
	 * 
	 * @param gasGaishaFax ガス会社名ＦＡＸ
	 */
	public void setGasGaishaFax(String gasGaishaFax) {
		this.gasGaishaFax = gasGaishaFax;
	}

	/**
	 * ガス会社名E-mailを取得します。
	 * 
	 * @return ガス会社名E-mail
	 */
	public String getGasGaishaMailAddress() {
		return gasGaishaMailAddress;
	}

	/**
	 * ガス会社名E-mailを設定します。
	 * 
	 * @param gasGaishaMailAddress ガス会社名E-mail
	 */
	public void setGasGaishaMailAddress(String gasGaishaMailAddress) {
		this.gasGaishaMailAddress = gasGaishaMailAddress;
	}

	/**
	 * ガス会社名備考を取得します。
	 * 
	 * @return ガス会社名備考
	 */
	public String getGasGaishaBiko() {
		return gasGaishaBiko;
	}

	/**
	 * ガス会社名備考を設定します。
	 * 
	 * @param gasGaishaBiko ガス会社名備考
	 */
	public void setGasGaishaBiko(String gasGaishaBiko) {
		this.gasGaishaBiko = gasGaishaBiko;
	}

	/**
	 * 給湯器保守業者名を取得します。
	 * 
	 * @return 給湯器保守業者名
	 */
	public String getKyutokiHoshuGyosha() {
		return kyutokiHoshuGyosha;
	}

	/**
	 * 給湯器保守業者名を設定します。
	 * 
	 * @param kyutokiHoshuGyosha 給湯器保守業者名
	 */
	public void setKyutokiHoshuGyosha(String kyutokiHoshuGyosha) {
		this.kyutokiHoshuGyosha = kyutokiHoshuGyosha;
	}

	/**
	 * 給湯器保守業者ＴＥＬを取得します。
	 * 
	 * @return 給湯器保守業者ＴＥＬ
	 */
	public String getKyutokiHoshuGyoshaTel() {
		return kyutokiHoshuGyoshaTel;
	}

	/**
	 * 給湯器保守業者ＴＥＬを設定します。
	 * 
	 * @param kyutokiHoshuGyoshaTel 給湯器保守業者ＴＥＬ
	 */
	public void setKyutokiHoshuGyoshaTel(String kyutokiHoshuGyoshaTel) {
		this.kyutokiHoshuGyoshaTel = kyutokiHoshuGyoshaTel;
	}

	/**
	 * 給湯器保守業者ＦＡＸを取得します。
	 * 
	 * @return 給湯器保守業者ＦＡＸ
	 */
	public String getKyutokiHoshuGyoshaFax() {
		return kyutokiHoshuGyoshaFax;
	}

	/**
	 * 給湯器保守業者ＦＡＸを設定します。
	 * 
	 * @param kyutokiHoshuGyoshaFax 給湯器保守業者ＦＡＸ
	 */
	public void setKyutokiHoshuGyoshaFax(String kyutokiHoshuGyoshaFax) {
		this.kyutokiHoshuGyoshaFax = kyutokiHoshuGyoshaFax;
	}

	/**
	 * 給湯器保守業者E-mailを取得します。
	 * 
	 * @return 給湯器保守業者E-mail
	 */
	public String getKyutokiHoshuGyoshaMailAddress() {
		return kyutokiHoshuGyoshaMailAddress;
	}

	/**
	 * 給湯器保守業者E-mailを設定します。
	 * 
	 * @param kyutokiHoshuGyoshaMailAddress 給湯器保守業者E-mail
	 */
	public void setKyutokiHoshuGyoshaMailAddress(String kyutokiHoshuGyoshaMailAddress) {
		this.kyutokiHoshuGyoshaMailAddress = kyutokiHoshuGyoshaMailAddress;
	}

	/**
	 * 給湯器保守業者備考を取得します。
	 * 
	 * @return 給湯器保守業者備考
	 */
	public String getKyutokiHoshuGyoshaBiko() {
		return kyutokiHoshuGyoshaBiko;
	}

	/**
	 * 給湯器保守業者備考を設定します。
	 * 
	 * @param kyutokiHoshuGyoshaBiko 給湯器保守業者備考
	 */
	public void setKyutokiHoshuGyoshaBiko(String kyutokiHoshuGyoshaBiko) {
		this.kyutokiHoshuGyoshaBiko = kyutokiHoshuGyoshaBiko;
	}

	/**
	 * エアコン保守業者名を取得します。
	 * 
	 * @return エアコン保守業者名
	 */
	public String getAirConHoshuGyosha() {
		return airConHoshuGyosha;
	}

	/**
	 * エアコン保守業者名を設定します。
	 * 
	 * @param airConHoshuGyosha エアコン保守業者名
	 */
	public void setAirConHoshuGyosha(String airConHoshuGyosha) {
		this.airConHoshuGyosha = airConHoshuGyosha;
	}

	/**
	 * エアコン保守業者ＴＥＬを取得します。
	 * 
	 * @return エアコン保守業者ＴＥＬ
	 */
	public String getAirConHoshuGyoshaTel() {
		return airConHoshuGyoshaTel;
	}

	/**
	 * エアコン保守業者ＴＥＬを設定します。
	 * 
	 * @param airConHoshuGyoshaTel エアコン保守業者ＴＥＬ
	 */
	public void setAirConHoshuGyoshaTel(String airConHoshuGyoshaTel) {
		this.airConHoshuGyoshaTel = airConHoshuGyoshaTel;
	}

	/**
	 * エアコン保守業者ＦＡＸを取得します。
	 * 
	 * @return エアコン保守業者ＦＡＸ
	 */
	public String getAirConHoshuGyoshaFax() {
		return airConHoshuGyoshaFax;
	}

	/**
	 * エアコン保守業者ＦＡＸを設定します。
	 * 
	 * @param airConHoshuGyoshaFax エアコン保守業者ＦＡＸ
	 */
	public void setAirConHoshuGyoshaFax(String airConHoshuGyoshaFax) {
		this.airConHoshuGyoshaFax = airConHoshuGyoshaFax;
	}

	/**
	 * エアコン保守業者E-mailを取得します。
	 * 
	 * @return エアコン保守業者E-mail
	 */
	public String getAirConHoshuGyoshaMailAddress() {
		return airConHoshuGyoshaMailAddress;
	}

	/**
	 * エアコン保守業者E-mailを設定します。
	 * 
	 * @param airConHoshuGyoshaMailAddress エアコン保守業者E-mail
	 */
	public void setAirConHoshuGyoshaMailAddress(String airConHoshuGyoshaMailAddress) {
		this.airConHoshuGyoshaMailAddress = airConHoshuGyoshaMailAddress;
	}

	/**
	 * エアコン保守業者備考を取得します。
	 * 
	 * @return エアコン保守業者備考
	 */
	public String getAirConHoshuGyoshaBiko() {
		return airConHoshuGyoshaBiko;
	}

	/**
	 * エアコン保守業者備考を設定します。
	 * 
	 * @param airConHoshuGyoshaBiko エアコン保守業者備考
	 */
	public void setAirConHoshuGyoshaBiko(String airConHoshuGyoshaBiko) {
		this.airConHoshuGyoshaBiko = airConHoshuGyoshaBiko;
	}

	/**
	 * 電気保守業者名を取得します。
	 * 
	 * @return 電気保守業者名
	 */
	public String getDenkiHoshuGyosha() {
		return denkiHoshuGyosha;
	}

	/**
	 * 電気保守業者名を設定します。
	 * 
	 * @param denkiHoshuGyosha 電気保守業者名
	 */
	public void setDenkiHoshuGyosha(String denkiHoshuGyosha) {
		this.denkiHoshuGyosha = denkiHoshuGyosha;
	}

	/**
	 * 電気保守業者ＴＥＬを取得します。
	 * 
	 * @return 電気保守業者ＴＥＬ
	 */
	public String getDenkiHoshuGyoshaTel() {
		return denkiHoshuGyoshaTel;
	}

	/**
	 * 電気保守業者ＴＥＬを設定します。
	 * 
	 * @param denkiHoshuGyoshaTel 電気保守業者ＴＥＬ
	 */
	public void setDenkiHoshuGyoshaTel(String denkiHoshuGyoshaTel) {
		this.denkiHoshuGyoshaTel = denkiHoshuGyoshaTel;
	}

	/**
	 * 電気保守業者ＦＡＸを取得します。
	 * 
	 * @return 電気保守業者ＦＡＸ
	 */
	public String getDenkiHoshuGyoshaFax() {
		return denkiHoshuGyoshaFax;
	}

	/**
	 * 電気保守業者ＦＡＸを設定します。
	 * 
	 * @param denkiHoshuGyoshaFax 電気保守業者ＦＡＸ
	 */
	public void setDenkiHoshuGyoshaFax(String denkiHoshuGyoshaFax) {
		this.denkiHoshuGyoshaFax = denkiHoshuGyoshaFax;
	}

	/**
	 * 電気保守業者E-mailを取得します。
	 * 
	 * @return 電気保守業者E-mail
	 */
	public String getDenkiHoshuGyoshaMailAddress() {
		return denkiHoshuGyoshaMailAddress;
	}

	/**
	 * 電気保守業者E-mailを設定します。
	 * 
	 * @param denkiHoshuGyoshaMailAddress 電気保守業者E-mail
	 */
	public void setDenkiHoshuGyoshaMailAddress(String denkiHoshuGyoshaMailAddress) {
		this.denkiHoshuGyoshaMailAddress = denkiHoshuGyoshaMailAddress;
	}

	/**
	 * 電気保守業者備考を取得します。
	 * 
	 * @return 電気保守業者備考
	 */
	public String getDenkiHoshuGyoshaBiko() {
		return denkiHoshuGyoshaBiko;
	}

	/**
	 * 電気保守業者備考を設定します。
	 * 
	 * @param denkiHoshuGyoshaBiko 電気保守業者備考
	 */
	public void setDenkiHoshuGyoshaBiko(String denkiHoshuGyoshaBiko) {
		this.denkiHoshuGyoshaBiko = denkiHoshuGyoshaBiko;
	}

	/**
	 * ＥＶ会社名を取得します。
	 * 
	 * @return ＥＶ会社名
	 */
	public String getEvGaisha() {
		return evGaisha;
	}

	/**
	 * ＥＶ会社名を設定します。
	 * 
	 * @param evGaisha ＥＶ会社名
	 */
	public void setEvGaisha(String evGaisha) {
		this.evGaisha = evGaisha;
	}

	/**
	 * ＥＶ会社名ＴＥＬを取得します。
	 * 
	 * @return ＥＶ会社名ＴＥＬ
	 */
	public String getEvGaishaTel() {
		return evGaishaTel;
	}

	/**
	 * ＥＶ会社名ＴＥＬを設定します。
	 * 
	 * @param evGaishaTel ＥＶ会社名ＴＥＬ
	 */
	public void setEvGaishaTel(String evGaishaTel) {
		this.evGaishaTel = evGaishaTel;
	}

	/**
	 * ＥＶ会社名ＦＡＸを取得します。
	 * 
	 * @return ＥＶ会社名ＦＡＸ
	 */
	public String getEvGaishaFax() {
		return evGaishaFax;
	}

	/**
	 * ＥＶ会社名ＦＡＸを設定します。
	 * 
	 * @param evGaishaFax ＥＶ会社名ＦＡＸ
	 */
	public void setEvGaishaFax(String evGaishaFax) {
		this.evGaishaFax = evGaishaFax;
	}

	/**
	 * ＥＶ会社名E-mailを取得します。
	 * 
	 * @return ＥＶ会社名E-mail
	 */
	public String getEvGaishaMailAddress() {
		return evGaishaMailAddress;
	}

	/**
	 * ＥＶ会社名E-mailを設定します。
	 * 
	 * @param evGaishaMailAddress ＥＶ会社名E-mail
	 */
	public void setEvGaishaMailAddress(String evGaishaMailAddress) {
		this.evGaishaMailAddress = evGaishaMailAddress;
	}

	/**
	 * ＥＶ会社名備考を取得します。
	 * 
	 * @return ＥＶ会社名備考
	 */
	public String getEvGaishaBiko() {
		return evGaishaBiko;
	}

	/**
	 * ＥＶ会社名備考を設定します。
	 * 
	 * @param evGaishaBiko ＥＶ会社名備考
	 */
	public void setEvGaishaBiko(String evGaishaBiko) {
		this.evGaishaBiko = evGaishaBiko;
	}

	/**
	 * 鍵業者名を取得します。
	 * 
	 * @return 鍵業者名
	 */
	public String getKagiGyosha() {
		return kagiGyosha;
	}

	/**
	 * 鍵業者名を設定します。
	 * 
	 * @param kagiGyosha 鍵業者名
	 */
	public void setKagiGyosha(String kagiGyosha) {
		this.kagiGyosha = kagiGyosha;
	}

	/**
	 * 鍵業者名ＴＥＬを取得します。
	 * 
	 * @return 鍵業者名ＴＥＬ
	 */
	public String getKagiGyoshaTel() {
		return kagiGyoshaTel;
	}

	/**
	 * 鍵業者名ＴＥＬを設定します。
	 * 
	 * @param kagiGyoshaTel 鍵業者名ＴＥＬ
	 */
	public void setKagiGyoshaTel(String kagiGyoshaTel) {
		this.kagiGyoshaTel = kagiGyoshaTel;
	}

	/**
	 * 鍵業者名ＦＡＸを取得します。
	 * 
	 * @return 鍵業者名ＦＡＸ
	 */
	public String getKagiGyoshaFax() {
		return kagiGyoshaFax;
	}

	/**
	 * 鍵業者名ＦＡＸを設定します。
	 * 
	 * @param kagiGyoshaFax 鍵業者名ＦＡＸ
	 */
	public void setKagiGyoshaFax(String kagiGyoshaFax) {
		this.kagiGyoshaFax = kagiGyoshaFax;
	}

	/**
	 * 鍵業者名E-mailを取得します。
	 * 
	 * @return 鍵業者名E-mail
	 */
	public String getKagiGyoshaMailAddress() {
		return kagiGyoshaMailAddress;
	}

	/**
	 * 鍵業者名E-mailを設定します。
	 * 
	 * @param kagiGyoshaMailAddress 鍵業者名E-mail
	 */
	public void setKagiGyoshaMailAddress(String kagiGyoshaMailAddress) {
		this.kagiGyoshaMailAddress = kagiGyoshaMailAddress;
	}

	/**
	 * 鍵業者名備考を取得します。
	 * 
	 * @return 鍵業者名備考
	 */
	public String getKagiGyoshaBiko() {
		return kagiGyoshaBiko;
	}

	/**
	 * 鍵業者名備考を設定します。
	 * 
	 * @param kagiGyoshaBiko 鍵業者名備考
	 */
	public void setKagiGyoshaBiko(String kagiGyoshaBiko) {
		this.kagiGyoshaBiko = kagiGyoshaBiko;
	}

	/**
	 * 消防保守を取得します。
	 * 
	 * @return 消防保守
	 */
	public String getShoboHoshu() {
		return shoboHoshu;
	}

	/**
	 * 消防保守を設定します。
	 * 
	 * @param shoboHoshu 消防保守
	 */
	public void setShoboHoshu(String shoboHoshu) {
		this.shoboHoshu = shoboHoshu;
	}

	/**
	 * 消防保守ＴＥＬを取得します。
	 * 
	 * @return 消防保守ＴＥＬ
	 */
	public String getShoboHoshuTel() {
		return shoboHoshuTel;
	}

	/**
	 * 消防保守ＴＥＬを設定します。
	 * 
	 * @param shoboHoshuTel 消防保守ＴＥＬ
	 */
	public void setShoboHoshuTel(String shoboHoshuTel) {
		this.shoboHoshuTel = shoboHoshuTel;
	}

	/**
	 * 消防保守ＦＡＸを取得します。
	 * 
	 * @return 消防保守ＦＡＸ
	 */
	public String getShoboHoshuFax() {
		return shoboHoshuFax;
	}

	/**
	 * 消防保守ＦＡＸを設定します。
	 * 
	 * @param shoboHoshuFax 消防保守ＦＡＸ
	 */
	public void setShoboHoshuFax(String shoboHoshuFax) {
		this.shoboHoshuFax = shoboHoshuFax;
	}

	/**
	 * 消防保守E-mailを取得します。
	 * 
	 * @return 消防保守E-mail
	 */
	public String getShoboHoshuMailAddress() {
		return shoboHoshuMailAddress;
	}

	/**
	 * 消防保守E-mailを設定します。
	 * 
	 * @param shoboHoshuMailAddress 消防保守E-mail
	 */
	public void setShoboHoshuMailAddress(String shoboHoshuMailAddress) {
		this.shoboHoshuMailAddress = shoboHoshuMailAddress;
	}

	/**
	 * 消防保守備考を取得します。
	 * 
	 * @return 消防保守備考
	 */
	public String getShoboHoshuBiko() {
		return shoboHoshuBiko;
	}

	/**
	 * 消防保守備考を設定します。
	 * 
	 * @param shoboHoshuBiko 消防保守備考
	 */
	public void setShoboHoshuBiko(String shoboHoshuBiko) {
		this.shoboHoshuBiko = shoboHoshuBiko;
	}

	/**
	 * ＣＡＴＶ会社名を取得します。
	 * 
	 * @return CATV会社
	 */
	public String getCatvGaisha() {
		return catvGaisha;
	}

	/**
	 * ＣＡＴＶ会社名を設定します。
	 * 
	 * @param catvGaisha ＣＡＴＶ会社名
	 */
	public void setCatvGaisha(String catvGaisha) {
		this.catvGaisha = catvGaisha;
	}

	/**
	 * ＣＡＴＶ会社名ＴＥＬを取得します。
	 * 
	 * @return ＣＡＴＶ会社名ＴＥＬ
	 */
	public String getCatvGaishaTel() {
		return catvGaishaTel;
	}

	/**
	 * ＣＡＴＶ会社名ＴＥＬを設定します。
	 * 
	 * @param catvGaishaTel ＣＡＴＶ会社名ＴＥＬ
	 */
	public void setCatvGaishaTel(String catvGaishaTel) {
		this.catvGaishaTel = catvGaishaTel;
	}

	/**
	 * ＣＡＴＶ会社名ＦＡＸを取得します。
	 * 
	 * @return ＣＡＴＶ会社名ＦＡＸ
	 */
	public String getCatvGaishaFax() {
		return catvGaishaFax;
	}

	/**
	 * ＣＡＴＶ会社名ＦＡＸを設定します。
	 * 
	 * @param catvGaishaFax ＣＡＴＶ会社名ＦＡＸ
	 */
	public void setCatvGaishaFax(String catvGaishaFax) {
		this.catvGaishaFax = catvGaishaFax;
	}

	/**
	 * ＣＡＴＶ会社名E-mailを取得します。
	 * 
	 * @return ＣＡＴＶ会社名E-mail
	 */
	public String getCatvGaishaMailAddress() {
		return catvGaishaMailAddress;
	}

	/**
	 * ＣＡＴＶ会社名E-mailを設定します。
	 * 
	 * @param catvGaishaMailAddress ＣＡＴＶ会社名E-mail
	 */
	public void setCatvGaishaMailAddress(String catvGaishaMailAddress) {
		this.catvGaishaMailAddress = catvGaishaMailAddress;
	}

	/**
	 * ＣＡＴＶ会社名備考を取得します。
	 * 
	 * @return ＣＡＴＶ会社名備考
	 */
	public String getCatvGaishaBiko() {
		return catvGaishaBiko;
	}

	/**
	 * ＣＡＴＶ会社名備考を設定します。
	 * 
	 * @param catvGaishaBiko ＣＡＴＶ会社名備考
	 */
	public void setCatvGaishaBiko(String catvGaishaBiko) {
		this.catvGaishaBiko = catvGaishaBiko;
	}

	/**
	 * 小修繕会社を取得します。
	 * 
	 * @return 小修繕会社
	 */
	public String getShoshuZen() {
		return shoshuZen;
	}

	/**
	 * 小修繕会社を設定します。
	 * 
	 * @param shoshuZen 小修繕会社
	 */
	public void setShoshuZen(String shoshuZen) {
		this.shoshuZen = shoshuZen;
	}

	/**
	 * 小修繕会社ＴＥＬを取得します。
	 * 
	 * @return 小修繕会社ＴＥＬ
	 */
	public String getShoshuZenTel() {
		return shoshuZenTel;
	}

	/**
	 * 小修繕会社ＴＥＬを設定します。
	 * 
	 * @param shoshuZenTel 小修繕会社ＴＥＬ
	 */
	public void setShoshuZenTel(String shoshuZenTel) {
		this.shoshuZenTel = shoshuZenTel;
	}

	/**
	 * 小修繕会社ＦＡＸを取得します。
	 * 
	 * @return 小修繕会社ＦＡＸ
	 */
	public String getShoshuZenFax() {
		return shoshuZenFax;
	}

	/**
	 * 小修繕会社ＦＡＸを設定します。
	 * 
	 * @param shoshuZenFax 小修繕会社ＦＡＸ
	 */
	public void setShoshuZenFax(String shoshuZenFax) {
		this.shoshuZenFax = shoshuZenFax;
	}

	/**
	 * 小修繕会社E-mailを取得します。
	 * 
	 * @return 小修繕会社E-mail
	 */
	public String getShoshuZenMailAddress() {
		return shoshuZenMailAddress;
	}

	/**
	 * 小修繕会社E-mailを設定します。
	 * 
	 * @param shoshuZenMailAddress 小修繕会社E-mail
	 */
	public void setShoshuZenMailAddress(String shoshuZenMailAddress) {
		this.shoshuZenMailAddress = shoshuZenMailAddress;
	}

	/**
	 * 小修繕会社備考を取得します。
	 * 
	 * @return 小修繕会社備考
	 */
	public String getShoshuZenBiko() {
		return shoshuZenBiko;
	}

	/**
	 * 小修繕会社備考を設定します。
	 * 
	 * @param shoshuZenBiko 小修繕会社備考
	 */
	public void setShoshuZenBiko(String shoshuZenBiko) {
		this.shoshuZenBiko = shoshuZenBiko;
	}

	/**
	 * ガラス業者を取得します。
	 * 
	 * @return ガラス業者
	 */
	public String getGlassGyosha() {
		return glassGyosha;
	}

	/**
	 * ガラス業者を設定します。
	 * 
	 * @param glassGyosha ガラス業者
	 */
	public void setGlassGyosha(String glassGyosha) {
		this.glassGyosha = glassGyosha;
	}

	/**
	 * ガラス業者ＴＥＬを取得します。
	 * 
	 * @return ガラス業者ＴＥＬ
	 */
	public String getGlassGyoshaTel() {
		return glassGyoshaTel;
	}

	/**
	 * ガラス業者ＴＥＬを設定します。
	 * 
	 * @param glassGyoshaTel ガラス業者ＴＥＬ
	 */
	public void setGlassGyoshaTel(String glassGyoshaTel) {
		this.glassGyoshaTel = glassGyoshaTel;
	}

	/**
	 * ガラス業者ＦＡＸを取得します。
	 * 
	 * @return ガラス業者ＦＡＸ
	 */
	public String getGlassGyoshaFax() {
		return glassGyoshaFax;
	}

	/**
	 * ガラス業者ＦＡＸを設定します。
	 * 
	 * @param glassGyoshaFax ガラス業者ＦＡＸ
	 */
	public void setGlassGyoshaFax(String glassGyoshaFax) {
		this.glassGyoshaFax = glassGyoshaFax;
	}

	/**
	 * ガラス業者E-mailを取得します。
	 * 
	 * @return ガラス業者E-mail
	 */
	public String getGlassGyoshaMailAddress() {
		return glassGyoshaMailAddress;
	}

	/**
	 * ガラス業者E-mailを設定します。
	 * 
	 * @param glassGyoshaMailAddress ガラス業者E-mail
	 */
	public void setGlassGyoshaMailAddress(String glassGyoshaMailAddress) {
		this.glassGyoshaMailAddress = glassGyoshaMailAddress;
	}

	/**
	 * ガラス業者備考を取得します。
	 * 
	 * @return ガラス業者備考
	 */
	public String getGlassGyoshaBiko() {
		return glassGyoshaBiko;
	}

	/**
	 * ガラス業者備考を設定します。
	 * 
	 * @param glassGyoshaBiko ガラス業者備考
	 */
	public void setGlassGyoshaBiko(String glassGyoshaBiko) {
		this.glassGyoshaBiko = glassGyoshaBiko;
	}

	/**
	 * その他１を取得します。
	 * 
	 * @return その他１
	 */
	public String getEtc1() {
		return etc1;
	}

	/**
	 * その他１を設定します。
	 * 
	 * @param etc1 その他１
	 */
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	/**
	 * その他１ＴＥＬを取得します。
	 * 
	 * @return その他１ＴＥＬ
	 */
	public String getEtc1Tel() {
		return etc1Tel;
	}

	/**
	 * その他１ＴＥＬを設定します。
	 * 
	 * @param etc1Tel その他１ＴＥＬ
	 */
	public void setEtc1Tel(String etc1Tel) {
		this.etc1Tel = etc1Tel;
	}

	/**
	 * その他１ＦＡＸを取得します。
	 * 
	 * @return その他１ＦＡＸ
	 */
	public String getEtc1Fax() {
		return etc1Fax;
	}

	/**
	 * その他１ＦＡＸを設定します。
	 * 
	 * @param etc1Fax その他１ＦＡＸ
	 */
	public void setEtc1Fax(String etc1Fax) {
		this.etc1Fax = etc1Fax;
	}

	/**
	 * その他１E-mailを取得します。
	 * 
	 * @return その他１E-mail
	 */
	public String getEtc1MailAddress() {
		return etc1MailAddress;
	}

	/**
	 * その他１E-mailを設定します。
	 * 
	 * @param etc1MailAddress その他１E-mail
	 */
	public void setEtc1MailAddress(String etc1MailAddress) {
		this.etc1MailAddress = etc1MailAddress;
	}

	/**
	 * その他１備考を取得します。
	 * 
	 * @return その他１備考
	 */
	public String getEtc1Biko() {
		return etc1Biko;
	}

	/**
	 * その他１備考を設定します。
	 * 
	 * @param etc1Biko その他１備考
	 */
	public void setEtc1Biko(String etc1Biko) {
		this.etc1Biko = etc1Biko;
	}

	/**
	 * その他２を取得します。
	 * 
	 * @return その他２
	 */
	public String getEtc2() {
		return etc2;
	}

	/**
	 * その他２を設定します。
	 * 
	 * @param etc2 その他２
	 */
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	/**
	 * その他２ＴＥＬを取得します。
	 * 
	 * @return その他２ＴＥＬ
	 */
	public String getEtc2Tel() {
		return etc2Tel;
	}

	/**
	 * その他２ＴＥＬを設定します。
	 * 
	 * @param etc2Tel その他２ＴＥＬ
	 */
	public void setEtc2Tel(String etc2Tel) {
		this.etc2Tel = etc2Tel;
	}

	/**
	 * その他２ＦＡＸを取得します。
	 * 
	 * @return その他２ＦＡＸ
	 */
	public String getEtc2Fax() {
		return etc2Fax;
	}

	/**
	 * その他２ＦＡＸを設定します。
	 * 
	 * @param etc2Fax その他２ＦＡＸ
	 */
	public void setEtc2Fax(String etc2Fax) {
		this.etc2Fax = etc2Fax;
	}

	/**
	 * その他２E-mailを取得します。
	 * 
	 * @return その他２E-mail
	 */
	public String getEtc2MailAddress() {
		return etc2MailAddress;
	}

	/**
	 * その他２E-mailを設定します。
	 * 
	 * @param etc2MailAddress その他２E-mail
	 */
	public void setEtc2MailAddress(String etc2MailAddress) {
		this.etc2MailAddress = etc2MailAddress;
	}

	/**
	 * その他２備考を取得します。
	 * 
	 * @return その他２備考
	 */
	public String getEtc2Biko() {
		return etc2Biko;
	}

	/**
	 * その他２備考を設定します。
	 * 
	 * @param etc2Biko その他２備考
	 */
	public void setEtc2Biko(String etc2Biko) {
		this.etc2Biko = etc2Biko;
	}

	/**
	 * その他３を取得します。
	 * 
	 * @return その他３
	 */
	public String getEtc3() {
		return etc3;
	}

	/**
	 * その他３を設定します。
	 * 
	 * @param etc3 その他３
	 */
	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

	/**
	 * その他３ＴＥＬを取得します。
	 * 
	 * @return その他３ＴＥＬ
	 */
	public String getEtc3Tel() {
		return etc3Tel;
	}

	/**
	 * その他３ＴＥＬを設定します。
	 * 
	 * @param etc3Tel その他３ＴＥＬ
	 */
	public void setEtc3Tel(String etc3Tel) {
		this.etc3Tel = etc3Tel;
	}

	/**
	 * その他３ＦＡＸを取得します。
	 * 
	 * @return その他３ＦＡＸ
	 */
	public String getEtc3Fax() {
		return etc3Fax;
	}

	/**
	 * その他３ＦＡＸを設定します。
	 * 
	 * @param etc3Fax その他３ＦＡＸ
	 */
	public void setEtc3Fax(String etc3Fax) {
		this.etc3Fax = etc3Fax;
	}

	/**
	 * その他３E-mailを取得します。
	 * 
	 * @return その他３E-mail
	 */
	public String getEtc3MailAddress() {
		return etc3MailAddress;
	}

	/**
	 * その他３E-mailを設定します。
	 * 
	 * @param etc3MailAddress その他３E-mail
	 */
	public void setEtc3MailAddress(String etc3MailAddress) {
		this.etc3MailAddress = etc3MailAddress;
	}

	/**
	 * その他３備考を取得します。
	 * 
	 * @return その他３備考
	 */
	public String getEtc3Biko() {
		return etc3Biko;
	}

	/**
	 * その他３備考を設定します。
	 * 
	 * @param etc3Biko その他３備考
	 */
	public void setEtc3Biko(String etc3Biko) {
		this.etc3Biko = etc3Biko;
	}

	/**
	 * CSVカラム列（キー）を取得します。
	 *
	 * @return CSVカラム列（キー）
	 */
	public String[] getCsvColumnsKey() {
		return CSV_COLUMNS_KEY;
	}

	/**
	 * CSVカラム列（名前）を取得します。
	 *
	 * @return CSVカラム列（名前）
	 */
	public String[] getCsvColumnsName() {
		return CSV_COLUMNS_NAME;
	}

	/**
	 * 顧客区分が「3：物件」かどうかチェックします。
	 *
	 * @return true：顧客区分が「3：物件」
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="3または「3：物件」を指定して下さい。")
	private boolean isKokyakuKbn() {
		return kokyakuKbn.equals(KOKYAKU_KBN_BUKKEN) || kokyakuKbn.equals(RcpMKokyaku.KOKYAKU_KBN_BUKKEN);
	}

	/**
	 * 顧客種別（個人/法人）が「1：法人」かどうかチェックします。
	 *
	 * @return true：顧客種別（個人/法人）が「1：法人」
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="1または「1：法人」を指定して下さい。")
	private boolean isKokyakuShubetsu() {
		return kokyakuShubetsu.equals(KOKYAKU_SHUBETSU_HOJIN) || kokyakuShubetsu.equals(RcpMKokyaku.KOKYAKU_SHUBETSU_HOJIN);
	}
}
