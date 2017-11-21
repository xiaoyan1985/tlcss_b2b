package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyakuFBukken;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKanri;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKojin;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFOoya;
import jp.co.tokaigroup.reception.entity.RcpTBukkenShiteiGyosha;

/**
 * �ڋq�ڍוt����񃂃f���B
 *
 * @author k003316
 * @version 1.0 2015/08/03
 * @version 1.1 2015/11/04 J.Matsuba �����w��ƎҒǉ�
 * @version 1.2 2016/03/28 J.Matsuba �K���A�n��K�A�n���Knull���胁�\�b�h�ǉ�
 */
public class TB045AccompanyingInfoModel extends TB040CustomerCommonInfoModel {
	
	/** �ڋq�t���Ǘ���Џ�� */
	private RcpMKokyakuFKanri fuzuiKanriInfo;
	/** �ڋq�t��������� */
	private RcpMKokyakuFBukken fuzuiBukkenInfo;
	/** �����w��Ǝ҃e�[�u��Entity���X�g */
	private List<RcpTBukkenShiteiGyosha> bukkenShiteiGyoshaTableList;
	/** �ڋq�t����Ə�� */
	private RcpMKokyakuFOoya fuzuiOoyaInfo;
	/** �ڋq�t���l��� */
	private RcpMKokyakuFKojin fuzuiKojinInfo;
	
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
	 * �ڋq�t���Ǘ���Џ����擾���܂��B
	 *
	 * @return �ڋq�t���Ǘ���Џ��
	 */
	public RcpMKokyakuFKanri getFuzuiKanriInfo() {
		return fuzuiKanriInfo;
	}
	/**
	 * �ڋq�t���Ǘ���Џ���ݒ肵�܂��B
	 *
	 * @param fuzuiKanriInfo �ڋq�t���Ǘ���Џ��
	 */
	public void setFuzuiKanriInfo(RcpMKokyakuFKanri fuzuiKanriInfo) {
		this.fuzuiKanriInfo = fuzuiKanriInfo;
	}

	/**
	 * �ڋq�t�����������擾���܂��B
	 *
	 * @return �ڋq�t���������
	 */
	public RcpMKokyakuFBukken getFuzuiBukkenInfo() {
		return fuzuiBukkenInfo;
	}
	/**
	 * �ڋq�t����������ݒ肵�܂��B
	 *
	 * @param fuzuiBukkenInfo �ڋq�t���������
	 */
	public void setFuzuiBukkenInfo(RcpMKokyakuFBukken fuzuiBukkenInfo) {
		this.fuzuiBukkenInfo = fuzuiBukkenInfo;
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
	 * �ڋq�t����Ə����擾���܂��B
	 *
	 * @return �ڋq�t����Ə��
	 */
	public RcpMKokyakuFOoya getFuzuiOoyaInfo() {
		return fuzuiOoyaInfo;
	}
	/**
	 * �ڋq�t����Ə���ݒ肵�܂��B
	 *
	 * @param fuzuiOoyaInfo �ڋq�t����Ə��
	 */
	public void setFuzuiOoyaInfo(RcpMKokyakuFOoya fuzuiOoyaInfo) {
		this.fuzuiOoyaInfo = fuzuiOoyaInfo;
	}

	/**
	 * �ڋq�t���l�����擾���܂��B
	 *
	 * @return �ڋq�t���l���
	 */
	public RcpMKokyakuFKojin getFuzuiKojinInfo() {
		return fuzuiKojinInfo;
	}
	/**
	 * �ڋq�t���l����ݒ肵�܂��B
	 *
	 * @param fuzuiKojinInfo �ڋq�t���l���
	 */
	public void setFuzuiKojinInfo(RcpMKokyakuFKojin fuzuiKojinInfo) {
		this.fuzuiKojinInfo = fuzuiKojinInfo;
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
	 * �K�����󂩔ۂ��𔻒肷��B
	 *
	 * @return true:��ł���
	 */
	public boolean isEmptyKaisu() {
		if (fuzuiBukkenInfo == null) {
			return true;
		}

		return fuzuiBukkenInfo.getKaisu() == null ? true : false;
	}

	/**
	 * �K���@�n��K���󂩔ۂ��𔻒肷��B
	 *
	 * @return true:��ł���
	 */
	public boolean isEmptyChijoKaisu() {
		if (fuzuiBukkenInfo == null) {
			return true;
		}

		return fuzuiBukkenInfo.getChijoKaisu() == null ? true : false;
	}

	/**
	 * �K���@�n���K���󂩔ۂ��𔻒肷��B
	 *
	 * @return true:��ł���
	 */
	public boolean isEmptyChikaKaisu() {
		if (fuzuiBukkenInfo == null) {
			return true;
		}

		return fuzuiBukkenInfo.getChikaKaisu() == null ? true : false;
	}
}
