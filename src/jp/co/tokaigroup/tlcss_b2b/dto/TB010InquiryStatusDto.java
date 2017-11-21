package jp.co.tokaigroup.tlcss_b2b.dto;

import java.math.BigDecimal;
import java.util.List;

import jp.co.tokaigroup.reception.dto.RC014KeiyakuListDto;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;

/**
 * ���j���[��� �₢���킹�󋵂c�s�n�B
 *
 * @author k002849
 * @version 4.0 2014/06/05
 */
public class TB010InquiryStatusDto extends RC014KeiyakuListDto {
	/** �Ή������� */
	private BigDecimal inCompleteCount;
	/** �Ή��ό��� */
	private BigDecimal completedCount;

	/** �_�� */
	private BigDecimal keiyakuCount;
	/** �ː� */
	private BigDecimal kosuCount;

	/** �ڋq�_��}�X�^���X�g */
	private List<String> kokyakuKeiyakuList;

	/** �Q�ƌڋq�}�X�^ */
	private TbMRefKokyaku refKokyaku;

	/**
	 * �Ή����������擾���܂��B
	 *
	 * @return �Ή�������
	 */
	public BigDecimal getInCompleteCount() {
		return inCompleteCount;
	}
	/**
	 * �Ή���������ݒ肵�܂��B
	 *
	 * @param inCompleteCount �Ή�������
	 */
	public void setInCompleteCount(BigDecimal inCompleteCount) {
		this.inCompleteCount = inCompleteCount;
	}

	/**
	 * �Ή��ό������擾���܂��B
	 *
	 * @return complatedCount
	 */
	public BigDecimal getCompletedCount() {
		return completedCount;
	}
	/**
	 * �Ή��ό�����ݒ肵�܂��B
	 *
	 * @param complatedCount �Ή��ό���
	 */
	public void setCompletedCount(BigDecimal completedCount) {
		this.completedCount = completedCount;
	}

	/**
	 * �S�Ή��������擾���܂��B
	 *
	 * @return �S�Ή�����
	 */
	public BigDecimal getAllCount() {
		return this.inCompleteCount.add(this.completedCount);
	}

	/**
	 * �_�񐔂��擾���܂��B
	 *
	 * @return �_��
	 */
	public BigDecimal getKeiyakuCount() {
		return keiyakuCount;
	}
	/**
	 * �_�񐔂�ݒ肵�܂��B
	 *
	 * @param keiyakuCount �_��
	 */
	public void setKeiyakuCount(BigDecimal keiyakuCount) {
		this.keiyakuCount = keiyakuCount;
	}

	/**
	 * �ː����擾���܂��B
	 *
	 * @return �ː�
	 */
	public BigDecimal getKosuCount() {
		return kosuCount;
	}
	/**
	 * �ː���ݒ肵�܂��B
	 *
	 * @param kosuCount �ː�
	 */
	public void setKosuCount(BigDecimal kosuCount) {
		this.kosuCount = kosuCount;
	}
	/**
	 * �ڋq�_��}�X�^���X�g���擾���܂��B
	 *
	 * @return kokyakuKeiyakuList
	 */
	public List<String> getKokyakuKeiyakuList() {
		return kokyakuKeiyakuList;
	}
	/**
	 * �ڋq�_��}�X�^���X�g��ݒ肵�܂��B
	 *
	 * @param kokyakuKeiyakuList �Z�b�g���� kokyakuKeiyakuList
	 */
	public void setKokyakuKeiyakuList(List<String> kokyakuKeiyakuList) {
		this.kokyakuKeiyakuList = kokyakuKeiyakuList;
	}

	/**
	 * �Q�ƌڋq�}�X�^���擾���܂��B
	 *
	 * @return refKokyaku
	 */
	public TbMRefKokyaku getRefKokyaku() {
		return refKokyaku;
	}
	/**
	 * �Q�ƌڋq�}�X�^��ݒ肵�܂��B
	 *
	 * @param refKokyaku �Z�b�g���� refKokyaku
	 */
	public void setRefKokyaku(TbMRefKokyaku refKokyaku) {
		this.refKokyaku = refKokyaku;
	}

	/**
	 * �ڋq�_��}�X�^���X�g���ꌏ�ȏ゠�邩�𔻒肵�܂��B
	 *
	 * @return TRUE:�ꌏ�ȏ㑶�� FALSE:0��
	 */
	public boolean hasKeiyaku() {
		return (kokyakuKeiyakuList != null && ! kokyakuKeiyakuList.isEmpty());
	}
}
