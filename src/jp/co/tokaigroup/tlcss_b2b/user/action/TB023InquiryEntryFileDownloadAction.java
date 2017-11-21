package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB023InquiryEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB023InquiryEntryService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 問い合わせファイルダウンロードアクションクラス。
 *
 * @author k002849
 * @version 1.0 2015/09/10
 */
@Controller
@Scope("prototype")
public class TB023InquiryEntryFileDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB023InquiryEntryModel> {

	/** 問い合わせファイルアップロードパス */
	private static final String UPLOAD_PATH_INQUIRY_FILE = ResourceFactory.getResource().getString("UPLOAD_PATH_INQUIRY_FILE");

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB023InquiryEntryModel model = new TB023InquiryEntryModel();

	/** サービス */
	@Autowired
	private TB023InquiryEntryService service;

	/**
	 * 問い合わせファイルダウンロード処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("inquiryEntryFileDownload")
	public String download() throws Exception {
		// ログインユーザー情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		try {
			// 問い合わせファイルダウンロードチェック
			if (!service.isDownloableToiawaseFile(model)) {
				// 依頼チェックNGの場合
				if (userContext.isIPad()) {
					// iPadの場合は、403エラーを画面に表示
					return FORBIDDEN_ERROR;
				}

				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}
			
			// ファイル名
			String uploadFileName = 
				UPLOAD_PATH_INQUIRY_FILE + System.getProperty("file.separator") + model.getUploadFileNm();
			
			// チェックOKの場合は、ファイルのダウンロード
			File uploadFile = new File(uploadFileName);

			if (!uploadFile.exists()) {
				// ファイルが存在しない場合
				if (userContext.isIPad()) {
					// iPadの場合は、404エラーを画面に表示
					return PAGE_NOTFOUND_ERROR;
				}

				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0024");
				return MESSAGE_AND_CLOSE;
			}

			//ダウンロード情報を取得する
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(uploadFile)));
			setContentLength(uploadFile.length());
			setFileName(model.getUploadFileNm());
			
		} catch (Exception e) {
			log.error(getText("MSG9001"), e);

			if (userContext.isIPad()) {
				// iPadの場合は、別ウィンドウに表示するのでそのまま
				return ERROR;
			}

			// それ以外の場合は、アラートにエラーメッセージを表示
			addActionError("MSG9001");
			return MESSAGE_AND_CLOSE;
		}
		
		return DOWNLOAD;
	}
	
	/**
	 * 画面モデルを返します。
	 *
	 * @return 問い合わせ登録画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB023InquiryEntryModel getModel() {
		return model;
	}
}
