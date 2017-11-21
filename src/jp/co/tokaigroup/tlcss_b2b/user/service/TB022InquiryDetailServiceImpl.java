package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn1Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn2Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn3Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn4Dao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseFileDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseFile;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB022InquiryDetailModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 問い合わせ検索サービス実装クラス。
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 1.1 2015/10/23 C.Kobayashi 既読未読機能追加対応
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB022InquiryDetailServiceImpl extends TLCSSB2BBaseService
		implements TB022InquiryDetailService {

	/** ソート順(問い合わせ履歴取得) */
	private final static String orderBy = " ORDER BY"
		+ "    UKETSUKE_YMD DESC,"
		+ "    UKETSUKE_JIKAN DESC,"
		+ "    TOIAWASE_RIREKI_NO DESC";

	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** リセプション問い合わせテーブルDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** リセプション問い合わせ履歴テーブルDAO */
	@Autowired
	private RcpTToiawaseRirekiDao toiawaseRirekiDao;

	/** リセプション状況区分マスタDAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;

	/** リセプション依頼テーブルDAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** NATOSパスワードＭDAO */
	@Autowired
	private NatosMPasswordDao natosPswdDao;

	/** 問い合わせ区分1マスタDAO */
	@Autowired
	private RcpMToiawaseKbn1Dao toiawaseKbn1;

	/** 問い合わせ区分2マスタDAO */
	@Autowired
	private RcpMToiawaseKbn2Dao toiawaseKbn2;

	/** 問い合わせ区分3マスタDAO */
	@Autowired
	private RcpMToiawaseKbn3Dao toiawaseKbn3;

	/** 問い合わせ区分4マスタDAO */
	@Autowired
	private RcpMToiawaseKbn4Dao toiawaseKbn4;

	/** 問い合わせファイルテーブルDAO */
	@Autowired
	private RcpTToiawaseFileDao toiawaseFileDao;

	/**
	 * 初期表示を行います。
	 *
	 * @param model 問い合わせ詳細画面モデル
	 * @return 問い合わせ詳細画面モデル
	 */
	public TB022InquiryDetailModel getInitInfo(TB022InquiryDetailModel model) {

		//問い合わせNoのチェック
		if(StringUtils.isBlank(model.getToiawaseNo())){
			// 問い合わせNoが取得できない場合エラー
			throw new ApplicationException("問い合わせNO不正:パラメータの問い合わせNO" );
		}

		// アクセス権限のチェック
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// 問い合わせ情報のチェック
		if (!toiawaseDao.isOwn(model.getToiawaseNo(), userContext.getRefKokyakuId())) {
			// アクセス不可の場合は、403エラーを画面に表示
			return null;
		}
		
		// 問い合わせ情報取得
		RcpTToiawase toiawaseEntity = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if(toiawaseEntity == null){
			// 問い合わせ情報が取得できない場合エラー
			model.setToiawaseEntity(null);
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		//最終履歴情報取得
		RcpTToiawaseRireki toiawaseRirekiEntity = toiawaseRirekiDao.getLastToiawaseRireki(model.getToiawaseNo(), RC031ToiawaseSearchCondition.SEARCH_FLG_GAIBU);

		//顧客基本情報取得
		model.setKokyakuEntity(kokyakuKihon.getKokyakuInfo(toiawaseEntity.getKokyakuId()));

		//問い合わせ履歴情報取得
		List<RcpTToiawaseRireki> toiawaseRirekiList = toiawaseRirekiDao.selectKokaiTarget(model.getToiawaseNo(), orderBy);

		//依頼情報取得
		model.setIraiMap(iraiDao.selectAnyAsMap(model.getToiawaseNo()));

		// 問い合わせファイルリスト
		List<RcpTToiawaseFile> toiawaseFileList =
			toiawaseFileDao.selectBy(model.getToiawaseNo(), null);
		model.setUploadedFiles(createToiawaseFileList(toiawaseFileList));

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU,
				RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI,
				RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 依頼者区分Map
		Map<String, RcpMComCd> iraishaKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// 依頼者フラグMap
		Map<String, RcpMComCd> iraishaFlgMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA);
		// 依頼者有無区分Map
		Map<String, RcpMComCd> iraiUmuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU);
		// 受付形態Map
		Map<String, RcpMComCd> uketsukeKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		// 閲覧状況区分Map
		Map<String, RcpMComCd> browseStatusMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU);
		// 状況区分マスタからの取得
		Map<String, String> jokyoKbnMap = jokyoKbnDao.selectAllExtJokyoKbnNmAsMap();

		// 問い合わせ区分Map
		Map<String, String> toiawaseKbn1Map = toiawaseKbn1.selectAllAsMap();
		Map<String, String> toiawaseKbn2Map = toiawaseKbn2.selectAllAsMap();
		Map<String, String> toiawaseKbn3Map = toiawaseKbn3.selectAllAsMap();
		Map<String, String> toiawaseKbn4Map = toiawaseKbn4.selectAllAsMap();

		// パスワードマスタ・業者マスタ検索用のリスト生成
		List<String> userIdList = new ArrayList<String>();
		if (StringUtils.isNotBlank(toiawaseEntity.getUketsukeshaId()) &&
			!userIdList.contains(toiawaseEntity.getUketsukeshaId())) {
			// パスワードマスタ検索用のリストに含まれていなければ、リストに追加
			userIdList.add(toiawaseEntity.getUketsukeshaId());
		}
		for (RcpTToiawaseRireki dto : toiawaseRirekiList) {
			if (StringUtils.isNotBlank(dto.getTantoshaId()) &&
				!userIdList.contains(dto.getTantoshaId())) {
				// パスワードマスタ検索用のリストに含まれていなければ、リストに追加
				userIdList.add(dto.getTantoshaId());
			}
		}
		Map<String, String> userMap = new HashMap<String, String>();
		if (!(userIdList == null || userIdList.isEmpty())) {
			// パスワードマスタ取得
			userMap =
				natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));
		}

		// 和名変換処理
		// 依頼者フラグが顧客基本情報の依頼者フラグと同一の場合
		if (iraishaFlgMap != null) {
			if (RcpTToiawase.IRAI_FLG_ONAJI.equals(toiawaseEntity.getIraishaFlg()) &&
					iraishaFlgMap.containsKey(toiawaseEntity.getIraishaFlg())) {
				//依頼者名
				toiawaseEntity.setIraishaNm(iraishaFlgMap.get(toiawaseEntity.getIraishaFlg()).getExternalSiteVal());
			} else if (RcpTToiawase.IRAI_FLG_DIFF.equals(toiawaseEntity.getIraishaFlg()) &&
					iraishaKbnMap.containsKey(toiawaseEntity.getIraishaKbn())) {
				toiawaseEntity.setIraishaNm(iraishaKbnMap.get(toiawaseEntity.getIraishaKbn()).getExternalSiteVal() + " " + toiawaseEntity.getIraishaNm());
			}
		}
		// 受付者
		if (StringUtils.isBlank(toiawaseEntity.getUketsukeshaNm())) {
			// 受付者名がNULLの場合、受付者ＩＤを和名変換して表示
			if (userMap != null && StringUtils.isNotBlank(toiawaseEntity.getUketsukeshaId()) &&
					userMap.containsKey(toiawaseEntity.getUketsukeshaId())) {
				toiawaseEntity.setUketsukeNm(userMap.get(toiawaseEntity.getUketsukeshaId()));
			}
		} else {
			// 受付者名がNULLでなければ、受付者名を表示
			toiawaseEntity.setUketsukeNm(toiawaseEntity.getUketsukeshaNm());
		}
		if (toiawaseKbn1Map != null && StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn1()) &&
				toiawaseKbn1Map.containsKey(toiawaseEntity.getToiawaseKbn1())) {
			// 問い合わせ区分1名
			toiawaseEntity.setToiawaseKbn1Nm(toiawaseKbn1Map.get(toiawaseEntity.getToiawaseKbn1()));
		}
		if (toiawaseKbn2Map != null && StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn2()) &&
				toiawaseKbn2Map.containsKey(toiawaseEntity.getToiawaseKbn2())) {
			// 問い合わせ区分2
			toiawaseEntity.setToiawaseKbn2Nm(toiawaseKbn2Map.get(toiawaseEntity.getToiawaseKbn2()));
		}
		if (toiawaseKbn3Map != null && StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn3()) &&
				toiawaseKbn3Map.containsKey(toiawaseEntity.getToiawaseKbn3())) {
			// 問い合わせ区分3名
			toiawaseEntity.setToiawaseKbn3Nm(toiawaseKbn3Map.get(toiawaseEntity.getToiawaseKbn3()));
		}
		if (toiawaseKbn4Map != null && StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn4()) &&
				toiawaseKbn4Map.containsKey(toiawaseEntity.getToiawaseKbn4())) {
			// 問い合わせ区分4名
			toiawaseEntity.setToiawaseKbn4Nm(toiawaseKbn4Map.get(toiawaseEntity.getToiawaseKbn4()));
		}
		if (uketsukeKeitaiMap != null && StringUtils.isNotBlank(toiawaseEntity.getUketsukeKeitaiKbn()) &&
				uketsukeKeitaiMap.containsKey(toiawaseEntity.getUketsukeKeitaiKbn())) {
			// 受付形態名
			toiawaseEntity.setUketsukeKeitaiKbnNm(uketsukeKeitaiMap.get(toiawaseEntity.getUketsukeKeitaiKbn()).getExternalSiteVal());
		}
		if (iraiUmuMap != null && StringUtils.isNotBlank(toiawaseEntity.getIraiUmuKbn()) &&
				iraiUmuMap.containsKey(toiawaseEntity.getIraiUmuKbn())) {
			// 依頼有無区分名
			toiawaseEntity.setIraiUmuKbnNm(iraiUmuMap.get(toiawaseEntity.getIraiUmuKbn()).getExternalSiteVal());
		}
		if (jokyoKbnMap != null && toiawaseRirekiEntity != null && StringUtils.isNotBlank(toiawaseRirekiEntity.getJokyoKbn()) &&
				jokyoKbnMap.containsKey(toiawaseRirekiEntity.getJokyoKbn())) {
			// 最終履歴状況区分名
			toiawaseRirekiEntity.setJokyoKbnNm(jokyoKbnMap.get(toiawaseRirekiEntity.getJokyoKbn()));
		}

		//時間表記の修正(hhmm→hh:mm)
		toiawaseEntity.setUketsukeJikan(DateUtil.hhmmPlusColon(toiawaseEntity.getUketsukeJikan()));
		if (toiawaseRirekiEntity != null) {
			toiawaseRirekiEntity.setUketsukeJikan(DateUtil.hhmmPlusColon(toiawaseRirekiEntity.getUketsukeJikan()));
		}

		for (int i = 0; i < toiawaseRirekiList.size(); i++) {
			RcpTToiawaseRireki dto = toiawaseRirekiList.get(i);

			// 担当者名がNULLの場合、担当者ＩＤを和名変換して表示(NULLでなければ、そのまま表示)
			if (StringUtils.isBlank(dto.getTantoshaNm())) {
				if (StringUtils.isNotBlank(dto.getTantoshaId())
						&& userMap != null
						&& userMap.containsKey(dto.getTantoshaId())) {
					// 担当者区分名
					dto.setTantoshaNm(userMap.get(dto.getTantoshaId()));
				}
			}

			if (browseStatusMap != null && StringUtils.isNotBlank(dto.getBrowseStatusFlg()) &&
					browseStatusMap.containsKey(dto.getBrowseStatusFlg())) {
				// 閲覧状況フラグ名
				dto.setBrowseStatusFlgNm(browseStatusMap.get(dto.getBrowseStatusFlg()).getExternalSiteVal());
			}
			
			if (StringUtils.isNotBlank(dto.getJokyoKbn()) &&
				jokyoKbnMap.containsKey(dto.getJokyoKbn())) {
				// 状況区分名
				dto.setJokyoKbnNm(jokyoKbnMap.get(dto.getJokyoKbn()));
			}

			//時間表記の修正(hhmm→hh:mm)
			dto.setUketsukeJikan(DateUtil.hhmmPlusColon(dto.getUketsukeJikan()));

			//依頼テーブルの有無の確認（未公開の場合は、依頼は表示しない）
			RcpTIrai irai = model.getIraiMap().get(dto.getToiawaseNo() + dto.getToiawaseRirekiNo().toString());
			if (irai != null && irai.isPublished()) {
				// 依頼情報が存在し、公開済の場合のみ依頼リンクを表示
				dto.setIraiExsits(true);
			} else {
				// 依頼情報が存在しない、または、未公開の場合は依頼リンクを表示しない
				dto.setIraiExsits(false);
			}

			// 再セット
			toiawaseRirekiList.set(i, dto);
		}

		model.setToiawaseEntity(toiawaseEntity);
		model.setToiawaseRirekiEntity(toiawaseRirekiEntity);
		model.setToiawaseRirekiList(toiawaseRirekiList);

		return model;
	}

	/**
	 * 問い合わせ履歴・問い合わせ情報を更新します。
	 *
	 * @param model 問い合わせ詳細画面モデル
	 */
	@Transactional(value="txManager")
	public void updateInquiryHistoryDetailInfo(TB022InquiryDetailModel model) {

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		for (int i = 0; i < model.getToiawaseRirekiNoList().size(); i++) {
			RcpTToiawaseRireki toiawaseRireki = new RcpTToiawaseRireki();
			
			// 問い合わせＮＯ
			toiawaseRireki.setToiawaseNo(model.getToiawaseNo());
			// 問い合わせ履歴ＮＯ
			toiawaseRireki.setToiawaseRirekiNo(model.getToiawaseRirekiNoList().get(i));
			// 閲覧状況フラグ
			toiawaseRireki.setBrowseStatusFlg(model.getBrowseStatusFlgList().get(i));
			// 最終閲覧ＩＤ
			toiawaseRireki.setLastBrowseId(userContext.getLoginId());
			// 問い合わせ履歴未読既読情報更新
			toiawaseRirekiDao.updateBrowseStatusFlg(toiawaseRireki);
		}

		// 問い合わせ未既読情報更新
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), true);
	}

	/**
	 * 問い合わせファイル情報のダウンロードチェックを行います。
	 *
	 * @param model 問い合わせ検索画面モデル
	 * @return チェック結果 true:チェックOK、false:チェックNG
	 */
	public boolean canWorkReportDownload(TB022InquiryDetailModel model) {
		return toiawaseDao.isOwn(model.getToiawaseNo(),
				((TLCSSB2BUserContext) getUserContext()).getRefKokyakuId());
	}

	/**
	 * 問い合わせファイル情報を表示順に作成します。
	 *
	 * @param uploadedList ファイルアップロード情報リスト
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



}
