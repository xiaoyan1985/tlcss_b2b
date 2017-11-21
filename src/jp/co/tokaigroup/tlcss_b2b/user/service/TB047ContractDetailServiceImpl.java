package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKeiyakuTelDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKLifeDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKRcpDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dto.RC020KokyakuShosaiKeiyakuShosaiDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKeiyakuTel;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKLife;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKRcp;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKeiyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.kokyaku.logic.RC020KokyakuShosaiKeiyakuShosaiLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB047ContractDetailModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 顧客詳細契約詳細情報サービス実装クラス。
 *
 * @author k003858
 * @version 1.0
 * @version 1.1 2016/03/24 J.Matsuba ビル管理用画面表示による追加
 * @version 1.2 2016/03/29 S.Nakano 実施曜日取得、受注額タイトル取得など修正
 * @version 1.3 2016/07/14 C.Kobayashi ビル管理情報取得のロジック化対応
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB047ContractDetailServiceImpl extends TLCSSB2BBaseService
		implements TB047ContractDetailService {
	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** リセプションサービスマスタDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/** リセプション顧客契約情報マスタDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** 顧客契約リセプションマスタDAO */
	@Autowired
	private RcpMKokyakuKRcpDao kokyakuKRcpDao;

	/** 顧客契約ライフサポートマスタDAO */
	@Autowired
	private RcpMKokyakuKLifeDao kokyakuKLifeDao;

	/** 契約電話番号マスタDAO */
	@Autowired
	private RcpMKeiyakuTelDao keiyakuTelDao;

	/** 顧客詳細契約詳細ロジック */
	@Autowired
	private RC020KokyakuShosaiKeiyakuShosaiLogic keiyakuShosai;

	/**
	 * 初期表示を行います。
	 *
	 * @param model 顧客詳細契約詳細情報画面モデル
	 * @return 顧客詳細契約詳細情報画面モデル
	 */
	public TB047ContractDetailModel getInitInfo(TB047ContractDetailModel model) {
		
		// 顧客基本情報を取得する
		RcpMKokyaku kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		model.setKokyakuEntity(kokyakuEntity);
		

			// 顧客契約情報の取得
			RcpMKokyakuKeiyaku keiyakuInfo =
				kokyakuKeiyakuDao.selectByPrimaryKey(model.getKeiyakuKokyakuId(), String.valueOf(model.getKeiyakuNo()));

			if (keiyakuInfo == null) {
				return model;
			}

			// サービスマスタ情報取得
			RcpMService serviceInfo = null;
			// サービスマスタからの取得
			if (StringUtils.isNotBlank(keiyakuInfo.getServiceKbn())) {
				serviceInfo = serviceDao.selectByPrimaryKey(keiyakuInfo.getServiceKbn());

				keiyakuInfo.setServiceKbnNm(serviceInfo.getServiceKbnNm());
			}
			model.setServiceInfo(serviceInfo);

			// 共通コードマスタからの取得
			Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
					RcpMComCd.RCP_M_COM_CD_KBN_CLAIM_DEPT, RcpMComCd.RCP_M_COM_CD_KBN_CLAIM_METHOD,
					RcpMComCd.RCP_M_COM_CD_KBN_ACCOUNT_KBN, RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND,
					RcpMComCd.RCP_M_COM_CD_KBN_CHARGE_TARGET, RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE,
					RcpMComCd.RCP_M_COM_CD_KBN_ENTRY_KBN, RcpMComCd.RCP_M_COM_CD_KBN_BUILD_FORM,
					RcpMComCd.RCP_M_COM_CD_KBN_ADMISSION_COST, RcpMComCd.RCP_M_COM_CD_KBN_NYUKYO_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_SEX, RcpMComCd.RCP_M_COM_CD_KBN_MAIL_ADDRESS_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_RELATION, RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE,
					RcpMComCd.RCP_M_COM_CD_KBN_KANRIHI_FURIKAE_KBN);

			// 和名変換用Map
			Map<String, Map<String, RcpMComCd>> convertMap =
				comCdDao.convertMapAsKeyToEntity(comKbnMap);

			if (serviceInfo != null && serviceInfo.isReception()) {
				// サービス区分が1:リセプションの場合
				// 顧客契約リセプション情報の取得
				RcpMKokyakuKRcp rcpInfo =
					kokyakuKRcpDao.selectByPrimaryKey(model.getKeiyakuKokyakuId(), String.valueOf(model.getKeiyakuNo()));

				// 和名変換
				model.setRcpInfo(convertOfRcpInfo(rcpInfo, convertMap));
			} else if (serviceInfo != null && serviceInfo.isLifeSupport24()) {
				// サービス区分が2:ライフサポート２４の場合
				// 顧客契約ライフサポート情報の取得
				RcpMKokyakuKLife lifeInfo =
					kokyakuKLifeDao.selectByPrimaryKey(model.getKeiyakuKokyakuId(), model.getKeiyakuNo());

				// 和名変換
				model.setLifeInfo(convOfLifeInfo(lifeInfo, convertMap));
			} else if (serviceInfo != null && serviceInfo.isBuildingManagement()) {
				// サービス区分が3:ビル管理の場合
				// 顧客契約ビル管理情報の取得
				RC020KokyakuShosaiKeiyakuShosaiDto keiyakuShosaiDto = 
					keiyakuShosai.getKokyakuKeiyakuBuildingInfo(model.getKokyakuId(), model.getKeiyakuNo());
				model.setKeiyakuShosaiDto(keiyakuShosaiDto);
			}

			// 和名変換
			model.setKeiyakuInfo(convOfKeiyakuInfo(keiyakuInfo, convertMap));

			// 契約電話番号マスタデータ（契約電話番号指定）の取得
			RcpMKeiyakuTel keiyakuTel =
				keiyakuTelDao.selectByKeiyakuTelNo(model.getKeiyakuInfo().getMadoguchiTel());
			model.setKeiyakuTelInfo(keiyakuTel);

		return model;
	}

	/**
	 * 顧客契約情報の和名変換を行います。
	 *
	 * @param keiyakuInfo 顧客契約情報マスタエンティティ
	 * @param convertMap 和名変換用Map
	 * @return RcpMKokyakuKeiyaku 顧客契約情報マスタエンティティ
	 */
	public RcpMKokyakuKeiyaku convOfKeiyakuInfo(RcpMKokyakuKeiyaku keiyakuInfo, Map<String, Map<String, RcpMComCd>> convertMap) {

		// 請求先の和名変換
		Map<String, RcpMComCd> seikyusakiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_CLAIM_DEPT);
		// 請求方法の和名変換
		Map<String, RcpMComCd> seikyuKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_CLAIM_METHOD);
		// 口座区分の和名変換
		Map<String, RcpMComCd> kozaNoKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_ACCOUNT_KBN);
		// 管理費振替フラグの和名変換
		Map<String, RcpMComCd> kanrihiFurikaeMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KANRIHI_FURIKAE_KBN);

		// 請求先取得
		if (seikyusakiMap != null && seikyusakiMap.containsKey(keiyakuInfo.getSeikyusakiCd())) {
			keiyakuInfo.setSeikyusakiNm(seikyusakiMap.get(keiyakuInfo.getSeikyusakiCd()).getComVal());
		}

		// 請求方法取得
		if (seikyuKbnMap != null && seikyuKbnMap.containsKey(keiyakuInfo.getSeikyuKbn())) {
			keiyakuInfo.setSeikyuKbnNm(seikyuKbnMap.get(keiyakuInfo.getSeikyuKbn()).getComVal());
		}

		// 口座区分取得
		if (kozaNoKbnMap != null && kozaNoKbnMap.containsKey(keiyakuInfo.getKozaNoKbn())) {
			keiyakuInfo.setKozaNoKbnNm(kozaNoKbnMap.get(keiyakuInfo.getKozaNoKbn()).getComVal());
		}

		// 管理費振替フラグ取得
		if (kanrihiFurikaeMap != null && kanrihiFurikaeMap.containsKey(keiyakuInfo.getKanrihiFurikaeFlg())) {
			keiyakuInfo.setKanrihiFurikaeFlgNm(kanrihiFurikaeMap.get(keiyakuInfo.getKanrihiFurikaeFlg()).getComVal());
		}

		return keiyakuInfo;
	}

	/**
	 * 顧客契約リセプション情報の和名変換を行います。
	 *
	 * @param rcpInfo 顧客契約リセプション情報マスタエンティティ
	 * @param convertMap 和名変換用Map
	 * @return RcpMKokyakuKRcp 顧客契約リセプション情報マスタエンティティ
	 */
	public RcpMKokyakuKRcp convertOfRcpInfo(RcpMKokyakuKRcp rcpInfo, Map<String, Map<String, RcpMComCd>> convertMap) {

		if (rcpInfo != null) {

			// 回線種類の和名変換
			Map<String, RcpMComCd> kaisenCdMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND);
			// 課金対象の和名変換
			Map<String, RcpMComCd> kakinCdMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_CHARGE_TARGET);
			// 基本料金区分の和名変換
			Map<String, RcpMComCd> kihonRyokinKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE);
			// 時間帯の和名変換
			Map<String, RcpMComCd> jikanCdMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE);

			// 回線種類取得
			if (kaisenCdMap != null && kaisenCdMap.containsKey(rcpInfo.getKaisenCd())) {
				rcpInfo.setKaisenNm(kaisenCdMap.get(rcpInfo.getKaisenCd()).getComVal());
			}

			// 課金対象取得
			if (kakinCdMap != null && kakinCdMap.containsKey(rcpInfo.getKakinCd())) {
				rcpInfo.setKakinNm(kakinCdMap.get(rcpInfo.getKakinCd()).getComVal());
			}

			// 基本料金区分取得
			if (kihonRyokinKbnMap != null && kihonRyokinKbnMap.containsKey(rcpInfo.getKihonRyokinKbn())) {
				rcpInfo.setKihonRyokinKbnNm(kihonRyokinKbnMap.get(rcpInfo.getKihonRyokinKbn()).getComVal());
			}

			// 時間帯取得
			if (jikanCdMap != null && kakinCdMap.containsKey(rcpInfo.getJikanCd())) {
				rcpInfo.setJikanNm(jikanCdMap.get(rcpInfo.getJikanCd()).getComVal());
			}
		}

		return rcpInfo;
	}

	/**
	 * 顧客契約ライフサポート情報の和名変換を行います。
	 *
	 * @param lifeInfo 顧客契約情報マスタエンティティ
	 * @param convertMap 和名変換用Map
	 * @return RcpMKokyakuKeiyaku 顧客契約情報マスタエンティティ
	 */
	public RcpMKokyakuKLife convOfLifeInfo(RcpMKokyakuKLife lifeInfo, Map<String, Map<String, RcpMComCd>> convertMap) {

		if (lifeInfo != null) {

			// 申込区分の和名変換
			Map<String, RcpMComCd> moshikomiKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_ENTRY_KBN);
			// 建物形態区分の和名変換
			Map<String, RcpMComCd> tatemonoTypeMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_BUILD_FORM);
			// 入会費用区分の和名変換
			Map<String, RcpMComCd> nyukaiHiyoKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_ADMISSION_COST);
			// 入居区分の和名変換
			Map<String, RcpMComCd> nyukyoKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_NYUKYO_KBN);
			// 性別の和名変換
			Map<String, RcpMComCd> sexKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_SEX);
			// メールアドレス区分の和名変換
			Map<String, RcpMComCd> mailAddressKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_MAIL_ADDRESS_KBN);
			// 続柄（同居人続柄名１，同居人続柄名２）の和名変換
			Map<String, RcpMComCd> zokugaraMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_RELATION);

			// 申込区分取得
			if (moshikomiKbnMap != null && moshikomiKbnMap.containsKey(lifeInfo.getMoshikomiKbn())) {
				lifeInfo.setMoshikomiKbnNm(moshikomiKbnMap.get(lifeInfo.getMoshikomiKbn()).getComVal());
			}

			// 建物形態区分取得
			if (tatemonoTypeMap != null && tatemonoTypeMap.containsKey(lifeInfo.getTatemonoType())) {
				lifeInfo.setTatemonoTypeNm(tatemonoTypeMap.get(lifeInfo.getTatemonoType()).getComVal());
			}

			// 入会費用区分取得
			if (nyukaiHiyoKbnMap != null && nyukaiHiyoKbnMap.containsKey(lifeInfo.getNyukaiHiyoKbn())) {
				lifeInfo.setNyukaiHiyoKbnNm(nyukaiHiyoKbnMap.get(lifeInfo.getNyukaiHiyoKbn()).getComVal());
			}

			// 入居区分取得
			if (nyukyoKbnMap != null && nyukyoKbnMap.containsKey(lifeInfo.getNyukyoKbn())) {
				lifeInfo.setNyukyoKbnNm(nyukyoKbnMap.get(lifeInfo.getNyukyoKbn()).getComVal());
			}

			// 性別取得
			if (sexKbnMap != null && sexKbnMap.containsKey(lifeInfo.getSexKbn())) {
				lifeInfo.setSexKbnNm(sexKbnMap.get(lifeInfo.getSexKbn()).getComVal());
			}

			// メールアドレス区分取得
			if (mailAddressKbnMap != null && mailAddressKbnMap.containsKey(lifeInfo.getMailAddressKbn())) {
				lifeInfo.setMailAddressKbnNm(mailAddressKbnMap.get(lifeInfo.getMailAddressKbn()).getComVal());
			}

			// 続柄取得
			if (zokugaraMap != null && zokugaraMap.containsKey(lifeInfo.getKinkyuTsudukiKbn())) {
				lifeInfo.setKinkyuZokugaraNm(zokugaraMap.get(lifeInfo.getKinkyuTsudukiKbn()).getComVal());
			}

			// 同居人続柄名１取得
			if (zokugaraMap != null && zokugaraMap.containsKey(lifeInfo.getDokyoTsudukiKbn1())) {
				lifeInfo.setDoukyoZokugara1Nm(zokugaraMap.get(lifeInfo.getDokyoTsudukiKbn1()).getComVal());
			}

			// 同居人続柄名２取得
			if (zokugaraMap != null && zokugaraMap.containsKey(lifeInfo.getDokyoTsudukiKbn2())) {
				lifeInfo.setDoukyoZokugara2Nm(zokugaraMap.get(lifeInfo.getDokyoTsudukiKbn2()).getComVal());
			}
		}

		return lifeInfo;
	}
}