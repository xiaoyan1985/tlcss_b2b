package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.sql.Timestamp;
import java.util.List;

import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.reception.context.ReceptionUserContext;
import jp.co.tokaigroup.reception.dto.RC905NyudenHokokuHyoDto;
import jp.co.tokaigroup.reception.dto.RC906SagyoHokokuHyoDto;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSashidashinin;
import jp.co.tokaigroup.reception.entity.RcpTSagyohi;
import jp.co.tokaigroup.reception.kokyaku.model.RC016KokyakuKanrenEntrySearchCondition;

/**
 * �񍐏������ʃ��f���B
 *
 * @author k002785
 * @version 1.0 2015/08/05
 * @version 1.1 2016/07/08 H.Hirai ����������Ή�
 */
public class TB015ReportPrintModel {

	/** ���t���R�[�h�FTOKAI */
	public static final String SENDER_CD_TOKAI = "0";

	/** ��ʖ��F���d�񍐏���� */
	public static final String GAMEN_NM_INCOMING_CALL_REPORT = "���d�񍐏�";

	/** ��ʖ��F��ƕ񍐏���� */
	public static final String GAMEN_NM_WORK_REPORT = "��ƕ񍐏�";
	
	/** CSV���F���d�񍐏���� */
	public static final String CSV_NM_INCOMING_CALL_REPORT = "incoming_call_report_csv";
	
	/** CSV���F��ƕ񍐏���� */
	public static final String CSV_NM_WORK_REPORT = "work_report_csv";
	
	/** ���[�敪�F���d�񍐏� */
	public static final String PRINT_KBN_INCOMING_CALL_REPORT = "1";
	
	/** ���[�敪�F��ƕ񍐏� */
	public static final String PRINT_KBN_WORK_REPORT = "2";

	/** �₢���킹NO */
	private String toiawaseNo;

	/** �ڋqID */
	private String kokyakuId;

	/** ��ʋ敪 */
	private String dispKbn;

	/** �₢���킹����NO */
	private String toiawaseRirekiNo;

	/** �������� */
	private RC016KokyakuKanrenEntrySearchCondition condition;

	/** �֘A�t���ڋq */
	private List<RcpMKokyaku> kanrenList;

	/** ���o�l��� */
	private RcpMSashidashinin sashidashinin;

	/** ������ڋq��� */
	private RcpMKokyaku seikyusakiKokyaku;

	/** ������ڋq��񃊃X�g */
	private List<RcpMKokyaku> seikyusakiKokyakuList;

	/** ��Ɣ��� */
	private RcpTSagyohi sagyohi;

	/** ���W�I�I��l�i�֘A�t���ꗗ�j */
	private String rdoKokyakuIdListRelevance;
	/** ���W�I�I��l�i���t���j */
	private String rdoSender;

	/** �₢���킹�X�V�� */
	private Timestamp toiawaseUpdDt;
	/** ��Ə󋵍X�V�� */
	private Timestamp sagyoJokyoUpdDt;

	/** ���[�U�R���e�L�X�g */
	private ReceptionUserContext context;
	
	/** ��������(���d�񍐏� CSV�����p) */
	private List<RC905NyudenHokokuHyoDto> nyudenHokokushoResultList;
	
	/** ��������(��ƕ񍐏� CSV�����p) */
	private List<RC906SagyoHokokuHyoDto> sagyoHokokushoResultList;

	/** ���t�����̂P */
	private String senderNm1;

	/** ���t�����̂Q */
	private String senderNm2;

	/** ���t���Z�� */
	private String senderAddress;

	/** ���t���d�b�ԍ� */
	private String senderTelNo;

	/** ���t��FAX�ԍ� */
	private String senderFaxNo;

	/** ���[�敪 */
	private String printKbn;
	
	/** ���[�p�X */
	private String makePdfPath;
	
	/** ���[�� */
	private String pdfNm;

	/**
	 * @return toiawaseNo
	 */
	public String getToiawaseNo() {
		return toiawaseNo;
	}

	/**
	 * @param toiawaseNo �Z�b�g���� toiawaseNo
	 */
	public void setToiawaseNo(String toiawaseNo) {
		this.toiawaseNo = toiawaseNo;
	}

	/**
	 * @return kokyakuId
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}

	/**
	 * @param kokyakuId �Z�b�g���� kokyakuId
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}

	/**
	 * @return dispKbn
	 */
	public String getDispKbn() {
		return dispKbn;
	}

	/**
	 * @param dispKbn �Z�b�g���� dispKbn
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}

	/**
	 * @return toiawaseRirekiNo
	 */
	public String getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}

	/**
	 * @param toiawaseRirekiNo �Z�b�g���� toiawaseRirekiNo
	 */
	public void setToiawaseRirekiNo(String toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}

	/**
	 * @return condition
	 */
	public RC016KokyakuKanrenEntrySearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition �Z�b�g���� condition
	 */
	public void setCondition(RC016KokyakuKanrenEntrySearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * @return kanrenList
	 */
	public List<RcpMKokyaku> getKanrenList() {
		return kanrenList;
	}

	/**
	 * @param kanrenList �Z�b�g���� kanrenList
	 */
	public void setKanrenList(List<RcpMKokyaku> kanrenList) {
		this.kanrenList = kanrenList;
	}

	/**
	 * @return sashidashinin
	 */
	public RcpMSashidashinin getSashidashinin() {
		return sashidashinin;
	}

	/**
	 * @param sashidashinin �Z�b�g���� sashidashinin
	 */
	public void setSashidashinin(RcpMSashidashinin sashidashinin) {
		this.sashidashinin = sashidashinin;
	}

	/**
	 * @return seikyusakiKokyaku
	 */
	public RcpMKokyaku getSeikyusakiKokyaku() {
		return seikyusakiKokyaku;
	}

	/**
	 * @param seikyusakiKokyaku �Z�b�g���� seikyusakiKokyaku
	 */
	public void setSeikyusakiKokyaku(RcpMKokyaku seikyusakiKokyaku) {
		this.seikyusakiKokyaku = seikyusakiKokyaku;
	}

	/**
	 * ������ڋq��񃊃X�g���擾���܂��B
	 * 
	 * @return ������ڋq��񃊃X�g
	 */
	public List<RcpMKokyaku> getSeikyusakiKokyakuList() {
		return seikyusakiKokyakuList;
	}

	/**
	 * ������ڋq��񃊃X�g��ݒ肵�܂��B
	 * 
	 * @param seikyusakiKokyakuList ������ڋq��񃊃X�g
	 */
	public void setSeikyusakiKokyakuList(List<RcpMKokyaku> seikyusakiKokyakuList) {
		this.seikyusakiKokyakuList = seikyusakiKokyakuList;
	}

	/**
	 * @return sagyohi
	 */
	public RcpTSagyohi getSagyohi() {
		return sagyohi;
	}

	/**
	 * @param sagyohi �Z�b�g���� sagyohi
	 */
	public void setSagyohi(RcpTSagyohi sagyohi) {
		this.sagyohi = sagyohi;
	}

	/**
	 * ���W�I�I��l�i�֘A�t���ꗗ�j���擾���܂��B
	 *
	 * @return ���W�I�I��l�i�֘A�t���ꗗ�j
	 */
	public String getRdoKokyakuIdListRelevance() {
		return rdoKokyakuIdListRelevance;
	}
	/**
	 * ���W�I�I��l�i�֘A�t���ꗗ�j��ݒ肵�܂��B
	 *
	 * @param rdoKokyakuIdListRelevance ���W�I�I��l�i�֘A�t���ꗗ�j
	 */
	public void setRdoKokyakuIdListRelevance(String rdoKokyakuIdListRelevance) {
		this.rdoKokyakuIdListRelevance = rdoKokyakuIdListRelevance;
	}

	/**
	 * ���W�I�I��l�i���t���j���擾���܂��B
	 *
	 * @return ���W�I�I��l�i���t���j
	 */
	public String getRdoSender() {
		return rdoSender;
	}
	/**
	 * ���W�I�I��l�i���t���j��ݒ肵�܂��B
	 *
	 * @param rdoSender ���W�I�I��l�i���t���j
	 */
	public void setRdoSender(String rdoSender) {
		this.rdoSender = rdoSender;
	}

	/**
	 * �₢���킹�X�V�����擾���܂��B
	 *
	 * @return �₢���킹�X�V��
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}
	/**
	 * �₢���킹�X�V����ݒ肵�܂��B
	 *
	 * @param toiawaseUpdDt �₢���킹�X�V��
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}

	/**
	 * ��Ə󋵍X�V�����擾���܂��B
	 *
	 * @return �˗��X�V��
	 */
	public Timestamp getSagyoJokyoUpdDt() {
		return sagyoJokyoUpdDt;
	}
	/**
	 * ��Ə󋵍X�V����ݒ肵�܂��B
	 *
	 * @param iraiUpdDt �˗��X�V��
	 */
	public void setSagyoJokyoUpdDt(Timestamp sagyoJokyoUpdDt) {
		this.sagyoJokyoUpdDt = sagyoJokyoUpdDt;
	}

	/**
	 * ���[�U�R���e�L�X�g���擾���܂��B
	 *
	 * @return ���[�U�R���e�L�X�g
	 */
	public ReceptionUserContext getContext() {
		return context;
	}
	/**
	 * ���[�U�R���e�L�X�g��ݒ肵�܂��B
	 *
	 * @param context ���[�U�R���e�L�X�g
	 */
	public void setContext(ReceptionUserContext context) {
		this.context = context;
	}
	
	/**
	 * ��������(���d�񍐏� CSV�����p)���擾���܂��B
	 *
	 * @return ��������(���d�񍐏� CSV�����p)
	 */
	public List<RC905NyudenHokokuHyoDto> getNyudenHokokushoResultList() {
		return nyudenHokokushoResultList;
	}

	/**
	 * ��������(���d�񍐏� CSV�����p)��ݒ肵�܂��B
	 *
	 * @param nyudenHokokushoResultList ��������(���d�񍐏� CSV�����p)
	 */
	public void setNyudenHokokushoResultList(
			List<RC905NyudenHokokuHyoDto> nyudenHokokushoResultList) {
		this.nyudenHokokushoResultList = nyudenHokokushoResultList;
	}
	
	/**
	 * ��������(��ƕ񍐏� CSV�����p)���擾���܂��B
	 *
	 * @return ��������(��ƕ񍐏� CSV�����p)
	 */
	public List<RC906SagyoHokokuHyoDto> getSagyoHokokushoResultList() {
		return sagyoHokokushoResultList;
	}

	/**
	 * ��������(��ƕ񍐏� CSV�����p)��ݒ肵�܂��B
	 *
	 * @param sagyoHokokushoResultList ��������(��ƕ񍐏� CSV�����p)
	 */
	public void setSagyoHokokushoResultList(
			List<RC906SagyoHokokuHyoDto> sagyoHokokushoResultList) {
		this.sagyoHokokushoResultList = sagyoHokokushoResultList;
	}

	/**
	 * ���t�����̂P���擾���܂��B
	 *
	 * @return ���t�����̂P
	 */
	public String getSenderNm1() {
		return senderNm1;
	}

	/**
	 * ���t�����̂P��ݒ肵�܂��B
	 *
	 * @param senderNm1 ���t�����̂P
	 */
	public void setSenderNm1(String senderNm1) {
		this.senderNm1 = senderNm1;
	}

	/**
	 * ���t�����̂Q���擾���܂��B
	 *
	 * @return ���t�����̂Q
	 */
	public String getSenderNm2() {
		return senderNm2;
	}

	/**
	 * ���t�����̂Q��ݒ肵�܂��B
	 *
	 * @param senderNm2 ���t�����̂Q
	 */
	public void setSenderNm2(String senderNm2) {
		this.senderNm2 = senderNm2;
	}

	/**
	 * ���t���Z�����擾���܂��B
	 *
	 * @return ���t���Z��
	 */
	public String getSenderAddress() {
		return senderAddress;
	}

	/**
	 * ���t���Z����ݒ肵�܂��B
	 *
	 * @param senderAddress ���t���Z��
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	/**
	 * ���t���d�b�ԍ����擾���܂��B
	 *
	 * @return ���t���d�b�ԍ�
	 */
	public String getSenderTelNo() {
		return senderTelNo;
	}

	/**
	 * ���t���d�b�ԍ���ݒ肵�܂��B
	 *
	 * @param senderTelNo ���t���d�b�ԍ�
	 */
	public void setSenderTelNo(String senderTelNo) {
		this.senderTelNo = senderTelNo;
	}

	/**
	 * ���t��FAX�ԍ����擾���܂��B
	 *
	 * @return ���t��FAX�ԍ�
	 */
	public String getSenderFaxNo() {
		return senderFaxNo;
	}

	/**
	 * ���t��FAX�ԍ���ݒ肵�܂��B
	 *
	 * @param senderFaxNo ���t��FAX�ԍ�
	 */
	public void setSenderFaxNo(String senderFaxNo) {
		this.senderFaxNo = senderFaxNo;
	}

	/**
	 * ���[�敪���擾���܂��B
	 *
	 * @return ���[�敪
	 */
	public String getPrintKbn() {
		return printKbn;
	}

	/**
	 * ���[�敪��ݒ肵�܂��B
	 *
	 * @param printKbn ���[�敪
	 */
	public void setPrintKbn(String printKbn) {
		this.printKbn = printKbn;
	}

	/**
	 * ���[�p�X���擾���܂��B
	 *
	 * @return ���[�p�X
	 */
	public String getMakePdfPath() {
		return makePdfPath;
	}

	/**
	 * ���[�p�X��ݒ肵�܂��B
	 *
	 * @param makePdfPath ���[�p�X
	 */
	public void setMakePdfPath(String makePdfPath) {
		this.makePdfPath = makePdfPath;
	}
	
	/**
	 * ���[�����擾���܂��B
	 *
	 * @return ���[��
	 */
	public String getPdfNm() {
		return pdfNm;
	}

	/**
	 * ���[����ݒ肵�܂��B
	 *
	 * @param pdfNm ���[��
	 */
	public void setPdfNm(String pdfNm) {
		this.pdfNm = pdfNm;
	}

	/**
	 * �₢���킹�o�^��ʂ���̑J�ڂ����肵�܂��B
	 *
	 * @return true: �₢���킹�o�^��ʂ���̑J�ځAfalse:����ȊO����̑J��
	 */
	public boolean isFromInquiryEntry() {
		return Constants.GAMEN_KBN_INQUIRY_ENTRY.equals(this.dispKbn);
	}

	/**
	 * �˗��o�^��ʂ���̑J�ڂ����肵�܂��B
	 *
	 * @return true: �˗��o�^��ʂ���̑J�ځAfalse:����ȊO����̑J��
	 */
	public boolean isFromRequestEntry() {
		return Constants.GAMEN_KBN_REQUEST_ENTRY.equals(this.dispKbn);
	}
	
	/**
	 * ���[�敪���u1�F���d�񍐏��v�����肵�܂��B
	 *
	 * @return true: ���[�敪���u1�F���d�񍐏��v�̏ꍇ�Afalse�F����ȊO�̏ꍇ
	 */
	public boolean isPrintableIncomingCallReport() {
		return PRINT_KBN_INCOMING_CALL_REPORT.equals(this.printKbn);
	}

	/**
	 * ���[�敪���u2�F��ƕ񍐏��v�����肵�܂��B
	 *
	 * @return true: ���[�敪���u2�F��ƕ񍐏��v�̏ꍇ�Afalse�F����ȊO�̏ꍇ
	 */
	public boolean isPrintableWorkReport() {
		return PRINT_KBN_WORK_REPORT.equals(this.printKbn);
	}

	/**
	 * @return ��ʖ���
	 */
	public String getGamenNm() {
		String gamenNm = "";

		if (Constants.GAMEN_KBN_INQUIRY_ENTRY.equals(dispKbn)) {
			gamenNm = GAMEN_NM_INCOMING_CALL_REPORT;
		} else if(Constants.GAMEN_KBN_REQUEST_ENTRY.equals(dispKbn)) {
			gamenNm = GAMEN_NM_WORK_REPORT;
		}

		return gamenNm;
	}

	/**
	 * �֘A�t���ꗗ�őI���`�F�b�N�������l�ݒ肷��ڋq�h�c���擾���܂��B
	 *
	 * @param idx �֘A�t���ꗗ���̃C���f�b�N�X
	 * @return �����l�ݒ肷��ڋq�h�c�B
	 *         �܂��A�ȉ��̏ꍇ�͋󕶎���Ԃ��܂��B
	 *         �E�Y���s�̌ڋq�敪���u�Ǘ����(��Ɗ܂�)�v�ȊO�̏ꍇ
	 *         �E�֘A�t���ꗗ���ɁA�u�ڋq�敪�F�Ǘ����(��Ɗ܂�)�v�̌������O���A�܂��́A�Q���ȏ�̏ꍇ
	 */
	public String getInitKanrenKokyakuId(int idx) {
		RcpMKokyaku kanrenKokyaku = this.kanrenList.get(idx);
		if (!RcpMKokyaku.KOKYAKU_KBN_FUDOSAN.equals(kanrenKokyaku.getKokyakuKbn())) {
			// �Y���s�̌ڋq�敪���u�ڋq�敪�F�Ǘ����(��Ɗ܂�)�v�ȊO�̏ꍇ�A�S�ă`�F�b�NOFF
			return "";
		}

		// �֘A�t���ꗗ���́u�ڋq�敪�F�Ǘ����(��Ɗ܂�)�v�̌������擾
		String initKokyakuId = "";
		int targetCount = 0;
		for (RcpMKokyaku kokyaku : this.kanrenList) {
			if (RcpMKokyaku.KOKYAKU_KBN_FUDOSAN.equals(kokyaku.getKokyakuKbn())) {
				// �Ώی������J�E���g�A�b�v���A�ڋqID���Z�b�g
				targetCount++;
				initKokyakuId = kokyaku.getKokyakuId();
			}

			if (targetCount > 1) {
				// �Ώی������Q���ȏ�ɂȂ�����A�S�ă`�F�b�NOFF
				return "";
			}
		}

		// �Ώی������P���̏ꍇ�́A�`�F�b�NON
		return initKokyakuId;
	}
	
	/**
	 * ���[�敪���Ƃ̃��X�g���擾���܂��B
	 * 
	 * @return CSV���X�g
	 */
	public List<?> getCsvList(){
		// ���[�敪���u���d�񍐏��v�̏ꍇ
		if (isPrintableIncomingCallReport()) {
			return nyudenHokokushoResultList;
		// ���[�敪���u��ƕ񍐏��v�̏ꍇ
		} else if (isPrintableWorkReport()) {
			return sagyoHokokushoResultList;
		} else {
			return null;
		}
	}
	
	/**
	 * CSV���̂��擾���܂��B
	 * 
	 * @return CSV����
	 */
	public String getCsvNm() {
		// ���[�敪���u���d�񍐏��v�̏ꍇ
		if (isPrintableIncomingCallReport()) {
			return CSV_NM_INCOMING_CALL_REPORT;
		// ���[�敪���u��ƕ񍐏��v�̏ꍇ
		} else if (isPrintableWorkReport()) {
			return CSV_NM_WORK_REPORT;
		} else {
			return null;
		}
	}

	/**
	 * �����惊�X�g��\�����邩�𔻒肵�܂��B
	 * 
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isSeikyusakiLstDisplay() {
		if (this.seikyusakiKokyakuList != null
				&& !this.seikyusakiKokyakuList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
