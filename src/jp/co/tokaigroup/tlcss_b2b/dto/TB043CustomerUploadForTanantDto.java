package jp.co.tokaigroup.tlcss_b2b.dto;

import javax.validation.constraints.AssertTrue;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.si.fw.validation.constraints.Alphanum;
import jp.co.tokaigroup.si.fw.validation.constraints.Hankaku;
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
 * 管理情報アップロード(入居者)DTO。
 *
 * @author v140546
 * @version 1.0 2014/08/04
 * @version 1.1 2015/11/10 J.Matsuba 項目追加による修正
 * @version 1.2 2016/02/12 H.Yamamura 部屋番号の入力チェックを全角数字→全角文字に変更
 */
public class TB043CustomerUploadForTanantDto extends TB043CSVUploadCommonDto {

	/** 項目数 */
	public static final int ITEM_COUNT = 90;

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
		"kokyakuRoomNo",
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
		"nyukyoDt",
		"taikyoDt",
		"kinkyuRenrakusaki",
		"serviceKbn",
		"moshikomiDt",
		"keiyakuStartDt",
		"keiyakuEndDt",
		"madoguchiTel",
		"biko",
		"toriCd",
		"toriNm",
		"tantoshaNm",
		"seikyusakiCd",
		"seikyusakiMemo",
		"seikyuKbn",
		"ginkoCd",
		"shitenCd",
		"kozaNoKbn",
		"kozaNo",
		"meiginin",
		"cardNo1",
		"cardNo2",
		"cardNo3",
		"cardNo4",
		"yukoKigen",
		"seikyusakiKokyakuId",
		"kokyakuSeikyuStartDt",
		"kokyakuSeikyuEndDt",
		"kanrihiFurikaeFlg",
		"kaiinNo",
		"welboxFlg",
		"moshikomiKbn",
		"tatemonoType",
		"nyukaiHiyoKbn",
		"kihonRyokin",
		"nyukyoKbn",
		"serviceStartDt",
		"sexKbn",
		"birthDt",
		"moshikomiNenrei",
		"roomNo",
		"mailAddress",
		"mailAddressKbn",
		"kinkyuKanaNm",
		"kinkyuKanjiNm",
		"kinkyuTsudukiKbn",
		"kinkyuTel",
		"kinkyuJusho",
		"dokyoKanaNm1",
		"dokyoKanjiNm1",
		"dokyoTsudukiKbn1",
		"dokyoTel1",
		"dokyoJusho1",
		"dokyoKanaNm2",
		"dokyoKanjiNm2",
		"dokyoTsudukiKbn2",
		"dokyoTel2",
		"dokyoJusho2",
		"kanriKanaNm",
		"kanriKanjiNm",
		"kanriTel"
	};

	/** CSVカラム列（名前） */
	public static final String[] CSV_COLUMNS_NAME = new String[] {
		"顧客区分",
		"顧客種別（個人/法人）",
		"カナ氏名（姓）",
		"カナ氏名（名）",
		"漢字氏名（姓）",
		"漢字氏名（名）",
		"郵便番号",
		"住所１　都道府県",
		"住所２　市区町村",
		"住所３　町/大字",
		"住所４　番地",
		"住所５　アパートマンション",
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
		"入居日",
		"退居日",
		"緊急連絡先",
		"サービス区分",
		"申込日",
		"契約開始日",
		"契約終了日",
		"受電窓口電話番号",
		"備考",
		"取扱店コード",
		"取扱店名",
		"担当者名",
		"請求先",
		"請求先メモ",
		"請求方法",
		"銀行コード",
		"支店コード",
		"口座番号区分",
		"口座番号",
		"名義人",
		"カード番号１",
		"カード番号２",
		"カード番号３",
		"カード番号４",
		"有効期限",
		"請求先顧客ＩＤ",
		"顧客請求開始日",
		"顧客請求終了日",
		"管理費振替フラグ",
		"会員番号",
		"ＷＥＬＢＯＸフラグ",
		"申込区分",
		"建物形態",
		"入会費用区分",
		"基本料金",
		"入居区分",
		"サービス開始日",
		"性別",
		"生年月日",
		"申込時年齢",
		"部屋番号",
		"メールアドレス",
		"メールアドレス区分",
		"緊急連絡先　カナ名",
		"緊急連絡先　漢字名",
		"緊急連絡先　続柄区分",
		"緊急連絡先　ＴＥＬ",
		"緊急連絡先　住所",
		"同居人　カナ名１",
		"同居人　漢字名１",
		"同居人　続柄区分１",
		"同居人　連絡先ＴＥＬ１",
		"同居人　連絡先住所１",
		"同居人　カナ名２",
		"同居人　漢字名２",
		"同居人　続柄区分２",
		"同居人　連絡先ＴＥＬ２",
		"同居人　連絡先住所２",
		"管理会社　カナ名",
		"管理会社　漢字名",
		"管理会社　ＴＥＬ"
	};

	/** 顧客区分：「4：入居者・個人」 */
	private static final String KOKYAKU_KBN_NYUKYOSHA = "4：入居者・個人";

	/** 顧客種別：「0：個人」 */
	private static final String KOKYAKU_SHUBETSU_KOJIN = "0：個人";

	/** サービス区分：「2：ライフ」 */
	private static final String SERVICE_KBN_LIFE = "2：ライフ";

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
	 * カナ氏名（姓）
	 */
	@NotEmpty
	@KanaName
	@MaxLength(max=40)
	private String kanaNm1;

	/**
	 * カナ氏名（名）
	 */
	@KanaName
	@MaxLength(max=40)
	private String kanaNm2;

	/**
	 * 漢字氏名（姓）
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=40)
	private String kanjiNm1;

	/**
	 * 漢字氏名（名）
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
	 * 住所５　アパートマンション
	 */
	@Zenkaku
	@MaxLength(max=40)
	private String jusho5;

	/**
	 * 部屋番号
	 */
	@Zenkaku
	@MaxLength(max=20)
	private String kokyakuRoomNo;

	/**
	 * 電話番号１
	 */
	@Num
	@MaxLength(max=15)
	private String telNo1;

	/**
	 * 電話番号２
	 */
	@Num
	@MaxLength(max=15)
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
	 * 入居日
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String nyukyoDt;

	/**
	 * 退居日
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String taikyoDt;

	/**
	 * 緊急連絡先
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String kinkyuRenrakusaki;

	/**
	 * サービス区分
	 */
	@NotEmpty
	@MaxLength(max=10)
	private String serviceKbn;

	/**
	 * 申込日
	 */
	@NotEmpty
	@Time(pattern="yyyyMMdd")
	@MaxLength(max=8)
	private String moshikomiDt;

	/**
	 * 契約開始日
	 */
	@Time(pattern="yyyyMMdd")
	@MaxLength(max=8)
	private String keiyakuStartDt;

	/**
	 * 契約終了日
	 */
	@Time(pattern="yyyyMMdd")
	@MaxLength(max=8)
	private String keiyakuEndDt;

	/**
	 * 受電窓口電話番号
	 */
	private String madoguchiTel;

	/**
	 * 備考
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String biko;

	/**
	 * 取扱店コード
	 */
	private String toriCd;

	/**
	 * 取扱店名
	 */
	private String toriNm;

	/**
	 * 担当者名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String tantoshaNm;

	/**
	 * 請求先
	 */
	private String seikyusakiCd;

	/**
	 * 請求先メモ
	 */
	private String seikyusakiMemo;

	/**
	 * 請求方法
	 */
	private String seikyuKbn;

	/**
	 * 銀行コード
	 */
	private String ginkoCd;

	/**
	 * 支店コード
	 */
	private String shitenCd;

	/**
	 * 口座番号区分
	 */
	private String kozaNoKbn;

	/**
	 * 口座番号
	 */
	private String kozaNo;

	/**
	 * 名義人
	 */
	private String meiginin;

	/**
	 * カード番号１
	 */
	private String cardNo1;

	/**
	 * カード番号２
	 */
	private String cardNo2;

	/**
	 * カード番号３
	 */
	private String cardNo3;

	/**
	 * カード番号４
	 */
	private String cardNo4;

	/**
	 * 有効期限
	 */
	private String yukoKigen;

	/**
	 * 請求先顧客ＩＤ
	 */
	private String seikyusakiKokyakuId;

	/**
	 * 顧客請求開始日
	 */
	private String kokyakuSeikyuStartDt;

	/**
	 * 顧客請求終了日
	 */
	private String kokyakuSeikyuEndDt;

	/**
	 * 管理費振替フラグ
	 */
	private String kanrihiFurikaeFlg;

	/**
	 * 会員番号
	 */
	@NotEmpty
	@Alphanum
	@MaxLength(max=10)
	private String kaiinNo;

	/**
	 * ＷＥＬＢＯＸフラグ
	 */
	private String welboxFlg;

	/**
	 * 申込区分
	 */
	@NotEmpty
	@MaxLength(max=20)
	private String moshikomiKbn;

	/**
	 * 建物形態
	 */
	@NotEmpty
	@MaxLength(max=20)
	private String tatemonoType;

	/**
	 * 入会費用区分
	 */
	@NotEmpty
	@MaxLength(max=20)
	private String nyukaiHiyoKbn;

	/**
	 * 基本料金
	 */
	@NotEmpty
	@Num
	@MaxLength(max=7)
	private String kihonRyokin;

	/**
	 * 入居区分
	 */
	@NotEmpty
	@MaxLength(max=20)
	private String nyukyoKbn;

	/**
	 * サービス開始日
	 */
	@Time(pattern="yyyyMMdd")
	@MaxLength(max=8)
	private String serviceStartDt;

	/**
	 * 性別
	 */
	@NotEmpty
	@MaxLength(max=20)
	private String sexKbn;

	/**
	 * 生年月日
	 */
	@Time(pattern="yyyyMMdd")
	@MaxLength(max=8)
	private String birthDt;

	/**
	 * 申込時年齢
	 */
	@Num
	@MaxLength(max=3)
	private String moshikomiNenrei;

	/**
	 * 部屋番号
	 */
	@NotEmpty
	@Hankaku
	@MaxLength(max=10)
	private String roomNo;

	/**
	 * メールアドレス
	 */
	@MailAddress
	@MaxLength(max=256)
	private String mailAddress;

	/**
	 * メールアドレス区分
	 */
	@MaxLength(max=20)
	private String mailAddressKbn;

	/**
	 * 緊急連絡先カナ名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kinkyuKanaNm;

	/**
	 * 緊急連絡先漢字名
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kinkyuKanjiNm;

	/**
	 * 緊急連絡先続柄区分
	 */
	@MaxLength(max=20)
	private String kinkyuTsudukiKbn;

	/**
	 * 緊急連絡先ＴＥＬ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kinkyuTel;

	/**
	 * 緊急連絡先住所
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String kinkyuJusho;

	/**
	 * 同居人カナ名１
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoKanaNm1;

	/**
	 * 同居人漢字名１
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoKanjiNm1;

	/**
	 * 同居人続柄区分１
	 */
	@MaxLength(max=20)
	private String dokyoTsudukiKbn1;

	/**
	 * 同居人連絡先ＴＥＬ１
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoTel1;

	/**
	 * 同居人連絡先住所１
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String dokyoJusho1;

	/**
	 * 同居人カナ名２
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoKanaNm2;

	/**
	 * 同居人漢字名２
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoKanjiNm2;

	/**
	 * 同居人続柄区分２
	 */
	@MaxLength(max=20)
	private String dokyoTsudukiKbn2;

	/**
	 * 同居人連絡先ＴＥＬ２
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoTel2;

	/**
	 * 同居人連絡先住所２
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String dokyoJusho2;

	/**
	 * 管理会社カナ名
	 */
	@NotEmpty
	@Zenhankaku
	@MaxLength(max=50)
	private String kanriKanaNm;

	/**
	 * 管理会社漢字名
	 */
	@NotEmpty
	@Zenhankaku
	@MaxLength(max=100)
	private String kanriKanjiNm;

	/**
	 * 管理会社ＴＥＬ
	 */
	@NotEmpty
	@Zenhankaku
	@MaxLength(max=50)
	private String kanriTel;


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
	 * カナ氏名（姓）を取得します。
	 *
	 * @return カナ氏名（姓）
	 */
	public String getKanaNm1() {
		return kanaNm1;
	}

	/**
	 * カナ氏名（姓）を設定します。
	 *
	 * @param kanaNm1 カナ氏名（姓）
	 */
	public void setKanaNm1(String kanaNm1) {
		this.kanaNm1 = kanaNm1;
	}

	/**
	 * カナ氏名（名）を取得します。
	 *
	 * @return カナ氏名（名）
	 */
	public String getKanaNm2() {
		return kanaNm2;
	}

	/**
	 * カナ氏名（名）を設定します。
	 *
	 * @param kanaNm2 カナ氏名（名）
	 */
	public void setKanaNm2(String kanaNm2) {
		this.kanaNm2 = kanaNm2;
	}

	/**
	 * 漢字氏名（姓）を取得します。
	 *
	 * @return 漢字氏名（姓）
	 */
	public String getKanjiNm1() {
		return kanjiNm1;
	}

	/**
	 * 漢字氏名（姓）を設定します。
	 *
	 * @param kanjiNm1 漢字氏名（姓）
	 */
	public void setKanjiNm1(String kanjiNm1) {
		this.kanjiNm1 = kanjiNm1;
	}

	/**
	 * 漢字氏名（名）を取得します。
	 *
	 * @return 漢字氏名（名）
	 */
	public String getKanjiNm2() {
		return kanjiNm2;
	}

	/**
	 * 漢字氏名（名）を設定します。
	 *
	 * @param kanjiNm2 漢字氏名（名）
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
	 * 住所５　アパートマンションを取得します。
	 *
	 * @return 住所５　アパートマンション
	 */
	public String getJusho5() {
		return jusho5;
	}

	/**
	 * 住所５　アパートマンションを設定します。
	 *
	 * @param jusho5 住所５　アパートマンション
	 */
	public void setJusho5(String jusho5) {
		this.jusho5 = jusho5;
	}

	/**
	 * 部屋番号を取得します。
	 *
	 * @return 部屋番号
	 */
	public String getKokyakuRoomNo() {
		return kokyakuRoomNo;
	}

	/**
	 * 部屋番号を設定します。
	 *
	 * @param kokyakuRoomNo 部屋番号
	 */
	public void setKokyakuRoomNo(String kokyakuRoomNo) {
		this.kokyakuRoomNo = kokyakuRoomNo;
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
	 * 入居日を取得します。
	 *
	 * @return 入居日
	 */
	public String getNyukyoDt() {
		return nyukyoDt;
	}

	/**
	 * 入居日を設定します。
	 *
	 * @param nyukyoDt 入居日
	 */
	public void setNyukyoDt(String nyukyoDt) {
		this.nyukyoDt = nyukyoDt;
	}

	/**
	 * 退居日を取得します。
	 *
	 * @return 退居日
	 */
	public String getTaikyoDt() {
		return taikyoDt;
	}

	/**
	 * 退居日を設定します。
	 *
	 * @param taikyoDt 退居日
	 */
	public void setTaikyoDt(String taikyoDt) {
		this.taikyoDt = taikyoDt;
	}

	/**
	 * 緊急連絡先を取得します。
	 *
	 * @return 緊急連絡先
	 */
	public String getKinkyuRenrakusaki() {
		return kinkyuRenrakusaki;
	}

	/**
	 * 緊急連絡先を設定します。
	 *
	 * @param kinkyuRenrakusaki 緊急連絡先
	 */
	public void setKinkyuRenrakusaki(String kinkyuRenrakusaki) {
		this.kinkyuRenrakusaki = kinkyuRenrakusaki;
	}

	/**
	 * サービス区分を取得します。
	 *
	 * @return サービス区分
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
	 * 申込日を取得します。
	 *
	 * @return 申込日
	 */
	public String getMoshikomiDt() {
		return moshikomiDt;
	}

	/**
	 * 申込日を設定します。
	 *
	 * @param moshikomiDt 申込日
	 */
	public void setMoshikomiDt(String moshikomiDt) {
		this.moshikomiDt = moshikomiDt;
	}

	/**
	 * 契約開始日を取得します。
	 *
	 * @return 契約開始日
	 */
	public String getKeiyakuStartDt() {
		return keiyakuStartDt;
	}

	/**
	 * 契約開始日を設定します。
	 *
	 * @param keiyakuStartDt 契約開始日
	 */
	public void setKeiyakuStartDt(String keiyakuStartDt) {
		this.keiyakuStartDt = keiyakuStartDt;
	}

	/**
	 * 契約終了日を取得します。
	 *
	 * @return 契約終了日
	 */
	public String getKeiyakuEndDt() {
		return keiyakuEndDt;
	}

	/**
	 * 契約終了日を設定します。
	 *
	 * @param keiyakuEndDt 契約終了日
	 */
	public void setKeiyakuEndDt(String keiyakuEndDt) {
		this.keiyakuEndDt = keiyakuEndDt;
	}

	/**
	 * 受電窓口電話番号を取得します。
	 *
	 * @return 受電窓口電話番号
	 */
	public String getMadoguchiTel() {
		return madoguchiTel;
	}

	/**
	 * 受電窓口電話番号を設定します。
	 *
	 * @param madoguchiTel 受電窓口電話番号
	 */
	public void setMadoguchiTel(String madoguchiTel) {
		this.madoguchiTel = madoguchiTel;
	}

	/**
	 * 備考を取得します。
	 *
	 * @return 備考
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * 備考を設定します。
	 *
	 * @param biko 備考
	 */
	public void setBiko(String biko) {
		this.biko = biko;
	}

	/**
	 * 取扱店コードを取得します。
	 *
	 * @return 取扱店コード
	 */
	public String getToriCd() {
		return toriCd;
	}

	/**
	 * 取扱店コードを設定します。
	 *
	 * @param toriCd 取扱店コード
	 */
	public void setToriCd(String toriCd) {
		this.toriCd = toriCd;
	}

	/**
	 * 取扱店名を取得します。
	 *
	 * @return 取扱店名
	 */
	public String getToriNm() {
		return toriNm;
	}

	/**
	 * 取扱店名を設定します。
	 *
	 * @param toriNm 取扱店名
	 */
	public void setToriNm(String toriNm) {
		this.toriNm = toriNm;
	}

	/**
	 * 担当者名を取得します。
	 *
	 * @return 担当者名
	 */
	public String getTantoshaNm() {
		return tantoshaNm;
	}

	/**
	 * 担当者名を設定します。
	 *
	 * @param tantoshaNm 担当者名
	 */
	public void setTantoshaNm(String tantoshaNm) {
		this.tantoshaNm = tantoshaNm;
	}

	/**
	 * 請求先を取得します。
	 *
	 * @return 請求先
	 */
	public String getSeikyusakiCd() {
		return seikyusakiCd;
	}

	/**
	 * 請求先を設定します。
	 *
	 * @param seikyusakiCd 請求先
	 */
	public void setSeikyusakiCd(String seikyusakiCd) {
		this.seikyusakiCd = seikyusakiCd;
	}

	/**
	 * 請求先メモを取得します。
	 *
	 * @return 請求先メモ
	 */
	public String getSeikyusakiMemo() {
		return seikyusakiMemo;
	}

	/**
	 * 請求先メモを設定します。
	 *
	 * @param seikyusakiMemo 請求先メモ
	 */
	public void setSeikyusakiMemo(String seikyusakiMemo) {
		this.seikyusakiMemo = seikyusakiMemo;
	}

	/**
	 * 請求方法を取得します。
	 *
	 * @return 請求方法
	 */
	public String getSeikyuKbn() {
		return seikyuKbn;
	}

	/**
	 * 請求方法を設定します。
	 *
	 * @param seikyuKbn 請求方法
	 */
	public void setSeikyuKbn(String seikyuKbn) {
		this.seikyuKbn = seikyuKbn;
	}

	/**
	 * 銀行コードを取得します。
	 *
	 * @return 銀行コード
	 */
	public String getGinkoCd() {
		return ginkoCd;
	}

	/**
	 * 銀行コードを設定します。
	 *
	 * @param ginkoCd 銀行コード
	 */
	public void setGinkoCd(String ginkoCd) {
		this.ginkoCd = ginkoCd;
	}

	/**
	 * 支店コードを取得します。
	 *
	 * @return 支店コード
	 */
	public String getShitenCd() {
		return shitenCd;
	}

	/**
	 * 支店コードを設定します。
	 *
	 * @param shitenCd 支店コード
	 */
	public void setShitenCd(String shitenCd) {
		this.shitenCd = shitenCd;
	}

	/**
	 * 口座番号区分を取得します。
	 *
	 * @return 口座番号区分
	 */
	public String getKozaNoKbn() {
		return kozaNoKbn;
	}

	/**
	 * 口座番号区分を設定します。
	 *
	 * @param kozaNoKbn 口座番号区分
	 */
	public void setKozaNoKbn(String kozaNoKbn) {
		this.kozaNoKbn = kozaNoKbn;
	}

	/**
	 * 口座番号を取得します。
	 *
	 * @return 口座番号
	 */
	public String getKozaNo() {
		return kozaNo;
	}

	/**
	 * 口座番号を設定します。
	 *
	 * @param kozaNo 口座番号
	 */
	public void setKozaNo(String kozaNo) {
		this.kozaNo = kozaNo;
	}

	/**
	 * 名義人を取得します。
	 *
	 * @return 名義人
	 */
	public String getMeiginin() {
		return meiginin;
	}

	/**
	 * 名義人を設定します。
	 *
	 * @param meiginin 名義人
	 */
	public void setMeiginin(String meiginin) {
		this.meiginin = meiginin;
	}

	/**
	 * カード番号１を取得します。
	 *
	 * @return カード番号１
	 */
	public String getCardNo1() {
		return cardNo1;
	}

	/**
	 * カード番号１を設定します。
	 *
	 * @param cardNo1 カード番号１
	 */
	public void setCardNo1(String cardNo1) {
		this.cardNo1 = cardNo1;
	}

	/**
	 * カード番号２を取得します。
	 *
	 * @return カード番号２
	 */
	public String getCardNo2() {
		return cardNo2;
	}

	/**
	 * カード番号２を設定します。
	 *
	 * @param cardNo2 カード番号２
	 */
	public void setCardNo2(String cardNo2) {
		this.cardNo2 = cardNo2;
	}

	/**
	 * カード番号３を取得します。
	 *
	 * @return カード番号３
	 */
	public String getCardNo3() {
		return cardNo3;
	}

	/**
	 * カード番号３を設定します。
	 *
	 * @param cardNo3 カード番号３
	 */
	public void setCardNo3(String cardNo3) {
		this.cardNo3 = cardNo3;
	}

	/**
	 * カード番号４を取得します。
	 *
	 * @return カード番号４
	 */
	public String getCardNo4() {
		return cardNo4;
	}

	/**
	 * カード番号４を設定します。
	 *
	 * @param cardNo4 カード番号４
	 */
	public void setCardNo4(String cardNo4) {
		this.cardNo4 = cardNo4;
	}

	/**
	 * 有効期限を取得します。
	 *
	 * @return 有効期限
	 */
	public String getYukoKigen() {
		return yukoKigen;
	}

	/**
	 * 有効期限を設定します。
	 *
	 * @param yukoKigen 有効期限
	 */
	public void setYukoKigen(String yukoKigen) {
		this.yukoKigen = yukoKigen;
	}

	/**
	 * 請求先顧客ＩＤを取得します。
	 *
	 * @return 請求先顧客ＩＤ
	 */
	public String getSeikyusakiKokyakuId() {
		return seikyusakiKokyakuId;
	}

	/**
	 * 請求先顧客ＩＤを設定します。
	 *
	 * @param seikyusakiKokyakuId 請求先顧客ＩＤ
	 */
	public void setSeikyusakiKokyakuId(String seikyusakiKokyakuId) {
		this.seikyusakiKokyakuId = seikyusakiKokyakuId;
	}

	/**
	 * 顧客請求開始日を取得します。
	 *
	 * @return 顧客請求開始日
	 */
	public String getKokyakuSeikyuStartDt() {
		return kokyakuSeikyuStartDt;
	}

	/**
	 * 顧客請求開始日を設定します。
	 *
	 * @param kokyakuSeikyuStartDt 顧客請求開始日
	 */
	public void setKokyakuSeikyuStartDt(String kokyakuSeikyuStartDt) {
		this.kokyakuSeikyuStartDt = kokyakuSeikyuStartDt;
	}

	/**
	 * 顧客請求終了日を取得します。
	 *
	 * @return 顧客請求終了日
	 */
	public String getKokyakuSeikyuEndDt() {
		return kokyakuSeikyuEndDt;
	}

	/**
	 * 顧客請求終了日を設定します。
	 *
	 * @param kokyakuSeikyuEndDt 顧客請求終了日
	 */
	public void setKokyakuSeikyuEndDt(String kokyakuSeikyuEndDt) {
		this.kokyakuSeikyuEndDt = kokyakuSeikyuEndDt;
	}

	/**
	 * 管理費振替フラグを取得します。
	 *
	 * @return 管理費振替フラグ
	 */
	public String getKanrihiFurikaeFlg() {
		return kanrihiFurikaeFlg;
	}

	/**
	 * 管理費振替フラグを設定します。
	 *
	 * @param kanrihiFurikaeFlg 管理費振替フラグ
	 */
	public void setKanrihiFurikaeFlg(String kanrihiFurikaeFlg) {
		this.kanrihiFurikaeFlg = kanrihiFurikaeFlg;
	}

	/**
	 * 会員番号を取得します。
	 *
	 * @return 会員番号
	 */
	public String getKaiinNo() {
		return kaiinNo;
	}

	/**
	 * 会員番号を設定します。
	 *
	 * @param kaiinNo 会員番号
	 */
	public void setKaiinNo(String kaiinNo) {
		this.kaiinNo = kaiinNo;
	}

	/**
	 * ＷＥＬＢＯＸフラグを取得します。
	 *
	 * @return ＷＥＬＢＯＸフラグ
	 */
	public String getWelboxFlg() {
		return welboxFlg;
	}

	/**
	 * ＷＥＬＢＯＸフラグを設定します。
	 *
	 * @param welboxFlg ＷＥＬＢＯＸフラグ
	 */
	public void setWelboxFlg(String welboxFlg) {
		this.welboxFlg = welboxFlg;
	}

	/**
	 * 申込区分を取得します。
	 *
	 * @return 申込区分
	 */
	public String getMoshikomiKbn() {
		return moshikomiKbn;
	}

	/**
	 * 申込区分を設定します。
	 *
	 * @param moshikomiKbn 申込区分
	 */
	public void setMoshikomiKbn(String moshikomiKbn) {
		this.moshikomiKbn = moshikomiKbn;
	}

	/**
	 * 建物形態を取得します。
	 *
	 * @return 建物形態
	 */
	public String getTatemonoType() {
		return tatemonoType;
	}

	/**
	 * 建物形態を設定します。
	 *
	 * @param tatemonoType 建物形態
	 */
	public void setTatemonoType(String tatemonoType) {
		this.tatemonoType = tatemonoType;
	}

	/**
	 * 入会費用区分を取得します。
	 *
	 * @return 入会費用区分
	 */
	public String getNyukaiHiyoKbn() {
		return nyukaiHiyoKbn;
	}

	/**
	 * 入会費用区分を設定します。
	 *
	 * @param nyukaiHiyoKbn 入会費用区分
	 */
	public void setNyukaiHiyoKbn(String nyukaiHiyoKbn) {
		this.nyukaiHiyoKbn = nyukaiHiyoKbn;
	}

	/**
	 * 基本料金を取得します。
	 *
	 * @return 基本料金
	 */
	public String getKihonRyokin() {
		return kihonRyokin;
	}

	/**
	 * 基本料金を設定します。
	 *
	 * @param kihonRyokin 基本料金
	 */
	public void setKihonRyokin(String kihonRyokin) {
		this.kihonRyokin = kihonRyokin;
	}

	/**
	 * 入居区分を取得します。
	 *
	 * @return 入居区分
	 */
	public String getNyukyoKbn() {
		return nyukyoKbn;
	}

	/**
	 * 入居区分を設定します。
	 *
	 * @param nyukyoKbn 入居区分
	 */
	public void setNyukyoKbn(String nyukyoKbn) {
		this.nyukyoKbn = nyukyoKbn;
	}

	/**
	 * サービス開始日を取得します。
	 *
	 * @return サービス開始日
	 */
	public String getServiceStartDt() {
		return serviceStartDt;
	}

	/**
	 * サービス開始日を設定します。
	 *
	 * @param serviceStartDt サービス開始日
	 */
	public void setServiceStartDt(String serviceStartDt) {
		this.serviceStartDt = serviceStartDt;
	}

	/**
	 * 性別を取得します。
	 *
	 * @return 性別
	 */
	public String getSexKbn() {
		return sexKbn;
	}

	/**
	 * 性別を設定します。
	 *
	 * @param sexKbn 性別
	 */
	public void setSexKbn(String sexKbn) {
		this.sexKbn = sexKbn;
	}

	/**
	 * 生年月日を取得します。
	 *
	 * @return 生年月日
	 */
	public String getBirthDt() {
		return birthDt;
	}

	/**
	 * 生年月日を設定します。
	 *
	 * @param birthDt 生年月日
	 */
	public void setBirthDt(String birthDt) {
		this.birthDt = birthDt;
	}

	/**
	 * 申込時年齢を取得します。
	 *
	 * @return 申込時年齢
	 */
	public String getMoshikomiNenrei() {
		return moshikomiNenrei;
	}

	/**
	 * 申込時年齢を設定します。
	 *
	 * @param moshikomiNenrei 申込時年齢
	 */
	public void setMoshikomiNenrei(String moshikomiNenrei) {
		this.moshikomiNenrei = moshikomiNenrei;
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
	 * メールアドレスを取得します。
	 *
	 * @return メールアドレス
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * メールアドレスを設定します。
	 *
	 * @param mailAddress メールアドレス
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * メールアドレス区分を取得します。
	 *
	 * @return メールアドレス区分
	 */
	public String getMailAddressKbn() {
		return mailAddressKbn;
	}

	/**
	 * メールアドレス区分を設定します。
	 *
	 * @param mailAddressKbn メールアドレス区分
	 */
	public void setMailAddressKbn(String mailAddressKbn) {
		this.mailAddressKbn = mailAddressKbn;
	}

	/**
	 * 緊急連絡先カナ名を取得します。
	 *
	 * @return 緊急連絡先カナ名
	 */
	public String getKinkyuKanaNm() {
		return kinkyuKanaNm;
	}

	/**
	 * 緊急連絡先カナ名を設定します。
	 *
	 * @param kinkyuKanaNm 緊急連絡先カナ名
	 */
	public void setKinkyuKanaNm(String kinkyuKanaNm) {
		this.kinkyuKanaNm = kinkyuKanaNm;
	}

	/**
	 * 緊急連絡先漢字名を取得します。
	 *
	 * @return 緊急連絡先漢字名
	 */
	public String getKinkyuKanjiNm() {
		return kinkyuKanjiNm;
	}

	/**
	 * 緊急連絡先漢字名を設定します。
	 *
	 * @param kinkyuKanjiNm 緊急連絡先漢字名
	 */
	public void setKinkyuKanjiNm(String kinkyuKanjiNm) {
		this.kinkyuKanjiNm = kinkyuKanjiNm;
	}

	/**
	 * 緊急連絡先続柄区分を取得します。
	 *
	 * @return 緊急連絡先続柄区分
	 */
	public String getKinkyuTsudukiKbn() {
		return kinkyuTsudukiKbn;
	}

	/**
	 * 緊急連絡先続柄区分を設定します。
	 *
	 * @param kinkyuTsudukiKbn 緊急連絡先続柄区分
	 */
	public void setKinkyuTsudukiKbn(String kinkyuTsudukiKbn) {
		this.kinkyuTsudukiKbn = kinkyuTsudukiKbn;
	}

	/**
	 * 緊急連絡先ＴＥＬを取得します。
	 *
	 * @return 緊急連絡先ＴＥＬ
	 */
	public String getKinkyuTel() {
		return kinkyuTel;
	}

	/**
	 * 緊急連絡先ＴＥＬを設定します。
	 *
	 * @param kinkyuTel 緊急連絡先ＴＥＬ
	 */
	public void setKinkyuTel(String kinkyuTel) {
		this.kinkyuTel = kinkyuTel;
	}

	/**
	 * 緊急連絡先住所を取得します。
	 *
	 * @return 緊急連絡先住所
	 */
	public String getKinkyuJusho() {
		return kinkyuJusho;
	}

	/**
	 * 緊急連絡先住所を設定します。
	 *
	 * @param kinkyuJusho 緊急連絡先住所
	 */
	public void setKinkyuJusho(String kinkyuJusho) {
		this.kinkyuJusho = kinkyuJusho;
	}

	/**
	 * 同居人カナ名１を取得します。
	 *
	 * @return 同居人カナ名１
	 */
	public String getDokyoKanaNm1() {
		return dokyoKanaNm1;
	}

	/**
	 * 同居人カナ名１を設定します。
	 *
	 * @param dokyoKanaNm1 同居人カナ名１
	 */
	public void setDokyoKanaNm1(String dokyoKanaNm1) {
		this.dokyoKanaNm1 = dokyoKanaNm1;
	}

	/**
	 * 同居人漢字名１を取得します。
	 *
	 * @return 同居人漢字名１
	 */
	public String getDokyoKanjiNm1() {
		return dokyoKanjiNm1;
	}

	/**
	 * 同居人漢字名１を設定します。
	 *
	 * @param dokyoKanjiNm1 同居人漢字名１
	 */
	public void setDokyoKanjiNm1(String dokyoKanjiNm1) {
		this.dokyoKanjiNm1 = dokyoKanjiNm1;
	}

	/**
	 * 同居人続柄区分１を取得します。
	 *
	 * @return 同居人続柄区分１
	 */
	public String getDokyoTsudukiKbn1() {
		return dokyoTsudukiKbn1;
	}

	/**
	 * 同居人続柄区分１を設定します。
	 *
	 * @param dokyoTsudukiKbn1 同居人続柄区分１
	 */
	public void setDokyoTsudukiKbn1(String dokyoTsudukiKbn1) {
		this.dokyoTsudukiKbn1 = dokyoTsudukiKbn1;
	}

	/**
	 * 同居人連絡先ＴＥＬ１を取得します。
	 *
	 * @return 同居人連絡先ＴＥＬ１
	 */
	public String getDokyoTel1() {
		return dokyoTel1;
	}

	/**
	 * 同居人連絡先ＴＥＬ１を設定します。
	 *
	 * @param dokyoTel1 同居人連絡先ＴＥＬ１
	 */
	public void setDokyoTel1(String dokyoTel1) {
		this.dokyoTel1 = dokyoTel1;
	}

	/**
	 * 同居人連絡先住所１を取得します。
	 *
	 * @return 同居人連絡先住所１
	 */
	public String getDokyoJusho1() {
		return dokyoJusho1;
	}

	/**
	 * 同居人連絡先住所１を設定します。
	 *
	 * @param dokyoJusho1 同居人連絡先住所１
	 */
	public void setDokyoJusho1(String dokyoJusho1) {
		this.dokyoJusho1 = dokyoJusho1;
	}

	/**
	 * 同居人カナ名２を取得します。
	 *
	 * @return 同居人カナ名２
	 */
	public String getDokyoKanaNm2() {
		return dokyoKanaNm2;
	}

	/**
	 * 同居人カナ名２を設定します。
	 *
	 * @param dokyoKanaNm2 同居人カナ名２
	 */
	public void setDokyoKanaNm2(String dokyoKanaNm2) {
		this.dokyoKanaNm2 = dokyoKanaNm2;
	}

	/**
	 * 同居人漢字名２を取得します。
	 *
	 * @return 同居人漢字名２
	 */
	public String getDokyoKanjiNm2() {
		return dokyoKanjiNm2;
	}

	/**
	 * 同居人漢字名２を設定します。
	 *
	 * @param dokyoKanjiNm2 同居人漢字名２
	 */
	public void setDokyoKanjiNm2(String dokyoKanjiNm2) {
		this.dokyoKanjiNm2 = dokyoKanjiNm2;
	}

	/**
	 * 同居人続柄区分２を取得します。
	 *
	 * @return 同居人続柄区分２
	 */
	public String getDokyoTsudukiKbn2() {
		return dokyoTsudukiKbn2;
	}

	/**
	 * 同居人続柄区分２を設定します。
	 *
	 * @param dokyoTsudukiKbn2 同居人続柄区分２
	 */
	public void setDokyoTsudukiKbn2(String dokyoTsudukiKbn2) {
		this.dokyoTsudukiKbn2 = dokyoTsudukiKbn2;
	}

	/**
	 * 同居人連絡先ＴＥＬ２を取得します。
	 *
	 * @return 同居人連絡先ＴＥＬ２
	 */
	public String getDokyoTel2() {
		return dokyoTel2;
	}

	/**
	 * 同居人連絡先ＴＥＬ２を設定します。
	 *
	 * @param dokyoTel2 同居人連絡先ＴＥＬ２
	 */
	public void setDokyoTel2(String dokyoTel2) {
		this.dokyoTel2 = dokyoTel2;
	}

	/**
	 * 同居人連絡先住所２を取得します。
	 *
	 * @return 同居人連絡先住所２
	 */
	public String getDokyoJusho2() {
		return dokyoJusho2;
	}

	/**
	 * 同居人連絡先住所２を設定します。
	 *
	 * @param dokyoJusho2 同居人連絡先住所２
	 */
	public void setDokyoJusho2(String dokyoJusho2) {
		this.dokyoJusho2 = dokyoJusho2;
	}

	/**
	 * 管理会社カナ名を取得します。
	 *
	 * @return 管理会社カナ名
	 */
	public String getKanriKanaNm() {
		return kanriKanaNm;
	}

	/**
	 * 管理会社カナ名を設定します。
	 *
	 * @param kanriKanaNm 管理会社カナ名
	 */
	public void setKanriKanaNm(String kanriKanaNm) {
		this.kanriKanaNm = kanriKanaNm;
	}

	/**
	 * 管理会社漢字名を取得します。
	 *
	 * @return 管理会社漢字名
	 */
	public String getKanriKanjiNm() {
		return kanriKanjiNm;
	}

	/**
	 * 管理会社漢字名を設定します。
	 *
	 * @param kanriKanjiNm 管理会社漢字名
	 */
	public void setKanriKanjiNm(String kanriKanjiNm) {
		this.kanriKanjiNm = kanriKanjiNm;
	}

	/**
	 * 管理会社ＴＥＬを取得します。
	 *
	 * @return 管理会社ＴＥＬ
	 */
	public String getKanriTel() {
		return kanriTel;
	}

	/**
	 * 管理会社ＴＥＬを設定します。
	 *
	 * @param kanriTel 管理会社ＴＥＬ
	 */
	public void setKanriTel(String kanriTel) {
		this.kanriTel = kanriTel;
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
	 * 顧客区分が「4：入居者・個人」かどうかチェックします。
	 *
	 * @return true：顧客区分が「4：入居者・個人」
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="4または「4：入居者・個人」を指定して下さい。")
	private boolean isKokyakuKbn() {
		return kokyakuKbn.equals(KOKYAKU_KBN_NYUKYOSHA) || kokyakuKbn.equals(RcpMKokyaku.KOKYAKU_KBN_NYUKYOSHA);
	}

	/**
	 * 顧客種別（個人/法人）が「0：個人」かどうかチェックします。
	 *
	 * @return true：顧客種別（個人/法人）が「0：個人」
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="0または「0：個人」を指定して下さい。")
	private boolean isKokyakuShubetsu() {
		return kokyakuShubetsu.equals(KOKYAKU_SHUBETSU_KOJIN) || kokyakuShubetsu.equals(RcpMKokyaku.KOKYAKU_SHUBETU_KOJIN);
	}

	/**
	 * サービス区分が「2：ライフ」かどうかチェックします。
	 *
	 * @return true：サービス区分が「2：ライフ」
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="2または「2：ライフ」を指定して下さい。")
	private boolean isServiceKbn() {
		return serviceKbn.equals(SERVICE_KBN_LIFE) || serviceKbn.equals(RcpMService.SERVICE_KBN_LIFE_SUPPORT24);
	}

}
