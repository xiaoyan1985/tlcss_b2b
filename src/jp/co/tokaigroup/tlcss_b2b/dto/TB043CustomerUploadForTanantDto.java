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
 * �Ǘ����A�b�v���[�h(������)DTO�B
 *
 * @author v140546
 * @version 1.0 2014/08/04
 * @version 1.1 2015/11/10 J.Matsuba ���ڒǉ��ɂ��C��
 * @version 1.2 2016/02/12 H.Yamamura �����ԍ��̓��̓`�F�b�N��S�p�������S�p�����ɕύX
 */
public class TB043CustomerUploadForTanantDto extends TB043CSVUploadCommonDto {

	/** ���ڐ� */
	public static final int ITEM_COUNT = 90;

	/** CSV�J������i�L�[�j */
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

	/** CSV�J������i���O�j */
	public static final String[] CSV_COLUMNS_NAME = new String[] {
		"�ڋq�敪",
		"�ڋq��ʁi�l/�@�l�j",
		"�J�i�����i���j",
		"�J�i�����i���j",
		"���������i���j",
		"���������i���j",
		"�X�֔ԍ�",
		"�Z���P�@�s���{��",
		"�Z���Q�@�s�撬��",
		"�Z���R�@��/�厚",
		"�Z���S�@�Ԓn",
		"�Z���T�@�A�p�[�g�}���V����",
		"�����ԍ�",
		"�d�b�ԍ��P",
		"�d�b�ԍ��Q",
		"�e�`�w�ԍ�",
		"���ӎ����P",
		"���ӎ����Q",
		"���ӎ����R",
		"���ӎ����S",
		"���ӎ����S�\���J�n��",
		"���ӎ����S�\���I����",
		"���ӎ����T",
		"���ӎ����T�\���J�n��",
		"���ӎ����T�\���I����",
		"���ӎ����U",
		"���ӎ����U�\���J�n��",
		"���ӎ����U�\���I����",
		"���ߓ�",
		"������",
		"�ދ���",
		"�ً}�A����",
		"�T�[�r�X�敪",
		"�\����",
		"�_��J�n��",
		"�_��I����",
		"��d�����d�b�ԍ�",
		"���l",
		"�戵�X�R�[�h",
		"�戵�X��",
		"�S���Җ�",
		"������",
		"�����惁��",
		"�������@",
		"��s�R�[�h",
		"�x�X�R�[�h",
		"�����ԍ��敪",
		"�����ԍ�",
		"���`�l",
		"�J�[�h�ԍ��P",
		"�J�[�h�ԍ��Q",
		"�J�[�h�ԍ��R",
		"�J�[�h�ԍ��S",
		"�L������",
		"������ڋq�h�c",
		"�ڋq�����J�n��",
		"�ڋq�����I����",
		"�Ǘ���U�փt���O",
		"����ԍ�",
		"�v�d�k�a�n�w�t���O",
		"�\���敪",
		"�����`��",
		"�����p�敪",
		"��{����",
		"�����敪",
		"�T�[�r�X�J�n��",
		"����",
		"���N����",
		"�\�����N��",
		"�����ԍ�",
		"���[���A�h���X",
		"���[���A�h���X�敪",
		"�ً}�A����@�J�i��",
		"�ً}�A����@������",
		"�ً}�A����@�����敪",
		"�ً}�A����@�s�d�k",
		"�ً}�A����@�Z��",
		"�����l�@�J�i���P",
		"�����l�@�������P",
		"�����l�@�����敪�P",
		"�����l�@�A����s�d�k�P",
		"�����l�@�A����Z���P",
		"�����l�@�J�i���Q",
		"�����l�@�������Q",
		"�����l�@�����敪�Q",
		"�����l�@�A����s�d�k�Q",
		"�����l�@�A����Z���Q",
		"�Ǘ���Ё@�J�i��",
		"�Ǘ���Ё@������",
		"�Ǘ���Ё@�s�d�k"
	};

	/** �ڋq�敪�F�u4�F�����ҁE�l�v */
	private static final String KOKYAKU_KBN_NYUKYOSHA = "4�F�����ҁE�l";

	/** �ڋq��ʁF�u0�F�l�v */
	private static final String KOKYAKU_SHUBETSU_KOJIN = "0�F�l";

	/** �T�[�r�X�敪�F�u2�F���C�t�v */
	private static final String SERVICE_KBN_LIFE = "2�F���C�t";

	/**
	 * �ڋq�敪
	 */
	@NotEmpty
	@MaxLength(max=15)
	private String kokyakuKbn;

	/**
	 * �ڋq��ʁi�l/�@�l�j
	 */
	@NotEmpty
	@MaxLength(max=7)
	private String kokyakuShubetsu;

	/**
	 * �J�i�����i���j
	 */
	@NotEmpty
	@KanaName
	@MaxLength(max=40)
	private String kanaNm1;

	/**
	 * �J�i�����i���j
	 */
	@KanaName
	@MaxLength(max=40)
	private String kanaNm2;

	/**
	 * ���������i���j
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=40)
	private String kanjiNm1;

	/**
	 * ���������i���j
	 */
	@Zenkaku
	@MaxLength(max=40)
	private String kanjiNm2;

	/**
	 * �X�֔ԍ�
	 */
	@NotEmpty
	@Num
	@MaxLength(max=7)
	private String yubinNo;

	/**
	 * �Z���P�@�s���{��
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=10)
	private String jusho1;

	/**
	 * �Z���Q�@�s�撬��
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=30)
	private String jusho2;

	/**
	 * �Z���R�@��/�厚
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=30)
	private String jusho3;

	/**
	 * �Z���S�@�Ԓn
	 */
	@NotEmpty
	@ZenNumMinus
	@MaxLength(max=30)
	private String jusho4;

	/**
	 * �Z���T�@�A�p�[�g�}���V����
	 */
	@Zenkaku
	@MaxLength(max=40)
	private String jusho5;

	/**
	 * �����ԍ�
	 */
	@Zenkaku
	@MaxLength(max=20)
	private String kokyakuRoomNo;

	/**
	 * �d�b�ԍ��P
	 */
	@Num
	@MaxLength(max=15)
	private String telNo1;

	/**
	 * �d�b�ԍ��Q
	 */
	@Num
	@MaxLength(max=15)
	private String telNo2;

	/**
	 * �e�`�w�ԍ�
	 */
	private String faxNo;

	/**
	 * ���ӎ����P
	 */
	private String attention1;

	/**
	 * ���ӎ����Q
	 */
	private String attention2;

	/**
	 * ���ӎ����R
	 */
	private String attention3;

	/**
	 * ���ӎ����S
	 */
	private String attention4;

	/**
	 * ���ӎ����S�\���J�n��
	 */
	private String attention4StartDt;

	/**
	 * ���ӎ����S�\���I����
	 */
	private String attention4EndDt;

	/**
	 * ���ӎ����T
	 */
	private String attention5;

	/**
	 * ���ӎ����T�\���J�n��
	 */
	private String attention5StartDt;

	/**
	 * ���ӎ����T�\���I����
	 */
	private String attention5EndDt;

	/**
	 * ���ӎ����U
	 */
	private String attention6;

	/**
	 * ���ӎ����U�\���J�n��
	 */
	private String attention6StartDt;

	/**
	 * ���ӎ����U�\���I����
	 */
	private String attention6EndDt;

	/**
	 * ���ߓ�
	 */
	private String shimeDay;

	/**
	 * ������
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String nyukyoDt;

	/**
	 * �ދ���
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String taikyoDt;

	/**
	 * �ً}�A����
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String kinkyuRenrakusaki;

	/**
	 * �T�[�r�X�敪
	 */
	@NotEmpty
	@MaxLength(max=10)
	private String serviceKbn;

	/**
	 * �\����
	 */
	@NotEmpty
	@Time(pattern="yyyyMMdd")
	@MaxLength(max=8)
	private String moshikomiDt;

	/**
	 * �_��J�n��
	 */
	@Time(pattern="yyyyMMdd")
	@MaxLength(max=8)
	private String keiyakuStartDt;

	/**
	 * �_��I����
	 */
	@Time(pattern="yyyyMMdd")
	@MaxLength(max=8)
	private String keiyakuEndDt;

	/**
	 * ��d�����d�b�ԍ�
	 */
	private String madoguchiTel;

	/**
	 * ���l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String biko;

	/**
	 * �戵�X�R�[�h
	 */
	private String toriCd;

	/**
	 * �戵�X��
	 */
	private String toriNm;

	/**
	 * �S���Җ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String tantoshaNm;

	/**
	 * ������
	 */
	private String seikyusakiCd;

	/**
	 * �����惁��
	 */
	private String seikyusakiMemo;

	/**
	 * �������@
	 */
	private String seikyuKbn;

	/**
	 * ��s�R�[�h
	 */
	private String ginkoCd;

	/**
	 * �x�X�R�[�h
	 */
	private String shitenCd;

	/**
	 * �����ԍ��敪
	 */
	private String kozaNoKbn;

	/**
	 * �����ԍ�
	 */
	private String kozaNo;

	/**
	 * ���`�l
	 */
	private String meiginin;

	/**
	 * �J�[�h�ԍ��P
	 */
	private String cardNo1;

	/**
	 * �J�[�h�ԍ��Q
	 */
	private String cardNo2;

	/**
	 * �J�[�h�ԍ��R
	 */
	private String cardNo3;

	/**
	 * �J�[�h�ԍ��S
	 */
	private String cardNo4;

	/**
	 * �L������
	 */
	private String yukoKigen;

	/**
	 * ������ڋq�h�c
	 */
	private String seikyusakiKokyakuId;

	/**
	 * �ڋq�����J�n��
	 */
	private String kokyakuSeikyuStartDt;

	/**
	 * �ڋq�����I����
	 */
	private String kokyakuSeikyuEndDt;

	/**
	 * �Ǘ���U�փt���O
	 */
	private String kanrihiFurikaeFlg;

	/**
	 * ����ԍ�
	 */
	@NotEmpty
	@Alphanum
	@MaxLength(max=10)
	private String kaiinNo;

	/**
	 * �v�d�k�a�n�w�t���O
	 */
	private String welboxFlg;

	/**
	 * �\���敪
	 */
	@NotEmpty
	@MaxLength(max=20)
	private String moshikomiKbn;

	/**
	 * �����`��
	 */
	@NotEmpty
	@MaxLength(max=20)
	private String tatemonoType;

	/**
	 * �����p�敪
	 */
	@NotEmpty
	@MaxLength(max=20)
	private String nyukaiHiyoKbn;

	/**
	 * ��{����
	 */
	@NotEmpty
	@Num
	@MaxLength(max=7)
	private String kihonRyokin;

	/**
	 * �����敪
	 */
	@NotEmpty
	@MaxLength(max=20)
	private String nyukyoKbn;

	/**
	 * �T�[�r�X�J�n��
	 */
	@Time(pattern="yyyyMMdd")
	@MaxLength(max=8)
	private String serviceStartDt;

	/**
	 * ����
	 */
	@NotEmpty
	@MaxLength(max=20)
	private String sexKbn;

	/**
	 * ���N����
	 */
	@Time(pattern="yyyyMMdd")
	@MaxLength(max=8)
	private String birthDt;

	/**
	 * �\�����N��
	 */
	@Num
	@MaxLength(max=3)
	private String moshikomiNenrei;

	/**
	 * �����ԍ�
	 */
	@NotEmpty
	@Hankaku
	@MaxLength(max=10)
	private String roomNo;

	/**
	 * ���[���A�h���X
	 */
	@MailAddress
	@MaxLength(max=256)
	private String mailAddress;

	/**
	 * ���[���A�h���X�敪
	 */
	@MaxLength(max=20)
	private String mailAddressKbn;

	/**
	 * �ً}�A����J�i��
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kinkyuKanaNm;

	/**
	 * �ً}�A���抿����
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kinkyuKanjiNm;

	/**
	 * �ً}�A���摱���敪
	 */
	@MaxLength(max=20)
	private String kinkyuTsudukiKbn;

	/**
	 * �ً}�A����s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kinkyuTel;

	/**
	 * �ً}�A����Z��
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String kinkyuJusho;

	/**
	 * �����l�J�i���P
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoKanaNm1;

	/**
	 * �����l�������P
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoKanjiNm1;

	/**
	 * �����l�����敪�P
	 */
	@MaxLength(max=20)
	private String dokyoTsudukiKbn1;

	/**
	 * �����l�A����s�d�k�P
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoTel1;

	/**
	 * �����l�A����Z���P
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String dokyoJusho1;

	/**
	 * �����l�J�i���Q
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoKanaNm2;

	/**
	 * �����l�������Q
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoKanjiNm2;

	/**
	 * �����l�����敪�Q
	 */
	@MaxLength(max=20)
	private String dokyoTsudukiKbn2;

	/**
	 * �����l�A����s�d�k�Q
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String dokyoTel2;

	/**
	 * �����l�A����Z���Q
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String dokyoJusho2;

	/**
	 * �Ǘ���ЃJ�i��
	 */
	@NotEmpty
	@Zenhankaku
	@MaxLength(max=50)
	private String kanriKanaNm;

	/**
	 * �Ǘ���Њ�����
	 */
	@NotEmpty
	@Zenhankaku
	@MaxLength(max=100)
	private String kanriKanjiNm;

	/**
	 * �Ǘ���Ђs�d�k
	 */
	@NotEmpty
	@Zenhankaku
	@MaxLength(max=50)
	private String kanriTel;


	/**
	 * �ڋq�敪���擾���܂��B
	 *
	 * @return �ڋq�敪
	 */
	public String getKokyakuKbn() {
		return kokyakuKbn;
	}

	/**
	 * �ڋq�敪��ݒ肵�܂��B
	 *
	 * @param kokyakuKbn �ڋq�敪
	 */
	public void setKokyakuKbn(String kokyakuKbn) {
		this.kokyakuKbn = kokyakuKbn;
	}

	/**
	 * �ڋq��ʁi�l/�@�l�j���擾���܂��B
	 *
	 * @return �ڋq��ʁi�l/�@�l�j
	 */
	public String getKokyakuShubetsu() {
		return kokyakuShubetsu;
	}

	/**
	 * �ڋq��ʁi�l/�@�l�j��ݒ肵�܂��B
	 *
	 * @param kokyakuShubetsu �ڋq��ʁi�l/�@�l�j
	 */
	public void setKokyakuShubetsu(String kokyakuShubetsu) {
		this.kokyakuShubetsu = kokyakuShubetsu;
	}

	/**
	 * �J�i�����i���j���擾���܂��B
	 *
	 * @return �J�i�����i���j
	 */
	public String getKanaNm1() {
		return kanaNm1;
	}

	/**
	 * �J�i�����i���j��ݒ肵�܂��B
	 *
	 * @param kanaNm1 �J�i�����i���j
	 */
	public void setKanaNm1(String kanaNm1) {
		this.kanaNm1 = kanaNm1;
	}

	/**
	 * �J�i�����i���j���擾���܂��B
	 *
	 * @return �J�i�����i���j
	 */
	public String getKanaNm2() {
		return kanaNm2;
	}

	/**
	 * �J�i�����i���j��ݒ肵�܂��B
	 *
	 * @param kanaNm2 �J�i�����i���j
	 */
	public void setKanaNm2(String kanaNm2) {
		this.kanaNm2 = kanaNm2;
	}

	/**
	 * ���������i���j���擾���܂��B
	 *
	 * @return ���������i���j
	 */
	public String getKanjiNm1() {
		return kanjiNm1;
	}

	/**
	 * ���������i���j��ݒ肵�܂��B
	 *
	 * @param kanjiNm1 ���������i���j
	 */
	public void setKanjiNm1(String kanjiNm1) {
		this.kanjiNm1 = kanjiNm1;
	}

	/**
	 * ���������i���j���擾���܂��B
	 *
	 * @return ���������i���j
	 */
	public String getKanjiNm2() {
		return kanjiNm2;
	}

	/**
	 * ���������i���j��ݒ肵�܂��B
	 *
	 * @param kanjiNm2 ���������i���j
	 */
	public void setKanjiNm2(String kanjiNm2) {
		this.kanjiNm2 = kanjiNm2;
	}

	/**
	 * �X�֔ԍ����擾���܂��B
	 *
	 * @return �X�֔ԍ�
	 */
	public String getYubinNo() {
		return yubinNo;
	}

	/**
	 * �X�֔ԍ���ݒ肵�܂��B
	 *
	 * @param yubinNo �X�֔ԍ�
	 */
	public void setYubinNo(String yubinNo) {
		this.yubinNo = yubinNo;
	}

	/**
	 * �Z���P�@�s���{�����擾���܂��B
	 *
	 * @return �Z���P�@�s���{��
	 */
	public String getJusho1() {
		return jusho1;
	}

	/**
	 * �Z���P�@�s���{����ݒ肵�܂��B
	 *
	 * @param jusho1 �Z���P�@�s���{��
	 */
	public void setJusho1(String jusho1) {
		this.jusho1 = jusho1;
	}

	/**
	 * �Z���Q�@�s�撬�����擾���܂��B
	 *
	 * @return �Z���Q�@�s�撬��
	 */
	public String getJusho2() {
		return jusho2;
	}

	/**
	 * �Z���Q�@�s�撬����ݒ肵�܂��B
	 *
	 * @param jusho2 �Z���Q�@�s�撬��
	 */
	public void setJusho2(String jusho2) {
		this.jusho2 = jusho2;
	}

	/**
	 * �Z���R�@��/�厚���擾���܂��B
	 *
	 * @return �Z���R�@��/�厚
	 */
	public String getJusho3() {
		return jusho3;
	}

	/**
	 * �Z���R�@��/�厚��ݒ肵�܂��B
	 *
	 * @param jusho3 �Z���R�@��/�厚
	 */
	public void setJusho3(String jusho3) {
		this.jusho3 = jusho3;
	}

	/**
	 * �Z���S�@�Ԓn���擾���܂��B
	 *
	 * @return �Z���S�@�Ԓn
	 */
	public String getJusho4() {
		return jusho4;
	}

	/**
	 * �Z���S�@�Ԓn��ݒ肵�܂��B
	 *
	 * @param jusho4 �Z���S�@�Ԓn
	 */
	public void setJusho4(String jusho4) {
		this.jusho4 = jusho4;
	}

	/**
	 * �Z���T�@�A�p�[�g�}���V�������擾���܂��B
	 *
	 * @return �Z���T�@�A�p�[�g�}���V����
	 */
	public String getJusho5() {
		return jusho5;
	}

	/**
	 * �Z���T�@�A�p�[�g�}���V������ݒ肵�܂��B
	 *
	 * @param jusho5 �Z���T�@�A�p�[�g�}���V����
	 */
	public void setJusho5(String jusho5) {
		this.jusho5 = jusho5;
	}

	/**
	 * �����ԍ����擾���܂��B
	 *
	 * @return �����ԍ�
	 */
	public String getKokyakuRoomNo() {
		return kokyakuRoomNo;
	}

	/**
	 * �����ԍ���ݒ肵�܂��B
	 *
	 * @param kokyakuRoomNo �����ԍ�
	 */
	public void setKokyakuRoomNo(String kokyakuRoomNo) {
		this.kokyakuRoomNo = kokyakuRoomNo;
	}

	/**
	 * �d�b�ԍ��P���擾���܂��B
	 *
	 * @return �d�b�ԍ��P
	 */
	public String getTelNo1() {
		return telNo1;
	}

	/**
	 * �d�b�ԍ��P��ݒ肵�܂��B
	 *
	 * @param telNo1 �d�b�ԍ��P
	 */
	public void setTelNo1(String telNo1) {
		this.telNo1 = telNo1;
	}

	/**
	 * �d�b�ԍ��Q���擾���܂��B
	 *
	 * @return �d�b�ԍ��Q
	 */
	public String getTelNo2() {
		return telNo2;
	}

	/**
	 * �d�b�ԍ��Q��ݒ肵�܂��B
	 *
	 * @param telNo2 �d�b�ԍ��Q
	 */
	public void setTelNo2(String telNo2) {
		this.telNo2 = telNo2;
	}

	/**
	 * �e�`�w�ԍ����擾���܂��B
	 *
	 * @return �e�`�w�ԍ�
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * �e�`�w�ԍ���ݒ肵�܂��B
	 *
	 * @param faxNo �e�`�w�ԍ�
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * ���ӎ����P���擾���܂��B
	 *
	 * @return ���ӎ����P
	 */
	public String getAttention1() {
		return attention1;
	}

	/**
	 * ���ӎ����P��ݒ肵�܂��B
	 *
	 * @param attention1 ���ӎ����P
	 */
	public void setAttention1(String attention1) {
		this.attention1 = attention1;
	}

	/**
	 * ���ӎ����Q���擾���܂��B
	 *
	 * @return ���ӎ����Q
	 */
	public String getAttention2() {
		return attention2;
	}

	/**
	 * ���ӎ����Q��ݒ肵�܂��B
	 *
	 * @param attention2 ���ӎ����Q
	 */
	public void setAttention2(String attention2) {
		this.attention2 = attention2;
	}

	/**
	 * ���ӎ����R���擾���܂��B
	 *
	 * @return ���ӎ����R
	 */
	public String getAttention3() {
		return attention3;
	}

	/**
	 * ���ӎ����R��ݒ肵�܂��B
	 *
	 * @param attention3 ���ӎ����R
	 */
	public void setAttention3(String attention3) {
		this.attention3 = attention3;
	}

	/**
	 * ���ӎ����S���擾���܂��B
	 *
	 * @return ���ӎ����S
	 */
	public String getAttention4() {
		return attention4;
	}

	/**
	 * ���ӎ����S��ݒ肵�܂��B
	 *
	 * @param attention4 ���ӎ����S
	 */
	public void setAttention4(String attention4) {
		this.attention4 = attention4;
	}

	/**
	 * ���ӎ����S�\���J�n�����擾���܂��B
	 *
	 * @return ���ӎ����S�\���J�n��
	 */
	public String getAttention4StartDt() {
		return attention4StartDt;
	}

	/**
	 * ���ӎ����S�\���J�n����ݒ肵�܂��B
	 *
	 * @param attention4StartDt ���ӎ����S�\���J�n��
	 */
	public void setAttention4StartDt(String attention4StartDt) {
		this.attention4StartDt = attention4StartDt;
	}

	/**
	 * ���ӎ����S�\���I�������擾���܂��B
	 *
	 * @return ���ӎ����S�\���I����
	 */
	public String getAttention4EndDt() {
		return attention4EndDt;
	}

	/**
	 * ���ӎ����S�\���I������ݒ肵�܂��B
	 *
	 * @param attention4EndDt ���ӎ����S�\���I����
	 */
	public void setAttention4EndDt(String attention4EndDt) {
		this.attention4EndDt = attention4EndDt;
	}

	/**
	 * ���ӎ����T���擾���܂��B
	 *
	 * @return ���ӎ����T
	 */
	public String getAttention5() {
		return attention5;
	}

	/**
	 * ���ӎ����T��ݒ肵�܂��B
	 *
	 * @param attention5 ���ӎ����T
	 */
	public void setAttention5(String attention5) {
		this.attention5 = attention5;
	}

	/**
	 * ���ӎ����T�\���J�n�����擾���܂��B
	 *
	 * @return ���ӎ����T�\���J�n��
	 */
	public String getAttention5StartDt() {
		return attention5StartDt;
	}

	/**
	 * ���ӎ����T�\���J�n����ݒ肵�܂��B
	 *
	 * @param attention5StartDt �T�\���J�n��
	 */
	public void setAttention5StartDt(String attention5StartDt) {
		this.attention5StartDt = attention5StartDt;
	}

	/**
	 * ���ӎ����T�\���I�������擾���܂��B
	 *
	 * @return ���ӎ����T�\���I����
	 */
	public String getAttention5EndDt() {
		return attention5EndDt;
	}

	/**
	 * ���ӎ����T�\���I������ݒ肵�܂��B
	 *
	 * @param attention5EndDt ���ӎ����T�\���I����
	 */
	public void setAttention5EndDt(String attention5EndDt) {
		this.attention5EndDt = attention5EndDt;
	}

	/**
	 * ���ӎ����U���擾���܂��B
	 *
	 * @return ���ӎ����U
	 */
	public String getAttention6() {
		return attention6;
	}

	/**
	 * ���ӎ����U��ݒ肵�܂��B
	 *
	 * @param attention6 ���ӎ����U
	 */
	public void setAttention6(String attention6) {
		this.attention6 = attention6;
	}

	/**
	 * ���ӎ����U�\���J�n�����擾���܂��B
	 *
	 * @return ���ӎ����U�\���J�n��
	 */
	public String getAttention6StartDt() {
		return attention6StartDt;
	}

	/**
	 * ���ӎ����U�\���J�n����ݒ肵�܂��B
	 *
	 * @param attention6StartDt ���ӎ����U�\���J�n��
	 */
	public void setAttention6StartDt(String attention6StartDt) {
		this.attention6StartDt = attention6StartDt;
	}

	/**
	 * ���ӎ����U�\���I�������擾���܂��B
	 *
	 * @return ���ӎ����U�\���I����
	 */
	public String getAttention6EndDt() {
		return attention6EndDt;
	}

	/**
	 * ���ӎ����U�\���I������ݒ肵�܂��B
	 *
	 * @param attention6EndDt ���ӎ����U�\���I����
	 */
	public void setAttention6EndDt(String attention6EndDt) {
		this.attention6EndDt = attention6EndDt;
	}

	/**
	 * ���ߓ����擾���܂��B
	 *
	 * @return ���ߓ�
	 */
	public String getShimeDay() {
		return shimeDay;
	}

	/**
	 * ���ߓ���ݒ肵�܂��B
	 *
	 * @param shimeDay ���ߓ�
	 */
	public void setShimeDay(String shimeDay) {
		this.shimeDay = shimeDay;
	}

	/**
	 * ���������擾���܂��B
	 *
	 * @return ������
	 */
	public String getNyukyoDt() {
		return nyukyoDt;
	}

	/**
	 * ��������ݒ肵�܂��B
	 *
	 * @param nyukyoDt ������
	 */
	public void setNyukyoDt(String nyukyoDt) {
		this.nyukyoDt = nyukyoDt;
	}

	/**
	 * �ދ������擾���܂��B
	 *
	 * @return �ދ���
	 */
	public String getTaikyoDt() {
		return taikyoDt;
	}

	/**
	 * �ދ�����ݒ肵�܂��B
	 *
	 * @param taikyoDt �ދ���
	 */
	public void setTaikyoDt(String taikyoDt) {
		this.taikyoDt = taikyoDt;
	}

	/**
	 * �ً}�A������擾���܂��B
	 *
	 * @return �ً}�A����
	 */
	public String getKinkyuRenrakusaki() {
		return kinkyuRenrakusaki;
	}

	/**
	 * �ً}�A�����ݒ肵�܂��B
	 *
	 * @param kinkyuRenrakusaki �ً}�A����
	 */
	public void setKinkyuRenrakusaki(String kinkyuRenrakusaki) {
		this.kinkyuRenrakusaki = kinkyuRenrakusaki;
	}

	/**
	 * �T�[�r�X�敪���擾���܂��B
	 *
	 * @return �T�[�r�X�敪
	 */
	public String getServiceKbn() {
		return serviceKbn;
	}

	/**
	 * �T�[�r�X�敪��ݒ肵�܂��B
	 *
	 * @param serviceKbn �T�[�r�X�敪
	 */
	public void setServiceKbn(String serviceKbn) {
		this.serviceKbn = serviceKbn;
	}

	/**
	 * �\�������擾���܂��B
	 *
	 * @return �\����
	 */
	public String getMoshikomiDt() {
		return moshikomiDt;
	}

	/**
	 * �\������ݒ肵�܂��B
	 *
	 * @param moshikomiDt �\����
	 */
	public void setMoshikomiDt(String moshikomiDt) {
		this.moshikomiDt = moshikomiDt;
	}

	/**
	 * �_��J�n�����擾���܂��B
	 *
	 * @return �_��J�n��
	 */
	public String getKeiyakuStartDt() {
		return keiyakuStartDt;
	}

	/**
	 * �_��J�n����ݒ肵�܂��B
	 *
	 * @param keiyakuStartDt �_��J�n��
	 */
	public void setKeiyakuStartDt(String keiyakuStartDt) {
		this.keiyakuStartDt = keiyakuStartDt;
	}

	/**
	 * �_��I�������擾���܂��B
	 *
	 * @return �_��I����
	 */
	public String getKeiyakuEndDt() {
		return keiyakuEndDt;
	}

	/**
	 * �_��I������ݒ肵�܂��B
	 *
	 * @param keiyakuEndDt �_��I����
	 */
	public void setKeiyakuEndDt(String keiyakuEndDt) {
		this.keiyakuEndDt = keiyakuEndDt;
	}

	/**
	 * ��d�����d�b�ԍ����擾���܂��B
	 *
	 * @return ��d�����d�b�ԍ�
	 */
	public String getMadoguchiTel() {
		return madoguchiTel;
	}

	/**
	 * ��d�����d�b�ԍ���ݒ肵�܂��B
	 *
	 * @param madoguchiTel ��d�����d�b�ԍ�
	 */
	public void setMadoguchiTel(String madoguchiTel) {
		this.madoguchiTel = madoguchiTel;
	}

	/**
	 * ���l���擾���܂��B
	 *
	 * @return ���l
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * ���l��ݒ肵�܂��B
	 *
	 * @param biko ���l
	 */
	public void setBiko(String biko) {
		this.biko = biko;
	}

	/**
	 * �戵�X�R�[�h���擾���܂��B
	 *
	 * @return �戵�X�R�[�h
	 */
	public String getToriCd() {
		return toriCd;
	}

	/**
	 * �戵�X�R�[�h��ݒ肵�܂��B
	 *
	 * @param toriCd �戵�X�R�[�h
	 */
	public void setToriCd(String toriCd) {
		this.toriCd = toriCd;
	}

	/**
	 * �戵�X�����擾���܂��B
	 *
	 * @return �戵�X��
	 */
	public String getToriNm() {
		return toriNm;
	}

	/**
	 * �戵�X����ݒ肵�܂��B
	 *
	 * @param toriNm �戵�X��
	 */
	public void setToriNm(String toriNm) {
		this.toriNm = toriNm;
	}

	/**
	 * �S���Җ����擾���܂��B
	 *
	 * @return �S���Җ�
	 */
	public String getTantoshaNm() {
		return tantoshaNm;
	}

	/**
	 * �S���Җ���ݒ肵�܂��B
	 *
	 * @param tantoshaNm �S���Җ�
	 */
	public void setTantoshaNm(String tantoshaNm) {
		this.tantoshaNm = tantoshaNm;
	}

	/**
	 * ��������擾���܂��B
	 *
	 * @return ������
	 */
	public String getSeikyusakiCd() {
		return seikyusakiCd;
	}

	/**
	 * �������ݒ肵�܂��B
	 *
	 * @param seikyusakiCd ������
	 */
	public void setSeikyusakiCd(String seikyusakiCd) {
		this.seikyusakiCd = seikyusakiCd;
	}

	/**
	 * �����惁�����擾���܂��B
	 *
	 * @return �����惁��
	 */
	public String getSeikyusakiMemo() {
		return seikyusakiMemo;
	}

	/**
	 * �����惁����ݒ肵�܂��B
	 *
	 * @param seikyusakiMemo �����惁��
	 */
	public void setSeikyusakiMemo(String seikyusakiMemo) {
		this.seikyusakiMemo = seikyusakiMemo;
	}

	/**
	 * �������@���擾���܂��B
	 *
	 * @return �������@
	 */
	public String getSeikyuKbn() {
		return seikyuKbn;
	}

	/**
	 * �������@��ݒ肵�܂��B
	 *
	 * @param seikyuKbn �������@
	 */
	public void setSeikyuKbn(String seikyuKbn) {
		this.seikyuKbn = seikyuKbn;
	}

	/**
	 * ��s�R�[�h���擾���܂��B
	 *
	 * @return ��s�R�[�h
	 */
	public String getGinkoCd() {
		return ginkoCd;
	}

	/**
	 * ��s�R�[�h��ݒ肵�܂��B
	 *
	 * @param ginkoCd ��s�R�[�h
	 */
	public void setGinkoCd(String ginkoCd) {
		this.ginkoCd = ginkoCd;
	}

	/**
	 * �x�X�R�[�h���擾���܂��B
	 *
	 * @return �x�X�R�[�h
	 */
	public String getShitenCd() {
		return shitenCd;
	}

	/**
	 * �x�X�R�[�h��ݒ肵�܂��B
	 *
	 * @param shitenCd �x�X�R�[�h
	 */
	public void setShitenCd(String shitenCd) {
		this.shitenCd = shitenCd;
	}

	/**
	 * �����ԍ��敪���擾���܂��B
	 *
	 * @return �����ԍ��敪
	 */
	public String getKozaNoKbn() {
		return kozaNoKbn;
	}

	/**
	 * �����ԍ��敪��ݒ肵�܂��B
	 *
	 * @param kozaNoKbn �����ԍ��敪
	 */
	public void setKozaNoKbn(String kozaNoKbn) {
		this.kozaNoKbn = kozaNoKbn;
	}

	/**
	 * �����ԍ����擾���܂��B
	 *
	 * @return �����ԍ�
	 */
	public String getKozaNo() {
		return kozaNo;
	}

	/**
	 * �����ԍ���ݒ肵�܂��B
	 *
	 * @param kozaNo �����ԍ�
	 */
	public void setKozaNo(String kozaNo) {
		this.kozaNo = kozaNo;
	}

	/**
	 * ���`�l���擾���܂��B
	 *
	 * @return ���`�l
	 */
	public String getMeiginin() {
		return meiginin;
	}

	/**
	 * ���`�l��ݒ肵�܂��B
	 *
	 * @param meiginin ���`�l
	 */
	public void setMeiginin(String meiginin) {
		this.meiginin = meiginin;
	}

	/**
	 * �J�[�h�ԍ��P���擾���܂��B
	 *
	 * @return �J�[�h�ԍ��P
	 */
	public String getCardNo1() {
		return cardNo1;
	}

	/**
	 * �J�[�h�ԍ��P��ݒ肵�܂��B
	 *
	 * @param cardNo1 �J�[�h�ԍ��P
	 */
	public void setCardNo1(String cardNo1) {
		this.cardNo1 = cardNo1;
	}

	/**
	 * �J�[�h�ԍ��Q���擾���܂��B
	 *
	 * @return �J�[�h�ԍ��Q
	 */
	public String getCardNo2() {
		return cardNo2;
	}

	/**
	 * �J�[�h�ԍ��Q��ݒ肵�܂��B
	 *
	 * @param cardNo2 �J�[�h�ԍ��Q
	 */
	public void setCardNo2(String cardNo2) {
		this.cardNo2 = cardNo2;
	}

	/**
	 * �J�[�h�ԍ��R���擾���܂��B
	 *
	 * @return �J�[�h�ԍ��R
	 */
	public String getCardNo3() {
		return cardNo3;
	}

	/**
	 * �J�[�h�ԍ��R��ݒ肵�܂��B
	 *
	 * @param cardNo3 �J�[�h�ԍ��R
	 */
	public void setCardNo3(String cardNo3) {
		this.cardNo3 = cardNo3;
	}

	/**
	 * �J�[�h�ԍ��S���擾���܂��B
	 *
	 * @return �J�[�h�ԍ��S
	 */
	public String getCardNo4() {
		return cardNo4;
	}

	/**
	 * �J�[�h�ԍ��S��ݒ肵�܂��B
	 *
	 * @param cardNo4 �J�[�h�ԍ��S
	 */
	public void setCardNo4(String cardNo4) {
		this.cardNo4 = cardNo4;
	}

	/**
	 * �L���������擾���܂��B
	 *
	 * @return �L������
	 */
	public String getYukoKigen() {
		return yukoKigen;
	}

	/**
	 * �L��������ݒ肵�܂��B
	 *
	 * @param yukoKigen �L������
	 */
	public void setYukoKigen(String yukoKigen) {
		this.yukoKigen = yukoKigen;
	}

	/**
	 * ������ڋq�h�c���擾���܂��B
	 *
	 * @return ������ڋq�h�c
	 */
	public String getSeikyusakiKokyakuId() {
		return seikyusakiKokyakuId;
	}

	/**
	 * ������ڋq�h�c��ݒ肵�܂��B
	 *
	 * @param seikyusakiKokyakuId ������ڋq�h�c
	 */
	public void setSeikyusakiKokyakuId(String seikyusakiKokyakuId) {
		this.seikyusakiKokyakuId = seikyusakiKokyakuId;
	}

	/**
	 * �ڋq�����J�n�����擾���܂��B
	 *
	 * @return �ڋq�����J�n��
	 */
	public String getKokyakuSeikyuStartDt() {
		return kokyakuSeikyuStartDt;
	}

	/**
	 * �ڋq�����J�n����ݒ肵�܂��B
	 *
	 * @param kokyakuSeikyuStartDt �ڋq�����J�n��
	 */
	public void setKokyakuSeikyuStartDt(String kokyakuSeikyuStartDt) {
		this.kokyakuSeikyuStartDt = kokyakuSeikyuStartDt;
	}

	/**
	 * �ڋq�����I�������擾���܂��B
	 *
	 * @return �ڋq�����I����
	 */
	public String getKokyakuSeikyuEndDt() {
		return kokyakuSeikyuEndDt;
	}

	/**
	 * �ڋq�����I������ݒ肵�܂��B
	 *
	 * @param kokyakuSeikyuEndDt �ڋq�����I����
	 */
	public void setKokyakuSeikyuEndDt(String kokyakuSeikyuEndDt) {
		this.kokyakuSeikyuEndDt = kokyakuSeikyuEndDt;
	}

	/**
	 * �Ǘ���U�փt���O���擾���܂��B
	 *
	 * @return �Ǘ���U�փt���O
	 */
	public String getKanrihiFurikaeFlg() {
		return kanrihiFurikaeFlg;
	}

	/**
	 * �Ǘ���U�փt���O��ݒ肵�܂��B
	 *
	 * @param kanrihiFurikaeFlg �Ǘ���U�փt���O
	 */
	public void setKanrihiFurikaeFlg(String kanrihiFurikaeFlg) {
		this.kanrihiFurikaeFlg = kanrihiFurikaeFlg;
	}

	/**
	 * ����ԍ����擾���܂��B
	 *
	 * @return ����ԍ�
	 */
	public String getKaiinNo() {
		return kaiinNo;
	}

	/**
	 * ����ԍ���ݒ肵�܂��B
	 *
	 * @param kaiinNo ����ԍ�
	 */
	public void setKaiinNo(String kaiinNo) {
		this.kaiinNo = kaiinNo;
	}

	/**
	 * �v�d�k�a�n�w�t���O���擾���܂��B
	 *
	 * @return �v�d�k�a�n�w�t���O
	 */
	public String getWelboxFlg() {
		return welboxFlg;
	}

	/**
	 * �v�d�k�a�n�w�t���O��ݒ肵�܂��B
	 *
	 * @param welboxFlg �v�d�k�a�n�w�t���O
	 */
	public void setWelboxFlg(String welboxFlg) {
		this.welboxFlg = welboxFlg;
	}

	/**
	 * �\���敪���擾���܂��B
	 *
	 * @return �\���敪
	 */
	public String getMoshikomiKbn() {
		return moshikomiKbn;
	}

	/**
	 * �\���敪��ݒ肵�܂��B
	 *
	 * @param moshikomiKbn �\���敪
	 */
	public void setMoshikomiKbn(String moshikomiKbn) {
		this.moshikomiKbn = moshikomiKbn;
	}

	/**
	 * �����`�Ԃ��擾���܂��B
	 *
	 * @return �����`��
	 */
	public String getTatemonoType() {
		return tatemonoType;
	}

	/**
	 * �����`�Ԃ�ݒ肵�܂��B
	 *
	 * @param tatemonoType �����`��
	 */
	public void setTatemonoType(String tatemonoType) {
		this.tatemonoType = tatemonoType;
	}

	/**
	 * �����p�敪���擾���܂��B
	 *
	 * @return �����p�敪
	 */
	public String getNyukaiHiyoKbn() {
		return nyukaiHiyoKbn;
	}

	/**
	 * �����p�敪��ݒ肵�܂��B
	 *
	 * @param nyukaiHiyoKbn �����p�敪
	 */
	public void setNyukaiHiyoKbn(String nyukaiHiyoKbn) {
		this.nyukaiHiyoKbn = nyukaiHiyoKbn;
	}

	/**
	 * ��{�������擾���܂��B
	 *
	 * @return ��{����
	 */
	public String getKihonRyokin() {
		return kihonRyokin;
	}

	/**
	 * ��{������ݒ肵�܂��B
	 *
	 * @param kihonRyokin ��{����
	 */
	public void setKihonRyokin(String kihonRyokin) {
		this.kihonRyokin = kihonRyokin;
	}

	/**
	 * �����敪���擾���܂��B
	 *
	 * @return �����敪
	 */
	public String getNyukyoKbn() {
		return nyukyoKbn;
	}

	/**
	 * �����敪��ݒ肵�܂��B
	 *
	 * @param nyukyoKbn �����敪
	 */
	public void setNyukyoKbn(String nyukyoKbn) {
		this.nyukyoKbn = nyukyoKbn;
	}

	/**
	 * �T�[�r�X�J�n�����擾���܂��B
	 *
	 * @return �T�[�r�X�J�n��
	 */
	public String getServiceStartDt() {
		return serviceStartDt;
	}

	/**
	 * �T�[�r�X�J�n����ݒ肵�܂��B
	 *
	 * @param serviceStartDt �T�[�r�X�J�n��
	 */
	public void setServiceStartDt(String serviceStartDt) {
		this.serviceStartDt = serviceStartDt;
	}

	/**
	 * ���ʂ��擾���܂��B
	 *
	 * @return ����
	 */
	public String getSexKbn() {
		return sexKbn;
	}

	/**
	 * ���ʂ�ݒ肵�܂��B
	 *
	 * @param sexKbn ����
	 */
	public void setSexKbn(String sexKbn) {
		this.sexKbn = sexKbn;
	}

	/**
	 * ���N�������擾���܂��B
	 *
	 * @return ���N����
	 */
	public String getBirthDt() {
		return birthDt;
	}

	/**
	 * ���N������ݒ肵�܂��B
	 *
	 * @param birthDt ���N����
	 */
	public void setBirthDt(String birthDt) {
		this.birthDt = birthDt;
	}

	/**
	 * �\�����N����擾���܂��B
	 *
	 * @return �\�����N��
	 */
	public String getMoshikomiNenrei() {
		return moshikomiNenrei;
	}

	/**
	 * �\�����N���ݒ肵�܂��B
	 *
	 * @param moshikomiNenrei �\�����N��
	 */
	public void setMoshikomiNenrei(String moshikomiNenrei) {
		this.moshikomiNenrei = moshikomiNenrei;
	}

	/**
	 * �����ԍ����擾���܂��B
	 *
	 * @return �����ԍ�
	 */
	public String getRoomNo() {
		return roomNo;
	}

	/**
	 * �����ԍ���ݒ肵�܂��B
	 *
	 * @param roomNo �����ԍ�
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	/**
	 * ���[���A�h���X���擾���܂��B
	 *
	 * @return ���[���A�h���X
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * ���[���A�h���X��ݒ肵�܂��B
	 *
	 * @param mailAddress ���[���A�h���X
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * ���[���A�h���X�敪���擾���܂��B
	 *
	 * @return ���[���A�h���X�敪
	 */
	public String getMailAddressKbn() {
		return mailAddressKbn;
	}

	/**
	 * ���[���A�h���X�敪��ݒ肵�܂��B
	 *
	 * @param mailAddressKbn ���[���A�h���X�敪
	 */
	public void setMailAddressKbn(String mailAddressKbn) {
		this.mailAddressKbn = mailAddressKbn;
	}

	/**
	 * �ً}�A����J�i�����擾���܂��B
	 *
	 * @return �ً}�A����J�i��
	 */
	public String getKinkyuKanaNm() {
		return kinkyuKanaNm;
	}

	/**
	 * �ً}�A����J�i����ݒ肵�܂��B
	 *
	 * @param kinkyuKanaNm �ً}�A����J�i��
	 */
	public void setKinkyuKanaNm(String kinkyuKanaNm) {
		this.kinkyuKanaNm = kinkyuKanaNm;
	}

	/**
	 * �ً}�A���抿�������擾���܂��B
	 *
	 * @return �ً}�A���抿����
	 */
	public String getKinkyuKanjiNm() {
		return kinkyuKanjiNm;
	}

	/**
	 * �ً}�A���抿������ݒ肵�܂��B
	 *
	 * @param kinkyuKanjiNm �ً}�A���抿����
	 */
	public void setKinkyuKanjiNm(String kinkyuKanjiNm) {
		this.kinkyuKanjiNm = kinkyuKanjiNm;
	}

	/**
	 * �ً}�A���摱���敪���擾���܂��B
	 *
	 * @return �ً}�A���摱���敪
	 */
	public String getKinkyuTsudukiKbn() {
		return kinkyuTsudukiKbn;
	}

	/**
	 * �ً}�A���摱���敪��ݒ肵�܂��B
	 *
	 * @param kinkyuTsudukiKbn �ً}�A���摱���敪
	 */
	public void setKinkyuTsudukiKbn(String kinkyuTsudukiKbn) {
		this.kinkyuTsudukiKbn = kinkyuTsudukiKbn;
	}

	/**
	 * �ً}�A����s�d�k���擾���܂��B
	 *
	 * @return �ً}�A����s�d�k
	 */
	public String getKinkyuTel() {
		return kinkyuTel;
	}

	/**
	 * �ً}�A����s�d�k��ݒ肵�܂��B
	 *
	 * @param kinkyuTel �ً}�A����s�d�k
	 */
	public void setKinkyuTel(String kinkyuTel) {
		this.kinkyuTel = kinkyuTel;
	}

	/**
	 * �ً}�A����Z�����擾���܂��B
	 *
	 * @return �ً}�A����Z��
	 */
	public String getKinkyuJusho() {
		return kinkyuJusho;
	}

	/**
	 * �ً}�A����Z����ݒ肵�܂��B
	 *
	 * @param kinkyuJusho �ً}�A����Z��
	 */
	public void setKinkyuJusho(String kinkyuJusho) {
		this.kinkyuJusho = kinkyuJusho;
	}

	/**
	 * �����l�J�i���P���擾���܂��B
	 *
	 * @return �����l�J�i���P
	 */
	public String getDokyoKanaNm1() {
		return dokyoKanaNm1;
	}

	/**
	 * �����l�J�i���P��ݒ肵�܂��B
	 *
	 * @param dokyoKanaNm1 �����l�J�i���P
	 */
	public void setDokyoKanaNm1(String dokyoKanaNm1) {
		this.dokyoKanaNm1 = dokyoKanaNm1;
	}

	/**
	 * �����l�������P���擾���܂��B
	 *
	 * @return �����l�������P
	 */
	public String getDokyoKanjiNm1() {
		return dokyoKanjiNm1;
	}

	/**
	 * �����l�������P��ݒ肵�܂��B
	 *
	 * @param dokyoKanjiNm1 �����l�������P
	 */
	public void setDokyoKanjiNm1(String dokyoKanjiNm1) {
		this.dokyoKanjiNm1 = dokyoKanjiNm1;
	}

	/**
	 * �����l�����敪�P���擾���܂��B
	 *
	 * @return �����l�����敪�P
	 */
	public String getDokyoTsudukiKbn1() {
		return dokyoTsudukiKbn1;
	}

	/**
	 * �����l�����敪�P��ݒ肵�܂��B
	 *
	 * @param dokyoTsudukiKbn1 �����l�����敪�P
	 */
	public void setDokyoTsudukiKbn1(String dokyoTsudukiKbn1) {
		this.dokyoTsudukiKbn1 = dokyoTsudukiKbn1;
	}

	/**
	 * �����l�A����s�d�k�P���擾���܂��B
	 *
	 * @return �����l�A����s�d�k�P
	 */
	public String getDokyoTel1() {
		return dokyoTel1;
	}

	/**
	 * �����l�A����s�d�k�P��ݒ肵�܂��B
	 *
	 * @param dokyoTel1 �����l�A����s�d�k�P
	 */
	public void setDokyoTel1(String dokyoTel1) {
		this.dokyoTel1 = dokyoTel1;
	}

	/**
	 * �����l�A����Z���P���擾���܂��B
	 *
	 * @return �����l�A����Z���P
	 */
	public String getDokyoJusho1() {
		return dokyoJusho1;
	}

	/**
	 * �����l�A����Z���P��ݒ肵�܂��B
	 *
	 * @param dokyoJusho1 �����l�A����Z���P
	 */
	public void setDokyoJusho1(String dokyoJusho1) {
		this.dokyoJusho1 = dokyoJusho1;
	}

	/**
	 * �����l�J�i���Q���擾���܂��B
	 *
	 * @return �����l�J�i���Q
	 */
	public String getDokyoKanaNm2() {
		return dokyoKanaNm2;
	}

	/**
	 * �����l�J�i���Q��ݒ肵�܂��B
	 *
	 * @param dokyoKanaNm2 �����l�J�i���Q
	 */
	public void setDokyoKanaNm2(String dokyoKanaNm2) {
		this.dokyoKanaNm2 = dokyoKanaNm2;
	}

	/**
	 * �����l�������Q���擾���܂��B
	 *
	 * @return �����l�������Q
	 */
	public String getDokyoKanjiNm2() {
		return dokyoKanjiNm2;
	}

	/**
	 * �����l�������Q��ݒ肵�܂��B
	 *
	 * @param dokyoKanjiNm2 �����l�������Q
	 */
	public void setDokyoKanjiNm2(String dokyoKanjiNm2) {
		this.dokyoKanjiNm2 = dokyoKanjiNm2;
	}

	/**
	 * �����l�����敪�Q���擾���܂��B
	 *
	 * @return �����l�����敪�Q
	 */
	public String getDokyoTsudukiKbn2() {
		return dokyoTsudukiKbn2;
	}

	/**
	 * �����l�����敪�Q��ݒ肵�܂��B
	 *
	 * @param dokyoTsudukiKbn2 �����l�����敪�Q
	 */
	public void setDokyoTsudukiKbn2(String dokyoTsudukiKbn2) {
		this.dokyoTsudukiKbn2 = dokyoTsudukiKbn2;
	}

	/**
	 * �����l�A����s�d�k�Q���擾���܂��B
	 *
	 * @return �����l�A����s�d�k�Q
	 */
	public String getDokyoTel2() {
		return dokyoTel2;
	}

	/**
	 * �����l�A����s�d�k�Q��ݒ肵�܂��B
	 *
	 * @param dokyoTel2 �����l�A����s�d�k�Q
	 */
	public void setDokyoTel2(String dokyoTel2) {
		this.dokyoTel2 = dokyoTel2;
	}

	/**
	 * �����l�A����Z���Q���擾���܂��B
	 *
	 * @return �����l�A����Z���Q
	 */
	public String getDokyoJusho2() {
		return dokyoJusho2;
	}

	/**
	 * �����l�A����Z���Q��ݒ肵�܂��B
	 *
	 * @param dokyoJusho2 �����l�A����Z���Q
	 */
	public void setDokyoJusho2(String dokyoJusho2) {
		this.dokyoJusho2 = dokyoJusho2;
	}

	/**
	 * �Ǘ���ЃJ�i�����擾���܂��B
	 *
	 * @return �Ǘ���ЃJ�i��
	 */
	public String getKanriKanaNm() {
		return kanriKanaNm;
	}

	/**
	 * �Ǘ���ЃJ�i����ݒ肵�܂��B
	 *
	 * @param kanriKanaNm �Ǘ���ЃJ�i��
	 */
	public void setKanriKanaNm(String kanriKanaNm) {
		this.kanriKanaNm = kanriKanaNm;
	}

	/**
	 * �Ǘ���Њ��������擾���܂��B
	 *
	 * @return �Ǘ���Њ�����
	 */
	public String getKanriKanjiNm() {
		return kanriKanjiNm;
	}

	/**
	 * �Ǘ���Њ�������ݒ肵�܂��B
	 *
	 * @param kanriKanjiNm �Ǘ���Њ�����
	 */
	public void setKanriKanjiNm(String kanriKanjiNm) {
		this.kanriKanjiNm = kanriKanjiNm;
	}

	/**
	 * �Ǘ���Ђs�d�k���擾���܂��B
	 *
	 * @return �Ǘ���Ђs�d�k
	 */
	public String getKanriTel() {
		return kanriTel;
	}

	/**
	 * �Ǘ���Ђs�d�k��ݒ肵�܂��B
	 *
	 * @param kanriTel �Ǘ���Ђs�d�k
	 */
	public void setKanriTel(String kanriTel) {
		this.kanriTel = kanriTel;
	}

	/**
	 * CSV�J������i�L�[�j���擾���܂��B
	 *
	 * @return CSV�J������i�L�[�j
	 */
	public String[] getCsvColumnsKey() {
		return CSV_COLUMNS_KEY;
	}

	/**
	 * CSV�J������i���O�j���擾���܂��B
	 *
	 * @return CSV�J������i���O�j
	 */
	public String[] getCsvColumnsName() {
		return CSV_COLUMNS_NAME;
	}

	/**
	 * �ڋq�敪���u4�F�����ҁE�l�v���ǂ����`�F�b�N���܂��B
	 *
	 * @return true�F�ڋq�敪���u4�F�����ҁE�l�v
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="4�܂��́u4�F�����ҁE�l�v���w�肵�ĉ������B")
	private boolean isKokyakuKbn() {
		return kokyakuKbn.equals(KOKYAKU_KBN_NYUKYOSHA) || kokyakuKbn.equals(RcpMKokyaku.KOKYAKU_KBN_NYUKYOSHA);
	}

	/**
	 * �ڋq��ʁi�l/�@�l�j���u0�F�l�v���ǂ����`�F�b�N���܂��B
	 *
	 * @return true�F�ڋq��ʁi�l/�@�l�j���u0�F�l�v
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="0�܂��́u0�F�l�v���w�肵�ĉ������B")
	private boolean isKokyakuShubetsu() {
		return kokyakuShubetsu.equals(KOKYAKU_SHUBETSU_KOJIN) || kokyakuShubetsu.equals(RcpMKokyaku.KOKYAKU_SHUBETU_KOJIN);
	}

	/**
	 * �T�[�r�X�敪���u2�F���C�t�v���ǂ����`�F�b�N���܂��B
	 *
	 * @return true�F�T�[�r�X�敪���u2�F���C�t�v
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="2�܂��́u2�F���C�t�v���w�肵�ĉ������B")
	private boolean isServiceKbn() {
		return serviceKbn.equals(SERVICE_KBN_LIFE) || serviceKbn.equals(RcpMService.SERVICE_KBN_LIFE_SUPPORT24);
	}

}
