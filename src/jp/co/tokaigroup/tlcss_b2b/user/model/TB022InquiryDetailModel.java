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
 * 問い合わせ詳細画面モデル。
 *
 * @author v140546
 * @version 1.0 2014/05/27
 * @version 1.1 2015/10/23 C.Kobayashi 既読未読機能追加対応
 * @version 1.2 2016/07/13 H.Yamamura エンコード済み部屋番号を追加
 */
public class TB022InquiryDetailModel extends TB040CustomerCommonInfoModel {
	/** 画面名 */
	public static final String GAMEN_NM = "問い合わせ詳細";

	/** 遷移元画面区分 1:問い合わせ検索 */
	private static final String DISP_KBN_INQUIRY_SEARCH = "1";
	/** 遷移元画面区分 2:ダイレクトログイン */
	private static final String DISP_KBN_DIRECT_LOGIN = "2";

	/** 問い合わせNO **/
	private String toiawaseNo;

	/** 検索条件 */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();

	/** 顧客テーブルEntity */
	private RcpMKokyaku kokyakuEntity = new RcpMKokyaku();

	/** 問い合わせテーブルEntity */
	private RcpTToiawase toiawaseEntity = new RcpTToiawase();

	/** 問い合わせ履歴テーブルEntity */
	private RcpTToiawaseRireki toiawaseRirekiEntity = new RcpTToiawaseRireki();

	/** 問い合わせ履歴テーブルリスト */
	private List<RcpTToiawaseRireki> toiawaseRirekiList;

	/** 依頼テーブルMap */
	private Map<String, RcpTIrai> iraiMap;

	/** アップロード済み問い合わせ添付ファイル */
	private RcpTToiawaseFile[] uploadedFiles;

	/** ファイルインデックス */
	private BigDecimal fileIndex;

	/** 問い合わせ履歴ＮＯリスト */
	private List<BigDecimal> toiawaseRirekiNoList;

	/** 閲覧状況フラグリスト */
	private List<String> browseStatusFlgList;

	/** エンコード済み住所１ */
	private String encodedJusho1;
	/** エンコード済み住所２ */
	private String encodedJusho2;
	/** エンコード済み住所３ */
	private String encodedJusho3;
	/** エンコード済み住所４ */
	private String encodedJusho4;
	/** エンコード済み住所５ */
	private String encodedJusho5;
	/** エンコード済み部屋番号 */
	private String encodedRoomNo;
	/** エンコード済みカナ氏名１ */
	private String encodedKanaNm1;
	/** エンコード済みカナ氏名２ */
	private String encodedKanaNm2;
	/** エンコード済み漢字氏名１ */
	private String encodedKanjiNm1;
	/** エンコード済み漢字氏名２ */
	private String encodedKanjiNm2;

	/** 検索条件hidden出力除外項目 */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

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
	 * 検索条件を取得します。
	 *
	 * @return 検索条件
	 */
	public RC031ToiawaseSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 検索条件を設定します。
	 *
	 * @param condition 検索条件
	 */
	public void setCondition(RC031ToiawaseSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 顧客テーブルEntityを取得します。
	 *
	 * @return 顧客テーブルEntity
	 */
	public RcpMKokyaku getKokyakuEntity() {
		return kokyakuEntity;
	}
	/**
	 * 顧客テーブルEntityを設定します。
	 *
	 * @param kokyakuEntity 顧客テーブルEntity
	 */
	public void setKokyakuEntity(RcpMKokyaku kokyakuEntity) {
		this.kokyakuEntity = kokyakuEntity;
	}

	/**
	 * 問い合わせテーブルEntityを取得します。
	 *
	 * @return 問い合わせテーブルEntity
	 */
	public RcpTToiawase getToiawaseEntity() {
		return toiawaseEntity;
	}
	/**
	 * 問い合わせテーブルEntityを設定します。
	 *
	 * @param toiawaseEntity 問い合わせテーブルEntity
	 */
	public void setToiawaseEntity(RcpTToiawase toiawaseEntity) {
		this.toiawaseEntity = toiawaseEntity;
	}

	/**
	 * 問い合わせ履歴テーブルEntityを取得します。
	 *
	 * @return 問い合わせ履歴テーブルEntity
	 */
	public RcpTToiawaseRireki getToiawaseRirekiEntity() {
		return toiawaseRirekiEntity;
	}
	/**
	 * 問い合わせ履歴テーブルEntityを設定します。
	 *
	 * @param toiawaseRirekiEntity 問い合わせ履歴テーブルEntity
	 */
	public void setToiawaseRirekiEntity(RcpTToiawaseRireki toiawaseRirekiEntity) {
		this.toiawaseRirekiEntity = toiawaseRirekiEntity;
	}

	/**
	 * 問い合わせ履歴テーブルListを取得します。
	 *
	 * @return 問い合わせ履歴テーブルList
	 */
	public List<RcpTToiawaseRireki> getToiawaseRirekiList() {
		return toiawaseRirekiList;
	}
	/**
	 * 問い合わせ履歴テーブルListを設定します。
	 *
	 * @param toiawaseRirekiList 問い合わせ履歴テーブルList
	 */
	public void setToiawaseRirekiList(List<RcpTToiawaseRireki> toiawaseRirekiList) {
		this.toiawaseRirekiList = toiawaseRirekiList;
	}

	/**
	 * 依頼テーブルMapを取得します。
	 *
	 * @return 依頼テーブルMap
	 */
	public Map<String, RcpTIrai> getIraiMap() {
		return iraiMap;
	}
	/**
	 * 依頼テーブルMapを設定します。
	 *
	 * @param iraiMap 依頼テーブルMap
	 */
	public void setIraiMap(Map<String, RcpTIrai> iraiMap) {
		this.iraiMap = iraiMap;
	}

	/**
	 * アップロード済み問い合わせ添付ファイルを取得します。
	 *
	 * @return アップロード済み問い合わせ添付ファイル
	 */
	public RcpTToiawaseFile[] getUploadedFiles() {
		return uploadedFiles;
	}

	/**
	 * アップロード済み問い合わせ添付ファイルを設定します。
	 *
	 * @param uploadedFiles アップロード済み問い合わせ添付ファイル
	 */
	public void setUploadedFiles(RcpTToiawaseFile[] uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	/**
	 * ファイルインデックスを取得します。
	 *
	 * @return ファイルインデックス
	 */
	public BigDecimal getFileIndex() {
		return fileIndex;
	}

	/**
	 * ファイルインデックスを設定します。
	 *
	 * @param fileIndex ファイルインデックス
	 */
	public void setFileIndex(BigDecimal fileIndex) {
		this.fileIndex = fileIndex;
	}

	/**
	 * 問い合わせ履歴ＮＯリストを取得します。
	 * @return 問い合わせ履歴ＮＯリスト
	 */
	public List<BigDecimal> getToiawaseRirekiNoList() {
		return toiawaseRirekiNoList;
	}
	/**
	 * 問い合わせ履歴ＮＯリストを設定します。
	 * @param toiawaseRirekiNoList 問い合わせ履歴ＮＯリスト
	 */
	public void setToiawaseRirekiNoList(List<BigDecimal> toiawaseRirekiNoList) {
		this.toiawaseRirekiNoList = toiawaseRirekiNoList;
	}

	/**
	 * 閲覧状況フラグリストを取得します。
	 * @return 閲覧状況フラグリスト
	 */
	public List<String> getBrowseStatusFlgList() {
		return browseStatusFlgList;
	}
	/**
	 * 閲覧状況フラグリストを設定します。
	 * @param browseStatusFlgList 閲覧状況フラグリスト
	 */
	public void setBrowseStatusFlgList(List<String> browseStatusFlgList) {
		this.browseStatusFlgList = browseStatusFlgList;
	}

	/**
	 * エンコード済み住所１を取得します。
	 *
	 * @return エンコード済み住所１
	 */
	public String getEncodedJusho1() {
		return encodedJusho1;
	}
	/**
	 * エンコード済み住所１を設定します。
	 *
	 * @param encodedJusho1 エンコード済み住所１
	 */
	public void setEncodedJusho1(String encodedJusho1) {
		this.encodedJusho1 = createEncodeString(encodedJusho1);
	}

	/**
	 * エンコード済み住所２を取得します。
	 *
	 * @return エンコード済み住所２
	 */
	public String getEncodedJusho2() {
		return encodedJusho2;
	}
	/**
	 * エンコード済み住所２を設定します。
	 *
	 * @param encodedJusho2 エンコード済み住所２
	 */
	public void setEncodedJusho2(String encodedJusho2) {
		this.encodedJusho2 = createEncodeString(encodedJusho2);
	}

	/**
	 * エンコード済み住所３を取得します。
	 *
	 * @return エンコード済み住所３
	 */
	public String getEncodedJusho3() {
		return encodedJusho3;
	}
	/**
	 * エンコード済み住所３を設定します。
	 *
	 * @param encodedJusho3 エンコード済み住所３
	 */
	public void setEncodedJusho3(String encodedJusho3) {
		this.encodedJusho3 = createEncodeString(encodedJusho3);
	}

	/**
	 * エンコード済み住所４を取得します。
	 *
	 * @return エンコード済み住所４
	 */
	public String getEncodedJusho4() {
		return encodedJusho4;
	}
	/**
	 * エンコード済み住所４を設定します。
	 *
	 * @param encodedJusho4 エンコード済み住所４
	 */
	public void setEncodedJusho4(String encodedJusho4) {
		this.encodedJusho4 = createEncodeString(encodedJusho4);
	}

	/**
	 * エンコード済み住所５を取得します。
	 *
	 * @return エンコード済み住所５
	 */
	public String getEncodedJusho5() {
		return encodedJusho5;
	}
	/**
	 * エンコード済み住所５を設定します。
	 *
	 * @param encodedJusho5 エンコード済み住所５
	 */
	public void setEncodedJusho5(String encodedJusho5) {
		this.encodedJusho5 = createEncodeString(encodedJusho5);
	}

	/**
	 * エンコード済みカナ氏名１を取得します。
	 *
	 * @return エンコード済みカナ氏名１
	 */
	public String getEncodedKanaNm1() {
		return encodedKanaNm1;
	}
	/**
	 * エンコード済みカナ氏名１を設定します。
	 *
	 * @param encodedKanaNm1 エンコード済みカナ氏名１
	 */
	public void setEncodedKanaNm1(String encodedKanaNm1) {
		this.encodedKanaNm1 = createEncodeString(encodedKanaNm1);
	}

	/**
	 * エンコード済みカナ氏名２を取得します。
	 *
	 * @return エンコード済みカナ氏名２
	 */
	public String getEncodedKanaNm2() {
		return encodedKanaNm2;
	}
	/**
	 * エンコード済みカナ氏名２を設定します。
	 *
	 * @param encodedKanaNm2 エンコード済みカナ氏名２
	 */
	public void setEncodedKanaNm2(String encodedKanaNm2) {
		this.encodedKanaNm2 = createEncodeString(encodedKanaNm2);
	}

	/**
	 * エンコード済み漢字氏名１を取得します。
	 *
	 * @return エンコード済み漢字氏名１
	 */
	public String getEncodedKanjiNm1() {
		return encodedKanjiNm1;
	}
	/**
	 * エンコード済み漢字氏名１を設定します。
	 *
	 * @param encodedKanjiNm1 エンコード済み漢字氏名１
	 */
	public void setEncodedKanjiNm1(String encodedKanjiNm1) {
		this.encodedKanjiNm1 = createEncodeString(encodedKanjiNm1);
	}

	/**
	 * エンコード済み漢字氏名２を取得します。
	 *
	 * @return エンコード済み漢字氏名２
	 */
	public String getEncodedKanjiNm2() {
		return encodedKanjiNm2;
	}
	/**
	 * エンコード済み漢字氏名２を設定します。
	 *
	 * @param encodedKanjiNm2 エンコード済み漢字氏名２
	 */
	public void setEncodedKanjiNm2(String encodedKanjiNm2) {
		this.encodedKanjiNm2 = createEncodeString(encodedKanjiNm2);
	}

	/**
	 * エンコード済み部屋番号を取得します。
	 * @return エンコード済み部屋番号
	 */
	public String getEncodedRoomNo() {
		return encodedRoomNo;
	}

	/**
	 * エンコード済み部屋番号を設定します。
	 * @param encodedRoomNo エンコード済み部屋番号
	 */
	public void setEncodedRoomNo(String encodedRoomNo) {
		this.encodedRoomNo = createEncodeString(encodedRoomNo);
	}

	/**
	 * hidden出力除外項目をカンマ区切りで取得します。
	 *
	 * @return hidden出力除外項目（カンマ区切り）
	 */
	public String getExcludeField() {
		return StringUtils.join(EXCLUDE_FIELD_NAME, ",");
	}

	/**
	 * 報告対象フラグが「1：報告する」かを判定します。
	 *
	 * @return true:報告する、false:報告する以外
	 */
	public boolean isReporting(String kokaiFlg) {
		return (RcpTToiawase.HOKOKU_TARGET_FLG_ON.equals(kokaiFlg) ? true : false);
	}

	/**
	 * 問い合わせ検索画面からの遷移かを判定します。
	 *
	 * @return true:問い合わせ検索画面、メニュー画面からの遷移、false:それ以外
	 */
	public boolean isFromInquirySearch() {
		return DISP_KBN_INQUIRY_SEARCH.equals(getDispKbn());
	}

	/**
	 * ダイレクトログインからの遷移かを判定します。
	 *
	 * @return true:ダイレクトログインからの遷移、false:それ以外
	 */
	public boolean isFromDirectLogin() {
		return DISP_KBN_DIRECT_LOGIN.equals(getDispKbn());
	}

	/**
	 * 作業報告書印刷が印刷可能かを判定します。
	 *
	 * @return true:印刷可能、false:印刷不可
	 */
	public boolean canPrintWorkReport() {
		// 外部連携 作業報告書がNOT NULLの場合のみ表示
		return StringUtils.isNotBlank(toiawaseEntity.getExtHokokushoFileNm());
	}
	
	/**
	 * "UTF-8"のエンコーディング結果を取得します。
	 *
	 * @param value エンコーディング対象
	 * @return エンコーディング結果（valueがnullの場合、""を返す）
	 */
	public String encode(String value) {
		try {
			return StringUtils.isNotBlank(value) ? (URLEncoder.encode(value, CharEncoding.UTF_8)) : "";
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
	 * 問い合わせ履歴一覧が存在するか判定します。
	 * 
	 * @return true：存在する。
	 */
	public boolean isToiawaseRirekiList() {
		return (this.toiawaseRirekiList != null && this.toiawaseRirekiList.size() > 0);
	}
}
