package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMGrpKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMRefKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMRoleUrlDao;
import jp.co.tokaigroup.reception.dao.TbMUserDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMGrpKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010ManagedTargetListModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理対象一覧サービス実装クラス。
 *
 * @author v140546
 * @version 4.0 2014/09/25
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB010ManagedTargetListServiceImpl extends TLCSSB2BBaseService implements TB010ManagedTargetListService {

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** 外部サイトシステム ユーザーマスタDAO */
	@Autowired
	private TbMUserDao userDao;

	/** リセプション グループ顧客マスタ（ＴＯＫＡＩ用）DAO */
	@Autowired
	private TbMGrpKokyakuDao grpKokyakuDao;

	/** リセプション 参照顧客マスタ */
	@Autowired
	private TbMRefKokyakuDao refKokyakuDao;

	/** 外部サイトシステム アクセス可能ＵＲＬマスタDAO */
	@Autowired
	private TbMRoleUrlDao roleUrlDao;

	/**
	 * 初期処理を行います。
	 *
	 * @param model 管理対象一覧モデル
	 */
	public TB010ManagedTargetListModel getInitInfo(TB010ManagedTargetListModel model) {

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// ユーザーマスタから情報取得
		TbMUser user = userDao.selectByLoginId(userContext.getLoginId());
		if(user == null){
			//ユーザーマスタが取得できない場合エラー
			throw new ValidationException(new ValidationPack().addError("MSG0002"));
		}

		// グループ顧客マスタから情報取得
		model.setGrpKokyakuList(new ArrayList<TbMGrpKokyaku>());
		List<TbMGrpKokyaku> grpKokyakuList = grpKokyakuDao.selectBy(user.getSeqNo(), null);
		if(grpKokyakuList == null || grpKokyakuList.size() == 0){
			//グループ顧客マスタが取得できない場合エラー
			throw new ValidationException(new ValidationPack().addError("MSG0002"));
		}

		for (TbMGrpKokyaku grpKokyaku : grpKokyakuList) {
			grpKokyaku.setKanjiKokyakuNm(kokyakuDao.selectByPrimaryKey(grpKokyaku.getRefKokyakuId()).getKanjiNm());
			model.getGrpKokyakuList().add(grpKokyaku);
		}
		return model;
	}

	/**
	 * 顧客選択処理を行います。
	 *
	 * @param model 管理対象一覧モデル
	 */
	public TB010ManagedTargetListModel selectKokyakuId(TB010ManagedTargetListModel model) {

		// 参照顧客マスタから情報取得
		model.setRefKokyakuList(refKokyakuDao.selectBy(model.getSelectedKokyakuId(), null));

		// アクセス可能ＵＲＬマスタからアクセス可能ＵＲＬMap取得
		Map<String, TbMRoleUrl> accessibleMap =
			roleUrlDao.selectAccessibleMap(TLCSSB2BUserContext.ROLE_REAL_ESTATE);

		// 顧客マスタから顧客情報取得
		RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(model.getSelectedKokyakuId());
		if (kokyaku == null) {
			// 取得できなかった場合は、システムエラー
			throw new ApplicationException("顧客マスタ情報が取得できません。 顧客ＩＤ=" + model.getSelectedKokyakuId());
		}
		
		model.setAccessibleMap(accessibleMap);
		model.setKokyakuInfo(kokyaku);

		return model;
	}
}
