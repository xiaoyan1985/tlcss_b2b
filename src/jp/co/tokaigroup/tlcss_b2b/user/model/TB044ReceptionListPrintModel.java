package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;

/**
 * ��t�ꗗ�����ʃ��f���B
 *
 * @author v140546
 * @version 1.0 2014/07/09
 */
public class TB044ReceptionListPrintModel extends TB040CustomerCommonInfoModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "��t�ꗗ���";

	/** ���[�U�[�G�[�W�F���g �����L�[�FiPad */
	private static final String USER_AGENT_KEY_IPAD = "iPad";

	/** �ڋqID */
	private String kokyakuId;

	/** �ڋq�� */
	private String kokyakuNm;

	/** �T�[�r�X�敪 */
	private String serviceKbn;

	/** �Ώۊ���(FROM) */
	private Timestamp targetDtFrom;

	/** �Ώۊ���(TO) */
	private Timestamp targetDtTo;

	/** ������ڋqID */
	private String seikyusakiKokyakuId;

	/** �쐬����PDF��URL */
	private String pdfUrl;

	/** �T�[�r�X�l���X�g */
	private List<RcpMService> serviceMEntityList;

	/** �ڋq�l */
	private RcpMKokyaku kokyakuMEntity;

	/** ���[�U�[�G�[�W�F���g */
	private String userAgent;

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
	 * �ڋq�����擾���܂��B
	 *
	 * @return �ڋq��
	 */
	public String getKokyakuNm() {
		return kokyakuNm;
	}
	/**
	 * �ڋq����ݒ肵�܂��B
	 *
	 * @param kokyakuNm �ڋq��
	 */
	public void setKokyakuNm(String kokyakuNm) {
		this.kokyakuNm = kokyakuNm;
	}

	/**
	 * �T�[�r�X�敪���擾���܂��B
	 *
	 * @return �ڋq��
	 */
	public String getServiceKbn() {
		return serviceKbn;
	}
	/**
	 * �T�[�r�X�敪��ݒ肵�܂��B
	 *
	 * @param serviceKbn �T�[�r�X�敪
	 */
	public void setServiceKbn(String serviceKbn) {
		this.serviceKbn = serviceKbn;
	}

	/**
	 * �Ώۊ���(FROM)���擾���܂��B
	 *
	 * @return �Ώۊ���(FROM)
	 */
	public Timestamp getTargetDtFrom() {
		return targetDtFrom;
	}
	/**
	 * �Ώۊ���(FROM)��ݒ肵�܂��B
	 *
	 * @param targetDtFrom �Ώۊ���(FROM)
	 */
	public void setTargetDtFrom(Timestamp targetDtFrom) {
		this.targetDtFrom = targetDtFrom;
	}

	/**
	 * �Ώۊ���(TO)���擾���܂��B
	 *
	 * @return �Ώۊ���(TO)
	 */
	public Timestamp getTargetDtTo() {
		return targetDtTo;
	}
	/**
	 * �Ώۊ���(TO)��ݒ肵�܂��B
	 *
	 * @param targetDtTo �Ώۊ���(TO)
	 */
	public void setTargetDtTo(Timestamp targetDtTo) {
		this.targetDtTo = targetDtTo;
	}

	/**
	 * ������ڋqID���擾���܂��B
	 *
	 * @return ������ڋqID
	 */
	public String getSeikyusakiKokyakuId() {
		return seikyusakiKokyakuId;
	}
	/**
	 * ������ڋqID��ݒ肵�܂��B
	 *
	 * @param seikyusakiKokyakuId ������ڋqID
	 */
	public void setSeikyusakiKokyakuId(String seikyusakiKokyakuId) {
		this.seikyusakiKokyakuId = seikyusakiKokyakuId;
	}

	/**
	 * �쐬����PDF��URL��ݒ肵�܂��B
	 *
	 * @return pdfUrl �쐬����PDF��URL
	 */
	public String getPdfUrl() {
		return pdfUrl;
	}

	/**
	 * �쐬����PDF��URL��ݒ肵�܂��B
	 *
	 * @param pdfUrl �쐬����PDF��URL
	 */
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	/**
	 * �T�[�r�X�l���X�g���擾���܂��B
	 *
	 * @return �T�[�r�X�l���X�g
	 */
	public List<RcpMService> getServiceMEntityList() {
		return serviceMEntityList;
	}
	/**
	 * �T�[�r�X�l���X�g��ݒ肵�܂��B
	 *
	 * @param serviceMEntityList �T�[�r�X�l���X�g
	 */
	public void setServiceMEntityList(List<RcpMService> serviceMEntityList) {
		this.serviceMEntityList = serviceMEntityList;
	}

	/**
	 * �ڋq�l���擾���܂��B
	 *
	 * @return �ڋq�l
	 */
	public RcpMKokyaku getKokyakuMEntity() {
		return kokyakuMEntity;
	}
	/**
	 * �ڋq�l��ݒ肵�܂��B
	 *
	 * @param kokyakuMEntity �ڋq�l
	 */
	public void setKokyakuMEntity(RcpMKokyaku kokyakuMEntity) {
		this.kokyakuMEntity = kokyakuMEntity;
	}

	/**
	 * ���[�U�[�G�[�W�F���g���擾���܂��B
	 *
	 * @return ���[�U�[�G�[�W�F���g
	 */
	public String getUserAgent() {
		return userAgent;
	}
	/**
	 * ���[�U�[�G�[�W�F���g��ݒ肵�܂��B
	 *
	 * @param userAgent ���[�U�[�G�[�W�F���g
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * iPad�Ń��O�C�����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:iPad�Ń��O�C���Afalse:����ȊO
	 */
	public boolean isIPad() {
		if (StringUtils.isBlank(getUserAgent())) {
			return false;
		}

		return getUserAgent().indexOf(USER_AGENT_KEY_IPAD) != -1;
	}

}
