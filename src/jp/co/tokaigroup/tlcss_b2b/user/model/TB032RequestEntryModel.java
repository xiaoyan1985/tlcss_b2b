package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTOtherFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.dto.TB032FileUploadDto;
import jp.co.tokaigroup.tlcss_b2b.dto.TB032SagyoJokyoDto;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f���B
 *
 * @author k002849
 * @version 4.0 2014/07/14
 * @version 4.1 2016/03/01 J.Matsuba �摜�t�@�C���\�������̒ǉ�
 */
public class TB032RequestEntryModel extends TB040CustomerCommonInfoModel {
	/**
	 * �f�t�H���g�R���X�g���N�^�B
	 */
	public TB032RequestEntryModel() {
		super();
	}

	/**
	 * �����t���R���X�g���N�^�B
	 *
	 * @param uploadMax �A�b�v���[�h�ő匏��
	 */
	public TB032RequestEntryModel(int uploadMax) {
		super();

		this.imageFiles = new File[uploadMax];
	}

	/** ��ʖ� */
	public static final String GAMEN_NM = "�˗����e�ڍׁE��Ə󋵓o�^";

	/** ��ʖ� �˗����e�ڍ� */
	public static final String GAMEN_NM_REQUEST_DETAIL = "�˗����e�ڍ�";
	/** ��ʖ� ��Ə󋵓o�^ */
	public static final String GAMEN_NM_SAGYO_JOKYO_ENTRY = "��Ɠ��e�o�^";

	/** �J�ڌ���ʋ敪 1:�˗����� */
	private static final String DISP_KBN_REQUEST_SEARCH = "1";
	/** �J�ڌ���ʋ敪 2:�₢���킹���e�ڍ� */
	private static final String DISP_KBN_INQUIRY_DETAIL = "2";
	/** �J�ڌ���ʋ敪 3:�_�C���N�g���O�C�� */
	private static final String DISP_KBN_DIRECT_LOGIN = "3";

	/** �t�@�C���敪 1:�Ǝ҉񓚑� */
	private static final String FILE_KBN_CONTRACTOR_ANSWER = "1";
	/** �t�@�C���敪 2:TLCSS�� */
	private static final String FILE_KBN_TLCSS = "2";

	/** hidden�o�͏��O���� */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** �₢���킹NO */
	private String toiawaseNo;
	/** �₢���킹����NO */
	private BigDecimal toiawaseRirekiNo;
	/** �A�b�v���[�h�t�@�C���� */
	private String uploadFileNm;
	/** �t�@�C���C���f�b�N�X */
	private BigDecimal fileIndex;
	/** ���������i������ʂ���̕ۑ��p�p�����[�^�j */
	private RC041IraiSearchCondition condition = new RC041IraiSearchCondition();
	/** �t�@�C���敪 */
	private String fileKbn;

	/** �ڋq��� */
	private RcpMKokyaku kokyakuEntity;
	/** �₢���킹��� */
	private RcpTToiawase toiawase;
	/** �₢���킹������� */
	private RcpTToiawaseRireki toiawaseRireki;
	/** �˗���� */
	private RcpTIrai irai;
	/** ��Ə󋵏�� */
	private TB032SagyoJokyoDto sagyoJokyo;

	/** �A�b�v���[�h�ς݃t�@�C�� */
	private TB032FileUploadDto[] uploadedFiles;
	/** �˗����̑��t�@�C�����X�g */
	private RcpTOtherFileUpload[] otherUploadedFiles;
	/** �摜�t�@�C�� */
	private File[] imageFiles;
	/** �摜�t�@�C����(�J���}��؂�) */
	private String imageFileNm;

	/** �\���t�@�C������ */
	private Integer displayFileCount;
	/** �摜�t�@�C���\������ */
	private Integer imageFileDisplayFileCount;
	/** ���̑��t�@�C���\������ */
	private Integer otherFileDisplayFileCount;

	/** ���[�U�[�R���e�L�X�g */
	private TLCSSB2BUserContext userContext;

	/** �摜�t�@�C���폜�����t���O */
	private boolean successDeleteImage;

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

	/** ���[�U�[�G�[�W�F���g */
	private String userAgent;
	
	/**
	 * �₢���킹NO���擾���܂��B
	 *
	 * @return �₢���킹NO
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}
	/**
	 * �₢���킹NO��ݒ肵�܂��B
	 *
	 * @param toiawaseNo �₢���킹NO
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * �₢���킹����NO���擾���܂��B
	 *
	 * @return �₢���킹����NO
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}
	/**
	 * �₢���킹����NO��ݒ肵�܂��B
	 *
	 * @param toiawaseRirekiNo �₢���킹����NO
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
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
	 * �t�@�C���C���f�b�N�X���擾���܂��B
	 *
	 * @return �t�@�C���C���f�b�N�X
	 */
	public BigDecimal getFileIndex() {
		return fileIndex;
	}
	/**
	 * �t�@�C���C���f�b�N�X��ݒ肵�܂��B
	 *
	 * @param fileIndex �t�@�C���C���f�b�N�X
	 */
	public void setFileIndex(BigDecimal fileIndex) {
		this.fileIndex = fileIndex;
	}

	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public RC041IraiSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(RC041IraiSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �t�@�C���敪���擾���܂��B
	 *
	 * @return �t�@�C���敪
	 */
	public String getFileKbn() {
		return fileKbn;
	}
	/**
	 * �t�@�C���敪��ݒ肵�܂��B
	 *
	 * @param fileKbn �t�@�C���敪
	 */
	public void setFileKbn(String fileKbn) {
		this.fileKbn = fileKbn;
	}

	/**
	 * �ڋq�����擾���܂��B
	 *
	 * @return �ڋq���
	 */
	public RcpMKokyaku getKokyakuEntity() {
		return kokyakuEntity;
	}
	/**
	 * �ڋq����ݒ肵�܂��B
	 *
	 * @param kokyakuEntity �ڋq���
	 */
	public void setKokyakuEntity(RcpMKokyaku kokyakuEntity) {
		this.kokyakuEntity = kokyakuEntity;
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
	public TB032SagyoJokyoDto getSagyoJokyo() {
		return sagyoJokyo;
	}
	/**
	 * ��Ə󋵏���ݒ肵�܂��B
	 *
	 * @param sagyoJokyo ��Ə󋵏��
	 */
	public void setSagyoJokyo(TB032SagyoJokyoDto sagyoJokyo) {
		this.sagyoJokyo = sagyoJokyo;
	}

	/**
	 * �A�b�v���[�h�ς݃t�@�C�����擾���܂��B
	 *
	 * @return �A�b�v���[�h�ς݃t�@�C��
	 */
	public TB032FileUploadDto[] getUploadedFiles() {
		return uploadedFiles;
	}
	/**
	 * �A�b�v���[�h�ς݃t�@�C����ݒ肵�܂��B
	 *
	 * @param uploadedFiles �A�b�v���[�h�ς݃t�@�C��
	 */
	public void setUploadedFiles(TB032FileUploadDto[] uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	/**
	 * �˗����̑��t�@�C�����X�g���擾���܂��B
	 *
	 * @return �˗����̑��t�@�C�����X�g
	 */
	public RcpTOtherFileUpload[] getOtherUploadedFiles() {
		return otherUploadedFiles;
	}

	/**
	 * �˗����̑��t�@�C�����X�g��ݒ肵�܂��B
	 *
	 * @param otherUploadedFiles �˗����̑��t�@�C�����X�g
	 */
	public void setOtherUploadedFiles(RcpTOtherFileUpload[] otherUploadedFiles) {
		this.otherUploadedFiles = otherUploadedFiles;
	}


	/**
	 * �摜�t�@�C�����擾���܂��B
	 *
	 * @return �摜�t�@�C��
	 */
	public File[] getImageFiles() {
		return imageFiles;
	}
	/**
	 * �摜�t�@�C����ݒ肵�܂��B
	 *
	 * @param imageFiles �摜�t�@�C��
	 */
	public void setImageFiles(File[] imageFiles) {
		this.imageFiles = imageFiles;
	}

	/**
	 * �摜�t�@�C����(�J���}��؂�)���擾���܂��B
	 *
	 * @return �摜�t�@�C����(�J���}��؂�)
	 */
	public String getImageFileNm() {
		return imageFileNm;
	}
	/**
	 * �摜�t�@�C����(�J���}��؂�)��ݒ肵�܂��B
	 *
	 * @param imageFileNm �摜�t�@�C����(�J���}��؂�)
	 */
	public void setImageFileNm(String imageFileNm) {
		this.imageFileNm = imageFileNm;
	}

	/**
	 * �\���t�@�C���������擾���܂��B
	 *
	 * @return �\���t�@�C������
	 */
	public Integer getDisplayFileCount() {
		return displayFileCount;
	}
	/**
	 * �\���t�@�C��������ݒ肵�܂��B
	 *
	 * @param displayFileCount �\���t�@�C������
	 */
	public void setDisplayFileCount(Integer displayFileCount) {
		this.displayFileCount = displayFileCount;
	}

	/**
	 * �摜�t�@�C���\���������擾���܂��B
	 *
	 * @return �摜�t�@�C���\������
	 */
	public Integer getImageFileDisplayFileCount() {
		return imageFileDisplayFileCount;
	}

	/**
	 * �摜�t�@�C���\��������ݒ肵�܂��B
	 *
	 * @param imageFileDisplayFileCount �摜�t�@�C���\������
	 */
	public void setImageFileDisplayFileCount(Integer imageFileDisplayFileCount) {
		this.imageFileDisplayFileCount = imageFileDisplayFileCount;
	}

	/**
	 * ���̑��t�@�C���\���������擾���܂��B
	 *
	 * @return ���̑��t�@�C���\������
	 */
	public Integer getOtherFileDisplayFileCount() {
		return otherFileDisplayFileCount;
	}

	/**
	 * ���̑��t�@�C���\��������ݒ肵�܂��B
	 *
	 * @param otherFileDisplayFileCount ���̑��t�@�C���\������
	 */
	public void setOtherFileDisplayFileCount(Integer otherFileDisplayFileCount) {
		this.otherFileDisplayFileCount = otherFileDisplayFileCount;
	}

	/**
	 * �摜�t�@�C���폜�����t���O���擾���܂��B
	 *
	 * @return �摜�t�@�C���폜�����t���O
	 */
	public boolean isSuccessDeleteImage() {
		return successDeleteImage;
	}
	/**
	 * �摜�t�@�C���폜�����t���O��ݒ肵�܂��B
	 *
	 * @param successDeleteImage �摜�t�@�C���폜�����t���O
	 */
	public void setSuccessDeleteImage(boolean successDeleteImage) {
		this.successDeleteImage = successDeleteImage;
	}

	/**
	 * ���[�U�[�R���e�L�X�g���擾���܂��B
	 *
	 * @return ���[�U�[�R���e�L�X�g
	 */
	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}
	/**
	 * ���[�U�[�R���e�L�X�g��ݒ肵�܂��B
	 *
	 * @param userContext ���[�U�[�R���e�L�X�g
	 */
	public void setUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
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
	 * ���[�U�[�G�[�W�F���g���擾���܂��B
	 *
	 * @return ���[�U�[�G�[�W�F���g
	 */
	public String getUserAgent() {
		return userAgent;
	}
	/**
	 * ���[�U�[�G�[�W�F���g��ݒ肵�܂��B
	 *
	 * @param userAgent ���[�U�[�G�[�W�F���g
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * �˗�������ʂ���̑J�ڂ��𔻒肵�܂��B
	 *
	 * @return true:�˗�������ʂ���̑J�ځAfalse:����ȊO
	 */
	public boolean isFromRequestSearch() {
		return DISP_KBN_REQUEST_SEARCH.equals(getDispKbn());
	}

	/**
	 * �₢���킹���e�ڍׂ���̑J�ڂ��𔻒肵�܂��B
	 *
	 * @return true:�₢���킹���e�ڍׂ���̑J�ځAfalse:����ȊO
	 */
	public boolean isFromInquiryDetail() {
		return DISP_KBN_INQUIRY_DETAIL.equals(getDispKbn());
	}

	/**
	 * �_�C���N�g���O�C������̑J�ڂ��𔻒肵�܂��B
	 *
	 * @return true:�_�C���N�g���O�C������̑J�ځAfalse:����ȊO
	 */
	public boolean isFromDirectLogin() {
		return DISP_KBN_DIRECT_LOGIN.equals(getDispKbn());
	}

	/**
	 * ��ʏ�񂪕ҏW�\���𔻒肵�܂��B
	 *
	 * @return true:�ҏW�\�Afalse:�ҏW�s��
	 */
	public boolean isEditable() {
		if (this.userContext == null) {
			return false;
		}

		// ���[�U�[�������u10:TOKAI�Ǘ��Ҍ����v�u30:�˗��Ǝҁv�̏ꍇ�A�ҏW�\
		return (this.userContext.isAdministrativeInhouse() || this.userContext.isConstractor());
	}

	/**
	 * �˗���񂪌��J�ςłȂ������肵�܂��B
	 *
	 * @return true:���J�ςłȂ��Afalse:����ȊO
	 */
	public boolean isNotPublish() {
		return (this.toiawase == null || this.toiawaseRireki == null || this.irai == null);
	}

	/**
	 * ��ʂ̃^�C�g�����擾���܂��B
	 * ��ʂ��ҏW�\�̏ꍇ�u��Ɠ��e�o�^�v�A�ҏW�s�̏ꍇ�u�˗����e�ڍׁv���擾���܂��B
	 *
	 * @return ��ʂ̃^�C�g��
	 */
	public String getTitle() {
		return isEditable() ? GAMEN_NM_SAGYO_JOKYO_ENTRY : GAMEN_NM_REQUEST_DETAIL;
	}

	/**
	 * ��ƕ񍐏����������\���𔻒肵�܂��B
	 *
	 * @return true:����\�Afalse:����s��
	 */
	public boolean isWorkReportPrintable() {
		// ��Ə󋵏�񂪌��J�ςłȂ���΁A��ƕ񍐏�����͕\�����Ȃ�
		if (this.sagyoJokyo == null) {
			return false;
		}

		RcpTSagyoJokyo tlcssSagyoJokyo = this.sagyoJokyo.toRcpTSagyoJokyo();

		return tlcssSagyoJokyo.isReportPublished();
	}

	/**
	 * �摜�����N�ɂĐݒ肷��t�@�C���敪���擾���܂��B
	 *
	 * @return �t�@�C���敪
	 */
	public String getFileKbnForLink() {
		return isEditable() ? FILE_KBN_CONTRACTOR_ANSWER : FILE_KBN_TLCSS;
	}

	/**
	 * �t�@�C���敪���u�Ǝ҉񓚑��\���v���𔻒肵�܂��B
	 *
	 * @return true:�Ǝ҉񓚑��\���Afalse:����ȊO
	 */
	public boolean isContractorAnswerFileDisplay() {
		return FILE_KBN_CONTRACTOR_ANSWER.equals(this.fileKbn);
	}

	/**
	 * �t�@�C���敪���uTLCSS���v���𔻒肵�܂��B
	 *
	 * @return true:TLCSS���\���Afalse:����ȊO
	 */
	public boolean isTlcssFileDisplay() {
		return FILE_KBN_TLCSS.equals(this.fileKbn);
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
	 * ��Ɗ����`�F�b�N�{�b�N�X�̒l�𔻒肵�܂��B
	 *
	 * @return true:�`�F�b�NON(��Ɗ���)�Afalse:�`�F�b�NOFF(����ȊO)
	 */
	public boolean isSagyoKanryoChecked() {
		if (this.sagyoJokyo == null) {
			return false;
		}

		return RcpTSagyoJokyo.SAGYO_KANRYO_FLG_KANRYO.equals(this.sagyoJokyo.getSagyoKanryoFlg());
	}

	/**
	 * �摜�t�@�C������z��ɂ��ĕԂ��܂��B
	 *
	 * @return �摜�t�@�C�����z��
	 */
	public String[] getImageFileNmByArray() {
		if (StringUtils.isBlank(this.imageFileNm)) {
			return new String[0];
		}

		return this.imageFileNm.split(",");
	}

	public boolean isUploadExecute() {
		if (this.imageFiles == null || this.imageFiles.length == 0) {
			return false;
		}

		boolean isExecute = false;
		for (File file : this.imageFiles) {
			if (file != null) {
				isExecute = true;
				break;
			}
		}

		return isExecute;
	}

	public String getImageToken() {
		return DateUtil.getSysDateString("yyyyMMddHHmmssSSS");
	}

	/**
	 * iPad�Ń��O�C�����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:iPad�Ń��O�C���Afalse:����ȊO
	 */
	public boolean isIPad() {
		if (StringUtils.isBlank(getUserAgent())) {
			return false;
		}

		return getUserAgent().indexOf(Constants.USER_AGENT_KEY_IPAD) != -1;
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
