package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB013DisclosureMailSendModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB013DisclosureMailSendService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * TORES公開メール送信アクションクラス。
 *
 * @author k003856
 * @version 5.0 2015/09/08
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb013_disclosure_mail_send.jsp"),
	@Result(name=INPUT, location="tb013_disclosure_mail_send.jsp")
})
public class TB013DisclosureMailSendAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB013DisclosureMailSendModel>, ServiceValidatable {

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB013DisclosureMailSendModel model = new TB013DisclosureMailSendModel();

	/** サービス */
	@Autowired
	private TB013DisclosureMailSendService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return TORES公開メール送信画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("disclosureMailSendInit")
	public String init() throws Exception {
		// 初期表示処理
		model.setActionType("");
		
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			throw new ApplicationException("問い合わせNO不正：パラメータの問い合わせNO");
		}

		// 処理区分チェック
		if (!model.isFromToiawaseEntry() && !model.isFromIraiEntry() 
				&& !model.isFromToiawaseRirekiEntry()) {
			throw new ApplicationException("処理区分：パラメータの処理区分");
		}

		// 問い合わせ履歴NOチェック
		if ((model.isFromIraiEntry() || model.isFromToiawaseRirekiEntry()) && model.getToiawaseRirekiNo() == null) {
			throw new ApplicationException("問い合わせ履歴NO不正：パラメータの問い合わせ履歴NO");
		}

		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * メール送信処理を行います。
	 *
	 * @return TORES公開メール送信画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("disclosureMailSendSendMail")
	public String sendMail() throws Exception {
		try {
			// メール送信処理
			service.executeSendMail(model);

			// 完了メッセージ
			addActionMessage("MSG0001", "公開メール送信");
			
			model = service.getInitInfo(model);

			return SUCCESS;
		} catch (ApplicationException e) {
			log.error("メール送信に失敗しました。", e);
			model = service.getInitInfo(model);
			// 処理失敗メッセージ
			throw new ValidationException(new ValidationPack().addError(
					"MSG0040", "公開メール送信"));
		}
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model TORES公開メール送信画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB013DisclosureMailSendModel getModel() {
		return model;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
	}
}
