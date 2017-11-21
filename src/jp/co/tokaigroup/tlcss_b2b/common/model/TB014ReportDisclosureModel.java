package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * 報告書公開設定画面モデル。
 *
 * @author k002785
 * @version 1.0 2015/08/11
 */
public class TB014ReportDisclosureModel {

	/** 画面名：入電報告書印刷 */
	public static final String GAMEN_NM_INCOMING_CALL_REPORT = "入電報告書";

	/** 画面名：作業報告書印刷 */
	public static final String GAMEN_NM_WORK_REPORT = "作業報告書";
	
	/** 帳票区分：入電報告書 */
	public static final String PRINT_KBN_INCOMING_CALL_REPORT = "1";
	
	/** 帳票区分：作業報告書 */
	public static final String PRINT_KBN_WORK_REPORT = "2";
	
	/** 問い合わせNO */
	private String toiawaseNo;
	
	/** 問い合わせ履歴NO */
	private String toiawaseRirekiNo;

	/** 顧客ID */
	private String kokyakuId;
	
	/** 送付元名称１ */
	private String senderNm1;

	/** 送付元名称２ */
	private String senderNm2;

	/** 送付元住所 */
	private String senderAddress;

	/** 送付元電話番号 */
	private String senderTelNo;

	/** 送付元FAX番号 */
	private String senderFaxNo;
	
	/** 帳票区分 */
	private String printKbn;
	
	/** 問い合わせ更新日 */
	private Timestamp toiawaseUpdDt;

	/** 作業状況更新日 */
	private Timestamp sagyoJokyoUpdDt;
	
	/** PDFのURL */
	private String pdfUrl;

	/** PDF出力ファイルパス */
	private String outputPdfPath;

	/**
	 * 問い合わせNOを取得します。
	 *
	 * @return 問い合わせNO
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * 問い合わせNOを設定します。
	 *
	 * @param toiawaseNo 問い合わせNO
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * 顧客IDを取得します。
	 *
	 * @return 顧客ID
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}

	/**
	 * 顧客IDを設定します。
	 *
	 * @param kokyakuId 顧客ID
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}

	/**
	 * 問い合わせ履歴NOを取得します。
	 *
	 * @return 問い合わせ履歴NO
	 */
	public String getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}

	/**
	 * 問い合わせ履歴NOを設定します。
	 *
	 * @param toiawaseRirekiNo 問い合わせ履歴NO
	 */
	public void setToiawaseRirekiNo(String toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}

	/**
	 * 送付元名称１を取得します。
	 *
	 * @return 送付元名称１
	 */
	public String getSenderNm1() {
		return senderNm1;
	}

	/**
	 * 送付元名称１を設定します。
	 *
	 * @param senderNm1 送付元名称１
	 */
	public void setSenderNm1(String senderNm1) {
		this.senderNm1 = senderNm1;
	}

	/**
	 * 送付元名称２を取得します。
	 *
	 * @return 送付元名称２
	 */
	public String getSenderNm2() {
		return senderNm2;
	}

	/**
	 * 送付元名称２を設定します。
	 *
	 * @param senderNm2 送付元名称２
	 */
	public void setSenderNm2(String senderNm2) {
		this.senderNm2 = senderNm2;
	}

	/**
	 * 送付元住所を取得します。
	 *
	 * @return 送付元住所
	 */
	public String getSenderAddress() {
		return senderAddress;
	}

	/**
	 * 送付元住所を設定します。
	 *
	 * @param senderAddress 送付元住所
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	/**
	 * 送付元電話番号を取得します。
	 *
	 * @return 送付元電話番号
	 */
	public String getSenderTelNo() {
		return senderTelNo;
	}

	/**
	 * 送付元電話番号を設定します。
	 *
	 * @param senderTelNo 送付元電話番号
	 */
	public void setSenderTelNo(String senderTelNo) {
		this.senderTelNo = senderTelNo;
	}

	/**
	 * 送付元FAX番号を取得します。
	 *
	 * @return 送付元FAX番号
	 */
	public String getSenderFaxNo() {
		return senderFaxNo;
	}

	/**
	 * 送付元FAX番号を設定します。
	 *
	 * @param senderFaxNo 送付元FAX番号
	 */
	public void setSenderFaxNo(String senderFaxNo) {
		this.senderFaxNo = senderFaxNo;
	}

	/**
	 * 帳票区分を取得します。
	 *
	 * @return 帳票区分
	 */
	public String getPrintKbn() {
		return printKbn;
	}

	/**
	 * 帳票区分を設定します。
	 *
	 * @param printKbn 帳票区分
	 */
	public void setPrintKbn(String printKbn) {
		this.printKbn = printKbn;
	}

	/**
	 * 問い合わせ更新日を取得します。
	 *
	 * @return 問い合わせ更新日
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}
	/**
	 * 問い合わせ更新日を設定します。
	 *
	 * @param toiawaseUpdDt 問い合わせ更新日
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}

	/**
	 * 作業状況更新日を取得します。
	 *
	 * @return 依頼更新日
	 */
	public Timestamp getSagyoJokyoUpdDt() {
		return sagyoJokyoUpdDt;
	}
	/**
	 * 作業状況更新日を設定します。
	 *
	 * @param iraiUpdDt 依頼更新日
	 */
	public void setSagyoJokyoUpdDt(Timestamp sagyoJokyoUpdDt) {
		this.sagyoJokyoUpdDt = sagyoJokyoUpdDt;
	}

	/**
	 * PDFのURLを取得します。
	 *
	 * @return PDFのURL
	 */
	public String getPdfUrl() {
		return pdfUrl;
	}

	/**
	 * PDFのURLを設定します。
	 *
	 * @param pdfUrl PDFのURL
	 */
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	
	/**
	 * PDF出力ファイルパスを取得します。
	 *
	 * @return PDF出力ファイルパス
	 */
	public String getOutputPdfPath() {
		return outputPdfPath;
	}
	/**
	 * PDF出力ファイルパスを設定します。
	 *
	 * @param outputPdfPath PDF出力ファイルパス
	 */
	public void setOutputPdfPath(String outputPdfPath) {
		this.outputPdfPath = outputPdfPath;
	}

	/**
	 * 帳票区分が「入電報告書」か判定します。
	 *
	 * @return true: 帳票区分が「入電報告書」の場合、false：それ以外の場合
	 */
	public boolean isPrintableIncomingCallReport() {
		return PRINT_KBN_INCOMING_CALL_REPORT.equals(this.printKbn);
	}

	/**
	 * 帳票区分が「作業報告書」か判定します。
	 *
	 * @return true: 帳票区分が「作業報告書」の場合、false：それ以外の場合
	 */
	public boolean isPrintableWorkReport() {
		return PRINT_KBN_WORK_REPORT.equals(this.printKbn);
	}
	
	/**
	 * 画面のタイトル名を取得します。
	 * 
	 * @return タイトル名
	 */
	public String getGamenNm() {
		// 帳票区分が「入電報告書」の場合
		if (isPrintableIncomingCallReport()) {
			return GAMEN_NM_INCOMING_CALL_REPORT;
		// 帳票区分が「作業報告書」の場合
		} else if (isPrintableWorkReport()) {
			return GAMEN_NM_WORK_REPORT;
		} else {
			return "";
		}
	}
	
	/**
	 * PDFダウンロードURLのパラメータを取得します。
	 * 
	 * @return PDFダウンロードURLのパラメータ
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
	 * "UTF-8"のエンコーディング結果を取得します。
	 *
	 * @param value エンコーディング対象
	 * @return エンコーディング結果（valueがnullの場合、""を返す）
	 */
	private String encode(String value) {
		try {
			return StringUtils.isNotBlank(value) ? URLEncoder.encode(value, CharEncoding.UTF_8) : "";
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}
	
	/**
	 * エンコードされた文字列を取得します。
	 *
	 * @param orgString エンコード前文字列
	 * @return エンコード後文字列
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
