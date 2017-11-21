package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010ManagedTargetListModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB010ManagedTargetListService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 管理対象一覧アクションクラス。
 *
 * @author v140546
 * @version 4.0 2014/09/25
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb010_managed_target_list.jsp"),
	@Result(name=INPUT, location="tb010_managed_target_list.jsp")
})
public class TB010ManagedTargetListAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB010ManagedTargetListModel> , ServiceValidatable{

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB010ManagedTargetListModel model = new TB010ManagedTargetListModel();

	/** サービス */
	@Autowired
	private TB010ManagedTargetListService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("managedTargetListInit")
	public String init() throws Exception {
		// 初期表示処理
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * 顧客選択を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="managedTargetListChooseId",
			results = {@Result(name=SUCCESS, type="redirectAction",
					location="menuInit")}
	)
	public String chooseId() throws Exception {

		// 顧客選択処理を行います。
		model = service.selectKokyakuId(model);

		// ログインユーザ情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		userContext.setRefKokyakuId(model.getSelectedKokyakuId());
		userContext.setRole(TLCSSB2BUserContext.ROLE_REAL_ESTATE);
		userContext.setAccessibleMap(model.getAccessibleMap());
		userContext.setKokyakuId(model.getSelectedKokyakuId());
		userContext.setKokyakuName(model.getKokyakuInfo().getKanjiNm());
		
		if (model.getRefKokyakuList().size() == 1 && model.getRefKokyakuList().get(0).isLowerDisplay()) {
		// 取得した顧客ＩＤが1件かつ、下位層表示フラグが「1：表示」の場合
			userContext.setKokyakuIdSelected(true);
			userContext.setSingleFlg(true);
		} else {
			userContext.setKokyakuIdSelected(false);
			userContext.setSingleFlg(false);
		}

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB010ManagedTargetListModel getModel() {
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
