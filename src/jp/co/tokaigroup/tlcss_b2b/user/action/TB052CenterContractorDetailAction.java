package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB052CenterContractorDetailModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB052CenterContractorDetailService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * センター業者詳細アクションクラス。
 *
 * @author v145527
 * @version 1.0 2015/10/08
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb052_center_contractor_detail.jsp")
 })
public class TB052CenterContractorDetailAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB052CenterContractorDetailModel>, ServiceValidatable {
	
	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB052CenterContractorDetailModel model = new TB052CenterContractorDetailModel();

	/** サービス */
	@Autowired
	private TB052CenterContractorDetailService service;
	
	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("centerContractorDetailInit")
	public String init() throws Exception {
		
		try {
			// 初期表示パラメータチェック
			if (StringUtils.isBlank(model.getGyoshaCd())) {
				// パラメータの問い合わせＮＯが取得できない場合エラー
				throw new ApplicationException("業者コード不正：パラメータの業者コード" );
			}

			// 初期情報取得処理
			model = service.getInitInfo(model);

		} catch (ValidationException e) {
			// 初期表示エラーフラグを設定し、javascriptと内容を表示しないようにする
			model.setInitError(true);
			// メッセージが消えるため、Exceptionから取り出して再設定
			addActionError(e.getValidationPack().getActionErrors().get(0).getMessageId()
					, e.getValidationPack().getActionErrors().get(0).getParams());
		}
		
		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB052CenterContractorDetailModel getModel() {

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
