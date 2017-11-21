package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB033RequestFullEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB033RequestFullEntryService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 依頼登録画像ダウンロードアクションクラス。
 *
 * @author S.Nakano
 * @version 1.0 2015/10/27
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb033_request_full_entry.jsp")
})
public class TB033RequestFullEntryImageDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB033RequestFullEntryModel> {
	// プロパティファイルから取得
	/** 作業状況ファイル(TLCSS)アップロードパス */
	private static final String UPLOAD_PATH_TLCSS_SAGYO_JOKYO = ResourceFactory.getResource().getString("UPLOAD_PATH_TLCSS_SAGYO_JOKYO");

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB033RequestFullEntryModel model = new TB033RequestFullEntryModel();

	/** サービス */
	@Autowired
	private TB033RequestFullEntryService service;

	/**
	 * 画像ダウンロード処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("requestFullEntryImageDownload")
	public String download() throws Exception {
		try {
			// ダウンロード妥当性チェック
			service.validateImageFileDownlod(model);

			// チェックOKの場合は、ファイルのダウンロード
			File uploadFile = new File(UPLOAD_PATH_TLCSS_SAGYO_JOKYO + 
					System.getProperty("file.separator") + model.getUploadFileNm());

			if (uploadFile.exists()) {
				// ファイルが存在する場合、対象の画像を表示
				downloadImageFile(uploadFile);
			} else {
				// ファイルが存在しない場合、404エラー画像を画面に表示（swipebox使用にて、画面エラー表示ができないため）
				downloadImageFile(getResourceImageFile(Constants.IMAGE_FILENAME_FILE_NOTFOUND));
			}
		} catch (ForbiddenException e) {
			log.error(e.getMessage());
			
			// アクセス権エラーの場合、403エラー画像を画面に表示（swipebox使用にて、画面エラー表示ができないため）
			downloadImageFile(getResourceImageFile(Constants.IMAGE_FILENAME_ACCESS_ERROR));
		} catch (Exception e) {
			log.error(e.getMessage());

			// エラー発生時、500エラー画像を画面に表示（swipebox使用にて、画面エラー表示ができないため）
			downloadImageFile(getResourceImageFile(Constants.IMAGE_FILENAME_SERVER_ERROR));
		}

		return DOWNLOAD;
	}

	/**
	 * リソースから画像ファイルを取得します。
	 *
	 * @param imageFileNm 画像ファイル名
	 * @return 画像ファイルオブジェクト
	 */
	private File getResourceImageFile(String imageFileNm) {
		URL imageFileUrl = this.getClass().getResource(
				Constants.RESOURCE_IMAGE_PATH + "/" + imageFileNm);

		return new File(imageFileUrl.getFile());
	}

	/**
	 * イメージ画像ファイルをダウンロードします。
	 *
	 * @param imageFile イメージ画像ファイル
	 * @throws IOException 入出力例外が発生した場合
	 */
	private void downloadImageFile(File imageFile) throws IOException {
		//ダウンロード情報を取得する
		setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(imageFile)));
		setContentLength(imageFile.length());
		setFileName(model.getUploadFileNm());
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB033RequestFullEntryModel getModel() {
		return model;
	}
}
