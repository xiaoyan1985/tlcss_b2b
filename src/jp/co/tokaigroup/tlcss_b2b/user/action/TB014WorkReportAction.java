package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB014WorkReportModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB014WorkReportService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 作業報告書ダウンロードアクションクラス。
 *
 * @author k002849
 * @version 4.0 2014/07/18
 */
@Controller
@Scope("prototype")
public class TB014WorkReportAction extends DownloadActionSupport
		implements ModelDriven<TB014WorkReportModel> {
	// プロパティファイルから取得
	/** 報告書アップロードパス */
	private static final String UPLOAD_PATH_HOKOKUSHO_KOKAI = ResourceFactory.getResource().getString("UPLOAD_PATH_HOKOKUSHO_KOKAI");

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB014WorkReportModel model = new TB014WorkReportModel();

	/** サービス */
	@Autowired
	private TB014WorkReportService service;

	/**
	 * 作業報告書ダウンロード処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("workReportDownload")
	public String download() throws Exception {
		try {
			// ログインユーザー情報取得
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// ユーザーエージェント設定
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			// 顧客ID、業者コードの設定
			model.setKokyakuId(userContext.getRefKokyakuId());
			model.setGyoshaCd(userContext.getGyoshaCd());

			// ログインユーザーが社内以外、依頼情報のチェック
			if (!userContext.isInhouse()) {
				// 依頼情報チェック
				if (!service.isOwn(model)) {
					if (model.isIPad()) {
						// iPadの場合は、403エラーを画面に表示
						return FORBIDDEN_ERROR;
					}

					// それ以外の場合は、アラートにエラーメッセージを表示
					addActionError("MSG0022");
					return MESSAGE_AND_CLOSE;
				}
			}

			// ファイル名
			String fileNm = model.getToiawaseNo() + "-" +
							model.getToiawaseRirekiNo().toString() + ".pdf";

			// チェックOKの場合は、ファイルのダウンロード
			File uploadFile = new File(UPLOAD_PATH_HOKOKUSHO_KOKAI +
								System.getProperty("file.separator") +
								fileNm);

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
			setFileName(fileNm);

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
	public TB014WorkReportModel getModel() {
		return model;
	}
}
