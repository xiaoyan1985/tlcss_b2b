package jp.co.tokaigroup.tlcss_b2b.master.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB101UserMasterSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB101UserMasterSearchModel;
import jp.co.tokaigroup.tlcss_b2b.master.service.TB101UserMasterSearchService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ユーザーマスタ検索アクションクラス。
 *
 * @author k002849
 * @version 1.0 2014/05/27
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb101_user_master_search.jsp"),
	@Result(name=INPUT, location="tb101_user_master_search.jsp")
})
public class TB101UserMasterSearchAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB101UserMasterSearchModel> {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB101UserMasterSearchModel model = new TB101UserMasterSearchModel();

	/** サービス */
	@Autowired
	private TB101UserMasterSearchService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("userMasterSearchInit")
	public String init() throws Exception {
		// 初期表示処理
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * 検索処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="userMasterSearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb101_user_master_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			TB101UserMasterSearchCondition condition = model.getCondition();

			// システムマスタ定数Mapから最大検索可能件数、最大表示件数を取得
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// 最大検索可能件数取得
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_USER_MASTER_MAX_COUNT);
			// 最大表示件数取得
			int displayMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_USER_MASTER_MAX_LIMIT_PER_PAGE);

			if ((condition.getOffset() == 0) ||
				(condition.getOffset() > 0 && condition.getLimit() != -1)) {
				// 初回検索（オフセットが０）、
				// ２回目以降の検索（オフセットが１以上、最大表示件数が-1でない）は、
				// オフセットを1にセット
				condition.setOffset(1);
			}
			condition.setDisplayToMax(true);
			condition.setLimit(displayMax);
			condition.setMaxCount(searchMax);

			model.setCondition(condition);

			// 検索処理実行
			model = service.executeSearch(model);

			// 検索結果の判定
			if (model.getResultList() == null || model.getResultList().isEmpty()) {
				// 検索結果が0件の場合
				addActionMessage("MSG0002");
			} else if (!condition.isCompleted()) {
				// 検索完了フラグが「false」（該当件数オーバー）の場合
				addActionMessage("MSG0006");
			}
		} catch (PagerMaxCountException e) {
			// 検索上限の場合
			addActionMessage("MSG0005", String.valueOf(model.getCondition().getMaxCount()));
		}

		return SUCCESS;
	}

	/**
	 * ページリンク処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="userMasterSearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb101_user_master_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			// 検索処理実行
			model = service.executeSearch(model);

		} catch (PagerMaxCountException e) {
			// 検索上限の場合
			addActionMessage("MSG0005", String.valueOf(model.getCondition().getMaxCount()));
		}

		// 検索結果の判定
		if (model.getResultList() == null || model.getResultList().isEmpty()) {
			// 検索結果が0件の場合
			addActionMessage("MSG0002");
		}

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB101UserMasterSearchModel getModel() {
		return model;
	}
}
