package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB023InquiryEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB023InquiryEntryService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 入電報告書ダウンロードアクションクラス。
 *
 * @author 阿部
 * @version 1.0 2015/08/20
 * @version 1.1 2015/12/11 S.Nakano ダウンロードをそれぞれのファイル拡張子で行えるように修正
 */
@Controller
@Scope("prototype")
public class TB023InquiryEntryDownloadAction extends DownloadActionSupport
		implements ModelDriven<TB023InquiryEntryModel> {

	/** 入電報告書アップロードパス */
	private static final String UPLOAD_PATH_HOKOKUSHO_KOKAI = ResourceFactory.getResource().getString("UPLOAD_PATH_HOKOKUSHO_KOKAI");

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB023InquiryEntryModel model = new TB023InquiryEntryModel();

	/** サービス */
	@Autowired
	private TB023InquiryEntryService service;

	/**
	 * 入電報告書ダウンロード処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("inquiryEntryDownload")
	public String download() throws Exception {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		try {
			// 帳票ダウンロード情報取得
			model = service.getPrintDownloadInfo(model);
			
			if (!model.isDownloadable()) {
				// ダウンロードエラーの場合
				if (userContext.isIPad()) {
					// 403エラーを画面に表示
					return FORBIDDEN_ERROR;
				} else {
					// アラートにエラーメッセージを表示
					addActionError("MSG0022");
					return MESSAGE_AND_CLOSE;
				}
			}
			
			// ファイル名
			String uploadFileName = "";
			
			if (TB023InquiryEntryModel.SHORI_KBN_INCOMING_CALL_REPORT.equals(model.getShoriKbn())) {
				// 入電報告書ダウンロードの場合
				if (StringUtils.isBlank(model.getToiawaseInfo().getExtNyudenHokokushoFileNm())) {
					// 問い合わせ情報の外部連携＿入電報告書ファイル名が設定されていない場合
					uploadFileName = UPLOAD_PATH_HOKOKUSHO_KOKAI +
						System.getProperty("file.separator") + model.getToiawaseNo() + ".pdf";
				} else {
					// 問い合わせ情報の外部連携＿入電報告書ファイル名が設定されている場合
					String extension = FilenameUtils.getExtension(model.getToiawaseInfo().getExtNyudenHokokushoFileNm());
					if (StringUtils.isNotBlank(extension)) {
						// 拡張子なしでない場合は、「.」を付加する
						extension = "." + extension;
					}
					uploadFileName = UPLOAD_PATH_HOKOKUSHO_KOKAI +
						System.getProperty("file.separator") + model.getToiawaseNo() + extension;
				}
			} else if (TB023InquiryEntryModel.SHORI_KBN_WORK_REPORT.equals(model.getShoriKbn())) {
				// 作業報告書ダウンロードの場合
				if (StringUtils.isBlank(model.getToiawaseInfo().getExtHokokushoFileNm())) {
					// 問い合わせ情報の外部連携＿作業報告書ファイル名が設定されていない場合
					uploadFileName = UPLOAD_PATH_HOKOKUSHO_KOKAI +
						System.getProperty("file.separator") + model.getToiawaseNo() + "-" +
						model.getToiawaseRirekiNo().toPlainString() + ".pdf";
				} else {
					// 問い合わせ情報の外部連携＿作業報告書ファイル名が設定されている場合
					String extension = FilenameUtils.getExtension(model.getToiawaseInfo().getExtHokokushoFileNm());
					if (StringUtils.isNotBlank(extension)) {
						// 拡張子なしでない場合は、「.」を付加する
						extension = "." + extension;
					}
					uploadFileName = UPLOAD_PATH_HOKOKUSHO_KOKAI +
						System.getProperty("file.separator") + model.getToiawaseNo() + "-" +
						model.getToiawaseRirekiNo().toPlainString() + extension;
				}
			} else {
				// それ以外の場合は、エラー
				if (userContext.isIPad()) {
					// 別ウィンドウに表示するのでそのまま
					return ERROR;
				} else {
					// アラートにエラーメッセージを表示
					addActionError("MSG9001");
					return MESSAGE_AND_CLOSE;
				}
			}
			
			// チェックOKの場合は、ファイルのダウンロード
			File uploadFile = new File(uploadFileName);

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
			setFileName(FilenameUtils.getName(uploadFileName));

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
	 * @return 問い合わせ登録画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB023InquiryEntryModel getModel() {
		return model;
	}
}
