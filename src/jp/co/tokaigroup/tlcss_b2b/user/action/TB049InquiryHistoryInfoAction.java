package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB049InquiryHistoryInfoModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB049InquiryHistoryInfoService;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 顧客詳細問い合わせ履歴アクションクラス。
 *
 * @author v145527
 * @version 1.0 2015/07/24
 */

@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb049_inquiry_history_info.jsp"),
	@Result(name=INPUT, location="tb049_inquiry_history_info.jsp")
})

public class TB049InquiryHistoryInfoAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB049InquiryHistoryInfoModel>, ServiceValidatable {
	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB049InquiryHistoryInfoModel model = new TB049InquiryHistoryInfoModel();

	/** サービス */
	@Autowired
	private TB049InquiryHistoryInfoService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("inquiryHistoryInfoInit")
	public String init() throws Exception {

		// 初期表示処理
		try {
			// 初期表示パラメータチェック
			if (StringUtils.isBlank(model.getKokyakuId())) {
				// パラメータの顧客ＩＤが取得できない場合エラー
				throw new ApplicationException("顧客ID不正：パラメータの顧客ID" );
			}
			if (! Constants.GAMEN_KBN_CUSTOMER_DETAIL_INQUIRY_HISTORY.equals(model.getGamenKbn())) {
				// パラメータの画面区分がTB049：顧客詳細問い合わせ履歴参照画面でない場合エラー
				throw new ApplicationException("画面区分不正：パラメータの画面区分" );
			}
			
			RC031ToiawaseSearchCondition condition = model.getCondition();
			if (condition == null) {
				condition = new RC031ToiawaseSearchCondition();
			}
			condition.setOffset(1);
			
			condition.setDisplayToMax(true);
			
			if (StringUtils.isNotBlank(model.getKokyakuId()) &&
				model.getKokyakuId().length() < RcpMKokyaku.KOKYAKU_ID_COLUMN_LENGTH) {
				// 顧客ＩＤのMAX値に満たない場合には、C+前0詰めして表示
				model.setKokyakuId("C" + StringUtils.leftPad(model.getKokyakuId(), RcpMKokyaku.KOKYAKU_ID_COLUMN_LENGTH - 1, '0'));
			}
			
			model = service.getInitInfo(model);
		
		} catch (ForbiddenException e) {
			// セキュリティエラーの場合は、403エラーを画面に表示
			return FORBIDDEN_ERROR;
		}

		model.setGamenKbn(Constants.GAMEN_KBN_CUSTOMER_DETAIL_INQUIRY_HISTORY);

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB049InquiryHistoryInfoModel getModel() {
		return model;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	@Override
	public void prepareModel() {
		
	}
}