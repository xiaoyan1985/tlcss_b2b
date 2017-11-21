package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseManual;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseFile;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * �₢���킹�o�^��ʃ��f���B
 *
 * @author k003316
 * @version 1.0 2015/08/11
 * @version 1.1 2016/07/13 H.Yamamura �₢���킹������ʂ̕��������΍�
 * @version 1.2 2016/07/15 H.Yamamura �T�[�r�X��ʃ��X�g�ǉ�
 */
public class TB023InquiryEntryModel extends TB040CustomerCommonInfoModel {
	
	/** ��ʖ� */
	public static final String GAMEN_NM = "�₢���킹�o�^";
	
	/** �����敪 1�F���d�񍐏� */
	public static final String SHORI_KBN_INCOMING_CALL_REPORT = "1";
	/** �����敪 2�F��ƕ񍐏� */
	public static final String SHORI_KBN_WORK_REPORT = "2";
	
	/** �R�[���������N��=�󗓎��̃��b�Z�[�W */
	private static final String MESSAGE_CALL_SEIKYU_YM_EMPTY_DISPLAY = "�����O";
	/** ���ߔN��=�󗓎��̃��b�Z�[�W */
	private static final String MESSAGE_SHIME_YM_EMPTY_DISPLAY = "���ߏ����O";
	
	/** �₢���킹�������� */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();
	
	/** ��������hidden�o�͏��O���� */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};
	
	/** �₢���킹NO */
	private String toiawaseNo;
	
	/** �₢���킹�X�V�� */
	private Timestamp toiawaseUpdDt;
	
	/** �t�@�C���C���f�b�N�X */
	private BigDecimal fileIndex;
	
	/** �A�b�v���[�h�t�@�C���� */
	private String uploadFileNm;
	
	/** ID�����ڋq��� */
	private RcpTKokyakuWithNoId kokyakuInfoWithoutId;
	
	/** �₢���킹��{��� */
	private RcpTToiawase toiawaseInfo;
	
	/** �p�X���[�h�l��� */
	private NatosMPassword passwordInfo;
	
	/** �₢���킹�������X�g */
	private List<RcpTToiawaseRireki> toiawaseRirekiList;
	
	/** �˗��L���敪(�ύX�O) */
	private String iraiUmuKbn;
	
	/** �₢���킹���J�t���O(�ύX�O) */
	private String befereToiawaseKokaiFlg;
	
	/** �񍐏����J���~�t���O(1:���J���~,0:���J�p��) */
	private String kokaiTyushiFlg;
	
	/** �˗��ҋ敪���X�g�i�ڋq��{�j */
	private List<RcpMComCd> kokyakuIraishaKbnList;
	/** �˗��ҋ敪���X�g�i�₢���킹�j */
	private List<RcpMComCd> toiawaseIraishaKbnList;
	/** �˗��҃t���O���X�g */
	private List<RcpMComCd> iraishaFlgList;
	/** �₢���킹�敪�P���X�g */
	private List<RcpMToiawaseKbn1> toiawaseKbn1List;
	/** �₢���킹�敪�Q���X�g */
	private List<RcpMToiawaseKbn2> toiawaseKbn2List;
	/** �₢���킹�敪�R���X�g */
	private List<RcpMToiawaseKbn3> toiawaseKbn3List;
	/** �₢���킹�敪�S���X�g */
	private List<RcpMToiawaseKbn4> toiawaseKbn4List;
	/** �˗��L�����X�g */
	private List<RcpMComCd> iraiUmuList;
	/** �˗��Ґ��ʃ��X�g */
	private List<RcpMComCd> iraishaSexKbnList;
	/** ��t�`�ԃ��X�g */
	private List<RcpMComCd> uketsukeKeitaiKbnList;
	/** �T�[�r�X��ʃ��X�g */
	private List<RcpMComCd> serviceShubetsuList;
	
	/** �Ώیڋq�h�c */
	private String targetKokyakuId;
	
	/** �����\���G���[�t���O */
	private boolean isInitError = false;
	/** �폜�����t���O */
	private boolean isDeleteCompleted = false;
	/** �t�@�C���폜�����t���O */
	private boolean isFileDeleteSuccess = false;
	/** �_�E�����[�h�\�t���O */
	private boolean isDownloadable = false;

	/** �₢���킹�}�j���A����� */
	private RcpMToiawaseManual toiawaseManual;
	/** ���J���[�����M������� */
	private RcpTMailRireki mailRireki;

	/** �A�b�v���[�h�ςݖ₢���킹�Y�t�t�@�C�� */
	private RcpTToiawaseFile[] uploadedFiles;
	/** �₢���킹�Y�t�t�@�C�� */
	private File[] toiawaseFiles;
	/** �₢���킹�Y�t�t�@�C����(�J���}��؂�) */
	private String toiawaseFileNm;
	
	/** �₢���킹�����m�n */
	private BigDecimal toiawaseRirekiNo;
	/** �����敪 */
	private String shoriKbn;

	/** �������b�Z�[�WID */
	private String completeMessageId;
	
	/** �������b�Z�[�W���� */
	private String completeMessageStr;

	/** �ύX��ڋq�h�c */
	private String changeKokyakuId;
	/** �ړ���₢���킹�m�n */
	private String newToiawaseNo;

	/** �ύX�O�񍐏����J�t���O */
	private String beforeHokokushoKokaiFlg;

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
	/** �G���R�[�h�ςݕ����ԍ� */
	private String encodedRoomNo;
	/** �G���R�[�h�ς݃J�i�����P */
	private String encodedKanaNm1;
	/** �G���R�[�h�ς݃J�i�����Q */
	private String encodedKanaNm2;
	/** �G���R�[�h�ς݊��������P */
	private String encodedKanjiNm1;
	/** �G���R�[�h�ς݊��������Q */
	private String encodedKanjiNm2;

	/** �T�[�r�X��ʏ����l */
	private String initServiceShubetsu;

	/**
	 * �f�t�H���g�R���X�g���N�^�B
	 */
	public TB023InquiryEntryModel() {
		super();
	}

	/**
	 * �����t���R���X�g���N�^�B
	 *
	 * @param uploadMax �A�b�v���[�h�ő匏��
	 */
	public TB023InquiryEntryModel(int uploadMax) {
		super();

		this.toiawaseFiles = new File[uploadMax];
	}
	
	/**
	 * �₢���킹�����������擾���܂��B
	 * 
	 * @return condition �₢���킹��������
	 */
	public RC031ToiawaseSearchCondition getCondition() {
		return condition;
	}

	/**
	 * �₢���킹����������ݒ肵�܂��B
	 * 
	 * @param condition �Z�b�g���� �₢���킹��������
	 */
	public void setCondition(RC031ToiawaseSearchCondition condition) {
		this.condition = condition;
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
	 * �₢���킹NO���擾���܂��B
	 * 
	 * @return toiawaseNo �₢���킹NO
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * �₢���킹NO��ݒ肵�܂��B
	 * 
	 * @param toiawaseNo �Z�b�g���� �₢���킹NO
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}
	
	/**
	 * �₢���킹�X�V�����擾���܂��B
	 * 
	 * @return toiawaseUpdDt �₢���킹�X�V��
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}

	/**
	 * �₢���킹�X�V����ݒ肵�܂��B
	 * 
	 * @param toiawaseUpdDt �Z�b�g���� �₢���킹�X�V��
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
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
	 * ID�����ڋq�����擾���܂��B
	 * 
	 * @return kokyakuInfoWithoutId ID�����ڋq���
	 */
	public RcpTKokyakuWithNoId getKokyakuInfoWithoutId() {
		return kokyakuInfoWithoutId;
	}

	/**
	 * ID�����ڋq����ݒ肵�܂��B
	 * 
	 * @param kokyakuInfoWithoutId �Z�b�g���� ID�����ڋq���
	 */
	public void setKokyakuInfoWithoutId(RcpTKokyakuWithNoId kokyakuInfoWithoutId) {
		this.kokyakuInfoWithoutId = kokyakuInfoWithoutId;
	}

	/**
	 * �₢���킹��{�����擾���܂��B
	 *
	 * @return �₢���킹��{���
	 */
	public RcpTToiawase getToiawaseInfo() {
		return toiawaseInfo;
	}
	/**
	 * �₢���킹��{����ݒ肵�܂��B
	 *
	 * @param toiawaseInfo �₢���킹��{���
	 */
	public void setToiawaseInfo(RcpTToiawase toiawaseInfo) {
		this.toiawaseInfo = toiawaseInfo;
	}
	
	/**
	 * �p�X���[�h�l�����擾���܂��B
	 * 
	 * @return passwordInfo �p�X���[�h�l���
	 */
	public NatosMPassword getPasswordInfo() {
		return passwordInfo;
	}

	/**
	 * �p�X���[�h�l����ݒ肵�܂��B
	 * 
	 * @param passwordInfo �Z�b�g���� �p�X���[�h�l���
	 */
	public void setPasswordInfo(NatosMPassword passwordInfo) {
		this.passwordInfo = passwordInfo;
	}

	/**
	 * �₢���킹�������X�g���擾���܂��B
	 * 
	 * @return toiawaseRirekiList
	 */
	public List<RcpTToiawaseRireki> getToiawaseRirekiList() {
		return toiawaseRirekiList;
	}

	/**
	 * �₢���킹�������X�g��ݒ肵�܂��B
	 * 
	 * @param toiawaseRirekiList �Z�b�g���� �₢���킹�������X�g
	 */
	public void setToiawaseRirekiList(List<RcpTToiawaseRireki> toiawaseRirekiList) {
		this.toiawaseRirekiList = toiawaseRirekiList;
	}
	
	/**
	 * �˗��L���敪(�ύX�O)���擾���܂��B
	 * 
	 * @return iraiUmuKbn
	 */
	public String getIraiUmuKbn() {
		return iraiUmuKbn;
	}

	/**
	 * �˗��L���敪(�ύX�O)��ݒ肵�܂��B
	 * 
	 * @param iraiUmuKbn �Z�b�g���� �˗��L���敪(�ύX�O)
	 */
	public void setIraiUmuKbn(String iraiUmuKbn) {
		this.iraiUmuKbn = iraiUmuKbn;
	}
	
	/**
	 * �₢���킹���J�t���O(�ύX�O)���擾���܂��B
	 *
	 * @return �₢���킹���J�t���O(�ύX�O)
	 */
	public String getBefereToiawaseKokaiFlg() {
		return befereToiawaseKokaiFlg;
	}
	/**
	 * �₢���킹���J�t���O(�ύX�O)��ݒ肵�܂��B
	 *
	 * @param befereToiawaseKokaiFlg �₢���킹���J�t���O(�ύX�O)
	 */
	public void setBefereToiawaseKokaiFlg(String befereToiawaseKokaiFlg) {
		this.befereToiawaseKokaiFlg = befereToiawaseKokaiFlg;
	}

	/**
	 * �񍐏����J���~�t���O���擾���܂��B
	 *
	 * @return �񍐏����J���~�t���O
	 */
	public String getKokaiTyushiFlg() {
		return kokaiTyushiFlg;
	}

	/**
	 * �񍐏����J���~�t���O��ݒ肵�܂��B
	 *
	 * @param kokaiTyushiFlg �񍐏����J���~�t���O
	 */
	public void setKokaiTyushiFlg(String kokaiTyushiFlg) {
		this.kokaiTyushiFlg = kokaiTyushiFlg;
	}
	
	/**
	 * �˗��ҋ敪���X�g�i�ڋq��{�j���擾���܂��B
	 * 
	 * @return kokyakuIraishaKbnList �˗��ҋ敪���X�g�i�ڋq��{�j
	 */
	public List<RcpMComCd> getKokyakuIraishaKbnList() {
		return kokyakuIraishaKbnList;
	}

	/**
	 * �˗��ҋ敪���X�g�i�ڋq��{�j��ݒ肵�܂��B
	 * 
	 * @param kokyakuIraishaKbnList �Z�b�g���� �˗��ҋ敪���X�g�i�ڋq��{�j
	 */
	public void setKokyakuIraishaKbnList(List<RcpMComCd> kokyakuIraishaKbnList) {
		this.kokyakuIraishaKbnList = kokyakuIraishaKbnList;
	}

	/**
	 * �˗��ҋ敪���X�g�i�₢���킹�j���擾���܂��B
	 * 
	 * @return toiawaseIraishaKbnList �˗��ҋ敪���X�g�i�₢���킹�j
	 */
	public List<RcpMComCd> getToiawaseIraishaKbnList() {
		return toiawaseIraishaKbnList;
	}

	/**
	 * �˗��ҋ敪���X�g�i�₢���킹�j��ݒ肵�܂��B
	 * 
	 * @param toiawaseIraishaKbnList �Z�b�g���� �˗��ҋ敪���X�g�i�₢���킹�j
	 */
	public void setToiawaseIraishaKbnList(List<RcpMComCd> toiawaseIraishaKbnList) {
		this.toiawaseIraishaKbnList = toiawaseIraishaKbnList;
	}

	/**
	 * �˗��҃t���O���X�g���擾���܂��B
	 * 
	 * @return iraishaFlgList �˗��҃t���O���X�g
	 */
	public List<RcpMComCd> getIraishaFlgList() {
		return iraishaFlgList;
	}

	/**
	 * �˗��҃t���O���X�g��ݒ肵�܂��B
	 * 
	 * @param iraishaFlgList �Z�b�g���� �˗��҃t���O���X�g
	 */
	public void setIraishaFlgList(List<RcpMComCd> iraishaFlgList) {
		this.iraishaFlgList = iraishaFlgList;
	}

	/**
	 * �₢���킹�敪�P���X�g���擾���܂��B
	 * 
	 * @return toiawaseKbn1List �₢���킹�敪�P���X�g
	 */
	public List<RcpMToiawaseKbn1> getToiawaseKbn1List() {
		return toiawaseKbn1List;
	}

	/**
	 * �₢���킹�敪�P���X�g��ݒ肵�܂��B
	 * 
	 * @param toiawaseKbn1List �Z�b�g���� �₢���킹�敪�P���X�g
	 */
	public void setToiawaseKbn1List(List<RcpMToiawaseKbn1> toiawaseKbn1List) {
		this.toiawaseKbn1List = toiawaseKbn1List;
	}

	/**
	 * �₢���킹�敪�Q���X�g���擾���܂��B
	 * 
	 * @return toiawaseKbn2List �₢���킹�敪�Q
	 */
	public List<RcpMToiawaseKbn2> getToiawaseKbn2List() {
		return toiawaseKbn2List;
	}

	/**
	 * �₢���킹�敪�Q���X�g��ݒ肵�܂��B
	 * 
	 * @param toiawaseKbn2List �Z�b�g���� �₢���킹�敪�Q
	 */
	public void setToiawaseKbn2List(List<RcpMToiawaseKbn2> toiawaseKbn2List) {
		this.toiawaseKbn2List = toiawaseKbn2List;
	}

	/**
	 * �₢���킹�敪�R���X�g���擾���܂��B
	 * 
	 * @return toiawaseKbn3List �₢���킹�敪�R
	 */
	public List<RcpMToiawaseKbn3> getToiawaseKbn3List() {
		return toiawaseKbn3List;
	}

	/**
	 * �₢���킹�敪�R���X�g��ݒ肵�܂��B
	 * 
	 * @param toiawaseKbn3List �Z�b�g���� �₢���킹�敪�R
	 */
	public void setToiawaseKbn3List(List<RcpMToiawaseKbn3> toiawaseKbn3List) {
		this.toiawaseKbn3List = toiawaseKbn3List;
	}

	/**
	 * �₢���킹�敪�S���X�g���擾���܂��B
	 * 
	 * @return toiawaseKbn4List �₢���킹�敪�S���X�g
	 */
	public List<RcpMToiawaseKbn4> getToiawaseKbn4List() {
		return toiawaseKbn4List;
	}

	/**
	 * �₢���킹�敪�S���X�g��ݒ肵�܂��B
	 * 
	 * @param toiawaseKbn4List �Z�b�g���� �₢���킹�敪�S���X�g
	 */
	public void setToiawaseKbn4List(List<RcpMToiawaseKbn4> toiawaseKbn4List) {
		this.toiawaseKbn4List = toiawaseKbn4List;
	}

	/**
	 * �˗��L�����X�g���擾���܂��B
	 * 
	 * @return iraiUmuList �˗��L�����X�g
	 */
	public List<RcpMComCd> getIraiUmuList() {
		return iraiUmuList;
	}

	/**
	 * �˗��L�����X�g��ݒ肵�܂��B
	 * 
	 * @param iraiUmuList �Z�b�g���� �˗��L�����X�g
	 */
	public void setIraiUmuList(List<RcpMComCd> iraiUmuList) {
		this.iraiUmuList = iraiUmuList;
	}

	/**
	 * �˗��Ґ��ʃ��X�g���擾���܂��B
	 * 
	 * @return iraishaSexKbnList �˗��Ґ��ʃ��X�g
	 */
	public List<RcpMComCd> getIraishaSexKbnList() {
		return iraishaSexKbnList;
	}

	/**
	 * �T�[�r�X��ʃ��X�g���擾���܂��B
	 * @return �T�[�r�X��ʃ��X�g
	 */
	public List<RcpMComCd> getServiceShubetsuList() {
		return serviceShubetsuList;
	}

	/**
	 * �T�[�r�X��ʃ��X�g��ݒ肵�܂��B
	 * @param serviceShubetsuList �T�[�r�X��ʃ��X�g
	 */
	public void setServiceShubetsuList(List<RcpMComCd> serviceShubetsuList) {
		this.serviceShubetsuList = serviceShubetsuList;
	}

	/**
	 * �˗��Ґ��ʃ��X�g��ݒ肵�܂��B
	 * 
	 * @param iraishaSexKbnList �Z�b�g���� �˗��Ґ��ʃ��X�g
	 */
	public void setIraishaSexKbnList(List<RcpMComCd> iraishaSexKbnList) {
		this.iraishaSexKbnList = iraishaSexKbnList;
	}

	/**
	 * ��t�`�ԃ��X�g���擾���܂��B
	 * 
	 * @return uketsukeKeitaiKbnList ��t�`�ԃ��X�g
	 */
	public List<RcpMComCd> getUketsukeKeitaiKbnList() {
		return uketsukeKeitaiKbnList;
	}

	/**
	 * ��t�`�ԃ��X�g��ݒ肵�܂��B
	 * 
	 * @param uketsukeKeitaiKbnList �Z�b�g���� ��t�`�ԃ��X�g
	 */
	public void setUketsukeKeitaiKbnList(List<RcpMComCd> uketsukeKeitaiKbnList) {
		this.uketsukeKeitaiKbnList = uketsukeKeitaiKbnList;
	}
	
	/**
	 * �ΏیڋqID���擾���܂��B
	 * 
	 * @return targetKokyakuId �ΏیڋqID
	 */
	public String getTargetKokyakuId() {
		return targetKokyakuId;
	}

	/**
	 * �ΏیڋqID��ݒ肵�܂��B
	 * 
	 * @param targetKokyakuId �Z�b�g���� �ΏیڋqID
	 */
	public void setTargetKokyakuId(String targetKokyakuId) {
		this.targetKokyakuId = targetKokyakuId;
	}
	
	/**
	 * �����\���G���[�t���O���擾���܂��B
	 * 
	 * @return isInitError �����\���G���[�t���O
	 */
	public boolean isInitError() {
		return isInitError;
	}

	/**
	 * �����\���G���[�t���O��ݒ肵�܂��B
	 * 
	 * @param isInitError �Z�b�g���� �����\���G���[�t���O
	 */
	public void setInitError(boolean isInitError) {
		this.isInitError = isInitError;
	}
	
	/**
	 * �폜�����t���O���擾���܂��B
	 * 
	 * @return isDeleteCompleted �폜�����t���O
	 */
	public boolean isDeleteCompleted() {
		return isDeleteCompleted;
	}

	/**
	 * �폜�����t���O��ݒ肵�܂��B
	 * 
	 * @param isDeleteCompleted �Z�b�g���� �폜�����t���O
	 */
	public void setDeleteCompleted(boolean isDeleteCompleted) {
		this.isDeleteCompleted = isDeleteCompleted;
	}

	/**
	 * �t�@�C���폜�����t���O���擾���܂��B
	 *
	 * @return �t�@�C���폜�����t���O
	 */
	public boolean isFileDeleteSuccess() {
		return isFileDeleteSuccess;
	}
	/**
	 * �t�@�C���폜�����t���O��ݒ肵�܂��B
	 *
	 * @param isFileDeleteSuccess �t�@�C���폜�����t���O
	 */
	public void setFileDeleteSuccess(boolean isFileDeleteSuccess) {
		this.isFileDeleteSuccess = isFileDeleteSuccess;
	}

	/**
	 * �_�E�����[�h�\�t���O���擾���܂��B
	 * @return �_�E�����[�h�\�t���O
	 */
	public boolean isDownloadable() {
		return isDownloadable;
	}
	/**
	 * �_�E�����[�h�\�t���O��ݒ肵�܂��B
	 * @param isDownloadable �_�E�����[�h�\�t���O
	 */
	public void setDownloadable(boolean isDownloadable) {
		this.isDownloadable = isDownloadable;
	}

	/**
	 * �₢���킹�}�j���A�������擾���܂��B
	 *
	 * @return �₢���킹�}�j���A�����
	 */
	public RcpMToiawaseManual getToiawaseManual() {
		return toiawaseManual;
	}
	/**
	 * �₢���킹�}�j���A������ݒ肵�܂��B
	 *
	 * @param toiawaseManual �₢���킹�}�j���A�����
	 */
	public void setToiawaseManual(RcpMToiawaseManual toiawaseManual) {
		this.toiawaseManual = toiawaseManual;
	}

	/**
	 * ���J���[�����M���������擾���܂��B
	 *
	 * @return ���J���[�����M�������
	 */
	public RcpTMailRireki getMailRireki() {
		return mailRireki;
	}
	/**
	 * ���J���[�����M��������ݒ肵�܂��B
	 *
	 * @param mailRireki ���J���[�����M�������
	 */
	public void setMailRireki(RcpTMailRireki mailRireki) {
		this.mailRireki = mailRireki;
	}

	/**
	 * �A�b�v���[�h�ςݖ₢���킹�Y�t�t�@�C�����擾���܂��B
	 *
	 * @return �A�b�v���[�h�ςݖ₢���킹�Y�t�t�@�C��
	 */
	public RcpTToiawaseFile[] getUploadedFiles() {
		return uploadedFiles;
	}
	/**
	 * �A�b�v���[�h�ςݖ₢���킹�Y�t�t�@�C����ݒ肵�܂��B
	 *
	 * @param uploadedFiles �A�b�v���[�h�ςݖ₢���킹�Y�t�t�@�C��
	 */
	public void setUploadedFiles(RcpTToiawaseFile[] uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	/**
	 * �₢���킹�Y�t�t�@�C�����擾���܂��B
	 *
	 * @return �₢���킹�Y�t�t�@�C��
	 */
	public File[] getToiawaseFiles() {
		return toiawaseFiles;
	}
	/**
	 * �₢���킹�Y�t�t�@�C����ݒ肵�܂��B
	 *
	 * @param toiawaseFiles �₢���킹�Y�t�t�@�C��
	 */
	public void setToiawaseFiles(File[] toiawaseFiles) {
		this.toiawaseFiles = toiawaseFiles;
	}

	/**
	 * �₢���킹�Y�t�t�@�C����(�J���}��؂�)���擾���܂��B
	 *
	 * @return �₢���킹�Y�t�t�@�C����(�J���}��؂�)
	 */
	public String getToiawaseFileNm() {
		return toiawaseFileNm;
	}
	/**
	 * �₢���킹�Y�t�t�@�C����(�J���}��؂�)��ݒ肵�܂��B
	 *
	 * @param toiawaseFileNm �₢���킹�Y�t�t�@�C����(�J���}��؂�)
	 */
	public void setToiawaseFileNm(String toiawaseFileNm) {
		this.toiawaseFileNm = toiawaseFileNm;
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
	 * �����敪���擾���܂��B
	 *
	 * @return �����敪
	 */
	public String getShoriKbn() {
		return shoriKbn;
	}
	/**
	 * �����敪��ݒ肵�܂��B
	 *
	 * @param shoriKbn �����敪
	 */
	public void setShoriKbn(String shoriKbn) {
		this.shoriKbn = shoriKbn;
	}

	/**
	 * �������b�Z�[�WID���擾���܂��B
	 * @return �������b�Z�[�WID
	 */
	public String getCompleteMessageId() {
		return completeMessageId;
	}

	/**
	 * �������b�Z�[�WID��ݒ肵�܂��B
	 * @param completeMessageId �������b�Z�[�WID
	 */
	public void setCompleteMessageId(String completeMessageId) {
		this.completeMessageId = completeMessageId;
	}

	/**
	 * �������b�Z�[�W�������擾���܂��B
	 * @return �������b�Z�[�W����
	 */
	public String getCompleteMessageStr() {
		return completeMessageStr;
	}

	/**
	 * �������b�Z�[�W�������擾���܂��B
	 * @param completeMessageStr �������b�Z�[�W����
	 */
	public void setCompleteMessageStr(String completeMessageStr) {
		this.completeMessageStr = completeMessageStr;
	}

	/**
	 * �ύX��ڋq�h�c���擾���܂��B
	 * 
	 * @return �ύX��ڋq�h�c
	 */
	public String getChangeKokyakuId() {
		return changeKokyakuId;
	}
	/**
	 * �ύX��ڋq�h�c��ݒ肵�܂��B
	 * 
	 * @param changeKokyakuId �ύX��ڋq�h�c
	 */
	public void setChangeKokyakuId(String changeKokyakuId) {
		this.changeKokyakuId = changeKokyakuId;
	}

	/**
	 * �ړ���₢���킹�m�n���擾���܂��B
	 * 
	 * @return �ړ���₢���킹�m�n
	 */
	public String getNewToiawaseNo() {
		return newToiawaseNo;
	}
	/**
	 * �ړ���₢���킹�m�n��ݒ肵�܂��B
	 * 
	 * @param newToiawaseNo �ړ���₢���킹�m�n
	 */
	public void setNewToiawaseNo(String newToiawaseNo) {
		this.newToiawaseNo = newToiawaseNo;
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
	 * �G���R�[�h�ςݕ����ԍ����擾���܂��B
	 * @return �G���R�[�h�ςݕ����ԍ�
	 */
	public String getEncodedRoomNo() {
		return encodedRoomNo;
	}

	/**
	 * �G���R�[�h�ςݕ����ԍ���ݒ肵�܂��B
	 * @param encodedRoomNo �G���R�[�h�ςݕ����ԍ�
	 */
	public void setEncodedRoomNo(String encodedRoomNo) {
		this.encodedRoomNo = createEncodeString(encodedRoomNo);
	}

	/**
	 * �ύX�O�񍐏����J�t���O���擾���܂��B
	 * @return �ύX�O�񍐏����J�t���O
	 */
	public String getBeforeHokokushoKokaiFlg() {
		return beforeHokokushoKokaiFlg;
	}
	/**
	 * �ύX�O�񍐏����J�t���O��ݒ肵�܂��B
	 * @param beforeHokokushoKokaiFlg �ύX�O�񍐏����J�t���O
	 */
	public void setBeforeHokokushoKokaiFlg(String beforeHokokushoKokaiFlg) {
		this.beforeHokokushoKokaiFlg = beforeHokokushoKokaiFlg;
	}

	/**
	 * �T�[�r�X��ʏ����l���擾���܂��B
	 * @return �T�[�r�X��ʏ����l
	 */
	public String getInitServiceShubetsu() {
		return initServiceShubetsu;
	}

	/**
	 * �T�[�r�X��ʏ����l��ݒ肵�܂��B
	 * @param initServiceShubetsu �T�[�r�X��ʏ����l
	 */
	public void setInitServiceShubetsu(String initServiceShubetsu) {
		this.initServiceShubetsu = initServiceShubetsu;
	}

	/**
	 * �J�ڌ���ʂ��₢���킹������ʂł��邩���肵�܂��B
	 * 
	 * @return true:�₢���킹�������
	 */
	public boolean isFromInquirySearch() {
		return Constants.GAMEN_KBN_INQUIRY_SEARCH.equals(getDispKbn());
	}
	
	/**
	 * �J�ڌ���ʂ��˗��o�^��ʂł��邩���肵�܂��B
	 * 
	 * @return true:�J�ڌ����˗��o�^���
	 */
	public boolean isFromRequestEntry() {
		return Constants.GAMEN_KBN_REQUEST_ENTRY.equals(getDispKbn());
	}
	
	/**
	 * �˗��L���敪���ύX���ꂽ�����肵�܂��B
	 *
	 * @return true:�ύX�� false:���ύX
	 */
	public boolean isIraiUmuKbnChanged() {
		if (StringUtils.isBlank(toiawaseInfo.getIraiUmuKbn())) {
			return false;
		}

		return !toiawaseInfo.getIraiUmuKbn().equals(iraiUmuKbn);
	}

	/**
	 * �˗��L���敪���u�����v���𔻒肵�܂��B
	 *
	 * @return true:�˗��L���敪�u�����v
	 */
	public boolean isIraiKbnNashi() {
		return RcpTToiawase.IRAI_UMU_KBN_NASHI.equals(toiawaseInfo.getIraiUmuKbn());
	}
	
	/**
	 * �X�V�{�^���̎g�p�۔�����s���܂��B
	 * 
	 * @return true:�g�p�\
	 */
	public boolean isUpdateButtonAvailable() {
		
		if (this.toiawaseInfo == null || isInsert()) {
			return false;
		}
		
		// �₢���킹�e�[�u���̓o�^�敪�`�F�b�N
		if (RcpTToiawase.REGIST_KBN_EXTERNAL_COOPERATION_DATA.equals(this.toiawaseInfo.getRegistKbn())) {
			return false;
		}
		
		// ���ߔN����NULL�Ȃ�Ύg�p�\
		return StringUtils.isBlank(this.toiawaseInfo.getShimeYm());
	}
	
	/**
	 * �₢���킹���J�t���O�Ɋ܂ރ`�F�b�N���`�F�b�N����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:�`�F�b�N����Ă���Afalse:�`�F�b�N����Ă��Ȃ�
	 */
	public boolean isToiawaseKokaiFlgChecked() {

		if (this.toiawaseInfo == null) {
			// �f�t�H���g�́A�`�F�b�NOFF
			return false;
		}

		if (StringUtils.isBlank(this.toiawaseInfo.getToiawaseKokaiFlg())) {
			// �f�t�H���g�́A�`�F�b�NOFF
			return false;
		}
		
		if (isKokyakuInfoWithoutIdVisible()) {
			// �h�c�����ڋq�̏ꍇ�́A�`�F�b�NOFF
			return false;
		}
		
		return RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI.equals(this.toiawaseInfo.getToiawaseKokaiFlg());
	}
	
	/**
	 * �񍐏����J�t���O�Ɋ܂ރ`�F�b�N���`�F�b�N����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:�`�F�b�N����Ă���Afalse:�`�F�b�N����Ă��Ȃ�
	 */
	public boolean isHoukokushoKokaiFlgChecked() {
		
		if (this.toiawaseInfo == null) {
			// �f�t�H���g�́A�`�F�b�NOFF
			return false;
		}

		if (StringUtils.isBlank(this.toiawaseInfo.getHokokushoKokaiFlg())) {
			// �f�t�H���g�́A�`�F�b�NOFF
			return false;
		}
		if (isKokyakuInfoWithoutIdVisible()) {
			// �h�c�����ڋq�̏ꍇ�́A�`�F�b�NOFF
			return false;
		}

		return RcpTToiawase.HOKOKUSHO_KOKAI_FLG_KOKAI.equals(this.toiawaseInfo.getHokokushoKokaiFlg());
	}
	
	/**
	 * �₢���킹�����ꗗ����˗��o�^��ʂɑJ�ڂł��邩���肵�܂��B
	 *
	 * @return ture:�J�ډ\�Afalse:�J�ڕs��
	 */
	public boolean canMoveToIraiEntry() {
		
		if (this.toiawaseInfo == null || isInsert()) {
			return false;
		}
		
		// �˗��L���敪���u�˗��L��v�u��z�̂݁v�ȊO�͕s��
		if (!RcpTToiawase.IRAI_UMU_KBN_ARI.equals(this.iraiUmuKbn)
				&& !RcpTToiawase.IRAI_UMU_KBN_TEHAI_NOMI.equals(this.iraiUmuKbn)) {
			return false;
		}
		
		// ���ߓ���NULL�̏ꍇ�͉\
		return StringUtils.isBlank(this.toiawaseInfo.getShimeYm());
	}
	
	/**
	 * ���d�񍐏�����{�^�����g�p�s�����肵�܂��B
	 *
	 * @return true: ���d�񍐏�����{�^�����g�p�s��
	 */
	public boolean isNyudenHoukokuPrintButtonDisabled() {
		return (isInsert() || StringUtils.isBlank(getKokyakuId()));
	}
	
	/**
	 * ���d�񍐌����Ɋ܂ރ`�F�b�N���`�F�b�N����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:�`�F�b�N����Ă���Afalse:�`�F�b�N����Ă��Ȃ�
	 */
	public boolean isHoukokuTargetFlgChecked() {
		if (this.toiawaseInfo == null) {
			// �f�t�H���g�́A�`�F�b�NON
			return true;
		}

		if (StringUtils.isBlank(this.toiawaseInfo.getHoukokuTargetFlg())) {
			// �f�t�H���g�́A�`�F�b�NON
			return true;
		}

		return RcpTToiawase.HOKOKU_TARGET_FLG_ON.equals(this.toiawaseInfo.getHoukokuTargetFlg());
	}
	
	/**
	 * ID�����ڋq����ʂ�\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isKokyakuInfoWithoutIdVisible() {
		return StringUtils.isBlank(getKokyakuId());
	}
	
	/**
	 * �ŏI�X�V�҂𔻒肵�ĕԋp���܂��B
	 * 
	 * @return �ŏI�X�V��
	 */
	public String getLastUpdateName() {
		
		if (this.toiawaseInfo == null) {
			return "";
		}
		
		// �₢���킹�e�[�u��.�ŏI�X�V�Җ����ݒ肳��Ă��Ȃ��ꍇ�́A�h�c����ϊ��������̂��g�p����B
		return StringUtils.isBlank(this.toiawaseInfo.getLastUpdNm()) ?
				this.toiawaseInfo.getLastUpdIdToNm() : this.toiawaseInfo.getLastUpdNm();
	}
	
	/**
	 * ���ߓ������݂��邩���肵�܂��B
	 * 
	 * @return true:����
	 */
	public boolean isShimeYmExists() {
		
		if (this.toiawaseInfo == null) {
			return false;
		}
		
		return StringUtils.isNotBlank(this.toiawaseInfo.getShimeYm());
	}
	
	/**
	 * ��ʕ\������A�R�[���������N�����擾���܂��B
	 * 
	 * @return �R�[���������N���i��ʕ\���p�j
	 */
	public String getCallSeikyuYmForDisplay() {
		if (this.toiawaseInfo == null) {
			// �₢���킹��񂪎擾�ł��Ȃ��ꍇ�́A��
			return "";
		}
		
		if (StringUtils.isNotBlank(this.toiawaseInfo.getCallSeikyuYm())) {
			// �R�[���������N�����擾�ł����ꍇ
			return DateUtil.yyyymmPlusSlash(this.toiawaseInfo.getCallSeikyuYm());
		} else {
			// �R�[���������N�����擾�ł��Ȃ��ꍇ
			return MESSAGE_CALL_SEIKYU_YM_EMPTY_DISPLAY;
		}
	}
	
	/**
	 * ��ʕ\������A���ߔN�����擾���܂��B
	 * 
	 * @return ���ߔN���i��ʕ\���p�j
	 */
	public String getShimeYmForDisplay() {
		if (this.toiawaseInfo == null) {
			// �₢���킹��񂪎擾�ł��Ȃ��ꍇ�́A��
			return "";
		}
		
		if (StringUtils.isNotBlank(this.toiawaseInfo.getShimeYm())) {
			// ���ߔN�����擾�ł����ꍇ
			return DateUtil.yyyymmPlusSlash(this.toiawaseInfo.getShimeYm());
		} else {
			// ���ߔN�����擾�ł��Ȃ��ꍇ
			return MESSAGE_SHIME_YM_EMPTY_DISPLAY;
		}
	}
	
	/**
	 * ��ʕ\������A��t�Җ����擾���܂��B
	 * 
	 * @return ��t�Җ��i��ʕ\���p�j
	 */
	public String getUketsukeshaNmForDisplay() {
		if (this.toiawaseInfo == null) {
			// �₢���킹��񂪎擾�ł��Ȃ��ꍇ�́A��
			return "";
		}
		
		if (StringUtils.isNotBlank(this.toiawaseInfo.getUketsukeshaNm())) {
			// ��t�Җ������݂���ꍇ�́A��t�Җ���\��
			return this.toiawaseInfo.getUketsukeshaNm();
		} else {
			// ��t�Җ������݂��Ȃ��ꍇ�́A��t�҂h�c��a���ϊ������l��\��
			return this.toiawaseInfo.getUketsukeNm();
		}
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
		
		if (this.toiawaseInfo == null) {
			// �₢���킹��񂪂Ȃ��ꍇ�́A�\�����Ȃ�
			return false;
		}
		
		RcpMKokyaku kokyaku = getKokyakuEntity();
		if (kokyaku == null) {
			// �ڋq��񂪂Ȃ��ꍇ�͕\�����Ȃ�
			return false;
		}
		
		if (!RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI.equals(this.befereToiawaseKokaiFlg)) {
			// �₢���킹���J�t���O���u���J�ρv�łȂ���΁A�\�����Ȃ�
			// �X�V�l�i�`�F�b�NON�˃`�F�b�NOFF�j�ɂ���āA�{���\�������͂���
			// ���J���[�����M�{�^���������Ă��܂��̂ŁA��ɕύX�O�̖₢���킹���J�t���O������
			return false;
		}
		
		// �ڋq�}�X�^�̌ڋq�敪���u�Ǘ����(��Ɗ܂�)�v�u�����v�u�����ҁE�l�v�̂����ꂩ�̏ꍇ�A�\��
		return (kokyaku.isKokyakuKbnFudosan() || kokyaku.isKokyakuKbnBukken() || kokyaku.isKokyakuKbnNyukyosha());
	}
	
	/**
	 * �₢���킹�Y�t�t�@�C������z��ɂ��ĕԂ��܂��B
	 *
	 * @return �₢���킹�Y�t�t�@�C�����z��
	 */
	public String[] getToiawaseFileNmByArray() {
		if (StringUtils.isBlank(this.toiawaseFileNm)) {
			return new String[0];
		}

		return this.toiawaseFileNm.split(",");
	}
	
	/**
	 * �t�@�C���A�b�v���[�h���������s���邩�𔻒肵�܂��B
	 * 
	 * @return true�F���������s����
	 */
	public boolean isExecuteFileUpload() {
		if (this.toiawaseFiles == null || this.toiawaseFiles.length == 0) {
			return false;
		}

		for (File file : this.toiawaseFiles) {
			if (file != null) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * ��ƕ񍐏�����{�^�����\���\���𔻒肵�܂��B
	 * 
	 * @return true�F�\���\
	 */
	public boolean isWorkReportButtonVisible() {
		if (this.toiawaseInfo == null) {
			// �₢���킹��񂪑��݂��Ȃ��ꍇ�́A�\�����Ȃ�
			return false;
		}
		
		return StringUtils.isNotBlank(this.toiawaseInfo.getExtHokokushoFileNm());
	}
	
	/**
	 * �ύX�O�₢���킹���J�t���O�����J�ς��𔻒肵�܂��B
	 * 
	 * @return true�F���J��
	 */
	public boolean isBeforeToiawasePublished() {
		return RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI.equals(this.befereToiawaseKokaiFlg);
	}
	
	/**
	 * �ύX�O�񍐏��t���O�����J�ς��𔻒肵�܂��B
	 * 
	 * @return true�F���J��
	 */
	public boolean isBeforeHokokushoPublished() {
		return RcpTToiawase.HOKOKUSHO_KOKAI_FLG_KOKAI.equals(this.beforeHokokushoKokaiFlg);
	}
	
	/**
	 * ���J���~�߂�`�F�b�N���`�F�b�N�n�m���𔻒肵�܂��B
	 * 
	 * @return true�F�`�F�b�N�n�m
	 */
	public boolean isStopPublishChecked() {
		return RcpTToiawase.KOKAI_TYUSHI_FLG.equals(this.kokaiTyushiFlg);
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
