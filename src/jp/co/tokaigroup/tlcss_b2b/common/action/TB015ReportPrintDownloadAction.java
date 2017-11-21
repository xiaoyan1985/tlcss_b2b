package jp.co.tokaigroup.tlcss_b2b.common.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB015ReportPrintService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 報告書印刷ダウンロードアクションクラス。
 *
 * @author k002785
 * @version 1.0 2015/08/07
 */
@Controller
@Scope("prototype")
public class TB015ReportPrintDownloadAction extends DownloadActionSupport
implements ModelDriven<TB015ReportPrintModel>{

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/** 画面モデル */
	private TB015ReportPrintModel model = new TB015ReportPrintModel();
	
	/** サービス */
	@Autowired
	private TB015ReportPrintService service;

	/**
	 * 帳票出力処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("reportPrintDownload")
	public String download() throws Exception {
		// セッション情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		try {
			// 帳票を作成
			model = service.createPdf(model);

			// ファイルオブジェクトを取得する
			File printFile = new File(model.getMakePdfPath());

			// ダウンロード情報を取得する
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(printFile)));
			setContentLength(printFile.length());
			setFileName(model.getPdfNm());

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
	public TB015ReportPrintModel getModel() {
		return model;
	}
}
