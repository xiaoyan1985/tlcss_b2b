package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB011RealEstateAgencySelectCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB011RealEstateAgencySelectModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB011RealEstateAgencySelectService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 不動産･管理会社選択アクションクラス。
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb011_real_estate_agency_select.jsp")
})
public class TB011RealEstateAgencySelectAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB011RealEstateAgencySelectModel> , ServiceValidatable{

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB011RealEstateAgencySelectModel model = new TB011RealEstateAgencySelectModel();

	/** サービス */
	@Autowired
	private TB011RealEstateAgencySelectService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("realEstateAgencyInit")
	public String init() throws Exception {
		//初期表示時、コンデションを初期値に戻す
		model.setCondition(new TB011RealEstateAgencySelectCondition());

		// 遷移元がユーザマスタ登録画面の「権限：TOKAI管理者・TOKAI一般」の場合
		if (StringUtils.isNotBlank(model.getKokyakuListNmResultNm())
				&& "selectTokaiKokyakuInfo".equals(model.getKokyakuListNmResultNm())) {
			// TOKAIフラグをtrueに設定
			model.getCondition().setTokai(true);
		}

		return SUCCESS;
	}

	/**
	 * 検索処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("realEstateAgencySearch")
	public String search() throws Exception {

			TB011RealEstateAgencySelectCondition condition = model.getCondition();

			// システムマスタ定数Mapから最大検索可能件数、最大表示件数を取得
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// 最大検索可能件数取得
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_REAL_ESTATE_MAX_COUNT);

			condition.setMaxCount(searchMax);

			model.setCondition(condition);

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
		} else {
			// 検索結果が取得できた場合
			if (model.getCondition().isCompleted()) {
				addActionMessage("MSG0004", String.valueOf(model.getResultList().size()));
			}else{
				// 検索上限を超えた場合
				addActionMessage("MSG0006");
			}
		}

		return SUCCESS;
	}


	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB011RealEstateAgencySelectModel getModel() {
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
