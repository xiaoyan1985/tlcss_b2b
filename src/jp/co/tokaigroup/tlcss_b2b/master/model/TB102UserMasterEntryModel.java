package jp.co.tokaigroup.tlcss_b2b.master.model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

import jp.co.tokaigroup.reception.dto.RC02BKokyakuNodeDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.message.MessageBean;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * ���[�U�[�}�X�^�o�^��ʃ��f���B
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
public class TB102UserMasterEntryModel extends TB000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "���[�U�[�}�X�^�o�^";
	/** �{�^�����F�Ĕ��s */
	public static final String BUTTON_NM_REISSUE = "�Ĕ��s";

	/** ���[���ʒm�t���O 1:���M���� */
	public static final String SEND_MAIL_FLG_ON = "1";

	/** hidden�o�͏��O���� */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** ���[�U�[��� */
	private TbMUser user;

	/** ���[�����M�t���O */
	private String sendMailFlg;

	/** �p�X���[�h�Ĕ��s���[�����M�t���O */
	private String passwdSendMailFlg;

	/** �������X�g */
	private List<RcpMComCd> roleList;

	/** �d�����ڃG���[���b�Z�[�W���X�g */
	private List<MessageBean> duplicateContetErrorMessageList;
	/** �d�����ڃG���[���b�Z�[�W */
	private String ducplicateContentErrorMessage;

	// �ȉ��A��ʃp�����[�^
	/** �A�� */
	private BigDecimal seqNo;
	/** �₢���킹�������� */
	private TB101UserMasterSearchCondition condition = new TB101UserMasterSearchCondition();

	// �I��p�p�����[�^
	/** �ڋq�h�c���x��name������ */
	private String kokyakuIdResultNm;
	/** �ڋq�����x��name������ */
	private String kokyakuNmResultNm;
	/** �Ǝ҃R�[�h���x��name������ */
	private String gyoshaCdResultNm;
	/** �ƎҖ����x��name������ */
	private String gyoshaNmResultNm;
	/** TOKAI�ڋq���X�g */
	private List<RcpMKokyaku> tokaiKokyakuList;
	/** TOKAI�ڋq���X�g�e�L�X�g�i�S�s�j */
	private String tokaiKokyakuListTexts;
	/** �ڋq�K�wDTO���X�g */
	private List<RC02BKokyakuNodeDto> kokyakuNodeList;

	/** ���[�U���i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j */
	private String encodedUserNm;

	/**
	 * ���[�U�[�����擾���܂��B
	 *
	 * @return ���[�U�[���
	 */
	public TbMUser getUser() {
		return user;
	}
	/**
	 * ���[�U�[����ݒ肵�܂��B
	 *
	 * @param user ���[�U�[���
	 */
	public void setUser(TbMUser user) {
		this.user = user;
	}

	/**
	 * ���[�����M�t���O���擾���܂��B
	 *
	 * @return ���[�����M�t���O
	 */
	public String getSendMailFlg() {
		return sendMailFlg;
	}
	/**
	 * ���[�����M�t���O��ݒ肵�܂��B
	 *
	 * @param sendMailFlg ���[�����M�t���O
	 */
	public void setSendMailFlg(String sendMailFlg) {
		this.sendMailFlg = sendMailFlg;
	}

	/**
	 * �p�X���[�h�Ĕ��s���[�����M�t���O���擾���܂��B
	 *
	 * @return �p�X���[�h�Ĕ��s���[�����M�t���O
	 */
	public String getPasswdSendMailFlg() {
		return passwdSendMailFlg;
	}
	/**
	 * �p�X���[�h�Ĕ��s���[�����M�t���O��ݒ肵�܂��B
	 *
	 * @param passwdSendMailFlg �p�X���[�h�Ĕ��s���[�����M�t���O
	 */
	public void setPasswdSendMailFlg(String passwdSendMailFlg) {
		this.passwdSendMailFlg = passwdSendMailFlg;
	}

	/**
	 * �������X�g���擾���܂��B
	 *
	 * @return �������X�g
	 */
	public List<RcpMComCd> getRoleList() {
		return roleList;
	}
	/**
	 * �������X�g��ݒ肵�܂��B
	 *
	 * @param roleList �������X�g
	 */
	public void setRoleList(List<RcpMComCd> roleList) {
		this.roleList = roleList;
	}
	/**
	 * �d�����ڃG���[���b�Z�[�W���X�g���擾���܂��B
	 *
	 * @return �d�����ڃG���[���b�Z�[�W���X�g
	 */
	public List<MessageBean> getDuplicateContetErrorMessageList() {
		return duplicateContetErrorMessageList;
	}
	/**
	 * �d�����ڃG���[���b�Z�[�W���X�g��ݒ肵�܂��B
	 *
	 * @param duplicateContetErrorMessageList �d�����ڃG���[���b�Z�[�W���X�g
	 */
	public void setDuplicateContetErrorMessageList(
			List<MessageBean> duplicateContetErrorMessageList) {
		this.duplicateContetErrorMessageList = duplicateContetErrorMessageList;
	}

	/**
	 * �d�����ڃG���[���b�Z�[�W���擾���܂��B
	 *
	 * @return �d�����ڃG���[���b�Z�[�W
	 */
	public String getDucplicateContentErrorMessage() {
		return ducplicateContentErrorMessage;
	}
	/**
	 * �d�����ڃG���[���b�Z�[�W��ݒ肵�܂��B
	 *
	 * @param ducplicateContentErrorMessage �d�����ڃG���[���b�Z�[�W
	 */
	public void setDucplicateContentErrorMessage(
			String ducplicateContentErrorMessage) {
		this.ducplicateContentErrorMessage = ducplicateContentErrorMessage;
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
	 * �₢���킹�����������擾���܂��B
	 *
	 * @return �₢���킹��������
	 */
	public TB101UserMasterSearchCondition getCondition() {
		return condition;
	}
	/**
	 * �₢���킹����������ݒ肵�܂��B
	 *
	 * @param condition �₢���킹��������
	 */
	public void setCondition(TB101UserMasterSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �ڋq�h�c���x��name���������擾���܂��B
	 *
	 * @return �ڋq�h�c���x��name������
	 */
	public String getKokyakuIdResultNm() {
		return kokyakuIdResultNm;
	}
	/**
	 * �ڋq�h�c���x��name��������ݒ肵�܂��B
	 *
	 * @param kokyakuIdResultNm �ڋq�h�c���x��name������
	 */
	public void setKokyakuIdResultNm(String kokyakuIdResultNm) {
		this.kokyakuIdResultNm = kokyakuIdResultNm;
	}

	/**
	 * �ڋq�����x��name���������擾���܂��B
	 *
	 * @return �ڋq�����x��name������
	 */
	public String getKokyakuNmResultNm() {
		return kokyakuNmResultNm;
	}
	/**
	 * �ڋq�����x��name��������ݒ肵�܂��B
	 *
	 * @param kokyakuNmResultNm �ڋq�����x��name������
	 */
	public void setKokyakuNmResultNm(String kokyakuNmResultNm) {
		this.kokyakuNmResultNm = kokyakuNmResultNm;
	}

	/**
	 * �Ǝ҃R�[�h���x��name���������擾���܂��B
	 *
	 * @return �Ǝ҃R�[�h���x��name������
	 */
	public String getGyoshaCdResultNm() {
		return gyoshaCdResultNm;
	}
	/**
	 * �Ǝ҃R�[�h���x��name��������ݒ肵�܂��B
	 *
	 * @param gyoshaCdResultNm �Ǝ҃R�[�h���x��name������
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
	 * TOKAI�ڋq���X�g���擾���܂��B
	 *
	 * @return TOKAI�ڋq���X�g
	 */
	public List<RcpMKokyaku> getTokaiKokyakuList() {
		return tokaiKokyakuList;
	}
	/**
	 * TOKAI�ڋq���X�g��ݒ肵�܂��B
	 *
	 * @param roleList TOKAI�ڋq���X�g
	 */
	public void setTokaiKokyakuList(List<RcpMKokyaku> tokaiKokyakuList) {
		this.tokaiKokyakuList = tokaiKokyakuList;
	}

	/**
	 * ���[�U���i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j���擾���܂��B
	 *
	 * @return ���[�U���i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j
	 */
	public String getEncodedUserNm() {
		return encodedUserNm;
	}

	/**
	 * ���[�U���i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j��ݒ肵�܂��B
	 *
	 * @param encodedUserNm ���[�U���i�X�V-�X�V�����\�����̃A�N�V�����N���X�̃p�����[�^�p�j
	 */
	public void setEncodedUserNm(String encodedUserNm) {
		try {
			byte[] data = encodedUserNm.getBytes(CharEncoding.ISO_8859_1);
			this.encodedUserNm = new String(data, CharEncoding.UTF_8);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * TOKAI�ڋq���X�g�e�L�X�g���擾���܂��B
	 *
	 * @return TOKAI�ڋq���X�g�e�L�X�g
	 */
	public String getTokaiKokyakuListTexts() {
		return tokaiKokyakuListTexts;
	}
	/**
	 * TOKAI�ڋq���X�g�e�L�X�g��ݒ肵�܂��B
	 *
	 * @param tokaiKokyakuListTexts TOKAI�ڋq���X�g�e�L�X�g
	 */
	public void setTokaiKokyakuListTexts(String tokaiKokyakuListTexts) {
		this.tokaiKokyakuListTexts = tokaiKokyakuListTexts;
	}
	/**
	 * �ڋq�K�wDTO���X�g���擾���܂��B
	 *
	 * @return �ڋq�K�wDTO���X�g
	 */
	public List<RC02BKokyakuNodeDto> getKokyakuNodeList() {
		return kokyakuNodeList;
	}
	/**
	 * �ڋq�K�wDTO���X�g��ݒ肵�܂��B
	 *
	 * @param kokyakuNodeList �ڋq�K�wDTO���X�g
	 */
	public void setKokyakuNodeList(List<RC02BKokyakuNodeDto> kokyakuNodeList) {
		this.kokyakuNodeList = kokyakuNodeList;
	}
	
	/**
	 * ���[�����M���s�����𔻒肵�܂��B
	 *
	 * @return true:�s���Afalse:�s��Ȃ�
	 */
	public boolean isMailSend() {
		return SEND_MAIL_FLG_ON.equals(this.sendMailFlg);
	}

	/**
	 * �d�����ڂ����邩�𔻒肵�܂��B
	 *
	 * @return true:����Afalse:�Ȃ�
	 */
	public boolean isDuplicateContentExists() {
		return !(this.duplicateContetErrorMessageList == null || this.duplicateContetErrorMessageList.isEmpty());
	}

	/**
	 * �폜�`�F�b�N�̃`�F�b�NON������s���܂��B
	 *
	 * @return true:�`�F�b�NON�Afalse:�`�F�b�NOFF
	 */
	public boolean isCheckedDelFlg() {
		if (this.user == null) {
			return false;
		}

		return (TbMUser.DEL_FLG_DELETE.equals(this.user.getDelFlg()));
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

	/**
	 * ���[�����M�t���O���u���M����v�ł��邩�𔻒肵�܂��B
	 *
	 * @return true:���M����Afalse:���M���Ȃ�
	 */
	public boolean isCheckedSendMailFlg() {
		return SEND_MAIL_FLG_ON.equals(this.sendMailFlg);
	}

	/**
	 * �ڋq�K�w���X�g�̃f�[�^�s�����擾���܂��B
	 *
	 * @return �ڋq�K�w���X�g�̃f�[�^�s��
	 */
	public int getKokyakuNodeSize() {
		return (getKokyakuNodeList() != null) ? getKokyakuNodeList().size() : 0;
	}

}
