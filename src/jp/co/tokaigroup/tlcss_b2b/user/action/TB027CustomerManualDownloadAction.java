package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB027CustomerManualModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB027CustomerManualService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 顧客マニュアルダウンロードアクションクラス。
 *
 * @author 松葉
 * @version 1.0 2015/08/06
 */
@Controller
@Scope("prototype")
public class TB027CustomerManualDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB027CustomerManualModel> {
	// プロパティファイルから取得
	/** 顧客マニュアルファイル(TLCSS)アップロードパス */
	private static final String UPLOAD_PATH_TLCSS_KOKYAKU_MANUAL = ResourceFactory.getResource().getString("UPLOAD_PATH_KOKYAKU_MANUAL");

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB027CustomerManualModel model = new TB027CustomerManualModel();

	/** サービス */
	@Autowired
	private TB027CustomerManualService service;

	/**
	 * マニュアルファイルダウンロード処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("customerManualDownload")
	public String download() throws Exception {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		try {
			// セキュリティチェック（委託会社関連チェック）
			if (!service.isValidKokyakuManualInfo(model)) {
				if (userContext.isIPad()) {
					// 403エラーを画面に表示
					return FORBIDDEN_ERROR;
				} else {
					// アラートにエラーメッセージを表示
					addActionError("MSG0022");
					return MESSAGE_AND_CLOSE;
				}
			}

			// チェックOKの場合は、ファイルのダウンロード
			File uploadFile = new File(UPLOAD_PATH_TLCSS_KOKYAKU_MANUAL + System.getProperty("file.separator") + model.getUploadFileNm());

			if (!uploadFile.exists()) {
				// ファイルが存在しない場合
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
			setFileName(model.getUploadFileNm());

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
	 * @return 顧客マニュアル一覧画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB027CustomerManualModel getModel() {
		return model;
	}
}
