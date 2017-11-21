package jp.co.tokaigroup.tlcss_b2b.user.model;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.common.model.RC000CommonModel;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseManual;

/**
 * �₢���킹�敪�}�j���A���ꗗ��ʃ��f���B
 *
 * @author H.Yamamura
 * @version 1.0 2015/11/20
 */
public class TB029InquiryKbnManualModel extends RC000CommonModel{

	/**
	 * �₢���킹�敪�P
	 */
	private String toiawaseKbn1;

	/**
	 * �₢���킹�敪�Q
	 */
	private String toiawaseKbn2;

	/**
	 * �₢���킹�敪�R
	 */
	private String toiawaseKbn3;

	/**
	 * �₢���킹�敪�S
	 */
	private String toiawaseKbn4;

	/**
	 * �f�t�H���g�R���X�g���N�^�B
	 */
	public TB029InquiryKbnManualModel() {
		super();
	}

	/**
	 * �₢���킹�敪�P���擾���܂��B
	 * @return �₢���킹�敪�P
	 */
	public String getToiawaseKbn1() {
		return toiawaseKbn1;
	}

	/**
	 * �₢���킹�敪�P��ݒ肵�܂��B
	 * @param toiawaseKbn1 �₢���킹�敪�P
	 */
	public void setToiawaseKbn1(String toiawaseKbn1) {
		this.toiawaseKbn1 = toiawaseKbn1;
	}

	/**
	 * �₢���킹�敪�Q���擾���܂��B
	 * @return �₢���킹�敪�Q
	 */
	public String getToiawaseKbn2() {
		return toiawaseKbn2;
	}

	/**
	 * �₢���킹�敪�Q��ݒ肵�܂��B
	 * @param toiawaseKbn2 �₢���킹�敪�Q
	 */
	public void setToiawaseKbn2(String toiawaseKbn2) {
		this.toiawaseKbn2 = toiawaseKbn2;
	}

	/**
	 * �₢���킹�敪�R���擾���܂��B
	 * @return �₢���킹�敪�R
	 */
	public String getToiawaseKbn3() {
		return toiawaseKbn3;
	}

	/**
	 * �₢���킹�敪�R��ݒ肵�܂��B
	 * @param toiawaseKbn3 �₢���킹�敪�R
	 */
	public void setToiawaseKbn3(String toiawaseKbn3) {
		this.toiawaseKbn3 = toiawaseKbn3;
	}

	/**
	 * �₢���킹�敪�S���擾���܂��B
	 * @return �₢���킹�敪�S
	 */
	public String getToiawaseKbn4() {
		return toiawaseKbn4;
	}

	/**
	 * �₢���킹�敪�S��ݒ肵�܂��B
	 * @param toiawaseKbn4 �₢���킹�敪�S
	 */
	public void setToiawaseKbn4(String toiawaseKbn4) {
		this.toiawaseKbn4 = toiawaseKbn4;
	}

	/**
	 * �₢���킹�}�j���A���Q�ƃL�[���擾���܂��B
	 * 
	 * @return �₢���킹�}�j���A���Q�ƃL�[
	 */
	public String getToiawaseManualKey() {
		// �₢���킹�敪�L�[�𐶐� �₢���킹�敪�P�`�S��A��
		String toiawaseKbnKey = "";
		if (StringUtils.isNotBlank(this.toiawaseKbn1)) {
			toiawaseKbnKey += this.toiawaseKbn1;
		} else {
			// �擾�ł��Ȃ��ꍇ�͎w�蕶������g�p
			toiawaseKbnKey += RcpMToiawaseManual.TOIAWASEKBN_NONEXIST_STR;
		}
		if (StringUtils.isNotBlank(this.toiawaseKbn2)) {
			toiawaseKbnKey += this.toiawaseKbn2;
		} else {
			// �擾�ł��Ȃ��ꍇ�͎w�蕶������g�p
			toiawaseKbnKey += RcpMToiawaseManual.TOIAWASEKBN_NONEXIST_STR;
		}
		if (StringUtils.isNotBlank(this.toiawaseKbn3)) {
			toiawaseKbnKey += this.toiawaseKbn3;
		} else {
			// �擾�ł��Ȃ��ꍇ�͎w�蕶������g�p
			toiawaseKbnKey += RcpMToiawaseManual.TOIAWASEKBN_NONEXIST_STR;
		}
		if (StringUtils.isNotBlank(this.toiawaseKbn4)) {
			toiawaseKbnKey += this.toiawaseKbn4;
		} else {
			// �擾�ł��Ȃ��ꍇ�͎w�蕶������g�p
			toiawaseKbnKey += RcpMToiawaseManual.TOIAWASEKBN_NONEXIST_STR;
		}

		return toiawaseKbnKey;
	}
}
