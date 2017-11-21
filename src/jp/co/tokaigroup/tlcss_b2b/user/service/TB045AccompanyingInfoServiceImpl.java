package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMBukkenShiteiGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFBukkenDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFKanriDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFKojinDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFOoyaDao;
import jp.co.tokaigroup.reception.dao.RcpTBukkenShiteiGyoshaDao;
import jp.co.tokaigroup.reception.entity.RcpMBukkenShiteiGyosha;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFBukken;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKanri;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKojin;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFOoya;
import jp.co.tokaigroup.reception.entity.RcpTBukkenShiteiGyosha;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB045AccompanyingInfoModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 顧客付随情報登録サービス実装クラス。
 *
 * @author k003316
 * @version 1.0 2015/08/03
 * @version 1.1 2015/11/04 J.Matsuba 物件指定業者に関する処理追加
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB045AccompanyingInfoServiceImpl extends TLCSSB2BBaseService
		implements TB045AccompanyingInfoService {
	
	/** リセプション共通マスタDAO */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;
	
	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;
	
	/** 顧客付随物件情報マスタDAO */
	@Autowired
	private RcpMKokyakuFBukkenDao kokyakuFBukkenDao;

	/** 物件指定業者マスタDAO */
	@Autowired
	private RcpMBukkenShiteiGyoshaDao bukkenShiteiGyoshaMasterDao;

	/** 物件指定業者テーブルDAO */
	@Autowired
	private RcpTBukkenShiteiGyoshaDao bukkenShiteiGyoshaTableDao;

	/** 顧客付随管理会社情報マスタDAO */
	@Autowired
	private RcpMKokyakuFKanriDao kokyakuFKanriDao;

	/** 顧客付随個人情報マスタDAO */
	@Autowired
	private RcpMKokyakuFKojinDao kokyakuFKojinDao;

	/** 顧客付随大家情報マスタDAO */
	@Autowired
	private RcpMKokyakuFOoyaDao kokyakuFOoyaDao;

	/** 物件指定業者マスタのソート順 */
	private static final String BUKKEN_SHITEI_GYOSHA_ORDER_BY = " ORDER BY HYOJI_JUN ASC";

	/**
	 * 初期表示を行います。
	 *
	 * @param model 顧客詳細付随情報モデル
	 * @return 顧客詳細付随情報モデル
	 */
	public TB045AccompanyingInfoModel getInitInfo(TB045AccompanyingInfoModel model) {
		
		// 顧客基本情報を取得する
		RcpMKokyaku kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		model.setKokyakuEntity(kokyakuEntity);
		
		// 顧客区分が管理会社の場合
		if (model.isRealEstate()) {
			
			// 顧客付随管理会社
			RcpMKokyakuFKanri kokyakuFKanri = kokyakuFKanriDao.selectByPrimaryKey(model.getKokyakuId());
			model.setFuzuiKanriInfo(kokyakuFKanri);
			
		// 顧客区分が物件の場合
		} else if (model.isProperty()) {
			
			// 顧客付随物件
			RcpMKokyakuFBukken kokyakuFBukken = kokyakuFBukkenDao.selectByPrimaryKey(model.getKokyakuId());
			
			// 表示用の築年月に変換
			if (kokyakuFBukken != null && StringUtils.isNotBlank(kokyakuFBukken.getChikuNengetsu())) {
				
				kokyakuFBukken.setChikuNengetsuDisplay(
						DateUtil.yyyymmPlusSlash(kokyakuFBukken.getChikuNengetsu()));
			}
			
			model.setFuzuiBukkenInfo(kokyakuFBukken);

			// ソート順の設定
			bukkenShiteiGyoshaMasterDao.setOrderBy(BUKKEN_SHITEI_GYOSHA_ORDER_BY);

			// 物件指定業者マスタEntityリストの取得
			List<RcpMBukkenShiteiGyosha> bukkenShiteiGyoshaMasterList = bukkenShiteiGyoshaMasterDao.selectAll();

			// 物件指定業者テーブルEntityリストの取得
			List<RcpTBukkenShiteiGyosha> bukkenShiteiGyoshaTableList = bukkenShiteiGyoshaTableDao.selectBy(null, model.getKokyakuId());

			// 表示用Entityリスト
			List<RcpTBukkenShiteiGyosha> displayBukkenShiteiGyoshaTableList = new ArrayList<RcpTBukkenShiteiGyosha>();

			// 物件指定業者マスタと物件指定業者テーブルの紐付け
			for (RcpMBukkenShiteiGyosha bukkenShiteiGyoshaMaster : bukkenShiteiGyoshaMasterList) {
				for (RcpTBukkenShiteiGyosha bukkenShiteiGyoshaTable : bukkenShiteiGyoshaTableList) {
					if (bukkenShiteiGyoshaMaster.getGyoshuCd().equals(bukkenShiteiGyoshaTable.getGyoshuCd())) {
						// 業種コードが等しい場合、表示用Entityに各情報を格納
						RcpTBukkenShiteiGyosha displayBukkenShiteiGyosha = new RcpTBukkenShiteiGyosha();

						displayBukkenShiteiGyosha.setGyoshuNm(bukkenShiteiGyoshaMaster.getGyoshuNm());
						displayBukkenShiteiGyosha.setName(bukkenShiteiGyoshaTable.getName());
						displayBukkenShiteiGyosha.setTelNo(bukkenShiteiGyoshaTable.getTelNo());
						displayBukkenShiteiGyosha.setFaxNo(bukkenShiteiGyoshaTable.getFaxNo());
						displayBukkenShiteiGyosha.setMailAddress(bukkenShiteiGyoshaTable.getMailAddress());
						displayBukkenShiteiGyosha.setBiko(bukkenShiteiGyoshaTable.getBiko());

						// 表示用Listに格納
						displayBukkenShiteiGyoshaTableList.add(displayBukkenShiteiGyosha);

						break;
					}
				}
			}

			model.setBukkenShiteiGyoshaTableList(displayBukkenShiteiGyoshaTableList);

		// 顧客区分が大家の場合
		} else if (model.isLandlord()) {
			
			// 顧客付随大家情報の取得
			RcpMKokyakuFOoya kokyakuFOoya = kokyakuFOoyaDao.selectByPrimaryKey(model.getKokyakuId());
			model.setFuzuiOoyaInfo(kokyakuFOoya);
			
		// 顧客区分が入居者・個人の場合
		} else if (model.isTenant()) {
			
			// 顧客付随個人
			RcpMKokyakuFKojin kokyakuFKojin = kokyakuFKojinDao.selectByPrimaryKey(model.getKokyakuId());
			model.setFuzuiKojinInfo(kokyakuFKojin);
			
		// 顧客区分が想定外の場合
		} else {
			throw new ApplicationException("顧客区分不正：" + model.getKokyakuEntity().getKokyakuKbn());
		}
		
		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM);
		Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 管理形態の和名変換
		Map<String, RcpMComCd> mgrFormMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM);
		
		// 物件情報が取得出来ている場合は変換
		if (model.isProperty() && model.getFuzuiBukkenInfo() != null) {
			
			RcpMKokyakuFBukken bukkenInfo = model.getFuzuiBukkenInfo();
			// 管理形態名の設定
			if (mgrFormMap.containsKey(bukkenInfo.getKanriKeitaiKbn())) {
				bukkenInfo.setKanriKeitaiKbnNm(mgrFormMap.get(bukkenInfo.getKanriKeitaiKbn()).getExternalSiteVal());
			}
			model.setFuzuiBukkenInfo(bukkenInfo);
		}
		
		return model;
	}
}