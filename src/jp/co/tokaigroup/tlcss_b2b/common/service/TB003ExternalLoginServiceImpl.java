package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.List;

import jp.co.tokaigroup.reception.dao.TbMExtUrlDao;
import jp.co.tokaigroup.reception.entity.TbMExtUrl;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB003ExternalLoginModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 外部ログインサービス実装クラス。
 *
 * @author k002849
 * @version 1.0 2014/04/25
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB003ExternalLoginServiceImpl extends TLCSSB2BBaseService implements TB003ExternalLoginService {
	/** 外部サイトシステム 外部ログイン可能ＵＲＬマスタDAO */
	@Autowired
	private TbMExtUrlDao extUrlDao;
	/**
	 * パラメータチェック処理を実行します。
	 *
	 * @param model 外部ログイン画面モデル
	 */
	public void validateParameter(TB003ExternalLoginModel model) {
		// パラメータチェック
		if (StringUtils.isBlank(model.getActionURL())) {
			// 遷移先URLが取得できない場合
			throw new ApplicationException("遷移先URL不正：null");
		}

		// 遷移先URLの妥当性チェック
		List<TbMExtUrl> extUrlList = extUrlDao.selectAll();
		if (extUrlList == null || extUrlList.isEmpty()) {
			// 外部ログイン可能ＵＲＬマスタが０件の場合は、エラー画面に遷移
			throw new ApplicationException("外部ログイン可能ＵＲＬマスタに値が設定されていません。");
		}

		// パラメータの遷移先URLが含まれているかチェック
		boolean isExtUrlExists = false;
		for (TbMExtUrl extUrl : extUrlList) {
			if (model.getActionURL().equals(extUrl.getActionUrl())) {
				// 存在していた場合、チェックOKとする
				isExtUrlExists = true;
				break;
			}
		}

		if (!isExtUrlExists) {
			// パラメータの遷移先URLが含まれていなかった場合は、エラー画面に遷移
			throw new ApplicationException("遷移先URL不正：パラメータの遷移先URL");
		}
	}
}
