package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB033RequestEntryTestModel;

public interface TB033RequestEntryTestService {

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB033RequestEntryTestModel getInitInfo(TB033RequestEntryTestModel model);


	/**
	 * �˗����擾�������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB033RequestEntryTestModel getIraiInfo(TB033RequestEntryTestModel model);

}
