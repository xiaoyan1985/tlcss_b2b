package jp.co.tokaigroup.tlcss_b2b.user.logic;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;

/**

 * �ڋq��{���ڍ׃��W�b�N�N���X
 *
 * @author v140546
 * @version 1.0 2014/05/28
 */
public interface TB040CustomerCommonInfoLogic {


	/**
	 * �ڋq��{���̎擾���s���܂��B
	 *
	 * @param kokyakuId �ڋqID
	 * @return �ڋq�}�X�^
	 */
	public RcpMKokyaku getKokyakuInfo(String kokyakuId);

}
