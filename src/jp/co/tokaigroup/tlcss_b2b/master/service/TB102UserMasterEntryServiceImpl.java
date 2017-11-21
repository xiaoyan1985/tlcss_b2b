package jp.co.tokaigroup.tlcss_b2b.master.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.MailTemplate;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFKanriDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKanrenDao;
import jp.co.tokaigroup.reception.dao.TbMGrpKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbMRefKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMUserDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dto.RC02BKokyakuNodeDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKanri;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbMGrpKokyaku;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.mail.VelocityEmail;
import jp.co.tokaigroup.si.fw.mail.VelocityWrapper;
import jp.co.tokaigroup.si.fw.message.MessageBean;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.EncryptionUtil;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.si.fw.util.PasswordGenerator;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB102UserMasterEntryModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ���[�U�[�}�X�^�o�^�T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB102UserMasterEntryServiceImpl extends TLCSSB2BBaseService
		implements TB102UserMasterEntryService {

	/** ���[���敪 1:���[�U�[�o�^�������[�� */
	private static final int MAIL_KBN_ENTRY = 1;
	/** ���[���敪 2:�p�X���[�h�Ĕ��s���[�� */
	private static final int MAIL_KBN_PASSWORD_REISSUE = 2;

	// ���[���e���v���[�g���ځ@�L�[
	/** ���[���e���v���[�g���� �L�[ ���[�U�[�� */
	private static final String MAIL_KEY_USER_NAME = "userName";
	/** ���[���e���v���[�g���� �L�[ �V�X�e���� */
	private static final String MAIL_KEY_SYSTEM_NAME = "systemName";
	/** ���[���e���v���[�g���� �L�[ �V�X�e����URL */
	private static final String MAIL_KEY_SYSTEM_URL = "systemUrl";
	/** ���[���e���v���[�g���� �L�[ �V�X�e���₢���킹��d�b�ԍ� */
	private static final String MAIL_KEY_CONTACT_INFORMAITION = "contactInformation";
	/** ���[���e���v���[�g���� �L�[ �ԐM���[���A�h���X */
	private static final String MAIL_KEY_RETURN_MAIL_ADDRESS = "returnMailAddress";
	/** ���[���e���v���[�g���� �L�[ ���s�����b��p�X���[�h */
	private static final String MAIL_KEY_TEMP_PASSWORD = "tempPassword";
	/** ���[���e���v���[�g���� �L�[ �p�X���[�h������ */
	private static final String MAIL_KEY_PASSWD_KIGEN_DT = "passwdKigenDt";
	/** ���[���e���v���[�g���� �L�[ �j�� */
	private static final String MAIL_KEY_DAY_OF_WEEK = "dayOfWeek";
	/** ���[���e���v���[�g���� �L�[ �T�|�[�g�Z���^�[��t���� */
	private static final String MAIL_KEY_SUPPORT_RECEPTION_TIME = "supportReceptionTime";

	/** �O���T�C�g���[�U�[�}�X�^DAO */
	@Autowired
	private TbMUserDao userDao;

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** ���Z�v�V�����ڋq�t���Ǘ���Џ��}�X�^DAO */
	@Autowired
	private RcpMKokyakuFKanriDao kokyakuFKanriDao;

	/** ���Z�v�V�����Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** �O���[�v�ڋq�}�X�^DAO */
	@Autowired
	private TbMGrpKokyakuDao grpKokyakuDao;

	/** �ڋq�֘A�}�X�^DAO */
	@Autowired
	private RcpMKokyakuKanrenDao kokyakuKanrenDao;

	/** �Q�ƌڋq�}�X�^DAO */
	@Autowired
	private TbMRefKokyakuDao refKokyakuDao;
	
	/** ��Ѓ}�X�^DAO */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/**
	 * �����\���������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 * @return ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public TB102UserMasterEntryModel getInitInfo(TB102UserMasterEntryModel model) {
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);

		// �������X�g�̎擾
		List<RcpMComCd> roleList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);

		model.setRoleList(roleList);

		return model;
	}

	/**
	 * ���[�U�[���擾�������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 * @return ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public TB102UserMasterEntryModel getUserInfo(TB102UserMasterEntryModel model) {
		// �����\�������Ăяo��
		model = this.getInitInfo(model);

		// ���[�U�[���̎擾
		TbMUser user = userDao.selectByPrimaryKey(model.getSeqNo());
		if (user == null) {
			// ���[�U�[��񂪑��݂��Ȃ��ꍇ�́A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		if (user.isInhouse()) {
			model.setTokaiKokyakuList(new ArrayList<RcpMKokyaku>());
			// �������uTOKAI�Ǘ��ҁv�܂��́A�uTOKAI��ʁv�̏ꍇ
			List<TbMGrpKokyaku> grpKokyakuList = grpKokyakuDao.selectBy(user.getSeqNo(), null);
			for (TbMGrpKokyaku grpKokyaku : grpKokyakuList) {
				model.getTokaiKokyakuList().add(kokyakuDao.selectByPrimaryKey(grpKokyaku.getRefKokyakuId()));
			}
		} else if (user.isRealEstate()) {
			// �������u�Ǘ���Ёv�̏ꍇ
			// �Q�ƌڋq�h�c���̌ڋq���擾
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(user.getKokyakuId());
			if (kokyaku == null) {
				throw new ApplicationException("�ڋq�}�X�^��񂪑��݂��܂���B");
			}
			user.setKokyakuNm(kokyaku.getKanjiNm());

			// �ڋq�K�wDTO���X�g�ݒ�
			model.setKokyakuNodeList(getRefKokyaku(user.getKokyakuId(), true));

			// �Q�ƌڋq���擾
			List<TbMRefKokyaku> refKokuakuList = refKokyakuDao.selectBy(user.getKokyakuId(), null);
			List<MessageBean> duplicateContentList = new ArrayList<MessageBean>();

			// �Q�ƌڋq���̌������A�J��Ԃ�
			for (TbMRefKokyaku refKokyaku : refKokuakuList) {
				// �ڋq�t���Ǘ���Џ��̎擾
				RcpMKokyakuFKanri fKanri =
					kokyakuFKanriDao.selectByPrimaryKey(refKokyaku.getKokyakuId());
				if (fKanri != null) {
					// ���O�C���h�c�Ɠ����l�����`�F�b�N
					if (user.getLoginId().equals(fKanri.getTaioMailAddress1())) {
						// �Ή��񍐃��[���A�h���X�P
						duplicateContentList.add(new MessageBean(
								"MSG0016", "�ڋq�t�����y�s���Y�E�Ǘ���Џ��z",
								"�Ή��񍐃��[���A�h���X�P"));
					}
					if (user.getLoginId().equals(fKanri.getTaioMailAddress2())) {
						// �Ή��񍐃��[���A�h���X�Q
						duplicateContentList.add(new MessageBean(
								"MSG0016", "�ڋq�t�����y�s���Y�E�Ǘ���Џ��z",
								"�Ή��񍐃��[���A�h���X�Q"));
					}
					if (user.getLoginId().equals(fKanri.getTaioMailAddress3())) {
						// �Ή��񍐃��[���A�h���X�R
						duplicateContentList.add(new MessageBean(
								"MSG0016", "�ڋq�t�����y�s���Y�E�Ǘ���Џ��z",
								"�Ή��񍐃��[���A�h���X�R"));
					}
				}
			}
			model.setDuplicateContetErrorMessageList(duplicateContentList);

		} else if (user.isConstractor()) {
			// �������˗��Ǝ҂̏ꍇ�́A�Ǝҏ��̎擾
			RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(user.getGyoshaCd());
			if (gyosha == null) {
				throw new ApplicationException("�Ǝ҃}�X�^��񂪑��݂��܂���B");
			}

			user.setGyoshaNm(gyosha.getGyoshaNm());

			// �d�����ڃ��X�g�̍쐬�i�`�F�b�N�����˂�j
			model.setDuplicateContetErrorMessageList(createDuplicateContentList(user));

		} else if (user.isOutsourcerSv() || user.isOutsourcerOp()) {
			// �������ϑ����SV�������͈ϑ����OP�̏ꍇ�́A��Џ��̎擾
			TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(user.getKaishaId());
			if (kaisha == null) {
				throw new ApplicationException("��Ѓ}�X�^��񂪑��݂��܂���B");
			}

			user.setKaishaNm(kaisha.getKaishaNm());
		}

		// �a���ϊ�����
		// ���[�U�[�}�X�^�����p���X�g�̍쐬
		List<String> loginIdList = new ArrayList<String>();
		loginIdList.add(user.getCreId());
		loginIdList.add(user.getLastUpdId());

		// �a���ϊ�Map�擾
		Map<String, String> userMap = userDao.selectByListAsMap(loginIdList);

		// �o�^�҂h�c
		user.setCreUserNm(userMap.containsKey(user.getCreId()) ? userMap.get(user.getCreId()) : user.getCreId());
		// �ŏI�X�V�҂h�c
		user.setLastUpdUserNm(userMap.containsKey(user.getLastUpdId()) ? userMap.get(user.getLastUpdId()) : user.getLastUpdId());

		model.setUser(user);

		return model;
	}

	/**
	 * ���[�U�[���o�^�������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public void insertUserInfo(TB102UserMasterEntryModel model) {
		// �o�^�`�F�b�N����
		validateEntryUserInfo(model);

		// ���[�U�[�}�X�^�ւ̓o�^�l����
		TbMUser user = createEntryUserInfo(model);

		// ���[�U�[�}�X�^�ւ̓o�^����
		userDao.insert(user);

		model.setSeqNo(user.getSeqNo());
		model.setUser(user);

		if (model.getUser().isInhouse()) {
			// �������uTOKAI�Ǘ��ҁv�܂��́A�uTOKAI��ʁv�̏ꍇ
			insertGrpKokyaku(model);
		} else if (model.getUser().isRealEstate()) {
			// �Q�ƌڋq�}�X�^���̍폜����
			refKokyakuDao.deleteBy(user.getKokyakuId(), null);
			// �������u�Ǘ���Ёv�̏ꍇ
			insertRefKokyaku(model);
		}

		// �A�N�Z�X���O�ւ̓o�^����
		tbAccesslogDao.insert(TB102UserMasterEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_INSERT, createKensakuJoken(model));

		// �b��p�X���[�h�����[���ʒm���邪�`�F�b�NON�̏ꍇ�A���[�����M���s��
		if (model.isMailSend()) {
			// ���[�U�[�o�^�������[���̑��M
			sendMail(model, MAIL_KBN_ENTRY);
		}
	}

	/**
	 * ���[�U�[���X�V�������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public void updateUserInfo(TB102UserMasterEntryModel model) {
		// �o�^�`�F�b�N����
		validateEntryUserInfo(model);

		// ���[�U�[�}�X�^�ւ̍X�V����
		TbMUser user = model.getUser();
		user.setSeqNo(model.getSeqNo());
		// �������uTOKAI�Ǘ��ҁv�܂��́A�uTOKAI��ʁv�̏ꍇ
		if (model.getUser().isInhouse()) {
			// ���ID��TOKAI�Őݒ�
			user.setKaishaId(
				((TLCSSB2BUserContext)getUserContext()).
					getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_TOKAI_KAISHA_ID));
		}

		userDao.update(user);

		// �Q�ƌڋq�}�X�^���̍폜����
		refKokyakuDao.deleteBy(model.getUser().getKokyakuId(), null);
		// �O���[�v�ڋq�}�X�^���̍폜����
		grpKokyakuDao.deleteBy(model.getSeqNo(), null);

		if (model.getUser().isInhouse()) {
			// �������uTOKAI�Ǘ��ҁv�܂��́A�uTOKAI��ʁv�̏ꍇ
			// �O���[�v�ڋq�}�X�^���̓o�^����
			insertGrpKokyaku(model);
		} else if (model.getUser().isRealEstate()) {
			// �������u�Ǘ���Ёv�̏ꍇ
			// �Q�ƌڋq�}�X�^���̓o�^����
			insertRefKokyaku(model);
		}

		// �A�N�Z�X���O�ւ̓o�^����
		tbAccesslogDao.insert(TB102UserMasterEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_UPDATE, createKensakuJoken(model));
	}

	/**
	 * �p�X���[�h�Ĕ��s�������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public void reissuePasswordInfo(TB102UserMasterEntryModel model) {
		// ���[�U�[�}�X�^�ւ̍X�V�l�𐶐�
		TbMUser user = createReissuePasswordInfo(model);

		// ���[�U�[�}�X�^�ւ̍X�V����
		userDao.updateForTempPasswordChange(user);

		// �A�N�Z�X���O�ւ̓o�^����
		tbAccesslogDao.insert(TB102UserMasterEntryModel.GAMEN_NM,
				TB102UserMasterEntryModel.BUTTON_NM_REISSUE, createKensakuJoken(model));

		// �b��p�X���[�h�����[���ʒm���邪�`�F�b�NON�̏ꍇ�A���[�����M���s��
		if (model.isMailSend()) {
			// �p�X���[�h�Ĕ��s���[���̑��M
			sendMail(model, MAIL_KBN_PASSWORD_REISSUE);
		}
	}

	/**
	 * �Q�ƌڋq��ݒ肵�܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	public TB102UserMasterEntryModel setRefKokyaku(TB102UserMasterEntryModel model) {
		// �������擾����
		getInitInfo(model);

		// �Q�ƌڋq�ݒ�
		model.setKokyakuNodeList(getRefKokyaku(model.getUser().getKokyakuId(), false));

		// ��ʕ\���p�̌ڋq�h�c���X�g�̓��e��ݒ肵�܂�
		String[] tokaiKokyakuListTexts = model.getTokaiKokyakuListTexts().replaceAll(" ","").split(",");
		if (tokaiKokyakuListTexts != null && !"".equals(tokaiKokyakuListTexts[0])) {
			List<RcpMKokyaku> kokyakuList = new ArrayList<RcpMKokyaku>();
			for (String tokaiKokyakuListText:tokaiKokyakuListTexts) {
				String kokyakuID = tokaiKokyakuListText.substring(0,10);
				kokyakuList.add(kokyakuDao.selectByPrimaryKey(kokyakuID));
			}
			model.setTokaiKokyakuList(kokyakuList);
		}

		return model;
	}

	/**
	 * ���[�U�̃��O�C���h�c�ɑ΂���A�d�����ڃ��X�g�𐶐����܂��B
	 *
	 * @param user ���[�U�[���
	 * @return �d�����ڃ��X�g
	 */
	private List<MessageBean> createDuplicateContentList(TbMUser user) {
		List<MessageBean> duplicateContentList = new ArrayList<MessageBean>();
		if (StringUtils.isNotBlank(user.getGyoshaCd())) {
			// �Ǝ҃R�[�h��NULL�ȊO�̏ꍇ�́A�Ǝ҃}�X�^���̎擾���`�F�b�N
			RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(user.getGyoshaCd());

			if (gyosha != null &&
				RcpMGyosha.RCP_M_GYOSHA_IRAI_MAIL_ATESAKI_FLG_TRUE.equals(
						gyosha.getIraiMailAtesakiFlg())) {
				// ��ƈ˗����[�����t�L�����u1:�������t����v�̏ꍇ�̂݁A�`�F�b�N
				if (RcpMGyosha.RCP_M_GYOSHA_IRAI_MAIL_ATESAKI_KBN_SAME.equals(
						gyosha.getIraiMailAtesakiKbn())) {
					// ��ƈ˗����[������敪���u1:��ƗpTEL�Ɠ����v�ꍇ�A��Ɨp���[���A�h���X���`�F�b�N
					if (user.getLoginId().equals(gyosha.getSagyoMailAddress())) {
						// ��Ɨp���[���A�h���X
						duplicateContentList.add(new MessageBean(
								"MSG0016", "��ƈ˗����[��",
								"��Ɨp���[���A�h���X"));
					}
				} else if (RcpMGyosha.RCP_M_GYOSHA_IRAI_MAIL_ATESAKI_KBN_DIFF.equals(
						gyosha.getIraiMailAtesakiKbn())) {
					// ��ƈ˗����[������敪���u2:�قȂ�v�ꍇ�A��ƈ˗����[�����惁�[���A�h���X���`�F�b�N
					if (user.getLoginId().equals(gyosha.getIraiMailAtesakiAddress())) {
						// ��ƈ˗����[�����惁�[���A�h���X
						duplicateContentList.add(new MessageBean(
								"MSG0016", "��ƈ˗����[��",
								"��ƈ˗����[�����惁�[���A�h���X"));
					}
				}
			}
		}

		return duplicateContentList;
	}

	/**
	 * ���[�U�[���̓o�^�`�F�b�N�������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 */
	private void validateEntryUserInfo(TB102UserMasterEntryModel model) {
		if (model.isInsert()) {
			// �A�N�V�����^�C�v���V�K�o�^�̏ꍇ�̂ݎ��{
			// ���O�C���h�c�̏d���`�F�b�N
			TbMUser user = userDao.selectByLoginId(model.getUser().getLoginId());
			if (user != null) {
				throw new ValidationException(new ValidationPack().addError("MSG0014"));
			}
		}
	}

	/**
	 * �o�^���郆�[�U�[�}�X�^���𐶐����܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 * @return ���[�U�[�}�X�^���
	 */
	private TbMUser createEntryUserInfo(TB102UserMasterEntryModel model) {
		// �A�Ԃ̎擾
		BigDecimal newSeqNo = userDao.nextVal();

		// �b��p�X���[�h���s����
		String newTempPassword = PasswordGenerator.generate(TbMUser.TEMP_PASSWORD_LENGTH);

		// �b��p�X���[�h�̃n�b�V����
		String hashTempPassword = EncryptionUtil.encrypt(newTempPassword);

		// �p�X���[�h������
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// �b��p�X���[�h�L���������擾
		int tempPasswordValidDate = userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_TEMP_PASSWORD_VALID_DATE);

		Calendar calendar = Calendar.getInstance();
		// �V�X�e�����t�{�b��p�X���[�h�L������
		calendar.add(Calendar.DATE, tempPasswordValidDate);

		TbMUser user = model.getUser();

		// �o�^����l�̃Z�b�g
		user.setSeqNo(newSeqNo);
		user.setPasswd(hashTempPassword);
		user.setTmpPasswd(newTempPassword);
		user.setPasswdUpdDt(DateUtil.getSysDateTime());
		user.setPasswdKigenDt(DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.toSqlTimestamp(calendar.getTime()), "yyyy/MM/dd"), "yyyy/MM/dd"));
		user.setTmpPasswdFlg(TbMUser.TMP_PASSWD_FLG_TEMP_PASSWORD);
		
		// �������uTOKAI�Ǘ��ҁv�܂��́A�uTOKAI��ʁv�̏ꍇ
		if (model.getUser().isInhouse()) {
			// ���ID��TOKAI�Őݒ�
			user.setKaishaId(
				userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_TOKAI_KAISHA_ID));
		}

		return user;
	}

	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 * @return �A�N�Z�X���O�ɓo�^���錟������
	 */
	private String createKensakuJoken(TB102UserMasterEntryModel model) {
		NullExclusionToStringBuilder entryContents =
			new NullExclusionToStringBuilder(
				model.getUser(),
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// ���O���鍀��
		entryContents.setExcludeFieldNames(Constants.EXCLUDE_FIELD_NAMES);

		return entryContents.toString();
	}

	/**
	 * �p�X���[�h�Ĕ��s�����郆�[�U�[���𐶐����܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 * @return ���[�U�[�}�X�^���
	 */
	private TbMUser createReissuePasswordInfo(TB102UserMasterEntryModel model) {
		// �b��p�X���[�h���s����
		String newTempPassword = PasswordGenerator.generate(TbMUser.TEMP_PASSWORD_LENGTH);

		// �b��p�X���[�h�̃n�b�V����
		String hashTempPassword = EncryptionUtil.encrypt(newTempPassword);

		TbMUser user = model.getUser();

		// �o�^����l�̃Z�b�g
		user.setSeqNo(model.getSeqNo());
		user.setPasswd(hashTempPassword);
		user.setTmpPasswd(newTempPassword);

		return user;
	}

	/**
	 * ���[�����M���s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 * @param mailKbn ���[���敪(1:���[�U�[�o�^�������[���A2:�p�X���[�h�Ĕ��s���[��)
	 */
	private void sendMail(TB102UserMasterEntryModel model, int mailKbn) {
		try {
			// ��ʂœ��͂������[�U�[���
			TbMUser user = model.getUser();
			// �p�X���[�h�Ĕ��s���[���̏ꍇ�A
			// ���[�U�[���Ȃǂ͍X�V����Ȃ����߁A�c�a�ɓo�^����Ă��郆�[�U�[�����g�p����
			TbMUser dbUser = (mailKbn == MAIL_KBN_PASSWORD_REISSUE) ?
					userDao.selectByPrimaryKey(model.getSeqNo()) : null;

			// �e���v���[�g�t�@�C����
			String templateFileNm = (mailKbn == MAIL_KBN_PASSWORD_REISSUE) ?
					MailTemplate.PASSWORD_REISSUE_MAIL_FILE_NAME :
					MailTemplate.USER_ENTRY_MAIL_FILE_NAME;

			// �u��������Map����
			VelocityWrapper wrapper = new VelocityWrapper(
					templateFileNm);

			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// �s���Y�E�Ǘ���Ж��A�ƎҖ��A��Ж��p�G���e�B�e�B�擾
			RcpMKokyaku kokyakuEntity = new RcpMKokyaku();
			RcpMGyosha gyoshaEntitiy = new RcpMGyosha();
			TbMKaisha kaishaEntity = new TbMKaisha();
			if (StringUtils.isNotBlank(user.getKokyakuId())) {
				kokyakuEntity = kokyakuDao.selectByPrimaryKey(user.getKokyakuId());
			}
			if (StringUtils.isNotBlank(user.getGyoshaCd())) {
				gyoshaEntitiy = gyoshaDao.selectByPrimaryKey(user.getGyoshaCd());
			}
			if (StringUtils.isNotBlank(user.getKaishaId())) {
				kaishaEntity = kaishaDao.selectByPrimaryKey(user.getKaishaId());
			}

			// ���[�U�[��
			// �p�X���[�h�Ĕ��s���[���̏ꍇ�A�c�a�ɓo�^����Ă��郆�[�U�[����\��
			String userNm = (mailKbn == MAIL_KBN_PASSWORD_REISSUE) ?
					dbUser.getUserNm() : user.getUserNm();
			if (StringUtils.isNotBlank(user.getKokyakuId())) {
				// �ڋq�h�c��null�ȊO�̏ꍇ�A���[�U�[���ɕs���Y�E�Ǘ���Ж���ݒ�
				userNm = kokyakuEntity.getKanjiNm();
			}
			if (StringUtils.isNotBlank(user.getGyoshaCd())) {
				// �Ǝ҃R�[�h��null�ȊO�̏ꍇ�A���[�U�[���ɋƎҖ���ݒ�
				userNm = gyoshaEntitiy.getGyoshaNm();
			}
			if (StringUtils.isNotBlank(user.getKaishaId())) {
				// ���ID��null�ȊO�̏ꍇ�A���[�U�[���ɉ�Ж���ݒ�
				userNm = kaishaEntity.getKaishaNm();
			}
			// �V�X�e����
			String systemName = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_NAME);
			// �V�X�e����URL
			String systemUrl = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL);
			// �V�X�e���₢���킹��d�b�ԍ�
			String systemTelNo = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_TOIAWASE_TEL_NO);
			// �ԐM���[���A�h���X
			String returnMailAddress = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_RETURN_MAIL_ADDRESS);
			// �T�|�[�g�Z���^�[��t����
			String supportReceptionTime = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SUPPORT_RECEPTION_TIME);

			// ���[������
			String subject = (mailKbn == MAIL_KBN_PASSWORD_REISSUE) ?
					userContext.getSystgemContstantAsString(
							RcpMSystem.RCP_M_SYSTEM_B2B_SUBJECT_PASSWORD_REISUUE) :
					userContext.getSystgemContstantAsString(
							RcpMSystem.RCP_M_SYSTEM_B2B_SUBJECT_USER_ENTRY_COMPLATE);

			// �u��������
			// ���[�U�[��
			wrapper.put(MAIL_KEY_USER_NAME, userNm);
			// �V�X�e����
			wrapper.put(MAIL_KEY_SYSTEM_NAME, systemName);
			// ���s�����b��p�X���[�h
			wrapper.put(MAIL_KEY_TEMP_PASSWORD, user.getTmpPasswd());
			// �V�X�e����URL
			wrapper.put(MAIL_KEY_SYSTEM_URL, systemUrl);
			// �V�X�e���₢���킹��d�b�ԍ�
			wrapper.put(MAIL_KEY_CONTACT_INFORMAITION, systemTelNo);
			// �ԐM���[���A�h���X
			wrapper.put(MAIL_KEY_RETURN_MAIL_ADDRESS, returnMailAddress);
			// �T�|�[�g�Z���^�[��t����
			wrapper.put(MAIL_KEY_SUPPORT_RECEPTION_TIME, supportReceptionTime);

			// �p�X���[�h�Ĕ��s�̏ꍇ�́A�c�a�̃p�X���[�h���������g�p
			Timestamp passwdKigenDt = (mailKbn == MAIL_KBN_PASSWORD_REISSUE) ?
					dbUser.getPasswdKigenDt() : user.getPasswdKigenDt();
			// �p�X���[�h������
			wrapper.put(MAIL_KEY_PASSWD_KIGEN_DT,
					DateUtil.formatTimestamp(passwdKigenDt, "yyyy/MM/dd"));
			// �p�X���[�h�������̗j��
			wrapper.put(MAIL_KEY_DAY_OF_WEEK,
					DateUtil.getDayOfWeek(passwdKigenDt));

			VelocityEmail email = new VelocityEmail();
			// ���o���A�h���X
			email.setFrom(userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ADDRESS));
			// �F��
			email.setAuthenticator(new DefaultAuthenticator(
					userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ACCOUNT),
					userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_PASSWORD)));
			// ����
			email.addTo(user.getLoginId());
			// ����BCC
			String bcc = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_BCC_ADDRESS);
			if (StringUtils.isNotBlank(bcc)) {
				email.addBcc(StringUtils.split(bcc, ","));
			}
			// �ԐM���[���A�h���X
			email.addReplyTo(returnMailAddress);
			// ����
			email.setSubject(subject);
			// �u��������Map�̐ݒ�
			email.setVelocityWrapper(wrapper);

			// ���[�����M
			email.send();
		} catch (EmailException e) {
			// ���[�����M�Ɏ��s�����ꍇ
			throw new ApplicationException("���[�����M�Ɏ��s���܂����B", e);
		} catch (Exception e) {
			// ���̑���O
			throw new ApplicationException(e);
		}
	}

	/**
	 * �O���[�v�ڋq�}�X�^�i�s�n�j�`�h�p�j�̓o�^���s���܂��B
	 *
	 * @param model
	 */
	private void insertGrpKokyaku(TB102UserMasterEntryModel model) {
		// �o�^����
		String[] tokaiKokyakuListTexts = model.getTokaiKokyakuListTexts().replaceAll(" ","").split(",");
		BigDecimal hyojiJun = BigDecimal.ZERO;
		for (String tokaiKokyakuListText:tokaiKokyakuListTexts) {
			TbMGrpKokyaku grpKokyaku = new TbMGrpKokyaku();
			hyojiJun = hyojiJun.add(BigDecimal.ONE);
			// �o�^����l�̃Z�b�g
			grpKokyaku.setSeqNo(model.getSeqNo());
			grpKokyaku.setRefKokyakuId(tokaiKokyakuListText.substring(0,10));
			grpKokyaku.setHyojiJun(hyojiJun);
			// �o�^����
			grpKokyakuDao.insert(grpKokyaku);
		}
		return;
	}

	/**
	 * �Q�ƌڋq�}�X�^�̓o�^���s���܂��B
	 *
	 * @param model
	 */
	private void insertRefKokyaku(TB102UserMasterEntryModel model) {
		List<RC02BKokyakuNodeDto> kokyakuNodeList = model.getKokyakuNodeList();
		BigDecimal hyojiJun = BigDecimal.ZERO;
		// �o�^����
		for(int i=0; i < kokyakuNodeList.size(); i++) {
			RC02BKokyakuNodeDto kokyakuNode = kokyakuNodeList.get(i);
			// �o�^�t���O�u1:�o�^�\�v�̏ꍇ�͓o�^���s
			// ���o�^�t���O�͉�ʑ���Őݒ�i��ʂ̊������ڂ��o�^�Ώہj
			if (StringUtils.isNotBlank(kokyakuNode.getEntryFlg())
					&& RC02BKokyakuNodeDto.ENTRY_OK.equals(kokyakuNode.getEntryFlg())) {
				TbMRefKokyaku refKokyaku = new TbMRefKokyaku();
				hyojiJun = hyojiJun.add(BigDecimal.ONE);
				// �o�^����l�̃Z�b�g
				refKokyaku.setRefKokyakuId(model.getUser().getKokyakuId());
				refKokyaku.setKokyakuId(kokyakuNode.getKokyakuId());
				refKokyaku.setKaisoLevel(kokyakuNode.getKaisoLevel());
				refKokyaku.setLowerDspFlg(StringUtils.isNotBlank(kokyakuNode.getHyojiFlg()) ? kokyakuNode.getHyojiFlg() : RC02BKokyakuNodeDto.HYOJI_OFF);
				refKokyaku.setLeafFlg(kokyakuNode.getIsLeaf());
				refKokyaku.setHyojiJun(hyojiJun);
				// �o�^���s
				refKokyakuDao.insert(refKokyaku);
			}
		}

		return;
	}

	/**
	 * �ڋq�K�wDTO���X�g���擾���܂��B
	 *
	 * @param rootKokyakuId	�Q�ƌڋqID
	 * @param isUpdateInit	�X�V�����\���t���O
	 *
	 * @return List<RC02BKokyakuNodeDto> �ڋq�K�wDTO���X�g
	 */
	private List<RC02BKokyakuNodeDto> getRefKokyaku(String rootKokyakuId, boolean isUpdateInit) {

		// �ڋq�K�wDTO���X�g�擾
		List<RC02BKokyakuNodeDto> kokyakuNodeList =
			kokyakuKanrenDao.selectNodeList(rootKokyakuId, RcpMKokyaku.KOKYAKU_KBN_FUDOSAN);
		// �Q�ƌڋq���擾
		List<TbMRefKokyaku> refKokuakuList = refKokyakuDao.selectBy(rootKokyakuId, null);
		boolean newDataFlg = (refKokuakuList == null || refKokuakuList.isEmpty());

		for (RC02BKokyakuNodeDto kokyakuNode : kokyakuNodeList) {
			// �\���t���O�����ݒ�i�Q�ƌڋq��񂪑��݂��Ȃ��F�u1:�\���v�A���݂���F�u0:��\���v�i��ŕύX����j�j
			kokyakuNode.setHyojiFlg((newDataFlg) ? RC02BKokyakuNodeDto.HYOJI_ON : RC02BKokyakuNodeDto.HYOJI_OFF);
			// �w�i�F�ݒ�i�X�V�����\�����̂݁A�D�F�ݒ��true�Őݒ�j
			kokyakuNode.setDisplayGray(isUpdateInit);
			for (TbMRefKokyaku refKokuaku : refKokuakuList) {
				if (kokyakuNode.getKokyakuId().toString().equals(refKokuaku.getKokyakuId().toString())){
					// �ڋq�K�wDTO�̌ڋqID���Q�ƌڋq���ɑ��݂���ꍇ
					kokyakuNode.setDisplayGray(false);
					if(RC02BKokyakuNodeDto.HYOJI_ON.equals(refKokuaku.getLowerDspFlg())) {
						// �\���t���O�ݒ�
						// �ڋq�K�wDTO�̌ڋqID���Q�ƌڋq���ɑ��݂��A���Q�Ə�񂪁u�\��ON�v�̏ꍇ�A�u1:�\���v�ݒ�
						kokyakuNode.setHyojiFlg(RC02BKokyakuNodeDto.HYOJI_ON);
					}
				}
			}
		}

		return kokyakuNodeList;
	}
}
