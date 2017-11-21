package jp.co.tokaigroup.tlcss_b2b.user.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import jp.co.tokaigroup.si.fw.action.DownloadActionSupport;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.MessageBean;
import jp.co.tokaigroup.tlcss_b2b.dto.TB044ReceptionListPrintDto;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB044ReceptionListPrintModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB044ReceptionListPrintService;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 受付一覧印刷ダウンロードアクションクラス。
 *
 * @author v140546
 * @version 1.0 2014/07/08
 */
@Controller
@Scope("prototype")

public class TB044ReceptionListPrintDownloadAction extends DownloadActionSupport
	implements ModelDriven<TB044ReceptionListPrintModel> {

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB044ReceptionListPrintModel model = new TB044ReceptionListPrintModel();

	/** サービス */
	@Autowired
	private TB044ReceptionListPrintService service;


	/**
	 * 印刷処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("receptionListPrint")
	public String download() throws Exception {
		try {
			// 印刷処理
			model.setPdfUrl(service.createPdf(model));

			// ファイルオブジェクトを取得する
			File printFile = new File(model.getPdfUrl());
			//ダウンロード情報を取得する
			setInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(printFile)));
			setContentLength(printFile.length());
			setFileName(model.getPdfUrl().substring(model.getPdfUrl().lastIndexOf(TB044ReceptionListPrintDto.REPORT_NM)));

			return DOWNLOAD;

		} catch (ValidationException e) {
			List<MessageBean> beanList =  e.getValidationPack().getActionErrors();
			for (MessageBean mb : beanList) {
				addActionError(mb.getMessageId());
			}
			return (model.isIPad() ? MESSAGE : MESSAGE_AND_CLOSE);

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
	public TB044ReceptionListPrintModel getModel() {
		return model;
	}
}
