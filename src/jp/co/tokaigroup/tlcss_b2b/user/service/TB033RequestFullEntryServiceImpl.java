package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.FileUploadLogic;
import jp.co.tokaigroup.reception.common.MailTemplate;
import jp.co.tokaigroup.reception.common.logic.CheckToPublishToiawaseLogic;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMSashidashininDao;
import jp.co.tokaigroup.reception.dao.RcpTFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuWithNoIdDao;
import jp.co.tokaigroup.reception.dao.RcpTMailRirekiDao;
import jp.co.tokaigroup.reception.dao.RcpTOtherFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyoJokyoDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTFileUploadDao;
import jp.co.tokaigroup.reception.dao.TbTSagyoJokyoDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMSashidashinin;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTOtherFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.entity.TbTFileUpload;
import jp.co.tokaigroup.reception.entity.TbTSagyoJokyo;
import jp.co.tokaigroup.reception.print.logic.RC902SagyoIraiHyoLogic;
import jp.co.tokaigroup.reception.print.logic.RC907GyoshaSagyoHokokuHyoLogic;
import jp.co.tokaigroup.reception.util.FileRemoteUtil;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.mail.VelocityEmail;
import jp.co.tokaigroup.si.fw.mail.VelocityWrapper;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.CommonUtil;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB033RequestFullEntryModel;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �˗��o�^�T�[�r�X�����N���X�B
 * 
 * @author S.Nakano
 * @version 1.0 2015/10/21
 * @version 1.1 2016/02/12 C.Kobayashi �t�@�C���A�b�v���[�h���̃v���C�}���[�L�[�ύX�Ή�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB033RequestFullEntryServiceImpl extends TLCSSB2BBaseService
		implements TB033RequestFullEntryService {
	// �v���p�e�B�t�@�C������擾
	/** ��Ə󋵉摜�t�@�C���A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_TLCSS_SAGYO_JOKYO = ResourceFactory.getResource().getString("UPLOAD_PATH_TLCSS_SAGYO_JOKYO");
	/** �˗����A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_IRAI_FILE = ResourceFactory.getResource().getString("UPLOAD_PATH_IRAI_FILE");
	/** �R�s�[���A�b�v���[�h�p�X */
	private static final String UPLOAD_PATH_PDF = ResourceFactory.getResource().getString("PDF_PATH");
	/** �R�s�[��A�b�v���[�h�p�X�i�ꎞ�o�͗p�j */
	private static final String UPLOAD_PATH_TEMP_REPORT = ResourceFactory.getResource().getString("UPLOAD_PATH_TEMP_REPORT");
	
	// ���[���e���v���[�g���ځ@�L�[
	/** ���[���e���v���[�g���� �L�[ �ƎҖ� */
	private static final String MAIL_KEY_GYOSHA_NAME = "gyoshaName";
	/** ���[���e���v���[�g���� �L�[ �V�X�e���� */
	private static final String MAIL_KEY_SYSTEM_NAME = "systemName";
	/** ���[���e���v���[�g���� �L�[ ��ƈ˗�URL */
	private static final String MAIL_KEY_SAGYO_IRAI_URL = "sagyoIraiUrl";
	/** ���[���e���v���[�g���� �L�[ �₢���킹�m�n */
	private static final String MAIL_KEY_TOIAWASE_NO = "toiawaseNo";
	/** ���[���e���v���[�g���� �L�[ �V�X�e���₢���킹��d�b�ԍ� */
	private static final String MAIL_KEY_CONTACT_INFORMAITION = "contactInformation";
	/** ���[���e���v���[�g���� �L�[ �ԐM���[���A�h���X */
	private static final String MAIL_KEY_REPLY_MAIL_ADDRESS = "returnMailAddress";
	/** ���[���e���v���[�g���� �L�[ ���M����Ж� */
	private static final String MAIL_KEY_NOTICE_COMPAY_NAME = "noticeCompanyNm";
	
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
	/** ���Z�v�V�����h�c�����ڋq�e�[�u��DAO */
	@Autowired
	private RcpTKokyakuWithNoIdDao kokyakuWithoutDao;
	/** ���Z�v�V�����Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;
	/** ���Z�v�V�������[�����M����DAO */
	@Autowired
	private RcpTMailRirekiDao mailRirekiDao;
	/** ���Z�v�V�����󋵋敪�}�X�^DAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;
	/** ���Z�v�V�������o�l���}�X�^DAO */
	@Autowired
	private RcpMSashidashininDao sashidashininDao;
	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;
	/** �O���T�C�g�Ǝ҉񓚍�Ə󋵃e�[�u��DAO */
	@Autowired
	private TbTSagyoJokyoDao gyoshaSagyoJokyoDao;
	/** �O���T�C�g�Ǝ҉񓚈˗��t�@�C���A�b�v���[�h�e�[�u��DAO */
	@Autowired
	private TbTFileUploadDao gyoshaFileUploadDao;
	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** NATOS�p�X���[�h�lDAO */
	@Autowired
	private NatosMPasswordDao natosPswdDao;
	
	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic commonInfoLogic;
	/** �₢���킹���J�`�F�b�N���W�b�N */
	@Autowired
	private CheckToPublishToiawaseLogic toiawaseCheckLogic;
	/** �ϑ���Њ֘A�`�F�b�N���W�b�N */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	/** ��ƈ˗������W�b�N�N���X */
	@Autowired
	private RC902SagyoIraiHyoLogic sagyoIraiHyoLogic;
	/** ��ƕ񍐏��i�Ǝҗp�j���W�b�N�N���X */
	@Autowired
	private RC907GyoshaSagyoHokokuHyoLogic gyoshaSagyoHokokuHyoLogic;
	
	/**
	 * �����\���������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	@Override
	public TB033RequestFullEntryModel getInitInfo(TB033RequestFullEntryModel model) {
		// �����\���p�����[�^�`�F�b�N
		this.validateInitParameter(model);
		
		// �₢���킹���擾
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// �₢���킹��񂪑��݂��Ȃ��ꍇ�́A�r���`�F�b�N�G���[
			model.setInitError(true);
			
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		model.setToiawase(toiawase);
		model.setToiawaseUpdDt(toiawase.getUpdDt());
		
		try {
			// �����\�������̋��ʏ������Ăяo��
			return executeInitCommonProcess(model);
		} catch (ValidationException e) {
			// �����\�������ŃG���[���N�������ꍇ�́A�����G���[�t���O�𗧂ĂāA
			// ��O�𑗏o����
			model.setInitError(true);
			
			throw e;
		}
	}

	/**
	 * �T�[�o�[�T�C�h�G���[�������́A�����\���������s���܂��B
	 *
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	@Override
	public TB033RequestFullEntryModel parepareInitInfo(TB033RequestFullEntryModel model) {
		return executeInitCommonProcess(model);
	}
	
	/**
	 * �����\�������̋��ʏ������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	private TB033RequestFullEntryModel executeInitCommonProcess(TB033RequestFullEntryModel model) {
		// ���ʃR�[�h�}�X�^���擾
		Map<String, List<RcpMComCd>> comKbnMap = 
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);
		
		// �ڋq���擾
		if (StringUtils.isNotBlank(model.getToiawase().getKokyakuId())) {
			// �₢���킹���̌ڋq�h�c������ꍇ�́A�ڋq��{���擾
			model.setKokyakuEntity(commonInfoLogic.getKokyakuInfo(model.getToiawase().getKokyakuId()));
		} else {
			// �₢���킹���̌ڋq�h�c���Ȃ��ꍇ�́A�h�c�����ڋq���擾
			RcpTKokyakuWithNoId kokyakuWithoutId = 
				kokyakuWithoutDao.selectByPrimaryKey(model.getToiawaseNo());
			if (kokyakuWithoutId == null) {
				// �h�c�����ڋq��񂪑��݂��Ȃ��ꍇ�́A�r���`�F�b�N�G���[
				model.setInitError(true);
				
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			Map<String, RcpMComCd> kokyakuKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
			// �ڋq�敪��
			if (kokyakuKbnMap != null && kokyakuKbnMap.containsKey(kokyakuWithoutId.getKokyakuKbn())) {
				kokyakuWithoutId.setKokyakuKbnNm(kokyakuKbnMap.get(kokyakuWithoutId.getKokyakuKbn()).getComVal());
			}
			
			model.setKokyakuWithoutId(kokyakuWithoutId);
		}
				
		// �K���]���X�g
		model.setHomonKiboList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI));
		// �Ǝ҉񓚃��X�g
		model.setGyoshaKaitoList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI));
		
		// ���O�C�����[�U���
		model.setUserContext((TLCSSB2BUserContext) getUserContext());
		
		return model;
	}
	
	/**
	 * ��ƈ˗����擾�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	@Override
	public TB033RequestFullEntryModel getSagyoIraiInfo(TB033RequestFullEntryModel model) {
		// �����\���������Ăяo��
		model = this.getInitInfo(model);
		
		// �₢���킹�������擾
		RcpTToiawaseRireki toiawaseRireki = 
			toiawaseRirekiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (toiawaseRireki == null) {
			// �₢���킹������񂪑��݂��Ȃ��ꍇ�́A�r���`�F�b�N�G���[
			model.setInitError(true);
			
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		model.setToiawaseRireki(toiawaseRireki);
		
		// �˗����擾
		RcpTIrai irai = 
			iraiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (irai == null) {
			// �˗���񂪑��݂��Ȃ��ꍇ�́A�r���`�F�b�N�G���[
			model.setInitError(true);
			
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		// ���[�����M�󋵏��擾
		model.setMailRireki(mailRirekiDao.selectByPrimaryKey(
				model.getToiawaseNo() + "-" + StringUtils.leftPad(model.getToiawaseRirekiNo().toPlainString(), 3, '0')));
		
		// ��Ə󋵏��擾
		RcpTSagyoJokyo sagyoJokyo = 
			sagyoJokyoDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
		
		List<RcpTFileUpload> iraiFileUploadList = null;
		List<RcpTOtherFileUpload> otherFileUploadList = null;
		if (sagyoJokyo != null) {
			model.setSagyoJokyoUpdDt(sagyoJokyo.getUpdDt());
			
			// ��Ə󋵏�񂪎擾�ł����ꍇ
			// �a���ϊ�����
			Map<String, List<RcpMComCd>> comCdMap = comCdDao.selectAnyAsMap(
					RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);
			Map<String, Map<String, RcpMComCd>> convertMap =
				comCdDao.convertMapAsKeyToEntity(comCdMap);

			// �����t���OMap
			Map<String, RcpMComCd> kanryoFlgMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);
			
			// �˗��t�@�C���A�b�v���[�h���擾
			iraiFileUploadList = fileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			
			// �˗����̑��t�@�C���A�b�v���[�h���擾
			otherFileUploadList = otherFileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			
			// �Ǝ҉񓚍�Ə󋵏��擾
			TbTSagyoJokyo gyoshaSagyoJokyo = gyoshaSagyoJokyoDao.selectByPrimaryKey(
					model.getToiawaseNo(), model.getToiawaseRirekiNo());
		
			// ��Ɗ����t���O
			if (gyoshaSagyoJokyo != null &&
				kanryoFlgMap.containsKey(gyoshaSagyoJokyo.getSagyoKanryoFlg())) {
				gyoshaSagyoJokyo.setSagyoKanryoFlgNm(kanryoFlgMap.get(
						gyoshaSagyoJokyo.getSagyoKanryoFlg()).getComVal());
			}
			
			model.setGyoshaSagyoJokyo(gyoshaSagyoJokyo);
			
			// �Ǝ҉񓚈˗��t�@�C���A�b�v���[�h���擾
			List<TbTFileUpload> gyoshaFileUploadList =
				gyoshaFileUploadDao.selectBy(model.getToiawaseNo(),
						model.getToiawaseRirekiNo(), null);
			
			// �\���p�̋Ǝ҉񓚃t�@�C���A�b�v���[�h��񃊃X�g�擾
			model.setGyoshaUploadFileList(createGyoshaAnswerFileUploadList(
					gyoshaFileUploadList));
		}
		
		// ��ʒl��������
		if (model.isRestore()) {
			// �A�N�V�����^�C�v���urestore:��ʒl�����v�̏ꍇ�́A��ʓ��͒l�𕜌�
			// �˗��t�@�C���A�b�v���[�h������ɂR���ɂ���
			editSagyoJokyoImageFileContents(model, iraiFileUploadList, true);
			// �˗����̑��t�@�C���A�b�v���[�h������ɂR���ɂ���
			editOtherFileContents(model, otherFileUploadList, true);
			
			// ��ƈ˗����e
			irai.setTantoshaNm(model.getIrai().getTantoshaNm());
			irai.setIraiGyoshaCd(model.getIrai().getIraiGyoshaCd());
			irai.setIraiNaiyo(model.getIrai().getIraiNaiyo());
			irai.setHomonKiboYmd(model.getIrai().getHomonKiboYmd());
			irai.setHomonKiboJikanKbn(model.getIrai().getHomonKiboJikanKbn());
			irai.setHomonKiboBiko(model.getIrai().getHomonKiboBiko());
			irai.setGyoshaKaitoYmd(model.getIrai().getGyoshaKaitoYmd());
			irai.setGyoshaKaitoJikanKbn(model.getIrai().getGyoshaKaitoJikanKbn());
			irai.setGyoshaKaitoBiko(model.getIrai().getGyoshaKaitoBiko());
			irai.setIraiKokaiFlg(model.getIrai().getIraiKokaiFlg());
			irai.setTantoshaNm(model.getIrai().getTantoshaNm());
			
			// ��Ə�
			if ((model.getSagyoJokyo() != null && StringUtils.isNotBlank(model.getSagyoJokyo().getToiawaseNo()))
				&& sagyoJokyo != null) {
				sagyoJokyo.setSagyoKanryoFlg(model.getSagyoJokyo().getSagyoKanryoFlg());
				sagyoJokyo.setSagyoKanryoJikan(model.getSagyoJokyo().getSagyoKanryoJikan());
				sagyoJokyo.setSagyoKanryoYmd(model.getSagyoJokyo().getSagyoKanryoYmd());
				sagyoJokyo.setJokyo(model.getSagyoJokyo().getJokyo());
				sagyoJokyo.setCause(model.getSagyoJokyo().getCause());
				sagyoJokyo.setJisshiNaiyo(model.getSagyoJokyo().getJisshiNaiyo());
				sagyoJokyo.setSagyoJokyoKokaiFlg(model.getSagyoJokyo().getSagyoJokyoKokaiFlg());
			}
		} else {
			// �˗��t�@�C���A�b�v���[�h������ɂR���ɂ���
			editSagyoJokyoImageFileContents(model, iraiFileUploadList, false);
			// �˗����̑��t�@�C���A�b�v���[�h������ɂR���ɂ���
			editOtherFileContents(model, otherFileUploadList, false);
		}
		model.setIrai(irai);
		model.setBeforeIraiKokaiFlg(irai.getIraiKokaiFlg());
		model.setIraiUpdDt(irai.getUpdDt());
		
		model.setSagyoJokyo(sagyoJokyo);
		
		// �˗��Ǝҏ��擾
		model.setIraiGyosha(gyoshaDao.selectByPrimaryKey(irai.getIraiGyoshaCd()));
		
		// �X�V�����\�������̋��ʏ������Ăяo��
		return executeUpdateInitCommonProcess(model);
	}

	/**
	 * �T�[�o�[�T�C�h�G���[�������́A�X�V�����\���������s���܂��B
	 *
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	@Override
	public TB033RequestFullEntryModel parepareInitInfoForUpdate(TB033RequestFullEntryModel model) {
		// �T�[�o�[�T�C�h�G���[�p�̏����\���������Ăяo��
		model = this.parepareInitInfo(model);
		
		// �₢���킹�������擾
		RcpTToiawaseRireki toiawaseRireki = 
			toiawaseRirekiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (toiawaseRireki == null) {
			// �₢���킹������񂪑��݂��Ȃ��ꍇ�́A�r���`�F�b�N�G���[
			model.setInitError(true);
			
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		model.setToiawaseRireki(toiawaseRireki);
		
		// ���[�����M�󋵏��擾
		model.setMailRireki(mailRirekiDao.selectByPrimaryKey(
				model.getToiawaseNo() + "-" + model.getToiawaseRirekiNo().toPlainString()));

		List<RcpTFileUpload> iraiFileUploadList = null;
		List<RcpTOtherFileUpload> otherFileUploadList = null;
		if (model.getSagyoJokyo() != null) {
			// �˗��t�@�C���A�b�v���[�h���擾
			iraiFileUploadList = fileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			
			// �˗����̑��t�@�C���A�b�v���[�h���擾
			otherFileUploadList = otherFileUploadDao.selectBy(
					model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			
			// �Ǝ҉񓚈˗��t�@�C���A�b�v���[�h���擾
			List<TbTFileUpload> gyoshaFileUploadList =
				gyoshaFileUploadDao.selectBy(model.getToiawaseNo(),
						model.getToiawaseRirekiNo(), null);
			
			// �\���p�̋Ǝ҉񓚃t�@�C���A�b�v���[�h��񃊃X�g�擾
			model.setGyoshaUploadFileList(createGyoshaAnswerFileUploadList(
					gyoshaFileUploadList));
		}
		
		// �˗��t�@�C���A�b�v���[�h������ɂR���ɂ���
		editSagyoJokyoImageFileContents(model, iraiFileUploadList, true);
		// �˗����̑��t�@�C���A�b�v���[�h������ɂR���ɂ���
		editOtherFileContents(model, otherFileUploadList, true);
		
		// �X�V�����\�������̋��ʏ������Ăяo��
		return executeUpdateInitCommonProcess(model);
	}
	
	/**
	 * �X�V�����\�������̋��ʏ������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	private TB033RequestFullEntryModel executeUpdateInitCommonProcess(TB033RequestFullEntryModel model) {
		// �󋵋敪�}�X�^���X�g�̎擾
		List<String> gamenKbnList = new ArrayList<String>();
		gamenKbnList.add(Constants.JOKYO_GAMEN_KBN_IRAI_ENTRY);
		gamenKbnList.add(Constants.JOKYO_GAMEN_KBN_ZENGAMEN);
		model.setJokyoKbnList(jokyoKbnDao.selectByGamenKbn(gamenKbnList, ""));
		
		// �p�X���[�h�}�X�^�̃��X�g����
		List<String> userIdList = new ArrayList<String>();
		userIdList.add(model.getIrai().getTantoshaId());
		if (StringUtils.isNotBlank(model.getIrai().getLastUpdId())
			&& !userIdList.contains(model.getIrai().getLastUpdId())) {
			userIdList.add(model.getIrai().getLastUpdId());
		}
		if (StringUtils.isNotBlank(model.getIrai().getLastPrintId())
			&& !userIdList.contains(model.getIrai().getLastPrintId())) {
			userIdList.add(model.getIrai().getLastPrintId());
		}
		if (model.getSagyoJokyo() != null && !userIdList.contains(model.getSagyoJokyo().getLastUpdId())) {
			userIdList.add(model.getSagyoJokyo().getLastUpdId());
		}
		
		// �p�X���[�h�}�X�^Map�擾
		Map<String, String> userMap = natosPswdDao.convertMap(
				natosPswdDao.selectByList(userIdList, null));
		
		if (userMap.containsKey(model.getIrai().getTantoshaId())) {
			// �˗����̔����S���҂h�c�̘a���ϊ�
			model.getIrai().setTantoshaNmForId(userMap.get(model.getIrai().getTantoshaId()));
		}
		
		if (userMap.containsKey(model.getIrai().getLastUpdId())) {
			// �˗����̍ŏI�X�V�҂h�c�̘a���ϊ�
			model.getIrai().setLastUpdNmForId(userMap.get(model.getIrai().getLastUpdId()));
		}
		
		if (StringUtils.isNotBlank(model.getIrai().getLastPrintId())
			&& userMap.containsKey(model.getIrai().getLastPrintId())) {
			// �˗����̍ŏI����҂h�c�̘a���ϊ�
			model.getIrai().setLastPrintNmForId(userMap.get(model.getIrai().getLastPrintId()));
		}
		
		if (model.getSagyoJokyo() != null && userMap.containsKey(model.getSagyoJokyo().getLastUpdId())) {
			// ��Ə󋵏��̍ŏI�X�V�҂h�c�̘a���ϊ�
			model.getSagyoJokyo().setLastUpdNmForId(userMap.get(model.getSagyoJokyo().getLastUpdId()));
		}
		
		return model;
	}
	
	/**
	 * ��ƈ˗����o�^�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	@Override
	@Transactional(value="txManager")
	public void insertSagyoIraiInfo(TB033RequestFullEntryModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �₢���킹�����e�[�u���o�^
		insertToiawaseRireki(model);
		
		// �˗��e�[�u���o�^�i�o�^���͐V�����C���X�^���X�𐶐����ēo�^�j
		RcpTIrai irai = new RcpTIrai();
		// �v���p�e�B�R�s�[
		CommonUtil.copyProperties(model.getIrai(), irai);
		irai.setToiawaseNo(model.getToiawaseNo());
		irai.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		irai.setTantoshaId(Constants.TORES_APL_ID);
		irai.setCreId(userContext.getLoginId());
		irai.setLastUpdId(userContext.getLoginId());
		irai.setCreKaishaId(userContext.getKaishaId());
		irai.setUpdKaishaId(userContext.getKaishaId());
		irai.setCreNm(userContext.getUserName());
		irai.setLastUpdNm(userContext.getUserName());
		
		// �˗��e�[�u���o�^
		iraiDao.insert(irai);
		
		// �₢���킹���Ǐ��X�V
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), false);
		
		// �o�^�`�F�b�N�����i�㏈���j
		validateSagyoIraiInfo(model);
		
		// �₢���킹�X�V���X�V
		updateToiawaseUpdDt(model);
		
		// �A�N�Z�X���O�o�^����
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM, 
				Constants.BUTTON_NM_INSERT, createKensakuJoken(model.getIrai()));
	}

	/**
	 * ��ƈ˗����X�V�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	@Override
	@Transactional(value="txManager")
	public void updateSagyoIraiInfo(TB033RequestFullEntryModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		if (model.isFileUploadExecutable()) {
			// �t�@�C���w�莞�̓t�@�C���A�b�v���[�h���������{����
			validateFileUpload(model);
		}
		
		if (model.isOtherFileUploadExecutable()) {
			// ���̑��t�@�C���w�莞�͂��̑��t�@�C���A�b�v���[�h���������{����
			validateOtherFileUpload(model);
		}
		
		// �o�^���͐V�����C���X�^���X�𐶐����ēo�^
		RcpTIrai irai = new RcpTIrai();
		// �v���p�e�B�R�s�[
		CommonUtil.copyProperties(model.getIrai(), irai);
		
		irai.setToiawaseNo(model.getToiawaseNo());
		irai.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		irai.setUpdKaishaId(userContext.getKaishaId());
		irai.setLastUpdNm(userContext.getUserName());
		
		// �˗��e�[�u���X�V
		if (iraiDao.updateForTores(irai) == 0) {
			// �˗����̍X�V�������O���̏ꍇ�́A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		// ��ʂɂč�Ə󋵂̖₢���킹�m�n��NOT NULL���𔻒肵�Ă��邽�߁A�V�����C���X�^���X�𐶐����ēo�^
		RcpTSagyoJokyo sagyoJokyo = new RcpTSagyoJokyo();
		// �v���p�e�B�R�s�[
		CommonUtil.copyProperties(model.getSagyoJokyo(), sagyoJokyo);
		sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
		sagyoJokyo.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		// ��ʂɂč�Ɗ����`�F�b�N���`�F�b�NOFF�ɂȂ��Ă����ꍇ�ɂ́A��Ɗ������A��Ɗ������Ԃ�null�ɂ���
		if (!model.isSagyoKanryoFlgChecked()) {
			sagyoJokyo.setSagyoKanryoYmd(null);
			sagyoJokyo.setSagyoKanryoJikan(null);
		}
		// ��ʂɂĕ񍐏��̌��J���~�ƂȂ��Ă����ꍇ�́A�񍐏����J�t���O�𖢌��J�ɂ���
		if (TB033RequestFullEntryModel.STOP_PUBLISH_FLG_ON.equals(model.getStopPublishFlg())){
			sagyoJokyo.setHokokushoKokaiFlg(RcpTSagyoJokyo.HOKOKUSHO_KOKAI_FLG_NOT_PUBLISH);
		}
		sagyoJokyo.setCreNm(userContext.getUserName());
		sagyoJokyo.setLastUpdNm(userContext.getUserName());
		
		// �N���b�N�����{�^�����i�f�t�H���g�F�X�V�j
		String clickButtonNm = Constants.BUTTON_NM_UPDATE;
		// ��Ə󋵃e�[�u���X�V
		if (sagyoJokyoDao.updateForTores(sagyoJokyo) == 0) {
			// �X�V�������O���̏ꍇ�́A�o�^�������s��
			sagyoJokyo.setHokokushoKokaiFlg(RcpTSagyoJokyo.HOKOKUSHO_KOKAI_FLG_NOT_PUBLISH);
			
			sagyoJokyoDao.insert(sagyoJokyo);
			
			// �N���b�N�����{�^������o�^�Ƃ���
			clickButtonNm = Constants.BUTTON_NM_INSERT;
		}
		
		// �X�V�`�F�b�N�����i�㏈���j
		// ���J�`�F�b�N�ł͂c�a�̒l���Q�Ƃ��邽�߁A
		// ��U�˗����E��Ə󋵏����X�V������Ɍ��J�`�F�b�N���s��
		validateSagyoIraiInfo(model);
		
		if (model.isFileUploadExecutable() || model.isFileCommentUpdateExecutable()) {
			// �t�@�C���w�莞�A�܂��́A�t�@�C���R�����g�X�V���̓t�@�C���A�b�v���[�h���������{����
			executeFileUpload(model);
		}
		
		if (model.isOtherFileUploadExecutable() || model.isOtherFileCommentUpdateExecutable()) {
			// ���̑��t�@�C���w�莞�A�܂��́A���̑��t�@�C���R�����g�X�V���͂��̑��t�@�C���A�b�v���[�h���������{����
			executeOtherFileUpload(model);
		}
		
		// �������ɓo�^����`�F�b�N��ON�̏ꍇ�A�₢���킹�����e�[�u���o�^
		if (model.isToiawaseHistoryAutoRegistExcecutable()) {
			// ��ʕ\���ł́A�o�^�O�̖₢���킹������\�����邽�߁A�ۑ����Ă���
			BigDecimal dispToiawaseRirekiNo = model.getToiawaseRirekiNo();
			// �₢���킹�����m�n���擾���₢���킹�����e�[�u���o�^
			insertToiawaseRireki(model);
			// �₢���킹�����m�n�����ɖ߂�
			model.setToiawaseRirekiNo(dispToiawaseRirekiNo);
		}
		
		// �₢���킹�X�V���X�V
		updateToiawaseUpdDt(model);
		
		// �A�N�Z�X���O�o�^����
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM, 
				clickButtonNm, createKensakuJoken(model.getSagyoJokyo()));
	}

	/**
	 * ��ƈ˗����폜�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	@Override
	@Transactional(value="txManager")
	public void deleteSagyoIraiInfo(TB033RequestFullEntryModel model) {
		List<RcpTFileUpload> fileUploadList = null;
		List<RcpTOtherFileUpload> otherFileUploadList = null;
		
		if (sagyoJokyoDao.countBy(model.getToiawaseNo(), model.getToiawaseRirekiNo()) > 0) {
			// ��Ə󋵂̌������P���ȏ゠��ꍇ
			// �˗��t�@�C���A�b�v���[�h��񃊃X�g�擾
			fileUploadList = 
				fileUploadDao.selectBy(model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			// �˗����̑��t�@�C���A�b�v���[�h��񃊃X�g�擾
			otherFileUploadList =
				otherFileUploadDao.selectBy(model.getToiawaseNo(), model.getToiawaseRirekiNo(), null);
			
			// �˗��t�@�C���A�b�v���[�h���ޔ�
			RcpTFileUpload fileUpload = new RcpTFileUpload();
			fileUpload.setToiawaseNo(model.getToiawaseNo());
			fileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
			// �ޔ��������s
			fileUploadDao.insertTaihiBy(fileUpload);
			
			// �˗��t�@�C���A�b�v���[�h���폜
			fileUploadDao.deleteBy(model.getToiawaseNo(), 
					model.getToiawaseRirekiNo(), null);
			
			// �˗����̑��t�@�C���A�b�v���[�h���ޔ�
			RcpTOtherFileUpload otherFileUpload = new RcpTOtherFileUpload();
			otherFileUpload.setToiawaseNo(model.getToiawaseNo());
			otherFileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
			// �ޔ��������s
			otherFileUploadDao.insertTaihiBy(otherFileUpload);
			
			// �˗����̑��t�@�C���A�b�v���[�h���폜
			otherFileUploadDao.deleteBy(model.getToiawaseNo(), 
					model.getToiawaseRirekiNo(), null);
			
			// ��Ə󋵏��ޔ�
			RcpTSagyoJokyo sagyoJokyo = new RcpTSagyoJokyo();
			sagyoJokyo.setToiawaseNo(model.getToiawaseNo());
			sagyoJokyo.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
			sagyoJokyo.setUpdDt(model.getSagyoJokyoUpdDt());
			// �ޔ��������s
			if (sagyoJokyoDao.insertTaihi(sagyoJokyo) == 0) {
				// �o�^�������O���̏ꍇ�́A�r���G���[
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			try {
				// ��Ə󋵏��폜
				sagyoJokyoDao.delete(sagyoJokyo);
			} catch (DataIntegrityViolationException e) {
				// �O���L�[�Q�ƃG���[�̏ꍇ
				throw new ValidationException(new ValidationPack().addError("MSG0041"));
			}
		}
		
		// �˗����ޔ�
		RcpTIrai irai = new RcpTIrai();
		irai.setToiawaseNo(model.getToiawaseNo());
		irai.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		irai.setUpdDt(model.getIraiUpdDt());
		
		// �ޔ��������s
		if (iraiDao.insertTaihi(irai) == 0) {
			// �o�^�������O���̏ꍇ�́A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		try {
			// �˗����폜
			iraiDao.delete(irai);
		} catch (DataIntegrityViolationException e) {
			// �O���L�[�Q�ƃG���[�̏ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0042"));
		}
		
		// �₢���킹�X�V���X�V
		updateToiawaseUpdDt(model);
		
		// �A�N�Z�X���O�o�^����
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM, 
				Constants.BUTTON_NM_DELETE, createKensakuJoken(model.getIrai()));
		
		if (fileUploadList != null && !fileUploadList.isEmpty()) {
			// �˗��t�@�C���A�b�v���[�h��񂪂P���ȏ゠��ꍇ
			for (RcpTFileUpload fileUpload : fileUploadList) {
				// �A�b�v���[�h�t�@�C���폜����
				if (!FileUploadLogic.deleteServerFile(
						UPLOAD_PATH_TLCSS_SAGYO_JOKYO + "/" + fileUpload.getUploadFileNm())) {
					// �G���[�̏ꍇ�͍폜���s�t�@�C������ۑ�
					model.setFileDeleteError(true);
				}
			}
		}
		
		if (otherFileUploadList != null && !otherFileUploadList.isEmpty()) {
			// �˗����̑��t�@�C���A�b�v���[�h��񂪂P���ȏ゠��ꍇ
			for (RcpTOtherFileUpload otherFileUpload : otherFileUploadList) {
				// �A�b�v���[�h�t�@�C���폜����
				if (!FileUploadLogic.deleteServerFile(
						UPLOAD_PATH_IRAI_FILE + "/" + otherFileUpload.getUploadFileNm())) {
					// �G���[�̏ꍇ�͍폜���s�t�@�C������ۑ�
					model.setFileDeleteError(true);
				}
			}
		}
	}
	
	/**
	 * �Ǝ҈˗����[�����M�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	@Override
	public void sendGyoshaIraiMail(TB033RequestFullEntryModel model) {
		// �Ǝҏ��擾
		RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(model.getIrai().getIraiGyoshaCd());
		if (gyosha == null) {
			// �Ǝҏ�񂪑��݂��Ȃ��ꍇ�A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		String accessLogKensakuJoken = String.format("gyoshaCd=%s,toiawaseNo=%s,toiawaseRirekiNo=%s", 
				model.getIrai().getIraiGyoshaCd(), model.getToiawaseNo(), model.getToiawaseRirekiNo().toString());
		
		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM,
								TB033RequestFullEntryModel.BUTTON_NM_IRAI_MAIL,
								accessLogKensakuJoken);

		try {
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// �V�X�e���}�X�^����擾
			// �V�X�e����
			String systemNm = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_NAME);
			// �V�X�e���t�q�k
			String systemUrl = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL);
			// �ԐM���[���A�h���X
			
			// ��ƈ˗����[���@����
			String subjectSagyoIraiMail = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_SUBJECT_SAGYO_IRAI_MAIL);
			// ���o���A�h���X
			String fromMailAddress = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ADDRESS);
			// ���o���A�J�E���g
			String fromMailAccount = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ACCOUNT);
			// ���o���p�X���[�h
			String fromMailPassword = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_PASSWORD);
			// �ԐM���[���A�h���X
			String returnMailAddress = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_RETURN_MAIL_ADDRESS);
			// ���o�l�}�X�^���擾
			RcpMSashidashinin sashidashinin = sashidashininDao.selectByPrimaryKey(
					RcpMSashidashinin.KBN_SAGYO_IRIA_MAIL_INFO, userContext.getKaishaId());
			if (sashidashinin == null) {
				throw new ApplicationException("���o�l��񂪑��݂��܂���B");
			}
			
			// ��ƈ˗�URL�𐶐�
			String sagyoIraiUrl = createSagyoIraiUrl(model, systemUrl);

			// �u��������Map����
			VelocityWrapper wrapper = new VelocityWrapper(
					MailTemplate.SAGYO_IRAI_MAIL_FILE_NAME);

			// �ԐM���[���A�h���X�i�u��������j
			String sashidashininReturnMailAddress = 
				StringUtils.defaultString(sashidashinin.getMailAddress());
			
			// �u��������
			// �ƎҖ�
			wrapper.put(MAIL_KEY_GYOSHA_NAME, gyosha.getGyoshaNm());
			// �V�X�e����
			wrapper.put(MAIL_KEY_SYSTEM_NAME, systemNm);
			// ��ƈ˗�URL
			wrapper.put(MAIL_KEY_SAGYO_IRAI_URL, sagyoIraiUrl);
			// �₢���킹NO
			wrapper.put(MAIL_KEY_TOIAWASE_NO, model.getToiawaseNo());
			// �V�X�e���₢���킹��d�b�ԍ��́A���o�l�}�X�^��������擾
			wrapper.put(MAIL_KEY_CONTACT_INFORMAITION, sashidashinin.getTelNo());
			// �ԐM���[���A�h���X
			wrapper.put(MAIL_KEY_REPLY_MAIL_ADDRESS, sashidashininReturnMailAddress);
			// ���M����Ж�
			wrapper.put(MAIL_KEY_NOTICE_COMPAY_NAME, sashidashinin.getKaishaNm());

			// ����擾
			String toMailAddress = "";
			if (gyosha.isSameAsSagyoIraiMailAtesaki()) {
				// ��ƗpTEL�Ɠ����ꍇ
				toMailAddress = gyosha.getSagyoMailAddress();
			} else {
				// ����ȊO�̏ꍇ
				toMailAddress = gyosha.getIraiMailAtesakiAddress();
			}

			VelocityEmail email = new VelocityEmail();
			// ���o���A�h���X
			email.setFrom(fromMailAddress);
			// �F��
			email.setAuthenticator(new DefaultAuthenticator(
					fromMailAccount, fromMailPassword));
			// ����
			email.addTo(toMailAddress);
			// ����BCC
			if (StringUtils.isNotBlank(sashidashinin.getBccAddress())) {
				email.addBcc(StringUtils.split(sashidashinin.getBccAddress(), ","));
			}
			// �ԐM���[���A�h���X
			email.addReplyTo(returnMailAddress);
			// ����
			email.setSubject(subjectSagyoIraiMail);
			// �u��������Map�̐ݒ�
			email.setVelocityWrapper(wrapper);

			// ���[�����M
			email.send();
		} catch (EmailException e) {
			// ���[�����M�Ɏ��s�����ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0040", "�Z���^�[�Ǝ҈˗����[�����M"));
		} catch (Exception e) {
			// ���̑���O
			throw new ApplicationException("�Z���^�[�Ǝ҈˗����[�����M�Ɏ��s���܂����B", e);
		}
	}
	
	/**
	 * ��Ə󋵉摜�t�@�C���폜�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	@Override
	@Transactional(value="txManager")
	public void deleteSagyoJokyoImageFile(TB033RequestFullEntryModel model) {
		// �p�����[�^�`�F�b�N
		if (StringUtils.isBlank(model.getUploadFileNm())) {
			// �p�����[�^�̃A�b�v���[�h�t�@�C�������󗓂̏ꍇ�́A�p�����[�^�`�F�b�N�G���[
			throw new ApplicationException("�A�b�v���[�h�t�@�C�����s���F�p�����[�^�̃A�b�v���[�h�t�@�C����");
		}

		if (model.getFileIndex() == null) {
			// �p�����[�^�̃t�@�C���C���f�b�N�X��NULL�̏ꍇ�́A�p�����[�^�`�F�b�N�G���[
			throw new ApplicationException("�t�@�C���C���f�b�N�X�s���F�p�����[�^�̃t�@�C���C���f�b�N�X");
		}
		
		// �˗��t�@�C���A�b�v���[�h���ޔ�
		RcpTFileUpload fileUpload = new RcpTFileUpload();
		fileUpload.setToiawaseNo(model.getToiawaseNo());
		fileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		fileUpload.setFileIndex(model.getFileIndex());
		
		// �ޔ��������s
		fileUploadDao.insertTaihiWithoutOptimisticLock(fileUpload);
		
		// �˗��t�@�C���A�b�v���[�h���폜
		fileUploadDao.deleteWithoutOptimisticLock(model.getToiawaseNo(), 
				model.getToiawaseRirekiNo(), model.getFileIndex());
		
		// �₢���킹�X�V���X�V
		updateToiawaseUpdDt(model);
		
		// �A�N�Z�X���O�o�^����
		String accessLogKensakuJoken = String.format("toiawaseNo=%s,toiawaseRirekiNo=%s,uploadFileNm=%s,fileIndex=%s", 
				model.getToiawaseNo(), model.getToiawaseRirekiNo().toString(), model.getUploadFileNm(), model.getFileIndex().toString());
		
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM, 
				Constants.BUTTON_NM_DELETE, accessLogKensakuJoken);
		
		// �A�b�v���[�h�t�@�C���폜
		String fileNm = UPLOAD_PATH_TLCSS_SAGYO_JOKYO +
						System.getProperty("file.separator") +
						model.getUploadFileNm();

		// �A�b�v���[�h���t�@�C���폜����
		model.setFileDeleteError(!FileUploadLogic.deleteServerFile(fileNm));
	}
	
	/**
	 * ���̑��t�@�C���폜�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	@Override
	@Transactional(value="txManager")
	public void deleteOtherFile(TB033RequestFullEntryModel model) {
		// �p�����[�^�`�F�b�N
		if (StringUtils.isBlank(model.getUploadFileNm())) {
			// �p�����[�^�̃A�b�v���[�h�t�@�C�������󗓂̏ꍇ�́A�p�����[�^�`�F�b�N�G���[
			throw new ApplicationException("�A�b�v���[�h�t�@�C�����s���F�p�����[�^�̃A�b�v���[�h�t�@�C����");
		}

		if (model.getFileIndex() == null) {
			// �p�����[�^�̃t�@�C���C���f�b�N�X��NULL�̏ꍇ�́A�p�����[�^�`�F�b�N�G���[
			throw new ApplicationException("�t�@�C���C���f�b�N�X�s���F�p�����[�^�̃t�@�C���C���f�b�N�X");
		}
		
		// �˗����̑��t�@�C���A�b�v���[�h���ޔ�
		RcpTOtherFileUpload otherFileUpload = new RcpTOtherFileUpload();
		otherFileUpload.setToiawaseNo(model.getToiawaseNo());
		otherFileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		otherFileUpload.setFileIndex(model.getFileIndex());
		
		// �ޔ��������s
		otherFileUploadDao.insertTaihiWithoutOptimisticLock(otherFileUpload);
		
		// �˗����̑��t�@�C���A�b�v���[�h���폜
		otherFileUploadDao.deleteWithoutOptimisticLock(model.getToiawaseNo(), 
				model.getToiawaseRirekiNo(), model.getFileIndex());
		
		// �₢���킹�X�V���X�V
		updateToiawaseUpdDt(model);
		
		// �A�N�Z�X���O�o�^����
		String accessLogKensakuJoken = String.format("toiawaseNo=%s,toiawaseRirekiNo=%s,uploadFileNm=%s,fileIndex=%s", 
				model.getToiawaseNo(), model.getToiawaseRirekiNo().toString(), model.getUploadFileNm(), model.getFileIndex().toString());
		
		tbAccesslogDao.insert(TB033RequestFullEntryModel.GAMEN_NM, 
				Constants.BUTTON_NM_DELETE, accessLogKensakuJoken);
		
		// �A�b�v���[�h�t�@�C���폜
		String fileNm = UPLOAD_PATH_IRAI_FILE +
						System.getProperty("file.separator") +
						model.getUploadFileNm();

		// �A�b�v���[�h���t�@�C���폜����
		model.setFileDeleteError(!FileUploadLogic.deleteServerFile(fileNm));
	}
	
	/**
	 * �摜�_�E�����[�h�����̑Ó����`�F�b�N���s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	@Override
	public void validateImageFileDownlod(TB033RequestFullEntryModel model) {
		// �₢���킹���擾
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// �₢���킹��񂪂Ȃ��ꍇ�A�`�F�b�N�G���[
			throw new ForbiddenException("�₢���킹��񂪑��݂��܂���B");
		}
		
		// �˗����擾
		RcpTIrai irai = iraiDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (irai == null) {
			// �˗���񂪂Ȃ��ꍇ�A�`�F�b�N�G���[
			throw new ForbiddenException("�˗���񂪑��݂��܂���B");
		}
		
		// �˗��t�@�C���A�b�v���[�h���擾
		RcpTFileUpload fileUpload = fileUploadDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo(), model.getFileIndex());
		if (fileUpload == null) {
			// �˗��t�@�C���A�b�v���[�h��񂪂Ȃ��ꍇ�A�`�F�b�N�G���[
			throw new ForbiddenException("�˗��t�@�C���A�b�v���[�h��񂪑��݂��܂���B");
		}
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// �������ϑ����SV�A�ϑ����OP�̏ꍇ�Ƀ`�F�b�N
			if (StringUtils.isBlank(toiawase.getKokyakuId())) {
				// �h�c�����ڋq�������ꍇ
				RcpTKokyakuWithNoId kokyakuWithoutId = 
					kokyakuWithoutDao.selectByPrimaryKey(model.getToiawaseNo());
				if (kokyakuWithoutId == null) {
					// �h�c�����ڋq��񂪑��݂��Ȃ��ꍇ�́A�`�F�b�N�G���[
					throw new ForbiddenException("�h�c�����ڋq��񂪑��݂��܂���B");
				}
				
				// �˗��e�[�u���̓o�^��Ђh�c�ƁA�h�c�����ڋq�e�[�u���̓o�^��Ђh�c���r���A
				// �����łȂ������ꍇ�́A�G���[
				if (!irai.getCreKaishaId().equals(kokyakuWithoutId.getCreKaishaId())) {
					throw new ForbiddenException("�˗����Ƃh�c�����ڋq���̓o�^��Ђ���v���܂���B");
				}
			} else {
				// �h�c�L��ڋq�������ꍇ
				if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(),
						toiawase.getKokyakuId())) {
					// �ϑ���Њ֘A�`�F�b�N�G���[�������ꍇ�́A�G���[
					throw new ForbiddenException("�A�N�Z�X���������݂��܂���B");
				}
			}
		}
	}
	
	/**
	 * PDF�쐬�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @return �˗��o�^��ʃ��f��
	 */
	@Override
	public TB033RequestFullEntryModel createPdf(TB033RequestFullEntryModel model) {
		// �˗����擾
		RcpTIrai irai = iraiDao.selectByPrimaryKey(
				model.getToiawaseNo(), model.getToiawaseRirekiNo());
		if (irai == null) {
			// �˗���񂪂Ȃ��ꍇ�A�`�F�b�N�G���[
			throw new ForbiddenException("�˗���񂪑��݂��܂���B");
		}
		
		// �₢���킹���擾
		RcpTToiawase toiawase = 
			toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// �������ϑ����SV�A�ϑ����OP�̏ꍇ�Ƀ`�F�b�N
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(),
					toiawase.getKokyakuId())) {
				// �ϑ���Њ֘A�`�F�b�N�G���[�������ꍇ�́A�G���[
				throw new ForbiddenException("�A�N�Z�X���������݂��܂���B");
			}
		}
		
		// ��ƈ˗���
		List<String[]> sagyoIraiDataList =
			sagyoIraiHyoLogic.getSagyoIraiCsvList(model.getToiawaseNo(),
					model.getToiawaseRirekiNo(), userContext.getKaishaId());
		// ��ƕ񍐏��i�Ǝҗp�j
		List<String[]> gyoshaSagyoHokokuDataList =
			gyoshaSagyoHokokuHyoLogic.getGyoshaSagyoHokokuCsvList(
					model.getToiawaseNo(), userContext.getKaishaId());

		// �S�Ẵf�[�^
		List<String[]> allDataList = new ArrayList<String[]>();
		allDataList.addAll(sagyoIraiDataList);
		allDataList.addAll(gyoshaSagyoHokokuDataList);

		// PDF�o�́i�}���`�o�́j
		String pdfUrl = sagyoIraiHyoLogic.outputPdfBySsh(allDataList);
		
		// �˗����e�[�u���̍ŏI����ҏ��X�V
		irai.setLastPrintId(userContext.getLoginId());
		irai.setLastPrintNm(userContext.getUserName());
		
		iraiDao.updatePrintInfo(irai);
		
		// �擾�����p�X����t�@�C�������擾����
		String pdfNm = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);
		
		// �R�s�[���t�@�C�����̃p�X��ݒ�
		String fromCopyPdfPath = UPLOAD_PATH_PDF + System.getProperty("file.separator") + pdfNm;
		// �R�s�[��t�@�C�����̃p�X��ݒ�
		String toCopyPdfPath = UPLOAD_PATH_TEMP_REPORT + System.getProperty("file.separator") + pdfNm;
		
		// �t�@�C���R�s�[�̎��{
		FileRemoteUtil.remoteCopyFileBySsh(fromCopyPdfPath, toCopyPdfPath);
		
		model.setMakePdfPath(toCopyPdfPath);
		model.setPdfNm(pdfNm);
		
		// ��ʂ��ŐV���ɍX�V����
		model = this.getSagyoIraiInfo(model);
		
		return model;
	}
	
	/**
	 * �����\���p�����[�^�`�F�b�N���s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @throws ApplicationException �p�����[�^�`�F�b�N�G���[������
	 */
	private void validateInitParameter(TB033RequestFullEntryModel model) {
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			// �p�����[�^�̖₢���킹�m�n���󗓂̏ꍇ�́A�p�����[�^�`�F�b�N�G���[
			throw new ApplicationException("�₢���킹�m�n�s���F�p�����[�^�̖₢���킹�m�n");
		}
		if (model.isUpdate() && model.getToiawaseRirekiNo() == null) {
			// �p�����[�^�̖₢���킹�����m�n��NULL�̏ꍇ�́A�p�����[�^�`�F�b�N�G���[
			throw new ApplicationException("�₢���킹�����m�n�s���F�p�����[�^�̖₢���킹�����m�n");
		}
	}
	
	/**
	 * ��ƈ˗����̓o�^�E�X�V�����̑Ó����`�F�b�N���s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @throws ValidationException �Ó����`�F�b�N�G���[������
	 */
	private void validateSagyoIraiInfo(TB033RequestFullEntryModel model) {
		// �˗����̖₢���킹���J�`�F�b�N
		if (!toiawaseCheckLogic.isValid(
				CheckToPublishToiawaseLogic.CONTENT_TYPE_IRAI,
				model.getIrai().getIraiKokaiFlg(),
				model.getToiawaseNo(),
				model.getToiawaseRirekiNo())) {
			// �`�F�b�NNG�̏ꍇ�A�T�[�o�`�F�b�N�G���[
			String publishMsg = model.getIrai().isPublished() ? "���J" : "�����J��";

			// ���J�ݒ肪�����̏ꍇ�A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0030", "�˗����", publishMsg));
		}
		
		if (model.isUpdate()) {
			// �X�V�����̏ꍇ
			// ��Ə󋵏��̖₢���킹���J�`�F�b�N
			if (!toiawaseCheckLogic.isValid(
					CheckToPublishToiawaseLogic.CONTENT_TYPE_SAGYO_JOKYO,
					model.getSagyoJokyo().getSagyoJokyoKokaiFlg(),
					model.getToiawaseNo(),
					model.getToiawaseRirekiNo())) {
				// �`�F�b�NNG�̏ꍇ�A�T�[�o�`�F�b�N�G���[
				String publishMsg = model.getSagyoJokyo().isPublished() ? "���J" : "�����J��";

				// ���J�ݒ肪�����̏ꍇ�A�G���[
				throw new ValidationException(new ValidationPack().addError("MSG0030", "��Ə�", publishMsg));
			}
			
			// ��ƕ񍐏��̖₢���킹���J�`�F�b�N
			if (!toiawaseCheckLogic.isValid(
					CheckToPublishToiawaseLogic.CONTENT_TYPE_SAGYO_HOKOKUSHO,
					model.getSagyoJokyo().getSagyoJokyoKokaiFlg(),
					model.getToiawaseNo(),
					model.getToiawaseRirekiNo())) {
				// �`�F�b�NNG�̏ꍇ�A�T�[�o�`�F�b�N�G���[
				String publishMsg = model.getSagyoJokyo().isPublished() ? "���J" : "�����J��";

				// ���J�ݒ肪�����̏ꍇ�A�G���[
				throw new ValidationException(new ValidationPack().addError("MSG0030", "��Ə�", publishMsg));
			}
		}
	}
	
	/**
	 * �t�@�C���A�b�v���[�h���̑Ó����`�F�b�N���s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @throws ValidationException �Ó����`�F�b�N�G���[������
	 */
	private void validateFileUpload(TB033RequestFullEntryModel model) {
		// ���[�U���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �ő�t�@�C���o�C�g���擾
		Long maxFileSize = 
			Long.parseLong(userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_MAX_FILESIZE));
		
		for (File uploaFile : model.getSagyoJokyoImageFiles()) {
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
	 * ���̑��t�@�C���A�b�v���[�h���̑Ó����`�F�b�N���s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @throws ValidationException �Ó����`�F�b�N�G���[������
	 */
	private void validateOtherFileUpload(TB033RequestFullEntryModel model) {
		// ���[�U���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �ő�t�@�C���o�C�g���擾
		Long maxFileSize = 
			Long.parseLong(userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_GYOSHA_FILESIZE_TO_MAX));
		
		for (File uploaFile : model.getOtherFiles()) {
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
	 * ��Ə󋵉摜���e�̕ҏW���s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @param uploadedList �˗��t�@�C���A�b�v���[�h���X�g
	 * @param isRestore �l�����t���O�itrue�F�l�𕜌�����j
	 */
	private void editSagyoJokyoImageFileContents(TB033RequestFullEntryModel model, List<RcpTFileUpload> uploadedList, boolean isRestore) {
		RcpTFileUpload[] sagyoJokyoImageFiles = 
			new RcpTFileUpload[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		String[] fileComments =
			new String[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		String[] uploadFileNms = 
			new String[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		BigDecimal[] fileIndexs =
			new BigDecimal[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		
		if (uploadedList == null || uploadedList.isEmpty()) {
			if (!isRestore) {
				// �G���[�łȂ������ꍇ�́A�t�@�C���R�����g��������
				model.setSagyoJokyoImageFileComments(fileComments);
			}
			// �t�@�C���֌W�͋󗓂��w��i�Z�L�����e�B�s����A�폜����邽�߁j
			model.setUploadedFiles(sagyoJokyoImageFiles);
			model.setSagyoJokyoImageFileUploadFileNms(uploadFileNms);
			for (int i = 0; i < TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT; i++) {
				fileIndexs[i] = new BigDecimal(i + 1);
			}
			model.setSagyoJokyoImageFileFileIndexes(fileIndexs);
			
			return;
		}

		// ��ʂɏ�ɂR���\�����邽�߁A�s��������̃C���X�^���X�Ŗ��߂�
		for (int i = 0; i < TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT; i++) {
			// ���݂̃t�@�C���C���f�b�N�X
			int currentIdx = i + 1;

			boolean isExist = false;
			for (RcpTFileUpload sagyoJokyoImageFile : uploadedList) {
				if (currentIdx == sagyoJokyoImageFile.getFileIndex().intValue()) {
					// �f�[�^������ꍇ�́A�z��Ɋi�[
					sagyoJokyoImageFiles[i] = sagyoJokyoImageFile;
					fileComments[i] = sagyoJokyoImageFile.getFileComment();
					uploadFileNms[i] = sagyoJokyoImageFile.getUploadFileNm();
					fileIndexs[i] = sagyoJokyoImageFile.getFileIndex();
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				// ���[�v�J�E���g���A�b�v���[�h�σt�@�C�������ȏぁ�f�[�^����
				sagyoJokyoImageFiles[i] = null;
				fileComments[i] = null;
				uploadFileNms[i] = null;
				fileIndexs[i] = new BigDecimal(i + 1);
			}
		}

		model.setUploadedFiles(sagyoJokyoImageFiles);
		if (!isRestore) {
			// �G���[�łȂ��ꍇ�́A�R�����g��ݒ�
			model.setSagyoJokyoImageFileComments(fileComments);
		}
		model.setSagyoJokyoImageFileUploadFileNms(uploadFileNms);
		model.setSagyoJokyoImageFileFileIndexes(fileIndexs);
	}
	
	/**
	 * ���̑��t�@�C�����e�̕ҏW���s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @param uploadedList �˗����̑��t�@�C���A�b�v���[�h���X�g
	 * @param isRestore �l�����t���O�itrue�F�l�𕜌�����j
	 */
	private void editOtherFileContents(TB033RequestFullEntryModel model, List<RcpTOtherFileUpload> uploadedList, boolean isRestore) {
		RcpTOtherFileUpload[] otherFiles = 
			new RcpTOtherFileUpload[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		String[] fileComments =
			new String[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		String[] uploadFileNms = 
			new String[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		BigDecimal[] fileIndexs =
			new BigDecimal[TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT];
		
		if (uploadedList == null || uploadedList.isEmpty()) {
			if (!isRestore) {
				// �G���[�łȂ������ꍇ�́A�t�@�C���R�����g��������
				model.setOtherFileComments(fileComments);
			}
			// �t�@�C���֌W�͋󗓂��w��i�Z�L�����e�B�s����A�폜����邽�߁j
			model.setUploadedOtherFiles(otherFiles);
			model.setOtherFileUploadFileNms(uploadFileNms);
			for (int i = 0; i < TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT; i++) {
				fileIndexs[i] = new BigDecimal(i + 1);
			}
			model.setOtherFileUploadFileFileIndexes(fileIndexs);
			
			return;
		}

		// ��ʂɏ�ɂR���\�����邽�߁A�s��������̃C���X�^���X�Ŗ��߂�
		for (int i = 0; i < TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT; i++) {
			// ���݂̃t�@�C���C���f�b�N�X
			int currentIdx = i + 1;

			boolean isExist = false;
			for (RcpTOtherFileUpload otherFile : uploadedList) {
				if (currentIdx == otherFile.getFileIndex().intValue()) {
					// �f�[�^������ꍇ�́A�z��Ɋi�[
					otherFiles[i] = otherFile;
					fileComments[i] = otherFile.getFileComment();
					uploadFileNms[i] = otherFile.getUploadFileNm();
					fileIndexs[i] = otherFile.getFileIndex();
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				// ���[�v�J�E���g���A�b�v���[�h�σt�@�C�������ȏぁ�f�[�^����
				otherFiles[i] = null;
				fileComments[i] = null;
				uploadFileNms[i] = null;
				fileIndexs[i] = new BigDecimal(i + 1);
			}
		}
		
		model.setUploadedOtherFiles(otherFiles);
		if (!isRestore) {
			// �G���[�łȂ��ꍇ�́A�R�����g��ݒ�
			model.setOtherFileComments(fileComments);
		}
		model.setOtherFileUploadFileNms(uploadFileNms);
		model.setOtherFileUploadFileFileIndexes(fileIndexs);
	}
	
	/**
	 * �\���p�̋Ǝ҉񓚃t�@�C���A�b�v���[�h��񃊃X�g���쐬���܂��B
	 *
	 * @param uploadedList �t�@�C���A�b�v���[�h��񃊃X�g
	 * @return �\���p�t�@�C���A�b�v���[�h��񃊃X�g
	 */
	private List<TbTFileUpload> createGyoshaAnswerFileUploadList(List<TbTFileUpload> uploadedList) {
		// ��ʕ\�����邽�߂ɐV�������X�g�𐶐�����
		List<TbTFileUpload> dispFileUploadList = new ArrayList<TbTFileUpload>();

		// ��ʕ\�����镪�������[�v
		for (int i = 0; i < TB033RequestFullEntryModel.IMAGE_FILE_MAX_COUNT; i++) {
			// �t�@�C���C���f�b�N�X
			int fileIdx = i + 1;
			
			// �t�@�C���f�[�^���݃t���O
			boolean dataExists = false;
			for (TbTFileUpload fileUpload : uploadedList) {
				if (fileIdx == fileUpload.getFileIndex().intValue()) {
					// �Y������t�@�C���C���f�b�N�X���������ꍇ
					dispFileUploadList.add(fileUpload);
					// �t�@�C���f�[�^���݃t���O��ON�ɂ���
					dataExists = true;
				}
			}
			
			if (!dataExists) {
				// �f�[�^�����݂��Ȃ������ꍇ�ɂ́A��̃C���X�^���X�ōs�𖄂߂�
				dispFileUploadList.add(new TbTFileUpload());
			}
		}
		
		return dispFileUploadList;
	}
	
	/**
	 * �t�@�C���̃A�b�v���[�h���������s���܂��B
	 *
	 * @param model �˗��o�^�o�^��ʃ��f��
	 */
	private void executeFileUpload(TB033RequestFullEntryModel model) {
		// ���[�U���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		List<String> uploadFileNameList = new ArrayList<String>();
		List<File> uploadFileList = new ArrayList<File>();

		String[] inputFileNms = model.getSagyoJokyoImageFileNmByArray();
		// ��ʂŕ\������Ă��錏�����A�o�^
		for (int i = 0; i < model.getSagyoJokyoImageFiles().length; i++) {
			if (StringUtils.isNotBlank(model.getSagyoJokyoImageFileUploadFileNms()[i])) {
				// �A�b�v���[�h�t�@�C�������������ꍇ�i���ɓo�^����Ă���̂ŁA�X�V�����j
				updateFileUploadInfo(model,
						userContext,
						model.getSagyoJokyoImageFileUploadFileNms()[i],
						model.getSagyoJokyoImageFileComments()[i],
						model.getSagyoJokyoImageFileFileIndexes()[i]);
				
			} else {
				// �A�b�v���[�h�t�@�C�������Ȃ��ꍇ�i�o�^����Ă��Ȃ��̂ŁA�o�^�����j
				if (model.getSagyoJokyoImageFiles()[i] != null && StringUtils.isNotBlank(inputFileNms[i])) {
					// �A�Ԃ��擾����
					String newFileNo = fileUploadDao.selectFileUploadNo();

					// �A�b�v���[�h�t�@�C�����쐬
					String uploadFileNm = FileUploadLogic.makeUploadFileNm(
							inputFileNms[i],
							model.getToiawaseNo() + "-" + model.getToiawaseRirekiNo().toString() + "-" + newFileNo);

					// �t�@�C���w�肪�������ꍇ�̂ݓo�^
					insertFileUploadInfo(model, userContext, uploadFileNm,
							inputFileNms[i], model.getSagyoJokyoImageFileComments()[i],
							model.getSagyoJokyoImageFileFileIndexes()[i]);
					
					uploadFileNameList.add(uploadFileNm);
					uploadFileList.add(model.getSagyoJokyoImageFiles()[i]);
				}
			}
		}

		if (!uploadFileList.isEmpty()) {
			// �t�@�C���o�^������Ă���΁A�t�@�C���̃A�b�v���[�h
			FileUploadLogic.uploadFileList(uploadFileList,
					uploadFileNameList, UPLOAD_PATH_TLCSS_SAGYO_JOKYO);
		}
	}
	
	/**
	 * ���̑��t�@�C���̃A�b�v���[�h���������s���܂��B
	 *
	 * @param model �˗��o�^�o�^��ʃ��f��
	 */
	private void executeOtherFileUpload(TB033RequestFullEntryModel model) {
		// ���[�U���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		List<String> uploadFileNameList = new ArrayList<String>();
		List<File> uploadFileList = new ArrayList<File>();

		String[] inputFileNms = model.getOtherFileNmByArray();
		// ��ʂŕ\������Ă��錏�����A�o�^
		for (int i = 0; i < model.getOtherFiles().length; i++) {
			if (StringUtils.isNotBlank(model.getOtherFileUploadFileNms()[i])) {
				// �A�b�v���[�h�t�@�C�������������ꍇ�i���ɓo�^����Ă���̂ŁA�X�V�����j
				updateOtherFileUploadInfo(model,
						userContext,
						model.getOtherFileUploadFileNms()[i],
						model.getOtherFileComments()[i],
						model.getOtherFileUploadFileFileIndexes()[i]);
			} else {
				// �A�b�v���[�h�t�@�C�������Ȃ��ꍇ�i�o�^����Ă��Ȃ��̂ŁA�o�^�����j
				if (model.getOtherFiles()[i] != null && StringUtils.isNotBlank(inputFileNms[i])) {
					// �A�Ԃ��擾����
					String newFileNo = fileUploadDao.selectFileUploadNo();

					// �A�b�v���[�h�t�@�C�����쐬
					String uploadFileNm = FileUploadLogic.makeUploadFileNm(
							inputFileNms[i],
							model.getToiawaseNo() + "-" + model.getToiawaseRirekiNo().toString() + "-" + newFileNo);

					// �t�@�C���w�肪�������ꍇ�̂ݓo�^
					insertOtherFileUploadInfo(model, userContext, uploadFileNm,
							inputFileNms[i], model.getOtherFileComments()[i],
							model.getOtherFileUploadFileFileIndexes()[i]);
					
					uploadFileNameList.add(uploadFileNm);
					uploadFileList.add(model.getOtherFiles()[i]);
				}
			}
		}

		if (!uploadFileList.isEmpty()) {
			// �t�@�C���o�^������Ă���΁A�t�@�C���̃A�b�v���[�h
			FileUploadLogic.uploadFileList(uploadFileList,
					uploadFileNameList, UPLOAD_PATH_IRAI_FILE);
		}
	}
	
	/**
	 * �₢���킹�����e�[�u���ւ̓o�^�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	private void insertToiawaseRireki(TB033RequestFullEntryModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �₢���킹�����m�n���s
		BigDecimal newToiawaseNo =
			toiawaseRirekiDao.selectNewRirekiNo(model.getToiawaseNo());
		
		model.setToiawaseRirekiNo(newToiawaseNo);
		
		// �₢���킹�����e�[�u���o�^�������{
		RcpTToiawaseRireki toiawaseRireki = new RcpTToiawaseRireki();
		toiawaseRireki.setToiawaseNo(model.getToiawaseNo());
		toiawaseRireki.setToiawaseRirekiNo(newToiawaseNo);
		
		if (model.isToiawaseHistoryAutoRegistExcecutable()) {
			// �������ɓo�^����`�F�b�N��ON�̏ꍇ
			toiawaseRireki.setUketsukeYmd(model.getSagyoJokyo().getSagyoKanryoYmd());
			toiawaseRireki.setUketsukeJikan(model.getSagyoJokyo().getSagyoKanryoJikan());
			toiawaseRireki.setTantoshaId(Constants.TORES_APL_ID);
			toiawaseRireki.setToiawaseNaiyo(model.getSagyoJokyo().getJisshiNaiyo());
			toiawaseRireki.setJokyoKbn(model.getHistoryAutoRegistJokyoKbn());
			toiawaseRireki.setToiawaseRirekiKokaiFlg(RcpTToiawaseRireki.TOIAWASE_RIREKI_KOKAI_FLG_MIKOKAI);
		} else {
			toiawaseRireki.setUketsukeYmd(DateUtil.toTimestamp(DateUtil.getSysDateString(), "yyyy/MM/dd"));
			toiawaseRireki.setUketsukeJikan(DateUtil.getSysDateString("HHmm"));
			toiawaseRireki.setTantoshaId(Constants.TORES_APL_ID);
			toiawaseRireki.setToiawaseNaiyo(model.getIrai().getIraiNaiyo());
			toiawaseRireki.setJokyoKbn(RcpTToiawaseRireki.JOKYO_KBN_GYOSHATEHAI);
			toiawaseRireki.setToiawaseRirekiKokaiFlg(model.getIrai().getIraiKokaiFlg());
		}
		if (StringUtils.isNotBlank(model.getToiawase().getKokyakuId())){
			toiawaseRireki.setHoukokuPrintFlg(RcpTToiawaseRireki.HOUKOKU_PRINT_FLG_ON);
		} else {
			toiawaseRireki.setHoukokuPrintFlg(RcpTToiawaseRireki.HOUKOKU_PRINT_FLG_OFF);
		}
		toiawaseRireki.setCreId(userContext.getLoginId());
		toiawaseRireki.setLastUpdId(userContext.getLoginId());
		toiawaseRireki.setTantoshaNm(model.getIrai().getTantoshaNm());
		toiawaseRireki.setCreKaishaId(userContext.getKaishaId());
		toiawaseRireki.setUpdKaishaId(userContext.getKaishaId());
		toiawaseRireki.setCreNm(userContext.getUserName());
		toiawaseRireki.setLastUpdNm(userContext.getUserName());
		toiawaseRireki.setRegistKbn(RcpTToiawaseRireki.REGIST_KBN_TORES);
		
		// �₢���킹�����e�[�u���o�^
		toiawaseRirekiDao.insert(toiawaseRireki);
		
		// �₢���킹���Ǐ��X�V
		toiawaseDao.updateBrowseStatusFlg(model.getToiawaseNo(), false);
	}
	
	/**
	 * �˗��t�@�C���A�b�v���[�h���̓o�^�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @param userContext ���O�C�����[�U���
	 * @param uploadFileNm �A�b�v���[�h�t�@�C����
	 * @param imageFileNm �摜�t�@�C����
	 * @param fileComment �t�@�C���R�����g
	 * @param fileIndex �t�@�C���C���f�b�N�X
	 * @return �o�^����
	 */
	private int insertFileUploadInfo(TB033RequestFullEntryModel model,
			TLCSSB2BUserContext userContext, String uploadFileNm, 
			String imageFileNm, String fileComment, BigDecimal fileIndex) {

		// �˗��t�@�C���A�b�v���[�h�e�[�u���o�^
		RcpTFileUpload fileUpload = new RcpTFileUpload();
		fileUpload.setToiawaseNo(model.getToiawaseNo());
		fileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		fileUpload.setUploadFileNm(uploadFileNm);
		fileUpload.setBaseFileNm(FilenameUtils.getName(new File(imageFileNm).getName()));
		fileUpload.setFileComment(fileComment);
		fileUpload.setCreKaishaId(userContext.getKaishaId());
		fileUpload.setUpdKaishaId(userContext.getKaishaId());
		fileUpload.setCreNm(userContext.getUserName());
		fileUpload.setLastUpdNm(userContext.getUserName());
		fileUpload.setRegistKbn(RcpTFileUpload.REGIST_KBN_TORES);
		fileUpload.setFileIndex(fileIndex);
		
		return fileUploadDao.insert(fileUpload);
	}
	
	/**
	 * �˗����̑��t�@�C���A�b�v���[�h���̓o�^�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @param userContext ���O�C�����[�U���
	 * @param uploadFileNm �A�b�v���[�h�t�@�C����
	 * @param imageFileNm �摜�t�@�C����
	 * @param fileComment �t�@�C���R�����g
	 * @param fileIndex �t�@�C���C���f�b�N�X
	 * @return �o�^����
	 */
	private int insertOtherFileUploadInfo(TB033RequestFullEntryModel model,
			TLCSSB2BUserContext userContext, String uploadFileNm, 
			String imageFileNm, String fileComment, BigDecimal fileIndex) {

		// �˗����̑��t�@�C���A�b�v���[�h�e�[�u���o�^
		RcpTOtherFileUpload otherFileUpload = new RcpTOtherFileUpload();
		otherFileUpload.setToiawaseNo(model.getToiawaseNo());
		otherFileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		otherFileUpload.setUploadFileNm(uploadFileNm);
		otherFileUpload.setBaseFileNm(FilenameUtils.getName(new File(imageFileNm).getName()));
		otherFileUpload.setFileComment(fileComment);
		otherFileUpload.setCreKaishaId(userContext.getKaishaId());
		otherFileUpload.setUpdKaishaId(userContext.getKaishaId());
		otherFileUpload.setCreNm(userContext.getUserName());
		otherFileUpload.setLastUpdNm(userContext.getUserName());
		otherFileUpload.setRegistKbn(RcpTFileUpload.REGIST_KBN_TORES);
		otherFileUpload.setFileIndex(fileIndex);
		
		return otherFileUploadDao.insert(otherFileUpload);
	}
	
	/**
	 * �˗��t�@�C���A�b�v���[�h���̍X�V�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @param userContext ���O�C�����[�U���
	 * @param uploadFileNm �A�b�v���[�h�t�@�C����
	 * @param fileComment �t�@�C���R�����g
	 * @param fileIndex �t�@�C���C���f�b�N�X
	 * @return �X�V����
	 */
	private int updateFileUploadInfo(TB033RequestFullEntryModel model,
			TLCSSB2BUserContext userContext, String uploadFileNm, String fileComment, BigDecimal fileIndex) {
		// �˗��t�@�C���A�b�v���[�h�e�[�u���X�V
		RcpTFileUpload fileUpload = new RcpTFileUpload();
		fileUpload.setToiawaseNo(model.getToiawaseNo());
		fileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		fileUpload.setUploadFileNm(uploadFileNm);
		fileUpload.setFileComment(fileComment);
		fileUpload.setUpdKaishaId(userContext.getKaishaId());
		fileUpload.setLastUpdNm(userContext.getUserName());
		fileUpload.setFileIndex(fileIndex);
		
		return fileUploadDao.updateForTores(fileUpload);
	}
	
	/**
	 * �˗����̑��t�@�C���A�b�v���[�h���̍X�V�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 * @param userContext ���O�C�����[�U���
	 * @param uploadFileNm �A�b�v���[�h�t�@�C����
	 * @param fileComment �t�@�C���R�����g
	 * @param fileIndex �t�@�C���C���f�b�N�X
	 * @return �X�V����
	 */
	private int updateOtherFileUploadInfo(TB033RequestFullEntryModel model,
			TLCSSB2BUserContext userContext, String uploadFileNm, String fileComment, BigDecimal fileIndex) {
		// �˗����̑��t�@�C���A�b�v���[�h�e�[�u���X�V
		RcpTOtherFileUpload otherFileUpload = new RcpTOtherFileUpload();
		otherFileUpload.setToiawaseNo(model.getToiawaseNo());
		otherFileUpload.setToiawaseRirekiNo(model.getToiawaseRirekiNo());
		otherFileUpload.setUploadFileNm(uploadFileNm);
		otherFileUpload.setFileComment(fileComment);
		otherFileUpload.setUpdKaishaId(userContext.getKaishaId());
		otherFileUpload.setLastUpdNm(userContext.getUserName());
		otherFileUpload.setFileIndex(fileIndex);
		
		return otherFileUploadDao.updateForTores(otherFileUpload);
	}
	
	/**
	 * �₢���킹�e�[�u���̍X�V���X�V�������s���܂��B
	 * 
	 * @param model �˗��o�^��ʃ��f��
	 */
	private void updateToiawaseUpdDt(TB033RequestFullEntryModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		RcpTToiawase toiawase = new RcpTToiawase();
		
		toiawase.setToiawaseNo(model.getToiawaseNo());
		toiawase.setLastUpdId(userContext.getLoginId());
		toiawase.setUpdDt(model.getToiawaseUpdDt());
		
		// �₢���킹�e�[�u���̍X�V���X�V
		if (toiawaseDao.updateUpdDt(toiawase) == 0) {
			// �X�V�������O���̏ꍇ�́A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
	}
	
	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model �o�^�I�u�W�F�N�g
	 * @return ��������
	 */
	private String createKensakuJoken(Object entity) {
		NullExclusionToStringBuilder entryEntity = new NullExclusionToStringBuilder(
				entity,
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);


		return entryEntity.toString();
	}
	
	/**
	 * ��ƈ˗�URL�𐶐����܂��B
	 *
	 * @param model �˗��o�^��ʃ��f��
	 * @param systemUrl �V�X�e��URL
	 * @return ��ƈ˗�URL
	 */
	private String createSagyoIraiUrl(TB033RequestFullEntryModel model, String systemUrl) {
		return String.format(systemUrl
				+ "externalLogin.action?actionURL=/requestEntryInit&toiawaseNo=%s"
				+ "&toiawaseRirekiNo=%s&dispKbn=3",
				model.getToiawaseNo(), model.getToiawaseRirekiNo().toString());
	}
}
