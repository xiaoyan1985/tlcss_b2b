package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB013DisclosureMailSendModel;

/**
 * TORES公開メール送信画面サービスクラス。
 *
 * @author k003856
 * @version 5.0 2015/09/08
 */
public interface TB013DisclosureMailSendService {
	/**
	 * 初期表示処理を行います。
	 *
	 * @param model TORES公開メール送信画面モデル
	 * @return TORES公開メール送信画面モデル
	 */
	public TB013DisclosureMailSendModel getInitInfo(TB013DisclosureMailSendModel model);

	/**
	 * メール送信処理を行います。
	 *
	 * @param model TORES公開メール送信画面モデル
	 */
	public void executeSendMail(TB013DisclosureMailSendModel model);
}
