package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseFile;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;

/**
 * �₢���킹�ڍ׉�ʃ��f���B
 *
 * @author v140546
 * @version 1.0 2014/05/27
 * @version 1.1 2015/10/23 C.Kobayashi ���ǖ��ǋ@�\�ǉ��Ή�
 * @version 1.2 2016/07/13 H.Yamamura �G���R�[�h�ςݕ����ԍ���ǉ�
 */
public class TB022InquiryDetailModel extends TB040CustomerCommonInfoModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "�₢���킹�ڍ�";

	/** �J�ڌ���ʋ敪 1:�₢���킹���� */
	private static final String DISP_KBN_INQUIRY_SEARCH = "1";
	/** �J�ڌ���ʋ敪 2:�_�C���N�g���O�C�� */
	private static final String DISP_KBN_DIRECT_LOGIN = "2";

	/** �₢���킹NO **/
	private String toiawaseNo;

	/** �������� */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();

	/** �ڋq�e�[�u��Entity */
	private RcpMKokyaku kokyakuEntity = new RcpMKokyaku();

	/** �₢���킹�e�[�u��Entity */
	private RcpTToiawase toiawaseEntity = new RcpTToiawase();

	/** �₢���킹�����e�[�u��Entity */
	private RcpTToiawaseRireki toiawaseRirekiEntity = new RcpTToiawaseRireki();

	/** �₢���킹�����e�[�u�����X�g */
	private List<RcpTToiawaseRireki> toiawaseRirekiList;

	/** �˗��e�[�u��Map */
	private Map<String, RcpTIrai> iraiMap;

	/** �A�b�v���[�h�ςݖ₢���킹�Y�t�t�@�C�� */
	private RcpTToiawaseFile[] uploadedFiles;

	/** �t�@�C���C���f�b�N�X */
	private BigDecimal fileIndex;

	/** �₢���킹�����m�n���X�g */
	private List<BigDecimal> toiawaseRirekiNoList;

	/** �{���󋵃t���O���X�g */
	private List<String> browseStatusFlgList;

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

	/** ��������hidden�o�͏��O���� */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

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
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public RC031ToiawaseSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(RC031ToiawaseSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �ڋq�e�[�u��Entity���擾���܂��B
	 *
	 * @return �ڋq�e�[�u��Entity
	 */
	public RcpMKokyaku getKokyakuEntity() {
		return kokyakuEntity;
	}
	/**
	 * �ڋq�e�[�u��Entity��ݒ肵�܂��B
	 *
	 * @param kokyakuEntity �ڋq�e�[�u��Entity
	 */
	public void setKokyakuEntity(RcpMKokyaku kokyakuEntity) {
		this.kokyakuEntity = kokyakuEntity;
	}

	/**
	 * �₢���킹�e�[�u��Entity���擾���܂��B
	 *
	 * @return �₢���킹�e�[�u��Entity
	 */
	public RcpTToiawase getToiawaseEntity() {
		return toiawaseEntity;
	}
	/**
	 * �₢���킹�e�[�u��Entity��ݒ肵�܂��B
	 *
	 * @param toiawaseEntity �₢���킹�e�[�u��Entity
	 */
	public void setToiawaseEntity(RcpTToiawase toiawaseEntity) {
		this.toiawaseEntity = toiawaseEntity;
	}

	/**
	 * �₢���킹�����e�[�u��Entity���擾���܂��B
	 *
	 * @return �₢���킹�����e�[�u��Entity
	 */
	public RcpTToiawaseRireki getToiawaseRirekiEntity() {
		return toiawaseRirekiEntity;
	}
	/**
	 * �₢���킹�����e�[�u��Entity��ݒ肵�܂��B
	 *
	 * @param toiawaseRirekiEntity �₢���킹�����e�[�u��Entity
	 */
	public void setToiawaseRirekiEntity(RcpTToiawaseRireki toiawaseRirekiEntity) {
		this.toiawaseRirekiEntity = toiawaseRirekiEntity;
	}

	/**
	 * �₢���킹�����e�[�u��List���擾���܂��B
	 *
	 * @return �₢���킹�����e�[�u��List
	 */
	public List<RcpTToiawaseRireki> getToiawaseRirekiList() {
		return toiawaseRirekiList;
	}
	/**
	 * �₢���킹�����e�[�u��List��ݒ肵�܂��B
	 *
	 * @param toiawaseRirekiList �₢���킹�����e�[�u��List
	 */
	public void setToiawaseRirekiList(List<RcpTToiawaseRireki> toiawaseRirekiList) {
		this.toiawaseRirekiList = toiawaseRirekiList;
	}

	/**
	 * �˗��e�[�u��Map���擾���܂��B
	 *
	 * @return �˗��e�[�u��Map
	 */
	public Map<String, RcpTIrai> getIraiMap() {
		return iraiMap;
	}
	/**
	 * �˗��e�[�u��Map��ݒ肵�܂��B
	 *
	 * @param iraiMap �˗��e�[�u��Map
	 */
	public void setIraiMap(Map<String, RcpTIrai> iraiMap) {
		this.iraiMap = iraiMap;
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
	 * �₢���킹�����m�n���X�g���擾���܂��B
	 * @return �₢���킹�����m�n���X�g
	 */
	public List<BigDecimal> getToiawaseRirekiNoList() {
		return toiawaseRirekiNoList;
	}
	/**
	 * �₢���킹�����m�n���X�g��ݒ肵�܂��B
	 * @param toiawaseRirekiNoList �₢���킹�����m�n���X�g
	 */
	public void setToiawaseRirekiNoList(List<BigDecimal> toiawaseRirekiNoList) {
		this.toiawaseRirekiNoList = toiawaseRirekiNoList;
	}

	/**
	 * �{���󋵃t���O���X�g���擾���܂��B
	 * @return �{���󋵃t���O���X�g
	 */
	public List<String> getBrowseStatusFlgList() {
		return browseStatusFlgList;
	}
	/**
	 * �{���󋵃t���O���X�g��ݒ肵�܂��B
	 * @param browseStatusFlgList �{���󋵃t���O���X�g
	 */
	public void setBrowseStatusFlgList(List<String> browseStatusFlgList) {
		this.browseStatusFlgList = browseStatusFlgList;
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
	 * hidden�o�͏��O���ڂ��J���}��؂�Ŏ擾���܂��B
	 *
	 * @return hidden�o�͏��O���ځi�J���}��؂�j
	 */
	public String getExcludeField() {
		return StringUtils.join(EXCLUDE_FIELD_NAME, ",");
	}

	/**
	 * �񍐑Ώۃt���O���u1�F�񍐂���v���𔻒肵�܂��B
	 *
	 * @return true:�񍐂���Afalse:�񍐂���ȊO
	 */
	public boolean isReporting(String kokaiFlg) {
		return (RcpTToiawase.HOKOKU_TARGET_FLG_ON.equals(kokaiFlg) ? true : false);
	}

	/**
	 * �₢���킹������ʂ���̑J�ڂ��𔻒肵�܂��B
	 *
	 * @return true:�₢���킹������ʁA���j���[��ʂ���̑J�ځAfalse:����ȊO
	 */
	public boolean isFromInquirySearch() {
		return DISP_KBN_INQUIRY_SEARCH.equals(getDispKbn());
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
	 * ��ƕ񍐏����������\���𔻒肵�܂��B
	 *
	 * @return true:����\�Afalse:����s��
	 */
	public boolean canPrintWorkReport() {
		// �O���A�g ��ƕ񍐏���NOT NULL�̏ꍇ�̂ݕ\��
		return StringUtils.isNotBlank(toiawaseEntity.getExtHokokushoFileNm());
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

	/**
	 * �₢���킹�����ꗗ�����݂��邩���肵�܂��B
	 * 
	 * @return true�F���݂���B
	 */
	public boolean isToiawaseRirekiList() {
		return (this.toiawaseRirekiList != null && this.toiawaseRirekiList.size() > 0);
	}
}
