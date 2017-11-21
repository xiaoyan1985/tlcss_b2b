package jp.co.tokaigroup.tlcss_b2b.common.model;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * �������C�u�����_�E�����[�h���f���B
 *
 * @author v140546
 * @version 1.0 2014/08/29
 */
public class TB010DocumentFileDownloadModel extends TB000CommonModel {

	/** ���t�@�C���� **/
	private String realFileNm;

	/** ���[�U�[�G�[�W�F���g */
	private String userAgent;

	/** ���[�U�[�R���e�L�X�g */
	private TLCSSB2BUserContext userContext;

	/**
	 * ���t�@�C�������擾���܂��B
	 *
	 * @return ���t�@�C����
	 */
	public String getRealFileNm() {
		return realFileNm;
	}
	/**
	 * ���t�@�C������ݒ肵�܂��B
	 *
	 * @param toiawaseNo ���t�@�C����
	 */
	public void setRealFileNm(String realFileNm) {
		this.realFileNm = realFileNm;
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
	 * ���[�U�[�R���e�L�X�g���擾���܂��B
	 *
	 * @return ���[�U�[�R���e�L�X�g
	 */
	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}
	/**
	 * ���[�U�[�R���e�L�X�g��ݒ肵�܂��B
	 *
	 * @param userContext ���[�U�[�R���e�L�X�g
	 */
	public void setUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
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