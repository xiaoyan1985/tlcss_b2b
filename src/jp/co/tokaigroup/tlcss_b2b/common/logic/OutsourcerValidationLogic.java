package jp.co.tokaigroup.tlcss_b2b.common.logic;

/**
 * �ϑ���Њ֘A�`�F�b�N���W�b�N
 *
 * @author k003316
 * @version 1.0 2015/07/28
 */
public interface OutsourcerValidationLogic {
	/**
	 * �ϑ���ЎQ�ƌڋq�}�X�^�ɓo�^�ς̌ڋq�h�c�̊֘A�ڋq�h�c�ł��邱�Ƃ��`�F�b�N����B
	 * 
	 * @param kaishaId ��Ђh�c
	 * @param kokyakuId �ڋq�h�c
	 * @return true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean isValid(String kaishaId, String kokyakuId);
}
