package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn1Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn2Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn3Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn4Dao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dto.RC031ToiawaseSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB021InquirySearchModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 問い合わせ検索サービス実装クラス。
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 1.1 2015/10/16 v145527
 * @version 1.2 2015/12/24 H.Yamamura パスワードマスタ取得方法変更
 * @version 1.3 2016/06/17 H.Hirai 複数請求先対応
 * @version 1.4 2016/07/11 H.Yamamura 問い合わせ区分１～４のリストを追加
 * @version 1.5 2016/08/22 J.Matsuba 検索条件のサービスが指定されている場合の処理を修正
 * @version 1.6 2016/10/21 H.Yamamura サービス種別の設定方法を変更
 * @version 1.7 2017/09/12 J.Matsuba 請求先顧客情報取得処理の変更、会社略名対応
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB021InquirySearchServiceImpl extends TLCSSB2BBaseService
		implements TB021InquirySearchService {

	/** リセプション問い合わせテーブルDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** リセプション状況区分マスタDAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** NATOSパスワードＭDAO */
	@Autowired
	private NatosMPasswordDao natosPswdDao;

	/** 顧客契約ＭDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;
	
	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** サービスＭDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/** 問い合わせ区分1マスタDAO */
	@Autowired
	private RcpMToiawaseKbn1Dao toiawaseKbn1;

	/** 問い合わせ区分2マスタDAO */
	@Autowired
	private RcpMToiawaseKbn2Dao toiawaseKbn2;

	/** 問い合わせ区分3マスタDAO */
	@Autowired
	private RcpMToiawaseKbn3Dao toiawaseKbn3;

	/** 問い合わせ区分4マスタDAO */
	@Autowired
	private RcpMToiawaseKbn4Dao toiawaseKbn4;
	
	/** 会社マスタDAO */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/** 問い合わせ区分マスタのソート順 */
	private static final String TOIAWASE_KBN_ORDER_BY = " ORDER BY HYOJI_JUN ASC, KBN ASC";

	/**
	 * 初期表示を行います。
	 *
	 * @param model 問い合わせ検索画面モデル
	 * @return 問い合わせ検索画面モデル
	 */
	public TB021InquirySearchModel getInitInfo(TB021InquirySearchModel model) {

		// システムマスタ定数Mapから最大検索可能件数、最大表示件数を取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		//サービス取得
		if (userContext.isInhouse()){
			model.setServiceMEntityList(serviceDao.selectServiceList());
		} else {
			model.setServiceMEntityList(kokyakuKeiyakuDao.selectKokyakuKeiyakuService(userContext.getKokyakuId()));
		}

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_JOKYO,
				RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU);
		
		// 状況リスト
		List<RcpMComCd> jokyoList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_JOKYO);
		model.setJokyoList(jokyoList);
		
		// 閲覧状況リスト
		List<RcpMComCd> browseStatusList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU);
		RcpMComCd all = new RcpMComCd();
		all.setExternalSiteVal("全て");
		all.setComCd("");
		browseStatusList.add(0, all);
		model.setBrowseStatusList(browseStatusList);

		// 問い合わせ区分1リストの取得
		// ソート順を設定
		toiawaseKbn1.setOrderBy(TOIAWASE_KBN_ORDER_BY);
		List<RcpMToiawaseKbn1> toiawaseKbn1List = toiawaseKbn1.selectAll();
		model.setToiawase1List(toiawaseKbn1List);

		// 問い合わせ区分2リストの取得
		// ソート順を設定
		toiawaseKbn2.setOrderBy(TOIAWASE_KBN_ORDER_BY);
		List<RcpMToiawaseKbn2> toiawaseKbn2List = toiawaseKbn2.selectAll();
		model.setToiawase2List(toiawaseKbn2List);

		// 問い合わせ区分3リストの取得
		// ソート順を設定
		toiawaseKbn3.setOrderBy(TOIAWASE_KBN_ORDER_BY);
		List<RcpMToiawaseKbn3> toiawaseKbn3List = toiawaseKbn3.selectAll();
		model.setToiawase3List(toiawaseKbn3List);

		// 問い合わせ区分4リストの取得
		// ソート順を設定
		toiawaseKbn4.setOrderBy(TOIAWASE_KBN_ORDER_BY);
		List<RcpMToiawaseKbn4> toiawaseKbn4List = toiawaseKbn4.selectAll();
		model.setToiawase4List(toiawaseKbn4List);

		return model;
	}

	/**
	 * 検索処理を実行します。
	 *
	 * @param model 問い合わせ検索画面モデル
	 * @param csvDownloadFlg CSVダウンロードフラグ（true：CSVダウンロード）
	 * @return 問い合わせ検索画面モデル
	 */
	public TB021InquirySearchModel executeSearch(TB021InquirySearchModel model, boolean csvDownloadFlg) {

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		//初期表示を行う
		model = getInitInfo(model);
		
		// サービス種別 NULL取得フラグをfalseで初期設定
		model.getCondition().setServiceShubetsuNullFlg(false);

		if (StringUtils.isNotBlank(model.getCondition().getServiceKbn())) {
			// 検索条件のサービスが指定されている場合
			RcpMService service = serviceDao.selectByPrimaryKey(model.getCondition().getServiceKbn());
			List<String> serviceShubetsuList = new ArrayList<String>();

			serviceShubetsuList.add(service.getServiceShubetsu());
			if (RcpMService.SERVICE_KBN_RECEPTION.equals(service.getServiceShubetsu())
				|| RcpMService.SERVICE_KBN_LIFE_SUPPORT24.equals(service.getServiceShubetsu())) {
				// 取得したサービス種別がリセプション　または　ライフサポート２４の場合、不明、NULLを条件に追加
				serviceShubetsuList.add(RcpTToiawase.SERVICE_SHUBETSU_FUMEI);
				model.getCondition().setServiceShubetsuNullFlg(true);
			}
			model.getCondition().setServiceShubetsuList(serviceShubetsuList);
		}

		// 検索処理実行
		List<RC031ToiawaseSearchDto> resultList = toiawaseDao.selectByCondition(model.getCondition());

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_UMU,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_SEX_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI,
				RcpMComCd.RCP_M_COM_CD_KBN_PUBLISH_FLG,
				RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU,
				RcpMComCd.RCP_M_COM_CD_KBN_REGIST_KBN);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 顧客区分Map
		Map<String, RcpMComCd> kokyakuKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		// 顧客種別Map
		Map<String, RcpMComCd> kokyakuShubetsuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// 依頼者区分Map
		Map<String, RcpMComCd> iraishaKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// 依頼有無区分Map
		Map<String, RcpMComCd> iraiUmuKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_UMU);
		// 依頼者フラグMap
		Map<String, RcpMComCd> iraishaFlgMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA);
		// 依頼者性別
		Map<String, RcpMComCd> iraishaSexKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_SEX_KBN);
		// 受付形態Map
		Map<String, RcpMComCd> uketukeKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		// 公開Map
		Map<String, RcpMComCd> koukaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_PUBLISH_FLG);
		// 閲覧状況フラグMap
		Map<String, RcpMComCd> browseStatusMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU);
		// 登録区分Map
		Map<String, RcpMComCd> registKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_REGIST_KBN);

		// 問い合わせ区分Map
		Map<String, String> toiawaseKbn1Map = toiawaseKbn1.selectAllAsMap();
		Map<String, String> toiawaseKbn2Map = toiawaseKbn2.selectAllAsMap();
		Map<String, String> toiawaseKbn3Map = toiawaseKbn3.selectAllAsMap();
		Map<String, String> toiawaseKbn4Map = toiawaseKbn4.selectAllAsMap();

		// 状況区分マスタからの取得
		Map<String, String> jokyoKbnMap;
		if (userContext.isRealEstate()) {
			// 権限が管理会社の場合は外部サイト用
			jokyoKbnMap = jokyoKbnDao.selectAllExtJokyoKbnNmAsMap();
		} else {
			// 権限が管理会社以外は全件取得
			jokyoKbnMap = jokyoKbnDao.selectAllAsMap();
		}
		
		// 外部サイト用完了フラグMapを取得
		Map<String, String> jokyoKbnExtKanryoFlgMap = jokyoKbnDao.selectAllExtKanryoFlgAsMap();

		// パスワードマスタ・業者マスタ検索用のリスト生成
		List<String> userIdList = new ArrayList<String>();
		for (RC031ToiawaseSearchDto dto : resultList) {
			if (StringUtils.isNotBlank(dto.getUketsukeshaId()) &&
				!userIdList.contains(dto.getUketsukeshaId())) {
				// パスワードマスタ検索用のリストに含まれていなければ、リストに追加
				userIdList.add(dto.getUketsukeshaId());
			}
			// 画面最終更新者IDが含まれていなければ、リストに追加
			if (StringUtils.isNotBlank(dto.getGamenLastUpdId())
				&& ! userIdList.contains(dto.getGamenLastUpdId())) {
				userIdList.add(dto.getGamenLastUpdId());
			}
			// 初回登録者IDが含まれていなければ、リストに追加
			if (StringUtils.isNotBlank(dto.getCreId())
				&& ! userIdList.contains(dto.getCreId())) {
				userIdList.add(dto.getCreId());
			}
			// 最終更新者IDが含まれていなければ、リストに追加
			if (StringUtils.isNotBlank(dto.getLastUpdId())
				&& ! userIdList.contains(dto.getLastUpdId())) {
				userIdList.add(dto.getLastUpdId());
			}
		}

		// ユーザMap
		Map<String, String> userMap = new HashMap<String, String>();
		if (!(userIdList == null || userIdList.isEmpty())) {
			// CSVダウンロードの場合
			if (csvDownloadFlg) {
				// パスワードマスタ全件取得
				userMap = natosPswdDao.convertMap(natosPswdDao.selectAll());
			} else {
				// パスワードマスタ取得
				userMap =
					natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));
			}
		}
		
		// 会社Map
		Map<String, String> kaishaMap = kaishaDao.selectAllAsMapForRyakuNm();
		
		// 顧客ＩＤリスト生成
		List<String> kokyakuIdList = new ArrayList<String>();
		for (RC031ToiawaseSearchDto dto : resultList) {
			if (StringUtils.isNotBlank(dto.getKokyakuId()) &&
				!kokyakuIdList.contains(dto.getKokyakuId()) &&
				!RcpMKokyaku.KOKYAKU_KBN_FUDOSAN.equals(dto.getKokyakuKbn())) {
				// 検索結果の顧客ＩＤが空欄でない、かつ、顧客ＩＤリストに格納されていない、
				// かつ、検索結果の顧客区分が不動産・管理会社でない場合に、顧客ＩＤリストに登録
				kokyakuIdList.add(dto.getKokyakuId());
			}

			// 請求先情報取得
			if (StringUtils.isNotBlank(dto.getKokyakuId())) {
				// 検索結果の顧客ＩＤが空欄以外の場合
				RcpMKokyaku seikyusakiKokyaku = kokyakuDao.selectSeikyusakiKokyakuAsToiawaseServiceShubetsu(
						dto.getKokyakuId(), dto.getServiceShubetsu());

				if (seikyusakiKokyaku != null) {
					// 取得した請求先顧客のEntityがNULLでない場合
					dto.setSeikyusakiKokyakuId(seikyusakiKokyaku.getKokyakuId());
					dto.setSeikyusakiKanjiNm1(seikyusakiKokyaku.getKanjiNm1());
					dto.setSeikyusakiKanjiNm2(seikyusakiKokyaku.getKanjiNm2());
				}
			}
		}

		// 和名変換処理
		for (RC031ToiawaseSearchDto dto : resultList) {
	
			// 顧客ＩＤが存在する場合
			if (StringUtils.isNotBlank(dto.getKokyakuId())) {
				// 顧客区分Mapで和名変換
				if (kokyakuKbnMap.containsKey(dto.getKokyakuKbn())) {
					// 顧客区分名
					dto.setKokyakuKbnNm(kokyakuKbnMap.get(dto.getKokyakuKbn()).getExternalSiteVal());
				}
			} else {
				// 依頼者区分Mapで和名変換
				if (iraishaKbnMap.containsKey(dto.getKokyakuKbn())) {
					// 顧客区分名
					dto.setKokyakuKbnNm(iraishaKbnMap.get(dto.getKokyakuKbn()).getExternalSiteVal());
				}
			}
			
			if (kokyakuShubetsuMap.containsKey(dto.getKokyakuShubetsu())) {
				// 顧客種別名
				dto.setKokyakuShubetsuNm(kokyakuShubetsuMap.get(dto.getKokyakuShubetsu()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getIraishaKbn()) &&
				iraishaKbnMap.containsKey(dto.getIraishaKbn())) {
				// 依頼者区分名
				dto.setIraishaKbnNm(iraishaKbnMap.get(dto.getIraishaKbn()).getExternalSiteVal());
			}

			if (iraiUmuKbnMap.containsKey(dto.getIraiUmuKbn())) {
				// 依頼有無区分名
				dto.setIraiUmuKbnNm(iraiUmuKbnMap.get(dto.getIraiUmuKbn()).getExternalSiteVal());
			}

			if (iraishaFlgMap.containsKey(dto.getIraishaFlg())) {
				// 依頼者フラグ名
				dto.setIraishaFlgNm(iraishaFlgMap.get(dto.getIraishaFlg()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getIraishaSexKbn()) &&
					iraishaSexKbnMap.containsKey(dto.getIraishaSexKbn())) {
				// 依頼者性別
				dto.setIraishaSexKbnNm(iraishaSexKbnMap.get(dto.getIraishaSexKbn()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getUketsukeKeitaiKbn()) &&
				uketukeKeitaiMap.containsKey(dto.getUketsukeKeitaiKbn())) {
				// 受付形態名
				dto.setUketsukeKeitaiKbnNm(uketukeKeitaiMap.get(dto.getUketsukeKeitaiKbn()).getExternalSiteVal());
			}

			if (koukaiMap.containsKey(dto.getHokokushoKokaiFlg())) {
				// 報告書公開フラグ名
				dto.setHokokushoKokaiFlgNm(koukaiMap.get(dto.getHokokushoKokaiFlg()).getExternalSiteVal());
			}
			if (koukaiMap.containsKey(dto.getToiawaseKokaiFlg())) {
				// 問い合わせ公開フラグ名
				dto.setToiawaseKokaiFlgNm(koukaiMap.get(dto.getToiawaseKokaiFlg()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getBrowseStatusFlg()) &&
				browseStatusMap.containsKey(dto.getBrowseStatusFlg())) {
				// 閲覧状況区分名
				dto.setBrowseStatusFlgNm(browseStatusMap.get(dto.getBrowseStatusFlg()).getExternalSiteVal());
			} else if (StringUtils.isBlank(dto.getBrowseStatusFlg())) {
				// 閲覧状況区分名を未読で設定
				dto.setBrowseStatusFlgNm(browseStatusMap.get(RcpTToiawase.BROWSE_STATUS_FLG_MIDOKU).getExternalSiteVal());
			}

			if (registKbnMap.containsKey(dto.getRegistKbn())) {
				// 登録区分名
				dto.setRegistKbnNm(registKbnMap.get(dto.getRegistKbn()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getJokyoKbn()) &&
				jokyoKbnMap.containsKey(dto.getJokyoKbn())) {
				// 状況区分名
				dto.setJokyoKbnNm(jokyoKbnMap.get(dto.getJokyoKbn()));
			}

			if (StringUtils.isNotBlank(dto.getJokyoKbn()) &&
				jokyoKbnExtKanryoFlgMap.containsKey(dto.getJokyoKbn())) {
				// 外部サイト用完了フラグの設定
				dto.setJokyoKbnExtKanryoFlg(jokyoKbnExtKanryoFlgMap.get(dto.getJokyoKbn()));
			}

			// 問い合わせ受付者名が取得できた場合
			if (StringUtils.isNotBlank(dto.getToiawaseUketsukeshaNm())) {
				// 受付者名
				dto.setUketsukeshaNm(dto.getToiawaseUketsukeshaNm());
			} else {
				if (StringUtils.isNotBlank(dto.getUketsukeshaId()) &&
					userMap.containsKey(dto.getUketsukeshaId())) {
					// 受付者名
					dto.setUketsukeshaNm(userMap.get(dto.getUketsukeshaId()));
				}
			}

			if (StringUtils.isNotBlank(dto.getToiawaseKbn1()) &&
					toiawaseKbn1Map.containsKey(dto.getToiawaseKbn1())) {
				// 問い合わせ区分1名
				dto.setToiawaseKbn1Nm(toiawaseKbn1Map.get(dto.getToiawaseKbn1()));
			}

			if (StringUtils.isNotBlank(dto.getToiawaseKbn2()) &&
					toiawaseKbn2Map.containsKey(dto.getToiawaseKbn2())) {
				// 問い合わせ区分2
				dto.setToiawaseKbn2Nm(toiawaseKbn2Map.get(dto.getToiawaseKbn2()));
			}

			if (StringUtils.isNotBlank(dto.getToiawaseKbn3()) &&
					toiawaseKbn3Map.containsKey(dto.getToiawaseKbn3())) {
				// 問い合わせ区分3名
				dto.setToiawaseKbn3Nm(toiawaseKbn3Map.get(dto.getToiawaseKbn3()));
			}

			if (StringUtils.isNotBlank(dto.getToiawaseKbn4()) &&
					toiawaseKbn4Map.containsKey(dto.getToiawaseKbn4())) {
				// 問い合わせ区分4名
				dto.setToiawaseKbn4Nm(toiawaseKbn4Map.get(dto.getToiawaseKbn4()));
			}

			// 報告対象フラグ名 
			if (StringUtils.isNotBlank(dto.getHoukokuTargetFlg())) {
				if (RcpTToiawase.HOKOKU_TARGET_FLG_ON.equals(dto.getHoukokuTargetFlg())) {
					dto.setHoukokuTargetFlgNm(RcpTToiawase.HOKOKU_TARGET_FLG_ON_NM);
				} else {
					dto.setHoukokuTargetFlgNm(RcpTToiawase.HOKOKU_TARGET_FLG_OFF_NM);
				}
			}

			if (kaishaMap.containsKey(dto.getCreKaishaId())) {
				// 登録会社名
				dto.setCreKaishaNm(kaishaMap.get(dto.getCreKaishaId()));
			}

			if (kaishaMap.containsKey(dto.getUpdKaishaId())) {
				// 更新会社名
				dto.setUpdKaishaNm(kaishaMap.get(dto.getUpdKaishaId()));
			}
			
			if (userMap.containsKey(dto.getGamenLastUpdId())) {
				// 画面最終更新者名
				dto.setGamenLastUpdNm(userMap.get(dto.getGamenLastUpdId()));
			}

			// 登録者名が取得できた場合
			if (StringUtils.isNotBlank(dto.getCreNm())) {
				// 初回登録者名に取得した登録者名を設定
				dto.setDisplayCreNm(dto.getCreNm());
			} else {
				if (userMap.containsKey(dto.getCreId())) {
					// 初回登録者名
					dto.setDisplayCreNm(userMap.get(dto.getCreId()));
				}
			}

			// 最終更新者名が取得できた場合
			if (StringUtils.isNotBlank(dto.getLastUpdNm())) {
				// 最終更新者和名に取得した最終更新者名を設定
				dto.setDisplayLastUpdNm(dto.getLastUpdNm());
			} else {
				if (userMap.containsKey(dto.getLastUpdId())) {
					// 最終更新者名（和名変換）
					dto.setDisplayLastUpdNm(userMap.get(dto.getLastUpdId()));
				}
			}
			
			//時間表記の修正(hhmm→hh:mm)
			dto.setUketsukeJikanRireki(DateUtil.hhmmPlusColon(dto.getUketsukeJikanRireki()));
			dto.setUketsukeJikan(DateUtil.hhmmPlusColon(dto.getUketsukeJikan()));
		}

		// アクセスログ登録
		tbAccesslogDao.insert(TB021InquirySearchModel.GAMEN_NM,
				Constants.BUTTON_NM_SEARCH, createKensakuJoken(model));

		model.setResultList(resultList);

		return model;
	}

	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model 問い合わせ検索画面モデル
	 * @return アクセスログに登録する検索条件
	 */
	private String createKensakuJoken(TB021InquirySearchModel model) {
		NullExclusionToStringBuilder searchEntry =
			new NullExclusionToStringBuilder(
				model.getCondition(),
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// 除外する項目
		searchEntry.setExcludeFieldNames(Constants.EXCLUDE_FIELD_NAMES);

		return searchEntry.toString();
	}
}
