package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB041CustomerSearchModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物件・入居者検索サービス実装クラス。
 *
 * @author v138130
 * @version 4.0 2014/06/03
 * @version 4.1 2016/07/28 S.Nakano 権限が不動産・管理会社の場合の契約サービスリスト取得を修正
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB041CustomerSearchServiceImpl  extends TLCSSB2BBaseService
			implements TB041CustomerSearchService {

	/** 顧客契約ＭDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** 顧客ＭDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** リセプションサービスマスタDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/**
	 * 初期情報取得処理を行います。
	 *
	 * @param model 物件・入居者検索画面モデル
	 * @return 物件・入居者検索画面モデル
	 */
	public TB041CustomerSearchModel getInitInfo(TB041CustomerSearchModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// 郵便番号検索URL取得
		model.setYubinNoSearchURL(userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_YUBIN_NO_SEARCH_URL));

		// サービスリスト取得
		List<RcpMService> serviceList = new ArrayList<RcpMService>();
		if (userContext.isInhouse()) {
			// 権限が管理者の場合は、サービス全件取得
			serviceList = serviceDao.selectServiceList();
		} else if (userContext.isRealEstate()) {
			// 権限が不動産・管理会社の場合、請求先顧客ＩＤに紐付くサービスを取得
			if (userContext.isKokyakuIdSelected()) {
				// 顧客ＩＤ選択済の場合
				serviceList = kokyakuKeiyakuDao.selectKokyakuKeiyakuService(userContext.getKokyakuId());
			} else {
				// 顧客ＩＤ選択済でない場合
				serviceList = kokyakuKeiyakuDao.selectKokyakuKeiyakuServiceForParentKokyakuId(userContext.getKokyakuId());
			}
		} else if (userContext.isConstractor()) {
			// 権限が依頼業者の場合、依頼業者コードに紐付くサービスを取得
			serviceList = kokyakuKeiyakuDao.selectKokyakuKeiyakuServiceForGyosha(userContext.getGyoshaCd());
		}

		model.setServiceList(serviceList);

		return model;
	}


	/**
	 * 検索処理を行います。
	 *
	 * @param model 物件・入居者検索画面モデル
	 * @return 物件・入居者検索画面モデル
	 */
	public TB041CustomerSearchModel search(TB041CustomerSearchModel model) {
		// 初期情報取得処理
		model = getInitInfo(model);

		// 外部サイト公開に対応
		model.getCondition().setExternalSiteKokai(true);
		// 顧客マスタデータ抽出から、顧客マスタ検索結果リストを取得
		List<RcpMKokyaku> resultList = kokyakuDao.selectByCondition(model.getCondition(), RC011KokyakuSearchCondition.CSVFLG_OFF);

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		Map<String, Map<String, RcpMComCd>> convertMap =
				comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 和名変換処理
		Map<String, RcpMComCd> kokyakuKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		for (int i = 0; i < resultList.size(); i++) {
			RcpMKokyaku kokyaku = resultList.get(i);

			if (StringUtils.isNotBlank(kokyaku.getKokyakuKbn()) &&
					kokyakuKbnMap.containsKey(kokyaku.getKokyakuKbn())) {
				kokyaku.setKokyakuKbnNm(kokyakuKbnMap.get(kokyaku.getKokyakuKbn()).getExternalSiteVal());
			}
			resultList.set(i, kokyaku);
		}

		// アクセスログの登録処理
		tbAccesslogDao.insert(TB041CustomerSearchModel.GAMEN_NM,
				Constants.BUTTON_NM_SEARCH, createKensakuJoken(model));

		model.setResultList(resultList);

		return model;
	}


	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model 物件・入居者検索画面モデル
	 * @return 検索条件
	 */
	private String createKensakuJoken(TB041CustomerSearchModel model) {
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
