package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.gyosha.model.RC061GyoshaSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

/**
 * �Z���^�[�Ǝҏڍ׉�ʃ��f���B
 *
 * @author v145527
 * @version 1.0 2015/10/08
 */
public class TB052CenterContractorDetailModel extends TB000CommonModel {

	/** �Ǝҏ�� */
	private RcpMGyosha gyosha;
	
	/** �������� */
	private RC061GyoshaSearchCondition condition = new RC061GyoshaSearchCondition();

	/** �s���{���i�n��j���X�g */
	private List<RcpMComCd> todofukenAreaList;
	/** �s���{���i�n��j�}�b�v */
	private Map<String, String> todofukenAreaMap;

	/** �Ǝ҃R�[�h */
	private String gyoshaCd;

	/** �J�ڌ���ʋ敪(����ʂ���) */
	private String dispKbn;
	/** �Ǝ҃R�[�hname������ */
	private String gyoshaCdResultNm;
	/** �ƎҖ����x��name������ */
	private String gyoshaNmResultNm;
	
	/** �����\���G���[�t���O */
	private boolean isInitError = false;


	/**
	 * �Ǝҏ����擾���܂��B
	 *
	 * @return �Ǝҏ��
	 */
	public RcpMGyosha getGyosha() {
		return gyosha;
	}
	/**
	 * �Ǝҏ���ݒ肵�܂��B
	 *
	 * @param gyosha �Ǝҏ��
	 */
	public void setGyosha(RcpMGyosha gyosha) {
		this.gyosha = gyosha;
	}
	
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
	 * �s���{���i�n��j���X�g���擾���܂��B
	 *
	 * @return todofukenAreaList
	 */
	public List<RcpMComCd> getTodofukenAreaList() {
		return todofukenAreaList;
	}
	/**
	 * �s���{���i�n��j���X�g��ݒ肵�܂��B
	 *
	 * @param todofukenAreaList �s���{���i�n��j���X�g
	 */
	public void setTodofukenAreaList(List<RcpMComCd> todofukenAreaList) {
		this.todofukenAreaList = todofukenAreaList;
	}

	/**
	 * �s���{���i�n��j�}�b�v���擾���܂��B
	 *
	 * @return todofukenAreaMap
	 */
	public Map<String, String> getTodofukenAreaMap() {
		return todofukenAreaMap;
	}
	/**
	 * �s���{���i�n��j�}�b�v��ݒ肵�܂��B
	 *
	 * @param todofukenAreaMap �s���{���i�n��j�}�b�v
	 */
	public void setTodofukenAreaMap(Map<String, String> todofukenAreaMap) {
		this.todofukenAreaMap = todofukenAreaMap;
	}

	/**
	 * �Ǝ҃R�[�h���擾���܂��B
	 *
	 * @return gyoshaCd
	 */
	public String getGyoshaCd() {
		return gyoshaCd;
	}
	/**
	 * �Ǝ҃R�[�h��ݒ肵�܂��B
	 *
	 * @param gyoshaCd �Ǝ҃R�[�h
	 */
	public void setGyoshaCd(String gyoshaCd) {
		this.gyoshaCd = gyoshaCd;
	}

	/**
	 * �����\���G���[�t���O���擾���܂��B
	 * 
	 * @return isInitError �����\���G���[�t���O
	 */
	public boolean isInitError() {
		return isInitError;
	}
	/**
	 * �����\���G���[�t���O��ݒ肵�܂��B
	 * 
	 * @param isInitError �Z�b�g���� �����\���G���[�t���O
	 */
	public void setInitError(boolean isInitError) {
		this.isInitError = isInitError;
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
}
