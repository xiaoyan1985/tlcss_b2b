package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB025CustomerIdChangeModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB025CustomerIdChangeService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 顧客ＩＤ変更画面アクションクラス。
 *
 * @author k002785
 * @version 1.0 2015/08/19
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb025_customer_id_change.jsp"),
	@Result(name=INPUT, location="tb025_customer_id_change.jsp")
 })
public class TB025CustomerIdChangeAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB025CustomerIdChangeModel>, ServiceValidatable {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB025CustomerIdChangeModel model = new TB025CustomerIdChangeModel();

	/** サービス */
	@Autowired
	private TB025CustomerIdChangeService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("customerIdChangeInit")
	public String init() throws Exception {
	
		// パラメータの問い合わせNOに値が存在しない場合、処理を中断
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			throw new ApplicationException("問い合わせＮＯ不正：パラメータの問い合わせＮＯ" );
		}
	
		// パラメータの問い合わせ更新日に値が存在しない場合、処理を中断
		if (model.getToiawaseUpdDt() == null) {
			throw new ApplicationException("問い合わせ更新日不正：パラメータの問い合わせ更新日" );
		}
	
		// パラメータの入力顧客IDに値が存在しない場合、処理を中断
		if (StringUtils.isBlank(model.getNewKokyakuId())) {
			throw new ApplicationException("入力顧客ＩＤ不正：パラメータの入力顧客ＩＤ" );
		}

		// 初期表示処理実施
		model = service.getInitInfo(model);
	
		// 正常終了
		return SUCCESS;
	}
	
	/**
	 * 更新処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="customerIdChangeUpdate")
	public String update() throws Exception {
		// 更新処理の実施
		service.updateToiawaseKokyakuInfo(model);

		// 正常終了メッセージの設定
		addActionMessage("MSG0001", "顧客IDの変更");

		// 正常終了メッセージIDの設定
		model.setCompleteMessageId("MSG0001");

		// 正常終了メッセージ文言の設定
		model.setCompleteMessageStr("顧客IDの変更");

		// 正常終了
		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB025CustomerIdChangeModel getModel() {
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
