package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB044ReceptionListPrintModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB044ReceptionListPrintService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 受付一覧印刷アクションクラス。
 *
 * @author v140546
 * @version 1.0 2014/07/08
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name= SUCCESS, location="tb044_reception_list_print.jsp"),
	@Result(name= INPUT, location="tb044_reception_list_print.jsp")
})
public class TB044ReceptionListPrintAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB044ReceptionListPrintModel>{

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB044ReceptionListPrintModel model = new TB044ReceptionListPrintModel();

	/** サービス */
	@Autowired
	private TB044ReceptionListPrintService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("receptionListInit")
	public String init() throws Exception {

		// 初期表示処理
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB044ReceptionListPrintModel getModel() {
		return model;
	}
}
