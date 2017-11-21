package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.RcpTKeiyakuTargetDao;
import jp.co.tokaigroup.reception.dto.RC014KeiyakuListDto;
import jp.co.tokaigroup.reception.dto.RC014KeiyakuTargetListDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKeiyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.kokyaku.model.RC014KokyakuKeiyakuListCondition;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB046ContractInfoModel;

/**
 * 契約情報サービス実装クラス。
 *
 * @author v145527 小林
 * @version 1.0 2015/08/04
 * @version 1.1 2016/03/25 J.Matsuba ビル管理の場合の、実施月設定処理追加
 * @version 1.2 2016/04/07 J.Matsuba 実施月、毎月実施の場合「毎月」と表示するよう修正
 * @version 1.3 2016/08/05 H.Yamamura ビル管理契約の文言を追加
 *
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB046ContractInfoServiceImpl extends TLCSSB2BBaseService
implements TB046ContractInfoService{

	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** リセプション顧客契約情報マスタDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** リセプション顧客契約情報マスタDAO */
	@Autowired
	private RcpTKeiyakuTargetDao keiyakuTargetDao;

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** リセプション共通マスタDAO */
	@Autowired
	private RcpMComCdDao comCdDao;

	/** リセプションサービスマスタDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/**
	 * 初期表示を行います。
	 *
	 * @param model 契約情報画面モデル
	 * @return 契約情報画面モデル
	 */
	@Override
	public TB046ContractInfoModel getInitInfo(
			TB046ContractInfoModel model) {

		// 初期情報取得処理
		// 顧客基本情報取得
		RcpMKokyaku kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		// 契約情報検索
		model = searchKeiyaku(model);
		// model にEntityデータを設定
		model.setKokyakuEntity(kokyakuEntity);
		return model;
	}
		
	/**
	 * 契約情報契約一覧を検索します。
	 *
	 * @param model 画面モデル
	 * @return TB046ContractInfoModel 画面モデル
	 */
	private TB046ContractInfoModel searchKeiyaku(TB046ContractInfoModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		RC014KokyakuKeiyakuListCondition condition = model.getCondition();
		
		// TB046顧客詳細契約情報契約一覧最大検索可能件数
		condition.setLimit(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TO_MAX));
		condition.setMaxCount(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TO_MAX));

		// 契約一覧取得
		condition.setKokyakuId(model.getKokyakuId().replaceAll("C", ""));	// 顧客IDの先頭一桁（C）を除く
		List<RC014KeiyakuListDto> keiyakuList =
			kokyakuKeiyakuDao.selectKeiyakuShosaiList(model.getKokyakuId());

		if (keiyakuList != null && !keiyakuList.isEmpty()) {
			int limit = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TO_MAX);
			if (limit < keiyakuList.size()) {
				// 最大検索可能件数より検索結果の数が多い場合
				// 最大検索可能件数分のみ、検索結果を表示
				keiyakuList = keiyakuList.subList(0, limit);
			}
		}

		if (keiyakuList != null && !keiyakuList.isEmpty()) {
			// 顧客差異フラグ
			boolean isDiffrentKokyaku = false;
			String keiyakuKokyakuId = "";
			for (RC014KeiyakuListDto dto : keiyakuList) {
				if (!model.getKokyakuId().equals(dto.getSeikyusakiKokyakuId())) {
					// パラメータの顧客ＩＤと異なる顧客ＩＤがあった場合
					isDiffrentKokyaku = true;
					keiyakuKokyakuId = dto.getSeikyusakiKokyakuId();
					break;
				}
			}

			if (isDiffrentKokyaku && StringUtils.isNotBlank(keiyakuKokyakuId)) {
				// パラメータの顧客ＩＤと異なる顧客ＩＤがあった場合は、
				// 契約者の顧客情報を取得
				RcpMKokyaku keiyakuKokyaku = kokyakuDao.selectByPrimaryKey(keiyakuKokyakuId);

				if (keiyakuKokyaku != null) {
					model.setKeiyakuKokyakuInfo(keiyakuKokyaku);
				}
			}
		}
		
		// 契約対象一覧
		List<RC014KeiyakuTargetListDto> keiyakuTargetList = null;

		if (model.getKeiyakuNo() != null) {
			// パラメータの契約ＮＯがあった場合
			RcpMKokyakuKeiyaku keiyakuInfo =
				kokyakuKeiyakuDao.selectByPrimaryKey(model.getKeiyakuKokyakuId(), Integer.toString(model.getKeiyakuNo()));

			if (keiyakuInfo != null) {
				model.setKeiyakuUpdDt(keiyakuInfo.getUpdDt());

				// サービスマスタ-プライマリー検索 から サービスマスタEntity を取得
				RcpMService serviceInfo = serviceDao.selectByPrimaryKey(keiyakuInfo.getServiceKbn());
				if (serviceInfo != null && serviceInfo.isReception() || serviceInfo.isBuildingManagement()) {
					// サービス種別がリセプションの場合
					// TB046顧客詳細契約情報契約対象一覧最大検索可能件数
					condition.setLimit(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TARGET_TO_MAX));
					condition.setMaxCount(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TARGET_TO_MAX));
					// 契約対象一覧取得
					keiyakuTargetList =
						keiyakuTargetDao.selectKeiyakuTargetList(model.getKeiyakuKokyakuId(), model.getKeiyakuNo());

					if (keiyakuTargetList != null && !keiyakuTargetList.isEmpty()) {
						int limit = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TARGET_TO_MAX);
						if (limit < keiyakuTargetList.size()) {
							// 最大検索可能件数より検索結果の数が多い場合
							// 最大検索可能件数分のみ、検索結果を表示
							keiyakuTargetList = keiyakuTargetList.subList(0, limit);
						}
					}
				}
			}
		}
		
		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND,
					RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE,
					RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND,
					RcpMComCd.RCP_M_COM_CD_KBN_ENTRY_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_ADMISSION_COST,
					RcpMComCd.RCP_M_COM_CD_KBN_CLAIM_DEPT,
					RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE,
					RcpMComCd.RCP_M_COM_CD_KEIYAKU_KEITAI,
					RcpMComCd.RCP_M_COM_CD_JISSHI_KAISU);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// サービス和名変換用Map取得
		List<RcpMService> serviceList = serviceDao.selectAll();
		Map<String, String> serviceMap = serviceDao.convertMap(serviceList);
		// サービス種別変換用Map取得
		Map<String, String> serviceShubetsuMap = serviceDao.convertServiceShubetsuMap(serviceList);
		// 回線種類の和名変換
		Map<String, RcpMComCd> lineKindMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND);
		// 顧客区分の和名変換
		Map<String, RcpMComCd> kokyakuKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		// 顧客種別の和名変換
		Map<String, RcpMComCd> kokyakuShubetsuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// 申込区分の和名変換
		Map<String, RcpMComCd> entryKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_ENTRY_KBN);
		// 入会費用の和名変換
		Map<String, RcpMComCd> admissionCostMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_ADMISSION_COST);
		// 時間帯の和名変換
		Map<String, RcpMComCd> timeZoneMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE);
		// 基本料金区分の和名変換
		Map<String, RcpMComCd> kihonRyokinMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE);
		// 契約形態の和名変換
		Map<String, RcpMComCd> keiyakuKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KEIYAKU_KEITAI);
		// 実施区分の和名変換
		Map<String, RcpMComCd> jishiKaisuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_JISSHI_KAISU);

		if (keiyakuList != null && !keiyakuList.isEmpty()) {
			// 和名変換
			for (RC014KeiyakuListDto keiyakuDto : keiyakuList) {

				// サービス
				keiyakuDto.setServiceKbnNm(serviceMap.get(keiyakuDto.getServiceKbn()));

				// サービス種別
				keiyakuDto.setServiceShubetsu(serviceShubetsuMap.get(keiyakuDto.getServiceKbn()));

				// 入会費用、申込区分
				if (keiyakuDto.isLifeSupport24()) {
					// ライフサポート２４の場合に、和名変換
					String admissionCostNm = "";
					if (admissionCostMap.containsKey(keiyakuDto.getNyukaiHiyoKbn())) {
						admissionCostNm = admissionCostMap.get(keiyakuDto.getNyukaiHiyoKbn()).getComVal();
					}
					keiyakuDto.setNyukaiHiyoKbnNm(admissionCostNm);

					String entryKbnNm = "";
					if (entryKbnMap.containsKey(keiyakuDto.getMoshikomiKbn())) {
						entryKbnNm = entryKbnMap.get(keiyakuDto.getMoshikomiKbn()).getComVal();
					}
					keiyakuDto.setMoshikomiKbnNm(entryKbnNm);
				}

				// 回線種類、時間帯、基本料金区分
				if (keiyakuDto.isReception()) {
					// リセプションの場合に、和名変換

					String lineKindNm = "";
					if (lineKindMap.containsKey(keiyakuDto.getKaisenCd())) {
						lineKindNm = lineKindMap.get(keiyakuDto.getKaisenCd()).getComVal();
					}
					keiyakuDto.setKaisenCdNm(lineKindNm);

					String timeZoneNm = "";
					if (timeZoneMap.containsKey(keiyakuDto.getJikanCd())) {
						timeZoneNm = timeZoneMap.get(keiyakuDto.getJikanCd()).getComVal();
					}
					keiyakuDto.setJikanCdNm(timeZoneNm);

					String kihonRyokinKbnNm = "";
					if (kihonRyokinMap.containsKey(keiyakuDto.getKihonRyokinKbn())) {
						kihonRyokinKbnNm = kihonRyokinMap.get(keiyakuDto.getKihonRyokinKbn()).getComVal();
					}
					keiyakuDto.setKihonRyokinKbnNm(kihonRyokinKbnNm);
				}
				
				// 契約形態、実施区分
				if (keiyakuDto.isBuildingManagement()) {
					// ビル管理の場合に、和名変換
					// 契約形態
					String keiyakuKeitaiNm = "";
					if (keiyakuKeitaiMap.containsKey(keiyakuDto.getKeiyakuType())) {
						keiyakuKeitaiNm = keiyakuKeitaiMap.get(keiyakuDto.getKeiyakuType()).getComVal();
					}
					keiyakuDto.setKeiyakuTypeNm(keiyakuKeitaiNm);

					// 実施区分
					String jishiKaisuNm = "";
					if (jishiKaisuMap.containsKey(keiyakuDto.getJisshiKbn())) {
						jishiKaisuNm = jishiKaisuMap.get(keiyakuDto.getJisshiKbn()).getComVal();
					}
					keiyakuDto.setJisshiKbnNm(jishiKaisuNm);
				}
			}
		}

		if (keiyakuTargetList != null && !keiyakuTargetList.isEmpty()) {
			// 和名変換
			for (RC014KeiyakuTargetListDto keiyakuTargetDto : keiyakuTargetList) {

				// 顧客区分
				String kokyakuKbnNm = "";
				if (kokyakuKbnMap.containsKey(keiyakuTargetDto.getKokyakuKbn())) {
					kokyakuKbnNm = kokyakuKbnMap.get(keiyakuTargetDto.getKokyakuKbn()).getComVal();
				}
				keiyakuTargetDto.setKokyakuKbnNm(kokyakuKbnNm);

				// 顧客種別
				String kokyakuShubetsuNm = "";
				if (kokyakuShubetsuMap.containsKey(keiyakuTargetDto.getKokyakuShubetsu())) {
					kokyakuShubetsuNm = kokyakuShubetsuMap.get(keiyakuTargetDto.getKokyakuShubetsu()).getComVal();
				}
				keiyakuTargetDto.setKokyakuShubetsuNm(kokyakuShubetsuNm);
			}
		}
		
		// 契約対象一覧に表示する文言を設定
		if (model.getKeiyakuNo() != null) {
			// パラメータの契約Noが取得できた場合
			for(RC014KeiyakuListDto kokyakuKeiyakuDto : keiyakuList) {
				// 引数の顧客IDと契約NOが一致する場合
				if (model.getKeiyakuNo() == kokyakuKeiyakuDto.getKeiyakuNo().intValue()
						&& kokyakuKeiyakuDto.getKokyakuId().equals(model.getKeiyakuKokyakuId())) {
					String keiyakuTaishoIchiran = "";
					if (kokyakuKeiyakuDto.isReception()) {
						// サービス種別が「1:リセプション」の場合
						keiyakuTaishoIchiran = "【"
							+ kokyakuKeiyakuDto.getServiceKbnNm()
							+ "　"
							+ kokyakuKeiyakuDto.getKaisenCdNm()
							+ "　"
							+ kokyakuKeiyakuDto.getJikanCdNm()
							+ "　契約対象】";
					} else if (kokyakuKeiyakuDto.isBuildingManagement()) {
						// サービス種別が「3:ビル管理」の場合
						keiyakuTaishoIchiran = "【"
							+ kokyakuKeiyakuDto.getServiceKbnNm()
							+ "　契約対象】";
					}
					model.setKeiyakuTaisho(keiyakuTaishoIchiran);
				}
			}
		}

		model.setKeiyakuList(keiyakuList);
		model.setKeiyakuTargetList(keiyakuTargetList);

		return model;
	
	}

}
