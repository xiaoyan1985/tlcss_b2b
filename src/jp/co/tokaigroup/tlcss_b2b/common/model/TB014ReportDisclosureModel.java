package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * �񍐏����J�ݒ��ʃ��f���B
 *
 * @author k002785
 * @version 1.0 2015/08/11
 */
public class TB014ReportDisclosureModel {

	/** ��ʖ��F���d�񍐏���� */
	public static final String GAMEN_NM_INCOMING_CALL_REPORT = "���d�񍐏�";

	/** ��ʖ��F��ƕ񍐏���� */
	public static final String GAMEN_NM_WORK_REPORT = "��ƕ񍐏�";
	
	/** ���[�敪�F���d�񍐏� */
	public static final String PRINT_KBN_INCOMING_CALL_REPORT = "1";
	
	/** ���[�敪�F��ƕ񍐏� */
	public static final String PRINT_KBN_WORK_REPORT = "2";
	
	/** �₢���킹NO */
	private String toiawaseNo;
	
	/** �₢���킹����NO */
	private String toiawaseRirekiNo;

	/** �ڋqID */
	private String kokyakuId;
	
	/** ���t�����̂P */
	private String senderNm1;

	/** ���t�����̂Q */
	private String senderNm2;

	/** ���t���Z�� */
	private String senderAddress;

	/** ���t���d�b�ԍ� */
	private String senderTelNo;

	/** ���t��FAX�ԍ� */
	private String senderFaxNo;
	
	/** ���[�敪 */
	private String printKbn;
	
	/** �₢���킹�X�V�� */
	private Timestamp toiawaseUpdDt;

	/** ��Ə󋵍X�V�� */
	private Timestamp sagyoJokyoUpdDt;
	
	/** PDF��URL */
	private String pdfUrl;

	/** PDF�o�̓t�@�C���p�X */
	private String outputPdfPath;

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
	 * �ڋqID���擾���܂��B
	 *
	 * @return �ڋqID
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}

	/**
	 * �ڋqID��ݒ肵�܂��B
	 *
	 * @param kokyakuId �ڋqID
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}

	/**
	 * �₢���킹����NO���擾���܂��B
	 *
	 * @return �₢���킹����NO
	 */
	public String getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}

	/**
	 * �₢���킹����NO��ݒ肵�܂��B
	 *
	 * @param toiawaseRirekiNo �₢���킹����NO
	 */
	public void setToiawaseRirekiNo(String toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}

	/**
	 * ���t�����̂P���擾���܂��B
	 *
	 * @return ���t�����̂P
	 */
	public String getSenderNm1() {
		return senderNm1;
	}

	/**
	 * ���t�����̂P��ݒ肵�܂��B
	 *
	 * @param senderNm1 ���t�����̂P
	 */
	public void setSenderNm1(String senderNm1) {
		this.senderNm1 = senderNm1;
	}

	/**
	 * ���t�����̂Q���擾���܂��B
	 *
	 * @return ���t�����̂Q
	 */
	public String getSenderNm2() {
		return senderNm2;
	}

	/**
	 * ���t�����̂Q��ݒ肵�܂��B
	 *
	 * @param senderNm2 ���t�����̂Q
	 */
	public void setSenderNm2(String senderNm2) {
		this.senderNm2 = senderNm2;
	}

	/**
	 * ���t���Z�����擾���܂��B
	 *
	 * @return ���t���Z��
	 */
	public String getSenderAddress() {
		return senderAddress;
	}

	/**
	 * ���t���Z����ݒ肵�܂��B
	 *
	 * @param senderAddress ���t���Z��
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	/**
	 * ���t���d�b�ԍ����擾���܂��B
	 *
	 * @return ���t���d�b�ԍ�
	 */
	public String getSenderTelNo() {
		return senderTelNo;
	}

	/**
	 * ���t���d�b�ԍ���ݒ肵�܂��B
	 *
	 * @param senderTelNo ���t���d�b�ԍ�
	 */
	public void setSenderTelNo(String senderTelNo) {
		this.senderTelNo = senderTelNo;
	}

	/**
	 * ���t��FAX�ԍ����擾���܂��B
	 *
	 * @return ���t��FAX�ԍ�
	 */
	public String getSenderFaxNo() {
		return senderFaxNo;
	}

	/**
	 * ���t��FAX�ԍ���ݒ肵�܂��B
	 *
	 * @param senderFaxNo ���t��FAX�ԍ�
	 */
	public void setSenderFaxNo(String senderFaxNo) {
		this.senderFaxNo = senderFaxNo;
	}

	/**
	 * ���[�敪���擾���܂��B
	 *
	 * @return ���[�敪
	 */
	public String getPrintKbn() {
		return printKbn;
	}

	/**
	 * ���[�敪��ݒ肵�܂��B
	 *
	 * @param printKbn ���[�敪
	 */
	public void setPrintKbn(String printKbn) {
		this.printKbn = printKbn;
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
	 * ��Ə󋵍X�V�����擾���܂��B
	 *
	 * @return �˗��X�V��
	 */
	public Timestamp getSagyoJokyoUpdDt() {
		return sagyoJokyoUpdDt;
	}
	/**
	 * ��Ə󋵍X�V����ݒ肵�܂��B
	 *
	 * @param iraiUpdDt �˗��X�V��
	 */
	public void setSagyoJokyoUpdDt(Timestamp sagyoJokyoUpdDt) {
		this.sagyoJokyoUpdDt = sagyoJokyoUpdDt;
	}

	/**
	 * PDF��URL���擾���܂��B
	 *
	 * @return PDF��URL
	 */
	public String getPdfUrl() {
		return pdfUrl;
	}

	/**
	 * PDF��URL��ݒ肵�܂��B
	 *
	 * @param pdfUrl PDF��URL
	 */
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	
	/**
	 * PDF�o�̓t�@�C���p�X���擾���܂��B
	 *
	 * @return PDF�o�̓t�@�C���p�X
	 */
	public String getOutputPdfPath() {
		return outputPdfPath;
	}
	/**
	 * PDF�o�̓t�@�C���p�X��ݒ肵�܂��B
	 *
	 * @param outputPdfPath PDF�o�̓t�@�C���p�X
	 */
	public void setOutputPdfPath(String outputPdfPath) {
		this.outputPdfPath = outputPdfPath;
	}

	/**
	 * ���[�敪���u���d�񍐏��v�����肵�܂��B
	 *
	 * @return true: ���[�敪���u���d�񍐏��v�̏ꍇ�Afalse�F����ȊO�̏ꍇ
	 */
	public boolean isPrintableIncomingCallReport() {
		return PRINT_KBN_INCOMING_CALL_REPORT.equals(this.printKbn);
	}

	/**
	 * ���[�敪���u��ƕ񍐏��v�����肵�܂��B
	 *
	 * @return true: ���[�敪���u��ƕ񍐏��v�̏ꍇ�Afalse�F����ȊO�̏ꍇ
	 */
	public boolean isPrintableWorkReport() {
		return PRINT_KBN_WORK_REPORT.equals(this.printKbn);
	}
	
	/**
	 * ��ʂ̃^�C�g�������擾���܂��B
	 * 
	 * @return �^�C�g����
	 */
	public String getGamenNm() {
		// ���[�敪���u���d�񍐏��v�̏ꍇ
		if (isPrintableIncomingCallReport()) {
			return GAMEN_NM_INCOMING_CALL_REPORT;
		// ���[�敪���u��ƕ񍐏��v�̏ꍇ
		} else if (isPrintableWorkReport()) {
			return GAMEN_NM_WORK_REPORT;
		} else {
			return "";
		}
	}
	
	/**
	 * PDF�_�E�����[�hURL�̃p�����[�^���擾���܂��B
	 * 
	 * @return PDF�_�E�����[�hURL�̃p�����[�^
	 */
	public String getPdfDownloadParamter() {
		StringBuilder downloadUrl = new StringBuilder("");

		downloadUrl.append("toiawaseNo=");
		downloadUrl.append(this.toiawaseNo);
		downloadUrl.append("&");
		if (isPrintableWorkReport()) {
			downloadUrl.append("toiawaseRirekiNo=");
			downloadUrl.append(this.toiawaseRirekiNo);
			downloadUrl.append("&");
		}
		downloadUrl.append("kokyakuId=");
		downloadUrl.append(this.kokyakuId);
		downloadUrl.append("&");
		downloadUrl.append("senderNm1=");
		downloadUrl.append(encode(this.senderNm1));
		downloadUrl.append("&");
		downloadUrl.append("senderNm2=");
		downloadUrl.append(encode(this.senderNm2));
		downloadUrl.append("&");
		downloadUrl.append("senderAddress=");
		downloadUrl.append(encode(this.senderAddress));
		downloadUrl.append("&");
		downloadUrl.append("senderTelNo=");
		downloadUrl.append(encode(this.senderTelNo));
		downloadUrl.append("&");
		downloadUrl.append("senderFaxNo=");
		downloadUrl.append(encode(this.senderFaxNo));
		downloadUrl.append("&");
		downloadUrl.append("printKbn=");
		downloadUrl.append(this.printKbn);
		
		return downloadUrl.toString();
	}
	
	/**
	 * "UTF-8"�̃G���R�[�f�B���O���ʂ��擾���܂��B
	 *
	 * @param value �G���R�[�f�B���O�Ώ�
	 * @return �G���R�[�f�B���O���ʁivalue��null�̏ꍇ�A""��Ԃ��j
	 */
	private String encode(String value) {
		try {
			return StringUtils.isNotBlank(value) ? URLEncoder.encode(value, CharEncoding.UTF_8) : "";
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
	public String createEncodeString(String orgString) {
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
