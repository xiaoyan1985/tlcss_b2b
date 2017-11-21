package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.dto.RC014KeiyakuListDto;
import jp.co.tokaigroup.reception.dto.RC014KeiyakuTargetListDto;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.kokyaku.model.RC014KokyakuKeiyakuListCondition;

/**
 * �_���񃂃f���B
 *
 * @author v145527 ����
 * @version 1.0 2015/08/04
 * @version 1.1 2016/08/05 H.Yamamura �_��Ώۈꗗ������ǉ�
 */

public class TB046ContractInfoModel extends TB040CustomerCommonInfoModel {


	/** �_��ꗗ */
	private List<RC014KeiyakuListDto> keiyakuList;
	/** �_��Ώۈꗗ */
	private List<RC014KeiyakuTargetListDto> keiyakuTargetList;

	// ��ʃp�����[�^
	/** �_��m�n */
	private Integer keiyakuNo;
	/** �_��X�V�� */
	private Timestamp keiyakuUpdDt;
	/** �_��Ώێ擾�p�ڋqID */
	private String keiyakuKokyakuId;

	/** �_��ҏ�� */
	private RcpMKokyaku keiyakuKokyakuInfo;
	
	/** �_��Ώۈꗗ ���� */
	private String keiyakuTaisho;
	
	/**
	 *  ��ʂ̌�������
	 */
	private RC014KokyakuKeiyakuListCondition condition = new RC014KokyakuKeiyakuListCondition();

	/**
	 *  ��������
	 */
	private List<RC014KeiyakuListDto> result;
	
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
	 * �_��ꗗ���擾���܂��B
	 *
	 * @return �_��ꗗ
	 */
	public List<RC014KeiyakuListDto> getKeiyakuList() {
		return keiyakuList;
	}
	/**
	 * �_��ꗗ��ݒ肵�܂��B
	 *
	 * @param keiyakuList �_��ꗗ
	 */
	public void setKeiyakuList(List<RC014KeiyakuListDto> keiyakuList) {
		this.keiyakuList = keiyakuList;
	}

	/**
	 * �_��Ώۈꗗ���擾���܂��B
	 *
	 * @return �_��Ώۈꗗ
	 */
	public List<RC014KeiyakuTargetListDto> getKeiyakuTargetList() {
		return keiyakuTargetList;
	}
	/**
	 * �_��Ώۈꗗ��ݒ肵�܂��B
	 *
	 * @param keiyakuTargetList �_��Ώۈꗗ
	 */
	public void setKeiyakuTargetList(
			List<RC014KeiyakuTargetListDto> keiyakuTargetList) {
		this.keiyakuTargetList = keiyakuTargetList;
	}

	/**
	 * �_��m�n���擾���܂��B
	 *
	 * @return �_��m�n
	 */
	public Integer getKeiyakuNo() {
		return keiyakuNo;
	}
	/**
	 * �_��m�n��ݒ肵�܂��B
	 *
	 * @param keiyakuNo �_��m�n
	 */
	public void setKeiyakuNo(Integer keiyakuNo) {
		this.keiyakuNo = keiyakuNo;
	}

	/**
	 * �_��X�V�����擾���܂��B
	 *
	 * @return �_��X�V��
	 */
	public Timestamp getKeiyakuUpdDt() {
		return keiyakuUpdDt;
	}
	/**
	 * �_��X�V����ݒ肵�܂��B
	 *
	 * @param keiyakuUpdDt �_��X�V��
	 */
	public void setKeiyakuUpdDt(Timestamp keiyakuUpdDt) {
		this.keiyakuUpdDt = keiyakuUpdDt;
	}

	/**
	 * �_��ڋqID���擾���܂��B
	 *
	 * @return �_��ڋqID
	 */
	public String getKeiyakuKokyakuId() {
		return keiyakuKokyakuId;
	}
	/**
	 * �_��ڋqID��ݒ肵�܂��B
	 *
	 * @param keiyakuKokyakuId �_��ڋqID
	 */
	public void setKeiyakuKokyakuId(String keiyakuKokyakuId) {
		this.keiyakuKokyakuId = keiyakuKokyakuId;
	}

	/**
	 * �_��ҏ����擾���܂��B
	 *
	 * @return �_��ҏ��
	 */
	public RcpMKokyaku getKeiyakuKokyakuInfo() {
		return keiyakuKokyakuInfo;
	}
	/**
	 * �_��ҏ���ݒ肵�܂��B
	 *
	 * @param keiyakuKokyakuInfo �_��ҏ��
	 */
	public void setKeiyakuKokyakuInfo(RcpMKokyaku keiyakuKokyakuInfo) {
		this.keiyakuKokyakuInfo = keiyakuKokyakuInfo;
	}

	/**
	 * �_��Ώۈꗗ �������擾���܂��B
	 * 
	 * @return �_��Ώۈꗗ ����
	 */
	public String getKeiyakuTaisho() {
		return keiyakuTaisho;
	}

	/**
	 * �_��Ώۈꗗ ������ݒ肵�܂��B
	 * 
	 * @param keiyakuTaisho �_��Ώۈꗗ ����
	 */
	public void setKeiyakuTaisho(String keiyakuTaisho) {
		this.keiyakuTaisho = keiyakuTaisho;
	}

	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public RC014KokyakuKeiyakuListCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(RC014KokyakuKeiyakuListCondition condition) {
		this.condition = condition;
	}

	/**
	 * �������ʂ��擾���܂��B
	 *
	 * @return ��������
	 */
	public List<RC014KeiyakuListDto> getResult() {
		return result;
	}
	/**
	 * �������ʂ�ݒ肵�܂��B
	 *
	 * @param result ��������
	 */
	public void setResult(List<RC014KeiyakuListDto> result) {
		this.result = result;
	}

	/**
	 * �_��Ώۈꗗ��\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isDisplayKeiyakuTargetList() {
		return this.keiyakuNo != null;
	}

	/**
	 * �Ǘ��J�n�I�����o�^�{�^����\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isDisplayButtonKanriDateEntry() {
		if (!isDisplayKeiyakuTargetList()) {
			// �_��Ώۈꗗ����\���̏ꍇ�́A�\�����Ȃ�
			return false;
		}

		return (this.keiyakuTargetList != null && this.keiyakuTargetList.isEmpty());
	}

	/**
	 * �_��ҏ���\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�_��ҏ���\������Afalse:�_��ҏ���\�����Ȃ�
	 */
	public boolean isKokyakuKeiyakuInfoDisplay() {
		return this.keiyakuKokyakuInfo != null;
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