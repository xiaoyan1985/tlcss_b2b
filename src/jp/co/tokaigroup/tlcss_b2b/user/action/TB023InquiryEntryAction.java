package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.context.UserContextSupport;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB023InquiryEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB023InquiryEntryService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 問い合わせ登録アクションクラス。
 *
 * @author k003316
 * @version 1.0 2015/08/11
 * @version 1.1 2016/07/13 H.Yamamura 問い合わせ検索画面の問い合わせ区分１～４、部屋番号の値を持ちまわるように修正
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb023_inquiry_entry.jsp"),
	@Result(name=INPUT, location="tb023_inquiry_entry.jsp")
})
public class TB023InquiryEntryAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB023InquiryEntryModel>, ServiceValidatable {
	
	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB023InquiryEntryModel model = new TB023InquiryEntryModel();

	/** サービス */
	@Autowired
	private TB023InquiryEntryService service;

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
			model = new TB023InquiryEntryModel(userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_BSB_TOIAWASE_FILE_TO_MAX));
		} catch (ApplicationException e) {
			// システムマスタに055:TB032依頼内容詳細・作業状況登録添付可能最大件数がないと、
			// システム500エラーとなるため、ここでcatchし、デフォルトコンストラクタでmodelを生成
			model = new TB023InquiryEntryModel();
		}
	}
	
	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="inquiryEntryInit",
			params={"actionType", Constants.ACTION_TYPE_INSERT}
	)
	public String init() throws Exception {
		
		try {
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
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		RcpTToiawase toiawase = new RcpTToiawase();
		toiawase.setUketsukeshaNm(userContext.getUserName());
		toiawase.setUketsukeYmd(DateUtil.toSqlTimestamp(DateUtils.truncate(DateUtil.getSysDateTime(), Calendar.DATE)));
		toiawase.setServiceShubetsu(model.getInitServiceShubetsu());
		
		model.setToiawaseInfo(toiawase);
		model.setGamenKbn(Constants.GAMEN_KBN_INQUIRY_ENTRY);
		
		return SUCCESS;
	}
	
	/**
	 * 更新初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="inquiryEntryUpdateInit",
			params={"actionType", Constants.ACTION_TYPE_UPDATE}
	)
	public String updateInit() throws Exception {
		
		try {
			if (StringUtils.isNotBlank(model.getToiawaseNo()) &&
				model.getToiawaseNo().length() < RcpTToiawase.TOIAWASE_NO_COLUMN_LENGTH) {
				// 問い合わせＮＯのMAX値に満たない場合には、C+前0詰めして表示
				model.setToiawaseNo(StringUtils.leftPad(model.getToiawaseNo(), RcpTToiawase.TOIAWASE_NO_COLUMN_LENGTH, '0'));
			}
			
			// 問い合わせ情報取得
			model = service.getInitInfoForUpdate(model);
			
			if (model.getToiawaseInfo().isRegistKbnToExternalCooperationData()) {
				// 問い合わせ情報の登録区分が「外部連携データ」の場合は、メッセージを表示
				addActionError("MSG0038", "外部連携データ");
			}
			if (StringUtils.isNotBlank(model.getToiawaseInfo().getShimeYm())) {
				// 締め済の問い合わせ情報の場合は、メッセージを表示
				addActionError("MSG0038", DateUtil.yyyymmPlusSlash(model.getToiawaseInfo().getShimeYm()) + "締め済");
			}
			
			// 子画面の処理が完了している場合
			if (StringUtils.isNotBlank(model.getCompleteMessageId())) {
				addActionMessage(model.getCompleteMessageId(), model.getCompleteMessageStr());
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
		
		model.setGamenKbn(Constants.GAMEN_KBN_INQUIRY_ENTRY);
		model.setActionType(Constants.ACTION_TYPE_UPDATE);
		
		return SUCCESS;
	}
	
	/**
	 * 問い合わせ情報の登録、更新、削除を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="inquiryEntryUpdate",
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="inquiryEntryUpdateInit?" +
									"&kokyakuId=${kokyakuId}" +
									"&toiawaseNo=${toiawaseNo}" +
									"&toiawaseUpdDt=${toiawaseUpdDt}" +
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
									"&condition.offset=${condition.offset}"
					),
					@Result(name=DELETE, location="tb023_inquiry_entry.jsp")
			}
	)
	public String update() throws Exception {
		
		try {
			// アクションタイプによって、処理切り替え
			if (model.isInsert()) {
				// 登録
				service.insertToiawaseInfo(model);
				addActionMessage("MSG0001", "問い合わせ情報の登録");
				// リダイレクト前に変更のあるパラメータを設定
				model.setToiawaseNo(model.getToiawaseInfo().getToiawaseNo());
				
			} else if (model.isUpdate()) {
				// 更新
				service.updateToiawaseInfo(model);
				addActionMessage("MSG0001", "問い合わせ情報の更新");
				
			} else if (model.isDelete()) {
				// 削除
				service.deleteToiawaseInfo(model);
				addActionMessage("MSG0001", "問い合わせ情報の削除");
				// 削除完了フラグを設定
				model.setDeleteCompleted(true);
				
				if (!model.isFileDeleteSuccess()) {
					// ファイル削除に失敗した場合は、ログにメッセージを出力
					log.error(getText("MSG0019"));
				}
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
	 * 問い合わせファイル削除処理を行います。
	 * 
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="inquiryFileDelete",
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="inquiryEntryUpdateInit?" +
									"&kokyakuId=${kokyakuId}" +
									"&toiawaseNo=${toiawaseNo}" +
									"&toiawaseUpdDt=${toiawaseUpdDt}" +
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
									"&condition.offset=${condition.offset}"
					)
			}
	)
	public String fileDelete() throws Exception {
		// パラメータチェック
		if (model.getFileIndex() == null) {
			// ファイルインデックスがない場合、エラー
			throw new ApplicationException("ファイルインデックス不正：パラメータのファイルインデックス");
		}
		if (StringUtils.isBlank(model.getUploadFileNm())) {
			// アップロードファイル名がない場合、エラー
			throw new ApplicationException("アップロードファイル名不正：パラメータのアップロードファイル名");
		}
		
		// 問い合わせファイル削除処理
		service.deleteToiawaseFileInfo(model);
		
		if (!model.isFileDeleteSuccess()) {
			// ファイル削除に失敗した場合は、ログにメッセージを出力
			log.error(getText("MSG0019"));
		}
		
		addActionMessage("MSG0001", "ファイルの削除");

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
	private TB023InquiryEntryModel executeEncode(TB023InquiryEntryModel model) {

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
	private RC031ToiawaseSearchCondition remakeCondition(TB023InquiryEntryModel model) {
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
	public TB023InquiryEntryModel getModel() {
		return model;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		
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