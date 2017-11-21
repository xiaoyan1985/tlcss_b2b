package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuManualDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuManual;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.dto.TB027CommonManualListDto;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB027CustomerManualModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 顧客マニュアル一覧サービス実装クラス。
 *
 * @author 松葉
 * @version 1.0 2015/08/05
 * @version 1.1 2016/07/07 H.Hirai 請求先複数対応
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB027CustomerManualServiceImpl extends TLCSSB2BBaseService
		implements TB027CustomerManualService {
	/** リセプション顧客マニュアルテーブルDAO */
	@Autowired
	private RcpTKokyakuManualDao kokyakuManualDao;

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** 委託会社関連チェックロジック */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;

	/**
	 * 初期表示処理を行います。
	 *
	 * @param model 顧客マニュアル一覧画面モデル
	 * @return 顧客マニュアル一覧画面モデル
	 */
	public TB027CustomerManualModel getInitInfo(TB027CustomerManualModel model) {
		// セッションから添付可能最大件数を取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		int maxAppendableCount = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_KOKYAKU_MANUAL_APPENEDED_TO_MAX);

		// 顧客基本情報取得
		RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(model.getKokyakuId());
		if (kokyaku == null) {
			// 顧客基本情報が取得できない場合は、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// 顧客マニュアル情報取得
		List<RcpTKokyakuManual> kobetsuManualList =
			kokyakuManualDao.selectBy(model.getKokyakuId(), null);

		if (kobetsuManualList != null) {
			if (kobetsuManualList.size() > maxAppendableCount) {
				// 添付可能最大件数よりリストの数が多い場合
				// 添付可能最大件数分のみ、リストを表示
				kobetsuManualList = kobetsuManualList.subList(0, maxAppendableCount);
			} else if (kobetsuManualList.size() < maxAppendableCount) {
				// 添付可能最大件数に達していない場合は、空の情報を作成
				for (int i = kobetsuManualList.size(); i < maxAppendableCount; i++) {
					kobetsuManualList.add(new RcpTKokyakuManual());
				}
			}
		}

		// 請求先顧客情報リスト取得
		List<RcpMKokyaku> seikyusakiKokyakuList =
			kokyakuDao.selectSeikyusakiKokyakuList(model.getKokyakuId());

		// 顧客マニュアルリストＤＴＯリスト
		List<TB027CommonManualListDto> commonManualDtoList = new ArrayList<TB027CommonManualListDto>();
		// 取得した請求先顧客情報リスト分マニュアル情報取得
		for (RcpMKokyaku seikyusakiKokyakuInfo : seikyusakiKokyakuList) {

			String seikyusakiKokyakuId = seikyusakiKokyakuInfo.getKokyakuId();

			// 顧客マニュアル情報リスト
			List<RcpTKokyakuManual> commonManualInfoList = null;
			// 請求先顧客情報の顧客ＩＤとパラメータの顧客ＩＤが異なる場合、共通マニュアル情報リストを取得
			if (seikyusakiKokyakuInfo != null
					&& !(model.getKokyakuId().equals(seikyusakiKokyakuId))) {

				// 共通マニュアル情報リスト取得
				commonManualInfoList = kokyakuManualDao.selectBy(
						seikyusakiKokyakuId, null);

				// 添付可能最大件数に達していない場合は、空の情報を作成
				if (commonManualInfoList != null) {
					for (int i = commonManualInfoList.size(); i < maxAppendableCount; i++) {
						commonManualInfoList.add(new RcpTKokyakuManual());
					}
				}
			}

			// 取得した共通マニュアル情報を設定
			TB027CommonManualListDto commonManualDto = new TB027CommonManualListDto();
			commonManualDto.setKokyakuId(seikyusakiKokyakuId);
			commonManualDto.setCommonManualList(commonManualInfoList);
			commonManualDtoList.add(commonManualDto);
		}

		model.setKokyaku(kokyaku);
		model.setSeikyusakiKokyakuList(seikyusakiKokyakuList);
		model.setKobetsuManualList(kobetsuManualList);
		model.setCommonManualDtoList(commonManualDtoList);

		return model;
	}

	/**
	 * マニュアルファイルダウンロードのチェックを行います。
	 *
	 * @param model 顧客マニュアル一覧画面モデル
	 * @return チェック結果 true:チェックOK、false:チェックNG
	 */
	public boolean isValidKokyakuManualInfo(TB027CustomerManualModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// セッションの権限が「40:委託会社SV」または「41:委託会社OP」の場合
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			return outsourcerValidationLogic.isValid(userContext.getKaishaId(), model.getTargetKokyakuId());
		} else {
			return true;
		}
	}
}
