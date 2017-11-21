package jp.co.tokaigroup.tlcss_b2b.user.action;


import java.io.ByteArrayInputStream;
import java.io.File;


import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;


import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 管理情報アップロードダウンロードアクションクラス。
 *
 * @author v140546
 * @version 1.0 2014/08/05
 */
@Controller
@Scope("prototype")

public class TB043CustomerFileDownloadAction  extends DownloadActionSupport
	implements ModelDriven<TB043CustomerUploadModel> {

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB043CustomerUploadModel model = new TB043CustomerUploadModel();

	/** 取込ファイルパス */
	private static final String UPLOAD_PATH_CUSTOMER = ResourceFactory.getResource().getString("UPLOAD_PATH_CUSTOMER");

	/** 取込エラーファイルパス */
	private static final String UPLOAD_PATH_CUSTOMER_ERROR = ResourceFactory.getResource().getString("UPLOAD_PATH_CUSTOMER_ERROR");

	/**
	 * 選択したファイルを開きます。
	 *
	 * @return 管理情報アップロード画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="selectFileDownload",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			}
	)
	public String download() throws Exception {

		try {

			// ユーザーエージェント設定
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));
			// ユーザーコンテキストの設定
			model.setUserContext((TLCSSB2BUserContext) getUserContext());


			//ファイルパスを設定する
			String selectFileNm = model.getSelectFileNm();
			String filyType = model.getFileDownloadType();
			String selectFilePath = null;

			//セキュリティチェック
			if (!selectFileNm.substring(0,10).equals(model.getUserContext().getKokyakuId())) {
				if (model.isIPad()) {
					// iPadの場合は、403エラーを画面に表示
					return FORBIDDEN_ERROR;
				}
				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}

			//ダウンロード情報の設定
			if (TB043CustomerUploadModel.FILE_DOWNLOAD_TYPE_NOEMAL.equals(filyType)) {
				selectFilePath = UPLOAD_PATH_CUSTOMER + "/" + selectFileNm;
			} else if (TB043CustomerUploadModel.FILE_DOWNLOAD_TYPE_ERROR.equals(filyType)) {
				selectFilePath = UPLOAD_PATH_CUSTOMER_ERROR + "/" + selectFileNm;
			} else {
				if (model.isIPad()) {
					// iPadの場合は、403エラーを画面に表示
					return FORBIDDEN_ERROR;
				}
				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}

			// ファイルオブジェクトを取得する
			File selectFile = new File(selectFilePath);

			if(!selectFile.exists()) {
				if (model.isIPad()) {
					// iPadの場合は、404エラーを画面に表示
					return PAGE_NOTFOUND_ERROR;
				}
				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0024");
				return MESSAGE_AND_CLOSE;
			}

			//ダウンロード情報を取得する
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(selectFile)));
			setContentLength(selectFile.length());
			setFileName(selectFileNm);

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
	public TB043CustomerUploadModel getModel() {
		return model;
	}
}


