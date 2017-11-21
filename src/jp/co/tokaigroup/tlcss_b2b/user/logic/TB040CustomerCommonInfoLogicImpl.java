package jp.co.tokaigroup.tlcss_b2b.user.logic;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFBukkenDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFBukken;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.tags.Functions;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 顧客基本情報詳細ロジック実装クラス。
 *
 * @author v140546
 * @version 1.0 2014/05/29
 * @version 1.1 2015/11/04 J.Matsuba 和名変換Mapの修正、顧客付随物件情報取得処理の追加
 * @version 1.2 2016/06/17 S.Nakano 請求先顧客情報複数件対応
 * @version 1.3 2016/07/19 J.Matsuba 請求先顧客情報リスト表示対応
 */
@Service
public class TB040CustomerCommonInfoLogicImpl extends TLCSSB2BBaseService
		implements TB040CustomerCommonInfoLogic {

	/** リセプション顧客テーブルDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** リセプション顧客付随物件情報マスタDAO */
	@Autowired
	private RcpMKokyakuFBukkenDao kokyakuFBukkenDao;

	/** 委託会社関連チェックロジック */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	
	/**
	 * 顧客基本情報の取得を行います。
	 *
	 * @param kokyakuId 顧客ID
	 * @return 顧客マスタ
	 */
	public RcpMKokyaku getKokyakuInfo(String kokyakuId) throws ValidationException {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// セキュリティチェック
		if (userContext.isOutsourcerOp() || userContext.isOutsourcerSv()) {
			// 共通-委託会社関連チェックの結果がNGの場合は例外
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), kokyakuId)) {
				throw new ForbiddenException("委託先に関連しない顧客ＩＤです。"); 
			}
		}
		
		//顧客基本情報取得
		RcpMKokyaku kokyakuEntity = null;
		if(StringUtils.isNotBlank(kokyakuId)){
			kokyakuEntity = kokyakuDao.selectByPrimaryKey(kokyakuId);
		}
		if(kokyakuEntity == null){
			// 顧客情報が取得できない場合エラー
			throw new ValidationException(new ValidationPack().addError("MSG0015", "該当顧客ID"));
		}

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND,
				RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM,
				RcpMComCd.RCP_M_COM_CD_SERVICE_SHUBETSU);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 顧客区分Map
		Map<String, RcpMComCd> kokyakuKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		// 顧客種別Map
		Map<String, RcpMComCd> kokyakuShubetsuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// 管理形態Map
		Map<String, RcpMComCd> kanriKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM);

		// 和名変換処理
		if (StringUtils.isNotBlank(kokyakuEntity.getKokyakuKbn()) &&
				kokyakuKbnMap.containsKey(kokyakuEntity.getKokyakuKbn())) {
			// 顧客区分名
			kokyakuEntity.setKokyakuKbnNm(kokyakuKbnMap.get(kokyakuEntity.getKokyakuKbn()).getExternalSiteVal());
		}
		if (StringUtils.isNotBlank(kokyakuEntity.getKokyakuShubetsu()) &&
				kokyakuShubetsuMap.containsKey(kokyakuEntity.getKokyakuShubetsu())) {
			// 顧客種別
			kokyakuEntity.setKokyakuShubetsuNm(kokyakuShubetsuMap.get(kokyakuEntity.getKokyakuShubetsu()).getExternalSiteVal());
		}

		kokyakuEntity.setYubinNo(Functions.formatYubinNo(kokyakuEntity.getYubinNo()));

		// 請求先顧客情報取得
		List<RcpMKokyaku> seikyusakiKokyakuList = kokyakuDao.selectSeikyusakiKokyakuList(kokyakuId);

		kokyakuEntity.setSeikyusakiKokyakuList(seikyusakiKokyakuList);

		// 注意事項の表示期間チェック
		setAttention(kokyakuEntity);

		// 顧客付随物件情報取得
		if (kokyakuEntity.isKokyakuKbnBukken()) {

			RcpMKokyakuFBukken kokyakuFBukkenEntity = kokyakuFBukkenDao.selectByPrimaryKey(kokyakuId);

			// 和名変換処理
			if (kokyakuFBukkenEntity != null
					&& StringUtils.isNotBlank(kokyakuFBukkenEntity.getKanriKeitaiKbn())
					&& kanriKeitaiMap.containsKey(kokyakuFBukkenEntity.getKanriKeitaiKbn())) {
				// 管理形態
				kokyakuEntity.setKanriKeitaiKbnNm(kanriKeitaiMap.get(kokyakuFBukkenEntity.getKanriKeitaiKbn()).getExternalSiteVal());
			}
		}

		return kokyakuEntity;
	}

	/**
	 * 注意事項のセット処理を行います。
	 *
	 * @param kokyakuEntity 顧客マスタEntity
	 */
	private void setAttention(RcpMKokyaku kokyakuEntity) {

		// 判定用システム日付取得
		Timestamp sysdate = DateUtil.toTimestamp(DateUtil.getSysDateString(), "yyyy/MM/dd");

		// 顧客基本情報.注意事項４
		if (!judgesHyojiKikan(sysdate
				, kokyakuEntity.getAttention4StartDt()
				, kokyakuEntity.getAttention4EndDt())) {
			// 表示期間外のため、nullをセット
			kokyakuEntity.setAttention4(null);
		}

		// 顧客基本情報.注意事項５
		if (!judgesHyojiKikan(sysdate
				, kokyakuEntity.getAttention5StartDt()
				, kokyakuEntity.getAttention5EndDt())) {
			// 表示期間外のため、nullをセット
			kokyakuEntity.setAttention5(null);
		}

		// 顧客基本情報.注意事項６
		if (!judgesHyojiKikan(sysdate
				, kokyakuEntity.getAttention6StartDt()
				, kokyakuEntity.getAttention6EndDt())) {
			// 表示期間外のため、nullをセット
			kokyakuEntity.setAttention6(null);
		}

		if (kokyakuEntity.getSeikyusakiKokyakuList() != null && !kokyakuEntity.getSeikyusakiKokyakuList().isEmpty()) {
			// 請求先顧客情報分以下処理を行う
			for (RcpMKokyaku seikyusakiKokyaku : kokyakuEntity.getSeikyusakiKokyakuList()) {

				// 請求先顧客情報.注意事項４
				if (!judgesHyojiKikan(sysdate
						, seikyusakiKokyaku.getAttention4StartDt()
						, seikyusakiKokyaku.getAttention4EndDt())) {
					// 表示期間外のため、nullをセット
					seikyusakiKokyaku.setAttention4(null);
				}

				// 請求先顧客情報.注意事項５
				if (!judgesHyojiKikan(sysdate
						, seikyusakiKokyaku.getAttention5StartDt()
						, seikyusakiKokyaku.getAttention5EndDt())) {
					// 表示期間外のため、nullをセット
					seikyusakiKokyaku.setAttention5(null);
				}
				// 請求先顧客情報.注意事項６
				if (!judgesHyojiKikan(sysdate
						, seikyusakiKokyaku.getAttention6StartDt()
						, seikyusakiKokyaku.getAttention6EndDt())) {
					// 表示期間外のため、nullをセット
					seikyusakiKokyaku.setAttention6(null);
				}
			}
		}
	}

	/**
	 * 表示期間の表示判定処理を行います。
	 *
	 * @param sysdate システム日付
	 * @param startDt 表示開始日
	 * @param endDt 表示終了日
	 * @return true：表示する
	 */
	private boolean judgesHyojiKikan(Timestamp sysdate, Timestamp startDt, Timestamp endDt) {

		if (startDt != null && endDt == null) {
			// 表示開始日がnull以外かつ表示終了日がnull

			if (sysdate.compareTo(startDt) < 0) {
				return false;
			}
		} else if (startDt == null && endDt != null) {
			// 表示開始日がnullかつ表示終了日がnull以外

			if (sysdate.compareTo(endDt) > 0) {
				return false;
			}
		} else if (startDt != null && endDt != null) {
			// 表示開始日がnull以外かつ表示終了日がnull以外

			if (sysdate.compareTo(startDt) < 0 || sysdate.compareTo(endDt) > 0) {
				return false;
			}
		}

		return true;
	}

}
