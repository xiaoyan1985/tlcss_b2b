package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB047ContractDetailModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB047ContractDetailService;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 顧客詳細契約詳細情報アクションクラス。
 *
 * @author k003858
 * @version 1.0 2015/09/17
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb047_contract_detail.jsp")
 })
public class TB047ContractDetailAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB047ContractDetailModel> {
	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB047ContractDetailModel model = new TB047ContractDetailModel();

	/** サービス */
	@Autowired
	private TB047ContractDetailService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 顧客詳細契約詳細情報画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("contractDetailInit")
	public String init() throws Exception {
		
		// 初期表示処理
		if (StringUtils.isBlank(model.getKeiyakuKokyakuId())) {
			throw new ApplicationException("顧客ID不正：パラメータの顧客ID");
		}

		if (model.getKeiyakuNo() == null) {
			throw new ApplicationException("契約NO不正：パラメータの契約NO");
		}

		if (StringUtils.isNotBlank(model.getKokyakuId()) &&
			model.getKokyakuId().length() < RcpMKokyaku.KOKYAKU_ID_COLUMN_LENGTH) {
			// 顧客ＩＤのMAX値に満たない場合には、C+前0詰めして表示
			model.setKokyakuId("C" + StringUtils.leftPad(model.getKokyakuId(), RcpMKokyaku.KOKYAKU_ID_COLUMN_LENGTH - 1, '0'));
		}
		
		model = service.getInitInfo(model);

		// 検索結果の判定
		if (model.getKeiyakuInfo() == null) {
			// 検索結果が0件の場合
			addActionMessage("MSG0015", "該当の契約情報");
		}

		// ユーザコンテキスト取得
		model.setContext((TLCSSB2BUserContext) getUserContext());

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return ContractDetailModel 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB047ContractDetailModel getModel() {
		return model;
	}

}