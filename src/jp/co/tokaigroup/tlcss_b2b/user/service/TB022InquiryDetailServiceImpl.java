package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn1Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn2Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn3Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn4Dao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseFileDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseFile;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB022InquiryDetailModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �₢���킹�����T�[�r�X�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 1.1 2015/10/23 C.Kobayashi ���ǖ��ǋ@�\�ǉ��Ή�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB022InquiryDetailServiceImpl extends TLCSSB2BBaseService
		implements TB022InquiryDetailService {

	/** �\�[�g��(�₢���킹�����擾) */
	private final static String orderBy = " ORDER BY"
		+ "    UKETSUKE_YMD DESC,"
		+ "    UKETSUKE_JIKAN DESC,"
		+ "    TOIAWASE_RIREKI_NO DESC";

	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** ���Z�v�V�����₢���킹�e�[�u��DAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** ���Z�v�V�����₢���킹�����e�[�u��DAO */
	@Autowired
	private RcpTToiawaseRirekiDao toiawaseRirekiDao;

	/** ���Z�v�V�����󋵋敪�}�X�^DAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;

	/** ���Z�v�V�����˗��e�[�u��DAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** NATOS�p�X���[�h�lDAO */
	@Autowired
	private NatosMPasswordDao natosPswdDao;

	/** �₢���킹�敪1�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn1Dao toiawaseKbn1;

	/** �₢���킹�敪2�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn2Dao toiawaseKbn2;

	/** �₢���킹�敪3�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn3Dao toiawaseKbn3;

	/** �₢���킹�敪4�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn4Dao toiawaseKbn4;

	/** �₢���킹�t�@�C���e�[�u��DAO */
	@Autowired
	private RcpTToiawaseFileDao toiawaseFileDao;

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �₢���킹�ڍ׉�ʃ��f��
	 * @return �₢���킹�ڍ׉�ʃ��f��
	 */
	public TB022InquiryDetailModel getInitInfo(TB022InquiryDetailModel model) {

		//�₢���킹No�̃`�F�b�N
		if(StringUtils.isBlank(model.getToiawaseNo())){
			// �₢���킹No���擾�ł��Ȃ��ꍇ�G���[
			throw new ApplicationException("�₢���킹NO�s��:�p�����[�^�̖₢���킹NO" );
		}

		// �A�N�Z�X�����̃`�F�b�N
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// �₢���킹���̃`�F�b�N
		if (!toiawaseDao.isOwn(model.getToiawaseNo(), userContext.getRefKokyakuId())) {
			// �A�N�Z�X�s�̏ꍇ�́A403�G���[����ʂɕ\��
			return null;
		}
		
		// �₢���킹���擾
		RcpTToiawase toiawaseEntity = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if(toiawaseEntity == null){
			// �₢���킹��񂪎擾�ł��Ȃ��ꍇ�G���[
			model.setToiawaseEntity(null);
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		//�ŏI�������擾
		RcpTToiawaseRireki toiawaseRirekiEntity = toiawaseRirekiDao.getLastToiawaseRireki(model.getToiawaseNo(), RC031ToiawaseSearchCondition.SEARCH_FLG_GAIBU);

		//�ڋq��{���擾
		model.setKokyakuEntity(kokyakuKihon.getKokyakuInfo(toiawaseEntity.getKokyakuId()));

		//�₢���킹�������擾
		List<RcpTToiawaseRireki> toiawaseRirekiList = toiawaseRirekiDao.selectKokaiTarget(model.getToiawaseNo(), orderBy);

		//�˗����擾
		model.setIraiMap(iraiDao.selectAnyAsMap(model.getToiawaseNo()));

		// �₢���킹�t�@�C�����X�g
		List<RcpTToiawaseFile> toiawaseFileList =
			toiawaseFileDao.selectBy(model.getToiawaseNo(), null);
		model.setUploadedFiles(createToiawaseFileList(toiawaseFileList));

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU,
				RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI,
				RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �˗��ҋ敪Map
		Map<String, RcpMComCd> iraishaKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// �˗��҃t���OMap
		Map<String, RcpMComCd> iraishaFlgMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA);
		// �˗��җL���敪Map
		Map<String, RcpMComCd> iraiUmuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU);
		// ��t�`��Map
		Map<String, RcpMComCd> uketsukeKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		// �{���󋵋敪Map
		Map<String, RcpMComCd> browseStatusMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU);
		// �󋵋敪�}�X�^����̎擾
		Map<String, String> jokyoKbnMap = jokyoKbnDao.selectAllExtJokyoKbnNmAsMap();

		// �₢���킹�敪Map
		Map<String, String> toiawaseKbn1Map = toiawaseKbn1.selectAllAsMap();
		Map<String, String> toiawaseKbn2Map = toiawaseKbn2.selectAllAsMap();
		Map<String, String> toiawaseKbn3Map = toiawaseKbn3.selectAllAsMap();
		Map<String, String> toiawaseKbn4Map = toiawaseKbn4.selectAllAsMap();

		// �p�X���[�h�}�X�^�E�Ǝ҃}�X�^�����p�̃��X�g����
		List<String> userIdList = new ArrayList<String>();
		if (StringUtils.isNotBlank(toiawaseEntity.getUketsukeshaId()) &&
			!userIdList.contains(toiawaseEntity.getUketsukeshaId())) {
			// �p�X���[�h�}�X�^�����p�̃��X�g�Ɋ܂܂�Ă��Ȃ���΁A���X�g�ɒǉ�
			userIdList.add(toiawaseEntity.getUketsukeshaId());
		}
		for (RcpTToiawaseRireki dto : toiawaseRirekiList) {
			if (StringUtils.isNotBlank(dto.getTantoshaId()) &&
				!userIdList.contains(dto.getTantoshaId())) {
				// �p�X���[�h�}�X�^�����p�̃��X�g�Ɋ܂܂�Ă��Ȃ���΁A���X�g�ɒǉ�
				userIdList.add(dto.getTantoshaId());
			}
		}
		Map<String, String> userMap = new HashMap<String, String>();
		if (!(userIdList == null || userIdList.isEmpty())) {
			// �p�X���[�h�}�X�^�擾
			userMap =
				natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));
		}

		// �a���ϊ�����
		// �˗��҃t���O���ڋq��{���̈˗��҃t���O�Ɠ���̏ꍇ
		if (iraishaFlgMap != null) {
			if (RcpTToiawase.IRAI_FLG_ONAJI.equals(toiawaseEntity.getIraishaFlg()) &&
					iraishaFlgMap.containsKey(toiawaseEntity.getIraishaFlg())) {
				//�˗��Җ�
				toiawaseEntity.setIraishaNm(iraishaFlgMap.get(toiawaseEntity.getIraishaFlg()).getExternalSiteVal());
			} else if (RcpTToiawase.IRAI_FLG_DIFF.equals(toiawaseEntity.getIraishaFlg()) &&
					iraishaKbnMap.containsKey(toiawaseEntity.getIraishaKbn())) {
				toiawaseEntity.setIraishaNm(iraishaKbnMap.get(toiawaseEntity.getIraishaKbn()).getExternalSiteVal() + " " + toiawaseEntity.getIraishaNm());
			}
		}
		// ��t��
		if (StringUtils.isBlank(toiawaseEntity.getUketsukeshaNm())) {
			// ��t�Җ���NULL�̏ꍇ�A��t�҂h�c��a���ϊ����ĕ\��
			if (userMap != null && StringUtils.isNotBlank(toiawaseEntity.getUketsukeshaId()) &&
					userMap.containsKey(toiawaseEntity.getUketsukeshaId())) {
				toiawaseEntity.setUketsukeNm(userMap.get(toiawaseEntity.getUketsukeshaId()));
			}
		} else {
			// ��t�Җ���NULL�łȂ���΁A��t�Җ���\��
			toiawaseEntity.setUketsukeNm(toiawaseEntity.getUketsukeshaNm());
		}
		if (toiawaseKbn1Map != null && StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn1()) &&
				toiawaseKbn1Map.containsKey(toiawaseEntity.getToiawaseKbn1())) {
			// �₢���킹�敪1��
			toiawaseEntity.setToiawaseKbn1Nm(toiawaseKbn1Map.get(toiawaseEntity.getToiawaseKbn1()));
		}
		if (toiawaseKbn2Map != null && StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn2()) &&
				toiawaseKbn2Map.containsKey(toiawaseEntity.getToiawaseKbn2())) {
			// �₢���킹�敪2
			toiawaseEntity.setToiawaseKbn2Nm(toiawaseKbn2Map.get(toiawaseEntity.getToiawaseKbn2()));
		}
		if (toiawaseKbn3Map != null && StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn3()) &&
				toiawaseKbn3Map.containsKey(toiawaseEntity.getToiawaseKbn3())) {
			// �₢���킹�敪3��
			toiawaseEntity.setToiawaseKbn3Nm(toiawaseKbn3Map.get(toiawaseEntity.getToiawaseKbn3()));
		}
		if (toiawaseKbn4Map != null && StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn4()) &&
				toiawaseKbn4Map.containsKey(toiawaseEntity.getToiawaseKbn4())) {
			// �₢���킹�敪4��
			toiawaseEntity.setToiawaseKbn4Nm(toiawaseKbn4Map.get(toiawaseEntity.getToiawaseKbn4()));
		}
		if (uketsukeKeitaiMap != null && StringUtils.isNotBlank(toiawaseEntity.getUketsukeKeitaiKbn()) &&
				uketsukeKeitaiMap.containsKey(toiawaseEntity.getUketsukeKeitaiKbn())) {
			// ��t�`�Ԗ�
			toiawaseEntity.setUketsukeKeitaiKbnNm(uketsukeKeitaiMap.get(toiawaseEntity.getUketsukeKeitaiKbn()).getExternalSiteVal());
		}
		if (iraiUmuMap != null && StringUtils.isNotBlank(toiawaseEntity.getIraiUmuKbn()) &&
				iraiUmuMap.containsKey(toiawaseEntity.getIraiUmuKbn())) {
			// �˗��L���敪��
			toiawaseEntity.setIraiUmuKbnNm(iraiUmuMap.get(toiawaseEntity.getIraiUmuKbn()).getExternalSiteVal());
		}
		if (jokyoKbnMap != null && toiawaseRirekiEntity != null && StringUtils.isNotBlank(toiawaseRirekiEntity.getJokyoKbn()) &&
				jokyoKbnMap.containsKey(toiawaseRirekiEntity.getJokyoKbn())) {
			// �ŏI�����󋵋敪��
			toiawaseRirekiEntity.setJokyoKbnNm(jokyoKbnMap.get(toiawaseRirekiEntity.getJokyoKbn()));
		}

		//���ԕ\�L�̏C��(hhmm��hh:mm)
		toiawaseEntity.setUketsukeJikan(DateUtil.hhmmPlusColon(toiawaseEntity.getUketsukeJikan()));
		if (toiawaseRirekiEntity != null) {
			toiawaseRirekiEntity.setUketsukeJikan(DateUtil.hhmmPlusColon(toiawaseRirekiEntity.getUketsukeJikan()));
		}

		for (int i = 0; i < toiawaseRirekiList.size(); i++) {
			RcpTToiawaseRireki dto = toiawaseRirekiList.get(i);

			// �S���Җ���NULL�̏ꍇ�A�S���҂h�c��a���ϊ����ĕ\��(NULL�łȂ���΁A���̂܂ܕ\��)
			if (StringUtils.isBlank(dto.getTantoshaNm())) {
				if (StringUtils.isNotBlank(dto.getTantoshaId())
						&& userMap != null
						&& userMap.containsKey(dto.getTantoshaId())) {
					// �S���ҋ敪��
					dto.setTantoshaNm(userMap.get(dto.getTantoshaId()));
				}
			}

			if (browseStatusMap != null && StringUtils.isNotBlank(dto.getBrowseStatusFlg()) &&
					browseStatusMap.containsKey(dto.getBrowseStatusFlg())) {
				// �{���󋵃t���O��
				dto.setBrowseStatusFlgNm(browseStatusMap.get(dto.getBrowseStatusFlg()).getExternalSiteVal());
			}
			
			if (StringUtils.isNotBlank(dto.getJokyoKbn()) &&
				jokyoKbnMap.containsKey(dto.getJokyoKbn())) {
				// �󋵋敪��
				dto.setJokyoKbnNm(jokyoKbnMap.get(dto.getJokyoKbn()));
			}

			//���ԕ\�L�̏C��(hhmm��hh:mm)
			dto.setUketsukeJikan(DateUtil.hhmmPlusColon(dto.getUketsukeJikan()));

			//�˗��e�[�u���̗L���̊m�F�i�����J�̏ꍇ�́A�˗��͕\�����Ȃ��j
			RcpTIrai irai = model.getIraiMap().get(dto.getToiawaseNo() + dto.getToiawaseRirekiNo().toString());
			if (irai != null && irai.isPublished()) {
				// �˗���񂪑��݂��A���J�ς̏ꍇ�݈̂˗������N��\��
				dto.setIraiExsits(true);
			} else {
				// �˗���񂪑��݂��Ȃ��A�܂��́A�����J�̏ꍇ�͈˗������N��\�����Ȃ�
				dto.setIraiExsits(false);
			}

			// �ăZ�b�g
			toiawaseRirekiList.set(i, dto);
		}

		model.setToiawaseEntity(toiawaseEntity);
		model.setToiawaseRirekiEntity(toiawaseRirekiEntity);
		model.setToiawaseRirekiList(toiawaseRirekiList);

		return model;
	}

	/**
	 * �₢���킹�����E�₢���킹�����X�V���܂��B
	 *
	 * @param model �₢���킹�ڍ׉�ʃ��f��
	 */
	@Transactional(value="txManager")
	public void updateInquiryHistoryDetailInfo(TB022InquiryDetailModel model) {

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		for (int i = 0; i < model.getToiawaseRirekiNoList().size(); i++) {
			RcpTToiawaseRireki toiawaseRireki = new RcpTToiawaseRireki();
			
			// �₢���킹�m�n
			toiawaseRireki.setToiawaseNo(model.getToiawaseNo());
			// �₢���킹�����m�n
			toiawaseRireki.setToiawaseRirekiNo(model.getToiawaseRirekiNoList().get(i));
			// �{���󋵃t���O
			toiawaseRireki.setBrowseStatusFlg(model.getBrowseStatusFlgList().get(i));
			// �ŏI�{���h�c
			toiawaseRireki.setLastBrowseId(userContext.getLoginId());
			// �₢���킹���𖢓Ǌ��Ǐ��X�V
			toiawaseRirekiDao.updateBrowseStatusFlg(toiawaseRireki);
		}

		// �₢���킹�����Ǐ��X�V
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), true);
	}

	/**
	 * �₢���킹�t�@�C�����̃_�E�����[�h�`�F�b�N���s���܂��B
	 *
	 * @param model �₢���킹������ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean canWorkReportDownload(TB022InquiryDetailModel model) {
		return toiawaseDao.isOwn(model.getToiawaseNo(),
				((TLCSSB2BUserContext) getUserContext()).getRefKokyakuId());
	}

	/**
	 * �₢���킹�t�@�C������\�����ɍ쐬���܂��B
	 *
	 * @param uploadedList �t�@�C���A�b�v���[�h��񃊃X�g
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



}
