package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB016OutsourcerSelectModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 委託会社選択サービス実装クラス。
 *
 * @author k002785
 * @version 1.0 2015/09/08
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB016OutsourcerSelectServiceImpl extends TLCSSB2BBaseService
		implements TB016OutsourcerSelectService {

	/** 会社マスタ */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/**
	 * 検索処理を実行します。
	 *
	 * @param model 委託会社選択画面モデル
	 * @return 委託会社選択画面モデル
	 */
	public TB016OutsourcerSelectModel executeSearch(TB016OutsourcerSelectModel model) {

		// 検索処理実行
		model.setResultList(kaishaDao.selectOutsourcerList(model.getCondition()));

		// モデルを返却
		return model;
	}
}
