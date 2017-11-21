package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;
import jp.co.tokaigroup.reception.dto.RC061GyoshaSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMTodofuken;
import jp.co.tokaigroup.reception.gyosha.model.RC061GyoshaSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * �Z���^�[�ƎҌ�����ʃ��f���B
 *
 * @author v145527
 * @version 1.0 2015/09/29
 */
public class TB051CenterContractorSearchModel extends TB000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "�Z���^�[�ƎҌ���";
	
	/** �������� */
	private RC061GyoshaSearchCondition condition = new RC061GyoshaSearchCondition();
	
	/** �������ʃ��X�g */
	private List<RC061GyoshaSearchDto> resultList;
	
	/** �s���{�����X�g */
	private List<RcpMTodofuken> todofukenList;
	/** �Ǝ탊�X�g */
	private List<RcpMComCd> gyoshuList;
	
	/** �X�֔ԍ�����URL */
	private String yubinNoSearchURL;

	/** �J�ڌ���ʋ敪(����ʂ���) */
	private String dispKbn;
	/** �Ǝ҃R�[�hname������ */
	private String gyoshaCdResultNm;
	/** �ƎҖ����x��name������ */
	private String gyoshaNmResultNm;

	/**
	 * �����������擾���܂��B
	 *
	 * @return ��������
	 */
	public RC061GyoshaSearchCondition getCondition() {
		return condition;
	}
	/**
	 * ����������ݒ肵�܂��B
	 *
	 * @param condition ��������
	 */
	public void setCondition(RC061GyoshaSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �������ʂ��擾���܂��B
	 *
	 * @return ��������
	 */
	public List<RC061GyoshaSearchDto> getResultList() {
		return resultList;
	}
	/**
	 * �������ʂ�ݒ肵�܂��B
	 *
	 * @param resultList ��������
	 */
	public void setResultList(List<RC061GyoshaSearchDto> resultList) {
		this.resultList = resultList;
	}
	
	/**
	 * �s���{�����X�g���擾���܂��B
	 *
	 * @return �s���{�����X�g
	 */
	public List<RcpMTodofuken> getTodofukenList() {
		return todofukenList;
	}
	/**
	 * �s���{�����X�g��ݒ肵�܂��B
	 *
	 * @param todofukenList �s���{�����X�g
	 */
	public void setTodofukenList(List<RcpMTodofuken> todofukenList) {
		this.todofukenList = todofukenList;
	}

	/**
	 * �Ǝ탊�X�g���擾���܂��B
	 *
	 * @return �Ǝ탊�X�g
	 */
	public List<RcpMComCd> getGyoshuList() {
		return gyoshuList;
	}
	/**
	 * �Ǝ탊�X�g��ݒ肵�܂��B
	 *
	 * @param gyoshuList �Ǝ탊�X�g
	 */
	public void setGyoshuList(List<RcpMComCd> gyoshuList) {
		this.gyoshuList = gyoshuList;
	}
	
	/**
	 * �X�֔ԍ�����URL���擾���܂��B
	 *
	 * @return �X�֔ԍ�����URL
	 */
	public String getYubinNoSearchURL() {
		return yubinNoSearchURL;
	}
	/**
	 * �X�֔ԍ�����URL��ݒ肵�܂��B
	 *
	 * @param yubinNoSearchURL �X�֔ԍ�����URL
	 */
	public void setYubinNoSearchURL(String yubinNoSearchURL) {
		this.yubinNoSearchURL = yubinNoSearchURL;
	}

	/**
	 * �J�ڌ���ʋ敪���擾���܂��B
	 *
	 * @return �J�ڌ���ʋ敪
	 */
	public String getDispKbn() {
		return dispKbn;
	}
	/**
	 * �J�ڌ���ʋ敪��ݒ肵�܂��B
	 *
	 * @param dispKbn �J�ڌ���ʋ敪
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}

	/**
	 * �Ǝ҃R�[�hname���������擾���܂��B
	 *
	 * @return �Ǝ҃R�[�hname������
	 */
	public String getGyoshaCdResultNm() {
		return gyoshaCdResultNm;
	}
	/**
	 * �Ǝ҃R�[�hname��������ݒ肵�܂��B
	 *
	 * @param gyoshaCdResultNm �Ǝ҃R�[�hname������
	 */
	public void setGyoshaCdResultNm(String gyoshaCdResultNm) {
		this.gyoshaCdResultNm = gyoshaCdResultNm;
	}

	/**
	 * �ƎҖ����x��name���������擾���܂��B
	 *
	 * @return �ƎҖ����x��name������
	 */
	public String getGyoshaNmResultNm() {
		return gyoshaNmResultNm;
	}
	/**
	 * �ƎҖ����x��name��������ݒ肵�܂��B
	 *
	 * @param gyoshaNmResultNm �ƎҖ����x��name������
	 */
	public void setGyoshaNmResultNm(String gyoshaNmResultNm) {
		this.gyoshaNmResultNm = gyoshaNmResultNm;
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
	 * �˗��o�^��ʂ���̑J�ڂ����肵�܂��B
	 *
	 * @return true:�˗��o�^��ʂ���̑J��
	 */
	public boolean isFromRequestEntry() {
		return Constants.GAMEN_KBN_REQUEST_ENTRY.equals(this.dispKbn);
	}

	/**
	 * ���j���`�F�b�N�{�b�N�X�̒l�𔻒肵�܂��B
	 * 
	 * @return true:�`�F�b�NON
	 */
	public boolean isMondayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiMonday());
		
	}
	
	/**
	 * �Ηj���`�F�b�N�{�b�N�X�̒l�𔻒肵�܂��B
	 * 
	 * @return true:�`�F�b�NON
	 */
	public boolean isTuesdayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiTuesday());
		
	}
	
	/**
	 * ���j���`�F�b�N�{�b�N�X�̒l�𔻒肵�܂��B
	 * 
	 * @return true:�`�F�b�NON
	 */
	public boolean isWednesdayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiWednesday());
		
	}
	
	/**
	 * �ؗj���`�F�b�N�{�b�N�X�̒l�𔻒肵�܂��B
	 * 
	 * @return true:�`�F�b�NON
	 */
	public boolean isThursdayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiThursday());
		
	}
	
	/**
	 * ���j���`�F�b�N�{�b�N�X�̒l�𔻒肵�܂��B
	 * 
	 * @return true:�`�F�b�NON
	 */
	public boolean isFridayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiFriday());
		
	}
	
	/**
	 * �y�j���`�F�b�N�{�b�N�X�̒l�𔻒肵�܂��B
	 * 
	 * @return true:�`�F�b�NON
	 */
	public boolean isSaturdayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiSaturday());
		
	}
	
	/**
	 * ���j���`�F�b�N�{�b�N�X�̒l�𔻒肵�܂��B
	 * 
	 * @return true:�`�F�b�NON
	 */
	public boolean isSundayChecked() {
		return RcpMGyosha.RCP_M_GYOSHA_EIGYOBI_FLG_TRUE.equals(condition.getEigyobiSunday());
		
	}
	
	/**
	 * �Ǝ�`�F�b�N�{�b�N�X�̒l�𔻒肵�܂��B
	 * 
	 * @param idx
	 * @return ture:�`�F�b�NON
	 */
	public boolean isGyoshuListSelected(int idx) {
		if (this.gyoshuList == null || this.gyoshuList.isEmpty()) {
			return false;
		}
		
		if (this.gyoshuList.size() < idx) {
			return false;
		}
		
		if (this.condition.getGyoshuList() == null || this.condition.getGyoshuList().isEmpty()) {
			return false;
		}
		
		RcpMComCd comCd = this.gyoshuList.get(idx);
		
		return this.condition.getGyoshuList().contains(comCd.getComCd());
	}
	
	/**
	 * �폜�ς݂̋Ǝ҂��܂ރ`�F�b�N�{�b�N�X�̒l�𔻒肵�܂��B
	 * 
	 * @return true:�`�F�b�NON
	 */
	public boolean isYukoseiChecked() {
		return RC061GyoshaSearchCondition.YUKOSEI_FLG_ON.equals(condition.getYukosei());
	}
		
}
