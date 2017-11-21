package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.FileUploadLogic;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpTFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTOtherFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyoJokyoDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTFileUploadDao;
import jp.co.tokaigroup.reception.dao.TbTSagyoJokyoDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTOtherFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.TbTFileUpload;
import jp.co.tokaigroup.reception.entity.TbTSagyoJokyo;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.dto.TB032FileUploadDto;
import jp.co.tokaigroup.tlcss_b2b.dto.TB032SagyoJokyoDto;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB032RequestEntryModel;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 依頼内容詳細・作業状況登録サービス実装クラス。
 *
 * @author k002849
 * @version 4.0 2014/07/14
 * @version 4.1 2016/02/18 C.Kobayashi 依頼ファイルアップロード、依頼その他ファイルアップロード　ファイルインデックス対応
 * @version 4.2 2016/03/01 J.Matsuba 添付ファイル一覧最大件数の取得の修正
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB032RequestEntryServiceImpl extends TLCSSB2BBaseService
		implements TB032RequestEntryService {
	// プロパティファイルから取得
	/** 作業状況ファイルアップロードパス */
	private static final String UPLOAD_PATH_SAGYO_JOKYO = ResourceFactory.getResource().getString("UPLOAD_PATH_SAGYO_JOKYO");

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
	/** 外部サイト 業者回答作業状況テーブルDAO */
	@Autowired
	private TbTSagyoJokyoDao gyoshaSagyoJokyoDao;
	/**  外部サイト 業者回答依頼ファイルアップロードテーブルDAO */
	@Autowired
	private TbTFileUploadDao gyoshaFileUploadDao;
	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;
	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic commonInfoLogic;

	/** 委託会社関連チェックロジック */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	
	/**
	 * 初期表示（詳細表示）を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return 依頼内容詳細・作業状況登録画面モデル
	 */
	public TB032RequestEntryModel getDetailInitInfo(TB032RequestEntryModel model) {
		// 初期パラメータチェック
		validateInitParameter(model);

		// システムマスタから定数取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		model.setUserContext(userContext);
		// 添付可能最大件数
		model.setDisplayFileCount(userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_APPENEDED_MAX));
		// 画像ファイル添付可能最大件数
		model.setImageFileDisplayFileCount(userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_IRAI_FILE_UPDATE_TO_MAX));
		// その他ファイル添付可能最大件数
		model.setOtherFileDisplayFileCount(userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_IRAI_OTHER_FILE_UPDATE_TO_MAX));

		// ログインユーザーが社内以外、問い合わせ情報のチェック
		if (!userContext.isInhouse()
			&& !iraiDao.isOwn(model.getToiawaseNo(), model.getToiawaseRirekiNo(),userContext.getRefKokyakuId(), userContext.getGyoshaCd())) {
			// アクセス不可の場合は、403エラーを画面に表示
			return null;
		}
		
		// 依頼情報取得
		model = initIraiInfo(model);
		if (model.isNotPublish()) {
			// 依頼情報（問い合わせ情報、問い合わせ履歴情報）が公開済でない場合、ここで処理終了
			return model;
		}

		// 和名変換用Map取得
		Map<String, Map<String, RcpMComCd>> convertMap = getConvertMap();

		// 訪問予定日Map
		Map<String, RcpMComCd> homonYoteiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		// 完了フラグMap
		Map<String, RcpMComCd> complateFlgMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);

		RcpTIrai irai = model.getIrai();
		if (StringUtils.isNotBlank(irai.getHomonKiboJikanKbn()) &&
			homonYoteiMap.containsKey(irai.getHomonKiboJikanKbn())) {
			// 訪問予定日区分名
			irai.setHomonKiboJikanKbnNm(homonYoteiMap.get(irai.getHomonKiboJikanKbn()).getExternalSiteVal());
		}
		model.setIrai(irai);

		// 作業状況テーブル情報を取得
		RcpTSagyoJokyo sagyoJokyo = sagyoJokyoDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo());

		List<RcpTFileUpload> fileUploadList = null;
		List<RcpTOtherFileUpload> otherFileUploadList = null;
		if (sagyoJokyo != null) {
			// 作業状況テーブル情報が存在した場合は、依頼ファイルアップロード情報を取得
			fileUploadList = fileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);

			// 依頼その他ファイルアップロード情報を取得
			otherFileUploadList = otherFileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
		}

		TB032SagyoJokyoDto sagyoJokyoDto = null;
		List<TB032FileUploadDto> fileUploadDtoList = null;
		TB032FileUploadDto[] uploadedFiles = null;
		RcpTOtherFileUpload[] otherFileUploadedFiles = null;
		// 共通オブジェクトにコピー
		if (sagyoJokyo != null) {
			sagyoJokyoDto = new TB032SagyoJokyoDto(sagyoJokyo);
			fileUploadDtoList = new ArrayList<TB032FileUploadDto>();
			if (!(fileUploadList == null || fileUploadList.isEmpty())) {
				for (RcpTFileUpload fileUpload : fileUploadList) {
					fileUploadDtoList.add(new TB032FileUploadDto(fileUpload));
				}
			}

			if (StringUtils.isNotBlank(sagyoJokyoDto.getSagyoKanryoFlg()) &&
				complateFlgMap.containsKey(sagyoJokyoDto.getSagyoKanryoFlg())) {
				// 完了フラグ
				sagyoJokyoDto.setSagyoKanryoFlgNm(complateFlgMap.get(sagyoJokyoDto.getSagyoKanryoFlg()).getExternalSiteVal());
			}

			// 最大表示件数と同件数の要素を作成
			uploadedFiles =
				createFileUploadDtoListByTLCSS(fileUploadDtoList, model.getImageFileDisplayFileCount());
			otherFileUploadedFiles =
				createOtherFileUploadList(otherFileUploadList, model.getOtherFileDisplayFileCount());
		} else {
			// 最大表示件数と同件数の要素を作成
			uploadedFiles = new TB032FileUploadDto[model.getImageFileDisplayFileCount()];
			otherFileUploadedFiles = new RcpTOtherFileUpload[model.getOtherFileDisplayFileCount()];
		}

		model.setSagyoJokyo(sagyoJokyoDto);
		model.setUploadedFiles(uploadedFiles);
		model.setOtherUploadedFiles(otherFileUploadedFiles);

		return model;
	}

	/**
	 * 初期表示（登録表示）を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return 依頼内容詳細・作業状況登録画面モデル
	 */
	public TB032RequestEntryModel getEntryInitInfo(TB032RequestEntryModel model) {
		// 初期パラメータチェック
		validateInitParameter(model);

		// システムマスタから定数取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		model.setUserContext(userContext);
		// 添付可能最大件数
		model.setDisplayFileCount(userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_APPENEDED_MAX));

		// アクセス権限のチェック
		if (!iraiDao.isOwn(model.getToiawaseNo(), model.getToiawaseRirekiNo(),
				userContext.getRefKokyakuId(), userContext.getGyoshaCd())) {
			// アクセス不可の場合は、403エラーを画面に表示
			return null;
		}
		
		// 依頼情報取得
		model = initIraiInfo(model);

		if (model.isNotPublish()) {
			// 依頼情報（問い合わせ情報、問い合わせ履歴情報）が公開済でない場合、ここで処理終了
			return model;
		}

		// 和名変換用Map取得
		Map<String, Map<String, RcpMComCd>> convertMap = getConvertMap();

		// 訪問予定日Map
		Map<String, RcpMComCd> homonYoteiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);

		RcpTIrai irai = model.getIrai();
		if (StringUtils.isNotBlank(irai.getHomonKiboJikanKbn()) &&
			homonYoteiMap.containsKey(irai.getHomonKiboJikanKbn())) {
			// 訪問予定日区分名
			irai.setHomonKiboJikanKbnNm(homonYoteiMap.get(irai.getHomonKiboJikanKbn()).getExternalSiteVal());
		}
		model.setIrai(irai);

		TB032SagyoJokyoDto sagyoJokyoDto = null;
		List<TB032FileUploadDto> fileUploadDtoList = null;
		TB032FileUploadDto[] uploadedFiles = null;

		// 業者回答作業状況テーブル情報を取得
		TbTSagyoJokyo gyoshaSagyoJokyo = gyoshaSagyoJokyoDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo());

		List<TbTFileUpload> gyoshaFileUploadList = null;
		if (gyoshaSagyoJokyo != null) {
			// 業者回答作業状況テーブル情報が存在した場合は、業者回答依頼ファイルアップロード情報を取得
			gyoshaFileUploadList = gyoshaFileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
		}

		if (gyoshaSagyoJokyo != null) {
			// 共通オブジェクトにコピー
			sagyoJokyoDto = new TB032SagyoJokyoDto(gyoshaSagyoJokyo);
			fileUploadDtoList = new ArrayList<TB032FileUploadDto>();
			if (!(gyoshaFileUploadList == null || gyoshaFileUploadList.isEmpty())) {
				for (TbTFileUpload fileUpload : gyoshaFileUploadList) {
					fileUploadDtoList.add(new TB032FileUploadDto(fileUpload));
				}
			}

			// 作業状況登録の場合は、データを順番に表示
			uploadedFiles = createFileUploadDtoList(fileUploadDtoList, model.getDisplayFileCount());
		} else {
			// 最大表示件数と同件数の要素を作成
			uploadedFiles = new TB032FileUploadDto[model.getDisplayFileCount()];
		}

		model.setSagyoJokyo(sagyoJokyoDto);
		model.setUploadedFiles(uploadedFiles);

		return model;
	}

	/**
	 * 初期表示（登録表示・サーバエラー時）を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return 依頼内容詳細・作業状況登録画面モデル
	 */
	public TB032RequestEntryModel getEntryPrepareInitInfo(TB032RequestEntryModel model) {
		// システムマスタから定数取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		model.setUserContext(userContext);
		// 添付可能最大件数
		model.setDisplayFileCount(userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_APPENEDED_MAX));

		// 依頼情報取得
		model = initIraiInfo(model);

		if (model.isNotPublish()) {
			// 依頼情報（問い合わせ情報、問い合わせ履歴情報）が公開済でない場合、ここで処理終了
			return model;
		}

		// 和名変換用Map取得
		Map<String, Map<String, RcpMComCd>> convertMap = getConvertMap();

		// 訪問予定日Map
		Map<String, RcpMComCd> homonYoteiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);

		RcpTIrai irai = model.getIrai();
		if (StringUtils.isNotBlank(irai.getHomonKiboJikanKbn()) &&
			homonYoteiMap.containsKey(irai.getHomonKiboJikanKbn())) {
			// 訪問予定日区分名
			irai.setHomonKiboJikanKbnNm(homonYoteiMap.get(irai.getHomonKiboJikanKbn()).getExternalSiteVal());
		}
		model.setIrai(irai);

		TB032FileUploadDto[] uploadedFiles = null;

		if (model.isInsert()) {
			uploadedFiles = new TB032FileUploadDto[model.getDisplayFileCount()];
		} else {
			List<TbTFileUpload> gyoshaFileUploadList = null;
			// 業者回答作業状況テーブル情報が存在した場合は、業者回答依頼ファイルアップロード情報を取得
			gyoshaFileUploadList = gyoshaFileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			List<TB032FileUploadDto> fileUploadDtoList = new ArrayList<TB032FileUploadDto>();
			if (!(gyoshaFileUploadList == null || gyoshaFileUploadList.isEmpty())) {
				for (TbTFileUpload fileUpload : gyoshaFileUploadList) {
					fileUploadDtoList.add(new TB032FileUploadDto(fileUpload));
				}
			}

			// 作業状況登録の場合は、データを順番に表示
			uploadedFiles = createFileUploadDtoList(fileUploadDtoList, model.getDisplayFileCount());
		}

		model.setUploadedFiles(uploadedFiles);

		return model;
	}

	/**
	 * 画像削除処理を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 */
	@Transactional(value="txManager")
	public void deleteImageInfo(TB032RequestEntryModel model) {
		// 画像削除時パラメータチェック
		validateDeleteImageParameter(model);

		// 業者回答作業状況テーブルの更新日を更新
		TbTSagyoJokyo sagyoJokyo = new TbTSagyoJokyo();
		sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
		sagyoJokyo.setToiawaseRirekiNo(model.getToiawaseRirekiNo());

		gyoshaSagyoJokyoDao.updateUpdDt(sagyoJokyo);

		// 業者回答依頼ファイルアップロード情報を削除
		gyoshaFileUploadDao.deleteWithoutOptimisticLock(
				model.getToiawaseNo(),
				model.getToiawaseRirekiNo(),
				model.getFileIndex());

		// アクセスログ登録
		tbAccesslogDao.insert(TB032RequestEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_DELETE,
				createKensakuJokenAsDeleteImage(model));

		// アップロード実ファイル削除処理
		String fileNm = UPLOAD_PATH_SAGYO_JOKYO +
						System.getProperty("file.separator") +
						model.getUploadFileNm();

		// アップロード実ファイルの削除
		model.setSuccessDeleteImage(FileUploadLogic.deleteServerFile(fileNm));
	}

	/**
	 * 業者回答作業状況登録処理を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @throws ValidationException ファイルサイズ０の場合もしくは、ファイルサイズオーバーの場合
	 */
	@Transactional(value="txManager")
	public void insertGyoshaSagyoJokyoInfo(TB032RequestEntryModel model) {
		// 登録チェック
		validateInsert(model);

		// 業者回答作業状況テーブルの登録
		TbTSagyoJokyo sagyoJokyo = model.getSagyoJokyo().toTbTSagyoJokyo();
		sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
		sagyoJokyo.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		sagyoJokyo.setKakuninJokyo(TbTSagyoJokyo.KAKUNIN_JOKYO_MIKAKUNIN);

		gyoshaSagyoJokyoDao.insert(sagyoJokyo);

		if (model.isUploadExecute()) {
			// ファイルアップロード処理
			executeUploadFile(model);

			// アクセスログにファイル名を登録
			List<String> uploadFileList = new ArrayList<String>();
			for (String fileNm : model.getImageFileNmByArray()) {
				if (StringUtils.isBlank(fileNm)) {
					continue;
				}

				uploadFileList.add(fileNm);
			}
			sagyoJokyo.setUploadFileList(uploadFileList);
		}

		// アクセスログ登録
		tbAccesslogDao.insert(TB032RequestEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_INSERT,
				createKensakuJokenAsUpdate(sagyoJokyo));
	}

	/**
	 * 業者回答作業状況更新処理を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 */
	@Transactional(value="txManager")
	public void updateGyoshaSagyoJokyoInfo(TB032RequestEntryModel model) {
		// 登録チェック
		validateInsert(model);

		// 業者回答作業状況テーブルの更新
		TbTSagyoJokyo sagyoJokyo = model.getSagyoJokyo().toTbTSagyoJokyo();
		sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
		sagyoJokyo.setToiawaseRirekiNo(model.getToiawaseRirekiNo());

		gyoshaSagyoJokyoDao.updateWithoutOptimisticLock(sagyoJokyo);

		if (model.isUploadExecute()) {
			// ファイルアップロード処理
			executeUploadFile(model);

			// アクセスログにファイル名を登録
			List<String> uploadFileList = new ArrayList<String>();
			for (String fileNm : model.getImageFileNmByArray()) {
				if (StringUtils.isBlank(fileNm)) {
					continue;
				}

				uploadFileList.add(fileNm);
			}
			sagyoJokyo.setUploadFileList(uploadFileList);
		}

		// アクセスログ登録
		tbAccesslogDao.insert(TB032RequestEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_UPDATE,
				createKensakuJokenAsUpdate(sagyoJokyo));
	}

	/**
	 * 画像ダウンロードのチェックを行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return チェック結果 true:チェックOK、false:チェックNG
	 */
	public boolean checkDownloadInfo(TB032RequestEntryModel model) {
		if (model.getUserContext().isRealEstate() || model.getUserContext().isConstractor()) {
			// ログインユーザの権限が「20：管理会社」「30：業者」の場合は、依頼情報のチェックを行う
			return iraiDao.isOwn(model.getToiawaseNo(),
					model.getToiawaseRirekiNo(),
					model.getUserContext().getRefKokyakuId(),
					model.getUserContext().getGyoshaCd());
		} else if (model.getUserContext().isOutsourcerSv() || model.getUserContext().isOutsourcerOp()) {
			// ログインユーザの権限が「40：委託会社ＳＶ」「41：委託会社ＯＰ」の場合は、
			// 問い合わせ情報のチェックを行う
			RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
			if (toiawase == null) {
				// 問い合わせ情報がない場合は、チェックNG
				return false;
			}
			// 委託会社関連チェック
			return outsourcerValidationLogic.isValid(
					model.getUserContext().getKaishaId(), toiawase.getKokyakuId());
		} else {
			// 上記以外の権限の場合は、チェックなし
			return true;
		}
	}

	/**
	 * その他ファイルダウンロードのセキュリティチェックを行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return チェック結果 true:チェックOK、false:チェックNG
	 */
	public boolean checkOtherFileDownloadInfo(TB032RequestEntryModel model) {
		if (model.getUserContext().isRealEstate()) {
			// ログインユーザの権限が「20：管理会社」の場合は、依頼情報のチェックを行う
			// 依頼情報のチェックを行う
			return iraiDao.isOwn(model.getToiawaseNo(),
					model.getToiawaseRirekiNo(),
					model.getUserContext().getRefKokyakuId(),
					null);
		} else if (model.getUserContext().isOutsourcerSv() || model.getUserContext().isOutsourcerOp()) {
			// ログインユーザの権限が「40：委託会社ＳＶ」「41：委託会社ＯＰ」の場合は、
			// 問い合わせ情報のチェックを行う
			RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
			if (toiawase == null) {
				// 問い合わせ情報がない場合は、チェックNG
				return false;
			}
			// 委託会社関連チェック
			return outsourcerValidationLogic.isValid(
					model.getUserContext().getKaishaId(), toiawase.getKokyakuId());
		} else {
			// 上記以外の権限の場合は、チェックなし
			return true;
		}
	}

	/**
	 * 初期表示パラメータチェックを行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 */
	private void validateInitParameter(TB032RequestEntryModel model) {
		// 問い合わせNOのチェック
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			throw new ApplicationException("問い合わせNO不正：パラメータの問い合わせNO");
		}

		// 問い合わせ履歴NOのチェック
		if (model.getToiawaseRirekiNo() == null) {
			throw new ApplicationException("問い合わせ履歴NO不正：パラメータの問い合わせ履歴NO");
		}

		// 遷移元画面区分のチェック
		if (!(model.isFromRequestSearch() || model.isFromInquiryDetail() ||
			model.isFromDirectLogin())) {
			throw new ApplicationException("遷移元画面区分不正：パラメータの遷移元画面区分");
		}
	}

	/**
	 * 依頼情報の初期設定処理を行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return 依頼内容詳細・作業状況登録画面モデル
	 */
	private TB032RequestEntryModel initIraiInfo(TB032RequestEntryModel model) {
		// 問い合わせ情報取得
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null || !toiawase.isPublished()) {
			// 問い合わせ情報が存在しない、または、問い合わせ公開フラグが「1:公開済」以外の場合
			return model;
		}
		model.setToiawase(toiawase);

		// 顧客基本情報取得
		try {
			RcpMKokyaku kokyaku = commonInfoLogic.getKokyakuInfo(toiawase.getKokyakuId());
			model.setKokyakuEntity(kokyaku);
		} catch (ValidationException e) {
			return model;
		}

		// 問い合わせ履歴情報取得
		RcpTToiawaseRireki toiawaseRireki =
			toiawaseRirekiDao.selectByPrimaryKey(
					model.getToiawaseNo(),
					model.getToiawaseRirekiNo());
		if (toiawaseRireki == null || !toiawaseRireki.isPublished()) {
			// 問い合わせ履歴情報が存在しない、問い合わせ履歴公開フラグが「1:公開済」以外の場合
			return model;
		}
		model.setToiawaseRireki(toiawaseRireki);

		// 依頼情報取得
		RcpTIrai irai = iraiDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (irai == null || !irai.isPublished()) {
			// 依頼情報が存在しない、または、依頼情報公開フラグが「1:公開済」以外の場合
			return model;
		}
		model.setIrai(irai);

		return model;
	}

	/**
	 * 和名変換用Mapを取得します。
	 *
	 * @return 和名変換用Map
	 */
	private Map<String, Map<String, RcpMComCd>> getConvertMap() {
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI,
				RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		return convertMap;
	}

	/**
	 * 画像削除時パラメータチェックを行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 */
	private void validateDeleteImageParameter(TB032RequestEntryModel model) {
		// アップロードファイル名のチェック
		if (StringUtils.isBlank(model.getUploadFileNm())) {
			throw new ApplicationException("アップロードファイル名不正：パラメータのアップロードファイル名");
		}
		// ファイルインデックスのチェック
		if (model.getFileIndex() == null) {
			throw new ApplicationException("ファイルインデックス不正：パラメータのファイルインデックス");
		}
	}


	/**
	 * 画像削除時のアクセスログの検索条件を生成します。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 * @return アクセスログの検索条件
	 */
	private String createKensakuJokenAsDeleteImage(TB032RequestEntryModel model) {
		return "toiawaseNo=" + model.getToiawaseNo() + "," +
				"toiawaseRirekiNo=" + model.getToiawaseRirekiNo().toString() + "," +
				"fileIndex=" + model.getFileIndex().toString() + "," +
				"uploadFileNm=" + model.getUploadFileNm();
	}

	/**
	 * 更新時（登録・更新時）のアクセスログの検索条件を生成します。
	 *
	 * @param sagyoJokyo 業者回答作業状況テーブルEntity
	 * @return アクセスログの検索条件
	 */
	private String createKensakuJokenAsUpdate(TbTSagyoJokyo sagyoJokyo) {
		NullExclusionToStringBuilder entryEntity =
			new NullExclusionToStringBuilder(
				sagyoJokyo,
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// 除外する項目
		List<String> excludeFiledList = new ArrayList<String>(Arrays.asList(Constants.EXCLUDE_FIELD_NAMES));

		entryEntity.setExcludeFieldNames((String[]) excludeFiledList.toArray(new String[0]));

		return entryEntity.toString();
	}

	/**
	 * 登録時チェックを行います。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 */
	private void validateInsert(TB032RequestEntryModel model) {
		// システムマスタから定数取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		Long maxFileSize = Long.parseLong(userContext.getSystgemContstantAsString(
				RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_MAX_FILESIZE));

		if (model.isUploadExecute()) {
			for (File file : model.getImageFiles()) {
				if (file == null) {
					continue;
				}

				String result = FileUploadLogic.checkFileSize(file, maxFileSize);

				if (FileUploadLogic.SIZE_ZERO.equals(result)) {
					// ファイルサイズ０の場合
					throw new ValidationException(new ValidationPack().addError("MSG0020"));
				} else if (FileUploadLogic.SIZE_OVER.equals(result)) {
					// ファイルサイズオーバーの場合
					throw new ValidationException(new ValidationPack().addError("MSG0021", maxFileSize.toString()));
				}
			}
		}
	}

	/**
	 * ファイルのアップロード処理を実行します。
	 *
	 * @param model 依頼内容詳細・作業状況登録画面モデル
	 */
	private void executeUploadFile(TB032RequestEntryModel model) {
		List<String> uploadFileNameList = new ArrayList<String>();
		List<File> uploadFileList = new ArrayList<File>();

		String[] uploadFileNames = model.getImageFileNmByArray();
		// 指定されたファイル分、登録
		for (int i = 0; i < uploadFileNames.length; i++) {
			String imageFileName = uploadFileNames[i];

			if (StringUtils.isBlank(imageFileName)) {
				// ファイルが指定されていなければ、次の処理へ
				continue;
			}
			File uploadFile = model.getImageFiles()[i];
			// ファイルインデックス
			int fileIndex = i + 1;

			// ファイル名の生成
			String uploadFileName = model.getToiawaseNo() + "-" +
									model.getToiawaseRirekiNo().toString() + "-" +
									fileIndex;
			// アップロードファイル名の生成
			uploadFileName =
				FileUploadLogic.makeUploadFileNm(imageFileName, uploadFileName);

			// 業者回答依頼ファイルアップロードテーブルの登録
			TbTFileUpload fileUpload = new TbTFileUpload();
			fileUpload.setToiawaseNo(model.getToiawaseNo());
			fileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
			fileUpload.setFileIndex(new BigDecimal(fileIndex));
			fileUpload.setUploadFileNm(uploadFileName);
			fileUpload.setBaseFileNm(FilenameUtils.getName(new File(imageFileName).getName()));

			gyoshaFileUploadDao.insert(fileUpload);

			uploadFileNameList.add(uploadFileName);
			uploadFileList.add(uploadFile);
		}

		// ファイルのアップロード
		FileUploadLogic.uploadFileList(uploadFileList,
				uploadFileNameList, UPLOAD_PATH_SAGYO_JOKYO);
	}

	/**
	 * ファイルアップロード情報を表示順に作成します。
	 *
	 * @param uploadedList ファイルアップロード情報リスト
	 * @param dispMax 最大表示件数
	 * @return ファイルアップロード情報配列
	 */
	private TB032FileUploadDto[] createFileUploadDtoList(List<TB032FileUploadDto> uploadedList, Integer dispMax) {
		TB032FileUploadDto[] fileUploadDtos = new TB032FileUploadDto[dispMax];

		if (uploadedList == null || uploadedList.isEmpty()) {
			return fileUploadDtos;
		}

		for (int i = 0; i < dispMax; i++) {
			// 現在のファイルインデックス
			int currentIdx = i + 1;

			for (TB032FileUploadDto dto : uploadedList) {
				// ファイルデックスを取得
				TbTFileUpload fileUpload = dto.toTbTFileUpload();

				if (currentIdx == fileUpload.getFileIndex().intValue()) {
					// データがある場合は、配列に格納
					fileUploadDtos[i] = dto;

					break;
				}
			}
		}

		return fileUploadDtos;
	}

	/**
	 * ファイルアップロード情報を表示順に作成します。
	 *
	 * @param uploadedList ファイルアップロード情報リスト
	 * @param dispMax 最大表示件数
	 * @return ファイルアップロード情報配列
	 */
	private TB032FileUploadDto[] createFileUploadDtoListByTLCSS(List<TB032FileUploadDto> uploadedList, Integer dispMax) {
		TB032FileUploadDto[] fileUploadDtos = new TB032FileUploadDto[dispMax];

		if (uploadedList == null || uploadedList.isEmpty()) {
			return fileUploadDtos;
		}

		for (int i = 0; i < dispMax; i++) {
			// 現在のファイルインデックス
			int currentIdx = i + 1;
			
			boolean isExist = false;
			for (TB032FileUploadDto fileUploadDto : uploadedList) {
				if (currentIdx == fileUploadDto.getFileIndex().intValue()) {
					// データがある場合は、配列に格納
					fileUploadDtos[i] = fileUploadDto;
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				// ループカウントがアップロード済ファイル件数以上＝データ無し
				fileUploadDtos[i] = null;
			}
		}

		return fileUploadDtos;
	}

	/**
	 * 依頼その他ファイルアップロード情報を表示順に作成します。
	 *
	 * @param otherFileUploadList 依頼その他ファイルアップロード情報リスト
	 * @param dispMax 最大表示件数
	 * @return 依頼その他ファイルアップロード情報配列
	 */
	private RcpTOtherFileUpload[] createOtherFileUploadList(List<RcpTOtherFileUpload> otherFileUploadList, Integer dispMax) {
		RcpTOtherFileUpload[] otherFileUploadeds = new RcpTOtherFileUpload[dispMax];

		if (otherFileUploadList == null || otherFileUploadList.isEmpty()) {
			return otherFileUploadeds;
		}

		for (int i = 0; i < dispMax; i++) {
			// 現在のファイルインデックス
			int currentIdx = i + 1;
			
			boolean isExist = false;
			for (RcpTOtherFileUpload otherFileUpload : otherFileUploadList) {
				if (currentIdx == otherFileUpload.getFileIndex().intValue()) {
					// データがある場合は、配列に格納
					otherFileUploadeds[i] = otherFileUpload;
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				// ループカウントがアップロード済ファイル件数以上＝データ無し
				otherFileUploadeds[i] = null;
			}	
		}

		return otherFileUploadeds;
	}
}
