package jp.co.tokaigroup.tlcss_b2b.dto;

import java.sql.Timestamp;

import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.TbTSagyoJokyo;
import jp.co.tokaigroup.si.fw.util.CommonUtil;
import jp.co.tokaigroup.si.fw.util.DateUtil;

import org.apache.commons.lang.StringUtils;

/**
 * ��Ə�DTO�B
 *
 * @author k002849
 * @version 4.0 2014/07/15
 */
public class TB032SagyoJokyoDto extends RcpTSagyoJokyo {
	/** �m�F�� */
	private String kakuninJokyo;
	/** �m�F�҂h�c */
	private String kakuninshaId;
	/** �m�F�� */
	private Timestamp kakuninDt;

	/** ��Ɗ����t���O���� */
	private String sagyoKanryoFlgNm;

	/**
	 * �f�t�H���g�R���X�g���N�^�B
	 */
	public TB032SagyoJokyoDto() {
		super();
	}

	/**
	 * �R���X�g���N�^�B
	 * ��Ə󋵃e�[�u���̏�����ɐݒ肵�܂��B
	 *
	 * @param sagyoJokyo ��Ə󋵃e�[�u��Entity
	 */
	public TB032SagyoJokyoDto(RcpTSagyoJokyo sagyoJokyo) {
		CommonUtil.copyProperties(sagyoJokyo, this);
	}

	/**
	 * �R���X�g���N�^�B
	 * �Ǝ҉񓚍�Ə󋵃e�[�u���̏�����ɐݒ肵�܂��B
	 *
	 * @param sagyoJokyo �Ǝ҉񓚍�Ə󋵃e�[�u��Entity
	 */
	public TB032SagyoJokyoDto(TbTSagyoJokyo sagyoJokyo) {
		CommonUtil.copyProperties(sagyoJokyo, this);
	}

	/**
	 * �Ǝ҉񓚍�Ə󋵃e�[�u���̏��ɕϊ��������ʂ��擾���܂��B
	 *
	 * @return �Ǝ҉񓚍�Ə󋵃e�[�u��Entity
	 */
	public TbTSagyoJokyo toTbTSagyoJokyo() {
		TbTSagyoJokyo sagyoJokyo = new TbTSagyoJokyo();

		CommonUtil.copyProperties(this, sagyoJokyo);

		return sagyoJokyo;
	}

	/**
	 * ��Ə󋵃e�[�u���̏��ɕϊ��������ʂ��擾���܂��B
	 *
	 * @return ��Ə󋵃e�[�u��Entity
	 */
	public RcpTSagyoJokyo toRcpTSagyoJokyo() {
		RcpTSagyoJokyo sagyoJokyo = new RcpTSagyoJokyo();

		CommonUtil.copyProperties(this, sagyoJokyo);

		return sagyoJokyo;
	}

	/**
	 * �m�F�󋵂��擾���܂��B
	 *
	 * @return �m�F��
	 */
	public String getKakuninJokyo() {
		return kakuninJokyo;
	}
	/**
	 * �m�F�󋵂�ݒ肵�܂��B
	 *
	 * @param kakuninJokyo �m�F��
	 */
	public void setKakuninJokyo(String kakuninJokyo) {
		this.kakuninJokyo = kakuninJokyo;
	}

	/**
	 * �m�F�҂h�c���擾���܂��B
	 *
	 * @return �m�F�҂h�c
	 */
	public String getKakuninshaId() {
		return kakuninshaId;
	}
	/**
	 * �m�F�҂h�c��ݒ肵�܂��B
	 *
	 * @param kakuninshaId �m�F�҂h�c
	 */
	public void setKakuninshaId(String kakuninshaId) {
		this.kakuninshaId = kakuninshaId;
	}

	/**
	 * �m�F�����擾���܂��B
	 *
	 * @return �m�F��
	 */
	public Timestamp getKakuninDt() {
		return kakuninDt;
	}
	/**
	 * �m�F����ݒ肵�܂��B
	 *
	 * @param kakuninDt �m�F��
	 */
	public void setKakuninDt(Timestamp kakuninDt) {
		this.kakuninDt = kakuninDt;
	}

	/**
	 * ��Ɗ����t���O���̂��擾���܂��B
	 *
	 * @return ��Ɗ����t���O����
	 */
	public String getSagyoKanryoFlgNm() {
		return sagyoKanryoFlgNm;
	}
	/**
	 * ��Ɗ����t���O���̂�ݒ肵�܂��B
	 *
	 * @param sagyoKanryoFlgNm ��Ɗ����t���O����
	 */
	public void setSagyoKanryoFlgNm(String sagyoKanryoFlgNm) {
		this.sagyoKanryoFlgNm = sagyoKanryoFlgNm;
	}

	/**
	 * ��Ɗ������Ԃ��R�����t���Ŏ擾���܂��B
	 *
	 * @return ��Ɗ������Ԃ��R�����t���Ő��`����������
	 */
	public String getSagyoKanryoJikanPlusColon() {
		if (StringUtils.isBlank(getSagyoKanryoJikan())) {
			return "";
		}

		return DateUtil.hhmmPlusColon(getSagyoKanryoJikan());
	}
}
