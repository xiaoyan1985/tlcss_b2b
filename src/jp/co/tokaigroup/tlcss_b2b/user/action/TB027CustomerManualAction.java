package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB027CustomerManualModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB027CustomerManualService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 顧客マニュアル一覧アクションクラス。
 *
 * @author 松葉
 * @version 1.0 2015/08/05
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb027_customer_manual.jsp")
})
public class TB027CustomerManualAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB027CustomerManualModel> {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB027CustomerManualModel model = new TB027CustomerManualModel();

	/** サービス */
	@Autowired
	private TB027CustomerManualService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 顧客マニュアル一覧画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("customerManualInit")
	public String init() throws Exception {
		if (StringUtils.isBlank(model.getKokyakuId())) {
			// パラメータの顧客ＩＤが取得できない場合は、パラメータエラー
			throw new ApplicationException("顧客ＩＤ不正：パラメータの顧客ＩＤ");
		}

		// 初期表示処理
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 顧客マニュアル一覧画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB027CustomerManualModel getModel() {
		return model;
	}
}
