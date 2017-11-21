package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.ArrayList;
import java.util.List;

import jp.co.tokaigroup.reception.dao.RcpMKokyakuDaoImpl;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB011RealEstateAgencySelectTestModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB011RealEstateAgencySelectTestService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 不動産・管理会社選択画面（スタブ）アクションクラス。
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="../test/jsp/test_tb011_real_estate_agency_select.jsp"),
	@Result(name=INPUT, location="../test/jsp/test_tb011_real_estate_agency_select.jsp")
})
public class TB011RealEstateAgencySelectTestAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB011RealEstateAgencySelectTestModel> {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB011RealEstateAgencySelectTestModel model = new TB011RealEstateAgencySelectTestModel();

	/** サービス */
	@Autowired
	private TB011RealEstateAgencySelectTestService service;

	@Action("realEstateAgencyTestInit")
	public String init() throws Exception {
		RC011KokyakuSearchCondition condition = new RC011KokyakuSearchCondition();

		List<String> kokyakuKbnList = new ArrayList<String>();
		kokyakuKbnList.add(RcpMKokyaku.KOKYAKU_KBN_FUDOSAN);

		condition.setKokyakuKbnList(kokyakuKbnList);
		condition.setKanrenJoho(RcpMKokyakuDaoImpl.SEARCH_KANREN_JOHO_TRUE);
		condition.setOffset(1);
		condition.setLimit(-1);
		condition.setDisplayToMax(true);

		model.setCondition(condition);

		model = service.getKokyakuList(model);

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB011RealEstateAgencySelectTestModel getModel() {
		return model;
	}

}
