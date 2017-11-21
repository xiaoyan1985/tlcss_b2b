package jp.co.tokaigroup.tlcss_b2b.common;


/**
 * ���ʂ̒萔�N���X�B
 * �S�̂ŋ��ʂ̒萔���`���܂��B
 * �R�[�h�萔���́A�e�[�u���ɌŗL����l�̓G���e�B�e�B�ɒ萔���`���ĉ������B
 *
 * @author N.Akahori
 * @version 1.0 2012/08/21
 * @version 1.1 2015/10/26 S.Nakano �萔�uJOKYO_GAMEN_KBN_�`�v�ǉ�
 * @version 1.2 2015/11/16 S.Nakano �萔�uACTION_TYPE_RESTORE�v�ǉ�
 */
public class Constants {
	/** �f�t�H���g�̌�����ʂł̍ő匟���\������ */
	public static final int MAX_COUNT  = 300;

	/** 1�y�[�W������̕\������ */
	public static final int LIMIT = 5;

	/** 1�y�[�W�ڂ�\������ */
	public static final int FIRST_PAGE_NO = 1;

	/** �����敪 insert */
	public static final String ACTION_TYPE_INSERT = "insert";

	/** �����敪 update */
	public static final String ACTION_TYPE_UPDATE = "update";

	/** �����敪 delete */
	public static final String ACTION_TYPE_DELETE = "delete";

	/** �����敪 clear */
	public static final String ACTION_TYPE_CLEAR = "clear";

	/** �����敪 restore */
	public static final String ACTION_TYPE_RESTORE = "restore";
	
	/** �A�N�Z�X���O�ɏo�͂��Ȃ����� */
	public static final String[] EXCLUDE_FIELD_NAMES = {"offset", "limit", "maxCount", "count"};

	// �{�^����(�悭�g�p������̂��)
	/** �{�^���� �o�^ */
	public static final String BUTTON_NM_INSERT = "�o�^";
	/** �{�^���� �X�V */
	public static final String BUTTON_NM_UPDATE = "�X�V";
	/** �{�^���� �폜 */
	public static final String BUTTON_NM_DELETE = "�폜";
	/** �{�^���� ���� */
	public static final String BUTTON_NM_RESTORE = "����";
	/** �{�^���� ���� */
	public static final String BUTTON_NM_SEARCH = "����";
	/** �{�^���� �ύX */
	public static final String BUTTON_NM_CHANGE = "�ύX";
	/** �{�^���� ��� */
	public static final String BUTTON_NM_PRINT = "���";

	/** �󋵋敪 ��ʋ敪 01:�₢���킹�o�^ */
	public static final String JOKYO_GAMEN_KBN_TOIAWASE_ENTRY = "01";
	/** �󋵋敪 ��ʋ敪 02:�˗��o�^ */
	public static final String JOKYO_GAMEN_KBN_IRAI_ENTRY = "02";
	/** �󋵋敪 ��ʋ敪 03:�₢���킹����o�^ */
	public static final String JOKYO_GAMEN_KBN_INQUIRY_HISTORY_ENTRY ="03";
	/** �󋵋敪 ��ʋ敪 99:�S��� */
	public static final String JOKYO_GAMEN_KBN_ZENGAMEN = "99";
	
	/** ��ʋ敪 tb021:�₢���킹���� */
	public static final String GAMEN_KBN_INQUIRY_SEARCH = "tb021";
	
	/** ��ʋ敪 tb022:�₢���킹���e�ڍ� */
	public static final String GAMEN_KBN_INQUIRY_DETAIL = "tb022";
	
	/** ��ʋ敪 tb023:�₢���킹�o�^ */
	public static final String GAMEN_KBN_INQUIRY_ENTRY = "tb023";

	/** ��ʋ敪 tb024:�₢���킹����o�^ */
	public static final String GAMEN_KBN_INQUIRY_HISTORY_ENTRY = "tb024";
	
	/** ��ʋ敪 tb031:�˗����� */
	public static final String GAMEN_KBN_REQUEST_SEARCH = "tb031";
	
	/** ��ʋ敪 tb032:�˗����e�ڍׁE��Ə󋵓o�^ */
	public static final String GAMEN_KBN_REQUEST_DETAIL = "tb032";
	
	/** ��ʋ敪 tb033:�˗��o�^ */
	public static final String GAMEN_KBN_REQUEST_ENTRY = "tb033";

	/** ��ʋ敪 tb041:�ڋq�i�����E�����ҁj���� */
	public static final String GAMEN_KBN_CUSTOMER_SEARCH = "tb041";

	/** ��ʋ敪 tb042:�ڋq�i�����E�����ҁj�ڍ� */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL = "tb042";
	
	/** ��ʋ敪 tb045:�ڋq�ڍ� �t�����Q�� */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL_ACCOMPANYING = "tb045";

	/** ��ʋ敪 tb046:�ڋq�ڍ� �_����Q�� */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL_CONTRACT = "tb046";
	
	/** ��ʋ敪 tb047:�ڋq�ڍ� �_����ڍ׎Q�� */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL_CONTRACT_DETAIL = "tb047";
	
	/** ��ʋ敪 tb049:�ڋq�ڍ� �₢���킹���Q�� */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL_INQUIRY_HISTORY = "tb049";
	
	/** ��ʋ敪 tb050:�ڋq�ڍ� �˗��������Q�� */
	public static final String GAMEN_KBN_CUSTOMER_DETAIL_REQUEST_HISTORY = "tb050";

	/** ���[�U�[�G�[�W�F���g �����L�[�FiPad */
	public static final String USER_AGENT_KEY_IPAD = "iPad";
	/** ���[�U�[�G�[�W�F���g �����L�[�FMacintosh */
	public static final String USER_AGENT_KEY_MACINTOSH = "Macintosh";

	/** ���\�[�X�F�摜�t�@�C���p�X */
	public static final String RESOURCE_IMAGE_PATH = "/images";

	/** �摜�t�@�C���� �A�N�Z�X�����G���[ */
	public static final String IMAGE_FILENAME_ACCESS_ERROR = "403.png";
	/** �摜�t�@�C���� �t�@�C���Ȃ��G���[ */
	public static final String IMAGE_FILENAME_FILE_NOTFOUND = "404.png";
	/** �摜�t�@�C���� �T�[�o�[�G���[ */
	public static final String IMAGE_FILENAME_SERVER_ERROR = "500.png";

	/** �󋵋敪 01:�₢���킹�o�^ */
	public static final String JOKYO_KBN_INQUIRY_ENTRY = "01";
	
	/** TORES ID�i�p�X���[�h�l�D���O�C���h�c�j*/
	public static final String TORES_APL_ID = "TORES_APL";

}