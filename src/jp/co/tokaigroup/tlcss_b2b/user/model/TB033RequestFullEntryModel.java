package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpTFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTOtherFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.TbTFileUpload;
import jp.co.tokaigroup.reception.entity.TbTSagyoJokyo;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * �˗��o�^��ʃ��f���B
 * 
 * @author S.Nakano
 * @version 1.0 2015/10/21 S.Nakano
 * @version 1.1 2016/02/12 C.Kobayashi �t�@�C���A�b�v���[�h���̃v���C�}���[�L�[�ύX�Ή�
 * @version 1.2 2016/07/13 H.Yamamura �G���R�[�h�ςݖ₢���킹�����ԍ���ǉ�
 */
public class TB033RequestFullEntryModel extends TB040CustomerCommonInfoModel {
	/**
	 * �f�t�H���g�R���X�g���N�^�B
	 */
	public TB033RequestFullEntryModel() {
		super();

		this.sagyoJokyoImageFiles = new File[IMAGE_FILE_MAX_COUNT];
		this.sagyoJokyoImageFileComments = new String[IMAGE_FILE_MAX_COUNT];
		this.sagyoJokyoImageFileUploadFileNms = new String[IMAGE_FILE_MAX_COUNT];
		this.sagyoJokyoImageFileFileIndexes = new BigDecimal[IMAGE_FILE_MAX_COUNT];
		this.otherFiles = new File[IMAGE_FILE_MAX_COUNT];
		this.otherFileComments = new String[IMAGE_FILE_MAX_COUNT];
		this.otherFileUploadFileNms = new String[IMAGE_FILE_MAX_COUNT];
		this.otherFileUploadFileFileIndexes = new BigDecimal[IMAGE_FILE_MAX_COUNT];
	}

	/** �摜�t�@�C���ő匏�� */
	public static final int IMAGE_FILE_MAX_COUNT = 3;
	/** ��ʖ� */
	public static final String GAMEN_NM = "�˗��o�^";
	/** �{�^���� �Ǝ҈˗����[�����M */
	public static final String BUTTON_NM_IRAI_MAIL = "�Z���^�[�Ǝ҈˗����[�����M";
	/** ���J���~�߂�t���O 1:�`�F�b�NON */
	public static final String STOP_PUBLISH_FLG_ON = "1";
	/** ���������o�^�t���O 1:�`�F�b�NON */
	public static final String HISTORY_AUTO_REGIST_FLG_ON = "1";
	/** �A�N�V�����^�C�v sendMail�F���[�����M */
	public static final String ACTION_TYPE_SEND_MAIL = "sendMail";

	/** ��������hidden�o�͏��O���� */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** �₢���킹�m�n */
	private String toiawaseNo;
	/** �₢���킹�����m�n */
	private BigDecimal toiawaseRirekiNo;
	/** �₢���킹�X�V�� */
	private Timestamp toiawaseUpdDt;
	/** �A�b�v���[�h�t�@�C���� */
	private String uploadFileNm;
	/** �˗��X�V�� */
	private Timestamp iraiUpdDt;
	/** ��Ə󋵍X�V�� */
	private Timestamp sagyoJokyoUpdDt;

	/** �˗��������� */
	private RC041IraiSearchCondition condition = new RC041IraiSearchCondition();
	/** �₢���킹�������� */
	private RC031ToiawaseSearchCondition toiawaseCondition = new RC031ToiawaseSearchCondition();

	/** �h�c�����ڋq��� */
	private RcpTKokyakuWithNoId kokyakuWithoutId;
	/** �₢���킹��� */
	private RcpTToiawase toiawase;
	/** �₢���킹������� */
	private RcpTToiawaseRireki toiawaseRireki;
	/** �˗���� */
	private RcpTIrai irai;
	/** ��Ə󋵏�� */
	private RcpTSagyoJokyo sagyoJokyo;
	/** �A�b�v���[�h�ςݍ�Ə󋵉摜�t�@�C�� */
	private RcpTFileUpload[] uploadedFiles;
	/** ��Ə󋵉摜�t�@�C�� */
	private File[] sagyoJokyoImageFiles;
	/** ��Ə󋵉摜�t�@�C��(�J���}��؂�) */
	private String sagyoJokyoImageFileNm;
	/** ��Ə󋵉摜�t�@�C���R�����g */
	// �ʂɏ��������邽�߁A�I�u�W�F�N�g�𕪂��Ă���
	private String[] sagyoJokyoImageFileComments;
	/** ��Ə󋵉摜�t�@�C���A�b�v���[�h�� */
	private String[] sagyoJokyoImageFileUploadFileNms;
	/** ��Ə󋵉摜�t�@�C���C���f�b�N�X */
	private BigDecimal[] sagyoJokyoImageFileFileIndexes;
	/** �A�b�v���[�h�ς݂��̑��t�@�C�� */
	private RcpTOtherFileUpload[] uploadedOtherFiles;
	/** ���̑��t�@�C�� */
	private File[] otherFiles;
	/** ���̑��t�@�C����(�J���}��؂�) */
	private String otherFileNm;
	/** ���̑��t�@�C���R�����g */
	// �ʂɏ��������邽�߁A�I�u�W�F�N�g�𕪂��Ă���
	private String[] otherFileComments;
	/** ���̑��t�@�C���A�b�v���[�h�� */
	private String[] otherFileUploadFileNms;
	/** ���̑��t�@�C���C���f�b�N�X */
	private BigDecimal[] otherFileUploadFileFileIndexes;
	/** �t�@�C���C���f�b�N�X */
	private BigDecimal fileIndex;
	/** ���[��������� */
	private RcpTMailRireki mailRireki;
	/** �˗��Ǝҏ�� */
	private RcpMGyosha iraiGyosha;
	/** �Ǝ҉񓚍�Ə󋵏�� */
	private TbTSagyoJokyo gyoshaSagyoJokyo;
	/** �Ǝ҉񓚃A�b�v���[�h�t�@�C�����X�g */
	private List<TbTFileUpload> gyoshaUploadFileList;
	/** ���O�C�����[�U��� */
	private TLCSSB2BUserContext userContext;

	/** ���J���~�߂�t���O */
	private String stopPublishFlg;
	/** ���������o�^�t���O */
	private String historyAutoRegistFlg;
	/** ���������o�^�󋵋敪 */
	private String historyAutoRegistJokyoKbn;

	/** �K���]���X�g */
	private List<RcpMComCd> homonKiboList;
	/** �Ǝ҉񓚃��X�g */
	private List<RcpMComCd> gyoshaKaitoList;
	/** �󋵋敪���X�g */
	private List<RcpMJokyoKbn> jokyoKbnList;

	/** �����\���G���[�t���O */
	private boolean isInitError;
	/** �폜�����t���O */
	private boolean isDeleteCompleted;
	/** �t�@�C���폜�G���[ */
	private boolean fileDeleteError;
	/** ��ƈ˗�������t���O */
	private boolean workRequestPrinted;
	
	/** ���[�����p�X */
	private String makePdfPath;
	/** ���[�� */
	private String pdfNm;
	/** �ύX�O�˗����J�t���O */
	private String beforeIraiKokaiFlg;

	/** �G���R�[�h�ςݏZ���P */
	private String encodedJusho1;
	/** �G���R�[�h�ςݏZ���Q */
	private String encodedJusho2;
	/** �G���R�[�h�ςݏZ���R */
	private String encodedJusho3;
	/** �G���R�[�h�ςݏZ���S */
	private String encodedJusho4;
	/** �G���R�[�h�ςݏZ���T */
	private String encodedJusho5;
	/** �G���R�[�h�ς݃J�i�����P */
	private String encodedKanaNm1;
	/** �G���R�[�h�ς݃J�i�����Q */
	private String encodedKanaNm2;
	/** �G���R�[�h�ς݊��������P */
	private String encodedKanjiNm1;
	/** �G���R�[�h�ς݊��������Q */
	private String encodedKanjiNm2;

	/** �G���R�[�h�ςݖ₢���킹�����Z���P */
	private String encodedToiawaseJusho1;
	/** �G���R�[�h�ςݖ₢���킹�����Z���Q */
	private String encodedToiawaseJusho2;
	/** �G���R�[�h�ςݖ₢���킹�����Z���R */
	private String encodedToiawaseJusho3;
	/** �G���R�[�h�ςݖ₢���킹�����Z���S */
	private String encodedToiawaseJusho4;
	/** �G���R�[�h�ςݖ₢���킹�����Z���T */
	private String encodedToiawaseJusho5;
	/** �G���R�[�h�ςݖ₢���킹�����ԍ� */
	private String encodedToiawaseRoomNo;
	/** �G���R�[�h�ςݖ₢���킹�����J�i�����P */
	private String encodedToiawaseKanaNm1;
	/** �G���R�[�h�ςݖ₢���킹�����J�i�����Q */
	private String encodedToiawaseKanaNm2;
	/** �G���R�[�h�ςݖ₢���킹�������������P */
	private String encodedToiawaseKanjiNm1;
	/** �G���R�[�h�ςݖ₢���킹�������������Q */
	private String encodedToiawaseKanjiNm2;


	/** �J�ڌ���ʂ��ێ����Ă����J�ڌ���ʋ敪 */
	private String rootDispKbn;

	/**
	 * �₢���킹�m�n���擾���܂��B
	 * 
	 * @return �₢���킹�m�n
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}
	/**
	 * �₢���킹�m�n��ݒ肵�܂��B
	 * 
	 * @param toiawaseNo �₢���킹�m�n
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * �₢���킹�����m�n���擾���܂��B
	 * 
	 * @return �₢���킹�����m�n
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}
	/**
	 * �₢���킹�����m�n��ݒ肵�܂��B
	 * 
	 * @param toiawaseRirekiNo �₢���킹�����m�n
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}

	/**
	 * �₢���킹�X�V�����擾���܂��B
	 * 
	 * @return �₢���킹�X�V��
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}
	/**
	 * �₢���킹�X�V����ݒ肵�܂��B
	 * 
	 * @param toiawaseUpdDt �₢���킹�X�V��
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}

	/**
	 * �A�b�v���[�h�t�@�C�������擾���܂��B
	 * 
	 * @return �A�b�v���[�h�t�@�C����
	 */
	public String getUploadFileNm() {
		return uploadFileNm;
	}
	/**
	 * �A�b�v���[�h�t�@�C������ݒ肵�܂��B
	 * 
	 * @param uploadFileNm �A�b�v���[�h�t�@�C����
	 */
	public void setUploadFileNm(String uploadFileNm) {
		this.uploadFileNm = uploadFileNm;
	}

	/**
	 * �˗��X�V�����擾���܂��B
	 * 
	 * @return �˗��X�V��
	 */
	public Timestamp getIraiUpdDt() {
		return iraiUpdDt;
	}
	/**
	 * �˗��X�V����ݒ肵�܂��B
	 * 
	 * @param iraiUpdDt �˗��X�V��
	 */
	public void setIraiUpdDt(Timestamp iraiUpdDt) {
		this.iraiUpdDt = iraiUpdDt;
	}

	/**
	 * ��Ə󋵍X�V�����擾���܂��B
	 * 
	 * @return ��Ə󋵍X�V��
	 */
	public Timestamp getSagyoJokyoUpdDt() {
		return sagyoJokyoUpdDt;
	}
	/**
	 * ��Ə󋵍X�V����ݒ肵�܂��B
	 * 
	 * @param sagyoJokyoUpdDt ��Ə󋵍X�V��
	 */
	public void setSagyoJokyoUpdDt(Timestamp sagyoJokyoUpdDt) {
		this.sagyoJokyoUpdDt = sagyoJokyoUpdDt;
	}

	/**
	 * �˗������������擾���܂��B
	 * 
	 * @return �˗���������
	 */
	public RC041IraiSearchCondition getCondition() {
		return condition;
	}
	/**
	 * �˗�����������ݒ肵�܂��B
	 * 
	 * @param condition �˗���������
	 */
	public void setCondition(RC041IraiSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �₢���킹�����������擾���܂��B
	 * @return �₢���킹��������
	 */
	public RC031ToiawaseSearchCondition getToiawaseCondition() {
		return toiawaseCondition;
	}
	/**
	 * �₢���킹����������ݒ肵�܂��B
	 * @param toiawaseCondition �₢���킹��������
	 */
	public void setToiawaseCondition(RC031ToiawaseSearchCondition toiawaseCondition) {
		this.toiawaseCondition = toiawaseCondition;
	}

	/**
	 * �h�c�����ڋq�����擾���܂��B
	 * 
	 * @return �h�c�����ڋq���
	 */
	public RcpTKokyakuWithNoId getKokyakuWithoutId() {
		return kokyakuWithoutId;
	}
	/**
	 * �h�c�����ڋq����ݒ肵�܂��B
	 * 
	 * @param kokyakuWithoutId �h�c�����ڋq���
	 */
	public void setKokyakuWithoutId(RcpTKokyakuWithNoId kokyakuWithoutId) {
		this.kokyakuWithoutId = kokyakuWithoutId;
	}

	/**
	 * �₢���킹�����擾���܂��B
	 * 
	 * @return �₢���킹���
	 */
	public RcpTToiawase getToiawase() {
		return toiawase;
	}
	/**
	 * �₢���킹����ݒ肵�܂��B
	 * 
	 * @param toiawase �₢���킹���
	 */
	public void setToiawase(RcpTToiawase toiawase) {
		this.toiawase = toiawase;
	}

	/**
	 * �₢���킹���������擾���܂��B
	 * 
	 * @return �₢���킹�������
	 */
	public RcpTToiawaseRireki getToiawaseRireki() {
		return toiawaseRireki;
	}
	/**
	 * �₢���킹��������ݒ肵�܂��B
	 * 
	 * @param toiawaseRireki �₢���킹�������
	 */
	public void setToiawaseRireki(RcpTToiawaseRireki toiawaseRireki) {
		this.toiawaseRireki = toiawaseRireki;
	}

	/**
	 * �˗������擾���܂��B
	 * 
	 * @return �˗����
	 */
	public RcpTIrai getIrai() {
		return irai;
	}
	/**
	 * �˗�����ݒ肵�܂��B
	 * 
	 * @param irai �˗����
	 */
	public void setIrai(RcpTIrai irai) {
		this.irai = irai;
	}

	/**
	 * ��Ə󋵏����擾���܂��B
	 * 
	 * @return ��Ə󋵏��
	 */
	public RcpTSagyoJokyo getSagyoJokyo() {
		return sagyoJokyo;
	}
	/**
	 * ��Ə󋵏���ݒ肵�܂��B
	 * 
	 * @param sagyoJokyo ��Ə󋵏��
	 */
	public void setSagyoJokyo(RcpTSagyoJokyo sagyoJokyo) {
		this.sagyoJokyo = sagyoJokyo;
	}

	/**
	 * �A�b�v���[�h�ςݍ�Ə󋵉摜�t�@�C�����擾���܂��B
	 * 
	 * @return �A�b�v���[�h�ςݍ�Ə󋵉摜�t�@�C��
	 */
	public RcpTFileUpload[] getUploadedFiles() {
		return uploadedFiles;
	}
	/**
	 * �A�b�v���[�h�ςݍ�Ə󋵉摜�t�@�C����ݒ肵�܂��B
	 * 
	 * @param uploadedFiles �A�b�v���[�h�ςݍ�Ə󋵉摜�t�@�C��
	 */
	public void setUploadedFiles(RcpTFileUpload[] uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	/**
	 * ��Ə󋵉摜�t�@�C�����擾���܂��B
	 * 
	 * @return ��Ə󋵉摜�t�@�C��
	 */
	public File[] getSagyoJokyoImageFiles() {
		return sagyoJokyoImageFiles;
	}
	/**
	 * ��Ə󋵉摜�t�@�C����ݒ肵�܂��B
	 * 
	 * @param sagyoJokyoImageFiles ��Ə󋵉摜�t�@�C��
	 */
	public void setSagyoJokyoImageFiles(File[] sagyoJokyoImageFiles) {
		this.sagyoJokyoImageFiles = sagyoJokyoImageFiles;
	}

	/**
	 * ��Ə󋵉摜�t�@�C��(�J���}��؂�)���擾���܂��B
	 * 
	 * @return ��Ə󋵉摜�t�@�C��(�J���}��؂�)
	 */
	public String getSagyoJokyoImageFileNm() {
		return sagyoJokyoImageFileNm;
	}
	/**
	 * ��Ə󋵉摜�t�@�C��(�J���}��؂�)��ݒ肵�܂��B
	 * 
	 * @param sagyoJokyoImageFileNm ��Ə󋵉摜�t�@�C��(�J���}��؂�)
	 */
	public void setSagyoJokyoImageFileNm(String sagyoJokyoImageFileNm) {
		this.sagyoJokyoImageFileNm = sagyoJokyoImageFileNm;
	}

	/**
	 * ��Ə󋵉摜�t�@�C���R�����g���擾���܂��B
	 * 
	 * @return ��Ə󋵉摜�t�@�C���R�����g
	 */
	public String[] getSagyoJokyoImageFileComments() {
		return sagyoJokyoImageFileComments;
	}
	/**
	 * ��Ə󋵉摜�t�@�C���R�����g��ݒ肵�܂��B
	 * 
	 * @param sagyoJokyoImageFileComments ��Ə󋵉摜�t�@�C���R�����g
	 */
	public void setSagyoJokyoImageFileComments(String[] sagyoJokyoImageFileComments) {
		this.sagyoJokyoImageFileComments = sagyoJokyoImageFileComments;
	}

	/**
	 * ��Ə󋵉摜�t�@�C���A�b�v���[�h�����擾���܂��B
	 * 
	 * @return ��Ə󋵉摜�t�@�C���A�b�v���[�h��
	 */
	public String[] getSagyoJokyoImageFileUploadFileNms() {
		return sagyoJokyoImageFileUploadFileNms;
	}
	/**
	 * ��Ə󋵉摜�t�@�C���A�b�v���[�h����ݒ肵�܂��B
	 * 
	 * @param sagyoJokyoImageFileUploadFileNms ��Ə󋵉摜�t�@�C���A�b�v���[�h��
	 */
	public void setSagyoJokyoImageFileUploadFileNms(String[] sagyoJokyoImageFileUploadFileNms) {
		this.sagyoJokyoImageFileUploadFileNms = sagyoJokyoImageFileUploadFileNms;
	}

	/**
	 * ��Ə󋵉摜�t�@�C���C���f�b�N�X���擾���܂��B
	 * @return ��Ə󋵉摜�t�@�C���C���f�b�N�X
	 */
	public BigDecimal[] getSagyoJokyoImageFileFileIndexes() {
		return sagyoJokyoImageFileFileIndexes;
	}
	/**
	 * ��Ə󋵉摜�t�@�C���C���f�b�N�X��ݒ肵�܂��B
	 * @param sagyoJokyoImageFileFileIndexes ��Ə󋵉摜�t�@�C���C���f�b�N�X
	 */
	public void setSagyoJokyoImageFileFileIndexes(BigDecimal[] sagyoJokyoImageFileFileIndexes) {
		this.sagyoJokyoImageFileFileIndexes = sagyoJokyoImageFileFileIndexes;
	}

	/**
	 * �A�b�v���[�h�ς݂��̑��t�@�C�����擾���܂��B
	 * 
	 * @return �A�b�v���[�h�ς݂��̑��t�@�C��
	 */
	public RcpTOtherFileUpload[] getUploadedOtherFiles() {
		return uploadedOtherFiles;
	}
	/**
	 * �A�b�v���[�h�ς݂��̑��t�@�C����ݒ肵�܂��B
	 * 
	 * @param uploadedOtherFiles �A�b�v���[�h�ς݂��̑��t�@�C��
	 */
	public void setUploadedOtherFiles(RcpTOtherFileUpload[] uploadedOtherFiles) {
		this.uploadedOtherFiles = uploadedOtherFiles;
	}

	/**
	 * ���̑��t�@�C�����擾���܂��B
	 * 
	 * @return ���̑��t�@�C��
	 */
	public File[] getOtherFiles() {
		return otherFiles;
	}
	/**
	 * ���̑��t�@�C����ݒ肵�܂��B
	 * 
	 * @param otherFiles ���̑��t�@�C��
	 */
	public void setOtherFiles(File[] otherFiles) {
		this.otherFiles = otherFiles;
	}

	/**
	 * ���̑��t�@�C����(�J���}��؂�)���擾���܂��B
	 * 
	 * @return ���̑��t�@�C����(�J���}��؂�)
	 */
	public String getOtherFileNm() {
		return otherFileNm;
	}
	/**
	 * ���̑��t�@�C����(�J���}��؂�)��ݒ肵�܂��B
	 * 
	 * @param otherFileNm ���̑��t�@�C����(�J���}��؂�)
	 */
	public void setOtherFileNm(String otherFileNm) {
		this.otherFileNm = otherFileNm;
	}

	/**
	 * ���̑��t�@�C���R�����g���擾���܂��B
	 * 
	 * @return ���̑��t�@�C���R�����g
	 */
	public String[] getOtherFileComments() {
		return otherFileComments;
	}
	/**
	 * ���̑��t�@�C���R�����g��ݒ肵�܂��B
	 * 
	 * @param otherFileComments ���̑��t�@�C���R�����g
	 */
	public void setOtherFileComments(String[] otherFileComments) {
		this.otherFileComments = otherFileComments;
	}

	/**
	 * ���̑��t�@�C���A�b�v���[�h�����擾���܂��B
	 * 
	 * @return ���̑��t�@�C���A�b�v���[�h��
	 */
	public String[] getOtherFileUploadFileNms() {
		return otherFileUploadFileNms;
	}
	/**
	 * ���̑��t�@�C���A�b�v���[�h����ݒ肵�܂��B
	 * 
	 * @param otherFileUploadFileNms ���̑��t�@�C���A�b�v���[�h��
	 */
	public void setOtherFileUploadFileNms(String[] otherFileUploadFileNms) {
		this.otherFileUploadFileNms = otherFileUploadFileNms;
	}

	/**
	 * ���̑��t�@�C���C���f�b�N�X���擾���܂��B
	 * @return ���̑��t�@�C���C���f�b�N�X
	 */
	public BigDecimal[] getOtherFileUploadFileFileIndexes() {
		return otherFileUploadFileFileIndexes;
	}
	/**
	 * ���̑��t�@�C���C���f�b�N�X��ݒ肵�܂��B
	 * @param otherFileUploadFileFileIndexes ���̑��t�@�C���C���f�b�N�X
	 */
	public void setOtherFileUploadFileFileIndexes(BigDecimal[] otherFileUploadFileFileIndexes) {
		this.otherFileUploadFileFileIndexes = otherFileUploadFileFileIndexes;
	}

	/**
	 * �t�@�C���C���f�b�N�X���擾���܂��B
	 * @return �t�@�C���C���f�b�N�X
	 */
	public BigDecimal getFileIndex() {
		return fileIndex;
	}
	/**
	 * �t�@�C���C���f�b�N�X��ݒ肵�܂��B
	 * @param fileIndex �t�@�C���C���f�b�N�X
	 */
	public void setFileIndex(BigDecimal fileIndex) {
		this.fileIndex = fileIndex;
	}
	/**
	 * ���[�����������擾���܂��B
	 * 
	 * @return ���[���������
	 */
	public RcpTMailRireki getMailRireki() {
		return mailRireki;
	}
	/**
	 * ���[����������ݒ肵�܂��B
	 * 
	 * @param mailRireki ���[���������
	 */
	public void setMailRireki(RcpTMailRireki mailRireki) {
		this.mailRireki = mailRireki;
	}

	/**
	 * �˗��Ǝҏ����擾���܂��B
	 * 
	 * @return �˗��Ǝҏ��
	 */
	public RcpMGyosha getIraiGyosha() {
		return iraiGyosha;
	}
	/**
	 * �˗��Ǝҏ���ݒ肵�܂��B
	 * 
	 * @param iraiGyosha �˗��Ǝҏ��
	 */
	public void setIraiGyosha(RcpMGyosha iraiGyosha) {
		this.iraiGyosha = iraiGyosha;
	}

	/**
	 * �Ǝ҉񓚍�Ə󋵏����擾���܂��B
	 * 
	 * @return �Ǝ҉񓚍�Ə󋵏��
	 */
	public TbTSagyoJokyo getGyoshaSagyoJokyo() {
		return gyoshaSagyoJokyo;
	}
	/**
	 * �Ǝ҉񓚍�Ə󋵏���ݒ肵�܂��B
	 * 
	 * @param gyoshaSagyoJokyo �Ǝ҉񓚍�Ə󋵏��
	 */
	public void setGyoshaSagyoJokyo(TbTSagyoJokyo gyoshaSagyoJokyo) {
		this.gyoshaSagyoJokyo = gyoshaSagyoJokyo;
	}

	/**
	 * �Ǝ҉񓚃A�b�v���[�h�t�@�C�����X�g���擾���܂��B
	 * 
	 * @return �Ǝ҉񓚃A�b�v���[�h�t�@�C�����X�g
	 */
	public List<TbTFileUpload> getGyoshaUploadFileList() {
		return gyoshaUploadFileList;
	}
	/**
	 * �Ǝ҉񓚃A�b�v���[�h�t�@�C�����X�g��ݒ肵�܂��B
	 * 
	 * @param gyoshaUploadFileList �Ǝ҉񓚃A�b�v���[�h�t�@�C�����X�g
	 */
	public void setGyoshaUploadFileList(List<TbTFileUpload> gyoshaUploadFileList) {
		this.gyoshaUploadFileList = gyoshaUploadFileList;
	}

	/**
	 * ���O�C�����[�U�����擾���܂��B
	 * @return ���O�C�����[�U���
	 */
	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}
	/**
	 * ���O�C�����[�U����ݒ肵�܂��B
	 * @param userContext ���O�C�����[�U���
	 */
	public void setUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * ���J���~�߂�t���O���擾���܂��B
	 * 
	 * @return ���J���~�߂�t���O
	 */
	public String getStopPublishFlg() {
		return stopPublishFlg;
	}
	/**
	 * ���J���~�߂�t���O��ݒ肵�܂��B
	 * 
	 * @param stopPublishFlg ���J���~�߂�t���O
	 */
	public void setStopPublishFlg(String stopPublishFlg) {
		this.stopPublishFlg = stopPublishFlg;
	}

	/**
	 * ���������o�^�t���O���擾���܂��B
	 * 
	 * @return ���������o�^�t���O
	 */
	public String getHistoryAutoRegistFlg() {
		return historyAutoRegistFlg;
	}
	/**
	 * ���������o�^�t���O��ݒ肵�܂��B
	 * 
	 * @param historyAutoRegistFlg ���������o�^�t���O
	 */
	public void setHistoryAutoRegistFlg(String historyAutoRegistFlg) {
		this.historyAutoRegistFlg = historyAutoRegistFlg;
	}

	/**
	 * ���������o�^�󋵋敪���擾���܂��B
	 * 
	 * @return ���������o�^�󋵋敪
	 */
	public String getHistoryAutoRegistJokyoKbn() {
		return historyAutoRegistJokyoKbn;
	}
	/**
	 * ���������o�^�󋵋敪��ݒ肵�܂��B
	 * 
	 * @param historyAutoRegistJokyoKbn ���������o�^�󋵋敪
	 */
	public void setHistoryAutoRegistJokyoKbn(String historyAutoRegistJokyoKbn) {
		this.historyAutoRegistJokyoKbn = historyAutoRegistJokyoKbn;
	}

	/**
	 * �K���]���X�g���擾���܂��B
	 * 
	 * @return �K���]���X�g
	 */
	public List<RcpMComCd> getHomonKiboList() {
		return homonKiboList;
	}
	/**
	 * �K���]���X�g��ݒ肵�܂��B
	 * 
	 * @param homonKiboList �K���]���X�g
	 */
	public void setHomonKiboList(List<RcpMComCd> homonKiboList) {
		this.homonKiboList = homonKiboList;
	}

	/**
	 * �Ǝ҉񓚃��X�g���擾���܂��B
	 * 
	 * @return �Ǝ҉񓚃��X�g
	 */
	public List<RcpMComCd> getGyoshaKaitoList() {
		return gyoshaKaitoList;
	}
	/**
	 * �Ǝ҉񓚃��X�g��ݒ肵�܂��B
	 * 
	 * @param gyoshaKaitoList �Ǝ҉񓚃��X�g
	 */
	public void setGyoshaKaitoList(List<RcpMComCd> gyoshaKaitoList) {
		this.gyoshaKaitoList = gyoshaKaitoList;
	}

	/**
	 * �󋵋敪���X�g���擾���܂��B
	 * 
	 * @return �󋵋敪���X�g
	 */
	public List<RcpMJokyoKbn> getJokyoKbnList() {
		return jokyoKbnList;
	}
	/**
	 * �󋵋敪���X�g��ݒ肵�܂��B
	 * 
	 * @param jokyoKbnList �󋵋敪���X�g
	 */
	public void setJokyoKbnList(List<RcpMJokyoKbn> jokyoKbnList) {
		this.jokyoKbnList = jokyoKbnList;
	}

	/**
	 * �����\���G���[�t���O���擾���܂��B
	 * 
	 * @return �����\���G���[�t���O
	 */
	public boolean isInitError() {
		return isInitError;
	}
	/**
	 * �����\���G���[�t���O��ݒ肵�܂��B
	 * 
	 * @param isInitError �����\���G���[�t���O
	 */
	public void setInitError(boolean isInitError) {
		this.isInitError = isInitError;
	}

	/**
	 * �폜�����t���O���擾���܂��B
	 * 
	 * @return �폜�����t���O
	 */
	public boolean isDeleteCompleted() {
		return isDeleteCompleted;
	}
	/**
	 * �폜�����t���O��ݒ肵�܂��B
	 * 
	 * @param isDeleteCompleted �폜�����t���O
	 */
	public void setDeleteCompleted(boolean isDeleteCompleted) {
		this.isDeleteCompleted = isDeleteCompleted;
	}

	/**
	 * �t�@�C���폜�G���[���擾���܂��B
	 * 
	 * @return �t�@�C���폜�G���[
	 */
	public boolean isFileDeleteError() {
		return fileDeleteError;
	}
	/**
	 * �t�@�C���폜�G���[��ݒ肵�܂��B
	 * 
	 * @param fileDeleteError �t�@�C���폜�G���[
	 */
	public void setFileDeleteError(boolean fileDeleteError) {
		this.fileDeleteError = fileDeleteError;
	}

	/**
	 * ��ƈ˗�������t���O���擾���܂��B
	 * 
	 * @return ��ƈ˗�������t���O
	 */
	public boolean isWorkRequestPrinted() {
		return workRequestPrinted;
	}
	/**
	 * ��ƈ˗�������t���O��ݒ肵�܂��B
	 * 
	 * @param workRequestPrinted ��ƈ˗�������t���O
	 */
	public void setWorkRequestPrinted(boolean workRequestPrinted) {
		this.workRequestPrinted = workRequestPrinted;
	}
	
	/**
	 * ���[�����p�X���擾���܂��B
	 * 
	 * @return ���[�����p�X
	 */
	public String getMakePdfPath() {
		return makePdfPath;
	}
	/**
	 * ���[�����p�X��ݒ肵�܂��B
	 * 
	 * @param makePdfPath ���[�����p�X
	 */
	public void setMakePdfPath(String makePdfPath) {
		this.makePdfPath = makePdfPath;
	}

	/**
	 * ���[�����擾���܂��B
	 * 
	 * @return ���[��
	 */
	public String getPdfNm() {
		return pdfNm;
	}
	/**
	 * ���[����ݒ肵�܂��B
	 * 
	 * @param pdfNm ���[��
	 */
	public void setPdfNm(String pdfNm) {
		this.pdfNm = pdfNm;
	}

	/**
	 * �ύX�O�˗����J�t���O���擾���܂��B
	 * 
	 * @return �ύX�O�˗����J�t���O
	 */
	public String getBeforeIraiKokaiFlg() {
		return beforeIraiKokaiFlg;
	}
	/**
	 * �ύX�O�˗����J�t���O��ݒ肵�܂��B
	 * 
	 * @param beforeIraiKokaiFlg �ύX�O�˗����J�t���O
	 */
	public void setBeforeIraiKokaiFlg(String beforeIraiKokaiFlg) {
		this.beforeIraiKokaiFlg = beforeIraiKokaiFlg;
	}

	/**
	 * �G���R�[�h�ςݏZ���P���擾���܂��B
	 * 
	 * @return �G���R�[�h�ςݏZ���P
	 */
	public String getEncodedJusho1() {
		return encodedJusho1;
	}
	/**
	 * �G���R�[�h�ςݏZ���P��ݒ肵�܂��B
	 * 
	 * @param encodedJusho1 �G���R�[�h�ςݏZ���P
	 */
	public void setEncodedJusho1(String encodedJusho1) {
		this.encodedJusho1 = createEncodeString(encodedJusho1);
	}

	/**
	 * �G���R�[�h�ςݏZ���Q���擾���܂��B
	 * 
	 * @return �G���R�[�h�ςݏZ���Q
	 */
	public String getEncodedJusho2() {
		return encodedJusho2;
	}
	/**
	 * �G���R�[�h�ςݏZ���Q��ݒ肵�܂��B
	 * 
	 * @param encodedJusho2 �G���R�[�h�ςݏZ���Q
	 */
	public void setEncodedJusho2(String encodedJusho2) {
		this.encodedJusho2 = createEncodeString(encodedJusho2);
	}

	/**
	 * �G���R�[�h�ςݏZ���R���擾���܂��B
	 * 
	 * @return �G���R�[�h�ςݏZ���R
	 */
	public String getEncodedJusho3() {
		return encodedJusho3;
	}
	/**
	 * �G���R�[�h�ςݏZ���R��ݒ肵�܂��B
	 * 
	 * @param encodedJusho3 �G���R�[�h�ςݏZ���R
	 */
	public void setEncodedJusho3(String encodedJusho3) {
		this.encodedJusho3 = createEncodeString(encodedJusho3);
	}

	/**
	 * �G���R�[�h�ςݏZ���S���擾���܂��B
	 * 
	 * @return �G���R�[�h�ςݏZ���S
	 */
	public String getEncodedJusho4() {
		return encodedJusho4;
	}
	/**
	 * �G���R�[�h�ςݏZ���S��ݒ肵�܂��B
	 * 
	 * @param encodedJusho4 �G���R�[�h�ςݏZ���S
	 */
	public void setEncodedJusho4(String encodedJusho4) {
		this.encodedJusho4 = createEncodeString(encodedJusho4);
	}

	/**
	 * �G���R�[�h�ςݏZ���T���擾���܂��B
	 * 
	 * @return �G���R�[�h�ςݏZ���T
	 */
	public String getEncodedJusho5() {
		return encodedJusho5;
	}
	/**
	 * �G���R�[�h�ςݏZ���T��ݒ肵�܂��B
	 * 
	 * @param encodedJusho5 �G���R�[�h�ςݏZ���T
	 */
	public void setEncodedJusho5(String encodedJusho5) {
		this.encodedJusho5 = createEncodeString(encodedJusho5);
	}

	/**
	 * �G���R�[�h�ς݃J�i�����P���擾���܂��B
	 * 
	 * @return �G���R�[�h�ς݃J�i�����P
	 */
	public String getEncodedKanaNm1() {
		return encodedKanaNm1;
	}
	/**
	 * �G���R�[�h�ς݃J�i�����P��ݒ肵�܂��B
	 * 
	 * @param encodedKanaNm1 �G���R�[�h�ς݃J�i�����P
	 */
	public void setEncodedKanaNm1(String encodedKanaNm1) {
		this.encodedKanaNm1 = createEncodeString(encodedKanaNm1);
	}

	/**
	 * �G���R�[�h�ς݃J�i�����Q���擾���܂��B
	 * 
	 * @return �G���R�[�h�ς݃J�i�����Q
	 */
	public String getEncodedKanaNm2() {
		return encodedKanaNm2;
	}
	/**
	 * �G���R�[�h�ς݃J�i�����Q��ݒ肵�܂��B
	 * 
	 * @param encodedKanaNm2 �G���R�[�h�ς݃J�i�����Q
	 */
	public void setEncodedKanaNm2(String encodedKanaNm2) {
		this.encodedKanaNm2 = createEncodeString(encodedKanaNm2);
	}

	/**
	 * �G���R�[�h�ς݊��������P���擾���܂��B
	 * 
	 * @return �G���R�[�h�ς݊��������P
	 */
	public String getEncodedKanjiNm1() {
		return encodedKanjiNm1;
	}
	/**
	 * �G���R�[�h�ς݊��������P��ݒ肵�܂��B
	 * 
	 * @param encodedKanjiNm1 �G���R�[�h�ς݊��������P
	 */
	public void setEncodedKanjiNm1(String encodedKanjiNm1) {
		this.encodedKanjiNm1 = createEncodeString(encodedKanjiNm1);
	}

	/**
	 * �G���R�[�h�ς݊��������Q���擾���܂��B
	 * 
	 * @return �G���R�[�h�ς݊��������Q
	 */
	public String getEncodedKanjiNm2() {
		return encodedKanjiNm2;
	}
	/**
	 * �G���R�[�h�ς݊��������Q��ݒ肵�܂��B
	 * 
	 * @param encodedKanjiNm2 �G���R�[�h�ς݊��������Q
	 */
	public void setEncodedKanjiNm2(String encodedKanjiNm2) {
		this.encodedKanjiNm2 = createEncodeString(encodedKanjiNm2);
	}

	/**
	 * �G���R�[�h�ςݖ₢���킹�����Z���P���擾���܂��B
	 * @return �G���R�[�h�ςݖ₢���킹�����Z���P
	 */
	public String getEncodedToiawaseJusho1() {
		return encodedToiawaseJusho1;
	}
	/**
	 * �G���R�[�h�ςݖ₢���킹�����Z���P��ݒ肵�܂��B
	 * @param encodedToiawaseJusho1 �G���R�[�h�ςݖ₢���킹�����Z���P
	 */
	public void setEncodedToiawaseJusho1(String encodedToiawaseJusho1) {
		this.encodedToiawaseJusho1 = createEncodeString(encodedToiawaseJusho1);
	}

	/**
	 * �G���R�[�h�ςݖ₢���킹�����Z���Q���擾���܂��B
	 * @return �G���R�[�h�ςݖ₢���킹�����Z���Q
	 */
	public String getEncodedToiawaseJusho2() {
		return encodedToiawaseJusho2;
	}

	/**
	 * �G���R�[�h�ςݖ₢���킹�����Z���Q��ݒ肵�܂��B
	 * @param encodedToiawaseJusho2 �G���R�[�h�ςݖ₢���킹�����Z���Q
	 */
	public void setEncodedToiawaseJusho2(String encodedToiawaseJusho2) {
		this.encodedToiawaseJusho2 = createEncodeString(encodedToiawaseJusho2);
	}
	/**
	 * �G���R�[�h�ςݖ₢���킹�����Z���R���擾���܂��B
	 * @return �G���R�[�h�ςݖ₢���킹�����Z���R
	 */
	public String getEncodedToiawaseJusho3() {
		return encodedToiawaseJusho3;
	}

	/**
	 * �G���R�[�h�ςݖ₢���킹�����Z���R��ݒ肵�܂��B
	 * @param encodedToiawaseJusho3 �G���R�[�h�ςݖ₢���킹�����Z���R
	 */
	public void setEncodedToiawaseJusho3(String encodedToiawaseJusho3) {
		this.encodedToiawaseJusho3 = createEncodeString(encodedToiawaseJusho3);
	}
	/**
	 * �G���R�[�h�ςݖ₢���킹�����Z���S���擾���܂��B
	 * @return �G���R�[�h�ςݖ₢���킹�����Z���S
	 */
	public String getEncodedToiawaseJusho4() {
		return encodedToiawaseJusho4;
	}

	/**
	 * �G���R�[�h�ςݖ₢���킹�����Z���S��ݒ肵�܂��B
	 * @param encodedToiawaseJusho4 �G���R�[�h�ςݖ₢���킹�����Z���S
	 */
	public void setEncodedToiawaseJusho4(String encodedToiawaseJusho4) {
		this.encodedToiawaseJusho4 = createEncodeString(encodedToiawaseJusho4);
	}
	/**
	 * �G���R�[�h�ςݖ₢���킹�����Z���T���擾���܂��B
	 * @return �G���R�[�h�ςݖ₢���킹�����Z���T
	 */
	public String getEncodedToiawaseJusho5() {
		return encodedToiawaseJusho5;
	}
	/**
	 * �G���R�[�h�ςݖ₢���킹�����Z���T��ݒ肵�܂��B
	 * @param encodedToiawaseJusho5 �G���R�[�h�ςݖ₢���킹�����Z���T
	 */
	public void setEncodedToiawaseJusho5(String encodedToiawaseJusho5) {
		this.encodedToiawaseJusho5 = createEncodeString(encodedToiawaseJusho5);
	}

	/**
	 * �G���R�[�h�ςݖ₢���킹���������ԍ����擾���܂��B
	 * @return �G���R�[�h�ςݖ₢���킹���������ԍ�
	 */
	public String getEncodedToiawaseRoomNo() {
		return encodedToiawaseRoomNo;
	}

	/**
	 * �G���R�[�h�ςݖ₢���킹���������ԍ���ݒ肵�܂��B
	 * @param encodedToiawaseRoomNo �G���R�[�h�ςݖ₢���킹���������ԍ�
	 */
	public void setEncodedToiawaseRoomNo(String encodedToiawaseRoomNo) {
		this.encodedToiawaseRoomNo = createEncodeString(encodedToiawaseRoomNo);
	}
	/**
	 * �G���R�[�h�ςݖ₢���킹�����J�i�����P���擾���܂��B
	 * @return �G���R�[�h�ςݖ₢���킹�����J�i�����P
	 */
	public String getEncodedToiawaseKanaNm1() {
		return encodedToiawaseKanaNm1;
	}
	/**
	 * �G���R�[�h�ςݖ₢���킹�����J�i�����P��ݒ肵�܂��B
	 * @param encodedToiawaseKanaNm1 �G���R�[�h�ςݖ₢���킹�����J�i�����P
	 */
	public void setEncodedToiawaseKanaNm1(String encodedToiawaseKanaNm1) {
		this.encodedToiawaseKanaNm1 = createEncodeString(encodedToiawaseKanaNm1);
	}

	/**
	 * �G���R�[�h�ςݖ₢���킹�����J�i�����Q���擾���܂��B
	 * @return �G���R�[�h�ςݖ₢���킹�����J�i�����Q
	 */
	public String getEncodedToiawaseKanaNm2() {
		return encodedToiawaseKanaNm2;
	}
	/**
	 * �G���R�[�h�ςݖ₢���킹�����J�i�����Q��ݒ肵�܂��B
	 * @param encodedToiawaseKanaNm2 �G���R�[�h�ςݖ₢���킹�����J�i�����Q
	 */
	public void setEncodedToiawaseKanaNm2(String encodedToiawaseKanaNm2) {
		this.encodedToiawaseKanaNm2 = createEncodeString(encodedToiawaseKanaNm2);
	}

	/**
	 * �G���R�[�h�ςݖ₢���킹�������������P���擾���܂��B
	 * @return �G���R�[�h�ςݖ₢���킹�������������P
	 */
	public String getEncodedToiawaseKanjiNm1() {
		return encodedToiawaseKanjiNm1;
	}
	/**
	 * �G���R�[�h�ςݖ₢���킹�������������P��ݒ肵�܂��B
	 * @param encodedToiawaseKanjiNm1 �G���R�[�h�ςݖ₢���킹�������������P
	 */
	public void setEncodedToiawaseKanjiNm1(String encodedToiawaseKanjiNm1) {
		this.encodedToiawaseKanjiNm1 = createEncodeString(encodedToiawaseKanjiNm1);
	}

	/**
	 * �G���R�[�h�ςݖ₢���킹�������������Q���擾���܂��B
	 * @return �G���R�[�h�ςݖ₢���킹�������������Q
	 */
	public String getEncodedToiawaseKanjiNm2() {
		return encodedToiawaseKanjiNm2;
	}
	/**
	 * �G���R�[�h�ςݖ₢���킹�������������Q��ݒ肵�܂��B
	 * @param encodedToiawaseKanjiNm2 �G���R�[�h�ςݖ₢���킹�������������Q
	 */
	public void setEncodedToiawaseKanjiNm2(String encodedToiawaseKanjiNm2) {
		this.encodedToiawaseKanjiNm2 = createEncodeString(encodedToiawaseKanjiNm2);
	}

	/**
	 * �J�ڌ���ʂ��ێ����Ă����J�ڌ���ʋ敪���擾���܂��B
	 * @return �J�ڌ���ʂ��ێ����Ă����J�ڌ���ʋ敪
	 */
	public String getRootDispKbn() {
		return rootDispKbn;
	}
	/**
	 * �J�ڌ���ʂ��ێ����Ă����J�ڌ���ʋ敪��ݒ肵�܂��B
	 * @param rootDispKbn �J�ڌ���ʂ��ێ����Ă����J�ڌ���ʋ敪
	 */
	public void setRootDispKbn(String rootDispKbn) {
		this.rootDispKbn = rootDispKbn;
	}

	/**
	 * �t�@�C���A�b�v���[�h���������s���邩�𔻒肵�܂��B
	 * 
	 * @return true�F���������s����
	 */
	public boolean isFileUploadExecutable() {
		if (this.sagyoJokyoImageFiles == null || this.sagyoJokyoImageFiles.length == 0) {
			return false;
		}

		for (File file : this.sagyoJokyoImageFiles) {
			if (file != null) {
				return true;
			}
		}

		return false;
	}

	/**
	 * �t�@�C���R�����g�X�V�������s�����𔻒肵�܂��B
	 * 
	 * @return true�F���������s����
	 */
	public boolean isFileCommentUpdateExecutable() {
		if (this.sagyoJokyoImageFileUploadFileNms == null || this.sagyoJokyoImageFileUploadFileNms.length == 0) {
			return false;
		}

		for (String uploadFileNm : this.sagyoJokyoImageFileUploadFileNms) {
			if (StringUtils.isNotBlank(uploadFileNm)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * ���̑��t�@�C���A�b�v���[�h���������s���邩�𔻒肵�܂��B
	 * 
	 * @return true�F���������s����
	 */
	public boolean isOtherFileUploadExecutable() {
		if (this.otherFiles == null || this.otherFiles.length == 0) {
			return false;
		}

		for (File file : this.otherFiles) {
			if (file != null) {
				return true;
			}
		}

		return false;
	}

	/**
	 * ���̑��t�@�C���R�����g�X�V�������s�����𔻒肵�܂��B
	 * 
	 * @return true�F���������s����
	 */
	public boolean isOtherFileCommentUpdateExecutable() {
		if (this.otherFileUploadFileNms == null || this.otherFileUploadFileNms.length == 0) {
			return false;
		}

		for (String uploadFileNm : this.otherFileUploadFileNms) {
			if (StringUtils.isNotBlank(uploadFileNm)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * ��Ə󋵉摜�t�@�C������z��ɂ��ĕԂ��܂��B
	 *
	 * @return ��Ə󋵉摜�t�@�C�����z��
	 */
	public String[] getSagyoJokyoImageFileNmByArray() {
		if (StringUtils.isBlank(this.sagyoJokyoImageFileNm)) {
			return new String[0];
		}

		return this.sagyoJokyoImageFileNm.split(",");
	}

	/**
	 * ���̑��t�@�C������z��ɂ��ĕԂ��܂��B
	 *
	 * @return ���̑��t�@�C�����z��
	 */
	public String[] getOtherFileNmByArray() {
		if (StringUtils.isBlank(this.otherFileNm)) {
			return new String[0];
		}

		return this.otherFileNm.split(",");
	}

	/**
	 * �₢���킹���������o�^���s�����𔻒肵�܂��B
	 * 
	 * @return true�F���{����
	 */
	public boolean isToiawaseHistoryAutoRegistExcecutable() {
		return HISTORY_AUTO_REGIST_FLG_ON.equals(this.historyAutoRegistFlg);
	}

	/**
	 * �J�ڌ���ʂ��˗��������𔻒肵�܂��B
	 * 
	 * @return true�F�˗���������̑J��
	 */
	public boolean isFromRequestSearch() {
		return Constants.GAMEN_KBN_REQUEST_SEARCH.equals(getDispKbn());
	}

	/**
	 * ��ʕ\���p�̖₢���킹�m�n���擾���܂��B
	 * 
	 * @return ��ʕ\���p�̖₢���킹�m�n
	 */
	public String getToiawaseNoForDisplay() {
		return isInsert() ? this.toiawaseNo : this.toiawaseNo + "-" + this.toiawaseRirekiNo.toPlainString();
	}

	/**
	 * ��ʕ\���p�̍ŏI�X�V�����擾���܂��B
	 * 
	 * @return ��ʕ\���p�̍ŏI�X�V��
	 */
	public Timestamp getLastUpdDtForDisplay() {
		if (!isUpdate()) {
			// �X�V�\���łȂ���΁A�\�������Ȃ��i��ʂł͔�\���j
			return null;
		}

		if (this.irai == null || this.irai.getUpdDt() == null) {
			// �˗���񂪂Ȃ���΁A�\�����Ȃ��iNullpointerException����j
			return null;
		}

		if (this.sagyoJokyo == null || this.sagyoJokyo.getUpdDt() == null) {
			// ��Ə󋵏�񂪂Ȃ���΁A�˗����̍ŏI�X�V����\��
			return this.irai.getUpdDt();
		}

		if (this.irai.getUpdDt().compareTo(this.sagyoJokyo.getUpdDt()) > 0) {
			// �˗����̍ŏI�X�V������Ə󋵏��̍ŏI�X�V�������x�����t�̏ꍇ�A�˗����̍ŏI�X�V����\��
			return this.irai.getUpdDt();
		} else {
			// ��L�ȊO�̏ꍇ�A��Ə󋵏��̍ŏI�X�V����\��
			return this.sagyoJokyo.getUpdDt();
		}
	}

	/**
	 * ��ʕ\���p�̍ŏI�X�V�Җ����擾���܂��B
	 * 
	 * @return ��ʕ\���p�̍ŏI�X�V�Җ�
	 */
	public String getLastUpdNmForDisplay() {
		if (!isUpdate()) {
			// �X�V�\���łȂ���΁A�\�������Ȃ��i��ʂł͔�\���j
			return "";
		}

		if (this.irai == null || this.irai.getUpdDt() == null) {
			// �˗���񂪂Ȃ���΁A�\�����Ȃ��iNullpointerException����j
			return "";
		}

		if (this.sagyoJokyo == null || this.sagyoJokyo.getUpdDt() == null) {
			// ��Ə󋵏�񂪂Ȃ���΁A�˗����̃f�[�^��\��
			// �ŏI�X�V�Җ�������΁A�ŏI�X�V�Җ��A�Ȃ���΁A�ŏI�X�V�҂h�c�̘a���ϊ����ʂ�\��
			return StringUtils.isNotBlank(this.irai.getLastUpdNm()) ? 
					this.irai.getLastUpdNm() : this.irai.getLastUpdNmForId();
		}

		if (this.irai.getUpdDt().compareTo(this.sagyoJokyo.getUpdDt()) > 0) {
			// �˗����̍ŏI�X�V������Ə󋵏��̍ŏI�X�V�������x�����t�̏ꍇ�A�˗����̍ŏI�X�V�Җ���\��
			// �ŏI�X�V�Җ�������΁A�ŏI�X�V�Җ��A�Ȃ���΁A�ŏI�X�V�҂h�c�̘a���ϊ����ʂ�\��
			return StringUtils.isNotBlank(this.irai.getLastUpdNm()) ? 
					this.irai.getLastUpdNm() : this.irai.getLastUpdNmForId();
		} else {
			// ��L�ȊO�̏ꍇ�A��Ə󋵏��̍ŏI�X�V�Җ���\��
			// �ŏI�X�V�Җ�������΁A�ŏI�X�V�Җ��A�Ȃ���΁A�ŏI�X�V�҂h�c�̘a���ϊ����ʂ�\��
			return StringUtils.isNotBlank(this.sagyoJokyo.getLastUpdNm()) ? 
					this.sagyoJokyo.getLastUpdNm() : this.sagyoJokyo.getLastUpdNmForId();
		}
	}

	/**
	 * �Ǝҍ�ƈ˗����[�������M�\���̃R�����g��\�����邩�𔻒肵�܂��B
	 *
	 * @return true�F�\������
	 */
	public boolean isGyoshaIraiMailCommentDisplay() {
		if (this.irai == null) {
			// �˗���񂪂Ȃ��ꍇ�́A�R�����g�\�����Ȃ�
			return false;
		}

		if (this.userContext == null) {
			// ���O�C�����[�U��񂪂Ȃ��ꍇ�́A�R�����g�\�����Ȃ�
			return false;
		}

		if (!isUpdate()) {
			// �X�V�łȂ��ꍇ�i�V�K�o�^�Ȃǁj�A�R�����g�\�����Ȃ�
			return false;
		}

		if (!(this.userContext.isAdministrativeInhouse()
				|| this.userContext.isOutsourcerSv()
				|| this.userContext.isOutsourcerOp())) {
			// TOKAI�Ǘ��ҁA�ϑ����SV�AOP�ȊO�̏ꍇ�́A�R�����g�\�����Ȃ�
			return false;
		}

		// �X�V�̏ꍇ�A�Ǝҏ����`�F�b�N
		if (iraiGyosha == null) {
			// �Ǝҏ�񂪂Ȃ��ꍇ�́A�R�����g�\�����Ȃ�
			return false;
		}

		// ��ƈ˗����[�����t�L�����u1:�������M����v�̏ꍇ�A�R�����g�\��
		return iraiGyosha.isSagyoIraiMailSendable();
	}

	/**
	 * ���J���[�����M�{�^���̕\���ۂ𔻒肵�܂��B
	 * 
	 * @return true�F�\������Afalse�F�\�����Ȃ�
	 */
	public boolean isPublishMailButtonVisible() {
		if (!isUpdate()) {
			// ��ʂ��X�V�\���łȂ���΁A�\�����Ȃ�
			return false;
		}

		if (this.irai == null) {
			// �˗���񂪂Ȃ��ꍇ�́A�\�����Ȃ�
			return false;
		}

		if (getKokyakuEntity() == null) {
			// �ڋq��񂪂Ȃ��ꍇ�͕\�����Ȃ�
			return false;
		}

		if (this.userContext == null) {
			// ���O�C�����[�U��񂪂Ȃ��ꍇ�́A�\�����Ȃ�
			return false;
		}

		if (!(this.userContext.isAdministrativeInhouse()
				|| this.userContext.isOutsourcerSv()
				|| this.userContext.isOutsourcerOp())) {
			// �s�n�j�`�h�Ǘ��ҁA�ϑ���Ђr�u�A�ϑ���Ђn�o�ȊO�́A�\�����Ȃ�
			return false;
		}

		if (!(RcpTIrai.KOKAI_FLG_KOUKAIZUMI.equals(this.beforeIraiKokaiFlg))) {
			// �˗����J�t���O���u���J�ρv�łȂ���΁A�\�����Ȃ�
			// �X�V�l�i�`�F�b�NON�˃`�F�b�NOFF�j�ɂ���āA�{���\�������͂���
			// ���J���[�����M�{�^���������Ă��܂��̂ŁA��ɕύX�O�̈˗����J�t���O������
			return false;
		}

		// �ڋq�}�X�^�̌ڋq�敪���u�Ǘ����(��Ɗ܂�)�v�u�����v�u�����ҁE�l�v�̂����ꂩ�̏ꍇ�A�\��
		return (getKokyakuEntity().isKokyakuKbnFudosan()
				|| getKokyakuEntity().isKokyakuKbnBukken()
				|| getKokyakuEntity().isKokyakuKbnNyukyosha());
	}

	/**
	 * ��ʕ\���p�̍ŏI����Җ����擾���܂��B
	 * 
	 * @return ��ʕ\���p�̍ŏI����Җ�
	 */
	public String getLastPrintNmForDisplay() {
		if (!isUpdate()) {
			// ��ʂ��X�V�\���łȂ���΁A�\�����Ȃ�
			return "";
		}

		if (this.irai == null) {
			// �˗���񂪂Ȃ��ꍇ�́A�\�����Ȃ�
			return "";
		}
		// �ŏI����Җ�������΁A�ŏI����Җ��A�Ȃ���΁A�ŏI����҂h�c�̘a���ϊ����ʂ�\��
		return StringUtils.isNotBlank(this.irai.getLastPrintNm()) ? 
				this.irai.getLastPrintNm() : this.irai.getLastPrintNmForId();
	}

	/**
	 * ��Ɗ������`�F�b�N����Ă��邩�𔻒肵�܂��B
	 * 
	 * @return true�F�`�F�b�NON
	 */
	public boolean isSagyoKanryoFlgChecked() {
		if (!isUpdate()) {
			// ��ʂ��X�V�\���łȂ���΁A�\�����Ȃ�
			return false;
		}

		if (this.sagyoJokyo == null) {
			// ��Ə󋵏�񂪂Ȃ��ꍇ�́A�\�����Ȃ�
			return false;
		}

		// ��Ɗ����Ȃ�΃`�F�b�NON�A����ȊO�̏ꍇ�̓`�F�b�NOFF
		return RcpTSagyoJokyo.SAGYO_KANRYO_FLG_KANRYO.equals(this.sagyoJokyo.getSagyoKanryoFlg());
	}

	/**
	 * �摜�g�[�N���l���擾���܂��B
	 * 
	 * @return �摜�g�[�N���l
	 */
	public String getImageToken() {
		return DateUtil.getSysDateString("yyyyMMddHHmmssSSS");
	}

	/**
	 * �폜�{�^����\�����邩�̔�����s���܂��B
	 * 
	 * @return true�F�\������
	 */
	public boolean isDeleteButtonVisible() {
		if (!isUpdate()) {
			// ��ʂ��X�V�\���łȂ���΁A�\�����Ȃ�
			return false;
		}

		if (this.userContext == null) {
			// ���O�C�����[�U��񂪎擾�ł��Ȃ���΁A�\�����Ȃ�
			return false;
		}

		if (this.toiawase == null) {
			// �₢���킹��񂪎擾�ł��Ȃ���΁A�\�����Ȃ�
			return false;
		}

		// �������s�n�j�`�h�Ǘ��ҁA�ϑ���Ђr�u�̏ꍇ�́A�\��
		return (this.userContext.isAdministrativeInhouse()
				|| this.userContext.isOutsourcerSv());
	}

	/**
	 * ��ƕ񍐏�����{�^����\�����邩�̔�����s���܂��B
	 * 
	 * @return true�F�\������
	 */
	public boolean isWorkReportPrintButtonVisible() {
		if (!isUpdate()) {
			// ��ʂ��X�V�\���łȂ���΁A�\�����Ȃ�
			return false;
		}

		if (this.toiawase == null) {
			// �₢���킹��񂪎擾�ł��Ȃ���΁A�\�����Ȃ�
			return false;
		}

		if (this.sagyoJokyo == null) {
			// ��Ə󋵏�񂪎擾�ł��Ȃ���΁A�\�����Ȃ�
			return false;
		}

		// �₢���킹���̌ڋq�h�c��NOT NULL�Ȃ�\���A����ȊO�͔�\��
		return StringUtils.isNotBlank(this.toiawase.getKokyakuId());
	}

	/**
	 * �o�^�{�^����\�����邩�̔�����s���܂��B
	 * 
	 * @return true�F�\������
	 */
	public boolean isEntryButtonVisible() {
		if (this.userContext == null) {
			// ���O�C�����[�U��񂪎擾�ł��Ȃ���΁A�\�����Ȃ�
			return false;
		}

		if (this.toiawase == null) {
			// �₢���킹��񂪎擾�ł��Ȃ���΁A�\�����Ȃ�
			return false;
		}

		if (!(this.userContext.isAdministrativeInhouse()
				|| this.userContext.isOutsourcerSv())) {
			// �������s�n�j�`�h�Ǘ��ҁA�ϑ���Ђr�u�ȊO�̏ꍇ�́A�\�����Ȃ�
			return false;
		}

		// �V�K�o�^�\���A�܂��́A�X�V�\���ō�Ə󋵏�񂪂Ȃ���΁A�\��
		return isInsert() || (isUpdate() && (this.sagyoJokyo == null || StringUtils.isBlank(this.sagyoJokyo.getToiawaseNo())));
	}

	/**
	 * �X�V�{�^����\�����邩�̔�����s���܂��B
	 * 
	 * @return true�F�\������
	 */
	public boolean isUpdateButtonVisible() {
		if (!isUpdate()) {
			// ��ʂ��X�V�\���łȂ���΁A�\�����Ȃ�
			return false;
		}

		if (this.userContext == null) {
			// ���O�C�����[�U��񂪎擾�ł��Ȃ���΁A�\�����Ȃ�
			return false;
		}

		if (this.toiawase == null) {
			// �₢���킹��񂪎擾�ł��Ȃ���΁A�\�����Ȃ�
			return false;
		}

		if (!(this.userContext.isAdministrativeInhouse()
				|| this.userContext.isOutsourcerSv())) {
			// �������s�n�j�`�h�Ǘ��ҁA�ϑ���Ђr�u�ȊO�̏ꍇ�́A�\�����Ȃ�
			return false;
		}

		// �X�V�\���ō�Ə󋵏�񂪎擾�ł���΁A�\��
		return isUpdate() && (this.sagyoJokyo != null && StringUtils.isNotBlank(this.sagyoJokyo.getToiawaseNo()));
	}

	/**
	 * �J�ڌ���ʂ��₢���킹�o�^��ʂ��𔻒肵�܂��B
	 * 
	 * @return true�F�₢���킹�o�^���
	 */
	public boolean isFromInquiryEntry() {
		return Constants.GAMEN_KBN_INQUIRY_ENTRY.equals(getDispKbn());
	}

	/**
	 * �Ǝ҉񓚃R�s�[�i<<�j�{�^����\�����邩�𔻒肵�܂��B
	 * 
	 * @return true�F�\��
	 */
	public boolean isContractorAnswerCopyButtonVisible() {
		if (this.gyoshaSagyoJokyo == null) {
			// �Ǝ҉񓚍�Ə󋵏�񂪂Ȃ���΁A��\��
			return false;
		}

		return StringUtils.isNotBlank(this.gyoshaSagyoJokyo.getToiawaseNo());
	}

	/**
	 * �A�N�V�����^�C�v����ʒl�������[�h�Ȃ̂��𔻒肵�܂��B
	 * 
	 * @return true�F��ʒl�������[�h
	 */
	public boolean isRestore() {
		return Constants.ACTION_TYPE_RESTORE.equals(getActionType());
	}
	
	/**
	 * ���J���~�߂�`�F�b�N���`�F�b�N�n�m���𔻒肵�܂��B
	 * 
	 * @return true�F�`�F�b�N�n�m
	 */
	public boolean isStopPublishChecked() {
		return STOP_PUBLISH_FLG_ON.equals(this.stopPublishFlg);
	}
	
	/**
	 * �A�N�V�����^�C�v�����[�����M���[�h�Ȃ̂��𔻒肵�܂��B
	 * 
	 * @return true�F���[�����M���[�h
	 */
	public boolean isSendMail() {
		return ACTION_TYPE_SEND_MAIL.equals(getActionType());
	}
	
	/**
	 * hidden�o�͏��O���ڂ��J���}��؂�Ŏ擾���܂��B
	 *
	 * @return hidden�o�͏��O���ځi�J���}��؂�j
	 */
	public String getExcludeField() {
		return StringUtils.join(EXCLUDE_FIELD_NAME, ",");
	}

	/**
	 * "UTF-8"�̃G���R�[�f�B���O���ʂ��擾���܂��B
	 *
	 * @param value �G���R�[�f�B���O�Ώ�
	 * @return �G���R�[�f�B���O���ʁivalue��null�̏ꍇ�A""��Ԃ��j
	 */
	public String encode(String value) {
		try {
			return StringUtils.isNotBlank(value) ? (URLEncoder.encode(value, CharEncoding.UTF_8)) : "";
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}

	/**
	 * �V�X�e�����t���擾���܂��B
	 * 
	 * @return �V�X�e�����t�iyyyy/MM/dd�`���j
	 */
	public String getSysDate() {
		return DateUtil.getSysDateString("yyyy/MM/dd");
	}

	/**
	 * �G���R�[�h���ꂽ��������擾���܂��B
	 *
	 * @param orgString �G���R�[�h�O������
	 * @return �G���R�[�h�㕶����
	 */
	private String createEncodeString(String orgString) {
		if (StringUtils.isBlank(orgString)) {
			return "";
		}

		try {
			byte[] data = orgString.getBytes(CharEncoding.ISO_8859_1);

			return new String(data, CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

			return "";
		}
	}
}
