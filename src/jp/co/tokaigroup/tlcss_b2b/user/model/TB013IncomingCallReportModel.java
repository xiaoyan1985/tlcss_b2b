package jp.co.tokaigroup.tlcss_b2b.user.model;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * ���d�񍐏�������f���B
 *
 * @author v140546
 * @version 1.0 2014/05/27
 * @version 1.1 2015/12/11 S.Nakano �₢���킹���A�_�E�����[�h�\�t���O�ǉ�
 */
public class TB013IncomingCallReportModel extends TB000CommonModel {
	/** �₢���킹NO **/
	private String toiawaseNo;

	/** ���[�U�[�G�[�W�F���g */
	private String userAgent;

	/** �₢���킹��� */
	private RcpTToiawase toiawase;

	/** �_�E�����[�h�\�t���O */
	private boolean isDowanloadable;

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
	 * �₢���킹�����擾���܂��B
	 * @return �₢���킹���
	 */
	public RcpTToiawase getToiawase() {
		return toiawase;
	}
	/**
	 * �₢���킹����ݒ肵�܂��B
	 * @param toiawase �₢���킹���
	 */
	public void setToiawase(RcpTToiawase toiawase) {
		this.toiawase = toiawase;
	}

	/**
	 * �_�E�����[�h�\�t���O���擾���܂��B
	 * @return �_�E�����[�h�\�t���O
	 */
	public boolean isDownloadable() {
		return isDowanloadable;
	}
	/**
	 * �_�E�����[�h�\�t���O��ݒ肵�܂��B
	 * @param isDowanloadable �_�E�����[�h�\�t���O
	 */
	public void setDownloadable(boolean isDowanloadable) {
		this.isDowanloadable = isDowanloadable;
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