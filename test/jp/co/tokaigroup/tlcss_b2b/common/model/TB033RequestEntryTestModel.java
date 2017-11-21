package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.common.model.RC000CommonModel;
import jp.co.tokaigroup.reception.context.ReceptionUserContext;
import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.NatosMYubinNo;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;

public class TB033RequestEntryTestModel extends RC000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "依頼情報登録";

	/** ボタン名 作業依頼メール送信 */
	public static final String BUTTON_NM_IRAI_MAIL = "作業依頼メール送信";

	/** 検索結果 */
 	private List<NatosMYubinNo> result;

	/** hidden問い合わせＮＯ */
	private String toiawaseNo;

	/** hidden問い合わせ更新日 */
	private Timestamp toiawaseUpdDt;

	/** hidden履歴NO */
	private BigDecimal toiawaseRirekiNo;

	/** hidden依頼更新日 */
	private Timestamp iraiUpdDt;

	/** hidden処理区分 */
	private String shoriKbn;

	/** 依頼テーブルEntity */
	private RcpTIrai irai;

	/** 依頼情報最終更新者ID 和名 */
	private String iraiLastUpdNm;

	/** パスワードマスタEntity(最終更新者ID) */
	private NatosMPassword natosMPassword;

	/** 訪問予定日リスト */
	private List<RcpMComCd> homonYoteiYmdList;

	/** パスワードマスタEntityリスト */
	private List<NatosMPassword> natosMPasswordList;

	/** 依頼業者マスタリスト */
	private RcpMGyosha gyosha;

	/** 問い合わせ情報Entity */
	private RcpTToiawase toiawase;

	/** 登録正常フラグ(依頼登録) true：正常 false：異常*/
	private boolean insertNormalFlg;

	/** 登録正常フラグ(依頼登録) true：正常 false：異常*/
	private boolean updateNormalFlg;

	/** 登録・更新正常フラグ(作業状況) true：正常 false：異常*/
	private boolean sagyoUpdateNormalFlg;

	/** 削除失敗元ファイル名リスト(作業状況)*/
	private List<String> errorDeleteFileNmList;

	/** ログインユーザ情報 */
	private ReceptionUserContext context;

	/** 親画面画面区分 */
	private String openerDispKbn;

	/** 依頼情報削除フラグ */
	private boolean iraiDeleteSuccessFlg;

	/** 作業状況削除フラグ */
	private boolean sagyoJokyoDeleteSuccessFlg;

	/** 親画面名 */
	private String toiawaseWindowName;

	/** 自画面名 */
	private String iraiWindowName;

	/** パスワードマスタEntity(最終印刷者ID) */
	private NatosMPassword natosPasswordLastPrintId;

	/** 報告書PDFURL */
	private String pdfUrl;

	/** 業者コード */
	private String gyoshaCd;

	/** 例外発生フラグ */
	private boolean exceptionFlg;

	/** 顧客マスタ情報 */
	private RcpMKokyaku kokyaku;
	
	/**
	 * 検索結果を取得します。
	 *
	 * @return 検索結果
	 */
	public List<NatosMYubinNo> getResult() {
		return result;
	}

	/**
	 * @return toiawaseNo
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}


	/**
	 * @param toiawaseNo セットする toiawaseNo
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}


	/**
	 * @return toiawaseUpdDt
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}


	/**
	 * @param toiawaseUpdDt セットする toiawaseUpdDt
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}


	/**
	 * @return toiawaseRirekiNo
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}


	/**
	 * @param toiawaseRirekiNo セットする toiawaseRirekiNo
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}


	/**
	 * @return iraiUpdDt
	 */
	public Timestamp getIraiUpdDt() {
		return iraiUpdDt;
	}


	/**
	 * @param iraiUpdDt セットする iraiUpdDt
	 */
	public void setIraiUpdDt(Timestamp iraiUpdDt) {
		this.iraiUpdDt = iraiUpdDt;
	}


	/**
	 * @return shoriKbn
	 */
	public String getShoriKbn() {
		return shoriKbn;
	}


	/**
	 * @param shoriKbn セットする shoriKbn
	 */
	public void setShoriKbn(String shoriKbn) {
		this.shoriKbn = shoriKbn;
	}


	/**
	 * @return irai
	 */
	public RcpTIrai getIrai() {
		return irai;
	}


	/**
	 * @param irai セットする irai
	 */
	public void setIrai(RcpTIrai irai) {
		this.irai = irai;
	}


	/**
	 * @return iraiLastUpdNm
	 */
	public String getIraiLastUpdNm() {
		return iraiLastUpdNm;
	}


	/**
	 * @param iraiLastUpdNm セットする iraiLastUpdNm
	 */
	public void setIraiLastUpdNm(String iraiLastUpdNm) {
		this.iraiLastUpdNm = iraiLastUpdNm;
	}


	/**
	 * @return natosMPassword
	 */
	public NatosMPassword getNatosMPassword() {
		return natosMPassword;
	}


	/**
	 * @param natosMPassword セットする natosMPassword
	 */
	public void setNatosMPassword(NatosMPassword natosMPassword) {
		this.natosMPassword = natosMPassword;
	}


	/**
	 * @return homonYoteiYmdList
	 */
	public List<RcpMComCd> getHomonYoteiYmdList() {
		return homonYoteiYmdList;
	}


	/**
	 * @param homonYoteiYmdList セットする homonYoteiYmdList
	 */
	public void setHomonYoteiYmdList(List<RcpMComCd> homonYoteiYmdList) {
		if (homonYoteiYmdList != null) {
			this.homonYoteiYmdList = homonYoteiYmdList;
		} else {
			this.homonYoteiYmdList = new ArrayList<RcpMComCd>();
		}
	}


	/**
	 * @return natosMPasswordList
	 */
	public List<NatosMPassword> getNatosMPasswordList() {
		return natosMPasswordList;
	}


	/**
	 * @param natosMPasswordList セットする natosMPasswordList
	 */
	public void setNatosMPasswordList(List<NatosMPassword> natosMPasswordList) {
		this.natosMPasswordList = natosMPasswordList;
	}


	/**
	 * @return toiawase
	 */
	public RcpTToiawase getToiawase() {
		return toiawase;
	}


	/**
	 * @param toiawase セットする toiawase
	 */
	public void setToiawase(RcpTToiawase toiawase) {
		this.toiawase = toiawase;
	}


	/**
	 * @return insertNormalFlg
	 */
	public boolean isInsertNormalFlg() {
		return insertNormalFlg;
	}


	/**
	 * @param insertNormalFlg セットする insertNormalFlg
	 */
	public void setInsertNormalFlg(boolean insertNormalFlg) {
		this.insertNormalFlg = insertNormalFlg;
	}


	/**
	 * @return updateNormalFlg
	 */
	public boolean isUpdateNormalFlg() {
		return updateNormalFlg;
	}


	/**
	 * @param updateNormalFlg セットする updateNormalFlg
	 */
	public void setUpdateNormalFlg(boolean updateNormalFlg) {
		this.updateNormalFlg = updateNormalFlg;
	}


	/**
	 * @return sagyoUpdateNormalFlg
	 */
	public boolean isSagyoUpdateNormalFlg() {
		return sagyoUpdateNormalFlg;
	}


	/**
	 * @param sagyoUpdateNormalFlg セットする sagyoUpdateNormalFlg
	 */
	public void setSagyoUpdateNormalFlg(boolean sagyoUpdateNormalFlg) {
		this.sagyoUpdateNormalFlg = sagyoUpdateNormalFlg;
	}


	/**
	 * @return errorDeleteFileNmList
	 */
	public List<String> getErrorDeleteFileNmList() {
		return errorDeleteFileNmList;
	}


	/**
	 * @param errorDeleteFileNmList セットする errorDeleteFileNmList
	 */
	public void setErrorDeleteFileNmList(List<String> errorDeleteFileNmList) {
		this.errorDeleteFileNmList = errorDeleteFileNmList;
	}


	/**
	 * @return gyosha
	 */
	public RcpMGyosha getGyosha() {
		return gyosha;
	}


	/**
	 * @param gyosha セットする gyosha
	 */
	public void setGyosha(RcpMGyosha gyosha) {
		this.gyosha = gyosha;
	}

	/**
	 * ログインユーザを取得します。
	 *
	 * @return ログインユーザ
	 */
	public ReceptionUserContext getContext() {
		return context;
	}

	/**
	 * ログインユーザを設定します。
	 *
	 * @param context ログインユーザ
	 */
	public void setContext(ReceptionUserContext context) {
		this.context = context;
	}


	/**
	 * @return openerDispKbn
	 */
	public String getOpenerDispKbn() {
		return openerDispKbn;
	}


	/**
	 * @param openerDispKbn セットする openerDispKbn
	 */
	public void setOpenerDispKbn(String openerDispKbn) {
		this.openerDispKbn = openerDispKbn;
	}


	/**
	 * @return iraiDeleteSuccessFlg
	 */
	public boolean isIraiDeleteSuccessFlg() {
		return iraiDeleteSuccessFlg;
	}


	/**
	 * @param iraiDeleteSuccessFlg セットする iraiDeleteSuccessFlg
	 */
	public void setIraiDeleteSuccessFlg(boolean iraiDeleteSuccessFlg) {
		this.iraiDeleteSuccessFlg = iraiDeleteSuccessFlg;
	}


	/**
	 * @return sagyoJokyoDeleteSuccessFlg
	 */
	public boolean isSagyoJokyoDeleteSuccessFlg() {
		return sagyoJokyoDeleteSuccessFlg;
	}


	/**
	 * @param sagyoJokyoDeleteSuccessFlg セットする sagyoJokyoDeleteSuccessFlg
	 */
	public void setSagyoJokyoDeleteSuccessFlg(boolean sagyoJokyoDeleteSuccessFlg) {
		this.sagyoJokyoDeleteSuccessFlg = sagyoJokyoDeleteSuccessFlg;
	}


	/**
	 * @return toiawaseWindowName
	 */
	public String getToiawaseWindowName() {
		return toiawaseWindowName;
	}


	/**
	 * @param toiawaseWindowName セットする toiawaseWindowName
	 */
	public void setToiawaseWindowName(String toiawaseWindowName) {
		this.toiawaseWindowName = toiawaseWindowName;
	}


	/**
	 * @return iraiWindowName
	 */
	public String getIraiWindowName() {
		return iraiWindowName;
	}


	/**
	 * @param iraiWindowName セットする iraiWindowName
	 */
	public void setIraiWindowName(String iraiWindowName) {
		this.iraiWindowName = iraiWindowName;
	}


	/**
	 * @return natosPasswordLastPrintId
	 */
	public NatosMPassword getNatosPasswordLastPrintId() {
		return natosPasswordLastPrintId;
	}


	/**
	 * @param natosPasswordLastPrintId セットする natosPasswordLastPrintId
	 */
	public void setNatosPasswordLastPrintId(NatosMPassword natosPasswordLastPrintId) {
		this.natosPasswordLastPrintId = natosPasswordLastPrintId;
	}


	/**
	 * 報告書PDFURLを取得します。
	 *
	 * @return 報告書PDFURL
	 */
	public String getPdfUrl() {
		return pdfUrl;
	}

	/**
	 * 報告書PDFURLを設定します。
	 *
	 * @param pdfUrl 報告書PDFURL
	 */
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	/**
	 * 業者コードを取得します。
	 *
	 * @return 業者コード
	 */
	public String getGyoshaCd() {
		return gyoshaCd;
	}
	/**
	 * 業者コードを設定します。
	 *
	 * @param gyoshaCd 業者コード
	 */
	public void setGyoshaCd(String gyoshaCd) {
		this.gyoshaCd = gyoshaCd;
	}

	/**
	 * 例外発生フラグを取得します。
	 *
	 * @return 例外発生フラグ
	 */
	public boolean isExceptionFlg() {
		return exceptionFlg;
	}
	/**
	 * 例外発生フラグを設定します。
	 *
	 * @param exceptionFlg 例外発生フラグ
	 */
	public void setExceptionFlg(boolean exceptionFlg) {
		this.exceptionFlg = exceptionFlg;
	}

	/**
	 * 顧客マスタ情報を取得します。
	 *
	 * @return 顧客マスタ情報
	 */
	public RcpMKokyaku getKokyaku() {
		return kokyaku;
	}
	/**
	 * 顧客マスタ情報を設定します。
	 *
	 * @param kokyaku 顧客マスタ情報
	 */
	public void setKokyaku(RcpMKokyaku kokyaku) {
		this.kokyaku = kokyaku;
	}

	/**
	 * 親画面を再表示するかを判定します。
	 * 再表示したい親画面が増えた場合は、条件を追加してください。
	 *
	 * @return true:再表示する、false:再表示しない
	 */
	public boolean isOpenerReload() {
		if (Constants.FROM_DISP_KBN_TOIAWASE_RIREKI_ENTRY.equals(openerDispKbn)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * 顧客詳細画面を表示するかを判定します。
	 *
	 * @return true:表示する、false:表示しない
	 */
	public boolean isKokyakuDetailDisplay() {
		return (StringUtils.isNotBlank(toiawase.getKokyakuId()));
	}


	/**
	 * ID無し顧客情報画面を表示するかを判定します。
	 *
	 * @return true:表示する、false:表示しない
	 */
	public boolean isKokyakuWithNoIdInfoDisplay() {
		return (StringUtils.isBlank(toiawase.getKokyakuId()));
	}

	/**
	 * 締め年月が入力されているか判定します。
	 *
	 * @return true:締め年月が入力されている(not null)、false:締め年月が入力されていない(null)
	 */
	public boolean isCheckedShimeYmFlg() {
		return (toiawase.getShimeYm() != null);
	}

	/**
	 * 最終印刷者IDが存在するか判定します。
	 *
	 * @return true:最終印刷者IDが存在する、false:最終印刷者IDが存在しない
	 */
	public boolean isExistLastPrintId() {
		return (StringUtils.isNotBlank(irai.getLastPrintId()));
	}

	/**
	 * 依頼公開フラグに含むチェックがチェックされているかを判定します。
	 *
	 * @return true:チェックされている、false:チェックされていない
	 */
	public boolean isCheckedIraiKokaiFlg() {
		if (this.irai == null) {
			// デフォルトは、チェックON
			return false;
		}

		if (StringUtils.isBlank(this.irai.getIraiKokaiFlg())) {
			// デフォルトは、チェックON
			return false;
		}

		return (RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI.equals(this.irai.getIraiKokaiFlg()));
	}

	/**
	 * 作業依頼メールが送信可能かのコメントを表示するかを判定します。
	 *
	 * @return true:表示する、false:表示しない
	 */
	public boolean isSagyoIraiMailCommentDisplay() {
		if (this.irai == null) {
			// 依頼情報がない場合は、コメント表示しない
			return false;
		}

		// 更新の場合、業者情報をチェック
		if (gyosha == null) {
			// 業者情報がない場合は、コメント表示しない
			return false;
		}

		// 作業依頼メール送付有無が「1:自動送信する」の場合、コメント表示
		return gyosha.isSagyoIraiMailSendable();
	}

	/**
	 * TORES公開メール送信ボタンを表示するかを判定します。
	 *
	 * @return true：表示する、false：表示しない
	 */
	public boolean isVisiblePublishMail() {
		if (this.irai == null) {
			// 依頼情報がない場合は、非表示
			return false;
		}
		
		if (!isUpdate()) {
			// 更新でない場合（新規登録など）、コメント表示しない
			return false;
		}

		if (this.kokyaku == null) {
			// 顧客情報がない場合は、非表示
			return false;
		}

		if (!RcpTIrai.KOKAI_FLG_KOUKAIZUMI.equals(this.irai.getIraiKokaiFlg())) {
			// 依頼情報が公開済以外は、非表示
			return false;
		}

		// 顧客区分が「1：管理会社(大家含む)」「3：物件」「4：入居者・個人」の場合は、表示
		// それ以外は非表示
		return this.kokyaku.isKokyakuKbnFudosan() ||
				this.kokyaku.isKokyakuKbnBukken() ||
				this.kokyaku.isKokyakuKbnNyukyosha();
	}
	
}
