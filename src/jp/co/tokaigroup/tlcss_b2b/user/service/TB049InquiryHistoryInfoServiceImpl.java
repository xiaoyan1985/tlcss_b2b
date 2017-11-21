package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn1Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn2Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn3Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn4Dao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dto.RC031ToiawaseSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB049InquiryHistoryInfoModel;

/**
 * 顧客詳細問い合わせ履歴サービス実装クラス。
 *
 * @author v145527 小林
 * @version 1.0 2015/07/24
 *
 */

@Service
@Transactional(value="txManager", readOnly = true)
public class TB049InquiryHistoryInfoServiceImpl extends TLCSSB2BBaseService
implements TB049InquiryHistoryInfoService {
	
	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;
	
	/** リセプション共通コードマスタDAO */
	@Autowired
	private RcpMComCdDao comCdDao;

	/** 問い合わせ区分1マスタDAO */
	@Autowired
	private RcpMToiawaseKbn1Dao toiawaseKbn1Dao;

	/** 問い合わせ区分2マスタDAO */
	@Autowired
	private RcpMToiawaseKbn2Dao toiawaseKbn2Dao;

	/** 問い合わせ区分3マスタDAO */
	@Autowired
	private RcpMToiawaseKbn3Dao toiawaseKbn3Dao;

	/** 問い合わせ区分4マスタDAO */
	@Autowired
	private RcpMToiawaseKbn4Dao toiawaseKbn4Dao;

	/** 状況区分マスタDAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;
	
	/** 顧客マスタDAO */
	@Autowired
	private RcpTToiawaseDao rcpTToiawaseDao;

	/** パスワードＭDAO */
	@Autowired
	private NatosMPasswordDao natosMPasswordDao;

	@Override
	/**
	 * 初期表示処理を行います。
	 *
	 * @param model 顧客詳細問い合わせ履歴モデル
	 * @return 顧客詳細問い合わせ履歴モデル
	 */
	public TB049InquiryHistoryInfoModel getInitInfo(
			TB049InquiryHistoryInfoModel model) {
		
		// 初期情報取得処理
		// 顧客基本情報取得
		RcpMKokyaku kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		// 問い合わせ情報検索
		model = searchToiawase(model);
		// model にEntityデータを設定
		model.setKokyakuEntity(kokyakuEntity);
		return model;
	}
	
	/**
	 * 問い合わせ一覧を検索します。
	 *
	 * @param model 画面モデル
	 * @return TB049InquiryHistoryInfoModel 画面モデル
	 */
	private TB049InquiryHistoryInfoModel searchToiawase(TB049InquiryHistoryInfoModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		RC031ToiawaseSearchCondition condition = model.getCondition();
		
		// TB049顧客詳細問い合わせ履歴最大検索可能件数
		condition.setLimit(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_INQUIRY_HISTORY_TO_MAX));
		condition.setMaxCount(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_INQUIRY_HISTORY_TO_MAX));

		// 一覧検索
		condition.setKokyakuId(model.getKokyakuId().replaceAll("C", ""));	// 顧客IDの先頭一桁（C）を除く
		condition.setIraiUmuRdo("");
		List<RC031ToiawaseSearchDto> toiawaseSearchList = rcpTToiawaseDao.selectByCondition(condition);

		// 共通コードマスタからの取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);

		Map<String, Map<String, RcpMComCd>> comKeyMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);
		// 依頼者区分変換Map
		Map<String, RcpMComCd> convMapIraiKbn = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// 問い合わせ区分1Map
		Map<String, String> convMapT1 = toiawaseKbn1Dao.selectAllAsMap();
		// 問い合わせ区分2Map
		Map<String, String> convMapT2 = toiawaseKbn2Dao.selectAllAsMap();
		// 問い合わせ区分3Map
		Map<String, String> convMapT3 = toiawaseKbn3Dao.selectAllAsMap();
		// 問い合わせ区分4Map
		Map<String, String> convMapT4 = toiawaseKbn4Dao.selectAllAsMap();
		// 状況区分Map
		List<RcpMJokyoKbn> jokyoKbnList = jokyoKbnDao.selectAll();
		Map<String, String> convMapJokyoKbn = jokyoKbnDao.convertMap(jokyoKbnList);
		// 受付者Map
		List<String> userIdList = new ArrayList<String>();
		for (RC031ToiawaseSearchDto dto : toiawaseSearchList) {
			if (StringUtils.isNotBlank(dto.getUketsukeshaId())
				&& ! userIdList.contains(dto.getUketsukeshaId())) {
				// パスワードマスタ検索用のリストに含まれていなければ、リストに追加
				userIdList.add(dto.getUketsukeshaId());
			}
		}
		Map<String, String> convMapPassword = getMPasswordAsMap(userIdList);

		// 和名変換
		for (RC031ToiawaseSearchDto toiawaseDto : toiawaseSearchList) {
			// 依頼者区分変換Map
			if (convMapIraiKbn != null && StringUtils.isNotBlank(toiawaseDto.getIraishaKbn())) {
				toiawaseDto.setIraishaKbnNm(convertToValue(convMapIraiKbn, toiawaseDto.getIraishaKbn()));
			}
			// 問い合わせ区分1Map
			if (convMapT1 != null && StringUtils.isNotBlank(toiawaseDto.getToiawaseKbn1())) {
				toiawaseDto.setToiawaseKbn1Nm(convMapT1.get(toiawaseDto.getToiawaseKbn1()));
			}
			// 問い合わせ区分2Map
			if (convMapT2 != null && StringUtils.isNotBlank(toiawaseDto.getToiawaseKbn2())) {
				toiawaseDto.setToiawaseKbn2Nm(convMapT2.get(toiawaseDto.getToiawaseKbn2()));
			}
			// 問い合わせ区分3Map
			if (convMapT3 != null && StringUtils.isNotBlank(toiawaseDto.getToiawaseKbn3())) {
				toiawaseDto.setToiawaseKbn3Nm(convMapT3.get(toiawaseDto.getToiawaseKbn3()));
			}
			// 問い合わせ区分4Map
			if (convMapT4 != null && StringUtils.isNotBlank(toiawaseDto.getToiawaseKbn4())) {
				toiawaseDto.setToiawaseKbn4Nm(convMapT4.get(toiawaseDto.getToiawaseKbn4()));
			}
			// 状況区分Map
			if (convMapJokyoKbn != null && StringUtils.isNotBlank(toiawaseDto.getJokyoKbn())) {
				toiawaseDto.setJokyoKbnNm(convMapJokyoKbn.get(toiawaseDto.getJokyoKbn()));
			}
			// ログイン者Map
			if (StringUtils.isBlank(toiawaseDto.getToiawaseUketsukeshaNm())) {
				// 受付者名がNULLの場合、受付者ＩＤを和名変換して表示
				if (convMapPassword != null && StringUtils.isNotBlank(toiawaseDto.getUketsukeshaId())
						&& convMapPassword.containsKey(toiawaseDto.getUketsukeshaId())) {
					toiawaseDto.setUketsukeshaNm(convMapPassword.get(toiawaseDto.getUketsukeshaId()));
				}
			} else {
				// 受付者名がNULLでなければ、受付者名を表示
				toiawaseDto.setUketsukeshaNm(toiawaseDto.getToiawaseUketsukeshaNm());
			}
		}
		model.setResult(toiawaseSearchList);

		return model;
	}

	/**
	 * 共通コードマスタの和名変換Mapを取得します。
	 *
	 * @param cdKbn リセプション共通コードマスタ コード区分
	 * @return Map<String, Map<String, RcpMComCd>> 和名変換Map
	 */
	public Map<String, Map<String, RcpMComCd>> getMPasswordAsMap(String cdKbn) {

		// 共通コードマスタからの取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(cdKbn);

		// 和名変換用Map
		Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);

		return convertMap;
	}

	
	/**
	 * パスワードＭの和名変換Mapを取得します。
	 *
	 * @param cdKbn パスワードＭ コード区分
	 * @return Map<String, String> 和名変換Map
	 */
	private Map<String, String> getMPasswordAsMap(List<String> userIdList) {

		Map<String, String> userMap = new HashMap<String, String>();
		if (! userIdList.isEmpty()) {
			// パスワードＭからの取得
			userMap = natosMPasswordDao.convertMap(natosMPasswordDao.selectByList(userIdList, null));
		}

		return userMap;
	}

	/**
	 * 共通コードマスタの和名変換を行います。
	 *
	 * @param cdKbn リセプション共通コードマスタ コード区分
	 * @param cd リセプション共通コードマスタ コード
	 * @param convertMap コード変換Map
	 * @return String 変換後文字列
	 */
	private String convertToValue(Map<String, RcpMComCd> convertMap, String cd) {

		// 和名取得
		RcpMComCd comCd = convertMap.get(cd);
		String wamei = "";
		if (StringUtils.isNotBlank(comCd.getComVal())) {
			wamei = comCd.getComVal();
		}

		return wamei;
	}
}