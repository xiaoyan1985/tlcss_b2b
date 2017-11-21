package jp.co.tokaigroup.tlcss_b2b.master.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB103InformationSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB103InformationSearchModel;
import jp.co.tokaigroup.tlcss_b2b.master.service.TB103InformationSearchService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * お知らせ検索アクションクラス。
 *
 * @author v138130
 * @version 4.0 2014/06/18
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb103_information_search.jsp"),
	@Result(name=INPUT, location="tb103_information_search.jsp")
})
public class TB103InformationSearchAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB103InformationSearchModel> , ServiceValidatable{

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB103InformationSearchModel model = new TB103InformationSearchModel();

	/** サービス */
	@Autowired
	private TB103InformationSearchService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("informationSearchInit")
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
	@Action(value="informationSearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb103_information_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			// 検索処理
			searchCommon();

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
	@Action(value="informationSearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb103_information_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			TB103InformationSearchCondition condition = model.getCondition();

			// ページング設定のセット
			condition.setOffset(condition.getOffset());
			condition.setDisplayToMax(true);
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
				addActionMessage("MSG0006");
			}

		} catch (PagerMaxCountException e) {
			// 検索上限の場合
			addActionMessage("MSG0005", String.valueOf(model.getCondition().getMaxCount()));
		}

		return SUCCESS;
	}

	/**
	 * 削除処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="informationDelete",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb103_information_search.jsp")
			}
	)
	public String delete() throws Exception {
		try {
			// 削除処理実行
			service.delete(model);

			// 正常に更新が完了した場合、メッセージを作成
			addActionMessage("MSG0001", "お知らせの削除");

			// 検索処理
			searchCommon();

		} catch (PagerMaxCountException e) {
			// 検索上限の場合
			addActionMessage("MSG0006", String.valueOf(model.getCondition().getMaxCount()));
		}
		return SUCCESS;
	}

	/**
	 * 検索処理を行います。（検索、削除で使用）
	 *
	 * @throws Exception
	 */
	private void searchCommon() throws Exception {
		TB103InformationSearchCondition condition = model.getCondition();

		// 検索条件設定
		// システムマスタ定数Mapから最大検索可能件数、最大表示件数を取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 最大検索可能件数取得
		int searchMax = userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_INFORMATION_SEARCH_MAX_COUNT);
		// 最大表示件数取得
		int displayMax = userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_INFORMATION_SEARCH_MAX_LIMIT_PER_PAGE);

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

		//登録から遷移時、表示条件を配列に格納し直す
		if(condition.getHyojiJoken() != null){
			if(condition.getHyojiJoken().length == 1) {
				condition.setHyojiJoken(condition.getHyojiJoken()[0].split(", "));
			}
		}
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
			addActionMessage("MSG0006");
		}

	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB103InformationSearchModel getModel() {
		return model;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		service.getInitInfo(model);
	}
}

