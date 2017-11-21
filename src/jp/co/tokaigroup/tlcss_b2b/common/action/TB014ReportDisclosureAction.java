package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB014ReportDisclosureModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB014ReportDisclosureService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 報告書公開設定アクションクラス。
 *
 * @author k002785
 * @version 1.0 2015/08/11
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb014_report_disclosure.jsp"),
	@Result(name=INPUT, location="tb014_report_disclosure.jsp")
})
public class TB014ReportDisclosureAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB014ReportDisclosureModel>, ServiceValidatable{

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB014ReportDisclosureModel model = new TB014ReportDisclosureModel();
	
	/** サービス */
	@Autowired
	private TB014ReportDisclosureService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("reportDisclosureInit")
	public String init() throws Exception {
		
		try{
			// パラメータの問い合わせNOに値が存在しない場合、処理を中断
			if (StringUtils.isBlank(model.getToiawaseNo())) {
				throw new ApplicationException("問い合わせNO不正：パラメータの問い合わせNO" );
			}
			
			// パラメータの問い合わせ更新日に値が存在しない場合、処理を中断
			if (model.getToiawaseUpdDt() == null) {
				throw new ApplicationException("問い合わせ更新日不正：パラメータの問い合わせ更新日" );
			}
			
			// 帳票区分が「作業報告書」の場合
			if (model.isPrintableWorkReport()) {
				// パラメータの問い合わせ履歴NOに値が存在しない場合、処理を中断
				if (StringUtils.isBlank(model.getToiawaseRirekiNo())) {
					throw new ApplicationException("問い合わせ履歴NO不正：パラメータの問い合わせ履歴NO" );
				}
				
				// パラメータの作業状況更新日に値が存在しない場合、処理を中断
				if (model.getSagyoJokyoUpdDt() == null) {
					throw new ApplicationException("作業状況更新日不正：パラメータの作業状況更新日" );
				}
			}
			
			// パラメータの顧客IDに値が存在しない場合、処理を中断
			if (StringUtils.isBlank(model.getKokyakuId())) {
				throw new ApplicationException("顧客ＩＤ不正：パラメータの顧客ＩＤ" );
			}
			
			// 帳票区分が「入電報告書」「作業報告書」以外の場合、処理を中断
			if (!model.isPrintableIncomingCallReport() && !model.isPrintableWorkReport()) {
				throw new ApplicationException("帳票区分不正：パラメータの帳票区分" );
			}
			
			// 初期情報取得の実施
			model = service.getInitInfo(model);
			// 正常終了
			return SUCCESS;
		} catch (ForbiddenException e) {
			// 403エラー
			return FORBIDDEN_ERROR;
		}
	}
	
	/**
	 * 更新処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("reportDisclosureUpdate")
	public String update() throws Exception {
		
		// 公開処理を行います。
		model = service.discloseReport(model);
		// 完了メッセージ
		addActionMessage("MSG0001", model.getGamenNm() + "の公開");
		// 正常終了
		return SUCCESS;
	}
	
	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB014ReportDisclosureModel getModel() {
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
