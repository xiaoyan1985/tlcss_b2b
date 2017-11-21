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
 * 依頼登録画面モデル。
 * 
 * @author S.Nakano
 * @version 1.0 2015/10/21 S.Nakano
 * @version 1.1 2016/02/12 C.Kobayashi ファイルアップロード時のプライマリーキー変更対応
 * @version 1.2 2016/07/13 H.Yamamura エンコード済み問い合わせ部屋番号を追加
 */
public class TB033RequestFullEntryModel extends TB040CustomerCommonInfoModel {
	/**
	 * デフォルトコンストラクタ。
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

	/** 画像ファイル最大件数 */
	public static final int IMAGE_FILE_MAX_COUNT = 3;
	/** 画面名 */
	public static final String GAMEN_NM = "依頼登録";
	/** ボタン名 業者依頼メール送信 */
	public static final String BUTTON_NM_IRAI_MAIL = "センター業者依頼メール送信";
	/** 公開を止めるフラグ 1:チェックON */
	public static final String STOP_PUBLISH_FLG_ON = "1";
	/** 履歴自動登録フラグ 1:チェックON */
	public static final String HISTORY_AUTO_REGIST_FLG_ON = "1";
	/** アクションタイプ sendMail：メール送信 */
	public static final String ACTION_TYPE_SEND_MAIL = "sendMail";

	/** 検索条件hidden出力除外項目 */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** 問い合わせＮＯ */
	private String toiawaseNo;
	/** 問い合わせ履歴ＮＯ */
	private BigDecimal toiawaseRirekiNo;
	/** 問い合わせ更新日 */
	private Timestamp toiawaseUpdDt;
	/** アップロードファイル名 */
	private String uploadFileNm;
	/** 依頼更新日 */
	private Timestamp iraiUpdDt;
	/** 作業状況更新日 */
	private Timestamp sagyoJokyoUpdDt;

	/** 依頼検索条件 */
	private RC041IraiSearchCondition condition = new RC041IraiSearchCondition();
	/** 問い合わせ検索条件 */
	private RC031ToiawaseSearchCondition toiawaseCondition = new RC031ToiawaseSearchCondition();

	/** ＩＤ無し顧客情報 */
	private RcpTKokyakuWithNoId kokyakuWithoutId;
	/** 問い合わせ情報 */
	private RcpTToiawase toiawase;
	/** 問い合わせ履歴情報 */
	private RcpTToiawaseRireki toiawaseRireki;
	/** 依頼情報 */
	private RcpTIrai irai;
	/** 作業状況情報 */
	private RcpTSagyoJokyo sagyoJokyo;
	/** アップロード済み作業状況画像ファイル */
	private RcpTFileUpload[] uploadedFiles;
	/** 作業状況画像ファイル */
	private File[] sagyoJokyoImageFiles;
	/** 作業状況画像ファイル(カンマ区切り) */
	private String sagyoJokyoImageFileNm;
	/** 作業状況画像ファイルコメント */
	// 個別に処理をするため、オブジェクトを分けている
	private String[] sagyoJokyoImageFileComments;
	/** 作業状況画像ファイルアップロード名 */
	private String[] sagyoJokyoImageFileUploadFileNms;
	/** 作業状況画像ファイルインデックス */
	private BigDecimal[] sagyoJokyoImageFileFileIndexes;
	/** アップロード済みその他ファイル */
	private RcpTOtherFileUpload[] uploadedOtherFiles;
	/** その他ファイル */
	private File[] otherFiles;
	/** その他ファイル名(カンマ区切り) */
	private String otherFileNm;
	/** その他ファイルコメント */
	// 個別に処理をするため、オブジェクトを分けている
	private String[] otherFileComments;
	/** その他ファイルアップロード名 */
	private String[] otherFileUploadFileNms;
	/** その他ファイルインデックス */
	private BigDecimal[] otherFileUploadFileFileIndexes;
	/** ファイルインデックス */
	private BigDecimal fileIndex;
	/** メール履歴情報 */
	private RcpTMailRireki mailRireki;
	/** 依頼業者情報 */
	private RcpMGyosha iraiGyosha;
	/** 業者回答作業状況情報 */
	private TbTSagyoJokyo gyoshaSagyoJokyo;
	/** 業者回答アップロードファイルリスト */
	private List<TbTFileUpload> gyoshaUploadFileList;
	/** ログインユーザ情報 */
	private TLCSSB2BUserContext userContext;

	/** 公開を止めるフラグ */
	private String stopPublishFlg;
	/** 履歴自動登録フラグ */
	private String historyAutoRegistFlg;
	/** 履歴自動登録状況区分 */
	private String historyAutoRegistJokyoKbn;

	/** 訪問希望リスト */
	private List<RcpMComCd> homonKiboList;
	/** 業者回答リスト */
	private List<RcpMComCd> gyoshaKaitoList;
	/** 状況区分リスト */
	private List<RcpMJokyoKbn> jokyoKbnList;

	/** 初期表示エラーフラグ */
	private boolean isInitError;
	/** 削除完了フラグ */
	private boolean isDeleteCompleted;
	/** ファイル削除エラー */
	private boolean fileDeleteError;
	/** 作業依頼書印刷フラグ */
	private boolean workRequestPrinted;
	
	/** 帳票生成パス */
	private String makePdfPath;
	/** 帳票名 */
	private String pdfNm;
	/** 変更前依頼公開フラグ */
	private String beforeIraiKokaiFlg;

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
	/** エンコード済みカナ氏名１ */
	private String encodedKanaNm1;
	/** エンコード済みカナ氏名２ */
	private String encodedKanaNm2;
	/** エンコード済み漢字氏名１ */
	private String encodedKanjiNm1;
	/** エンコード済み漢字氏名２ */
	private String encodedKanjiNm2;

	/** エンコード済み問い合わせ検索住所１ */
	private String encodedToiawaseJusho1;
	/** エンコード済み問い合わせ検索住所２ */
	private String encodedToiawaseJusho2;
	/** エンコード済み問い合わせ検索住所３ */
	private String encodedToiawaseJusho3;
	/** エンコード済み問い合わせ検索住所４ */
	private String encodedToiawaseJusho4;
	/** エンコード済み問い合わせ検索住所５ */
	private String encodedToiawaseJusho5;
	/** エンコード済み問い合わせ部屋番号 */
	private String encodedToiawaseRoomNo;
	/** エンコード済み問い合わせ検索カナ氏名１ */
	private String encodedToiawaseKanaNm1;
	/** エンコード済み問い合わせ検索カナ氏名２ */
	private String encodedToiawaseKanaNm2;
	/** エンコード済み問い合わせ検索漢字氏名１ */
	private String encodedToiawaseKanjiNm1;
	/** エンコード済み問い合わせ検索漢字氏名２ */
	private String encodedToiawaseKanjiNm2;


	/** 遷移元画面が保持していた遷移元画面区分 */
	private String rootDispKbn;

	/**
	 * 問い合わせＮＯを取得します。
	 * 
	 * @return 問い合わせＮＯ
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}
	/**
	 * 問い合わせＮＯを設定します。
	 * 
	 * @param toiawaseNo 問い合わせＮＯ
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * 問い合わせ履歴ＮＯを取得します。
	 * 
	 * @return 問い合わせ履歴ＮＯ
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}
	/**
	 * 問い合わせ履歴ＮＯを設定します。
	 * 
	 * @param toiawaseRirekiNo 問い合わせ履歴ＮＯ
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
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
	 * アップロードファイル名を取得します。
	 * 
	 * @return アップロードファイル名
	 */
	public String getUploadFileNm() {
		return uploadFileNm;
	}
	/**
	 * アップロードファイル名を設定します。
	 * 
	 * @param uploadFileNm アップロードファイル名
	 */
	public void setUploadFileNm(String uploadFileNm) {
		this.uploadFileNm = uploadFileNm;
	}

	/**
	 * 依頼更新日を取得します。
	 * 
	 * @return 依頼更新日
	 */
	public Timestamp getIraiUpdDt() {
		return iraiUpdDt;
	}
	/**
	 * 依頼更新日を設定します。
	 * 
	 * @param iraiUpdDt 依頼更新日
	 */
	public void setIraiUpdDt(Timestamp iraiUpdDt) {
		this.iraiUpdDt = iraiUpdDt;
	}

	/**
	 * 作業状況更新日を取得します。
	 * 
	 * @return 作業状況更新日
	 */
	public Timestamp getSagyoJokyoUpdDt() {
		return sagyoJokyoUpdDt;
	}
	/**
	 * 作業状況更新日を設定します。
	 * 
	 * @param sagyoJokyoUpdDt 作業状況更新日
	 */
	public void setSagyoJokyoUpdDt(Timestamp sagyoJokyoUpdDt) {
		this.sagyoJokyoUpdDt = sagyoJokyoUpdDt;
	}

	/**
	 * 依頼検索条件を取得します。
	 * 
	 * @return 依頼検索条件
	 */
	public RC041IraiSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 依頼検索条件を設定します。
	 * 
	 * @param condition 依頼検索条件
	 */
	public void setCondition(RC041IraiSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 問い合わせ検索条件を取得します。
	 * @return 問い合わせ検索条件
	 */
	public RC031ToiawaseSearchCondition getToiawaseCondition() {
		return toiawaseCondition;
	}
	/**
	 * 問い合わせ検索条件を設定します。
	 * @param toiawaseCondition 問い合わせ検索条件
	 */
	public void setToiawaseCondition(RC031ToiawaseSearchCondition toiawaseCondition) {
		this.toiawaseCondition = toiawaseCondition;
	}

	/**
	 * ＩＤ無し顧客情報を取得します。
	 * 
	 * @return ＩＤ無し顧客情報
	 */
	public RcpTKokyakuWithNoId getKokyakuWithoutId() {
		return kokyakuWithoutId;
	}
	/**
	 * ＩＤ無し顧客情報を設定します。
	 * 
	 * @param kokyakuWithoutId ＩＤ無し顧客情報
	 */
	public void setKokyakuWithoutId(RcpTKokyakuWithNoId kokyakuWithoutId) {
		this.kokyakuWithoutId = kokyakuWithoutId;
	}

	/**
	 * 問い合わせ情報を取得します。
	 * 
	 * @return 問い合わせ情報
	 */
	public RcpTToiawase getToiawase() {
		return toiawase;
	}
	/**
	 * 問い合わせ情報を設定します。
	 * 
	 * @param toiawase 問い合わせ情報
	 */
	public void setToiawase(RcpTToiawase toiawase) {
		this.toiawase = toiawase;
	}

	/**
	 * 問い合わせ履歴情報を取得します。
	 * 
	 * @return 問い合わせ履歴情報
	 */
	public RcpTToiawaseRireki getToiawaseRireki() {
		return toiawaseRireki;
	}
	/**
	 * 問い合わせ履歴情報を設定します。
	 * 
	 * @param toiawaseRireki 問い合わせ履歴情報
	 */
	public void setToiawaseRireki(RcpTToiawaseRireki toiawaseRireki) {
		this.toiawaseRireki = toiawaseRireki;
	}

	/**
	 * 依頼情報を取得します。
	 * 
	 * @return 依頼情報
	 */
	public RcpTIrai getIrai() {
		return irai;
	}
	/**
	 * 依頼情報を設定します。
	 * 
	 * @param irai 依頼情報
	 */
	public void setIrai(RcpTIrai irai) {
		this.irai = irai;
	}

	/**
	 * 作業状況情報を取得します。
	 * 
	 * @return 作業状況情報
	 */
	public RcpTSagyoJokyo getSagyoJokyo() {
		return sagyoJokyo;
	}
	/**
	 * 作業状況情報を設定します。
	 * 
	 * @param sagyoJokyo 作業状況情報
	 */
	public void setSagyoJokyo(RcpTSagyoJokyo sagyoJokyo) {
		this.sagyoJokyo = sagyoJokyo;
	}

	/**
	 * アップロード済み作業状況画像ファイルを取得します。
	 * 
	 * @return アップロード済み作業状況画像ファイル
	 */
	public RcpTFileUpload[] getUploadedFiles() {
		return uploadedFiles;
	}
	/**
	 * アップロード済み作業状況画像ファイルを設定します。
	 * 
	 * @param uploadedFiles アップロード済み作業状況画像ファイル
	 */
	public void setUploadedFiles(RcpTFileUpload[] uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	/**
	 * 作業状況画像ファイルを取得します。
	 * 
	 * @return 作業状況画像ファイル
	 */
	public File[] getSagyoJokyoImageFiles() {
		return sagyoJokyoImageFiles;
	}
	/**
	 * 作業状況画像ファイルを設定します。
	 * 
	 * @param sagyoJokyoImageFiles 作業状況画像ファイル
	 */
	public void setSagyoJokyoImageFiles(File[] sagyoJokyoImageFiles) {
		this.sagyoJokyoImageFiles = sagyoJokyoImageFiles;
	}

	/**
	 * 作業状況画像ファイル(カンマ区切り)を取得します。
	 * 
	 * @return 作業状況画像ファイル(カンマ区切り)
	 */
	public String getSagyoJokyoImageFileNm() {
		return sagyoJokyoImageFileNm;
	}
	/**
	 * 作業状況画像ファイル(カンマ区切り)を設定します。
	 * 
	 * @param sagyoJokyoImageFileNm 作業状況画像ファイル(カンマ区切り)
	 */
	public void setSagyoJokyoImageFileNm(String sagyoJokyoImageFileNm) {
		this.sagyoJokyoImageFileNm = sagyoJokyoImageFileNm;
	}

	/**
	 * 作業状況画像ファイルコメントを取得します。
	 * 
	 * @return 作業状況画像ファイルコメント
	 */
	public String[] getSagyoJokyoImageFileComments() {
		return sagyoJokyoImageFileComments;
	}
	/**
	 * 作業状況画像ファイルコメントを設定します。
	 * 
	 * @param sagyoJokyoImageFileComments 作業状況画像ファイルコメント
	 */
	public void setSagyoJokyoImageFileComments(String[] sagyoJokyoImageFileComments) {
		this.sagyoJokyoImageFileComments = sagyoJokyoImageFileComments;
	}

	/**
	 * 作業状況画像ファイルアップロード名を取得します。
	 * 
	 * @return 作業状況画像ファイルアップロード名
	 */
	public String[] getSagyoJokyoImageFileUploadFileNms() {
		return sagyoJokyoImageFileUploadFileNms;
	}
	/**
	 * 作業状況画像ファイルアップロード名を設定します。
	 * 
	 * @param sagyoJokyoImageFileUploadFileNms 作業状況画像ファイルアップロード名
	 */
	public void setSagyoJokyoImageFileUploadFileNms(String[] sagyoJokyoImageFileUploadFileNms) {
		this.sagyoJokyoImageFileUploadFileNms = sagyoJokyoImageFileUploadFileNms;
	}

	/**
	 * 作業状況画像ファイルインデックスを取得します。
	 * @return 作業状況画像ファイルインデックス
	 */
	public BigDecimal[] getSagyoJokyoImageFileFileIndexes() {
		return sagyoJokyoImageFileFileIndexes;
	}
	/**
	 * 作業状況画像ファイルインデックスを設定します。
	 * @param sagyoJokyoImageFileFileIndexes 作業状況画像ファイルインデックス
	 */
	public void setSagyoJokyoImageFileFileIndexes(BigDecimal[] sagyoJokyoImageFileFileIndexes) {
		this.sagyoJokyoImageFileFileIndexes = sagyoJokyoImageFileFileIndexes;
	}

	/**
	 * アップロード済みその他ファイルを取得します。
	 * 
	 * @return アップロード済みその他ファイル
	 */
	public RcpTOtherFileUpload[] getUploadedOtherFiles() {
		return uploadedOtherFiles;
	}
	/**
	 * アップロード済みその他ファイルを設定します。
	 * 
	 * @param uploadedOtherFiles アップロード済みその他ファイル
	 */
	public void setUploadedOtherFiles(RcpTOtherFileUpload[] uploadedOtherFiles) {
		this.uploadedOtherFiles = uploadedOtherFiles;
	}

	/**
	 * その他ファイルを取得します。
	 * 
	 * @return その他ファイル
	 */
	public File[] getOtherFiles() {
		return otherFiles;
	}
	/**
	 * その他ファイルを設定します。
	 * 
	 * @param otherFiles その他ファイル
	 */
	public void setOtherFiles(File[] otherFiles) {
		this.otherFiles = otherFiles;
	}

	/**
	 * その他ファイル名(カンマ区切り)を取得します。
	 * 
	 * @return その他ファイル名(カンマ区切り)
	 */
	public String getOtherFileNm() {
		return otherFileNm;
	}
	/**
	 * その他ファイル名(カンマ区切り)を設定します。
	 * 
	 * @param otherFileNm その他ファイル名(カンマ区切り)
	 */
	public void setOtherFileNm(String otherFileNm) {
		this.otherFileNm = otherFileNm;
	}

	/**
	 * その他ファイルコメントを取得します。
	 * 
	 * @return その他ファイルコメント
	 */
	public String[] getOtherFileComments() {
		return otherFileComments;
	}
	/**
	 * その他ファイルコメントを設定します。
	 * 
	 * @param otherFileComments その他ファイルコメント
	 */
	public void setOtherFileComments(String[] otherFileComments) {
		this.otherFileComments = otherFileComments;
	}

	/**
	 * その他ファイルアップロード名を取得します。
	 * 
	 * @return その他ファイルアップロード名
	 */
	public String[] getOtherFileUploadFileNms() {
		return otherFileUploadFileNms;
	}
	/**
	 * その他ファイルアップロード名を設定します。
	 * 
	 * @param otherFileUploadFileNms その他ファイルアップロード名
	 */
	public void setOtherFileUploadFileNms(String[] otherFileUploadFileNms) {
		this.otherFileUploadFileNms = otherFileUploadFileNms;
	}

	/**
	 * その他ファイルインデックスを取得します。
	 * @return その他ファイルインデックス
	 */
	public BigDecimal[] getOtherFileUploadFileFileIndexes() {
		return otherFileUploadFileFileIndexes;
	}
	/**
	 * その他ファイルインデックスを設定します。
	 * @param otherFileUploadFileFileIndexes その他ファイルインデックス
	 */
	public void setOtherFileUploadFileFileIndexes(BigDecimal[] otherFileUploadFileFileIndexes) {
		this.otherFileUploadFileFileIndexes = otherFileUploadFileFileIndexes;
	}

	/**
	 * ファイルインデックスを取得します。
	 * @return ファイルインデックス
	 */
	public BigDecimal getFileIndex() {
		return fileIndex;
	}
	/**
	 * ファイルインデックスを設定します。
	 * @param fileIndex ファイルインデックス
	 */
	public void setFileIndex(BigDecimal fileIndex) {
		this.fileIndex = fileIndex;
	}
	/**
	 * メール履歴情報を取得します。
	 * 
	 * @return メール履歴情報
	 */
	public RcpTMailRireki getMailRireki() {
		return mailRireki;
	}
	/**
	 * メール履歴情報を設定します。
	 * 
	 * @param mailRireki メール履歴情報
	 */
	public void setMailRireki(RcpTMailRireki mailRireki) {
		this.mailRireki = mailRireki;
	}

	/**
	 * 依頼業者情報を取得します。
	 * 
	 * @return 依頼業者情報
	 */
	public RcpMGyosha getIraiGyosha() {
		return iraiGyosha;
	}
	/**
	 * 依頼業者情報を設定します。
	 * 
	 * @param iraiGyosha 依頼業者情報
	 */
	public void setIraiGyosha(RcpMGyosha iraiGyosha) {
		this.iraiGyosha = iraiGyosha;
	}

	/**
	 * 業者回答作業状況情報を取得します。
	 * 
	 * @return 業者回答作業状況情報
	 */
	public TbTSagyoJokyo getGyoshaSagyoJokyo() {
		return gyoshaSagyoJokyo;
	}
	/**
	 * 業者回答作業状況情報を設定します。
	 * 
	 * @param gyoshaSagyoJokyo 業者回答作業状況情報
	 */
	public void setGyoshaSagyoJokyo(TbTSagyoJokyo gyoshaSagyoJokyo) {
		this.gyoshaSagyoJokyo = gyoshaSagyoJokyo;
	}

	/**
	 * 業者回答アップロードファイルリストを取得します。
	 * 
	 * @return 業者回答アップロードファイルリスト
	 */
	public List<TbTFileUpload> getGyoshaUploadFileList() {
		return gyoshaUploadFileList;
	}
	/**
	 * 業者回答アップロードファイルリストを設定します。
	 * 
	 * @param gyoshaUploadFileList 業者回答アップロードファイルリスト
	 */
	public void setGyoshaUploadFileList(List<TbTFileUpload> gyoshaUploadFileList) {
		this.gyoshaUploadFileList = gyoshaUploadFileList;
	}

	/**
	 * ログインユーザ情報を取得します。
	 * @return ログインユーザ情報
	 */
	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}
	/**
	 * ログインユーザ情報を設定します。
	 * @param userContext ログインユーザ情報
	 */
	public void setUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * 公開を止めるフラグを取得します。
	 * 
	 * @return 公開を止めるフラグ
	 */
	public String getStopPublishFlg() {
		return stopPublishFlg;
	}
	/**
	 * 公開を止めるフラグを設定します。
	 * 
	 * @param stopPublishFlg 公開を止めるフラグ
	 */
	public void setStopPublishFlg(String stopPublishFlg) {
		this.stopPublishFlg = stopPublishFlg;
	}

	/**
	 * 履歴自動登録フラグを取得します。
	 * 
	 * @return 履歴自動登録フラグ
	 */
	public String getHistoryAutoRegistFlg() {
		return historyAutoRegistFlg;
	}
	/**
	 * 履歴自動登録フラグを設定します。
	 * 
	 * @param historyAutoRegistFlg 履歴自動登録フラグ
	 */
	public void setHistoryAutoRegistFlg(String historyAutoRegistFlg) {
		this.historyAutoRegistFlg = historyAutoRegistFlg;
	}

	/**
	 * 履歴自動登録状況区分を取得します。
	 * 
	 * @return 履歴自動登録状況区分
	 */
	public String getHistoryAutoRegistJokyoKbn() {
		return historyAutoRegistJokyoKbn;
	}
	/**
	 * 履歴自動登録状況区分を設定します。
	 * 
	 * @param historyAutoRegistJokyoKbn 履歴自動登録状況区分
	 */
	public void setHistoryAutoRegistJokyoKbn(String historyAutoRegistJokyoKbn) {
		this.historyAutoRegistJokyoKbn = historyAutoRegistJokyoKbn;
	}

	/**
	 * 訪問希望リストを取得します。
	 * 
	 * @return 訪問希望リスト
	 */
	public List<RcpMComCd> getHomonKiboList() {
		return homonKiboList;
	}
	/**
	 * 訪問希望リストを設定します。
	 * 
	 * @param homonKiboList 訪問希望リスト
	 */
	public void setHomonKiboList(List<RcpMComCd> homonKiboList) {
		this.homonKiboList = homonKiboList;
	}

	/**
	 * 業者回答リストを取得します。
	 * 
	 * @return 業者回答リスト
	 */
	public List<RcpMComCd> getGyoshaKaitoList() {
		return gyoshaKaitoList;
	}
	/**
	 * 業者回答リストを設定します。
	 * 
	 * @param gyoshaKaitoList 業者回答リスト
	 */
	public void setGyoshaKaitoList(List<RcpMComCd> gyoshaKaitoList) {
		this.gyoshaKaitoList = gyoshaKaitoList;
	}

	/**
	 * 状況区分リストを取得します。
	 * 
	 * @return 状況区分リスト
	 */
	public List<RcpMJokyoKbn> getJokyoKbnList() {
		return jokyoKbnList;
	}
	/**
	 * 状況区分リストを設定します。
	 * 
	 * @param jokyoKbnList 状況区分リスト
	 */
	public void setJokyoKbnList(List<RcpMJokyoKbn> jokyoKbnList) {
		this.jokyoKbnList = jokyoKbnList;
	}

	/**
	 * 初期表示エラーフラグを取得します。
	 * 
	 * @return 初期表示エラーフラグ
	 */
	public boolean isInitError() {
		return isInitError;
	}
	/**
	 * 初期表示エラーフラグを設定します。
	 * 
	 * @param isInitError 初期表示エラーフラグ
	 */
	public void setInitError(boolean isInitError) {
		this.isInitError = isInitError;
	}

	/**
	 * 削除完了フラグを取得します。
	 * 
	 * @return 削除完了フラグ
	 */
	public boolean isDeleteCompleted() {
		return isDeleteCompleted;
	}
	/**
	 * 削除完了フラグを設定します。
	 * 
	 * @param isDeleteCompleted 削除完了フラグ
	 */
	public void setDeleteCompleted(boolean isDeleteCompleted) {
		this.isDeleteCompleted = isDeleteCompleted;
	}

	/**
	 * ファイル削除エラーを取得します。
	 * 
	 * @return ファイル削除エラー
	 */
	public boolean isFileDeleteError() {
		return fileDeleteError;
	}
	/**
	 * ファイル削除エラーを設定します。
	 * 
	 * @param fileDeleteError ファイル削除エラー
	 */
	public void setFileDeleteError(boolean fileDeleteError) {
		this.fileDeleteError = fileDeleteError;
	}

	/**
	 * 作業依頼書印刷フラグを取得します。
	 * 
	 * @return 作業依頼書印刷フラグ
	 */
	public boolean isWorkRequestPrinted() {
		return workRequestPrinted;
	}
	/**
	 * 作業依頼書印刷フラグを設定します。
	 * 
	 * @param workRequestPrinted 作業依頼書印刷フラグ
	 */
	public void setWorkRequestPrinted(boolean workRequestPrinted) {
		this.workRequestPrinted = workRequestPrinted;
	}
	
	/**
	 * 帳票生成パスを取得します。
	 * 
	 * @return 帳票生成パス
	 */
	public String getMakePdfPath() {
		return makePdfPath;
	}
	/**
	 * 帳票生成パスを設定します。
	 * 
	 * @param makePdfPath 帳票生成パス
	 */
	public void setMakePdfPath(String makePdfPath) {
		this.makePdfPath = makePdfPath;
	}

	/**
	 * 帳票名を取得します。
	 * 
	 * @return 帳票名
	 */
	public String getPdfNm() {
		return pdfNm;
	}
	/**
	 * 帳票名を設定します。
	 * 
	 * @param pdfNm 帳票名
	 */
	public void setPdfNm(String pdfNm) {
		this.pdfNm = pdfNm;
	}

	/**
	 * 変更前依頼公開フラグを取得します。
	 * 
	 * @return 変更前依頼公開フラグ
	 */
	public String getBeforeIraiKokaiFlg() {
		return beforeIraiKokaiFlg;
	}
	/**
	 * 変更前依頼公開フラグを設定します。
	 * 
	 * @param beforeIraiKokaiFlg 変更前依頼公開フラグ
	 */
	public void setBeforeIraiKokaiFlg(String beforeIraiKokaiFlg) {
		this.beforeIraiKokaiFlg = beforeIraiKokaiFlg;
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
	 * エンコード済み問い合わせ検索住所１を取得します。
	 * @return エンコード済み問い合わせ検索住所１
	 */
	public String getEncodedToiawaseJusho1() {
		return encodedToiawaseJusho1;
	}
	/**
	 * エンコード済み問い合わせ検索住所１を設定します。
	 * @param encodedToiawaseJusho1 エンコード済み問い合わせ検索住所１
	 */
	public void setEncodedToiawaseJusho1(String encodedToiawaseJusho1) {
		this.encodedToiawaseJusho1 = createEncodeString(encodedToiawaseJusho1);
	}

	/**
	 * エンコード済み問い合わせ検索住所２を取得します。
	 * @return エンコード済み問い合わせ検索住所２
	 */
	public String getEncodedToiawaseJusho2() {
		return encodedToiawaseJusho2;
	}

	/**
	 * エンコード済み問い合わせ検索住所２を設定します。
	 * @param encodedToiawaseJusho2 エンコード済み問い合わせ検索住所２
	 */
	public void setEncodedToiawaseJusho2(String encodedToiawaseJusho2) {
		this.encodedToiawaseJusho2 = createEncodeString(encodedToiawaseJusho2);
	}
	/**
	 * エンコード済み問い合わせ検索住所３を取得します。
	 * @return エンコード済み問い合わせ検索住所３
	 */
	public String getEncodedToiawaseJusho3() {
		return encodedToiawaseJusho3;
	}

	/**
	 * エンコード済み問い合わせ検索住所３を設定します。
	 * @param encodedToiawaseJusho3 エンコード済み問い合わせ検索住所３
	 */
	public void setEncodedToiawaseJusho3(String encodedToiawaseJusho3) {
		this.encodedToiawaseJusho3 = createEncodeString(encodedToiawaseJusho3);
	}
	/**
	 * エンコード済み問い合わせ検索住所４を取得します。
	 * @return エンコード済み問い合わせ検索住所４
	 */
	public String getEncodedToiawaseJusho4() {
		return encodedToiawaseJusho4;
	}

	/**
	 * エンコード済み問い合わせ検索住所４を設定します。
	 * @param encodedToiawaseJusho4 エンコード済み問い合わせ検索住所４
	 */
	public void setEncodedToiawaseJusho4(String encodedToiawaseJusho4) {
		this.encodedToiawaseJusho4 = createEncodeString(encodedToiawaseJusho4);
	}
	/**
	 * エンコード済み問い合わせ検索住所５を取得します。
	 * @return エンコード済み問い合わせ検索住所５
	 */
	public String getEncodedToiawaseJusho5() {
		return encodedToiawaseJusho5;
	}
	/**
	 * エンコード済み問い合わせ検索住所５を設定します。
	 * @param encodedToiawaseJusho5 エンコード済み問い合わせ検索住所５
	 */
	public void setEncodedToiawaseJusho5(String encodedToiawaseJusho5) {
		this.encodedToiawaseJusho5 = createEncodeString(encodedToiawaseJusho5);
	}

	/**
	 * エンコード済み問い合わせ検索部屋番号を取得します。
	 * @return エンコード済み問い合わせ検索部屋番号
	 */
	public String getEncodedToiawaseRoomNo() {
		return encodedToiawaseRoomNo;
	}

	/**
	 * エンコード済み問い合わせ検索部屋番号を設定します。
	 * @param encodedToiawaseRoomNo エンコード済み問い合わせ検索部屋番号
	 */
	public void setEncodedToiawaseRoomNo(String encodedToiawaseRoomNo) {
		this.encodedToiawaseRoomNo = createEncodeString(encodedToiawaseRoomNo);
	}
	/**
	 * エンコード済み問い合わせ検索カナ氏名１を取得します。
	 * @return エンコード済み問い合わせ検索カナ氏名１
	 */
	public String getEncodedToiawaseKanaNm1() {
		return encodedToiawaseKanaNm1;
	}
	/**
	 * エンコード済み問い合わせ検索カナ氏名１を設定します。
	 * @param encodedToiawaseKanaNm1 エンコード済み問い合わせ検索カナ氏名１
	 */
	public void setEncodedToiawaseKanaNm1(String encodedToiawaseKanaNm1) {
		this.encodedToiawaseKanaNm1 = createEncodeString(encodedToiawaseKanaNm1);
	}

	/**
	 * エンコード済み問い合わせ検索カナ氏名２を取得します。
	 * @return エンコード済み問い合わせ検索カナ氏名２
	 */
	public String getEncodedToiawaseKanaNm2() {
		return encodedToiawaseKanaNm2;
	}
	/**
	 * エンコード済み問い合わせ検索カナ氏名２を設定します。
	 * @param encodedToiawaseKanaNm2 エンコード済み問い合わせ検索カナ氏名２
	 */
	public void setEncodedToiawaseKanaNm2(String encodedToiawaseKanaNm2) {
		this.encodedToiawaseKanaNm2 = createEncodeString(encodedToiawaseKanaNm2);
	}

	/**
	 * エンコード済み問い合わせ検索漢字氏名１を取得します。
	 * @return エンコード済み問い合わせ検索漢字氏名１
	 */
	public String getEncodedToiawaseKanjiNm1() {
		return encodedToiawaseKanjiNm1;
	}
	/**
	 * エンコード済み問い合わせ検索漢字氏名１を設定します。
	 * @param encodedToiawaseKanjiNm1 エンコード済み問い合わせ検索漢字氏名１
	 */
	public void setEncodedToiawaseKanjiNm1(String encodedToiawaseKanjiNm1) {
		this.encodedToiawaseKanjiNm1 = createEncodeString(encodedToiawaseKanjiNm1);
	}

	/**
	 * エンコード済み問い合わせ検索漢字氏名２を取得します。
	 * @return エンコード済み問い合わせ検索漢字氏名２
	 */
	public String getEncodedToiawaseKanjiNm2() {
		return encodedToiawaseKanjiNm2;
	}
	/**
	 * エンコード済み問い合わせ検索漢字氏名２を設定します。
	 * @param encodedToiawaseKanjiNm2 エンコード済み問い合わせ検索漢字氏名２
	 */
	public void setEncodedToiawaseKanjiNm2(String encodedToiawaseKanjiNm2) {
		this.encodedToiawaseKanjiNm2 = createEncodeString(encodedToiawaseKanjiNm2);
	}

	/**
	 * 遷移元画面が保持していた遷移元画面区分を取得します。
	 * @return 遷移元画面が保持していた遷移元画面区分
	 */
	public String getRootDispKbn() {
		return rootDispKbn;
	}
	/**
	 * 遷移元画面が保持していた遷移元画面区分を設定します。
	 * @param rootDispKbn 遷移元画面が保持していた遷移元画面区分
	 */
	public void setRootDispKbn(String rootDispKbn) {
		this.rootDispKbn = rootDispKbn;
	}

	/**
	 * ファイルアップロード処理を実行するかを判定します。
	 * 
	 * @return true：処理を実行する
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
	 * ファイルコメント更新処理を行うかを判定します。
	 * 
	 * @return true：処理を実行する
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
	 * その他ファイルアップロード処理を実行するかを判定します。
	 * 
	 * @return true：処理を実行する
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
	 * その他ファイルコメント更新処理を行うかを判定します。
	 * 
	 * @return true：処理を実行する
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
	 * 作業状況画像ファイル名を配列にして返します。
	 *
	 * @return 作業状況画像ファイル名配列
	 */
	public String[] getSagyoJokyoImageFileNmByArray() {
		if (StringUtils.isBlank(this.sagyoJokyoImageFileNm)) {
			return new String[0];
		}

		return this.sagyoJokyoImageFileNm.split(",");
	}

	/**
	 * その他ファイル名を配列にして返します。
	 *
	 * @return その他ファイル名配列
	 */
	public String[] getOtherFileNmByArray() {
		if (StringUtils.isBlank(this.otherFileNm)) {
			return new String[0];
		}

		return this.otherFileNm.split(",");
	}

	/**
	 * 問い合わせ履歴自動登録を行うかを判定します。
	 * 
	 * @return true：実施する
	 */
	public boolean isToiawaseHistoryAutoRegistExcecutable() {
		return HISTORY_AUTO_REGIST_FLG_ON.equals(this.historyAutoRegistFlg);
	}

	/**
	 * 遷移元画面が依頼検索かを判定します。
	 * 
	 * @return true：依頼検索からの遷移
	 */
	public boolean isFromRequestSearch() {
		return Constants.GAMEN_KBN_REQUEST_SEARCH.equals(getDispKbn());
	}

	/**
	 * 画面表示用の問い合わせＮＯを取得します。
	 * 
	 * @return 画面表示用の問い合わせＮＯ
	 */
	public String getToiawaseNoForDisplay() {
		return isInsert() ? this.toiawaseNo : this.toiawaseNo + "-" + this.toiawaseRirekiNo.toPlainString();
	}

	/**
	 * 画面表示用の最終更新日を取得します。
	 * 
	 * @return 画面表示用の最終更新日
	 */
	public Timestamp getLastUpdDtForDisplay() {
		if (!isUpdate()) {
			// 更新表示でなければ、表示をしない（画面では非表示）
			return null;
		}

		if (this.irai == null || this.irai.getUpdDt() == null) {
			// 依頼情報がなければ、表示しない（NullpointerException回避）
			return null;
		}

		if (this.sagyoJokyo == null || this.sagyoJokyo.getUpdDt() == null) {
			// 作業状況情報がなければ、依頼情報の最終更新日を表示
			return this.irai.getUpdDt();
		}

		if (this.irai.getUpdDt().compareTo(this.sagyoJokyo.getUpdDt()) > 0) {
			// 依頼情報の最終更新日が作業状況情報の最終更新日よりも遅い日付の場合、依頼情報の最終更新日を表示
			return this.irai.getUpdDt();
		} else {
			// 上記以外の場合、作業状況情報の最終更新日を表示
			return this.sagyoJokyo.getUpdDt();
		}
	}

	/**
	 * 画面表示用の最終更新者名を取得します。
	 * 
	 * @return 画面表示用の最終更新者名
	 */
	public String getLastUpdNmForDisplay() {
		if (!isUpdate()) {
			// 更新表示でなければ、表示をしない（画面では非表示）
			return "";
		}

		if (this.irai == null || this.irai.getUpdDt() == null) {
			// 依頼情報がなければ、表示しない（NullpointerException回避）
			return "";
		}

		if (this.sagyoJokyo == null || this.sagyoJokyo.getUpdDt() == null) {
			// 作業状況情報がなければ、依頼情報のデータを表示
			// 最終更新者名があれば、最終更新者名、なければ、最終更新者ＩＤの和名変換結果を表示
			return StringUtils.isNotBlank(this.irai.getLastUpdNm()) ? 
					this.irai.getLastUpdNm() : this.irai.getLastUpdNmForId();
		}

		if (this.irai.getUpdDt().compareTo(this.sagyoJokyo.getUpdDt()) > 0) {
			// 依頼情報の最終更新日が作業状況情報の最終更新日よりも遅い日付の場合、依頼情報の最終更新者名を表示
			// 最終更新者名があれば、最終更新者名、なければ、最終更新者ＩＤの和名変換結果を表示
			return StringUtils.isNotBlank(this.irai.getLastUpdNm()) ? 
					this.irai.getLastUpdNm() : this.irai.getLastUpdNmForId();
		} else {
			// 上記以外の場合、作業状況情報の最終更新者名を表示
			// 最終更新者名があれば、最終更新者名、なければ、最終更新者ＩＤの和名変換結果を表示
			return StringUtils.isNotBlank(this.sagyoJokyo.getLastUpdNm()) ? 
					this.sagyoJokyo.getLastUpdNm() : this.sagyoJokyo.getLastUpdNmForId();
		}
	}

	/**
	 * 業者作業依頼メールが送信可能かのコメントを表示するかを判定します。
	 *
	 * @return true：表示する
	 */
	public boolean isGyoshaIraiMailCommentDisplay() {
		if (this.irai == null) {
			// 依頼情報がない場合は、コメント表示しない
			return false;
		}

		if (this.userContext == null) {
			// ログインユーザ情報がない場合は、コメント表示しない
			return false;
		}

		if (!isUpdate()) {
			// 更新でない場合（新規登録など）、コメント表示しない
			return false;
		}

		if (!(this.userContext.isAdministrativeInhouse()
				|| this.userContext.isOutsourcerSv()
				|| this.userContext.isOutsourcerOp())) {
			// TOKAI管理者、委託会社SV、OP以外の場合は、コメント表示しない
			return false;
		}

		// 更新の場合、業者情報をチェック
		if (iraiGyosha == null) {
			// 業者情報がない場合は、コメント表示しない
			return false;
		}

		// 作業依頼メール送付有無が「1:自動送信する」の場合、コメント表示
		return iraiGyosha.isSagyoIraiMailSendable();
	}

	/**
	 * 公開メール送信ボタンの表示可否を判定します。
	 * 
	 * @return true：表示する、false：表示しない
	 */
	public boolean isPublishMailButtonVisible() {
		if (!isUpdate()) {
			// 画面が更新表示でなければ、表示しない
			return false;
		}

		if (this.irai == null) {
			// 依頼情報がない場合は、表示しない
			return false;
		}

		if (getKokyakuEntity() == null) {
			// 顧客情報がない場合は表示しない
			return false;
		}

		if (this.userContext == null) {
			// ログインユーザ情報がない場合は、表示しない
			return false;
		}

		if (!(this.userContext.isAdministrativeInhouse()
				|| this.userContext.isOutsourcerSv()
				|| this.userContext.isOutsourcerOp())) {
			// ＴＯＫＡＩ管理者、委託会社ＳＶ、委託会社ＯＰ以外は、表示しない
			return false;
		}

		if (!(RcpTIrai.KOKAI_FLG_KOUKAIZUMI.equals(this.beforeIraiKokaiFlg))) {
			// 依頼公開フラグが「公開済」でなければ、表示しない
			// 更新値（チェックON⇒チェックOFF）によって、本来表示されるはずの
			// 公開メール送信ボタンが消えてしまうので、常に変更前の依頼公開フラグを見る
			return false;
		}

		// 顧客マスタの顧客区分が「管理会社(大家含む)」「物件」「入居者・個人」のいずれかの場合、表示
		return (getKokyakuEntity().isKokyakuKbnFudosan()
				|| getKokyakuEntity().isKokyakuKbnBukken()
				|| getKokyakuEntity().isKokyakuKbnNyukyosha());
	}

	/**
	 * 画面表示用の最終印刷者名を取得します。
	 * 
	 * @return 画面表示用の最終印刷者名
	 */
	public String getLastPrintNmForDisplay() {
		if (!isUpdate()) {
			// 画面が更新表示でなければ、表示しない
			return "";
		}

		if (this.irai == null) {
			// 依頼情報がない場合は、表示しない
			return "";
		}
		// 最終印刷者名があれば、最終印刷者名、なければ、最終印刷者ＩＤの和名変換結果を表示
		return StringUtils.isNotBlank(this.irai.getLastPrintNm()) ? 
				this.irai.getLastPrintNm() : this.irai.getLastPrintNmForId();
	}

	/**
	 * 作業完了がチェックされているかを判定します。
	 * 
	 * @return true：チェックON
	 */
	public boolean isSagyoKanryoFlgChecked() {
		if (!isUpdate()) {
			// 画面が更新表示でなければ、表示しない
			return false;
		}

		if (this.sagyoJokyo == null) {
			// 作業状況情報がない場合は、表示しない
			return false;
		}

		// 作業完了ならばチェックON、それ以外の場合はチェックOFF
		return RcpTSagyoJokyo.SAGYO_KANRYO_FLG_KANRYO.equals(this.sagyoJokyo.getSagyoKanryoFlg());
	}

	/**
	 * 画像トークン値を取得します。
	 * 
	 * @return 画像トークン値
	 */
	public String getImageToken() {
		return DateUtil.getSysDateString("yyyyMMddHHmmssSSS");
	}

	/**
	 * 削除ボタンを表示するかの判定を行います。
	 * 
	 * @return true：表示する
	 */
	public boolean isDeleteButtonVisible() {
		if (!isUpdate()) {
			// 画面が更新表示でなければ、表示しない
			return false;
		}

		if (this.userContext == null) {
			// ログインユーザ情報が取得できなければ、表示しない
			return false;
		}

		if (this.toiawase == null) {
			// 問い合わせ情報が取得できなければ、表示しない
			return false;
		}

		// 権限がＴＯＫＡＩ管理者、委託会社ＳＶの場合は、表示
		return (this.userContext.isAdministrativeInhouse()
				|| this.userContext.isOutsourcerSv());
	}

	/**
	 * 作業報告書印刷ボタンを表示するかの判定を行います。
	 * 
	 * @return true：表示する
	 */
	public boolean isWorkReportPrintButtonVisible() {
		if (!isUpdate()) {
			// 画面が更新表示でなければ、表示しない
			return false;
		}

		if (this.toiawase == null) {
			// 問い合わせ情報が取得できなければ、表示しない
			return false;
		}

		if (this.sagyoJokyo == null) {
			// 作業状況情報が取得できなければ、表示しない
			return false;
		}

		// 問い合わせ情報の顧客ＩＤがNOT NULLなら表示、それ以外は非表示
		return StringUtils.isNotBlank(this.toiawase.getKokyakuId());
	}

	/**
	 * 登録ボタンを表示するかの判定を行います。
	 * 
	 * @return true：表示する
	 */
	public boolean isEntryButtonVisible() {
		if (this.userContext == null) {
			// ログインユーザ情報が取得できなければ、表示しない
			return false;
		}

		if (this.toiawase == null) {
			// 問い合わせ情報が取得できなければ、表示しない
			return false;
		}

		if (!(this.userContext.isAdministrativeInhouse()
				|| this.userContext.isOutsourcerSv())) {
			// 権限がＴＯＫＡＩ管理者、委託会社ＳＶ以外の場合は、表示しない
			return false;
		}

		// 新規登録表示、または、更新表示で作業状況情報がなければ、表示
		return isInsert() || (isUpdate() && (this.sagyoJokyo == null || StringUtils.isBlank(this.sagyoJokyo.getToiawaseNo())));
	}

	/**
	 * 更新ボタンを表示するかの判定を行います。
	 * 
	 * @return true：表示する
	 */
	public boolean isUpdateButtonVisible() {
		if (!isUpdate()) {
			// 画面が更新表示でなければ、表示しない
			return false;
		}

		if (this.userContext == null) {
			// ログインユーザ情報が取得できなければ、表示しない
			return false;
		}

		if (this.toiawase == null) {
			// 問い合わせ情報が取得できなければ、表示しない
			return false;
		}

		if (!(this.userContext.isAdministrativeInhouse()
				|| this.userContext.isOutsourcerSv())) {
			// 権限がＴＯＫＡＩ管理者、委託会社ＳＶ以外の場合は、表示しない
			return false;
		}

		// 更新表示で作業状況情報が取得できれば、表示
		return isUpdate() && (this.sagyoJokyo != null && StringUtils.isNotBlank(this.sagyoJokyo.getToiawaseNo()));
	}

	/**
	 * 遷移元画面が問い合わせ登録画面かを判定します。
	 * 
	 * @return true：問い合わせ登録画面
	 */
	public boolean isFromInquiryEntry() {
		return Constants.GAMEN_KBN_INQUIRY_ENTRY.equals(getDispKbn());
	}

	/**
	 * 業者回答コピー（<<）ボタンを表示するかを判定します。
	 * 
	 * @return true：表示
	 */
	public boolean isContractorAnswerCopyButtonVisible() {
		if (this.gyoshaSagyoJokyo == null) {
			// 業者回答作業状況情報がなければ、非表示
			return false;
		}

		return StringUtils.isNotBlank(this.gyoshaSagyoJokyo.getToiawaseNo());
	}

	/**
	 * アクションタイプが画面値復元モードなのかを判定します。
	 * 
	 * @return true：画面値復元モード
	 */
	public boolean isRestore() {
		return Constants.ACTION_TYPE_RESTORE.equals(getActionType());
	}
	
	/**
	 * 公開を止めるチェックがチェックＯＮかを判定します。
	 * 
	 * @return true：チェックＯＮ
	 */
	public boolean isStopPublishChecked() {
		return STOP_PUBLISH_FLG_ON.equals(this.stopPublishFlg);
	}
	
	/**
	 * アクションタイプがメール送信モードなのかを判定します。
	 * 
	 * @return true：メール送信モード
	 */
	public boolean isSendMail() {
		return ACTION_TYPE_SEND_MAIL.equals(getActionType());
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
	 * システム日付を取得します。
	 * 
	 * @return システム日付（yyyy/MM/dd形式）
	 */
	public String getSysDate() {
		return DateUtil.getSysDateString("yyyy/MM/dd");
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
}
