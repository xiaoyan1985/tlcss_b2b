package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.FileUploadLogic;
import jp.co.tokaigroup.reception.common.logic.CheckToPublishToiawaseLogic;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn1Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn2Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn3Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn4Dao;
import jp.co.tokaigroup.reception.dao.RcpTBillScheduleDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuWithNoIdDao;
import jp.co.tokaigroup.reception.dao.RcpTMailRirekiDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyohiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseFileDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpTBillSchedule;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTSagyohi;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseFile;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB023InquiryEntryModel;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 問い合わせ登録サービス実装クラス。
 *
 * @author k003316
 * @version 1.0 2015/08/11
 * @version 1.1 2016/07/15 H.Yamamura サービス種別項目追加
 * @version 1.3 2016/08/08 S.Nakano 請求先情報妥当性チェック追加
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB023InquiryEntryServiceImpl extends TLCSSB2BBaseService
		implements TB023InquiryEntryService {
	// プロパティファイルから取得
	/** 問い合わせファイルアップロードパス */
	private static final String UPLOAD_PATH_INQUIRY_FILE = ResourceFactory.getResource().getString("UPLOAD_PATH_INQUIRY_FILE");

	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;
	
	/** 問い合わせテーブルDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** リセプション問い合わせ履歴テーブルDAO */
	@Autowired
	private RcpTToiawaseRirekiDao toiawaseRirekiDao;
	
	/** ＩＤ無し顧客テーブルDAO */
	@Autowired
	private RcpTKokyakuWithNoIdDao kokyakuWithoutIdDao;

	/** 依頼情報ＴDAO */
	@Autowired
	private RcpTIraiDao iraiDao;
	
	/** 共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 問い合わせ区分１マスタDAO */
	@Autowired
	private RcpMToiawaseKbn1Dao toiawaseKbn1Dao;

	/** 問い合わせ区分２マスタDAO */
	@Autowired
	private RcpMToiawaseKbn2Dao toiawaseKbn2Dao;

	/** 問い合わせ区分３マスタDAO */
	@Autowired
	private RcpMToiawaseKbn3Dao toiawaseKbn3Dao;

	/** 問い合わせ区分４マスタDAO */
	@Autowired
	private RcpMToiawaseKbn4Dao toiawaseKbn4Dao;

	/** リセプション状況区分マスタ */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;
	
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
	
	/** 問い合わせファイルテーブルDAO */
	@Autowired
	private RcpTToiawaseFileDao toiawaseFileDao;

	/** 顧客契約情報マスタDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** ビル管理スケジュールテーブルDAO */
	@Autowired
	private RcpTBillScheduleDao billScheduleDao;
	
	/** 作業費テーブルDAO */
	@Autowired
	private RcpTSagyohiDao sagyohiDao;
	
	/**
	 * 初期表示情報取得を行います。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel getInitInfo(TB023InquiryEntryModel model) {
		// サービス種別の初期値を不明で設定
		model.setInitServiceShubetsu(RcpTToiawase.SERVICE_SHUBETSU_FUMEI);
		// パラメータの顧客ＩＤが取得できた場合
		if (StringUtils.isNotBlank(model.getKokyakuId())) {
			
			try {
				// 顧客詳細の情報を取得
				model.setKokyakuEntity(kokyakuKihon.getKokyakuInfo(model.getKokyakuId()));
			} catch (ValidationException e) {
				// 共通処理内のメッセージと異なるため、書き換える
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			// 問い合わせ情報が作成されていない場合
			if (model.getToiawaseInfo() == null) {
				// サービス種別の初期値を取得
				String serviceShubetsu = kokyakuKeiyakuDao.getKeiyakuServiceShubetsu(model.getKokyakuId());
				// サービス種別が取得できた場合
				if (StringUtils.isNotBlank(serviceShubetsu)) {
					model.setInitServiceShubetsu(serviceShubetsu);
				}
			}
		} else {
			// 顧客ＩＤ無し表示用のデータ取得
			this.getInitKokyakuInfoWithoutId(model);

		}
		// 問い合わせ表示用データ取得
		return this.getInitToiawaseInfo(model);
	}
	
	/**
	 * サーバーサイドエラー発生時の、初期表示情報を用意します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel parepareInitInfo(TB023InquiryEntryModel model) {
		
		return this.getInitInfo(model);
	}
	
	/**
	 * 初期表示情報取得を行います。（更新画面表示用）
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel getInitInfoForUpdate(TB023InquiryEntryModel model) {
		// パラメータの問い合わせＮＯが取得できた場合
		if (StringUtils.isNotBlank(model.getToiawaseNo())) {
			
			// 問い合わせ情報を取得
			RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
			
			if (toiawase == null) {
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			RcpTKokyakuWithNoId kokyakuWithoutId = null;
			// 問い合わせ情報の顧客ＩＤが取得できない場合
			if (StringUtils.isBlank(toiawase.getKokyakuId())) {
				// ＩＤ無し顧客情報の取得
				kokyakuWithoutId = kokyakuWithoutIdDao.selectByPrimaryKey(model.getToiawaseNo());
			}
			
			// 画面値復元処理
			if (Constants.ACTION_TYPE_RESTORE.equals(model.getActionType())) {
				// アクションタイプが「restore:画面値復元」の場合
				if (kokyakuWithoutId != null && model.getKokyakuInfoWithoutId() != null) {
					// ＩＤ無し顧客情報表示の場合
					kokyakuWithoutId.setKokyakuKbn(model.getKokyakuInfoWithoutId().getKokyakuKbn());
					kokyakuWithoutId.setKokyakuKbnNm(model.getKokyakuInfoWithoutId().getKokyakuNm());
					kokyakuWithoutId.setKokyakuJusho(model.getKokyakuInfoWithoutId().getKokyakuJusho());
					kokyakuWithoutId.setKokyakuTel(model.getKokyakuInfoWithoutId().getKokyakuTel());
				}
				
				// 問い合わせ情報の復元
				toiawase.setIraishaFlg(model.getToiawaseInfo().getIraishaFlg());
				toiawase.setIraishaKbn(model.getToiawaseInfo().getIraishaKbn());
				toiawase.setIraishaNm(model.getToiawaseInfo().getIraishaNm());
				toiawase.setIraishaTel(model.getToiawaseInfo().getIraishaTel());
				toiawase.setIraishaRoomNo(model.getToiawaseInfo().getIraishaRoomNo());
				toiawase.setIraishaSexKbn(model.getToiawaseInfo().getIraishaSexKbn());
				toiawase.setIraishaMemo(model.getToiawaseInfo().getIraishaMemo());
				toiawase.setUketsukeYmd(model.getToiawaseInfo().getUketsukeYmd());
				toiawase.setUketsukeJikan(model.getToiawaseInfo().getUketsukeJikan());
				toiawase.setToiawaseKbn1(model.getToiawaseInfo().getToiawaseKbn1());
				toiawase.setToiawaseKbn2(model.getToiawaseInfo().getToiawaseKbn2());
				toiawase.setToiawaseKbn3(model.getToiawaseInfo().getToiawaseKbn3());
				toiawase.setToiawaseKbn4(model.getToiawaseInfo().getToiawaseKbn4());
				toiawase.setUketsukeKeitaiKbn(model.getToiawaseInfo().getUketsukeKeitaiKbn());
				toiawase.setToiawaseNaiyoSimple(model.getToiawaseInfo().getToiawaseNaiyoSimple());
				toiawase.setToiawaseNaiyo(model.getToiawaseInfo().getToiawaseNaiyo());
				toiawase.setIraiUmuKbn(model.getToiawaseInfo().getIraiUmuKbn());
				toiawase.setHoukokuTargetFlg(model.getToiawaseInfo().getHoukokuTargetFlg());
				toiawase.setToiawaseKokaiFlg(model.getToiawaseInfo().getToiawaseKokaiFlg());
				toiawase.setServiceShubetsu(model.getToiawaseInfo().getServiceShubetsu());
			} else {
				// 変更前の状態は、本来の入力前を保持
				// 変更前の依頼有無区分を保持
				model.setIraiUmuKbn(toiawase.getIraiUmuKbn());
				// 変更前の問い合わせ公開フラグを保持
				model.setBefereToiawaseKokaiFlg(toiawase.getToiawaseKokaiFlg());
				// 変更前の報告書公開フラグを保持
				model.setBeforeHokokushoKokaiFlg(toiawase.getHokokushoKokaiFlg());
			}
			// ＩＤ無し顧客情報の設定
			model.setKokyakuInfoWithoutId(kokyakuWithoutId);
			// 問い合わせ情報の顧客ＩＤを、パラメータの顧客ＩＤに設定
			model.setKokyakuId(toiawase.getKokyakuId());
			// 他画面へのパラメータ、更新の排他などで使用するため、更新日を移しておく
			model.setToiawaseUpdDt(toiawase.getUpdDt());
			// 問い合わせ情報を保持
			model.setToiawaseInfo(toiawase);
		} else {
			// 更新で問い合わせＮＯが無い場合はエラー
			throw new ApplicationException("問い合わせNO不正：パラメータの問い合わせNO" );
		}
		
		return this.getToiawaseInfoForUpdate(model);
	}

	/**
	 * サーバーサイドエラー発生時の、初期表示情報取得を行います。（更新画面表示用）
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel parepareInitInfoForUpdate(TB023InquiryEntryModel model) {
		
		// 更新画面用の問い合わせ表示用データ取得
		return this.getToiawaseInfoForUpdate(model);
	}
	
	/**
	 * ID無し顧客初期情報を取得します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	private TB023InquiryEntryModel getInitKokyakuInfoWithoutId(TB023InquiryEntryModel model) {
		
		// 依頼者区分リストの取得
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		
		model.setKokyakuIraishaKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN));

		return model;
	}
	
	/**
	 * 問い合わせ初期情報を取得します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	private TB023InquiryEntryModel getInitToiawaseInfo(TB023InquiryEntryModel model) {
		// 共通コードマスタから取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
					RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA,
					RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU,
					RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_SEX_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI,
					RcpMComCd.RCP_M_COM_CD_SERVICE_SHUBETSU_TOIAWASE);

		
		// 問い合わせ区分１マスタから全件取得
		List<RcpMToiawaseKbn1> toiawaseKbn1List = toiawaseKbn1Dao.selectKbnListForDisplay();

		// 問い合わせ区分２マスタから全件取得
		List<RcpMToiawaseKbn2> toiawaseKbn2List = toiawaseKbn2Dao.selectKbnListForDisplay();

		// 問い合わせ区分３マスタから全件取得
		List<RcpMToiawaseKbn3> toiawaseKbn3List = toiawaseKbn3Dao.selectKbnListForDisplay();

		// 問い合わせ区分４マスタから全件取得
		List<RcpMToiawaseKbn4> toiawaseKbn4List = toiawaseKbn4Dao.selectKbnListForDisplay();

		// 依頼者区分リスト（問い合わせ）
		model.setToiawaseIraishaKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN));
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
		// 依頼有無リスト
		model.setIraiUmuList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU));
		// 依頼者性別リスト
		model.setIraishaSexKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_SEX_KBN));
		// 受付形態リスト
		model.setUketsukeKeitaiKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI));
		// 問い合わせファイルリスト
		model.setUploadedFiles(createToiawaseFileList(new ArrayList<RcpTToiawaseFile>()));
		// サービス種別リスト
		model.setServiceShubetsuList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_SERVICE_SHUBETSU_TOIAWASE));
		
		return model;
	}
	
	/**
	 * 問い合わせ情報を取得します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel getToiawaseInfoForUpdate(TB023InquiryEntryModel model) {
		
		// 初期表示情報取得
		model = this.getInitInfo(model);
		
		// 最終履歴情報取得
		RcpTToiawaseRireki lastToiawaseRireki = toiawaseRirekiDao.getLastToiawaseRireki(
				model.getToiawaseNo(), RC031ToiawaseSearchCondition.EXTERNAL_SITE_KOKAI_RDO_ALL);
		
		// 問い合わせ履歴一覧を取得
		model.setToiawaseRirekiList(toiawaseRirekiDao.selectRirekiListForDisplay(model.getToiawaseNo()));
		
		// 依頼情報取得
		Map<String, RcpTIrai> iraiMap = iraiDao.selectAnyAsMap(model.getToiawaseNo());
		
		// パスワードマスタの和名変換マップ取得
		List<String> userIdList = new ArrayList<String>();
		
		if (model.getToiawaseInfo() != null
				&& StringUtils.isNotBlank(model.getToiawaseInfo().getUketsukeshaId())) {
			
			userIdList.add(model.getToiawaseInfo().getUketsukeshaId());	// 問い合わせ情報の受付者ＩＤ
		}
		if (model.getToiawaseInfo() != null
				&& StringUtils.isNotBlank(model.getToiawaseInfo().getLastUpdId())
				&& !userIdList.contains(model.getToiawaseInfo().getLastUpdId())) {
			
			userIdList.add(model.getToiawaseInfo().getLastUpdId());	// 問い合わせ情報の最終更新者ＩＤ
		}
		for (RcpTToiawaseRireki toiawaseRireki : model.getToiawaseRirekiList()) {
			if (StringUtils.isNotBlank(toiawaseRireki.getTantoshaId())
					&& !userIdList.contains(toiawaseRireki.getTantoshaId())) {
				
				userIdList.add(toiawaseRireki.getTantoshaId());	// 問い合わせ履歴一覧の担当者ＩＤ
			}
		}
		Map<String, String> userMap = new HashMap<String, String>();
		if (!userIdList.isEmpty()) {
			userMap = natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));	// 和名変換マップ取得
		}
		
		// 共通コードマスタ取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_CHECK_ON_FLG);
		Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);
		// 履歴公開フラグ
		Map<String, RcpMComCd> rirekiKokaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_CHECK_ON_FLG);
		
		// 状況区分マスタ
		List<RcpMJokyoKbn> jokyoKbnList = jokyoKbnDao.selectAll();
		Map<String, String> jokyoKbnMap = jokyoKbnDao.convertMap(jokyoKbnList);
		
		// 問い合わせ情報を変換
		if (userMap != null && model.getToiawaseInfo() != null) {
			// 受付者
			model.getToiawaseInfo().setUketsukeNm(userMap.get(model.getToiawaseInfo().getUketsukeshaId()));
			// 最終更新者（最終更新者ＩＤから和名変換）
			model.getToiawaseInfo().setLastUpdIdToNm(userMap.get(model.getToiawaseInfo().getLastUpdId()));
		}
		
		// 問い合わせ履歴一覧を変換
		for (RcpTToiawaseRireki toiawaseRireki : model.getToiawaseRirekiList()) {
			// 担当者名が存在しない場合
			if (StringUtils.isBlank(toiawaseRireki.getTantoshaNm())) {
				// 担当者名
				if (userMap != null) {
					toiawaseRireki.setTantoshaNm(userMap.get(toiawaseRireki.getTantoshaId()));
				}
			}
			// 状況区分
			if (jokyoKbnMap != null) {
				toiawaseRireki.setJokyoKbnNm(jokyoKbnMap.get(toiawaseRireki.getJokyoKbn()));
			}
			// 依頼存在有無
			if (iraiMap != null) {
				String searchKey = toiawaseRireki.getToiawaseNo() + toiawaseRireki.getToiawaseRirekiNo().toString();
				if (iraiMap.containsKey(searchKey)) {
					toiawaseRireki.setIraiExsits(true);
				}
			}
			// 履歴公開フラグ
			if (rirekiKokaiMap != null
					&& StringUtils.isNotBlank(toiawaseRireki.getToiawaseRirekiKokaiFlg())
					&& rirekiKokaiMap.containsKey(toiawaseRireki.getToiawaseRirekiKokaiFlg())) {
				toiawaseRireki.setToiawaseRirekiCheck(rirekiKokaiMap.get(toiawaseRireki.getToiawaseRirekiKokaiFlg()).getExternalSiteVal());
			}
		}
		// 問い合わせ最終履歴を変換して設定
		if (lastToiawaseRireki != null) {
			// 最終履歴状況
			if (jokyoKbnMap != null) {
				model.getToiawaseInfo().setLastJokyoKbnNm(jokyoKbnMap.get(lastToiawaseRireki.getJokyoKbn()));
			}
			// 最終履歴日付
			model.getToiawaseInfo().setLastRirekiYmd(lastToiawaseRireki.getUketsukeYmd());
			// 最終履歴時間
			model.getToiawaseInfo().setLastRirekiJikan(lastToiawaseRireki.getUketsukeJikan());
		}
		
		// 公開メール送信履歴情報取得
		RcpTMailRireki mailRireki =
			mailRirekiDao.selectByPrimaryKey(model.getToiawaseNo());
		model.setMailRireki(mailRireki);
		
		// 問い合わせファイルリスト
		List<RcpTToiawaseFile> toiawaseFileList = 
			toiawaseFileDao.selectBy(model.getToiawaseNo(), null);
		model.setUploadedFiles(createToiawaseFileList(toiawaseFileList));
		
		return model;
	}
	
	/**
	 * 問い合わせ情報を新規登録します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 */
	@Transactional(value="txManager")
	public void insertToiawaseInfo(TB023InquiryEntryModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// アップロードファイル妥当性チェック
		if (model.isExecuteFileUpload()) {
			isValidUploadFile(model);
		}
		
		// サービス種別妥当性チェック
		validateServiceShubetsu(model);
		
		// 問い合わせNOの新規発行
		String toiawaseNo = toiawaseDao.makeNewToiawaseNo();
		
		RcpTToiawase toiawase = model.getToiawaseInfo();
		
		// 問い合わせＮＯ
		toiawase.setToiawaseNo(toiawaseNo);
		// 顧客ＩＤ
		if (StringUtils.isNotBlank(model.getKokyakuId())) {
			toiawase.setKokyakuId(model.getKokyakuId());
		}
		// 報告書公開フラグ
		toiawase.setHokokushoKokaiFlg(RcpTToiawase.HOKOKUSHO_KOKAI_FLG_MIKOKAI);
		
		// 受付者名
		toiawase.setUketsukeshaNm(userContext.getUserName());
		// 登録会社ＩＤ
		toiawase.setCreKaishaId(userContext.getKaishaId());
		// 最終更新会社ＩＤ
		toiawase.setUpdKaishaId(userContext.getKaishaId());
		// 登録者名
		toiawase.setCreNm(userContext.getUserName());
		// 最終更新者名
		toiawase.setLastUpdNm(userContext.getUserName());
		
		// 問い合わせ登録
		toiawaseDao.insertForTores(model.getToiawaseInfo());
		// 問い合わせ履歴登録
		toiawaseRirekiDao.insertForTores(createTToiwaseRirekiInfo(model));
		
		// パラメータの顧客ＩＤがNULLの場合、ID無し顧客情報の登録を行う
		if (StringUtils.isBlank(model.getKokyakuId())) {
			model.getKokyakuInfoWithoutId().setToiawaseNo(toiawaseNo);
			model.getKokyakuInfoWithoutId().setCreKaishaId(userContext.getKaishaId());
			model.getKokyakuInfoWithoutId().setUpdKaishaId(userContext.getKaishaId());
			model.getKokyakuInfoWithoutId().setCreNm(userContext.getUserName());
			model.getKokyakuInfoWithoutId().setLastUpdNm(userContext.getUserName());
			kokyakuWithoutIdDao.insertForTores(model.getKokyakuInfoWithoutId());
		}

		model.setToiawaseNo(toiawaseNo);
		if (model.isExecuteFileUpload()) {
			// ファイル指定時はファイルアップロード処理を実施する
			executeFileUpload(model);
		}
		
		// アクセスログ登録
		tbAccesslogDao.insert(TB023InquiryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_INSERT,
				createKensakuJoken(model));
	}
	
	/**
	 * 問い合わせ情報を更新します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 */
	@Transactional(value="txManager")
	public void updateToiawaseInfo(TB023InquiryEntryModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 依頼有無区分が変更されて「無し」の場合、依頼情報存在チェック処理
		if (model.isIraiUmuKbnChanged() && model.isIraiKbnNashi()) {
			List<RcpTIrai> irai = iraiDao.selectBy(model.getToiawaseNo(), null);

			if (irai != null && !irai.isEmpty()) {
				// 依頼情報が存在する場合、更新チェックエラー
				throw new ValidationException(new ValidationPack().addError("MSG0031", "依頼情報が存在する", "依頼有無"));
			}
		}
		
		// 問い合わせ情報公開設定可否チェック
		isValid(model);

		// アップロードファイル妥当性チェック
		if (model.isExecuteFileUpload()) {
			isValidUploadFile(model);
		}

		// サービス種別妥当性チェック
		validateServiceShubetsu(model);
		
		// スケジュール情報妥当性チェック
		validateScheduleInfo(model);
		
		// 請求先情報妥当性チェック
		validateSeikyusakiInfo(model);
		
		// 問い合わせ情報の更新前に値を設定
		RcpTToiawase toiawase = model.getToiawaseInfo();
		// 問い合わせＮＯ
		toiawase.setToiawaseNo(model.getToiawaseNo());
		// 顧客ＩＤ
		if (StringUtils.isNotBlank(model.getKokyakuId())) {
			toiawase.setKokyakuId(model.getKokyakuId());
		}
		// 公開中止フラグがたっていれば、公開を中止する
		if (RcpTToiawase.KOKAI_TYUSHI_FLG.equals(model.getKokaiTyushiFlg())){
			model.getToiawaseInfo().setHokokushoKokaiFlg(RcpTToiawase.HOKOKUSHO_KOKAI_FLG_MIKOKAI);
		}
		// 最終更新会社ＩＤ
		toiawase.setUpdKaishaId(userContext.getKaishaId());
		// 最終更新者名
		toiawase.setLastUpdNm(userContext.getUserName());
		
		// 更新日(where)
		toiawase.setUpdDt(model.getToiawaseUpdDt());
		// 最終更新者ＩＤ(where)
		toiawase.setLastUpdId(Constants.TORES_APL_ID);

		// 問い合わせ情報の更新
		int ret = toiawaseDao.updateForTores(model.getToiawaseInfo());
		if (ret == 0) {
			// 更新件数が０件の場合、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		// パラメータの顧客ＩＤがNULLの場合、ID無し顧客情報の更新を行う
		if (StringUtils.isBlank(model.getKokyakuId())) {
			model.getKokyakuInfoWithoutId().setUpdKaishaId(userContext.getKaishaId());
			model.getKokyakuInfoWithoutId().setLastUpdNm(userContext.getUserName());
			kokyakuWithoutIdDao.updateForTores(model.getKokyakuInfoWithoutId());
		}

		if (model.isExecuteFileUpload()) {
			// ファイル指定時はファイルアップロード処理を実施する
			executeFileUpload(model);
		}
		
		// アクセスログ登録
		tbAccesslogDao.insert(TB023InquiryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_UPDATE,
				createKensakuJoken(model));
	}

	/**
	 * 問い合わせ情報を削除します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 */
	@Transactional(value="txManager")
	public void deleteToiawaseInfo(TB023InquiryEntryModel model) {
		
		// 問い合わせNOに紐づくスケジュール情報を取得
		List<RcpTBillSchedule> rcpTBillScheduleList = billScheduleDao.selectByToiawaseNo(model.getToiawaseNo());
		// スケジュール情報が存在する場合
		if (rcpTBillScheduleList != null && !rcpTBillScheduleList.isEmpty()) {
			throw new ValidationException(new ValidationPack().addError("MSG0043", "問い合わせＮＯ", "スケジュール情報"));
		}

		// 問い合わせ情報退避
		RcpTToiawase toiawase = model.getToiawaseInfo();
		toiawase.setToiawaseNo(model.getToiawaseNo());
		toiawase.setUpdDt(model.getToiawaseUpdDt());

		// 問い合わせ履歴テーブルの件数
		int rirekiCount = toiawaseRirekiDao.countBy(model.getToiawaseNo(), null);

		// 問い合わせ履歴の件数が２件以上の場合
		if (rirekiCount > 1) {
			
			throw new ValidationException(new ValidationPack().addError("MSG0032"));
		}

		int ret = 0;
		// 問い合わせ履歴の件数が１件の場合のみ処理
		if (rirekiCount == 1) {
			
			// 問い合わせ履歴情報退避
			RcpTToiawaseRireki toiawaseRireki = new RcpTToiawaseRireki();
			toiawaseRireki.setToiawaseNo(model.getToiawaseNo());
			toiawaseRireki.setToiawaseRirekiNo(new BigDecimal(1));

			// 問い合わせ履歴退避テーブルに退避
			ret = toiawaseRirekiDao.insertTaihiWithoutOptimisticLock(toiawaseRireki);
			// 更新件数が０件の場合、排他エラー
			if (ret == 0) {
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}

			try {
				// 問い合わせ履歴テーブルの削除
				toiawaseRirekiDao.deleteWithoutOptimisticLock(
						toiawaseRireki.getToiawaseNo(),
						toiawaseRireki.getToiawaseRirekiNo());
			} catch (DataIntegrityViolationException e) {
				// 外部キー参照エラーの場合
				throw new ValidationException(new ValidationPack().addError("MSG0033"));
			}
		}

		// 問い合わせ退避テーブルに退避
		ret = toiawaseDao.insertTaihiWithoutSelfUpdate(model.getToiawaseInfo());
		// 更新件数が０件の場合、排他エラー
		if (ret == 0) {
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		try {
			// 問い合わせテーブルの削除
			toiawaseDao.deleteWithoutOptimisticLock(model.getToiawaseNo());
		} catch (DataIntegrityViolationException e) {
			// 外部キー参照エラーの場合
			throw new ValidationException(new ValidationPack().addError("MSG0025"));
		}

		// パラメータの顧客ＩＤがNULLの場合、ID無し顧客情報の削除を行う
		if (StringUtils.isBlank(model.getKokyakuId())) {
			// ID無し顧客退避テーブルに退避
			ret = kokyakuWithoutIdDao.insertTaihi(model.getKokyakuInfoWithoutId());
			// 更新件数が０件の場合、排他エラー
			if (ret == 0) {
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			// ID無し顧客テーブルの削除
			kokyakuWithoutIdDao.deleteWithoutOptimisticLock(model.getKokyakuInfoWithoutId().getToiawaseNo());
		}

		// 問い合わせファイル情報取得
		List<RcpTToiawaseFile> toiawaseFileList = toiawaseFileDao.selectBy(model.getToiawaseNo(), null);
		
		if (toiawaseFileList != null && !toiawaseFileList.isEmpty()) {
			// 問い合わせファイル情報がある場合
			RcpTToiawaseFile toiawaseFile = new RcpTToiawaseFile();
			toiawaseFile.setToiawaseNo(model.getToiawaseNo());
			
			// 問い合わせファイル情報退避処理
			toiawaseFileDao.insertTaihiBy(toiawaseFile);
			// 問い合わせファイル情報削除処理
			toiawaseFileDao.deleteBy(model.getToiawaseNo(), null);
			
			model.setFileDeleteSuccess(true);
			// アップロードファイルの削除処理
			for (RcpTToiawaseFile uploadFileInfo : toiawaseFileList) {
				// アップロードファイル削除
				String fileNm = UPLOAD_PATH_INQUIRY_FILE +
								System.getProperty("file.separator") +
								uploadFileInfo.getUploadFileNm();

				// アップロード実ファイル削除処理
				if (!FileUploadLogic.deleteServerFile(fileNm)) {
					// ファイル削除処理の失敗
					model.setFileDeleteSuccess(false);
				}
			}
		}
		
		// アクセスログ登録
		tbAccesslogDao.insert(TB023InquiryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_DELETE,
				createKensakuJoken(model));
	}
	
	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 検索条件
	 */
	private String createKensakuJoken(TB023InquiryEntryModel model) {
		
		StringBuilder accesslog = new StringBuilder("");
		
		// 登録、更新
		if (model.isInsert() || model.isUpdate()) {
			
			NullExclusionToStringBuilder toiawaseEntry =
				new NullExclusionToStringBuilder(
					model.getToiawaseInfo(),
					NullExclusionToStringBuilder.CSV_STYLE, null, null,
					false, false);
			
			// 除外する項目
			List<String> excludeFiledList = new ArrayList<String>(Arrays.asList(Constants.EXCLUDE_FIELD_NAMES));
			
			// 名称部分は、出力しない
			excludeFiledList.add("uketsukeNm");
			excludeFiledList.add("lastJokyoKbnNm");
			excludeFiledList.add("lastRirekiYmd");
			excludeFiledList.add("lastRirekiJikan");

			toiawaseEntry.setExcludeFieldNames((String[]) excludeFiledList.toArray(new String[0]));

			accesslog.append(toiawaseEntry.toString());
			
		// 削除
		} else if (model.isDelete()) {
			accesslog.append("toiawaseNo=");
			accesslog.append(model.getToiawaseNo());
		}
		
		return accesslog.toString();
	}
	
	/**
	 * 登録する問い合わせ履歴情報を生成します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ履歴情報
	 */
	private RcpTToiawaseRireki createTToiwaseRirekiInfo(TB023InquiryEntryModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		RcpTToiawaseRireki toiawaseRireki = new RcpTToiawaseRireki();

		RcpTToiawase toiawase = model.getToiawaseInfo();

		toiawaseRireki.setToiawaseNo(toiawase.getToiawaseNo());
		toiawaseRireki.setToiawaseRirekiNo(new BigDecimal(1));
		toiawaseRireki.setIraishaKbn(toiawase.getIraishaKbn());
		toiawaseRireki.setIraishaNm(toiawase.getIraishaNm());
		toiawaseRireki.setIraishaTel(toiawase.getIraishaTel());
		toiawaseRireki.setIraishaRoomNo(toiawase.getIraishaRoomNo());
		toiawaseRireki.setIraishaMemo(toiawase.getIraishaMemo());
		toiawaseRireki.setUketsukeYmd(toiawase.getUketsukeYmd());
		toiawaseRireki.setUketsukeJikan(toiawase.getUketsukeJikan());
		toiawaseRireki.setToiawaseKbn1(toiawase.getToiawaseKbn1());
		toiawaseRireki.setToiawaseKbn2(toiawase.getToiawaseKbn2());
		toiawaseRireki.setToiawaseKbn3(toiawase.getToiawaseKbn3());
		toiawaseRireki.setToiawaseKbn4(toiawase.getToiawaseKbn4());
		toiawaseRireki.setToiawaseNaiyo(toiawase.getToiawaseNaiyo());
		toiawaseRireki.setJokyoKbn(RcpMJokyoKbn.RCP_M_JOKYO_KBN_SHOKAIUKETSUKE);
		
		if (StringUtils.isNotBlank(toiawase.getKokyakuId())) {
			toiawaseRireki.setHoukokuPrintFlg(RcpTToiawaseRireki.HOUKOKU_PRINT_FLG_ON);
		} else {
			toiawaseRireki.setHoukokuPrintFlg(RcpTToiawaseRireki.HOUKOKU_PRINT_FLG_OFF);
		}
		// 問い合わせ履歴新規登録時、報告書公開フラグは未公開をセット
		toiawaseRireki.setToiawaseRirekiKokaiFlg(RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_MIKOKAI);
		toiawaseRireki.setTantoshaNm(userContext.getUserName());
		toiawaseRireki.setCreKaishaId(userContext.getKaishaId());
		toiawaseRireki.setUpdKaishaId(userContext.getKaishaId());
		toiawaseRireki.setCreNm(userContext.getUserName());
		toiawaseRireki.setLastUpdNm(userContext.getUserName());
		
		return toiawaseRireki;
	}
	
	/**
	 * 更新チェック処理を行います。
	 *
	 * @param model 問い合わせ登録画面モデル
	 * @throws ValidationException 入力エラーが発生した場合
	 */
	private void isValid(TB023InquiryEntryModel model) {
		// 依頼情報公開設定可否チェック
		boolean isValidPublish = toiawaseCheckLogic.isValid(
				CheckToPublishToiawaseLogic.CONTENT_TYPE_TOIAWASE,
				model.getToiawaseInfo().getToiawaseKokaiFlg(),
				model.getToiawaseNo(),
				null);

		if (!isValidPublish) {
			String publishMsg = model.getToiawaseInfo().isPublished() ? "公開" : "未公開に";
			// 公開設定が無効の場合、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0030", "問い合わせ情報", publishMsg));
		}
	}
	
	/**
	 * 帳票ダウンロード情報の取得を行います。
	 * 
	 * @param model 問い合わせ登録画面モデル
	 * @return 問い合わせ登録画面モデル
	 */
	public TB023InquiryEntryModel getPrintDownloadInfo(TB023InquiryEntryModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		model.setDownloadable(false);
		
		if (userContext.isRealEstate()) {
			// セッションの権限が「20：管理会社」の場合
			if (!toiawaseDao.isOwn(model.getToiawaseNo(), userContext.getRefKokyakuId())) {
				// 問い合わせ情報チェックNGの場合は、ダウンロードエラー
				return model;
			}
		} else if ((userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) && StringUtils.isNotBlank(model.getTargetKokyakuId())) {
			// セッションの権限が「40:委託会社SV」または「41:委託会社OP」の場合
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), model.getTargetKokyakuId())) {
				// 委託会社関連チェックNGの場合は、ダウンロードエラー
				return model;
			}
		}
		
		// 問い合わせ情報取得
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// 問い合わせ情報が取得できない場合は、ダウンロードエラー
			return model;
		}
		
		model.setDownloadable(true);
		model.setToiawaseInfo(toiawase);
		
		return model;
	}
	
	/**
	 * 問い合わせファイル情報を削除します。
	 * 
	 * @param model  問い合わせ登録画面モデル
	 */
	public void deleteToiawaseFileInfo(TB023InquiryEntryModel model) {
		// ユーザ情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		RcpTToiawase toiawase = new RcpTToiawase();
		toiawase.setToiawaseNo(model.getToiawaseNo());
		toiawase.setLastUpdId(userContext.getLoginId());
		toiawase.setUpdDt(model.getToiawaseUpdDt());
		toiawase.setUpdKaishaId(userContext.getKaishaId());
		toiawase.setLastUpdNm(userContext.getUserName());
		
		// 問い合わせ情報の更新日更新
		if (toiawaseDao.updateUpdDt(toiawase) == 0) {
			// 更新件数が０件の場合は、排他チェックエラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		RcpTToiawaseFile toiawaseFile = new RcpTToiawaseFile();
		toiawaseFile.setToiawaseNo(model.getToiawaseNo());
		toiawaseFile.setFileIndex(model.getFileIndex());
		
		// 問い合わせファイル情報の退避
		toiawaseFileDao.insertTaihiWithoutOptimisticLock(toiawaseFile);
		// 問い合わせファイル情報の削除
		toiawaseFileDao.deleteWithoutOptimisticLock(model.getToiawaseNo(), model.getFileIndex());
		
		// アクセスログ登録（アクションタイプを設定していないため、ここに記述）
		StringBuilder accesslog = new StringBuilder("");
		accesslog.append("toiawaseNo=");
		accesslog.append(model.getToiawaseNo());
		accesslog.append(",");
		accesslog.append("fileIndex=");
		accesslog.append(model.getFileIndex().toPlainString());
		accesslog.append(",");
		accesslog.append("uploadFileNm=");
		accesslog.append(model.getUploadFileNm());
		
		tbAccesslogDao.insert(TB023InquiryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_DELETE,
				accesslog.toString());
		
		// アップロードファイル削除
		String fileNm = UPLOAD_PATH_INQUIRY_FILE +
						System.getProperty("file.separator") +
						model.getUploadFileNm();

		// アップロード実ファイル削除処理
		model.setFileDeleteSuccess(FileUploadLogic.deleteServerFile(fileNm));
	}
	
	/**
	 * 問い合わせファイル情報を表示順に作成します。
	 *
	 * @param uploadedList ファイルアップロード情報リスト
	 * @param dispMax 最大表示件数
	 * @return ファイルアップロード情報配列
	 */
	private RcpTToiawaseFile[] createToiawaseFileList(List<RcpTToiawaseFile> uploadedList) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// システムマスタから添付ファイル可能最大件数取得
		int maxFileListSize = 
			userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_BSB_TOIAWASE_FILE_TO_MAX);
		
		
		RcpTToiawaseFile[] toiawaseFiles = new RcpTToiawaseFile[maxFileListSize];

		if (uploadedList == null || uploadedList.isEmpty()) {
			return toiawaseFiles;
		}

		for (int i = 0; i < maxFileListSize; i++) {
			// 現在のファイルインデックス
			int currentIdx = i + 1;

			for (RcpTToiawaseFile toiawaseFile : uploadedList) {
				if (currentIdx == toiawaseFile.getFileIndex().intValue()) {
					// データがある場合は、配列に格納
					toiawaseFiles[i] = toiawaseFile;
					break;
				}
			}
		}

		return toiawaseFiles;
	}
	
	/**
	 * アップロードファイルの妥当性チェックを行います。
	 * 
	 * @param model 問い合わせ登録画面モデル
	 */
	private void isValidUploadFile(TB023InquiryEntryModel model) {
		// ユーザ情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 最大ファイルバイト数取得
		Long maxFileSize = 
			Long.parseLong(userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_BSB_TOIAWASE_FILESIZE_TO_MAX));
		
		for (File uploaFile : model.getToiawaseFiles()) {
			if (uploaFile == null) {
				// ファイルが指定されていなければ、処理しない
				continue;
			}
			
			// ファイルサイズチェック
			String result = FileUploadLogic.checkFileSize(uploaFile, maxFileSize);

			if (FileUploadLogic.SIZE_ZERO.equals(result)) {
				// ファイルサイズ０の場合
				throw new ValidationException(new ValidationPack().addError("MSG0020"));
			} else if (FileUploadLogic.SIZE_OVER.equals(result)) {
				// ファイルサイズオーバーの場合
				throw new ValidationException(new ValidationPack().addError("MSG0021", maxFileSize.toString()));
			}
		}
	}
	
	/**
	 * ファイルのアップロード処理を実行します。
	 *
	 * @param model 問い合わせ登録画面モデル
	 */
	private void executeFileUpload(TB023InquiryEntryModel model) {
		List<String> uploadFileNameList = new ArrayList<String>();
		List<File> uploadFileList = new ArrayList<File>();

		String[] uploadFileNames = model.getToiawaseFileNmByArray();
		// 指定されたファイル分、登録
		for (int i = 0; i < uploadFileNames.length; i++) {
			String toiawaseFileName = uploadFileNames[i];

			if (StringUtils.isBlank(toiawaseFileName)) {
				// ファイルが指定されていなければ、次の処理へ
				continue;
			}
			File uploadFile = model.getToiawaseFiles()[i];
			// ファイルインデックス
			int fileIndex = i + 1;

			// ファイル名の生成
			String uploadFileName = model.getToiawaseNo() + "-" + fileIndex;
			// アップロードファイル名の生成
			uploadFileName =
				FileUploadLogic.makeUploadFileNm(toiawaseFileName, uploadFileName);

			// 問い合わせファイルテーブルの登録
			RcpTToiawaseFile toiawaseFile = new RcpTToiawaseFile();
			toiawaseFile.setToiawaseNo(model.getToiawaseNo());
			toiawaseFile.setFileIndex(new BigDecimal(fileIndex));
			toiawaseFile.setUploadFileNm(uploadFileName);
			toiawaseFile.setBaseFileNm(FilenameUtils.getName(new File(toiawaseFileName).getName()));
			
			toiawaseFileDao.insert(toiawaseFile);

			uploadFileNameList.add(uploadFileName);
			uploadFileList.add(uploadFile);
		}

		// ファイルのアップロード
		FileUploadLogic.uploadFileList(uploadFileList,
				uploadFileNameList, UPLOAD_PATH_INQUIRY_FILE);
	}
	
	/**
	 * 問い合わせファイル情報のダウンロードチェックを行います。
	 * 
	 * @param model 問い合わせ登録画面モデル
	 * @return チェック結果 true：チェックOK、false：チェックNG
	 */
	public boolean isDownloableToiawaseFile(TB023InquiryEntryModel model) {
		// 問い合わせ情報取得
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// 問い合わせ情報がなければ、チェックNG
			return false;
		}
		
		// 問い合わせファイル情報取得
		RcpTToiawaseFile toiawaseFile = 
			toiawaseFileDao.selectByPrimaryKey(model.getToiawaseNo(), model.getFileIndex());
		if (toiawaseFile == null) {
			// 問い合わせファイル情報がなければ、チェックNG
			return false;
		}
		
		model.setUploadFileNm(toiawaseFile.getUploadFileNm());
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		if (userContext.isRealEstate()) {
			// セッションの権限が「20：管理会社」の場合
			// ※問い合わせ内容詳細にて機能を共有化するため、チェックを追加
			return toiawaseDao.isOwn(model.getToiawaseNo(), userContext.getRefKokyakuId());
		} else if ((userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) && StringUtils.isNotBlank(toiawase.getKokyakuId())) {
			// セッションの権限が「40:委託会社SV」または「41:委託会社OP」の場合
			return outsourcerValidationLogic.isValid(userContext.getKaishaId(), toiawase.getKokyakuId());
		} else {
			return true;
		}
	}

	/**
	 * サービス種別妥当性チェック
	 * 
	 * @param model model 問い合わせ登録画面モデル
	 */
	private void validateServiceShubetsu(TB023InquiryEntryModel model) {
		// 顧客IDなし　かつ　サービス種別が「ビル管理」の場合
		if (StringUtils.isBlank(model.getKokyakuId())
			&& RcpMService.SERVICE_SHUBETSU_BUILDING.equals(model.getToiawaseInfo().getServiceShubetsu())) {
			// メッセージを表示
			throw new ValidationException(new ValidationPack().addError("MSG0045"));
		}
	}

	/**
	 * スケジュール情報妥当性チェック
	 * 
	 * @param model model 問い合わせ登録画面モデル
	 */
	private void validateScheduleInfo(TB023InquiryEntryModel model) {
		// 問い合わせNOに紐づくスケジュール情報を取得
		List<RcpTBillSchedule> rcpTBillScheduleList = billScheduleDao.selectByToiawaseNo(model.getToiawaseNo());
		// スケジュール情報が存在する場合
		if (rcpTBillScheduleList != null && !rcpTBillScheduleList.isEmpty()) {
			// サービス種別が「ビル管理」以外の場合
			if (!RcpMService.SERVICE_SHUBETSU_BUILDING.equals(model.getToiawaseInfo().getServiceShubetsu())) {
				// メッセージを表示
				throw new ValidationException(new ValidationPack().addError("MSG0044", "サービス種別", "ビル管理以外"));
			}
			// 区分１が「共用部」以外の場合
			if (!RcpMToiawaseKbn1.KBN_KYOYOBU.equals(model.getToiawaseInfo().getToiawaseKbn1())) {
				// メッセージを表示
				throw new ValidationException(new ValidationPack().addError("MSG0044", "区分１", "共用部以外"));
			}
			// 区分２が「ビル管理」以外の場合
			if (!RcpMToiawaseKbn2.KBN_BUILDING.equals(model.getToiawaseInfo().getToiawaseKbn2())) {
				// メッセージを表示
				throw new ValidationException(new ValidationPack().addError("MSG0044", "区分２", "ビル管理以外"));
			}
			// スケジュール情報の１件目の定額・スポット区分が「定額」の場合
			RcpTBillSchedule rcpTBillSchedule = rcpTBillScheduleList.get(0);
			if (rcpTBillSchedule.isTeigaku()) {
				// 区分３が「定額」以外の場合
				if (!RcpMToiawaseKbn3.KBN_TEIGAKU.equals(model.getToiawaseInfo().getToiawaseKbn3())) {
					// メッセージを表示
					throw new ValidationException(new ValidationPack().addError("MSG0044", "区分３", "定額以外"));
				}
			} else if (rcpTBillSchedule.isSpot()) {
				// 区分３が「スポット」以外の場合
				if (!RcpMToiawaseKbn3.KBN_SPOT.equals(model.getToiawaseInfo().getToiawaseKbn3())) {
					// メッセージを表示
					throw new ValidationException(new ValidationPack().addError("MSG0044", "区分３", "スポット以外"));
				}
			}
		}
	}
	
	/**
	 * 請求先情報の妥当性チェックを行います。
	 * ※サービス種別がビル管理を指定された場合のみ行います。
	 * 
	 * @param model 問い合わせ登録画面モデル
	 */
	private void validateSeikyusakiInfo(TB023InquiryEntryModel model) {
		if (!RcpMService.SERVICE_SHUBETSU_BUILDING.equals(model.getToiawaseInfo().getServiceShubetsu())) {
			// サービス種別以外の場合は、チェックしない
			return;
		}
		
		// 作業費情報取得
		List<RcpTSagyohi> sagyohiList = sagyohiDao.selectBy(model.getToiawaseNo(), null);
		
		if (sagyohiList == null || sagyohiList.isEmpty()) {
			// 作業費情報が存在しない場合は、チェックしない
			return;
		}
		
		// チェック用請求先顧客ＩＤ
		String chkSeikyusakiKokyakuId = "";
		
		for (RcpTSagyohi sagyohi : sagyohiList) {
			if (StringUtils.isBlank(sagyohi.getSeikyusakiKokyakuId())) {
				// 作業費の請求先顧客ＩＤが１件でもNULLがあれば、チェックNG
				throw new ValidationException(new ValidationPack().addError("MSG0047"));
			} else {
				if (StringUtils.isBlank(chkSeikyusakiKokyakuId)) {
					// チェック用請求先顧客ＩＤが空欄の場合は、設定
					chkSeikyusakiKokyakuId = sagyohi.getSeikyusakiKokyakuId();
				}
				
				if (!sagyohi.getSeikyusakiKokyakuId().equals(chkSeikyusakiKokyakuId)) {
					// チェック用請求先顧客ＩＤと値が異なっている場合
					throw new ValidationException(new ValidationPack().addError("MSG0047"));
				}
			}
		}
	}
}
