package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.FileUploadLogic;
import jp.co.tokaigroup.reception.common.logic.CheckToPublishToiawaseLogic;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn1Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn2Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn3Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn4Dao;
import jp.co.tokaigroup.reception.dao.RcpTBillScheduleDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuWithNoIdDao;
import jp.co.tokaigroup.reception.dao.RcpTMailRirekiDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyohiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseFileDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpTBillSchedule;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTSagyohi;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseFile;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB023InquiryEntryModel;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �₢���킹�o�^�T�[�r�X�����N���X�B
 *
 * @author k003316
 * @version 1.0 2015/08/11
 * @version 1.1 2016/07/15 H.Yamamura �T�[�r�X��ʍ��ڒǉ�
 * @version 1.3 2016/08/08 S.Nakano ��������Ó����`�F�b�N�ǉ�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB023InquiryEntryServiceImpl extends TLCSSB2BBaseService
		implements TB023InquiryEntryService {
	// �v���p�e�B�t�@�C������擾
	/** �₢���킹�t�@�C���A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_INQUIRY_FILE = ResourceFactory.getResource().getString("UPLOAD_PATH_INQUIRY_FILE");

	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;
	
	/** �₢���킹�e�[�u��DAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** ���Z�v�V�����₢���킹�����e�[�u��DAO */
	@Autowired
	private RcpTToiawaseRirekiDao toiawaseRirekiDao;
	
	/** �h�c�����ڋq�e�[�u��DAO */
	@Autowired
	private RcpTKokyakuWithNoIdDao kokyakuWithoutIdDao;

	/** �˗����sDAO */
	@Autowired
	private RcpTIraiDao iraiDao;
	
	/** ���ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �₢���킹�敪�P�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn1Dao toiawaseKbn1Dao;

	/** �₢���킹�敪�Q�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn2Dao toiawaseKbn2Dao;

	/** �₢���킹�敪�R�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn3Dao toiawaseKbn3Dao;

	/** �₢���킹�敪�S�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn4Dao toiawaseKbn4Dao;

	/** ���Z�v�V�����󋵋敪�}�X�^ */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;
	
	/** NATOS�p�X���[�h�lDAO */
	@Autowired
	private NatosMPasswordDao natosPswdDao;

	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** �₢���킹���J�`�F�b�N���W�b�N */
	@Autowired
	private CheckToPublishToiawaseLogic toiawaseCheckLogic;
	
	/** �ϑ���Њ֘A�`�F�b�N���W�b�N */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	
	/** ���J���[�����M�����}�X�^DAO */
	@Autowired
	private RcpTMailRirekiDao mailRirekiDao;
	
	/** �₢���킹�t�@�C���e�[�u��DAO */
	@Autowired
	private RcpTToiawaseFileDao toiawaseFileDao;

	/** �ڋq�_����}�X�^DAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** �r���Ǘ��X�P�W���[���e�[�u��DAO */
	@Autowired
	private RcpTBillScheduleDao billScheduleDao;
	
	/** ��Ɣ�e�[�u��DAO */
	@Autowired
	private RcpTSagyohiDao sagyohiDao;
	
	/**
	 * �����\�����擾���s���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel getInitInfo(TB023InquiryEntryModel model) {
		// �T�[�r�X��ʂ̏����l��s���Őݒ�
		model.setInitServiceShubetsu(RcpTToiawase.SERVICE_SHUBETSU_FUMEI);
		// �p�����[�^�̌ڋq�h�c���擾�ł����ꍇ
		if (StringUtils.isNotBlank(model.getKokyakuId())) {
			
			try {
				// �ڋq�ڍׂ̏����擾
				model.setKokyakuEntity(kokyakuKihon.getKokyakuInfo(model.getKokyakuId()));
			} catch (ValidationException e) {
				// ���ʏ������̃��b�Z�[�W�ƈقȂ邽�߁A����������
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			// �₢���킹��񂪍쐬����Ă��Ȃ��ꍇ
			if (model.getToiawaseInfo() == null) {
				// �T�[�r�X��ʂ̏����l���擾
				String serviceShubetsu = kokyakuKeiyakuDao.getKeiyakuServiceShubetsu(model.getKokyakuId());
				// �T�[�r�X��ʂ��擾�ł����ꍇ
				if (StringUtils.isNotBlank(serviceShubetsu)) {
					model.setInitServiceShubetsu(serviceShubetsu);
				}
			}
		} else {
			// �ڋq�h�c�����\���p�̃f�[�^�擾
			this.getInitKokyakuInfoWithoutId(model);

		}
		// �₢���킹�\���p�f�[�^�擾
		return this.getInitToiawaseInfo(model);
	}
	
	/**
	 * �T�[�o�[�T�C�h�G���[�������́A�����\������p�ӂ��܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel parepareInitInfo(TB023InquiryEntryModel model) {
		
		return this.getInitInfo(model);
	}
	
	/**
	 * �����\�����擾���s���܂��B�i�X�V��ʕ\���p�j
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel getInitInfoForUpdate(TB023InquiryEntryModel model) {
		// �p�����[�^�̖₢���킹�m�n���擾�ł����ꍇ
		if (StringUtils.isNotBlank(model.getToiawaseNo())) {
			
			// �₢���킹�����擾
			RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
			
			if (toiawase == null) {
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			RcpTKokyakuWithNoId kokyakuWithoutId = null;
			// �₢���킹���̌ڋq�h�c���擾�ł��Ȃ��ꍇ
			if (StringUtils.isBlank(toiawase.getKokyakuId())) {
				// �h�c�����ڋq���̎擾
				kokyakuWithoutId = kokyakuWithoutIdDao.selectByPrimaryKey(model.getToiawaseNo());
			}
			
			// ��ʒl��������
			if (Constants.ACTION_TYPE_RESTORE.equals(model.getActionType())) {
				// �A�N�V�����^�C�v���urestore:��ʒl�����v�̏ꍇ
				if (kokyakuWithoutId != null && model.getKokyakuInfoWithoutId() != null) {
					// �h�c�����ڋq���\���̏ꍇ
					kokyakuWithoutId.setKokyakuKbn(model.getKokyakuInfoWithoutId().getKokyakuKbn());
					kokyakuWithoutId.setKokyakuKbnNm(model.getKokyakuInfoWithoutId().getKokyakuNm());
					kokyakuWithoutId.setKokyakuJusho(model.getKokyakuInfoWithoutId().getKokyakuJusho());
					kokyakuWithoutId.setKokyakuTel(model.getKokyakuInfoWithoutId().getKokyakuTel());
				}
				
				// �₢���킹���̕���
				toiawase.setIraishaFlg(model.getToiawaseInfo().getIraishaFlg());
				toiawase.setIraishaKbn(model.getToiawaseInfo().getIraishaKbn());
				toiawase.setIraishaNm(model.getToiawaseInfo().getIraishaNm());
				toiawase.setIraishaTel(model.getToiawaseInfo().getIraishaTel());
				toiawase.setIraishaRoomNo(model.getToiawaseInfo().getIraishaRoomNo());
				toiawase.setIraishaSexKbn(model.getToiawaseInfo().getIraishaSexKbn());
				toiawase.setIraishaMemo(model.getToiawaseInfo().getIraishaMemo());
				toiawase.setUketsukeYmd(model.getToiawaseInfo().getUketsukeYmd());
				toiawase.setUketsukeJikan(model.getToiawaseInfo().getUketsukeJikan());
				toiawase.setToiawaseKbn1(model.getToiawaseInfo().getToiawaseKbn1());
				toiawase.setToiawaseKbn2(model.getToiawaseInfo().getToiawaseKbn2());
				toiawase.setToiawaseKbn3(model.getToiawaseInfo().getToiawaseKbn3());
				toiawase.setToiawaseKbn4(model.getToiawaseInfo().getToiawaseKbn4());
				toiawase.setUketsukeKeitaiKbn(model.getToiawaseInfo().getUketsukeKeitaiKbn());
				toiawase.setToiawaseNaiyoSimple(model.getToiawaseInfo().getToiawaseNaiyoSimple());
				toiawase.setToiawaseNaiyo(model.getToiawaseInfo().getToiawaseNaiyo());
				toiawase.setIraiUmuKbn(model.getToiawaseInfo().getIraiUmuKbn());
				toiawase.setHoukokuTargetFlg(model.getToiawaseInfo().getHoukokuTargetFlg());
				toiawase.setToiawaseKokaiFlg(model.getToiawaseInfo().getToiawaseKokaiFlg());
				toiawase.setServiceShubetsu(model.getToiawaseInfo().getServiceShubetsu());
			} else {
				// �ύX�O�̏�Ԃ́A�{���̓��͑O��ێ�
				// �ύX�O�̈˗��L���敪��ێ�
				model.setIraiUmuKbn(toiawase.getIraiUmuKbn());
				// �ύX�O�̖₢���킹���J�t���O��ێ�
				model.setBefereToiawaseKokaiFlg(toiawase.getToiawaseKokaiFlg());
				// �ύX�O�̕񍐏����J�t���O��ێ�
				model.setBeforeHokokushoKokaiFlg(toiawase.getHokokushoKokaiFlg());
			}
			// �h�c�����ڋq���̐ݒ�
			model.setKokyakuInfoWithoutId(kokyakuWithoutId);
			// �₢���킹���̌ڋq�h�c���A�p�����[�^�̌ڋq�h�c�ɐݒ�
			model.setKokyakuId(toiawase.getKokyakuId());
			// ����ʂւ̃p�����[�^�A�X�V�̔r���ȂǂŎg�p���邽�߁A�X�V�����ڂ��Ă���
			model.setToiawaseUpdDt(toiawase.getUpdDt());
			// �₢���킹����ێ�
			model.setToiawaseInfo(toiawase);
		} else {
			// �X�V�Ŗ₢���킹�m�n�������ꍇ�̓G���[
			throw new ApplicationException("�₢���킹NO�s���F�p�����[�^�̖₢���킹NO" );
		}
		
		return this.getToiawaseInfoForUpdate(model);
	}

	/**
	 * �T�[�o�[�T�C�h�G���[�������́A�����\�����擾���s���܂��B�i�X�V��ʕ\���p�j
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel parepareInitInfoForUpdate(TB023InquiryEntryModel model) {
		
		// �X�V��ʗp�̖₢���킹�\���p�f�[�^�擾
		return this.getToiawaseInfoForUpdate(model);
	}
	
	/**
	 * ID�����ڋq���������擾���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	private TB023InquiryEntryModel getInitKokyakuInfoWithoutId(TB023InquiryEntryModel model) {
		
		// �˗��ҋ敪���X�g�̎擾
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		
		model.setKokyakuIraishaKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN));

		return model;
	}
	
	/**
	 * �₢���킹���������擾���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	private TB023InquiryEntryModel getInitToiawaseInfo(TB023InquiryEntryModel model) {
		// ���ʃR�[�h�}�X�^����擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
					RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA,
					RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU,
					RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_SEX_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI,
					RcpMComCd.RCP_M_COM_CD_SERVICE_SHUBETSU_TOIAWASE);

		
		// �₢���킹�敪�P�}�X�^����S���擾
		List<RcpMToiawaseKbn1> toiawaseKbn1List = toiawaseKbn1Dao.selectKbnListForDisplay();

		// �₢���킹�敪�Q�}�X�^����S���擾
		List<RcpMToiawaseKbn2> toiawaseKbn2List = toiawaseKbn2Dao.selectKbnListForDisplay();

		// �₢���킹�敪�R�}�X�^����S���擾
		List<RcpMToiawaseKbn3> toiawaseKbn3List = toiawaseKbn3Dao.selectKbnListForDisplay();

		// �₢���킹�敪�S�}�X�^����S���擾
		List<RcpMToiawaseKbn4> toiawaseKbn4List = toiawaseKbn4Dao.selectKbnListForDisplay();

		// �˗��ҋ敪���X�g�i�₢���킹�j
		model.setToiawaseIraishaKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN));
		// �˗��҃t���O���X�g
		model.setIraishaFlgList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA));
		// �₢���킹�敪�P���X�g
		model.setToiawaseKbn1List(toiawaseKbn1List);
		// �₢���킹�敪�Q���X�g
		model.setToiawaseKbn2List(toiawaseKbn2List);
		// �₢���킹�敪�R���X�g
		model.setToiawaseKbn3List(toiawaseKbn3List);
		// �₢���킹�敪�S���X�g
		model.setToiawaseKbn4List(toiawaseKbn4List);
		// �˗��L�����X�g
		model.setIraiUmuList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU));
		// �˗��Ґ��ʃ��X�g
		model.setIraishaSexKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_SEX_KBN));
		// ��t�`�ԃ��X�g
		model.setUketsukeKeitaiKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI));
		// �₢���킹�t�@�C�����X�g
		model.setUploadedFiles(createToiawaseFileList(new ArrayList<RcpTToiawaseFile>()));
		// �T�[�r�X��ʃ��X�g
		model.setServiceShubetsuList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_SERVICE_SHUBETSU_TOIAWASE));
		
		return model;
	}
	
	/**
	 * �₢���킹�����擾���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel getToiawaseInfoForUpdate(TB023InquiryEntryModel model) {
		
		// �����\�����擾
		model = this.getInitInfo(model);
		
		// �ŏI�������擾
		RcpTToiawaseRireki lastToiawaseRireki = toiawaseRirekiDao.getLastToiawaseRireki(
				model.getToiawaseNo(), RC031ToiawaseSearchCondition.EXTERNAL_SITE_KOKAI_RDO_ALL);
		
		// �₢���킹�����ꗗ���擾
		model.setToiawaseRirekiList(toiawaseRirekiDao.selectRirekiListForDisplay(model.getToiawaseNo()));
		
		// �˗����擾
		Map<String, RcpTIrai> iraiMap = iraiDao.selectAnyAsMap(model.getToiawaseNo());
		
		// �p�X���[�h�}�X�^�̘a���ϊ��}�b�v�擾
		List<String> userIdList = new ArrayList<String>();
		
		if (model.getToiawaseInfo() != null
				&& StringUtils.isNotBlank(model.getToiawaseInfo().getUketsukeshaId())) {
			
			userIdList.add(model.getToiawaseInfo().getUketsukeshaId());	// �₢���킹���̎�t�҂h�c
		}
		if (model.getToiawaseInfo() != null
				&& StringUtils.isNotBlank(model.getToiawaseInfo().getLastUpdId())
				&& !userIdList.contains(model.getToiawaseInfo().getLastUpdId())) {
			
			userIdList.add(model.getToiawaseInfo().getLastUpdId());	// �₢���킹���̍ŏI�X�V�҂h�c
		}
		for (RcpTToiawaseRireki toiawaseRireki : model.getToiawaseRirekiList()) {
			if (StringUtils.isNotBlank(toiawaseRireki.getTantoshaId())
					&& !userIdList.contains(toiawaseRireki.getTantoshaId())) {
				
				userIdList.add(toiawaseRireki.getTantoshaId());	// �₢���킹�����ꗗ�̒S���҂h�c
			}
		}
		Map<String, String> userMap = new HashMap<String, String>();
		if (!userIdList.isEmpty()) {
			userMap = natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));	// �a���ϊ��}�b�v�擾
		}
		
		// ���ʃR�[�h�}�X�^�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_CHECK_ON_FLG);
		Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);
		// �������J�t���O
		Map<String, RcpMComCd> rirekiKokaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_CHECK_ON_FLG);
		
		// �󋵋敪�}�X�^
		List<RcpMJokyoKbn> jokyoKbnList = jokyoKbnDao.selectAll();
		Map<String, String> jokyoKbnMap = jokyoKbnDao.convertMap(jokyoKbnList);
		
		// �₢���킹����ϊ�
		if (userMap != null && model.getToiawaseInfo() != null) {
			// ��t��
			model.getToiawaseInfo().setUketsukeNm(userMap.get(model.getToiawaseInfo().getUketsukeshaId()));
			// �ŏI�X�V�ҁi�ŏI�X�V�҂h�c����a���ϊ��j
			model.getToiawaseInfo().setLastUpdIdToNm(userMap.get(model.getToiawaseInfo().getLastUpdId()));
		}
		
		// �₢���킹�����ꗗ��ϊ�
		for (RcpTToiawaseRireki toiawaseRireki : model.getToiawaseRirekiList()) {
			// �S���Җ������݂��Ȃ��ꍇ
			if (StringUtils.isBlank(toiawaseRireki.getTantoshaNm())) {
				// �S���Җ�
				if (userMap != null) {
					toiawaseRireki.setTantoshaNm(userMap.get(toiawaseRireki.getTantoshaId()));
				}
			}
			// �󋵋敪
			if (jokyoKbnMap != null) {
				toiawaseRireki.setJokyoKbnNm(jokyoKbnMap.get(toiawaseRireki.getJokyoKbn()));
			}
			// �˗����ݗL��
			if (iraiMap != null) {
				String searchKey = toiawaseRireki.getToiawaseNo() + toiawaseRireki.getToiawaseRirekiNo().toString();
				if (iraiMap.containsKey(searchKey)) {
					toiawaseRireki.setIraiExsits(true);
				}
			}
			// �������J�t���O
			if (rirekiKokaiMap != null
					&& StringUtils.isNotBlank(toiawaseRireki.getToiawaseRirekiKokaiFlg())
					&& rirekiKokaiMap.containsKey(toiawaseRireki.getToiawaseRirekiKokaiFlg())) {
				toiawaseRireki.setToiawaseRirekiCheck(rirekiKokaiMap.get(toiawaseRireki.getToiawaseRirekiKokaiFlg()).getExternalSiteVal());
			}
		}
		// �₢���킹�ŏI������ϊ����Đݒ�
		if (lastToiawaseRireki != null) {
			// �ŏI������
			if (jokyoKbnMap != null) {
				model.getToiawaseInfo().setLastJokyoKbnNm(jokyoKbnMap.get(lastToiawaseRireki.getJokyoKbn()));
			}
			// �ŏI������t
			model.getToiawaseInfo().setLastRirekiYmd(lastToiawaseRireki.getUketsukeYmd());
			// �ŏI��������
			model.getToiawaseInfo().setLastRirekiJikan(lastToiawaseRireki.getUketsukeJikan());
		}
		
		// ���J���[�����M�������擾
		RcpTMailRireki mailRireki =
			mailRirekiDao.selectByPrimaryKey(model.getToiawaseNo());
		model.setMailRireki(mailRireki);
		
		// �₢���킹�t�@�C�����X�g
		List<RcpTToiawaseFile> toiawaseFileList = 
			toiawaseFileDao.selectBy(model.getToiawaseNo(), null);
		model.setUploadedFiles(createToiawaseFileList(toiawaseFileList));
		
		return model;
	}
	
	/**
	 * �₢���킹����V�K�o�^���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void insertToiawaseInfo(TB023InquiryEntryModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �A�b�v���[�h�t�@�C���Ó����`�F�b�N
		if (model.isExecuteFileUpload()) {
			isValidUploadFile(model);
		}
		
		// �T�[�r�X��ʑÓ����`�F�b�N
		validateServiceShubetsu(model);
		
		// �₢���킹NO�̐V�K���s
		String toiawaseNo = toiawaseDao.makeNewToiawaseNo();
		
		RcpTToiawase toiawase = model.getToiawaseInfo();
		
		// �₢���킹�m�n
		toiawase.setToiawaseNo(toiawaseNo);
		// �ڋq�h�c
		if (StringUtils.isNotBlank(model.getKokyakuId())) {
			toiawase.setKokyakuId(model.getKokyakuId());
		}
		// �񍐏����J�t���O
		toiawase.setHokokushoKokaiFlg(RcpTToiawase.HOKOKUSHO_KOKAI_FLG_MIKOKAI);
		
		// ��t�Җ�
		toiawase.setUketsukeshaNm(userContext.getUserName());
		// �o�^��Ђh�c
		toiawase.setCreKaishaId(userContext.getKaishaId());
		// �ŏI�X�V��Ђh�c
		toiawase.setUpdKaishaId(userContext.getKaishaId());
		// �o�^�Җ�
		toiawase.setCreNm(userContext.getUserName());
		// �ŏI�X�V�Җ�
		toiawase.setLastUpdNm(userContext.getUserName());
		
		// �₢���킹�o�^
		toiawaseDao.insertForTores(model.getToiawaseInfo());
		// �₢���킹����o�^
		toiawaseRirekiDao.insertForTores(createTToiwaseRirekiInfo(model));
		
		// �p�����[�^�̌ڋq�h�c��NULL�̏ꍇ�AID�����ڋq���̓o�^���s��
		if (StringUtils.isBlank(model.getKokyakuId())) {
			model.getKokyakuInfoWithoutId().setToiawaseNo(toiawaseNo);
			model.getKokyakuInfoWithoutId().setCreKaishaId(userContext.getKaishaId());
			model.getKokyakuInfoWithoutId().setUpdKaishaId(userContext.getKaishaId());
			model.getKokyakuInfoWithoutId().setCreNm(userContext.getUserName());
			model.getKokyakuInfoWithoutId().setLastUpdNm(userContext.getUserName());
			kokyakuWithoutIdDao.insertForTores(model.getKokyakuInfoWithoutId());
		}

		model.setToiawaseNo(toiawaseNo);
		if (model.isExecuteFileUpload()) {
			// �t�@�C���w�莞�̓t�@�C���A�b�v���[�h���������{����
			executeFileUpload(model);
		}
		
		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB023InquiryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_INSERT,
				createKensakuJoken(model));
	}
	
	/**
	 * �₢���킹�����X�V���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void updateToiawaseInfo(TB023InquiryEntryModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �˗��L���敪���ύX����āu�����v�̏ꍇ�A�˗���񑶍݃`�F�b�N����
		if (model.isIraiUmuKbnChanged() && model.isIraiKbnNashi()) {
			List<RcpTIrai> irai = iraiDao.selectBy(model.getToiawaseNo(), null);

			if (irai != null && !irai.isEmpty()) {
				// �˗���񂪑��݂���ꍇ�A�X�V�`�F�b�N�G���[
				throw new ValidationException(new ValidationPack().addError("MSG0031", "�˗���񂪑��݂���", "�˗��L��"));
			}
		}
		
		// �₢���킹�����J�ݒ�ۃ`�F�b�N
		isValid(model);

		// �A�b�v���[�h�t�@�C���Ó����`�F�b�N
		if (model.isExecuteFileUpload()) {
			isValidUploadFile(model);
		}

		// �T�[�r�X��ʑÓ����`�F�b�N
		validateServiceShubetsu(model);
		
		// �X�P�W���[�����Ó����`�F�b�N
		validateScheduleInfo(model);
		
		// ��������Ó����`�F�b�N
		validateSeikyusakiInfo(model);
		
		// �₢���킹���̍X�V�O�ɒl��ݒ�
		RcpTToiawase toiawase = model.getToiawaseInfo();
		// �₢���킹�m�n
		toiawase.setToiawaseNo(model.getToiawaseNo());
		// �ڋq�h�c
		if (StringUtils.isNotBlank(model.getKokyakuId())) {
			toiawase.setKokyakuId(model.getKokyakuId());
		}
		// ���J���~�t���O�������Ă���΁A���J�𒆎~����
		if (RcpTToiawase.KOKAI_TYUSHI_FLG.equals(model.getKokaiTyushiFlg())){
			model.getToiawaseInfo().setHokokushoKokaiFlg(RcpTToiawase.HOKOKUSHO_KOKAI_FLG_MIKOKAI);
		}
		// �ŏI�X�V��Ђh�c
		toiawase.setUpdKaishaId(userContext.getKaishaId());
		// �ŏI�X�V�Җ�
		toiawase.setLastUpdNm(userContext.getUserName());
		
		// �X�V��(where)
		toiawase.setUpdDt(model.getToiawaseUpdDt());
		// �ŏI�X�V�҂h�c(where)
		toiawase.setLastUpdId(Constants.TORES_APL_ID);

		// �₢���킹���̍X�V
		int ret = toiawaseDao.updateForTores(model.getToiawaseInfo());
		if (ret == 0) {
			// �X�V�������O���̏ꍇ�A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		// �p�����[�^�̌ڋq�h�c��NULL�̏ꍇ�AID�����ڋq���̍X�V���s��
		if (StringUtils.isBlank(model.getKokyakuId())) {
			model.getKokyakuInfoWithoutId().setUpdKaishaId(userContext.getKaishaId());
			model.getKokyakuInfoWithoutId().setLastUpdNm(userContext.getUserName());
			kokyakuWithoutIdDao.updateForTores(model.getKokyakuInfoWithoutId());
		}

		if (model.isExecuteFileUpload()) {
			// �t�@�C���w�莞�̓t�@�C���A�b�v���[�h���������{����
			executeFileUpload(model);
		}
		
		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB023InquiryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_UPDATE,
				createKensakuJoken(model));
	}

	/**
	 * �₢���킹�����폜���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void deleteToiawaseInfo(TB023InquiryEntryModel model) {
		
		// �₢���킹NO�ɕR�Â��X�P�W���[�������擾
		List<RcpTBillSchedule> rcpTBillScheduleList = billScheduleDao.selectByToiawaseNo(model.getToiawaseNo());
		// �X�P�W���[����񂪑��݂���ꍇ
		if (rcpTBillScheduleList != null && !rcpTBillScheduleList.isEmpty()) {
			throw new ValidationException(new ValidationPack().addError("MSG0043", "�₢���킹�m�n", "�X�P�W���[�����"));
		}

		// �₢���킹���ޔ�
		RcpTToiawase toiawase = model.getToiawaseInfo();
		toiawase.setToiawaseNo(model.getToiawaseNo());
		toiawase.setUpdDt(model.getToiawaseUpdDt());

		// �₢���킹�����e�[�u���̌���
		int rirekiCount = toiawaseRirekiDao.countBy(model.getToiawaseNo(), null);

		// �₢���킹�����̌������Q���ȏ�̏ꍇ
		if (rirekiCount > 1) {
			
			throw new ValidationException(new ValidationPack().addError("MSG0032"));
		}

		int ret = 0;
		// �₢���킹�����̌������P���̏ꍇ�̂ݏ���
		if (rirekiCount == 1) {
			
			// �₢���킹�������ޔ�
			RcpTToiawaseRireki toiawaseRireki = new RcpTToiawaseRireki();
			toiawaseRireki.setToiawaseNo(model.getToiawaseNo());
			toiawaseRireki.setToiawaseRirekiNo(new BigDecimal(1));

			// �₢���킹����ޔ��e�[�u���ɑޔ�
			ret = toiawaseRirekiDao.insertTaihiWithoutOptimisticLock(toiawaseRireki);
			// �X�V�������O���̏ꍇ�A�r���G���[
			if (ret == 0) {
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}

			try {
				// �₢���킹�����e�[�u���̍폜
				toiawaseRirekiDao.deleteWithoutOptimisticLock(
						toiawaseRireki.getToiawaseNo(),
						toiawaseRireki.getToiawaseRirekiNo());
			} catch (DataIntegrityViolationException e) {
				// �O���L�[�Q�ƃG���[�̏ꍇ
				throw new ValidationException(new ValidationPack().addError("MSG0033"));
			}
		}

		// �₢���킹�ޔ��e�[�u���ɑޔ�
		ret = toiawaseDao.insertTaihiWithoutSelfUpdate(model.getToiawaseInfo());
		// �X�V�������O���̏ꍇ�A�r���G���[
		if (ret == 0) {
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		try {
			// �₢���킹�e�[�u���̍폜
			toiawaseDao.deleteWithoutOptimisticLock(model.getToiawaseNo());
		} catch (DataIntegrityViolationException e) {
			// �O���L�[�Q�ƃG���[�̏ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0025"));
		}

		// �p�����[�^�̌ڋq�h�c��NULL�̏ꍇ�AID�����ڋq���̍폜���s��
		if (StringUtils.isBlank(model.getKokyakuId())) {
			// ID�����ڋq�ޔ��e�[�u���ɑޔ�
			ret = kokyakuWithoutIdDao.insertTaihi(model.getKokyakuInfoWithoutId());
			// �X�V�������O���̏ꍇ�A�r���G���[
			if (ret == 0) {
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			// ID�����ڋq�e�[�u���̍폜
			kokyakuWithoutIdDao.deleteWithoutOptimisticLock(model.getKokyakuInfoWithoutId().getToiawaseNo());
		}

		// �₢���킹�t�@�C�����擾
		List<RcpTToiawaseFile> toiawaseFileList = toiawaseFileDao.selectBy(model.getToiawaseNo(), null);
		
		if (toiawaseFileList != null && !toiawaseFileList.isEmpty()) {
			// �₢���킹�t�@�C����񂪂���ꍇ
			RcpTToiawaseFile toiawaseFile = new RcpTToiawaseFile();
			toiawaseFile.setToiawaseNo(model.getToiawaseNo());
			
			// �₢���킹�t�@�C�����ޔ�����
			toiawaseFileDao.insertTaihiBy(toiawaseFile);
			// �₢���킹�t�@�C�����폜����
			toiawaseFileDao.deleteBy(model.getToiawaseNo(), null);
			
			model.setFileDeleteSuccess(true);
			// �A�b�v���[�h�t�@�C���̍폜����
			for (RcpTToiawaseFile uploadFileInfo : toiawaseFileList) {
				// �A�b�v���[�h�t�@�C���폜
				String fileNm = UPLOAD_PATH_INQUIRY_FILE +
								System.getProperty("file.separator") +
								uploadFileInfo.getUploadFileNm();

				// �A�b�v���[�h���t�@�C���폜����
				if (!FileUploadLogic.deleteServerFile(fileNm)) {
					// �t�@�C���폜�����̎��s
					model.setFileDeleteSuccess(false);
				}
			}
		}
		
		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB023InquiryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_DELETE,
				createKensakuJoken(model));
	}
	
	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return ��������
	 */
	private String createKensakuJoken(TB023InquiryEntryModel model) {
		
		StringBuilder accesslog = new StringBuilder("");
		
		// �o�^�A�X�V
		if (model.isInsert() || model.isUpdate()) {
			
			NullExclusionToStringBuilder toiawaseEntry =
				new NullExclusionToStringBuilder(
					model.getToiawaseInfo(),
					NullExclusionToStringBuilder.CSV_STYLE, null, null,
					false, false);
			
			// ���O���鍀��
			List<String> excludeFiledList = new ArrayList<String>(Arrays.asList(Constants.EXCLUDE_FIELD_NAMES));
			
			// ���̕����́A�o�͂��Ȃ�
			excludeFiledList.add("uketsukeNm");
			excludeFiledList.add("lastJokyoKbnNm");
			excludeFiledList.add("lastRirekiYmd");
			excludeFiledList.add("lastRirekiJikan");

			toiawaseEntry.setExcludeFieldNames((String[]) excludeFiledList.toArray(new String[0]));

			accesslog.append(toiawaseEntry.toString());
			
		// �폜
		} else if (model.isDelete()) {
			accesslog.append("toiawaseNo=");
			accesslog.append(model.getToiawaseNo());
		}
		
		return accesslog.toString();
	}
	
	/**
	 * �o�^����₢���킹�������𐶐����܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�������
	 */
	private RcpTToiawaseRireki createTToiwaseRirekiInfo(TB023InquiryEntryModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		RcpTToiawaseRireki toiawaseRireki = new RcpTToiawaseRireki();

		RcpTToiawase toiawase = model.getToiawaseInfo();

		toiawaseRireki.setToiawaseNo(toiawase.getToiawaseNo());
		toiawaseRireki.setToiawaseRirekiNo(new BigDecimal(1));
		toiawaseRireki.setIraishaKbn(toiawase.getIraishaKbn());
		toiawaseRireki.setIraishaNm(toiawase.getIraishaNm());
		toiawaseRireki.setIraishaTel(toiawase.getIraishaTel());
		toiawaseRireki.setIraishaRoomNo(toiawase.getIraishaRoomNo());
		toiawaseRireki.setIraishaMemo(toiawase.getIraishaMemo());
		toiawaseRireki.setUketsukeYmd(toiawase.getUketsukeYmd());
		toiawaseRireki.setUketsukeJikan(toiawase.getUketsukeJikan());
		toiawaseRireki.setToiawaseKbn1(toiawase.getToiawaseKbn1());
		toiawaseRireki.setToiawaseKbn2(toiawase.getToiawaseKbn2());
		toiawaseRireki.setToiawaseKbn3(toiawase.getToiawaseKbn3());
		toiawaseRireki.setToiawaseKbn4(toiawase.getToiawaseKbn4());
		toiawaseRireki.setToiawaseNaiyo(toiawase.getToiawaseNaiyo());
		toiawaseRireki.setJokyoKbn(RcpMJokyoKbn.RCP_M_JOKYO_KBN_SHOKAIUKETSUKE);
		
		if (StringUtils.isNotBlank(toiawase.getKokyakuId())) {
			toiawaseRireki.setHoukokuPrintFlg(RcpTToiawaseRireki.HOUKOKU_PRINT_FLG_ON);
		} else {
			toiawaseRireki.setHoukokuPrintFlg(RcpTToiawaseRireki.HOUKOKU_PRINT_FLG_OFF);
		}
		// �₢���킹����V�K�o�^���A�񍐏����J�t���O�͖����J���Z�b�g
		toiawaseRireki.setToiawaseRirekiKokaiFlg(RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_MIKOKAI);
		toiawaseRireki.setTantoshaNm(userContext.getUserName());
		toiawaseRireki.setCreKaishaId(userContext.getKaishaId());
		toiawaseRireki.setUpdKaishaId(userContext.getKaishaId());
		toiawaseRireki.setCreNm(userContext.getUserName());
		toiawaseRireki.setLastUpdNm(userContext.getUserName());
		
		return toiawaseRireki;
	}
	
	/**
	 * �X�V�`�F�b�N�������s���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @throws ValidationException ���̓G���[�����������ꍇ
	 */
	private void isValid(TB023InquiryEntryModel model) {
		// �˗������J�ݒ�ۃ`�F�b�N
		boolean isValidPublish = toiawaseCheckLogic.isValid(
				CheckToPublishToiawaseLogic.CONTENT_TYPE_TOIAWASE,
				model.getToiawaseInfo().getToiawaseKokaiFlg(),
				model.getToiawaseNo(),
				null);

		if (!isValidPublish) {
			String publishMsg = model.getToiawaseInfo().isPublished() ? "���J" : "�����J��";
			// ���J�ݒ肪�����̏ꍇ�A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0030", "�₢���킹���", publishMsg));
		}
	}
	
	/**
	 * ���[�_�E�����[�h���̎擾���s���܂��B
	 * 
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �₢���킹�o�^��ʃ��f��
	 */
	public TB023InquiryEntryModel getPrintDownloadInfo(TB023InquiryEntryModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		model.setDownloadable(false);
		
		if (userContext.isRealEstate()) {
			// �Z�b�V�����̌������u20�F�Ǘ���Ёv�̏ꍇ
			if (!toiawaseDao.isOwn(model.getToiawaseNo(), userContext.getRefKokyakuId())) {
				// �₢���킹���`�F�b�NNG�̏ꍇ�́A�_�E�����[�h�G���[
				return model;
			}
		} else if ((userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) && StringUtils.isNotBlank(model.getTargetKokyakuId())) {
			// �Z�b�V�����̌������u40:�ϑ����SV�v�܂��́u41:�ϑ����OP�v�̏ꍇ
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), model.getTargetKokyakuId())) {
				// �ϑ���Њ֘A�`�F�b�NNG�̏ꍇ�́A�_�E�����[�h�G���[
				return model;
			}
		}
		
		// �₢���킹���擾
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// �₢���킹��񂪎擾�ł��Ȃ��ꍇ�́A�_�E�����[�h�G���[
			return model;
		}
		
		model.setDownloadable(true);
		model.setToiawaseInfo(toiawase);
		
		return model;
	}
	
	/**
	 * �₢���킹�t�@�C�������폜���܂��B
	 * 
	 * @param model  �₢���킹�o�^��ʃ��f��
	 */
	public void deleteToiawaseFileInfo(TB023InquiryEntryModel model) {
		// ���[�U���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		RcpTToiawase toiawase = new RcpTToiawase();
		toiawase.setToiawaseNo(model.getToiawaseNo());
		toiawase.setLastUpdId(userContext.getLoginId());
		toiawase.setUpdDt(model.getToiawaseUpdDt());
		toiawase.setUpdKaishaId(userContext.getKaishaId());
		toiawase.setLastUpdNm(userContext.getUserName());
		
		// �₢���킹���̍X�V���X�V
		if (toiawaseDao.updateUpdDt(toiawase) == 0) {
			// �X�V�������O���̏ꍇ�́A�r���`�F�b�N�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		RcpTToiawaseFile toiawaseFile = new RcpTToiawaseFile();
		toiawaseFile.setToiawaseNo(model.getToiawaseNo());
		toiawaseFile.setFileIndex(model.getFileIndex());
		
		// �₢���킹�t�@�C�����̑ޔ�
		toiawaseFileDao.insertTaihiWithoutOptimisticLock(toiawaseFile);
		// �₢���킹�t�@�C�����̍폜
		toiawaseFileDao.deleteWithoutOptimisticLock(model.getToiawaseNo(), model.getFileIndex());
		
		// �A�N�Z�X���O�o�^�i�A�N�V�����^�C�v��ݒ肵�Ă��Ȃ����߁A�����ɋL�q�j
		StringBuilder accesslog = new StringBuilder("");
		accesslog.append("toiawaseNo=");
		accesslog.append(model.getToiawaseNo());
		accesslog.append(",");
		accesslog.append("fileIndex=");
		accesslog.append(model.getFileIndex().toPlainString());
		accesslog.append(",");
		accesslog.append("uploadFileNm=");
		accesslog.append(model.getUploadFileNm());
		
		tbAccesslogDao.insert(TB023InquiryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_DELETE,
				accesslog.toString());
		
		// �A�b�v���[�h�t�@�C���폜
		String fileNm = UPLOAD_PATH_INQUIRY_FILE +
						System.getProperty("file.separator") +
						model.getUploadFileNm();

		// �A�b�v���[�h���t�@�C���폜����
		model.setFileDeleteSuccess(FileUploadLogic.deleteServerFile(fileNm));
	}
	
	/**
	 * �₢���킹�t�@�C������\�����ɍ쐬���܂��B
	 *
	 * @param uploadedList �t�@�C���A�b�v���[�h��񃊃X�g
	 * @param dispMax �ő�\������
	 * @return �t�@�C���A�b�v���[�h���z��
	 */
	private RcpTToiawaseFile[] createToiawaseFileList(List<RcpTToiawaseFile> uploadedList) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �V�X�e���}�X�^����Y�t�t�@�C���\�ő匏���擾
		int maxFileListSize = 
			userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_BSB_TOIAWASE_FILE_TO_MAX);
		
		
		RcpTToiawaseFile[] toiawaseFiles = new RcpTToiawaseFile[maxFileListSize];

		if (uploadedList == null || uploadedList.isEmpty()) {
			return toiawaseFiles;
		}

		for (int i = 0; i < maxFileListSize; i++) {
			// ���݂̃t�@�C���C���f�b�N�X
			int currentIdx = i + 1;

			for (RcpTToiawaseFile toiawaseFile : uploadedList) {
				if (currentIdx == toiawaseFile.getFileIndex().intValue()) {
					// �f�[�^������ꍇ�́A�z��Ɋi�[
					toiawaseFiles[i] = toiawaseFile;
					break;
				}
			}
		}

		return toiawaseFiles;
	}
	
	/**
	 * �A�b�v���[�h�t�@�C���̑Ó����`�F�b�N���s���܂��B
	 * 
	 * @param model �₢���킹�o�^��ʃ��f��
	 */
	private void isValidUploadFile(TB023InquiryEntryModel model) {
		// ���[�U���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �ő�t�@�C���o�C�g���擾
		Long maxFileSize = 
			Long.parseLong(userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_BSB_TOIAWASE_FILESIZE_TO_MAX));
		
		for (File uploaFile : model.getToiawaseFiles()) {
			if (uploaFile == null) {
				// �t�@�C�����w�肳��Ă��Ȃ���΁A�������Ȃ�
				continue;
			}
			
			// �t�@�C���T�C�Y�`�F�b�N
			String result = FileUploadLogic.checkFileSize(uploaFile, maxFileSize);

			if (FileUploadLogic.SIZE_ZERO.equals(result)) {
				// �t�@�C���T�C�Y�O�̏ꍇ
				throw new ValidationException(new ValidationPack().addError("MSG0020"));
			} else if (FileUploadLogic.SIZE_OVER.equals(result)) {
				// �t�@�C���T�C�Y�I�[�o�[�̏ꍇ
				throw new ValidationException(new ValidationPack().addError("MSG0021", maxFileSize.toString()));
			}
		}
	}
	
	/**
	 * �t�@�C���̃A�b�v���[�h���������s���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 */
	private void executeFileUpload(TB023InquiryEntryModel model) {
		List<String> uploadFileNameList = new ArrayList<String>();
		List<File> uploadFileList = new ArrayList<File>();

		String[] uploadFileNames = model.getToiawaseFileNmByArray();
		// �w�肳�ꂽ�t�@�C�����A�o�^
		for (int i = 0; i < uploadFileNames.length; i++) {
			String toiawaseFileName = uploadFileNames[i];

			if (StringUtils.isBlank(toiawaseFileName)) {
				// �t�@�C�����w�肳��Ă��Ȃ���΁A���̏�����
				continue;
			}
			File uploadFile = model.getToiawaseFiles()[i];
			// �t�@�C���C���f�b�N�X
			int fileIndex = i + 1;

			// �t�@�C�����̐���
			String uploadFileName = model.getToiawaseNo() + "-" + fileIndex;
			// �A�b�v���[�h�t�@�C�����̐���
			uploadFileName =
				FileUploadLogic.makeUploadFileNm(toiawaseFileName, uploadFileName);

			// �₢���킹�t�@�C���e�[�u���̓o�^
			RcpTToiawaseFile toiawaseFile = new RcpTToiawaseFile();
			toiawaseFile.setToiawaseNo(model.getToiawaseNo());
			toiawaseFile.setFileIndex(new BigDecimal(fileIndex));
			toiawaseFile.setUploadFileNm(uploadFileName);
			toiawaseFile.setBaseFileNm(FilenameUtils.getName(new File(toiawaseFileName).getName()));
			
			toiawaseFileDao.insert(toiawaseFile);

			uploadFileNameList.add(uploadFileName);
			uploadFileList.add(uploadFile);
		}

		// �t�@�C���̃A�b�v���[�h
		FileUploadLogic.uploadFileList(uploadFileList,
				uploadFileNameList, UPLOAD_PATH_INQUIRY_FILE);
	}
	
	/**
	 * �₢���킹�t�@�C�����̃_�E�����[�h�`�F�b�N���s���܂��B
	 * 
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �`�F�b�N���� true�F�`�F�b�NOK�Afalse�F�`�F�b�NNG
	 */
	public boolean isDownloableToiawaseFile(TB023InquiryEntryModel model) {
		// �₢���킹���擾
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// �₢���킹��񂪂Ȃ���΁A�`�F�b�NNG
			return false;
		}
		
		// �₢���킹�t�@�C�����擾
		RcpTToiawaseFile toiawaseFile = 
			toiawaseFileDao.selectByPrimaryKey(model.getToiawaseNo(), model.getFileIndex());
		if (toiawaseFile == null) {
			// �₢���킹�t�@�C����񂪂Ȃ���΁A�`�F�b�NNG
			return false;
		}
		
		model.setUploadFileNm(toiawaseFile.getUploadFileNm());
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		if (userContext.isRealEstate()) {
			// �Z�b�V�����̌������u20�F�Ǘ���Ёv�̏ꍇ
			// ���₢���킹���e�ڍׂɂċ@�\�����L�����邽�߁A�`�F�b�N��ǉ�
			return toiawaseDao.isOwn(model.getToiawaseNo(), userContext.getRefKokyakuId());
		} else if ((userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) && StringUtils.isNotBlank(toiawase.getKokyakuId())) {
			// �Z�b�V�����̌������u40:�ϑ����SV�v�܂��́u41:�ϑ����OP�v�̏ꍇ
			return outsourcerValidationLogic.isValid(userContext.getKaishaId(), toiawase.getKokyakuId());
		} else {
			return true;
		}
	}

	/**
	 * �T�[�r�X��ʑÓ����`�F�b�N
	 * 
	 * @param model model �₢���킹�o�^��ʃ��f��
	 */
	private void validateServiceShubetsu(TB023InquiryEntryModel model) {
		// �ڋqID�Ȃ��@���@�T�[�r�X��ʂ��u�r���Ǘ��v�̏ꍇ
		if (StringUtils.isBlank(model.getKokyakuId())
			&& RcpMService.SERVICE_SHUBETSU_BUILDING.equals(model.getToiawaseInfo().getServiceShubetsu())) {
			// ���b�Z�[�W��\��
			throw new ValidationException(new ValidationPack().addError("MSG0045"));
		}
	}

	/**
	 * �X�P�W���[�����Ó����`�F�b�N
	 * 
	 * @param model model �₢���킹�o�^��ʃ��f��
	 */
	private void validateScheduleInfo(TB023InquiryEntryModel model) {
		// �₢���킹NO�ɕR�Â��X�P�W���[�������擾
		List<RcpTBillSchedule> rcpTBillScheduleList = billScheduleDao.selectByToiawaseNo(model.getToiawaseNo());
		// �X�P�W���[����񂪑��݂���ꍇ
		if (rcpTBillScheduleList != null && !rcpTBillScheduleList.isEmpty()) {
			// �T�[�r�X��ʂ��u�r���Ǘ��v�ȊO�̏ꍇ
			if (!RcpMService.SERVICE_SHUBETSU_BUILDING.equals(model.getToiawaseInfo().getServiceShubetsu())) {
				// ���b�Z�[�W��\��
				throw new ValidationException(new ValidationPack().addError("MSG0044", "�T�[�r�X���", "�r���Ǘ��ȊO"));
			}
			// �敪�P���u���p���v�ȊO�̏ꍇ
			if (!RcpMToiawaseKbn1.KBN_KYOYOBU.equals(model.getToiawaseInfo().getToiawaseKbn1())) {
				// ���b�Z�[�W��\��
				throw new ValidationException(new ValidationPack().addError("MSG0044", "�敪�P", "���p���ȊO"));
			}
			// �敪�Q���u�r���Ǘ��v�ȊO�̏ꍇ
			if (!RcpMToiawaseKbn2.KBN_BUILDING.equals(model.getToiawaseInfo().getToiawaseKbn2())) {
				// ���b�Z�[�W��\��
				throw new ValidationException(new ValidationPack().addError("MSG0044", "�敪�Q", "�r���Ǘ��ȊO"));
			}
			// �X�P�W���[�����̂P���ڂ̒�z�E�X�|�b�g�敪���u��z�v�̏ꍇ
			RcpTBillSchedule rcpTBillSchedule = rcpTBillScheduleList.get(0);
			if (rcpTBillSchedule.isTeigaku()) {
				// �敪�R���u��z�v�ȊO�̏ꍇ
				if (!RcpMToiawaseKbn3.KBN_TEIGAKU.equals(model.getToiawaseInfo().getToiawaseKbn3())) {
					// ���b�Z�[�W��\��
					throw new ValidationException(new ValidationPack().addError("MSG0044", "�敪�R", "��z�ȊO"));
				}
			} else if (rcpTBillSchedule.isSpot()) {
				// �敪�R���u�X�|�b�g�v�ȊO�̏ꍇ
				if (!RcpMToiawaseKbn3.KBN_SPOT.equals(model.getToiawaseInfo().getToiawaseKbn3())) {
					// ���b�Z�[�W��\��
					throw new ValidationException(new ValidationPack().addError("MSG0044", "�敪�R", "�X�|�b�g�ȊO"));
				}
			}
		}
	}
	
	/**
	 * ��������̑Ó����`�F�b�N���s���܂��B
	 * ���T�[�r�X��ʂ��r���Ǘ����w�肳�ꂽ�ꍇ�̂ݍs���܂��B
	 * 
	 * @param model �₢���킹�o�^��ʃ��f��
	 */
	private void validateSeikyusakiInfo(TB023InquiryEntryModel model) {
		if (!RcpMService.SERVICE_SHUBETSU_BUILDING.equals(model.getToiawaseInfo().getServiceShubetsu())) {
			// �T�[�r�X��ʈȊO�̏ꍇ�́A�`�F�b�N���Ȃ�
			return;
		}
		
		// ��Ɣ���擾
		List<RcpTSagyohi> sagyohiList = sagyohiDao.selectBy(model.getToiawaseNo(), null);
		
		if (sagyohiList == null || sagyohiList.isEmpty()) {
			// ��Ɣ��񂪑��݂��Ȃ��ꍇ�́A�`�F�b�N���Ȃ�
			return;
		}
		
		// �`�F�b�N�p������ڋq�h�c
		String chkSeikyusakiKokyakuId = "";
		
		for (RcpTSagyohi sagyohi : sagyohiList) {
			if (StringUtils.isBlank(sagyohi.getSeikyusakiKokyakuId())) {
				// ��Ɣ�̐�����ڋq�h�c���P���ł�NULL������΁A�`�F�b�NNG
				throw new ValidationException(new ValidationPack().addError("MSG0047"));
			} else {
				if (StringUtils.isBlank(chkSeikyusakiKokyakuId)) {
					// �`�F�b�N�p������ڋq�h�c���󗓂̏ꍇ�́A�ݒ�
					chkSeikyusakiKokyakuId = sagyohi.getSeikyusakiKokyakuId();
				}
				
				if (!sagyohi.getSeikyusakiKokyakuId().equals(chkSeikyusakiKokyakuId)) {
					// �`�F�b�N�p������ڋq�h�c�ƒl���قȂ��Ă���ꍇ
					throw new ValidationException(new ValidationPack().addError("MSG0047"));
				}
			}
		}
	}
}
