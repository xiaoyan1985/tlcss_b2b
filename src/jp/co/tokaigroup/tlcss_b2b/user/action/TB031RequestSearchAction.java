package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTIraiDaoImpl;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB031RequestSearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB031RequestSearchService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 依頼検索アクションクラス。
 *
 * @author k002849
 * @version 1.0 2014/05/12
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb031_request_search.jsp"),
	@Result(name=INPUT, location="tb031_request_search.jsp")
})
public class TB031RequestSearchAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB031RequestSearchModel> {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB031RequestSearchModel model = new TB031RequestSearchModel();

	/** サービス */
	@Autowired
	private TB031RequestSearchService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("requestSearchInit")
	public String init() throws Exception {
		// 初期表示処理
		model = service.getInitInfo(model);

		// 初期値セット（リセプションの依頼検索とは違い、同一画面のため、JSPではセットしづらいため、ここでセット）
		RC041IraiSearchCondition condition = model.getCondition();
		condition.setIraiKanryo(RcpTIraiDaoImpl.SEARCH_IRAI_KANRYO_RDO_ALL);	// 作業完了
		condition.setShimeSts(RcpTIraiDaoImpl.SEARCH_SHIME_RDO_MAE);			// 締め年月
		condition.setJokyo(RC041IraiSearchCondition.JOKYO_RDO_ALL);	// 状況ラジオ

		model.setCondition(condition);

		return SUCCESS;
	}

	/**
	 * 検索処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="requestSearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb031_request_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			RC041IraiSearchCondition condition = model.getCondition();

			// システムマスタ定数Mapから最大検索可能件数、最大表示件数を取得
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// 最大検索可能件数取得
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_MAX_COUNT);
			// 最大表示件数取得
			int displayMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_MAX_LIMIT_PER_PAGE);

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

			// 検索条件設定
			if (userContext.isRealEstate()) {
				// セッションの権限が管理会社の場合
				if (userContext.isKokyakuIdSelected()) {
					// 顧客選択済の場合
					// 請求先顧客ＩＤには、セッションの顧客ＩＤを設定
					condition.setSeikyusakiKokyakuId(userContext.getKokyakuId());
					// 親顧客ＩＤには、NULLを設定
					condition.setParentKokyakuId(null);
				} else {
					// それ以外の場合は、NULLを設定
					condition.setSeikyusakiKokyakuId(null);
					// 親顧客ＩＤには、セッションの顧客ＩＤを設定
					condition.setParentKokyakuId(userContext.getKokyakuId());
				}
				condition.setIraiKokaiFlg(RcpTIrai.KOKAI_FLG_KOUKAIZUMI);
				condition.setSearchFlg(RC041IraiSearchCondition.SEARCH_FLG_GAIBU);
			} else {
				// セッションの権限が管理会社以外の場合は、何も設定しない
				condition.setSeikyusakiKokyakuId(null);
				condition.setParentKokyakuId(null);
				condition.setSearchFlg(null);
			}
			// 会社ＩＤを設定
			if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
				condition.setOutsourcerKaishaId(userContext.getKaishaId());
			} else {
				condition.setOutsourcerKaishaId(null);
			}
			condition.setIraiGyosha(userContext.getGyoshaCd());
			condition.setKanrenJoho(RcpTIraiDaoImpl.SEARCH_KANREN_JOHO_TRUE);

			model.setCondition(condition);

			// 検索処理実行
			model = service.executeSearch(model);

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
	 * ページリンク処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="requestSearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb031_request_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			RC041IraiSearchCondition condition = model.getCondition();
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// セッションの権限が管理会社、かつ、顧客選択済の場合
			if (userContext.isRealEstate()) {
				// セッションの権限が管理会社の場合
				if (userContext.isKokyakuIdSelected()) {
					// 顧客選択済の場合
					// 請求先顧客ＩＤには、セッションの顧客ＩＤを設定
					condition.setSeikyusakiKokyakuId(userContext.getKokyakuId());
					// 親顧客ＩＤには、NULLを設定
					condition.setParentKokyakuId(null);
				} else {
					// それ以外の場合は、NULLを設定
					condition.setSeikyusakiKokyakuId(null);
					// 親顧客ＩＤには、セッションの顧客ＩＤを設定
					condition.setParentKokyakuId(userContext.getKokyakuId());
				}
				condition.setIraiKokaiFlg(RcpTIrai.KOKAI_FLG_KOUKAIZUMI);
				condition.setSearchFlg(RC041IraiSearchCondition.SEARCH_FLG_GAIBU);
			} else {
				// セッションの権限が管理会社以外の場合は、何も設定しない
				condition.setSeikyusakiKokyakuId(null);
				condition.setParentKokyakuId(null);
				condition.setSearchFlg(null);
			}
			// 会社ＩＤを設定
			if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
				condition.setOutsourcerKaishaId(userContext.getKaishaId());
			} else {
				condition.setOutsourcerKaishaId(null);
			}
			condition.setIraiGyosha(userContext.getGyoshaCd());
			condition.setKanrenJoho(RcpTIraiDaoImpl.SEARCH_KANREN_JOHO_TRUE);


			model.setCondition(condition);

			// 検索処理実行
			model = service.executeSearch(model);

			if (!condition.isCompleted()) {
				// 検索完了フラグが「false」（該当件数オーバー）の場合
				addActionMessage("MSG0039", String.valueOf(model.getCondition().getActualCount()));
			}
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
	public TB031RequestSearchModel getModel() {
		return model;
	}
}
