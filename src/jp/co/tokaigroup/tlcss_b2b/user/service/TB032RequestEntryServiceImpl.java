package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.FileUploadLogic;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpTFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTOtherFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyoJokyoDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTFileUploadDao;
import jp.co.tokaigroup.reception.dao.TbTSagyoJokyoDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTOtherFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.TbTFileUpload;
import jp.co.tokaigroup.reception.entity.TbTSagyoJokyo;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.dto.TB032FileUploadDto;
import jp.co.tokaigroup.tlcss_b2b.dto.TB032SagyoJokyoDto;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB032RequestEntryModel;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �˗����e�ڍׁE��Ə󋵓o�^�T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 4.0 2014/07/14
 * @version 4.1 2016/02/18 C.Kobayashi �˗��t�@�C���A�b�v���[�h�A�˗����̑��t�@�C���A�b�v���[�h�@�t�@�C���C���f�b�N�X�Ή�
 * @version 4.2 2016/03/01 J.Matsuba �Y�t�t�@�C���ꗗ�ő匏���̎擾�̏C��
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB032RequestEntryServiceImpl extends TLCSSB2BBaseService
		implements TB032RequestEntryService {
	// �v���p�e�B�t�@�C������擾
	/** ��Ə󋵃t�@�C���A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_SAGYO_JOKYO = ResourceFactory.getResource().getString("UPLOAD_PATH_SAGYO_JOKYO");

	/** ���Z�v�V�����₢���킹�e�[�u��DAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;
	/** ���Z�v�V�����₢���킹�����e�[�u��DAO */
	@Autowired
	private RcpTToiawaseRirekiDao toiawaseRirekiDao;
	/** ���Z�v�V�����˗��e�[�u��DAO */
	@Autowired
	private RcpTIraiDao iraiDao;
	/** ���Z�v�V������Ə󋵃e�[�u��DAO */
	@Autowired
	private RcpTSagyoJokyoDao sagyoJokyoDao;
	/** ���Z�v�V�����˗��t�@�C���A�b�v���[�h�e�[�u��DAO */
	@Autowired
	private RcpTFileUploadDao fileUploadDao;
	/** ���Z�v�V�����˗����̑��t�@�C���A�b�v���[�h�e�[�u��DAO */
	@Autowired
	private RcpTOtherFileUploadDao otherFileUploadDao;
	/** �O���T�C�g �Ǝ҉񓚍�Ə󋵃e�[�u��DAO */
	@Autowired
	private TbTSagyoJokyoDao gyoshaSagyoJokyoDao;
	/**  �O���T�C�g �Ǝ҉񓚈˗��t�@�C���A�b�v���[�h�e�[�u��DAO */
	@Autowired
	private TbTFileUploadDao gyoshaFileUploadDao;
	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;
	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic commonInfoLogic;

	/** �ϑ���Њ֘A�`�F�b�N���W�b�N */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	
	/**
	 * �����\���i�ڍו\���j���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	public TB032RequestEntryModel getDetailInitInfo(TB032RequestEntryModel model) {
		// �����p�����[�^�`�F�b�N
		validateInitParameter(model);

		// �V�X�e���}�X�^����萔�擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		model.setUserContext(userContext);
		// �Y�t�\�ő匏��
		model.setDisplayFileCount(userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_APPENEDED_MAX));
		// �摜�t�@�C���Y�t�\�ő匏��
		model.setImageFileDisplayFileCount(userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_IRAI_FILE_UPDATE_TO_MAX));
		// ���̑��t�@�C���Y�t�\�ő匏��
		model.setOtherFileDisplayFileCount(userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_IRAI_OTHER_FILE_UPDATE_TO_MAX));

		// ���O�C�����[�U�[���Г��ȊO�A�₢���킹���̃`�F�b�N
		if (!userContext.isInhouse()
			&& !iraiDao.isOwn(model.getToiawaseNo(), model.getToiawaseRirekiNo(),userContext.getRefKokyakuId(), userContext.getGyoshaCd())) {
			// �A�N�Z�X�s�̏ꍇ�́A403�G���[����ʂɕ\��
			return null;
		}
		
		// �˗����擾
		model = initIraiInfo(model);
		if (model.isNotPublish()) {
			// �˗����i�₢���킹���A�₢���킹�������j�����J�ςłȂ��ꍇ�A�����ŏ����I��
			return model;
		}

		// �a���ϊ��pMap�擾
		Map<String, Map<String, RcpMComCd>> convertMap = getConvertMap();

		// �K��\���Map
		Map<String, RcpMComCd> homonYoteiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		// �����t���OMap
		Map<String, RcpMComCd> complateFlgMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);

		RcpTIrai irai = model.getIrai();
		if (StringUtils.isNotBlank(irai.getHomonKiboJikanKbn()) &&
			homonYoteiMap.containsKey(irai.getHomonKiboJikanKbn())) {
			// �K��\����敪��
			irai.setHomonKiboJikanKbnNm(homonYoteiMap.get(irai.getHomonKiboJikanKbn()).getExternalSiteVal());
		}
		model.setIrai(irai);

		// ��Ə󋵃e�[�u�������擾
		RcpTSagyoJokyo sagyoJokyo = sagyoJokyoDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo());

		List<RcpTFileUpload> fileUploadList = null;
		List<RcpTOtherFileUpload> otherFileUploadList = null;
		if (sagyoJokyo != null) {
			// ��Ə󋵃e�[�u����񂪑��݂����ꍇ�́A�˗��t�@�C���A�b�v���[�h�����擾
			fileUploadList = fileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);

			// �˗����̑��t�@�C���A�b�v���[�h�����擾
			otherFileUploadList = otherFileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
		}

		TB032SagyoJokyoDto sagyoJokyoDto = null;
		List<TB032FileUploadDto> fileUploadDtoList = null;
		TB032FileUploadDto[] uploadedFiles = null;
		RcpTOtherFileUpload[] otherFileUploadedFiles = null;
		// ���ʃI�u�W�F�N�g�ɃR�s�[
		if (sagyoJokyo != null) {
			sagyoJokyoDto = new TB032SagyoJokyoDto(sagyoJokyo);
			fileUploadDtoList = new ArrayList<TB032FileUploadDto>();
			if (!(fileUploadList == null || fileUploadList.isEmpty())) {
				for (RcpTFileUpload fileUpload : fileUploadList) {
					fileUploadDtoList.add(new TB032FileUploadDto(fileUpload));
				}
			}

			if (StringUtils.isNotBlank(sagyoJokyoDto.getSagyoKanryoFlg()) &&
				complateFlgMap.containsKey(sagyoJokyoDto.getSagyoKanryoFlg())) {
				// �����t���O
				sagyoJokyoDto.setSagyoKanryoFlgNm(complateFlgMap.get(sagyoJokyoDto.getSagyoKanryoFlg()).getExternalSiteVal());
			}

			// �ő�\�������Ɠ������̗v�f���쐬
			uploadedFiles =
				createFileUploadDtoListByTLCSS(fileUploadDtoList, model.getImageFileDisplayFileCount());
			otherFileUploadedFiles =
				createOtherFileUploadList(otherFileUploadList, model.getOtherFileDisplayFileCount());
		} else {
			// �ő�\�������Ɠ������̗v�f���쐬
			uploadedFiles = new TB032FileUploadDto[model.getImageFileDisplayFileCount()];
			otherFileUploadedFiles = new RcpTOtherFileUpload[model.getOtherFileDisplayFileCount()];
		}

		model.setSagyoJokyo(sagyoJokyoDto);
		model.setUploadedFiles(uploadedFiles);
		model.setOtherUploadedFiles(otherFileUploadedFiles);

		return model;
	}

	/**
	 * �����\���i�o�^�\���j���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	public TB032RequestEntryModel getEntryInitInfo(TB032RequestEntryModel model) {
		// �����p�����[�^�`�F�b�N
		validateInitParameter(model);

		// �V�X�e���}�X�^����萔�擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		model.setUserContext(userContext);
		// �Y�t�\�ő匏��
		model.setDisplayFileCount(userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_APPENEDED_MAX));

		// �A�N�Z�X�����̃`�F�b�N
		if (!iraiDao.isOwn(model.getToiawaseNo(), model.getToiawaseRirekiNo(),
				userContext.getRefKokyakuId(), userContext.getGyoshaCd())) {
			// �A�N�Z�X�s�̏ꍇ�́A403�G���[����ʂɕ\��
			return null;
		}
		
		// �˗����擾
		model = initIraiInfo(model);

		if (model.isNotPublish()) {
			// �˗����i�₢���킹���A�₢���킹�������j�����J�ςłȂ��ꍇ�A�����ŏ����I��
			return model;
		}

		// �a���ϊ��pMap�擾
		Map<String, Map<String, RcpMComCd>> convertMap = getConvertMap();

		// �K��\���Map
		Map<String, RcpMComCd> homonYoteiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);

		RcpTIrai irai = model.getIrai();
		if (StringUtils.isNotBlank(irai.getHomonKiboJikanKbn()) &&
			homonYoteiMap.containsKey(irai.getHomonKiboJikanKbn())) {
			// �K��\����敪��
			irai.setHomonKiboJikanKbnNm(homonYoteiMap.get(irai.getHomonKiboJikanKbn()).getExternalSiteVal());
		}
		model.setIrai(irai);

		TB032SagyoJokyoDto sagyoJokyoDto = null;
		List<TB032FileUploadDto> fileUploadDtoList = null;
		TB032FileUploadDto[] uploadedFiles = null;

		// �Ǝ҉񓚍�Ə󋵃e�[�u�������擾
		TbTSagyoJokyo gyoshaSagyoJokyo = gyoshaSagyoJokyoDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo());

		List<TbTFileUpload> gyoshaFileUploadList = null;
		if (gyoshaSagyoJokyo != null) {
			// �Ǝ҉񓚍�Ə󋵃e�[�u����񂪑��݂����ꍇ�́A�Ǝ҉񓚈˗��t�@�C���A�b�v���[�h�����擾
			gyoshaFileUploadList = gyoshaFileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
		}

		if (gyoshaSagyoJokyo != null) {
			// ���ʃI�u�W�F�N�g�ɃR�s�[
			sagyoJokyoDto = new TB032SagyoJokyoDto(gyoshaSagyoJokyo);
			fileUploadDtoList = new ArrayList<TB032FileUploadDto>();
			if (!(gyoshaFileUploadList == null || gyoshaFileUploadList.isEmpty())) {
				for (TbTFileUpload fileUpload : gyoshaFileUploadList) {
					fileUploadDtoList.add(new TB032FileUploadDto(fileUpload));
				}
			}

			// ��Ə󋵓o�^�̏ꍇ�́A�f�[�^�����Ԃɕ\��
			uploadedFiles = createFileUploadDtoList(fileUploadDtoList, model.getDisplayFileCount());
		} else {
			// �ő�\�������Ɠ������̗v�f���쐬
			uploadedFiles = new TB032FileUploadDto[model.getDisplayFileCount()];
		}

		model.setSagyoJokyo(sagyoJokyoDto);
		model.setUploadedFiles(uploadedFiles);

		return model;
	}

	/**
	 * �����\���i�o�^�\���E�T�[�o�G���[���j���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	public TB032RequestEntryModel getEntryPrepareInitInfo(TB032RequestEntryModel model) {
		// �V�X�e���}�X�^����萔�擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		model.setUserContext(userContext);
		// �Y�t�\�ő匏��
		model.setDisplayFileCount(userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_APPENEDED_MAX));

		// �˗����擾
		model = initIraiInfo(model);

		if (model.isNotPublish()) {
			// �˗����i�₢���킹���A�₢���킹�������j�����J�ςłȂ��ꍇ�A�����ŏ����I��
			return model;
		}

		// �a���ϊ��pMap�擾
		Map<String, Map<String, RcpMComCd>> convertMap = getConvertMap();

		// �K��\���Map
		Map<String, RcpMComCd> homonYoteiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);

		RcpTIrai irai = model.getIrai();
		if (StringUtils.isNotBlank(irai.getHomonKiboJikanKbn()) &&
			homonYoteiMap.containsKey(irai.getHomonKiboJikanKbn())) {
			// �K��\����敪��
			irai.setHomonKiboJikanKbnNm(homonYoteiMap.get(irai.getHomonKiboJikanKbn()).getExternalSiteVal());
		}
		model.setIrai(irai);

		TB032FileUploadDto[] uploadedFiles = null;

		if (model.isInsert()) {
			uploadedFiles = new TB032FileUploadDto[model.getDisplayFileCount()];
		} else {
			List<TbTFileUpload> gyoshaFileUploadList = null;
			// �Ǝ҉񓚍�Ə󋵃e�[�u����񂪑��݂����ꍇ�́A�Ǝ҉񓚈˗��t�@�C���A�b�v���[�h�����擾
			gyoshaFileUploadList = gyoshaFileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			List<TB032FileUploadDto> fileUploadDtoList = new ArrayList<TB032FileUploadDto>();
			if (!(gyoshaFileUploadList == null || gyoshaFileUploadList.isEmpty())) {
				for (TbTFileUpload fileUpload : gyoshaFileUploadList) {
					fileUploadDtoList.add(new TB032FileUploadDto(fileUpload));
				}
			}

			// ��Ə󋵓o�^�̏ꍇ�́A�f�[�^�����Ԃɕ\��
			uploadedFiles = createFileUploadDtoList(fileUploadDtoList, model.getDisplayFileCount());
		}

		model.setUploadedFiles(uploadedFiles);

		return model;
	}

	/**
	 * �摜�폜�������s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void deleteImageInfo(TB032RequestEntryModel model) {
		// �摜�폜���p�����[�^�`�F�b�N
		validateDeleteImageParameter(model);

		// �Ǝ҉񓚍�Ə󋵃e�[�u���̍X�V�����X�V
		TbTSagyoJokyo sagyoJokyo = new TbTSagyoJokyo();
		sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
		sagyoJokyo.setToiawaseRirekiNo(model.getToiawaseRirekiNo());

		gyoshaSagyoJokyoDao.updateUpdDt(sagyoJokyo);

		// �Ǝ҉񓚈˗��t�@�C���A�b�v���[�h�����폜
		gyoshaFileUploadDao.deleteWithoutOptimisticLock(
				model.getToiawaseNo(),
				model.getToiawaseRirekiNo(),
				model.getFileIndex());

		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB032RequestEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_DELETE,
				createKensakuJokenAsDeleteImage(model));

		// �A�b�v���[�h���t�@�C���폜����
		String fileNm = UPLOAD_PATH_SAGYO_JOKYO +
						System.getProperty("file.separator") +
						model.getUploadFileNm();

		// �A�b�v���[�h���t�@�C���̍폜
		model.setSuccessDeleteImage(FileUploadLogic.deleteServerFile(fileNm));
	}

	/**
	 * �Ǝ҉񓚍�Ə󋵓o�^�������s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @throws ValidationException �t�@�C���T�C�Y�O�̏ꍇ�������́A�t�@�C���T�C�Y�I�[�o�[�̏ꍇ
	 */
	@Transactional(value="txManager")
	public void insertGyoshaSagyoJokyoInfo(TB032RequestEntryModel model) {
		// �o�^�`�F�b�N
		validateInsert(model);

		// �Ǝ҉񓚍�Ə󋵃e�[�u���̓o�^
		TbTSagyoJokyo sagyoJokyo = model.getSagyoJokyo().toTbTSagyoJokyo();
		sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
		sagyoJokyo.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		sagyoJokyo.setKakuninJokyo(TbTSagyoJokyo.KAKUNIN_JOKYO_MIKAKUNIN);

		gyoshaSagyoJokyoDao.insert(sagyoJokyo);

		if (model.isUploadExecute()) {
			// �t�@�C���A�b�v���[�h����
			executeUploadFile(model);

			// �A�N�Z�X���O�Ƀt�@�C������o�^
			List<String> uploadFileList = new ArrayList<String>();
			for (String fileNm : model.getImageFileNmByArray()) {
				if (StringUtils.isBlank(fileNm)) {
					continue;
				}

				uploadFileList.add(fileNm);
			}
			sagyoJokyo.setUploadFileList(uploadFileList);
		}

		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB032RequestEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_INSERT,
				createKensakuJokenAsUpdate(sagyoJokyo));
	}

	/**
	 * �Ǝ҉񓚍�Ə󋵍X�V�������s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void updateGyoshaSagyoJokyoInfo(TB032RequestEntryModel model) {
		// �o�^�`�F�b�N
		validateInsert(model);

		// �Ǝ҉񓚍�Ə󋵃e�[�u���̍X�V
		TbTSagyoJokyo sagyoJokyo = model.getSagyoJokyo().toTbTSagyoJokyo();
		sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
		sagyoJokyo.setToiawaseRirekiNo(model.getToiawaseRirekiNo());

		gyoshaSagyoJokyoDao.updateWithoutOptimisticLock(sagyoJokyo);

		if (model.isUploadExecute()) {
			// �t�@�C���A�b�v���[�h����
			executeUploadFile(model);

			// �A�N�Z�X���O�Ƀt�@�C������o�^
			List<String> uploadFileList = new ArrayList<String>();
			for (String fileNm : model.getImageFileNmByArray()) {
				if (StringUtils.isBlank(fileNm)) {
					continue;
				}

				uploadFileList.add(fileNm);
			}
			sagyoJokyo.setUploadFileList(uploadFileList);
		}

		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB032RequestEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_UPDATE,
				createKensakuJokenAsUpdate(sagyoJokyo));
	}

	/**
	 * �摜�_�E�����[�h�̃`�F�b�N���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean checkDownloadInfo(TB032RequestEntryModel model) {
		if (model.getUserContext().isRealEstate() || model.getUserContext().isConstractor()) {
			// ���O�C�����[�U�̌������u20�F�Ǘ���Ёv�u30�F�Ǝҁv�̏ꍇ�́A�˗����̃`�F�b�N���s��
			return iraiDao.isOwn(model.getToiawaseNo(),
					model.getToiawaseRirekiNo(),
					model.getUserContext().getRefKokyakuId(),
					model.getUserContext().getGyoshaCd());
		} else if (model.getUserContext().isOutsourcerSv() || model.getUserContext().isOutsourcerOp()) {
			// ���O�C�����[�U�̌������u40�F�ϑ���Ђr�u�v�u41�F�ϑ���Ђn�o�v�̏ꍇ�́A
			// �₢���킹���̃`�F�b�N���s��
			RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
			if (toiawase == null) {
				// �₢���킹��񂪂Ȃ��ꍇ�́A�`�F�b�NNG
				return false;
			}
			// �ϑ���Њ֘A�`�F�b�N
			return outsourcerValidationLogic.isValid(
					model.getUserContext().getKaishaId(), toiawase.getKokyakuId());
		} else {
			// ��L�ȊO�̌����̏ꍇ�́A�`�F�b�N�Ȃ�
			return true;
		}
	}

	/**
	 * ���̑��t�@�C���_�E�����[�h�̃Z�L�����e�B�`�F�b�N���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean checkOtherFileDownloadInfo(TB032RequestEntryModel model) {
		if (model.getUserContext().isRealEstate()) {
			// ���O�C�����[�U�̌������u20�F�Ǘ���Ёv�̏ꍇ�́A�˗����̃`�F�b�N���s��
			// �˗����̃`�F�b�N���s��
			return iraiDao.isOwn(model.getToiawaseNo(),
					model.getToiawaseRirekiNo(),
					model.getUserContext().getRefKokyakuId(),
					null);
		} else if (model.getUserContext().isOutsourcerSv() || model.getUserContext().isOutsourcerOp()) {
			// ���O�C�����[�U�̌������u40�F�ϑ���Ђr�u�v�u41�F�ϑ���Ђn�o�v�̏ꍇ�́A
			// �₢���킹���̃`�F�b�N���s��
			RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
			if (toiawase == null) {
				// �₢���킹��񂪂Ȃ��ꍇ�́A�`�F�b�NNG
				return false;
			}
			// �ϑ���Њ֘A�`�F�b�N
			return outsourcerValidationLogic.isValid(
					model.getUserContext().getKaishaId(), toiawase.getKokyakuId());
		} else {
			// ��L�ȊO�̌����̏ꍇ�́A�`�F�b�N�Ȃ�
			return true;
		}
	}

	/**
	 * �����\���p�����[�^�`�F�b�N���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	private void validateInitParameter(TB032RequestEntryModel model) {
		// �₢���킹NO�̃`�F�b�N
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			throw new ApplicationException("�₢���킹NO�s���F�p�����[�^�̖₢���킹NO");
		}

		// �₢���킹����NO�̃`�F�b�N
		if (model.getToiawaseRirekiNo() == null) {
			throw new ApplicationException("�₢���킹����NO�s���F�p�����[�^�̖₢���킹����NO");
		}

		// �J�ڌ���ʋ敪�̃`�F�b�N
		if (!(model.isFromRequestSearch() || model.isFromInquiryDetail() ||
			model.isFromDirectLogin())) {
			throw new ApplicationException("�J�ڌ���ʋ敪�s���F�p�����[�^�̑J�ڌ���ʋ敪");
		}
	}

	/**
	 * �˗����̏����ݒ菈�����s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	private TB032RequestEntryModel initIraiInfo(TB032RequestEntryModel model) {
		// �₢���킹���擾
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null || !toiawase.isPublished()) {
			// �₢���킹��񂪑��݂��Ȃ��A�܂��́A�₢���킹���J�t���O���u1:���J�ρv�ȊO�̏ꍇ
			return model;
		}
		model.setToiawase(toiawase);

		// �ڋq��{���擾
		try {
			RcpMKokyaku kokyaku = commonInfoLogic.getKokyakuInfo(toiawase.getKokyakuId());
			model.setKokyakuEntity(kokyaku);
		} catch (ValidationException e) {
			return model;
		}

		// �₢���킹�������擾
		RcpTToiawaseRireki toiawaseRireki =
			toiawaseRirekiDao.selectByPrimaryKey(
					model.getToiawaseNo(),
					model.getToiawaseRirekiNo());
		if (toiawaseRireki == null || !toiawaseRireki.isPublished()) {
			// �₢���킹������񂪑��݂��Ȃ��A�₢���킹�������J�t���O���u1:���J�ρv�ȊO�̏ꍇ
			return model;
		}
		model.setToiawaseRireki(toiawaseRireki);

		// �˗����擾
		RcpTIrai irai = iraiDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (irai == null || !irai.isPublished()) {
			// �˗���񂪑��݂��Ȃ��A�܂��́A�˗������J�t���O���u1:���J�ρv�ȊO�̏ꍇ
			return model;
		}
		model.setIrai(irai);

		return model;
	}

	/**
	 * �a���ϊ��pMap���擾���܂��B
	 *
	 * @return �a���ϊ��pMap
	 */
	private Map<String, Map<String, RcpMComCd>> getConvertMap() {
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI,
				RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		return convertMap;
	}

	/**
	 * �摜�폜���p�����[�^�`�F�b�N���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	private void validateDeleteImageParameter(TB032RequestEntryModel model) {
		// �A�b�v���[�h�t�@�C�����̃`�F�b�N
		if (StringUtils.isBlank(model.getUploadFileNm())) {
			throw new ApplicationException("�A�b�v���[�h�t�@�C�����s���F�p�����[�^�̃A�b�v���[�h�t�@�C����");
		}
		// �t�@�C���C���f�b�N�X�̃`�F�b�N
		if (model.getFileIndex() == null) {
			throw new ApplicationException("�t�@�C���C���f�b�N�X�s���F�p�����[�^�̃t�@�C���C���f�b�N�X");
		}
	}


	/**
	 * �摜�폜���̃A�N�Z�X���O�̌��������𐶐����܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 * @return �A�N�Z�X���O�̌�������
	 */
	private String createKensakuJokenAsDeleteImage(TB032RequestEntryModel model) {
		return "toiawaseNo=" + model.getToiawaseNo() + "," +
				"toiawaseRirekiNo=" + model.getToiawaseRirekiNo().toString() + "," +
				"fileIndex=" + model.getFileIndex().toString() + "," +
				"uploadFileNm=" + model.getUploadFileNm();
	}

	/**
	 * �X�V���i�o�^�E�X�V���j�̃A�N�Z�X���O�̌��������𐶐����܂��B
	 *
	 * @param sagyoJokyo �Ǝ҉񓚍�Ə󋵃e�[�u��Entity
	 * @return �A�N�Z�X���O�̌�������
	 */
	private String createKensakuJokenAsUpdate(TbTSagyoJokyo sagyoJokyo) {
		NullExclusionToStringBuilder entryEntity =
			new NullExclusionToStringBuilder(
				sagyoJokyo,
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// ���O���鍀��
		List<String> excludeFiledList = new ArrayList<String>(Arrays.asList(Constants.EXCLUDE_FIELD_NAMES));

		entryEntity.setExcludeFieldNames((String[]) excludeFiledList.toArray(new String[0]));

		return entryEntity.toString();
	}

	/**
	 * �o�^���`�F�b�N���s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	private void validateInsert(TB032RequestEntryModel model) {
		// �V�X�e���}�X�^����萔�擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		Long maxFileSize = Long.parseLong(userContext.getSystgemContstantAsString(
				RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_MAX_FILESIZE));

		if (model.isUploadExecute()) {
			for (File file : model.getImageFiles()) {
				if (file == null) {
					continue;
				}

				String result = FileUploadLogic.checkFileSize(file, maxFileSize);

				if (FileUploadLogic.SIZE_ZERO.equals(result)) {
					// �t�@�C���T�C�Y�O�̏ꍇ
					throw new ValidationException(new ValidationPack().addError("MSG0020"));
				} else if (FileUploadLogic.SIZE_OVER.equals(result)) {
					// �t�@�C���T�C�Y�I�[�o�[�̏ꍇ
					throw new ValidationException(new ValidationPack().addError("MSG0021", maxFileSize.toString()));
				}
			}
		}
	}

	/**
	 * �t�@�C���̃A�b�v���[�h���������s���܂��B
	 *
	 * @param model �˗����e�ڍׁE��Ə󋵓o�^��ʃ��f��
	 */
	private void executeUploadFile(TB032RequestEntryModel model) {
		List<String> uploadFileNameList = new ArrayList<String>();
		List<File> uploadFileList = new ArrayList<File>();

		String[] uploadFileNames = model.getImageFileNmByArray();
		// �w�肳�ꂽ�t�@�C�����A�o�^
		for (int i = 0; i < uploadFileNames.length; i++) {
			String imageFileName = uploadFileNames[i];

			if (StringUtils.isBlank(imageFileName)) {
				// �t�@�C�����w�肳��Ă��Ȃ���΁A���̏�����
				continue;
			}
			File uploadFile = model.getImageFiles()[i];
			// �t�@�C���C���f�b�N�X
			int fileIndex = i + 1;

			// �t�@�C�����̐���
			String uploadFileName = model.getToiawaseNo() + "-" +
									model.getToiawaseRirekiNo().toString() + "-" +
									fileIndex;
			// �A�b�v���[�h�t�@�C�����̐���
			uploadFileName =
				FileUploadLogic.makeUploadFileNm(imageFileName, uploadFileName);

			// �Ǝ҉񓚈˗��t�@�C���A�b�v���[�h�e�[�u���̓o�^
			TbTFileUpload fileUpload = new TbTFileUpload();
			fileUpload.setToiawaseNo(model.getToiawaseNo());
			fileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
			fileUpload.setFileIndex(new BigDecimal(fileIndex));
			fileUpload.setUploadFileNm(uploadFileName);
			fileUpload.setBaseFileNm(FilenameUtils.getName(new File(imageFileName).getName()));

			gyoshaFileUploadDao.insert(fileUpload);

			uploadFileNameList.add(uploadFileName);
			uploadFileList.add(uploadFile);
		}

		// �t�@�C���̃A�b�v���[�h
		FileUploadLogic.uploadFileList(uploadFileList,
				uploadFileNameList, UPLOAD_PATH_SAGYO_JOKYO);
	}

	/**
	 * �t�@�C���A�b�v���[�h����\�����ɍ쐬���܂��B
	 *
	 * @param uploadedList �t�@�C���A�b�v���[�h��񃊃X�g
	 * @param dispMax �ő�\������
	 * @return �t�@�C���A�b�v���[�h���z��
	 */
	private TB032FileUploadDto[] createFileUploadDtoList(List<TB032FileUploadDto> uploadedList, Integer dispMax) {
		TB032FileUploadDto[] fileUploadDtos = new TB032FileUploadDto[dispMax];

		if (uploadedList == null || uploadedList.isEmpty()) {
			return fileUploadDtos;
		}

		for (int i = 0; i < dispMax; i++) {
			// ���݂̃t�@�C���C���f�b�N�X
			int currentIdx = i + 1;

			for (TB032FileUploadDto dto : uploadedList) {
				// �t�@�C���f�b�N�X���擾
				TbTFileUpload fileUpload = dto.toTbTFileUpload();

				if (currentIdx == fileUpload.getFileIndex().intValue()) {
					// �f�[�^������ꍇ�́A�z��Ɋi�[
					fileUploadDtos[i] = dto;

					break;
				}
			}
		}

		return fileUploadDtos;
	}

	/**
	 * �t�@�C���A�b�v���[�h����\�����ɍ쐬���܂��B
	 *
	 * @param uploadedList �t�@�C���A�b�v���[�h��񃊃X�g
	 * @param dispMax �ő�\������
	 * @return �t�@�C���A�b�v���[�h���z��
	 */
	private TB032FileUploadDto[] createFileUploadDtoListByTLCSS(List<TB032FileUploadDto> uploadedList, Integer dispMax) {
		TB032FileUploadDto[] fileUploadDtos = new TB032FileUploadDto[dispMax];

		if (uploadedList == null || uploadedList.isEmpty()) {
			return fileUploadDtos;
		}

		for (int i = 0; i < dispMax; i++) {
			// ���݂̃t�@�C���C���f�b�N�X
			int currentIdx = i + 1;
			
			boolean isExist = false;
			for (TB032FileUploadDto fileUploadDto : uploadedList) {
				if (currentIdx == fileUploadDto.getFileIndex().intValue()) {
					// �f�[�^������ꍇ�́A�z��Ɋi�[
					fileUploadDtos[i] = fileUploadDto;
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				// ���[�v�J�E���g���A�b�v���[�h�σt�@�C�������ȏぁ�f�[�^����
				fileUploadDtos[i] = null;
			}
		}

		return fileUploadDtos;
	}

	/**
	 * �˗����̑��t�@�C���A�b�v���[�h����\�����ɍ쐬���܂��B
	 *
	 * @param otherFileUploadList �˗����̑��t�@�C���A�b�v���[�h��񃊃X�g
	 * @param dispMax �ő�\������
	 * @return �˗����̑��t�@�C���A�b�v���[�h���z��
	 */
	private RcpTOtherFileUpload[] createOtherFileUploadList(List<RcpTOtherFileUpload> otherFileUploadList, Integer dispMax) {
		RcpTOtherFileUpload[] otherFileUploadeds = new RcpTOtherFileUpload[dispMax];

		if (otherFileUploadList == null || otherFileUploadList.isEmpty()) {
			return otherFileUploadeds;
		}

		for (int i = 0; i < dispMax; i++) {
			// ���݂̃t�@�C���C���f�b�N�X
			int currentIdx = i + 1;
			
			boolean isExist = false;
			for (RcpTOtherFileUpload otherFileUpload : otherFileUploadList) {
				if (currentIdx == otherFileUpload.getFileIndex().intValue()) {
					// �f�[�^������ꍇ�́A�z��Ɋi�[
					otherFileUploadeds[i] = otherFileUpload;
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				// ���[�v�J�E���g���A�b�v���[�h�σt�@�C�������ȏぁ�f�[�^����
				otherFileUploadeds[i] = null;
			}	
		}

		return otherFileUploadeds;
	}
}
