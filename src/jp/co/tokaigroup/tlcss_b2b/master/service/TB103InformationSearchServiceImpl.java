package jp.co.tokaigroup.tlcss_b2b.master.service;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTInformationDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB103InformationSearchModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * お知らせ検索画面サービス実装クラス。
 *
 * @author v138130
 * @version 4.0 2014/06/18
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB103InformationSearchServiceImpl  extends TLCSSB2BBaseService
			implements TB103InformationSearchService {

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** お知らせテーブルDAO */
	@Autowired
	private TbTInformationDao infoDao;

	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/**
	 * 初期情報取得処理を行います。
	 *
	 * @param model お知らせ検索画面モデル
	 * @return お知らせ検索画面モデル
	 */
	public TB103InformationSearchModel getInitInfo(TB103InformationSearchModel model) {
		// 共通コードマスタからMapを取得し、表示対象用リストを設定する
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET);
		model.setTargetList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET));

		return model;
	}

	/**
	 * 検索処理を行います。
	 *
	 * @param model お知らせ検索画面モデル
	 * @return お知らせ検索画面モデル
	 */
	public TB103InformationSearchModel search(TB103InformationSearchModel model) {
		// 初期情報取得処理
		model = getInitInfo(model);

		// お知らせテーブル検索結果リストを取得
		List<TbTInformation> resultList = infoDao.selectByCondition(model.getCondition());
		// sql抽出の漢字名称をTbTInformationに項目追加すること

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET);
		Map<String, Map<String, RcpMComCd>> convertMap =
				comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 和名変換処理
		Map<String, RcpMComCd> targetMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET);
		for (int i = 0; i < resultList.size(); i++) {
			TbTInformation info = resultList.get(i);
			String targetDisplay = "";

			// 表示対象の名称設定
			if (StringUtils.isNotBlank(info.getKanjiNm1())) {
				// 漢字氏名１が存在する場合
				targetDisplay = info.getKokyakuId() + "<br>" + info.getKanjiNm1() + ((StringUtils.isNotBlank(info.getKanjiNm2()) ? (" " + info.getKanjiNm2()) : "" ));
			} else if (StringUtils.isNotBlank(info.getGyoshaNm())) {
				// 業者名が存在する場合
				targetDisplay = info.getGyoshaCd() + "<br>" + info.getGyoshaNm();
			} else if (StringUtils.isNotBlank(info.getKaishaNm())) {
				// 会社名が存在する場合
				targetDisplay = info.getKaishaId() + "<br>" + info.getKaishaNm();
			} else if (StringUtils.isNotBlank(info.getTarget()) && targetMap.containsKey(info.getTarget())) {
				// 漢字氏名１、業務名が存在しない場合
				targetDisplay = targetMap.get(info.getTarget()).getExternalSiteVal();
			}
			info.setTargetDisplay(targetDisplay);
			resultList.set(i, info);
		}

		// アクセスログの登録処理
		tbAccesslogDao.insert(TB103InformationSearchModel.GAMEN_NM,
				Constants.BUTTON_NM_SEARCH, createKensakuJoken(model));

		model.setResultList(resultList);

		return model;
	}

	/**
	 * 削除処理を行います。
	 *
	 * @param model お知らせ検索画面モデル
	 * @return お知らせ検索画面モデル
	 */
	public void delete(TB103InformationSearchModel model) {
		// お知らせ情報の削除処理
		int result = infoDao.deleteBy(model.getSeqNo());
		if (result == 0) {
			// お知らせ情報が存在しない場合は、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// アクセスログの登録処理
		tbAccesslogDao.insert(TB103InformationSearchModel.GAMEN_NM,
				Constants.BUTTON_NM_DELETE, createKensakuJoken(model));

		return;
	}


	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model お知らせ検索画面モデル
	 * @return 検索条件
	 */
	private String createKensakuJoken(TB103InformationSearchModel model) {
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
