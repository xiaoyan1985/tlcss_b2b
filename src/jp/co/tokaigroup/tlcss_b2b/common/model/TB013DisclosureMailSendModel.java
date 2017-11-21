package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKanri;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKLife;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.si.fw.mail.VelocityWrapper;
import jp.co.tokaigroup.tlcss_b2b.dto.TB013DisclosureMailSendDto;

import org.apache.commons.lang.StringUtils;

/**
 * TORES���J���[�����M��ʃ��f���B
 *
 * @author k003856
 * @version 5.0 2015/09/08
 * @version 5.1 2016/07/14 H.Hirai ����������Ή�
 */
public class TB013DisclosureMailSendModel extends TB000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "TORES���J���[�����M";

	/** �{�^�����F���[�����M */
	public static final String BUTTON_NM_MAIL_SEND = "���[�����M";

	/** �����敪 1:�₢���킹�o�^ */
	public static final int SHORI_KBN_TOIAWASE_ENTRY = 1;
	/** �����敪 2:�₢���킹����o�^ */
	public static final int SHORI_KBN_TOIAWASE_RIREKI_ENTRY = 2;
	/** �����敪 3:�˗��o�^ */
	public static final int SHORI_KBN_IRAI_ENTRY = 3;

	/** �₢���킹�m�n */
	private String toiawaseNo;
	/** �₢���킹�����m�n */
	private BigDecimal toiawaseRirekiNo;
	/** �₢���킹�X�V�� */
	private Timestamp toiawaseUpdDt;
	/** �����敪 */
	private BigDecimal shoriKbn;
	/** ������ڋq�h�c */
	private String seikyusakiKokyakuId;
	/** �Ή��񍐃��[���A�h���X�i�J���}��؂�j */
	private String taioMailAddress;
	/** ��Ђh�c */
	private String kaishaId;

	/** �ڋq��� */
	private RcpMKokyaku kokyaku;
	/** �₢���킹��� */
	private RcpTToiawase toiawase;
	/** �ڋq�_�񃉃C�t�T�|�[�g��� */
	private RcpMKokyakuKLife kokyakuKLife;
	/** ��Џ�� */
	private TbMKaisha kaisha;
	/** Velocity���b�p�[�N���X */
	private VelocityWrapper wrapper;
	/** �������Ăяo���t���O */
	private boolean isInitFlg;

	/** ���� */
	private String subject;
	/** ���M�����[���A�h���X */
	private String senderMailAddress;
	/** BCC���[���A�h���X */
	private String bccMailAddress;
	/** ���[���{�� */
	private String mailText;

	/** �₢���킹������� */
	private RcpTToiawaseRireki toiawaseRirekiInfo;

	/** TORES���J���[�����M��ʂc�s�n���X�g */
	private List<TB013DisclosureMailSendDto> disclosureMailSendList;
	/** TORES���J���[�����M��ʂc�s�n���X�g�T�C�Y */
	private int disclosureMailSendListSize;
	/** ���M�惊�X�g�I���ڋq�h�c */
	private String selectKokyakuId;
	/** �ڋq�t���Ǘ���Ѓ}�X�^���X�g */
	private List<RcpMKokyakuFKanri> kokyakuFKanriList;

	/**
	 * �₢���킹�m�n���擾���܂��B
	 *
	 * @return �₢���킹�m�n
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}
	/**
	 * �₢���킹�m�n��ݒ肵�܂��B
	 *
	 * @param toiawaseNo �₢���킹�m�n
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * �₢���킹�����m�n���擾���܂��B
	 *
	 * @return �₢���킹�����m�n
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}
	/**
	 * �₢���킹�����m�n��ݒ肵�܂��B
	 *
	 * @param toiawaseRirekiNo �₢���킹�����m�n
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
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
	 * �����敪���擾���܂��B
	 *
	 * @return �����敪
	 */
	public BigDecimal getShoriKbn() {
		return shoriKbn;
	}
	/**
	 * �����敪��ݒ肵�܂��B
	 *
	 * @param shoriKbn �����敪
	 */
	public void setShoriKbn(BigDecimal shoriKbn) {
		this.shoriKbn = shoriKbn;
	}

	/**
	 * ������ڋq�h�c���擾���܂��B
	 *
	 * @return ������ڋq�h�c
	 */
	public String getSeikyusakiKokyakuId() {
		return seikyusakiKokyakuId;
	}
	/**
	 * ������ڋq�h�c��ݒ肵�܂��B
	 *
	 * @param seikyusakiKokyakuId ������ڋq�h�c
	 */
	public void setSeikyusakiKokyakuId(String seikyusakiKokyakuId) {
		this.seikyusakiKokyakuId = seikyusakiKokyakuId;
	}

	/**
	 * ��Ђh�c���擾���܂��B
	 *
	 * @return ��Ђh�c
	 */
	public String getKaishaId() {
		return kaishaId;
	}
	/**
	 * ��Ђh�c��ݒ肵�܂��B
	 *
	 * @param kaishaId ��Ђh�c
	 */
	public void setKaishaId(String kaishaId) {
		this.kaishaId = kaishaId;
	}

	/**
	 * �Ή��񍐃��[���A�h���X�i�J���}��؂�j���擾���܂��B
	 *
	 * @return �Ή��񍐃��[���A�h���X�i�J���}��؂�j
	 */
	public String getTaioMailAddress() {
		return taioMailAddress;
	}
	/**
	 * �Ή��񍐃��[���A�h���X�i�J���}��؂�j��ݒ肵�܂��B
	 *
	 * @param taioMailAddress �Ή��񍐃��[���A�h���X�i�J���}��؂�j
	 */
	public void setTaioMailAddress(String taioMailAddress) {
		this.taioMailAddress = taioMailAddress;
	}

	/**
	 * �ڋq�����擾���܂��B
	 *
	 * @return �ڋq���
	 */
	public RcpMKokyaku getKokyaku() {
		return kokyaku;
	}
	/**
	 * �ڋq����ݒ肵�܂��B
	 *
	 * @param kokyaku �ڋq���
	 */
	public void setKokyaku(RcpMKokyaku kokyaku) {
		this.kokyaku = kokyaku;
	}

	/**
	 * �₢���킹�����擾���܂��B
	 *
	 * @return �₢���킹���
	 */
	public RcpTToiawase getToiawase() {
		return toiawase;
	}
	/**
	 * �₢���킹����ݒ肵�܂��B
	 *
	 * @param toiawase �₢���킹���
	 */
	public void setToiawase(RcpTToiawase toiawase) {
		this.toiawase = toiawase;
	}

	/**
	 * �ڋq�_�񃉃C�t�T�|�[�g�����擾���܂��B
	 *
	 * @return �ڋq�_�񃉃C�t�T�|�[�g���
	 */
	public RcpMKokyakuKLife getKokyakuKLife() {
		return kokyakuKLife;
	}
	/**
	 * �ڋq�_�񃉃C�t�T�|�[�g����ݒ肵�܂��B
	 *
	 * @param kokyakuKLife �ڋq�_�񃉃C�t�T�|�[�g���
	 */
	public void setKokyakuKLife(RcpMKokyakuKLife kokyakuKLife) {
		this.kokyakuKLife = kokyakuKLife;
	}

	/**
	 * ��Џ����擾���܂��B
	 *
	 * @return ��Џ��
	 */
	public TbMKaisha getKaisha() {
		return kaisha;
	}
	/**
	 * ��Џ���ݒ肵�܂��B
	 *
	 * @param kaisha ��Џ��
	 */
	public void setKaisha(TbMKaisha kaisha) {
		this.kaisha = kaisha;
	}

	/**
	 * Velocity���b�p�[�N���X���擾���܂��B
	 *
	 * @return Velocity���b�p�[�N���X
	 */
	public VelocityWrapper getWrapper() {
		return wrapper;
	}
	/**
	 * Velocity���b�p�[�N���X��ݒ肵�܂��B
	 *
	 * @param wrapper Velocity���b�p�[�N���X
	 */
	public void setWrapper(VelocityWrapper wrapper) {
		this.wrapper = wrapper;
	}

	/**
	 * �������Ăяo���t���O���擾���܂��B
	 *
	 * @return �������Ăяo���t���O
	 */
	public boolean isInitFlg() {
		return isInitFlg;
	}
	/**
	 * �������Ăяo���t���O��ݒ肵�܂��B
	 *
	 * @param isInitFlg �������Ăяo���t���O
	 */
	public void setInitFlg(boolean isInitFlg) {
		this.isInitFlg = isInitFlg;
	}

	/**
	 * �������擾���܂��B
	 *
	 * @return ����
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * ������ݒ肵�܂��B
	 *
	 * @param subject ����
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * ���M�����[���A�h���X���擾���܂��B
	 *
	 * @return ���M�����[���A�h���X
	 */
	public String getSenderMailAddress() {
		return senderMailAddress;
	}
	/**
	 * ���M�����[���A�h���X��ݒ肵�܂��B
	 *
	 * @param senderMailAddress ���M�����[���A�h���X
	 */
	public void setSenderMailAddress(String senderMailAddress) {
		this.senderMailAddress = senderMailAddress;
	}

	/**
	 * BCC���[���A�h���X���擾���܂��B
	 *
	 * @return BCC���[���A�h���X
	 */
	public String getBccMailAddress() {
		return bccMailAddress;
	}
	/**
	 * BCC���[���A�h���X��ݒ肵�܂��B
	 *
	 * @param bccMailAddress BCC���[���A�h���X
	 */
	public void setBccMailAddress(String bccMailAddress) {
		this.bccMailAddress = bccMailAddress;
	}

	/**
	 * ���[���{�����擾���܂��B
	 *
	 * @return ���[���{��
	 */
	public String getMailText() {
		return mailText;
	}
	/**
	 * ���[���{����ݒ肵�܂��B
	 *
	 * @param mailText ���[���{��
	 */
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}

	/**
	 * �₢���킹���������擾���܂��B
	 *
	 * @return �₢���킹�������
	 */
	public RcpTToiawaseRireki getToiawaseRirekiInfo() {
		return toiawaseRirekiInfo;
	}
	/**
	 * �₢���킹��������ݒ肵�܂��B
	 *
	 * @param toiawaseRirekiInfo �₢���킹�������
	 */
	public void setToiawaseRirekiInfo(RcpTToiawaseRireki toiawaseRirekiInfo) {
		this.toiawaseRirekiInfo = toiawaseRirekiInfo;
	}

	/**
	 * TORES���J���[�����M��ʂc�s�n���X�g���擾���܂��B
	 * 
	 * @return TORES���J���[�����M��ʂc�s�n���X�g
	 */
	public List<TB013DisclosureMailSendDto> getDisclosureMailSendList() {
		return disclosureMailSendList;
	}

	/**
	 * TORES���J���[�����M��ʂc�s�n���X�g��ݒ肵�܂��B
	 * 
	 * @param disclosureMailSendList TORES���J���[�����M��ʂc�s�n���X�g
	 */
	public void setDisclosureMailSendList(
			List<TB013DisclosureMailSendDto> disclosureMailSendList) {
		this.disclosureMailSendList = disclosureMailSendList;
	}

	/**
	 * TORES���J���[�����M��ʂc�s�n���X�g�T�C�Y���擾���܂��B
	 * 
	 * @return TORES���J���[�����M��ʂc�s�n���X�g�T�C�Y
	 */
	public int getDisclosureMailSendListSize() {
		return disclosureMailSendListSize;
	}

	/**
	 * TORES���J���[�����M��ʂc�s�n���X�g�T�C�Y��ݒ肵�܂��B
	 * 
	 * @param disclosureMailSendListSize TORES���J���[�����M��ʂc�s�n���X�g�T�C�Y
	 */
	public void setDisclosureMailSendListSize(int disclosureMailSendListSize) {
		this.disclosureMailSendListSize = disclosureMailSendListSize;
	}

	/**
	 * ���M�惊�X�g�I���ڋq�h�c���擾���܂��B
	 * 
	 * @return ���M�惊�X�g�I���ڋq�h�c
	 */
	public String getSelectKokyakuId() {
		return selectKokyakuId;
	}

	/**
	 * ���M�惊�X�g�I���ڋq�h�c��ݒ肵�܂��B
	 * 
	 * @param selectKokyakuId ���M�惊�X�g�I���ڋq�h�c
	 */
	public void setSelectKokyakuId(String selectKokyakuId) {
		this.selectKokyakuId = selectKokyakuId;
	}

	/**
	 * �ڋq�t���Ǘ���Ѓ}�X�^���X�g���擾���܂��B
	 * 
	 * @return �ڋq�t���Ǘ���Ѓ}�X�^���X�g
	 */
	public List<RcpMKokyakuFKanri> getKokyakuFKanriList() {
		return kokyakuFKanriList;
	}

	/**
	 * �ڋq�t���Ǘ���Ѓ}�X�^���X�g��ݒ肵�܂��B
	 * 
	 * @param kokyakuFKanriList �ڋq�t���Ǘ���Ѓ}�X�^���X�g
	 */
	public void setKokyakuFKanriList(List<RcpMKokyakuFKanri> kokyakuFKanriList) {
		this.kokyakuFKanriList = kokyakuFKanriList;
	}

	/**
	 * �₢���킹�o�^��ʂ���̑J�ڂ��𔻒肵�܂��B
	 *
	 * @return true�F�₢���킹�o�^��ʂ���̑J�ځAfalse�F����ȊO
	 */
	public boolean isFromToiawaseEntry() {
		if (this.shoriKbn == null) {
			return false;
		}

		return SHORI_KBN_TOIAWASE_ENTRY == this.shoriKbn.intValue();
	}

	/**
	 * �₢���킹�o�^��ʂ���̑J�ڂ��𔻒肵�܂��B
	 *
	 * @return true�F�₢���킹�o�^��ʂ���̑J�ځAfalse�F����ȊO
	 */
	public boolean isFromToiawaseRirekiEntry() {
		if (this.shoriKbn == null) {
			return false;
		}

		return SHORI_KBN_TOIAWASE_RIREKI_ENTRY == this.shoriKbn.intValue();
	}

	/**
	 * �˗��o�^��ʂ���̑J�ڂ��𔻒肵�܂��B
	 *
	 * @return true�F�˗��o�^��ʂ���̑J�ځAfalse�F����ȊO
	 */
	public boolean isFromIraiEntry() {
		if (this.shoriKbn == null) {
			return false;
		}

		return SHORI_KBN_IRAI_ENTRY == this.shoriKbn.intValue();
	}

	/**
	 * �Ή��񍐃��[���A�h���X���J���}�ŕ������A���X�g�ɂĕԋp���܂��B
	 *
	 * @return �Ή��񍐃��[���A�h���X���X�g
	 */
	public List<String> getTaioMailAddressList() {
		if (StringUtils.isBlank(this.taioMailAddress)) {
			return new ArrayList<String>();
		}

		return Arrays.asList(this.taioMailAddress.split(","));
	}

	/**
	 * ���M�惊�X�g��\�����邩�𔻒肵�܂��B
	 * 
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isSendMailDisplay() {
		if (this.disclosureMailSendList != null
				&& !this.disclosureMailSendList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
