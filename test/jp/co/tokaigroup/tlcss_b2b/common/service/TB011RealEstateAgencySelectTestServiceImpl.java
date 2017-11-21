package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB011RealEstateAgencySelectTestModel;

/**
 * 不動産・管理会社選択画面（スタブ）サービス実装クラス。
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB011RealEstateAgencySelectTestServiceImpl extends TLCSSB2BBaseService
	implements TB011RealEstateAgencySelectTestService {
	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/**
	 * 顧客情報リストを取得します。
	 *
	 * @param model 不動産・管理会社選択画面（スタブ）モデル
	 * @return 不動産・管理会社選択画面（スタブ）モデル
	 */
	public TB011RealEstateAgencySelectTestModel getKokyakuList(TB011RealEstateAgencySelectTestModel model) {
		List<RcpMKokyaku> kokyakuList =
			kokyakuDao.selectByCondition(model.getCondition(), "0");

		model.setKokyakuList(kokyakuList);

		return model;
	}

}
