package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import static com.opensymphony.xwork2.Action.INPUT;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB045AccompanyingInfoModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB045AccompanyingInfoService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 顧客詳細付随情報アクションクラス。
 *
 * @author k003316
 * @version 1.0 2015/08/03
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb045_accompanying_info.jsp"),
	@Result(name=INPUT, location="tb045_accompanying_info.jsp")
})
public class TB045AccompanyingInfoAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB045AccompanyingInfoModel>, ServiceValidatable {
	
	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB045AccompanyingInfoModel model = new TB045AccompanyingInfoModel();

	/** サービス */
	@Autowired
	private TB045AccompanyingInfoService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 顧客詳細付随情報モデル	
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("accompanyingInfoInit")
	public String init() throws Exception {

		try {
			// パラメータの顧客IDがNULL、または、空欄の場合、処理を中断
			if (StringUtils.isBlank(model.getKokyakuId())) {
				throw new ApplicationException("顧客ID不正：パラメータの顧客ID" );
			}
			
			// パラメータの画面区分が「tb045：顧客詳細付随情報参照」以外の場合、処理を中断
			if (! Constants.GAMEN_KBN_CUSTOMER_DETAIL_ACCOMPANYING.equals(model.getGamenKbn())) {
				throw new ApplicationException("画面区分不正：パラメータの画面区分");
			}
			
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

		model.setGamenKbn(Constants.GAMEN_KBN_CUSTOMER_DETAIL_ACCOMPANYING);
		
		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB045AccompanyingInfoModel getModel() {
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