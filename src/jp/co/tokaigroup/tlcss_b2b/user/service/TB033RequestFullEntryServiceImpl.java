package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.FileUploadLogic;
import jp.co.tokaigroup.reception.common.MailTemplate;
import jp.co.tokaigroup.reception.common.logic.CheckToPublishToiawaseLogic;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMSashidashininDao;
import jp.co.tokaigroup.reception.dao.RcpTFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuWithNoIdDao;
import jp.co.tokaigroup.reception.dao.RcpTMailRirekiDao;
import jp.co.tokaigroup.reception.dao.RcpTOtherFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyoJokyoDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTFileUploadDao;
import jp.co.tokaigroup.reception.dao.TbTSagyoJokyoDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMSashidashinin;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTOtherFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.TbTFileUpload;
import jp.co.tokaigroup.reception.entity.TbTSagyoJokyo;
import jp.co.tokaigroup.reception.print.logic.RC902SagyoIraiHyoLogic;
import jp.co.tokaigroup.reception.print.logic.RC907GyoshaSagyoHokokuHyoLogic;
import jp.co.tokaigroup.reception.util.FileRemoteUtil;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.mail.VelocityEmail;
import jp.co.tokaigroup.si.fw.mail.VelocityWrapper;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.CommonUtil;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB033RequestFullEntryModel;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 依頼登録サービス実装クラス。
 * 
 * @author S.Nakano
 * @version 1.0 2015/10/21
 * @version 1.1 2016/02/12 C.Kobayashi ファイルアップロード時のプライマリーキー変更対応
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB033RequestFullEntryServiceImpl extends TLCSSB2BBaseService
		implements TB033RequestFullEntryService {
	// プロパティファイルから取得
	/** 作業状況画像ファイルアップロードパス */
	private static final String UPLOAD_PATH_TLCSS_SAGYO_JOKYO = ResourceFactory.getResource().getString("UPLOAD_PATH_TLCSS_SAGYO_JOKYO");
	/** 依頼情報アップロードパス */
	private static final String UPLOAD_PATH_IRAI_FILE = ResourceFactory.getResource().getString("UPLOAD_PATH_IRAI_FILE");
	/** コピー元アップロードパス */
	private static final String UPLOAD_PATH_PDF = ResourceFactory.getResource().getString("PDF_PATH");
	/** コピー先アップロードパス（一時出力用） */
	private static final String UPLOAD_PATH_TEMP_REPORT = ResourceFactory.getResource().getString("UPLOAD_PATH_TEMP_REPORT");
	
	// メールテンプレート項目　キー
	/** メールテンプレート項目 キー 業者名 */
	private static final String MAIL_KEY_GYOSHA_NAME = "gyoshaName";
	/** メールテンプレート項目 キー システム名 */
	private static final String MAIL_KEY_SYSTEM_NAME = "systemName";
	/** メールテンプレート項目 キー 作業依頼URL */
	private static final String MAIL_KEY_SAGYO_IRAI_URL = "sagyoIraiUrl";
	/** メールテンプレート項目 キー 問い合わせＮＯ */
	private static final String MAIL_KEY_TOIAWASE_NO = "toiawaseNo";
	/** メールテンプレート項目 キー システム問い合わせ先電話番号 */
	private static final String MAIL_KEY_CONTACT_INFORMAITION = "contactInformation";
	/** メールテンプレート項目 キー 返信メールアドレス */
	private static final String MAIL_KEY_REPLY_MAIL_ADDRESS = "returnMailAddress";
	/** メールテンプレート項目 キー 送信元会社名 */
	private static final String MAIL_KEY_NOTICE_COMPAY_NAME = "noticeCompanyNm";
	
	/** リセプション問い合わせテーブルDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;
	/** リセプション問い合わせ履歴テーブルDAO */
	@Autowired
	private RcpTToiawaseRirekiDao toiawaseRirekiDao;
	/** リセプション依頼テーブルDAO */
	@Autowired
	private RcpTIraiDao iraiDao;
	/** リセプション作業状況テーブルDAO */
	@Autowired
	private RcpTSagyoJokyoDao sagyoJokyoDao;
	/** リセプション依頼ファイルアップロードテーブルDAO */
	@Autowired
	private RcpTFileUploadDao fileUploadDao;
	/** リセプション依頼その他ファイルアップロードテーブルDAO */
	@Autowired
	private RcpTOtherFileUploadDao otherFileUploadDao;
	/** リセプションＩＤ無し顧客テーブルDAO */
	@Autowired
	private RcpTKokyakuWithNoIdDao kokyakuWithoutDao;
	/** リセプション業者マスタDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;
	/** リセプションメール送信履歴DAO */
	@Autowired
	private RcpTMailRirekiDao mailRirekiDao;
	/** リセプション状況区分マスタDAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;
	/** リセプション差出人情報マスタDAO */
	@Autowired
	private RcpMSashidashininDao sashidashininDao;
	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;
	/** 外部サイト業者回答作業状況テーブルDAO */
	@Autowired
	private TbTSagyoJokyoDao gyoshaSagyoJokyoDao;
	/** 外部サイト業者回答依頼ファイルアップロードテーブルDAO */
	@Autowired
	private TbTFileUploadDao gyoshaFileUploadDao;
	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** NATOSパスワードＭDAO */
	@Autowired
	private NatosMPasswordDao natosPswdDao;
	
	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic commonInfoLogic;
	/** 問い合わせ公開チェックロジック */
	@Autowired
	private CheckToPublishToiawaseLogic toiawaseCheckLogic;
	/** 委託会社関連チェックロジック */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	/** 作業依頼書ロジッククラス */
	@Autowired
	private RC902SagyoIraiHyoLogic sagyoIraiHyoLogic;
	/** 作業報告書（業者用）ロジッククラス */
	@Autowired
	private RC907GyoshaSagyoHokokuHyoLogic gyoshaSagyoHokokuHyoLogic;
	
	/**
	 * 初期表示処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	@Override
	public TB033RequestFullEntryModel getInitInfo(TB033RequestFullEntryModel model) {
		// 初期表示パラメータチェック
		this.validateInitParameter(model);
		
		// 問い合わせ情報取得
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// 問い合わせ情報が存在しない場合は、排他チェックエラー
			model.setInitError(true);
			
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		model.setToiawase(toiawase);
		model.setToiawaseUpdDt(toiawase.getUpdDt());
		
		try {
			// 初期表示処理の共通処理を呼び出し
			return executeInitCommonProcess(model);
		} catch (ValidationException e) {
			// 初期表示処理でエラーが起こった場合は、初期エラーフラグを立てて、
			// 例外を送出する
			model.setInitError(true);
			
			throw e;
		}
	}

	/**
	 * サーバーサイドエラー発生時の、初期表示処理を行います。
	 *
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	@Override
	public TB033RequestFullEntryModel parepareInitInfo(TB033RequestFullEntryModel model) {
		return executeInitCommonProcess(model);
	}
	
	/**
	 * 初期表示処理の共通処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	private TB033RequestFullEntryModel executeInitCommonProcess(TB033RequestFullEntryModel model) {
		// 共通コードマスタ情報取得
		Map<String, List<RcpMComCd>> comKbnMap = 
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);
		
		// 顧客情報取得
		if (StringUtils.isNotBlank(model.getToiawase().getKokyakuId())) {
			// 問い合わせ情報の顧客ＩＤがある場合は、顧客基本情報取得
			model.setKokyakuEntity(commonInfoLogic.getKokyakuInfo(model.getToiawase().getKokyakuId()));
		} else {
			// 問い合わせ情報の顧客ＩＤがない場合は、ＩＤ無し顧客情報取得
			RcpTKokyakuWithNoId kokyakuWithoutId = 
				kokyakuWithoutDao.selectByPrimaryKey(model.getToiawaseNo());
			if (kokyakuWithoutId == null) {
				// ＩＤ無し顧客情報が存在しない場合は、排他チェックエラー
				model.setInitError(true);
				
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			Map<String, RcpMComCd> kokyakuKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
			// 顧客区分名
			if (kokyakuKbnMap != null && kokyakuKbnMap.containsKey(kokyakuWithoutId.getKokyakuKbn())) {
				kokyakuWithoutId.setKokyakuKbnNm(kokyakuKbnMap.get(kokyakuWithoutId.getKokyakuKbn()).getComVal());
			}
			
			model.setKokyakuWithoutId(kokyakuWithoutId);
		}
				
		// 訪問希望リスト
		model.setHomonKiboList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI));
		// 業者回答リスト
		model.setGyoshaKaitoList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI));
		
		// ログインユーザ情報
		model.setUserContext((TLCSSB2BUserContext) getUserContext());
		
		return model;
	}
	
	/**
	 * 作業依頼情報取得処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	@Override
	public TB033RequestFullEntryModel getSagyoIraiInfo(TB033RequestFullEntryModel model) {
		// 初期表示処理を呼び出す
		model = this.getInitInfo(model);
		
		// 問い合わせ履歴情報取得
		RcpTToiawaseRireki toiawaseRireki = 
			toiawaseRirekiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (toiawaseRireki == null) {
			// 問い合わせ履歴情報が存在しない場合は、排他チェックエラー
			model.setInitError(true);
			
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		model.setToiawaseRireki(toiawaseRireki);
		
		// 依頼情報取得
		RcpTIrai irai = 
			iraiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (irai == null) {
			// 依頼情報が存在しない場合は、排他チェックエラー
			model.setInitError(true);
			
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		// メール送信状況情報取得
		model.setMailRireki(mailRirekiDao.selectByPrimaryKey(
				model.getToiawaseNo() + "-" + StringUtils.leftPad(model.getToiawaseRirekiNo().toPlainString(), 3, '0')));
		
		// 作業状況情報取得
		RcpTSagyoJokyo sagyoJokyo = 
			sagyoJokyoDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
		
		List<RcpTFileUpload> iraiFileUploadList = null;
		List<RcpTOtherFileUpload> otherFileUploadList = null;
		if (sagyoJokyo != null) {
			model.setSagyoJokyoUpdDt(sagyoJokyo.getUpdDt());
			
			// 作業状況情報が取得できた場合
			// 和名変換処理
			Map<String, List<RcpMComCd>> comCdMap = comCdDao.selectAnyAsMap(
					RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);
			Map<String, Map<String, RcpMComCd>> convertMap =
				comCdDao.convertMapAsKeyToEntity(comCdMap);

			// 完了フラグMap
			Map<String, RcpMComCd> kanryoFlgMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);
			
			// 依頼ファイルアップロード情報取得
			iraiFileUploadList = fileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			
			// 依頼その他ファイルアップロード情報取得
			otherFileUploadList = otherFileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			
			// 業者回答作業状況情報取得
			TbTSagyoJokyo gyoshaSagyoJokyo = gyoshaSagyoJokyoDao.selectByPrimaryKey(
					model.getToiawaseNo(), model.getToiawaseRirekiNo());
		
			// 作業完了フラグ
			if (gyoshaSagyoJokyo != null &&
				kanryoFlgMap.containsKey(gyoshaSagyoJokyo.getSagyoKanryoFlg())) {
				gyoshaSagyoJokyo.setSagyoKanryoFlgNm(kanryoFlgMap.get(
						gyoshaSagyoJokyo.getSagyoKanryoFlg()).getComVal());
			}
			
			model.setGyoshaSagyoJokyo(gyoshaSagyoJokyo);
			
			// 業者回答依頼ファイルアップロード情報取得
			List<TbTFileUpload> gyoshaFileUploadList =
				gyoshaFileUploadDao.selectBy(model.getToiawaseNo(),
						model.getToiawaseRirekiNo(), null);
			
			// 表示用の業者回答ファイルアップロード情報リスト取得
			model.setGyoshaUploadFileList(createGyoshaAnswerFileUploadList(
					gyoshaFileUploadList));
		}
		
		// 画面値復元処理
		if (model.isRestore()) {
			// アクションタイプが「restore:画面値復元」の場合は、画面入力値を復元
			// 依頼ファイルアップロード情報を常に３件にする
			editSagyoJokyoImageFileContents(model, iraiFileUploadList, true);
			// 依頼その他ファイルアップロード情報を常に３件にする
			editOtherFileContents(model, otherFileUploadList, true);
			
			// 作業依頼内容
			irai.setTantoshaNm(model.getIrai().getTantoshaNm());
			irai.setIraiGyoshaCd(model.getIrai().getIraiGyoshaCd());
			irai.setIraiNaiyo(model.getIrai().getIraiNaiyo());
			irai.setHomonKiboYmd(model.getIrai().getHomonKiboYmd());
			irai.setHomonKiboJikanKbn(model.getIrai().getHomonKiboJikanKbn());
			irai.setHomonKiboBiko(model.getIrai().getHomonKiboBiko());
			irai.setGyoshaKaitoYmd(model.getIrai().getGyoshaKaitoYmd());
			irai.setGyoshaKaitoJikanKbn(model.getIrai().getGyoshaKaitoJikanKbn());
			irai.setGyoshaKaitoBiko(model.getIrai().getGyoshaKaitoBiko());
			irai.setIraiKokaiFlg(model.getIrai().getIraiKokaiFlg());
			irai.setTantoshaNm(model.getIrai().getTantoshaNm());
			
			// 作業状況
			if ((model.getSagyoJokyo() != null && StringUtils.isNotBlank(model.getSagyoJokyo().getToiawaseNo()))
				&& sagyoJokyo != null) {
				sagyoJokyo.setSagyoKanryoFlg(model.getSagyoJokyo().getSagyoKanryoFlg());
				sagyoJokyo.setSagyoKanryoJikan(model.getSagyoJokyo().getSagyoKanryoJikan());
				sagyoJokyo.setSagyoKanryoYmd(model.getSagyoJokyo().getSagyoKanryoYmd());
				sagyoJokyo.setJokyo(model.getSagyoJokyo().getJokyo());
				sagyoJokyo.setCause(model.getSagyoJokyo().getCause());
				sagyoJokyo.setJisshiNaiyo(model.getSagyoJokyo().getJisshiNaiyo());
				sagyoJokyo.setSagyoJokyoKokaiFlg(model.getSagyoJokyo().getSagyoJokyoKokaiFlg());
			}
		} else {
			// 依頼ファイルアップロード情報を常に３件にする
			editSagyoJokyoImageFileContents(model, iraiFileUploadList, false);
			// 依頼その他ファイルアップロード情報を常に３件にする
			editOtherFileContents(model, otherFileUploadList, false);
		}
		model.setIrai(irai);
		model.setBeforeIraiKokaiFlg(irai.getIraiKokaiFlg());
		model.setIraiUpdDt(irai.getUpdDt());
		
		model.setSagyoJokyo(sagyoJokyo);
		
		// 依頼業者情報取得
		model.setIraiGyosha(gyoshaDao.selectByPrimaryKey(irai.getIraiGyoshaCd()));
		
		// 更新初期表示処理の共通処理を呼び出す
		return executeUpdateInitCommonProcess(model);
	}

	/**
	 * サーバーサイドエラー発生時の、更新初期表示処理を行います。
	 *
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	@Override
	public TB033RequestFullEntryModel parepareInitInfoForUpdate(TB033RequestFullEntryModel model) {
		// サーバーサイドエラー用の初期表示処理を呼び出す
		model = this.parepareInitInfo(model);
		
		// 問い合わせ履歴情報取得
		RcpTToiawaseRireki toiawaseRireki = 
			toiawaseRirekiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (toiawaseRireki == null) {
			// 問い合わせ履歴情報が存在しない場合は、排他チェックエラー
			model.setInitError(true);
			
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		model.setToiawaseRireki(toiawaseRireki);
		
		// メール送信状況情報取得
		model.setMailRireki(mailRirekiDao.selectByPrimaryKey(
				model.getToiawaseNo() + "-" + model.getToiawaseRirekiNo().toPlainString()));

		List<RcpTFileUpload> iraiFileUploadList = null;
		List<RcpTOtherFileUpload> otherFileUploadList = null;
		if (model.getSagyoJokyo() != null) {
			// 依頼ファイルアップロード情報取得
			iraiFileUploadList = fileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			
			// 依頼その他ファイルアップロード情報取得
			otherFileUploadList = otherFileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			
			// 業者回答依頼ファイルアップロード情報取得
			List<TbTFileUpload> gyoshaFileUploadList =
				gyoshaFileUploadDao.selectBy(model.getToiawaseNo(),
						model.getToiawaseRirekiNo(), null);
			
			// 表示用の業者回答ファイルアップロード情報リスト取得
			model.setGyoshaUploadFileList(createGyoshaAnswerFileUploadList(
					gyoshaFileUploadList));
		}
		
		// 依頼ファイルアップロード情報を常に３件にする
		editSagyoJokyoImageFileContents(model, iraiFileUploadList, true);
		// 依頼その他ファイルアップロード情報を常に３件にする
		editOtherFileContents(model, otherFileUploadList, true);
		
		// 更新初期表示処理の共通処理を呼び出す
		return executeUpdateInitCommonProcess(model);
	}
	
	/**
	 * 更新初期表示処理の共通処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	private TB033RequestFullEntryModel executeUpdateInitCommonProcess(TB033RequestFullEntryModel model) {
		// 状況区分マスタリストの取得
		List<String> gamenKbnList = new ArrayList<String>();
		gamenKbnList.add(Constants.JOKYO_GAMEN_KBN_IRAI_ENTRY);
		gamenKbnList.add(Constants.JOKYO_GAMEN_KBN_ZENGAMEN);
		model.setJokyoKbnList(jokyoKbnDao.selectByGamenKbn(gamenKbnList, ""));
		
		// パスワードマスタのリスト生成
		List<String> userIdList = new ArrayList<String>();
		userIdList.add(model.getIrai().getTantoshaId());
		if (StringUtils.isNotBlank(model.getIrai().getLastUpdId())
			&& !userIdList.contains(model.getIrai().getLastUpdId())) {
			userIdList.add(model.getIrai().getLastUpdId());
		}
		if (StringUtils.isNotBlank(model.getIrai().getLastPrintId())
			&& !userIdList.contains(model.getIrai().getLastPrintId())) {
			userIdList.add(model.getIrai().getLastPrintId());
		}
		if (model.getSagyoJokyo() != null && !userIdList.contains(model.getSagyoJokyo().getLastUpdId())) {
			userIdList.add(model.getSagyoJokyo().getLastUpdId());
		}
		
		// パスワードマスタMap取得
		Map<String, String> userMap = natosPswdDao.convertMap(
				natosPswdDao.selectByList(userIdList, null));
		
		if (userMap.containsKey(model.getIrai().getTantoshaId())) {
			// 依頼情報の発注担当者ＩＤの和名変換
			model.getIrai().setTantoshaNmForId(userMap.get(model.getIrai().getTantoshaId()));
		}
		
		if (userMap.containsKey(model.getIrai().getLastUpdId())) {
			// 依頼情報の最終更新者ＩＤの和名変換
			model.getIrai().setLastUpdNmForId(userMap.get(model.getIrai().getLastUpdId()));
		}
		
		if (StringUtils.isNotBlank(model.getIrai().getLastPrintId())
			&& userMap.containsKey(model.getIrai().getLastPrintId())) {
			// 依頼情報の最終印刷者ＩＤの和名変換
			model.getIrai().setLastPrintNmForId(userMap.get(model.getIrai().getLastPrintId()));
		}
		
		if (model.getSagyoJokyo() != null && userMap.containsKey(model.getSagyoJokyo().getLastUpdId())) {
			// 作業状況情報の最終更新者ＩＤの和名変換
			model.getSagyoJokyo().setLastUpdNmForId(userMap.get(model.getSagyoJokyo().getLastUpdId()));
		}
		
		return model;
	}
	
	/**
	 * 作業依頼情報登録処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	@Override
	@Transactional(value="txManager")
	public void insertSagyoIraiInfo(TB033RequestFullEntryModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 問い合わせ履歴テーブル登録
		insertToiawaseRireki(model);
		
		// 依頼テーブル登録（登録時は新しくインスタンスを生成して登録）
		RcpTIrai irai = new RcpTIrai();
		// プロパティコピー
		CommonUtil.copyProperties(model.getIrai(), irai);
		irai.setToiawaseNo(model.getToiawaseNo());
		irai.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		irai.setTantoshaId(Constants.TORES_APL_ID);
		irai.setCreId(userContext.getLoginId());
		irai.setLastUpdId(userContext.getLoginId());
		irai.setCreKaishaId(userContext.getKaishaId());
		irai.setUpdKaishaId(userContext.getKaishaId());
		irai.setCreNm(userContext.getUserName());
		irai.setLastUpdNm(userContext.getUserName());
		
		// 依頼テーブル登録
		iraiDao.insert(irai);
		
		// 問い合わせ未読情報更新
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), false);
		
		// 登録チェック処理（後処理）
		validateSagyoIraiInfo(model);
		
		// 問い合わせ更新日更新
		updateToiawaseUpdDt(model);
		
		// アクセスログ登録処理
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM, 
				Constants.BUTTON_NM_INSERT, createKensakuJoken(model.getIrai()));
	}

	/**
	 * 作業依頼情報更新処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	@Override
	@Transactional(value="txManager")
	public void updateSagyoIraiInfo(TB033RequestFullEntryModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		if (model.isFileUploadExecutable()) {
			// ファイル指定時はファイルアップロード処理を実施する
			validateFileUpload(model);
		}
		
		if (model.isOtherFileUploadExecutable()) {
			// その他ファイル指定時はその他ファイルアップロード処理を実施する
			validateOtherFileUpload(model);
		}
		
		// 登録時は新しくインスタンスを生成して登録
		RcpTIrai irai = new RcpTIrai();
		// プロパティコピー
		CommonUtil.copyProperties(model.getIrai(), irai);
		
		irai.setToiawaseNo(model.getToiawaseNo());
		irai.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		irai.setUpdKaishaId(userContext.getKaishaId());
		irai.setLastUpdNm(userContext.getUserName());
		
		// 依頼テーブル更新
		if (iraiDao.updateForTores(irai) == 0) {
			// 依頼情報の更新件数が０件の場合は、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		// 画面にて作業状況の問い合わせＮＯがNOT NULLかを判定しているため、新しくインスタンスを生成して登録
		RcpTSagyoJokyo sagyoJokyo = new RcpTSagyoJokyo();
		// プロパティコピー
		CommonUtil.copyProperties(model.getSagyoJokyo(), sagyoJokyo);
		sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
		sagyoJokyo.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		// 画面にて作業完了チェックがチェックOFFになっていた場合には、作業完了日、作業完了時間をnullにする
		if (!model.isSagyoKanryoFlgChecked()) {
			sagyoJokyo.setSagyoKanryoYmd(null);
			sagyoJokyo.setSagyoKanryoJikan(null);
		}
		// 画面にて報告書の公開中止となっていた場合は、報告書公開フラグを未公開にする
		if (TB033RequestFullEntryModel.STOP_PUBLISH_FLG_ON.equals(model.getStopPublishFlg())){
			sagyoJokyo.setHokokushoKokaiFlg(RcpTSagyoJokyo.HOKOKUSHO_KOKAI_FLG_NOT_PUBLISH);
		}
		sagyoJokyo.setCreNm(userContext.getUserName());
		sagyoJokyo.setLastUpdNm(userContext.getUserName());
		
		// クリックしたボタン名（デフォルト：更新）
		String clickButtonNm = Constants.BUTTON_NM_UPDATE;
		// 作業状況テーブル更新
		if (sagyoJokyoDao.updateForTores(sagyoJokyo) == 0) {
			// 更新件数が０件の場合は、登録処理を行う
			sagyoJokyo.setHokokushoKokaiFlg(RcpTSagyoJokyo.HOKOKUSHO_KOKAI_FLG_NOT_PUBLISH);
			
			sagyoJokyoDao.insert(sagyoJokyo);
			
			// クリックしたボタン名を登録とする
			clickButtonNm = Constants.BUTTON_NM_INSERT;
		}
		
		// 更新チェック処理（後処理）
		// 公開チェックではＤＢの値を参照するため、
		// 一旦依頼情報・作業状況情報を更新した後に公開チェックを行う
		validateSagyoIraiInfo(model);
		
		if (model.isFileUploadExecutable() || model.isFileCommentUpdateExecutable()) {
			// ファイル指定時、または、ファイルコメント更新時はファイルアップロード処理を実施する
			executeFileUpload(model);
		}
		
		if (model.isOtherFileUploadExecutable() || model.isOtherFileCommentUpdateExecutable()) {
			// その他ファイル指定時、または、その他ファイルコメント更新時はその他ファイルアップロード処理を実施する
			executeOtherFileUpload(model);
		}
		
		// 履歴情報に登録するチェックがONの場合、問い合わせ履歴テーブル登録
		if (model.isToiawaseHistoryAutoRegistExcecutable()) {
			// 画面表示では、登録前の問い合わせ履歴を表示するため、保存しておく
			BigDecimal dispToiawaseRirekiNo = model.getToiawaseRirekiNo();
			// 問い合わせ履歴ＮＯを取得し問い合わせ履歴テーブル登録
			insertToiawaseRireki(model);
			// 問い合わせ履歴ＮＯを元に戻す
			model.setToiawaseRirekiNo(dispToiawaseRirekiNo);
		}
		
		// 問い合わせ更新日更新
		updateToiawaseUpdDt(model);
		
		// アクセスログ登録処理
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM, 
				clickButtonNm, createKensakuJoken(model.getSagyoJokyo()));
	}

	/**
	 * 作業依頼情報削除処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	@Override
	@Transactional(value="txManager")
	public void deleteSagyoIraiInfo(TB033RequestFullEntryModel model) {
		List<RcpTFileUpload> fileUploadList = null;
		List<RcpTOtherFileUpload> otherFileUploadList = null;
		
		if (sagyoJokyoDao.countBy(model.getToiawaseNo(), model.getToiawaseRirekiNo()) > 0) {
			// 作業状況の件数が１件以上ある場合
			// 依頼ファイルアップロード情報リスト取得
			fileUploadList = 
				fileUploadDao.selectBy(model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			// 依頼その他ファイルアップロード情報リスト取得
			otherFileUploadList =
				otherFileUploadDao.selectBy(model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			
			// 依頼ファイルアップロード情報退避
			RcpTFileUpload fileUpload = new RcpTFileUpload();
			fileUpload.setToiawaseNo(model.getToiawaseNo());
			fileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
			// 退避処理実行
			fileUploadDao.insertTaihiBy(fileUpload);
			
			// 依頼ファイルアップロード情報削除
			fileUploadDao.deleteBy(model.getToiawaseNo(), 
					model.getToiawaseRirekiNo(), null);
			
			// 依頼その他ファイルアップロード情報退避
			RcpTOtherFileUpload otherFileUpload = new RcpTOtherFileUpload();
			otherFileUpload.setToiawaseNo(model.getToiawaseNo());
			otherFileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
			// 退避処理実行
			otherFileUploadDao.insertTaihiBy(otherFileUpload);
			
			// 依頼その他ファイルアップロード情報削除
			otherFileUploadDao.deleteBy(model.getToiawaseNo(), 
					model.getToiawaseRirekiNo(), null);
			
			// 作業状況情報退避
			RcpTSagyoJokyo sagyoJokyo = new RcpTSagyoJokyo();
			sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
			sagyoJokyo.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
			sagyoJokyo.setUpdDt(model.getSagyoJokyoUpdDt());
			// 退避処理実行
			if (sagyoJokyoDao.insertTaihi(sagyoJokyo) == 0) {
				// 登録件数が０件の場合は、排他エラー
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			try {
				// 作業状況情報削除
				sagyoJokyoDao.delete(sagyoJokyo);
			} catch (DataIntegrityViolationException e) {
				// 外部キー参照エラーの場合
				throw new ValidationException(new ValidationPack().addError("MSG0041"));
			}
		}
		
		// 依頼情報退避
		RcpTIrai irai = new RcpTIrai();
		irai.setToiawaseNo(model.getToiawaseNo());
		irai.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		irai.setUpdDt(model.getIraiUpdDt());
		
		// 退避処理実行
		if (iraiDao.insertTaihi(irai) == 0) {
			// 登録件数が０件の場合は、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		try {
			// 依頼情報削除
			iraiDao.delete(irai);
		} catch (DataIntegrityViolationException e) {
			// 外部キー参照エラーの場合
			throw new ValidationException(new ValidationPack().addError("MSG0042"));
		}
		
		// 問い合わせ更新日更新
		updateToiawaseUpdDt(model);
		
		// アクセスログ登録処理
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM, 
				Constants.BUTTON_NM_DELETE, createKensakuJoken(model.getIrai()));
		
		if (fileUploadList != null && !fileUploadList.isEmpty()) {
			// 依頼ファイルアップロード情報が１件以上ある場合
			for (RcpTFileUpload fileUpload : fileUploadList) {
				// アップロードファイル削除処理
				if (!FileUploadLogic.deleteServerFile(
						UPLOAD_PATH_TLCSS_SAGYO_JOKYO + "/" + fileUpload.getUploadFileNm())) {
					// エラーの場合は削除失敗ファイル名を保存
					model.setFileDeleteError(true);
				}
			}
		}
		
		if (otherFileUploadList != null && !otherFileUploadList.isEmpty()) {
			// 依頼その他ファイルアップロード情報が１件以上ある場合
			for (RcpTOtherFileUpload otherFileUpload : otherFileUploadList) {
				// アップロードファイル削除処理
				if (!FileUploadLogic.deleteServerFile(
						UPLOAD_PATH_IRAI_FILE + "/" + otherFileUpload.getUploadFileNm())) {
					// エラーの場合は削除失敗ファイル名を保存
					model.setFileDeleteError(true);
				}
			}
		}
	}
	
	/**
	 * 業者依頼メール送信処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	@Override
	public void sendGyoshaIraiMail(TB033RequestFullEntryModel model) {
		// 業者情報取得
		RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(model.getIrai().getIraiGyoshaCd());
		if (gyosha == null) {
			// 業者情報が存在しない場合、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		String accessLogKensakuJoken = String.format("gyoshaCd=%s,toiawaseNo=%s,toiawaseRirekiNo=%s", 
				model.getIrai().getIraiGyoshaCd(), model.getToiawaseNo(), model.getToiawaseRirekiNo().toString());
		
		// アクセスログ登録
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM,
								TB033RequestFullEntryModel.BUTTON_NM_IRAI_MAIL,
								accessLogKensakuJoken);

		try {
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// システムマスタから取得
			// システム名
			String systemNm = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_NAME);
			// システムＵＲＬ
			String systemUrl = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL);
			// 返信メールアドレス
			
			// 作業依頼メール　件名
			String subjectSagyoIraiMail = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_SUBJECT_SAGYO_IRAI_MAIL);
			// 差出元アドレス
			String fromMailAddress = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ADDRESS);
			// 差出元アカウント
			String fromMailAccount = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ACCOUNT);
			// 差出元パスワード
			String fromMailPassword = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_PASSWORD);
			// 返信メールアドレス
			String returnMailAddress = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_RETURN_MAIL_ADDRESS);
			// 差出人マスタ情報取得
			RcpMSashidashinin sashidashinin = sashidashininDao.selectByPrimaryKey(
					RcpMSashidashinin.KBN_SAGYO_IRIA_MAIL_INFO, userContext.getKaishaId());
			if (sashidashinin == null) {
				throw new ApplicationException("差出人情報が存在しません。");
			}
			
			// 作業依頼URLを生成
			String sagyoIraiUrl = createSagyoIraiUrl(model, systemUrl);

			// 置換文字列Map生成
			VelocityWrapper wrapper = new VelocityWrapper(
					MailTemplate.SAGYO_IRAI_MAIL_FILE_NAME);

			// 返信メールアドレス（置換文字列）
			String sashidashininReturnMailAddress = 
				StringUtils.defaultString(sashidashinin.getMailAddress());
			
			// 置換文字列
			// 業者名
			wrapper.put(MAIL_KEY_GYOSHA_NAME, gyosha.getGyoshaNm());
			// システム名
			wrapper.put(MAIL_KEY_SYSTEM_NAME, systemNm);
			// 作業依頼URL
			wrapper.put(MAIL_KEY_SAGYO_IRAI_URL, sagyoIraiUrl);
			// 問い合わせNO
			wrapper.put(MAIL_KEY_TOIAWASE_NO, model.getToiawaseNo());
			// システム問い合わせ先電話番号は、差出人マスタから情報を取得
			wrapper.put(MAIL_KEY_CONTACT_INFORMAITION, sashidashinin.getTelNo());
			// 返信メールアドレス
			wrapper.put(MAIL_KEY_REPLY_MAIL_ADDRESS, sashidashininReturnMailAddress);
			// 送信元会社名
			wrapper.put(MAIL_KEY_NOTICE_COMPAY_NAME, sashidashinin.getKaishaNm());

			// 宛先取得
			String toMailAddress = "";
			if (gyosha.isSameAsSagyoIraiMailAtesaki()) {
				// 作業用TELと同じ場合
				toMailAddress = gyosha.getSagyoMailAddress();
			} else {
				// それ以外の場合
				toMailAddress = gyosha.getIraiMailAtesakiAddress();
			}

			VelocityEmail email = new VelocityEmail();
			// 差出元アドレス
			email.setFrom(fromMailAddress);
			// 認証
			email.setAuthenticator(new DefaultAuthenticator(
					fromMailAccount, fromMailPassword));
			// 宛先
			email.addTo(toMailAddress);
			// 宛先BCC
			if (StringUtils.isNotBlank(sashidashinin.getBccAddress())) {
				email.addBcc(StringUtils.split(sashidashinin.getBccAddress(), ","));
			}
			// 返信メールアドレス
			email.addReplyTo(returnMailAddress);
			// 件名
			email.setSubject(subjectSagyoIraiMail);
			// 置換文字列Mapの設定
			email.setVelocityWrapper(wrapper);

			// メール送信
			email.send();
		} catch (EmailException e) {
			// メール送信に失敗した場合
			throw new ValidationException(new ValidationPack().addError("MSG0040", "センター業者依頼メール送信"));
		} catch (Exception e) {
			// その他例外
			throw new ApplicationException("センター業者依頼メール送信に失敗しました。", e);
		}
	}
	
	/**
	 * 作業状況画像ファイル削除処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	@Override
	@Transactional(value="txManager")
	public void deleteSagyoJokyoImageFile(TB033RequestFullEntryModel model) {
		// パラメータチェック
		if (StringUtils.isBlank(model.getUploadFileNm())) {
			// パラメータのアップロードファイル名が空欄の場合は、パラメータチェックエラー
			throw new ApplicationException("アップロードファイル名不正：パラメータのアップロードファイル名");
		}

		if (model.getFileIndex() == null) {
			// パラメータのファイルインデックスがNULLの場合は、パラメータチェックエラー
			throw new ApplicationException("ファイルインデックス不正：パラメータのファイルインデックス");
		}
		
		// 依頼ファイルアップロード情報退避
		RcpTFileUpload fileUpload = new RcpTFileUpload();
		fileUpload.setToiawaseNo(model.getToiawaseNo());
		fileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		fileUpload.setFileIndex(model.getFileIndex());
		
		// 退避処理実行
		fileUploadDao.insertTaihiWithoutOptimisticLock(fileUpload);
		
		// 依頼ファイルアップロード情報削除
		fileUploadDao.deleteWithoutOptimisticLock(model.getToiawaseNo(), 
				model.getToiawaseRirekiNo(), model.getFileIndex());
		
		// 問い合わせ更新日更新
		updateToiawaseUpdDt(model);
		
		// アクセスログ登録処理
		String accessLogKensakuJoken = String.format("toiawaseNo=%s,toiawaseRirekiNo=%s,uploadFileNm=%s,fileIndex=%s", 
				model.getToiawaseNo(), model.getToiawaseRirekiNo().toString(), model.getUploadFileNm(), model.getFileIndex().toString());
		
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM, 
				Constants.BUTTON_NM_DELETE, accessLogKensakuJoken);
		
		// アップロードファイル削除
		String fileNm = UPLOAD_PATH_TLCSS_SAGYO_JOKYO +
						System.getProperty("file.separator") +
						model.getUploadFileNm();

		// アップロード実ファイル削除処理
		model.setFileDeleteError(!FileUploadLogic.deleteServerFile(fileNm));
	}
	
	/**
	 * その他ファイル削除処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	@Override
	@Transactional(value="txManager")
	public void deleteOtherFile(TB033RequestFullEntryModel model) {
		// パラメータチェック
		if (StringUtils.isBlank(model.getUploadFileNm())) {
			// パラメータのアップロードファイル名が空欄の場合は、パラメータチェックエラー
			throw new ApplicationException("アップロードファイル名不正：パラメータのアップロードファイル名");
		}

		if (model.getFileIndex() == null) {
			// パラメータのファイルインデックスがNULLの場合は、パラメータチェックエラー
			throw new ApplicationException("ファイルインデックス不正：パラメータのファイルインデックス");
		}
		
		// 依頼その他ファイルアップロード情報退避
		RcpTOtherFileUpload otherFileUpload = new RcpTOtherFileUpload();
		otherFileUpload.setToiawaseNo(model.getToiawaseNo());
		otherFileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		otherFileUpload.setFileIndex(model.getFileIndex());
		
		// 退避処理実行
		otherFileUploadDao.insertTaihiWithoutOptimisticLock(otherFileUpload);
		
		// 依頼その他ファイルアップロード情報削除
		otherFileUploadDao.deleteWithoutOptimisticLock(model.getToiawaseNo(), 
				model.getToiawaseRirekiNo(), model.getFileIndex());
		
		// 問い合わせ更新日更新
		updateToiawaseUpdDt(model);
		
		// アクセスログ登録処理
		String accessLogKensakuJoken = String.format("toiawaseNo=%s,toiawaseRirekiNo=%s,uploadFileNm=%s,fileIndex=%s", 
				model.getToiawaseNo(), model.getToiawaseRirekiNo().toString(), model.getUploadFileNm(), model.getFileIndex().toString());
		
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM, 
				Constants.BUTTON_NM_DELETE, accessLogKensakuJoken);
		
		// アップロードファイル削除
		String fileNm = UPLOAD_PATH_IRAI_FILE +
						System.getProperty("file.separator") +
						model.getUploadFileNm();

		// アップロード実ファイル削除処理
		model.setFileDeleteError(!FileUploadLogic.deleteServerFile(fileNm));
	}
	
	/**
	 * 画像ダウンロード処理の妥当性チェックを行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	@Override
	public void validateImageFileDownlod(TB033RequestFullEntryModel model) {
		// 問い合わせ情報取得
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// 問い合わせ情報がない場合、チェックエラー
			throw new ForbiddenException("問い合わせ情報が存在しません。");
		}
		
		// 依頼情報取得
		RcpTIrai irai = iraiDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (irai == null) {
			// 依頼情報がない場合、チェックエラー
			throw new ForbiddenException("依頼情報が存在しません。");
		}
		
		// 依頼ファイルアップロード情報取得
		RcpTFileUpload fileUpload = fileUploadDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo(), model.getFileIndex());
		if (fileUpload == null) {
			// 依頼ファイルアップロード情報がない場合、チェックエラー
			throw new ForbiddenException("依頼ファイルアップロード情報が存在しません。");
		}
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// 権限が委託会社SV、委託会社OPの場合にチェック
			if (StringUtils.isBlank(toiawase.getKokyakuId())) {
				// ＩＤ無し顧客だった場合
				RcpTKokyakuWithNoId kokyakuWithoutId = 
					kokyakuWithoutDao.selectByPrimaryKey(model.getToiawaseNo());
				if (kokyakuWithoutId == null) {
					// ＩＤ無し顧客情報が存在しない場合は、チェックエラー
					throw new ForbiddenException("ＩＤ無し顧客情報が存在しません。");
				}
				
				// 依頼テーブルの登録会社ＩＤと、ＩＤ無し顧客テーブルの登録会社ＩＤを比較し、
				// 同じでなかった場合は、エラー
				if (!irai.getCreKaishaId().equals(kokyakuWithoutId.getCreKaishaId())) {
					throw new ForbiddenException("依頼情報とＩＤ無し顧客情報の登録会社が一致しません。");
				}
			} else {
				// ＩＤ有り顧客だった場合
				if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(),
						toiawase.getKokyakuId())) {
					// 委託会社関連チェックエラーだった場合は、エラー
					throw new ForbiddenException("アクセス権限が存在しません。");
				}
			}
		}
	}
	
	/**
	 * PDF作成処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @return 依頼登録画面モデル
	 */
	@Override
	public TB033RequestFullEntryModel createPdf(TB033RequestFullEntryModel model) {
		// 依頼情報取得
		RcpTIrai irai = iraiDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (irai == null) {
			// 依頼情報がない場合、チェックエラー
			throw new ForbiddenException("依頼情報が存在しません。");
		}
		
		// 問い合わせ情報取得
		RcpTToiawase toiawase = 
			toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// 権限が委託会社SV、委託会社OPの場合にチェック
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(),
					toiawase.getKokyakuId())) {
				// 委託会社関連チェックエラーだった場合は、エラー
				throw new ForbiddenException("アクセス権限が存在しません。");
			}
		}
		
		// 作業依頼書
		List<String[]> sagyoIraiDataList =
			sagyoIraiHyoLogic.getSagyoIraiCsvList(model.getToiawaseNo(),
					model.getToiawaseRirekiNo(), userContext.getKaishaId());
		// 作業報告書（業者用）
		List<String[]> gyoshaSagyoHokokuDataList =
			gyoshaSagyoHokokuHyoLogic.getGyoshaSagyoHokokuCsvList(
					model.getToiawaseNo(), userContext.getKaishaId());

		// 全てのデータ
		List<String[]> allDataList = new ArrayList<String[]>();
		allDataList.addAll(sagyoIraiDataList);
		allDataList.addAll(gyoshaSagyoHokokuDataList);

		// PDF出力（マルチ出力）
		String pdfUrl = sagyoIraiHyoLogic.outputPdfBySsh(allDataList);
		
		// 依頼情報テーブルの最終印刷者情報更新
		irai.setLastPrintId(userContext.getLoginId());
		irai.setLastPrintNm(userContext.getUserName());
		
		iraiDao.updatePrintInfo(irai);
		
		// 取得したパスからファイル名を取得する
		String pdfNm = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);
		
		// コピー元ファイル名のパスを設定
		String fromCopyPdfPath = UPLOAD_PATH_PDF + System.getProperty("file.separator") + pdfNm;
		// コピー先ファイル名のパスを設定
		String toCopyPdfPath = UPLOAD_PATH_TEMP_REPORT + System.getProperty("file.separator") + pdfNm;
		
		// ファイルコピーの実施
		FileRemoteUtil.remoteCopyFileBySsh(fromCopyPdfPath, toCopyPdfPath);
		
		model.setMakePdfPath(toCopyPdfPath);
		model.setPdfNm(pdfNm);
		
		// 画面を最新情報に更新する
		model = this.getSagyoIraiInfo(model);
		
		return model;
	}
	
	/**
	 * 初期表示パラメータチェックを行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @throws ApplicationException パラメータチェックエラー発生時
	 */
	private void validateInitParameter(TB033RequestFullEntryModel model) {
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			// パラメータの問い合わせＮＯが空欄の場合は、パラメータチェックエラー
			throw new ApplicationException("問い合わせＮＯ不正：パラメータの問い合わせＮＯ");
		}
		if (model.isUpdate() && model.getToiawaseRirekiNo() == null) {
			// パラメータの問い合わせ履歴ＮＯがNULLの場合は、パラメータチェックエラー
			throw new ApplicationException("問い合わせ履歴ＮＯ不正：パラメータの問い合わせ履歴ＮＯ");
		}
	}
	
	/**
	 * 作業依頼情報の登録・更新処理の妥当性チェックを行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @throws ValidationException 妥当性チェックエラー発生時
	 */
	private void validateSagyoIraiInfo(TB033RequestFullEntryModel model) {
		// 依頼情報の問い合わせ公開チェック
		if (!toiawaseCheckLogic.isValid(
				CheckToPublishToiawaseLogic.CONTENT_TYPE_IRAI,
				model.getIrai().getIraiKokaiFlg(),
				model.getToiawaseNo(),
				model.getToiawaseRirekiNo())) {
			// チェックNGの場合、サーバチェックエラー
			String publishMsg = model.getIrai().isPublished() ? "公開" : "未公開に";

			// 公開設定が無効の場合、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0030", "依頼情報", publishMsg));
		}
		
		if (model.isUpdate()) {
			// 更新処理の場合
			// 作業状況情報の問い合わせ公開チェック
			if (!toiawaseCheckLogic.isValid(
					CheckToPublishToiawaseLogic.CONTENT_TYPE_SAGYO_JOKYO,
					model.getSagyoJokyo().getSagyoJokyoKokaiFlg(),
					model.getToiawaseNo(),
					model.getToiawaseRirekiNo())) {
				// チェックNGの場合、サーバチェックエラー
				String publishMsg = model.getSagyoJokyo().isPublished() ? "公開" : "未公開に";

				// 公開設定が無効の場合、エラー
				throw new ValidationException(new ValidationPack().addError("MSG0030", "作業状況", publishMsg));
			}
			
			// 作業報告書の問い合わせ公開チェック
			if (!toiawaseCheckLogic.isValid(
					CheckToPublishToiawaseLogic.CONTENT_TYPE_SAGYO_HOKOKUSHO,
					model.getSagyoJokyo().getSagyoJokyoKokaiFlg(),
					model.getToiawaseNo(),
					model.getToiawaseRirekiNo())) {
				// チェックNGの場合、サーバチェックエラー
				String publishMsg = model.getSagyoJokyo().isPublished() ? "公開" : "未公開に";

				// 公開設定が無効の場合、エラー
				throw new ValidationException(new ValidationPack().addError("MSG0030", "作業状況", publishMsg));
			}
		}
	}
	
	/**
	 * ファイルアップロード時の妥当性チェックを行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @throws ValidationException 妥当性チェックエラー発生時
	 */
	private void validateFileUpload(TB033RequestFullEntryModel model) {
		// ユーザ情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 最大ファイルバイト数取得
		Long maxFileSize = 
			Long.parseLong(userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_MAX_FILESIZE));
		
		for (File uploaFile : model.getSagyoJokyoImageFiles()) {
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
	 * その他ファイルアップロード時の妥当性チェックを行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @throws ValidationException 妥当性チェックエラー発生時
	 */
	private void validateOtherFileUpload(TB033RequestFullEntryModel model) {
		// ユーザ情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 最大ファイルバイト数取得
		Long maxFileSize = 
			Long.parseLong(userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_GYOSHA_FILESIZE_TO_MAX));
		
		for (File uploaFile : model.getOtherFiles()) {
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
	 * 作業状況画像内容の編集を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @param uploadedList 依頼ファイルアップロードリスト
	 * @param isRestore 値復元フラグ（true：値を復元する）
	 */
	private void editSagyoJokyoImageFileContents(TB033RequestFullEntryModel model, List<RcpTFileUpload> uploadedList, boolean isRestore) {
		RcpTFileUpload[] sagyoJokyoImageFiles = 
			new RcpTFileUpload[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		String[] fileComments =
			new String[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		String[] uploadFileNms = 
			new String[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		BigDecimal[] fileIndexs =
			new BigDecimal[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		
		if (uploadedList == null || uploadedList.isEmpty()) {
			if (!isRestore) {
				// エラーでなかった場合は、ファイルコメントを初期化
				model.setSagyoJokyoImageFileComments(fileComments);
			}
			// ファイル関係は空欄を指定（セキュリティ都合上、削除されるため）
			model.setUploadedFiles(sagyoJokyoImageFiles);
			model.setSagyoJokyoImageFileUploadFileNms(uploadFileNms);
			for (int i = 0; i < TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT; i++) {
				fileIndexs[i] = new BigDecimal(i + 1);
			}
			model.setSagyoJokyoImageFileFileIndexes(fileIndexs);
			
			return;
		}

		// 画面に常に３件表示するため、不足分を空のインスタンスで埋める
		for (int i = 0; i < TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT; i++) {
			// 現在のファイルインデックス
			int currentIdx = i + 1;

			boolean isExist = false;
			for (RcpTFileUpload sagyoJokyoImageFile : uploadedList) {
				if (currentIdx == sagyoJokyoImageFile.getFileIndex().intValue()) {
					// データがある場合は、配列に格納
					sagyoJokyoImageFiles[i] = sagyoJokyoImageFile;
					fileComments[i] = sagyoJokyoImageFile.getFileComment();
					uploadFileNms[i] = sagyoJokyoImageFile.getUploadFileNm();
					fileIndexs[i] = sagyoJokyoImageFile.getFileIndex();
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				// ループカウントがアップロード済ファイル件数以上＝データ無し
				sagyoJokyoImageFiles[i] = null;
				fileComments[i] = null;
				uploadFileNms[i] = null;
				fileIndexs[i] = new BigDecimal(i + 1);
			}
		}

		model.setUploadedFiles(sagyoJokyoImageFiles);
		if (!isRestore) {
			// エラーでない場合は、コメントを設定
			model.setSagyoJokyoImageFileComments(fileComments);
		}
		model.setSagyoJokyoImageFileUploadFileNms(uploadFileNms);
		model.setSagyoJokyoImageFileFileIndexes(fileIndexs);
	}
	
	/**
	 * その他ファイル内容の編集を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @param uploadedList 依頼その他ファイルアップロードリスト
	 * @param isRestore 値復元フラグ（true：値を復元する）
	 */
	private void editOtherFileContents(TB033RequestFullEntryModel model, List<RcpTOtherFileUpload> uploadedList, boolean isRestore) {
		RcpTOtherFileUpload[] otherFiles = 
			new RcpTOtherFileUpload[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		String[] fileComments =
			new String[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		String[] uploadFileNms = 
			new String[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		BigDecimal[] fileIndexs =
			new BigDecimal[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		
		if (uploadedList == null || uploadedList.isEmpty()) {
			if (!isRestore) {
				// エラーでなかった場合は、ファイルコメントを初期化
				model.setOtherFileComments(fileComments);
			}
			// ファイル関係は空欄を指定（セキュリティ都合上、削除されるため）
			model.setUploadedOtherFiles(otherFiles);
			model.setOtherFileUploadFileNms(uploadFileNms);
			for (int i = 0; i < TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT; i++) {
				fileIndexs[i] = new BigDecimal(i + 1);
			}
			model.setOtherFileUploadFileFileIndexes(fileIndexs);
			
			return;
		}

		// 画面に常に３件表示するため、不足分を空のインスタンスで埋める
		for (int i = 0; i < TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT; i++) {
			// 現在のファイルインデックス
			int currentIdx = i + 1;

			boolean isExist = false;
			for (RcpTOtherFileUpload otherFile : uploadedList) {
				if (currentIdx == otherFile.getFileIndex().intValue()) {
					// データがある場合は、配列に格納
					otherFiles[i] = otherFile;
					fileComments[i] = otherFile.getFileComment();
					uploadFileNms[i] = otherFile.getUploadFileNm();
					fileIndexs[i] = otherFile.getFileIndex();
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				// ループカウントがアップロード済ファイル件数以上＝データ無し
				otherFiles[i] = null;
				fileComments[i] = null;
				uploadFileNms[i] = null;
				fileIndexs[i] = new BigDecimal(i + 1);
			}
		}
		
		model.setUploadedOtherFiles(otherFiles);
		if (!isRestore) {
			// エラーでない場合は、コメントを設定
			model.setOtherFileComments(fileComments);
		}
		model.setOtherFileUploadFileNms(uploadFileNms);
		model.setOtherFileUploadFileFileIndexes(fileIndexs);
	}
	
	/**
	 * 表示用の業者回答ファイルアップロード情報リストを作成します。
	 *
	 * @param uploadedList ファイルアップロード情報リスト
	 * @return 表示用ファイルアップロード情報リスト
	 */
	private List<TbTFileUpload> createGyoshaAnswerFileUploadList(List<TbTFileUpload> uploadedList) {
		// 画面表示するために新しくリストを生成する
		List<TbTFileUpload> dispFileUploadList = new ArrayList<TbTFileUpload>();

		// 画面表示する分だけループ
		for (int i = 0; i < TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT; i++) {
			// ファイルインデックス
			int fileIdx = i + 1;
			
			// ファイルデータ存在フラグ
			boolean dataExists = false;
			for (TbTFileUpload fileUpload : uploadedList) {
				if (fileIdx == fileUpload.getFileIndex().intValue()) {
					// 該当するファイルインデックスがあった場合
					dispFileUploadList.add(fileUpload);
					// ファイルデータ存在フラグをONにする
					dataExists = true;
				}
			}
			
			if (!dataExists) {
				// データが存在しなかった場合には、空のインスタンスで行を埋める
				dispFileUploadList.add(new TbTFileUpload());
			}
		}
		
		return dispFileUploadList;
	}
	
	/**
	 * ファイルのアップロード処理を実行します。
	 *
	 * @param model 依頼登録登録画面モデル
	 */
	private void executeFileUpload(TB033RequestFullEntryModel model) {
		// ユーザ情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		List<String> uploadFileNameList = new ArrayList<String>();
		List<File> uploadFileList = new ArrayList<File>();

		String[] inputFileNms = model.getSagyoJokyoImageFileNmByArray();
		// 画面で表示されている件数分、登録
		for (int i = 0; i < model.getSagyoJokyoImageFiles().length; i++) {
			if (StringUtils.isNotBlank(model.getSagyoJokyoImageFileUploadFileNms()[i])) {
				// アップロードファイル名があった場合（既に登録されているので、更新処理）
				updateFileUploadInfo(model,
						userContext,
						model.getSagyoJokyoImageFileUploadFileNms()[i],
						model.getSagyoJokyoImageFileComments()[i],
						model.getSagyoJokyoImageFileFileIndexes()[i]);
				
			} else {
				// アップロードファイル名がない場合（登録されていないので、登録処理）
				if (model.getSagyoJokyoImageFiles()[i] != null && StringUtils.isNotBlank(inputFileNms[i])) {
					// 連番を取得する
					String newFileNo = fileUploadDao.selectFileUploadNo();

					// アップロードファイル名作成
					String uploadFileNm = FileUploadLogic.makeUploadFileNm(
							inputFileNms[i],
							model.getToiawaseNo() + "-" + model.getToiawaseRirekiNo().toString() + "-" + newFileNo);

					// ファイル指定があった場合のみ登録
					insertFileUploadInfo(model, userContext, uploadFileNm,
							inputFileNms[i], model.getSagyoJokyoImageFileComments()[i],
							model.getSagyoJokyoImageFileFileIndexes()[i]);
					
					uploadFileNameList.add(uploadFileNm);
					uploadFileList.add(model.getSagyoJokyoImageFiles()[i]);
				}
			}
		}

		if (!uploadFileList.isEmpty()) {
			// ファイル登録がされていれば、ファイルのアップロード
			FileUploadLogic.uploadFileList(uploadFileList,
					uploadFileNameList, UPLOAD_PATH_TLCSS_SAGYO_JOKYO);
		}
	}
	
	/**
	 * その他ファイルのアップロード処理を実行します。
	 *
	 * @param model 依頼登録登録画面モデル
	 */
	private void executeOtherFileUpload(TB033RequestFullEntryModel model) {
		// ユーザ情報取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		List<String> uploadFileNameList = new ArrayList<String>();
		List<File> uploadFileList = new ArrayList<File>();

		String[] inputFileNms = model.getOtherFileNmByArray();
		// 画面で表示されている件数分、登録
		for (int i = 0; i < model.getOtherFiles().length; i++) {
			if (StringUtils.isNotBlank(model.getOtherFileUploadFileNms()[i])) {
				// アップロードファイル名があった場合（既に登録されているので、更新処理）
				updateOtherFileUploadInfo(model,
						userContext,
						model.getOtherFileUploadFileNms()[i],
						model.getOtherFileComments()[i],
						model.getOtherFileUploadFileFileIndexes()[i]);
			} else {
				// アップロードファイル名がない場合（登録されていないので、登録処理）
				if (model.getOtherFiles()[i] != null && StringUtils.isNotBlank(inputFileNms[i])) {
					// 連番を取得する
					String newFileNo = fileUploadDao.selectFileUploadNo();

					// アップロードファイル名作成
					String uploadFileNm = FileUploadLogic.makeUploadFileNm(
							inputFileNms[i],
							model.getToiawaseNo() + "-" + model.getToiawaseRirekiNo().toString() + "-" + newFileNo);

					// ファイル指定があった場合のみ登録
					insertOtherFileUploadInfo(model, userContext, uploadFileNm,
							inputFileNms[i], model.getOtherFileComments()[i],
							model.getOtherFileUploadFileFileIndexes()[i]);
					
					uploadFileNameList.add(uploadFileNm);
					uploadFileList.add(model.getOtherFiles()[i]);
				}
			}
		}

		if (!uploadFileList.isEmpty()) {
			// ファイル登録がされていれば、ファイルのアップロード
			FileUploadLogic.uploadFileList(uploadFileList,
					uploadFileNameList, UPLOAD_PATH_IRAI_FILE);
		}
	}
	
	/**
	 * 問い合わせ履歴テーブルへの登録処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	private void insertToiawaseRireki(TB033RequestFullEntryModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// 問い合わせ履歴ＮＯ発行
		BigDecimal newToiawaseNo =
			toiawaseRirekiDao.selectNewRirekiNo(model.getToiawaseNo());
		
		model.setToiawaseRirekiNo(newToiawaseNo);
		
		// 問い合わせ履歴テーブル登録処理実施
		RcpTToiawaseRireki toiawaseRireki = new RcpTToiawaseRireki();
		toiawaseRireki.setToiawaseNo(model.getToiawaseNo());
		toiawaseRireki.setToiawaseRirekiNo(newToiawaseNo);
		
		if (model.isToiawaseHistoryAutoRegistExcecutable()) {
			// 履歴情報に登録するチェックがONの場合
			toiawaseRireki.setUketsukeYmd(model.getSagyoJokyo().getSagyoKanryoYmd());
			toiawaseRireki.setUketsukeJikan(model.getSagyoJokyo().getSagyoKanryoJikan());
			toiawaseRireki.setTantoshaId(Constants.TORES_APL_ID);
			toiawaseRireki.setToiawaseNaiyo(model.getSagyoJokyo().getJisshiNaiyo());
			toiawaseRireki.setJokyoKbn(model.getHistoryAutoRegistJokyoKbn());
			toiawaseRireki.setToiawaseRirekiKokaiFlg(RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_MIKOKAI);
		} else {
			toiawaseRireki.setUketsukeYmd(DateUtil.toTimestamp(DateUtil.getSysDateString(), "yyyy/MM/dd"));
			toiawaseRireki.setUketsukeJikan(DateUtil.getSysDateString("HHmm"));
			toiawaseRireki.setTantoshaId(Constants.TORES_APL_ID);
			toiawaseRireki.setToiawaseNaiyo(model.getIrai().getIraiNaiyo());
			toiawaseRireki.setJokyoKbn(RcpTToiawaseRireki.JOKYO_KBN_GYOSHATEHAI);
			toiawaseRireki.setToiawaseRirekiKokaiFlg(model.getIrai().getIraiKokaiFlg());
		}
		if (StringUtils.isNotBlank(model.getToiawase().getKokyakuId())){
			toiawaseRireki.setHoukokuPrintFlg(RcpTToiawaseRireki.HOUKOKU_PRINT_FLG_ON);
		} else {
			toiawaseRireki.setHoukokuPrintFlg(RcpTToiawaseRireki.HOUKOKU_PRINT_FLG_OFF);
		}
		toiawaseRireki.setCreId(userContext.getLoginId());
		toiawaseRireki.setLastUpdId(userContext.getLoginId());
		toiawaseRireki.setTantoshaNm(model.getIrai().getTantoshaNm());
		toiawaseRireki.setCreKaishaId(userContext.getKaishaId());
		toiawaseRireki.setUpdKaishaId(userContext.getKaishaId());
		toiawaseRireki.setCreNm(userContext.getUserName());
		toiawaseRireki.setLastUpdNm(userContext.getUserName());
		toiawaseRireki.setRegistKbn(RcpTToiawaseRireki.REGIST_KBN_TORES);
		
		// 問い合わせ履歴テーブル登録
		toiawaseRirekiDao.insert(toiawaseRireki);
		
		// 問い合わせ未読情報更新
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), false);
	}
	
	/**
	 * 依頼ファイルアップロード情報の登録処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @param userContext ログインユーザ情報
	 * @param uploadFileNm アップロードファイル名
	 * @param imageFileNm 画像ファイル名
	 * @param fileComment ファイルコメント
	 * @param fileIndex ファイルインデックス
	 * @return 登録件数
	 */
	private int insertFileUploadInfo(TB033RequestFullEntryModel model,
			TLCSSB2BUserContext userContext, String uploadFileNm, 
			String imageFileNm, String fileComment, BigDecimal fileIndex) {

		// 依頼ファイルアップロードテーブル登録
		RcpTFileUpload fileUpload = new RcpTFileUpload();
		fileUpload.setToiawaseNo(model.getToiawaseNo());
		fileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		fileUpload.setUploadFileNm(uploadFileNm);
		fileUpload.setBaseFileNm(FilenameUtils.getName(new File(imageFileNm).getName()));
		fileUpload.setFileComment(fileComment);
		fileUpload.setCreKaishaId(userContext.getKaishaId());
		fileUpload.setUpdKaishaId(userContext.getKaishaId());
		fileUpload.setCreNm(userContext.getUserName());
		fileUpload.setLastUpdNm(userContext.getUserName());
		fileUpload.setRegistKbn(RcpTFileUpload.REGIST_KBN_TORES);
		fileUpload.setFileIndex(fileIndex);
		
		return fileUploadDao.insert(fileUpload);
	}
	
	/**
	 * 依頼その他ファイルアップロード情報の登録処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @param userContext ログインユーザ情報
	 * @param uploadFileNm アップロードファイル名
	 * @param imageFileNm 画像ファイル名
	 * @param fileComment ファイルコメント
	 * @param fileIndex ファイルインデックス
	 * @return 登録件数
	 */
	private int insertOtherFileUploadInfo(TB033RequestFullEntryModel model,
			TLCSSB2BUserContext userContext, String uploadFileNm, 
			String imageFileNm, String fileComment, BigDecimal fileIndex) {

		// 依頼その他ファイルアップロードテーブル登録
		RcpTOtherFileUpload otherFileUpload = new RcpTOtherFileUpload();
		otherFileUpload.setToiawaseNo(model.getToiawaseNo());
		otherFileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		otherFileUpload.setUploadFileNm(uploadFileNm);
		otherFileUpload.setBaseFileNm(FilenameUtils.getName(new File(imageFileNm).getName()));
		otherFileUpload.setFileComment(fileComment);
		otherFileUpload.setCreKaishaId(userContext.getKaishaId());
		otherFileUpload.setUpdKaishaId(userContext.getKaishaId());
		otherFileUpload.setCreNm(userContext.getUserName());
		otherFileUpload.setLastUpdNm(userContext.getUserName());
		otherFileUpload.setRegistKbn(RcpTFileUpload.REGIST_KBN_TORES);
		otherFileUpload.setFileIndex(fileIndex);
		
		return otherFileUploadDao.insert(otherFileUpload);
	}
	
	/**
	 * 依頼ファイルアップロード情報の更新処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @param userContext ログインユーザ情報
	 * @param uploadFileNm アップロードファイル名
	 * @param fileComment ファイルコメント
	 * @param fileIndex ファイルインデックス
	 * @return 更新件数
	 */
	private int updateFileUploadInfo(TB033RequestFullEntryModel model,
			TLCSSB2BUserContext userContext, String uploadFileNm, String fileComment, BigDecimal fileIndex) {
		// 依頼ファイルアップロードテーブル更新
		RcpTFileUpload fileUpload = new RcpTFileUpload();
		fileUpload.setToiawaseNo(model.getToiawaseNo());
		fileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		fileUpload.setUploadFileNm(uploadFileNm);
		fileUpload.setFileComment(fileComment);
		fileUpload.setUpdKaishaId(userContext.getKaishaId());
		fileUpload.setLastUpdNm(userContext.getUserName());
		fileUpload.setFileIndex(fileIndex);
		
		return fileUploadDao.updateForTores(fileUpload);
	}
	
	/**
	 * 依頼その他ファイルアップロード情報の更新処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 * @param userContext ログインユーザ情報
	 * @param uploadFileNm アップロードファイル名
	 * @param fileComment ファイルコメント
	 * @param fileIndex ファイルインデックス
	 * @return 更新件数
	 */
	private int updateOtherFileUploadInfo(TB033RequestFullEntryModel model,
			TLCSSB2BUserContext userContext, String uploadFileNm, String fileComment, BigDecimal fileIndex) {
		// 依頼その他ファイルアップロードテーブル更新
		RcpTOtherFileUpload otherFileUpload = new RcpTOtherFileUpload();
		otherFileUpload.setToiawaseNo(model.getToiawaseNo());
		otherFileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		otherFileUpload.setUploadFileNm(uploadFileNm);
		otherFileUpload.setFileComment(fileComment);
		otherFileUpload.setUpdKaishaId(userContext.getKaishaId());
		otherFileUpload.setLastUpdNm(userContext.getUserName());
		otherFileUpload.setFileIndex(fileIndex);
		
		return otherFileUploadDao.updateForTores(otherFileUpload);
	}
	
	/**
	 * 問い合わせテーブルの更新日更新処理を行います。
	 * 
	 * @param model 依頼登録画面モデル
	 */
	private void updateToiawaseUpdDt(TB033RequestFullEntryModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		RcpTToiawase toiawase = new RcpTToiawase();
		
		toiawase.setToiawaseNo(model.getToiawaseNo());
		toiawase.setLastUpdId(userContext.getLoginId());
		toiawase.setUpdDt(model.getToiawaseUpdDt());
		
		// 問い合わせテーブルの更新日更新
		if (toiawaseDao.updateUpdDt(toiawase) == 0) {
			// 更新件数が０件の場合は、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
	}
	
	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model 登録オブジェクト
	 * @return 検索条件
	 */
	private String createKensakuJoken(Object entity) {
		NullExclusionToStringBuilder entryEntity = new NullExclusionToStringBuilder(
				entity,
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);


		return entryEntity.toString();
	}
	
	/**
	 * 作業依頼URLを生成します。
	 *
	 * @param model 依頼登録画面モデル
	 * @param systemUrl システムURL
	 * @return 作業依頼URL
	 */
	private String createSagyoIraiUrl(TB033RequestFullEntryModel model, String systemUrl) {
		return String.format(systemUrl
				+ "externalLogin.action?actionURL=/requestEntryInit&toiawaseNo=%s"
				+ "&toiawaseRirekiNo=%s&dispKbn=3",
				model.getToiawaseNo(), model.getToiawaseRirekiNo().toString());
	}
}
