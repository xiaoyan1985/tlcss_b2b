package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.dto.RC031ToiawaseSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * �₢���킹������ʃ��f���B
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 1.1 2016/07/11 H.Yamamura �₢���킹�敪�P�`�S���X�g��ǉ�
 */
public class TB021InquirySearchModel extends TB000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "�₢���킹����";

	/** �������� */
	private RC031ToiawaseSearchCondition condition = new RC031ToiawaseSearchCondition();

	/** �������ʃ��X�g */
	private List<RC031ToiawaseSearchDto> resultList;

	/** �O����ߔN�� */
	private String zenkaiShoriYm;

	/** �T�[�r�X�l���X�g */
	private List<RcpMService> serviceMEntityList;

	/** �󋵃��X�g */
	private List<RcpMComCd> jokyoList;
	
	/** �{���󋵃��X�g */
	private List<RcpMComCd> browseStatusList;

	/** �₢���킹�敪1���X�g */
	private List<RcpMToiawaseKbn1> toiawase1List;

	/** �₢���킹�敪2���X�g */
	private List<RcpMToiawaseKbn2> toiawase2List;

	/** �₢���킹�敪3���X�g */
	private List<RcpMToiawaseKbn3> toiawase3List;

	/** �₢���킹�敪4���X�g */
	private List<RcpMToiawaseKbn4> toiawase4List;

	/** ���[�U�[�G�[�W�F���g */
	private String userAgent;

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
	 * �������ʃ��X�g���擾���܂��B
	 *
	 * @return �������ʃ��X�g
	 */
	public List<RC031ToiawaseSearchDto> getResultList() {
		return resultList;
	}
	/**
	 * �������ʃ��X�g��ݒ肵�܂��B
	 *
	 * @param resultList �������ʃ��X�g
	 */
	public void setResultList(List<RC031ToiawaseSearchDto> resultList) {
		this.resultList = resultList;
	}

	/**
	 * �O����ߔN�����擾���܂��B
	 *
	 * @return �O����ߔN��
	 */
	public String getZenkaiShoriYm() {
		return zenkaiShoriYm;
	}
	/**
	 * �O����ߔN����ݒ肵�܂��B
	 *
	 * @param zenkaiShoriYm �O����ߔN��
	 */
	public void setZenkaiShoriYm(String zenkaiShoriYm) {
		this.zenkaiShoriYm = zenkaiShoriYm;
	}

	/**
	 * �T�[�r�X�l���X�g���擾���܂��B
	 *
	 * @return �T�[�r�X�l���X�g
	 */
	public List<RcpMService> getServiceMEntityList() {
		return serviceMEntityList;
	}
	/**
	 * �T�[�r�X�l���X�g��ݒ肵�܂��B
	 *
	 * @param serviceMEntityList �T�[�r�X�l���X�g
	 */
	public void setServiceMEntityList(List<RcpMService> serviceMEntityList) {
		this.serviceMEntityList = serviceMEntityList;
	}

	/**
	 * �󋵃��X�g���擾���܂��B
	 *
	 * @return �󋵃��X�g
	 */
	public List<RcpMComCd> getJokyoList() {
		return jokyoList;
	}
	/**
	 * �󋵃��X�g��ݒ肵�܂��B
	 *
	 * @param jokyoList �󋵃��X�g
	 */
	public void setJokyoList(List<RcpMComCd> jokyoList) {
		this.jokyoList = jokyoList;
	}

	/**
	 * �{���󋵃��X�g���擾���܂��B
	 *
	 * @return �{���󋵃��X�g
	 */
	public List<RcpMComCd> getBrowseStatusList() {
		return browseStatusList;
	}
	/**
	 * �{���󋵃��X�g��ݒ肵�܂��B
	 *
	 * @param browseStatusList �{���󋵃��X�g
	 */
	public void setBrowseStatusList(List<RcpMComCd> browseStatusList) {
		this.browseStatusList = browseStatusList;
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
	 * �������ʂ�\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isResultDisplay() {
		return !(this.resultList == null || this.resultList.isEmpty());
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
