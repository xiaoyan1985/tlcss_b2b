package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseManual;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;

/**
 * �₢���킹����o�^��ʃ��f���B
 *
 * @author v145527
 * @version 1.0 2015/08/28
 * @version 1.1 2016/07/13 H.Yamamura �₢���킹������ʂ̕��������΍�
 *
 */
public class TB024InquiryHistoryEntryModel extends TB040CustomerCommonInfoModel{
	/** ��ʖ� */
	public static final String GAMEN_NM = "�₢���킹����o�^";

	/** �₢���킹��� */
	private RcpTToiawase toiawaseInfo;
	/** �₢���킹������� */
	private RcpTToiawaseRireki toiawaseRirekiInfo;
	/** �ŏI�X�V�ҏ�� */
	private NatosMPassword lastUpdInfo;
	/** ID�����ڋq��� */
	private RcpTKokyakuWithNoId kokyakuInfoWithoutId;
	
	/** �₢���킹�������� */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();
	
	/** ��������hidden�o�͏��O���� */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** �₢���킹�X�V�� */
	private Timestamp toiawaseUpdDt;
	
	/** �₢���킹No */
	private String toiawaseNo;
	/** �₢���킹����No */
	private BigDecimal toiawaseRirekiNo;
	/** ���J���[�����M�������擾�pNo */
	private String mailRirekiNo;
	
	/** �X�V�G���[�t���O */
	private boolean updateError;
	/** ���������G���[�t���O */
	private boolean initError;
	/** �폜�����t���O */
	private boolean isDeleteCompleted = false;
	
	/** �₢���킹�������J�t���O(�ύX�O) */
	private String beforeToiawaseRirekiKokaiFlg;
	
	/** �₢���킹�}�j���A����� */
	private RcpMToiawaseManual toiawaseManual;
	/** ���J���[�����M������� */
	private RcpTMailRireki mailRireki;

	/** ���[�U��� */
	private TLCSSB2BUserContext userContext;
	/** �Ώیڋq�h�c */
	private String targetKokyakuId;
	
	/** ���[�U�}�X�^����̃��[�U��� */
	private TbMUser userInfo;

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
	
	// ���X�g���
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
	/** �S���҃��X�g */
	private List<NatosMPassword> tantoshaList;
	/** �󋵋敪���X�g */
	private List<RcpMJokyoKbn> jokyoKbnList;
	/** �˗��L�����X�g */
	private List<RcpMComCd> iraiUmuList;
	/** ��t�`�ԃ��X�g */
	private List<RcpMComCd> uketsukeKeitaiKbnList;
	

	/**
	 * �₢���킹�����擾���܂��B
	 *
	 * @return �₢���킹���
	 */
	public RcpTToiawase getToiawaseInfo() {
		return toiawaseInfo;
	}
	/**
	 * �₢���킹����ݒ肵�܂��B
	 *
	 * @param toiawaseInfo �₢���킹���
	 */
	public void setToiawaseInfo(RcpTToiawase toiawaseInfo) {
		this.toiawaseInfo = toiawaseInfo;
	}

	/**
	 * �₢���킹���������擾���܂��B
	 *
	 * @return �₢���킹�������
	 */
	public RcpTToiawaseRireki getToiawaseRirekiInfo() {
		return toiawaseRirekiInfo;
	}
	/**
	 * �₢���킹��������ݒ肵�܂��B
	 *
	 * @param toiawaseRireki �₢���킹�������
	 */
	public void setToiawaseRirekiInfo(RcpTToiawaseRireki toiawaseRirekiInfo) {
		this.toiawaseRirekiInfo = toiawaseRirekiInfo;
	}

	/**
	 * �ŏI�X�V�ҏ����擾���܂��B
	 *
	 * @return �ŏI�X�V�ҏ��
	 */
	public NatosMPassword getLastUpdInfo() {
		return lastUpdInfo;
	}
	/**
	 * �ŏI�X�V�ҏ���ݒ肵�܂��B
	 *
	 * @param lastUpdInfo �ŏI�X�V�ҏ��
	 */
	public void setLastUpdInfo(NatosMPassword lastUpdInfo) {
		this.lastUpdInfo = lastUpdInfo;
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
	 * �₢���킹No���擾���܂��B
	 *
	 * @return �₢���킹No
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}
	/**
	 * �₢���킹No��ݒ肵�܂��B
	 *
	 * @param toiawaseNo �₢���킹No
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * �₢���킹����No���擾���܂��B
	 *
	 * @return �₢���킹����No
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}
	/**
	 * �₢���킹����No��ݒ肵�܂��B
	 *
	 * @param toiawaseRirekiNo �₢���킹�������
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}
	
	/**
	 * ���[�����M�������擾�pNo���擾���܂��B
	 *
	 * @return ���[�����M�������擾�pNo
	 */
	public String getMailRirekiNo() {
		return mailRirekiNo;
	}
	/**
	 * ���[�����M�������擾�pNo��ݒ肵�܂��B
	 *
	 * @param mailRirekiNo ���[�����M�������
	 */
	public void setMailRirekiNo(String mailRirekiNo) {
		this.mailRirekiNo = mailRirekiNo;
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
	 * �X�V�G���[�t���O���擾���܂��B
	 *
	 * @return �X�V�G���[�t���O
	 */
	public boolean isUpdateError() {
		return updateError;
	}
	/**
	 * �X�V�G���[�t���O��ݒ肵�܂��B
	 *
	 * @param updateError �X�V�G���[�t���O
	 */
	public void setUpdateError(boolean updateError) {
		this.updateError = updateError;
	}

	/**
	 * ���������G���[�t���O���擾���܂��B
	 *
	 * @return ���������G���[�t���O
	 */
	public boolean isInitError() {
		return initError;
	}
	/**
	 * ���������G���[�t���O��ݒ肵�܂��B
	 *
	 * @param initError ���������G���[�t���O
	 */
	public void setInitError(boolean initError) {
		this.initError = initError;
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
	 * �₢���킹�������J�t���O(�ύX�O)���擾���܂��B
	 *
	 * @return �₢���킹�������J�t���O(�ύX�O)
	 */
	public String getBeforeToiawaseRirekiKokaiFlg() {
		return beforeToiawaseRirekiKokaiFlg;
	}
	/**
	 * �₢���킹�������J�t���O(�ύX�O)��ݒ肵�܂��B
	 *
	 * @param beforeToiawaseRirekiKokaiFlg �₢���킹�������J�t���O(�ύX�O)
	 */
	public void setBeforeToiawaseRirekiKokaiFlg(String beforeToiawaseRirekiKokaiFlg) {
		this.beforeToiawaseRirekiKokaiFlg = beforeToiawaseRirekiKokaiFlg;
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
	 * ���[�U�����擾���܂��B
	 *
	 * @return ���[�U���
	 */
	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}
	/**
	 * ���[�U����ݒ肵�܂��B
	 *
	 * @param userContext ���[�U���
	 */
	public void TLCSSB2BUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
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
	 * ���[�U�}�X�^���烆�[�U�����擾���܂��B
	 * 
	 * @return userInfo ���[�U���
	 */
	public TbMUser getUserInfo() {
		return userInfo;
	}
	/**
	 * ���[�U�}�X�^����̃��[�U����ݒ肵�܂��B
	 * 
	 * @param userInfo �Z�b�g���� �ΏیڋqID
	 */
	public void setUserInfo(TbMUser userInfo) {
		this.userInfo = userInfo;
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
	 * @return �₢���킹�敪�P���X�g
	 */
	public List<RcpMToiawaseKbn1> getToiawaseKbn1List() {
		return toiawaseKbn1List;
	}
	/**
	 * �₢���킹�敪�P���X�g��ݒ肵�܂��B
	 *
	 * @param toiawaseKbn1List �₢���킹�敪�P���X�g
	 */
	public void setToiawaseKbn1List(List<RcpMToiawaseKbn1> toiawaseKbn1List) {
		this.toiawaseKbn1List = toiawaseKbn1List;
	}

	/**
	 * �₢���킹�敪�Q���X�g���擾���܂��B
	 *
	 * @return �₢���킹�敪�Q���X�g
	 */
	public List<RcpMToiawaseKbn2> getToiawaseKbn2List() {
		return toiawaseKbn2List;
	}
	/**
	 * �₢���킹�敪�Q���X�g��ݒ肵�܂��B
	 *
	 * @param toiawaseKbn2List �₢���킹�敪�Q���X�g
	 */
	public void setToiawaseKbn2List(List<RcpMToiawaseKbn2> toiawaseKbn2List) {
		this.toiawaseKbn2List = toiawaseKbn2List;
	}

	/**
	 * �₢���킹�敪�R���X�g���擾���܂��B
	 *
	 * @return �₢���킹�敪�R���X�g
	 */
	public List<RcpMToiawaseKbn3> getToiawaseKbn3List() {
		return toiawaseKbn3List;
	}
	/**
	 * �₢���킹�敪�R���X�g��ݒ肵�܂��B
	 *
	 * @param toiawaseKbn3List �₢���킹�敪�R���X�g
	 */
	public void setToiawaseKbn3List(List<RcpMToiawaseKbn3> toiawaseKbn3List) {
		this.toiawaseKbn3List = toiawaseKbn3List;
	}

	/**
	 * �₢���킹�敪�S���X�g���擾���܂��B
	 *
	 * @return �₢���킹�敪�S���X�g
	 */
	public List<RcpMToiawaseKbn4> getToiawaseKbn4List() {
		return toiawaseKbn4List;
	}
	/**
	 * �₢���킹�敪�S���X�g��ݒ肵�܂��B
	 *
	 * @param toiawaseKbn4List �₢���킹�敪�S���X�g
	 */
	public void setToiawaseKbn4List(List<RcpMToiawaseKbn4> toiawaseKbn4List) {
		this.toiawaseKbn4List = toiawaseKbn4List;
	}

	/**
	 * �S���҃��X�g���擾���܂��B
	 *
	 * @return �S���҃��X�g
	 */
	public List<NatosMPassword> getTantoshaList() {
		return tantoshaList;
	}
	/**
	 * �S���҃��X�g��ݒ肵�܂��B
	 *
	 * @param tantoshaList �S���҃��X�g
	 */
	public void setTantoshaList(List<NatosMPassword> tantoshaList) {
		this.tantoshaList = tantoshaList;
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
	 * �񍐏��Ɉ󎚂���Ɋ܂ރ`�F�b�N���`�F�b�N����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:�`�F�b�N����Ă���Afalse:�`�F�b�N����Ă��Ȃ�
	 */
	public boolean isHoukokuPrintFlgChecked() {
		if (this.toiawaseRirekiInfo == null) {
			// �f�t�H���g�́A�`�F�b�NON
			return true;
		}

		if (StringUtils.isBlank(this.toiawaseRirekiInfo.getHoukokuPrintFlg())) {
			// �f�t�H���g�́A�`�F�b�NON
			return true;
		}

		return RcpTToiawaseRireki.HOUKOKU_PRINT_FLG_ON.equals(this.toiawaseRirekiInfo.getHoukokuPrintFlg());
	}

	/**
	 * �폜�{�^�����\�����邩���肵�܂��B
	 *
	 * @return true:�\���Afalse:��\��
	 */
	public boolean isDeleteButtonVisible() {
		if (this.toiawaseRirekiInfo == null || this.toiawaseRirekiInfo.getToiawaseRirekiNo() == null) {
			// �₢���킹������񂪂Ȃ���΁A�폜�s��
			return false;
		}
		
		if (!isUpdate()) {
			// �X�V���[�h�łȂ���΁A�폜�s��
			return false;
		}

		if (this.userContext == null) {
			// ���[�U��񂪎擾�ł��Ȃ�΁A�폜�s��
			return false;
		}

		if (!(this.userContext instanceof TLCSSB2BUserContext)) {
			// TORES�̃��[�U���łȂ���΁A�폜�s��
			return false;
		}

		if (!this.userContext.isAdministrativeInhouse() && !this.userContext.isOutsourcerSv()) {
			// �Z�b�V�����̌������s�n�j�`�h�Ǘ��ҁA�ϑ���Ђr�u�łȂ���΁A�폜�s��
			return false;
		}

		// �₢���킹�����m�n���P�ȊO�̗����̂ݍ폜�\
		return this.toiawaseRirekiInfo.getToiawaseRirekiNo().intValue() != 1;
	}

	/**
	 * �₢���킹�������J�t���O�Ɋ܂ރ`�F�b�N���`�F�b�N����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:�`�F�b�N����Ă���Afalse:�`�F�b�N����Ă��Ȃ�
	 */
	public boolean isToiawaseRirekiKokaiFlgChecked() {
		if (this.toiawaseRirekiInfo == null) {
			// �f�t�H���g�́A�`�F�b�NON
			return false;
		}

		if (StringUtils.isBlank(this.toiawaseRirekiInfo.getToiawaseRirekiKokaiFlg())) {
			// �f�t�H���g�́A�`�F�b�NON
			return false;
		}

		return RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_KOKAI.equals(this.toiawaseRirekiInfo.getToiawaseRirekiKokaiFlg());
	}

	/**
	 * ID�����ڋq���𔻒肵�܂��B
	 *
	 * @return true:ID�����ڋq�ł���Afalse:ID�L��̌ڋq�ł���
	 */
	public boolean isKokyakuWithNoIdInfoDisplay() {
		return StringUtils.isBlank(this.toiawaseInfo.getKokyakuId());
	}
	
	/**
	 * �ŏI�X�V�҂𔻒肵�܂��B
	 * 
	 * @return �ŏI�X�V��
	 */
	public String getLastUpdateName() {
		
		if (this.toiawaseRirekiInfo == null) {
			return "";
		}
		
		// �₢���킹�e�[�u��.�ŏI�X�V�Җ����ݒ肳��Ă��Ȃ��ꍇ�́A�h�c����ϊ��������̂��g�p����B
		if (StringUtils.isNotBlank(this.toiawaseRirekiInfo.getLastUpdNm())) {
			return this.toiawaseRirekiInfo.getLastUpdNm();
		} else {
			return this.toiawaseRirekiInfo.getLastUpdIdToNm();
		}
	}
	
	/**
	 * �S���Җ���\�����邩���肵�܂��B
	 * 
	 * @return true:�\������
	 */
	public boolean isTantoshaNmVisible(){
		// �₢���킹������񂪑��݂��Ȃ��ꍇ�͒S���Җ���\�����Ȃ�
		if (this.toiawaseRirekiInfo == null) {
			return false;
		}

		// �₢���킹�������̓o�^�敪���uTLCSS�v�̏ꍇ�͒S���Җ���\�����Ȃ�
		return !this.toiawaseRirekiInfo.isRegistKbnToTlcss();
	}
	
	/**
	 * ��ʂɕ\������S���Җ����擾���܂��B
	 * 
	 * @return ��ʂɕ\������S���Җ�
	 */
	public String getTantoshaNmForDisplay(){
		if (StringUtils.isNotBlank(this.toiawaseRirekiInfo.getTantoshaNm())) {
			return this.toiawaseRirekiInfo.getTantoshaNm();
		} else {
			return this.toiawaseRirekiInfo.getTantoshaNmToTantoshaId();
		}
	}
	
	/**
	 * �ҏW�\�����肷��B
	 * 
	 * @return true�F�ҏW�\
	 */
	public boolean canEditToiawaseRireki(){
		return (!this.toiawaseRirekiInfo.isRegistKbnToExternalCooperationData() && !isShimeYmExists());
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
	 * ���J���[�����M�{�^���̕\���ۂ𔻒肵�܂��B
	 * 
	 * @return true�F�\������Afalse�F�\�����Ȃ�
	 */
	public boolean isPublishMailButtonVisible() {
		if (!isUpdate()) {
			// ��ʂ��X�V�\���łȂ���΁A�\�����Ȃ�
			return false;
		}
		
		if (!RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_KOKAI.equals(this.beforeToiawaseRirekiKokaiFlg)) {
			// �₢���킹�������J�t���O���u���J�ρv�łȂ���΁A�\�����Ȃ�
			// �X�V�l�i�`�F�b�NON�˃`�F�b�NOFF�j�ɂ���āA�{���\�������͂���
			// ���J���[�����M�{�^���������Ă��܂��̂ŁA��ɕύX�O�̖₢���킹���J�t���O������
			return false;
		}
		
		RcpMKokyaku kokyaku = getKokyakuEntity();
		if (kokyaku == null) {
			// �ڋq��񂪂Ȃ��ꍇ�͕\�����Ȃ�
			return false;
		}
		
		// �ڋq�}�X�^�̌ڋq�敪���u�Ǘ����(��Ɗ܂�)�v�u�����v�u�����ҁE�l�v�̂����ꂩ�̏ꍇ�A�\��
		return (kokyaku.isKokyakuKbnFudosan() || kokyaku.isKokyakuKbnBukken() || kokyaku.isKokyakuKbnNyukyosha());
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
