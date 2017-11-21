package jp.co.tokaigroup.tlcss_b2b.master.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.message.MessageBean;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB102UserMasterEntryModel;
import jp.co.tokaigroup.tlcss_b2b.master.service.TB102UserMasterEntryService;

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
 * ユーザーマスタ登録アクションクラス。
 *
 * @author k002849
 * @version 1.1 2014/06/18
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb102_user_master_entry.jsp"),
	@Result(name=INPUT, location="tb102_user_master_entry.jsp")
})
public class TB102UserMasterEntryAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB102UserMasterEntryModel>, ServiceValidatable {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB102UserMasterEntryModel model = new TB102UserMasterEntryModel();

	/** サービス */
	@Autowired
	private TB102UserMasterEntryService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="userMasterEntryInit", params={"actionType", Constants.ACTION_TYPE_INSERT})
	public String init() throws Exception {
		// 初期表示処理
		model = service.getInitInfo(model);

		// メール送信フラグの初期値を設定
		model.setSendMailFlg(TB102UserMasterEntryModel.SEND_MAIL_FLG_ON);

		return SUCCESS;
	}

	/**
	 * 更新初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="userMasterEntryUpdateInit", params={"actionType", Constants.ACTION_TYPE_UPDATE})
	public String updateInit() throws Exception {
		// 更新後の検索画面用パラメータを設定
		// ※日本語の項目は Actionクラスのリダイレクト時に設定出来ない為、ここで実施
		if (StringUtils.isNotBlank(model.getEncodedUserNm())) {
			model.getCondition().setUserNm(model.getEncodedUserNm());
		}

		// 更新初期表示処理
		model = service.getUserInfo(model);

		if (model.isDuplicateContentExists()) {
			// 重複項目がある場合は、メッセージを生成
			StringBuilder message = new StringBuilder("");
			for (MessageBean messageBean : model.getDuplicateContetErrorMessageList()) {
				if (StringUtils.isNotBlank(message.toString())) {
					// ２つ以上メッセージがある場合は、改行を出力
					message.append(System.getProperty("line.separator"));
				}

				message.append(getText(messageBean.getMessageId(), messageBean.getParams()));
			}

			model.setDucplicateContentErrorMessage(message.toString());
		}

		// メール送信フラグの初期値を設定
		model.setSendMailFlg(TB102UserMasterEntryModel.SEND_MAIL_FLG_ON);

		return SUCCESS;
	}

	/**
	 * 更新処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="userMasterEntryUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="userMasterEntryUpdateInit?" +
									"seqNo=${seqNo}" +
									"&condition.offset=${condition.offset}" +
									"&condition.loginId=${condition.loginId}" +
									"&condition.role=${condition.role}" +
									"&encodedUserNm=${encodedUserNm}")
			}
	)
	public String update() throws Exception {
		if (model.isInsert()) {
			// 削除フラグに未削除をセット
			model.getUser().setDelFlg(TbMUser.DEL_FLG_NOT_DELETE);
			// 登録処理
			service.insertUserInfo(model);

			// 完了メッセージ
			addActionMessage("MSG0001", "ユーザー情報の登録");
		} else if (model.isUpdate()) {
			// 更新処理
			service.updateUserInfo(model);

			// 完了メッセージ
			addActionMessage("MSG0001", "ユーザー情報の更新");
		} else {
			// アクションタイプが想定外の場合は、共通業務エラー
			throw new ApplicationException("アクションタイプ不正：パラメータのアクションタイプ");
		}

		// 更新初期表示処理用パラメータ設定
		model.setEncodedUserNm(model.encode(model.getCondition().getUserNm()));

		return SUCCESS;
	}

	/**
	 * パスワード再発行処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="userMasterEntryReissue",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="userMasterEntryUpdateInit?" +
									"seqNo=${seqNo}" +
									"&condition.offset=${condition.offset}" +
									"&condition.loginId=${condition.loginId}" +
									"&condition.role=${condition.role}" +
									"&encodedUserNm=${encodedUserNm}")
			}
	)
	public String reissue() throws Exception {
		// パスワード再発行処理
		service.reissuePasswordInfo(model);

		// 完了メッセージ
		addActionMessage("MSG0001", "パスワードの再発行");

		// 更新初期表示処理用パラメータ設定
		model.setEncodedUserNm(model.encode(model.getCondition().getUserNm()));

		return SUCCESS;
	}

	/**
	 * 参照顧客設定を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("userMasterSetRefKokyaku")
	public String setRefKokyaku() throws Exception {
		// 初期表示処理
		model = service.setRefKokyaku(model);

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB102UserMasterEntryModel getModel() {
		return model;
	}

	/**
	 * 画面モデルを用意します。
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		model = service.getInitInfo(model);
	}
}
