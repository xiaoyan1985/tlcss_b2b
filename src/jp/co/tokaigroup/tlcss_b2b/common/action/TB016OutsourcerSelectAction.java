package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB016OutsourcerSelectCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB016OutsourcerSelectModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB016OutsourcerSelectService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 委託会社選択アクションクラス。
 *
 * @author k002785
 * @version 1.0 2015/09/08
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb016_outsourcer_select.jsp")
})
public class TB016OutsourcerSelectAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB016OutsourcerSelectModel> {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB016OutsourcerSelectModel model = new TB016OutsourcerSelectModel();

	/** サービス */
	@Autowired
	private TB016OutsourcerSelectService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("outsourcerInit")
	public String init() throws Exception {
		
		// 親画面の会社ＩＤ入力欄のnameの値が存在しない場合
		if (StringUtils.isBlank(model.getKaishaIdResultNm())) {
			throw new ApplicationException("親画面の会社ＩＤ入力欄のname不正：パラメータの親画面の会社ＩＤ入力欄のname" );
		}
		
		// 親画面の会社名入力欄のnameの値が存在しない場合
		if (StringUtils.isBlank(model.getKaishaNmResultNm())) {
			throw new ApplicationException("親画面の会社名入力欄のname不正：パラメータの親画面の会社名入力欄のname" );
		}

		// 初期表示時、コンデションを初期値で設定
		model.setCondition(null);

		// 正常終了
		return SUCCESS;
	}
	
	/**
	 * 検索処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("outsourcerSearch")
	public String search() throws Exception {

		// 画面の入力パラメータに紐づく検索条件を取得
		TB016OutsourcerSelectCondition condition = model.getCondition();

		// システムマスタ定数Mapから最大検索可能件数、最大表示件数を取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 最大検索可能件数取得
		int searchMax = userContext.getSystgemContstantAsInt(
			RcpMSystem.RCP_M_SYSTEM_B2B_CONSIGNMENT_COMPANY_MAX_COUNT);

		// 最大検索件数の設定
		condition.setMaxCount(searchMax);

		// 検索条件の設定
		model.setCondition(condition);

		// 検索処理実行
		model = service.executeSearch(model);

		// 検索結果の判定（検索結果の返却値はnullの場合はないためemptyで行う）
		if (model.getResultList().isEmpty()) {
			// 検索結果が0件の場合
			addActionMessage("MSG0002");
		} else {
			// 検索結果が上限を超えていない場合
			if (condition.isCompleted()) {
				addActionMessage("MSG0004", String.valueOf(model.getResultList().size()));
			} else {
				// 検索上限を超えた場合
				addActionMessage("MSG0006");
			}
		}

		// 正常終了
		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB016OutsourcerSelectModel getModel() {
		return model;
	}
}
