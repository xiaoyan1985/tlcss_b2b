package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB026InquiryHistoryMoveModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB026InquiryHistoryMoveService;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 問い合わせ履歴移動画面アクションクラス。
 *
 * @author 松葉
 * @version 1.0 2015/08/11
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb026_inquiry_history_move.jsp"),
	@Result(name=INPUT, location="tb026_inquiry_history_move.jsp")
 })
public class TB026InquiryHistoryMoveAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB026InquiryHistoryMoveModel>, ServiceValidatable {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB026InquiryHistoryMoveModel model = new TB026InquiryHistoryMoveModel();

	/** サービス */
	@Autowired
	private TB026InquiryHistoryMoveService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("inquiryHistoryMoveInit")
	public String init() throws Exception {
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			// パラメータの移動元問い合わせＮＯが取得できない場合は、パラメータエラー
			throw new ApplicationException("移動元問い合わせＮＯ不正：パラメータの移動元問い合わせＮＯ");
		}

		if (StringUtils.isBlank(model.getNewToiawaseNo())) {
			// パラメータの入力問い合わせＮＯが取得できない場合は、パラメータエラー
			throw new ApplicationException("入力問い合わせＮＯ不正：パラメータの入力問い合わせＮＯ");
		}

		if (model.getToiawaseUpdDt() == null) {
			// パラメータの問い合わせ更新日が取得できない場合は、パラメータエラー
			throw new ApplicationException("問い合わせ更新日不正：パラメータの問い合わせ更新日");
		}

		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * 問い合わせ情報の更新を行います。
	 *
	 * @return 顧客基本情報登録画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="inquiryHistoryMoveUpdate")
	public String update() throws Exception {
		// 更新処理
		service.updateInquiryHistoryMoveInfo(model);

		addActionMessage("MSG0001", "問い合わせ履歴の移動");

		// 正常終了メッセージIDの設定
		model.setCompleteMessageId("MSG0001");

		// 正常終了メッセージ文言の設定
		model.setCompleteMessageStr("問い合わせ履歴の移動");

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB026InquiryHistoryMoveModel getModel() {
		return model;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		if (!model.isInitError()) {
			model = service.getInitInfo(model);
		}
	}
}