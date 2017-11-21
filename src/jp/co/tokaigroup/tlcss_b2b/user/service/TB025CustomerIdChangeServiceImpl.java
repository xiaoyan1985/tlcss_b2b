package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuWithNoIdDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB025CustomerIdChangeModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 顧客ＩＤ変更画面サービス実装クラス。
 *
 * @author k002785
 * @version 1.0 2015/08/19
 * @version 1.1 2016/07/22 J.Matsuba 更新チェック処理isValid()の追加
 * @version 1.2 2016/08/04 H.Yamamura 入力チェックを問い合わせのサービス種別で行うように変更
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB025CustomerIdChangeServiceImpl  extends TLCSSB2BBaseService
	implements TB025CustomerIdChangeService {

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;
	
	/** リセプション問い合わせテーブルDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;
	
	/** リセプションＩＤ無し顧客テーブルDAO */
	@Autowired
	private RcpTKokyakuWithNoIdDao kokyakuWithNoIdDao;

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao accesslogDao;

	/** 委託会社関連チェックロジック */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;

	/**
	 * 初期表示処理を行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB025CustomerIdChangeModel getInitInfo(TB025CustomerIdChangeModel model) {

		// セッション情報を取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		
		Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);
		
		// 顧客区分和名Mapの取得
		Map<String, RcpMComCd> kokyakuKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);

		// 依頼者区分和名Mapの取得
		Map<String, RcpMComCd> iraishaKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		
		// 変更前顧客IDが存在する場合
		if (model.isOldKokyakuIdExsits()) {
			// 変更前顧客IDを編集
			model.setOldKokyakuId(editKokyakuId(model.getOldKokyakuId()));
			// 顧客マスタ取得
			RcpMKokyaku oldKokyakuInfo = kokyakuDao.selectByPrimaryKey(model.getOldKokyakuId());

			// 顧客情報が存在しない場合
			if (oldKokyakuInfo == null) {
				// 排他エラー
				model.setInitError(true);
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			// 顧客区分名を設定（顧客区分Mapに対象の顧客区分が存在する場合）
			if (kokyakuKbnMap.containsKey(oldKokyakuInfo.getKokyakuKbn())) {
				oldKokyakuInfo.setKokyakuKbnNm(kokyakuKbnMap.get(oldKokyakuInfo.getKokyakuKbn()).getExternalSiteVal());
			}

			model.setOldKokyakuInfo(oldKokyakuInfo);
		} else {
			// ID無し顧客情報取得
			RcpTKokyakuWithNoId kokyakuWithNoId = kokyakuWithNoIdDao.selectByPrimaryKey(model.getToiawaseNo());

			// ID無し顧客情報が存在しない場合
			if (kokyakuWithNoId == null) {
				// 排他エラー
				model.setInitError(true);
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			// 顧客区分名を取得（依頼者区分Mapに対象の顧客区分が存在する場合）
			if (iraishaKbnMap.containsKey(kokyakuWithNoId.getKokyakuKbn())) {
				kokyakuWithNoId.setKokyakuKbnNm(iraishaKbnMap.get(kokyakuWithNoId.getKokyakuKbn()).getExternalSiteVal());
			}

			model.setOldKokyakuInfoWithoutId(kokyakuWithNoId);
		}
		
		// 変更後顧客IDを編集
		model.setNewKokyakuId(editKokyakuId(model.getNewKokyakuId()));
		// 変更後顧客情報取得
		RcpMKokyaku newKokyakuInfo = kokyakuDao.selectByPrimaryKey(model.getNewKokyakuId());
		// 顧客情報が存在しない場合
		if (newKokyakuInfo == null) {
			// 排他エラー
			model.setInitError(true);
			throw new ValidationException(new ValidationPack().addError("MSG0002"));
		}
		
		// セッションの権限が「40:委託会社SV」または「41:委託会社OP」の場合
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// 委託関連会社チェックでNGの場合
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), model.getNewKokyakuId())){
				// 委託関連会社チェックエラー
				model.setInitError(true);
				throw new ValidationException(new ValidationPack().addError("MSG0031", "参照不可顧客の", "顧客ＩＤ"));
			}
		}

		// 顧客区分名を設定（顧客区分Mapに対象の顧客区分が存在する場合）
		if (kokyakuKbnMap.containsKey(newKokyakuInfo.getKokyakuKbn())) {
			newKokyakuInfo.setKokyakuKbnNm(kokyakuKbnMap.get(newKokyakuInfo.getKokyakuKbn()).getExternalSiteVal());
		}

		model.setNewKokyakuInfo(newKokyakuInfo);
		
		// 顧客IDの設定（問い合わせ登録画面再表示用に入力した顧客IDを設定）
		model.setKokyakuId(model.getNewKokyakuId());

		// 画面モデルを返却
		return model;
	}

	/**
	 * 問い合わせ顧客更新処理を行います。
	 *
	 * @param model 画面モデル
	 */
	@Transactional(value="txManager")
	public void updateToiawaseKokyakuInfo(TB025CustomerIdChangeModel model) {
		// 更新チェック処理
		isValid(model);

		// 問い合わせテーブルの更新処理
		toiawaseDao.updateKokyakuId(createUpdateToiawaseEntity(model));

		// 変更前顧客IDが存在しない場合
		if (!model.isOldKokyakuIdExsits()) {
			// ID無し顧客情報の退避
			int ret = kokyakuWithNoIdDao.insertTaihi(model.getOldKokyakuInfoWithoutId());
			if (ret == 0) {
				// 更新件数が０件の場合は、排他エラー
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}

			// ID無し顧客情報の削除
			kokyakuWithNoIdDao.deleteWithoutOptimisticLock(model.getOldKokyakuInfoWithoutId().getToiawaseNo());
		}

		// アクセスログ登録
		accesslogDao.insert(TB025CustomerIdChangeModel.GAMAN_NM
			,TB025CustomerIdChangeModel.BUTTON_NM_CHANGE
			,createKensakuJoken(model));
	}

	/**
	 * 更新チェック処理を行います。
	 *
	 * @param model 画面モデル
	 * @throws ValidationException スケジュール情報が存在する場合
	 */
	private void isValid(TB025CustomerIdChangeModel model) {
		// 問い合わせ情報を取得
		RcpTToiawase toiawaseInfo = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		// 問い合わせ情報が存在しない　または　サービス種別がビル管理の場合
		if (toiawaseInfo == null || toiawaseInfo.isBuildingManagement()) {
			// エラーメッセージを表示
			throw new ValidationException(new ValidationPack().addError("MSG0046", "問い合わせNO", "顧客IDの変更を実施できません"));
		}
	}

	/**
	 * 検索する顧客IDの編集を行います。
	 *
	 * @param kokyakuId 顧客ID
	 * @return 編集後顧客ID
	 */
	private String editKokyakuId(String kokyakuId) {
		// 顧客IDの文字数が10以上の場合は編集無で返却
		if (kokyakuId.length() >= 10) {
			return kokyakuId;
		}

		// 顧客IDが9桁未満の場合は、「C」付加 + 「0」埋めを行った文字で返却
		return new StringBuilder("C").append(StringUtils.leftPad(kokyakuId, 9, '0')).toString();
	}
	
	/**
	 * 問い合わせテーブル情報の更新情報を作成します。
	 *
	 * @param model 画面モデル
	 * @return 問い合わせ情報
	 */
	private RcpTToiawase createUpdateToiawaseEntity(TB025CustomerIdChangeModel model) {
		RcpTToiawase toiawase = new RcpTToiawase();
		toiawase.setToiawaseNo(model.getToiawaseNo());
		toiawase.setKokyakuId(model.getNewKokyakuId());
		toiawase.setUpdDt(model.getToiawaseUpdDt());
		toiawase.setLastUpdId(getUserContext().getLoginId());

		return toiawase;
	}
	
	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model 問い合わせ顧客ＩＤ変更画面モデル
	 * @return 検索条件
	 */
	private String createKensakuJoken(TB025CustomerIdChangeModel model) {
		StringBuilder accesslog = new StringBuilder("");

		accesslog.append("toiawaseNo=");
		accesslog.append(model.getToiawaseNo());
		accesslog.append(",");
		accesslog.append("oldKokyakuId=");
		accesslog.append(model.getOldKokyakuId());
		accesslog.append(",");
		accesslog.append("kokyakuId=");
		accesslog.append(model.getNewKokyakuId());

		return accesslog.toString();
	}

}
