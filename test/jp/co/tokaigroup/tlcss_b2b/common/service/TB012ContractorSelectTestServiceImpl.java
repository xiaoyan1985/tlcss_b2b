package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.List;

import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dto.RC061GyoshaSearchDto;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB012ContractorSelectTestModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 業者選択画面（スタブ）サービス実装クラス。
 *
 * @author k002849
 * @version 1.0 2014/05/29
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB012ContractorSelectTestServiceImpl extends TLCSSB2BBaseService
		implements TB012ContractorSelectTestService {
	/** リセプション業者マスタDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/**
	 * 業者リストを取得します。
	 *
	 * @param model 業者選択画面（スタブ）モデル
	 * @return 業者選択画面（スタブ）モデル
	 */
	public TB012ContractorSelectTestModel getGyoshaList(TB012ContractorSelectTestModel model) {
		List<RC061GyoshaSearchDto> resultList = gyoshaDao.selectByCondition(model.getCondition());

		model.setGyoshaList(resultList);

		return model;
	}
}
