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
 * ŠÇ—î•ñƒAƒbƒvƒ[ƒh(•¨Œ)DTOB
 *
 * @author v140546
 * @version 1.0 2014/08/04
 * @version 1.1 2015/10/28 J.Matsuba €–Ú’Ç‰Á‚É‚æ‚éC³B
 * @version 1.2 2015/11/10 J.Matsuba €–Ú’Ç‰ÁEíœ‚É‚æ‚éC³B
 * @version 1.3 2016/02/12 H.Yamamura •”‰®”Ô†‚Ì“ü—Íƒ`ƒFƒbƒN‚ğ‘SŠp”š¨‘SŠp•¶š‚É•ÏX
 */
public class TB043CustomerUploadForRealEstateDto extends TB043CSVUploadCommonDto{

	/** €–Ú” */
	public static final int ITEM_COUNT = 139;

	/** CSVƒJƒ‰ƒ€—ñiƒL[j */
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

	/** CSVƒJƒ‰ƒ€—ñi–¼‘Oj */
	public static final String[] CSV_COLUMNS_NAME = new String[] {
		"ŒÚ‹q‹æ•ª",
		"ŒÚ‹qí•ÊiŒÂl/–@lj",
		"ƒJƒi•¨Œ–¼",
		"i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj",
		"•¨Œ–¼",
		"i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj",
		"—X•Ö”Ô†",
		"ZŠ‚P@“s“¹•{Œ§",
		"ZŠ‚Q@s‹æ’¬‘º",
		"ZŠ‚R@’¬/‘åš",
		"ZŠ‚S@”Ô’n",
		"•¨Œ–¼",
		"•”‰®”Ô†",
		"“d˜b”Ô†‚P",
		"“d˜b”Ô†‚Q",
		"‚e‚`‚w”Ô†",
		"’ˆÓ–€‚P",
		"’ˆÓ–€‚Q",
		"’ˆÓ–€‚R",
		"’ˆÓ–€‚S",
		"’ˆÓ–€‚S•\¦ŠJn“ú",
		"’ˆÓ–€‚S•\¦I—¹“ú",
		"’ˆÓ–€‚T",
		"’ˆÓ–€‚T•\¦ŠJn“ú",
		"’ˆÓ–€‚T•\¦I—¹“ú",
		"’ˆÓ–€‚U",
		"’ˆÓ–€‚U•\¦ŠJn“ú",
		"’ˆÓ–€‚U•\¦I—¹“ú",
		"’÷‚ß“ú",
		"\‘¢",
		"ŠK”",
		"’z”NŒ",
		"ŒË”",
		"’S“–Ò–¼‚P",
		"’S“–Ò–¼‚Q",
		"ŠÇ—Œ`‘Ô",
		"˜A—æ‚P",
		"˜A—æ‚Q",
		"ƒ|ƒ“ƒvºƒƒ‚",
		"ƒI[ƒgƒƒbƒNƒƒ‚",
		"ƒ[ƒ‹‚a‚n‚w",
		"”õl",
		"ƒI[ƒi[–¼",
		"ƒI[ƒi[“d˜b”Ô†",
		"ƒI[ƒi[ZŠ",
		"ƒI[ƒi[”õl",
		"‹¤—p•”ŠÇ—",
		"‹¤—p•”ŠÇ—‰c‹Æ“ú",
		"‹¤—p•”ŠÇ—‰c‹ÆŠÔ",
		"‹¤—p•”ŠÇ—‚s‚d‚k",
		"‹¤—p•”ŠÇ—ŠÔŠO‚s‚d‚k",
		"‹¤—p•”ŠÇ—E-mail",
		"‹¤—p•”ŠÇ—”õl",
		"ŠÇ—l–¼",
		"ŠÇ—l‰c‹Æ“ú",
		"ŠÇ—l‰c‹ÆŠÔ",
		"ŠÇ—l‚s‚d‚k",
		"ŠÇ—lE-mail",
		"ŠÇ—l”õl",
		"Œx”õ‰ïĞ",
		"Œx”õ‰ïĞ‚s‚d‚k",
		"Œx”õ‰ïĞ‚e‚`‚w",
		"Œx”õ‰ïĞE-mail",
		"Œx”õ‰ïĞ”õl",
		"…“¹‹ÆÒ‡@",
		"…“¹‹ÆÒ‡@‚s‚d‚k",
		"…“¹‹ÆÒ‡@‚e‚`‚w",
		"…“¹‹ÆÒ‡@E-mail",
		"…“¹‹ÆÒ‡@”õl",
		"‹‹”r…‹ÆÒ‡Aƒ|ƒ“ƒv",
		"‹‹”r…‹ÆÒ‡Aƒ|ƒ“ƒv‚s‚d‚k",
		"‹‹”r…‹ÆÒ‡Aƒ|ƒ“ƒv‚e‚`‚w",
		"‹‹”r…‹ÆÒ‡Aƒ|ƒ“ƒvE-mail",
		"‹‹”r…‹ÆÒ‡Aƒ|ƒ“ƒv”õl",
		"ƒKƒX‰ïĞ",
		"ƒKƒX‰ïĞ‚s‚d‚k",
		"ƒKƒX‰ïĞ‚e‚`‚w",
		"ƒKƒX‰ïĞE-mail",
		"ƒKƒX‰ïĞ”õl",
		"‹‹“’Ší•Ûç‹ÆÒ",
		"‹‹“’Ší•Ûç‹ÆÒ‚s‚d‚k",
		"‹‹“’Ší•Ûç‹ÆÒ‚e‚`‚w",
		"‹‹“’Ší•Ûç‹ÆÒE-mail",
		"‹‹“’Ší•Ûç‹ÆÒ”õl",
		"ƒGƒAƒRƒ“•Ûç‹ÆÒ",
		"ƒGƒAƒRƒ“•Ûç‹ÆÒ‚s‚d‚k",
		"ƒGƒAƒRƒ“•Ûç‹ÆÒ‚e‚`‚w",
		"ƒGƒAƒRƒ“•Ûç‹ÆÒE-mail",
		"ƒGƒAƒRƒ“•Ûç‹ÆÒ”õl",
		"“d‹C•Ûç‹ÆÒ",
		"“d‹C•Ûç‹ÆÒ‚s‚d‚k",
		"“d‹C•Ûç‹ÆÒ‚e‚`‚w",
		"“d‹C•Ûç‹ÆÒE-mail",
		"“d‹C•Ûç‹ÆÒ”õl",
		"‚d‚u‰ïĞ",
		"‚d‚u‰ïĞ‚s‚d‚k",
		"‚d‚u‰ïĞ‚e‚`‚w",
		"‚d‚u‰ïĞE-mail",
		"‚d‚u‰ïĞ”õl",
		"Œ®‹ÆÒ",
		"Œ®‹ÆÒ‚s‚d‚k",
		"Œ®‹ÆÒ‚e‚`‚w",
		"Œ®‹ÆÒE-mail",
		"Œ®‹ÆÒ”õl",
		"Á–h•Ûç",
		"Á–h•Ûç‚s‚d‚k",
		"Á–h•Ûç‚e‚`‚w",
		"Á–h•ÛçE-mail",
		"Á–h•Ûç”õl",
		"‚b‚`‚s‚u‰ïĞ",
		"‚b‚`‚s‚u‰ïĞ‚s‚d‚k",
		"‚b‚`‚s‚u‰ïĞ‚e‚`‚w",
		"‚b‚`‚s‚u‰ïĞE-mail",
		"‚b‚`‚s‚u‰ïĞ”õl",
		"¬C‘U‰ïĞ",
		"¬C‘U‰ïĞ‚s‚d‚k",
		"¬C‘U‰ïĞ‚e‚`‚w",
		"¬C‘U‰ïĞE-mail",
		"¬C‘U‰ïĞ”õl",
		"ƒKƒ‰ƒX‹ÆÒ",
		"ƒKƒ‰ƒX‹ÆÒ‚s‚d‚k",
		"ƒKƒ‰ƒX‹ÆÒ‚e‚`‚w",
		"ƒKƒ‰ƒX‹ÆÒE-mail",
		"ƒKƒ‰ƒX‹ÆÒ”õl",
		"‚»‚Ì‘¼‚P",
		"‚»‚Ì‘¼‚P‚s‚d‚k",
		"‚»‚Ì‘¼‚P‚e‚`‚w",
		"‚»‚Ì‘¼‚PE-mail",
		"‚»‚Ì‘¼‚P”õl",
		"‚»‚Ì‘¼‚Q",
		"‚»‚Ì‘¼‚Q‚s‚d‚k",
		"‚»‚Ì‘¼‚Q‚e‚`‚w",
		"‚»‚Ì‘¼‚QE-mail",
		"‚»‚Ì‘¼‚Q”õl",
		"‚»‚Ì‘¼‚R",
		"‚»‚Ì‘¼‚R‚s‚d‚k",
		"‚»‚Ì‘¼‚R‚e‚`‚w",
		"‚»‚Ì‘¼‚RE-mail",
		"‚»‚Ì‘¼‚R”õl"
	};

	/** ŒÚ‹q‹æ•ªFu3F•¨Œv */
	private static final String KOKYAKU_KBN_BUKKEN = "3F•¨Œ";

	/** ŒÚ‹qí•ÊFu1F–@lv */
	private static final String KOKYAKU_SHUBETSU_HOJIN = "1F–@l";

	/**
	 * ŒÚ‹q‹æ•ª
	 */
	@NotEmpty
	@MaxLength(max=15)
	private String kokyakuKbn;

	/**
	 * ŒÚ‹qí•ÊiŒÂl/–@lj
	 */
	@NotEmpty
	@MaxLength(max=7)
	private String kokyakuShubetsu;

	/**
	 * ƒJƒi•¨Œ–¼
	 */
	@NotEmpty
	@KanaName
	@MaxLength(max=40)
	private String kanaNm1;

	/**
	 * i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj
	 */
	@KanaName
	@MaxLength(max=40)
	private String kanaNm2;

	/**
	 * Š¿š•¨Œ–¼
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=40)
	private String kanjiNm1;

	/**
	 * i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj
	 */
	@Zenkaku
	@MaxLength(max=40)
	private String kanjiNm2;

	/**
	 * —X•Ö”Ô†
	 */
	@NotEmpty
	@Num
	@MaxLength(max=7)
	private String yubinNo;

	/**
	 * ZŠ‚P@“s“¹•{Œ§
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=10)
	private String jusho1;

	/**
	 * ZŠ‚Q@s‹æ’¬‘º
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=30)
	private String jusho2;

	/**
	 * ZŠ‚R@’¬/‘åš
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=30)
	private String jusho3;

	/**
	 * ZŠ‚S@”Ô’n
	 */
	@NotEmpty
	@ZenNumMinus
	@MaxLength(max=30)
	private String jusho4;

	/**
	 * •¨Œ–¼
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=40)
	private String jusho5;

	/**
	 * •”‰®”Ô†
	 */
	@Zenkaku
	@MaxLength(max=20)
	private String roomNo;

	/**
	 * “d˜b”Ô†‚P
	 */
	private String telNo1;

	/**
	 * “d˜b”Ô†‚Q
	 */
	private String telNo2;

	/**
	 * ‚e‚`‚w”Ô†
	 */
	private String faxNo;

	/**
	 * ’ˆÓ–€‚P
	 */
	private String attention1;

	/**
	 * ’ˆÓ–€‚Q
	 */
	private String attention2;

	/**
	 * ’ˆÓ–€‚R
	 */
	private String attention3;

	/**
	 * ’ˆÓ–€‚S
	 */
	private String attention4;

	/**
	 * ’ˆÓ–€‚S•\¦ŠJn“ú
	 */
	private String attention4StartDt;

	/**
	 * ’ˆÓ–€‚S•\¦I—¹“ú
	 */
	private String attention4EndDt;

	/**
	 * ’ˆÓ–€‚T
	 */
	private String attention5;

	/**
	 * ’ˆÓ–€‚T•\¦ŠJn“ú
	 */
	private String attention5StartDt;

	/**
	 * ’ˆÓ–€‚T•\¦I—¹“ú
	 */
	private String attention5EndDt;

	/**
	 * ’ˆÓ–€‚U
	 */
	private String attention6;

	/**
	 * ’ˆÓ–€‚U•\¦ŠJn“ú
	 */
	private String attention6StartDt;

	/**
	 * ’ˆÓ–€‚U•\¦I—¹“ú
	 */
	private String attention6EndDt;

	/**
	 * ’÷‚ß“ú
	 */
	private String shimeDay;

	/**
	 * \‘¢
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kozo;

	/**
	 * ŠK”
	 */
	@Num
	@MaxLength(max=3)
	private String kaisu;

	/**
	 * ’z”NŒ
	 */
	@Time(pattern="yyyyMM")
	@MaxLength(max=6)
	private String chikuNengetsu;

	/**
	 * ŒË”
	 */
	@Num
	@MaxLength(max=5)
	private String kosu;

	/**
	 * ’S“–Ò–¼‚P
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String tantoshaNm1;

	/**
	 * ’S“–Ò–¼‚Q
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String tantoshaNm2;

	/**
	 * ŠÇ—Œ`‘Ô‹æ•ª
	 */
	@MaxLength(max=15)
	private String kanriKeitaiKbn;

	/**
	 * ˜A—æ‚P
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String renrakusaki1;

	/**
	 * ˜A—æ‚Q
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String renrakusaki2;

	/**
	 * ƒ|ƒ“ƒvºƒƒ‚
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String pompMemo;

	/**
	 * ƒI[ƒgƒƒbƒNƒƒ‚
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String autoLockMemo;

	/**
	 * ƒ[ƒ‹‚a‚n‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String mailBox;

	/**
	 * ŠÇ—Œ`‘Ô”õl
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String biko;

	/**
	 * ƒI[ƒi[–¼
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String ooyaNm;

	/**
	 * ƒI[ƒi[“d˜b”Ô†
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String ooyaTel;

	/**
	 * ƒI[ƒi[ZŠ
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String ooyaJusho;

	/**
	 * ƒI[ƒi[”õl
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String ooyaBiko;

	/**
	 * ‹¤—p•”ŠÇ—
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriNm;

	/**
	 * ‹¤—p•”ŠÇ—‰c‹Æ“ú
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriEigyobi;

	/**
	 * ‹¤—p•”ŠÇ—‰c‹ÆŠÔ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriEigyoJikan;

	/**
	 * ‹¤—p•”ŠÇ—‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriTel;

	/**
	 * ‹¤—p•”ŠÇ—ŠÔŠO‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriJikangaiTel;

	/**
	 * ‹¤—p•”ŠÇ—E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kyoyoKanriMailAddress;

	/**
	 * ‹¤—p•”ŠÇ—”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kyoyoKanriBiko;

	/**
	 * ŠÇ—l–¼
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininNm;

	/**
	 * ŠÇ—l‰c‹Æ“ú
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininEigyobi;

	/**
	 * ŠÇ—l‰c‹ÆŠÔ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininEigyoJikan;

	/**
	 * ŠÇ—l–¼‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininTel;

	/**
	 * ŠÇ—lE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kanrininMailAddress;

	/**
	 * ŠÇ—l”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kanrininBiko;

	/**
	 * Œx”õ‰ïĞ–¼
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String keibiGaisha;

	/**
	 * Œx”õ‰ïĞ‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String keibiGaishaTel;

	/**
	 * Œx”õ‰ïĞ‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String keibiGaishaFax;

	/**
	 * Œx”õ‰ïĞE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String keibiGaishaMailAddress;

	/**
	 * Œx”õ‰ïĞ”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String keibiGaishaBiko;

	/**
	 * …“¹‹ÆÒ‡@
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String suidoGyosha;

	/**
	 * …“¹‹ÆÒ‡@‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String suidoGyoshaTel;

	/**
	 * …“¹‹ÆÒ‡@‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String suidoGyoshaFax;

	/**
	 * …“¹‹ÆÒ‡@E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String suidoGyoshaMailAddress;

	/**
	 * …“¹‹ÆÒ‡@”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String suidoGyoshaBiko;

	/**
	 * ‹‹”r…‹ÆÒ‡A
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyuHaisuiGyosha;

	/**
	 * ‹‹”r…‹ÆÒ‡A‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyuHaisuiGyoshaTel;

	/**
	 * ‹‹”r…‹ÆÒ‡A‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyuHaisuiGyoshaFax;

	/**
	 * ‹‹”r…‹ÆÒ‡AE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kyuHaisuiGyoshaMailAddress;

	/**
	 * ‹‹”r…‹ÆÒ‡A”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kyuHaisuiGyoshaBiko;

	/**
	 * ƒKƒX‰ïĞ–¼
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String gasGaisha;

	/**
	 * ƒKƒX‰ïĞ–¼‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String gasGaishaTel;

	/**
	 * ƒKƒX‰ïĞ–¼‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String gasGaishaFax;

	/**
	 * ƒKƒX‰ïĞ–¼E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String gasGaishaMailAddress;

	/**
	 * ƒKƒX‰ïĞ–¼”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String gasGaishaBiko;

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ–¼
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyutokiHoshuGyosha;

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyutokiHoshuGyoshaTel;

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyutokiHoshuGyoshaFax;

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kyutokiHoshuGyoshaMailAddress;

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kyutokiHoshuGyoshaBiko;

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ–¼
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String airConHoshuGyosha;

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String airConHoshuGyoshaTel;

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String airConHoshuGyoshaFax;

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String airConHoshuGyoshaMailAddress;

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String airConHoshuGyoshaBiko;

	/**
	 * “d‹C•Ûç‹ÆÒ–¼
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String denkiHoshuGyosha;

	/**
	 * “d‹C•Ûç‹ÆÒ‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String denkiHoshuGyoshaTel;

	/**
	 * “d‹C•Ûç‹ÆÒ‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String denkiHoshuGyoshaFax;

	/**
	 * “d‹C•Ûç‹ÆÒE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String denkiHoshuGyoshaMailAddress;

	/**
	 * “d‹C•Ûç‹ÆÒ”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String denkiHoshuGyoshaBiko;

	/**
	 * ‚d‚u‰ïĞ–¼
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String evGaisha;

	/**
	 * ‚d‚u‰ïĞ–¼‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String evGaishaTel;

	/**
	 * ‚d‚u‰ïĞ–¼‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String evGaishaFax;

	/**
	 * ‚d‚u‰ïĞ–¼E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String evGaishaMailAddress;

	/**
	 * ‚d‚u‰ïĞ–¼”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String evGaishaBiko;

	/**
	 * Œ®‹ÆÒ–¼
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kagiGyosha;

	/**
	 * Œ®‹ÆÒ–¼‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kagiGyoshaTel;

	/**
	 * Œ®‹ÆÒ–¼‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kagiGyoshaFax;

	/**
	 * Œ®‹ÆÒ–¼E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kagiGyoshaMailAddress;

	/**
	 * Œ®‹ÆÒ–¼”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kagiGyoshaBiko;

	/**
	 * Á–h•Ûç
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoboHoshu;

	/**
	 * Á–h•Ûç‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoboHoshuTel;

	/**
	 * Á–h•Ûç‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoboHoshuFax;

	/**
	 * Á–h•ÛçE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String shoboHoshuMailAddress;

	/**
	 * Á–h•Ûç”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String shoboHoshuBiko;

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String catvGaisha;

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String catvGaishaTel;

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String catvGaishaFax;

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String catvGaishaMailAddress;

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String catvGaishaBiko;

	/**
	 * ¬C‘U‰ïĞ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoshuZen;

	/**
	 * ¬C‘U‰ïĞ‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoshuZenTel;

	/**
	 * ¬C‘U‰ïĞ‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoshuZenFax;

	/**
	 * ¬C‘U‰ïĞE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String shoshuZenMailAddress;

	/**
	 * ¬C‘U‰ïĞ”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String shoshuZenBiko;

	/**
	 * ƒKƒ‰ƒX‹ÆÒ
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String glassGyosha;

	/**
	 * ƒKƒ‰ƒX‹ÆÒ‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String glassGyoshaTel;

	/**
	 * ƒKƒ‰ƒX‹ÆÒ‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String glassGyoshaFax;

	/**
	 * ƒKƒ‰ƒX‹ÆÒE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String glassGyoshaMailAddress;

	/**
	 * ƒKƒ‰ƒX‹ÆÒ”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String glassGyoshaBiko;

	/**
	 * ‚»‚Ì‘¼‚P
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc1;

	/**
	 * ‚»‚Ì‘¼‚P‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc1Tel;

	/**
	 * ‚»‚Ì‘¼‚P‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc1Fax;

	/**
	 * ‚»‚Ì‘¼‚PE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String etc1MailAddress;

	/**
	 * ‚»‚Ì‘¼‚P”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String etc1Biko;

	/**
	 * ‚»‚Ì‘¼‚Q
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc2;

	/**
	 * ‚»‚Ì‘¼‚Q‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc2Tel;

	/**
	 * ‚»‚Ì‘¼‚Q‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc2Fax;

	/**
	 * ‚»‚Ì‘¼‚QE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String etc2MailAddress;

	/**
	 * ‚»‚Ì‘¼‚Q”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String etc2Biko;

	/**
	 * ‚»‚Ì‘¼‚R
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc3;

	/**
	 * ‚»‚Ì‘¼‚R‚s‚d‚k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc3Tel;

	/**
	 * ‚»‚Ì‘¼‚R‚e‚`‚w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc3Fax;

	/**
	 * ‚»‚Ì‘¼‚RE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String etc3MailAddress;

	/**
	 * ‚»‚Ì‘¼‚R”õl
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String etc3Biko;

	/**
	 * ŒÚ‹q‹æ•ª‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ŒÚ‹q‹æ•ª
	 */
	public String getKokyakuKbn() {
		return kokyakuKbn;
	}
	/**
	 * ŒÚ‹q‹æ•ª‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param kokyakuKbn ŒÚ‹q‹æ•ª
	 */
	public void setKokyakuKbn(String kokyakuKbn) {
		this.kokyakuKbn = kokyakuKbn;
	}

	/**
	 * ŒÚ‹qí•ÊiŒÂl/–@lj‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ŒÚ‹qí•ÊiŒÂl/–@lj
	 */
	public String getKokyakuShubetsu() {
		return kokyakuShubetsu;
	}
	/**
	 * ŒÚ‹qí•ÊiŒÂl/–@lj‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param kokyakuShubetsu ŒÚ‹qí•ÊiŒÂl/–@lj
	 */
	public void setKokyakuShubetsu(String kokyakuShubetsu) {
		this.kokyakuShubetsu = kokyakuShubetsu;
	}

	/**
	 * ƒJƒi•¨Œ–¼‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ƒJƒi•¨Œ–¼
	 */
	public String getKanaNm1() {
		return kanaNm1;
	}
	/**
	 * ƒJƒi•¨Œ–¼‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param kanaNm1 ƒJƒi•¨Œ–¼
	 */
	public void setKanaNm1(String kanaNm1) {
		this.kanaNm1 = kanaNm1;
	}

	/**
	 * i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj
	 */
	public String getKanaNm2() {
		return kanaNm2;
	}
	/**
	 * i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param kanaNm2 i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj
	 */
	public void setKanaNm2(String kanaNm2) {
		this.kanaNm2 = kanaNm2;
	}

	/**
	 * •¨Œ–¼‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return •¨Œ–¼
	 */
	public String getKanjiNm1() {
		return kanjiNm1;
	}
	/**
	 * •¨Œ–¼‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param kanjiNm1 •¨Œ–¼
	 */
	public void setKanjiNm1(String kanjiNm1) {
		this.kanjiNm1 = kanjiNm1;
	}

	/**
	 * i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj
	 */
	public String getKanjiNm2() {
		return kanjiNm2;
	}
	/**
	 * i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param kanjiNm2 i’´‰ß‚µ‚½ê‡‚ÌƒGƒŠƒAj
	 */
	public void setKanjiNm2(String kanjiNm2) {
		this.kanjiNm2 = kanjiNm2;
	}

	/**
	 * —X•Ö”Ô†‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return —X•Ö”Ô†
	 */
	public String getYubinNo() {
		return yubinNo;
	}
	/**
	 * —X•Ö”Ô†‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param yubinNo —X•Ö”Ô†
	 */
	public void setYubinNo(String yubinNo) {
		this.yubinNo = yubinNo;
	}

	/**
	 * ZŠ‚P@“s“¹•{Œ§‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ZŠ‚P@“s“¹•{Œ§
	 */
	public String getJusho1() {
		return jusho1;
	}
	/**
	 * ZŠ‚P@“s“¹•{Œ§‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param jusho1 ZŠ‚P@“s“¹•{Œ§
	 */
	public void setJusho1(String jusho1) {
		this.jusho1 = jusho1;
	}

	/**
	 * ZŠ‚Q@s‹æ’¬‘º‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ZŠ‚Q@s‹æ’¬‘º
	 */
	public String getJusho2() {
		return jusho2;
	}
	/**
	 * ZŠ‚Q@s‹æ’¬‘º‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param jusho2 ZŠ‚Q@s‹æ’¬‘º
	 */
	public void setJusho2(String jusho2) {
		this.jusho2 = jusho2;
	}

	/**
	 * ZŠ‚R@’¬/‘åš‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ZŠ‚R@’¬/‘åš
	 */
	public String getJusho3() {
		return jusho3;
	}
	/**
	 * ZŠ‚R@’¬/‘åš‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param jusho3 ZŠ‚R@’¬/‘åš
	 */
	public void setJusho3(String jusho3) {
		this.jusho3 = jusho3;
	}

	/**
	 * ZŠ‚S@”Ô’n‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ZŠ‚S@”Ô’n
	 */
	public String getJusho4() {
		return jusho4;
	}
	/**
	 * ZŠ‚S@”Ô’n‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param jusho4 ZŠ‚S@”Ô’n
	 */
	public void setJusho4(String jusho4) {
		this.jusho4 = jusho4;
	}

	/**
	 * •¨Œ–¼‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return •¨Œ–¼
	 */
	public String getJusho5() {
		return jusho5;
	}
	/**
	 * •¨Œ–¼‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param jusho5 •¨Œ–¼
	 */
	public void setJusho5(String jusho5) {
		this.jusho5 = jusho5;
	}

	/**
	 * •”‰®”Ô†‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return •”‰®”Ô†
	 */
	public String getRoomNo() {
		return roomNo;
	}

	/**
	 * •”‰®”Ô†‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param roomNo •”‰®”Ô†
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	/**
	 * “d˜b”Ô†‚P‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return “d˜b”Ô†‚P
	 */
	public String getTelNo1() {
		return telNo1;
	}
	/**
	 * “d˜b”Ô†‚P‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param telNo1 “d˜b”Ô†‚P
	 */
	public void setTelNo1(String telNo1) {
		this.telNo1 = telNo1;
	}

	/**
	 * “d˜b”Ô†‚Q‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return “d˜b”Ô†‚Q
	 */
	public String getTelNo2() {
		return telNo2;
	}
	/**
	 * “d˜b”Ô†‚Q‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param telNo2 “d˜b”Ô†‚Q
	 */
	public void setTelNo2(String telNo2) {
		this.telNo2 = telNo2;
	}

	/**
	 * ‚e‚`‚w”Ô†‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ‚e‚`‚w”Ô†
	 */
	public String getFaxNo() {
		return faxNo;
	}
	/**
	 * ‚e‚`‚w”Ô†‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param faxNo ‚e‚`‚w”Ô†
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * ’ˆÓ–€‚P‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚P
	 */
	public String getAttention1() {
		return attention1;
	}
	/**
	 * ’ˆÓ–€‚P‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention1 ’ˆÓ–€‚P
	 */
	public void setAttention1(String attention1) {
		this.attention1 = attention1;
	}

	/**
	 * ’ˆÓ–€‚Q‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚Q
	 */
	public String getAttention2() {
		return attention2;
	}
	/**
	 * ’ˆÓ–€‚Q‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention2 ’ˆÓ–€‚Q
	 */
	public void setAttention2(String attention2) {
		this.attention2 = attention2;
	}

	/**
	 * ’ˆÓ–€‚R‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚R
	 */
	public String getAttention3() {
		return attention3;
	}
	/**
	 * ’ˆÓ–€‚R‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention3 ’ˆÓ–€‚R
	 */
	public void setAttention3(String attention3) {
		this.attention3 = attention3;
	}

	/**
	 * ’ˆÓ–€‚S‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚S
	 */
	public String getAttention4() {
		return attention4;
	}

	/**
	 * ’ˆÓ–€‚S‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention4 ’ˆÓ–€‚S
	 */
	public void setAttention4(String attention4) {
		this.attention4 = attention4;
	}

	/**
	 * ’ˆÓ–€‚S•\¦ŠJn“ú‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚S•\¦ŠJn“ú
	 */
	public String getAttention4StartDt() {
		return attention4StartDt;
	}

	/**
	 * ’ˆÓ–€‚S•\¦ŠJn“ú‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention4StartDt ’ˆÓ–€‚S•\¦ŠJn“ú
	 */
	public void setAttention4StartDt(String attention4StartDt) {
		this.attention4StartDt = attention4StartDt;
	}

	/**
	 * ’ˆÓ–€‚S•\¦I—¹“ú‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚S•\¦I—¹“ú
	 */
	public String getAttention4EndDt() {
		return attention4EndDt;
	}

	/**
	 * ’ˆÓ–€‚S•\¦I—¹“ú‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention4EndDt ’ˆÓ–€‚S•\¦I—¹“ú
	 */
	public void setAttention4EndDt(String attention4EndDt) {
		this.attention4EndDt = attention4EndDt;
	}

	/**
	 * ’ˆÓ–€‚T‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚T
	 */
	public String getAttention5() {
		return attention5;
	}

	/**
	 * ’ˆÓ–€‚T‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention5 ’ˆÓ–€‚T
	 */
	public void setAttention5(String attention5) {
		this.attention5 = attention5;
	}

	/**
	 * ’ˆÓ–€‚T•\¦ŠJn“ú‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚T•\¦ŠJn“ú
	 */
	public String getAttention5StartDt() {
		return attention5StartDt;
	}

	/**
	 * ’ˆÓ–€‚T•\¦ŠJn“ú‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention5StartDt ‚T•\¦ŠJn“ú
	 */
	public void setAttention5StartDt(String attention5StartDt) {
		this.attention5StartDt = attention5StartDt;
	}

	/**
	 * ’ˆÓ–€‚T•\¦I—¹“ú‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚T•\¦I—¹“ú
	 */
	public String getAttention5EndDt() {
		return attention5EndDt;
	}

	/**
	 * ’ˆÓ–€‚T•\¦I—¹“ú‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention5EndDt ’ˆÓ–€‚T•\¦I—¹“ú
	 */
	public void setAttention5EndDt(String attention5EndDt) {
		this.attention5EndDt = attention5EndDt;
	}

	/**
	 * ’ˆÓ–€‚U‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚U
	 */
	public String getAttention6() {
		return attention6;
	}

	/**
	 * ’ˆÓ–€‚U‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention6 ’ˆÓ–€‚U
	 */
	public void setAttention6(String attention6) {
		this.attention6 = attention6;
	}

	/**
	 * ’ˆÓ–€‚U•\¦ŠJn“ú‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚U•\¦ŠJn“ú
	 */
	public String getAttention6StartDt() {
		return attention6StartDt;
	}

	/**
	 * ’ˆÓ–€‚U•\¦ŠJn“ú‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention6StartDt ’ˆÓ–€‚U•\¦ŠJn“ú
	 */
	public void setAttention6StartDt(String attention6StartDt) {
		this.attention6StartDt = attention6StartDt;
	}

	/**
	 * ’ˆÓ–€‚U•\¦I—¹“ú‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’ˆÓ–€‚U•\¦I—¹“ú
	 */
	public String getAttention6EndDt() {
		return attention6EndDt;
	}

	/**
	 * ’ˆÓ–€‚U•\¦I—¹“ú‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param attention6EndDt ’ˆÓ–€‚U•\¦I—¹“ú
	 */
	public void setAttention6EndDt(String attention6EndDt) {
		this.attention6EndDt = attention6EndDt;
	}

	/**
	 * ’÷‚ß“ú‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’÷‚ß“ú
	 */
	public String getShimeDay() {
		return shimeDay;
	}
	/**
	 * ’÷‚ß“ú‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param shimeDay ’÷‚ß“ú
	 */
	public void setShimeDay(String shimeDay) {
		this.shimeDay = shimeDay;
	}

	/**
	 * \‘¢‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return \‘¢
	 */
	public String getKozo() {
		return kozo;
	}
	/**
	 * \‘¢‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param kozo \‘¢
	 */
	public void setKozo(String kozo) {
		this.kozo = kozo;
	}

	/**
	 * ŠK”‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ŠK”
	 */
	public String getKaisu() {
		return kaisu;
	}
	/**
	 * ŠK”‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param kaisu ŠK”
	 */
	public void setKaisu(String kaisu) {
		this.kaisu = kaisu;
	}

	/**
	 * ’z”NŒ‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’z”NŒ
	 */
	public String getChikuNengetsu() {
		return chikuNengetsu;
	}
	/**
	 * ’z”NŒ‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param chikuNengetsu ’z”NŒ
	 */
	public void setChikuNengetsu(String chikuNengetsu) {
		this.chikuNengetsu = chikuNengetsu;
	}

	/**
	 * ŒË”‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ŒË”
	 */
	public String getKosu() {
		return kosu;
	}
	/**
	 * ŒË”‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param kosu ŒË”
	 */
	public void setKosu(String kosu) {
		this.kosu = kosu;
	}

	/**
	 * ’S“–Ò–¼‚P‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’S“–Ò–¼‚P
	 */
	public String getTantoshaNm1() {
		return tantoshaNm1;
	}
	/**
	 * ’S“–Ò–¼‚P‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param tantoshaNm1 ’S“–Ò–¼‚P
	 */
	public void setTantoshaNm1(String tantoshaNm1) {
		this.tantoshaNm1 = tantoshaNm1;
	}

	/**
	 * ’S“–Ò–¼‚Q‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ’S“–Ò–¼‚Q
	 */
	public String getTantoshaNm2() {
		return tantoshaNm2;
	}
	/**
	 * ’S“–Ò–¼‚Q‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param tantoshaNm2 ’S“–Ò–¼‚Q
	 */
	public void setTantoshaNm2(String tantoshaNm2) {
		this.tantoshaNm2 = tantoshaNm2;
	}

	/**
	 * ŠÇ—Œ`‘Ô‹æ•ª‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ŠÇ—Œ`‘Ô‹æ•ª
	 */
	public String getKanriKeitaiKbn() {
		return kanriKeitaiKbn;
	}
	/**
	 * ŠÇ—Œ`‘Ô‹æ•ª‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param kanriKeitaiKbn ŠÇ—Œ`‘Ô‹æ•ª
	 */
	public void setKanriKeitaiKbn(String kanriKeitaiKbn) {
		this.kanriKeitaiKbn = kanriKeitaiKbn;
	}

	/**
	 * ˜A—æ‚P‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ˜A—æ
	 */
	public String getRenrakusaki1() {
		return renrakusaki1;
	}
	/**
	 * ˜A—æ‚P‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param renrakusaki1 ˜A—æ‚P
	 */
	public void setRenrakusaki1(String renrakusaki1) {
		this.renrakusaki1 = renrakusaki1;
	}

	/**
	 * ˜A—æ‚Q‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ˜A—æ
	 */
	public String getRenrakusaki2() {
		return renrakusaki2;
	}
	/**
	 * ˜A—æ‚Q‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param renrakusaki2 ˜A—æ‚Q
	 */
	public void setRenrakusaki2(String renrakusaki2) {
		this.renrakusaki2 = renrakusaki2;
	}

	/**
	 * ƒ|ƒ“ƒvºƒƒ‚‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ƒ|ƒ“ƒvºƒƒ‚
	 */
	public String getPompMemo() {
		return pompMemo;
	}
	/**
	 * ƒ|ƒ“ƒvºƒƒ‚‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param pompMemo ƒ|ƒ“ƒvºƒƒ‚
	 */
	public void setPompMemo(String pompMemo) {
		this.pompMemo = pompMemo;
	}

	/**
	 * ƒI[ƒgƒƒbƒNƒƒ‚‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ƒI[ƒgƒƒbƒNƒƒ‚
	 */
	public String getAutoLockMemo() {
		return autoLockMemo;
	}
	/**
	 * ƒI[ƒgƒƒbƒNƒƒ‚‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param autoLockMemo ƒI[ƒgƒƒbƒNƒƒ‚
	 */
	public void setAutoLockMemo(String autoLockMemo) {
		this.autoLockMemo = autoLockMemo;
	}

	/**
	 * ƒ[ƒ‹‚a‚n‚w‚ğæ“¾‚µ‚Ü‚·B
	 * @return ƒ[ƒ‹‚a‚n‚w
	 */
	public String getMailBox() {
		return mailBox;
	}

	/**
	 * ƒ[ƒ‹‚a‚n‚w‚ğİ’è‚µ‚Ü‚·B
	 * @param mailBox ƒ[ƒ‹‚a‚n‚w
	 */
	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}

	/**
	 * ŠÇ—Œ`‘Ô”õl‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ŠÇ—Œ`‘Ô”õl
	 */
	public String getBiko() {
		return biko;
	}
	/**
	 * ŠÇ—Œ`‘Ô”õl‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param biko ŠÇ—Œ`‘Ô”õl
	 */
	public void setBiko(String biko) {
		this.biko = biko;
	}

	/**
	 * ƒI[ƒi[–¼‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ƒI[ƒi[–¼
	 */
	public String getOoyaNm() {
		return ooyaNm;
	}
	/**
	 * ƒI[ƒi[–¼‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param ooyaNm ƒI[ƒi[–¼
	 */
	public void setOoyaNm(String ooyaNm) {
		this.ooyaNm = ooyaNm;
	}

	/**
	 * ƒI[ƒi[“d˜b”Ô†‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ƒI[ƒi[“d˜b”Ô†
	 */
	public String getOoyaTel() {
		return ooyaTel;
	}
	/**
	 * ƒI[ƒi[“d˜b”Ô†‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param ooyaTel ƒI[ƒi[“d˜b”Ô†
	 */
	public void setOoyaTel(String ooyaTel) {
		this.ooyaTel = ooyaTel;
	}

	/**
	 * ƒI[ƒi[ZŠ‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ƒI[ƒi[ZŠ
	 */
	public String getOoyaJusho() {
		return ooyaJusho;
	}
	/**
	 * ƒI[ƒi[ZŠ‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param ooyaJusho ƒI[ƒi[ZŠ
	 */
	public void setOoyaJusho(String ooyaJusho) {
		this.ooyaJusho = ooyaJusho;
	}

	/**
	 * ƒI[ƒi[”õl‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return ƒI[ƒi[”õl
	 */
	public String getOoyaBiko() {
		return ooyaBiko;
	}
	/**
	 * ƒI[ƒi[”õl‚ğİ’è‚µ‚Ü‚·B
	 *
	 * @param ooyaBiko ƒI[ƒi[”õl
	 */
	public void setOoyaBiko(String ooyaBiko) {
		this.ooyaBiko = ooyaBiko;
	}

	/**
	 * ‹¤—p•”ŠÇ—‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹¤—p•”ŠÇ—
	 */
	public String getKyoyoKanriNm() {
		return kyoyoKanriNm;
	}

	/**
	 * ‹¤—p•”ŠÇ—‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyoyoKanriNm ‹¤—p•”ŠÇ—
	 */
	public void setKyoyoKanriNm(String kyoyoKanriNm) {
		this.kyoyoKanriNm = kyoyoKanriNm;
	}

	/**
	 * ‹¤—p•”ŠÇ—‰c‹Æ“ú‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹¤—p•”ŠÇ—‰c‹Æ“ú
	 */
	public String getKyoyoKanriEigyobi() {
		return kyoyoKanriEigyobi;
	}

	/**
	 * ‹¤—p•”ŠÇ—‰c‹Æ“ú‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyoyoKanriEigyobi ‹¤—p•”ŠÇ—‰c‹Æ“ú
	 */
	public void setKyoyoKanriEigyobi(String kyoyoKanriEigyobi) {
		this.kyoyoKanriEigyobi = kyoyoKanriEigyobi;
	}

	/**
	 * ‹¤—p•”ŠÇ—‰c‹ÆŠÔ‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹¤—p•”ŠÇ—‰c‹ÆŠÔ
	 */
	public String getKyoyoKanriEigyoJikan() {
		return kyoyoKanriEigyoJikan;
	}

	/**
	 * ‹¤—p•”ŠÇ—‰c‹ÆŠÔ‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyoyoKanriEigyoJikan ‹¤—p•”ŠÇ—‰c‹ÆŠÔ
	 */
	public void setKyoyoKanriEigyoJikan(String kyoyoKanriEigyoJikan) {
		this.kyoyoKanriEigyoJikan = kyoyoKanriEigyoJikan;
	}

	/**
	 * ‹¤—p•”ŠÇ—‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹¤—p•”ŠÇ—‚s‚d‚k
	 */
	public String getKyoyoKanriTel() {
		return kyoyoKanriTel;
	}

	/**
	 * ‹¤—p•”ŠÇ—‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyoyoKanriTel ‹¤—p•”ŠÇ—‚s‚d‚k
	 */
	public void setKyoyoKanriTel(String kyoyoKanriTel) {
		this.kyoyoKanriTel = kyoyoKanriTel;
	}

	/**
	 * ‹¤—p•”ŠÇ—ŠÔŠO‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹¤—p•”ŠÇ—ŠÔŠO‚s‚d‚k
	 */
	public String getKyoyoKanriJikangaiTel() {
		return kyoyoKanriJikangaiTel;
	}

	/**
	 * ‹¤—p•”ŠÇ—ŠÔŠO‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyoyoKanriJikangaiTel ‹¤—p•”ŠÇ—ŠÔŠO‚s‚d‚k
	 */
	public void setKyoyoKanriJikangaiTel(String kyoyoKanriJikangaiTel) {
		this.kyoyoKanriJikangaiTel = kyoyoKanriJikangaiTel;
	}

	/**
	 * ‹¤—p•”ŠÇ—E-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹¤—p•”ŠÇ—E-mail
	 */
	public String getKyoyoKanriMailAddress() {
		return kyoyoKanriMailAddress;
	}

	/**
	 * ‹¤—p•”ŠÇ—E-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyoyoKanriMailAddress ‹¤—p•”ŠÇ—E-mail
	 */
	public void setKyoyoKanriMailAddress(String kyoyoKanriMailAddress) {
		this.kyoyoKanriMailAddress = kyoyoKanriMailAddress;
	}

	/**
	 * ‹¤—p•”ŠÇ—”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹¤—p•”ŠÇ—”õl
	 */
	public String getKyoyoKanriBiko() {
		return kyoyoKanriBiko;
	}

	/**
	 * ‹¤—p•”ŠÇ—”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyoyoKanriBiko ‹¤—p•”ŠÇ—”õl
	 */
	public void setKyoyoKanriBiko(String kyoyoKanriBiko) {
		this.kyoyoKanriBiko = kyoyoKanriBiko;
	}

	/**
	 * ŠÇ—l–¼‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ŠÇ—l–¼
	 */
	public String getKanrininNm() {
		return kanrininNm;
	}

	/**
	 * ŠÇ—l–¼‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kanrininNm ŠÇ—l–¼
	 */
	public void setKanrininNm(String kanrininNm) {
		this.kanrininNm = kanrininNm;
	}

	/**
	 * ŠÇ—l‰c‹Æ“ú‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ŠÇ—l‰c‹Æ“ú
	 */
	public String getKanrininEigyobi() {
		return kanrininEigyobi;
	}

	/**
	 * ŠÇ—l‰c‹Æ“ú‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kanrininEigyobi ŠÇ—l‰c‹Æ“ú
	 */
	public void setKanrininEigyobi(String kanrininEigyobi) {
		this.kanrininEigyobi = kanrininEigyobi;
	}

	/**
	 * ŠÇ—l‰c‹ÆŠÔ‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ŠÇ—l‰c‹ÆŠÔ
	 */
	public String getKanrininEigyoJikan() {
		return kanrininEigyoJikan;
	}

	/**
	 * ŠÇ—l‰c‹ÆŠÔ‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kanrininEigyoJikan ŠÇ—l‰c‹ÆŠÔ
	 */
	public void setKanrininEigyoJikan(String kanrininEigyoJikan) {
		this.kanrininEigyoJikan = kanrininEigyoJikan;
	}

	/**
	 * ŠÇ—l–¼‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ŠÇ—l–¼‚s‚d‚k
	 */
	public String getKanrininTel() {
		return kanrininTel;
	}

	/**
	 * ŠÇ—l–¼‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kanrininTel ŠÇ—l–¼‚s‚d‚k
	 */
	public void setKanrininTel(String kanrininTel) {
		this.kanrininTel = kanrininTel;
	}

	/**
	 * ŠÇ—lE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ŠÇ—lE-mail
	 */
	public String getKanrininMailAddress() {
		return kanrininMailAddress;
	}

	/**
	 * ŠÇ—lE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kanrininMailAddress ŠÇ—lE-mail
	 */
	public void setKanrininMailAddress(String kanrininMailAddress) {
		this.kanrininMailAddress = kanrininMailAddress;
	}

	/**
	 * ŠÇ—l”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ŠÇ—l”õl
	 */
	public String getKanrininBiko() {
		return kanrininBiko;
	}

	/**
	 * ŠÇ—l”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kanrininBiko ŠÇ—l”õl
	 */
	public void setKanrininBiko(String kanrininBiko) {
		this.kanrininBiko = kanrininBiko;
	}

	/**
	 * Œx”õ‰ïĞ–¼‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Œx”õ‰ïĞ–¼
	 */
	public String getKeibiGaisha() {
		return keibiGaisha;
	}

	/**
	 * Œx”õ‰ïĞ–¼‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param keibiGaisha Œx”õ‰ïĞ–¼
	 */
	public void setKeibiGaisha(String keibiGaisha) {
		this.keibiGaisha = keibiGaisha;
	}

	/**
	 * Œx”õ‰ïĞ‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Œx”õ‰ïĞ‚s‚d‚k
	 */
	public String getKeibiGaishaTel() {
		return keibiGaishaTel;
	}

	/**
	 * Œx”õ‰ïĞ‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param keibiGaishaTel Œx”õ‰ïĞ‚s‚d‚k
	 */
	public void setKeibiGaishaTel(String keibiGaishaTel) {
		this.keibiGaishaTel = keibiGaishaTel;
	}

	/**
	 * Œx”õ‰ïĞ‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Œx”õ‰ïĞ‚e‚`‚w
	 */
	public String getKeibiGaishaFax() {
		return keibiGaishaFax;
	}

	/**
	 * Œx”õ‰ïĞ‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param keibiGaishaFax Œx”õ‰ïĞ‚e‚`‚w
	 */
	public void setKeibiGaishaFax(String keibiGaishaFax) {
		this.keibiGaishaFax = keibiGaishaFax;
	}

	/**
	 * Œx”õ‰ïĞE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Œx”õ‰ïĞE-mail
	 */
	public String getKeibiGaishaMailAddress() {
		return keibiGaishaMailAddress;
	}

	/**
	 * Œx”õ‰ïĞE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param keibiGaishaMailAddress Œx”õ‰ïĞE-mail
	 */
	public void setKeibiGaishaMailAddress(String keibiGaishaMailAddress) {
		this.keibiGaishaMailAddress = keibiGaishaMailAddress;
	}

	/**
	 * Œx”õ‰ïĞ”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Œx”õ‰ïĞ”õl
	 */
	public String getKeibiGaishaBiko() {
		return keibiGaishaBiko;
	}

	/**
	 * Œx”õ‰ïĞ”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param keibiGaishaBiko Œx”õ‰ïĞ”õl
	 */
	public void setKeibiGaishaBiko(String keibiGaishaBiko) {
		this.keibiGaishaBiko = keibiGaishaBiko;
	}

	/**
	 * …“¹‹ÆÒ‡@‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return …“¹‹ÆÒ‡@
	 */
	public String getSuidoGyosha() {
		return suidoGyosha;
	}

	/**
	 * …“¹‹ÆÒ‡@‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param suidoGyosha …“¹‹ÆÒ‡@
	 */
	public void setSuidoGyosha(String suidoGyosha) {
		this.suidoGyosha = suidoGyosha;
	}

	/**
	 * …“¹‹ÆÒ‡@‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return …“¹‹ÆÒ‡@‚s‚d‚k
	 */
	public String getSuidoGyoshaTel() {
		return suidoGyoshaTel;
	}

	/**
	 * …“¹‹ÆÒ‡@‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param suidoGyoshaTel …“¹‹ÆÒ‡@‚s‚d‚k
	 */
	public void setSuidoGyoshaTel(String suidoGyoshaTel) {
		this.suidoGyoshaTel = suidoGyoshaTel;
	}

	/**
	 * …“¹‹ÆÒ‡@‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return …“¹‹ÆÒ‡@‚e‚`‚w
	 */
	public String getSuidoGyoshaFax() {
		return suidoGyoshaFax;
	}

	/**
	 * …“¹‹ÆÒ‡@‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param suidoGyoshaFax …“¹‹ÆÒ‡@‚e‚`‚w
	 */
	public void setSuidoGyoshaFax(String suidoGyoshaFax) {
		this.suidoGyoshaFax = suidoGyoshaFax;
	}

	/**
	 * …“¹‹ÆÒ‡@E-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return …“¹‹ÆÒ‡@E-mail
	 */
	public String getSuidoGyoshaMailAddress() {
		return suidoGyoshaMailAddress;
	}

	/**
	 * …“¹‹ÆÒ‡@E-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param suidoGyoshaMailAddress …“¹‹ÆÒ‡@E-mail
	 */
	public void setSuidoGyoshaMailAddress(String suidoGyoshaMailAddress) {
		this.suidoGyoshaMailAddress = suidoGyoshaMailAddress;
	}

	/**
	 * …“¹‹ÆÒ‡@”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return …“¹‹ÆÒ‡@”õl
	 */
	public String getSuidoGyoshaBiko() {
		return suidoGyoshaBiko;
	}

	/**
	 * …“¹‹ÆÒ‡@”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param suidoGyoshaBiko …“¹‹ÆÒ‡@”õl
	 */
	public void setSuidoGyoshaBiko(String suidoGyoshaBiko) {
		this.suidoGyoshaBiko = suidoGyoshaBiko;
	}

	/**
	 * ‹‹”r…‹ÆÒ‡A‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹‹”r…‹ÆÒ‡A
	 */
	public String getKyuHaisuiGyosha() {
		return kyuHaisuiGyosha;
	}

	/**
	 * ‹‹”r…‹ÆÒ‡A‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyuHaisuiGyosha ‹‹”r…‹ÆÒ‡A
	 */
	public void setKyuHaisuiGyosha(String kyuHaisuiGyosha) {
		this.kyuHaisuiGyosha = kyuHaisuiGyosha;
	}

	/**
	 * ‹‹”r…‹ÆÒ‡A‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹‹”r…‹ÆÒ‡A‚s‚d‚k
	 */
	public String getKyuHaisuiGyoshaTel() {
		return kyuHaisuiGyoshaTel;
	}

	/**
	 * ‹‹”r…‹ÆÒ‡A‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyuHaisuiGyoshaTel ‹‹”r…‹ÆÒ‡A‚s‚d‚k
	 */
	public void setKyuHaisuiGyoshaTel(String kyuHaisuiGyoshaTel) {
		this.kyuHaisuiGyoshaTel = kyuHaisuiGyoshaTel;
	}

	/**
	 * ‹‹”r…‹ÆÒ‡A‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹‹”r…‹ÆÒ‡A‚e‚`‚w
	 */
	public String getKyuHaisuiGyoshaFax() {
		return kyuHaisuiGyoshaFax;
	}

	/**
	 * ‹‹”r…‹ÆÒ‡A‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyuHaisuiGyoshaFax ‹‹”r…‹ÆÒ‡A‚e‚`‚w
	 */
	public void setKyuHaisuiGyoshaFax(String kyuHaisuiGyoshaFax) {
		this.kyuHaisuiGyoshaFax = kyuHaisuiGyoshaFax;
	}

	/**
	 * ‹‹”r…‹ÆÒ‡AE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹‹”r…‹ÆÒ‡AE-mail
	 */
	public String getKyuHaisuiGyoshaMailAddress() {
		return kyuHaisuiGyoshaMailAddress;
	}

	/**
	 * ‹‹”r…‹ÆÒ‡AE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyuHaisuiGyoshaMailAddress ‹‹”r…‹ÆÒ‡AE-mail
	 */
	public void setKyuHaisuiGyoshaMailAddress(String kyuHaisuiGyoshaMailAddress) {
		this.kyuHaisuiGyoshaMailAddress = kyuHaisuiGyoshaMailAddress;
	}

	/**
	 * ‹‹”r…‹ÆÒ‡A”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹‹”r…‹ÆÒ‡A”õl
	 */
	public String getKyuHaisuiGyoshaBiko() {
		return kyuHaisuiGyoshaBiko;
	}

	/**
	 * ‹‹”r…‹ÆÒ‡A”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyuHaisuiGyoshaBiko ‹‹”r…‹ÆÒ‡A”õl
	 */
	public void setKyuHaisuiGyoshaBiko(String kyuHaisuiGyoshaBiko) {
		this.kyuHaisuiGyoshaBiko = kyuHaisuiGyoshaBiko;
	}

	/**
	 * ƒKƒX‰ïĞ–¼‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒKƒX‰ïĞ–¼
	 */
	public String getGasGaisha() {
		return gasGaisha;
	}

	/**
	 * ƒKƒX‰ïĞ–¼‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param gasGaisha ƒKƒX‰ïĞ–¼
	 */
	public void setGasGaisha(String gasGaisha) {
		this.gasGaisha = gasGaisha;
	}

	/**
	 * ƒKƒX‰ïĞ–¼‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒKƒX‰ïĞ–¼‚s‚d‚k
	 */
	public String getGasGaishaTel() {
		return gasGaishaTel;
	}

	/**
	 * ƒKƒX‰ïĞ–¼‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param gasGaishaTel ƒKƒX‰ïĞ–¼‚s‚d‚k
	 */
	public void setGasGaishaTel(String gasGaishaTel) {
		this.gasGaishaTel = gasGaishaTel;
	}

	/**
	 * ƒKƒX‰ïĞ–¼‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒKƒX‰ïĞ–¼‚e‚`‚w
	 */
	public String getGasGaishaFax() {
		return gasGaishaFax;
	}

	/**
	 * ƒKƒX‰ïĞ–¼‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param gasGaishaFax ƒKƒX‰ïĞ–¼‚e‚`‚w
	 */
	public void setGasGaishaFax(String gasGaishaFax) {
		this.gasGaishaFax = gasGaishaFax;
	}

	/**
	 * ƒKƒX‰ïĞ–¼E-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒKƒX‰ïĞ–¼E-mail
	 */
	public String getGasGaishaMailAddress() {
		return gasGaishaMailAddress;
	}

	/**
	 * ƒKƒX‰ïĞ–¼E-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param gasGaishaMailAddress ƒKƒX‰ïĞ–¼E-mail
	 */
	public void setGasGaishaMailAddress(String gasGaishaMailAddress) {
		this.gasGaishaMailAddress = gasGaishaMailAddress;
	}

	/**
	 * ƒKƒX‰ïĞ–¼”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒKƒX‰ïĞ–¼”õl
	 */
	public String getGasGaishaBiko() {
		return gasGaishaBiko;
	}

	/**
	 * ƒKƒX‰ïĞ–¼”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param gasGaishaBiko ƒKƒX‰ïĞ–¼”õl
	 */
	public void setGasGaishaBiko(String gasGaishaBiko) {
		this.gasGaishaBiko = gasGaishaBiko;
	}

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ–¼‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹‹“’Ší•Ûç‹ÆÒ–¼
	 */
	public String getKyutokiHoshuGyosha() {
		return kyutokiHoshuGyosha;
	}

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ–¼‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyutokiHoshuGyosha ‹‹“’Ší•Ûç‹ÆÒ–¼
	 */
	public void setKyutokiHoshuGyosha(String kyutokiHoshuGyosha) {
		this.kyutokiHoshuGyosha = kyutokiHoshuGyosha;
	}

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹‹“’Ší•Ûç‹ÆÒ‚s‚d‚k
	 */
	public String getKyutokiHoshuGyoshaTel() {
		return kyutokiHoshuGyoshaTel;
	}

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyutokiHoshuGyoshaTel ‹‹“’Ší•Ûç‹ÆÒ‚s‚d‚k
	 */
	public void setKyutokiHoshuGyoshaTel(String kyutokiHoshuGyoshaTel) {
		this.kyutokiHoshuGyoshaTel = kyutokiHoshuGyoshaTel;
	}

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹‹“’Ší•Ûç‹ÆÒ‚e‚`‚w
	 */
	public String getKyutokiHoshuGyoshaFax() {
		return kyutokiHoshuGyoshaFax;
	}

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyutokiHoshuGyoshaFax ‹‹“’Ší•Ûç‹ÆÒ‚e‚`‚w
	 */
	public void setKyutokiHoshuGyoshaFax(String kyutokiHoshuGyoshaFax) {
		this.kyutokiHoshuGyoshaFax = kyutokiHoshuGyoshaFax;
	}

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹‹“’Ší•Ûç‹ÆÒE-mail
	 */
	public String getKyutokiHoshuGyoshaMailAddress() {
		return kyutokiHoshuGyoshaMailAddress;
	}

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyutokiHoshuGyoshaMailAddress ‹‹“’Ší•Ûç‹ÆÒE-mail
	 */
	public void setKyutokiHoshuGyoshaMailAddress(String kyutokiHoshuGyoshaMailAddress) {
		this.kyutokiHoshuGyoshaMailAddress = kyutokiHoshuGyoshaMailAddress;
	}

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‹‹“’Ší•Ûç‹ÆÒ”õl
	 */
	public String getKyutokiHoshuGyoshaBiko() {
		return kyutokiHoshuGyoshaBiko;
	}

	/**
	 * ‹‹“’Ší•Ûç‹ÆÒ”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kyutokiHoshuGyoshaBiko ‹‹“’Ší•Ûç‹ÆÒ”õl
	 */
	public void setKyutokiHoshuGyoshaBiko(String kyutokiHoshuGyoshaBiko) {
		this.kyutokiHoshuGyoshaBiko = kyutokiHoshuGyoshaBiko;
	}

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ–¼‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒGƒAƒRƒ“•Ûç‹ÆÒ–¼
	 */
	public String getAirConHoshuGyosha() {
		return airConHoshuGyosha;
	}

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ–¼‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param airConHoshuGyosha ƒGƒAƒRƒ“•Ûç‹ÆÒ–¼
	 */
	public void setAirConHoshuGyosha(String airConHoshuGyosha) {
		this.airConHoshuGyosha = airConHoshuGyosha;
	}

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒGƒAƒRƒ“•Ûç‹ÆÒ‚s‚d‚k
	 */
	public String getAirConHoshuGyoshaTel() {
		return airConHoshuGyoshaTel;
	}

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param airConHoshuGyoshaTel ƒGƒAƒRƒ“•Ûç‹ÆÒ‚s‚d‚k
	 */
	public void setAirConHoshuGyoshaTel(String airConHoshuGyoshaTel) {
		this.airConHoshuGyoshaTel = airConHoshuGyoshaTel;
	}

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒGƒAƒRƒ“•Ûç‹ÆÒ‚e‚`‚w
	 */
	public String getAirConHoshuGyoshaFax() {
		return airConHoshuGyoshaFax;
	}

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param airConHoshuGyoshaFax ƒGƒAƒRƒ“•Ûç‹ÆÒ‚e‚`‚w
	 */
	public void setAirConHoshuGyoshaFax(String airConHoshuGyoshaFax) {
		this.airConHoshuGyoshaFax = airConHoshuGyoshaFax;
	}

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒGƒAƒRƒ“•Ûç‹ÆÒE-mail
	 */
	public String getAirConHoshuGyoshaMailAddress() {
		return airConHoshuGyoshaMailAddress;
	}

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param airConHoshuGyoshaMailAddress ƒGƒAƒRƒ“•Ûç‹ÆÒE-mail
	 */
	public void setAirConHoshuGyoshaMailAddress(String airConHoshuGyoshaMailAddress) {
		this.airConHoshuGyoshaMailAddress = airConHoshuGyoshaMailAddress;
	}

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒGƒAƒRƒ“•Ûç‹ÆÒ”õl
	 */
	public String getAirConHoshuGyoshaBiko() {
		return airConHoshuGyoshaBiko;
	}

	/**
	 * ƒGƒAƒRƒ“•Ûç‹ÆÒ”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param airConHoshuGyoshaBiko ƒGƒAƒRƒ“•Ûç‹ÆÒ”õl
	 */
	public void setAirConHoshuGyoshaBiko(String airConHoshuGyoshaBiko) {
		this.airConHoshuGyoshaBiko = airConHoshuGyoshaBiko;
	}

	/**
	 * “d‹C•Ûç‹ÆÒ–¼‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return “d‹C•Ûç‹ÆÒ–¼
	 */
	public String getDenkiHoshuGyosha() {
		return denkiHoshuGyosha;
	}

	/**
	 * “d‹C•Ûç‹ÆÒ–¼‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param denkiHoshuGyosha “d‹C•Ûç‹ÆÒ–¼
	 */
	public void setDenkiHoshuGyosha(String denkiHoshuGyosha) {
		this.denkiHoshuGyosha = denkiHoshuGyosha;
	}

	/**
	 * “d‹C•Ûç‹ÆÒ‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return “d‹C•Ûç‹ÆÒ‚s‚d‚k
	 */
	public String getDenkiHoshuGyoshaTel() {
		return denkiHoshuGyoshaTel;
	}

	/**
	 * “d‹C•Ûç‹ÆÒ‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param denkiHoshuGyoshaTel “d‹C•Ûç‹ÆÒ‚s‚d‚k
	 */
	public void setDenkiHoshuGyoshaTel(String denkiHoshuGyoshaTel) {
		this.denkiHoshuGyoshaTel = denkiHoshuGyoshaTel;
	}

	/**
	 * “d‹C•Ûç‹ÆÒ‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return “d‹C•Ûç‹ÆÒ‚e‚`‚w
	 */
	public String getDenkiHoshuGyoshaFax() {
		return denkiHoshuGyoshaFax;
	}

	/**
	 * “d‹C•Ûç‹ÆÒ‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param denkiHoshuGyoshaFax “d‹C•Ûç‹ÆÒ‚e‚`‚w
	 */
	public void setDenkiHoshuGyoshaFax(String denkiHoshuGyoshaFax) {
		this.denkiHoshuGyoshaFax = denkiHoshuGyoshaFax;
	}

	/**
	 * “d‹C•Ûç‹ÆÒE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return “d‹C•Ûç‹ÆÒE-mail
	 */
	public String getDenkiHoshuGyoshaMailAddress() {
		return denkiHoshuGyoshaMailAddress;
	}

	/**
	 * “d‹C•Ûç‹ÆÒE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param denkiHoshuGyoshaMailAddress “d‹C•Ûç‹ÆÒE-mail
	 */
	public void setDenkiHoshuGyoshaMailAddress(String denkiHoshuGyoshaMailAddress) {
		this.denkiHoshuGyoshaMailAddress = denkiHoshuGyoshaMailAddress;
	}

	/**
	 * “d‹C•Ûç‹ÆÒ”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return “d‹C•Ûç‹ÆÒ”õl
	 */
	public String getDenkiHoshuGyoshaBiko() {
		return denkiHoshuGyoshaBiko;
	}

	/**
	 * “d‹C•Ûç‹ÆÒ”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param denkiHoshuGyoshaBiko “d‹C•Ûç‹ÆÒ”õl
	 */
	public void setDenkiHoshuGyoshaBiko(String denkiHoshuGyoshaBiko) {
		this.denkiHoshuGyoshaBiko = denkiHoshuGyoshaBiko;
	}

	/**
	 * ‚d‚u‰ïĞ–¼‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚d‚u‰ïĞ–¼
	 */
	public String getEvGaisha() {
		return evGaisha;
	}

	/**
	 * ‚d‚u‰ïĞ–¼‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param evGaisha ‚d‚u‰ïĞ–¼
	 */
	public void setEvGaisha(String evGaisha) {
		this.evGaisha = evGaisha;
	}

	/**
	 * ‚d‚u‰ïĞ–¼‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚d‚u‰ïĞ–¼‚s‚d‚k
	 */
	public String getEvGaishaTel() {
		return evGaishaTel;
	}

	/**
	 * ‚d‚u‰ïĞ–¼‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param evGaishaTel ‚d‚u‰ïĞ–¼‚s‚d‚k
	 */
	public void setEvGaishaTel(String evGaishaTel) {
		this.evGaishaTel = evGaishaTel;
	}

	/**
	 * ‚d‚u‰ïĞ–¼‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚d‚u‰ïĞ–¼‚e‚`‚w
	 */
	public String getEvGaishaFax() {
		return evGaishaFax;
	}

	/**
	 * ‚d‚u‰ïĞ–¼‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param evGaishaFax ‚d‚u‰ïĞ–¼‚e‚`‚w
	 */
	public void setEvGaishaFax(String evGaishaFax) {
		this.evGaishaFax = evGaishaFax;
	}

	/**
	 * ‚d‚u‰ïĞ–¼E-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚d‚u‰ïĞ–¼E-mail
	 */
	public String getEvGaishaMailAddress() {
		return evGaishaMailAddress;
	}

	/**
	 * ‚d‚u‰ïĞ–¼E-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param evGaishaMailAddress ‚d‚u‰ïĞ–¼E-mail
	 */
	public void setEvGaishaMailAddress(String evGaishaMailAddress) {
		this.evGaishaMailAddress = evGaishaMailAddress;
	}

	/**
	 * ‚d‚u‰ïĞ–¼”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚d‚u‰ïĞ–¼”õl
	 */
	public String getEvGaishaBiko() {
		return evGaishaBiko;
	}

	/**
	 * ‚d‚u‰ïĞ–¼”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param evGaishaBiko ‚d‚u‰ïĞ–¼”õl
	 */
	public void setEvGaishaBiko(String evGaishaBiko) {
		this.evGaishaBiko = evGaishaBiko;
	}

	/**
	 * Œ®‹ÆÒ–¼‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Œ®‹ÆÒ–¼
	 */
	public String getKagiGyosha() {
		return kagiGyosha;
	}

	/**
	 * Œ®‹ÆÒ–¼‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kagiGyosha Œ®‹ÆÒ–¼
	 */
	public void setKagiGyosha(String kagiGyosha) {
		this.kagiGyosha = kagiGyosha;
	}

	/**
	 * Œ®‹ÆÒ–¼‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Œ®‹ÆÒ–¼‚s‚d‚k
	 */
	public String getKagiGyoshaTel() {
		return kagiGyoshaTel;
	}

	/**
	 * Œ®‹ÆÒ–¼‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kagiGyoshaTel Œ®‹ÆÒ–¼‚s‚d‚k
	 */
	public void setKagiGyoshaTel(String kagiGyoshaTel) {
		this.kagiGyoshaTel = kagiGyoshaTel;
	}

	/**
	 * Œ®‹ÆÒ–¼‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Œ®‹ÆÒ–¼‚e‚`‚w
	 */
	public String getKagiGyoshaFax() {
		return kagiGyoshaFax;
	}

	/**
	 * Œ®‹ÆÒ–¼‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kagiGyoshaFax Œ®‹ÆÒ–¼‚e‚`‚w
	 */
	public void setKagiGyoshaFax(String kagiGyoshaFax) {
		this.kagiGyoshaFax = kagiGyoshaFax;
	}

	/**
	 * Œ®‹ÆÒ–¼E-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Œ®‹ÆÒ–¼E-mail
	 */
	public String getKagiGyoshaMailAddress() {
		return kagiGyoshaMailAddress;
	}

	/**
	 * Œ®‹ÆÒ–¼E-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kagiGyoshaMailAddress Œ®‹ÆÒ–¼E-mail
	 */
	public void setKagiGyoshaMailAddress(String kagiGyoshaMailAddress) {
		this.kagiGyoshaMailAddress = kagiGyoshaMailAddress;
	}

	/**
	 * Œ®‹ÆÒ–¼”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Œ®‹ÆÒ–¼”õl
	 */
	public String getKagiGyoshaBiko() {
		return kagiGyoshaBiko;
	}

	/**
	 * Œ®‹ÆÒ–¼”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param kagiGyoshaBiko Œ®‹ÆÒ–¼”õl
	 */
	public void setKagiGyoshaBiko(String kagiGyoshaBiko) {
		this.kagiGyoshaBiko = kagiGyoshaBiko;
	}

	/**
	 * Á–h•Ûç‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Á–h•Ûç
	 */
	public String getShoboHoshu() {
		return shoboHoshu;
	}

	/**
	 * Á–h•Ûç‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param shoboHoshu Á–h•Ûç
	 */
	public void setShoboHoshu(String shoboHoshu) {
		this.shoboHoshu = shoboHoshu;
	}

	/**
	 * Á–h•Ûç‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Á–h•Ûç‚s‚d‚k
	 */
	public String getShoboHoshuTel() {
		return shoboHoshuTel;
	}

	/**
	 * Á–h•Ûç‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param shoboHoshuTel Á–h•Ûç‚s‚d‚k
	 */
	public void setShoboHoshuTel(String shoboHoshuTel) {
		this.shoboHoshuTel = shoboHoshuTel;
	}

	/**
	 * Á–h•Ûç‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Á–h•Ûç‚e‚`‚w
	 */
	public String getShoboHoshuFax() {
		return shoboHoshuFax;
	}

	/**
	 * Á–h•Ûç‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param shoboHoshuFax Á–h•Ûç‚e‚`‚w
	 */
	public void setShoboHoshuFax(String shoboHoshuFax) {
		this.shoboHoshuFax = shoboHoshuFax;
	}

	/**
	 * Á–h•ÛçE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Á–h•ÛçE-mail
	 */
	public String getShoboHoshuMailAddress() {
		return shoboHoshuMailAddress;
	}

	/**
	 * Á–h•ÛçE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param shoboHoshuMailAddress Á–h•ÛçE-mail
	 */
	public void setShoboHoshuMailAddress(String shoboHoshuMailAddress) {
		this.shoboHoshuMailAddress = shoboHoshuMailAddress;
	}

	/**
	 * Á–h•Ûç”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return Á–h•Ûç”õl
	 */
	public String getShoboHoshuBiko() {
		return shoboHoshuBiko;
	}

	/**
	 * Á–h•Ûç”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param shoboHoshuBiko Á–h•Ûç”õl
	 */
	public void setShoboHoshuBiko(String shoboHoshuBiko) {
		this.shoboHoshuBiko = shoboHoshuBiko;
	}

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return CATV‰ïĞ
	 */
	public String getCatvGaisha() {
		return catvGaisha;
	}

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param catvGaisha ‚b‚`‚s‚u‰ïĞ–¼
	 */
	public void setCatvGaisha(String catvGaisha) {
		this.catvGaisha = catvGaisha;
	}

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚b‚`‚s‚u‰ïĞ–¼‚s‚d‚k
	 */
	public String getCatvGaishaTel() {
		return catvGaishaTel;
	}

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param catvGaishaTel ‚b‚`‚s‚u‰ïĞ–¼‚s‚d‚k
	 */
	public void setCatvGaishaTel(String catvGaishaTel) {
		this.catvGaishaTel = catvGaishaTel;
	}

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚b‚`‚s‚u‰ïĞ–¼‚e‚`‚w
	 */
	public String getCatvGaishaFax() {
		return catvGaishaFax;
	}

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param catvGaishaFax ‚b‚`‚s‚u‰ïĞ–¼‚e‚`‚w
	 */
	public void setCatvGaishaFax(String catvGaishaFax) {
		this.catvGaishaFax = catvGaishaFax;
	}

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼E-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚b‚`‚s‚u‰ïĞ–¼E-mail
	 */
	public String getCatvGaishaMailAddress() {
		return catvGaishaMailAddress;
	}

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼E-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param catvGaishaMailAddress ‚b‚`‚s‚u‰ïĞ–¼E-mail
	 */
	public void setCatvGaishaMailAddress(String catvGaishaMailAddress) {
		this.catvGaishaMailAddress = catvGaishaMailAddress;
	}

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚b‚`‚s‚u‰ïĞ–¼”õl
	 */
	public String getCatvGaishaBiko() {
		return catvGaishaBiko;
	}

	/**
	 * ‚b‚`‚s‚u‰ïĞ–¼”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param catvGaishaBiko ‚b‚`‚s‚u‰ïĞ–¼”õl
	 */
	public void setCatvGaishaBiko(String catvGaishaBiko) {
		this.catvGaishaBiko = catvGaishaBiko;
	}

	/**
	 * ¬C‘U‰ïĞ‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ¬C‘U‰ïĞ
	 */
	public String getShoshuZen() {
		return shoshuZen;
	}

	/**
	 * ¬C‘U‰ïĞ‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param shoshuZen ¬C‘U‰ïĞ
	 */
	public void setShoshuZen(String shoshuZen) {
		this.shoshuZen = shoshuZen;
	}

	/**
	 * ¬C‘U‰ïĞ‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ¬C‘U‰ïĞ‚s‚d‚k
	 */
	public String getShoshuZenTel() {
		return shoshuZenTel;
	}

	/**
	 * ¬C‘U‰ïĞ‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param shoshuZenTel ¬C‘U‰ïĞ‚s‚d‚k
	 */
	public void setShoshuZenTel(String shoshuZenTel) {
		this.shoshuZenTel = shoshuZenTel;
	}

	/**
	 * ¬C‘U‰ïĞ‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ¬C‘U‰ïĞ‚e‚`‚w
	 */
	public String getShoshuZenFax() {
		return shoshuZenFax;
	}

	/**
	 * ¬C‘U‰ïĞ‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param shoshuZenFax ¬C‘U‰ïĞ‚e‚`‚w
	 */
	public void setShoshuZenFax(String shoshuZenFax) {
		this.shoshuZenFax = shoshuZenFax;
	}

	/**
	 * ¬C‘U‰ïĞE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ¬C‘U‰ïĞE-mail
	 */
	public String getShoshuZenMailAddress() {
		return shoshuZenMailAddress;
	}

	/**
	 * ¬C‘U‰ïĞE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param shoshuZenMailAddress ¬C‘U‰ïĞE-mail
	 */
	public void setShoshuZenMailAddress(String shoshuZenMailAddress) {
		this.shoshuZenMailAddress = shoshuZenMailAddress;
	}

	/**
	 * ¬C‘U‰ïĞ”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ¬C‘U‰ïĞ”õl
	 */
	public String getShoshuZenBiko() {
		return shoshuZenBiko;
	}

	/**
	 * ¬C‘U‰ïĞ”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param shoshuZenBiko ¬C‘U‰ïĞ”õl
	 */
	public void setShoshuZenBiko(String shoshuZenBiko) {
		this.shoshuZenBiko = shoshuZenBiko;
	}

	/**
	 * ƒKƒ‰ƒX‹ÆÒ‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒKƒ‰ƒX‹ÆÒ
	 */
	public String getGlassGyosha() {
		return glassGyosha;
	}

	/**
	 * ƒKƒ‰ƒX‹ÆÒ‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param glassGyosha ƒKƒ‰ƒX‹ÆÒ
	 */
	public void setGlassGyosha(String glassGyosha) {
		this.glassGyosha = glassGyosha;
	}

	/**
	 * ƒKƒ‰ƒX‹ÆÒ‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒKƒ‰ƒX‹ÆÒ‚s‚d‚k
	 */
	public String getGlassGyoshaTel() {
		return glassGyoshaTel;
	}

	/**
	 * ƒKƒ‰ƒX‹ÆÒ‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param glassGyoshaTel ƒKƒ‰ƒX‹ÆÒ‚s‚d‚k
	 */
	public void setGlassGyoshaTel(String glassGyoshaTel) {
		this.glassGyoshaTel = glassGyoshaTel;
	}

	/**
	 * ƒKƒ‰ƒX‹ÆÒ‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒKƒ‰ƒX‹ÆÒ‚e‚`‚w
	 */
	public String getGlassGyoshaFax() {
		return glassGyoshaFax;
	}

	/**
	 * ƒKƒ‰ƒX‹ÆÒ‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param glassGyoshaFax ƒKƒ‰ƒX‹ÆÒ‚e‚`‚w
	 */
	public void setGlassGyoshaFax(String glassGyoshaFax) {
		this.glassGyoshaFax = glassGyoshaFax;
	}

	/**
	 * ƒKƒ‰ƒX‹ÆÒE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒKƒ‰ƒX‹ÆÒE-mail
	 */
	public String getGlassGyoshaMailAddress() {
		return glassGyoshaMailAddress;
	}

	/**
	 * ƒKƒ‰ƒX‹ÆÒE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param glassGyoshaMailAddress ƒKƒ‰ƒX‹ÆÒE-mail
	 */
	public void setGlassGyoshaMailAddress(String glassGyoshaMailAddress) {
		this.glassGyoshaMailAddress = glassGyoshaMailAddress;
	}

	/**
	 * ƒKƒ‰ƒX‹ÆÒ”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ƒKƒ‰ƒX‹ÆÒ”õl
	 */
	public String getGlassGyoshaBiko() {
		return glassGyoshaBiko;
	}

	/**
	 * ƒKƒ‰ƒX‹ÆÒ”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param glassGyoshaBiko ƒKƒ‰ƒX‹ÆÒ”õl
	 */
	public void setGlassGyoshaBiko(String glassGyoshaBiko) {
		this.glassGyoshaBiko = glassGyoshaBiko;
	}

	/**
	 * ‚»‚Ì‘¼‚P‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚P
	 */
	public String getEtc1() {
		return etc1;
	}

	/**
	 * ‚»‚Ì‘¼‚P‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc1 ‚»‚Ì‘¼‚P
	 */
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	/**
	 * ‚»‚Ì‘¼‚P‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚P‚s‚d‚k
	 */
	public String getEtc1Tel() {
		return etc1Tel;
	}

	/**
	 * ‚»‚Ì‘¼‚P‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc1Tel ‚»‚Ì‘¼‚P‚s‚d‚k
	 */
	public void setEtc1Tel(String etc1Tel) {
		this.etc1Tel = etc1Tel;
	}

	/**
	 * ‚»‚Ì‘¼‚P‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚P‚e‚`‚w
	 */
	public String getEtc1Fax() {
		return etc1Fax;
	}

	/**
	 * ‚»‚Ì‘¼‚P‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc1Fax ‚»‚Ì‘¼‚P‚e‚`‚w
	 */
	public void setEtc1Fax(String etc1Fax) {
		this.etc1Fax = etc1Fax;
	}

	/**
	 * ‚»‚Ì‘¼‚PE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚PE-mail
	 */
	public String getEtc1MailAddress() {
		return etc1MailAddress;
	}

	/**
	 * ‚»‚Ì‘¼‚PE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc1MailAddress ‚»‚Ì‘¼‚PE-mail
	 */
	public void setEtc1MailAddress(String etc1MailAddress) {
		this.etc1MailAddress = etc1MailAddress;
	}

	/**
	 * ‚»‚Ì‘¼‚P”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚P”õl
	 */
	public String getEtc1Biko() {
		return etc1Biko;
	}

	/**
	 * ‚»‚Ì‘¼‚P”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc1Biko ‚»‚Ì‘¼‚P”õl
	 */
	public void setEtc1Biko(String etc1Biko) {
		this.etc1Biko = etc1Biko;
	}

	/**
	 * ‚»‚Ì‘¼‚Q‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚Q
	 */
	public String getEtc2() {
		return etc2;
	}

	/**
	 * ‚»‚Ì‘¼‚Q‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc2 ‚»‚Ì‘¼‚Q
	 */
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	/**
	 * ‚»‚Ì‘¼‚Q‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚Q‚s‚d‚k
	 */
	public String getEtc2Tel() {
		return etc2Tel;
	}

	/**
	 * ‚»‚Ì‘¼‚Q‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc2Tel ‚»‚Ì‘¼‚Q‚s‚d‚k
	 */
	public void setEtc2Tel(String etc2Tel) {
		this.etc2Tel = etc2Tel;
	}

	/**
	 * ‚»‚Ì‘¼‚Q‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚Q‚e‚`‚w
	 */
	public String getEtc2Fax() {
		return etc2Fax;
	}

	/**
	 * ‚»‚Ì‘¼‚Q‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc2Fax ‚»‚Ì‘¼‚Q‚e‚`‚w
	 */
	public void setEtc2Fax(String etc2Fax) {
		this.etc2Fax = etc2Fax;
	}

	/**
	 * ‚»‚Ì‘¼‚QE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚QE-mail
	 */
	public String getEtc2MailAddress() {
		return etc2MailAddress;
	}

	/**
	 * ‚»‚Ì‘¼‚QE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc2MailAddress ‚»‚Ì‘¼‚QE-mail
	 */
	public void setEtc2MailAddress(String etc2MailAddress) {
		this.etc2MailAddress = etc2MailAddress;
	}

	/**
	 * ‚»‚Ì‘¼‚Q”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚Q”õl
	 */
	public String getEtc2Biko() {
		return etc2Biko;
	}

	/**
	 * ‚»‚Ì‘¼‚Q”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc2Biko ‚»‚Ì‘¼‚Q”õl
	 */
	public void setEtc2Biko(String etc2Biko) {
		this.etc2Biko = etc2Biko;
	}

	/**
	 * ‚»‚Ì‘¼‚R‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚R
	 */
	public String getEtc3() {
		return etc3;
	}

	/**
	 * ‚»‚Ì‘¼‚R‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc3 ‚»‚Ì‘¼‚R
	 */
	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

	/**
	 * ‚»‚Ì‘¼‚R‚s‚d‚k‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚R‚s‚d‚k
	 */
	public String getEtc3Tel() {
		return etc3Tel;
	}

	/**
	 * ‚»‚Ì‘¼‚R‚s‚d‚k‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc3Tel ‚»‚Ì‘¼‚R‚s‚d‚k
	 */
	public void setEtc3Tel(String etc3Tel) {
		this.etc3Tel = etc3Tel;
	}

	/**
	 * ‚»‚Ì‘¼‚R‚e‚`‚w‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚R‚e‚`‚w
	 */
	public String getEtc3Fax() {
		return etc3Fax;
	}

	/**
	 * ‚»‚Ì‘¼‚R‚e‚`‚w‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc3Fax ‚»‚Ì‘¼‚R‚e‚`‚w
	 */
	public void setEtc3Fax(String etc3Fax) {
		this.etc3Fax = etc3Fax;
	}

	/**
	 * ‚»‚Ì‘¼‚RE-mail‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚RE-mail
	 */
	public String getEtc3MailAddress() {
		return etc3MailAddress;
	}

	/**
	 * ‚»‚Ì‘¼‚RE-mail‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc3MailAddress ‚»‚Ì‘¼‚RE-mail
	 */
	public void setEtc3MailAddress(String etc3MailAddress) {
		this.etc3MailAddress = etc3MailAddress;
	}

	/**
	 * ‚»‚Ì‘¼‚R”õl‚ğæ“¾‚µ‚Ü‚·B
	 * 
	 * @return ‚»‚Ì‘¼‚R”õl
	 */
	public String getEtc3Biko() {
		return etc3Biko;
	}

	/**
	 * ‚»‚Ì‘¼‚R”õl‚ğİ’è‚µ‚Ü‚·B
	 * 
	 * @param etc3Biko ‚»‚Ì‘¼‚R”õl
	 */
	public void setEtc3Biko(String etc3Biko) {
		this.etc3Biko = etc3Biko;
	}

	/**
	 * CSVƒJƒ‰ƒ€—ñiƒL[j‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return CSVƒJƒ‰ƒ€—ñiƒL[j
	 */
	public String[] getCsvColumnsKey() {
		return CSV_COLUMNS_KEY;
	}

	/**
	 * CSVƒJƒ‰ƒ€—ñi–¼‘Oj‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @return CSVƒJƒ‰ƒ€—ñi–¼‘Oj
	 */
	public String[] getCsvColumnsName() {
		return CSV_COLUMNS_NAME;
	}

	/**
	 * ŒÚ‹q‹æ•ª‚ªu3F•¨Œv‚©‚Ç‚¤‚©ƒ`ƒFƒbƒN‚µ‚Ü‚·B
	 *
	 * @return trueFŒÚ‹q‹æ•ª‚ªu3F•¨Œv
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="3‚Ü‚½‚Íu3F•¨Œv‚ğw’è‚µ‚Ä‰º‚³‚¢B")
	private boolean isKokyakuKbn() {
		return kokyakuKbn.equals(KOKYAKU_KBN_BUKKEN) || kokyakuKbn.equals(RcpMKokyaku.KOKYAKU_KBN_BUKKEN);
	}

	/**
	 * ŒÚ‹qí•ÊiŒÂl/–@lj‚ªu1F–@lv‚©‚Ç‚¤‚©ƒ`ƒFƒbƒN‚µ‚Ü‚·B
	 *
	 * @return trueFŒÚ‹qí•ÊiŒÂl/–@lj‚ªu1F–@lv
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="1‚Ü‚½‚Íu1F–@lv‚ğw’è‚µ‚Ä‰º‚³‚¢B")
	private boolean isKokyakuShubetsu() {
		return kokyakuShubetsu.equals(KOKYAKU_SHUBETSU_HOJIN) || kokyakuShubetsu.equals(RcpMKokyaku.KOKYAKU_SHUBETSU_HOJIN);
	}
}
