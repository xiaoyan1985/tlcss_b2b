package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.tlcss_b2b.user.model.TB025CustomerIdChangeModel;

/**
 * �ڋq�h�c�ύX��ʃT�[�r�X�N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/19
 */
public interface TB025CustomerIdChangeService {

	/**
	 * �����\���������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB025CustomerIdChangeModel getInitInfo(TB025CustomerIdChangeModel model);

	/**
	 * �₢���킹�ڋq�X�V�������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 */
	public void updateToiawaseKokyakuInfo(TB025CustomerIdChangeModel model);
}
