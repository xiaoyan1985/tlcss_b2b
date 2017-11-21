package jp.co.tokaigroup.tlcss_b2b.user.action;


import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB013IncomingCallReportModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB013IncomingCallReportService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 入電報告書印刷アクションクラス。
 *
 * @author v140546
 * @version 1.0 2014/05/27
 * @version 1.1 2015/12/11 S.Nakano ダウンロードをそれぞれのファイル拡張子で行えるように修正
 */
@Controller
@Scope("prototype")

public class TB013IncomingCallReportAction extends DownloadActionSupport
	implements ModelDriven<TB013IncomingCallReportModel> {
	/** PDF公開ファイルパス */
	private final static  String UPLOAD_PATH_HOKOKUSHO_KOKAI = ResourceFactory.getResource().getString("UPLOAD_PATH_HOKOKUSHO_KOKAI");

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB013IncomingCallReportModel model = new TB013IncomingCallReportModel();

	/** サービス */
	@Autowired
	private TB013IncomingCallReportService service;

	/**
	 * ダウロード処理を行います。
	 *
	 * @return 顧客情報CSV一括取込画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="incomingCallReportDownload",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			}
	)
	public String download() throws Exception {

		try {
			// ユーザーエージェント設定
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			// ダウンロード情報取得
			model = service.getDowloadInfo(model);
			
			if (!model.isDownloadable()) {
				// ダウンロードエラーの場合は
				if (model.isIPad()) {
					// iPadの場合は、403エラーを画面に表示
					return FORBIDDEN_ERROR;
				}

				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}
			
			String nyudenFilePath = "";
			String extension = "";
			if (StringUtils.isBlank(model.getToiawase().getExtNyudenHokokushoFileNm())) {
				// 問い合わせ情報の外部連携＿入電報告書ファイル名が設定されていない場合
				nyudenFilePath = UPLOAD_PATH_HOKOKUSHO_KOKAI + "/" + model.getToiawaseNo() + ".pdf";
				extension = ".pdf";
			} else {
				// 問い合わせ情報の外部連携＿入電報告書ファイル名が設定されている場合
				extension = FilenameUtils.getExtension(model.getToiawase().getExtNyudenHokokushoFileNm());
				if (StringUtils.isNotBlank(extension)) {
					// 拡張子なしでない場合は、「.」を付加する
					extension = "." + extension;
				}
				
				nyudenFilePath = UPLOAD_PATH_HOKOKUSHO_KOKAI + "/" + model.getToiawaseNo() + extension;
			}

			// ファイルオブジェクトを取得する
			File nyudenFile = new File(nyudenFilePath);

			if (!nyudenFile.exists()) {
				if (model.isIPad()) {
					// iPadの場合は、404エラーを画面に表示
					return PAGE_NOTFOUND_ERROR;
				}

				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0024");
				return MESSAGE_AND_CLOSE;
			}

			//ダウンロード情報を取得する
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(nyudenFile)));
			setContentLength(nyudenFile.length());
			setFileName(model.getToiawaseNo() + extension);

			return DOWNLOAD;
		} catch (Exception e) {
			log.error(getText("MSG9001"), e);

			if (model.isIPad()) {
				// iPadの場合は、別ウィンドウに表示するのでそのまま
				return ERROR;
			}

			// それ以外の場合は、アラートにエラーメッセージを表示
			addActionError("MSG9001");
			return MESSAGE_AND_CLOSE;
		}

	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB013IncomingCallReportModel getModel() {
		return model;
	}
}


