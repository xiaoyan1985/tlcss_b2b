package jp.co.tokaigroup.tlcss_b2b.common.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010DocumentFileDownloadModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB010MenuService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 文書ライブラリダウンロードアクションクラス。
 *
 * @author v140546
 * @version 1.0 2014/08/29
 * @version 1.1 2015/10/28 C.Kobayashi セキュリティチェックの追加
 */
@Controller
@Scope("prototype")

public class TB010DocumentFileDownloadAction extends DownloadActionSupport
	implements ModelDriven<TB010DocumentFileDownloadModel> {
	/** PDF公開ファイルパス */
	private final static  String UPLOAD_PATH_KOKYAKU_DOCUMENT = ResourceFactory.getResource().getString("UPLOAD_PATH_KOKYAKU_DOCUMENT");

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB010DocumentFileDownloadModel model = new TB010DocumentFileDownloadModel();
	
	/** サービス */
	@Autowired
	private TB010MenuService service;
	

	/**
	 * ダウロード処理を行います。
	 *
	 * @return
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action(value="documentFileDownload",
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

			//セキュリティチェック
			if (model.getUserContext().isOutsourcerSv() || model.getUserContext().isOutsourcerOp()) {
				// セッションの権限が「40:委託会社SV」または「41:委託会社OP」の場合
				// 委託会社関連チェックを行う
				if (!service.isValidDocumentDownload(model)) {
					// 委託会社関連チェックがNGの場合は、アクセスエラー
					if (model.isIPad()) {
						// iPadの場合は、403エラーを画面に表示
						return FORBIDDEN_ERROR;
					}
					// それ以外の場合は、アラートにエラーメッセージを表示
					addActionError("MSG0022");
					return MESSAGE_AND_CLOSE;
				}
			} else if (model.getUserContext().isRealEstate()) {
				// セッションの権限が「20:管理会社」の場合
				if (!model.getRealFileNm().substring(0,10).equals(model.getUserContext().getKokyakuId())) {
					if (model.isIPad()) {
						// iPadの場合は、403エラーを画面に表示
						return FORBIDDEN_ERROR;
					}
					// それ以外の場合は、アラートにエラーメッセージを表示
					addActionError("MSG0022");
					return MESSAGE_AND_CLOSE;
				}
			}

			//ファイルパスを設定する
			String downloadFilePath = UPLOAD_PATH_KOKYAKU_DOCUMENT + "/" + model.getRealFileNm();
			// ファイルオブジェクトを取得する
			File downloadFile = new File(downloadFilePath);

			if (!downloadFile.exists()) {
				if (model.isIPad()) {
					// iPadの場合は、404エラーを画面に表示
					return PAGE_NOTFOUND_ERROR;
				}

				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0024");
				return MESSAGE_AND_CLOSE;
			}

			//ダウンロード情報を取得する
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(downloadFile)));
			setContentLength(downloadFile.length());
			setFileName(model.getRealFileNm());

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
	public TB010DocumentFileDownloadModel getModel() {
		return model;
	}
}


