package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKeiyaku;
import jp.co.tokaigroup.reception.util.FileRemoteUtil;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.dto.TB044ReceptionListPrintDto;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB044ReceptionListPrintLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB044ReceptionListPrintModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ��t�ꗗ����T�[�r�X�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/07/08
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB044ReceptionListPrintServiceImpl extends TLCSSB2BBaseService
		implements TB044ReceptionListPrintService {

	// �v���p�e�B�t�@�C������擾
	/** PDF�t�@�C���i�[�f�B���N�g���� */
	private static final String PDF_PATH = ResourceFactory.getResource().getString("PDF_PATH");
	/** PDF���J�t�@�C���p�X */
	private static final String UPLOAD_PATH_RECEPTION_LIST = ResourceFactory.getResource().getString("UPLOAD_PATH_RECEPTION_LIST");

	/** �ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** �ڋq�_��lDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** �T�[�r�X�lDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** ��t�ꗗ����@���W�b�N�N���X */
	@Autowired
	private TB044ReceptionListPrintLogic logic;

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model ��t�ꗗ�����ʃ��f��
	 * @return ��t�ꗗ�����ʃ��f��
	 */
	public TB044ReceptionListPrintModel getInitInfo(TB044ReceptionListPrintModel model) {

		// ���[�U�[���̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// �Z�b�V�����������Г��̏ꍇ
		if (userContext.isInhouse()){
			// �T�[�r�X�̎擾
			model.setServiceMEntityList(serviceDao.selectServiceList());
		// �Z�b�V�����������s���Y�E�Ǘ���Ђ̏ꍇ
		} else if (userContext.isRealEstate()) {
			// �T�[�r�X�̎擾
			model.setServiceMEntityList(kokyakuKeiyakuDao.selectKokyakuKeiyakuService(userContext.getKokyakuId()));
			// �ڋq���̎擾
			// �ΏۊJ�n���A�ΏۏI�����̏����l�ݒ�
			getTargetDay(kokyakuDao.selectByPrimaryKey(userContext.getKokyakuId()), model);
		}

		return model;
	}

	/**
	 * ����������s���܂��B
	 *
	 * @param model ��t�ꗗ�����ʃ��f��
	 * @return PDF�o�̓t�@�C���p�X(�t�@�C���R�s�[��)
	 */
	public String createPdf(TB044ReceptionListPrintModel model){

		// ���[�U�[���̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// ����Ώێ擾
		// �Z�b�V�����������Г��̏ꍇ
		if (userContext.isInhouse()){
			// �T�[�r�X�̎擾
			model.setSeikyusakiKokyakuId(model.getKokyakuId());
		// �Z�b�V�����������s���Y�E�Ǘ���Ђ̏ꍇ
		} else if (userContext.isRealEstate()) {
			model.setSeikyusakiKokyakuId(userContext.getKokyakuId());
		}

		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB044ReceptionListPrintModel.GAMEN_NM,
				Constants.BUTTON_NM_PRINT, createPrintJoken(model));

		// PDF�o��
		// ��t�ꗗ�o�̓f�[�^���X�g�̎擾
		List<String[]> allDataList = logic.getReceiptListDataList(model.getTargetDtFrom(), model.getTargetDtTo(), model.getSeikyusakiKokyakuId(), model.getServiceKbn());
		// ��t�ꗗPDF�̍쐬
		String pdfUrl = logic.outputPdf(allDataList);

		// ��t�ꗗPDF�̃t�@�C���R�s�[
		// PDF�t�@�C���̖��O���擾����
		String pdfFileName = pdfUrl.substring(pdfUrl.lastIndexOf(TB044ReceptionListPrintDto.REPORT_NM));

		// �R�s�[���iPDF�̃p�X�j
		String orgFilePath = PDF_PATH + System.getProperty("file.separator") + pdfFileName;
		// �R�s�[��t�@�C��
		String destFilePath = UPLOAD_PATH_RECEPTION_LIST + System.getProperty("file.separator") + pdfFileName;

		// �t�@�C���R�s�[�R�}���h���s(SSH)
		FileRemoteUtil.remoteCopyFileBySsh(orgFilePath, destFilePath);

		return destFilePath;
	}

	/**
	 * �ΏۊJ�n���A�ΏۏI�����̏����l�ݒ���s���܂��B
	 *
	 * @param kokyaku �ڋq�}�X�^���
	 * @param model ��t�ꗗ������
	 * @return ��t�ꗗ������
	 */
	private TB044ReceptionListPrintModel getTargetDay(RcpMKokyaku kokyaku, TB044ReceptionListPrintModel model) {
		// �ΏۊJ�n���A�ΏۏI�����̎擾
		Timestamp startDt = null;
		Timestamp endDt = null;

		if (kokyaku.getShimeDay() == null || RcpMKokyakuKeiyaku.LAST_DAY_OF_SHIME_DAY == kokyaku.getShimeDay().intValue()) {
			// ���ߓ���NULL�A�܂��́A�����̏ꍇ�́A�������`������ݒ�
			startDt = DateUtil.getFirstDayOfMonth(DateUtil.formatTimestamp(
					DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
			endDt = DateUtil.getLastDayOfMonth(DateUtil.formatTimestamp(
					DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
		} else {
			// �O���̌��������擾
			Date lastMonth = DateUtils.addMonths(DateUtil.getSysDate(), -1);
			Integer lastDate = Integer.parseInt(
					DateUtil.formatTimestamp(new Timestamp(
							DateUtil.getLastDayOfMonth(lastMonth.getTime())), "dd"));

			//�ΏۊJ�n���̐ݒ�
			if ((kokyaku.getShimeDay().intValue() + 1) > lastDate.intValue()) {
				// ���ߓ��{�P���O���̌������𒴂��Ă����ꍇ�A��������ݒ�
				startDt = DateUtil.getFirstDayOfMonth(DateUtil.formatTimestamp(
						DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
			} else {
				// ����ȊO�̏ꍇ�́A�O���{�i���ߓ��{�P�j��ݒ�
				startDt = DateUtil.toTimestamp(
						DateUtil.formatTimestamp(DateUtil.toSqlTimestamp(lastMonth), "yyyyMM") +
						StringUtils.left(Integer.toString(kokyaku.getShimeDay().intValue() + 1), 2),
						"yyyyMMdd");
			}

			// �����̌��������擾
			lastDate = Integer.parseInt(
					DateUtil.formatTimestamp(new Timestamp(
							DateUtil.getLastDayOfMonth(DateUtil.getSysDate().getTime())), "dd"));

			//�ΏۏI�����̐ݒ�
			if ((kokyaku.getShimeDay().intValue()) > lastDate.intValue()) {
				// ���ߓ��������̌������𒴂��Ă����ꍇ�A��������ݒ�
				endDt = DateUtil.getLastDayOfMonth(DateUtil.formatTimestamp(
						DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
			} else {
				// ����ȊO�̏ꍇ�́A�����{�i���ߓ��j��ݒ�
				endDt = DateUtil.toTimestamp(
						DateUtil.formatTimestamp(DateUtil.toSqlTimestamp(DateUtil.getSysDate()), "yyyyMM") +
						StringUtils.left(Integer.toString(kokyaku.getShimeDay().intValue()), 2),
						"yyyyMMdd");
			}
		}

		model.setTargetDtFrom(startDt);
		model.setTargetDtTo(endDt);

		return model;
	}

	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param toiawaseNO �₢���킹NO
	 * @param pdfFileName pdf�t�@�C���l�[��
	 * @return kensakuJoken �ϊ���CSV�o�͏��
	 */
	private String createPrintJoken(TB044ReceptionListPrintModel model){
		StringBuilder sb = new StringBuilder();
		sb.append("targetDtFrom=");
		sb.append(DateUtil.formatTimestamp(model.getTargetDtFrom(), "yyyyMMdd"));
		sb.append(",targetDtTo=");
		sb.append(DateUtil.formatTimestamp(model.getTargetDtTo(), "yyyyMMdd"));
		sb.append(",seikyusakiKokyakuId=");
		sb.append(model.getSeikyusakiKokyakuId());
		if (StringUtils.isNotBlank(model.getServiceKbn())){
			sb.append(",serviceKbn=");
			sb.append(model.getServiceKbn());
		}
		String printJoken = new String(sb);
		return printJoken;
	}
}
