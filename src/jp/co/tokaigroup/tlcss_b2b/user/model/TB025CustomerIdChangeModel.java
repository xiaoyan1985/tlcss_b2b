package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;

/**
 * �ڋq�h�c�ύX��ʃ��f���B
 *
 * @author k002785
 * @version 1.0 2015/08/19
 */
public class TB025CustomerIdChangeModel {
	
	/** ��ʖ� */
	public static final String GAMAN_NM = "�ڋq�h�c�ύX";

	/** �{�^�����F�ύX */
	public static final String BUTTON_NM_CHANGE = "�ύX";
	
	/** ��������hidden�o�͏��O���� */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};
	
	/** �₢���킹�������� */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();
	
	/** �₢���킹NO */
	private String toiawaseNo;
	
	/** �₢���킹�X�V�� */
	private Timestamp toiawaseUpdDt;
	
	/** �ύX�O�ڋqID */
	private String oldKokyakuId;
	
	/** ���͌ڋqID */
	private String newKokyakuId;
	
	/** �ύX�O�ڋq��� */
	private RcpMKokyaku oldKokyakuInfo;
	
	/** �ύX�O�ڋqID������� */
	private RcpTKokyakuWithNoId oldKokyakuInfoWithoutId;

	/** ���͌ڋq�ڋq��� */
	private RcpMKokyaku newKokyakuInfo;
	
	/** �₢���킹�o�^��ʂ̃E�B���h�E�� */
	private String toiawaseWindowName;
	
	/** �J�ڌ���ʋ敪 */
	private String dispKbn;
	
	/** �ڋqID */
	private String kokyakuId;
	
	/** ���������G���[�t���O */
	private boolean initError;
	
	/** �������b�Z�[�WID */
	private String completeMessageId;
	
	/** �������b�Z�[�W���� */
	private String completeMessageStr;
	
	/**
	 * �₢���킹�����������擾���܂��B
	 * 
	 * @return condition �₢���킹��������
	 */
	public RC031ToiawaseSearchCondition getCondition() {
		return condition;
	}

	/**
	 * �₢���킹����������ݒ肵�܂��B
	 * 
	 * @param condition �Z�b�g���� �₢���킹��������
	 */
	public void setCondition(RC031ToiawaseSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * hidden�o�͏��O���ڂ��J���}��؂�Ŏ擾���܂��B
	 *
	 * @return hidden�o�͏��O���ځi�J���}��؂�j
	 */
	public String getExcludeField() {
		return StringUtils.join(EXCLUDE_FIELD_NAME, ",");
	}

	/**
	 * �₢���킹NO���擾���܂��B
	 *
	 * @return �₢���킹NO
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * �₢���킹NO��ݒ肵�܂��B
	 *
	 * @param toiawaseNo �₢���킹NO
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * �₢���킹�X�V�����擾���܂��B
	 *
	 * @return �₢���킹�X�V��
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}

	/**
	 * �₢���킹�X�V����ݒ肵�܂��B
	 *
	 * @param toiawaseUpdDt �₢���킹�X�V��
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}

	/**
	 * �ύX�O�ڋqID���擾���܂��B
	 *
	 * @return �ύX�O�ڋqID
	 */
	public String getOldKokyakuId() {
		return oldKokyakuId;
	}

	/**
	 * �ύX�O�ڋqID��ݒ肵�܂��B
	 *
	 * @param oldKokyakuId �ύX�O�ڋqID
	 */
	public void setOldKokyakuId(String oldKokyakuId) {
		this.oldKokyakuId = oldKokyakuId;
	}

	/**
	 * ���͌ڋqID���擾���܂��B
	 *
	 * @return ���͌ڋqID
	 */
	public String getNewKokyakuId() {
		return newKokyakuId;
	}

	/**
	 * ���͌ڋqID��ݒ肵�܂��B
	 *
	 * @param newKokyakuId ���͌ڋqID
	 */
	public void setNewKokyakuId(String newKokyakuId) {
		this.newKokyakuId = newKokyakuId;
	}

	/**
	 * �ύX�O�ڋq�����擾���܂��B
	 *
	 * @return �ύX�O�ڋq���
	 */
	public RcpMKokyaku getOldKokyakuInfo() {
		return oldKokyakuInfo;
	}

	/**
	 * �ύX�O�ڋq����ݒ肵�܂��B
	 *
	 * @param oldKokyakuInfo �ύX�O�ڋq���
	 */
	public void setOldKokyakuInfo(RcpMKokyaku oldKokyakuInfo) {
		this.oldKokyakuInfo = oldKokyakuInfo;
	}

	/**
	 * �ύX�O�ڋqID���������擾���܂��B
	 *
	 * @return �ύX�O�ڋqID�������
	 */
	public RcpTKokyakuWithNoId getOldKokyakuInfoWithoutId() {
		return oldKokyakuInfoWithoutId;
	}

	/**
	 * �ύX�O�ڋqID��������ݒ肵�܂��B
	 *
	 * @param oldKokyakuInfoWithoutId �ύX�O�ڋqID�������
	 */
	public void setOldKokyakuInfoWithoutId(RcpTKokyakuWithNoId oldKokyakuInfoWithoutId) {
		this.oldKokyakuInfoWithoutId = oldKokyakuInfoWithoutId;
	}

	/**
	 * ���͌ڋq�����擾���܂��B
	 *
	 * @return ���͌ڋq���
	 */
	public RcpMKokyaku getNewKokyakuInfo() {
		return newKokyakuInfo;
	}

	/**
	 * ���͌ڋq����ݒ肵�܂��B
	 *
	 * @param newKokyakuInfo ���͌ڋq���
	 */
	public void setNewKokyakuInfo(RcpMKokyaku newKokyakuInfo) {
		this.newKokyakuInfo = newKokyakuInfo;
	}
	
	/**
	 * �₢���킹�o�^��ʂ̃E�B���h�E�����擾���܂��B
	 *
	 * @return �₢���킹�o�^��ʂ̃E�B���h�E��
	 */
	public String getToiawaseWindowName() {
		return toiawaseWindowName;
	}

	/**
	 * �₢���킹�o�^��ʂ̃E�B���h�E����ݒ肵�܂��B
	 *
	 * @param toiawaseWindowName �₢���킹�o�^��ʂ̃E�B���h�E��
	 */
	public void setToiawaseWindowName(String toiawaseWindowName) {
		this.toiawaseWindowName = toiawaseWindowName;
	}
	
	/**
	 * �J�ڌ���ʋ敪���擾���܂��B
	 *
	 * @return �J�ڌ���ʋ敪
	 */
	public String getDispKbn() {
		return dispKbn;
	}

	/**
	 * �J�ڌ���ʋ敪��ݒ肵�܂��B
	 *
	 * @param dispKbn �J�ڌ���ʋ敪
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}
	
	/**
	 * �ڋqID���擾���܂��B
	 *
	 * @return �ڋqID
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}
	/**
	 * �ڋqID��ݒ肵�܂��B
	 *
	 * @param kokyakuId �ڋqID
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}
	
	/**
	 * ���������G���[�t���O���擾���܂��B
	 *
	 * @return ���������G���[�t���O
	 */
	public boolean isInitError() {
		return initError;
	}
	/**
	 * ���������G���[�t���O��ݒ肵�܂��B
	 *
	 * @param initError ���������G���[�t���O
	 */
	public void setInitError(boolean initError) {
		this.initError = initError;
	}

	/**
	 * �������b�Z�[�WID���擾���܂��B
	 * @return �������b�Z�[�WID
	 */
	public String getCompleteMessageId() {
		return completeMessageId;
	}

	/**
	 * �������b�Z�[�WID��ݒ肵�܂��B
	 * @param completeMessageId �������b�Z�[�WID
	 */
	public void setCompleteMessageId(String completeMessageId) {
		this.completeMessageId = completeMessageId;
	}

	/**
	 * �������b�Z�[�W�������擾���܂��B
	 * @return �������b�Z�[�W����
	 */
	public String getCompleteMessageStr() {
		return completeMessageStr;
	}

	/**
	 * �������b�Z�[�W�������擾���܂��B
	 * @param completeMessageStr �������b�Z�[�W����
	 */
	public void setCompleteMessageStr(String completeMessageStr) {
		this.completeMessageStr = completeMessageStr;
	}

	/**
	 * �ύX�O�ڋq�OID�����݂��邩����
	 *
	 * @return true:���݂���
	 */
	public boolean isOldKokyakuIdExsits() {
		return StringUtils.isNotBlank(this.oldKokyakuId);
	}
}
