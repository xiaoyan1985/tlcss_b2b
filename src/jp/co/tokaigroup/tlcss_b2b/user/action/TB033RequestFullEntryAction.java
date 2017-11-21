package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB033RequestFullEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB033RequestFullEntryService;

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
 * 依頼登録アクションクラス。
 * 
 * @author S.Nakano
 * @version 1.0 2015/10/27
 * @version 1.1 2016/07/13 H.Yamamura 問い合わせ検索画面の問い合わせ区分１～４、部屋番号の値を持ちまわるように修正
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb033_request_full_entry.jsp"),
	@Result(name=INPUT, location="tb033_request_full_entry.jsp")
})
public class TB033RequestFullEntryAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB033RequestFullEntryModel>, ServiceValidatable {

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB033RequestFullEntryModel model = new TB033RequestFullEntryModel();

	/** サービス */
	@Autowired
	private TB033RequestFullEntryService service;
	
	/**
	 * 初期表示処理を行います。
	 * 
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("requestFullEntryInit")
	public String init() {
		try {
			// 初期表示処理
			model = service.getInitInfo(model);
			
			if (model.getIrai() == null) {
				model.setIrai(new RcpTIrai());
			}
			
			// 初期値として、発注担当者名にログインユーザ名をセット
			model.getIrai().setTantoshaNm(model.getUserContext().getUserName());
			
			model.setActionType(Constants.ACTION_TYPE_INSERT);
			model.setGamenKbn(Constants.GAMEN_KBN_REQUEST_ENTRY);
		} catch (ForbiddenException e) {
			// セキュリティエラーの場合は、403エラーを画面に表示
			return FORBIDDEN_ERROR;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 更新初期表示処理を行います。
	 * 
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("requestFullEntryUpdateInit")
	public String updateInit() {
		try {
			// 更新初期表示処理
			model = service.getSagyoIraiInfo(model);
			
			model.setActionType(Constants.ACTION_TYPE_UPDATE);
			model.setGamenKbn(Constants.GAMEN_KBN_REQUEST_ENTRY);
			
			// 更新後の検索画面用パラメータを設定
			// ※日本語の項目は Actionクラスのリダイレクト時に設定出来ない為、ここで実施
			model.setCondition(remakeCondition(model));
			// 問い合わせ検索画面用パラメータを設定
			model.setToiawaseCondition(remakeToiawaseCondition(model));
		} catch (ForbiddenException e) {
			// セキュリティエラーの場合は、403エラーを画面に表示
			return FORBIDDEN_ERROR;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 更新処理を行います。
	 * 
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="requestFullEntryUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="requestFullEntryUpdateInit?" +
									"&toiawaseNo=${toiawaseNo}" +
									"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
									"&dispKbn=${dispKbn}" +
									"&rootDispKbn=${rootDispKbn}" +
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
									"&encodedKanjiNm2=${encodedKanjiNm2}" +
									"&toiawaseCondition.toiawaseNo=${toiawaseCondition.toiawaseNo}" +
									"&toiawaseCondition.uketsukebiFrom=${toiawaseCondition.uketsukebiFrom}" +
									"&toiawaseCondition.uketsukebiTo=${toiawaseCondition.uketsukebiTo}" +
									"&toiawaseCondition.sortOrder=${toiawaseCondition.sortOrder}" +
									"&toiawaseCondition.jokyo=${toiawaseCondition.jokyo}" +
									"&toiawaseCondition.serviceKbn=${toiawaseCondition.serviceKbn}" +
									"&toiawaseCondition.telNo=${toiawaseCondition.telNo}" +
									"&toiawaseCondition.offset=${toiawaseCondition.offset}" +
									"&toiawaseCondition.kubun1=${toiawaseCondition.kubun1}" +
									"&toiawaseCondition.kubun2=${toiawaseCondition.kubun2}" +
									"&toiawaseCondition.kubun3=${toiawaseCondition.kubun3}" +
									"&toiawaseCondition.kubun4=${toiawaseCondition.kubun4}" +
									"&encodedToiawaseJusho1=${encodedToiawaseJusho1}" +
									"&encodedToiawaseJusho2=${encodedToiawaseJusho2}" +
									"&encodedToiawaseJusho3=${encodedToiawaseJusho3}" +
									"&encodedToiawaseJusho4=${encodedToiawaseJusho4}" +
									"&encodedToiawaseJusho5=${encodedToiawaseJusho5}" +
									"&encodedToiawaseRoomNo=${encodedToiawaseRoomNo}" +
									"&encodedToiawaseKanaNm1=${encodedToiawaseKanaNm1}" +
									"&encodedToiawaseKanaNm2=${encodedToiawaseKanaNm2}" +
									"&encodedToiawaseKanjiNm1=${encodedToiawaseKanjiNm1}" +
									"&encodedToiawaseKanjiNm2=${encodedToiawaseKanjiNm2}"
					),
					@Result(name=DELETE, location="tb033_request_full_entry.jsp")
			}
	)
	public String update() {
		if (model.isInsert()) {
			// 登録処理
			service.insertSagyoIraiInfo(model);
			
			addActionMessage("MSG0001", "依頼情報の登録");
		} else if (model.isUpdate()) {
			// 更新処理
			service.updateSagyoIraiInfo(model);
			
			addActionMessage("MSG0001", "依頼情報の更新");
		} else if (model.isDelete()) {
			// 削除処理
			service.deleteSagyoIraiInfo(model);
			
			if (model.isFileDeleteError()) {
				// ファイル削除エラーの場合は、ログを出力
				log.error(getText("MSG0019"));
			}
			
			model.setDeleteCompleted(true);
			
			addActionMessage("MSG0001", "依頼情報の削除");
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
	 * 業者依頼メール送信処理を行います。
	 * 
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("requestFullEntrySendMail")
	public String sendMail() {
		// 業者依頼メール送信処理
		service.sendGyoshaIraiMail(model);
		
		addActionMessage("MSG0001", "センター業者依頼メール送信");
		
		return SUCCESS;
	}
	
	/**
	 * 画像削除処理を行います。
	 * 
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="requestFullEntryImageDelete",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="requestFullEntryUpdateInit?" +
									"&toiawaseNo=${toiawaseNo}" +
									"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
									"&dispKbn=${dispKbn}" +
									"&rootDispKbn=${rootDispKbn}" +
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
									"&encodedKanjiNm2=${encodedKanjiNm2}" +
									"&toiawaseCondition.toiawaseNo=${toiawaseCondition.toiawaseNo}" +
									"&toiawaseCondition.uketsukebiFrom=${toiawaseCondition.uketsukebiFrom}" +
									"&toiawaseCondition.uketsukebiTo=${toiawaseCondition.uketsukebiTo}" +
									"&toiawaseCondition.sortOrder=${toiawaseCondition.sortOrder}" +
									"&toiawaseCondition.jokyo=${toiawaseCondition.jokyo}" +
									"&toiawaseCondition.serviceKbn=${toiawaseCondition.serviceKbn}" +
									"&toiawaseCondition.telNo=${toiawaseCondition.telNo}" +
									"&toiawaseCondition.offset=${toiawaseCondition.offset}" +
									"&toiawaseCondition.kubun1=${toiawaseCondition.kubun1}" +
									"&toiawaseCondition.kubun2=${toiawaseCondition.kubun2}" +
									"&toiawaseCondition.kubun3=${toiawaseCondition.kubun3}" +
									"&toiawaseCondition.kubun4=${toiawaseCondition.kubun4}" +
									"&encodedToiawaseJusho1=${encodedToiawaseJusho1}" +
									"&encodedToiawaseJusho2=${encodedToiawaseJusho2}" +
									"&encodedToiawaseJusho3=${encodedToiawaseJusho3}" +
									"&encodedToiawaseJusho4=${encodedToiawaseJusho4}" +
									"&encodedToiawaseJusho5=${encodedToiawaseJusho5}" +
									"&encodedToiawaseRoomNo=${encodedToiawaseRoomNo}" +
									"&encodedToiawaseKanaNm1=${encodedToiawaseKanaNm1}" +
									"&encodedToiawaseKanaNm2=${encodedToiawaseKanaNm2}" +
									"&encodedToiawaseKanjiNm1=${encodedToiawaseKanjiNm1}" +
									"&encodedToiawaseKanjiNm2=${encodedToiawaseKanjiNm2}"
					)
			}
	)
	public String imageDelete() {
		// 画像ファイル削除処理
		service.deleteSagyoJokyoImageFile(model);
		
		if (model.isFileDeleteError()) {
			// ファイル削除エラーの場合は、ログを出力
			log.error(getText("MSG0019"));
		}
		
		addActionMessage("MSG0001", "画像ファイルの削除");
		
		// 更新後の検索画面用パラメータを設定
		executeEncode(model);

		return SUCCESS;
	}
	
	/**
	 * その他ファイル削除処理を行います。
	 * 
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="requestFullEntryOtherDelete",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="requestFullEntryUpdateInit?" +
									"&toiawaseNo=${toiawaseNo}" +
									"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
									"&dispKbn=${dispKbn}" +
									"&rootDispKbn=${rootDispKbn}" +
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
									"&encodedKanjiNm2=${encodedKanjiNm2}" +
									"&toiawaseCondition.toiawaseNo=${toiawaseCondition.toiawaseNo}" +
									"&toiawaseCondition.uketsukebiFrom=${toiawaseCondition.uketsukebiFrom}" +
									"&toiawaseCondition.uketsukebiTo=${toiawaseCondition.uketsukebiTo}" +
									"&toiawaseCondition.sortOrder=${toiawaseCondition.sortOrder}" +
									"&toiawaseCondition.jokyo=${toiawaseCondition.jokyo}" +
									"&toiawaseCondition.serviceKbn=${toiawaseCondition.serviceKbn}" +
									"&toiawaseCondition.telNo=${toiawaseCondition.telNo}" +
									"&toiawaseCondition.offset=${toiawaseCondition.offset}" +
									"&toiawaseCondition.kubun1=${toiawaseCondition.kubun1}" +
									"&toiawaseCondition.kubun2=${toiawaseCondition.kubun2}" +
									"&toiawaseCondition.kubun3=${toiawaseCondition.kubun3}" +
									"&toiawaseCondition.kubun4=${toiawaseCondition.kubun4}" +
									"&encodedToiawaseJusho1=${encodedToiawaseJusho1}" +
									"&encodedToiawaseJusho2=${encodedToiawaseJusho2}" +
									"&encodedToiawaseJusho3=${encodedToiawaseJusho3}" +
									"&encodedToiawaseJusho4=${encodedToiawaseJusho4}" +
									"&encodedToiawaseJusho5=${encodedToiawaseJusho5}" +
									"&encodedToiawaseRoomNo=${encodedToiawaseRoomNo}" +
									"&encodedToiawaseKanaNm1=${encodedToiawaseKanaNm1}" +
									"&encodedToiawaseKanaNm2=${encodedToiawaseKanaNm2}" +
									"&encodedToiawaseKanjiNm1=${encodedToiawaseKanjiNm1}" +
									"&encodedToiawaseKanjiNm2=${encodedToiawaseKanjiNm2}"
					)
			}
	)
	public String otherDelete() {
		// その他ファイル削除処理
		service.deleteOtherFile(model);
		
		if (model.isFileDeleteError()) {
			// ファイル削除エラーの場合は、ログを出力
			log.error(getText("MSG0019"));
		}
		
		addActionMessage("MSG0001", "その他ファイルの削除");
		
		// 更新後の検索画面用パラメータを設定
		executeEncode(model);

		return SUCCESS;
	}
	
	/**
	 * 依頼書印刷処理を行います。
	 * 
	 * @return
	 */
	@Action(
			value="requestFullEntryWorkRequestPrint",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			}
	)
	public String workRequestPrint() {
		// 依頼書印刷処理
		model.setWorkRequestPrinted(true);
		model.setActionType(Constants.ACTION_TYPE_RESTORE);
		
		try {
			// 作業依頼書印刷処理
			model = service.createPdf(model);
		} catch (Exception e) {
			log.error(e.getMessage());
			try {
				// 画面再表示処理呼び出し（アラートメッセージを表示するため、ValidationExceptionはthrowできない）
				model = service.parepareInitInfoForUpdate(model);
			} catch (Exception e2) {
				log.error(e.getMessage());
			}
		}
		model.setActionType(Constants.ACTION_TYPE_UPDATE);
		
		return SUCCESS;
	}
	
	/**
	 * 検索条件のエンコードを行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	private TB033RequestFullEntryModel executeEncode(TB033RequestFullEntryModel model) {
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

		// 問い合わせ検索用
		RC031ToiawaseSearchCondition toiawaseCondition = model.getToiawaseCondition();
		if (toiawaseCondition == null) {
			toiawaseCondition = new RC031ToiawaseSearchCondition();
		}
		// 住所１
		model.setEncodedToiawaseJusho1(model.encode(toiawaseCondition.getJusho1()));
		// 住所２
		model.setEncodedToiawaseJusho2(model.encode(toiawaseCondition.getJusho2()));
		// 住所３
		model.setEncodedToiawaseJusho3(model.encode(toiawaseCondition.getJusho3()));
		// 住所４
		model.setEncodedToiawaseJusho4(model.encode(toiawaseCondition.getJusho4()));
		// 住所５
		model.setEncodedToiawaseJusho5(model.encode(toiawaseCondition.getJusho5()));
		// 部屋番号
		model.setEncodedToiawaseRoomNo(model.encode(toiawaseCondition.getRoomNo()));
		// カナ氏名１
		model.setEncodedToiawaseKanaNm1(model.encode(toiawaseCondition.getKanaNm1()));
		// カナ氏名２
		model.setEncodedToiawaseKanaNm2(model.encode(toiawaseCondition.getKanaNm2()));
		// 漢字氏名１
		model.setEncodedToiawaseKanjiNm1(model.encode(toiawaseCondition.getKanjiNm1()));
		// 漢字氏名２
		model.setEncodedToiawaseKanjiNm2(model.encode(toiawaseCondition.getKanjiNm2()));
		
		return model;
	}

	/**
	 * 検索条件を再作成します。
	 * ※日本語の項目は Actionクラスのリダイレクト時に設定出来ない為。
	 *
	 * @param model 画面モデル
	 * @return 検索条件クラス
	 */
	private RC041IraiSearchCondition remakeCondition(TB033RequestFullEntryModel model) {
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
	 * 問い合わせ検索条件を再作成します。
	 * ※日本語の項目は Actionクラスのリダイレクト時に設定出来ない為。
	 *
	 * @param model 画面モデル
	 * @return 検索条件クラス
	 */
	private RC031ToiawaseSearchCondition remakeToiawaseCondition(TB033RequestFullEntryModel model) {
		RC031ToiawaseSearchCondition condition = model.getToiawaseCondition();
		if (condition == null) {
			condition = new RC031ToiawaseSearchCondition();
		}

		// 住所１
		if (StringUtils.isNotBlank(model.getEncodedToiawaseJusho1())) {
			condition.setJusho1(model.getEncodedToiawaseJusho1());
		}
		// 住所２
		if (StringUtils.isNotBlank(model.getEncodedToiawaseJusho2())) {
			condition.setJusho2(model.getEncodedToiawaseJusho2());
		}
		// 住所３
		if (StringUtils.isNotBlank(model.getEncodedToiawaseJusho3())) {
			condition.setJusho3(model.getEncodedToiawaseJusho3());
		}
		// 住所４
		if (StringUtils.isNotBlank(model.getEncodedToiawaseJusho4())) {
			condition.setJusho4(model.getEncodedToiawaseJusho4());
		}
		// 住所５
		if (StringUtils.isNotBlank(model.getEncodedToiawaseJusho5())) {
			condition.setJusho5(model.getEncodedToiawaseJusho5());
		}
		// 部屋番号
		if (StringUtils.isNotBlank(model.getEncodedToiawaseRoomNo())) {
			condition.setRoomNo(model.getEncodedToiawaseRoomNo());
		}
		// カナ氏名１
		if (StringUtils.isNotBlank(model.getEncodedToiawaseKanaNm1())) {
			condition.setKanaNm1(model.getEncodedToiawaseKanaNm1());
		}
		// カナ氏名２
		if (StringUtils.isNotBlank(model.getEncodedToiawaseKanaNm2())) {
			condition.setKanaNm2(model.getEncodedToiawaseKanaNm2());
		}
		// 漢字氏名１
		if (StringUtils.isNotBlank(model.getEncodedToiawaseKanjiNm1())) {
			condition.setKanjiNm1(model.getEncodedToiawaseKanjiNm1());
		}
		// 漢字氏名２
		if (StringUtils.isNotBlank(model.getEncodedToiawaseKanjiNm2())) {
			condition.setKanjiNm2(model.getEncodedToiawaseKanjiNm2());
		}

		return condition;
	}
	
	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	@Override
	public TB033RequestFullEntryModel getModel() {
		return model;
	}
	
	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	@Override
	public void prepareModel() {
		if (model.isInitError()) {
			// 初期表示エラーの際は、何もしない
			return;
		}
		
		if (model.isInsert()) {
			model = service.parepareInitInfo(model);
		} else if (model.isUpdate()) {
			model = service.parepareInitInfoForUpdate(model);
		} else if (model.isDelete()) {
			// アクションタイプを戻す
			model.setActionType(Constants.ACTION_TYPE_UPDATE);
			model = service.parepareInitInfoForUpdate(model);
		}
	}
}
