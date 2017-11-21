package jp.co.tokaigroup.tlcss_b2b.common.logic;

import jp.co.tokaigroup.reception.dao.RcpMKokyakuKanrenDao;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 委託会社関連チェックロジック
 *
 * @author k003316
 * @version 1.0 2015/07/28
 */
@Service
public class OutsourcerValidationLogicImpl implements OutsourcerValidationLogic {

	/** リセプション顧客関連付けマスタDAO */
	@Autowired
	private RcpMKokyakuKanrenDao kokyakuKanrenDao;

	/**
	 * 委託会社参照顧客マスタに登録済の顧客ＩＤの関連顧客ＩＤであることをチェックする。
	 * 
	 * @param kaishaId 会社ＩＤ
	 * @param kokyakuId 顧客ＩＤ
	 * @return true:チェックOK、false:チェックNG
	 */
	public boolean isValid(String kaishaId, String kokyakuId) {
		
		// パラメータチェック
		if (StringUtils.isBlank(kaishaId)) {
			throw new ApplicationException("会社ＩＤ不正：パラメータの会社ＩＤ");
		}
		
		if (StringUtils.isBlank(kokyakuId)) {
			throw new ApplicationException("顧客ＩＤ不正：パラメータの顧客ＩＤ");
		}
		
		// 関連付けチェック
		return kokyakuKanrenDao.isValidRefKanren(kaishaId, kokyakuId);
	}
}
