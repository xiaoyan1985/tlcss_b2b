package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.gyosha.model.RC061GyoshaSearchCondition;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB051CenterContractorSearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB051CenterContractorSearchService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * センター業者検索アクションクラス。
 *
 * @author v145527
 * @version 1.0 2015/09/29
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb051_center_contractor_search.jsp"),
})
public class TB051CenterContractorSearchAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB051CenterContractorSearchModel> {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB051CenterContractorSearchModel model = new TB051CenterContractorSearchModel();

	/** サービス */
	@Autowired
	private TB051CenterContractorSearchService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("centerContractorSearchInit")
	public String init() throws Exception {

		// 初期情報取得処理
		model = service.getInitInfo(model);


		return SUCCESS;
	}
	
	/**
	 * 検索処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="centerContractorSearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb051_center_contractor_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			RC061GyoshaSearchCondition condition = model.getCondition();

			// 検索条件設定
			
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
			// システムマスタ定数Mapから最大検索可能件数、最大表示件数を取得
			// 最大検索可能件数取得
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_CONTRACTOR_SEARCH_MAX_COUNT);
			// 最大表示件数取得
			int displayMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_CONTRACTOR_SEARCH_MAX_LIMIT_PER_PAGE);

			// ページング設定のセット
			if ((condition.getOffset() == 0) ||
					(condition.getOffset() > 0 && condition.getLimit() != -1)) {
				// 初回検索（オフセットが０）、
				// ２回目以降の検索（オフセットが１以上、最大表示件数が-1でない）は、
				// オフセットを1にセット
				condition.setOffset(1);
			}
			condition.setLimit(displayMax);
			condition.setMaxCount(searchMax);
			condition.setDisplayToMax(true);
			
			// 会社ＩＤには、セッションの会社ＩＤを設定
			condition.setKaishaId(userContext.getKaishaId());
			
			// 検索条件の設定
			model.setCondition(condition);

			// 検索処理実行
			model = service.search(model);

			// 検索結果の判定
			if (model.getResultList() == null || model.getResultList().isEmpty()) {
				// 検索結果が0件の場合
				addActionMessage("MSG0002");
			} else if (!condition.isCompleted()) {
				// 検索完了フラグが「false」（該当件数オーバー）の場合
				addActionMessage("MSG0039", String.valueOf(model.getCondition().getActualCount()));
			}

		} catch (PagerMaxCountException e) {
			// 検索上限の場合
			addActionMessage("MSG0005", String.valueOf(model.getCondition().getMaxCount()));
		}

		return SUCCESS;
	}
	
	/**
	 * ページリンクの検索を実行します。
	 *
	 * @return 依頼検索画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="centerContractorSearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb051_center_contractor_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			// 検索条件を取得
			RC061GyoshaSearchCondition condition = model.getCondition();
			// ページング設定のセット
			condition.setOffset(condition.getOffset());
			condition.setDisplayToMax(true);
			
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
			
			// 会社ＩＤには、セッションの会社ＩＤを設定
			condition.setKaishaId(userContext.getKaishaId());
			
			// 検索条件の設定
			model.setCondition(condition);
			
			// 検索処理実行
			model = service.search(model);

			// 検索結果の判定
			if (model.getResultList() == null || model.getResultList().isEmpty()) {
				// 検索結果が0件の場合
				addActionMessage("MSG0002");
			} else if (!condition.isCompleted()) {
				// 検索完了フラグが「false」（該当件数オーバー）の場合
				addActionMessage("MSG0039", String.valueOf(model.getCondition().getActualCount()));
			}

		} catch (PagerMaxCountException e) {
			// 検索上限の場合
			addActionMessage("MSG0005", String.valueOf(model.getCondition().getMaxCount()));
		}

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB051CenterContractorSearchModel getModel() {
		return model;
	}

}
