package jp.co.tokaigroup.tlcss_b2b.master.model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB103InformationSearchCondition;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * ���m�点�o�^��ʃ��f���B
 *
 * @author v138130
 * @version 4.0 2014/06/20
 */
public class TB104InformationEntryModel extends TB000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "���m�点�o�^";

	/** hidden�o�͏��O���� */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** ���m�点��� */
	private TbTInformation info;

	/** �\���Ώۗp���X�g */
	private List<RcpMComCd> targetList;

	// �ȉ��A��ʃp�����[�^
	/** �A�� */
	private BigDecimal seqNo;
	/** ���m�点�������� */
	private TB103InformationSearchCondition condition = new TB103InformationSearchCondition();

	/** �ڋq���i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j */
	private String encodedKokyakuNm;

	/** �ƎҖ��i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j */
	private String encodedGyoshaNm;


	/**
	 * ���m�点�����擾���܂��B
	 *
	 * @return info
	 */
	public TbTInformation getInfo() {
		return info;
	}
	/**
	 * ���m�点����ݒ肵�܂��B
	 *
	 * @param info ���m�点���
	 */
	public void setInfo(TbTInformation info) {
		this.info = info;
	}
	/**
	 * �\���Ώۗp���X�g���擾���܂��B
	 *
	 * @return �\���Ώۗp���X�g
	 */
	public List<RcpMComCd> getTargetList() {
		return targetList;
	}
	/**
	 * �\���Ώۗp���X�g��ݒ肵�܂��B
	 *
	 * @param serviceList �\���Ώۗp���X�g
	 */
	public void setTargetList(List<RcpMComCd> targetList) {
		this.targetList = targetList;
	}
	/**
	 * �A�Ԃ��擾���܂��B
	 *
	 * @return �A��
	 */
	public BigDecimal getSeqNo() {
		return seqNo;
	}
	/**
	 * �A�Ԃ�ݒ肵�܂��B
	 *
	 * @param seqNo �A��
	 */
	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * ���m�点�����������擾���܂��B
	 *
	 * @return ���m�点��������
	 */
	public TB103InformationSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ���m�点����������ݒ肵�܂��B
	 *
	 * @param condition ���m�点��������
	 */
	public void setCondition(TB103InformationSearchCondition condition) {
		this.condition = condition;
	}


	/**
	 * �ڋq���i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j���擾���܂��B
	 *
	 * @return �ڋq���i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j
	 */
	public String getEncodedKokyakuNm() {
		return encodedKokyakuNm;
	}

	/**
	 * �ڋq���i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j��ݒ肵�܂��B
	 *
	 * @param encodedKokyakuNm �ڋq��
	 */
	public void setEncodedKokyakuNm(String encodedKokyakuNm) {
		try {
			byte[] data = encodedKokyakuNm.getBytes(CharEncoding.ISO_8859_1);
			this.encodedKokyakuNm = new String(data, CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ƎҖ��i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j���擾���܂��B
	 *
	 * @return �ƎҖ��i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j
	 */
	public String getEncodedGyoshaNm() {
		return encodedGyoshaNm;
	}

	/**
	 * �ƎҖ��i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j��ݒ肵�܂��B
	 *
	 * @param encodedGyoshaNm �ƎҖ��i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j
	 */
	public void setEncodedGyoshaNm(String encodedGyoshaNm) {
		try {
			byte[] data = encodedGyoshaNm.getBytes(CharEncoding.ISO_8859_1);
			this.encodedGyoshaNm = new String(data, CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * hidden�o�͏��O���ڂ��J���}��؂�Ŏ擾���܂��B
	 *
	 * @return hidden�o�͏��O���ځi�J���}��؂�j
	 */
	public String getExcludeField() {
		return StringUtils.join(EXCLUDE_FIELD_NAME, ",");
	}

	public String getDecodePrm(String prm) {
		try {
			return URLDecoder.decode(prm, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return prm;
		}
	}

	/**
	 * "UTF-8"�̃G���R�[�f�B���O���ʂ��擾���܂��B
	 *
	 * @param value �G���R�[�f�B���O�Ώ�
	 * @return �G���R�[�f�B���O���ʁivalue��null�̏ꍇ�A""��Ԃ��j
	 */
	public String encode(String value) {
		try {
			return StringUtils.isNotBlank(value) ? (URLEncoder.encode(value, CharEncoding.UTF_8)) : "";
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}

}
