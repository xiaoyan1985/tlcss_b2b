package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010MenuModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB010MenuService;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * メニューアクションクラス。
 *
 * @author k002849
 * @version 4.0 2014/06/05
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb010_menu.jsp"),
	@Result(name=INPUT, location="tb010_menu.jsp")
})
public class TB010MenuAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB010MenuModel> {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB010MenuModel model = new TB010MenuModel();

	/** サービス */
	@Autowired
	private TB010MenuService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("menuInit")
	public String init() throws Exception {
		// 初期表示処理
		model = service.getInitInfo(model);

		// ユーザーエージェント設定
		model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

		return SUCCESS;
	}

	/**
	 * 顧客選択を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="menuChooseId",
			results = {@Result(name=SUCCESS, type="redirectAction",
					location="menuInit")}
	)
	public String chooseId() throws Exception {

		// ログインユーザ情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 顧客マスタ情報取得
		RcpMKokyaku kokyaku = service.getKokyakuInfo(model.getSelectedKokyakuId());
		
		// 選択した顧客IDをセッションに入れる
		userContext.setKokyakuId(model.getSelectedKokyakuId());
		userContext.setKokyakuIdSelected(true);
		userContext.setKokyakuName(kokyaku.getKanjiNm());

		return SUCCESS;
	}

	/**
	 * 顧客選択画面に戻ります。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="menuBack",
			results = {@Result(name=SUCCESS, type="redirectAction",
					location="menuInit")}
	)
	public String back() throws Exception {

		// ログインユーザ情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 顧客マスタ情報取得
		RcpMKokyaku kokyaku = service.getKokyakuInfo(userContext.getRefKokyakuId());
		
		// 顧客ＩＤ選択を未選択に戻す。
		userContext.setKokyakuId(userContext.getRefKokyakuId());
		userContext.setKokyakuIdSelected(false);
		userContext.setKokyakuName(kokyaku.getKanjiNm());

		return SUCCESS;
	}

	/**
	 * 管理対象一覧画面に戻ります。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="menuBackForAdmin",
			results = {@Result(name=SUCCESS, type="redirectAction",
					location="managedTargetListInit")}
	)
	public String backForAdmin() throws Exception {

		// ログインユーザ情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// アクセス可能ＵＲＬMap取得
		userContext.setAccessibleMap(service.getAccessUrl(userContext.getLoginRole()));

		// 顧客ＩＤ選択を未選択に戻す。
		userContext.setKokyakuIdSelected(false);
		userContext.setRole(userContext.getLoginRole());
		userContext.setRefKokyakuId(null);
		userContext.setKokyakuId(null);
		userContext.setKokyakuName(null);

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB010MenuModel getModel() {
		return model;
	}
}
