package jp.co.tokaigroup.tlcss_b2b.master.service;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.TbMUserDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB101UserMasterSearchModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザーマスタ検索サービス実装クラス。
 *
 * @author k002849
 * @version 1.0 2014/05/26
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB101UserMasterSearchServiceImpl extends TLCSSB2BBaseService
	implements TB101UserMasterSearchService {

	/** 外部サイトユーザーマスタDAO */
	@Autowired
	private TbMUserDao userDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/**
	 * 初期表示処理を行います。
	 *
	 * @param model ユーザーマスタ検索画面モデル
	 * @return ユーザーマスタ検索画面モデル
	 */
	public TB101UserMasterSearchModel getInitInfo(TB101UserMasterSearchModel model) {
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);

		// 権限リストの取得
		List<RcpMComCd> roleList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);

		model.setRoleList(roleList);

		return model;
	}

	/**
	 * 検索処理を行います。
	 *
	 * @param model ユーザーマスタ検索画面モデル
	 * @return ユーザーマスタ検索画面モデル
	 */
	public TB101UserMasterSearchModel executeSearch(TB101UserMasterSearchModel model) {
		// 初期表示処理再実行（リスト情報取得のため）
		model = getInitInfo(model);

		// 検索処理実行
		List<TbMUser> resultList = userDao.selectByCondition(model.getCondition());

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 権限Map
		Map<String, RcpMComCd> roleMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);

		// 和名変換処理
		for (int i = 0; i < resultList.size(); i++) {
			TbMUser user = resultList.get(i);

			// 権限
			if (roleMap.containsKey(user.getRole())) {
				user.setRoleNm(roleMap.get(user.getRole()).getExternalSiteVal());
			}

			// 再設定
			resultList.set(i, user);
		}

		// アクセスログ登録
		tbAccesslogDao.insert(TB101UserMasterSearchModel.GAMEN_NM,
				Constants.BUTTON_NM_SEARCH, createKensakuJoken(model));

		model.setResultList(resultList);

		return model;
	}

	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model ユーザーマスタ画面モデル
	 * @return アクセスログに登録する検索条件
	 */
	private String createKensakuJoken(TB101UserMasterSearchModel model) {
		NullExclusionToStringBuilder searchEntry =
			new NullExclusionToStringBuilder(
				model.getCondition(),
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// 除外する項目
		searchEntry.setExcludeFieldNames(Constants.EXCLUDE_FIELD_NAMES);

		return searchEntry.toString();
	}
}
