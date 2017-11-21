package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.dto.RC020KokyakuShosaiKeiyakuShosaiDto;
import jp.co.tokaigroup.reception.entity.RcpMKeiyakuTel;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKBill;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKLife;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKRcp;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKeiyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;

/**
 * �ڋq�ڍ׌_��ڍ׏���ʃ��f���B
 *
 * @author k003858
 * @version 1.0
 * @version 1.1 J.Matsuba 2016/03/24 �r���Ǘ���ʕ\���ɂ��ǉ�
 * @version 1.2 2016/07/14 C.Kobayashi �r���Ǘ����擾�̃��W�b�N���Ή�
 */
public class TB047ContractDetailModel extends TB040CustomerCommonInfoModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "�ڋq�ڍ׌_��ڍ׏��";
	/** �Ǘ���U�փt���O - ������ڋq�̊Ǘ���ŐU�� */
	public static final String FURIKAE_KBN_KOKYAKU = "1";
	/** �Ǘ���U�փt���O - �ʊǗ���ŐU�� */
	public static final String FURIKAE_KBN_KOBETSU = "2";

	/** �_��ڋqID */
	private String keiyakuKokyakuId;
	/** �ڋq�_���� */
	private RcpMKokyakuKeiyaku keiyakuInfo;
	/** �ڋq�_�񃊃Z�v�V������� */
	private RcpMKokyakuKRcp rcpInfo;
	/** �ڋq�_�񃉃C�t�T�|�[�g��� */
	private RcpMKokyakuKLife lifeInfo;
	/** �ڋq�_��r���Ǘ���� */
	private RcpMKokyakuKBill billInfo;
	/** �T�[�r�X�}�X�^��� */
	private RcpMService serviceInfo;
	/** �_��d�b�ԍ��}�X�^��� */
	private RcpMKeiyakuTel keiyakuTelInfo;
	/** ���[�U�R���e�L�X�g */
	private TLCSSB2BUserContext context;

	/** �r���Ǘ����擾�c�s�n */
	private RC020KokyakuShosaiKeiyakuShosaiDto keiyakuShosaiDto;

	// ��ʃp�����[�^
	/** �_��m�n */
	private BigDecimal keiyakuNo;

	
	/** �I�������N�\���t���O */
	private String viewSelectLinkFlg;
	
	/** �ڋqIDname������ */
	private String kokyakuIdResultNm;

	/** �ڋq��Ж�name������ */
	private String kokyakuKaishaNmResultNm;

	/** �ڋq��name������ */
	private String kokyakuNmResultNm;

	/** �ڋq�Z��name������ */
	private String kokyakuJushoResultNm;

	/** �d�b�ԍ�name������ */
	private String kokyakuTelResultNm;

	/** FAX�ԍ�name������ */
	private String kokyakuFaxResultNm;

	/** hidden�o�͏��O���� */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/**
	 * �_��ڋqID���擾���܂��B
	 * @return �_��ڋqID
	 */
	public String getKeiyakuKokyakuId() {
		return keiyakuKokyakuId;
	}

	/**
	 * �_��ڋqID��ݒ肵�܂��B
	 * @param keiyakuKokyakuId �_��ڋqID
	 */
	public void setKeiyakuKokyakuId(String keiyakuKokyakuId) {
		this.keiyakuKokyakuId = keiyakuKokyakuId;
	}
	/**
	 * �ڋq�_������擾���܂��B
	 *
	 * @return �ڋq�_����
	 */
	public RcpMKokyakuKeiyaku getKeiyakuInfo() {
		return keiyakuInfo;
	}
	/**
	 * �ڋq�_�����ݒ肵�܂��B
	 *
	 * @param keiyakuInfo �ڋq�_����
	 */
	public void setKeiyakuInfo(RcpMKokyakuKeiyaku keiyakuInfo) {
		this.keiyakuInfo = keiyakuInfo;
	}

	/**
	 * �ڋq�_�񃊃Z�v�V���������擾���܂��B
	 *
	 * @return �ڋq�_�񃊃Z�v�V�������
	 */
	public RcpMKokyakuKRcp getRcpInfo() {
		return rcpInfo;
	}
	/**
	 * �ڋq�_�񃊃Z�v�V��������ݒ肵�܂��B
	 *
	 * @param rcpInfo �ڋq�_�񃊃Z�v�V�������
	 */
	public void setRcpInfo(RcpMKokyakuKRcp rcpInfo) {
		this.rcpInfo = rcpInfo;
	}

	/**
	 * �ڋq�_�񃉃C�t�T�|�[�g�����擾���܂��B
	 *
	 * @return �ڋq�_�񃉃C�t�T�|�[�g���
	 */
	public RcpMKokyakuKLife getLifeInfo() {
		return lifeInfo;
	}
	/**
	 * �ڋq�_�񃉃C�t�T�|�[�g����ݒ肵�܂��B
	 *
	 * @param lifeInfo �ڋq�_�񃉃C�t�T�|�[�g���
	 */
	public void setLifeInfo(RcpMKokyakuKLife lifeInfo) {
		this.lifeInfo = lifeInfo;
	}

	/**
	 * �ڋq�_��r���Ǘ������擾���܂��B
	 *
	 * @return �ڋq�_��r���Ǘ����
	 */
	public RcpMKokyakuKBill getBillInfo() {
		return billInfo;
	}
	/**
	 * �ڋq�_��r���Ǘ�����ݒ肵�܂��B
	 *
	 * @param billInfo �ڋq�_��r���Ǘ����
	 */
	public void setBillInfo(RcpMKokyakuKBill billInfo) {
		this.billInfo = billInfo;
	}

	/**
	 * �r���Ǘ����擾�c�s�n���擾���܂��B
	 * @return �r���Ǘ����擾�c�s�n
	 */
	public RC020KokyakuShosaiKeiyakuShosaiDto getKeiyakuShosaiDto() {
		return keiyakuShosaiDto;
	}
	/**
	 * �r���Ǘ����擾�c�s�n��ݒ肵�܂��B
	 * @param keiyakuShosaiDto �r���Ǘ����擾�c�s�n
	 */
	public void setKeiyakuShosaiDto(RC020KokyakuShosaiKeiyakuShosaiDto keiyakuShosaiDto) {
		this.keiyakuShosaiDto = keiyakuShosaiDto;
	}

	/**
	 * �_��m�n���擾���܂��B
	 *
	 * @return �_��m�n
	 */
	public BigDecimal getKeiyakuNo() {
		return keiyakuNo;
	}
	/**
	 * �_��m�n��ݒ肵�܂��B
	 *
	 * @param keiyakuNo �_��m�n
	 */
	public void setKeiyakuNo(BigDecimal keiyakuNo) {
		this.keiyakuNo = keiyakuNo;
	}

	/**
	 * ���[�U�R���e�L�X�g���擾���܂��B
	 *
	 * @return ���[�U�R���e�L�X�g
	 */
	public TLCSSB2BUserContext getContext() {
		return context;
	}
	/**
	 * ���[�U�R���e�L�X�g��ݒ肵�܂��B
	 *
	 * @param context ���[�U�R���e�L�X�g
	 */
	public void setContext(TLCSSB2BUserContext context) {
		this.context = context;
	}

	/**
	 * �T�[�r�X�}�X�^�����擾���܂��B
	 *
	 * @return �T�[�r�X�}�X�^���
	 */
	public RcpMService getServiceInfo() {
		return serviceInfo;
	}
	/**
	 * �T�[�r�X�}�X�^����ݒ肵�܂��B
	 *
	 * @param serviceInfo �T�[�r�X�}�X�^���
	 */
	public void setServiceInfo(RcpMService serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	/**
	 * �_��d�b�ԍ��}�X�^�����擾���܂��B
	 *
	 * @return �_��d�b�ԍ��}�X�^���
	 */
	public RcpMKeiyakuTel getKeiyakuTelInfo() {
		return keiyakuTelInfo;
	}
	/**
	 * �_��d�b�ԍ��}�X�^����ݒ肵�܂��B
	 *
	 * @param keiyakuTelInfo �_��d�b�ԍ��}�X�^���
	 */
	public void setKeiyakuTelInfo(RcpMKeiyakuTel keiyakuTelInfo) {
		this.keiyakuTelInfo = keiyakuTelInfo;
	}

	/**
	 * �I�������N�\���t���O���擾���܂��B
	 * @return �I�������N�\���t���O
	 */
	public String getViewSelectLinkFlg() {
		return viewSelectLinkFlg;
	}

	/**
	 * �I�������N�\���t���O��ݒ肵�܂��B
	 * @param viewSelectLinkFlg�@�I�������N�\���t���O
	 */
	public void setViewSelectLinkFlg(String viewSelectLinkFlg) {
		this.viewSelectLinkFlg = viewSelectLinkFlg;
	}

	/**
	 * @return kokyakuIdResultNm
	 */
	public String getKokyakuIdResultNm() {
		return kokyakuIdResultNm;
	}
	/**
	 * @param kokyakuIdResultNm �Z�b�g���� kokyakuIdResultNm
	 */
	public void setKokyakuIdResultNm(String kokyakuIdResultNm) {
		this.kokyakuIdResultNm = kokyakuIdResultNm;
	}

	/**
	 * @return kokyakuKaishaNmResultNm
	 */
	public String getKokyakuKaishaNmResultNm() {
		return kokyakuKaishaNmResultNm;
	}
	/**
	 * @param kokyakuKaishaNmResultNm �Z�b�g���� kokyakuKaishaNmResultNm
	 */
	public void setKokyakuKaishaNmResultNm(String kokyakuKaishaNmResultNm) {
		this.kokyakuKaishaNmResultNm = kokyakuKaishaNmResultNm;
	}

	/**
	 * @return kokyakuNmResultNm
	 */
	public String getKokyakuNmResultNm() {
		return kokyakuNmResultNm;
	}
	/**
	 * @param kokyakuNmResultNm �Z�b�g���� kokyakuNmResultNm
	 */
	public void setKokyakuNmResultNm(String kokyakuNmResultNm) {
		this.kokyakuNmResultNm = kokyakuNmResultNm;
	}

	/**
	 * @return kokyakuJushoResultNm
	 */
	public String getKokyakuJushoResultNm() {
		return kokyakuJushoResultNm;
	}
	/**
	 * @param kokyakuJushoResultNm �Z�b�g���� kokyakuJushoResultNm
	 */
	public void setKokyakuJushoResultNm(String kokyakuJushoResultNm) {
		this.kokyakuJushoResultNm = kokyakuJushoResultNm;
	}
	/**
	 * @return kokyakuTelResultNm
	 */
	public String getKokyakuTelResultNm() {
		return kokyakuTelResultNm;
	}
	/**
	 * @param kokyakuTelResultNm �Z�b�g���� kokyakuTelResultNm
	 */
	public void setKokyakuTelResultNm(String kokyakuTelResultNm) {
		this.kokyakuTelResultNm = kokyakuTelResultNm;
	}
	/**
	 * @return kokyakuFaxResultNm
	 */
	public String getKokyakuFaxResultNm() {
		return kokyakuFaxResultNm;
	}
	/**
	 * @param kokyakuFaxResultNm �Z�b�g���� kokyakuFaxResultNm
	 */
	public void setKokyakuFaxResultNm(String kokyakuFaxResultNm) {
		this.kokyakuFaxResultNm = kokyakuFaxResultNm;
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
	 * �T�[�r�X�����Z�v�V�������𔻒肵�܂��B
	 *
	 * @return true:���Z�v�V����
	 */
	public boolean isReception() {
		if (serviceInfo == null) {
			return false;
		}

		return serviceInfo.isReception();
	}

	/**
	 * �T�[�r�X�����C�t�T�|�[�g�Q�S���𔻒肵�܂��B
	 *
	 * @return true:���C�t�T�|�[�g�Q�S
	 */
	public boolean isLifeSupport24() {
		if (serviceInfo == null) {
			return false;
		}

		return serviceInfo.isLifeSupport24();
	}

	/**
	 * �T�[�r�X���r���Ǘ����𔻒肵�܂��B
	 *
	 * @return true:�r���Ǘ�
	 */
	public boolean isBuildingManagement() {
		if (serviceInfo == null) {
			return false;
		}

		return serviceInfo.isBuildingManagement();
	}

	/**
	 * WELBOX�t���O�ɕ\�����镶������擾���܂��B
	 *
	 * @return  WELBOX�t���O�ɕ\�����镶����
	 */
	public String getWelboxFlgForDisplay() {
		if (this.lifeInfo == null) {
			return "";
		}

		StringBuilder builder = new StringBuilder("");

		if (RcpMKokyakuKLife.WELBOX_FLG_ON.equals(this.lifeInfo.getWelboxFlg())) {
			builder.append("WELBOX�i8044�{����ԍ��j�L��");
		}

		return builder.toString();
	}

	/**
	 * �������@�̕\������ʂɕ\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�\������
	 */
	public boolean hasAuthority() {
		if (this.context == null) {
			return false;
		}
		if (this.context.isAdministrativeInhouse() || context.isOutsourcerSv()) {
			return true;
		} else {
			return false;
		}
	}
	
}
