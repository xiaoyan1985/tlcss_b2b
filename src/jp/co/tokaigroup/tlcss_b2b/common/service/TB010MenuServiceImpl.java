package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.ClosingProcessQuery;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.TbMHolidayDao;
import jp.co.tokaigroup.reception.dao.TbMItakuRefKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbMRefKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMRoleUrlDao;
import jp.co.tokaigroup.reception.dao.TbTInformationDao;
import jp.co.tokaigroup.reception.dao.TbTPublishFileDao;
import jp.co.tokaigroup.reception.dto.InquiryStatus;
import jp.co.tokaigroup.reception.dto.RC014KeiyakuListDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKeiyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.TbMHoliday;
import jp.co.tokaigroup.reception.entity.TbMItakuRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.reception.entity.TbTPublishFile;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.CommonUtil;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010DocumentFileDownloadModel;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010MenuModel;
import jp.co.tokaigroup.tlcss_b2b.dto.TB010InquiryStatusDto;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * メニューサービス実装クラス。
 *
 * @author k002849
 * @version 4.0 2014/06/05
 * @version 4.1 2015/10/28 C.Kobayashi 委託関連会社チェックの追加
 * @version 4.2 2016/09/13 J.Matsuba 管理会社ユーザのメニュー表示処理修正
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB010MenuServiceImpl extends TLCSSB2BBaseService implements TB010MenuService {

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** リセプション顧客契約マスタDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** リセプション問い合わせテーブルDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** リセプション依頼テーブルDAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/** リセプション業者マスタDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	@Autowired
	/** 締め処理クエリ */
	private ClosingProcessQuery closingProcessQuery;

	/** 外部サイトシステム お知らせテーブルDAO */
	@Autowired
	private TbTInformationDao informationDao;

	/** 外部サイトシステム 祝日マスタDAO */
	@Autowired
	private TbMHolidayDao holidayDao;

	/** リセプションサービスマスタDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 公開ファイルテーブルDAO（外部サイト用） */
	@Autowired
	private TbTPublishFileDao publishFileDao;

	/** リセプション 参照顧客マスタ */
	@Autowired
	private TbMRefKokyakuDao refKokyakuDao;

	/** 外部サイトシステム アクセス可能ＵＲＬマスタDAO */
	@Autowired
	private TbMRoleUrlDao roleUrlDao;

	/** 会社マスタDAO */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/** 委託会社参照顧客マスタDAO */
	@Autowired
	private TbMItakuRefKokyakuDao itakuRefKokyakuDao;
	
	/** 委託会社関連チェックロジック */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	
	
	/**
	 * 初期表示処理を行います。
	 *
	 * @param model メニュー画面モデル
	 * @return メニュー画面モデル
	 */
	public TB010MenuModel getInitInfo(TB010MenuModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// お知らせリストの取得
		List<TbTInformation> informationList =
			informationDao.getInformationList();

		if (userContext.isRealEstate()) {
			// セッションの権限が「20:不動産・管理会社」の場合
			// 顧客マスタ情報取得
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(userContext.getKokyakuId());
			if (kokyaku == null) {
				// 顧客マスタ情報がない場合は、エラー
				throw new ApplicationException("顧客マスタ情報が取得できません。");
			}
			model.setKokyaku(kokyaku);
			if (userContext.isKokyakuIdSelected()) {
				// セッションの顧客ＩＤ選択済が選択済の場合
				model = getRealEstateInfo(model);
			} else {
				model = getRealEstateInfoForKokyakuSelect(model);
			}

			// 委託会社参照顧客情報リストを取得し、設定
			List<TbMItakuRefKokyaku> itakuRefKokyakuList = itakuRefKokyakuDao.selectBy(null, userContext.getKokyakuId());
			model.setItakuRefKokyakuList(itakuRefKokyakuList);

			// 再表示タイマー時間を取得し設定（分→ミリ秒に変換）
			int reloadTime = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_MENU_RELOAD_TIME) * 60000;
			model.setReloadTime(String.valueOf(reloadTime));
		} else if (userContext.isConstractor()) {
			// セッションの権限が「30:依頼業者」の場合
			model = getConstractInfo(model);
		} else if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// セッションの権限が「40:委託会社SV」「41:委託会社OP」の場合
			TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(userContext.getKaishaId());
			if (kaisha == null) {
				throw new ApplicationException("会社マスタ情報が取得できません。");
			}
			model.setKaisha(kaisha);
		}

		model.setInfomationList(informationList);

		model.setUserContext(userContext);

		return model;
	}

	/**
	 * ユーザーが不動産・管理会社の場合に表示する情報を取得します。
	 *
	 * @param model メニュー画面モデル
	 * @return メニュー画面モデル
	 */
	private TB010MenuModel getRealEstateInfo(TB010MenuModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND,
				RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE,
				RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE,
				RcpMComCd.RCP_M_COM_CD_KEIYAKU_KEITAI,
				RcpMComCd.RCP_M_COM_CD_JISSHI_KAISU);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 回線種類Map
		Map<String, RcpMComCd> lineKindMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND);
		// 基本料金区分Map
		Map<String, RcpMComCd> basicPriceMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE);
		// 時間帯Map
		Map<String, RcpMComCd> timeZoneMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE);
		// 契約形態Map
		Map<String, RcpMComCd> keiyakuKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KEIYAKU_KEITAI);
		// 実施回数Map
		Map<String, RcpMComCd> jisshiKaisuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_JISSHI_KAISU);

		// サービスマスタ和名変換用Map取得
		List<RcpMService> serviceList = serviceDao.selectAll();
		Map<String, String> serviceMap = serviceDao.convertMap(serviceList);

		// 集計開始日、集計終了日の設定
		Timestamp summaryStartDt = getSummaryStartDt(model.getKokyaku());
		Timestamp summaryEndDt = DateUtil.toTimestamp(
				DateUtil.getSysDateString("yyyyMMdd"), "yyyyMMdd");

		model.setSummaryStartDt(summaryStartDt);
		model.setSummaryEndDt(summaryEndDt);


		// 契約情報リストの取得
		List<RC014KeiyakuListDto> keiyakuList =
			kokyakuKeiyakuDao.selectKeiyakuListByMenu(userContext.getKokyakuId());

		// 契約情報リスト（ビル管理）の取得
		List<RC014KeiyakuListDto> keiyakuBuildingList =
			kokyakuKeiyakuDao.selectKeiyakuListForBuildingByMenu(userContext.getKokyakuId());

		if (keiyakuList == null) {
			// 契約情報リストがNULLの場合
			keiyakuList = new ArrayList<RC014KeiyakuListDto>();
		}

		// 契約情報リストに契約情報リスト（ビル管理）を加える
		for (RC014KeiyakuListDto dto : keiyakuBuildingList) {
			int count = 0;

			for (RC014KeiyakuListDto dto2 : keiyakuList) {
				if (Integer.parseInt(dto2.getHyojiJun().toString()) > Integer.parseInt(dto.getHyojiJun().toString())) {
					break;
				}
				count++;
			}

			keiyakuList.add(count, dto);
		}

		List<TB010InquiryStatusDto> inquiryStatusList =
			new ArrayList<TB010InquiryStatusDto>();

		Long allSumIncompleteCount = new Long(0);
		Long allSumCompletedCount = new Long(0);

		for (RC014KeiyakuListDto dto : keiyakuList) {
			// 和名変換処理
			if (StringUtils.isNotBlank(dto.getKaisenCd()) &&
				lineKindMap.containsKey(dto.getKaisenCd())) {
				// 回線種類
				dto.setKaisenCdNm(lineKindMap.get(dto.getKaisenCd()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(dto.getKihonRyokinKbn()) &&
				basicPriceMap.containsKey(dto.getKihonRyokinKbn())) {
				// 基本料金区分
				dto.setKihonRyokinKbnNm(basicPriceMap.get(dto.getKihonRyokinKbn()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(dto.getJikanCd()) &&
				timeZoneMap.containsKey(dto.getJikanCd())) {
				// 時間帯
				dto.setJikanCdNm(timeZoneMap.get(dto.getJikanCd()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(dto.getKeiyakuType()) &&
					keiyakuKeitaiMap.containsKey(dto.getKeiyakuType())) {
				// 契約形態
				dto.setKeiyakuTypeNm(keiyakuKeitaiMap.get(dto.getKeiyakuType()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(dto.getJisshiKaisu()) &&
					jisshiKaisuMap.containsKey(dto.getJisshiKaisu())) {
				// 実施回数
				dto.setJisshiKaisuNm(jisshiKaisuMap.get(dto.getJisshiKaisu()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(dto.getServiceKbn()) &&
				serviceMap.containsKey(dto.getServiceKbn())) {
				// サービス区分
				dto.setServiceKbnNm(serviceMap.get(dto.getServiceKbn()));
			}

			TB010InquiryStatusDto inquiryStatusDto = new TB010InquiryStatusDto();

			// オブジェクトをコピー
			CommonUtil.copyProperties(dto, inquiryStatusDto);

			// 対応中件数、対応済件数の取得
			List<InquiryStatus> completedList = new ArrayList<InquiryStatus>();

			completedList = toiawaseDao.countInquiryStatus(
					InquiryStatus.EXT_SEARCH_TYPE_SERVICE,
					userContext.getKokyakuId(),
					dto.getKeiyakuNo(),
					summaryStartDt,
					summaryEndDt,
					dto.getServiceKbn(),
					null,
					dto.getServiceShubetsu());

			long sumIncomplete = 0L;
			long sumCompleted = 0L;
			for (InquiryStatus status : completedList) {

				if (status.isIncomplete()) {
					// 対応中の合計値
					sumIncomplete += status.getCount().longValue();
				} else if (status.isCompleted()) {
					// 対応済の合計値
					sumCompleted += status.getCount().longValue();
				}
			}

			inquiryStatusDto.setInCompleteCount(new BigDecimal(sumIncomplete));
			inquiryStatusDto.setCompletedCount(new BigDecimal(sumCompleted));

			allSumIncompleteCount = allSumIncompleteCount + sumIncomplete;
			allSumCompletedCount = allSumCompletedCount + sumCompleted;

			if (dto.isReception()) {
				// サービスがリセプションの場合
				RcpMKokyakuKeiyaku keiyaku =
					closingProcessQuery.countKosuOnContractTarget(
							userContext.getKokyakuId(), dto.getKeiyakuNo());

				if (keiyaku != null) {
					// 契約件数が１件以上の場合
					inquiryStatusDto.setKeiyakuCount(keiyaku.getKokyakuIdCount());
					inquiryStatusDto.setKosuCount(keiyaku.getKakinKeiyakuKosu());
				} else {
					// 契約件数が０件の場合
					inquiryStatusDto.setKeiyakuCount(BigDecimal.ZERO);
					inquiryStatusDto.setKosuCount(BigDecimal.ZERO);
				}
			} else if (dto.isLifeSupport24()) {
				// サービスがライフサポート２４の場合
				BigDecimal keiyakuCount =
					closingProcessQuery.selectConstractCountOnContractTarget(
							userContext.getKokyakuId(), dto.getServiceKbn());

				inquiryStatusDto.setKeiyakuCount(keiyakuCount);
			}

			inquiryStatusList.add(inquiryStatusDto);
		}

		model.setSumIncompleteCount(new BigDecimal(allSumIncompleteCount));
		model.setSumCompleteCount(new BigDecimal(allSumCompletedCount));

		// 問い合わせテーブルから未読件数を取得
		model.setUnreadCount(toiawaseDao.selectUnreadCount(userContext.getKokyakuId(), summaryStartDt, summaryEndDt));

		// 受付履歴一覧の取得
		List<InquiryStatus> inquiryHistoryList = toiawaseDao.countInquiryStatus(
						InquiryStatus.EXT_SEARCH_TYPE_UKETSUKE,
						userContext.getKokyakuId(),
						null,
						DateUtil.toSqlTimestamp(DateUtils.truncate(
								DateUtils.addYears(DateUtil.getSysDate(), -1), Calendar.DAY_OF_MONTH)),
						DateUtil.toSqlTimestamp(DateUtils.truncate(
								DateUtils.addYears(DateUtil.getSysDate(), +1), Calendar.DAY_OF_MONTH)),
						null,
						null,
						null);

		// 祝日一覧の取得
		List<TbMHoliday> holidayList = holidayDao.selectAll();

		model.setInqueryStatusList(inquiryStatusList);
		model.setInquiryHistoryList(inquiryHistoryList);
		model.setHolidayList(holidayList);

		// 文書ライブラリの取得
		model.setPublishFileList(publishFileDao.selectBy(userContext.getKokyakuId(), null));

		return model;
	}

	/**
	 * ユーザーが不動産・管理会社かつ複数顧客の場合に表示する情報を取得します。
	 *
	 * @param model メニュー画面モデル
	 * @return メニュー画面モデル
	 */
	private TB010MenuModel getRealEstateInfoForKokyakuSelect(TB010MenuModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 画面に返す問い合わせ状況リストを設定します。
		List<TB010InquiryStatusDto> inquiryStatusDtoList = new ArrayList<TB010InquiryStatusDto>();
		Long sumIncomplete = new Long(0);
		Long sumCompleted = new Long(0);

		// 参照顧客マスタリストを取得する。
		List<TbMRefKokyaku> refKokyakuList = refKokyakuDao.selectBy(userContext.getRefKokyakuId(), null);

		for (TbMRefKokyaku refKokyaku : refKokyakuList) {
			// 顧客マスタEntityを取得する。
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(refKokyaku.getKokyakuId());

			if (!kokyaku.isKeiyakuKanriEndFlgExpired()) {
			// 契約管理終了フラグが「2：終了、保持期間経過」以外のもののみ表示する。

				// 問い合わせ状況DTOを設定する。
				TB010InquiryStatusDto inquiryStatusDto = new TB010InquiryStatusDto();

				// 顧客契約マスタリストを取得する。
				inquiryStatusDto.setKokyakuKeiyakuList(kokyakuKeiyakuDao.selectSeikyumotoKokyakuId(refKokyaku.getKokyakuId()));

				// 件数を取得する。
				List<InquiryStatus> inquiryHistoryList = new ArrayList<InquiryStatus>();
				if (TbMRefKokyaku.LEAF_FLG_NOT_LEAF.equals(refKokyaku.getLeafFlg())) {
					// 末端でない場合
					if (refKokyaku.isLowerDisplay()) {
						// 下位表示の場合
						// 管理会社一覧を取得する
						inquiryHistoryList = toiawaseDao.countInquiryStatus(
							InquiryStatus.EXT_SEARCH_TYPE_TOIAWASE,
							refKokyaku.getKokyakuId(),
							null,
							DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getFirstDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
							DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getLastDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
							null,
							null,
							null);
					} else {
						// 下位非表示の場合
						// 管理会社一覧を取得する
						inquiryHistoryList = toiawaseDao.countInquiryStatus(
							InquiryStatus.EXT_SEARCH_TYPE_SUBTOTAL,
							refKokyaku.getKokyakuId(),
							null,
							DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getFirstDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
							DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getLastDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
							null,
							null,
							null);
					}
				} else {
					// 末端の場合
					// 管理会社一覧を取得する
					inquiryHistoryList = toiawaseDao.countInquiryStatus(
						InquiryStatus.EXT_SEARCH_TYPE_TOIAWASE,
						refKokyaku.getKokyakuId(),
						null,
						DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getFirstDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
						DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getLastDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
						null,
						null,
						null);
				}

				inquiryStatusDto.setSeikyusakiKokyakuId(refKokyaku.getKokyakuId());
				inquiryStatusDto.setKanjiNm(kokyaku.getKanjiNm());
				inquiryStatusDto.setRefKokyaku(refKokyaku);

				for (InquiryStatus status : inquiryHistoryList) {
					if (status.isIncomplete()) {
						// 対応中の場合
						inquiryStatusDto.setInCompleteCount(status.getCount());
						if (!refKokyaku.isLowerDisplay() || inquiryStatusDto.hasKeiyaku()) {
							// 対応中の合計値
							sumIncomplete += status.getCount().longValue();
						}
					} else if (status.isCompleted()) {
						// 対応済の場合
						inquiryStatusDto.setCompletedCount(status.getCount());
						if (!refKokyaku.isLowerDisplay() || inquiryStatusDto.hasKeiyaku()) {
							// 対応済の合計値
							sumCompleted += status.getCount().longValue();
						}
					}
				}

				// 対応済が０件の場合
				if (inquiryStatusDto.getCompletedCount() == null) {
					inquiryStatusDto.setCompletedCount(BigDecimal.ZERO);
				}
				// 対応中が０件の場合
				if (inquiryStatusDto.getInCompleteCount() == null) {
					inquiryStatusDto.setInCompleteCount(BigDecimal.ZERO);
				}

				inquiryStatusDtoList.add(inquiryStatusDto);
			}
		}
		
		// 受付履歴一覧の取得
		List<InquiryStatus> inquiryHistoryList = toiawaseDao.countInquiryStatus(
						InquiryStatus.EXT_SEARCH_TYPE_UKETSUKE,
						null,
						null,
						DateUtil.toSqlTimestamp(DateUtils.truncate(
								DateUtils.addYears(DateUtil.getSysDate(), -1), Calendar.DAY_OF_MONTH)),
						DateUtil.toSqlTimestamp(DateUtils.truncate(
								DateUtils.addYears(DateUtil.getSysDate(), +1), Calendar.DAY_OF_MONTH)),
						null,
						userContext.getKokyakuId(),
						null);

		// 祝日一覧の取得
		List<TbMHoliday> holidayList = holidayDao.selectAll();

		// 文書ライブラリ情報の取得
		List<TbTPublishFile> publishFileList =
			publishFileDao.selectBy(userContext.getRefKokyakuId(), null);

		model.setSummaryStartDt(DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getFirstDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"));
		model.setSummaryEndDt(DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getLastDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"));
		model.setSumIncompleteCount(new BigDecimal(sumIncomplete));
		model.setSumCompleteCount(new BigDecimal(sumCompleted));
		model.setInqueryStatusList(inquiryStatusDtoList);
		model.setPublishFileList(publishFileList);

		model.setInquiryHistoryList(inquiryHistoryList);
		model.setHolidayList(holidayList);
		
		return model;
	}

	/**
	 * ユーザーが依頼業者の場合に表示する情報を取得します。
	 *
	 * @param model メニュー画面モデル
	 * @return メニュー画面モデル
	 */
	private TB010MenuModel getConstractInfo(TB010MenuModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI,
				RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 訪問予定区分Map
		Map<String, RcpMComCd> homonYoteiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		// 受付形態Map
		Map<String, RcpMComCd> uketsukeKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);

		// 対応中案件一覧を取得
		List<RcpTIrai> iraiList =
			iraiDao.selectForOnGoinigRequests(userContext.getGyoshaCd());

		for (int i = 0; i < iraiList.size(); i++) {
			RcpTIrai irai = iraiList.get(i);

			if (StringUtils.isNotBlank(irai.getHomonKiboJikanKbn()) &&
				homonYoteiMap.containsKey(irai.getHomonKiboJikanKbn())) {
				// 訪問予定区分
				irai.setHomonKiboJikanKbnNm(homonYoteiMap.get(
						irai.getHomonKiboJikanKbn()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(irai.getUketsukeKeitaiKbn()) &&
				uketsukeKeitaiMap.containsKey(irai.getUketsukeKeitaiKbn())) {
				// 受付形態
				irai.setUketsukeKeitaiKbnNm(uketsukeKeitaiMap.get(
						irai.getUketsukeKeitaiKbn()).getExternalSiteVal());
			}

			iraiList.set(i, irai);
		}

		// 業者情報を取得
		RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(userContext.getGyoshaCd());

		model.setRequestList(iraiList);
		model.setGyosha(gyosha);

		return model;
	}

	/**
	 * アクセス可能ＵＲＬMap取得処理を行います。
	 *
	 * @param role 権限
	 * @return アクセス可能ＵＲＬMap
	 */
	public Map<String,TbMRoleUrl> getAccessUrl(String role) {
		// アクセス可能ＵＲＬマスタからアクセス可能ＵＲＬMap取得
		return (roleUrlDao.selectAccessibleMap(role));
	}

	/**
	 * 集計開始日を取得します。
	 *
	 * @param kokyaku 顧客マスタ情報
	 * @return 集計開始日
	 */
	private Timestamp getSummaryStartDt(RcpMKokyaku kokyaku) {
		// 集計開始日の取得
		Timestamp summaryStartDt = null;

		// システム日
		BigDecimal today =
			new BigDecimal(DateUtil.formatTimestamp(DateUtil.getSysDateTime(), "dd"));

		if (kokyaku.getShimeDay() == null || RcpMKokyakuKeiyaku.LAST_DAY_OF_SHIME_DAY == kokyaku.getShimeDay().intValue()) {
			// 締め日がNULL、または、月末の場合は、月初日を設定
			summaryStartDt = DateUtil.getFirstDayOfMonth(DateUtil.formatTimestamp(
					DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
		} else if (today.compareTo(kokyaku.getShimeDay()) <= 0) {
			// システム日＜＝締め日の場合
			Date lastMonth = DateUtils.addMonths(DateUtil.getSysDate(), -1);

			// 月末日を取得
			Integer lastDate = Integer.parseInt(
					DateUtil.formatTimestamp(new Timestamp(
							DateUtil.getLastDayOfMonth(lastMonth.getTime())), "dd"));
			if ((kokyaku.getShimeDay().intValue() + 1) > lastDate.intValue()) {
				// 締め日＋１が月末日を超えていた場合、月初日を設定
				summaryStartDt = DateUtil.getFirstDayOfMonth(DateUtil.formatTimestamp(
						DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
			} else {
				// それ以外の場合は、前月＋（締め日＋１）を設定
				summaryStartDt = DateUtil.toTimestamp(
						DateUtil.formatTimestamp(DateUtil.toSqlTimestamp(lastMonth), "yyyyMM") +
						StringUtils.left(Integer.toString(kokyaku.getShimeDay().intValue() + 1), 2),
						"yyyyMMdd");
			}
		} else {
			// それ以外（システム日＞締め日）の場合

			// 月末日を取得
			Integer lastDate = Integer.parseInt(
					DateUtil.formatTimestamp(new Timestamp(
							DateUtil.getLastDayOfMonth(DateUtil.getSysDateTime().getTime())), "dd"));

			if ((kokyaku.getShimeDay().intValue() + 1) > lastDate.intValue()) {
				// 締め日＋１が月末日を超えていた場合、月初日を設定
				summaryStartDt = DateUtil.getFirstDayOfMonth(DateUtil.formatTimestamp(
						DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
			} else {
				// それ以外の場合は、今月＋（締め日＋１）を設定
				summaryStartDt = DateUtil.toTimestamp(
						DateUtil.formatTimestamp(DateUtil.getSysDateTime(), "yyyyMM") +
						StringUtils.left(Integer.toString(kokyaku.getShimeDay().intValue() + 1), 2),
						"yyyyMMdd");
			}
		}

		return summaryStartDt;
	}
	
	/**
	 * 顧客マスタから顧客情報を取得します。
	 * 
	 * @param selectedKokyakuId 選択した顧客情報
	 * @return 顧客マスタ情報
	 */
	public RcpMKokyaku getKokyakuInfo(String selectedKokyakuId) {
		// 顧客マスタから顧客情報取得
		RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(selectedKokyakuId);
		if (kokyaku == null) {
			// 取得できなかった場合は、システムエラー
			throw new ApplicationException("顧客マスタ情報が取得できません。 顧客ＩＤ=" + selectedKokyakuId);
		}
		
		return kokyaku;
	}
	
	/**
	 * 委託会社関連チェックを行います。
	 *
	 * @param modelメニューダウンロード画面モデル
	 * @return チェック結果 true:チェックOK
	 */
	public boolean isValidDocumentDownload(TB010DocumentFileDownloadModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// セッションの権限が「40:委託会社SV」または「41:委託会社OP」の場合
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			return outsourcerValidationLogic.isValid(userContext.getKaishaId(), model.getRealFileNm().substring(0,10));
		} else {
			return true;
		}
	}
}
