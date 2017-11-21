package jp.co.tokaigroup.tlcss_b2b.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB014WorkReportModel;

/**
 * 作業報告書印刷サービス実装クラス。
 *
 * @author k002849
 * @version 4.0 2014/07/22
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB014WorkReportServiceImpl extends TLCSSB2BBaseService
		implements TB014WorkReportService {

	/** リセプション依頼テーブルDAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/**
	 * 作業報告書のダウンロードチェックを行います。
	 *
	 * @param model 作業報告書印刷モデル
	 * @return true:チェックOK、false:チェックNG
	 */
	public boolean isOwn(TB014WorkReportModel model) {
		// 依頼情報のチェックを行う
		return iraiDao.isOwn(model.getToiawaseNo(),
				model.getToiawaseRirekiNo(),
				model.getKokyakuId(),
				model.getGyoshaCd());
	}

}
