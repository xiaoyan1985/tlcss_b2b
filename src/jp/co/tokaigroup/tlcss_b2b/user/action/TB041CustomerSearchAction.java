package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import static com.opensymphony.xwork2.Action.INPUT;

import java.util.ArrayList;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB041CustomerSearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB041CustomerSearchService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 物件・入居者検索アクションクラス。
 *
 * @author v138130
 * @version 4.0 2014/06/03
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb041_customer_search.jsp"),
	@Result(name=INPUT, location="tb041_customer_search.jsp")
})
public class TB041CustomerSearchAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB041CustomerSearchModel> , ServiceValidatable{

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB041CustomerSearchModel model = new TB041CustomerSearchModel();

	/** サービス */
	@Autowired
	private TB041CustomerSearchService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("customerSearchInit")
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
	@Action(value="customerSearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb041_customer_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			RC011KokyakuSearchCondition condition = model.getCondition();

			// 検索条件設定
			// システムマスタ定数Mapから最大検索可能件数、最大表示件数を取得
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// 最大検索可能件数取得
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_MAX_COUNT);
			// 最大表示件数取得
			int displayMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_MAX_LIMIT_PER_PAGE);

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
			// 検索条件の設定
			model.setCondition(setCondition(condition));

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
	 * ページリンク処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="customerSearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb041_customer_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			RC011KokyakuSearchCondition condition = model.getCondition();

			// ページング設定のセット
			condition.setOffset(condition.getOffset());
			condition.setDisplayToMax(true);
			// 検索条件の設定
			model.setCondition(setCondition(condition));

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
	 * 検索条件の設定を行います。（共通処理）
	 *
	 * @return
	 * @throws Exception 実行時例外が発生した場合
	 */
	private RC011KokyakuSearchCondition setCondition(RC011KokyakuSearchCondition cond) throws Exception {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// ①請求先顧客ID,関連付けチェック,親顧客ID
		// セッションの権限が管理会社の場合
		if (userContext.isRealEstate()) {
			// 顧客選択済の場合
			if (userContext.isKokyakuIdSelected()) {
				cond.setSeikyusakiKokyakuId(userContext.getKokyakuId());
				cond.setParentKokyakuId(null);
			} else {
				cond.setSeikyusakiKokyakuId(null);
				cond.setParentKokyakuId(userContext.getKokyakuId());
			}
		} else {
			cond.setSeikyusakiKokyakuId(null);
			cond.setParentKokyakuId(null);
		}
		cond.setKanrenJoho(RC011KokyakuSearchCondition.KANREN_JOHO_ON);

		// ②顧客区分(「3:物件」,「4:入居者・個人」)
		List<String> kokyakuKbnList = new ArrayList<String>();
		kokyakuKbnList.add(RcpMKokyaku.KOKYAKU_KBN_BUKKEN);
		kokyakuKbnList.add(RcpMKokyaku.KOKYAKU_KBN_NYUKYOSHA);
		// ログインユーザ権限が「管理会社」以外の場合
		if (!userContext.isRealEstate()) {
			kokyakuKbnList.add(RcpMKokyaku.KOKYAKU_KBN_FUDOSAN);
		}
		cond.setKokyakuKbnList(kokyakuKbnList);
		
		// ログインユーザ権限が「委託会社SV」もしくは「委託会社OP」の場合
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// 会社IDを設定
			cond.setKaishaId(userContext.getKaishaId());
		}

		return cond;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB041CustomerSearchModel getModel() {
		return model;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		model = service.getInitInfo(model);
	}
}

