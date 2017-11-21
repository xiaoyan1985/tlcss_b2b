package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.math.BigDecimal;

import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

import org.apache.commons.lang.StringUtils;

/**
 * ��ƕ񍐏�������f���B
 *
 * @author k002849
 * @version 4.0 2014/07/22
 */
public class TB014WorkReportModel extends TB000CommonModel {
	/** �₢���킹NO */
	private String toiawaseNo;
	/** �₢���킹����NO */
	private BigDecimal toiawaseRirekiNo;
	/** �ڋqID */
	private String kokyakuId;
	/** �Ǝ҃R�[�h */
	private String gyoshaCd;

	/** ���[�U�[�G�[�W�F���g */
	private String userAgent;

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
	 * �₢���킹����NO���擾���܂��B
	 *
	 * @return �₢���킹����NO
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}
	/**
	 * �₢���킹����NO��ݒ肵�܂��B
	 *
	 * @param toiawaseRirekiNo �₢���킹����NO
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
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
	 * �Ǝ҃R�[�h���擾���܂��B
	 *
	 * @return �Ǝ҃R�[�h
	 */
	public String getGyoshaCd() {
		return gyoshaCd;
	}
	/**
	 * �Ǝ҃R�[�h��ݒ肵�܂��B
	 *
	 * @param gyoshaCd �Ǝ҃R�[�h
	 */
	public void setGyoshaCd(String gyoshaCd) {
		this.gyoshaCd = gyoshaCd;
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

		return getUserAgent().indexOf(Constants.USER_AGENT_KEY_IPAD) != -1;
	}
}
