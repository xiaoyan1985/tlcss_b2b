package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKeiyaku;
import jp.co.tokaigroup.reception.util.FileRemoteUtil;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.dto.TB044ReceptionListPrintDto;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB044ReceptionListPrintLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB044ReceptionListPrintModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 受付一覧印刷サービス実装クラス。
 *
 * @author v140546
 * @version 1.0 2014/07/08
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB044ReceptionListPrintServiceImpl extends TLCSSB2BBaseService
		implements TB044ReceptionListPrintService {

	// プロパティファイルから取得
	/** PDFファイル格納ディレクトリ名 */
	private static final String PDF_PATH = ResourceFactory.getResource().getString("PDF_PATH");
	/** PDF公開ファイルパス */
	private static final String UPLOAD_PATH_RECEPTION_LIST = ResourceFactory.getResource().getString("UPLOAD_PATH_RECEPTION_LIST");

	/** 顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** 顧客契約ＭDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** サービスＭDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** 受付一覧印刷　ロジッククラス */
	@Autowired
	private TB044ReceptionListPrintLogic logic;

	/**
	 * 初期表示を行います。
	 *
	 * @param model 受付一覧印刷画面モデル
	 * @return 受付一覧印刷画面モデル
	 */
	public TB044ReceptionListPrintModel getInitInfo(TB044ReceptionListPrintModel model) {

		// ユーザー情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// セッション権限が社内の場合
		if (userContext.isInhouse()){
			// サービスの取得
			model.setServiceMEntityList(serviceDao.selectServiceList());
		// セッション権限が不動産・管理会社の場合
		} else if (userContext.isRealEstate()) {
			// サービスの取得
			model.setServiceMEntityList(kokyakuKeiyakuDao.selectKokyakuKeiyakuService(userContext.getKokyakuId()));
			// 顧客情報の取得
			// 対象開始日、対象終了日の初期値設定
			getTargetDay(kokyakuDao.selectByPrimaryKey(userContext.getKokyakuId()), model);
		}

		return model;
	}

	/**
	 * 印刷処理を行います。
	 *
	 * @param model 受付一覧印刷画面モデル
	 * @return PDF出力ファイルパス(ファイルコピー先)
	 */
	public String createPdf(TB044ReceptionListPrintModel model){

		// ユーザー情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 印刷対象取得
		// セッション権限が社内の場合
		if (userContext.isInhouse()){
			// サービスの取得
			model.setSeikyusakiKokyakuId(model.getKokyakuId());
		// セッション権限が不動産・管理会社の場合
		} else if (userContext.isRealEstate()) {
			model.setSeikyusakiKokyakuId(userContext.getKokyakuId());
		}

		// アクセスログ登録
		tbAccesslogDao.insert(TB044ReceptionListPrintModel.GAMEN_NM,
				Constants.BUTTON_NM_PRINT, createPrintJoken(model));

		// PDF出力
		// 受付一覧出力データリストの取得
		List<String[]> allDataList = logic.getReceiptListDataList(model.getTargetDtFrom(), model.getTargetDtTo(), model.getSeikyusakiKokyakuId(), model.getServiceKbn());
		// 受付一覧PDFの作成
		String pdfUrl = logic.outputPdf(allDataList);

		// 受付一覧PDFのファイルコピー
		// PDFファイルの名前を取得する
		String pdfFileName = pdfUrl.substring(pdfUrl.lastIndexOf(TB044ReceptionListPrintDto.REPORT_NM));

		// コピー元（PDFのパス）
		String orgFilePath = PDF_PATH + System.getProperty("file.separator") + pdfFileName;
		// コピー先ファイル
		String destFilePath = UPLOAD_PATH_RECEPTION_LIST + System.getProperty("file.separator") + pdfFileName;

		// ファイルコピーコマンド実行(SSH)
		FileRemoteUtil.remoteCopyFileBySsh(orgFilePath, destFilePath);

		return destFilePath;
	}

	/**
	 * 対象開始日、対象終了日の初期値設定を行います。
	 *
	 * @param kokyaku 顧客マスタ情報
	 * @param model 受付一覧印刷情報
	 * @return 受付一覧印刷情報
	 */
	private TB044ReceptionListPrintModel getTargetDay(RcpMKokyaku kokyaku, TB044ReceptionListPrintModel model) {
		// 対象開始日、対象終了日の取得
		Timestamp startDt = null;
		Timestamp endDt = null;

		if (kokyaku.getShimeDay() == null || RcpMKokyakuKeiyaku.LAST_DAY_OF_SHIME_DAY == kokyaku.getShimeDay().intValue()) {
			// 締め日がNULL、または、月末の場合は、月初日～月末を設定
			startDt = DateUtil.getFirstDayOfMonth(DateUtil.formatTimestamp(
					DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
			endDt = DateUtil.getLastDayOfMonth(DateUtil.formatTimestamp(
					DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
		} else {
			// 前月の月末日を取得
			Date lastMonth = DateUtils.addMonths(DateUtil.getSysDate(), -1);
			Integer lastDate = Integer.parseInt(
					DateUtil.formatTimestamp(new Timestamp(
							DateUtil.getLastDayOfMonth(lastMonth.getTime())), "dd"));

			//対象開始日の設定
			if ((kokyaku.getShimeDay().intValue() + 1) > lastDate.intValue()) {
				// 締め日＋１が前月の月末日を超えていた場合、月初日を設定
				startDt = DateUtil.getFirstDayOfMonth(DateUtil.formatTimestamp(
						DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
			} else {
				// それ以外の場合は、前月＋（締め日＋１）を設定
				startDt = DateUtil.toTimestamp(
						DateUtil.formatTimestamp(DateUtil.toSqlTimestamp(lastMonth), "yyyyMM") +
						StringUtils.left(Integer.toString(kokyaku.getShimeDay().intValue() + 1), 2),
						"yyyyMMdd");
			}

			// 今月の月末日を取得
			lastDate = Integer.parseInt(
					DateUtil.formatTimestamp(new Timestamp(
							DateUtil.getLastDayOfMonth(DateUtil.getSysDate().getTime())), "dd"));

			//対象終了日の設定
			if ((kokyaku.getShimeDay().intValue()) > lastDate.intValue()) {
				// 締め日が今月の月末日を超えていた場合、月末日を設定
				endDt = DateUtil.getLastDayOfMonth(DateUtil.formatTimestamp(
						DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
			} else {
				// それ以外の場合は、今月＋（締め日）を設定
				endDt = DateUtil.toTimestamp(
						DateUtil.formatTimestamp(DateUtil.toSqlTimestamp(DateUtil.getSysDate()), "yyyyMM") +
						StringUtils.left(Integer.toString(kokyaku.getShimeDay().intValue()), 2),
						"yyyyMMdd");
			}
		}

		model.setTargetDtFrom(startDt);
		model.setTargetDtTo(endDt);

		return model;
	}

	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param toiawaseNO 問い合わせNO
	 * @param pdfFileName pdfファイルネーム
	 * @return kensakuJoken 変換後CSV出力情報
	 */
	private String createPrintJoken(TB044ReceptionListPrintModel model){
		StringBuilder sb = new StringBuilder();
		sb.append("targetDtFrom=");
		sb.append(DateUtil.formatTimestamp(model.getTargetDtFrom(), "yyyyMMdd"));
		sb.append(",targetDtTo=");
		sb.append(DateUtil.formatTimestamp(model.getTargetDtTo(), "yyyyMMdd"));
		sb.append(",seikyusakiKokyakuId=");
		sb.append(model.getSeikyusakiKokyakuId());
		if (StringUtils.isNotBlank(model.getServiceKbn())){
			sb.append(",serviceKbn=");
			sb.append(model.getServiceKbn());
		}
		String printJoken = new String(sb);
		return printJoken;
	}
}
