package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.message.MessageBean;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB043CustomerUploadService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;


/**
 * 管理情報アップロードアクションクラス。
 *
 * @author v140546
 * @version 1.0 2014/08/04
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb043_customer_upload.jsp"),
	@Result(name=INPUT, location="tb043_customer_upload.jsp")
})
public class TB043CustomerUploadAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB043CustomerUploadModel> , ServiceValidatable{

	// プロパティファイルから読み込み
	/** ＣＳＶテンプレートフォルダPATH */
	private static final String CSV_TEMPLATE_PATH = ResourceFactory.getResource().getString("CSV_TEMPLATE_PATH");
	/** テンプレートファイル名 指定業者 */
	private static final String TEMPLATE_FILE_NM_ERROR_FILE_HEADER = "tb043_customer_upload_error_header_template.csv";
	/** エラーファイルエンコーディング */
	private static final String ERROR_FILE_ENCODING = "Windows-31J";

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB043CustomerUploadModel model = new TB043CustomerUploadModel();

	/** サービス */
	@Autowired
	private TB043CustomerUploadService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("customerUploadInit")
	public String init() throws Exception {

		// ユーザーエージェント設定
		model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

		// ユーザーコンテキストの設定
		model.setUserContext((TLCSSB2BUserContext) getUserContext());

		// 初期表示処理
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * CSV取込処理を行います。
	 *
	 * @return 管理情報アップロード画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("customerUploadUpload")
	public String upload() throws Exception {

		// ユーザーエージェント設定
		model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

		// ユーザーコンテキストの設定
		model.setUserContext((TLCSSB2BUserContext) getUserContext());

		// ユーザーがmacintoshの場合、文字化け回避処理
		if (model.isMacintosh()) {
			model.setUploadFileFileName(
					Normalizer.normalize(model.getUploadFileFileName(), Form.NFKC));
		}

		// CSV取込処理
		service.executeCsvUpload(model);

		if (model.isSuccessCsvUpload()) {
			// 処理が成功した場合
			addActionMessage("MSG0001", "ファイルのアップロード");
		} else {
			// 処理が失敗した場合
			// エラー情報のソート
			model.setErrorInfo(sortErrorInfo(model.getErrorInfo()));
			// エラーファイルへの書き込み
			writeErrFile(model.getUploadFileFileName(), model.getErrorFilePath(), model.getErrorInfo());

			addActionError("MSG0023");
		}

		// 初期表示処理
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * 削除処理を行います。
	 *
	 * @return 管理情報アップロード画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="customerUploadDelete",
			results = {
					@Result(name=SUCCESS, type="redirectAction",
					location="customerUploadInit")
	})
	public String delete() throws Exception {

		// ユーザーコンテキストの設定
		model.setUserContext((TLCSSB2BUserContext) getUserContext());

		// 削除処理
		model = service.deleteUploadRireki(model);

		// 処理が成功した場合
		addActionMessage("MSG0001", "ファイルの削除");

		return SUCCESS;
	}

	/**
	 * エラーファイルにエラー内容を書き込みます。
	 *
	 * @param uploadFileNm アップロードファイル名
	 * @param errFilePath エラーファイルパス（絶対パス）
	 * @param errorInfo エラー情報
	 */
	private void writeErrFile(String uploadFileNm, String errFilePath, ValidationPack errorInfo) {
		// エラーファイル
		File errFile = new File(errFilePath);

		PrintWriter writer = null;
		BufferedReader reader = null;
		try {
			// エラーファイル書き出し
			writer = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(errFile), ERROR_FILE_ENCODING));
			// ヘッダファイル読み込み
			reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(CSV_TEMPLATE_PATH + System.getProperty("file.separator") + TEMPLATE_FILE_NM_ERROR_FILE_HEADER), ERROR_FILE_ENCODING));

			// ヘッダ情報の読み込み
			String line = "";
			List<String> headerList = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				headerList.add(line);
			}

			// ヘッダ情報の前に参照ファイル名を書き込み
			writer.println(uploadFileNm);

			// ヘッダ情報の書き込み
			for (String header : headerList) {
				writer.println(header);
			}

			// エラー内容の書き込み
			for (MessageBean mb : errorInfo.getActionErrors()) {
				String errMsg = null;
				if (mb.isMessageId()) {
					errMsg = getText(mb.getMessageId(), mb.getParams());
				} else {
					errMsg = mb.getMessageId();
				}

				writer.println(errMsg);
			}
		} catch (IOException e) {
			throw new ApplicationException(e);
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					// closeの際のIOExceptionは無視
				}
			}
		}
	}

	/**
	 * エラー情報をソートします。
	 *
	 * @param orgVp エラー情報
	 * @return ソートしたエラー情報
	 */
	private ValidationPack sortErrorInfo(ValidationPack orgVp) {
		ValidationPack vp = new ValidationPack();
		List<String> errorMessageList = new ArrayList<String>();

		// 現在の行番号
		String nowRowData = "";
		// 行番号毎のエラーリスト
		List<String> rowErrorMessageList = new ArrayList<String>();

		// エラーメッセージ分、ループ
		for (MessageBean mb : orgVp.getActionErrors()) {
			String errMsg = getText(mb.getMessageId(), mb.getParams());
			String[] errMsgArray = StringUtils.split(errMsg, ",");

			if (StringUtils.isBlank(nowRowData) || nowRowData.equals(errMsgArray[0])) {
				// 初回、または、行番号が同じ場合は、リストにつめる
				rowErrorMessageList.add(errMsg);

				if (StringUtils.isBlank(nowRowData)) {
					// 初回の場合は、現在の行番号の更新
					nowRowData = errMsgArray[0];
				}
			} else {
				// 行番号が違う場合は、今までのリストをソート
				Collections.sort(rowErrorMessageList);

				// ソート結果を全体のエラーメッセージリストにつめる
				errorMessageList.addAll(rowErrorMessageList);

				// 行番号エラーリストの初期化
				rowErrorMessageList.clear();
				rowErrorMessageList.add(errMsg);

				// 現在の行番号の更新
				nowRowData = errMsgArray[0];
			}
		}

		// 最終処理
		if (!(rowErrorMessageList == null || rowErrorMessageList.isEmpty())) {
			// 行番号が違う場合は、今までのリストをソート
			Collections.sort(rowErrorMessageList);

			// ソート結果を全体のエラーメッセージリストにつめる
			errorMessageList.addAll(rowErrorMessageList);
		}

		// ValidationPackに詰め直す
		for (String errorMessage : errorMessageList) {
			vp.addErrorAsMessage(errorMessage);
		}

		return vp;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB043CustomerUploadModel getModel() {
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
