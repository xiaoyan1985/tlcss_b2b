package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMSashidashininDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyohiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dto.RC905NyudenHokokuHyoDto;
import jp.co.tokaigroup.reception.dto.RC906SagyoHokokuHyoDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSashidashinin;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTSagyohi;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.kokyaku.model.RC016KokyakuKanrenEntrySearchCondition;
import jp.co.tokaigroup.reception.print.logic.RC905NyudenHokokuHyoLogic;
import jp.co.tokaigroup.reception.print.logic.RC906SagyoHokokuHyoLogic;
import jp.co.tokaigroup.reception.print.logic.RC912PhotoReportLogic;
import jp.co.tokaigroup.reception.util.FileRemoteUtil;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintModel;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 報告書印刷サービス実装クラス。
 *
 * @author k002785
 * @version 1.0 2015/08/05
 * @version 1.1 2016/07/08 H.Hirai 複数請求先対応
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB015ReportPrintServiceImpl extends TLCSSB2BBaseService implements TB015ReportPrintService {

	/** コピー元アップロードパス */
	private static final String UPLOAD_PATH_PDF = ResourceFactory.getResource().getString("PDF_PATH");
	/** コピー先アップロードパス */
	private static final String UPLOAD_PATH_INCOMING_CALL_REPORT = ResourceFactory.getResource().getString("UPLOAD_PATH_INCOMING_CALL_REPORT_FILE");
	
	/** 問い合わせ情報ＴDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;
	
	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;
	
	/** リセプション顧客契約情報マスタDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;
	
	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;
	
	/** リセプション差出人情報マスタDAO */
	@Autowired
	private RcpMSashidashininDao sashidashininDao;
	
	/** 作業費ＴDAO */
	@Autowired
	private RcpTSagyohiDao sagyohiDao;
	
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

	/**
	 * 初期情報取得処理を行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB015ReportPrintModel getInitInfo(TB015ReportPrintModel model) {
		
		// 問い合わせ情報の取得
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		
		// 問い合わせ情報が存在しない場合は処理を終了
		if (toiawase == null){
			throw new ForbiddenException("問い合わせ情報が取得できません。");
		}
		
		// セッション情報を取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 検索条件の設定
		RC016KokyakuKanrenEntrySearchCondition condition = new RC016KokyakuKanrenEntrySearchCondition();
		// 問い合わせテーブルの顧客IDを設定
		condition.setKokyakuId(toiawase.getKokyakuId());
		// 顧客区分リストを設定
		List<String> notInKokyakukbnList = new ArrayList<String>();
		notInKokyakukbnList.add(RcpMKokyaku.KOKYAKU_KBN_NYUKYOSHA);
		condition.setNotInKokyakuKbnList(notInKokyakukbnList);
		// ページ番号を設定
		condition.setOffset(1);
		// 1ページ最大表示件数を設定
		condition.setLimit(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_HOKOKUSHO_PRINT_SELECT_TO_MAX));
		// 最大検索可能件数を設定
		condition.setMaxCount(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_HOKOKUSHO_PRINT_PAGE_TO_MAX));
		// 表示最大値まで画面に表示設定をtrueで設定
		condition.setDisplayToMax(true);
		// 検索条件を設定
		model.setCondition(condition);
		
		// 検索処理実行
		List<RcpMKokyaku> kanrenList = kokyakuDao.selectKanrenList(condition);
		// 検索結果が０件の場合
		if (kanrenList == null) {
			kanrenList = new ArrayList<RcpMKokyaku>();
		}
		
		// 和名変換用情報の取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN, RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// 和名変換用Map
		Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);
		
		// 契約終了検索用顧客ＩＤリスト作成
		List<String> kokyakuIdList = new ArrayList<String>();
		for (RcpMKokyaku kokyakuInfo : kanrenList) {
			kokyakuIdList.add(kokyakuInfo.getKokyakuId());
		}

		// 契約が終了している顧客の取得
		Map<String, String> endKokyakuMap = new HashMap<String, String>();
		if (kokyakuIdList != null && !kokyakuIdList.isEmpty()) {
			endKokyakuMap = kokyakuKeiyakuDao.selectKeiyakuKanriEndKokyaku(kokyakuIdList);
		}
		
		// 取得結果に対して、和名変換と契約終了フラグの設定を行う
		for (RcpMKokyaku kokyakuInfo : kanrenList) {
			// 顧客区分の和名変換
			Map<String, RcpMComCd> kokyakuKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
			// 顧客種別の和名変換
			Map<String, RcpMComCd> kokyakuShubetsuMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);

			// 顧客区分が顧客区分Mapに存在する場合設定
			if (kokyakuKbnMap.containsKey(kokyakuInfo.getKokyakuKbn())) {
				kokyakuInfo.setKokyakuKbnNm(kokyakuKbnMap.get(kokyakuInfo.getKokyakuKbn()).getExternalSiteVal());
			}
			// 顧客種別が顧客種別Mapに存在する場合設定
			if (kokyakuShubetsuMap.containsKey(kokyakuInfo.getKokyakuShubetsu())) {
				kokyakuInfo.setKokyakuShubetsuNm(kokyakuShubetsuMap.get(kokyakuInfo.getKokyakuShubetsu()).getExternalSiteVal());
			}

			if (endKokyakuMap.containsKey(kokyakuInfo.getKokyakuId())) {
				// 契約が終了している顧客の場合
				if (StringUtils.isNotBlank(endKokyakuMap.get(kokyakuInfo.getKokyakuId()))) {
					kokyakuInfo.setEndKeiyaku(true);
				}
			}
		}
		
		model.setKanrenList(kanrenList);

		// 差出人情報を取得
		String kaishaId;

		if (userContext.isOutsourcerOp() || userContext.isOutsourcerSv()) {
			// セッション情報の権限が委託会社SVまたは委託会社OPの場合、セッションの会社IDをセット
			kaishaId = userContext.getKaishaId();
		} else {
			// セッションのシステム定数MapからＴＯＫＡＩ会社ＩＤの値を取得してセット
			kaishaId = userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_TOKAI_KAISHA_ID);
		}

		RcpMSashidashinin sashidashinin = sashidashininDao.selectByPrimaryKey(RcpMSashidashinin.KBN_NYUDEN_HOKOKU_SASHIDASHININ_KBN, kaishaId);
		model.setSashidashinin(sashidashinin);

		// 請求先顧客情報リスト取得
		List<RcpMKokyaku> seikyusakiKokyakuList =
			kokyakuDao.selectSeikyusakiKokyakuList(model.getKokyakuId());
		model.setSeikyusakiKokyakuList(seikyusakiKokyakuList);

		// 依頼登録画面からの遷移の場合
		if (model.isFromRequestEntry()) {
			// 作業費請求先情報取得
			RcpTSagyohi sagyohi = sagyohiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
			model.setSagyohi(sagyohi);
		}
		
		// 帳票区分の設定
		String initPrintKbn = model.isFromInquiryEntry() ? TB015ReportPrintModel.PRINT_KBN_INCOMING_CALL_REPORT : TB015ReportPrintModel.PRINT_KBN_WORK_REPORT ;
		model.setPrintKbn(initPrintKbn);

		return model;
	}

	/**
	 * 生成した帳票のパスを取得します。
	 *
	 * @param model 画面モデル
	 * @return 生成した帳票パス
	 */
	public TB015ReportPrintModel createPdf(TB015ReportPrintModel model) {
		
		// コンテンツ情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 帳票生成時のパス
		String pdfPath = null;

		// セキュリティチェックの実施
		executeSecurityCheck(model, userContext);
		
		// 帳票区分が「入電報告書」の場合
		if (model.isPrintableIncomingCallReport()) {
			// 入電報告書
			List<String[]> allDataList =
				nyudenHokokuHyoLogic.getNyudenHokokuHyoCsvList(model.getToiawaseNo(),
						model.getKokyakuId(), model.getSenderNm1(),
						model.getSenderNm2(), model.getSenderAddress(),
						model.getSenderTelNo(), model.getSenderFaxNo(), null);

			// PDF出力（マルチ出力）
			pdfPath = nyudenHokokuHyoLogic.outputPdf(allDataList, true, true);
		// 帳票区分が「作業報告書」の場合
		} else if (model.isPrintableWorkReport()) {
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
			pdfPath = sagyoHokokuHyoLogic.outputPdf(allDataList, true, true);
		// 上記以外の場合は例外
		} else {
			throw new ApplicationException("帳票区分不正。パラメータの帳票区分" );
		}
		// 取得したパスからファイル名を取得する
		String pdfNm = pdfPath.substring(pdfPath.lastIndexOf("/") + 1);
		
		// コピー元ファイル名のパスを設定
		String fromCopyPdfPath = UPLOAD_PATH_PDF + System.getProperty("file.separator") + pdfNm;
		// コピー先ファイル名のパスを設定
		String toCopyPdfPath = UPLOAD_PATH_INCOMING_CALL_REPORT + System.getProperty("file.separator") + pdfNm;
		
		// ファイルコピーの実施
		FileRemoteUtil.remoteCopyFileBySsh(fromCopyPdfPath, toCopyPdfPath);
		model.setMakePdfPath(toCopyPdfPath);
		model.setPdfNm(pdfNm);
		
		return model;
	}
	
	/**
	 * CSV出力情報を取得します。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB015ReportPrintModel createCsvData(TB015ReportPrintModel model) {
		// コンテンツ情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// セキュリティチェックでNGの場合
		executeSecurityCheck(model, userContext);

		// 帳票区分が「入電報告書」の場合
		if (model.isPrintableIncomingCallReport()) {
			// 入電報告書
			RC905NyudenHokokuHyoDto inputDto =
				nyudenHokokuHyoLogic.getNyudenHokokuHyoCsvInfo(model.getToiawaseNo(),
					model.getKokyakuId(), model.getSenderNm1(),
					model.getSenderNm2(), model.getSenderAddress(),
					model.getSenderTelNo(), model.getSenderFaxNo(), null);

			// CSV出力用に変換
			model.setNyudenHokokushoResultList(toCsvByIncomingCallReport(inputDto, userContext));
		// 帳票区分が「作業報告書」の場合
		} else if (model.isPrintableWorkReport()) {
			// 作業報告書
			RC906SagyoHokokuHyoDto inputDto =
				sagyoHokokuHyoLogic.createOutputData(model.getToiawaseNo(),
						model.getKokyakuId(), model.getToiawaseRirekiNo(),
						model.getSenderNm1(), model.getSenderNm2(),
						model.getSenderAddress(), model.getSenderTelNo(),
						model.getSenderFaxNo());

			// CSV出力用に変換
			model.setSagyoHokokushoResultList(toCsvByWorkReport(inputDto));
		// 上記以外の場合は例外
		} else {
			throw new ApplicationException("帳票区分不正。パラメータの帳票区分" );
		}

		return model;
	}
	
	/**
	 * セキュリティチェックを行います。
	 * 
	 * @param model 画面モデル
	 * @param userContext コンテンツ情報
	 * @return 処理結果（true：正常、false：異常）
	 */
	private void  executeSecurityCheck(TB015ReportPrintModel model, TLCSSB2BUserContext userContext){
		// 問い合わせ情報の取得
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		
		// 問い合わせ情報が存在しない場合
		if (toiawase == null){
			throw new ForbiddenException("問い合わせ情報が存在しません。");
		}

		// セッションの権限が「40:委託会社SV」または「41:委託会社OP」の場合
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// 委託関連会社チェックでNGの場合
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), toiawase.getKokyakuId())){
				throw new ForbiddenException("アクセス権限が存在しません。");
			}
		}
	}
	
	/**
	 * 入電報告書CSV出力用にCSV出力情報を変換します。
	 *
	 * @param inputDto 入電報告書CSV出力情報
	 * @return 変換後入電報告書CSV出力情報
	 */
	private List<RC905NyudenHokokuHyoDto> toCsvByIncomingCallReport(RC905NyudenHokokuHyoDto inputDto, TLCSSB2BUserContext userContext) {
		List<RC905NyudenHokokuHyoDto> resultList = new ArrayList<RC905NyudenHokokuHyoDto>();
		// タイトルはテンプレートファイルからデータを取得する為、空データを設定
		RC905NyudenHokokuHyoDto dummyDto = new RC905NyudenHokokuHyoDto();

		// 差出人情報を取得
		String kaishaId;

		if (userContext.isOutsourcerOp() || userContext.isOutsourcerSv()) {
			// セッション情報の権限が委託会社SVまたは委託会社OPの場合、セッションの会社IDをセット
			kaishaId = userContext.getKaishaId();
		} else {
			// セッションのシステム定数MapからＴＯＫＡＩ会社ＩＤの値を取得してセット
			kaishaId = userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_TOKAI_KAISHA_ID);
		}

		inputDto.setSashidashinin(sashidashininDao.selectByPrimaryKey(RcpMSashidashinin.KBN_SAGYO_HOKOKU_GYOSHA_SASHIDASHININ, kaishaId));

		// 1行目：表紙部タイトル行　1行目のタイトル行はCSVDownloadAction.downloadで行っているため不要

		// 2行目： 表示部データ行
		resultList.add(inputDto);

		// 3行目：名細部タイトル行
		resultList.add(dummyDto);

		// 4行目以降：名細部データ行 問い合わせ履歴件数分、行出力
		for (int i = 0 ; i < inputDto.getToiawaseRirekiList().size() ; i++) {
			resultList.add(inputDto);
		}

		return resultList;
	}
	
	/**
	 * 作業報告書CSV出力用にCSV出力情報を変換します。
	 *
	 * @param inputDto 作業報告書CSV出力情報
	 * @return 変換後作業報告書CSV出力情報
	 */
	private List<RC906SagyoHokokuHyoDto> toCsvByWorkReport(RC906SagyoHokokuHyoDto inputDto) {
		List<RC906SagyoHokokuHyoDto> resultList = new ArrayList<RC906SagyoHokokuHyoDto>();

		// 1行目：入電受付報告書部タイトル行　1行目のタイトル行はCSVDownloadAction.downloadで行っているため不要

		// 2行目： 作業内容データ行
		resultList.add(inputDto);

		return resultList;
	}
}
