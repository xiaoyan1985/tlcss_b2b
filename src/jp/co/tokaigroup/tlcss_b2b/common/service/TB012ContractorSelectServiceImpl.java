package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB012ContractorSelectModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 不動産･管理会社選択サービス実装クラス。
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB012ContractorSelectServiceImpl extends TLCSSB2BBaseService
		implements TB012ContractorSelectService {

	/** サービスＭDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/**
	 * 検索処理を実行します。
	 *
	 * @param model 問い合わせ検索画面モデル
	 * @return 問い合わせ検索画面モデル
	 */
	public TB012ContractorSelectModel executeSearch(TB012ContractorSelectModel model) throws ValidationException {

		// 検索処理実行
		model.setResultList(gyoshaDao.selectContractor(model.getCondition()));

		return model;
	}

}
