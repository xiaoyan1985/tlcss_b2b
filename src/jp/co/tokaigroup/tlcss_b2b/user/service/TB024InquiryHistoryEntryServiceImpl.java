package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.common.logic.CheckToPublishToiawaseLogic;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn1Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn2Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn3Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn4Dao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuWithNoIdDao;
import jp.co.tokaigroup.reception.dao.RcpTMailRirekiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.dao.SosMUserInfoDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.SosMUserInfo;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB024InquiryHistoryEntryModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �₢���킹����o�^�T�[�r�X�����N���X�B
 *
 * @author v145527
 * @version 1.0 2015/08/28
 *
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB024InquiryHistoryEntryServiceImpl extends TLCSSB2BBaseService
		implements TB024InquiryHistoryEntryService {

	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** ���Z�v�V�����₢���킹�e�[�u��DAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** ���Z�v�V�����₢���킹�����e�[�u��DAO */
	@Autowired
	private RcpTToiawaseRirekiDao toiawaseRirekiDao;

	/** �h�c�����ڋq�e�[�u��DAO */
	@Autowired
	private RcpTKokyakuWithNoIdDao kokyakuWithNoIdDao;

	/** ���ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** ���Z�v�V�����₢���킹�敪�P�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn1Dao toiawaseKbn1Dao;

	/** ���Z�v�V�����₢���킹�敪�Q�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn2Dao toiawaseKbn2Dao;

	/** ���Z�v�V�����₢���킹�敪�R�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn3Dao toiawaseKbn3Dao;

	/** ���Z�v�V�����₢���킹�敪�S�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn4Dao toiawaseKbn4Dao;

	/** ���Z�v�V�����󋵋敪�}�X�^ */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;

	/** SOS���[�U�t�����}�X�^ */
	@Autowired
	private SosMUserInfoDao userInfoDao;

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
	

	/**
	 * �����\�����s���܂��B
	 * �i�₢���킹�����擾���܂��B�j
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 * @return �₢���킹����o�^��ʃ��f��
	 */
	public TB024InquiryHistoryEntryModel getInitInfo(TB024InquiryHistoryEntryModel model) {

		// �₢���킹���擾
		RcpTToiawase toiawaseEntity = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if(toiawaseEntity == null){
			// �₢���킹��񂪎擾�ł��Ȃ��ꍇ�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		// �h�c�Ȃ��ڋq
		RcpTKokyakuWithNoId kokyakuWithoutId = null;

		if (StringUtils.isNotBlank(toiawaseEntity.getKokyakuId())) {
			// �ڋq�h�c�����݂���ꍇ
			// �ڋq��{���擾
			RcpMKokyaku kokyaku = kokyakuKihon.getKokyakuInfo(toiawaseEntity.getKokyakuId());
			model.setKokyakuEntity(kokyaku);
		} else {
			// �h�c�����ڋq�̏ꍇ
			kokyakuWithoutId = kokyakuWithNoIdDao.selectByPrimaryKey(model.getToiawaseNo());
			if (kokyakuWithoutId == null) {
				// �擾�ł��Ȃ��ꍇ�́A�r���G���[
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			model.setKokyakuInfoWithoutId(kokyakuWithoutId);
		}
		
		//�ŏI�������擾
		RcpTToiawaseRireki lastToiawaseRireki = toiawaseRirekiDao.getLastToiawaseRireki(model.getToiawaseNo(), null);
		
		// ���ʃR�[�h�}�X�^����擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
							RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
							RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA,
							RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU,
							RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �˗���Map
		Map<String, RcpMComCd> iraishaMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA);
		// �˗��ҋ敪Map
		Map<String, RcpMComCd> iraishaKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// �˗��җL���敪Map
		Map<String, RcpMComCd> iraiUmuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU);
		// ��t�`��Map
		Map<String, RcpMComCd> uketsukeKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		
		// �󋵋敪�}�X�^����̎擾
		Map<String, String> jokyoKbnMap = jokyoKbnDao.selectAllAsMap();
		
		// �₢���킹�敪Map
		Map<String, String> toiawaseKbn1Map = toiawaseKbn1Dao.selectAllAsMap();
		Map<String, String> toiawaseKbn2Map = toiawaseKbn2Dao.selectAllAsMap();
		Map<String, String> toiawaseKbn3Map = toiawaseKbn3Dao.selectAllAsMap();
		Map<String, String> toiawaseKbn4Map = toiawaseKbn3Dao.selectAllAsMap();
		
		
		// �p�X���[�h�}�X�^�̘a���ϊ��}�b�v�擾
		List<String> userIdList = new ArrayList<String>();
		if (StringUtils.isNotBlank(model.getToiawaseInfo().getUketsukeshaId())) {
			// �₢���킹���̎�t�҂h�c�擾
			userIdList.add(model.getToiawaseInfo().getUketsukeshaId());
		}
		if (StringUtils.isNotBlank(model.getToiawaseInfo().getLastUpdId())
			&& !userIdList.contains(model.getToiawaseInfo().getLastUpdId())) {
			// �₢���킹���̍ŏI�X�V�҂h�c�擾
			userIdList.add(model.getToiawaseInfo().getLastUpdId());	
		}
		

		// �a���ϊ��}�b�v�擾
		Map<String, String> userMap = new HashMap<String, String>();
		if (!userIdList.isEmpty()) {
			userMap = natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));	
		}

		// �a���ϊ�����
		// �˗��Җ�
		if (RcpTToiawase.IRAI_FLG_ONAJI.equals(model.getToiawaseInfo().getIraishaFlg())) {
			// �˗��҃t���O���u0:�ڋq��{���Ɠ����v�ꍇ
			// �u�ڋq��{���Ɠ����v��\��
			if (iraishaMap.containsKey(model.getToiawaseInfo().getIraishaFlg())) {
				toiawaseEntity.setIraishaNm(iraishaMap.get(toiawaseEntity.getIraishaFlg()).getExternalSiteVal());
			}
		} else {
			// �˗��҃t���O���u1:�قȂ�v�ꍇ
			// �˗��ҋ敪�ɑ΂��閼�̂�\��
			if (iraishaKbnMap.containsKey(model.getToiawaseInfo().getIraishaKbn())) {
				toiawaseEntity.setIraishaNm(iraishaKbnMap.get(toiawaseEntity.getIraishaKbn()).getExternalSiteVal() + " " + toiawaseEntity.getIraishaNm());
			}
		}
		// ��t�`�Ԗ�
		if (uketsukeKeitaiMap != null
				&& StringUtils.isNotBlank(toiawaseEntity.getUketsukeKeitaiKbn())
				&& uketsukeKeitaiMap.containsKey(toiawaseEntity.getUketsukeKeitaiKbn())) {
			toiawaseEntity.setUketsukeKeitaiKbnNm(uketsukeKeitaiMap.get(toiawaseEntity.getUketsukeKeitaiKbn()).getExternalSiteVal());
		}
		// �˗��L���敪��
		if (iraiUmuMap != null
				&& StringUtils.isNotBlank(toiawaseEntity.getIraiUmuKbn())
				&& iraiUmuMap.containsKey(toiawaseEntity.getIraiUmuKbn())) {
			toiawaseEntity.setIraiUmuKbnNm(iraiUmuMap.get(toiawaseEntity.getIraiUmuKbn()).getExternalSiteVal());
		}
		// �₢���킹�敪1��
		if (toiawaseKbn1Map != null
				&& StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn1())
				&& toiawaseKbn1Map.containsKey(toiawaseEntity.getToiawaseKbn1())) {
			toiawaseEntity.setToiawaseKbn1Nm(toiawaseKbn1Map.get(toiawaseEntity.getToiawaseKbn1()));
		}
		// �₢���킹�敪2��
		if (toiawaseKbn2Map != null
				&& StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn2())
				&& toiawaseKbn2Map.containsKey(toiawaseEntity.getToiawaseKbn2())) {
			toiawaseEntity.setToiawaseKbn2Nm(toiawaseKbn2Map.get(toiawaseEntity.getToiawaseKbn2()));
		}
		// �₢���킹�敪3��
		if (toiawaseKbn3Map != null
				&& StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn3())
				&& toiawaseKbn3Map.containsKey(toiawaseEntity.getToiawaseKbn3())) {
			toiawaseEntity.setToiawaseKbn3Nm(toiawaseKbn3Map.get(toiawaseEntity.getToiawaseKbn3()));
		}
		// �₢���킹�敪4��
		if (toiawaseKbn4Map != null
				&& StringUtils.isNotBlank(toiawaseEntity.getToiawaseKbn4())
				&& toiawaseKbn4Map.containsKey(toiawaseEntity.getToiawaseKbn4())) {
			toiawaseEntity.setToiawaseKbn4Nm(toiawaseKbn4Map.get(toiawaseEntity.getToiawaseKbn4()));
		}

		if (lastToiawaseRireki != null) {
			if (jokyoKbnMap != null
					&& StringUtils.isNotBlank(lastToiawaseRireki.getJokyoKbn())
					&& jokyoKbnMap.containsKey(lastToiawaseRireki.getJokyoKbn())) {
				// �ŏI�����󋵋敪��
				toiawaseEntity.setLastJokyoKbnNm(jokyoKbnMap.get(lastToiawaseRireki.getJokyoKbn()));
			}
			// �ŏI������t
			toiawaseEntity.setLastRirekiYmd(lastToiawaseRireki.getUketsukeYmd());
			// �ŏI��������
			toiawaseEntity.setLastRirekiJikan(DateUtil.hhmmPlusColon(lastToiawaseRireki.getUketsukeJikan()));
		}

		// ��t�Җ�
		if (StringUtils.isBlank(toiawaseEntity.getUketsukeshaNm())) {
			// ��t�Җ���NULL�̏ꍇ�A��t�҂h�c��a���ϊ����ĕ\��
			if (userMap != null && StringUtils.isNotBlank(toiawaseEntity.getUketsukeshaId())
					&& userMap.containsKey(toiawaseEntity.getUketsukeshaId())) {
				toiawaseEntity.setUketsukeNm(userMap.get(toiawaseEntity.getUketsukeshaId()));
			}
		} else {
			// ��t�Җ���NULL�łȂ���΁A��t�Җ���\��
			toiawaseEntity.setUketsukeNm(toiawaseEntity.getUketsukeshaNm());
		}

		if (userMap != null) {
			// �ŏI�X�V�Җ��i�ŏI�X�V�҂h�c����a���ϊ��j
			toiawaseEntity.setLastUpdIdToNm(userMap.get(toiawaseEntity.getLastUpdId()));
		}
		
		// �h�c�Ȃ��ڋq�̌ڋq�敪
		if(kokyakuWithoutId != null) {
			if (iraishaKbnMap != null
					&& StringUtils.isNotBlank(kokyakuWithoutId.getKokyakuKbn())
					&& iraishaKbnMap.containsKey(kokyakuWithoutId.getKokyakuKbn())) {
				kokyakuWithoutId.setKokyakuKbnNm(iraishaKbnMap.get(kokyakuWithoutId.getKokyakuKbn()).getExternalSiteVal());
			}
		}
		// ��t���ԁi�R����������j
		toiawaseEntity.setUketsukeJikan(DateUtil.hhmmPlusColon(toiawaseEntity.getUketsukeJikan()));
		

		// �₢���킹�敪�P���X�g�̎擾
		List<RcpMToiawaseKbn1> toiawaseKbn1List = toiawaseKbn1Dao.selectAll();
		// �₢���킹�敪�Q���X�g�̎擾
		List<RcpMToiawaseKbn2> toiawaseKbn2List = toiawaseKbn2Dao.selectAll();
		// �₢���킹�敪�R���X�g�̎擾
		List<RcpMToiawaseKbn3> toiawaseKbn3List = toiawaseKbn3Dao.selectAll();
		// �₢���킹�敪�S���X�g�̎擾
		List<RcpMToiawaseKbn4> toiawaseKbn4List = toiawaseKbn4Dao.selectAll();

		// �󋵋敪���X�g�̎擾
		// �����̉�ʋ敪���X�g�̐���
		List<String> gamenKbnList = new ArrayList<String>();
		gamenKbnList.add(Constants.GAMEN_KBN_TOIAWASE_ENTRY);
		gamenKbnList.add(Constants.GAMEN_KBN_ZENGAMEN);

		List<RcpMJokyoKbn> jokyoKbnList = jokyoKbnDao.selectByGamenKbn(gamenKbnList, "");

		// �S���҃��X�g�̎擾
		List<String> sosShokumuKbnList = new ArrayList<String>();
		sosShokumuKbnList.add(Constants.SHOKUMU_KBN_SOS_KANRISHA);
		sosShokumuKbnList.add(Constants.SHOKUMU_KBN_SV);
		sosShokumuKbnList.add(Constants.SHOKUMU_KBN_OP);
		// SOS�̃��[�U�t�����}�X�^����擾
		List<SosMUserInfo> userInfoList = userInfoDao.selectByShokumuKbnList(sosShokumuKbnList);

		// �����̃��O�C���h�c���X�g�̐���
		List<String> loginIdList = new ArrayList<String>();
		for (SosMUserInfo userInfo : userInfoList) {
			loginIdList.add(userInfo.getUserId());
		}

		List<NatosMPassword> tantoshaList = new ArrayList<NatosMPassword>();
		if (loginIdList.size() > 0) {
			tantoshaList = natosPswdDao.selectByList(loginIdList, "");
		}

		// �˗��ҋ敪���X�g�i�₢���킹�j
		model.setToiawaseIraishaKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN));
		// �˗��ҋ敪���X�g�i�˗��ҁj
		model.setKokyakuIraishaKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN));
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
		// �󋵋敪���X�g
		model.setJokyoKbnList(jokyoKbnList);
		// �S���҃��X�g
		model.setTantoshaList(tantoshaList);
		// �˗��L�����X�g
		model.setIraiUmuList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_UMU));
		// ��t�`�ԃ��X�g
		model.setUketsukeKeitaiKbnList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI));
		
		// �₢���킹Entity
		model.setToiawaseInfo(toiawaseEntity);

		return model;
	}
	
	/**
	 * �����\�����s���܂��B�i�X�V��ʕ\���p�j
	 * �i�₢���킹���������擾���܂��B�j
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 * @return �₢���킹����o�^��ʃ��f��
	 */
	public TB024InquiryHistoryEntryModel getInitInfoForUpdate(TB024InquiryHistoryEntryModel model) {
		
		// �p�����[�^�̖₢���킹�m�n���擾�ł����ꍇ
		// �₢���킹�����擾
		model.setToiawaseInfo(toiawaseDao.selectByPrimaryKey(model.getToiawaseNo()));
		
		// �₢���킹��񂪎擾�ł��Ȃ��ꍇ
		if (model.getToiawaseInfo() == null) {
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		// �₢���킹���̌ڋq�h�c���A�p�����[�^�̌ڋq�h�c�ɐݒ�
		model.setKokyakuId(model.getToiawaseInfo().getKokyakuId());
		// �X�V�̔r���Ŏg�p���邽�߁A�X�V����ێ�
		model.setToiawaseUpdDt(model.getToiawaseInfo().getUpdDt());

		// �₢���킹���̌ڋq�h�c���擾�ł��Ȃ��ꍇ
		if (StringUtils.isBlank(model.getToiawaseInfo().getKokyakuId())) {
			// �h�c�����ڋq���̎擾
			model.setKokyakuInfoWithoutId(kokyakuWithNoIdDao.selectByPrimaryKey(model.getToiawaseNo()));
		}

		// �����\�����擾
		model = this.getInitInfo(model);
		
		// �₢���킹�������̎擾
		RcpTToiawaseRireki toiawaseRireki =
			toiawaseRirekiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (toiawaseRireki == null) {
			// �₢���킹������񂪂Ȃ��ꍇ�A�r���G���[
			model.setInitError(true);

			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// �p�X���[�h�}�X�^�̘a���ϊ��}�b�v�擾
		List<String> userIdList = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(model.getToiawaseInfo().getLastUpdId())) {
			
			userIdList.add(model.getToiawaseInfo().getLastUpdId());	// �₢���킹���̍ŏI�X�V�҂h�c
		}
		if (StringUtils.isNotBlank(toiawaseRireki.getLastUpdId())
				&& !userIdList.contains(toiawaseRireki.getLastUpdId())) {
			
			userIdList.add(toiawaseRireki.getLastUpdId());	// �₢���킹�������̍ŏI�X�V�҂h�c
		}
		if (StringUtils.isNotBlank(toiawaseRireki.getTantoshaId())
				&& !userIdList.contains(toiawaseRireki.getTantoshaId())) {
			
			userIdList.add(toiawaseRireki.getTantoshaId());	// �₢���킹�������̒S���҂h�c
		}
	
		// �a���ϊ��}�b�v�擾
		Map<String, String> userMap = new HashMap<String, String>();
		if (!userIdList.isEmpty()) {
			userMap = natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));	
		}
		
		// �a���ϊ�����
		// �S���Җ���NULL�̏ꍇ�A�S���҂h�c��a���ϊ����ĕ\��(NULL�łȂ���΁A���̂܂ܕ\��)
		if (StringUtils.isBlank(toiawaseRireki.getTantoshaNm())) {
			if (StringUtils.isNotBlank(toiawaseRireki.getTantoshaId())
					&& userMap != null
					&& userMap.containsKey(toiawaseRireki.getTantoshaId())) {
				// �S���Җ�
				toiawaseRireki.setTantoshaNm(userMap.get(toiawaseRireki.getTantoshaId()));
			}
		}
		if (userMap != null) {
			// �ŏI�X�V�ҁi�ŏI�X�V�҂h�c����a���ϊ��j
			toiawaseRireki.setLastUpdIdToNm(userMap.get(toiawaseRireki.getLastUpdId()));
		}
		
		// ���J���[�����M�������擾�p�m�n�ݒ�
		model.setMailRirekiNo(model.getToiawaseNo() + "-" + StringUtils.leftPad(model.getToiawaseRirekiNo().toPlainString(), 3, '0'));
		// ���J���[�����M�������擾
		RcpTMailRireki mailRireki =
			mailRirekiDao.selectByPrimaryKey(model.getMailRirekiNo());
		model.setMailRireki(mailRireki);
		
		// �ύX�O�̖₢���킹�������J�t���O��ۑ�
		model.setBeforeToiawaseRirekiKokaiFlg(toiawaseRireki.getToiawaseRirekiKokaiFlg());

		// ��ʒl��������
		if (Constants.ACTION_TYPE_RESTORE.equals(model.getActionType())) {
			// �A�N�V�����^�C�v���urestore:��ʒl�����v�̏ꍇ
			toiawaseRireki.setUketsukeYmd(model.getToiawaseRirekiInfo().getUketsukeYmd());
			toiawaseRireki.setUketsukeJikan(model.getToiawaseRirekiInfo().getUketsukeJikan());
			toiawaseRireki.setTantoshaNm(model.getToiawaseRirekiInfo().getTantoshaNm());
			toiawaseRireki.setToiawaseKbn1(model.getToiawaseRirekiInfo().getToiawaseKbn1());
			toiawaseRireki.setToiawaseKbn2(model.getToiawaseRirekiInfo().getToiawaseKbn2());
			toiawaseRireki.setToiawaseKbn3(model.getToiawaseRirekiInfo().getToiawaseKbn3());
			toiawaseRireki.setToiawaseKbn4(model.getToiawaseRirekiInfo().getToiawaseKbn4());
			toiawaseRireki.setToiawaseNaiyo(model.getToiawaseRirekiInfo().getToiawaseNaiyo());
			toiawaseRireki.setJokyoKbn(model.getToiawaseRirekiInfo().getJokyoKbn());
			toiawaseRireki.setHoukokuPrintFlg(model.getToiawaseRirekiInfo().getHoukokuPrintFlg());
			toiawaseRireki.setToiawaseRirekiKokaiFlg(model.getToiawaseRirekiInfo().getToiawaseRirekiKokaiFlg());
		}

		model.setToiawaseRirekiInfo(toiawaseRireki);
		
		return model;
	}

	/**
	 * �₢���킹�������o�^���s���܂��B
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void insertToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model) {
		
		// ���[�U���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �����m�n�̐V�K���s
		BigDecimal newRirekiNo = toiawaseRirekiDao.selectNewRirekiNo(model.getToiawaseNo());

		// �₢���킹�������̎擾�E�Đݒ�
		RcpTToiawaseRireki toiawaseRireki = model.getToiawaseRirekiInfo();
		
		// �₢���킹�m�n
		toiawaseRireki.setToiawaseNo(model.getToiawaseNo());
		// �₢���킹�����m�n
		toiawaseRireki.setToiawaseRirekiNo(newRirekiNo);
		// �o�^�Җ�
		toiawaseRireki.setCreNm(userContext.getUserName());
		// �ŏI�X�V�Җ�
		toiawaseRireki.setLastUpdNm(userContext.getUserName());
		// �S���҂h�c
		toiawaseRireki.setTantoshaId(Constants.TORES_APL_ID);
		// �o�^��Ђh�c
		toiawaseRireki.setCreKaishaId(userContext.getKaishaId());
		// �X�V��Ђh�c
		toiawaseRireki.setUpdKaishaId(userContext.getKaishaId());

		model.setToiawaseRirekiInfo(toiawaseRireki);

		// �₢���킹����o�^
		toiawaseRirekiDao.insertForTores(toiawaseRireki);

		// �₢���킹�e�[�u���̍X�V�����X�V
		model.getToiawaseInfo().setUpdDt(model.getToiawaseUpdDt());
		model.getToiawaseInfo().setLastUpdId(getUserContext().getLoginId());
		model.getToiawaseInfo().setLastUpdNm(((TLCSSB2BUserContext) getUserContext()).getUserName());
		int ret = toiawaseDao.updateUpdDt(model.getToiawaseInfo());
		if (ret == 0) {
			// �߂�̏������擾���ɁA�₢���킹�����擾���Ȃ�
			model.setUpdateError(true);

			// �X�V�������O���̏ꍇ�A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// �₢���킹�����Ǐ��X�V
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), false);

		// �o�^�`�F�b�N�����i�㏈���`�F�b�N�j
		model.setToiawaseRirekiNo(newRirekiNo);
		isValid(model);

		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB024InquiryHistoryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_INSERT, createKensakuJoken(model));
	}

	/**
	 * �₢���킹�������X�V���s���܂��B
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void updateToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model) {
		
		// ���[�U���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �₢���킹�������̎擾�E�Đݒ�
		RcpTToiawaseRireki toiawaseRireki = model.getToiawaseRirekiInfo();
		
		// �₢���킹�m�n
		toiawaseRireki.setToiawaseNo(model.getToiawaseNo());
		// �₢���킹�����m�n
		toiawaseRireki.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		// �ŏI�X�V�Җ�
		toiawaseRireki.setLastUpdNm(userContext.getUserName());
		// �ŏI�X�V��Ђh�c
		toiawaseRireki.setUpdKaishaId(userContext.getKaishaId());
		// �o�^�敪���uTLCSS�v�̏ꍇ�A�S���Җ���NULL�N���A
		if (model.getToiawaseRirekiInfo().isRegistKbnToTlcss()) {
			toiawaseRireki.setTantoshaNm(null);
		}

		// �₢���킹�������̍X�V
		if (toiawaseRirekiDao.updateForTores(toiawaseRireki) == 0) {
			// �߂�̏������擾���ɁA�₢���킹�����擾���Ȃ�
			model.setUpdateError(true);

			// �X�V�������O���̏ꍇ�A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// �₢���킹�e�[�u���̍X�V�����X�V
		model.getToiawaseInfo().setUpdDt(model.getToiawaseUpdDt());
		model.getToiawaseInfo().setLastUpdId(userContext.getLoginId());
		model.getToiawaseInfo().setLastUpdNm(userContext.getUserName());
		if (toiawaseDao.updateUpdDt(model.getToiawaseInfo()) == 0) {
			// �߂�̏������擾���ɁA�₢���킹�����擾���Ȃ�
			model.setUpdateError(true);

			// �X�V�������O���̏ꍇ�A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// �₢���킹�����Ǐ��X�V
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), false);

		// �X�V�`�F�b�N�����i�㏈���`�F�b�N�j
		isValid(model);

		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB024InquiryHistoryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_UPDATE, createKensakuJoken(model));
	}

	/**
	 * �₢���킹�������폜���s���܂��B
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void deleteToiawaseRirekiInfo(TB024InquiryHistoryEntryModel model) {
		// ���[�U���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �₢���킹�������ޔ�
		if (toiawaseRirekiDao.insertTaihi(model.getToiawaseRirekiInfo()) == 0) {
			// �߂�̏������擾���ɁA�₢���킹�����擾���Ȃ�
			model.setUpdateError(true);

			// �X�V�������O���̏ꍇ�A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		try {
			// �₢���킹�������폜
			toiawaseRirekiDao.delete(model.getToiawaseRirekiInfo());
		} catch (DataIntegrityViolationException e) {
			// �߂�̏������擾���ɁA�₢���킹�����擾���Ȃ�
			model.setUpdateError(true);

			// �O���L�[�Q�ƃG���[�̏ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0033"));
		}

		// �₢���킹�e�[�u���̍X�V�����X�V
		model.getToiawaseInfo().setUpdDt(model.getToiawaseUpdDt());
		model.getToiawaseInfo().setLastUpdId(userContext.getLoginId());
		model.getToiawaseInfo().setLastUpdNm(userContext.getUserName());
		if (toiawaseDao.updateUpdDt(model.getToiawaseInfo()) == 0) {
			// �߂�̏������擾���ɁA�₢���킹�����擾���Ȃ�
			model.setUpdateError(true);

			// �X�V�������O���̏ꍇ�A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// �₢���킹�����Ǐ��X�V
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), false);

		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB024InquiryHistoryEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_DELETE, createKensakuJoken(model));
	}

	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 * @return ��������
	 */
	private String createKensakuJoken(TB024InquiryHistoryEntryModel model) {
		String kensakuJoken = "";
		StringBuilder accesslog = new StringBuilder("");

		if (model.isInsert() || model.isUpdate()) {
			// �o�^�A�X�V�̏ꍇ
			NullExclusionToStringBuilder toiawaseRirekiEntry =
				new NullExclusionToStringBuilder(
					model.getToiawaseRirekiInfo(),
					NullExclusionToStringBuilder.CSV_STYLE, null, null,
					false, false);

			// ���O���鍀��
			List<String> excludeFiledList = new ArrayList<String>(Arrays.asList(Constants.EXCLUDE_FIELD_NAMES));
			// ���̕����́A�o�͂��Ȃ�
			excludeFiledList.add("tantoshaNmToTantoshaId");
			excludeFiledList.add("kaishaNm");
			excludeFiledList.add("jokyoKbnNm");
			excludeFiledList.add("iraiExsits");

			toiawaseRirekiEntry.setExcludeFieldNames((String[]) excludeFiledList.toArray(new String[0]));

			accesslog.append(toiawaseRirekiEntry.toString());

			kensakuJoken = accesslog.toString();
		} else if (model.isDelete()) {
			// �폜�̏ꍇ
			RcpTToiawaseRireki toiawaseRireki = model.getToiawaseRirekiInfo();

			accesslog.append("toiawaseNo=");
			accesslog.append(toiawaseRireki.getToiawaseNo());
			accesslog.append(",");
			accesslog.append("toiawaseRirekiNo=");
			accesslog.append(toiawaseRireki.getToiawaseRirekiNo().intValue());

			kensakuJoken = accesslog.toString();
		}

		return kensakuJoken;
	}

	/**
	 * �o�^,�X�V�`�F�b�N�������s���܂��B
	 *
	 * @param model �₢���킹����o�^��ʃ��f��
	 * @throws ValidationException ���̓G���[�����������ꍇ
	 */
	private void isValid(TB024InquiryHistoryEntryModel model) {
		// �˗������J�ݒ�ۃ`�F�b�N
		boolean isValidPublish = toiawaseCheckLogic.isValid(
				CheckToPublishToiawaseLogic.CONTENT_TYPE_TOIAWASE_RIREKI,
				model.getToiawaseRirekiInfo().getToiawaseRirekiKokaiFlg(),
				model.getToiawaseNo(), model.getToiawaseRirekiNo());

		if (!isValidPublish) {
			String publishMsg = model.getToiawaseRirekiInfo().isPublished() ? "���J" : "�����J��";

			// ���J�ݒ肪�����̏ꍇ�A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0030", "�₢���킹�������", publishMsg));
		}

	}

	/**
	 * �ϑ���Њ֘A�`�F�b�N���s���܂��B
	 *
	 * @param model �₢���킹�o�^��ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean isOutsourcer(TB024InquiryHistoryEntryModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �Z�b�V�����̌������u40:�ϑ����SV�v�܂��́u41:�ϑ����OP�v�̏ꍇ
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			return outsourcerValidationLogic.isValid(userContext.getKaishaId(), model.getToiawaseInfo().getKokyakuId());
		} else {
			return true;
		}
	}
}
