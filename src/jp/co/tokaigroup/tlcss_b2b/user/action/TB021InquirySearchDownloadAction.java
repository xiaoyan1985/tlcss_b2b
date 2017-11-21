package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.tokaigroup.reception.common.CsvHeaderMaker;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDaoImpl;
import jp.co.tokaigroup.reception.dto.RC031ToiawaseSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.CSVDownloadAction;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.StringUtil;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB021InquirySearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB021InquirySearchService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;


/**
 * 問い合わせ検索ダウンロードアクションクラス。
 *
 * @author v145527
 * @version 1.0 2015/10/16
 * @version 1.1 2015/11/16 H.Hirai 不具合修正
 * @version 1.2 2015/12/24 H.Yamamura CSVダウンロードフラグの追加
 */

@Controller
@Scope("prototype")
@Results({
	@Result(name= SUCCESS, location="tb021_inquiry_search.jsp")
})
public class TB021InquirySearchDownloadAction extends CSVDownloadAction
	implements ModelDriven<TB021InquirySearchModel>{

	/** ロガー */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB021InquirySearchModel model = new TB021InquirySearchModel();

	/** サービス */
	@Autowired
	private TB021InquirySearchService service;

	/**
	 * CSVをダウンロードします。
	 * 
	 * @return 処理結果
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("inquirySearchDownload")
	public String download() throws Exception {
		
		try{
			// 検索条件取得
			RC031ToiawaseSearchCondition condition = model.getCondition();
			
			// システムマスタ定数Mapから最大検索可能件数を取得
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
			
			// 最大検索可能件数
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_BSB_TOIAWASE_CSV_DOWNLOAD_TO_MAX);

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
				condition.setToiawaseKokaiFlg(RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI);
				condition.setSearchFlg(RC031ToiawaseSearchCondition.SEARCH_FLG_GAIBU);
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
			
			//依頼有無区分に空値(全て)をいれる
			condition.setIraiUmuRdo(RcpTToiawaseDaoImpl.SEARCH_SORT_IRAI_UMU_RDO_ALL);
			// 関連情報を取得しないようにする
			condition.setKanrenJoho(RcpTToiawaseDaoImpl.SEARCH_KANREN_JOHO_TRUE);
			
			model.setCondition(condition);

			// 検索処理実行
			model = service.executeSearch(model, true);

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
		RC031ToiawaseSearchDto dto = (RC031ToiawaseSearchDto) line;

		List<String> lineList = new ArrayList<String>();

		// 問い合わせ基本情報テーブルから出力（その一）
		// 問い合わせＮＯ
		lineList.add(dto.getToiawaseNo());
		// 受付日
		if (dto.getUketsukeYmd() != null) {
			lineList.add(DateUtil.formatTimestamp(dto.getUketsukeYmd(), "yyyy/MM/dd"));
		} else {
			lineList.add("");
		}
		// 受付時間
		if (StringUtils.isNotBlank(dto.getUketsukeJikan())) {
			lineList.add(DateUtil.hhmmPlusColon(dto.getUketsukeJikan()));
		} else {
			lineList.add("");
		}
		// 受付者ＩＤ
		if (StringUtils.isNotBlank(dto.getToiawaseUketsukeshaNm())) {
			lineList.add(dto.getToiawaseUketsukeshaNm());
		} else {
			lineList.add(concatCodeValue(dto.getUketsukeshaId(), dto.getUketsukeshaNm()));
		}
		// 請求先顧客情報出力
		lineList.add(dto.getSeikyusakiKokyakuId());
		lineList.add(StringUtil.plusSpace(dto.getSeikyusakiKanjiNm1(), dto.getSeikyusakiKanjiNm2()));

		lineList.add(dto.getKokyakuId());

		// 顧客マスタから出力
		// 顧客区分
		lineList.add(concatCodeValue(dto.getKokyakuKbn(), dto.getKokyakuKbnNm()));
		// 顧客種別
		if (StringUtils.isNotBlank(dto.getKokyakuShubetsu())) {
			lineList.add(concatCodeValue(dto.getKokyakuShubetsu(), dto.getKokyakuShubetsuNm()));
		} else {
			lineList.add("");
		}
		// 漢字氏名１
		lineList.add(dto.getKanjiNm1());
		// 漢字氏名２
		lineList.add(dto.getKanjiNm2());
		// 住所１
		lineList.add(dto.getJusho1());
		// 住所２
		lineList.add(dto.getJusho2());
		// 住所３
		lineList.add(dto.getJusho3());

		// 問い合わせ基本情報テーブルから出力（その二）
		// 依頼者フラグ
		if (StringUtils.isNotBlank(dto.getIraishaFlg())) {
			lineList.add(concatCodeValue(dto.getIraishaFlg(), dto.getIraishaFlgNm()));
		} else {
			lineList.add("");
		}
		// 依頼者区分
		if (StringUtils.isNotBlank(dto.getIraishaKbn())) {
			lineList.add(concatCodeValue(dto.getIraishaKbn(), dto.getIraishaKbnNm()));
		} else {
			lineList.add("");
		}
		// 依頼者氏名
		lineList.add(dto.getIraishaNm());
		// 依頼者TEL
		lineList.add(dto.getIraishaTel());
		// 依頼者部屋番号
		lineList.add(dto.getIraishaRoomNo());
		// 依頼者性別
		if (StringUtils.isNotBlank(dto.getIraishaSexKbn())) {
			lineList.add(concatCodeValue(dto.getIraishaSexKbn(), dto.getIraishaSexKbnNm()));
		} else {
			lineList.add("");
		}
		// 依頼者メモ
		lineList.add(dto.getIraishaMemo());
		// 問い合わせ区分１
		if (StringUtils.isNotBlank(dto.getToiawaseKbn1())) {
			lineList.add(concatCodeValue(dto.getToiawaseKbn1(), dto.getToiawaseKbn1Nm()));
		} else {
			lineList.add("");
		}
		// 問い合わせ区分２
		if (StringUtils.isNotBlank(dto.getToiawaseKbn2())) {
			lineList.add(concatCodeValue(dto.getToiawaseKbn2(), dto.getToiawaseKbn2Nm()));
		} else {
			lineList.add("");
		}
		// 問い合わせ区分３
		if (StringUtils.isNotBlank(dto.getToiawaseKbn3())) {
			lineList.add(concatCodeValue(dto.getToiawaseKbn3(), dto.getToiawaseKbn3Nm()));
		} else {
			lineList.add("");
		}
		// 問い合わせ区分４
		if (StringUtils.isNotBlank(dto.getToiawaseKbn4())) {
			lineList.add(concatCodeValue(dto.getToiawaseKbn4(), dto.getToiawaseKbn4Nm()));
		} else {
			lineList.add("");
		}
		// 受付形態
		if (StringUtils.isNotBlank(dto.getUketsukeKeitaiKbn())) {
			lineList.add(concatCodeValue(dto.getUketsukeKeitaiKbn(), dto.getUketsukeKeitaiKbnNm()));
		} else {
			lineList.add("");
		}
		// 問い合わせ内容（簡易）
		lineList.add(dto.getToiawaseNaiyoSimple());
		// 問い合わせ内容
		lineList.add(dto.getToiawaseNaiyo());
		// 依頼者有無区分
		lineList.add(concatCodeValue(dto.getIraiUmuKbn(), dto.getIraiUmuKbnNm()));

		// 問い合わせ履歴テーブルから出力
		// 最終状況区分
		if (StringUtils.isNotBlank(dto.getJokyoKbn())) {
			lineList.add(concatCodeValue(dto.getJokyoKbn(), dto.getJokyoKbnNm()));
		} else {
			lineList.add("");
		}
		// 最終履歴日付
		if (dto.getUketsukeYmdRireki() != null) {
			lineList.add(DateUtil.formatTimestamp(dto.getUketsukeYmdRireki(), "yyyy/MM/dd"));
		} else {
			lineList.add("");
		}
		// 最終履歴時間
		lineList.add(DateUtil.hhmmPlusColon(dto.getUketsukeJikanRireki()));

		// 問い合わせ基本情報テーブルから出力（その三）
		// 締め年月
		lineList.add(DateUtil.yyyymmPlusSlash(dto.getShimeYm()));
		// コール数請求年月、報告対象フラグ、画面最終更新者ＩＤ追加 2013/11/27
		lineList.add(DateUtil.yyyymmPlusSlash(dto.getCallSeikyuYm()));
		lineList.add(concatCodeValue(dto.getHoukokuTargetFlg(), dto.getHoukokuTargetFlgNm()));
		if (StringUtils.isNotBlank(dto.getGamenLastUpdId())) {
			lineList.add(concatCodeValue(dto.getGamenLastUpdId(), dto.getGamenLastUpdNm()));
		} else {
			lineList.add("");
		}
		// 画面更新日
		lineList.add(DateUtil.formatTimestamp(dto.getGamenUpdDt()));
		// 問い合わせ公開フラグ
		lineList.add(concatCodeValue(dto.getToiawaseKokaiFlg(), dto.getToiawaseKokaiFlgNm()));
		// 報告書公開フラグ
		lineList.add(concatCodeValue(dto.getHokokushoKokaiFlg(), dto.getHokokushoKokaiFlgNm()));
		// 初回登録者ＩＤ 2015/10/16 NULL、NOTNULLの場合追加
		if (StringUtils.isNotBlank(dto.getCreNm())) {
			lineList.add(concatCodeValue(dto.getCreId(), dto.getCreNm()));
		} else {
			lineList.add(concatCodeValue(dto.getCreId(), dto.getDisplayCreNm()));
		}
		// 登録日
		lineList.add(DateUtil.formatTimestamp(dto.getCreDt()));
		// 最終更新者ＩＤ 2015/10/16 NULL、NOTNULLの場合追加
		if (StringUtils.isNotBlank(dto.getLastUpdNm())) {
			lineList.add(concatCodeValue(dto.getLastUpdId(), dto.getLastUpdNm()));
		} else {
			lineList.add(concatCodeValue(dto.getLastUpdId(), dto.getDisplayLastUpdNm()));
		}
		// 更新日
		lineList.add(DateUtil.formatTimestamp(dto.getUpdDt()));
		// 外部連携 作業報告書ファイル名 2015/10/16追加
		lineList.add(dto.getExtHokokushoFileNm());
		// 登録会社ＩＤ 2015/10/16追加
		lineList.add(concatCodeValue(dto.getCreKaishaId(), dto.getCreKaishaNm()));
		// 最終更新会社ＩＤ 2015/10/16追加
		lineList.add(concatCodeValue(dto.getUpdKaishaId(), dto.getUpdKaishaNm()));
		// 登録区分 2015/10/16追加
		lineList.add(concatCodeValue(dto.getRegistKbn(), dto.getRegistKbnNm()));

		return lineList;
	}

	/**
	 * CSVファイルオブジェクトを取得します。
	 * 
	 * @return CSVファイルオブジェクト（ファイル名設定）
	 */
	protected String getCsvFileNm() {
		// フォーマットの設定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		// システム時刻の取得
		Date date = new Date();
		
		return "inquiry_csv" + sdf.format(date) + ".csv";
	}

	/**
	 * CSVタイトルを取得します。
	 * 
	 * @return CSVタイトル行
	 */
	protected String[] getTitle() {
		try {
			return CsvHeaderMaker.makeToiawaseCsvHeader();

		} catch (IOException e) {
			throw new ApplicationException(e);
		}
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

	/**
	 * 画面モデルを返します。
	 * 
	 * @return 問い合わせ検索画面モデル
	 */
	public TB021InquirySearchModel getModel() {
		return model;
	}

}
