package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.common.logic.CheckToPublishToiawaseLogic;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn1Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn2Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn3Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn4Dao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuWithNoIdDao;
import jp.co.tokaigroup.reception.dao.RcpTMailRirekiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.dao.SosMUserInfoDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.SosMUserInfo;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB024InquiryHistoryEntryModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 問い合わせ履歴登録サービス実装クラス。
 *
 * @author v145527
 * @version 1.0 2015/08/28
 *
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB024InquiryHistoryEntryServiceImpl extends TLCSSB2BBaseService
		implements TB024InquiryHistoryEntryService {

	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** リセプション問い合わせテーブルDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** リセプション問い合わせ履歴テーブルDAO */
	@Autowired
	private RcpTToiawaseRirekiDao toiawaseRirekiDao;

	/** ＩＤ無し顧客テーブルDAO */
	@Autowired
	private RcpTKokyakuWithNoIdDao kokyakuWithNoIdDao;

	/** 共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** リセプション問い合わせ区分１マスタDAO */
	@Autowired
	private RcpMToiawaseKbn1Dao toiawaseKbn1Dao;

	/** リセプション問い合わせ区分２マスタDAO */
	@Autowired
	private RcpMToiawaseKbn2Dao toiawaseKbn2Dao;

	/** リセプション問い合わせ区分３マスタDAO */
	@Autowired
	private RcpMToiawaseKbn3Dao toiawaseKbn3Dao;

	/** リセプション問い合わせ区分４マスタDAO */
	@Autowired
	private RcpMToiawaseKbn4Dao toiawaseKbn4Dao;

	/** リセプション状況区分マスタ */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;

	/** SOSユーザ付随情報マスタ */
	@Autowired
	private SosMUserInfoDao userInfoDao;

	/** NATOSパスワードＭDAO */
	@Autowired
	private NatosMPasswordDao natosPswdDao;
	
	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** 問い合わせ公開チェックロジック */
	@Autowired
	private CheckToPublishToiawaseLogic toiawaseCheckLogic;

	/** 委託会社関連チェックロジック */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;

	/** 公開メール送信履歴マスタDAO */
	@Autowired
	private RcpTMailRirekiDao mailRirekiDao;
	

	/**
	 * 初期表示を行います。
	 * （問い合わせ情報を取得します。）
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 * @return 問い合わせ履歴登録画面モデル
	 */
	public TB024InquiryHistoryEntryModel getInitInfo(TB024InquiryHistoryEntryModel model) {

		// 問い合わせ情報取得
		RcpTToiawase toiawaseEntity = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if(toiawaseEntity == null){
			// 問い合わせ情報が取得できない場合エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		// ＩＤなし顧客
		RcpTKokyakuWithNoId kokyakuWithoutId = null;

		if (StringUtils.isNotBlank(toiawaseEntity.getKokyakuId())) {
			// 顧客ＩＤが存在する場合
			// 顧客基本情報取得
			RcpMKokyaku kokyaku = kokyakuKihon.getKokyakuInfo(toiawaseEntity.getKokyakuId());
			model.setKokyakuEntity(kokyaku);
		} else {
			// ＩＤ無し顧客の場合
			kokyakuWithoutId = kokyakuWithNoIdDao.selectByPrimaryKey(model.getToiawaseNo());
			if (kokyakuWithoutId == null) {
				// 取得できない場合は、排他エラー
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			model.setKokyakuInfoWithoutId(kokyakuWithoutId);
		}
		
		//最終履歴情報取得
		RcpTToiawaseRireki lastToiawaseRireki = toiawaseRirekiDao.getLastToiawaseRireki(model.getToiawaseNo(), null);
		
		// 共通コードマスタから取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
							RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
							RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA,
							RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU,
							RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 依頼者Map
		Map<String, RcpMComCd> iraishaMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA);
		// 依頼者区分Map
		Map<String, RcpMComCd> iraishaKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// 依頼者有無区分Map
		Map<String, RcpMComCd> iraiUmuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU);
		// 受付形態Map
		Map<String, RcpMComCd> uketsukeKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		
		// 状況区分マスタからの取得
		Map<String, String> jokyoKbnMap = jokyoKbnDao.selectAllAsMap();
		
		// 問い合わせ区分Map
		Map<String, String> toiawaseKbn1Map = toiawaseKbn1Dao.selectAllAsMap();
		Map<String, String> toiawaseKbn2Map = toiawaseKbn2Dao.selectAllAsMap();
		Map<String, String> toiawaseKbn3Map = toiawaseKbn3Dao.selectAllAsMap();
		Map<String, String> toiawaseKbn4Map = toiawaseKbn3Dao.selectAllAsMap();
		
		
		// パスワードマスタの和名変換マップ取得
		List<String> userIdList = new ArrayList<String>();
		if (StringUtils.isNotBlank(model.getToiawaseInfo().getUketsukeshaId())) {
			// 問い合わせ情報の受付者ＩＤ取得
			userIdList.add(model.getToiawaseInfo().getUketsukeshaId());
		}
		if (StringUtils.isNotBlank(model.getToiawaseInfo().getLastUpdId())
			&& !userIdList.contains(model.getToiawaseInfo().getLastUpdId())) {
			// 問い合わせ情報の最終更新者ＩＤ取得
			userIdList.add(model.getToiawaseInfo().getLastUpdId());	
		}
		

		// 和名変換マップ取得
		Map<String, String> userMap = new HashMap<String, String>();
		if (!userIdList.isEmpty()) {
			userMap = natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));	
		}

		// 和名変換処理
		// 依頼者名
		if (RcpTToiawase.IRAI_FLG_ONAJI.equals(model.getToiawaseInfo().getIraishaFlg())) {
			// 依頼者フラグが「0:顧客基本情報と同じ」場合
			// 「顧客基本情報と同じ」を表示
			if (iraishaMap.containsKey(model.getToiawaseInfo().getIraishaFlg())) {
				toiawaseEntity.setIraishaNm(iraishaMap.get(toiawaseEntity.getIraishaFlg()).getExternalSiteVal());
			}
		} else {
			// 依頼者フラグが「1:異なる」場合
			// 依頼者区分に対する名称を表示
			if (iraishaKbnMap.containsKey(model.getToiawaseInfo().getIraishaKbn())) {
				toiawaseEntity.setIraishaNm(iraishaKbnMap.get(toiawaseEntity.getIraishaKbn()).getExternalSiteVal() + " " + toiawaseEntity.getIraishaNm());
			}
		}
		// 受付形態名
		if (uketsukeKeitaiMap != null
				&& StringUtils.isNotBlank(toiawaseEntity.getUketsukeKeitaiKbn())
				&& uketsukeKeitaiMap.containsKey(toiawaseEntity.getUketsukeKeitaiKbn())) {
			toiawaseEntity.setUketsukeKeitaiKbnNm(uketsukeKeitaiMap.get(toiawaseEntity.getUketsukeKeitaiKbn()).getExternalSiteVal());
		}
		// 依頼有無区分名
		if (iraiUmuMap != null
				&& StringUtils.isNotBlank(toiawaseEntity.getIraiUmuKbn())
				&& iraiUmuMap.containsKey(toiawaseEntity.getIraiUmuKbn())) {
			toiawaseEntity.setIraiUmuKbnNm(iraiUmuMap.get(toiawaseEntity.getIraiUmuKbn()).getExternalSiteVal());
		}
		// 問い合わせ区分1名
		if (toiawaseKbn1Map != null
				&& StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn1())
				&& toiawaseKbn1Map.containsKey(toiawaseEntity.getToiawaseKbn1())) {
			toiawaseEntity.setToiawaseKbn1Nm(toiawaseKbn1Map.get(toiawaseEntity.getToiawaseKbn1()));
		}
		// 問い合わせ区分2名
		if (toiawaseKbn2Map != null
				&& StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn2())
				&& toiawaseKbn2Map.containsKey(toiawaseEntity.getToiawaseKbn2())) {
			toiawaseEntity.setToiawaseKbn2Nm(toiawaseKbn2Map.get(toiawaseEntity.getToiawaseKbn2()));
		}
		// 問い合わせ区分3名
		if (toiawaseKbn3Map != null
				&& StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn3())
				&& toiawaseKbn3Map.containsKey(toiawaseEntity.getToiawaseKbn3())) {
			toiawaseEntity.setToiawaseKbn3Nm(toiawaseKbn3Map.get(toiawaseEntity.getToiawaseKbn3()));
		}
		// 問い合わせ区分4名
		if (toiawaseKbn4Map != null
				&& StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn4())
				&& toiawaseKbn4Map.containsKey(toiawaseEntity.getToiawaseKbn4())) {
			toiawaseEntity.setToiawaseKbn4Nm(toiawaseKbn4Map.get(toiawaseEntity.getToiawaseKbn4()));
		}

		if (lastToiawaseRireki != null) {
			if (jokyoKbnMap != null
					&& StringUtils.isNotBlank(lastToiawaseRireki.getJokyoKbn())
					&& jokyoKbnMap.containsKey(lastToiawaseRireki.getJokyoKbn())) {
				// 最終履歴状況区分名
				toiawaseEntity.setLastJokyoKbnNm(jokyoKbnMap.get(lastToiawaseRireki.getJokyoKbn()));
			}
			// 最終履歴日付
			toiawaseEntity.setLastRirekiYmd(lastToiawaseRireki.getUketsukeYmd());
			// 最終履歴時間
			toiawaseEntity.setLastRirekiJikan(DateUtil.hhmmPlusColon(lastToiawaseRireki.getUketsukeJikan()));
		}

		// 受付者名
		if (StringUtils.isBlank(toiawaseEntity.getUketsukeshaNm())) {
			// 受付者名がNULLの場合、受付者ＩＤを和名変換して表示
			if (userMap != null && StringUtils.isNotBlank(toiawaseEntity.getUketsukeshaId())
					&& userMap.containsKey(toiawaseEntity.getUketsukeshaId())) {
				toiawaseEntity.setUketsukeNm(userMap.get(toiawaseEntity.getUketsukeshaId()));
			}
		} else {
			// 受付者名がNULLでなければ、受付者名を表示
			toiawaseEntity.setUketsukeNm(toiawaseEntity.getUketsukeshaNm());
		}

		if (userMap != null) {
			// 最終更新者名（最終更新者ＩＤから和名変換）
			toiawaseEntity.setLastUpdIdToNm(userMap.get(toiawaseEntity.getLastUpdId()));
		}
		
		// ＩＤなし顧客の顧客区分
		if(kokyakuWithoutId != null) {
			if (iraishaKbnMap != null
					&& StringUtils.isNotBlank(kokyakuWithoutId.getKokyakuKbn())
					&& iraishaKbnMap.containsKey(kokyakuWithoutId.getKokyakuKbn())) {
				kokyakuWithoutId.setKokyakuKbnNm(iraishaKbnMap.get(kokyakuWithoutId.getKokyakuKbn()).getExternalSiteVal());
			}
		}
		// 受付時間（コロンをつける）
		toiawaseEntity.setUketsukeJikan(DateUtil.hhmmPlusColon(toiawaseEntity.getUketsukeJikan()));
		

		// 問い合わせ区分１リストの取得
		List<RcpMToiawaseKbn1> toiawaseKbn1List = toiawaseKbn1Dao.selectAll();
		// 問い合わせ区分２リストの取得
		List<RcpMToiawaseKbn2> toiawaseKbn2List = toiawaseKbn2Dao.selectAll();
		// 問い合わせ区分３リストの取得
		List<RcpMToiawaseKbn3> toiawaseKbn3List = toiawaseKbn3Dao.selectAll();
		// 問い合わせ区分４リストの取得
		List<RcpMToiawaseKbn4> toiawaseKbn4List = toiawaseKbn4Dao.selectAll();

		// 状況区分リストの取得
		// 引数の画面区分リストの生成
		List<String> gamenKbnList = new ArrayList<String>();
		gamenKbnList.add(Constants.GAMEN_KBN_TOIAWASE_ENTRY);
		gamenKbnList.add(Constants.GAMEN_KBN_ZENGAMEN);

		List<RcpMJokyoKbn> jokyoKbnList = jokyoKbnDao.selectByGamenKbn(gamenKbnList, "");

		// 担当者リストの取得
		List<String> sosShokumuKbnList = new ArrayList<String>();
		sosShokumuKbnList.add(Constants.SHOKUMU_KBN_SOS_KANRISHA);
		sosShokumuKbnList.add(Constants.SHOKUMU_KBN_SV);
		sosShokumuKbnList.add(Constants.SHOKUMU_KBN_OP);
		// SOSのユーザ付随情報マスタから取得
		List<SosMUserInfo> userInfoList = userInfoDao.selectByShokumuKbnList(sosShokumuKbnList);

		// 引数のログインＩＤリストの生成
		List<String> loginIdList = new ArrayList<String>();
		for (SosMUserInfo userInfo : userInfoList) {
			loginIdList.add(userInfo.getUserId());
		}

		List<NatosMPassword> tantoshaList = new ArrayList<NatosMPassword>();
		if (loginIdList.size() > 0) {
			tantoshaList = natosPswdDao.selectByList(loginIdList, "");
		}

		// 依頼者区分リスト（問い合わせ）
		model.setToiawaseIraishaKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN));
		// 依頼者区分リスト（依頼者）
		model.setKokyakuIraishaKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN));
		// 依頼者フラグリスト
		model.setIraishaFlgList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA));
		// 問い合わせ区分１リスト
		model.setToiawaseKbn1List(toiawaseKbn1List);
		// 問い合わせ区分２リスト
		model.setToiawaseKbn2List(toiawaseKbn2List);
		// 問い合わせ区分３リスト
		model.setToiawaseKbn3List(toiawaseKbn3List);
		// 問い合わせ区分４リスト
		model.setToiawaseKbn4List(toiawaseKbn4List);
		// 状況区分リスト
		model.setJokyoKbnList(jokyoKbnList);
		// 担当者リスト
		model.setTantoshaList(tantoshaList);
		// 依頼有無リスト
		model.setIraiUmuList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU));
		// 受付形態リスト
		model.setUketsukeKeitaiKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI));
		
		// 問い合わせEntity
		model.setToiawaseInfo(toiawaseEntity);

		return model;
	}
	
	/**
	 * 初期表示を行います。（更新画面表示用）
	 * （問い合わせ履歴情報を取得します。）
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 * @return 問い合わせ履歴登録画面モデル
	 */
	public TB024InquiryHistoryEntryModel getInitInfoForUpdate(TB024InquiryHistoryEntryModel model) {
		
		// パラメータの問い合わせＮＯが取得できた場合
		// 問い合わせ情報を取得
		model.setToiawaseInfo(toiawaseDao.selectByPrimaryKey(model.getToiawaseNo()));
		
		// 問い合わせ情報が取得できない場合
		if (model.getToiawaseInfo() == null) {
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		// 問い合わせ情報の顧客ＩＤを、パラメータの顧客ＩＤに設定
		model.setKokyakuId(model.getToiawaseInfo().getKokyakuId());
		// 更新の排他で使用するため、更新日を保持
		model.setToiawaseUpdDt(model.getToiawaseInfo().getUpdDt());

		// 問い合わせ情報の顧客ＩＤが取得できない場合
		if (StringUtils.isBlank(model.getToiawaseInfo().getKokyakuId())) {
			// ＩＤ無し顧客情報の取得
			model.setKokyakuInfoWithoutId(kokyakuWithNoIdDao.selectByPrimaryKey(model.getToiawaseNo()));
		}

		// 初期表示情報取得
		model = this.getInitInfo(model);
		
		// 問い合わせ履歴情報の取得
		RcpTToiawaseRireki toiawaseRireki =
			toiawaseRirekiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (toiawaseRireki == null) {
			// 問い合わせ履歴情報がない場合、排他エラー
			model.setInitError(true);

			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// パスワードマスタの和名変換マップ取得
		List<String> userIdList = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(model.getToiawaseInfo().getLastUpdId())) {
			
			userIdList.add(model.getToiawaseInfo().getLastUpdId());	// 問い合わせ情報の最終更新者ＩＤ
		}
		if (StringUtils.isNotBlank(toiawaseRireki.getLastUpdId())
				&& !userIdList.contains(toiawaseRireki.getLastUpdId())) {
			
			userIdList.add(toiawaseRireki.getLastUpdId());	// 問い合わせ履歴情報の最終更新者ＩＤ
		}
		if (StringUtils.isNotBlank(toiawaseRireki.getTantoshaId())
				&& !userIdList.contains(toiawaseRireki.getTantoshaId())) {
			
			userIdList.add(toiawaseRireki.getTantoshaId());	// 問い合わせ履歴情報の担当者ＩＤ
		}
	
		// 和名変換マップ取得
		Map<String, String> userMap = new HashMap<String, String>();
		if (!userIdList.isEmpty()) {
			userMap = natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));	
		}
		
		// 和名変換処理
		// 担当者名がNULLの場合、担当者ＩＤを和名変換して表示(NULLでなければ、そのまま表示)
		if (StringUtils.isBlank(toiawaseRireki.getTantoshaNm())) {
			if (StringUtils.isNotBlank(toiawaseRireki.getTantoshaId())
					&& userMap != null
					&& userMap.containsKey(toiawaseRireki.getTantoshaId())) {
				// 担当者名
				toiawaseRireki.setTantoshaNm(userMap.get(toiawaseRireki.getTantoshaId()));
			}
		}
		if (userMap != null) {
			// 最終更新者（最終更新者ＩＤから和名変換）
			toiawaseRireki.setLastUpdIdToNm(userMap.get(toiawaseRireki.getLastUpdId()));
		}
		
		// 公開メール送信履歴情報取得用ＮＯ設定
		model.setMailRirekiNo(model.getToiawaseNo() + "-" + StringUtils.leftPad(model.getToiawaseRirekiNo().toPlainString(), 3, '0'));
		// 公開メール送信履歴情報取得
		RcpTMailRireki mailRireki =
			mailRirekiDao.selectByPrimaryKey(model.getMailRirekiNo());
		model.setMailRireki(mailRireki);
		
		// 変更前の問い合わせ履歴公開フラグを保存
		model.setBeforeToiawaseRirekiKokaiFlg(toiawaseRireki.getToiawaseRirekiKokaiFlg());

		// 画面値復元処理
		if (Constants.ACTION_TYPE_RESTORE.equals(model.getActionType())) {
			// アクションタイプが「restore:画面値復元」の場合
			toiawaseRireki.setUketsukeYmd(model.getToiawaseRirekiInfo().getUketsukeYmd());
			toiawaseRireki.setUketsukeJikan(model.getToiawaseRirekiInfo().getUketsukeJikan());
			toiawaseRireki.setTantoshaNm(model.getToiawaseRirekiInfo().getTantoshaNm());
			toiawaseRireki.setToiawaseKbn1(model.getToiawaseRirekiInfo().getToiawaseKbn1());
			toiawaseRireki.setToiawaseKbn2(model.getToiawaseRirekiInfo().getToiawaseKbn2());
			toiawaseRireki.setToiawaseKbn3(model.getToiawaseRirekiInfo().getToiawaseKbn3());
			toiawaseRireki.setToiawaseKbn4(model.getToiawaseRirekiInfo().getToiawaseKbn4());
			toiawaseRireki.setToiawaseNaiyo(model.getToiawaseRirekiInfo().getToiawaseNaiyo());
			toiawaseRireki.setJokyoKbn(model.getToiawaseRirekiInfo().getJokyoKbn());
			toiawaseRireki.setHoukokuPrintFlg(model.getToiawaseRirekiInfo().getHoukokuPrintFlg());
			toiawaseRireki.setToiawaseRirekiKokaiFlg(model.getToiawaseRirekiInfo().getToiawaseRirekiKokaiFlg());
		}

		model.setToiawaseRirekiInfo(toiawaseRireki);
		
		return model;
	}

	/**
	 * 問い合わせ履歴情報登録を行います。
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 */
	@Transactional(value="txManager")
	public void insertToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model) {
		
		// ユーザ情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 履歴ＮＯの新規発行
		BigDecimal newRirekiNo = toiawaseRirekiDao.selectNewRirekiNo(model.getToiawaseNo());

		// 問い合わせ履歴情報の取得・再設定
		RcpTToiawaseRireki toiawaseRireki = model.getToiawaseRirekiInfo();
		
		// 問い合わせＮＯ
		toiawaseRireki.setToiawaseNo(model.getToiawaseNo());
		// 問い合わせ履歴ＮＯ
		toiawaseRireki.setToiawaseRirekiNo(newRirekiNo);
		// 登録者名
		toiawaseRireki.setCreNm(userContext.getUserName());
		// 最終更新者名
		toiawaseRireki.setLastUpdNm(userContext.getUserName());
		// 担当者ＩＤ
		toiawaseRireki.setTantoshaId(Constants.TORES_APL_ID);
		// 登録会社ＩＤ
		toiawaseRireki.setCreKaishaId(userContext.getKaishaId());
		// 更新会社ＩＤ
		toiawaseRireki.setUpdKaishaId(userContext.getKaishaId());

		model.setToiawaseRirekiInfo(toiawaseRireki);

		// 問い合わせ履歴登録
		toiawaseRirekiDao.insertForTores(toiawaseRireki);

		// 問い合わせテーブルの更新日を更新
		model.getToiawaseInfo().setUpdDt(model.getToiawaseUpdDt());
		model.getToiawaseInfo().setLastUpdId(getUserContext().getLoginId());
		model.getToiawaseInfo().setLastUpdNm(((TLCSSB2BUserContext) getUserContext()).getUserName());
		int ret = toiawaseDao.updateUpdDt(model.getToiawaseInfo());
		if (ret == 0) {
			// 戻りの初期情報取得時に、問い合わせ情報を取得しない
			model.setUpdateError(true);

			// 更新件数が０件の場合、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// 問い合わせ未既読情報更新
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), false);

		// 登録チェック処理（後処理チェック）
		model.setToiawaseRirekiNo(newRirekiNo);
		isValid(model);

		// アクセスログ登録
		tbAccesslogDao.insert(TB024InquiryHistoryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_INSERT, createKensakuJoken(model));
	}

	/**
	 * 問い合わせ履歴情報更新を行います。
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 */
	@Transactional(value="txManager")
	public void updateToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model) {
		
		// ユーザ情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 問い合わせ履歴情報の取得・再設定
		RcpTToiawaseRireki toiawaseRireki = model.getToiawaseRirekiInfo();
		
		// 問い合わせＮＯ
		toiawaseRireki.setToiawaseNo(model.getToiawaseNo());
		// 問い合わせ履歴ＮＯ
		toiawaseRireki.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		// 最終更新者名
		toiawaseRireki.setLastUpdNm(userContext.getUserName());
		// 最終更新会社ＩＤ
		toiawaseRireki.setUpdKaishaId(userContext.getKaishaId());
		// 登録区分が「TLCSS」の場合、担当者名をNULLクリア
		if (model.getToiawaseRirekiInfo().isRegistKbnToTlcss()) {
			toiawaseRireki.setTantoshaNm(null);
		}

		// 問い合わせ履歴情報の更新
		if (toiawaseRirekiDao.updateForTores(toiawaseRireki) == 0) {
			// 戻りの初期情報取得時に、問い合わせ情報を取得しない
			model.setUpdateError(true);

			// 更新件数が０件の場合、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// 問い合わせテーブルの更新日を更新
		model.getToiawaseInfo().setUpdDt(model.getToiawaseUpdDt());
		model.getToiawaseInfo().setLastUpdId(userContext.getLoginId());
		model.getToiawaseInfo().setLastUpdNm(userContext.getUserName());
		if (toiawaseDao.updateUpdDt(model.getToiawaseInfo()) == 0) {
			// 戻りの初期情報取得時に、問い合わせ情報を取得しない
			model.setUpdateError(true);

			// 更新件数が０件の場合、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// 問い合わせ未既読情報更新
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), false);

		// 更新チェック処理（後処理チェック）
		isValid(model);

		// アクセスログ登録
		tbAccesslogDao.insert(TB024InquiryHistoryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_UPDATE, createKensakuJoken(model));
	}

	/**
	 * 問い合わせ履歴情報削除を行います。
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 */
	@Transactional(value="txManager")
	public void deleteToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model) {
		// ユーザ情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 問い合わせ履歴情報退避
		if (toiawaseRirekiDao.insertTaihi(model.getToiawaseRirekiInfo()) == 0) {
			// 戻りの初期情報取得時に、問い合わせ情報を取得しない
			model.setUpdateError(true);

			// 更新件数が０件の場合、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		try {
			// 問い合わせ履歴情報削除
			toiawaseRirekiDao.delete(model.getToiawaseRirekiInfo());
		} catch (DataIntegrityViolationException e) {
			// 戻りの初期情報取得時に、問い合わせ情報を取得しない
			model.setUpdateError(true);

			// 外部キー参照エラーの場合
			throw new ValidationException(new ValidationPack().addError("MSG0033"));
		}

		// 問い合わせテーブルの更新日を更新
		model.getToiawaseInfo().setUpdDt(model.getToiawaseUpdDt());
		model.getToiawaseInfo().setLastUpdId(userContext.getLoginId());
		model.getToiawaseInfo().setLastUpdNm(userContext.getUserName());
		if (toiawaseDao.updateUpdDt(model.getToiawaseInfo()) == 0) {
			// 戻りの初期情報取得時に、問い合わせ情報を取得しない
			model.setUpdateError(true);

			// 更新件数が０件の場合、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// 問い合わせ未既読情報更新
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), false);

		// アクセスログ登録
		tbAccesslogDao.insert(TB024InquiryHistoryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_DELETE, createKensakuJoken(model));
	}

	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 * @return 検索条件
	 */
	private String createKensakuJoken(TB024InquiryHistoryEntryModel model) {
		String kensakuJoken = "";
		StringBuilder accesslog = new StringBuilder("");

		if (model.isInsert() || model.isUpdate()) {
			// 登録、更新の場合
			NullExclusionToStringBuilder toiawaseRirekiEntry =
				new NullExclusionToStringBuilder(
					model.getToiawaseRirekiInfo(),
					NullExclusionToStringBuilder.CSV_STYLE, null, null,
					false, false);

			// 除外する項目
			List<String> excludeFiledList = new ArrayList<String>(Arrays.asList(Constants.EXCLUDE_FIELD_NAMES));
			// 名称部分は、出力しない
			excludeFiledList.add("tantoshaNmToTantoshaId");
			excludeFiledList.add("kaishaNm");
			excludeFiledList.add("jokyoKbnNm");
			excludeFiledList.add("iraiExsits");

			toiawaseRirekiEntry.setExcludeFieldNames((String[]) excludeFiledList.toArray(new String[0]));

			accesslog.append(toiawaseRirekiEntry.toString());

			kensakuJoken = accesslog.toString();
		} else if (model.isDelete()) {
			// 削除の場合
			RcpTToiawaseRireki toiawaseRireki = model.getToiawaseRirekiInfo();

			accesslog.append("toiawaseNo=");
			accesslog.append(toiawaseRireki.getToiawaseNo());
			accesslog.append(",");
			accesslog.append("toiawaseRirekiNo=");
			accesslog.append(toiawaseRireki.getToiawaseRirekiNo().intValue());

			kensakuJoken = accesslog.toString();
		}

		return kensakuJoken;
	}

	/**
	 * 登録,更新チェック処理を行います。
	 *
	 * @param model 問い合わせ履歴登録画面モデル
	 * @throws ValidationException 入力エラーが発生した場合
	 */
	private void isValid(TB024InquiryHistoryEntryModel model) {
		// 依頼情報公開設定可否チェック
		boolean isValidPublish = toiawaseCheckLogic.isValid(
				CheckToPublishToiawaseLogic.CONTENT_TYPE_TOIAWASE_RIREKI,
				model.getToiawaseRirekiInfo().getToiawaseRirekiKokaiFlg(),
				model.getToiawaseNo(), model.getToiawaseRirekiNo());

		if (!isValidPublish) {
			String publishMsg = model.getToiawaseRirekiInfo().isPublished() ? "公開" : "未公開に";

			// 公開設定が無効の場合、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0030", "問い合わせ履歴情報", publishMsg));
		}

	}

	/**
	 * 委託会社関連チェックを行います。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return チェック結果 true:チェックOK、false:チェックNG
	 */
	public boolean isOutsourcer(TB024InquiryHistoryEntryModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// セッションの権限が「40:委託会社SV」または「41:委託会社OP」の場合
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			return outsourcerValidationLogic.isValid(userContext.getKaishaId(), model.getToiawaseInfo().getKokyakuId());
		} else {
			return true;
		}
	}
}
