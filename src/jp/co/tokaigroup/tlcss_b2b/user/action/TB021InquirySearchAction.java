package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDaoImpl;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB021InquirySearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB021InquirySearchService;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 問い合わせ検索アクションクラス。
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 2.0 2015/10/15 v145527 既読未読追加
 * @version 2.1 2015/12/24 H.Yamamura CSVダウンロードフラグの追加
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb021_inquiry_search.jsp"),
	@Result(name=INPUT, location="tb021_inquiry_search.jsp")
})
public class TB021InquirySearchAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB021InquirySearchModel> {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB021InquirySearchModel model = new TB021InquirySearchModel();

	/** サービス */
	@Autowired
	private TB021InquirySearchService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("inquirySearchInit")
	public String init() throws Exception {

		// 初期表示処理
		model = service.getInitInfo(model);
		// 初期値の設定（状況ラジオ）
		model.getCondition().setJokyo(RC031ToiawaseSearchCondition.JOKYO_RDO_ALL);
		// 初期値の設定（閲覧状況ラジオ）
		model.getCondition().setBrowseStatus(RC031ToiawaseSearchCondition.BROWSE_STATUS_KBN_ALL);
		// ユーザーエージェント設定
		model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

		return SUCCESS;
	}

	/**
	 * 検索処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="inquirySearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb021_inquiry_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			// ユーザーエージェント設定
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			RC031ToiawaseSearchCondition condition = model.getCondition();

			// システムマスタ定数Mapから最大検索可能件数、最大表示件数を取得
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// 最大検索可能件数取得
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_TOIAWASE_MAX_COUNT);
			// 最大表示件数取得
			int displayMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_TOIAWASE_MAX_LIMIT_PER_PAGE);

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
				condition.setToiawaseKokaiFlg(RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI);
				condition.setSearchFlg(RC031ToiawaseSearchCondition.SEARCH_FLG_GAIBU);
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
			condition.setKanrenJoho(RcpTToiawaseDaoImpl.SEARCH_KANREN_JOHO_TRUE);
			

			//依頼有無区分に空値(全て)をいれる
			condition.setIraiUmuRdo(RcpTToiawaseDaoImpl.SEARCH_SORT_IRAI_UMU_RDO_ALL);

			model.setCondition(condition);

			// 検索処理実行
			model = service.executeSearch(model, false);

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
	@Action(value="inquirySearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb021_inquiry_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			// ユーザーエージェント設定
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			RC031ToiawaseSearchCondition condition = model.getCondition();
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
			
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
				condition.setToiawaseKokaiFlg(RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI);
				condition.setSearchFlg(RC031ToiawaseSearchCondition.SEARCH_FLG_GAIBU);
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
			condition.setKanrenJoho(RcpTToiawaseDaoImpl.SEARCH_KANREN_JOHO_TRUE);

			//依頼有無区分に空値(全て)をいれる
			condition.setIraiUmuRdo(RcpTToiawaseDaoImpl.SEARCH_SORT_IRAI_UMU_RDO_ALL);

			model.setCondition(condition);

			// 検索処理実行
			model = service.executeSearch(model, false);
			
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
	public TB021InquirySearchModel getModel() {
		return model;
	}
}
