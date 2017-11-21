package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.Calendar;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB024InquiryHistoryEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB024InquiryHistoryEntryService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 問い合わせ履歴登録アクションクラス。
 *
 * @author v145527
 * @version 1.0 2015/8/28
 * @version 1.1 2016/07/13 H.Yamamura 問い合わせ検索画面の問い合わせ区分１～４、部屋番号の値を持ちまわるように修正
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb024_inquiry_history_entry.jsp"),
	@Result(name=INPUT, location="tb024_inquiry_history_entry.jsp")
 })

public class TB024InquiryHistoryEntryAction extends TLCSSB2BBaseActionSupport implements
ModelDriven<TB024InquiryHistoryEntryModel>, ServiceValidatable{

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB024InquiryHistoryEntryModel model = new TB024InquiryHistoryEntryModel();

	/** サービス */
	@Autowired
	private TB024InquiryHistoryEntryService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="inquiryHistoryEntryInit")
	
	public String init() throws Exception {
		
		try {
			// 初期表示パラメータチェック
			isValidParameter();
			
			// 初期表示情報取得
			model = service.getInitInfo(model);
			
		} catch (ForbiddenException e) {
			// セキュリティエラーの場合は、403エラーを画面に表示
			return FORBIDDEN_ERROR;
			
		} catch (ValidationException e) {
			// 初期表示エラーフラグを設定し、javascriptと内容を表示しないようにする
			model.setInitError(true);
			// メッセージが消えるため、Exceptionから取り出して再設定
			addActionError(e.getValidationPack().getActionErrors().get(0).getMessageId()
					, e.getValidationPack().getActionErrors().get(0).getParams());
		}
		
		

		
		model.setGamenKbn(Constants.GAMEN_KBN_INQUIRY_HISTORY_ENTRY);

		// 初期値の設定
		RcpTToiawaseRireki toiawaseRireki = new RcpTToiawaseRireki();
		
		// ユーザ情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 受付日＝システム日付
		toiawaseRireki.setUketsukeYmd(DateUtil.toSqlTimestamp(DateUtils.truncate(DateUtil.getSysDateTime(), Calendar.DATE)));
		
		// 担当者名＝ログイン者名
		toiawaseRireki.setTantoshaNm(userContext.getUserName());

		model.setToiawaseRirekiInfo(toiawaseRireki);
		
		// アクションタイプの設定
		model.setActionType(Constants.ACTION_TYPE_INSERT);

		// ログインユーザ情報の取得
		model.TLCSSB2BUserContext(userContext);

		return SUCCESS;
	}
	
	/**
	 * パラメータチェックを行います。
	 */
	private void isValidParameter() {
		// 初期表示パラメータチェック
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			// パラメータの問い合わせＮＯが取得できない場合エラー
			throw new ApplicationException("問い合わせＮＯ不正：パラメータの問い合わせＮＯ" );
		}
		if (model.getToiawaseUpdDt() == null) {
			// パラメータの問い合わせ更新日がNULLの場合エラー
			throw new ApplicationException("問い合わせ更新日不正：パラメータの問い合わせ更新日" );
		}
	}

	/**
	 * 更新初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="inquiryHistoryEntryUpdateInit")
	public String updateInit() throws Exception {
		
		try {
			// 初期表示パラメータチェック
			isValidParameter();
			
			// 更新初期表示処理
			model = service.getInitInfoForUpdate(model);

			if (model.getToiawaseInfo().isRegistKbnToExternalCooperationData()) {
				// 問い合わせ情報の登録区分が「外部連携データ」の場合は、メッセージを表示
				addActionError("MSG0038", "外部連携データ");
			}
		} catch (ForbiddenException e) {
			// セキュリティエラーの場合は、403エラーを画面に表示
			return FORBIDDEN_ERROR;
			
		} catch (ValidationException e) {
			// 初期表示エラーフラグを設定し、javascriptと内容を表示しないようにする
			model.setInitError(true);
			// メッセージが消えるため、Exceptionから取り出して再設定
			addActionError(e.getValidationPack().getActionErrors().get(0).getMessageId()
					, e.getValidationPack().getActionErrors().get(0).getParams());
		}
		// 問い合わせ検索画面用パラメータを設定
		model.setCondition(remakeCondition(model));

		// アクションタイプの設定
		model.setActionType(Constants.ACTION_TYPE_UPDATE);
		// 画面区分の設定
		model.setGamenKbn(Constants.GAMEN_KBN_INQUIRY_HISTORY_ENTRY);

		// ログインユーザ情報の取得
		model.TLCSSB2BUserContext((TLCSSB2BUserContext) getUserContext());
		
		return SUCCESS;
	}

	/**
	 * 問い合わせ履歴情報の登録、更新、削除を行います。
	 *
	 * @return 問い合わせ履歴登録画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="inquiryHistoryEntryUpdate",
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="inquiryHistoryEntryUpdateInit?" +
								"toiawaseNo=${toiawaseNo}" +
								"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
								"&toiawaseUpdDt=${toiawaseUpdDt}" +
								"&actionType=${actionType}" +
								"&dispKbn=${dispKbn}" +
								"&condition.toiawaseNo=${condition.toiawaseNo}" +
								"&condition.uketsukebiFrom=${condition.uketsukebiFrom}" +
								"&condition.uketsukebiTo=${condition.uketsukebiTo}" +
								"&encodedJusho1=${encodedJusho1}" +
								"&encodedJusho2=${encodedJusho2}" +
								"&encodedJusho3=${encodedJusho3}" +
								"&encodedJusho4=${encodedJusho4}" +
								"&encodedJusho5=${encodedJusho5}" +
								"&encodedRoomNo=${encodedRoomNo}" +
								"&encodedKanaNm1=${encodedKanaNm1}" +
								"&encodedKanaNm2=${encodedKanaNm2}" +
								"&encodedKanjiNm1=${encodedKanjiNm1}" +
								"&encodedKanjiNm2=${encodedKanjiNm2}" +
								"&condition.sortOrder=${condition.sortOrder}" +
								"&condition.jokyo=${condition.jokyo}" +
								"&condition.serviceKbn=${condition.serviceKbn}" +
								"&condition.telNo=${condition.telNo}" +
								"&condition.kubun1=${condition.kubun1}" +
								"&condition.kubun2=${condition.kubun2}" +
								"&condition.kubun3=${condition.kubun3}" +
								"&condition.kubun4=${condition.kubun4}" +
								"&condition.offset=${condition.offset}" +
								"&condition.toiawaseRirekiNo=${condition.toiawaseRirekiNo}"
					),
					@Result(name=DELETE, location="tb024_inquiry_history_entry.jsp")
			}
	)
	public String update() throws Exception {
		
		try{
			// アクションタイプによって、処理切り替え
			if (model.isInsert()) {
				// 登録処理
				service.insertToiawaseRirekiInfo(model);
				// アクションタイプの設定
				model.setActionType(Constants.ACTION_TYPE_UPDATE);
			
				// 完了メッセージ
				addActionMessage("MSG0001", "問い合わせ履歴情報の登録");
			} else if (model.isUpdate()) {
				// 更新処理
				service.updateToiawaseRirekiInfo(model);
	
				// 完了メッセージ
				addActionMessage("MSG0001", "問い合わせ履歴情報の更新");
			} else if (model.isDelete()) {
				// 削除処理
				service.deleteToiawaseRirekiInfo(model);
	
				// 完了メッセージ
				addActionMessage("MSG0001", "問い合わせ履歴情報の削除");
				
				// 削除完了フラグを設定
				model.setDeleteCompleted(true);
			} else {
				// 不測のアクションタイプ
				throw new ApplicationException("アクションタイプ不正：" + model.getActionType());
			}

		} catch (ForbiddenException e) {
			// セキュリティエラーの場合は、403エラーを画面に表示
			return FORBIDDEN_ERROR;
			
		}
		// 削除の場合はredirectしない
		if (model.isDelete()) {
			return DELETE;
		} else {
			// 更新後の検索画面用パラメータを設定
			executeEncode(model);

			return SUCCESS;
		}
	}

	/**
	 * 検索条件のエンコードを行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	private TB024InquiryHistoryEntryModel executeEncode(TB024InquiryHistoryEntryModel model) {

		// 問い合わせ検索用
		RC031ToiawaseSearchCondition condition = model.getCondition();
		if (condition == null) {
			condition = new RC031ToiawaseSearchCondition();
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
		// 部屋番号
		model.setEncodedRoomNo(model.encode(condition.getRoomNo()));
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
	 * 問い合わせ検索条件を再作成します。
	 * ※日本語の項目は Actionクラスのリダイレクト時に設定出来ない為。
	 *
	 * @param model 画面モデル
	 * @return 検索条件クラス
	 */
	private RC031ToiawaseSearchCondition remakeCondition(TB024InquiryHistoryEntryModel model) {
		RC031ToiawaseSearchCondition condition = model.getCondition();
		if (condition == null) {
			condition = new RC031ToiawaseSearchCondition();
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
		// 部屋番号
		if (StringUtils.isNotBlank(model.getEncodedRoomNo())) {
			condition.setRoomNo(model.getEncodedRoomNo());
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
	public TB024InquiryHistoryEntryModel getModel() {
		return model;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		if (model.isDelete()) {
			model.setActionType(Constants.ACTION_TYPE_UPDATE);
		}

		if (!model.isInitError()) {
			// 初期化エラーでなければ、実施（初期化の場合に実施すると、システムエラーになるため）
			model = service.getInitInfo(model);
		}

		// ログインユーザ情報の取得
		model.TLCSSB2BUserContext((TLCSSB2BUserContext) getUserContext());
	}

}
