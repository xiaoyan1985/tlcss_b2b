package jp.co.tokaigroup.tlcss_b2b.user.model;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.common.model.RC000CommonModel;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseManual;

/**
 * 問い合わせ区分マニュアル一覧画面モデル。
 *
 * @author H.Yamamura
 * @version 1.0 2015/11/20
 */
public class TB029InquiryKbnManualModel extends RC000CommonModel{

	/**
	 * 問い合わせ区分１
	 */
	private String toiawaseKbn1;

	/**
	 * 問い合わせ区分２
	 */
	private String toiawaseKbn2;

	/**
	 * 問い合わせ区分３
	 */
	private String toiawaseKbn3;

	/**
	 * 問い合わせ区分４
	 */
	private String toiawaseKbn4;

	/**
	 * デフォルトコンストラクタ。
	 */
	public TB029InquiryKbnManualModel() {
		super();
	}

	/**
	 * 問い合わせ区分１を取得します。
	 * @return 問い合わせ区分１
	 */
	public String getToiawaseKbn1() {
		return toiawaseKbn1;
	}

	/**
	 * 問い合わせ区分１を設定します。
	 * @param toiawaseKbn1 問い合わせ区分１
	 */
	public void setToiawaseKbn1(String toiawaseKbn1) {
		this.toiawaseKbn1 = toiawaseKbn1;
	}

	/**
	 * 問い合わせ区分２を取得します。
	 * @return 問い合わせ区分２
	 */
	public String getToiawaseKbn2() {
		return toiawaseKbn2;
	}

	/**
	 * 問い合わせ区分２を設定します。
	 * @param toiawaseKbn2 問い合わせ区分２
	 */
	public void setToiawaseKbn2(String toiawaseKbn2) {
		this.toiawaseKbn2 = toiawaseKbn2;
	}

	/**
	 * 問い合わせ区分３を取得します。
	 * @return 問い合わせ区分３
	 */
	public String getToiawaseKbn3() {
		return toiawaseKbn3;
	}

	/**
	 * 問い合わせ区分３を設定します。
	 * @param toiawaseKbn3 問い合わせ区分３
	 */
	public void setToiawaseKbn3(String toiawaseKbn3) {
		this.toiawaseKbn3 = toiawaseKbn3;
	}

	/**
	 * 問い合わせ区分４を取得します。
	 * @return 問い合わせ区分４
	 */
	public String getToiawaseKbn4() {
		return toiawaseKbn4;
	}

	/**
	 * 問い合わせ区分４を設定します。
	 * @param toiawaseKbn4 問い合わせ区分４
	 */
	public void setToiawaseKbn4(String toiawaseKbn4) {
		this.toiawaseKbn4 = toiawaseKbn4;
	}

	/**
	 * 問い合わせマニュアル参照キーを取得します。
	 * 
	 * @return 問い合わせマニュアル参照キー
	 */
	public String getToiawaseManualKey() {
		// 問い合わせ区分キーを生成 問い合わせ区分１～４を連結
		String toiawaseKbnKey = "";
		if (StringUtils.isNotBlank(this.toiawaseKbn1)) {
			toiawaseKbnKey += this.toiawaseKbn1;
		} else {
			// 取得できない場合は指定文字列を使用
			toiawaseKbnKey += RcpMToiawaseManual.TOIAWASEKBN_NONEXIST_STR;
		}
		if (StringUtils.isNotBlank(this.toiawaseKbn2)) {
			toiawaseKbnKey += this.toiawaseKbn2;
		} else {
			// 取得できない場合は指定文字列を使用
			toiawaseKbnKey += RcpMToiawaseManual.TOIAWASEKBN_NONEXIST_STR;
		}
		if (StringUtils.isNotBlank(this.toiawaseKbn3)) {
			toiawaseKbnKey += this.toiawaseKbn3;
		} else {
			// 取得できない場合は指定文字列を使用
			toiawaseKbnKey += RcpMToiawaseManual.TOIAWASEKBN_NONEXIST_STR;
		}
		if (StringUtils.isNotBlank(this.toiawaseKbn4)) {
			toiawaseKbnKey += this.toiawaseKbn4;
		} else {
			// 取得できない場合は指定文字列を使用
			toiawaseKbnKey += RcpMToiawaseManual.TOIAWASEKBN_NONEXIST_STR;
		}

		return toiawaseKbnKey;
	}
}
