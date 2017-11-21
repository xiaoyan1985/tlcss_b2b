package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.dto.RC031ToiawaseSearchDto;
import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;

/**
 * �ڋq�ڍז₢���킹�������f���B
 *
 * @author v145527 ����
 * @version 1.0 2015/07/24
 */

public class TB049InquiryHistoryInfoModel extends TB040CustomerCommonInfoModel {
	/**
	 *  ��ʂ̌�������
	 */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();
	
	/** �ڋqID�i������ʂ���̃p�����[�^�j */
	private String kokyakuId;
	/** �₢���킹No */
	private String toiawaseNo;

	/**
	 *  ��������
	 */
	private List<RC031ToiawaseSearchDto> result;

	/** ��t�҃��X�g */
	private List<NatosMPassword> uketsukeshaList;
	/** �₢���킹�敪1���X�g */
	private List<RcpMToiawaseKbn1> toiawase1List;
	/** �₢���킹�敪2���X�g */
	private List<RcpMToiawaseKbn2> toiawase2List;
	/** �₢���킹�敪3���X�g */
	private List<RcpMToiawaseKbn3> toiawase3List;
	/** �₢���킹�敪4���X�g */
	private List<RcpMToiawaseKbn4> toiawase4List;
	/** �ŏI�������X�g */
	private List<RcpMJokyoKbn> lastRirekiList;
	/** �ڋq�敪���X�g */
	private List<RcpMComCd> kokyakuKbnList;
	/** �˗��L�����X�g */
	private List<RcpMComCd> iraiUmuList;
	
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
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public RC031ToiawaseSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(RC031ToiawaseSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �₢���킹No���擾���܂��B
	 * 
	 * @return �₢���킹No
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * �₢���킹No��ݒ肵�܂��B
	 * 
	 * @param toiawaseNo �₢���킹No
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * �������ʂ��擾���܂��B
	 *
	 * @return ��������
	 */
	public List<RC031ToiawaseSearchDto> getResult() {
		return result;
	}
	/**
	 * �������ʂ�ݒ肵�܂��B
	 *
	 * @param result ��������
	 */
	public void setResult(List<RC031ToiawaseSearchDto> result) {
		this.result = result;
	}

	/**
	 * ��t�҃��X�g���擾���܂��B
	 *
	 * @return ��t�҃��X�g
	 */
	public List<NatosMPassword> getUketsukeshaList() {
		return uketsukeshaList;
	}
	/**
	 * ��t�҃��X�g��ݒ肵�܂��B
	 *
	 * @param uketsukeshaList ��t�҃��X�g
	 */
	public void setUketsukeshaList(List<NatosMPassword> uketsukeshaList) {
		this.uketsukeshaList = uketsukeshaList;
	}

	/**
	 * �₢���킹�敪1���X�g���擾���܂��B
	 *
	 * @return �₢���킹�敪1���X�g
	 */
	public List<RcpMToiawaseKbn1> getToiawase1List() {
		return toiawase1List;
	}
	/**
	 * �₢���킹�敪1���X�g��ݒ肵�܂��B
	 *
	 * @param kokyakuKbnList �₢���킹�敪1���X�g
	 */
	public void setToiawase1List(List<RcpMToiawaseKbn1> toiawase1List) {
		this.toiawase1List = toiawase1List;
	}

	/**
	 * �₢���킹�敪2���X�g���擾���܂��B
	 *
	 * @return �₢���킹�敪2���X�g
	 */
	public List<RcpMToiawaseKbn2> getToiawase2List() {
		return toiawase2List;
	}
	/**
	 * �₢���킹�敪2���X�g��ݒ肵�܂��B
	 *
	 * @param toiawase2List �₢���킹�敪2���X�g
	 */
	public void setToiawase2List(List<RcpMToiawaseKbn2> toiawase2List) {
		this.toiawase2List = toiawase2List;
	}

	/**
	 * �₢���킹�敪3���X�g���擾���܂��B
	 *
	 * @return �₢���킹�敪3���X�g
	 */
	public List<RcpMToiawaseKbn3> getToiawase3List() {
		return toiawase3List;
	}
	/**
	 * �₢���킹�敪3���X�g��ݒ肵�܂��B
	 *
	 * @param toiawase3List �₢���킹�敪3���X�g
	 */
	public void setToiawase3List(List<RcpMToiawaseKbn3> toiawase3List) {
		this.toiawase3List = toiawase3List;
	}

	/**
	 * �₢���킹�敪4���X�g���擾���܂��B
	 *
	 * @return �₢���킹�敪4���X�g
	 */
	public List<RcpMToiawaseKbn4> getToiawase4List() {
		return toiawase4List;
	}
	/**
	 * �₢���킹�敪4���X�g��ݒ肵�܂��B
	 *
	 * @param toiawase4List �₢���킹�敪4���X�g
	 */
	public void setToiawase4List(List<RcpMToiawaseKbn4> toiawase4List) {
		this.toiawase4List = toiawase4List;
	}

	/**
	 * �ŏI�������X�g���擾���܂��B
	 *
	 * @return �ŏI�������X�g
	 */
	public List<RcpMJokyoKbn> getLastRirekiList() {
		return lastRirekiList;
	}
	/**
	 * �ŏI�������X�g��ݒ肵�܂��B
	 *
	 * @param lastRirekiList �ŏI�������X�g
	 */
	public void setLastRirekiList(List<RcpMJokyoKbn> lastRirekiList) {
		this.lastRirekiList = lastRirekiList;
	}

	/**
	 * �ڋq�敪���X�g���擾���܂��B
	 *
	 * @return �ڋq�敪���X�g
	 */
	public List<RcpMComCd> getKokyakuKbnList() {
		return kokyakuKbnList;
	}
	/**
	 * �ڋq�敪���X�g��ݒ肵�܂��B
	 *
	 * @param kokyakuKbnList �ڋq�敪���X�g
	 */
	public void setKokyakuKbnList(List<RcpMComCd> kokyakuKbnList) {
		this.kokyakuKbnList = kokyakuKbnList;
	}

	/**
	 * �˗��L�����X�g���擾���܂��B
	 *
	 * @return �˗��L�����X�g
	 */
	public List<RcpMComCd> getIraiUmuList() {
		return iraiUmuList;
	}
	/**
	 * �˗��L�����X�g��ݒ肵�܂��B
	 *
	 * @param iraiUmuList �˗��L�����X�g
	 */
	public void setIraiUmuList(List<RcpMComCd> iraiUmuList) {
		this.iraiUmuList = iraiUmuList;
	}

	/**
	 * �񍐑Ώۃt���O���u1�F�񍐂���v���𔻒肵�܂��B
	 *
	 * @return true:�񍐂���Afalse:�񍐂���ȊO
	 */
	public boolean isHoukokuTarget(int idx) {
		return (RcpTToiawase.HOKOKU_TARGET_FLG_ON.equals(this.getResult().get(idx).getHoukokuTargetFlg().toString()) ? true : false);
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
}
