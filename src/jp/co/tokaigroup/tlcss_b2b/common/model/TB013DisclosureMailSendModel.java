package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKanri;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKLife;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.si.fw.mail.VelocityWrapper;
import jp.co.tokaigroup.tlcss_b2b.dto.TB013DisclosureMailSendDto;

import org.apache.commons.lang.StringUtils;

/**
 * TORES公開メール送信画面モデル。
 *
 * @author k003856
 * @version 5.0 2015/09/08
 * @version 5.1 2016/07/14 H.Hirai 複数請求先対応
 */
public class TB013DisclosureMailSendModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "TORES公開メール送信";

	/** ボタン名：メール送信 */
	public static final String BUTTON_NM_MAIL_SEND = "メール送信";

	/** 処理区分 1:問い合わせ登録 */
	public static final int SHORI_KBN_TOIAWASE_ENTRY = 1;
	/** 処理区分 2:問い合わせ履歴登録 */
	public static final int SHORI_KBN_TOIAWASE_RIREKI_ENTRY = 2;
	/** 処理区分 3:依頼登録 */
	public static final int SHORI_KBN_IRAI_ENTRY = 3;

	/** 問い合わせＮＯ */
	private String toiawaseNo;
	/** 問い合わせ履歴ＮＯ */
	private BigDecimal toiawaseRirekiNo;
	/** 問い合わせ更新日 */
	private Timestamp toiawaseUpdDt;
	/** 処理区分 */
	private BigDecimal shoriKbn;
	/** 請求先顧客ＩＤ */
	private String seikyusakiKokyakuId;
	/** 対応報告メールアドレス（カンマ区切り） */
	private String taioMailAddress;
	/** 会社ＩＤ */
	private String kaishaId;

	/** 顧客情報 */
	private RcpMKokyaku kokyaku;
	/** 問い合わせ情報 */
	private RcpTToiawase toiawase;
	/** 顧客契約ライフサポート情報 */
	private RcpMKokyakuKLife kokyakuKLife;
	/** 会社情報 */
	private TbMKaisha kaisha;
	/** Velocityラッパークラス */
	private VelocityWrapper wrapper;
	/** 初期化呼び出しフラグ */
	private boolean isInitFlg;

	/** 件名 */
	private String subject;
	/** 送信元メールアドレス */
	private String senderMailAddress;
	/** BCCメールアドレス */
	private String bccMailAddress;
	/** メール本文 */
	private String mailText;

	/** 問い合わせ履歴情報 */
	private RcpTToiawaseRireki toiawaseRirekiInfo;

	/** TORES公開メール送信画面ＤＴＯリスト */
	private List<TB013DisclosureMailSendDto> disclosureMailSendList;
	/** TORES公開メール送信画面ＤＴＯリストサイズ */
	private int disclosureMailSendListSize;
	/** 送信先リスト選択顧客ＩＤ */
	private String selectKokyakuId;
	/** 顧客付随管理会社マスタリスト */
	private List<RcpMKokyakuFKanri> kokyakuFKanriList;

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
	 * 処理区分を取得します。
	 *
	 * @return 処理区分
	 */
	public BigDecimal getShoriKbn() {
		return shoriKbn;
	}
	/**
	 * 処理区分を設定します。
	 *
	 * @param shoriKbn 処理区分
	 */
	public void setShoriKbn(BigDecimal shoriKbn) {
		this.shoriKbn = shoriKbn;
	}

	/**
	 * 請求先顧客ＩＤを取得します。
	 *
	 * @return 請求先顧客ＩＤ
	 */
	public String getSeikyusakiKokyakuId() {
		return seikyusakiKokyakuId;
	}
	/**
	 * 請求先顧客ＩＤを設定します。
	 *
	 * @param seikyusakiKokyakuId 請求先顧客ＩＤ
	 */
	public void setSeikyusakiKokyakuId(String seikyusakiKokyakuId) {
		this.seikyusakiKokyakuId = seikyusakiKokyakuId;
	}

	/**
	 * 会社ＩＤを取得します。
	 *
	 * @return 会社ＩＤ
	 */
	public String getKaishaId() {
		return kaishaId;
	}
	/**
	 * 会社ＩＤを設定します。
	 *
	 * @param kaishaId 会社ＩＤ
	 */
	public void setKaishaId(String kaishaId) {
		this.kaishaId = kaishaId;
	}

	/**
	 * 対応報告メールアドレス（カンマ区切り）を取得します。
	 *
	 * @return 対応報告メールアドレス（カンマ区切り）
	 */
	public String getTaioMailAddress() {
		return taioMailAddress;
	}
	/**
	 * 対応報告メールアドレス（カンマ区切り）を設定します。
	 *
	 * @param taioMailAddress 対応報告メールアドレス（カンマ区切り）
	 */
	public void setTaioMailAddress(String taioMailAddress) {
		this.taioMailAddress = taioMailAddress;
	}

	/**
	 * 顧客情報を取得します。
	 *
	 * @return 顧客情報
	 */
	public RcpMKokyaku getKokyaku() {
		return kokyaku;
	}
	/**
	 * 顧客情報を設定します。
	 *
	 * @param kokyaku 顧客情報
	 */
	public void setKokyaku(RcpMKokyaku kokyaku) {
		this.kokyaku = kokyaku;
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
	 * 顧客契約ライフサポート情報を取得します。
	 *
	 * @return 顧客契約ライフサポート情報
	 */
	public RcpMKokyakuKLife getKokyakuKLife() {
		return kokyakuKLife;
	}
	/**
	 * 顧客契約ライフサポート情報を設定します。
	 *
	 * @param kokyakuKLife 顧客契約ライフサポート情報
	 */
	public void setKokyakuKLife(RcpMKokyakuKLife kokyakuKLife) {
		this.kokyakuKLife = kokyakuKLife;
	}

	/**
	 * 会社情報を取得します。
	 *
	 * @return 会社情報
	 */
	public TbMKaisha getKaisha() {
		return kaisha;
	}
	/**
	 * 会社情報を設定します。
	 *
	 * @param kaisha 会社情報
	 */
	public void setKaisha(TbMKaisha kaisha) {
		this.kaisha = kaisha;
	}

	/**
	 * Velocityラッパークラスを取得します。
	 *
	 * @return Velocityラッパークラス
	 */
	public VelocityWrapper getWrapper() {
		return wrapper;
	}
	/**
	 * Velocityラッパークラスを設定します。
	 *
	 * @param wrapper Velocityラッパークラス
	 */
	public void setWrapper(VelocityWrapper wrapper) {
		this.wrapper = wrapper;
	}

	/**
	 * 初期化呼び出しフラグを取得します。
	 *
	 * @return 初期化呼び出しフラグ
	 */
	public boolean isInitFlg() {
		return isInitFlg;
	}
	/**
	 * 初期化呼び出しフラグを設定します。
	 *
	 * @param isInitFlg 初期化呼び出しフラグ
	 */
	public void setInitFlg(boolean isInitFlg) {
		this.isInitFlg = isInitFlg;
	}

	/**
	 * 件名を取得します。
	 *
	 * @return 件名
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 件名を設定します。
	 *
	 * @param subject 件名
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 送信元メールアドレスを取得します。
	 *
	 * @return 送信元メールアドレス
	 */
	public String getSenderMailAddress() {
		return senderMailAddress;
	}
	/**
	 * 送信元メールアドレスを設定します。
	 *
	 * @param senderMailAddress 送信元メールアドレス
	 */
	public void setSenderMailAddress(String senderMailAddress) {
		this.senderMailAddress = senderMailAddress;
	}

	/**
	 * BCCメールアドレスを取得します。
	 *
	 * @return BCCメールアドレス
	 */
	public String getBccMailAddress() {
		return bccMailAddress;
	}
	/**
	 * BCCメールアドレスを設定します。
	 *
	 * @param bccMailAddress BCCメールアドレス
	 */
	public void setBccMailAddress(String bccMailAddress) {
		this.bccMailAddress = bccMailAddress;
	}

	/**
	 * メール本文を取得します。
	 *
	 * @return メール本文
	 */
	public String getMailText() {
		return mailText;
	}
	/**
	 * メール本文を設定します。
	 *
	 * @param mailText メール本文
	 */
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}

	/**
	 * 問い合わせ履歴情報を取得します。
	 *
	 * @return 問い合わせ履歴情報
	 */
	public RcpTToiawaseRireki getToiawaseRirekiInfo() {
		return toiawaseRirekiInfo;
	}
	/**
	 * 問い合わせ履歴情報を設定します。
	 *
	 * @param toiawaseRirekiInfo 問い合わせ履歴情報
	 */
	public void setToiawaseRirekiInfo(RcpTToiawaseRireki toiawaseRirekiInfo) {
		this.toiawaseRirekiInfo = toiawaseRirekiInfo;
	}

	/**
	 * TORES公開メール送信画面ＤＴＯリストを取得します。
	 * 
	 * @return TORES公開メール送信画面ＤＴＯリスト
	 */
	public List<TB013DisclosureMailSendDto> getDisclosureMailSendList() {
		return disclosureMailSendList;
	}

	/**
	 * TORES公開メール送信画面ＤＴＯリストを設定します。
	 * 
	 * @param disclosureMailSendList TORES公開メール送信画面ＤＴＯリスト
	 */
	public void setDisclosureMailSendList(
			List<TB013DisclosureMailSendDto> disclosureMailSendList) {
		this.disclosureMailSendList = disclosureMailSendList;
	}

	/**
	 * TORES公開メール送信画面ＤＴＯリストサイズを取得します。
	 * 
	 * @return TORES公開メール送信画面ＤＴＯリストサイズ
	 */
	public int getDisclosureMailSendListSize() {
		return disclosureMailSendListSize;
	}

	/**
	 * TORES公開メール送信画面ＤＴＯリストサイズを設定します。
	 * 
	 * @param disclosureMailSendListSize TORES公開メール送信画面ＤＴＯリストサイズ
	 */
	public void setDisclosureMailSendListSize(int disclosureMailSendListSize) {
		this.disclosureMailSendListSize = disclosureMailSendListSize;
	}

	/**
	 * 送信先リスト選択顧客ＩＤを取得します。
	 * 
	 * @return 送信先リスト選択顧客ＩＤ
	 */
	public String getSelectKokyakuId() {
		return selectKokyakuId;
	}

	/**
	 * 送信先リスト選択顧客ＩＤを設定します。
	 * 
	 * @param selectKokyakuId 送信先リスト選択顧客ＩＤ
	 */
	public void setSelectKokyakuId(String selectKokyakuId) {
		this.selectKokyakuId = selectKokyakuId;
	}

	/**
	 * 顧客付随管理会社マスタリストを取得します。
	 * 
	 * @return 顧客付随管理会社マスタリスト
	 */
	public List<RcpMKokyakuFKanri> getKokyakuFKanriList() {
		return kokyakuFKanriList;
	}

	/**
	 * 顧客付随管理会社マスタリストを設定します。
	 * 
	 * @param kokyakuFKanriList 顧客付随管理会社マスタリスト
	 */
	public void setKokyakuFKanriList(List<RcpMKokyakuFKanri> kokyakuFKanriList) {
		this.kokyakuFKanriList = kokyakuFKanriList;
	}

	/**
	 * 問い合わせ登録画面からの遷移かを判定します。
	 *
	 * @return true：問い合わせ登録画面からの遷移、false：それ以外
	 */
	public boolean isFromToiawaseEntry() {
		if (this.shoriKbn == null) {
			return false;
		}

		return SHORI_KBN_TOIAWASE_ENTRY == this.shoriKbn.intValue();
	}

	/**
	 * 問い合わせ登録画面からの遷移かを判定します。
	 *
	 * @return true：問い合わせ登録画面からの遷移、false：それ以外
	 */
	public boolean isFromToiawaseRirekiEntry() {
		if (this.shoriKbn == null) {
			return false;
		}

		return SHORI_KBN_TOIAWASE_RIREKI_ENTRY == this.shoriKbn.intValue();
	}

	/**
	 * 依頼登録画面からの遷移かを判定します。
	 *
	 * @return true：依頼登録画面からの遷移、false：それ以外
	 */
	public boolean isFromIraiEntry() {
		if (this.shoriKbn == null) {
			return false;
		}

		return SHORI_KBN_IRAI_ENTRY == this.shoriKbn.intValue();
	}

	/**
	 * 対応報告メールアドレスをカンマで分解し、リストにて返却します。
	 *
	 * @return 対応報告メールアドレスリスト
	 */
	public List<String> getTaioMailAddressList() {
		if (StringUtils.isBlank(this.taioMailAddress)) {
			return new ArrayList<String>();
		}

		return Arrays.asList(this.taioMailAddress.split(","));
	}

	/**
	 * 送信先リストを表示するかを判定します。
	 * 
	 * @return true:表示する、false:表示しない
	 */
	public boolean isSendMailDisplay() {
		if (this.disclosureMailSendList != null
				&& !this.disclosureMailSendList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
