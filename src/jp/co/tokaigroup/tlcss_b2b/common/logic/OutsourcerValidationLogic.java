package jp.co.tokaigroup.tlcss_b2b.common.logic;

/**
 * 委託会社関連チェックロジック
 *
 * @author k003316
 * @version 1.0 2015/07/28
 */
public interface OutsourcerValidationLogic {
	/**
	 * 委託会社参照顧客マスタに登録済の顧客ＩＤの関連顧客ＩＤであることをチェックする。
	 * 
	 * @param kaishaId 会社ＩＤ
	 * @param kokyakuId 顧客ＩＤ
	 * @return true:チェックOK、false:チェックNG
	 */
	public boolean isValid(String kaishaId, String kokyakuId);
}
