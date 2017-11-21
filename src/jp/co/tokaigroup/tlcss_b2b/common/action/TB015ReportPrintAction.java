package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB015ReportPrintService;
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
 * 報告書印刷アクションクラス。
 *
 * @author k002785
 * @version 1.0 2015/08/05
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb015_report_print.jsp")
})
public class TB015ReportPrintAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB015ReportPrintModel>{

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB015ReportPrintModel model = new TB015ReportPrintModel();
	
	/** サービス */
	@Autowired
	private TB015ReportPrintService service;
	
	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("reportPrintInit")
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
			
			// 遷移元画面区分が「依頼登録画面」の場合
			if (model.isFromRequestEntry()) {
				// パラメータの問い合わせ履歴NOに値が存在しない場合、処理を中断
				if (StringUtils.isBlank(model.getToiawaseRirekiNo())) {
					throw new ApplicationException("問い合わせ履歴NO不正：パラメータの問い合わせ履歴NO" );
				}
				
				// パラメータの作業状況更新日に値が存在しない場合、処理を中断
				if (model.getSagyoJokyoUpdDt() == null) {
					throw new ApplicationException("作業状況更新日不正：パラメータの作業状況更新日" );
				}
			}
			
			// 遷移元画面区分が「問い合わせ登録画面」「依頼登録画面」以外の場合、処理を中断
			if (!model.isFromInquiryEntry() && !model.isFromRequestEntry()) {
				throw new ApplicationException("遷移元画面区分不正：パラメータの遷移元画面区分" );
			}
			
			// 初期表示取得処理を実施
			model = service.getInitInfo(model);
			// 検索完了でない場合
			if (!model.getCondition().isCompleted()) {
				// エラーメッセージの設定
				addActionError("MSG0034");
			}
			// 送付元(TOKAIをセット)
			model.setRdoSender(TB015ReportPrintModel.SENDER_CD_TOKAI);
			// 正常終了
			return SUCCESS;
		} catch (ForbiddenException e) {
			// 403エラー
			return FORBIDDEN_ERROR;
		}
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB015ReportPrintModel getModel() {
		return model;
	}
}
