package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.toiawase.model.RC030ToiawaseCommonModel;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;

/**
 * �₢���킹�����ړ���ʃ��f���B
 *
 * @author ���t
 *
 */
public class TB026InquiryHistoryMoveModel extends RC030ToiawaseCommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "�₢���킹�����ړ�";

	/** �{�^���� �ړ� */
	public static final String BUTTON_NM_MOVE = "�ړ�";

	/** �₢���킹�������� */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();

	/** �ύX�O�₢���킹��� */
	private RcpTToiawase oldToiawaseInfo;
	/** �ύX��₢���킹��� */
	private RcpTToiawase newToiawaseInfo;

	/** �ύX�O�ڋq��� */
	private RcpMKokyaku oldKokyakuInfo;
	/** �ύX��ڋq��� */
	private RcpMKokyaku newKokyakuInfo;

	/** �ύX�OID�����ڋq��� */
	private RcpTKokyakuWithNoId oldKokyakuInfoWithoutId;
	/** �ύX��ID�����ڋq��� */
	private RcpTKokyakuWithNoId newKokyakuInfoWithoutId;

	/** ���������G���[�t���O */
	private boolean initError;

	/** �o�^����NO���X�g */
	private List<BigDecimal> entryRirekiNoList;

	/** �₢���킹�X�V�� */
	private Timestamp toiawaseUpdDt;

	/** �J�ڌ���ʋ敪 */
	private String dispKbn;

	/** �₢���킹�o�^��ʂ̃E�B���h�E�� */
	private String toiawaseWindowName;

	// �p�����[�^
	/** �ύX��₢���킹NO */
	private String newToiawaseNo;

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
	 * �ύX�O�₢���킹�����擾���܂��B
	 *
	 * @return �ύX�O�₢���킹���
	 */
	public RcpTToiawase getOldToiawaseInfo() {
		return oldToiawaseInfo;
	}
	/**
	 * �ύX�O�₢���킹����ݒ肵�܂��B
	 *
	 * @param oldToiawaseInfo �ύX�O�₢���킹���
	 */
	public void setOldToiawaseInfo(RcpTToiawase oldToiawaseInfo) {
		this.oldToiawaseInfo = oldToiawaseInfo;
	}

	/**
	 * �ύX��₢���킹�����擾���܂��B
	 *
	 * @return �ύX��₢���킹���
	 */
	public RcpTToiawase getNewToiawaseInfo() {
		return newToiawaseInfo;
	}
	/**
	 * �ύX��₢���킹����ݒ肵�܂��B
	 *
	 * @param newToiawaseInfo �ύX��₢���킹���
	 */
	public void setNewToiawaseInfo(RcpTToiawase newToiawaseInfo) {
		this.newToiawaseInfo = newToiawaseInfo;
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
	 * �ύX��ڋq�����擾���܂��B
	 *
	 * @return �ύX��ڋq���
	 */
	public RcpMKokyaku getNewKokyakuInfo() {
		return newKokyakuInfo;
	}
	/**
	 * �ύX��ڋq����ݒ肵�܂��B
	 *
	 * @param newKokyakuInfo �ύX��ڋq���
	 */
	public void setNewKokyakuInfo(RcpMKokyaku newKokyakuInfo) {
		this.newKokyakuInfo = newKokyakuInfo;
	}

	/**
	 * �ύX�OID�����ڋq�����擾���܂��B
	 *
	 * @return �ύX�OID�����ڋq���
	 */
	public RcpTKokyakuWithNoId getOldKokyakuInfoWithoutId() {
		return oldKokyakuInfoWithoutId;
	}
	/**
	 * �ύX�OID�����ڋq����ݒ肵�܂��B
	 *
	 * @param oldKokyakuInfoWithoutId �ύX�OID�����ڋq���
	 */
	public void setOldKokyakuInfoWithoutId(RcpTKokyakuWithNoId oldKokyakuInfoWithoutId) {
		this.oldKokyakuInfoWithoutId = oldKokyakuInfoWithoutId;
	}

	/**
	 * �ύX��ID�����ڋq�����擾���܂��B
	 *
	 * @return �ύX��ID�����ڋq���
	 */
	public RcpTKokyakuWithNoId getNewKokyakuInfoWithoutId() {
		return newKokyakuInfoWithoutId;
	}
	/**
	 * �ύX��ID�����ڋq����ݒ肵�܂��B
	 *
	 * @param newKokyakuInfoWithoutId �ύX��ID�����ڋq���
	 */
	public void setNewKokyakuInfoWithoutId(RcpTKokyakuWithNoId newKokyakuInfoWithoutId) {
		this.newKokyakuInfoWithoutId = newKokyakuInfoWithoutId;
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
	 * �o�^����NO���X�g���擾���܂��B
	 *
	 * @return �o�^����NO���X�g
	 */
	public List<BigDecimal> getEntryRirekiNoList() {
		return entryRirekiNoList;
	}
	/**
	 * �o�^����NO���X�g��ݒ肵�܂��B
	 *
	 * @param entryRirekiNoList �o�^����NO���X�g
	 */
	public void setEntryRirekiNoList(List<BigDecimal> entryRirekiNoList) {
		this.entryRirekiNoList = entryRirekiNoList;
	}

	/**
	 * �ύX��₢���킹NO���擾���܂��B
	 *
	 * @return �ύX��₢���킹NO
	 */
	public String getNewToiawaseNo() {
		return newToiawaseNo;
	}
	/**
	 * �ύX��₢���킹NO��ݒ肵�܂��B
	 *
	 * @param newToiawaseNo �ύX��₢���킹NO
	 */
	public void setNewToiawaseNo(String newToiawaseNo) {
		this.newToiawaseNo = newToiawaseNo;
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
	 * �ύX�O���ID�����ڋq��񂩂𔻒肵�܂��B
	 *
	 * @return true:ID�����ڋq���Afalse:����ȊO
	 */
	public boolean isOldKokyakuWithoutId() {
		if (this.oldToiawaseInfo == null) {
			return false;
		}

		return StringUtils.isBlank(this.oldToiawaseInfo.getKokyakuId());
	}

	/**
	 * �ύX����ID�����ڋq��񂩂𔻒肵�܂��B
	 *
	 * @return true:ID�����ڋq���Afalse:����ȊO
	 */
	public boolean isNewKokyakuWithoutId() {
		if (this.newToiawaseInfo == null) {
			return false;
		}

		return StringUtils.isBlank(this.newToiawaseInfo.getKokyakuId());
	}
}
