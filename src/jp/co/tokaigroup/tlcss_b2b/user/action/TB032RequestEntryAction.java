package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.context.UserContextSupport;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB032RequestEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB032RequestEntryService;

import org.apache.commons.lang.StringUtils;
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
 * 依頼内容詳細・作業状況登録アクションクラス。
 *
 * @author k002849
 * @version 4.0 2014/07/15
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb032_request_entry.jsp"),
	@Result(name=INPUT, location="tb032_request_entry.jsp")
})
public class TB032RequestEntryAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB032RequestEntryModel>, ServiceValidatable {
	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB032RequestEntryModel model;

	/** サービス */
	@Autowired
	private TB032RequestEntryService service;

	/**
	 * モデルの初期化処理。
	 * ※アップロードファイルの要素数をモデル生成時に指定しなければ、登録できないため、
	 * ここで、モデルの生成。
	 */
	@PostConstruct
	public void modelInit() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (session == null) {
			return;
		}

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) session.getAttribute(UserContextSupport.KEY);
		if (userContext == null) {
			return;
		}

		try {
			model = new TB032RequestEntryModel(userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_APPENEDED_MAX));
		} catch (ApplicationException e) {
			// システムマスタに055:TB032依頼内容詳細・作業状況登録添付可能最大件数がないと、
			// システム500エラーとなるため、ここでcatchし、デフォルトコンストラクタでmodelを生成
			model = new TB032RequestEntryModel();
		}

	}

	/**
	 * 初期表示処理（詳細表示）を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("requestDetail")
	public String detail() throws Exception {
		// 初期化処理（詳細）実行
		model = service.getDetailInitInfo(model);
		if (model == null) {
			// アクセス不可の場合は、403エラーを画面に表示
			return FORBIDDEN_ERROR;
		}
		if (model.isNotPublish()) {
			// 非公開の情報がある場合は、403エラー画面に遷移
			return FORBIDDEN_ERROR;
		}

		// 更新後の検索画面用パラメータを設定
		// ※日本語の項目は Actionクラスのリダイレクト時に設定出来ない為、ここで実施
		model.setCondition(remakeCondition(model));
		// 画面区分の設定
		model.setGamenKbn(Constants.GAMEN_KBN_REQUEST_DETAIL);
		
		return SUCCESS;
	}

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("requestEntryInit")
	public String entryInit() throws Exception {
		// 初期化処理（登録）実行
		model = service.getEntryInitInfo(model);
		if (model == null) {
			// アクセス不可の場合は、403エラーを画面に表示
			return FORBIDDEN_ERROR;
		}

		if (model.isNotPublish()) {
			// 非公開の情報がある場合は、403エラー画面に遷移
			return FORBIDDEN_ERROR;
		}

		// 更新後の検索画面用パラメータを設定
		// ※日本語の項目は Actionクラスのリダイレクト時に設定出来ない為、ここで実施
		model.setCondition(remakeCondition(model));

		if (model.getSagyoJokyo() == null) {
			// 作業状況情報がなければ、新規登録モード
			model.setActionType(Constants.ACTION_TYPE_INSERT);
		} else {
			// 作業状況情報があれば、更新モード
			model.setActionType(Constants.ACTION_TYPE_UPDATE);
		}
		
		// 画面区分の設定
		model.setGamenKbn(Constants.GAMEN_KBN_REQUEST_DETAIL);

		return SUCCESS;
	}

	/**
	 * 画像削除処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="requestEntryImageDelete",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="requestEntryInit?" +
								"toiawaseNo=${toiawaseNo}" +
								"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
								"&dispKbn=${dispKbn}" +
								"&condition.offset=${condition.offset}" +
								"&condition.serviceKbn=${condition.serviceKbn}" +
								"&condition.toiawaseNo=${condition.toiawaseNo}" +
								"&condition.telNo=${condition.telNo}" +
								"&condition.uketsukebiFrom=${condition.uketsukebiFrom}" +
								"&condition.uketsukebiTo=${condition.uketsukebiTo}" +
								"&condition.iraiKanryo=${condition.iraiKanryo}" +
								"&condition.iraiKanryobiFrom=${condition.iraiKanryobiFrom}" +
								"&condition.iraiKanryobiTo=${condition.iraiKanryobiTo}" +
								"&encodedJusho1=${encodedJusho1}" +
								"&encodedJusho2=${encodedJusho2}" +
								"&encodedJusho3=${encodedJusho3}" +
								"&encodedJusho4=${encodedJusho4}" +
								"&encodedJusho5=${encodedJusho5}" +
								"&encodedKanaNm1=${encodedKanaNm1}" +
								"&encodedKanaNm2=${encodedKanaNm2}" +
								"&encodedKanjiNm1=${encodedKanjiNm1}" +
								"&encodedKanjiNm2=${encodedKanjiNm2}"
							)
			}
	)
	public String imageDelete() throws Exception {
		// 画像削除処理
		service.deleteImageInfo(model);

		if (!model.isSuccessDeleteImage()) {
			// ファイル削除失敗時のログ
			log.error(getText("MSG0019"));
		}

		// 完了メッセージ
		addActionMessage("MSG0001", "画像ファイルの削除");

		// 更新後の検索画面用パラメータを設定
		executeEncode(model);

		return SUCCESS;
	}

	/**
	 * 更新処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外
	 */
	@Action(
			value="requestEntryUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="requestEntryInit?" +
								"toiawaseNo=${toiawaseNo}" +
								"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
								"&dispKbn=${dispKbn}" +
								"&condition.offset=${condition.offset}" +
								"&condition.serviceKbn=${condition.serviceKbn}" +
								"&condition.toiawaseNo=${condition.toiawaseNo}" +
								"&condition.telNo=${condition.telNo}" +
								"&condition.uketsukebiFrom=${condition.uketsukebiFrom}" +
								"&condition.uketsukebiTo=${condition.uketsukebiTo}" +
								"&condition.iraiKanryo=${condition.iraiKanryo}" +
								"&condition.iraiKanryobiFrom=${condition.iraiKanryobiFrom}" +
								"&condition.iraiKanryobiTo=${condition.iraiKanryobiTo}" +
								"&encodedJusho1=${encodedJusho1}" +
								"&encodedJusho2=${encodedJusho2}" +
								"&encodedJusho3=${encodedJusho3}" +
								"&encodedJusho4=${encodedJusho4}" +
								"&encodedJusho5=${encodedJusho5}" +
								"&encodedKanaNm1=${encodedKanaNm1}" +
								"&encodedKanaNm2=${encodedKanaNm2}" +
								"&encodedKanjiNm1=${encodedKanjiNm1}" +
								"&encodedKanjiNm2=${encodedKanjiNm2}"
					)
			}
	)
	public String update() throws Exception {
		if (model.isInsert()) {
			// 登録処理
			service.insertGyoshaSagyoJokyoInfo(model);

			// 完了メッセージ
			addActionMessage("MSG0001", "作業内容の登録");
		} else if (model.isUpdate()) {
			// 更新処理
			service.updateGyoshaSagyoJokyoInfo(model);

			// 完了メッセージ
			addActionMessage("MSG0001", "作業内容の更新");
		}

		// 更新後の検索画面用パラメータを設定
		executeEncode(model);

		return SUCCESS;
	}

	/**
	 * 検索条件のエンコードを行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	private TB032RequestEntryModel executeEncode(TB032RequestEntryModel model) {
		// 更新後の検索画面用パラメータを設定
		RC041IraiSearchCondition condition = model.getCondition();
		if (condition == null) {
			condition = new RC041IraiSearchCondition();
		}
		// 住所１
		model.setEncodedJusho1(model.encode(condition.getJusho1()));
		// 住所２
		model.setEncodedJusho2(model.encode(condition.getJusho2()));
		// 住所３
		model.setEncodedJusho3(model.encode(condition.getJusho3()));
		// 住所４
		model.setEncodedJusho4(model.encode(condition.getJusho4()));
		// 住所５
		model.setEncodedJusho5(model.encode(condition.getJusho5()));
		// カナ氏名１
		model.setEncodedKanaNm1(model.encode(condition.getKanaNm1()));
		// カナ氏名２
		model.setEncodedKanaNm2(model.encode(condition.getKanaNm2()));
		// 漢字氏名１
		model.setEncodedKanjiNm1(model.encode(condition.getKanjiNm1()));
		// 漢字氏名２
		model.setEncodedKanjiNm2(model.encode(condition.getKanjiNm2()));

		return model;
	}

	/**
	 * 検索条件を再作成します。
	 * ※日本語の項目は Actionクラスのリダイレクト時に設定出来ない為。
	 *
	 * @param model 画面モデル
	 * @return 検索条件クラス
	 */
	private RC041IraiSearchCondition remakeCondition(TB032RequestEntryModel model) {
		RC041IraiSearchCondition condition = model.getCondition();
		if (condition == null) {
			condition = new RC041IraiSearchCondition();
		}

		// 住所１
		if (StringUtils.isNotBlank(model.getEncodedJusho1())) {
			condition.setJusho1(model.getEncodedJusho1());
		}
		// 住所２
		if (StringUtils.isNotBlank(model.getEncodedJusho2())) {
			condition.setJusho2(model.getEncodedJusho2());
		}
		// 住所３
		if (StringUtils.isNotBlank(model.getEncodedJusho3())) {
			condition.setJusho3(model.getEncodedJusho3());
		}
		// 住所４
		if (StringUtils.isNotBlank(model.getEncodedJusho4())) {
			condition.setJusho4(model.getEncodedJusho4());
		}
		// 住所５
		if (StringUtils.isNotBlank(model.getEncodedJusho5())) {
			condition.setJusho5(model.getEncodedJusho5());
		}
		// カナ氏名１
		if (StringUtils.isNotBlank(model.getEncodedKanaNm1())) {
			condition.setKanaNm1(model.getEncodedKanaNm1());
		}
		// カナ氏名２
		if (StringUtils.isNotBlank(model.getEncodedKanaNm2())) {
			condition.setKanaNm2(model.getEncodedKanaNm2());
		}
		// 漢字氏名１
		if (StringUtils.isNotBlank(model.getEncodedKanjiNm1())) {
			condition.setKanjiNm1(model.getEncodedKanjiNm1());
		}
		// 漢字氏名２
		if (StringUtils.isNotBlank(model.getEncodedKanjiNm2())) {
			condition.setKanjiNm2(model.getEncodedKanjiNm2());
		}

		return condition;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB032RequestEntryModel getModel() {
		return model;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		model = service.getEntryPrepareInitInfo(model);
	}
}
