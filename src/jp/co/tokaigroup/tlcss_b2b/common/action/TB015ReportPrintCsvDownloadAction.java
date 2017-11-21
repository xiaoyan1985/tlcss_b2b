package jp.co.tokaigroup.tlcss_b2b.common.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.common.CsvHeaderMaker;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dto.RC905NyudenHokokuHyoDto;
import jp.co.tokaigroup.reception.dto.RC906SagyoHokokuHyoDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.si.fw.action.CSVDownloadAction;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.tags.Functions;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.StringUtil;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB015ReportPrintService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �񍐏����CSV�_�E�����[�h�A�N�V�����N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/07
 * @version 1.1 2016/02/12 H.Yamamura ��ƕ񍐏��o�͎��ɁA�₢���킹��t���e���o�͂��Ȃ��悤�ύX
 */
@Controller
@Scope("prototype")
public class TB015ReportPrintCsvDownloadAction extends CSVDownloadAction
implements ModelDriven<TB015ReportPrintModel>{

	/** ���݂̍s���F2�s�ڕ\�����f�[�^ */
	private static final int LINE_NO_COVER_SHEET = 2;

	/** ���݂̍s���F3�s�ږ��ו��^�C�g�� */
	private static final int LINE_NO_DETAIL_TITLE = 3;

	/** ���݂̍s���F4�s�ږ��ו��f�[�^ */
	private static final int LINE_NO_DETAIL_DATA = 4;
	
	/** �敪 6:�ő����� */
	private static final int BREAKDOWN_TO_MAX = 6;
	
	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/** ��ʃ��f�� */
	private TB015ReportPrintModel model = new TB015ReportPrintModel();
	
	/** �T�[�r�X */
	@Autowired
	private TB015ReportPrintService service;
	
	/** ���݂̍s�� 2�s�ڂ���J�n */
	private int lineNo = LINE_NO_COVER_SHEET;

	/**
	 * CSV�_�E�����[�h�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("reportPrintCsvDownload")
	public String download() throws Exception {
		// �Z�b�V�������̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		try{
			// CSV����
			model = service.createCsvData(model);
			
			// �_�E�����[�h����
			super.download(model.getCsvList());
			
			return DOWNLOAD;

		} catch (ForbiddenException e) {
			if (userContext.isIPad()) {
				// iPad�̏ꍇ�́A403�G���[����ʂɕ\��
				return FORBIDDEN_ERROR;
			} else {
				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG0022");
				return MESSAGE_AND_CLOSE;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			if (userContext.isIPad()) {
				// iPad�̏ꍇ�́A500�G���[����ʂɕ\��
				return ERROR;
			} else {
				// ����ȊO�̏ꍇ�́A�A���[�g�ɃG���[���b�Z�[�W��\��
				addActionError("MSG9001");
				return MESSAGE_AND_CLOSE;
			}
		}
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB015ReportPrintModel getModel() {
		return model;
	}

	/**
	 * CSV�t�@�C���I�u�W�F�N�g�擾
	 * @return �t�@�C���I�u�W�F�N�g(�t�@�C�����ݒ�)
	 */
	protected String getCsvFileNm() {
		// �t�@�C�����擾
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date1 = new Date();
		return model.getCsvNm() + sdf1.format(date1) + ".txt";
	}

	/**
	 * ���X�g�f�[�^�擾�擾
	 *
	 * @return ���X�g�f�[�^
	 */
	protected <T> List<String> getLineData(T line) {
		return model.isPrintableIncomingCallReport() ? getNyudenHokokushoLineData(line) : getSagyoHokokushoLineData(line);
	}
	
	/**
	 * ���d�񍐏����X�g�f�[�^�擾�擾
	 *
	 * @return ���X�g�f�[�^
	 */
	protected <T> List<String> getNyudenHokokushoLineData(T line) {
		RC905NyudenHokokuHyoDto inputDto = (RC905NyudenHokokuHyoDto) line;
		List<String> lineList = new ArrayList<String>();

		if (lineNo == LINE_NO_COVER_SHEET) {
			// �\�����f�[�^�s
			// ���d�҂̌ڋq���擾
			RcpMKokyaku nyudenKokyaku = inputDto.getNyudenKokyaku();
			// �₢���킹��{���擾
			RcpTToiawase toiawase = inputDto.getToiawase();

			////////////////////////////////////////////////////////////////////////////
			// �o�͗p�ɕҏW
			////////////////////////////////////////////////////////////////////////////
			// ��t�`��
			String uketsukeKetai = inputDto.getKyotsuCodeEntity(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI, toiawase.getUketsukeKeitaiKbn()).getComVal();
			// ��t����
			String uketsukeYmd = "";
			if (toiawase.getUketsukeYmd() != null) {
				uketsukeYmd = DateUtil.formatTimestamp(toiawase.getUketsukeYmd(), Constants.DATE_FORMAT_YMD);
			}
			String uketsukeTime = "";
			if (StringUtils.isNotBlank(toiawase.getUketsukeJikan())) {
				uketsukeTime = DateUtil.hhmmPlusColon(toiawase.getUketsukeJikan());
			}
			String uketsukeDate = StringUtil.plusSpace(uketsukeYmd, uketsukeTime);
			// ���d�S����
			String nyudenTantosha = inputDto.getNatosMPasswordMap().get(toiawase.getUketsukeshaId());
			// �X�֔ԍ�
			String yubinNo = "";
			if (StringUtils.isNotBlank(nyudenKokyaku.getYubinNo())) {
				yubinNo = Functions.formatYubinNo(nyudenKokyaku.getYubinNo());
			}
			// ��������
			String kanjiNm = StringUtil.plusSpace(nyudenKokyaku.getKanjiNm1(), nyudenKokyaku.getKanjiNm2());
			// �˗���
			String iraisha = "";
			if (RcpTToiawase.IRAI_FLG_ONAJI.equals(toiawase.getIraishaFlg())) {
				iraisha = inputDto.getKyotsuCodeEntity(RcpMComCd.RCP_M_COM_CD_KBN_DOJO, toiawase.getIraishaFlg()).getComVal();
			} else {
				String iraishaNm = inputDto.getKyotsuCodeEntity(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN, toiawase.getIraishaKbn()).getComVal();
				String iraishaSexKbnNm = inputDto.getKyotsuCodeEntity(
						RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_SEX_KBN,
						toiawase.getIraishaSexKbn()).getComVal();

				if (StringUtils.isNotBlank(iraishaNm) || StringUtils.isNotBlank(iraishaSexKbnNm)) {
					iraishaNm = "(" + StringUtils.defaultString(iraishaNm) + " " + StringUtils.defaultString(iraishaSexKbnNm) + ")";
				}
				iraisha = StringUtil.plusSpace(
						StringUtils.defaultString(toiawase.getIraishaRoomNo()) + "����",
						toiawase.getIraishaNm(), iraishaNm);
			}
			// �敪
			String toiawasekbn3 = inputDto.getToiawase3Map().get(toiawase.getToiawaseKbn3());
			String toiawasekbn4 = inputDto.getToiawase4Map().get(toiawase.getToiawaseKbn4());
			String toiawaseKbn = StringUtil.plusSpace(toiawasekbn3, toiawasekbn4);

			////////////////////////////////////////////////////////////////////////////
			// �f�[�^�Z�b�g
			////////////////////////////////////////////////////////////////////////////
			// ���Ў�t�ԍ�
			lineList.add(toiawase.getToiawaseNo());
			// ��t�`��
			lineList.add(uketsukeKetai);
			// ��t����
			lineList.add(uketsukeDate);
			// ���d�S����
			lineList.add(nyudenTantosha);
			// �X�֔ԍ�
			lineList.add(yubinNo.toString());
			// �Z���P
			lineList.add(nyudenKokyaku.getJusho1());
			// �Z���Q
			lineList.add(nyudenKokyaku.getJusho2());
			// �Z���R
			lineList.add(nyudenKokyaku.getJusho3());
			// �Z���S
			lineList.add(nyudenKokyaku.getJusho4());
			// �Z���T
			String jusho5 = "";
			// �Z���T�����݂������ԍ������݂���ꍇ
			if (StringUtils.isNotBlank(nyudenKokyaku.getJusho5()) && StringUtils.isNotBlank(nyudenKokyaku.getRoomNo())) {
				jusho5 = nyudenKokyaku.getJusho5() + "�@" + nyudenKokyaku.getRoomNo();
			// �Z���T�̂ݑ��݂���ꍇ
			} else if (StringUtils.isNotBlank(nyudenKokyaku.getJusho5())) {
				jusho5 = nyudenKokyaku.getJusho5();
			// �����ԍ��̂ݑ��݂���ꍇ
			} else if (StringUtils.isNotBlank(nyudenKokyaku.getRoomNo())) {
				jusho5 = nyudenKokyaku.getRoomNo();
			}
			lineList.add(jusho5);
			// ��������
			lineList.add(kanjiNm);
			// �˗���
			lineList.add(iraisha);
			// �敪
			lineList.add(toiawaseKbn);
			// ��t���e
			lineList.add(toiawase.getToiawaseNaiyo());
		} else if (lineNo == LINE_NO_DETAIL_TITLE) {
			// ���ו��^�C�g���s
			String[] detailLine = getMeisaiTitle();
			// ���o���o��
			if (detailLine != null && detailLine.length > 0) {
				for (String midasi : detailLine) {
					lineList.add(midasi);
				}
			}
		} else if (lineNo >= LINE_NO_DETAIL_DATA) {
			// ���ו��f�[�^�s
			// �₢���킹�������擾
			RcpTToiawaseRireki entitiy = inputDto.getToiawaseRirekiList().get(lineNo - LINE_NO_DETAIL_DATA);

			////////////////////////////////////////////////////////////////////////////
			// �o�͗p�ɕҏW
			////////////////////////////////////////////////////////////////////////////
			// ����
			String nyudenUketsukeYmd = "";
			if (entitiy.getUketsukeYmd() != null) {
				nyudenUketsukeYmd = DateUtil.formatTimestamp(entitiy.getUketsukeYmd(), Constants.DATE_FORMAT_YMD);
			}
			String nyudenUketsukeTime = "";
			if (StringUtils.isNotBlank(entitiy.getUketsukeJikan())) {
				nyudenUketsukeTime = DateUtil.hhmmPlusColon(entitiy.getUketsukeJikan());
			}
			String nitiji = StringUtil.plusSpace(nyudenUketsukeYmd, nyudenUketsukeTime);
			// �S����
			String tantosha = "";
			// �S���Җ������݂���ꍇ
			if (StringUtils.isNotBlank(entitiy.getTantoshaNm())) {
				tantosha = entitiy.getTantoshaNm();
			} else {
				tantosha = inputDto.getNatosMPasswordMap().get(entitiy.getTantoshaId());
			}
			// ��
			String jokyo = inputDto.getJoKyoKbnMap().get(entitiy.getJokyoKbn());

			////////////////////////////////////////////////////////////////////////////
			// �f�[�^�Z�b�g
			////////////////////////////////////////////////////////////////////////////
			// ����
			lineList.add(nitiji);
			// ��
			lineList.add(jokyo);
			// �S����
			lineList.add(tantosha);
			// �₢���킹���e
			lineList.add(entitiy.getToiawaseNaiyo());
		}

		// ���̍s��
		lineNo++;

		return lineList;
	}
	
	/**
	 * ��ƕ񍐏����X�g�f�[�^�擾�擾
	 *
	 * @return ���X�g�f�[�^
	 */
	protected <T> List<String> getSagyoHokokushoLineData(T line) {
		RC906SagyoHokokuHyoDto inputDto = (RC906SagyoHokokuHyoDto) line;
		List<String> lineList = new ArrayList<String>();

		// ���Ў�t�ԍ�
		lineList.add(inputDto.getToiawaseNo());
		// ��t�`��
		lineList.add(inputDto.getUketsukeKeitai());
		// ��t����
		lineList.add(inputDto.getUketsukeYmd());
		// ���d�S����
		lineList.add(inputDto.getNyudenTantosha());
		// �X�֔ԍ�
		lineList.add(inputDto.getYubinNo());
		// �Z��
		lineList.add(inputDto.getJusho());
		// ��������
		lineList.add(inputDto.getKanjiNm());
		// �˗���
		lineList.add(inputDto.getIraisha());
		// �敪
		lineList.add(inputDto.getToiawaseKbn());

		// ��Ɗ�����
		lineList.add(inputDto.getSagyoKanryoYmd());
		// ��
		lineList.add(inputDto.getSagyoJokyo());
		// ����
		lineList.add(inputDto.getSagyoCause());
		// ���{���e
		lineList.add(inputDto.getSagyoJisshiNaiyo());

		for(int i = 0; i < BREAKDOWN_TO_MAX; ++i){
			// ������e
			lineList.add(inputDto.getUchiwakeNaiyo()[i]);
			// ����
			lineList.add(inputDto.getUchiwakeSuryo()[i]);
			// �P��
			lineList.add(inputDto.getUchiwakeRiekiTanka()[i]);
			// ���z
			lineList.add(inputDto.getUchiwakeRiekiShokei()[i]);
		}

		// ���v(�ō���)
		lineList.add(inputDto.getGokeiSeikyugaku());
		// �����於
		lineList.add(inputDto.getSeikyusakiNm());
		// ������Z��
		lineList.add(inputDto.getSeikyusakiJusho());
		// ������TEL
		lineList.add(inputDto.getSeikyusakiTel());
		// ������FAX
		lineList.add(inputDto.getSeikyusakiFax());


		return lineList;
	}
	
	/**
	 * CSV���ו��^�C�g���擾
	 *
	 * @return CSV���ו��^�C�g���s
	 */
	protected String[] getMeisaiTitle() {
		try {
			return CsvHeaderMaker.makeNyudenHokokuHyoMeisaiCsvHeader();
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * CSV�\�����^�C�g���擾
	 *
	 * @return CSV�\�����^�C�g���s
	 */
	protected String[] getTitle() {
		try {
			return model.isPrintableIncomingCallReport() ? CsvHeaderMaker.makeNyudenHokokuHyoHyoshiCsvHeader() : CsvHeaderMaker.makeSagyoHokokuHyoCsvHeader();
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}
}
