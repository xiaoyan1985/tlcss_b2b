package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB032RequestEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB032RequestEntryService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 依頼内容詳細・作業状況登録画像ダウンロードアクションクラス。
 *
 * @author k002849
 * @version 4.0 2014/07/18
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb032_request_entry.jsp")
})
public class TB032RequestEntryDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB032RequestEntryModel> {
	// プロパティファイルから取得
	/** 作業状況ファイルアップロードパス */
	private static final String UPLOAD_PATH_SAGYO_JOKYO = ResourceFactory.getResource().getString("UPLOAD_PATH_SAGYO_JOKYO");
	/** 作業状況ファイル(TLCSS)アップロードパス */
	private static final String UPLOAD_PATH_TLCSS_SAGYO_JOKYO = ResourceFactory.getResource().getString("UPLOAD_PATH_TLCSS_SAGYO_JOKYO");

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB032RequestEntryModel model = new TB032RequestEntryModel();

	/** サービス */
	@Autowired
	private TB032RequestEntryService service;

	/**
	 * 画像ダウンロード処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("requestEntryDownload")
	public String download() throws Exception {
		try {
			// ログインユーザー情報取得
			model.setUserContext((TLCSSB2BUserContext) getUserContext());

			// ユーザーエージェント設定
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			// 依頼情報チェック
			if (!service.checkDownloadInfo(model)) {
				// 依頼情報チェックNGの場合、403エラー画像を画面に表示（swipebox使用にて、画面エラー表示ができないため）
				downloadImageFile(getResourceImageFile(Constants.IMAGE_FILENAME_ACCESS_ERROR));

				return DOWNLOAD;
			}

			// チェックOKの場合は、ファイルのダウンロード
			// 画面が作業状況登録の場合は、TORES側のパスを使用、画面が依頼内容詳細の場合は、TLCSS側のパスを使用
			String uploadPath = model.isContractorAnswerFileDisplay() ?
					UPLOAD_PATH_SAGYO_JOKYO : UPLOAD_PATH_TLCSS_SAGYO_JOKYO;
			File uploadFile = new File(uploadPath + System.getProperty("file.separator") + model.getUploadFileNm());

			if (uploadFile.exists()) {
				// ファイルが存在する場合、対象の画像を表示
				downloadImageFile(uploadFile);
			} else {
				// ファイルが存在しない場合、404エラー画像を画面に表示（swipebox使用にて、画面エラー表示ができないため）
				downloadImageFile(getResourceImageFile(Constants.IMAGE_FILENAME_FILE_NOTFOUND));
			}
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
	public TB032RequestEntryModel getModel() {
		return model;
	}
}
