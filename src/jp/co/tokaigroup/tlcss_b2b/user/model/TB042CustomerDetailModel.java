package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMKokyakuFBukken;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKojin;
import jp.co.tokaigroup.reception.entity.RcpTBukkenShiteiGyosha;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;

import org.apache.commons.lang.StringUtils;

/**
 * �����E�����ҏڍ׃��f���B
 *
 * @author v138130
 * @version 4.0 2014/06/06
 * @version 4.1 2015/11/06 J.Matsuba �����w��ƎҒǉ�
 * @version 4.2 2016/04/07 J.Matsuba �K���A�n��K�A�n���Knull���胁�\�b�h�ǉ�
 */
public class TB042CustomerDetailModel extends TB040CustomerCommonInfoModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "�����E�����ҏڍ�";

	/** �ڋqID�i������ʂ���̃p�����[�^�j */
	private String kokyakuId;

	/** hidden�o�͏��O���� */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** ���������i������ʂ���̕ۑ��p�p�����[�^�j */
	private RC011KokyakuSearchCondition condition = new RC011KokyakuSearchCondition();

	/** �ڋq�t���������}�X�^Entity */
	private RcpMKokyakuFBukken kokyakuBukkenEntity = new RcpMKokyakuFBukken();

	/** �����w��Ǝ҃e�[�u��Entity���X�g */
	private List<RcpTBukkenShiteiGyosha> bukkenShiteiGyoshaTableList;

	/** �ڋq�t���l���}�X�^Entity */
	private RcpMKokyakuFKojin kokyakuKojinEntity = new RcpMKokyakuFKojin();

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

	/**
	 * setter & getter
	 */

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
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public RC011KokyakuSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(RC011KokyakuSearchCondition condition) {
		this.condition = condition;
	}
	/**
	 * �ڋq�t���������}�X�^Entity���擾���܂��B
	 *
	 * @return �ڋq�t���������}�X�^Entity
	 */
	public RcpMKokyakuFBukken getKokyakuBukkenEntity() {
		return kokyakuBukkenEntity;
	}
	/**
	 * �ڋq�t���������}�X�^Entity��ݒ肵�܂��B
	 *
	 * @param bukkenEntity �ڋq�t���������}�X�^Entity
	 */
	public void setKokyakuBukkenEntity(RcpMKokyakuFBukken kokyakuBukkenEntity) {
		this.kokyakuBukkenEntity = kokyakuBukkenEntity;
	}
	/**
	 * �����w��Ǝ҃e�[�u��Entity���X�g���擾���܂��B
	 *
	 * @return �����w��Ǝ҃e�[�u��Entity���X�g
	 */
	public List<RcpTBukkenShiteiGyosha> getBukkenShiteiGyoshaTableList() {
		return bukkenShiteiGyoshaTableList;
	}
	/**
	 * �����w��Ǝ҃e�[�u��Entity���X�g��ݒ肵�܂��B
	 *
	 * @param bukkenShiteiGyoshaTableList �����w��Ǝ҃e�[�u��Entity���X�g
	 */
	public void setBukkenShiteiGyoshaTableList(List<RcpTBukkenShiteiGyosha> bukkenShiteiGyoshaTableList) {
		this.bukkenShiteiGyoshaTableList = bukkenShiteiGyoshaTableList;
	}
	/**
	 * �ڋq�t���l���}�X�^Entity���擾���܂��B
	 *
	 * @return �ڋq�t���l���}�X�^Entity
	 */
	public RcpMKokyakuFKojin getKokyakuKojinEntity() {
		return kokyakuKojinEntity;
	}
	/**
	 * �ڋq�t���l���}�X�^Entity��ݒ肵�܂��B
	 *
	 * @param kojinEntity �ڋq�t���l���}�X�^Entity
	 */
	public void setKokyakuKojinEntity(RcpMKokyakuFKojin kokyakuKojinEntity) {
		this.kokyakuKojinEntity = kokyakuKojinEntity;
	}

	/**
	 * ��ʗp���\�b�h
	 */

	/**
	 * �^�C�g�����̎擾���\�b�h
	 *
	 * @return
	 *   �ڋq�}�X�^Entity�̌ڋq�敪���u3�F�����v�̏ꍇ:			"����"
	 *   �ڋq�}�X�^Entity�̌ڋq�敪���u4�F�����ҁE�l�v�̏ꍇ:	"������"
	 */
	public String getTitileNm() {
		if (getKokyakuEntity().isKokyakuKbnBukken()) {
			return "����";
		} else if (getKokyakuEntity().isKokyakuKbnNyukyosha()) {
			return "������";
		}
		return "";
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
	 * �����w��Ǝ҃e�[�u��Entity���X�g���󂩔��肷��B
	 *
	 * @return true�F��ł���
	 */
	public boolean isEmptyBukkenShiteiGyoshaTableList() {
		return bukkenShiteiGyoshaTableList == null || bukkenShiteiGyoshaTableList.isEmpty();
	}

	/**
	 * �K�����󂩔��肷��B
	 *
	 * @return true�F��ł���
	 */
	public boolean isEmptyKaisu() {
		return (kokyakuBukkenEntity == null || kokyakuBukkenEntity.getKaisu() == null);
	}

	/**
	 * �K���@�n��K���󂩔��肷��B
	 *
	 * @return true�F��ł���
	 */
	public boolean isEmptyChijoKaisu() {
		return (kokyakuBukkenEntity == null || kokyakuBukkenEntity.getChijoKaisu() == null);
	}

	/**
	 * �K���@�n���K���󂩔��肷��B
	 *
	 * @return true�F��ł���
	 */
	public boolean isEmptyChikaKaisu() {
		return (kokyakuBukkenEntity == null || kokyakuBukkenEntity.getChikaKaisu() == null);
	}
}
