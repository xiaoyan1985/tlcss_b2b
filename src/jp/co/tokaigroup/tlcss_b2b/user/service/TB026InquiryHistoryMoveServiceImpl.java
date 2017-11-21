package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpTFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuWithNoIdDao;
import jp.co.tokaigroup.reception.dao.RcpTOtherFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyoJokyoDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyohiDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyohiMeisaiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTOtherFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.RcpTSagyohi;
import jp.co.tokaigroup.reception.entity.RcpTSagyohiMeisai;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB026InquiryHistoryMoveModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 問い合わせ履歴移動画面サービス実装クラス。
 *
 * @author J.Matsuba
 * @version 1.0 2015/08/18
 * @version 1.1 2016/07/22 J.Matsuba 更新チェック処理isValid()の追加
 * @version 1.2 2016/08/04 H.Yamamura 入力チェックを問い合わせのサービス種別で行うように変更
 *
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB026InquiryHistoryMoveServiceImpl extends TLCSSB2BBaseService
		implements TB026InquiryHistoryMoveService {
	/** リセプション問い合わせテーブルDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** リセプション問い合わせ履歴テーブルDAO */
	@Autowired
	private RcpTToiawaseRirekiDao toiawaseRirekiDao;

	/** リセプションＩＤ無し顧客テーブルDAO */
	@Autowired
	private RcpTKokyakuWithNoIdDao kokyakuWithNoIdDao;

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** リセプション依頼テーブルDAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/** リセプション作業状況テーブルDAO */
	@Autowired
	private RcpTSagyoJokyoDao sagyoJokyoDao;

	/** リセプション作業費明細テーブルDAO */
	@Autowired
	private RcpTSagyohiMeisaiDao sagyohiMeisaiDao;

	/** リセプション依頼ファイルアップロードテーブルDAO */
	@Autowired
	private RcpTFileUploadDao fileUploadDao;

	/** リセプション依頼ファイルアップロードテーブルDAO */
	@Autowired
	private RcpTOtherFileUploadDao otherFileUploadDao;

	/** リセプション作業費テーブルDAO */
	@Autowired
	private RcpTSagyohiDao sagyohiDao;

	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao accesslogDao;
	
	/** 委託会社関連チェックロジック */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;

	/**
	 * 初期表示処理を行います。
	 *
	 * @param model 問い合わせ履歴移動画面モデル
	 * @return 問い合わせ履歴移動画面モデル
	 */
	public TB026InquiryHistoryMoveModel getInitInfo(TB026InquiryHistoryMoveModel model) {
		// 変更前情報取得
		// 問い合わせ情報取得
		RcpTToiawase oldToiawase =
			toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (oldToiawase == null) {
			// 変更前の問い合わせ情報がない場合
			model.setInitError(true);
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		model.setOldToiawaseInfo(oldToiawase);

		if (model.isOldKokyakuWithoutId()) {
			// ID無し顧客情報取得の場合
			RcpTKokyakuWithNoId oldKokyakuWithNoId =
				kokyakuWithNoIdDao.selectByPrimaryKey(model.getToiawaseNo());

			model.setOldKokyakuInfoWithoutId(oldKokyakuWithNoId);
		} else {
			// 顧客マスタ情報取得の場合
			RcpMKokyaku oldKokyaku =
				kokyakuDao.selectByPrimaryKey(oldToiawase.getKokyakuId());

			model.setOldKokyakuInfo(oldKokyaku);
		}

		// 変更後情報取得
		// 問い合わせ情報取得
		String newToiawaseNo = StringUtils.leftPad(model.getNewToiawaseNo(), 10, '0');

		RcpTToiawase newToiawase =
			toiawaseDao.selectByPrimaryKey(newToiawaseNo);
		if (newToiawase == null) {
			// 変更後の問い合わせ情報がない場合
			model.setInitError(true);
			throw new ValidationException(new ValidationPack().addError("MSG0002"));

		}

		// 移動先の問い合わせが締め済の場合、登録不可
		if (StringUtils.isNotBlank(newToiawase.getShimeYm())) {
			model.setInitError(true);
			throw new ValidationException(new ValidationPack().addError("MSG0035"));
		}

		// セッション情報を取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// セッションの権限が「40:委託会社SV」または「41:委託会社OP」の場合
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// 委託関連会社チェックでNGの場合
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), newToiawase.getKokyakuId())){
				// 委託関連会社チェックエラー
				model.setInitError(true);
				throw new ValidationException(new ValidationPack().addError("MSG0031", "参照不可顧客の", "問い合わせ情報"));
			}
		}
		
		model.setNewToiawaseInfo(newToiawase);

		if (model.isNewKokyakuWithoutId()) {
			// ID無し顧客情報取得の場合
			RcpTKokyakuWithNoId newKokyakuWithNoId =
				kokyakuWithNoIdDao.selectByPrimaryKey(newToiawaseNo);

			model.setNewKokyakuInfoWithoutId(newKokyakuWithNoId);
		} else {
			// 顧客マスタ情報取得の場合
			RcpMKokyaku newKokyaku =
				kokyakuDao.selectByPrimaryKey(newToiawase.getKokyakuId());

			model.setNewKokyakuInfo(newKokyaku);
		}

		return model;
	}

	/**
	 * 問い合わせ履歴移動処理を行います。
	 *
	 * @param model問い合わせ履歴移動画面モデル
	 */
	@Transactional(value="txManager")
	public void updateInquiryHistoryMoveInfo(TB026InquiryHistoryMoveModel model) {
		// 更新チェック処理
		isValid(model);

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// 移動元の問い合わせテーブル更新
		RcpTToiawase oldToiawaseInfo = model.getOldToiawaseInfo();
		oldToiawaseInfo.setLastUpdId(userContext.getLoginId());
		oldToiawaseInfo.setLastUpdNm(userContext.getUserName());

		if (toiawaseDao.updateUpdDt(oldToiawaseInfo) == 0) {
			// 更新件数が０件の場合は、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003", ""));
		}

		// 移動先の問い合わせテーブル更新
		RcpTToiawase newToiawaseInfo = model.getNewToiawaseInfo();
		newToiawaseInfo.setLastUpdId(userContext.getLoginId());
		newToiawaseInfo.setLastUpdNm(userContext.getUserName());

		if (toiawaseDao.updateUpdDt(model.getNewToiawaseInfo()) == 0) {
			// 更新件数が０件の場合は、排他エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003", ""));
		}

		//---------- 各テーブル移動処理 ここから ----------//
		// 問い合わせ履歴情報作成
		// 移動先の最大履歴NO取得
		String newToiawaseNo = StringUtils.leftPad(model.getNewToiawaseNo(), 10, '0');
		BigDecimal newMaxRirekiNo = toiawaseRirekiDao.selectMaxRirekiNo(newToiawaseNo);

		// 問い合わせ履歴テーブル作成
		moveToToiawaseRireki(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo, model);

		// 依頼テーブル作成
		moveToIrai(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);

		// 作業状況テーブル作成
		moveToSagyoJokyo(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);

		// 依頼ファイルアップロードテーブル作成
		moveToFileUpload(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);

		// 依頼その他ファイルアップロードテーブル作成
		moveToOtherFileUpload(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);

		// 作業費テーブル作成
		moveToSagyohi(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);

		// 作業費明細テーブル作成
		moveToSagyohiMeisai(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);
		//---------- 各テーブル移動処理 ここまで ----------//

		//---------- 各テーブル削除処理 ここから ----------//
		// 作業費明細テーブル削除
		sagyohiMeisaiDao.deleteBy(model.getToiawaseNo(), null, null);

		// 作業費テーブル削除
		sagyohiDao.deleteBy(model.getToiawaseNo(), null);

		// 依頼その他ファイルアップロードテーブル削除
		otherFileUploadDao.deleteBy(model.getToiawaseNo(), null, null);

		// 依頼ファイルアップロードテーブル削除
		fileUploadDao.deleteBy(model.getToiawaseNo(), null, null);

		// 作業状況テーブル削除
		sagyoJokyoDao.deleteBy(model.getToiawaseNo(), null);

		// 依頼テーブル削除
		iraiDao.deleteBy(model.getToiawaseNo(), null);

		// 問い合わせ履歴テーブル削除
		toiawaseRirekiDao.deleteBy(model.getToiawaseNo(), null);
		//---------- 各テーブル削除処理 ここまで ----------//

		// 移動元がＩＤ無し顧客の場合は、ＩＤ無し顧客テーブルの退避・削除
		if (model.isOldKokyakuWithoutId()) {
			// 退避処理
			kokyakuWithNoIdDao.insertTaihiWithoutOptimisticLock(model.getOldKokyakuInfoWithoutId());
			// 削除処理
			kokyakuWithNoIdDao.deleteWithoutOptimisticLock(model.getToiawaseNo());
		}

		// 問い合わせテーブルの退避・削除
		// 退避条件情報の作成
		RcpTToiawase toiawase = new RcpTToiawase();
		toiawase.setToiawaseNo(model.getToiawaseNo());

		// 退避処理
		toiawaseDao.insertTaihiWithoutOptimisticLock(toiawase);
		// 削除処理
		toiawaseDao.deleteWithoutOptimisticLock(model.getToiawaseNo());
		
		// 問い合わせ未既読情報更新
		toiawaseDao.updateBrowseStatusFlg(newToiawaseNo, false);

		// アクセスログ登録
		accesslogDao.insert(TB026InquiryHistoryMoveModel.GAMEN_NM,
				TB026InquiryHistoryMoveModel.BUTTON_NM_MOVE, createSearchCondition(model));
	}

	/**
	 * 更新チェック処理を行います。
	 *
	 * @param model 問い合わせ履歴移動画面モデル
	 * @throws ValidationException 移動元または移動先スケジュール情報が1件でない場合
	 */
	private void isValid(TB026InquiryHistoryMoveModel model) {
		// 移動前の問い合わせ情報を取得
		RcpTToiawase toiawaseInfo = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		// 問い合わせ情報が存在しない　または　サービス種別がビル管理の場合
		if (toiawaseInfo == null || toiawaseInfo.isBuildingManagement()) {
			// エラーメッセージを表示
			throw new ValidationException(new ValidationPack().addError("MSG0046", "移動元問い合わせNO", "履歴移動を実施できません"));
		}
		
		// 移動後の問い合わせ情報を取得
		RcpTToiawase newToiawaseInfo = toiawaseDao.selectByPrimaryKey(model.getNewToiawaseInfo().getToiawaseNo());
		// 問い合わせ情報が存在しない　または　サービス種別がビル管理の場合
		if (newToiawaseInfo == null || newToiawaseInfo.isBuildingManagement()) {
			// エラーメッセージを表示
			throw new ValidationException(new ValidationPack().addError("MSG0046", "移動先問い合わせNO", "履歴移動を実施できません"));
		}
	}

	/**
	 * 問い合わせ履歴情報の移動を行います。
	 *
	 * @param oldToiawaseNo 移動元問い合わせNO
	 * @param newToiawaseNo 移動先問い合わせNO
	 * @param newMaxRirekiNo 移動先の問い合わせ履歴NO最大値
	 */
	private void moveToToiawaseRireki(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo, TB026InquiryHistoryMoveModel model) {
		// 移動元の問い合わせ履歴情報取得
		List<RcpTToiawaseRireki> moveToiawaseRirekiList =
			toiawaseRirekiDao.selectBy(oldToiawaseNo, null);
		// アクセスログ用に記載する問い合わせ履歴NO
		List<BigDecimal> entryRirekiNoList = new ArrayList<BigDecimal>();
		for (RcpTToiawaseRireki toiawaseRireki : moveToiawaseRirekiList) {
			// 問い合わせNOの再セット
			toiawaseRireki.setToiawaseNo(newToiawaseNo);

			// 問い合わせ履歴NOの生成
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					toiawaseRireki.getToiawaseRirekiNo(), newMaxRirekiNo);

			// 問い合わせ履歴NOの再セット
			toiawaseRireki.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// 移動した問い合わせ履歴NOの保存
			entryRirekiNoList.add(entryToiawaseRirekiNo);

			// 問い合わせ履歴情報の登録
			toiawaseRirekiDao.insertAsEntity(toiawaseRireki);
		}

		model.setEntryRirekiNoList(entryRirekiNoList);
	}

	/**
	 * 依頼情報の移動を行います。
	 *
	 * @param oldToiawaseNo 移動元問い合わせNO
	 * @param newToiawaseNo 移動先問い合わせNO
	 * @param newMaxRirekiNo 移動先の問い合わせ履歴NO最大値
	 */
	private void moveToIrai(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTIrai> moveIraiList = iraiDao.selectBy(oldToiawaseNo, null);
		for (RcpTIrai irai : moveIraiList) {
			// 問い合わせNOの再セット
			irai.setToiawaseNo(newToiawaseNo);

			// 問い合わせ履歴NOの生成
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					irai.getToiawaseRirekiNo(), newMaxRirekiNo);

			// 問い合わせ履歴NOの再セット
			irai.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// 依頼情報の登録
			iraiDao.insertAsEntity(irai);
		}
	}

	/**
	 * 作業状況情報の移動を行います。
	 *
	 * @param oldToiawaseNo 移動元問い合わせNO
	 * @param newToiawaseNo 移動先問い合わせNO
	 * @param newMaxRirekiNo 移動先の問い合わせ履歴NO最大値
	 */
	private void moveToSagyoJokyo(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTSagyoJokyo> moveSagyoJokyoList =
			sagyoJokyoDao.selectBy(oldToiawaseNo, null);
		for (RcpTSagyoJokyo sagyoJokyo : moveSagyoJokyoList) {
			// 問い合わせNOの再セット
			sagyoJokyo.setToiawaseNo(newToiawaseNo);

			// 問い合わせ履歴NOの生成
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					sagyoJokyo.getToiawaseRirekiNo(), newMaxRirekiNo);

			// 問い合わせ履歴NOの再セット
			sagyoJokyo.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// 作業状況情報の登録
			sagyoJokyoDao.insertAsEntity(sagyoJokyo);
		}
	}

	/**
	 * 依頼ファイルアップロード情報の移動を行います。
	 *
	 * @param oldToiawaseNo 移動元問い合わせNO
	 * @param newToiawaseNo 移動先問い合わせNO
	 * @param newMaxRirekiNo 移動先の問い合わせ履歴NO最大値
	 */
	private void moveToFileUpload(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTFileUpload> moveFileUploadList =
			fileUploadDao.selectBy(oldToiawaseNo, null, null);
		for (RcpTFileUpload fileUpload : moveFileUploadList) {
			// 問い合わせNOの再セット
			fileUpload.setToiawaseNo(newToiawaseNo);

			// 問い合わせ履歴NOの生成
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					fileUpload.getToiawaseRirekiNo(), newMaxRirekiNo);

			// 問い合わせ履歴NOの再セット
			fileUpload.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// 依頼ファイルアップロード情報の登録
			fileUploadDao.insertAsEntity(fileUpload);
		}
	}

	/**
	 * 依頼その他ファイルアップロード情報の移動を行います。
	 *
	 * @param oldToiawaseNo 移動元問い合わせNO
	 * @param newToiawaseNo 移動先問い合わせNO
	 * @param newMaxRirekiNo 移動先の問い合わせ履歴NO最大値
	 */
	private void moveToOtherFileUpload(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTOtherFileUpload> moveOtherFileUploadList =
			otherFileUploadDao.selectBy(oldToiawaseNo, null, null);
		for (RcpTOtherFileUpload otherFileUpload : moveOtherFileUploadList) {
			// 問い合わせNOの再セット
			otherFileUpload.setToiawaseNo(newToiawaseNo);

			// 問い合わせ履歴NOの生成
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					otherFileUpload.getToiawaseRirekiNo(), newMaxRirekiNo);

			// 問い合わせ履歴NOの再セット
			otherFileUpload.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// 依頼ファイルアップロード情報の登録
			otherFileUploadDao.insertAsEntity(otherFileUpload);
		}
	}

	/**
	 * 作業費情報の移動を行います。
	 *
	 * @param oldToiawaseNo 移動元問い合わせNO
	 * @param newToiawaseNo 移動先問い合わせNO
	 * @param newMaxRirekiNo 移動先の問い合わせ履歴NO最大値
	 */
	private void moveToSagyohi(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTSagyohi> moveSagyohiList =
			sagyohiDao.selectBy(oldToiawaseNo, null);
		for (RcpTSagyohi sagyohi : moveSagyohiList) {
			// 問い合わせNOの再セット
			sagyohi.setToiawaseNo(newToiawaseNo);

			// 問い合わせ履歴NOの生成
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					sagyohi.getToiawaseRirekiNo(), newMaxRirekiNo);

			// 問い合わせ履歴NOの再セット
			sagyohi.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// 作業費情報の登録
			sagyohiDao.insertAsEntity(sagyohi);
		}
	}

	/**
	 * 作業費明細情報の移動を行います。
	 *
	 * @param oldToiawaseNo 移動元問い合わせNO
	 * @param newToiawaseNo 移動先問い合わせNO
	 * @param newMaxRirekiNo 移動先の問い合わせ履歴NO最大値
	 */
	private void moveToSagyohiMeisai(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTSagyohiMeisai> moveSagyohiMeisaiList =
			sagyohiMeisaiDao.selectBy(oldToiawaseNo, null, null);
		for (RcpTSagyohiMeisai sagyohiMeisai : moveSagyohiMeisaiList) {
			// 問い合わせNOの再セット
			sagyohiMeisai.setToiawaseNo(newToiawaseNo);

			// 問い合わせ履歴NOの生成
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					sagyohiMeisai.getToiawaseRirekiNo(), newMaxRirekiNo);

			// 問い合わせ履歴NOの再セット
			sagyohiMeisai.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// 依頼ファイルアップロード情報の登録
			sagyohiMeisaiDao.insertAsEntity(sagyohiMeisai);
		}
	}

	/**
	 * 登録する問い合わせ履歴NOを作成します。
	 *
	 * @param oldToiawaseRirekiNo 移動元問い合わせ履歴NO
	 * @param maxToiawaseRirekiNo 移動先の最大問い合わせ履歴NO
	 * @return 移動先に登録する問い合わせ履歴NO
	 */
	private BigDecimal createEntryToiawaseRirekiNo(BigDecimal oldToiawaseRirekiNo, BigDecimal maxToiawaseRirekiNo) {
		// 登録する問い合わせ履歴NO
		BigDecimal entryToiawaseRirekiNo =
			new BigDecimal(oldToiawaseRirekiNo.intValue());
		entryToiawaseRirekiNo = entryToiawaseRirekiNo.add(maxToiawaseRirekiNo);

		return entryToiawaseRirekiNo;
	}

	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model 問い合わせ履歴移動画面モデル
	 * @return 検索条件
	 */
	private String createSearchCondition(TB026InquiryHistoryMoveModel model) {
		StringBuilder accesslog = new StringBuilder("");

		accesslog.append("oldToiawaseNo=");
		accesslog.append(model.getToiawaseNo());
		accesslog.append(",");
		accesslog.append("newToiawaseNo=");
		accesslog.append(StringUtils.leftPad(model.getNewToiawaseNo(), 10, '0'));
		accesslog.append(",");
		accesslog.append("newToiawaseRirekiNo=");

		StringBuilder entryToiawaseRirekNo = new StringBuilder("");
		for (int i = 0; i < model.getEntryRirekiNoList().size(); i++) {
			BigDecimal toiawaseRirekiNo = model.getEntryRirekiNoList().get(i);

			if (StringUtils.isNotBlank(entryToiawaseRirekNo.toString())) {
				entryToiawaseRirekNo.append(",");
			}

			entryToiawaseRirekNo.append(toiawaseRirekiNo.toString());
		}

		accesslog.append(entryToiawaseRirekNo.toString());

		return accesslog.toString();
	}
}
