package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB022InquiryDetailModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB022InquiryDetailService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 問い合わせ詳細アクションクラス。
 *
 * @author v140546
 * @version 1.0 2014/05/27
 * @version 1.1 2015/10/23 C.Kobayashi 既読未読機能追加対応
 * @version 1.2 2016/07/13 H.Yamamura 問い合わせ検索画面の問い合わせ区分１～４、部屋番号を持ちまわるように修正
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb022_inquiry_detail.jsp"),
	@Result(name=INPUT, location="tb022_inquiry_detail.jsp")
})
public class TB022InquiryDetailAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB022InquiryDetailModel> , ServiceValidatable{

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB022InquiryDetailModel model = new TB022InquiryDetailModel();

	/** サービス */
	@Autowired
	private TB022InquiryDetailService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("inquiryDetailInit")
	public String init() throws Exception {

		// 初期表示処理
		model = service.getInitInfo(model);
		if (model == null) {
			// アクセス不可の場合は、403エラーを画面に表示
			return FORBIDDEN_ERROR;
		}

		//顧客基本情報の個人/法人･締め日の表示
		model.setGamenKbn(Constants.GAMEN_KBN_INQUIRY_DETAIL);
		
		// 問い合わせ検索画面用パラメータを設定
		model.setCondition(remakeCondition(model));

		return SUCCESS;
	}
	
	/**
	 * 更新処理を行います。
	 * 
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="inquiryDetailUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="inquiryDetailInit?" +
									"&toiawaseNo=${toiawaseNo}" +
									"&dispKbn=${dispKbn}" +
									"&condition.toiawaseNo=${condition.toiawaseNo}" +
									"&condition.serviceKbn=${condition.serviceKbn}" +
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
									"&condition.telNo=${condition.telNo}" +
									"&condition.uketsukebiFrom=${condition.uketsukebiFrom}" +
									"&condition.uketsukebiTo=${condition.uketsukebiTo}" +
									"&condition.jokyo=${condition.jokyo}" +
									"&condition.browseStatus=${condition.browseStatus}" +
									"&condition.kubun1=${condition.kubun1}" +
									"&condition.kubun2=${condition.kubun2}" +
									"&condition.kubun3=${condition.kubun3}" +
									"&condition.kubun4=${condition.kubun4}" +
									"&condition.sortOrder=${condition.sortOrder}" +
									"&condition.offset=${condition.offset}"
					)
			}
	)
	public String update() {

		// 更新処理
		service.updateInquiryHistoryDetailInfo(model);

		// 完了メッセージ
		addActionMessage("MSG0001", "未読／既読情報の更新");

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
	private TB022InquiryDetailModel executeEncode(TB022InquiryDetailModel model) {

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
	private RC031ToiawaseSearchCondition remakeCondition(TB022InquiryDetailModel model) {
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
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB022InquiryDetailModel getModel() {
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
