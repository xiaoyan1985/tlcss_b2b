package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dto.RC041IraiSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB050RequestHistoryInfoModel;

/**
 * 顧客詳細依頼履歴サービス実装クラス。
 *
 * @author v145527 小林
 * @version 1.0 2015/07/30
 *
 */

@Service
@Transactional(value="txManager", readOnly = true)
public class TB050RequestHistoryInfoServiceImpl extends TLCSSB2BBaseService
implements TB050RequestHistoryInfoService {
	
	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;
	
	/** リセプション共通コードマスタDAO */
	@Autowired
	private RcpMComCdDao comCdDao;

	/** 状況区分マスタDAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;

	/** 顧客マスタDAO */
	@Autowired
	private RcpTIraiDao rcpTIraiDao;

	/** パスワードＭDAO */
	@Autowired
	private NatosMPasswordDao natosMPasswordDao;

	/** リセプション業者マスタDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	
	public TB050RequestHistoryInfoModel getInitInfo(
			TB050RequestHistoryInfoModel model) {
		
		// 初期情報取得処理
		// 顧客基本情報取得
		RcpMKokyaku kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		// 依頼情報検索
		model = searchIrai(model);
		// model にEntityデータを設定
		model.setKokyakuEntity(kokyakuEntity);
		return model;
		
	}

	/**
	 * 依頼一覧を検索します。
	 *
	 * @param model 画面モデル
	 * @return TB050RequestHistoryInfoModel 画面モデル
	 */
	private TB050RequestHistoryInfoModel searchIrai(TB050RequestHistoryInfoModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		RC041IraiSearchCondition condition = model.getCondition();
		
		// TB050顧客詳細依頼履歴最大検索可能件数
		condition.setLimit(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_REQUEST_HISTORY_TO_MAX));
		condition.setMaxCount(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_REQUEST_HISTORY_TO_MAX));

		// 一覧検索
		condition.setKokyakuId(model.getKokyakuId().replaceAll("C", ""));	// 顧客IDの先頭一桁（C）を除く
		List<RC041IraiSearchDto> iraiSearchList = rcpTIraiDao.selectByCondition(condition, "");
		
		// 共通コードマスタからの取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN, RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND, RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI, RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);
		Map<String, Map<String, RcpMComCd>> comKeyMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 発注担当Map
		List<String> userIdList = new ArrayList<String>();
		for (RC041IraiSearchDto dto : iraiSearchList) {
			if (StringUtils.isNotBlank(dto.getTantoshaId())
				&& !userIdList.contains(dto.getTantoshaId())) {
				// パスワードマスタ検索用のリストに含まれていなければ、リストに追加
				userIdList.add(dto.getTantoshaId());
			}
		}
		Map<String, String> convMapHachuTanto = getMPasswordAsMap(userIdList);
		// 依頼者区分変換Map
		Map<String, RcpMComCd> convMapIraiKbn = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// 状況区分Map
		List<RcpMJokyoKbn> jokyoKbnList = jokyoKbnDao.selectAll();
		Map<String, String> convMapJokyoKbn = jokyoKbnDao.convertMap(jokyoKbnList);
		
		// CSV用 
		// 顧客区分変換Map
		Map<String, RcpMComCd> convMapkokyakuKbn = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		// 顧客種別変換Map
		Map<String, RcpMComCd> convMapkokyakuKind = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// 訪問予定時間区分変換Map
		Map<String, RcpMComCd> convMapHomonKbn = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		// 作業完了区分変換Map
		Map<String, RcpMComCd> convMapSagyoKbn = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);

		// 業者マスタ変換Map
		List<RcpMGyosha> gyoshaList = gyoshaDao.selectAll();
		Map<String, String> convMapGyosha = gyoshaDao.convertMap(gyoshaList);

	
		// 和名変換
		for (RC041IraiSearchDto iraiDto : iraiSearchList) {
			
			// 担当者名が存在する場合
			if (StringUtils.isNotBlank(iraiDto.getTantoshaNm())) {
				// 発注担当者名
				iraiDto.setTantoshaIdNm(iraiDto.getTantoshaNm());
			}  else {
				// 発注担当Map
				if (convMapHachuTanto != null && StringUtils.isNotBlank(iraiDto.getTantoshaId())) {
					iraiDto.setTantoshaIdNm(convMapHachuTanto.get(iraiDto.getTantoshaId()));
				}
			}
			// 依頼者区分変換Map
			if (convMapIraiKbn != null && StringUtils.isNotBlank(iraiDto.getIraishaKbn())) {
				iraiDto.setIraishaKbnNm(convertToValue(convMapIraiKbn, iraiDto.getIraishaKbn()));
			}
			// 状況区分Map
			if (convMapJokyoKbn != null && StringUtils.isNotBlank(iraiDto.getJokyoKbn())) {
				iraiDto.setJokyoKbnNm(convMapJokyoKbn.get(iraiDto.getJokyoKbn()));
			}
			// 顧客区分変換Map
			if (convMapkokyakuKbn != null && StringUtils.isNotBlank(iraiDto.getKokyakuKbn())) {
				iraiDto.setKokyakuKbnNm(convertToValue(convMapkokyakuKbn, iraiDto.getKokyakuKbn()));
			}
			// 顧客種別変換Map
			if (convMapkokyakuKind != null && StringUtils.isNotBlank(iraiDto.getKokyakuShubetsu())) {
				iraiDto.setKokyakuShubetsuNm(convertToValue(convMapkokyakuKind, iraiDto.getKokyakuShubetsu()));
			}
			// 訪問予定時間区分変換Map
			if (convMapHomonKbn != null && StringUtils.isNotBlank(iraiDto.getHomonKiboJikanKbn())) {
				iraiDto.setHomonKiboJikanKbnNm(convertToValue(convMapHomonKbn, iraiDto.getHomonKiboJikanKbn()));
			}
			// 作業完了区分変換Map
			if (convMapSagyoKbn != null && StringUtils.isNotBlank(iraiDto.getSagyoKanryoFlg())) {
				iraiDto.setSagyoKanryoFlgNm(convertToValue(convMapSagyoKbn, iraiDto.getSagyoKanryoFlg()));
			}
			// 依頼業者コード変換
			if (convMapGyosha != null && StringUtils.isNotBlank(iraiDto.getIraiGyoshaCd())) {
				iraiDto.setIraiGyoshaNm(convMapGyosha.get(iraiDto.getIraiGyoshaCd()));
			}
		}

		
		model.setResult(iraiSearchList);

		return model;
	}

	/**
	 * パスワードＭの和名変換Mapを取得します。
	 *
	 * @param cdKbn パスワードＭ コード区分
	 * @return Map<String, String> 和名変換Map
	 */
	private Map<String, String> getMPasswordAsMap(List<String> userIdList) {

		Map<String, String> userMap = new HashMap<String, String>();
		if (! userIdList.isEmpty()) {
			// パスワードＭからの取得
			userMap = natosMPasswordDao.convertMap(natosMPasswordDao.selectByList(userIdList, null));
		}

		return userMap;
	}

	/**
	 * 共通コードマスタの和名変換を行います。
	 *
	 * @param cdKbn リセプション共通コードマスタ コード区分
	 * @param cd リセプション共通コードマスタ コード
	 * @param convertMap コード変換Map
	 * @return String 変換後文字列
	 */
	private String convertToValue(Map<String, RcpMComCd> convertMap, String cd) {

		// 和名取得
		RcpMComCd comCd = convertMap.get(cd);
		String wamei = "";
		if (StringUtils.isNotBlank(comCd.getComVal())) {
			wamei = comCd.getComVal();
		}

		return wamei;
	}
	
}
