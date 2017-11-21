package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpTGyoshaGyoshuDao;
import jp.co.tokaigroup.reception.dao.RcpTGyoshaTodofukenDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB052CenterContractorDetailModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * センター業者詳細サービス実装クラス。
 *
 * @author v145527
 * @version 1.0 2015/10/08
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB052CenterContractorDetailServiceImpl extends TLCSSB2BBaseService
implements TB052CenterContractorDetailService {

	/** 共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 業者マスタDAO */
	@Autowired
	private RcpMGyoshaDao rcpMGyoshaDao;

	/** 業者都道府県テーブルDAO */
	@Autowired
	private RcpTGyoshaTodofukenDao rcpTGyoshaTodofukenDao;

	/** 業者業種テーブルDAO */
	@Autowired
	private RcpTGyoshaGyoshuDao rcpTGyoshaGyoshuDao;

	/** パスワードＭDAO */
	@Autowired
	private NatosMPasswordDao natosMPasswordDao;

	/**
	 * 初期表示を行います。
	 *
	 * @param model センター業者詳細画面モデル
	 * @return センター業者詳細画面モデル
	 */
	public TB052CenterContractorDetailModel getInitInfo(
			TB052CenterContractorDetailModel model) {
		
		// 業者情報取得
		RcpMGyosha gyosha = rcpMGyoshaDao.selectByPrimaryKey(model.getGyoshaCd());
		if (gyosha == null) {
			// 業者情報が取得できない場合、排他エラーとする
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		//----- 画面項目の取得 -----//
		// 共通コードマスタからの取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
			RcpMComCd.RCP_M_COM_CD_KBN_TODOFUKEN,
			RcpMComCd.RCP_M_COM_CD_KBN_GYOSHU,
			RcpMComCd.RCP_M_COM_CD_KBN_IRAI_MAIL_ATESAKI_FLG,
			RcpMComCd.RCP_M_COM_CD_KBN_IRAI_MAIL_ATESAKI_KBN);

		// 都道府県（地域）リストの取得
		List<RcpMComCd> todofukenAreaList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TODOFUKEN);
		model.setTodofukenAreaList(todofukenAreaList);

		// 和名取得用のマップの取得
		Map<String, Map<String, RcpMComCd>> comCdMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 業者コード一覧作成
		List<String> gyoshaCdList = new ArrayList<String>();
		gyoshaCdList.add(gyosha.getGyoshaCd());

		// 更新者Map
		Map<String, String> userMap = natosMPasswordDao.selectAllAsMap();
		// 都道府県(地域)変換Map
		Map<String, String> todofukenAreaMap = rcpTGyoshaTodofukenDao.selectNmMapByArea(model.getGyoshaCd(), " ");
		// 業種変換Map
		Map<String, String> gyoshuMap = rcpTGyoshaGyoshuDao.selectJoinNmMap(gyoshaCdList, " ");
		// 作業依頼メール自動送付フラグMap
		Map<String, RcpMComCd> iraiMailAtesakiFlgMap = comCdMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_MAIL_ATESAKI_FLG);
		// 作業依頼メール宛先区分Map
		Map<String, RcpMComCd> iraiMailAtesakiKbnMap = comCdMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_MAIL_ATESAKI_KBN);
		
		// 和名変換処理
		// 最終更新者
		gyosha.setLastUpdIdNm(userMap.get(gyosha.getLastUpdId()));
		// 都道府県（地域）
		gyosha.setTodofukenChikiMap(todofukenAreaMap);
		// 業種
		gyosha.setGyoshuNm(gyoshuMap.get(gyosha.getGyoshaCd()));
		// 自動送付有無
		if (iraiMailAtesakiFlgMap != null && iraiMailAtesakiFlgMap.containsKey(gyosha.getIraiMailAtesakiFlg())) {
			gyosha.setIraiMailAtesakiFlgNm(iraiMailAtesakiFlgMap.get(gyosha.getIraiMailAtesakiFlg()).getComVal());
		}
		// 宛先メールアドレス区分
		if (StringUtils.isNotBlank(gyosha.getIraiMailAtesakiKbn())
			&& iraiMailAtesakiKbnMap != null
			&& iraiMailAtesakiKbnMap.containsKey(gyosha.getIraiMailAtesakiKbn())) {
			gyosha.setIraiMailAtesakiKbnNm(iraiMailAtesakiKbnMap.get(gyosha.getIraiMailAtesakiKbn()).getComVal());
		}
		
		model.setTodofukenAreaMap(todofukenAreaMap);

		model.setGyosha(gyosha);



		return model;
	}

}
