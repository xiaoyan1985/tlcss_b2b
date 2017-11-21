package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.math.BigDecimal;
import java.util.List;

import jp.co.tokaigroup.reception.common.logic.CheckToPublishToiawaseLogic;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTSagyoJokyoDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.print.logic.RC905NyudenHokokuHyoLogic;
import jp.co.tokaigroup.reception.print.logic.RC906SagyoHokokuHyoLogic;
import jp.co.tokaigroup.reception.print.logic.RC912PhotoReportLogic;
import jp.co.tokaigroup.reception.util.FileRemoteUtil;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB014ReportDisclosureModel;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 報告書公開設定サービス実装クラス。
 *
 * @author k002785
 * @version 1.0 2015/08/11
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB014ReportDisclosureServiceImpl extends TLCSSB2BBaseService implements TB014ReportDisclosureService {

	/** コピー元アップロードパス */
	private static final String UPLOAD_PATH_PDF = ResourceFactory.getResource().getString("PDF_PATH");

	/** コピー先アップロードパス */
	private static final String UPLOAD_PATH_HOKOKUSHO_KOKAI = ResourceFactory.getResource().getString("UPLOAD_PATH_HOKOKUSHO_KOKAI");

	/** 一時保存ファイル名 */
	private static final String TEMP_FILE_NM = "TEMP";

	/** 問い合わせ情報ＴDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;
	
	/** 作業状況ＴDAO */
	@Autowired
	private RcpTSagyoJokyoDao sagyoJokyoDao;
	
	/** リセプションアクセスログDAO */
	@Autowired
	private TbTAccesslogDao accesslogDao;
	
	/** 委託会社関連チェックロジック */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	
	/** 帳票 入電報告書　ロジッククラス */
	@Autowired
	private RC905NyudenHokokuHyoLogic nyudenHokokuHyoLogic;
	
	/** 帳票 作業依頼書　ロジッククラス */
	@Autowired
	private RC906SagyoHokokuHyoLogic sagyoHokokuHyoLogic;
	
	/** 写真報告書ロジッククラス */
	@Autowired
	private RC912PhotoReportLogic photoReportLogic;
	
	/** チェックロジック */
	@Autowired
	private CheckToPublishToiawaseLogic checkToPublishToiawaseLogic;

	/**
	 * 初期情報取得処理を行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB014ReportDisclosureModel getInitInfo(TB014ReportDisclosureModel model) {
		
		// コンテンツ情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 問い合わせ情報の取得
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		
		// 問い合わせ情報が存在しない場合は処理を終了
		if (toiawase == null){
			throw new ForbiddenException("問い合わせ情報が取得できません。");
		}
		
		// セッションの権限が「40:委託会社SV」または「41:委託会社OP」の場合
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// 委託関連会社チェックでNGの場合
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), toiawase.getKokyakuId())){
				throw new ForbiddenException("アクセス権限が存在しません。");
			}
		}

//		// 帳票区分が「入電報告書」の場合
//		if (model.isPrintableIncomingCallReport()) {
//			// 入電報告書
//			List<String[]> allDataList =
//				nyudenHokokuHyoLogic.getNyudenHokokuHyoCsvList(model.getToiawaseNo(),
//						model.getKokyakuId(), model.getSenderNm1(),
//						model.getSenderNm2(), model.getSenderAddress(),
//						model.getSenderTelNo(), model.getSenderFaxNo(), RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_KOKAI);
//
//			// PDF出力（マルチ出力）
//			model.setPdfUrl(nyudenHokokuHyoLogic.outputPdf(allDataList, true, true));
//		// 帳票区分が「作業報告書」の場合
//		} else {
//			// 作業報告書
//			List<String[]> allDataList =
//				sagyoHokokuHyoLogic.getSagyoHokokuCsvList(model.getToiawaseNo(),
//						model.getKokyakuId(), model.getToiawaseRirekiNo(),
//						model.getSenderNm1(), model.getSenderNm2(),
//						model.getSenderAddress(), model.getSenderTelNo(),
//						model.getSenderFaxNo());
//			// 写真報告書
//			if (photoReportLogic.isExistUploadFile(model.getToiawaseNo(),
//					new BigDecimal(model.getToiawaseRirekiNo()))) {
//				//アップロードファイルが存在する場合
//				List<String[]> photoReportDataList =
//					photoReportLogic.getPhotoReportCsvList(model.getToiawaseNo(),
//							new BigDecimal(model.getToiawaseRirekiNo()), model.getKokyakuId(),
//							model.getSenderNm1(), model.getSenderNm2(),
//							model.getSenderAddress(), model.getSenderTelNo(),
//							model.getSenderFaxNo());
//				//写真報告書データを作業報告書データに加える
//				allDataList.addAll(photoReportDataList);
//			}
//
//			// PDF出力(CSV出力も同時に行う)
//			model.setPdfUrl(sagyoHokokuHyoLogic.outputPdf(allDataList, true, true));
//		}

		return model;
	}
	
	/**
	 * 公開処理を行います。
	 * 
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	@Transactional(value="txManager")
	public TB014ReportDisclosureModel discloseReport(TB014ReportDisclosureModel model){
		
		// 問い合わせ履歴番号
		BigDecimal toiawaseRirekiNo = null;
		
		// 公開種別
		int disclosureType = 0;
		
		// PDFファイル名
		String newPdfFileName = null;
		
		// 帳票区分が「入電報告書」の場合
		if (model.isPrintableIncomingCallReport()) {
			// 問い合わせテーブルの報告公開フラグを更新
			RcpTToiawase toiawase = new RcpTToiawase();
			toiawase.setToiawaseNo(model.getToiawaseNo());
			toiawase.setUpdDt(model.getToiawaseUpdDt());
			toiawase.setHokokushoKokaiFlg(RcpTToiawase.HOKOKUSHO_KOKAI_FLG_KOKAI);
			toiawase.setLastUpdId(getUserContext().getLoginId());
			toiawaseDao.updateHokokushoKokaiFlg(toiawase);
			toiawaseRirekiNo = null;
			disclosureType = CheckToPublishToiawaseLogic.CONTENT_TYPE_NYUDEN_HOKOKUSHO;
			newPdfFileName = model.getToiawaseNo() + ".pdf";
		// 帳票区分が「作業報告書」の場合
		} else {
			// 問い合わせテーブルの更新日を更新
			toiawaseDao.updateToiawaseUpdDt(model.getToiawaseNo(), model.getToiawaseUpdDt(), getUserContext().getLoginId());
			
			// 作業状況テーブルの報告公開フラグを更新
			RcpTSagyoJokyo sagyoJokyo = new RcpTSagyoJokyo();
			sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
			sagyoJokyo.setToiawaseRirekiNo(new BigDecimal(model.getToiawaseRirekiNo()));
			sagyoJokyo.setHokokushoKokaiFlg(RcpTSagyoJokyo.HOKOKUSHO_KOKAI_FLG_PUBLISH);
			sagyoJokyo.setLastUpdId(getUserContext().getLoginId());
			sagyoJokyo.setUpdDt(model.getSagyoJokyoUpdDt());
			sagyoJokyoDao.updateHoukokushoKokaiFlg(sagyoJokyo);
			
			toiawaseRirekiNo = new BigDecimal(model.getToiawaseRirekiNo());
			disclosureType = CheckToPublishToiawaseLogic.CONTENT_TYPE_SAGYO_HOKOKUSHO;
			newPdfFileName = model.getToiawaseNo() + "-" + model.getToiawaseRirekiNo() + ".pdf";
		}

		// 報告書公開設定可否チェック
		if (!checkToPublishToiawaseLogic.isValid(disclosureType, RcpTToiawase.HOKOKUSHO_KOKAI_FLG_KOKAI, model.getToiawaseNo(), toiawaseRirekiNo)) {
			// 公開設定が無効の場合、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0030", model.getGamenNm(), "公開"));
		}

		// アクセスログの登録処理
		accesslogDao.insert(model.getGamenNm(), "公開", createSearchInfo(model.getToiawaseNo(), model.getToiawaseRirekiNo(), TEMP_FILE_NM + newPdfFileName));

		// コピー元ファイル名のパスを設定
		String fromCopyPdfPath = UPLOAD_PATH_HOKOKUSHO_KOKAI + System.getProperty("file.separator") + TEMP_FILE_NM + newPdfFileName;
		// コピー先ファイル名のパスを設定
		String toCopyPdfPath = UPLOAD_PATH_HOKOKUSHO_KOKAI + System.getProperty("file.separator") + newPdfFileName;
		
		// ファイルコピーの実施
		FileRemoteUtil.remoteCopyFileBySsh(fromCopyPdfPath, toCopyPdfPath);
		
		// 問い合わせ更新日を再設定
		model.setToiawaseUpdDt(toiawaseDao.selectByPrimaryKey(model.getToiawaseNo()).getUpdDt());
		
		// 帳票区分が「作業報告書」の場合のみ作業状況更新日を再設定
		if (model.isPrintableWorkReport()) {
			model.setSagyoJokyoUpdDt(sagyoJokyoDao.selectByPrimaryKey(model.getToiawaseNo(), toiawaseRirekiNo).getUpdDt());
		}
		
		return model;
	}
	
	/**
	 * 帳票ダウンロード処理を行います。
	 * 
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB014ReportDisclosureModel downloadReport(TB014ReportDisclosureModel model) {
		
		String pdfUrl = "";
		String newPdfFileName = "";
		// 帳票区分が「入電報告書」の場合
		if (model.isPrintableIncomingCallReport()) {
			// 入電報告書
			List<String[]> allDataList =
				nyudenHokokuHyoLogic.getNyudenHokokuHyoCsvList(model.getToiawaseNo(),
						model.getKokyakuId(), model.getSenderNm1(),
						model.getSenderNm2(), model.getSenderAddress(),
						model.getSenderTelNo(), model.getSenderFaxNo(), RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_KOKAI);

			// PDF出力（マルチ出力）
			pdfUrl = nyudenHokokuHyoLogic.outputPdf(allDataList, true, true);
			// PDFファイル名
			newPdfFileName = model.getToiawaseNo() + ".pdf";
		// 帳票区分が「作業報告書」の場合
		} else {
			// 作業報告書
			List<String[]> allDataList =
				sagyoHokokuHyoLogic.getSagyoHokokuCsvList(model.getToiawaseNo(),
						model.getKokyakuId(), model.getToiawaseRirekiNo(),
						model.getSenderNm1(), model.getSenderNm2(),
						model.getSenderAddress(), model.getSenderTelNo(),
						model.getSenderFaxNo());
			// 写真報告書
			if (photoReportLogic.isExistUploadFile(model.getToiawaseNo(),
					new BigDecimal(model.getToiawaseRirekiNo()))) {
				//アップロードファイルが存在する場合
				List<String[]> photoReportDataList =
					photoReportLogic.getPhotoReportCsvList(model.getToiawaseNo(),
							new BigDecimal(model.getToiawaseRirekiNo()), model.getKokyakuId(),
							model.getSenderNm1(), model.getSenderNm2(),
							model.getSenderAddress(), model.getSenderTelNo(),
							model.getSenderFaxNo());
				//写真報告書データを作業報告書データに加える
				allDataList.addAll(photoReportDataList);
			}

			// PDF出力(CSV出力も同時に行う)
			pdfUrl = sagyoHokokuHyoLogic.outputPdf(allDataList, true, true);
			// PDFファイル名
			newPdfFileName = model.getToiawaseNo() + "-" + model.getToiawaseRirekiNo() + ".pdf";
		}
		
		// PDFファイルの名前を取得する
		String pdfFileName = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);
		// コピー元ファイル名のパスを設定
		String fromCopyPdfPath = UPLOAD_PATH_PDF + System.getProperty("file.separator") + pdfFileName;
		
		// コピー先ファイル名のパスを設定
		String toCopyPdfPath = UPLOAD_PATH_HOKOKUSHO_KOKAI + System.getProperty("file.separator") + TEMP_FILE_NM + newPdfFileName;
		
		// ファイルコピーの実施
		FileRemoteUtil.remoteCopyFileBySsh(fromCopyPdfPath, toCopyPdfPath);
		
		model.setOutputPdfPath(toCopyPdfPath);
		
		return model;
	}
	
	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param toiawaseNo 問い合わせNO
	 * @param toiawaseRirekiNo 問い合わせ履歴NO
	 * @param pdfFileName pdfファイル名
	 * 
	 * @return ログ出力内容
	 */
	private String createSearchInfo(String toiawaseNo, String toiawaseRirekiNo, String pdfFileName){
		StringBuilder sb = new StringBuilder();
		sb.append("toiawaseNo=");
		sb.append(toiawaseNo);
		sb.append(",toiawaseRirekiNo=");
		sb.append(toiawaseRirekiNo);
		sb.append(",pdfFileName=");
		sb.append(pdfFileName);
		return sb.toString();
	}

}
