package jp.co.tokaigroup.tlcss_b2b.master.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB104InformationEntryModel;
import jp.co.tokaigroup.tlcss_b2b.master.service.TB104InformationEntryService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * お知らせ登録アクションクラス。
 *
 * @author v138130
 * @version 4.0 2014/06/20
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb104_information_entry.jsp"),
	@Result(name=INPUT, location="tb104_information_entry.jsp")
})
public class TB104InformationEntryAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB104InformationEntryModel>, ServiceValidatable {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB104InformationEntryModel model = new TB104InformationEntryModel();

	/** サービス */
	@Autowired
	private TB104InformationEntryService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="informationEntryInit", params={"actionType", Constants.ACTION_TYPE_INSERT})
	public String init() throws Exception {
		// 初期表示処理
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * 更新初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="informationEntryUpdateInit", params={"actionType", Constants.ACTION_TYPE_UPDATE})
	public String updateInit() throws Exception {
		// 更新後の検索画面用パラメータを設定
		// ※日本語の項目は Actionクラスのリダイレクト時に設定出来ない為、ここで実施
		if (StringUtils.isNotBlank(model.getEncodedKokyakuNm())) {
			model.getCondition().setKokyakuNm(model.getEncodedKokyakuNm());
		} else if (StringUtils.isNotBlank(model.getEncodedGyoshaNm())) {
			model.getCondition().setGyoshaNm(model.getEncodedGyoshaNm());
		}

		// 更新初期表示処理
		model = service.getUpdateInitInfo(model);

		return SUCCESS;
	}

	/**
	 * 更新処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="informationEntryUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="informationEntryUpdateInit?" +
									"seqNo=${seqNo}" +
									"&condition.offset=${condition.offset}" +
									"&condition.target=${condition.target}" +
									"&condition.kokyakuId=${condition.kokyakuId}" +
									"&encodedKokyakuNm=${encodedKokyakuNm}" +
									"&condition.gyoshaCd=${condition.gyoshaCd}" +
									"&encodedGyoshaNm=${encodedGyoshaNm}" +
									"&condition.hyojiJoken=${condition.hyojiJoken}")
			}
	)
	public String update() throws Exception {
		if (model.isInsert()) {
			// 登録処理
			model = service.insertInfo(model);

			// 完了メッセージ
			addActionMessage("MSG0001", "お知らせの登録");
		} else if (model.isUpdate()) {
			// 更新処理
			service.updateInfo(model);

			// 完了メッセージ
			addActionMessage("MSG0001", "お知らせの更新");
		} else {
			// アクションタイプが想定外の場合は、共通業務エラー
			throw new ApplicationException("アクションタイプ不正：パラメータのアクションタイプ");
		}

		// 更新初期表示処理用パラメータ設定
		model.setSeqNo(model.getInfo().getSeqNo());
		model.setEncodedKokyakuNm(model.encode(model.getCondition().getKokyakuNm()));
		model.setEncodedGyoshaNm(model.encode(model.getCondition().getGyoshaNm()));

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB104InformationEntryModel getModel() {
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

