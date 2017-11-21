package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMSashidashininDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyohiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dto.RC905NyudenHokokuHyoDto;
import jp.co.tokaigroup.reception.dto.RC906SagyoHokokuHyoDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSashidashinin;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTSagyohi;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.kokyaku.model.RC016KokyakuKanrenEntrySearchCondition;
import jp.co.tokaigroup.reception.print.logic.RC905NyudenHokokuHyoLogic;
import jp.co.tokaigroup.reception.print.logic.RC906SagyoHokokuHyoLogic;
import jp.co.tokaigroup.reception.print.logic.RC912PhotoReportLogic;
import jp.co.tokaigroup.reception.util.FileRemoteUtil;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB015ReportPrintModel;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �񍐏�����T�[�r�X�����N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/05
 * @version 1.1 2016/07/08 H.Hirai ����������Ή�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB015ReportPrintServiceImpl extends TLCSSB2BBaseService implements TB015ReportPrintService {

	/** �R�s�[���A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_PDF = ResourceFactory.getResource().getString("PDF_PATH");
	/** �R�s�[��A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_INCOMING_CALL_REPORT = ResourceFactory.getResource().getString("UPLOAD_PATH_INCOMING_CALL_REPORT_FILE");
	
	/** �₢���킹���sDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;
	
	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;
	
	/** ���Z�v�V�����ڋq�_����}�X�^DAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;
	
	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;
	
	/** ���Z�v�V�������o�l���}�X�^DAO */
	@Autowired
	private RcpMSashidashininDao sashidashininDao;
	
	/** ��Ɣ�sDAO */
	@Autowired
	private RcpTSagyohiDao sagyohiDao;
	
	/** �ϑ���Њ֘A�`�F�b�N���W�b�N */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	
	/** ���[ ���d�񍐏��@���W�b�N�N���X */
	@Autowired
	private RC905NyudenHokokuHyoLogic nyudenHokokuHyoLogic;
	
	/** ���[ ��ƈ˗����@���W�b�N�N���X */
	@Autowired
	private RC906SagyoHokokuHyoLogic sagyoHokokuHyoLogic;
	
	/** �ʐ^�񍐏����W�b�N�N���X */
	@Autowired
	private RC912PhotoReportLogic photoReportLogic;

	/**
	 * �������擾�������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB015ReportPrintModel getInitInfo(TB015ReportPrintModel model) {
		
		// �₢���킹���̎擾
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		
		// �₢���킹��񂪑��݂��Ȃ��ꍇ�͏������I��
		if (toiawase == null){
			throw new ForbiddenException("�₢���킹��񂪎擾�ł��܂���B");
		}
		
		// �Z�b�V���������擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// ���������̐ݒ�
		RC016KokyakuKanrenEntrySearchCondition condition = new RC016KokyakuKanrenEntrySearchCondition();
		// �₢���킹�e�[�u���̌ڋqID��ݒ�
		condition.setKokyakuId(toiawase.getKokyakuId());
		// �ڋq�敪���X�g��ݒ�
		List<String> notInKokyakukbnList = new ArrayList<String>();
		notInKokyakukbnList.add(RcpMKokyaku.KOKYAKU_KBN_NYUKYOSHA);
		condition.setNotInKokyakuKbnList(notInKokyakukbnList);
		// �y�[�W�ԍ���ݒ�
		condition.setOffset(1);
		// 1�y�[�W�ő�\��������ݒ�
		condition.setLimit(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_HOKOKUSHO_PRINT_SELECT_TO_MAX));
		// �ő匟���\������ݒ�
		condition.setMaxCount(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_HOKOKUSHO_PRINT_PAGE_TO_MAX));
		// �\���ő�l�܂ŉ�ʂɕ\���ݒ��true�Őݒ�
		condition.setDisplayToMax(true);
		// ����������ݒ�
		model.setCondition(condition);
		
		// �����������s
		List<RcpMKokyaku> kanrenList = kokyakuDao.selectKanrenList(condition);
		// �������ʂ��O���̏ꍇ
		if (kanrenList == null) {
			kanrenList = new ArrayList<RcpMKokyaku>();
		}
		
		// �a���ϊ��p���̎擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN, RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// �a���ϊ��pMap
		Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);
		
		// �_��I�������p�ڋq�h�c���X�g�쐬
		List<String> kokyakuIdList = new ArrayList<String>();
		for (RcpMKokyaku kokyakuInfo : kanrenList) {
			kokyakuIdList.add(kokyakuInfo.getKokyakuId());
		}

		// �_�񂪏I�����Ă���ڋq�̎擾
		Map<String, String> endKokyakuMap = new HashMap<String, String>();
		if (kokyakuIdList != null && !kokyakuIdList.isEmpty()) {
			endKokyakuMap = kokyakuKeiyakuDao.selectKeiyakuKanriEndKokyaku(kokyakuIdList);
		}
		
		// �擾���ʂɑ΂��āA�a���ϊ��ƌ_��I���t���O�̐ݒ���s��
		for (RcpMKokyaku kokyakuInfo : kanrenList) {
			// �ڋq�敪�̘a���ϊ�
			Map<String, RcpMComCd> kokyakuKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
			// �ڋq��ʂ̘a���ϊ�
			Map<String, RcpMComCd> kokyakuShubetsuMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);

			// �ڋq�敪���ڋq�敪Map�ɑ��݂���ꍇ�ݒ�
			if (kokyakuKbnMap.containsKey(kokyakuInfo.getKokyakuKbn())) {
				kokyakuInfo.setKokyakuKbnNm(kokyakuKbnMap.get(kokyakuInfo.getKokyakuKbn()).getExternalSiteVal());
			}
			// �ڋq��ʂ��ڋq���Map�ɑ��݂���ꍇ�ݒ�
			if (kokyakuShubetsuMap.containsKey(kokyakuInfo.getKokyakuShubetsu())) {
				kokyakuInfo.setKokyakuShubetsuNm(kokyakuShubetsuMap.get(kokyakuInfo.getKokyakuShubetsu()).getExternalSiteVal());
			}

			if (endKokyakuMap.containsKey(kokyakuInfo.getKokyakuId())) {
				// �_�񂪏I�����Ă���ڋq�̏ꍇ
				if (StringUtils.isNotBlank(endKokyakuMap.get(kokyakuInfo.getKokyakuId()))) {
					kokyakuInfo.setEndKeiyaku(true);
				}
			}
		}
		
		model.setKanrenList(kanrenList);

		// ���o�l�����擾
		String kaishaId;

		if (userContext.isOutsourcerOp() || userContext.isOutsourcerSv()) {
			// �Z�b�V�������̌������ϑ����SV�܂��͈ϑ����OP�̏ꍇ�A�Z�b�V�����̉��ID���Z�b�g
			kaishaId = userContext.getKaishaId();
		} else {
			// �Z�b�V�����̃V�X�e���萔Map����s�n�j�`�h��Ђh�c�̒l���擾���ăZ�b�g
			kaishaId = userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_TOKAI_KAISHA_ID);
		}

		RcpMSashidashinin sashidashinin = sashidashininDao.selectByPrimaryKey(RcpMSashidashinin.KBN_NYUDEN_HOKOKU_SASHIDASHININ_KBN, kaishaId);
		model.setSashidashinin(sashidashinin);

		// ������ڋq��񃊃X�g�擾
		List<RcpMKokyaku> seikyusakiKokyakuList =
			kokyakuDao.selectSeikyusakiKokyakuList(model.getKokyakuId());
		model.setSeikyusakiKokyakuList(seikyusakiKokyakuList);

		// �˗��o�^��ʂ���̑J�ڂ̏ꍇ
		if (model.isFromRequestEntry()) {
			// ��Ɣ������擾
			RcpTSagyohi sagyohi = sagyohiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
			model.setSagyohi(sagyohi);
		}
		
		// ���[�敪�̐ݒ�
		String initPrintKbn = model.isFromInquiryEntry() ? TB015ReportPrintModel.PRINT_KBN_INCOMING_CALL_REPORT : TB015ReportPrintModel.PRINT_KBN_WORK_REPORT ;
		model.setPrintKbn(initPrintKbn);

		return model;
	}

	/**
	 * �����������[�̃p�X���擾���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return �����������[�p�X
	 */
	public TB015ReportPrintModel createPdf(TB015ReportPrintModel model) {
		
		// �R���e���c���̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// ���[�������̃p�X
		String pdfPath = null;

		// �Z�L�����e�B�`�F�b�N�̎��{
		executeSecurityCheck(model, userContext);
		
		// ���[�敪���u���d�񍐏��v�̏ꍇ
		if (model.isPrintableIncomingCallReport()) {
			// ���d�񍐏�
			List<String[]> allDataList =
				nyudenHokokuHyoLogic.getNyudenHokokuHyoCsvList(model.getToiawaseNo(),
						model.getKokyakuId(), model.getSenderNm1(),
						model.getSenderNm2(), model.getSenderAddress(),
						model.getSenderTelNo(), model.getSenderFaxNo(), null);

			// PDF�o�́i�}���`�o�́j
			pdfPath = nyudenHokokuHyoLogic.outputPdf(allDataList, true, true);
		// ���[�敪���u��ƕ񍐏��v�̏ꍇ
		} else if (model.isPrintableWorkReport()) {
			// ��ƕ񍐏�
			List<String[]> allDataList =
				sagyoHokokuHyoLogic.getSagyoHokokuCsvList(model.getToiawaseNo(),
						model.getKokyakuId(), model.getToiawaseRirekiNo(),
						model.getSenderNm1(), model.getSenderNm2(),
						model.getSenderAddress(), model.getSenderTelNo(),
						model.getSenderFaxNo());
			// �ʐ^�񍐏�
			if (photoReportLogic.isExistUploadFile(model.getToiawaseNo(),
					new BigDecimal(model.getToiawaseRirekiNo()))) {
				//�A�b�v���[�h�t�@�C�������݂���ꍇ
				List<String[]> photoReportDataList =
					photoReportLogic.getPhotoReportCsvList(model.getToiawaseNo(),
							new BigDecimal(model.getToiawaseRirekiNo()), model.getKokyakuId(),
							model.getSenderNm1(), model.getSenderNm2(),
							model.getSenderAddress(), model.getSenderTelNo(),
							model.getSenderFaxNo());
				//�ʐ^�񍐏��f�[�^����ƕ񍐏��f�[�^�ɉ�����
				allDataList.addAll(photoReportDataList);
			}

			// PDF�o��(CSV�o�͂������ɍs��)
			pdfPath = sagyoHokokuHyoLogic.outputPdf(allDataList, true, true);
		// ��L�ȊO�̏ꍇ�͗�O
		} else {
			throw new ApplicationException("���[�敪�s���B�p�����[�^�̒��[�敪" );
		}
		// �擾�����p�X����t�@�C�������擾����
		String pdfNm = pdfPath.substring(pdfPath.lastIndexOf("/") + 1);
		
		// �R�s�[���t�@�C�����̃p�X��ݒ�
		String fromCopyPdfPath = UPLOAD_PATH_PDF + System.getProperty("file.separator") + pdfNm;
		// �R�s�[��t�@�C�����̃p�X��ݒ�
		String toCopyPdfPath = UPLOAD_PATH_INCOMING_CALL_REPORT + System.getProperty("file.separator") + pdfNm;
		
		// �t�@�C���R�s�[�̎��{
		FileRemoteUtil.remoteCopyFileBySsh(fromCopyPdfPath, toCopyPdfPath);
		model.setMakePdfPath(toCopyPdfPath);
		model.setPdfNm(pdfNm);
		
		return model;
	}
	
	/**
	 * CSV�o�͏����擾���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB015ReportPrintModel createCsvData(TB015ReportPrintModel model) {
		// �R���e���c���̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// �Z�L�����e�B�`�F�b�N��NG�̏ꍇ
		executeSecurityCheck(model, userContext);

		// ���[�敪���u���d�񍐏��v�̏ꍇ
		if (model.isPrintableIncomingCallReport()) {
			// ���d�񍐏�
			RC905NyudenHokokuHyoDto inputDto =
				nyudenHokokuHyoLogic.getNyudenHokokuHyoCsvInfo(model.getToiawaseNo(),
					model.getKokyakuId(), model.getSenderNm1(),
					model.getSenderNm2(), model.getSenderAddress(),
					model.getSenderTelNo(), model.getSenderFaxNo(), null);

			// CSV�o�͗p�ɕϊ�
			model.setNyudenHokokushoResultList(toCsvByIncomingCallReport(inputDto, userContext));
		// ���[�敪���u��ƕ񍐏��v�̏ꍇ
		} else if (model.isPrintableWorkReport()) {
			// ��ƕ񍐏�
			RC906SagyoHokokuHyoDto inputDto =
				sagyoHokokuHyoLogic.createOutputData(model.getToiawaseNo(),
						model.getKokyakuId(), model.getToiawaseRirekiNo(),
						model.getSenderNm1(), model.getSenderNm2(),
						model.getSenderAddress(), model.getSenderTelNo(),
						model.getSenderFaxNo());

			// CSV�o�͗p�ɕϊ�
			model.setSagyoHokokushoResultList(toCsvByWorkReport(inputDto));
		// ��L�ȊO�̏ꍇ�͗�O
		} else {
			throw new ApplicationException("���[�敪�s���B�p�����[�^�̒��[�敪" );
		}

		return model;
	}
	
	/**
	 * �Z�L�����e�B�`�F�b�N���s���܂��B
	 * 
	 * @param model ��ʃ��f��
	 * @param userContext �R���e���c���
	 * @return �������ʁitrue�F����Afalse�F�ُ�j
	 */
	private void  executeSecurityCheck(TB015ReportPrintModel model, TLCSSB2BUserContext userContext){
		// �₢���킹���̎擾
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		
		// �₢���킹��񂪑��݂��Ȃ��ꍇ
		if (toiawase == null){
			throw new ForbiddenException("�₢���킹��񂪑��݂��܂���B");
		}

		// �Z�b�V�����̌������u40:�ϑ����SV�v�܂��́u41:�ϑ����OP�v�̏ꍇ
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// �ϑ��֘A��Ѓ`�F�b�N��NG�̏ꍇ
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), toiawase.getKokyakuId())){
				throw new ForbiddenException("�A�N�Z�X���������݂��܂���B");
			}
		}
	}
	
	/**
	 * ���d�񍐏�CSV�o�͗p��CSV�o�͏���ϊ����܂��B
	 *
	 * @param inputDto ���d�񍐏�CSV�o�͏��
	 * @return �ϊ�����d�񍐏�CSV�o�͏��
	 */
	private List<RC905NyudenHokokuHyoDto> toCsvByIncomingCallReport(RC905NyudenHokokuHyoDto inputDto, TLCSSB2BUserContext userContext) {
		List<RC905NyudenHokokuHyoDto> resultList = new ArrayList<RC905NyudenHokokuHyoDto>();
		// �^�C�g���̓e���v���[�g�t�@�C������f�[�^���擾����ׁA��f�[�^��ݒ�
		RC905NyudenHokokuHyoDto dummyDto = new RC905NyudenHokokuHyoDto();

		// ���o�l�����擾
		String kaishaId;

		if (userContext.isOutsourcerOp() || userContext.isOutsourcerSv()) {
			// �Z�b�V�������̌������ϑ����SV�܂��͈ϑ����OP�̏ꍇ�A�Z�b�V�����̉��ID���Z�b�g
			kaishaId = userContext.getKaishaId();
		} else {
			// �Z�b�V�����̃V�X�e���萔Map����s�n�j�`�h��Ђh�c�̒l���擾���ăZ�b�g
			kaishaId = userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_TOKAI_KAISHA_ID);
		}

		inputDto.setSashidashinin(sashidashininDao.selectByPrimaryKey(RcpMSashidashinin.KBN_SAGYO_HOKOKU_GYOSHA_SASHIDASHININ, kaishaId));

		// 1�s�ځF�\�����^�C�g���s�@1�s�ڂ̃^�C�g���s��CSVDownloadAction.download�ōs���Ă��邽�ߕs�v

		// 2�s�ځF �\�����f�[�^�s
		resultList.add(inputDto);

		// 3�s�ځF���ו��^�C�g���s
		resultList.add(dummyDto);

		// 4�s�ڈȍ~�F���ו��f�[�^�s �₢���킹�����������A�s�o��
		for (int i = 0 ; i < inputDto.getToiawaseRirekiList().size() ; i++) {
			resultList.add(inputDto);
		}

		return resultList;
	}
	
	/**
	 * ��ƕ񍐏�CSV�o�͗p��CSV�o�͏���ϊ����܂��B
	 *
	 * @param inputDto ��ƕ񍐏�CSV�o�͏��
	 * @return �ϊ����ƕ񍐏�CSV�o�͏��
	 */
	private List<RC906SagyoHokokuHyoDto> toCsvByWorkReport(RC906SagyoHokokuHyoDto inputDto) {
		List<RC906SagyoHokokuHyoDto> resultList = new ArrayList<RC906SagyoHokokuHyoDto>();

		// 1�s�ځF���d��t�񍐏����^�C�g���s�@1�s�ڂ̃^�C�g���s��CSVDownloadAction.download�ōs���Ă��邽�ߕs�v

		// 2�s�ځF ��Ɠ��e�f�[�^�s
		resultList.add(inputDto);

		return resultList;
	}
}
