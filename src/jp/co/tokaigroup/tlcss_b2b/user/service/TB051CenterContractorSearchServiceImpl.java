package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMTodofukenDao;
import jp.co.tokaigroup.reception.dao.RcpTGyoshaGyoshuDao;
import jp.co.tokaigroup.reception.dao.RcpTGyoshaTodofukenDao;
import jp.co.tokaigroup.reception.dto.RC061GyoshaSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpMTodofuken;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB051CenterContractorSearchModel;

/**
 * センター業者検索検索サービス実装クラス。
 *
 * @author v145527
 * @version 1.0 2015/09/29
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB051CenterContractorSearchServiceImpl extends TLCSSB2BBaseService
implements TB051CenterContractorSearchService {
	
	/** 共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 都道府県マスタDAO */
	@Autowired
	private RcpMTodofukenDao todofukenDao;

	/** 業者マスタDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/** 業者都道府県テーブルDAO */
	@Autowired
	private RcpTGyoshaTodofukenDao gyoshaTodofukenDao;

	/** 業者業種テーブルDAO */
	@Autowired
	private RcpTGyoshaGyoshuDao gyoshaGyoshuDao;

	/**
	 * 初期表示を行います。
	 *
	 * @param model センター業者検索画面モデル
	 * @return センター業者検索画面モデル
	 */
	public TB051CenterContractorSearchModel getInitInfo(
			TB051CenterContractorSearchModel model) {

		// 共通コードマスタからの取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_GYOSHU);

		// 業種取得
		List<RcpMComCd> gyoshuList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_GYOSHU);
		model.setGyoshuList(gyoshuList);

		// 都道府県リストの取得
		List<RcpMTodofuken> todofukenList = todofukenDao.selectAll();
		model.setTodofukenList(todofukenList);

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// 郵便番号検索URL取得
		model.setYubinNoSearchURL(userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_YUBIN_NO_SEARCH_URL));

		return model;
	}

	/**
	 * 業者一覧を検索します。
	 *
	 * @param model センター業者検索画面モデル
	 * @return TB051ContractorSearchModel センター業者検索画面モデル
	 */
	public TB051CenterContractorSearchModel search(
			TB051CenterContractorSearchModel model) {

		// 初期情報取得処理
		model = getInitInfo(model);
		
		// 検索処理実行
		List<RC061GyoshaSearchDto> resultList = gyoshaDao.selectByCondition(model.getCondition());
		
		// 業者コード一覧作成
		List<String> gyoshaCdList = new ArrayList<String>();
		for (RC061GyoshaSearchDto gyoshaSearchDto : resultList) {
			gyoshaCdList.add(gyoshaSearchDto.getGyoshaCd());
		}
		// 和名変換用Map取得
		// 都道府県変換Map 
		Map<String, String> todofukenMap = gyoshaTodofukenDao.selectJoinNmMap(gyoshaCdList, " ");
		// 業種変換Map
		Map<String, String> gyoshuMap = gyoshaGyoshuDao.selectJoinNmMap(gyoshaCdList, " ");

		// 和名変換処理
		for (RC061GyoshaSearchDto dto : resultList) {
			if (todofukenMap.containsKey(dto.getGyoshaCd())) {
				// 都道府県名
				dto.setTodofukenNm(todofukenMap.get(dto.getGyoshaCd()));
			}
			if (gyoshuMap.containsKey(dto.getGyoshaCd())) {
				// 業種名
				dto.setGyoshuNm(gyoshuMap.get(dto.getGyoshaCd()));
			}
		}

		// 検索結果の設定
		model.setResultList(resultList);
		
		return model;
	}

}
