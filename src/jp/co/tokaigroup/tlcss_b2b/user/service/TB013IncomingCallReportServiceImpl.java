package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB013IncomingCallReportModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 入電報告書印刷サービス実装クラス。
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 1.1 2015/12/11 S.Nakano isOwnメソッド削除、getDownloadInfoメソッド追加
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB013IncomingCallReportServiceImpl extends TLCSSB2BBaseService
		implements TB013IncomingCallReportService {

	/** リセプション問い合わせテーブルDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/**
	 * ダウンロード情報の取得を行います。
	 * 
	 * @param model 入電報告書印刷モデル
	 * @return 入電報告書印刷モデル
	 */
	public TB013IncomingCallReportModel getDowloadInfo(TB013IncomingCallReportModel model) {
		// セッション情報の取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		model.setDownloadable(false);
		
		// TOKAI管理者以外の場合、問い合わせ情報の判定を行う。
		if (!userContext.isInhouse()) {
			if (!toiawaseDao.isOwn(model.getToiawaseNo(), userContext.getRefKokyakuId())) {
				// 問い合わせ情報チェックNGの場合、ダウンロードエラー
				return model;
			}
		}
		
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// 問い合わせ情報が取得できない場合、ダウンロードエラー
			return model;
		}
		
		model.setDownloadable(true);
		model.setToiawase(toiawase);
		
		return model;
	}
}