package jp.co.tokaigroup.tlcss_b2b.common.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB014ReportDisclosureModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB014ReportDisclosureService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 報告書公開設定ダウンロードアクションクラス。
 *
 * @author k002849
 * @version 1.0 2015/09/24
 */
@Controller
@Scope("prototype")
public class TB014ReportDisclosureDownloadAction extends DownloadActionSupport
implements ModelDriven<TB014ReportDisclosureModel> {

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/** 画面モデル */
	private TB014ReportDisclosureModel model = new TB014ReportDisclosureModel();
	
	/** サービス */
	@Autowired
	private TB014ReportDisclosureService service;

	/**
	 * 報告書ダウンロード処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(
			value="reportDisclosureDownload",
			results = {
					@Result(name=DOWNLOAD, type="stream", 
							params={
							"inputName", "inputStream",
							"contentType", "application/pdf; charset=UTF-8",
							"contentLength", "${ contentLength }",
							"contentDisposition", "inline; filename = ${fileName}"
					})
			}
	)
	public String download() throws Exception {
		// セッション情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		try {
			// エンコードされている文字列を修正
			model.setSenderNm1(model.createEncodeString(model.getSenderNm1()));
			model.setSenderNm2(model.createEncodeString(model.getSenderNm2()));
			model.setSenderAddress(model.createEncodeString(model.getSenderAddress()));
			model.setSenderTelNo(model.createEncodeString(model.getSenderTelNo()));
			model.setSenderFaxNo(model.createEncodeString(model.getSenderFaxNo()));
			
			// 帳票を作成
			model = service.downloadReport(model);

			// ファイルオブジェクトを取得する
			File printFile = new File(model.getOutputPdfPath());

			// ダウンロード情報を取得する
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(printFile)));
			setContentLength(printFile.length());
			setFileName(FilenameUtils.getName(model.getOutputPdfPath()));

			return DOWNLOAD;
		} catch (ForbiddenException e) {
			if (userContext.isIPad()) {
				// iPadの場合は、403エラーを画面に表示
				return FORBIDDEN_ERROR;
			} else {
				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			if (userContext.isIPad()) {
				// iPadの場合は、500エラーを画面に表示
				return ERROR;
			} else {
				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG9001");
				return MESSAGE_AND_CLOSE;
			}
		}
	}
	
	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB014ReportDisclosureModel getModel() {
		return model;
	}
}
