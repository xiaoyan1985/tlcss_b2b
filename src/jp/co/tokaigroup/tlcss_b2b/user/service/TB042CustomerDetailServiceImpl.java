package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMBukkenShiteiGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFBukkenDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFKojinDao;
import jp.co.tokaigroup.reception.dao.RcpTBukkenShiteiGyoshaDao;
import jp.co.tokaigroup.reception.entity.RcpMBukkenShiteiGyosha;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFBukken;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKojin;
import jp.co.tokaigroup.reception.entity.RcpTBukkenShiteiGyosha;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB042CustomerDetailModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物件・入居者詳細サービス実装クラス。
 *
 * @author v138130
 * @version 4.0 2014/06/06
 * @version 4.1 2015/11/04 J.Matsuba 和名変換Map取得の修正
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB042CustomerDetailServiceImpl extends TLCSSB2BBaseService
		implements TB042CustomerDetailService {

	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** 顧客付随物件情報マスタDAO */
	@Autowired
	private RcpMKokyakuFBukkenDao kokyakuBukkenDao;

	/** 物件指定業者マスタDAO */
	@Autowired
	private RcpMBukkenShiteiGyoshaDao bukkenShiteiGyoshaMasterDao;

	/** 物件指定業者テーブルDAO */
	@Autowired
	private RcpTBukkenShiteiGyoshaDao bukkenShiteiGyoshaTableDao;

	/** 顧客付随個人情報マスタDAO */
	@Autowired
	private RcpMKokyakuFKojinDao kokyakuKojinDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 物件指定業者マスタのソート順 */
	private static final String BUKKEN_SHITEI_GYOSHA_ORDER_BY = " ORDER BY HYOJI_JUN ASC";

	/**
	 * 初期表示を行います。
	 *
	 * @param model 物件・入居者詳細画面モデル
	 * @return 物件・入居者詳細画面モデル
	 * @throws ValidationException 顧客情報が取得出来ない場合
	 */
	public TB042CustomerDetailModel getInitInfo(TB042CustomerDetailModel model)
	throws ValidationException {

		// １．初期表示パラメータチェック
		if (StringUtils.isBlank(model.getKokyakuId())) {
			// パラメータの顧客ＩＤが取得できない場合エラー
			throw new ApplicationException("顧客ID不正：パラメータの顧客ID" );
		}

		// ２．初期情報取得処理
		// 設定用Entity
		RcpMKokyaku kokyakuEntity = new RcpMKokyaku();						// 顧客マスタEntity（データ取得用）
		RcpMKokyakuFBukken kokyakuBukkenEntity = new RcpMKokyakuFBukken();	// 顧客付随物件情報マスタEntity（データ取得用）
		RcpMKokyakuFKojin kokyakuKojinEntity = new RcpMKokyakuFKojin();		// 顧客付随個人情報マスタEntity（データ取得用）

		// 画面表示項目
		// 顧客基本情報取得
		kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());

		// 顧客付随情報取得
		String kokyakuId = model.getKokyakuId();	// パラメータの顧客ID

		if (RcpMKokyaku.KOKYAKU_KBN_BUKKEN.equals(kokyakuEntity.getKokyakuKbn())) {
			// 顧客マスタEntityの顧客区分が「3：物件」の場合、顧客付随物件情報マスタEntityを取得する
			kokyakuBukkenEntity = kokyakuBukkenDao.selectByPrimaryKey(kokyakuId);

			// ソート順の設定
			bukkenShiteiGyoshaMasterDao.setOrderBy(BUKKEN_SHITEI_GYOSHA_ORDER_BY);

			// 物件指定業者マスタEntityリストの取得
			List<RcpMBukkenShiteiGyosha> bukkenShiteiGyoshaMasterList = bukkenShiteiGyoshaMasterDao.selectAll();

			// 物件指定業者テーブルEntityリストの取得
			List<RcpTBukkenShiteiGyosha> bukkenShiteiGyoshaTableList = bukkenShiteiGyoshaTableDao.selectBy(null, kokyakuId);

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

		} else if (RcpMKokyaku.KOKYAKU_KBN_NYUKYOSHA.equals(kokyakuEntity.getKokyakuKbn())) {
			// 顧客マスタEntityの顧客区分が「4：入居者・個人」の場合、顧客付随個人情報マスタEntityを取得する
			kokyakuKojinEntity = kokyakuKojinDao.selectByPrimaryKey(kokyakuId);

		} else {
			// 上記以外の場合、エラー画面に遷移
			throw new ApplicationException("顧客区分が不正です。" );
		}

		// 和名変換処理は、顧客付随物件情報マスタEntityが存在するのみ場合行う
		// ※顧客区分が「3：物件」かつ、データ取得出来た場合
		if (kokyakuBukkenEntity != null) {
			// 和名変換用Map取得
			Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM);
			Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);

			// 和名変換処理
			Map<String, RcpMComCd> mgrFormMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM);
			String kanriKeitaiKbn = kokyakuBukkenEntity.getKanriKeitaiKbn();
			if (StringUtils.isNotBlank(kanriKeitaiKbn) && mgrFormMap.containsKey(kanriKeitaiKbn)) {
				// 管理形態の名称を取得
				kokyakuBukkenEntity.setKanriKeitaiKbnNm(mgrFormMap.get(kanriKeitaiKbn).getExternalSiteVal());
			}

			// 築年数の表示用設定(「YYYY/MM」形式へ変換)
			kokyakuBukkenEntity.setChikuNengetsuDisplay(DateUtil.yyyymmPlusSlash(kokyakuBukkenEntity.getChikuNengetsu()));
		}

		// model にEntityデータを設定
		model.setKokyakuEntity(kokyakuEntity);
		model.setKokyakuBukkenEntity(kokyakuBukkenEntity);
		model.setKokyakuKojinEntity(kokyakuKojinEntity);

		return model;
	}

}

