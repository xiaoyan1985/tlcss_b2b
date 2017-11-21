package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.tokaigroup.reception.common.CsvHeaderMaker;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTIraiDaoImpl;
import jp.co.tokaigroup.reception.dto.RC041IraiSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchModel;
import jp.co.tokaigroup.si.fw.action.CSVDownloadAction;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB031RequestSearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB031RequestSearchService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;


/**
 * 依頼検索ダウンロードアクションクラス。
 *
 * @author H.Hirai
 * @version 1.0 2015/11/12
 * 
 */

@Controller
@Scope("prototype")
@Results({
	@Result(name= SUCCESS, location="tb031_request_search.jsp")
})
public class TB031RequestSearchDownloadAction extends CSVDownloadAction
	implements ModelDriven<TB031RequestSearchModel>{

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB031RequestSearchModel model = new TB031RequestSearchModel();

	/** サービス */
	@Autowired
	private TB031RequestSearchService service;

	/**
	 * CSVをダウンロードします。
	 * 
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("requestSearchDownload")
	public String download() throws Exception {
		
		try{
			// 検索条件取得
			RC041IraiSearchCondition condition = model.getCondition();
			
			// システムマスタ定数Mapから最大検索可能件数を取得
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
			
			// 最大検索可能件数
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_BSB_IRAI_CSV_DOWNLOAD_TO_MAX);

			// オフセットを1にセット
			condition.setOffset(1);

			// 1ページの最大件数を設定
			condition.setLimit(searchMax);
			condition.setMaxCount(searchMax);
			condition.setDisplayToMax(false);

			// 検索条件設定
			if (userContext.isRealEstate()) {
				// セッションの権限が管理会社の場合
				if (userContext.isKokyakuIdSelected()) {
					// 顧客選択済の場合
					// 請求先顧客ＩＤには、セッションの顧客ＩＤを設定
					condition.setSeikyusakiKokyakuId(userContext.getKokyakuId());
					// 親顧客ＩＤには、NULLを設定
					condition.setParentKokyakuId(null);
				} else {
					// それ以外の場合は、NULLを設定
					condition.setSeikyusakiKokyakuId(null);
					// 親顧客ＩＤには、セッションの顧客ＩＤを設定
					condition.setParentKokyakuId(userContext.getKokyakuId());
				}
				condition.setIraiKokaiFlg(RcpTIrai.KOKAI_FLG_KOUKAIZUMI);
				condition.setSearchFlg(RC041IraiSearchCondition.SEARCH_FLG_GAIBU);
			} else {
				// セッションの権限が管理会社以外の場合は、何も設定しない
				condition.setSeikyusakiKokyakuId(null);
				condition.setParentKokyakuId(null);
				condition.setSearchFlg(null);
			}
			// 会社ＩＤを設定
			if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
				condition.setOutsourcerKaishaId(userContext.getKaishaId());
			} else {
				condition.setOutsourcerKaishaId(null);
			}
			condition.setIraiGyosha(userContext.getGyoshaCd());
			condition.setKanrenJoho(RcpTIraiDaoImpl.SEARCH_KANREN_JOHO_TRUE);
			condition.setCsvFlg(RC041IraiSearchModel.CSV_OUT);

			model.setCondition(condition);

			// 検索処理実行
			model = service.executeSearch(model);

			// ダウンロード処理
			super.download(model.getResultList());

		} catch (PagerMaxCountException e) {
			// 検索上限の場合
			addActionError("MSG0006");
			return MESSAGE_AND_CLOSE;

		} catch (Exception e) {
			log.warn("CSVダウンロード失敗。", e);
			addActionError("MSG0040", "CSVダウンロード");
			return MESSAGE_AND_CLOSE;
		}
		return DOWNLOAD;
	}

	/**
	 * リストデータを取得します。
	 *
	 * @return リストデータ
	 */
	protected <T> List<String> getLineData(T line) {

		RC041IraiSearchDto dto = (RC041IraiSearchDto) line;

		List<String>lineList = new ArrayList<String>();

		// 依頼テーブルから出力（１／２）
		lineList.add(dto.getToiawaseNo());

		// 問い合わせテーブルから出力
		lineList.add(DateUtil.formatTimestamp(dto.getUketsukeYmd(), "yyyy/MM/dd"));
		lineList.add(DateUtil.hhmmPlusColon(dto.getUketsukeJikan()));
		if (StringUtils.isNotBlank(dto.getUketsukeshaNm())) {
			lineList.add(dto.getUketsukeshaNm());
		} else {
			lineList.add(concatCodeValue(dto.getUketsukeshaId(), dto.getUketsukeshaDisplayNm()));
		}

		lineList.add(dto.getKokyakuId());

		// 顧客マスタから出力
		if (StringUtils.isNotBlank(dto.getKokyakuKbn())) {
			lineList.add(concatCodeValue(dto.getKokyakuKbn(), dto.getKokyakuKbnNm()));
		} else {
			lineList.add("");
		}
		if (StringUtils.isNotBlank(dto.getKokyakuShubetsu())) {
			lineList.add(concatCodeValue(dto.getKokyakuShubetsu(), dto.getKokyakuShubetsuNm()));
		} else {
			lineList.add("");
		}
		lineList.add(dto.getKanjiNm1());
		lineList.add(dto.getKanjiNm2());
		lineList.add(dto.getJusho1());
		lineList.add(dto.getJusho2());
		lineList.add(dto.getJusho3());

		// 依頼テーブルから出力（２／２）
		lineList.add(dto.getToiawaseRirekiNo());
		if (StringUtils.isNotBlank(dto.getTantoshaNm())) {
			lineList.add(dto.getTantoshaNm());
		} else {
			lineList.add(concatCodeValue(dto.getTantoshaId(), dto.getTantoshaIdNm()));
		}
		if (StringUtils.isNotBlank(dto.getIraiGyoshaCd())) {
			lineList.add(concatCodeValue(dto.getIraiGyoshaCd(), dto.getIraiGyoshaNm()));
		} else {
			lineList.add("");
		}
		lineList.add(dto.getIraiNaiyo());
		lineList.add(DateUtil.formatTimestamp(dto.getHomonKiboYmd(), "yyyy/MM/dd"));
		if (StringUtils.isNotBlank(dto.getHomonKiboJikanKbn())) {
			lineList.add(concatCodeValue(dto.getHomonKiboJikanKbn(), dto.getHomonKiboJikanKbnNm()));
		} else {
			lineList.add("");
		}
		lineList.add(dto.getHomonKiboBiko());
		lineList.add(DateUtil.formatTimestamp(dto.getGyoshaKaitoYmd(), "yyyy/MM/dd"));
		if (StringUtils.isNotBlank(dto.getGyoshaKaitoJikanKbn())) {
			lineList.add(concatCodeValue(dto.getGyoshaKaitoJikanKbn(), dto.getGyoshaKaitoJikanKbnNm()));
		} else {
			lineList.add("");
		}
		lineList.add(dto.getGyoshaKaitoBiko());
		lineList.add(concatCodeValue(dto.getIraiKokaiFlg(), dto.getIraiKokaiFlgNm()));
		if (StringUtils.isNotBlank(dto.getCreNm())) {
			lineList.add(concatCodeValue(dto.getCreId(), dto.getCreNm()));
		} else {
			lineList.add(concatCodeValue(dto.getCreId(), dto.getCreDisplayNm()));
		}
		lineList.add(DateUtil.formatTimestamp(dto.getCreDt()));
		if (StringUtils.isNotBlank(dto.getLastUpdNm())) {
			lineList.add(concatCodeValue(dto.getLastUpdId(), dto.getLastUpdNm()));
		} else {
			lineList.add(concatCodeValue(dto.getLastUpdId(), dto.getLastUpdDisplayNm()));
		}
		lineList.add(DateUtil.formatTimestamp(dto.getUpdDt()));
		lineList.add(concatCodeValue(dto.getCreKaishaId(), dto.getCreKaishaDisplayNm()));
		lineList.add(concatCodeValue(dto.getUpdKaishaId(), dto.getUpdKaishaDisplayNm()));
		if (StringUtils.isNotBlank(dto.getLastPrintNm())) {
			lineList.add(concatCodeValue(dto.getLastPrintId(), dto.getLastPrintNm()));
		} else {
			lineList.add(concatCodeValue(dto.getLastPrintId(), dto.getLastPrintDisplayNm()));
		}

		// 作業状況テーブルから出力
		if (StringUtils.isNotBlank(dto.getSagyoKanryoFlg())) {
			lineList.add(concatCodeValue(dto.getSagyoKanryoFlg(), dto.getSagyoKanryoFlgNm()));
		} else {
			lineList.add("");
		}
		lineList.add(DateUtil.formatTimestamp(dto.getSagyoKanryoYmd(), "yyyy/MM/dd"));
		lineList.add(DateUtil.hhmmPlusColon(dto.getSagyoKanryoJikan()));
		lineList.add(dto.getJokyo());
		lineList.add(dto.getCause());
		lineList.add(dto.getJisshiNaiyo());
		lineList.add(concatCodeValue(dto.getSagyoJokyoKokaiFlg(), dto.getSagyoJokyoKokaiFlgNm()));
		lineList.add(concatCodeValue(dto.getHokokushoKokaiFlg(), dto.getHokokushoKokaiFlgNm()));
		if (StringUtils.isNotBlank(dto.getCreNmSagyoJokyo())) {
			lineList.add(concatCodeValue(dto.getCreIdSagyoJokyo(), dto.getCreNmSagyoJokyo()));
		} else {
			lineList.add(concatCodeValue(dto.getCreIdSagyoJokyo(), dto.getCreDisplayNmSagyoJokyo()));
		}
		lineList.add(DateUtil.formatTimestamp(dto.getCreDtSagyoJokyo()));
		if (StringUtils.isNotBlank(dto.getLastUpdNmSagyoJokyo())) {
			lineList.add(concatCodeValue(dto.getLastUpdIdSagyoJokyo(), dto.getLastUpdNmSagyoJokyo()));
		} else {
			lineList.add(concatCodeValue(dto.getLastUpdIdSagyoJokyo(), dto.getLastUpdDisplayNmSagyoJokyo()));
		}
		lineList.add(DateUtil.formatTimestamp(dto.getUpdDtSagyoJokyo()));

		return lineList;
	}

	/**
	 * CSVファイルオブジェクト取得
	 *
	 * @return ファイルオブジェクト(ファイル名設定)
	 */
	protected String getCsvFileNm(){
		// ファイル名取得
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date1 = new Date();
		return "request_csv" + sdf1.format(date1) + ".csv";
	}

	/**
	 * CSVタイトル取得
	 *
	 * @return CSVタイトル行
	 */
	protected String[] getTitle() {
		try {
			return CsvHeaderMaker.makeIraiCsvHeader();
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 */
	public TB031RequestSearchModel getModel() {
		return model;
	}

	/**
	 * コードと値を半角コロンで連結した値を返却します。
	 *
	 * @param code コード
	 * @param value 値
	 * @return コードと値を半角コロンで連結した値
	 */
	private String concatCodeValue(String code, String value) {
		if (StringUtils.isBlank(code)) {
			return "";
		}

		return code + ":" + StringUtils.defaultString(value);
	}

}
