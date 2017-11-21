package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB032RequestEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB032RequestEntryService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 依頼内容詳細・作業状況登録その他ファイルダウンロードアクションクラス。
 *
 * @author k002849
 * @version 4.0 2014/09/04
 */
@Controller
@Scope("prototype")
public class TB032RequestDetailDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB032RequestEntryModel> {
	// プロパティファイルから取得
	/** 依頼情報アップロードパス */
	private static final String UPLOAD_PATH_IRAI_FILE = ResourceFactory.getResource().getString("UPLOAD_PATH_IRAI_FILE");

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB032RequestEntryModel model = new TB032RequestEntryModel();

	/** サービス */
	@Autowired
	private TB032RequestEntryService service;

	/**
	 * その他ファイルダウンロード処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("requestDetailDownload")
	public String download() throws Exception {
		try {
			// ログインユーザー情報取得
			model.setUserContext((TLCSSB2BUserContext) getUserContext());
			// ユーザーエージェント設定
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			// 依頼情報のチェック
			if (!service.checkOtherFileDownloadInfo(model)) {
				// 依頼チェックNGの場合
				if (model.isIPad()) {
					// iPadの場合は、403エラーを画面に表示
					return FORBIDDEN_ERROR;
				}

				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}

			// チェックOKの場合は、ファイルのダウンロード
			File uploadFile = new File(UPLOAD_PATH_IRAI_FILE +
								System.getProperty("file.separator") +
								model.getUploadFileNm());

			if (!(uploadFile.exists())) {
				// ファイルが存在しない場合
				if (model.isIPad()) {
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
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB032RequestEntryModel getModel() {
		return model;
	}
}
