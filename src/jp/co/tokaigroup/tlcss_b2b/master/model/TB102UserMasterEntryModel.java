package jp.co.tokaigroup.tlcss_b2b.master.model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

import jp.co.tokaigroup.reception.dto.RC02BKokyakuNodeDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.message.MessageBean;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * ユーザーマスタ登録画面モデル。
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
public class TB102UserMasterEntryModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "ユーザーマスタ登録";
	/** ボタン名：再発行 */
	public static final String BUTTON_NM_REISSUE = "再発行";

	/** メール通知フラグ 1:送信する */
	public static final String SEND_MAIL_FLG_ON = "1";

	/** hidden出力除外項目 */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** ユーザー情報 */
	private TbMUser user;

	/** メール送信フラグ */
	private String sendMailFlg;

	/** パスワード再発行メール送信フラグ */
	private String passwdSendMailFlg;

	/** 権限リスト */
	private List<RcpMComCd> roleList;

	/** 重複項目エラーメッセージリスト */
	private List<MessageBean> duplicateContetErrorMessageList;
	/** 重複項目エラーメッセージ */
	private String ducplicateContentErrorMessage;

	// 以下、画面パラメータ
	/** 連番 */
	private BigDecimal seqNo;
	/** 問い合わせ検索条件 */
	private TB101UserMasterSearchCondition condition = new TB101UserMasterSearchCondition();

	// 選択用パラメータ
	/** 顧客ＩＤラベルname属性名 */
	private String kokyakuIdResultNm;
	/** 顧客名ラベルname属性名 */
	private String kokyakuNmResultNm;
	/** 業者コードラベルname属性名 */
	private String gyoshaCdResultNm;
	/** 業者名ラベルname属性名 */
	private String gyoshaNmResultNm;
	/** TOKAI顧客リスト */
	private List<RcpMKokyaku> tokaiKokyakuList;
	/** TOKAI顧客リストテキスト（全行） */
	private String tokaiKokyakuListTexts;
	/** 顧客階層DTOリスト */
	private List<RC02BKokyakuNodeDto> kokyakuNodeList;

	/** ユーザ名（更新-更新初期表示時のアクションクラスのパラメータ用） */
	private String encodedUserNm;

	/**
	 * ユーザー情報を取得します。
	 *
	 * @return ユーザー情報
	 */
	public TbMUser getUser() {
		return user;
	}
	/**
	 * ユーザー情報を設定します。
	 *
	 * @param user ユーザー情報
	 */
	public void setUser(TbMUser user) {
		this.user = user;
	}

	/**
	 * メール送信フラグを取得します。
	 *
	 * @return メール送信フラグ
	 */
	public String getSendMailFlg() {
		return sendMailFlg;
	}
	/**
	 * メール送信フラグを設定します。
	 *
	 * @param sendMailFlg メール送信フラグ
	 */
	public void setSendMailFlg(String sendMailFlg) {
		this.sendMailFlg = sendMailFlg;
	}

	/**
	 * パスワード再発行メール送信フラグを取得します。
	 *
	 * @return パスワード再発行メール送信フラグ
	 */
	public String getPasswdSendMailFlg() {
		return passwdSendMailFlg;
	}
	/**
	 * パスワード再発行メール送信フラグを設定します。
	 *
	 * @param passwdSendMailFlg パスワード再発行メール送信フラグ
	 */
	public void setPasswdSendMailFlg(String passwdSendMailFlg) {
		this.passwdSendMailFlg = passwdSendMailFlg;
	}

	/**
	 * 権限リストを取得します。
	 *
	 * @return 権限リスト
	 */
	public List<RcpMComCd> getRoleList() {
		return roleList;
	}
	/**
	 * 権限リストを設定します。
	 *
	 * @param roleList 権限リスト
	 */
	public void setRoleList(List<RcpMComCd> roleList) {
		this.roleList = roleList;
	}
	/**
	 * 重複項目エラーメッセージリストを取得します。
	 *
	 * @return 重複項目エラーメッセージリスト
	 */
	public List<MessageBean> getDuplicateContetErrorMessageList() {
		return duplicateContetErrorMessageList;
	}
	/**
	 * 重複項目エラーメッセージリストを設定します。
	 *
	 * @param duplicateContetErrorMessageList 重複項目エラーメッセージリスト
	 */
	public void setDuplicateContetErrorMessageList(
			List<MessageBean> duplicateContetErrorMessageList) {
		this.duplicateContetErrorMessageList = duplicateContetErrorMessageList;
	}

	/**
	 * 重複項目エラーメッセージを取得します。
	 *
	 * @return 重複項目エラーメッセージ
	 */
	public String getDucplicateContentErrorMessage() {
		return ducplicateContentErrorMessage;
	}
	/**
	 * 重複項目エラーメッセージを設定します。
	 *
	 * @param ducplicateContentErrorMessage 重複項目エラーメッセージ
	 */
	public void setDucplicateContentErrorMessage(
			String ducplicateContentErrorMessage) {
		this.ducplicateContentErrorMessage = ducplicateContentErrorMessage;
	}

	/**
	 * 連番を取得します。
	 *
	 * @return 連番
	 */
	public BigDecimal getSeqNo() {
		return seqNo;
	}
	/**
	 * 連番を設定します。
	 *
	 * @param seqNo 連番
	 */
	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * 問い合わせ検索条件を取得します。
	 *
	 * @return 問い合わせ検索条件
	 */
	public TB101UserMasterSearchCondition getCondition() {
		return condition;
	}
	/**
	 * 問い合わせ検索条件を設定します。
	 *
	 * @param condition 問い合わせ検索条件
	 */
	public void setCondition(TB101UserMasterSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 顧客ＩＤラベルname属性名を取得します。
	 *
	 * @return 顧客ＩＤラベルname属性名
	 */
	public String getKokyakuIdResultNm() {
		return kokyakuIdResultNm;
	}
	/**
	 * 顧客ＩＤラベルname属性名を設定します。
	 *
	 * @param kokyakuIdResultNm 顧客ＩＤラベルname属性名
	 */
	public void setKokyakuIdResultNm(String kokyakuIdResultNm) {
		this.kokyakuIdResultNm = kokyakuIdResultNm;
	}

	/**
	 * 顧客名ラベルname属性名を取得します。
	 *
	 * @return 顧客名ラベルname属性名
	 */
	public String getKokyakuNmResultNm() {
		return kokyakuNmResultNm;
	}
	/**
	 * 顧客名ラベルname属性名を設定します。
	 *
	 * @param kokyakuNmResultNm 顧客名ラベルname属性名
	 */
	public void setKokyakuNmResultNm(String kokyakuNmResultNm) {
		this.kokyakuNmResultNm = kokyakuNmResultNm;
	}

	/**
	 * 業者コードラベルname属性名を取得します。
	 *
	 * @return 業者コードラベルname属性名
	 */
	public String getGyoshaCdResultNm() {
		return gyoshaCdResultNm;
	}
	/**
	 * 業者コードラベルname属性名を設定します。
	 *
	 * @param gyoshaCdResultNm 業者コードラベルname属性名
	 */
	public void setGyoshaCdResultNm(String gyoshaCdResultNm) {
		this.gyoshaCdResultNm = gyoshaCdResultNm;
	}

	/**
	 * 業者名ラベルname属性名を取得します。
	 *
	 * @return 業者名ラベルname属性名
	 */
	public String getGyoshaNmResultNm() {
		return gyoshaNmResultNm;
	}
	/**
	 * 業者名ラベルname属性名を設定します。
	 *
	 * @param gyoshaNmResultNm 業者名ラベルname属性名
	 */
	public void setGyoshaNmResultNm(String gyoshaNmResultNm) {
		this.gyoshaNmResultNm = gyoshaNmResultNm;
	}

	/**
	 * TOKAI顧客リストを取得します。
	 *
	 * @return TOKAI顧客リスト
	 */
	public List<RcpMKokyaku> getTokaiKokyakuList() {
		return tokaiKokyakuList;
	}
	/**
	 * TOKAI顧客リストを設定します。
	 *
	 * @param roleList TOKAI顧客リスト
	 */
	public void setTokaiKokyakuList(List<RcpMKokyaku> tokaiKokyakuList) {
		this.tokaiKokyakuList = tokaiKokyakuList;
	}

	/**
	 * ユーザ名（更新-更新初期表示時のアクションクラスのパラメータ用）を取得します。
	 *
	 * @return ユーザ名（更新-更新初期表示時のアクションクラスのパラメータ用）
	 */
	public String getEncodedUserNm() {
		return encodedUserNm;
	}

	/**
	 * ユーザ名（更新-更新初期表示時のアクションクラスのパラメータ用）を設定します。
	 *
	 * @param encodedUserNm ユーザ名（更新-更新初期表示時のアクションクラスのパラメータ用）
	 */
	public void setEncodedUserNm(String encodedUserNm) {
		try {
			byte[] data = encodedUserNm.getBytes(CharEncoding.ISO_8859_1);
			this.encodedUserNm = new String(data, CharEncoding.UTF_8);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * TOKAI顧客リストテキストを取得します。
	 *
	 * @return TOKAI顧客リストテキスト
	 */
	public String getTokaiKokyakuListTexts() {
		return tokaiKokyakuListTexts;
	}
	/**
	 * TOKAI顧客リストテキストを設定します。
	 *
	 * @param tokaiKokyakuListTexts TOKAI顧客リストテキスト
	 */
	public void setTokaiKokyakuListTexts(String tokaiKokyakuListTexts) {
		this.tokaiKokyakuListTexts = tokaiKokyakuListTexts;
	}
	/**
	 * 顧客階層DTOリストを取得します。
	 *
	 * @return 顧客階層DTOリスト
	 */
	public List<RC02BKokyakuNodeDto> getKokyakuNodeList() {
		return kokyakuNodeList;
	}
	/**
	 * 顧客階層DTOリストを設定します。
	 *
	 * @param kokyakuNodeList 顧客階層DTOリスト
	 */
	public void setKokyakuNodeList(List<RC02BKokyakuNodeDto> kokyakuNodeList) {
		this.kokyakuNodeList = kokyakuNodeList;
	}
	
	/**
	 * メール送信を行うかを判定します。
	 *
	 * @return true:行う、false:行わない
	 */
	public boolean isMailSend() {
		return SEND_MAIL_FLG_ON.equals(this.sendMailFlg);
	}

	/**
	 * 重複項目があるかを判定します。
	 *
	 * @return true:あり、false:なし
	 */
	public boolean isDuplicateContentExists() {
		return !(this.duplicateContetErrorMessageList == null || this.duplicateContetErrorMessageList.isEmpty());
	}

	/**
	 * 削除チェックのチェックON判定を行います。
	 *
	 * @return true:チェックON、false:チェックOFF
	 */
	public boolean isCheckedDelFlg() {
		if (this.user == null) {
			return false;
		}

		return (TbMUser.DEL_FLG_DELETE.equals(this.user.getDelFlg()));
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
	 * メール送信フラグが「送信する」であるかを判定します。
	 *
	 * @return true:送信する、false:送信しない
	 */
	public boolean isCheckedSendMailFlg() {
		return SEND_MAIL_FLG_ON.equals(this.sendMailFlg);
	}

	/**
	 * 顧客階層リストのデータ行数を取得します。
	 *
	 * @return 顧客階層リストのデータ行数
	 */
	public int getKokyakuNodeSize() {
		return (getKokyakuNodeList() != null) ? getKokyakuNodeList().size() : 0;
	}

}
