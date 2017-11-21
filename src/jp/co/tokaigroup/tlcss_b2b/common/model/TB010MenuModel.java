package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dto.InquiryStatus;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.TbMHoliday;
import jp.co.tokaigroup.reception.entity.TbMItakuRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.reception.entity.TbTPublishFile;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.dto.TB010InquiryStatusDto;

import org.apache.commons.lang.StringUtils;

/**
 * ���j���[��ʃ��f���B
 *
 * @author k002849
 * @version 4.0 2014/06/04
 * @version 4.1 2016/09/13 J.Matsuba �ϑ���ЎQ�ƌڋq��񃊃X�g�̒ǉ�
 */
public class TB010MenuModel extends TB000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "���j���[";

	/** �₢���킹�󋵃��X�g */
	private List<TB010InquiryStatusDto> inqueryStatusList;
	/** �Ή������v���� */
	private BigDecimal sumIncompleteCount;
	/** �Ή��ύ��v���� */
	private BigDecimal sumCompleteCount;

	/** ���ǌ��� */
	private BigDecimal unreadCount;

	/** �W�v�J�n�� */
	private Timestamp summaryStartDt;
	/** �W�v�I���� */
	private Timestamp summaryEndDt;

	/** �ڋq��� */
	private RcpMKokyaku kokyaku;

	/** �₢���킹��t�������X�g */
	private List<InquiryStatus> inquiryHistoryList;

	/** �Ή����Č����X�g */
	private List<RcpTIrai> requestList;

	/** �Ǝҏ�� */
	private RcpMGyosha gyosha;

	/** ���m�点���X�g */
	private List<TbTInformation> infomationList;

	/** �j�����X�g */
	private List<TbMHoliday> holidayList;

	/** ���[�U�[�R���e�L�X�g */
	private TLCSSB2BUserContext userContext;

	/** �₢���킹�������� */
	private RC031ToiawaseSearchCondition condition;

	/** ���[�U�[�G�[�W�F���g */
	private String userAgent;

	/** �I�������ڋqID */
	private String selectedKokyakuId;

	/** ���J�t�@�C��Entity���X�g */
	private List<TbTPublishFile> publishFileList;

	/** ��Џ�� */
	private TbMKaisha kaisha;

	/** �ϑ���ЎQ�ƌڋq��񃊃X�g */
	private List<TbMItakuRefKokyaku> itakuRefKokyakuList;

	/** �ĕ\���^�C�}�[ */
	private String reloadTime;
	
	/**
	 * �₢���킹�󋵃��X�g���擾���܂��B
	 *
	 * @return �₢���킹�󋵃��X�g
	 */
	public List<TB010InquiryStatusDto> getInqueryStatusList() {
		return inqueryStatusList;
	}
	/**
	 * �₢���킹�󋵃��X�g��ݒ肵�܂��B
	 *
	 * @param inqueryStatusList �₢���킹�󋵃��X�g
	 */
	public void setInqueryStatusList(List<TB010InquiryStatusDto> inqueryStatusList) {
		this.inqueryStatusList = inqueryStatusList;
	}

	/**
	 * �Ή������v�������擾���܂��B
	 *
	 * @return �Ή������v����
	 */
	public BigDecimal getSumIncompleteCount() {
		return sumIncompleteCount;
	}
	/**
	 * �Ή������v������ݒ肵�܂��B
	 *
	 * @param sumIncomplateCount �Ή������v����
	 */
	public void setSumIncompleteCount(BigDecimal sumIncompleteCount) {
		this.sumIncompleteCount = sumIncompleteCount;
	}

	/**
	 * �Ή��ύ��v�������擾���܂��B
	 *
	 * @return �Ή��ύ��v����
	 */
	public BigDecimal getSumCompleteCount() {
		return sumCompleteCount;
	}
	/**
	 * �Ή��ύ��v������ݒ肵�܂��B
	 *
	 * @param sumCompleteCount �Ή��ύ��v����
	 */
	public void setSumCompleteCount(BigDecimal sumCompleteCount) {
		this.sumCompleteCount = sumCompleteCount;
	}

	/**
	 * ���ǌ������擾���܂��B
	 *
	 * @return ���ǌ���
	 */
	public BigDecimal getUnreadCount() {
		return unreadCount;
	}
	/**
	 * ���ǌ�����ݒ肵�܂��B
	 *
	 * @param unreadCount ���ǌ���
	 */
	public void setUnreadCount(BigDecimal unreadCount) {
		this.unreadCount = unreadCount;
	}

	/**
	 * �W�v�J�n�����擾���܂��B
	 *
	 * @return �W�v�J�n��
	 */
	public Date getSummaryStartDt() {
		return summaryStartDt;
	}
	/**
	 * �W�v�J�n����ݒ肵�܂��B
	 *
	 * @param summaryStartDt �W�v�J�n��
	 */
	public void setSummaryStartDt(Timestamp summaryStartDt) {
		this.summaryStartDt = summaryStartDt;
	}

	/**
	 * �W�v�I�������擾���܂��B
	 *
	 * @return �W�v�I����
	 */
	public Timestamp getSummaryEndDt() {
		return summaryEndDt;
	}
	/**
	 * �W�v�I������ݒ肵�܂��B
	 *
	 * @param summaryEndDt �W�v�I����
	 */
	public void setSummaryEndDt(Timestamp summaryEndDt) {
		this.summaryEndDt = summaryEndDt;
	}

	/**
	 * �ڋq�����擾���܂��B
	 *
	 * @return �ڋq���
	 */
	public RcpMKokyaku getKokyaku() {
		return kokyaku;
	}
	/**
	 * �ڋq����ݒ肵�܂��B
	 *
	 * @param kokyaku �ڋq���
	 */
	public void setKokyaku(RcpMKokyaku kokyaku) {
		this.kokyaku = kokyaku;
	}

	/**
	 * �₢���킹��t�������X�g���擾���܂��B
	 *
	 * @return �₢���킹��t�������X�g
	 */
	public List<InquiryStatus> getInquiryHistoryList() {
		return inquiryHistoryList;
	}
	/**
	 * �₢���킹��t�������X�g��ݒ肵�܂��B
	 *
	 * @param inquiryHistoryList �₢���킹��t�������X�g
	 */
	public void setInquiryHistoryList(List<InquiryStatus> inquiryHistoryList) {
		this.inquiryHistoryList = inquiryHistoryList;
	}

	/**
	 * �Ή����Č����X�g���擾���܂��B
	 *
	 * @return �Ή����Č����X�g
	 */
	public List<RcpTIrai> getRequestList() {
		return requestList;
	}
	/**
	 * �Ή����Č����X�g��ݒ肵�܂��B
	 *
	 * @param requestList �Ή����Č����X�g
	 */
	public void setRequestList(List<RcpTIrai> requestList) {
		this.requestList = requestList;
	}

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
	 * ���m�点���X�g���擾���܂��B
	 *
	 * @return ���m�点���X�g
	 */
	public List<TbTInformation> getInfomationList() {
		return infomationList;
	}
	/**
	 * ���m�点���X�g��ݒ肵�܂��B
	 *
	 * @param infomationList ���m�点���X�g
	 */
	public void setInfomationList(List<TbTInformation> infomationList) {
		this.infomationList = infomationList;
	}

	/**
	 * �j�����X�g���擾���܂��B
	 *
	 * @return �j�����X�g
	 */
	public List<TbMHoliday> getHolidayList() {
		return holidayList;
	}
	/**
	 * �j�����X�g��ݒ肵�܂��B
	 *
	 * @param holidayList �j�����X�g
	 */
	public void setHolidayList(List<TbMHoliday> holidayList) {
		this.holidayList = holidayList;
	}

	/**
	 * ���[�U�[�R���e�L�X�g���擾���܂��B
	 *
	 * @return ���[�U�[�R���e�L�X�g
	 */
	public TLCSSB2BUserContext getUserContext() {
		return userContext;
	}
	/**
	 * ���[�U�[�R���e�L�X�g��ݒ肵�܂��B
	 *
	 * @param userContext ���[�U�[�R���e�L�X�g
	 */
	public void setUserContext(TLCSSB2BUserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * �₢���킹�����������擾���܂��B
	 *
	 * @return �₢���킹��������
	 */
	public RC031ToiawaseSearchCondition getCondition() {
		return condition;
	}
	/**
	 * �₢���킹����������ݒ肵�܂��B
	 *
	 * @param condition �₢���킹��������
	 */
	public void setCondition(RC031ToiawaseSearchCondition condition) {
		this.condition = condition;
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
	 * �I�������ڋqID���擾���܂��B
	 *
	 * @return �I�������ڋqID
	 */
	public String getSelectedKokyakuId() {
		return selectedKokyakuId;
	}
	/**
	 * �I�������ڋqID��ݒ肵�܂��B
	 *
	 * @param selectedKokyakuId �I�������ڋqID
	 */
	public void setSelectedKokyakuId(String selectedKokyakuId) {
		this.selectedKokyakuId = selectedKokyakuId;
	}

	/**
	 * ���J�t�@�C��Entity���X�g���擾���܂��B
	 *
	 * @return publishFileList
	 */
	public List<TbTPublishFile> getPublishFileList() {
		return publishFileList;
	}
	/**
	 * ���J�t�@�C��Entity���X�g��ݒ肵�܂��B
	 *
	 * @param publishFileList �Z�b�g���� publishFileList
	 */
	public void setPublishFileList(List<TbTPublishFile> publishFileList) {
		this.publishFileList = publishFileList;
	}

	/**
	 * ��Џ����擾���܂��B
	 *
	 * @return ��Џ��
	 */
	public TbMKaisha getKaisha() {
		return kaisha;
	}
	/**
	 * ��Џ���ݒ肵�܂��B
	 *
	 * @param kaisha ��Џ��
	 */
	public void setKaisha(TbMKaisha kaisha) {
		this.kaisha = kaisha;
	}

	/**
	 * �ϑ���ЎQ�ƌڋq��񃊃X�g���擾���܂��B
	 *
	 * @return �ϑ���ЎQ�ƌڋq��񃊃X�g
	 */
	public List<TbMItakuRefKokyaku> getItakuRefKokyakuList() {
		return itakuRefKokyakuList;
	}
	/**
	 * �ϑ���ЎQ�ƌڋq��񃊃X�g��ݒ肵�܂��B
	 *
	 * @param itakuRefKokyakuList �ϑ���ЎQ�ƌڋq��񃊃X�g
	 */
	public void setItakuRefKokyakuList(List<TbMItakuRefKokyaku> itakuRefKokyakuList) {
		this.itakuRefKokyakuList = itakuRefKokyakuList;
	}

	/**
	 * �ĕ\���^�C�}�[���擾���܂��B
	 * @return �ĕ\���^�C�}�[
	 */
	public String getReloadTime() {
		return reloadTime;
	}

	/**
	 * �ĕ\���^�C�}�[��ݒ肵�܂��B
	 * @param reloadTime �ĕ\���^�C�}�[
	 */
	public void setReloadTime(String reloadTime) {
		this.reloadTime = reloadTime;
	}
	/**
	 * �S�Ή������̍��v���擾���܂��B
	 *
	 * @return �S�Ή������̍��v
	 */
	public BigDecimal getSumCorrespondence() {
		return this.sumIncompleteCount.add(this.sumCompleteCount);
	}

	/**
	 * ���m�点�̍ŐV�\���������擾���܂��B
	 *
	 * @return ���m�点�̍ŐV�\������
	 */
	public Integer getNewArrivalNoticeDate() {
		return this.userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_NEW_INFORMATION_NOTICE_DATE);
	}

	/**
	 * �w���v��ʂ�URL���擾���܂��B
	 *
	 * @return �w���v��ʂ�URL
	 */
	public String getHelpUrl() throws Exception {
		try {
			if (this.userContext.isInhouse()) {
				// ���O�C�����[�U�[�̌������Г��̏ꍇ
				return this.userContext.getSystgemContstantAsString(
						RcpMSystem.RCP_M_SYSTEM_B2B_ADMINISTRATOR_HELP_URL);
			} else if (this.userContext.isRealEstate()) {
				// ���O�C�����[�U�[�̌������Ǘ���Ђ̏ꍇ
				return this.userContext.getSystgemContstantAsString(
						RcpMSystem.RCP_M_SYSTEM_B2B_REAL_ESTATE_HELP_URL);
			} else if (this.userContext.isConstractor()) {
				// ���O�C�����[�U�[�̌������Ǝ҂̏ꍇ
				return this.userContext.getSystgemContstantAsString(
						RcpMSystem.RCP_M_SYSTEM_B2B_CONTRACTOR_HELP_URL);
			} else if (this.userContext.isOutsourcerSv() || this.userContext.isOutsourcerOp()) {
				// ���O�C�����[�U�[�̌������ϑ����SV�A�ϑ����OP�̏ꍇ
				return this.userContext.getSystgemContstantAsString(
						RcpMSystem.RCP_M_SYSTEM_B2B_OUTSOURCER_HELP_URL);
			} else {
				// ��L�ȊO�̏ꍇ
				throw new ApplicationException("���O�C�����[�U�[�̌������s���ł��B");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * �V�X�e�����Ԃ��擾���܂��B
	 *
	 * @return �V�X�e������
	 */
	public String getSystemTime() {
		return DateUtil.getSysDateString("yyyy/MM/dd HH:mm");
	}

	/**
	 * ���m�点���ɕ\������A���惆�[�U�[�����擾���܂��B
	 *
	 * @return �A���惆�[�U�[��
	 */
	public String getInformationUserName() {
		if (this.userContext == null) {
			return "";
		}

		if (this.userContext.isInhouse()) {
			// ���O�C�����[�U�[�̌������Г��̏ꍇ
			return this.userContext.getUserName();
		}

		if (this.userContext.isRealEstate()) {
			// ���O�C�����[�U�[�̌������s���Y�E�Ǘ���Ђ̏ꍇ
			return (this.kokyaku != null) ? kokyaku.getKanjiNm() : "";
		}

		if (this.userContext.isOutsourcerSv() || this.userContext.isOutsourcerOp()) {
			// ���O�C�����[�U�[�̌������ϑ����SV�A�ϑ����OP�̏ꍇ
			return (this.kaisha != null) ? this.kaisha.getKaishaNm() : "";
		}
		
		// ���O�C�����[�U�[�̌������˗��Ǝ҂̏ꍇ
		return (this.gyosha != null) ? this.gyosha.getGyoshaNm() : "";
	}

	/**
	 * �ϑ���ЎQ�ƌڋq��񂪑��݂��邩�𔻒肵�܂��B
	 *
	 * @return true:���݂���
	 */
	public boolean existsItakuRefKokyaku() {
		if (itakuRefKokyakuList == null || itakuRefKokyakuList.isEmpty()) {
			return false;
		} else {
			return true;
		}
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
