package jp.co.tokaigroup.tlcss_b2b.common.model;


/**
 * �O�����O�C����ʃ��f���B
 *
 * @author k002849
 * @version 1.0 2014/04/25
 */
public class TB003ExternalLoginModel extends TB000CommonModel {
	/** �J�ڐ�URL */
	private String actionURL;

	/** ���y�[�WURL */
	private String nextPageUrl;
	/** ���y�[�W�ւ̃p�����[�^ */
	private String nextPageParam;

	/**
	 * �J�ڐ�URL���擾���܂��B
	 *
	 * @return �J�ڐ�URL
	 */
	public String getActionURL() {
		return actionURL;
	}
	/**
	 * �J�ڐ�URL��ݒ肵�܂��B
	 *
	 * @param actionURL �J�ڐ�URL
	 */
	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	/**
	 * ���y�[�WURL���擾���܂��B
	 *
	 * @return ���y�[�WURL
	 */
	public String getNextPageUrl() {
		return nextPageUrl;
	}
	/**
	 * ���y�[�WURL��ݒ肵�܂��B
	 *
	 * @param nextPageUrl ���y�[�WURL
	 */
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}

	/**
	 * ���y�[�W�ւ̃p�����[�^���擾���܂��B
	 *
	 * @return ���y�[�W�ւ̃p�����[�^
	 */
	public String getNextPageParam() {
		return nextPageParam;
	}
	/**
	 * ���y�[�W�ւ̃p�����[�^��ݒ肵�܂��B
	 *
	 * @param nextPageParam ���y�[�W�ւ̃p�����[�^
	 */
	public void setNextPageParam(String nextPageParam) {
		this.nextPageParam = nextPageParam;
	}
}
