package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.math.BigDecimal;
import java.util.List;

import jp.co.tokaigroup.reception.common.logic.CheckToPublishToiawaseLogic;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTSagyoJokyoDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.print.logic.RC905NyudenHokokuHyoLogic;
import jp.co.tokaigroup.reception.print.logic.RC906SagyoHokokuHyoLogic;
import jp.co.tokaigroup.reception.print.logic.RC912PhotoReportLogic;
import jp.co.tokaigroup.reception.util.FileRemoteUtil;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB014ReportDisclosureModel;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * �񍐏����J�ݒ�T�[�r�X�����N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/11
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB014ReportDisclosureServiceImpl extends TLCSSB2BBaseService implements TB014ReportDisclosureService {

	/** �R�s�[���A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_PDF = ResourceFactory.getResource().getString("PDF_PATH");

	/** �R�s�[��A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_HOKOKUSHO_KOKAI = ResourceFactory.getResource().getString("UPLOAD_PATH_HOKOKUSHO_KOKAI");

	/** �ꎞ�ۑ��t�@�C���� */
	private static final String TEMP_FILE_NM = "TEMP";

	/** �₢���킹���sDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;
	
	/** ��Ə󋵂sDAO */
	@Autowired
	private RcpTSagyoJokyoDao sagyoJokyoDao;
	
	/** ���Z�v�V�����A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao accesslogDao;
	
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
	
	/** �`�F�b�N���W�b�N */
	@Autowired
	private CheckToPublishToiawaseLogic checkToPublishToiawaseLogic;

	/**
	 * �������擾�������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB014ReportDisclosureModel getInitInfo(TB014ReportDisclosureModel model) {
		
		// �R���e���c���̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �₢���킹���̎擾
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		
		// �₢���킹��񂪑��݂��Ȃ��ꍇ�͏������I��
		if (toiawase == null){
			throw new ForbiddenException("�₢���킹��񂪎擾�ł��܂���B");
		}
		
		// �Z�b�V�����̌������u40:�ϑ����SV�v�܂��́u41:�ϑ����OP�v�̏ꍇ
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// �ϑ��֘A��Ѓ`�F�b�N��NG�̏ꍇ
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), toiawase.getKokyakuId())){
				throw new ForbiddenException("�A�N�Z�X���������݂��܂���B");
			}
		}

//		// ���[�敪���u���d�񍐏��v�̏ꍇ
//		if (model.isPrintableIncomingCallReport()) {
//			// ���d�񍐏�
//			List<String[]> allDataList =
//				nyudenHokokuHyoLogic.getNyudenHokokuHyoCsvList(model.getToiawaseNo(),
//						model.getKokyakuId(), model.getSenderNm1(),
//						model.getSenderNm2(), model.getSenderAddress(),
//						model.getSenderTelNo(), model.getSenderFaxNo(), RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_KOKAI);
//
//			// PDF�o�́i�}���`�o�́j
//			model.setPdfUrl(nyudenHokokuHyoLogic.outputPdf(allDataList, true, true));
//		// ���[�敪���u��ƕ񍐏��v�̏ꍇ
//		} else {
//			// ��ƕ񍐏�
//			List<String[]> allDataList =
//				sagyoHokokuHyoLogic.getSagyoHokokuCsvList(model.getToiawaseNo(),
//						model.getKokyakuId(), model.getToiawaseRirekiNo(),
//						model.getSenderNm1(), model.getSenderNm2(),
//						model.getSenderAddress(), model.getSenderTelNo(),
//						model.getSenderFaxNo());
//			// �ʐ^�񍐏�
//			if (photoReportLogic.isExistUploadFile(model.getToiawaseNo(),
//					new BigDecimal(model.getToiawaseRirekiNo()))) {
//				//�A�b�v���[�h�t�@�C�������݂���ꍇ
//				List<String[]> photoReportDataList =
//					photoReportLogic.getPhotoReportCsvList(model.getToiawaseNo(),
//							new BigDecimal(model.getToiawaseRirekiNo()), model.getKokyakuId(),
//							model.getSenderNm1(), model.getSenderNm2(),
//							model.getSenderAddress(), model.getSenderTelNo(),
//							model.getSenderFaxNo());
//				//�ʐ^�񍐏��f�[�^����ƕ񍐏��f�[�^�ɉ�����
//				allDataList.addAll(photoReportDataList);
//			}
//
//			// PDF�o��(CSV�o�͂������ɍs��)
//			model.setPdfUrl(sagyoHokokuHyoLogic.outputPdf(allDataList, true, true));
//		}

		return model;
	}
	
	/**
	 * ���J�������s���܂��B
	 * 
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	@Transactional(value="txManager")
	public TB014ReportDisclosureModel discloseReport(TB014ReportDisclosureModel model){
		
		// �₢���킹����ԍ�
		BigDecimal toiawaseRirekiNo = null;
		
		// ���J���
		int disclosureType = 0;
		
		// PDF�t�@�C����
		String newPdfFileName = null;
		
		// ���[�敪���u���d�񍐏��v�̏ꍇ
		if (model.isPrintableIncomingCallReport()) {
			// �₢���킹�e�[�u���̕񍐌��J�t���O���X�V
			RcpTToiawase toiawase = new RcpTToiawase();
			toiawase.setToiawaseNo(model.getToiawaseNo());
			toiawase.setUpdDt(model.getToiawaseUpdDt());
			toiawase.setHokokushoKokaiFlg(RcpTToiawase.HOKOKUSHO_KOKAI_FLG_KOKAI);
			toiawase.setLastUpdId(getUserContext().getLoginId());
			toiawaseDao.updateHokokushoKokaiFlg(toiawase);
			toiawaseRirekiNo = null;
			disclosureType = CheckToPublishToiawaseLogic.CONTENT_TYPE_NYUDEN_HOKOKUSHO;
			newPdfFileName = model.getToiawaseNo() + ".pdf";
		// ���[�敪���u��ƕ񍐏��v�̏ꍇ
		} else {
			// �₢���킹�e�[�u���̍X�V�����X�V
			toiawaseDao.updateToiawaseUpdDt(model.getToiawaseNo(), model.getToiawaseUpdDt(), getUserContext().getLoginId());
			
			// ��Ə󋵃e�[�u���̕񍐌��J�t���O���X�V
			RcpTSagyoJokyo sagyoJokyo = new RcpTSagyoJokyo();
			sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
			sagyoJokyo.setToiawaseRirekiNo(new BigDecimal(model.getToiawaseRirekiNo()));
			sagyoJokyo.setHokokushoKokaiFlg(RcpTSagyoJokyo.HOKOKUSHO_KOKAI_FLG_PUBLISH);
			sagyoJokyo.setLastUpdId(getUserContext().getLoginId());
			sagyoJokyo.setUpdDt(model.getSagyoJokyoUpdDt());
			sagyoJokyoDao.updateHoukokushoKokaiFlg(sagyoJokyo);
			
			toiawaseRirekiNo = new BigDecimal(model.getToiawaseRirekiNo());
			disclosureType = CheckToPublishToiawaseLogic.CONTENT_TYPE_SAGYO_HOKOKUSHO;
			newPdfFileName = model.getToiawaseNo() + "-" + model.getToiawaseRirekiNo() + ".pdf";
		}

		// �񍐏����J�ݒ�ۃ`�F�b�N
		if (!checkToPublishToiawaseLogic.isValid(disclosureType, RcpTToiawase.HOKOKUSHO_KOKAI_FLG_KOKAI, model.getToiawaseNo(), toiawaseRirekiNo)) {
			// ���J�ݒ肪�����̏ꍇ�A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0030", model.getGamenNm(), "���J"));
		}

		// �A�N�Z�X���O�̓o�^����
		accesslogDao.insert(model.getGamenNm(), "���J", createSearchInfo(model.getToiawaseNo(), model.getToiawaseRirekiNo(), TEMP_FILE_NM + newPdfFileName));

		// �R�s�[���t�@�C�����̃p�X��ݒ�
		String fromCopyPdfPath = UPLOAD_PATH_HOKOKUSHO_KOKAI + System.getProperty("file.separator") + TEMP_FILE_NM + newPdfFileName;
		// �R�s�[��t�@�C�����̃p�X��ݒ�
		String toCopyPdfPath = UPLOAD_PATH_HOKOKUSHO_KOKAI + System.getProperty("file.separator") + newPdfFileName;
		
		// �t�@�C���R�s�[�̎��{
		FileRemoteUtil.remoteCopyFileBySsh(fromCopyPdfPath, toCopyPdfPath);
		
		// �₢���킹�X�V�����Đݒ�
		model.setToiawaseUpdDt(toiawaseDao.selectByPrimaryKey(model.getToiawaseNo()).getUpdDt());
		
		// ���[�敪���u��ƕ񍐏��v�̏ꍇ�̂ݍ�Ə󋵍X�V�����Đݒ�
		if (model.isPrintableWorkReport()) {
			model.setSagyoJokyoUpdDt(sagyoJokyoDao.selectByPrimaryKey(model.getToiawaseNo(), toiawaseRirekiNo).getUpdDt());
		}
		
		return model;
	}
	
	/**
	 * ���[�_�E�����[�h�������s���܂��B
	 * 
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB014ReportDisclosureModel downloadReport(TB014ReportDisclosureModel model) {
		
		String pdfUrl = "";
		String newPdfFileName = "";
		// ���[�敪���u���d�񍐏��v�̏ꍇ
		if (model.isPrintableIncomingCallReport()) {
			// ���d�񍐏�
			List<String[]> allDataList =
				nyudenHokokuHyoLogic.getNyudenHokokuHyoCsvList(model.getToiawaseNo(),
						model.getKokyakuId(), model.getSenderNm1(),
						model.getSenderNm2(), model.getSenderAddress(),
						model.getSenderTelNo(), model.getSenderFaxNo(), RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_KOKAI);

			// PDF�o�́i�}���`�o�́j
			pdfUrl = nyudenHokokuHyoLogic.outputPdf(allDataList, true, true);
			// PDF�t�@�C����
			newPdfFileName = model.getToiawaseNo() + ".pdf";
		// ���[�敪���u��ƕ񍐏��v�̏ꍇ
		} else {
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
			pdfUrl = sagyoHokokuHyoLogic.outputPdf(allDataList, true, true);
			// PDF�t�@�C����
			newPdfFileName = model.getToiawaseNo() + "-" + model.getToiawaseRirekiNo() + ".pdf";
		}
		
		// PDF�t�@�C���̖��O���擾����
		String pdfFileName = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);
		// �R�s�[���t�@�C�����̃p�X��ݒ�
		String fromCopyPdfPath = UPLOAD_PATH_PDF + System.getProperty("file.separator") + pdfFileName;
		
		// �R�s�[��t�@�C�����̃p�X��ݒ�
		String toCopyPdfPath = UPLOAD_PATH_HOKOKUSHO_KOKAI + System.getProperty("file.separator") + TEMP_FILE_NM + newPdfFileName;
		
		// �t�@�C���R�s�[�̎��{
		FileRemoteUtil.remoteCopyFileBySsh(fromCopyPdfPath, toCopyPdfPath);
		
		model.setOutputPdfPath(toCopyPdfPath);
		
		return model;
	}
	
	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param toiawaseNo �₢���킹NO
	 * @param toiawaseRirekiNo �₢���킹����NO
	 * @param pdfFileName pdf�t�@�C����
	 * 
	 * @return ���O�o�͓��e
	 */
	private String createSearchInfo(String toiawaseNo, String toiawaseRirekiNo, String pdfFileName){
		StringBuilder sb = new StringBuilder();
		sb.append("toiawaseNo=");
		sb.append(toiawaseNo);
		sb.append(",toiawaseRirekiNo=");
		sb.append(toiawaseRirekiNo);
		sb.append(",pdfFileName=");
		sb.append(pdfFileName);
		return sb.toString();
	}

}
