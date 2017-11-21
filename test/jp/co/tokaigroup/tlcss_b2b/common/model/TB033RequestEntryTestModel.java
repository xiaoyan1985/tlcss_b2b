package jp.co.tokaigroup.tlcss_b2b.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.common.model.RC000CommonModel;
import jp.co.tokaigroup.reception.context.ReceptionUserContext;
import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.NatosMYubinNo;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;

public class TB033RequestEntryTestModel extends RC000CommonModel {
	/** ��ʖ� */
	public static final String GAMEN_NM = "�˗����o�^";

	/** �{�^���� ��ƈ˗����[�����M */
	public static final String BUTTON_NM_IRAI_MAIL = "��ƈ˗����[�����M";

	/** �������� */
 	private List<NatosMYubinNo> result;

	/** hidden�₢���킹�m�n */
	private String toiawaseNo;

	/** hidden�₢���킹�X�V�� */
	private Timestamp toiawaseUpdDt;

	/** hidden����NO */
	private BigDecimal toiawaseRirekiNo;

	/** hidden�˗��X�V�� */
	private Timestamp iraiUpdDt;

	/** hidden�����敪 */
	private String shoriKbn;

	/** �˗��e�[�u��Entity */
	private RcpTIrai irai;

	/** �˗����ŏI�X�V��ID �a�� */
	private String iraiLastUpdNm;

	/** �p�X���[�h�}�X�^Entity(�ŏI�X�V��ID) */
	private NatosMPassword natosMPassword;

	/** �K��\������X�g */
	private List<RcpMComCd> homonYoteiYmdList;

	/** �p�X���[�h�}�X�^Entity���X�g */
	private List<NatosMPassword> natosMPasswordList;

	/** �˗��Ǝ҃}�X�^���X�g */
	private RcpMGyosha gyosha;

	/** �₢���킹���Entity */
	private RcpTToiawase toiawase;

	/** �o�^����t���O(�˗��o�^) true�F���� false�F�ُ�*/
	private boolean insertNormalFlg;

	/** �o�^����t���O(�˗��o�^) true�F���� false�F�ُ�*/
	private boolean updateNormalFlg;

	/** �o�^�E�X�V����t���O(��Ə�) true�F���� false�F�ُ�*/
	private boolean sagyoUpdateNormalFlg;

	/** �폜���s���t�@�C�������X�g(��Ə�)*/
	private List<String> errorDeleteFileNmList;

	/** ���O�C�����[�U��� */
	private ReceptionUserContext context;

	/** �e��ʉ�ʋ敪 */
	private String openerDispKbn;

	/** �˗����폜�t���O */
	private boolean iraiDeleteSuccessFlg;

	/** ��Ə󋵍폜�t���O */
	private boolean sagyoJokyoDeleteSuccessFlg;

	/** �e��ʖ� */
	private String toiawaseWindowName;

	/** ����ʖ� */
	private String iraiWindowName;

	/** �p�X���[�h�}�X�^Entity(�ŏI�����ID) */
	private NatosMPassword natosPasswordLastPrintId;

	/** �񍐏�PDFURL */
	private String pdfUrl;

	/** �Ǝ҃R�[�h */
	private String gyoshaCd;

	/** ��O�����t���O */
	private boolean exceptionFlg;

	/** �ڋq�}�X�^��� */
	private RcpMKokyaku kokyaku;
	
	/**
	 * �������ʂ��擾���܂��B
	 *
	 * @return ��������
	 */
	public List<NatosMYubinNo> getResult() {
		return result;
	}

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
	 * @return toiawaseUpdDt
	 */
	public Timestamp getToiawaseUpdDt() {
		return toiawaseUpdDt;
	}


	/**
	 * @param toiawaseUpdDt �Z�b�g���� toiawaseUpdDt
	 */
	public void setToiawaseUpdDt(Timestamp toiawaseUpdDt) {
		this.toiawaseUpdDt = toiawaseUpdDt;
	}


	/**
	 * @return toiawaseRirekiNo
	 */
	public BigDecimal getToiawaseRirekiNo() {
		return toiawaseRirekiNo;
	}


	/**
	 * @param toiawaseRirekiNo �Z�b�g���� toiawaseRirekiNo
	 */
	public void setToiawaseRirekiNo(BigDecimal toiawaseRirekiNo) {
		this.toiawaseRirekiNo = toiawaseRirekiNo;
	}


	/**
	 * @return iraiUpdDt
	 */
	public Timestamp getIraiUpdDt() {
		return iraiUpdDt;
	}


	/**
	 * @param iraiUpdDt �Z�b�g���� iraiUpdDt
	 */
	public void setIraiUpdDt(Timestamp iraiUpdDt) {
		this.iraiUpdDt = iraiUpdDt;
	}


	/**
	 * @return shoriKbn
	 */
	public String getShoriKbn() {
		return shoriKbn;
	}


	/**
	 * @param shoriKbn �Z�b�g���� shoriKbn
	 */
	public void setShoriKbn(String shoriKbn) {
		this.shoriKbn = shoriKbn;
	}


	/**
	 * @return irai
	 */
	public RcpTIrai getIrai() {
		return irai;
	}


	/**
	 * @param irai �Z�b�g���� irai
	 */
	public void setIrai(RcpTIrai irai) {
		this.irai = irai;
	}


	/**
	 * @return iraiLastUpdNm
	 */
	public String getIraiLastUpdNm() {
		return iraiLastUpdNm;
	}


	/**
	 * @param iraiLastUpdNm �Z�b�g���� iraiLastUpdNm
	 */
	public void setIraiLastUpdNm(String iraiLastUpdNm) {
		this.iraiLastUpdNm = iraiLastUpdNm;
	}


	/**
	 * @return natosMPassword
	 */
	public NatosMPassword getNatosMPassword() {
		return natosMPassword;
	}


	/**
	 * @param natosMPassword �Z�b�g���� natosMPassword
	 */
	public void setNatosMPassword(NatosMPassword natosMPassword) {
		this.natosMPassword = natosMPassword;
	}


	/**
	 * @return homonYoteiYmdList
	 */
	public List<RcpMComCd> getHomonYoteiYmdList() {
		return homonYoteiYmdList;
	}


	/**
	 * @param homonYoteiYmdList �Z�b�g���� homonYoteiYmdList
	 */
	public void setHomonYoteiYmdList(List<RcpMComCd> homonYoteiYmdList) {
		if (homonYoteiYmdList != null) {
			this.homonYoteiYmdList = homonYoteiYmdList;
		} else {
			this.homonYoteiYmdList = new ArrayList<RcpMComCd>();
		}
	}


	/**
	 * @return natosMPasswordList
	 */
	public List<NatosMPassword> getNatosMPasswordList() {
		return natosMPasswordList;
	}


	/**
	 * @param natosMPasswordList �Z�b�g���� natosMPasswordList
	 */
	public void setNatosMPasswordList(List<NatosMPassword> natosMPasswordList) {
		this.natosMPasswordList = natosMPasswordList;
	}


	/**
	 * @return toiawase
	 */
	public RcpTToiawase getToiawase() {
		return toiawase;
	}


	/**
	 * @param toiawase �Z�b�g���� toiawase
	 */
	public void setToiawase(RcpTToiawase toiawase) {
		this.toiawase = toiawase;
	}


	/**
	 * @return insertNormalFlg
	 */
	public boolean isInsertNormalFlg() {
		return insertNormalFlg;
	}


	/**
	 * @param insertNormalFlg �Z�b�g���� insertNormalFlg
	 */
	public void setInsertNormalFlg(boolean insertNormalFlg) {
		this.insertNormalFlg = insertNormalFlg;
	}


	/**
	 * @return updateNormalFlg
	 */
	public boolean isUpdateNormalFlg() {
		return updateNormalFlg;
	}


	/**
	 * @param updateNormalFlg �Z�b�g���� updateNormalFlg
	 */
	public void setUpdateNormalFlg(boolean updateNormalFlg) {
		this.updateNormalFlg = updateNormalFlg;
	}


	/**
	 * @return sagyoUpdateNormalFlg
	 */
	public boolean isSagyoUpdateNormalFlg() {
		return sagyoUpdateNormalFlg;
	}


	/**
	 * @param sagyoUpdateNormalFlg �Z�b�g���� sagyoUpdateNormalFlg
	 */
	public void setSagyoUpdateNormalFlg(boolean sagyoUpdateNormalFlg) {
		this.sagyoUpdateNormalFlg = sagyoUpdateNormalFlg;
	}


	/**
	 * @return errorDeleteFileNmList
	 */
	public List<String> getErrorDeleteFileNmList() {
		return errorDeleteFileNmList;
	}


	/**
	 * @param errorDeleteFileNmList �Z�b�g���� errorDeleteFileNmList
	 */
	public void setErrorDeleteFileNmList(List<String> errorDeleteFileNmList) {
		this.errorDeleteFileNmList = errorDeleteFileNmList;
	}


	/**
	 * @return gyosha
	 */
	public RcpMGyosha getGyosha() {
		return gyosha;
	}


	/**
	 * @param gyosha �Z�b�g���� gyosha
	 */
	public void setGyosha(RcpMGyosha gyosha) {
		this.gyosha = gyosha;
	}

	/**
	 * ���O�C�����[�U���擾���܂��B
	 *
	 * @return ���O�C�����[�U
	 */
	public ReceptionUserContext getContext() {
		return context;
	}

	/**
	 * ���O�C�����[�U��ݒ肵�܂��B
	 *
	 * @param context ���O�C�����[�U
	 */
	public void setContext(ReceptionUserContext context) {
		this.context = context;
	}


	/**
	 * @return openerDispKbn
	 */
	public String getOpenerDispKbn() {
		return openerDispKbn;
	}


	/**
	 * @param openerDispKbn �Z�b�g���� openerDispKbn
	 */
	public void setOpenerDispKbn(String openerDispKbn) {
		this.openerDispKbn = openerDispKbn;
	}


	/**
	 * @return iraiDeleteSuccessFlg
	 */
	public boolean isIraiDeleteSuccessFlg() {
		return iraiDeleteSuccessFlg;
	}


	/**
	 * @param iraiDeleteSuccessFlg �Z�b�g���� iraiDeleteSuccessFlg
	 */
	public void setIraiDeleteSuccessFlg(boolean iraiDeleteSuccessFlg) {
		this.iraiDeleteSuccessFlg = iraiDeleteSuccessFlg;
	}


	/**
	 * @return sagyoJokyoDeleteSuccessFlg
	 */
	public boolean isSagyoJokyoDeleteSuccessFlg() {
		return sagyoJokyoDeleteSuccessFlg;
	}


	/**
	 * @param sagyoJokyoDeleteSuccessFlg �Z�b�g���� sagyoJokyoDeleteSuccessFlg
	 */
	public void setSagyoJokyoDeleteSuccessFlg(boolean sagyoJokyoDeleteSuccessFlg) {
		this.sagyoJokyoDeleteSuccessFlg = sagyoJokyoDeleteSuccessFlg;
	}


	/**
	 * @return toiawaseWindowName
	 */
	public String getToiawaseWindowName() {
		return toiawaseWindowName;
	}


	/**
	 * @param toiawaseWindowName �Z�b�g���� toiawaseWindowName
	 */
	public void setToiawaseWindowName(String toiawaseWindowName) {
		this.toiawaseWindowName = toiawaseWindowName;
	}


	/**
	 * @return iraiWindowName
	 */
	public String getIraiWindowName() {
		return iraiWindowName;
	}


	/**
	 * @param iraiWindowName �Z�b�g���� iraiWindowName
	 */
	public void setIraiWindowName(String iraiWindowName) {
		this.iraiWindowName = iraiWindowName;
	}


	/**
	 * @return natosPasswordLastPrintId
	 */
	public NatosMPassword getNatosPasswordLastPrintId() {
		return natosPasswordLastPrintId;
	}


	/**
	 * @param natosPasswordLastPrintId �Z�b�g���� natosPasswordLastPrintId
	 */
	public void setNatosPasswordLastPrintId(NatosMPassword natosPasswordLastPrintId) {
		this.natosPasswordLastPrintId = natosPasswordLastPrintId;
	}


	/**
	 * �񍐏�PDFURL���擾���܂��B
	 *
	 * @return �񍐏�PDFURL
	 */
	public String getPdfUrl() {
		return pdfUrl;
	}

	/**
	 * �񍐏�PDFURL��ݒ肵�܂��B
	 *
	 * @param pdfUrl �񍐏�PDFURL
	 */
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	/**
	 * �Ǝ҃R�[�h���擾���܂��B
	 *
	 * @return �Ǝ҃R�[�h
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
	 * ��O�����t���O���擾���܂��B
	 *
	 * @return ��O�����t���O
	 */
	public boolean isExceptionFlg() {
		return exceptionFlg;
	}
	/**
	 * ��O�����t���O��ݒ肵�܂��B
	 *
	 * @param exceptionFlg ��O�����t���O
	 */
	public void setExceptionFlg(boolean exceptionFlg) {
		this.exceptionFlg = exceptionFlg;
	}

	/**
	 * �ڋq�}�X�^�����擾���܂��B
	 *
	 * @return �ڋq�}�X�^���
	 */
	public RcpMKokyaku getKokyaku() {
		return kokyaku;
	}
	/**
	 * �ڋq�}�X�^����ݒ肵�܂��B
	 *
	 * @param kokyaku �ڋq�}�X�^���
	 */
	public void setKokyaku(RcpMKokyaku kokyaku) {
		this.kokyaku = kokyaku;
	}

	/**
	 * �e��ʂ��ĕ\�����邩�𔻒肵�܂��B
	 * �ĕ\���������e��ʂ��������ꍇ�́A������ǉ����Ă��������B
	 *
	 * @return true:�ĕ\������Afalse:�ĕ\�����Ȃ�
	 */
	public boolean isOpenerReload() {
		if (Constants.FROM_DISP_KBN_TOIAWASE_RIREKI_ENTRY.equals(openerDispKbn)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * �ڋq�ڍ׉�ʂ�\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isKokyakuDetailDisplay() {
		return (StringUtils.isNotBlank(toiawase.getKokyakuId()));
	}


	/**
	 * ID�����ڋq����ʂ�\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isKokyakuWithNoIdInfoDisplay() {
		return (StringUtils.isBlank(toiawase.getKokyakuId()));
	}

	/**
	 * ���ߔN�������͂���Ă��邩���肵�܂��B
	 *
	 * @return true:���ߔN�������͂���Ă���(not null)�Afalse:���ߔN�������͂���Ă��Ȃ�(null)
	 */
	public boolean isCheckedShimeYmFlg() {
		return (toiawase.getShimeYm() != null);
	}

	/**
	 * �ŏI�����ID�����݂��邩���肵�܂��B
	 *
	 * @return true:�ŏI�����ID�����݂���Afalse:�ŏI�����ID�����݂��Ȃ�
	 */
	public boolean isExistLastPrintId() {
		return (StringUtils.isNotBlank(irai.getLastPrintId()));
	}

	/**
	 * �˗����J�t���O�Ɋ܂ރ`�F�b�N���`�F�b�N����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:�`�F�b�N����Ă���Afalse:�`�F�b�N����Ă��Ȃ�
	 */
	public boolean isCheckedIraiKokaiFlg() {
		if (this.irai == null) {
			// �f�t�H���g�́A�`�F�b�NON
			return false;
		}

		if (StringUtils.isBlank(this.irai.getIraiKokaiFlg())) {
			// �f�t�H���g�́A�`�F�b�NON
			return false;
		}

		return (RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI.equals(this.irai.getIraiKokaiFlg()));
	}

	/**
	 * ��ƈ˗����[�������M�\���̃R�����g��\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isSagyoIraiMailCommentDisplay() {
		if (this.irai == null) {
			// �˗���񂪂Ȃ��ꍇ�́A�R�����g�\�����Ȃ�
			return false;
		}

		// �X�V�̏ꍇ�A�Ǝҏ����`�F�b�N
		if (gyosha == null) {
			// �Ǝҏ�񂪂Ȃ��ꍇ�́A�R�����g�\�����Ȃ�
			return false;
		}

		// ��ƈ˗����[�����t�L�����u1:�������M����v�̏ꍇ�A�R�����g�\��
		return gyosha.isSagyoIraiMailSendable();
	}

	/**
	 * TORES���J���[�����M�{�^����\�����邩�𔻒肵�܂��B
	 *
	 * @return true�F�\������Afalse�F�\�����Ȃ�
	 */
	public boolean isVisiblePublishMail() {
		if (this.irai == null) {
			// �˗���񂪂Ȃ��ꍇ�́A��\��
			return false;
		}
		
		if (!isUpdate()) {
			// �X�V�łȂ��ꍇ�i�V�K�o�^�Ȃǁj�A�R�����g�\�����Ȃ�
			return false;
		}

		if (this.kokyaku == null) {
			// �ڋq��񂪂Ȃ��ꍇ�́A��\��
			return false;
		}

		if (!RcpTIrai.KOKAI_FLG_KOUKAIZUMI.equals(this.irai.getIraiKokaiFlg())) {
			// �˗���񂪌��J�ψȊO�́A��\��
			return false;
		}

		// �ڋq�敪���u1�F�Ǘ����(��Ɗ܂�)�v�u3�F�����v�u4�F�����ҁE�l�v�̏ꍇ�́A�\��
		// ����ȊO�͔�\��
		return this.kokyaku.isKokyakuKbnFudosan() ||
				this.kokyaku.isKokyakuKbnBukken() ||
				this.kokyaku.isKokyakuKbnNyukyosha();
	}
	
}
