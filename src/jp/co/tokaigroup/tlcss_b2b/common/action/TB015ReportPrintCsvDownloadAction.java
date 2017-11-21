package jp.co.tokaigroup.tlcss_b2b.common.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.common.CsvHeaderMaker;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dto.RC905NyudenHokokuHyoDto;
import jp.co.tokaigroup.reception.dto.RC906SagyoHokokuHyoDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.si.fw.action.CSVDownloadAction;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.tags.Functions;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.StringUtil;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB015ReportPrintService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 報告書印刷CSVダウンロードアクションクラス。
 *
 * @author k002785
 * @version 1.0 2015/08/07
 * @version 1.1 2016/02/12 H.Yamamura 作業報告書出力時に、問い合わせ受付内容を出力しないよう変更
 */
@Controller
@Scope("prototype")
public class TB015ReportPrintCsvDownloadAction extends CSVDownloadAction
implements ModelDriven<TB015ReportPrintModel>{

	/** 現在の行数：2行目表紙部データ */
	private static final int LINE_NO_COVER_SHEET = 2;

	/** 現在の行数：3行目明細部タイトル */
	private static final int LINE_NO_DETAIL_TITLE = 3;

	/** 現在の行数：4行目明細部データ */
	private static final int LINE_NO_DETAIL_DATA = 4;
	
	/** 区分 6:最大内訳個数 */
	private static final int BREAKDOWN_TO_MAX = 6;
	
	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/** 画面モデル */
	private TB015ReportPrintModel model = new TB015ReportPrintModel();
	
	/** サービス */
	@Autowired
	private TB015ReportPrintService service;
	
	/** 現在の行数 2行目から開始 */
	private int lineNo = LINE_NO_COVER_SHEET;

	/**
	 * CSVダウンロード処理を行います。
	 *
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("reportPrintCsvDownload")
	public String download() throws Exception {
		// セッション情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		try{
			// CSV生成
			model = service.createCsvData(model);
			
			// ダウンロード処理
			super.download(model.getCsvList());
			
			return DOWNLOAD;

		} catch (ForbiddenException e) {
			if (userContext.isIPad()) {
				// iPadの場合は、403エラーを画面に表示
				return FORBIDDEN_ERROR;
			} else {
				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			if (userContext.isIPad()) {
				// iPadの場合は、500エラーを画面に表示
				return ERROR;
			} else {
				// それ以外の場合は、アラートにエラーメッセージを表示
				addActionError("MSG9001");
				return MESSAGE_AND_CLOSE;
			}
		}
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB015ReportPrintModel getModel() {
		return model;
	}

	/**
	 * CSVファイルオブジェクト取得
	 * @return ファイルオブジェクト(ファイル名設定)
	 */
	protected String getCsvFileNm() {
		// ファイル名取得
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date1 = new Date();
		return model.getCsvNm() + sdf1.format(date1) + ".txt";
	}

	/**
	 * リストデータ取得取得
	 *
	 * @return リストデータ
	 */
	protected <T> List<String> getLineData(T line) {
		return model.isPrintableIncomingCallReport() ? getNyudenHokokushoLineData(line) : getSagyoHokokushoLineData(line);
	}
	
	/**
	 * 入電報告書リストデータ取得取得
	 *
	 * @return リストデータ
	 */
	protected <T> List<String> getNyudenHokokushoLineData(T line) {
		RC905NyudenHokokuHyoDto inputDto = (RC905NyudenHokokuHyoDto) line;
		List<String> lineList = new ArrayList<String>();

		if (lineNo == LINE_NO_COVER_SHEET) {
			// 表紙部データ行
			// 入電者の顧客情報取得
			RcpMKokyaku nyudenKokyaku = inputDto.getNyudenKokyaku();
			// 問い合わせ基本情報取得
			RcpTToiawase toiawase = inputDto.getToiawase();

			////////////////////////////////////////////////////////////////////////////
			// 出力用に編集
			////////////////////////////////////////////////////////////////////////////
			// 受付形態
			String uketsukeKetai = inputDto.getKyotsuCodeEntity(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI, toiawase.getUketsukeKeitaiKbn()).getComVal();
			// 受付日時
			String uketsukeYmd = "";
			if (toiawase.getUketsukeYmd() != null) {
				uketsukeYmd = DateUtil.formatTimestamp(toiawase.getUketsukeYmd(), Constants.DATE_FORMAT_YMD);
			}
			String uketsukeTime = "";
			if (StringUtils.isNotBlank(toiawase.getUketsukeJikan())) {
				uketsukeTime = DateUtil.hhmmPlusColon(toiawase.getUketsukeJikan());
			}
			String uketsukeDate = StringUtil.plusSpace(uketsukeYmd, uketsukeTime);
			// 入電担当者
			String nyudenTantosha = inputDto.getNatosMPasswordMap().get(toiawase.getUketsukeshaId());
			// 郵便番号
			String yubinNo = "";
			if (StringUtils.isNotBlank(nyudenKokyaku.getYubinNo())) {
				yubinNo = Functions.formatYubinNo(nyudenKokyaku.getYubinNo());
			}
			// 漢字氏名
			String kanjiNm = StringUtil.plusSpace(nyudenKokyaku.getKanjiNm1(), nyudenKokyaku.getKanjiNm2());
			// 依頼者
			String iraisha = "";
			if (RcpTToiawase.IRAI_FLG_ONAJI.equals(toiawase.getIraishaFlg())) {
				iraisha = inputDto.getKyotsuCodeEntity(RcpMComCd.RCP_M_COM_CD_KBN_DOJO, toiawase.getIraishaFlg()).getComVal();
			} else {
				String iraishaNm = inputDto.getKyotsuCodeEntity(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN, toiawase.getIraishaKbn()).getComVal();
				String iraishaSexKbnNm = inputDto.getKyotsuCodeEntity(
						RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_SEX_KBN,
						toiawase.getIraishaSexKbn()).getComVal();

				if (StringUtils.isNotBlank(iraishaNm) || StringUtils.isNotBlank(iraishaSexKbnNm)) {
					iraishaNm = "(" + StringUtils.defaultString(iraishaNm) + " " + StringUtils.defaultString(iraishaSexKbnNm) + ")";
				}
				iraisha = StringUtil.plusSpace(
						StringUtils.defaultString(toiawase.getIraishaRoomNo()) + "号室",
						toiawase.getIraishaNm(), iraishaNm);
			}
			// 区分
			String toiawasekbn3 = inputDto.getToiawase3Map().get(toiawase.getToiawaseKbn3());
			String toiawasekbn4 = inputDto.getToiawase4Map().get(toiawase.getToiawaseKbn4());
			String toiawaseKbn = StringUtil.plusSpace(toiawasekbn3, toiawasekbn4);

			////////////////////////////////////////////////////////////////////////////
			// データセット
			////////////////////////////////////////////////////////////////////////////
			// 弊社受付番号
			lineList.add(toiawase.getToiawaseNo());
			// 受付形態
			lineList.add(uketsukeKetai);
			// 受付日時
			lineList.add(uketsukeDate);
			// 入電担当者
			lineList.add(nyudenTantosha);
			// 郵便番号
			lineList.add(yubinNo.toString());
			// 住所１
			lineList.add(nyudenKokyaku.getJusho1());
			// 住所２
			lineList.add(nyudenKokyaku.getJusho2());
			// 住所３
			lineList.add(nyudenKokyaku.getJusho3());
			// 住所４
			lineList.add(nyudenKokyaku.getJusho4());
			// 住所５
			String jusho5 = "";
			// 住所５が存在かつ部屋番号が存在する場合
			if (StringUtils.isNotBlank(nyudenKokyaku.getJusho5()) && StringUtils.isNotBlank(nyudenKokyaku.getRoomNo())) {
				jusho5 = nyudenKokyaku.getJusho5() + "　" + nyudenKokyaku.getRoomNo();
			// 住所５のみ存在する場合
			} else if (StringUtils.isNotBlank(nyudenKokyaku.getJusho5())) {
				jusho5 = nyudenKokyaku.getJusho5();
			// 部屋番号のみ存在する場合
			} else if (StringUtils.isNotBlank(nyudenKokyaku.getRoomNo())) {
				jusho5 = nyudenKokyaku.getRoomNo();
			}
			lineList.add(jusho5);
			// 漢字氏名
			lineList.add(kanjiNm);
			// 依頼者
			lineList.add(iraisha);
			// 区分
			lineList.add(toiawaseKbn);
			// 受付内容
			lineList.add(toiawase.getToiawaseNaiyo());
		} else if (lineNo == LINE_NO_DETAIL_TITLE) {
			// 名細部タイトル行
			String[] detailLine = getMeisaiTitle();
			// 見出し出力
			if (detailLine != null && detailLine.length > 0) {
				for (String midasi : detailLine) {
					lineList.add(midasi);
				}
			}
		} else if (lineNo >= LINE_NO_DETAIL_DATA) {
			// 名細部データ行
			// 問い合わせ履歴情報取得
			RcpTToiawaseRireki entitiy = inputDto.getToiawaseRirekiList().get(lineNo - LINE_NO_DETAIL_DATA);

			////////////////////////////////////////////////////////////////////////////
			// 出力用に編集
			////////////////////////////////////////////////////////////////////////////
			// 日時
			String nyudenUketsukeYmd = "";
			if (entitiy.getUketsukeYmd() != null) {
				nyudenUketsukeYmd = DateUtil.formatTimestamp(entitiy.getUketsukeYmd(), Constants.DATE_FORMAT_YMD);
			}
			String nyudenUketsukeTime = "";
			if (StringUtils.isNotBlank(entitiy.getUketsukeJikan())) {
				nyudenUketsukeTime = DateUtil.hhmmPlusColon(entitiy.getUketsukeJikan());
			}
			String nitiji = StringUtil.plusSpace(nyudenUketsukeYmd, nyudenUketsukeTime);
			// 担当者
			String tantosha = "";
			// 担当者名が存在する場合
			if (StringUtils.isNotBlank(entitiy.getTantoshaNm())) {
				tantosha = entitiy.getTantoshaNm();
			} else {
				tantosha = inputDto.getNatosMPasswordMap().get(entitiy.getTantoshaId());
			}
			// 状況
			String jokyo = inputDto.getJoKyoKbnMap().get(entitiy.getJokyoKbn());

			////////////////////////////////////////////////////////////////////////////
			// データセット
			////////////////////////////////////////////////////////////////////////////
			// 日時
			lineList.add(nitiji);
			// 状況
			lineList.add(jokyo);
			// 担当者
			lineList.add(tantosha);
			// 問い合わせ内容
			lineList.add(entitiy.getToiawaseNaiyo());
		}

		// 次の行へ
		lineNo++;

		return lineList;
	}
	
	/**
	 * 作業報告書リストデータ取得取得
	 *
	 * @return リストデータ
	 */
	protected <T> List<String> getSagyoHokokushoLineData(T line) {
		RC906SagyoHokokuHyoDto inputDto = (RC906SagyoHokokuHyoDto) line;
		List<String> lineList = new ArrayList<String>();

		// 弊社受付番号
		lineList.add(inputDto.getToiawaseNo());
		// 受付形態
		lineList.add(inputDto.getUketsukeKeitai());
		// 受付日時
		lineList.add(inputDto.getUketsukeYmd());
		// 入電担当者
		lineList.add(inputDto.getNyudenTantosha());
		// 郵便番号
		lineList.add(inputDto.getYubinNo());
		// 住所
		lineList.add(inputDto.getJusho());
		// 漢字名称
		lineList.add(inputDto.getKanjiNm());
		// 依頼者
		lineList.add(inputDto.getIraisha());
		// 区分
		lineList.add(inputDto.getToiawaseKbn());

		// 作業完了日
		lineList.add(inputDto.getSagyoKanryoYmd());
		// 状況
		lineList.add(inputDto.getSagyoJokyo());
		// 原因
		lineList.add(inputDto.getSagyoCause());
		// 実施内容
		lineList.add(inputDto.getSagyoJisshiNaiyo());

		for(int i = 0; i < BREAKDOWN_TO_MAX; ++i){
			// 内訳内容
			lineList.add(inputDto.getUchiwakeNaiyo()[i]);
			// 数量
			lineList.add(inputDto.getUchiwakeSuryo()[i]);
			// 単価
			lineList.add(inputDto.getUchiwakeRiekiTanka()[i]);
			// 金額
			lineList.add(inputDto.getUchiwakeRiekiShokei()[i]);
		}

		// 合計(税込み)
		lineList.add(inputDto.getGokeiSeikyugaku());
		// 請求先名
		lineList.add(inputDto.getSeikyusakiNm());
		// 請求先住所
		lineList.add(inputDto.getSeikyusakiJusho());
		// 請求先TEL
		lineList.add(inputDto.getSeikyusakiTel());
		// 請求先FAX
		lineList.add(inputDto.getSeikyusakiFax());


		return lineList;
	}
	
	/**
	 * CSV明細部タイトル取得
	 *
	 * @return CSV名細部タイトル行
	 */
	protected String[] getMeisaiTitle() {
		try {
			return CsvHeaderMaker.makeNyudenHokokuHyoMeisaiCsvHeader();
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * CSV表紙部タイトル取得
	 *
	 * @return CSV表紙部タイトル行
	 */
	protected String[] getTitle() {
		try {
			return model.isPrintableIncomingCallReport() ? CsvHeaderMaker.makeNyudenHokokuHyoHyoshiCsvHeader() : CsvHeaderMaker.makeSagyoHokokuHyoCsvHeader();
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}
}
