package jp.co.tokaigroup.tlcss_b2b.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.dao.RcpMToiawaseManualDao;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseManual;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB029InquiryKbnManualModel;

/**
 * 問い合わせ区分マニュアル一覧サービス実装クラス。
 *
 * @author H.Yamamura
 * @version 1.0 2015/11/20
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB029InquiryKbnManualServiceImpl extends TLCSSB2BBaseService
		implements TB029InquiryKbnManualService {

	/** 問い合わせ区分マニュアルマスタDAO */
	@Autowired
	private RcpMToiawaseManualDao toiawaseManualDao;

	/**
	 * ダウンロードするファイル名を取得します。
	 *
	 * @param model 問い合わせ区分マニュアル一覧画面モデル
	 * @return ファイル名
	 */
	public String getFileNm(TB029InquiryKbnManualModel model) {
		// マニュアル情報を取得
		RcpMToiawaseManual toiawaseManual = 
			toiawaseManualDao.selectByPrimaryKey(model.getToiawaseManualKey());

		// マニュアル情報が取得できない場合
		if (toiawaseManual == null) {
			// nullを返却
			return null;
		} else {
			// アップロードファイル名を返却
			return toiawaseManual.getUploadFileNm();
		}
	}
}
