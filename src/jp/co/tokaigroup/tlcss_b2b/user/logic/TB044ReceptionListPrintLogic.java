package jp.co.tokaigroup.tlcss_b2b.user.logic;

import java.sql.Timestamp;
import java.util.List;

/**
 * ��t�ꗗ������W�b�N�N���X�B
 *
 * @author v140546
 *
 */
public interface TB044ReceptionListPrintLogic {

	/**
	 * PDF�o�͂��s���܂��B
	 *
	 * @param allDataList PDF�o�͂���f�[�^���X�g
	 * @return PDF�o��URL
	 */
	public String outputPdf(List<String[]> allDataList);

	/**
	 * ��t�ꗗ�o�̓f�[�^���쐬���܂��B
	 *
	 * @param targetDtFrom �Ώۊ���From
	 * @param targetDtTo �Ώۊ���To
	 * @param seikyusakiKokyakuId ������ڋq�h�c
	 * @param serviceKbn �T�[�r�X�敪
	 * @return  PDF�o�͂���f�[�^���X�g
	 */
	public List<String[]> getReceiptListDataList(Timestamp targetDtFrom, Timestamp targetDtTo, String seikyusakiKokyakuId, String serviceKbn);
}
