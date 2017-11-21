package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTSeikyuDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dto.RC041IraiSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchModel;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB031RequestSearchModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 依頼検索サービス実装クラス。
 *
 * @author k002849
 * @version 1.0 2014/05/09
 * @version 1.1 2015/08/11 v145527
 * @version 1.2 2015/11/12 H.Hirai CSVダウンロード機能追加対応
 * @version 1.3 2015/12/24 H.Yamamura パスワードマスタ取得方法変更
 * @version 1.4 2016/08/22 J.Matsuba 検索条件のサービスが指定されている場合の処理を修正
 * @version 1.5 2016/10/21 H.Yamamura サービス種別の設定方法を変更
 * @version 1.6 2017/09/13 C.Kobayashi 会社略名対応
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB031RequestSearchServiceImpl extends TLCSSB2BBaseService
		implements TB031RequestSearchService {

	/** リセプション依頼テーブルDAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/** リセプション請求情報テーブルDAO */
	@Autowired
	private RcpTSeikyuDao seikyuDao;

	/** リセプション顧客契約マスタDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao keiyakuDao;

	/** リセプション状況区分マスタDAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;

	/** リセプションサービスマスタDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** NATOSパスワードＭDAO */
	@Autowired
	private NatosMPasswordDao natosPswdDao;

	/** 業者マスタDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/** 会社マスタDAO */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/**
	 * 初期表示を行います。
	 *
	 * @param model 依頼検索画面モデル
	 * @return 依頼検索画面モデル
	 */
	public TB031RequestSearchModel getInitInfo(TB031RequestSearchModel model) {
		// 前回締め年月取得
		List<String> zenkaiShoriYmList = seikyuDao.selectShimeYm(null, null);
		if (zenkaiShoriYmList == null || zenkaiShoriYmList.isEmpty()) {
			model.setZenkaiShoriYm("");
		} else {
			String zenkaiShoriYm = zenkaiShoriYmList.get(0).replaceAll("/", "");
			model.setZenkaiShoriYm(zenkaiShoriYm);
		}

		// サービスリスト取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		List<RcpMService> serviceList = new ArrayList<RcpMService>();
		if (userContext.isInhouse()) {
			// 権限が管理者の場合は、サービス全件取得
			serviceList = serviceDao.selectServiceList();
		} else if (userContext.isRealEstate()) {
			// 権限が不動産・管理会社の場合、請求先顧客ＩＤに紐付くサービスを取得
			serviceList = keiyakuDao.selectKokyakuKeiyakuService(userContext.getKokyakuId());
		} else if (userContext.isConstractor()) {
			// 権限が依頼業者の場合、依頼業者コードに紐付くサービスを取得
			serviceList = keiyakuDao.selectKokyakuKeiyakuServiceForGyosha(userContext.getGyoshaCd());
		}
		model.setServiceList(serviceList);

		// 状況リスト取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_JOKYO);
		List<RcpMComCd> jokyoList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_JOKYO);
		model.setJokyoList(jokyoList);

		return model;
	}

	/**
	 * 検索処理を実行します。
	 *
	 * @param model 依頼検索画面モデル
	 * @return 依頼検索画面モデル
	 */
	public TB031RequestSearchModel executeSearch(TB031RequestSearchModel model) {
		// 初期表示処理再実行（リスト情報取得のため）
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
		List<RC041IraiSearchDto> resultList = iraiDao.selectByCondition(model.getCondition(), model.getCondition().getCsvFlg());

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI,
				RcpMComCd.RCP_M_COM_CD_FLG_KANRYO,
				RcpMComCd.RCP_M_COM_CD_KBN_PUBLISH_FLG);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 顧客区分Map
		Map<String, RcpMComCd> kokyakuKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		// 顧客種別Map
		Map<String, RcpMComCd> kokyakuKindMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// 依頼者区分Map
		Map<String, RcpMComCd> iraishaKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// 訪問予定日Map
		Map<String, RcpMComCd> homonYoteiMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		// 完了フラグMap
		Map<String, RcpMComCd> kanryoFlgMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);
		// 公開フラグMap
		Map<String, RcpMComCd> kokaiFlgMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_PUBLISH_FLG);

		// 状況区分Map
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		Map<String, String> jokyoKbnMap = null;
		if (userContext.isRealEstate()) {
			// セッションの権限が管理会社の場合
			// 状況区分マスタ‐外部サイト用名称Mapからの取得
			jokyoKbnMap = jokyoKbnDao.selectAllExtJokyoKbnNmAsMap();
		} else {
			// セッションの権限が管理会社以外の場合
			// 状況区分マスタ‐和名変換Mapからの取得
			jokyoKbnMap = jokyoKbnDao.selectAllAsMap();
		}
		// 外部サイト用完了フラグMapを取得
		Map<String, String> jokyoKbnExtKanryoFlgMap = jokyoKbnDao.selectAllExtKanryoFlgAsMap();

		// 会社マスタ情報Map取得
		Map<String, String> kaishaMap = kaishaDao.selectAllAsMapForRyakuNm();

		// パスワードマスタ・業者マスタ検索用のリスト生成
		List<String> userIdList = new ArrayList<String>();
		List<String> gyoshaCdList = new ArrayList<String>();
		for (RC041IraiSearchDto dto : resultList) {
			if (StringUtils.isNotBlank(dto.getTantoshaId()) &&
				!userIdList.contains(dto.getTantoshaId())) {
				// パスワードマスタリストに追加：発注担当者ID
				userIdList.add(dto.getTantoshaId());
			}

			if (StringUtils.isNotBlank(dto.getIraiGyoshaCd()) &&
				!gyoshaCdList.contains(dto.getIraiGyoshaCd())) {
				// 業者マスタ検索用のリストに含まれていなければ、リストに追加
				gyoshaCdList.add(dto.getIraiGyoshaCd());
			}

			if (RC041IraiSearchModel.CSV_OUT.equals(model.getCondition().getCsvFlg())) {
				// CSVダウンロード処理時のみ実施
				if (StringUtils.isNotBlank(dto.getUketsukeshaId()) &&
						!userIdList.contains(dto.getUketsukeshaId())) {
					// パスワードマスタリストに追加：受付者ID
					userIdList.add(dto.getUketsukeshaId());
				}

				if (StringUtils.isNotBlank(dto.getCreId()) &&
						!userIdList.contains(dto.getCreId())) {
					// パスワードマスタリストに追加：依頼初回登録者ID
					userIdList.add(dto.getCreId());
				}

				if (StringUtils.isNotBlank(dto.getLastUpdId()) &&
						!userIdList.contains(dto.getLastUpdId())) {
					// パスワードマスタリストに追加：依頼最終更新者ID
					userIdList.add(dto.getLastUpdId());
				}

				if (StringUtils.isNotBlank(dto.getLastPrintId()) &&
						!userIdList.contains(dto.getLastPrintId())) {
					// パスワードマスタリストに追加：依頼最終更新者ID
					userIdList.add(dto.getLastPrintId());
				}

				if (StringUtils.isNotBlank(dto.getCreIdSagyoJokyo()) &&
						!userIdList.contains(dto.getCreIdSagyoJokyo())) {
					// パスワードマスタリストに追加：作業状況初回登録者ID
					userIdList.add(dto.getCreIdSagyoJokyo());
				}

				if (StringUtils.isNotBlank(dto.getLastUpdIdSagyoJokyo()) &&
						!userIdList.contains(dto.getLastUpdIdSagyoJokyo())) {
					// パスワードマスタリストに追加：作業状況最終更新者ID
					userIdList.add(dto.getLastUpdIdSagyoJokyo());
				}
			}
		}

		Map<String, String> userMap = new HashMap<String, String>();
		if (!(userIdList == null || userIdList.isEmpty())) {
			// CSV出力の場合
			if (RC041IraiSearchModel.CSV_OUT.equals(model.getCondition().getCsvFlg())) {
				// パスワードマスタ全件取得
				userMap = natosPswdDao.convertMap(natosPswdDao.selectAll());
			} else {
				// パスワードマスタ取得
				userMap = natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));
			}
		}

		Map<String, String> gyoshaMap = new HashMap<String, String>();
		if (!(gyoshaCdList == null || gyoshaCdList.isEmpty())) {
			// 業者マスタ取得
			gyoshaMap = gyoshaDao.convertMap(gyoshaDao.selectByGyoshaCdList(gyoshaCdList));
		}

		// 和名変換処理
		for (int i = 0; i < resultList.size(); i++) {
			RC041IraiSearchDto dto = resultList.get(i);

			if (StringUtils.isNotBlank(dto.getIraishaKbn()) &&
				iraishaKbnMap.containsKey(dto.getIraishaKbn())) {
				// 依頼者区分名
				dto.setIraishaKbnNm(iraishaKbnMap.get(dto.getIraishaKbn()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getJokyoKbn()) &&
				jokyoKbnMap.containsKey(dto.getJokyoKbn())) {
				// 状況区分名
				dto.setJokyoKbnNm(jokyoKbnMap.get(dto.getJokyoKbn()));
			}
			
			if (jokyoKbnExtKanryoFlgMap != null && StringUtils.isNotBlank(dto.getJokyoKbn()) &&
				jokyoKbnExtKanryoFlgMap.containsKey(dto.getJokyoKbn())) {
				// 外部サイト用完了フラグの設定
				dto.setJokyoKbnExtKanryoFlg(jokyoKbnExtKanryoFlgMap.get(dto.getJokyoKbn()));
			}

			// 担当者名が存在する場合
			if (StringUtils.isNotBlank(dto.getTantoshaNm())) {
				// 発注担当者名
				dto.setTantoshaIdNm(dto.getTantoshaNm());
			} else {
				if (StringUtils.isNotBlank(dto.getTantoshaId()) &&
						userMap.containsKey(dto.getTantoshaId())) {
					// 発注担当者名
					dto.setTantoshaIdNm(userMap.get(dto.getTantoshaId()));
				}
			}

			if (RC041IraiSearchModel.CSV_OUT.equals(model.getCondition().getCsvFlg())) {
				// CSVダウンロード処理時のみ実施

				if (StringUtils.isNotBlank(dto.getUketsukeshaId()) &&
						userMap.containsKey(dto.getUketsukeshaId())) {
					// 受付者和名
					dto.setUketsukeshaDisplayNm(userMap.get(dto.getUketsukeshaId()));
				}

				// 顧客IDが存在する場合
				if (StringUtils.isNotBlank(dto.getKokyakuId())) {
					if (StringUtils.isNotBlank(dto.getKokyakuKbn()) &&
							kokyakuKbnMap.containsKey(dto.getKokyakuKbn())) {
						// 顧客区分名
						dto.setKokyakuKbnNm(kokyakuKbnMap.get(dto.getKokyakuKbn()).getExternalSiteVal());
					}
				} else {
					if (StringUtils.isNotBlank(dto.getKokyakuKbn()) &&
							iraishaKbnMap.containsKey(dto.getKokyakuKbn())) {
						// 顧客区分名
						dto.setKokyakuKbnNm(iraishaKbnMap.get(dto.getKokyakuKbn()).getExternalSiteVal());
					}
				}

				if (StringUtils.isNotBlank(dto.getKokyakuShubetsu()) &&
						kokyakuKindMap.containsKey(dto.getKokyakuShubetsu())) {
					// 顧客種別名
					dto.setKokyakuShubetsuNm(kokyakuKindMap.get(dto.getKokyakuShubetsu()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getIraiGyoshaCd()) &&
						gyoshaMap.containsKey(dto.getIraiGyoshaCd())) {
					// 依頼業者名
					dto.setIraiGyoshaNm(gyoshaMap.get(dto.getIraiGyoshaCd()));
				}

				if (StringUtils.isNotBlank(dto.getHomonKiboJikanKbn()) &&
						homonYoteiMap.containsKey(dto.getHomonKiboJikanKbn())) {
					// 訪問希望時間区分名
					dto.setHomonKiboJikanKbnNm(homonYoteiMap.get(dto.getHomonKiboJikanKbn()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getGyoshaKaitoJikanKbn()) &&
						homonYoteiMap.containsKey(dto.getGyoshaKaitoJikanKbn())) {
					// 業者回答訪問予定時間区分名
					dto.setGyoshaKaitoJikanKbnNm(homonYoteiMap.get(dto.getGyoshaKaitoJikanKbn()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getIraiKokaiFlg()) &&
						kokaiFlgMap.containsKey(dto.getIraiKokaiFlg())) {
					// 依頼公開フラグ名
					dto.setIraiKokaiFlgNm(kokaiFlgMap.get(dto.getIraiKokaiFlg()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getCreId()) &&
						userMap.containsKey(dto.getCreId())) {
					// 依頼登録者和名
					dto.setCreDisplayNm(userMap.get(dto.getCreId()));
				}

				if (StringUtils.isNotBlank(dto.getLastUpdId()) &&
						userMap.containsKey(dto.getLastUpdId())) {
					// 依頼最終更新者和名
					dto.setLastUpdDisplayNm(userMap.get(dto.getLastUpdId()));
				}

				if (StringUtils.isNotBlank(dto.getCreKaishaId()) &&
						kaishaMap.containsKey(dto.getCreKaishaId())) {
					// 登録会社和名
					dto.setCreKaishaDisplayNm(kaishaMap.get(dto.getCreKaishaId()));
				}

				if (StringUtils.isNotBlank(dto.getUpdKaishaId()) &&
						kaishaMap.containsKey(dto.getUpdKaishaId())) {
					// 最終更新会社和名
					dto.setUpdKaishaDisplayNm(kaishaMap.get(dto.getUpdKaishaId()));
				}

				if (StringUtils.isNotBlank(dto.getLastPrintId()) &&
						userMap.containsKey(dto.getLastPrintId())) {
					// 最終印刷者和名
					dto.setLastPrintDisplayNm(userMap.get(dto.getLastPrintId()));
				}

				if (StringUtils.isNotBlank(dto.getSagyoKanryoFlg()) &&
						kanryoFlgMap.containsKey(dto.getSagyoKanryoFlg())) {
					// 作業完了区分名
					dto.setSagyoKanryoFlgNm(kanryoFlgMap.get(dto.getSagyoKanryoFlg()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getSagyoJokyoKokaiFlg()) &&
						kokaiFlgMap.containsKey(dto.getSagyoJokyoKokaiFlg())) {
					// 作業状況公開フラグ名
					dto.setSagyoJokyoKokaiFlgNm(kokaiFlgMap.get(dto.getSagyoJokyoKokaiFlg()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getHokokushoKokaiFlg()) &&
						kokaiFlgMap.containsKey(dto.getHokokushoKokaiFlg())) {
					// 報告書公開フラグ名
					dto.setHokokushoKokaiFlgNm(kokaiFlgMap.get(dto.getHokokushoKokaiFlg()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getCreIdSagyoJokyo()) &&
						userMap.containsKey(dto.getCreIdSagyoJokyo())) {
					// 作業状況登録者和名
					dto.setCreDisplayNmSagyoJokyo(userMap.get(dto.getCreIdSagyoJokyo()));
				}

				if (StringUtils.isNotBlank(dto.getLastUpdIdSagyoJokyo()) &&
						userMap.containsKey(dto.getLastUpdIdSagyoJokyo())) {
					// 作業状況最終更新者和名
					dto.setLastUpdDisplayNmSagyoJokyo(userMap.get(dto.getLastUpdIdSagyoJokyo()));
				}
			}

			// 受付履歴時間
			dto.setUketsukeJikanRireki(DateUtil.hhmmPlusColon(dto.getUketsukeJikanRireki()));
			// 作業完了時間
			dto.setSagyoKanryoJikan(DateUtil.hhmmPlusColon(dto.getSagyoKanryoJikan()));

			// 再セット
			resultList.set(i, dto);
		}

		// アクセスログ登録
		tbAccesslogDao.insert(TB031RequestSearchModel.GAMEN_NM,
				Constants.BUTTON_NM_SEARCH, createKensakuJoken(model));

		model.setResultList(resultList);

		return model;
	}

	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model 依頼検索画面モデル
	 * @return アクセスログに登録する検索条件
	 */
	private String createKensakuJoken(TB031RequestSearchModel model) {
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
