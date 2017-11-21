package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB029InquiryKbnManualModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB029InquiryKbnManualService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 問い合わせ区分マニュアル一覧ダウンロードアクションクラス。
 *
 * @author H.Yamamura
 * @version 1.0 2015/11/20
 */
@Controller
@Scope("prototype")
public class TB029InquiryKbnManualDownloadAction extends DownloadActionSupport
	implements ModelDriven<TB029InquiryKbnManualModel>{

	/** アップロードパス */
	private static final String UPLOAD_PATH_INQUIRY_KBN_MANUAL = ResourceFactory.getResource().getString("UPLOAD_PATH_INQUIRY_KBN_MANUAL");

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB029InquiryKbnManualModel model = new TB029InquiryKbnManualModel();

	/** サービス */
	@Autowired
	private TB029InquiryKbnManualService service;

	/**
	 * マニュアルファイルダウンロード処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("inquiryKbnManualDownload")
	public String download() throws Exception {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// セッションの権限が「TOKAI管理者」または「TOKAI一般者」または「委託会社SV」または「委託会社OP」以外の場合403エラー
		if (!(userContext.isAdministrativeInhouse()
			|| userContext.isGeneralInhouse() 
			|| userContext.isOutsourcerSv() 
			|| userContext.isOutsourcerOp())) {
			throw new ForbiddenException("アクセス権限が存在しません。");
		}
		try {
			// 対象のアップロードファイルを取得
			String uploadFileNm = service.getFileNm(model);
			// ファイル名が存在しない場合
			if (StringUtils.isBlank(uploadFileNm)) {
				if (userContext.isIPad()) {
					// 404エラーを画面に表示
					return PAGE_NOTFOUND_ERROR;
				} else {
					// アラートにエラーメッセージを表示
					addActionError("MSG0024");
					return MESSAGE_AND_CLOSE;
				}
			}
			// チェックOKの場合は、ファイルのダウンロード
			File uploadFile = new File(UPLOAD_PATH_INQUIRY_KBN_MANUAL + System.getProperty("file.separator") + uploadFileNm);
			// ファイルが存在しない場合
			if (!uploadFile.exists()) {
				if (userContext.isIPad()) {
					// 404エラーを画面に表示
					return PAGE_NOTFOUND_ERROR;
				} else {
					// アラートにエラーメッセージを表示
					addActionError("MSG0024");
					return MESSAGE_AND_CLOSE;
				}
			}

			// ダウンロード情報を取得する
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(uploadFile)));
			setContentLength(uploadFile.length());
			setFileName(uploadFileNm);

			return DOWNLOAD;
		} catch (Exception e) {
			log.error(getText("MSG9001"), e);

			if (userContext.isIPad()) {
				// 別ウィンドウに表示するのでそのまま
				return ERROR;
			} else {
				// アラートにエラーメッセージを表示
				addActionError("MSG9001");
				return MESSAGE_AND_CLOSE;
			}
		}
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 問い合わせ区分マニュアル一覧画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB029InquiryKbnManualModel getModel() {
		return model;
	}
}
