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
 * �Ǘ����A�b�v���[�h(����)DTO�B
 *
 * @author v140546
 * @version 1.0 2014/08/04
 * @version 1.1 2015/10/28 J.Matsuba ���ڒǉ��ɂ��C���B
 * @version 1.2 2015/11/10 J.Matsuba ���ڒǉ��E�폜�ɂ��C���B
 * @version 1.3 2016/02/12 H.Yamamura �����ԍ��̓��̓`�F�b�N��S�p�������S�p�����ɕύX
 */
public class TB043CustomerUploadForRealEstateDto extends TB043CSVUploadCommonDto{

	/** ���ڐ� */
	public static final int ITEM_COUNT = 139;

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

	/** CSV�J������i���O�j */
	public static final String[] CSV_COLUMNS_NAME = new String[] {
		"�ڋq�敪",
		"�ڋq��ʁi�l/�@�l�j",
		"�J�i������",
		"�i���߂����ꍇ�̃G���A�j",
		"������",
		"�i���߂����ꍇ�̃G���A�j",
		"�X�֔ԍ�",
		"�Z���P�@�s���{��",
		"�Z���Q�@�s�撬��",
		"�Z���R�@��/�厚",
		"�Z���S�@�Ԓn",
		"������",
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
		"�\��",
		"�K��",
		"�z�N��",
		"�ː�",
		"�S���Җ��P",
		"�S���Җ��Q",
		"�Ǘ��`��",
		"�A����P",
		"�A����Q",
		"�|���v������",
		"�I�[�g���b�N����",
		"���[���a�n�w",
		"���l",
		"�I�[�i�[��",
		"�I�[�i�[�d�b�ԍ�",
		"�I�[�i�[�Z��",
		"�I�[�i�[���l",
		"���p���Ǘ�",
		"���p���Ǘ��c�Ɠ�",
		"���p���Ǘ��c�Ǝ���",
		"���p���Ǘ��s�d�k",
		"���p���Ǘ����ԊO�s�d�k",
		"���p���Ǘ�E-mail",
		"���p���Ǘ����l",
		"�Ǘ��l��",
		"�Ǘ��l�c�Ɠ�",
		"�Ǘ��l�c�Ǝ���",
		"�Ǘ��l�s�d�k",
		"�Ǘ��lE-mail",
		"�Ǘ��l���l",
		"�x�����",
		"�x����Ђs�d�k",
		"�x����Ђe�`�w",
		"�x�����E-mail",
		"�x����Д��l",
		"�����Ǝ҇@",
		"�����Ǝ҇@�s�d�k",
		"�����Ǝ҇@�e�`�w",
		"�����Ǝ҇@E-mail",
		"�����Ǝ҇@���l",
		"���r���Ǝ҇A�|���v",
		"���r���Ǝ҇A�|���v�s�d�k",
		"���r���Ǝ҇A�|���v�e�`�w",
		"���r���Ǝ҇A�|���vE-mail",
		"���r���Ǝ҇A�|���v���l",
		"�K�X���",
		"�K�X��Ђs�d�k",
		"�K�X��Ђe�`�w",
		"�K�X���E-mail",
		"�K�X��Д��l",
		"������ێ�Ǝ�",
		"������ێ�Ǝ҂s�d�k",
		"������ێ�Ǝ҂e�`�w",
		"������ێ�Ǝ�E-mail",
		"������ێ�ƎҔ��l",
		"�G�A�R���ێ�Ǝ�",
		"�G�A�R���ێ�Ǝ҂s�d�k",
		"�G�A�R���ێ�Ǝ҂e�`�w",
		"�G�A�R���ێ�Ǝ�E-mail",
		"�G�A�R���ێ�ƎҔ��l",
		"�d�C�ێ�Ǝ�",
		"�d�C�ێ�Ǝ҂s�d�k",
		"�d�C�ێ�Ǝ҂e�`�w",
		"�d�C�ێ�Ǝ�E-mail",
		"�d�C�ێ�ƎҔ��l",
		"�d�u���",
		"�d�u��Ђs�d�k",
		"�d�u��Ђe�`�w",
		"�d�u���E-mail",
		"�d�u��Д��l",
		"���Ǝ�",
		"���Ǝ҂s�d�k",
		"���Ǝ҂e�`�w",
		"���Ǝ�E-mail",
		"���ƎҔ��l",
		"���h�ێ�",
		"���h�ێ�s�d�k",
		"���h�ێ�e�`�w",
		"���h�ێ�E-mail",
		"���h�ێ���l",
		"�b�`�s�u���",
		"�b�`�s�u��Ђs�d�k",
		"�b�`�s�u��Ђe�`�w",
		"�b�`�s�u���E-mail",
		"�b�`�s�u��Д��l",
		"���C�U���",
		"���C�U��Ђs�d�k",
		"���C�U��Ђe�`�w",
		"���C�U���E-mail",
		"���C�U��Д��l",
		"�K���X�Ǝ�",
		"�K���X�Ǝ҂s�d�k",
		"�K���X�Ǝ҂e�`�w",
		"�K���X�Ǝ�E-mail",
		"�K���X�ƎҔ��l",
		"���̑��P",
		"���̑��P�s�d�k",
		"���̑��P�e�`�w",
		"���̑��PE-mail",
		"���̑��P���l",
		"���̑��Q",
		"���̑��Q�s�d�k",
		"���̑��Q�e�`�w",
		"���̑��QE-mail",
		"���̑��Q���l",
		"���̑��R",
		"���̑��R�s�d�k",
		"���̑��R�e�`�w",
		"���̑��RE-mail",
		"���̑��R���l"
	};

	/** �ڋq�敪�F�u3�F�����v */
	private static final String KOKYAKU_KBN_BUKKEN = "3�F����";

	/** �ڋq��ʁF�u1�F�@�l�v */
	private static final String KOKYAKU_SHUBETSU_HOJIN = "1�F�@�l";

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
	 * �J�i������
	 */
	@NotEmpty
	@KanaName
	@MaxLength(max=40)
	private String kanaNm1;

	/**
	 * �i���߂����ꍇ�̃G���A�j
	 */
	@KanaName
	@MaxLength(max=40)
	private String kanaNm2;

	/**
	 * ����������
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=40)
	private String kanjiNm1;

	/**
	 * �i���߂����ꍇ�̃G���A�j
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
	 * ������
	 */
	@NotEmpty
	@Zenkaku
	@MaxLength(max=40)
	private String jusho5;

	/**
	 * �����ԍ�
	 */
	@Zenkaku
	@MaxLength(max=20)
	private String roomNo;

	/**
	 * �d�b�ԍ��P
	 */
	private String telNo1;

	/**
	 * �d�b�ԍ��Q
	 */
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
	 * �\��
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kozo;

	/**
	 * �K��
	 */
	@Num
	@MaxLength(max=3)
	private String kaisu;

	/**
	 * �z�N��
	 */
	@Time(pattern="yyyyMM")
	@MaxLength(max=6)
	private String chikuNengetsu;

	/**
	 * �ː�
	 */
	@Num
	@MaxLength(max=5)
	private String kosu;

	/**
	 * �S���Җ��P
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String tantoshaNm1;

	/**
	 * �S���Җ��Q
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String tantoshaNm2;

	/**
	 * �Ǘ��`�ԋ敪
	 */
	@MaxLength(max=15)
	private String kanriKeitaiKbn;

	/**
	 * �A����P
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String renrakusaki1;

	/**
	 * �A����Q
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String renrakusaki2;

	/**
	 * �|���v������
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String pompMemo;

	/**
	 * �I�[�g���b�N����
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String autoLockMemo;

	/**
	 * ���[���a�n�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String mailBox;

	/**
	 * �Ǘ��`�Ԕ��l
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String biko;

	/**
	 * �I�[�i�[��
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String ooyaNm;

	/**
	 * �I�[�i�[�d�b�ԍ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String ooyaTel;

	/**
	 * �I�[�i�[�Z��
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String ooyaJusho;

	/**
	 * �I�[�i�[���l
	 */
	@Zenhankaku
	@MaxLength(max=100)
	private String ooyaBiko;

	/**
	 * ���p���Ǘ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriNm;

	/**
	 * ���p���Ǘ��c�Ɠ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriEigyobi;

	/**
	 * ���p���Ǘ��c�Ǝ���
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriEigyoJikan;

	/**
	 * ���p���Ǘ��s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriTel;

	/**
	 * ���p���Ǘ����ԊO�s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyoyoKanriJikangaiTel;

	/**
	 * ���p���Ǘ�E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kyoyoKanriMailAddress;

	/**
	 * ���p���Ǘ����l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kyoyoKanriBiko;

	/**
	 * �Ǘ��l��
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininNm;

	/**
	 * �Ǘ��l�c�Ɠ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininEigyobi;

	/**
	 * �Ǘ��l�c�Ǝ���
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininEigyoJikan;

	/**
	 * �Ǘ��l���s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kanrininTel;

	/**
	 * �Ǘ��lE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kanrininMailAddress;

	/**
	 * �Ǘ��l���l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kanrininBiko;

	/**
	 * �x����Ж�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String keibiGaisha;

	/**
	 * �x����Ђs�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String keibiGaishaTel;

	/**
	 * �x����Ђe�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String keibiGaishaFax;

	/**
	 * �x�����E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String keibiGaishaMailAddress;

	/**
	 * �x����Д��l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String keibiGaishaBiko;

	/**
	 * �����Ǝ҇@
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String suidoGyosha;

	/**
	 * �����Ǝ҇@�s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String suidoGyoshaTel;

	/**
	 * �����Ǝ҇@�e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String suidoGyoshaFax;

	/**
	 * �����Ǝ҇@E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String suidoGyoshaMailAddress;

	/**
	 * �����Ǝ҇@���l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String suidoGyoshaBiko;

	/**
	 * ���r���Ǝ҇A
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyuHaisuiGyosha;

	/**
	 * ���r���Ǝ҇A�s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyuHaisuiGyoshaTel;

	/**
	 * ���r���Ǝ҇A�e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyuHaisuiGyoshaFax;

	/**
	 * ���r���Ǝ҇AE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kyuHaisuiGyoshaMailAddress;

	/**
	 * ���r���Ǝ҇A���l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kyuHaisuiGyoshaBiko;

	/**
	 * �K�X��Ж�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String gasGaisha;

	/**
	 * �K�X��Ж��s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String gasGaishaTel;

	/**
	 * �K�X��Ж��e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String gasGaishaFax;

	/**
	 * �K�X��Ж�E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String gasGaishaMailAddress;

	/**
	 * �K�X��Ж����l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String gasGaishaBiko;

	/**
	 * ������ێ�ƎҖ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyutokiHoshuGyosha;

	/**
	 * ������ێ�Ǝ҂s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyutokiHoshuGyoshaTel;

	/**
	 * ������ێ�Ǝ҂e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kyutokiHoshuGyoshaFax;

	/**
	 * ������ێ�Ǝ�E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kyutokiHoshuGyoshaMailAddress;

	/**
	 * ������ێ�ƎҔ��l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kyutokiHoshuGyoshaBiko;

	/**
	 * �G�A�R���ێ�ƎҖ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String airConHoshuGyosha;

	/**
	 * �G�A�R���ێ�Ǝ҂s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String airConHoshuGyoshaTel;

	/**
	 * �G�A�R���ێ�Ǝ҂e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String airConHoshuGyoshaFax;

	/**
	 * �G�A�R���ێ�Ǝ�E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String airConHoshuGyoshaMailAddress;

	/**
	 * �G�A�R���ێ�ƎҔ��l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String airConHoshuGyoshaBiko;

	/**
	 * �d�C�ێ�ƎҖ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String denkiHoshuGyosha;

	/**
	 * �d�C�ێ�Ǝ҂s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String denkiHoshuGyoshaTel;

	/**
	 * �d�C�ێ�Ǝ҂e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String denkiHoshuGyoshaFax;

	/**
	 * �d�C�ێ�Ǝ�E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String denkiHoshuGyoshaMailAddress;

	/**
	 * �d�C�ێ�ƎҔ��l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String denkiHoshuGyoshaBiko;

	/**
	 * �d�u��Ж�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String evGaisha;

	/**
	 * �d�u��Ж��s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String evGaishaTel;

	/**
	 * �d�u��Ж��e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String evGaishaFax;

	/**
	 * �d�u��Ж�E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String evGaishaMailAddress;

	/**
	 * �d�u��Ж����l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String evGaishaBiko;

	/**
	 * ���ƎҖ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kagiGyosha;

	/**
	 * ���ƎҖ��s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kagiGyoshaTel;

	/**
	 * ���ƎҖ��e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String kagiGyoshaFax;

	/**
	 * ���ƎҖ�E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String kagiGyoshaMailAddress;

	/**
	 * ���ƎҖ����l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String kagiGyoshaBiko;

	/**
	 * ���h�ێ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoboHoshu;

	/**
	 * ���h�ێ�s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoboHoshuTel;

	/**
	 * ���h�ێ�e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoboHoshuFax;

	/**
	 * ���h�ێ�E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String shoboHoshuMailAddress;

	/**
	 * ���h�ێ���l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String shoboHoshuBiko;

	/**
	 * �b�`�s�u��Ж�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String catvGaisha;

	/**
	 * �b�`�s�u��Ж��s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String catvGaishaTel;

	/**
	 * �b�`�s�u��Ж��e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String catvGaishaFax;

	/**
	 * �b�`�s�u��Ж�E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String catvGaishaMailAddress;

	/**
	 * �b�`�s�u��Ж����l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String catvGaishaBiko;

	/**
	 * ���C�U���
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoshuZen;

	/**
	 * ���C�U��Ђs�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoshuZenTel;

	/**
	 * ���C�U��Ђe�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String shoshuZenFax;

	/**
	 * ���C�U���E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String shoshuZenMailAddress;

	/**
	 * ���C�U��Д��l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String shoshuZenBiko;

	/**
	 * �K���X�Ǝ�
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String glassGyosha;

	/**
	 * �K���X�Ǝ҂s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String glassGyoshaTel;

	/**
	 * �K���X�Ǝ҂e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String glassGyoshaFax;

	/**
	 * �K���X�Ǝ�E-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String glassGyoshaMailAddress;

	/**
	 * �K���X�ƎҔ��l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String glassGyoshaBiko;

	/**
	 * ���̑��P
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc1;

	/**
	 * ���̑��P�s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc1Tel;

	/**
	 * ���̑��P�e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc1Fax;

	/**
	 * ���̑��PE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String etc1MailAddress;

	/**
	 * ���̑��P���l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String etc1Biko;

	/**
	 * ���̑��Q
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc2;

	/**
	 * ���̑��Q�s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc2Tel;

	/**
	 * ���̑��Q�e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc2Fax;

	/**
	 * ���̑��QE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String etc2MailAddress;

	/**
	 * ���̑��Q���l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String etc2Biko;

	/**
	 * ���̑��R
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc3;

	/**
	 * ���̑��R�s�d�k
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc3Tel;

	/**
	 * ���̑��R�e�`�w
	 */
	@Zenhankaku
	@MaxLength(max=50)
	private String etc3Fax;

	/**
	 * ���̑��RE-mail
	 */
	@MailAddress
	@MaxLength(max=255)
	private String etc3MailAddress;

	/**
	 * ���̑��R���l
	 */
	@Zenhankaku
	@MaxLength(max=200)
	private String etc3Biko;

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
	 * �J�i���������擾���܂��B
	 *
	 * @return �J�i������
	 */
	public String getKanaNm1() {
		return kanaNm1;
	}
	/**
	 * �J�i��������ݒ肵�܂��B
	 *
	 * @param kanaNm1 �J�i������
	 */
	public void setKanaNm1(String kanaNm1) {
		this.kanaNm1 = kanaNm1;
	}

	/**
	 * �i���߂����ꍇ�̃G���A�j���擾���܂��B
	 *
	 * @return �i���߂����ꍇ�̃G���A�j
	 */
	public String getKanaNm2() {
		return kanaNm2;
	}
	/**
	 * �i���߂����ꍇ�̃G���A�j��ݒ肵�܂��B
	 *
	 * @param kanaNm2 �i���߂����ꍇ�̃G���A�j
	 */
	public void setKanaNm2(String kanaNm2) {
		this.kanaNm2 = kanaNm2;
	}

	/**
	 * ���������擾���܂��B
	 *
	 * @return ������
	 */
	public String getKanjiNm1() {
		return kanjiNm1;
	}
	/**
	 * ��������ݒ肵�܂��B
	 *
	 * @param kanjiNm1 ������
	 */
	public void setKanjiNm1(String kanjiNm1) {
		this.kanjiNm1 = kanjiNm1;
	}

	/**
	 * �i���߂����ꍇ�̃G���A�j���擾���܂��B
	 *
	 * @return �i���߂����ꍇ�̃G���A�j
	 */
	public String getKanjiNm2() {
		return kanjiNm2;
	}
	/**
	 * �i���߂����ꍇ�̃G���A�j��ݒ肵�܂��B
	 *
	 * @param kanjiNm2 �i���߂����ꍇ�̃G���A�j
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
	 * ���������擾���܂��B
	 *
	 * @return ������
	 */
	public String getJusho5() {
		return jusho5;
	}
	/**
	 * ��������ݒ肵�܂��B
	 *
	 * @param jusho5 ������
	 */
	public void setJusho5(String jusho5) {
		this.jusho5 = jusho5;
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
	 * �\�����擾���܂��B
	 *
	 * @return �\��
	 */
	public String getKozo() {
		return kozo;
	}
	/**
	 * �\����ݒ肵�܂��B
	 *
	 * @param kozo �\��
	 */
	public void setKozo(String kozo) {
		this.kozo = kozo;
	}

	/**
	 * �K�����擾���܂��B
	 *
	 * @return �K��
	 */
	public String getKaisu() {
		return kaisu;
	}
	/**
	 * �K����ݒ肵�܂��B
	 *
	 * @param kaisu �K��
	 */
	public void setKaisu(String kaisu) {
		this.kaisu = kaisu;
	}

	/**
	 * �z�N�����擾���܂��B
	 *
	 * @return �z�N��
	 */
	public String getChikuNengetsu() {
		return chikuNengetsu;
	}
	/**
	 * �z�N����ݒ肵�܂��B
	 *
	 * @param chikuNengetsu �z�N��
	 */
	public void setChikuNengetsu(String chikuNengetsu) {
		this.chikuNengetsu = chikuNengetsu;
	}

	/**
	 * �ː����擾���܂��B
	 *
	 * @return �ː�
	 */
	public String getKosu() {
		return kosu;
	}
	/**
	 * �ː���ݒ肵�܂��B
	 *
	 * @param kosu �ː�
	 */
	public void setKosu(String kosu) {
		this.kosu = kosu;
	}

	/**
	 * �S���Җ��P���擾���܂��B
	 *
	 * @return �S���Җ��P
	 */
	public String getTantoshaNm1() {
		return tantoshaNm1;
	}
	/**
	 * �S���Җ��P��ݒ肵�܂��B
	 *
	 * @param tantoshaNm1 �S���Җ��P
	 */
	public void setTantoshaNm1(String tantoshaNm1) {
		this.tantoshaNm1 = tantoshaNm1;
	}

	/**
	 * �S���Җ��Q���擾���܂��B
	 *
	 * @return �S���Җ��Q
	 */
	public String getTantoshaNm2() {
		return tantoshaNm2;
	}
	/**
	 * �S���Җ��Q��ݒ肵�܂��B
	 *
	 * @param tantoshaNm2 �S���Җ��Q
	 */
	public void setTantoshaNm2(String tantoshaNm2) {
		this.tantoshaNm2 = tantoshaNm2;
	}

	/**
	 * �Ǘ��`�ԋ敪���擾���܂��B
	 *
	 * @return �Ǘ��`�ԋ敪
	 */
	public String getKanriKeitaiKbn() {
		return kanriKeitaiKbn;
	}
	/**
	 * �Ǘ��`�ԋ敪��ݒ肵�܂��B
	 *
	 * @param kanriKeitaiKbn �Ǘ��`�ԋ敪
	 */
	public void setKanriKeitaiKbn(String kanriKeitaiKbn) {
		this.kanriKeitaiKbn = kanriKeitaiKbn;
	}

	/**
	 * �A����P���擾���܂��B
	 *
	 * @return �A����
	 */
	public String getRenrakusaki1() {
		return renrakusaki1;
	}
	/**
	 * �A����P��ݒ肵�܂��B
	 *
	 * @param renrakusaki1 �A����P
	 */
	public void setRenrakusaki1(String renrakusaki1) {
		this.renrakusaki1 = renrakusaki1;
	}

	/**
	 * �A����Q���擾���܂��B
	 *
	 * @return �A����
	 */
	public String getRenrakusaki2() {
		return renrakusaki2;
	}
	/**
	 * �A����Q��ݒ肵�܂��B
	 *
	 * @param renrakusaki2 �A����Q
	 */
	public void setRenrakusaki2(String renrakusaki2) {
		this.renrakusaki2 = renrakusaki2;
	}

	/**
	 * �|���v���������擾���܂��B
	 *
	 * @return �|���v������
	 */
	public String getPompMemo() {
		return pompMemo;
	}
	/**
	 * �|���v��������ݒ肵�܂��B
	 *
	 * @param pompMemo �|���v������
	 */
	public void setPompMemo(String pompMemo) {
		this.pompMemo = pompMemo;
	}

	/**
	 * �I�[�g���b�N�������擾���܂��B
	 *
	 * @return �I�[�g���b�N����
	 */
	public String getAutoLockMemo() {
		return autoLockMemo;
	}
	/**
	 * �I�[�g���b�N������ݒ肵�܂��B
	 *
	 * @param autoLockMemo �I�[�g���b�N����
	 */
	public void setAutoLockMemo(String autoLockMemo) {
		this.autoLockMemo = autoLockMemo;
	}

	/**
	 * ���[���a�n�w���擾���܂��B
	 * @return ���[���a�n�w
	 */
	public String getMailBox() {
		return mailBox;
	}

	/**
	 * ���[���a�n�w��ݒ肵�܂��B
	 * @param mailBox ���[���a�n�w
	 */
	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}

	/**
	 * �Ǘ��`�Ԕ��l���擾���܂��B
	 *
	 * @return �Ǘ��`�Ԕ��l
	 */
	public String getBiko() {
		return biko;
	}
	/**
	 * �Ǘ��`�Ԕ��l��ݒ肵�܂��B
	 *
	 * @param biko �Ǘ��`�Ԕ��l
	 */
	public void setBiko(String biko) {
		this.biko = biko;
	}

	/**
	 * �I�[�i�[�����擾���܂��B
	 *
	 * @return �I�[�i�[��
	 */
	public String getOoyaNm() {
		return ooyaNm;
	}
	/**
	 * �I�[�i�[����ݒ肵�܂��B
	 *
	 * @param ooyaNm �I�[�i�[��
	 */
	public void setOoyaNm(String ooyaNm) {
		this.ooyaNm = ooyaNm;
	}

	/**
	 * �I�[�i�[�d�b�ԍ����擾���܂��B
	 *
	 * @return �I�[�i�[�d�b�ԍ�
	 */
	public String getOoyaTel() {
		return ooyaTel;
	}
	/**
	 * �I�[�i�[�d�b�ԍ���ݒ肵�܂��B
	 *
	 * @param ooyaTel �I�[�i�[�d�b�ԍ�
	 */
	public void setOoyaTel(String ooyaTel) {
		this.ooyaTel = ooyaTel;
	}

	/**
	 * �I�[�i�[�Z�����擾���܂��B
	 *
	 * @return �I�[�i�[�Z��
	 */
	public String getOoyaJusho() {
		return ooyaJusho;
	}
	/**
	 * �I�[�i�[�Z����ݒ肵�܂��B
	 *
	 * @param ooyaJusho �I�[�i�[�Z��
	 */
	public void setOoyaJusho(String ooyaJusho) {
		this.ooyaJusho = ooyaJusho;
	}

	/**
	 * �I�[�i�[���l���擾���܂��B
	 *
	 * @return �I�[�i�[���l
	 */
	public String getOoyaBiko() {
		return ooyaBiko;
	}
	/**
	 * �I�[�i�[���l��ݒ肵�܂��B
	 *
	 * @param ooyaBiko �I�[�i�[���l
	 */
	public void setOoyaBiko(String ooyaBiko) {
		this.ooyaBiko = ooyaBiko;
	}

	/**
	 * ���p���Ǘ����擾���܂��B
	 * 
	 * @return ���p���Ǘ�
	 */
	public String getKyoyoKanriNm() {
		return kyoyoKanriNm;
	}

	/**
	 * ���p���Ǘ���ݒ肵�܂��B
	 * 
	 * @param kyoyoKanriNm ���p���Ǘ�
	 */
	public void setKyoyoKanriNm(String kyoyoKanriNm) {
		this.kyoyoKanriNm = kyoyoKanriNm;
	}

	/**
	 * ���p���Ǘ��c�Ɠ����擾���܂��B
	 * 
	 * @return ���p���Ǘ��c�Ɠ�
	 */
	public String getKyoyoKanriEigyobi() {
		return kyoyoKanriEigyobi;
	}

	/**
	 * ���p���Ǘ��c�Ɠ���ݒ肵�܂��B
	 * 
	 * @param kyoyoKanriEigyobi ���p���Ǘ��c�Ɠ�
	 */
	public void setKyoyoKanriEigyobi(String kyoyoKanriEigyobi) {
		this.kyoyoKanriEigyobi = kyoyoKanriEigyobi;
	}

	/**
	 * ���p���Ǘ��c�Ǝ��Ԃ��擾���܂��B
	 * 
	 * @return ���p���Ǘ��c�Ǝ���
	 */
	public String getKyoyoKanriEigyoJikan() {
		return kyoyoKanriEigyoJikan;
	}

	/**
	 * ���p���Ǘ��c�Ǝ��Ԃ�ݒ肵�܂��B
	 * 
	 * @param kyoyoKanriEigyoJikan ���p���Ǘ��c�Ǝ���
	 */
	public void setKyoyoKanriEigyoJikan(String kyoyoKanriEigyoJikan) {
		this.kyoyoKanriEigyoJikan = kyoyoKanriEigyoJikan;
	}

	/**
	 * ���p���Ǘ��s�d�k���擾���܂��B
	 * 
	 * @return ���p���Ǘ��s�d�k
	 */
	public String getKyoyoKanriTel() {
		return kyoyoKanriTel;
	}

	/**
	 * ���p���Ǘ��s�d�k��ݒ肵�܂��B
	 * 
	 * @param kyoyoKanriTel ���p���Ǘ��s�d�k
	 */
	public void setKyoyoKanriTel(String kyoyoKanriTel) {
		this.kyoyoKanriTel = kyoyoKanriTel;
	}

	/**
	 * ���p���Ǘ����ԊO�s�d�k���擾���܂��B
	 * 
	 * @return ���p���Ǘ����ԊO�s�d�k
	 */
	public String getKyoyoKanriJikangaiTel() {
		return kyoyoKanriJikangaiTel;
	}

	/**
	 * ���p���Ǘ����ԊO�s�d�k��ݒ肵�܂��B
	 * 
	 * @param kyoyoKanriJikangaiTel ���p���Ǘ����ԊO�s�d�k
	 */
	public void setKyoyoKanriJikangaiTel(String kyoyoKanriJikangaiTel) {
		this.kyoyoKanriJikangaiTel = kyoyoKanriJikangaiTel;
	}

	/**
	 * ���p���Ǘ�E-mail���擾���܂��B
	 * 
	 * @return ���p���Ǘ�E-mail
	 */
	public String getKyoyoKanriMailAddress() {
		return kyoyoKanriMailAddress;
	}

	/**
	 * ���p���Ǘ�E-mail��ݒ肵�܂��B
	 * 
	 * @param kyoyoKanriMailAddress ���p���Ǘ�E-mail
	 */
	public void setKyoyoKanriMailAddress(String kyoyoKanriMailAddress) {
		this.kyoyoKanriMailAddress = kyoyoKanriMailAddress;
	}

	/**
	 * ���p���Ǘ����l���擾���܂��B
	 * 
	 * @return ���p���Ǘ����l
	 */
	public String getKyoyoKanriBiko() {
		return kyoyoKanriBiko;
	}

	/**
	 * ���p���Ǘ����l��ݒ肵�܂��B
	 * 
	 * @param kyoyoKanriBiko ���p���Ǘ����l
	 */
	public void setKyoyoKanriBiko(String kyoyoKanriBiko) {
		this.kyoyoKanriBiko = kyoyoKanriBiko;
	}

	/**
	 * �Ǘ��l�����擾���܂��B
	 * 
	 * @return �Ǘ��l��
	 */
	public String getKanrininNm() {
		return kanrininNm;
	}

	/**
	 * �Ǘ��l����ݒ肵�܂��B
	 * 
	 * @param kanrininNm �Ǘ��l��
	 */
	public void setKanrininNm(String kanrininNm) {
		this.kanrininNm = kanrininNm;
	}

	/**
	 * �Ǘ��l�c�Ɠ����擾���܂��B
	 * 
	 * @return �Ǘ��l�c�Ɠ�
	 */
	public String getKanrininEigyobi() {
		return kanrininEigyobi;
	}

	/**
	 * �Ǘ��l�c�Ɠ���ݒ肵�܂��B
	 * 
	 * @param kanrininEigyobi �Ǘ��l�c�Ɠ�
	 */
	public void setKanrininEigyobi(String kanrininEigyobi) {
		this.kanrininEigyobi = kanrininEigyobi;
	}

	/**
	 * �Ǘ��l�c�Ǝ��Ԃ��擾���܂��B
	 * 
	 * @return �Ǘ��l�c�Ǝ���
	 */
	public String getKanrininEigyoJikan() {
		return kanrininEigyoJikan;
	}

	/**
	 * �Ǘ��l�c�Ǝ��Ԃ�ݒ肵�܂��B
	 * 
	 * @param kanrininEigyoJikan �Ǘ��l�c�Ǝ���
	 */
	public void setKanrininEigyoJikan(String kanrininEigyoJikan) {
		this.kanrininEigyoJikan = kanrininEigyoJikan;
	}

	/**
	 * �Ǘ��l���s�d�k���擾���܂��B
	 * 
	 * @return �Ǘ��l���s�d�k
	 */
	public String getKanrininTel() {
		return kanrininTel;
	}

	/**
	 * �Ǘ��l���s�d�k��ݒ肵�܂��B
	 * 
	 * @param kanrininTel �Ǘ��l���s�d�k
	 */
	public void setKanrininTel(String kanrininTel) {
		this.kanrininTel = kanrininTel;
	}

	/**
	 * �Ǘ��lE-mail���擾���܂��B
	 * 
	 * @return �Ǘ��lE-mail
	 */
	public String getKanrininMailAddress() {
		return kanrininMailAddress;
	}

	/**
	 * �Ǘ��lE-mail��ݒ肵�܂��B
	 * 
	 * @param kanrininMailAddress �Ǘ��lE-mail
	 */
	public void setKanrininMailAddress(String kanrininMailAddress) {
		this.kanrininMailAddress = kanrininMailAddress;
	}

	/**
	 * �Ǘ��l���l���擾���܂��B
	 * 
	 * @return �Ǘ��l���l
	 */
	public String getKanrininBiko() {
		return kanrininBiko;
	}

	/**
	 * �Ǘ��l���l��ݒ肵�܂��B
	 * 
	 * @param kanrininBiko �Ǘ��l���l
	 */
	public void setKanrininBiko(String kanrininBiko) {
		this.kanrininBiko = kanrininBiko;
	}

	/**
	 * �x����Ж����擾���܂��B
	 * 
	 * @return �x����Ж�
	 */
	public String getKeibiGaisha() {
		return keibiGaisha;
	}

	/**
	 * �x����Ж���ݒ肵�܂��B
	 * 
	 * @param keibiGaisha �x����Ж�
	 */
	public void setKeibiGaisha(String keibiGaisha) {
		this.keibiGaisha = keibiGaisha;
	}

	/**
	 * �x����Ђs�d�k���擾���܂��B
	 * 
	 * @return �x����Ђs�d�k
	 */
	public String getKeibiGaishaTel() {
		return keibiGaishaTel;
	}

	/**
	 * �x����Ђs�d�k��ݒ肵�܂��B
	 * 
	 * @param keibiGaishaTel �x����Ђs�d�k
	 */
	public void setKeibiGaishaTel(String keibiGaishaTel) {
		this.keibiGaishaTel = keibiGaishaTel;
	}

	/**
	 * �x����Ђe�`�w���擾���܂��B
	 * 
	 * @return �x����Ђe�`�w
	 */
	public String getKeibiGaishaFax() {
		return keibiGaishaFax;
	}

	/**
	 * �x����Ђe�`�w��ݒ肵�܂��B
	 * 
	 * @param keibiGaishaFax �x����Ђe�`�w
	 */
	public void setKeibiGaishaFax(String keibiGaishaFax) {
		this.keibiGaishaFax = keibiGaishaFax;
	}

	/**
	 * �x�����E-mail���擾���܂��B
	 * 
	 * @return �x�����E-mail
	 */
	public String getKeibiGaishaMailAddress() {
		return keibiGaishaMailAddress;
	}

	/**
	 * �x�����E-mail��ݒ肵�܂��B
	 * 
	 * @param keibiGaishaMailAddress �x�����E-mail
	 */
	public void setKeibiGaishaMailAddress(String keibiGaishaMailAddress) {
		this.keibiGaishaMailAddress = keibiGaishaMailAddress;
	}

	/**
	 * �x����Д��l���擾���܂��B
	 * 
	 * @return �x����Д��l
	 */
	public String getKeibiGaishaBiko() {
		return keibiGaishaBiko;
	}

	/**
	 * �x����Д��l��ݒ肵�܂��B
	 * 
	 * @param keibiGaishaBiko �x����Д��l
	 */
	public void setKeibiGaishaBiko(String keibiGaishaBiko) {
		this.keibiGaishaBiko = keibiGaishaBiko;
	}

	/**
	 * �����Ǝ҇@���擾���܂��B
	 * 
	 * @return �����Ǝ҇@
	 */
	public String getSuidoGyosha() {
		return suidoGyosha;
	}

	/**
	 * �����Ǝ҇@��ݒ肵�܂��B
	 * 
	 * @param suidoGyosha �����Ǝ҇@
	 */
	public void setSuidoGyosha(String suidoGyosha) {
		this.suidoGyosha = suidoGyosha;
	}

	/**
	 * �����Ǝ҇@�s�d�k���擾���܂��B
	 * 
	 * @return �����Ǝ҇@�s�d�k
	 */
	public String getSuidoGyoshaTel() {
		return suidoGyoshaTel;
	}

	/**
	 * �����Ǝ҇@�s�d�k��ݒ肵�܂��B
	 * 
	 * @param suidoGyoshaTel �����Ǝ҇@�s�d�k
	 */
	public void setSuidoGyoshaTel(String suidoGyoshaTel) {
		this.suidoGyoshaTel = suidoGyoshaTel;
	}

	/**
	 * �����Ǝ҇@�e�`�w���擾���܂��B
	 * 
	 * @return �����Ǝ҇@�e�`�w
	 */
	public String getSuidoGyoshaFax() {
		return suidoGyoshaFax;
	}

	/**
	 * �����Ǝ҇@�e�`�w��ݒ肵�܂��B
	 * 
	 * @param suidoGyoshaFax �����Ǝ҇@�e�`�w
	 */
	public void setSuidoGyoshaFax(String suidoGyoshaFax) {
		this.suidoGyoshaFax = suidoGyoshaFax;
	}

	/**
	 * �����Ǝ҇@E-mail���擾���܂��B
	 * 
	 * @return �����Ǝ҇@E-mail
	 */
	public String getSuidoGyoshaMailAddress() {
		return suidoGyoshaMailAddress;
	}

	/**
	 * �����Ǝ҇@E-mail��ݒ肵�܂��B
	 * 
	 * @param suidoGyoshaMailAddress �����Ǝ҇@E-mail
	 */
	public void setSuidoGyoshaMailAddress(String suidoGyoshaMailAddress) {
		this.suidoGyoshaMailAddress = suidoGyoshaMailAddress;
	}

	/**
	 * �����Ǝ҇@���l���擾���܂��B
	 * 
	 * @return �����Ǝ҇@���l
	 */
	public String getSuidoGyoshaBiko() {
		return suidoGyoshaBiko;
	}

	/**
	 * �����Ǝ҇@���l��ݒ肵�܂��B
	 * 
	 * @param suidoGyoshaBiko �����Ǝ҇@���l
	 */
	public void setSuidoGyoshaBiko(String suidoGyoshaBiko) {
		this.suidoGyoshaBiko = suidoGyoshaBiko;
	}

	/**
	 * ���r���Ǝ҇A���擾���܂��B
	 * 
	 * @return ���r���Ǝ҇A
	 */
	public String getKyuHaisuiGyosha() {
		return kyuHaisuiGyosha;
	}

	/**
	 * ���r���Ǝ҇A��ݒ肵�܂��B
	 * 
	 * @param kyuHaisuiGyosha ���r���Ǝ҇A
	 */
	public void setKyuHaisuiGyosha(String kyuHaisuiGyosha) {
		this.kyuHaisuiGyosha = kyuHaisuiGyosha;
	}

	/**
	 * ���r���Ǝ҇A�s�d�k���擾���܂��B
	 * 
	 * @return ���r���Ǝ҇A�s�d�k
	 */
	public String getKyuHaisuiGyoshaTel() {
		return kyuHaisuiGyoshaTel;
	}

	/**
	 * ���r���Ǝ҇A�s�d�k��ݒ肵�܂��B
	 * 
	 * @param kyuHaisuiGyoshaTel ���r���Ǝ҇A�s�d�k
	 */
	public void setKyuHaisuiGyoshaTel(String kyuHaisuiGyoshaTel) {
		this.kyuHaisuiGyoshaTel = kyuHaisuiGyoshaTel;
	}

	/**
	 * ���r���Ǝ҇A�e�`�w���擾���܂��B
	 * 
	 * @return ���r���Ǝ҇A�e�`�w
	 */
	public String getKyuHaisuiGyoshaFax() {
		return kyuHaisuiGyoshaFax;
	}

	/**
	 * ���r���Ǝ҇A�e�`�w��ݒ肵�܂��B
	 * 
	 * @param kyuHaisuiGyoshaFax ���r���Ǝ҇A�e�`�w
	 */
	public void setKyuHaisuiGyoshaFax(String kyuHaisuiGyoshaFax) {
		this.kyuHaisuiGyoshaFax = kyuHaisuiGyoshaFax;
	}

	/**
	 * ���r���Ǝ҇AE-mail���擾���܂��B
	 * 
	 * @return ���r���Ǝ҇AE-mail
	 */
	public String getKyuHaisuiGyoshaMailAddress() {
		return kyuHaisuiGyoshaMailAddress;
	}

	/**
	 * ���r���Ǝ҇AE-mail��ݒ肵�܂��B
	 * 
	 * @param kyuHaisuiGyoshaMailAddress ���r���Ǝ҇AE-mail
	 */
	public void setKyuHaisuiGyoshaMailAddress(String kyuHaisuiGyoshaMailAddress) {
		this.kyuHaisuiGyoshaMailAddress = kyuHaisuiGyoshaMailAddress;
	}

	/**
	 * ���r���Ǝ҇A���l���擾���܂��B
	 * 
	 * @return ���r���Ǝ҇A���l
	 */
	public String getKyuHaisuiGyoshaBiko() {
		return kyuHaisuiGyoshaBiko;
	}

	/**
	 * ���r���Ǝ҇A���l��ݒ肵�܂��B
	 * 
	 * @param kyuHaisuiGyoshaBiko ���r���Ǝ҇A���l
	 */
	public void setKyuHaisuiGyoshaBiko(String kyuHaisuiGyoshaBiko) {
		this.kyuHaisuiGyoshaBiko = kyuHaisuiGyoshaBiko;
	}

	/**
	 * �K�X��Ж����擾���܂��B
	 * 
	 * @return �K�X��Ж�
	 */
	public String getGasGaisha() {
		return gasGaisha;
	}

	/**
	 * �K�X��Ж���ݒ肵�܂��B
	 * 
	 * @param gasGaisha �K�X��Ж�
	 */
	public void setGasGaisha(String gasGaisha) {
		this.gasGaisha = gasGaisha;
	}

	/**
	 * �K�X��Ж��s�d�k���擾���܂��B
	 * 
	 * @return �K�X��Ж��s�d�k
	 */
	public String getGasGaishaTel() {
		return gasGaishaTel;
	}

	/**
	 * �K�X��Ж��s�d�k��ݒ肵�܂��B
	 * 
	 * @param gasGaishaTel �K�X��Ж��s�d�k
	 */
	public void setGasGaishaTel(String gasGaishaTel) {
		this.gasGaishaTel = gasGaishaTel;
	}

	/**
	 * �K�X��Ж��e�`�w���擾���܂��B
	 * 
	 * @return �K�X��Ж��e�`�w
	 */
	public String getGasGaishaFax() {
		return gasGaishaFax;
	}

	/**
	 * �K�X��Ж��e�`�w��ݒ肵�܂��B
	 * 
	 * @param gasGaishaFax �K�X��Ж��e�`�w
	 */
	public void setGasGaishaFax(String gasGaishaFax) {
		this.gasGaishaFax = gasGaishaFax;
	}

	/**
	 * �K�X��Ж�E-mail���擾���܂��B
	 * 
	 * @return �K�X��Ж�E-mail
	 */
	public String getGasGaishaMailAddress() {
		return gasGaishaMailAddress;
	}

	/**
	 * �K�X��Ж�E-mail��ݒ肵�܂��B
	 * 
	 * @param gasGaishaMailAddress �K�X��Ж�E-mail
	 */
	public void setGasGaishaMailAddress(String gasGaishaMailAddress) {
		this.gasGaishaMailAddress = gasGaishaMailAddress;
	}

	/**
	 * �K�X��Ж����l���擾���܂��B
	 * 
	 * @return �K�X��Ж����l
	 */
	public String getGasGaishaBiko() {
		return gasGaishaBiko;
	}

	/**
	 * �K�X��Ж����l��ݒ肵�܂��B
	 * 
	 * @param gasGaishaBiko �K�X��Ж����l
	 */
	public void setGasGaishaBiko(String gasGaishaBiko) {
		this.gasGaishaBiko = gasGaishaBiko;
	}

	/**
	 * ������ێ�ƎҖ����擾���܂��B
	 * 
	 * @return ������ێ�ƎҖ�
	 */
	public String getKyutokiHoshuGyosha() {
		return kyutokiHoshuGyosha;
	}

	/**
	 * ������ێ�ƎҖ���ݒ肵�܂��B
	 * 
	 * @param kyutokiHoshuGyosha ������ێ�ƎҖ�
	 */
	public void setKyutokiHoshuGyosha(String kyutokiHoshuGyosha) {
		this.kyutokiHoshuGyosha = kyutokiHoshuGyosha;
	}

	/**
	 * ������ێ�Ǝ҂s�d�k���擾���܂��B
	 * 
	 * @return ������ێ�Ǝ҂s�d�k
	 */
	public String getKyutokiHoshuGyoshaTel() {
		return kyutokiHoshuGyoshaTel;
	}

	/**
	 * ������ێ�Ǝ҂s�d�k��ݒ肵�܂��B
	 * 
	 * @param kyutokiHoshuGyoshaTel ������ێ�Ǝ҂s�d�k
	 */
	public void setKyutokiHoshuGyoshaTel(String kyutokiHoshuGyoshaTel) {
		this.kyutokiHoshuGyoshaTel = kyutokiHoshuGyoshaTel;
	}

	/**
	 * ������ێ�Ǝ҂e�`�w���擾���܂��B
	 * 
	 * @return ������ێ�Ǝ҂e�`�w
	 */
	public String getKyutokiHoshuGyoshaFax() {
		return kyutokiHoshuGyoshaFax;
	}

	/**
	 * ������ێ�Ǝ҂e�`�w��ݒ肵�܂��B
	 * 
	 * @param kyutokiHoshuGyoshaFax ������ێ�Ǝ҂e�`�w
	 */
	public void setKyutokiHoshuGyoshaFax(String kyutokiHoshuGyoshaFax) {
		this.kyutokiHoshuGyoshaFax = kyutokiHoshuGyoshaFax;
	}

	/**
	 * ������ێ�Ǝ�E-mail���擾���܂��B
	 * 
	 * @return ������ێ�Ǝ�E-mail
	 */
	public String getKyutokiHoshuGyoshaMailAddress() {
		return kyutokiHoshuGyoshaMailAddress;
	}

	/**
	 * ������ێ�Ǝ�E-mail��ݒ肵�܂��B
	 * 
	 * @param kyutokiHoshuGyoshaMailAddress ������ێ�Ǝ�E-mail
	 */
	public void setKyutokiHoshuGyoshaMailAddress(String kyutokiHoshuGyoshaMailAddress) {
		this.kyutokiHoshuGyoshaMailAddress = kyutokiHoshuGyoshaMailAddress;
	}

	/**
	 * ������ێ�ƎҔ��l���擾���܂��B
	 * 
	 * @return ������ێ�ƎҔ��l
	 */
	public String getKyutokiHoshuGyoshaBiko() {
		return kyutokiHoshuGyoshaBiko;
	}

	/**
	 * ������ێ�ƎҔ��l��ݒ肵�܂��B
	 * 
	 * @param kyutokiHoshuGyoshaBiko ������ێ�ƎҔ��l
	 */
	public void setKyutokiHoshuGyoshaBiko(String kyutokiHoshuGyoshaBiko) {
		this.kyutokiHoshuGyoshaBiko = kyutokiHoshuGyoshaBiko;
	}

	/**
	 * �G�A�R���ێ�ƎҖ����擾���܂��B
	 * 
	 * @return �G�A�R���ێ�ƎҖ�
	 */
	public String getAirConHoshuGyosha() {
		return airConHoshuGyosha;
	}

	/**
	 * �G�A�R���ێ�ƎҖ���ݒ肵�܂��B
	 * 
	 * @param airConHoshuGyosha �G�A�R���ێ�ƎҖ�
	 */
	public void setAirConHoshuGyosha(String airConHoshuGyosha) {
		this.airConHoshuGyosha = airConHoshuGyosha;
	}

	/**
	 * �G�A�R���ێ�Ǝ҂s�d�k���擾���܂��B
	 * 
	 * @return �G�A�R���ێ�Ǝ҂s�d�k
	 */
	public String getAirConHoshuGyoshaTel() {
		return airConHoshuGyoshaTel;
	}

	/**
	 * �G�A�R���ێ�Ǝ҂s�d�k��ݒ肵�܂��B
	 * 
	 * @param airConHoshuGyoshaTel �G�A�R���ێ�Ǝ҂s�d�k
	 */
	public void setAirConHoshuGyoshaTel(String airConHoshuGyoshaTel) {
		this.airConHoshuGyoshaTel = airConHoshuGyoshaTel;
	}

	/**
	 * �G�A�R���ێ�Ǝ҂e�`�w���擾���܂��B
	 * 
	 * @return �G�A�R���ێ�Ǝ҂e�`�w
	 */
	public String getAirConHoshuGyoshaFax() {
		return airConHoshuGyoshaFax;
	}

	/**
	 * �G�A�R���ێ�Ǝ҂e�`�w��ݒ肵�܂��B
	 * 
	 * @param airConHoshuGyoshaFax �G�A�R���ێ�Ǝ҂e�`�w
	 */
	public void setAirConHoshuGyoshaFax(String airConHoshuGyoshaFax) {
		this.airConHoshuGyoshaFax = airConHoshuGyoshaFax;
	}

	/**
	 * �G�A�R���ێ�Ǝ�E-mail���擾���܂��B
	 * 
	 * @return �G�A�R���ێ�Ǝ�E-mail
	 */
	public String getAirConHoshuGyoshaMailAddress() {
		return airConHoshuGyoshaMailAddress;
	}

	/**
	 * �G�A�R���ێ�Ǝ�E-mail��ݒ肵�܂��B
	 * 
	 * @param airConHoshuGyoshaMailAddress �G�A�R���ێ�Ǝ�E-mail
	 */
	public void setAirConHoshuGyoshaMailAddress(String airConHoshuGyoshaMailAddress) {
		this.airConHoshuGyoshaMailAddress = airConHoshuGyoshaMailAddress;
	}

	/**
	 * �G�A�R���ێ�ƎҔ��l���擾���܂��B
	 * 
	 * @return �G�A�R���ێ�ƎҔ��l
	 */
	public String getAirConHoshuGyoshaBiko() {
		return airConHoshuGyoshaBiko;
	}

	/**
	 * �G�A�R���ێ�ƎҔ��l��ݒ肵�܂��B
	 * 
	 * @param airConHoshuGyoshaBiko �G�A�R���ێ�ƎҔ��l
	 */
	public void setAirConHoshuGyoshaBiko(String airConHoshuGyoshaBiko) {
		this.airConHoshuGyoshaBiko = airConHoshuGyoshaBiko;
	}

	/**
	 * �d�C�ێ�ƎҖ����擾���܂��B
	 * 
	 * @return �d�C�ێ�ƎҖ�
	 */
	public String getDenkiHoshuGyosha() {
		return denkiHoshuGyosha;
	}

	/**
	 * �d�C�ێ�ƎҖ���ݒ肵�܂��B
	 * 
	 * @param denkiHoshuGyosha �d�C�ێ�ƎҖ�
	 */
	public void setDenkiHoshuGyosha(String denkiHoshuGyosha) {
		this.denkiHoshuGyosha = denkiHoshuGyosha;
	}

	/**
	 * �d�C�ێ�Ǝ҂s�d�k���擾���܂��B
	 * 
	 * @return �d�C�ێ�Ǝ҂s�d�k
	 */
	public String getDenkiHoshuGyoshaTel() {
		return denkiHoshuGyoshaTel;
	}

	/**
	 * �d�C�ێ�Ǝ҂s�d�k��ݒ肵�܂��B
	 * 
	 * @param denkiHoshuGyoshaTel �d�C�ێ�Ǝ҂s�d�k
	 */
	public void setDenkiHoshuGyoshaTel(String denkiHoshuGyoshaTel) {
		this.denkiHoshuGyoshaTel = denkiHoshuGyoshaTel;
	}

	/**
	 * �d�C�ێ�Ǝ҂e�`�w���擾���܂��B
	 * 
	 * @return �d�C�ێ�Ǝ҂e�`�w
	 */
	public String getDenkiHoshuGyoshaFax() {
		return denkiHoshuGyoshaFax;
	}

	/**
	 * �d�C�ێ�Ǝ҂e�`�w��ݒ肵�܂��B
	 * 
	 * @param denkiHoshuGyoshaFax �d�C�ێ�Ǝ҂e�`�w
	 */
	public void setDenkiHoshuGyoshaFax(String denkiHoshuGyoshaFax) {
		this.denkiHoshuGyoshaFax = denkiHoshuGyoshaFax;
	}

	/**
	 * �d�C�ێ�Ǝ�E-mail���擾���܂��B
	 * 
	 * @return �d�C�ێ�Ǝ�E-mail
	 */
	public String getDenkiHoshuGyoshaMailAddress() {
		return denkiHoshuGyoshaMailAddress;
	}

	/**
	 * �d�C�ێ�Ǝ�E-mail��ݒ肵�܂��B
	 * 
	 * @param denkiHoshuGyoshaMailAddress �d�C�ێ�Ǝ�E-mail
	 */
	public void setDenkiHoshuGyoshaMailAddress(String denkiHoshuGyoshaMailAddress) {
		this.denkiHoshuGyoshaMailAddress = denkiHoshuGyoshaMailAddress;
	}

	/**
	 * �d�C�ێ�ƎҔ��l���擾���܂��B
	 * 
	 * @return �d�C�ێ�ƎҔ��l
	 */
	public String getDenkiHoshuGyoshaBiko() {
		return denkiHoshuGyoshaBiko;
	}

	/**
	 * �d�C�ێ�ƎҔ��l��ݒ肵�܂��B
	 * 
	 * @param denkiHoshuGyoshaBiko �d�C�ێ�ƎҔ��l
	 */
	public void setDenkiHoshuGyoshaBiko(String denkiHoshuGyoshaBiko) {
		this.denkiHoshuGyoshaBiko = denkiHoshuGyoshaBiko;
	}

	/**
	 * �d�u��Ж����擾���܂��B
	 * 
	 * @return �d�u��Ж�
	 */
	public String getEvGaisha() {
		return evGaisha;
	}

	/**
	 * �d�u��Ж���ݒ肵�܂��B
	 * 
	 * @param evGaisha �d�u��Ж�
	 */
	public void setEvGaisha(String evGaisha) {
		this.evGaisha = evGaisha;
	}

	/**
	 * �d�u��Ж��s�d�k���擾���܂��B
	 * 
	 * @return �d�u��Ж��s�d�k
	 */
	public String getEvGaishaTel() {
		return evGaishaTel;
	}

	/**
	 * �d�u��Ж��s�d�k��ݒ肵�܂��B
	 * 
	 * @param evGaishaTel �d�u��Ж��s�d�k
	 */
	public void setEvGaishaTel(String evGaishaTel) {
		this.evGaishaTel = evGaishaTel;
	}

	/**
	 * �d�u��Ж��e�`�w���擾���܂��B
	 * 
	 * @return �d�u��Ж��e�`�w
	 */
	public String getEvGaishaFax() {
		return evGaishaFax;
	}

	/**
	 * �d�u��Ж��e�`�w��ݒ肵�܂��B
	 * 
	 * @param evGaishaFax �d�u��Ж��e�`�w
	 */
	public void setEvGaishaFax(String evGaishaFax) {
		this.evGaishaFax = evGaishaFax;
	}

	/**
	 * �d�u��Ж�E-mail���擾���܂��B
	 * 
	 * @return �d�u��Ж�E-mail
	 */
	public String getEvGaishaMailAddress() {
		return evGaishaMailAddress;
	}

	/**
	 * �d�u��Ж�E-mail��ݒ肵�܂��B
	 * 
	 * @param evGaishaMailAddress �d�u��Ж�E-mail
	 */
	public void setEvGaishaMailAddress(String evGaishaMailAddress) {
		this.evGaishaMailAddress = evGaishaMailAddress;
	}

	/**
	 * �d�u��Ж����l���擾���܂��B
	 * 
	 * @return �d�u��Ж����l
	 */
	public String getEvGaishaBiko() {
		return evGaishaBiko;
	}

	/**
	 * �d�u��Ж����l��ݒ肵�܂��B
	 * 
	 * @param evGaishaBiko �d�u��Ж����l
	 */
	public void setEvGaishaBiko(String evGaishaBiko) {
		this.evGaishaBiko = evGaishaBiko;
	}

	/**
	 * ���ƎҖ����擾���܂��B
	 * 
	 * @return ���ƎҖ�
	 */
	public String getKagiGyosha() {
		return kagiGyosha;
	}

	/**
	 * ���ƎҖ���ݒ肵�܂��B
	 * 
	 * @param kagiGyosha ���ƎҖ�
	 */
	public void setKagiGyosha(String kagiGyosha) {
		this.kagiGyosha = kagiGyosha;
	}

	/**
	 * ���ƎҖ��s�d�k���擾���܂��B
	 * 
	 * @return ���ƎҖ��s�d�k
	 */
	public String getKagiGyoshaTel() {
		return kagiGyoshaTel;
	}

	/**
	 * ���ƎҖ��s�d�k��ݒ肵�܂��B
	 * 
	 * @param kagiGyoshaTel ���ƎҖ��s�d�k
	 */
	public void setKagiGyoshaTel(String kagiGyoshaTel) {
		this.kagiGyoshaTel = kagiGyoshaTel;
	}

	/**
	 * ���ƎҖ��e�`�w���擾���܂��B
	 * 
	 * @return ���ƎҖ��e�`�w
	 */
	public String getKagiGyoshaFax() {
		return kagiGyoshaFax;
	}

	/**
	 * ���ƎҖ��e�`�w��ݒ肵�܂��B
	 * 
	 * @param kagiGyoshaFax ���ƎҖ��e�`�w
	 */
	public void setKagiGyoshaFax(String kagiGyoshaFax) {
		this.kagiGyoshaFax = kagiGyoshaFax;
	}

	/**
	 * ���ƎҖ�E-mail���擾���܂��B
	 * 
	 * @return ���ƎҖ�E-mail
	 */
	public String getKagiGyoshaMailAddress() {
		return kagiGyoshaMailAddress;
	}

	/**
	 * ���ƎҖ�E-mail��ݒ肵�܂��B
	 * 
	 * @param kagiGyoshaMailAddress ���ƎҖ�E-mail
	 */
	public void setKagiGyoshaMailAddress(String kagiGyoshaMailAddress) {
		this.kagiGyoshaMailAddress = kagiGyoshaMailAddress;
	}

	/**
	 * ���ƎҖ����l���擾���܂��B
	 * 
	 * @return ���ƎҖ����l
	 */
	public String getKagiGyoshaBiko() {
		return kagiGyoshaBiko;
	}

	/**
	 * ���ƎҖ����l��ݒ肵�܂��B
	 * 
	 * @param kagiGyoshaBiko ���ƎҖ����l
	 */
	public void setKagiGyoshaBiko(String kagiGyoshaBiko) {
		this.kagiGyoshaBiko = kagiGyoshaBiko;
	}

	/**
	 * ���h�ێ���擾���܂��B
	 * 
	 * @return ���h�ێ�
	 */
	public String getShoboHoshu() {
		return shoboHoshu;
	}

	/**
	 * ���h�ێ��ݒ肵�܂��B
	 * 
	 * @param shoboHoshu ���h�ێ�
	 */
	public void setShoboHoshu(String shoboHoshu) {
		this.shoboHoshu = shoboHoshu;
	}

	/**
	 * ���h�ێ�s�d�k���擾���܂��B
	 * 
	 * @return ���h�ێ�s�d�k
	 */
	public String getShoboHoshuTel() {
		return shoboHoshuTel;
	}

	/**
	 * ���h�ێ�s�d�k��ݒ肵�܂��B
	 * 
	 * @param shoboHoshuTel ���h�ێ�s�d�k
	 */
	public void setShoboHoshuTel(String shoboHoshuTel) {
		this.shoboHoshuTel = shoboHoshuTel;
	}

	/**
	 * ���h�ێ�e�`�w���擾���܂��B
	 * 
	 * @return ���h�ێ�e�`�w
	 */
	public String getShoboHoshuFax() {
		return shoboHoshuFax;
	}

	/**
	 * ���h�ێ�e�`�w��ݒ肵�܂��B
	 * 
	 * @param shoboHoshuFax ���h�ێ�e�`�w
	 */
	public void setShoboHoshuFax(String shoboHoshuFax) {
		this.shoboHoshuFax = shoboHoshuFax;
	}

	/**
	 * ���h�ێ�E-mail���擾���܂��B
	 * 
	 * @return ���h�ێ�E-mail
	 */
	public String getShoboHoshuMailAddress() {
		return shoboHoshuMailAddress;
	}

	/**
	 * ���h�ێ�E-mail��ݒ肵�܂��B
	 * 
	 * @param shoboHoshuMailAddress ���h�ێ�E-mail
	 */
	public void setShoboHoshuMailAddress(String shoboHoshuMailAddress) {
		this.shoboHoshuMailAddress = shoboHoshuMailAddress;
	}

	/**
	 * ���h�ێ���l���擾���܂��B
	 * 
	 * @return ���h�ێ���l
	 */
	public String getShoboHoshuBiko() {
		return shoboHoshuBiko;
	}

	/**
	 * ���h�ێ���l��ݒ肵�܂��B
	 * 
	 * @param shoboHoshuBiko ���h�ێ���l
	 */
	public void setShoboHoshuBiko(String shoboHoshuBiko) {
		this.shoboHoshuBiko = shoboHoshuBiko;
	}

	/**
	 * �b�`�s�u��Ж����擾���܂��B
	 * 
	 * @return CATV���
	 */
	public String getCatvGaisha() {
		return catvGaisha;
	}

	/**
	 * �b�`�s�u��Ж���ݒ肵�܂��B
	 * 
	 * @param catvGaisha �b�`�s�u��Ж�
	 */
	public void setCatvGaisha(String catvGaisha) {
		this.catvGaisha = catvGaisha;
	}

	/**
	 * �b�`�s�u��Ж��s�d�k���擾���܂��B
	 * 
	 * @return �b�`�s�u��Ж��s�d�k
	 */
	public String getCatvGaishaTel() {
		return catvGaishaTel;
	}

	/**
	 * �b�`�s�u��Ж��s�d�k��ݒ肵�܂��B
	 * 
	 * @param catvGaishaTel �b�`�s�u��Ж��s�d�k
	 */
	public void setCatvGaishaTel(String catvGaishaTel) {
		this.catvGaishaTel = catvGaishaTel;
	}

	/**
	 * �b�`�s�u��Ж��e�`�w���擾���܂��B
	 * 
	 * @return �b�`�s�u��Ж��e�`�w
	 */
	public String getCatvGaishaFax() {
		return catvGaishaFax;
	}

	/**
	 * �b�`�s�u��Ж��e�`�w��ݒ肵�܂��B
	 * 
	 * @param catvGaishaFax �b�`�s�u��Ж��e�`�w
	 */
	public void setCatvGaishaFax(String catvGaishaFax) {
		this.catvGaishaFax = catvGaishaFax;
	}

	/**
	 * �b�`�s�u��Ж�E-mail���擾���܂��B
	 * 
	 * @return �b�`�s�u��Ж�E-mail
	 */
	public String getCatvGaishaMailAddress() {
		return catvGaishaMailAddress;
	}

	/**
	 * �b�`�s�u��Ж�E-mail��ݒ肵�܂��B
	 * 
	 * @param catvGaishaMailAddress �b�`�s�u��Ж�E-mail
	 */
	public void setCatvGaishaMailAddress(String catvGaishaMailAddress) {
		this.catvGaishaMailAddress = catvGaishaMailAddress;
	}

	/**
	 * �b�`�s�u��Ж����l���擾���܂��B
	 * 
	 * @return �b�`�s�u��Ж����l
	 */
	public String getCatvGaishaBiko() {
		return catvGaishaBiko;
	}

	/**
	 * �b�`�s�u��Ж����l��ݒ肵�܂��B
	 * 
	 * @param catvGaishaBiko �b�`�s�u��Ж����l
	 */
	public void setCatvGaishaBiko(String catvGaishaBiko) {
		this.catvGaishaBiko = catvGaishaBiko;
	}

	/**
	 * ���C�U��Ђ��擾���܂��B
	 * 
	 * @return ���C�U���
	 */
	public String getShoshuZen() {
		return shoshuZen;
	}

	/**
	 * ���C�U��Ђ�ݒ肵�܂��B
	 * 
	 * @param shoshuZen ���C�U���
	 */
	public void setShoshuZen(String shoshuZen) {
		this.shoshuZen = shoshuZen;
	}

	/**
	 * ���C�U��Ђs�d�k���擾���܂��B
	 * 
	 * @return ���C�U��Ђs�d�k
	 */
	public String getShoshuZenTel() {
		return shoshuZenTel;
	}

	/**
	 * ���C�U��Ђs�d�k��ݒ肵�܂��B
	 * 
	 * @param shoshuZenTel ���C�U��Ђs�d�k
	 */
	public void setShoshuZenTel(String shoshuZenTel) {
		this.shoshuZenTel = shoshuZenTel;
	}

	/**
	 * ���C�U��Ђe�`�w���擾���܂��B
	 * 
	 * @return ���C�U��Ђe�`�w
	 */
	public String getShoshuZenFax() {
		return shoshuZenFax;
	}

	/**
	 * ���C�U��Ђe�`�w��ݒ肵�܂��B
	 * 
	 * @param shoshuZenFax ���C�U��Ђe�`�w
	 */
	public void setShoshuZenFax(String shoshuZenFax) {
		this.shoshuZenFax = shoshuZenFax;
	}

	/**
	 * ���C�U���E-mail���擾���܂��B
	 * 
	 * @return ���C�U���E-mail
	 */
	public String getShoshuZenMailAddress() {
		return shoshuZenMailAddress;
	}

	/**
	 * ���C�U���E-mail��ݒ肵�܂��B
	 * 
	 * @param shoshuZenMailAddress ���C�U���E-mail
	 */
	public void setShoshuZenMailAddress(String shoshuZenMailAddress) {
		this.shoshuZenMailAddress = shoshuZenMailAddress;
	}

	/**
	 * ���C�U��Д��l���擾���܂��B
	 * 
	 * @return ���C�U��Д��l
	 */
	public String getShoshuZenBiko() {
		return shoshuZenBiko;
	}

	/**
	 * ���C�U��Д��l��ݒ肵�܂��B
	 * 
	 * @param shoshuZenBiko ���C�U��Д��l
	 */
	public void setShoshuZenBiko(String shoshuZenBiko) {
		this.shoshuZenBiko = shoshuZenBiko;
	}

	/**
	 * �K���X�Ǝ҂��擾���܂��B
	 * 
	 * @return �K���X�Ǝ�
	 */
	public String getGlassGyosha() {
		return glassGyosha;
	}

	/**
	 * �K���X�Ǝ҂�ݒ肵�܂��B
	 * 
	 * @param glassGyosha �K���X�Ǝ�
	 */
	public void setGlassGyosha(String glassGyosha) {
		this.glassGyosha = glassGyosha;
	}

	/**
	 * �K���X�Ǝ҂s�d�k���擾���܂��B
	 * 
	 * @return �K���X�Ǝ҂s�d�k
	 */
	public String getGlassGyoshaTel() {
		return glassGyoshaTel;
	}

	/**
	 * �K���X�Ǝ҂s�d�k��ݒ肵�܂��B
	 * 
	 * @param glassGyoshaTel �K���X�Ǝ҂s�d�k
	 */
	public void setGlassGyoshaTel(String glassGyoshaTel) {
		this.glassGyoshaTel = glassGyoshaTel;
	}

	/**
	 * �K���X�Ǝ҂e�`�w���擾���܂��B
	 * 
	 * @return �K���X�Ǝ҂e�`�w
	 */
	public String getGlassGyoshaFax() {
		return glassGyoshaFax;
	}

	/**
	 * �K���X�Ǝ҂e�`�w��ݒ肵�܂��B
	 * 
	 * @param glassGyoshaFax �K���X�Ǝ҂e�`�w
	 */
	public void setGlassGyoshaFax(String glassGyoshaFax) {
		this.glassGyoshaFax = glassGyoshaFax;
	}

	/**
	 * �K���X�Ǝ�E-mail���擾���܂��B
	 * 
	 * @return �K���X�Ǝ�E-mail
	 */
	public String getGlassGyoshaMailAddress() {
		return glassGyoshaMailAddress;
	}

	/**
	 * �K���X�Ǝ�E-mail��ݒ肵�܂��B
	 * 
	 * @param glassGyoshaMailAddress �K���X�Ǝ�E-mail
	 */
	public void setGlassGyoshaMailAddress(String glassGyoshaMailAddress) {
		this.glassGyoshaMailAddress = glassGyoshaMailAddress;
	}

	/**
	 * �K���X�ƎҔ��l���擾���܂��B
	 * 
	 * @return �K���X�ƎҔ��l
	 */
	public String getGlassGyoshaBiko() {
		return glassGyoshaBiko;
	}

	/**
	 * �K���X�ƎҔ��l��ݒ肵�܂��B
	 * 
	 * @param glassGyoshaBiko �K���X�ƎҔ��l
	 */
	public void setGlassGyoshaBiko(String glassGyoshaBiko) {
		this.glassGyoshaBiko = glassGyoshaBiko;
	}

	/**
	 * ���̑��P���擾���܂��B
	 * 
	 * @return ���̑��P
	 */
	public String getEtc1() {
		return etc1;
	}

	/**
	 * ���̑��P��ݒ肵�܂��B
	 * 
	 * @param etc1 ���̑��P
	 */
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	/**
	 * ���̑��P�s�d�k���擾���܂��B
	 * 
	 * @return ���̑��P�s�d�k
	 */
	public String getEtc1Tel() {
		return etc1Tel;
	}

	/**
	 * ���̑��P�s�d�k��ݒ肵�܂��B
	 * 
	 * @param etc1Tel ���̑��P�s�d�k
	 */
	public void setEtc1Tel(String etc1Tel) {
		this.etc1Tel = etc1Tel;
	}

	/**
	 * ���̑��P�e�`�w���擾���܂��B
	 * 
	 * @return ���̑��P�e�`�w
	 */
	public String getEtc1Fax() {
		return etc1Fax;
	}

	/**
	 * ���̑��P�e�`�w��ݒ肵�܂��B
	 * 
	 * @param etc1Fax ���̑��P�e�`�w
	 */
	public void setEtc1Fax(String etc1Fax) {
		this.etc1Fax = etc1Fax;
	}

	/**
	 * ���̑��PE-mail���擾���܂��B
	 * 
	 * @return ���̑��PE-mail
	 */
	public String getEtc1MailAddress() {
		return etc1MailAddress;
	}

	/**
	 * ���̑��PE-mail��ݒ肵�܂��B
	 * 
	 * @param etc1MailAddress ���̑��PE-mail
	 */
	public void setEtc1MailAddress(String etc1MailAddress) {
		this.etc1MailAddress = etc1MailAddress;
	}

	/**
	 * ���̑��P���l���擾���܂��B
	 * 
	 * @return ���̑��P���l
	 */
	public String getEtc1Biko() {
		return etc1Biko;
	}

	/**
	 * ���̑��P���l��ݒ肵�܂��B
	 * 
	 * @param etc1Biko ���̑��P���l
	 */
	public void setEtc1Biko(String etc1Biko) {
		this.etc1Biko = etc1Biko;
	}

	/**
	 * ���̑��Q���擾���܂��B
	 * 
	 * @return ���̑��Q
	 */
	public String getEtc2() {
		return etc2;
	}

	/**
	 * ���̑��Q��ݒ肵�܂��B
	 * 
	 * @param etc2 ���̑��Q
	 */
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	/**
	 * ���̑��Q�s�d�k���擾���܂��B
	 * 
	 * @return ���̑��Q�s�d�k
	 */
	public String getEtc2Tel() {
		return etc2Tel;
	}

	/**
	 * ���̑��Q�s�d�k��ݒ肵�܂��B
	 * 
	 * @param etc2Tel ���̑��Q�s�d�k
	 */
	public void setEtc2Tel(String etc2Tel) {
		this.etc2Tel = etc2Tel;
	}

	/**
	 * ���̑��Q�e�`�w���擾���܂��B
	 * 
	 * @return ���̑��Q�e�`�w
	 */
	public String getEtc2Fax() {
		return etc2Fax;
	}

	/**
	 * ���̑��Q�e�`�w��ݒ肵�܂��B
	 * 
	 * @param etc2Fax ���̑��Q�e�`�w
	 */
	public void setEtc2Fax(String etc2Fax) {
		this.etc2Fax = etc2Fax;
	}

	/**
	 * ���̑��QE-mail���擾���܂��B
	 * 
	 * @return ���̑��QE-mail
	 */
	public String getEtc2MailAddress() {
		return etc2MailAddress;
	}

	/**
	 * ���̑��QE-mail��ݒ肵�܂��B
	 * 
	 * @param etc2MailAddress ���̑��QE-mail
	 */
	public void setEtc2MailAddress(String etc2MailAddress) {
		this.etc2MailAddress = etc2MailAddress;
	}

	/**
	 * ���̑��Q���l���擾���܂��B
	 * 
	 * @return ���̑��Q���l
	 */
	public String getEtc2Biko() {
		return etc2Biko;
	}

	/**
	 * ���̑��Q���l��ݒ肵�܂��B
	 * 
	 * @param etc2Biko ���̑��Q���l
	 */
	public void setEtc2Biko(String etc2Biko) {
		this.etc2Biko = etc2Biko;
	}

	/**
	 * ���̑��R���擾���܂��B
	 * 
	 * @return ���̑��R
	 */
	public String getEtc3() {
		return etc3;
	}

	/**
	 * ���̑��R��ݒ肵�܂��B
	 * 
	 * @param etc3 ���̑��R
	 */
	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

	/**
	 * ���̑��R�s�d�k���擾���܂��B
	 * 
	 * @return ���̑��R�s�d�k
	 */
	public String getEtc3Tel() {
		return etc3Tel;
	}

	/**
	 * ���̑��R�s�d�k��ݒ肵�܂��B
	 * 
	 * @param etc3Tel ���̑��R�s�d�k
	 */
	public void setEtc3Tel(String etc3Tel) {
		this.etc3Tel = etc3Tel;
	}

	/**
	 * ���̑��R�e�`�w���擾���܂��B
	 * 
	 * @return ���̑��R�e�`�w
	 */
	public String getEtc3Fax() {
		return etc3Fax;
	}

	/**
	 * ���̑��R�e�`�w��ݒ肵�܂��B
	 * 
	 * @param etc3Fax ���̑��R�e�`�w
	 */
	public void setEtc3Fax(String etc3Fax) {
		this.etc3Fax = etc3Fax;
	}

	/**
	 * ���̑��RE-mail���擾���܂��B
	 * 
	 * @return ���̑��RE-mail
	 */
	public String getEtc3MailAddress() {
		return etc3MailAddress;
	}

	/**
	 * ���̑��RE-mail��ݒ肵�܂��B
	 * 
	 * @param etc3MailAddress ���̑��RE-mail
	 */
	public void setEtc3MailAddress(String etc3MailAddress) {
		this.etc3MailAddress = etc3MailAddress;
	}

	/**
	 * ���̑��R���l���擾���܂��B
	 * 
	 * @return ���̑��R���l
	 */
	public String getEtc3Biko() {
		return etc3Biko;
	}

	/**
	 * ���̑��R���l��ݒ肵�܂��B
	 * 
	 * @param etc3Biko ���̑��R���l
	 */
	public void setEtc3Biko(String etc3Biko) {
		this.etc3Biko = etc3Biko;
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
	 * �ڋq�敪���u3�F�����v���ǂ����`�F�b�N���܂��B
	 *
	 * @return true�F�ڋq�敪���u3�F�����v
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="3�܂��́u3�F�����v���w�肵�ĉ������B")
	private boolean isKokyakuKbn() {
		return kokyakuKbn.equals(KOKYAKU_KBN_BUKKEN) || kokyakuKbn.equals(RcpMKokyaku.KOKYAKU_KBN_BUKKEN);
	}

	/**
	 * �ڋq��ʁi�l/�@�l�j���u1�F�@�l�v���ǂ����`�F�b�N���܂��B
	 *
	 * @return true�F�ڋq��ʁi�l/�@�l�j���u1�F�@�l�v
	 */
	@SuppressWarnings("unused")
	@AssertTrue(message="1�܂��́u1�F�@�l�v���w�肵�ĉ������B")
	private boolean isKokyakuShubetsu() {
		return kokyakuShubetsu.equals(KOKYAKU_SHUBETSU_HOJIN) || kokyakuShubetsu.equals(RcpMKokyaku.KOKYAKU_SHUBETSU_HOJIN);
	}
}
