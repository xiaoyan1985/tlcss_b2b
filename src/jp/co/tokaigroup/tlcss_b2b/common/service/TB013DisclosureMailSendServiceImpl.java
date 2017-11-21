package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.tokaigroup.reception.common.MailTemplate;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFKanriDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKLifeDao;
import jp.co.tokaigroup.reception.dao.RcpTMailRirekiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKanri;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKLife;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.mail.VelocityEmail;
import jp.co.tokaigroup.si.fw.mail.VelocityWrapper;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB013DisclosureMailSendModel;
import jp.co.tokaigroup.tlcss_b2b.dto.TB013DisclosureMailSendDto;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TORES���J���[�����M��ʃT�[�r�X�����N���X�B
 *
 * @author k003856
 * @version 5.0 2015/09/08
 * @version 5.1 2016/07/14 H.Hirai ����������Ή�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB013DisclosureMailSendServiceImpl extends TLCSSB2BBaseService
		implements TB013DisclosureMailSendService {

	// ���[���e���v���[�g���ځ@�L�[
	/** ���[���e���v���[�g���� �L�[ �s���Y�E�Ǘ���Ж� */
	private static final String MAIL_KEY_REAL_ESTATE_NAME = "realEstateName";
	/** ���[���e���v���[�g���� �L�[ �J�ڐ�URL */
	private static final String MAIL_KEY_TRANSITION_URL = "transitionUrl";
	/** ���[���e���v���[�g���� �L�[ �V�X�e���₢���킹��d�b�ԍ� */
	private static final String MAIL_KEY_CONTACT_INFORMAITION = "contactInformation";
	/** ���[���e���v���[�g���� �L�[ �ԐM���[���A�h���X */
	private static final String MAIL_KEY_REPLY_MAIL_ADDRESS = "returnMailAddress";
	/** ���[���e���v���[�g���� �L�[ ���M����Ж� */
	private static final String MAIL_KEY_NOTICE_COMPAY_NAME = "noticeCompanyNm";

	/** ���[�����쐬�敪 1�F�����\�� */
	private static final String MAIL_INFO_KBN_INIT = "1";
	/** ���[�����쐬�敪 2�F���[�����M */
	private static final String MAIL_INFO_KBN_MAIL_SEND = "2";

	/** ���Z�v�V�����₢���킹�e�[�u��DAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** ���Z�v�V�����ڋq�_�񃉃C�t�T�|�[�g�}�X�^DAO */
	@Autowired
	private RcpMKokyakuKLifeDao kokyakuKLifeDao;

	/** ���Z�v�V�����ڋq�t���Ǘ���Џ��}�X�^DAO */
	@Autowired
	private RcpMKokyakuFKanriDao kokyakuFuzuiDao;

	/** ���J���[�����M�����}�X�^-DAO */
	@Autowired
	private RcpTMailRirekiDao mailRirekiDao;

	/** �O���T�C�g�V�X�e�� �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** ���Z�v�V������Ѓ}�X�^DAO */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/**
	 * �����\���������s���܂��B
	 *
	 * @param model TORES���J���[�����M��ʃ��f��
	 * @return TORES���J���[�����M��ʃ��f��
	 */
	public TB013DisclosureMailSendModel getInitInfo(TB013DisclosureMailSendModel model) {

		// �₢���킹���擾
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// �₢���킹��񂪂Ȃ��ꍇ�́A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		model.setToiawase(toiawase);

		// �₢���킹���ɕR�t���ڋq���擾
		RcpMKokyaku kokyaku = kokyakuKihon.getKokyakuInfo(toiawase.getKokyakuId());
		model.setKokyaku(kokyaku);

		// ��Џ��擾
		TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(toiawase.getCreKaishaId());
		if (kaisha == null) {
			// ��Џ�񂪂Ȃ��ꍇ�́A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		model.setKaishaId(kaisha.getKaishaId());

		
		if (RcpTToiawase.IRAI_FLG_ONAJI.equals(toiawase.getIraishaFlg())) {
			// �˗��҃t���O���u�ڋq��{���Ɠ����v�̏ꍇ�A�ڋq�_�񃉃C�t�T�|�[�g�����擾
			RcpMKokyakuKLife kokyakuKLife = kokyakuKLifeDao.selectByPrimaryKey(
					kokyaku.getKokyakuId(), new BigDecimal(1));
			model.setKokyakuKLife(kokyakuKLife);
		}

		// ���M�惁�[���A�h���X�擾
		// ������ڋq��񃊃X�g�擾
		List<RcpMKokyaku> tmpKokyakuList = kokyakuDao.selectSeikyusakiKokyakuList(kokyaku.getKokyakuId());
		List<RcpMKokyaku> seikyusakiKokyakuList = new ArrayList<RcpMKokyaku>();
		// �ڋq�敪�u1�F�Ǘ���Ёi�I�[�i�[�j�v�ȊO�̃��X�g�������O����
		for (RcpMKokyaku kokyakuInfo : tmpKokyakuList) {

			if (!kokyakuInfo.isKokyakuKbnFudosan()) {
				// �u1�F�Ǘ���Ёi�I�[�i�[�j�v�ȊO�̏ꍇ�A�����R�[�h��
				continue;
			}

			seikyusakiKokyakuList.add(kokyakuInfo);
		}

		if ((seikyusakiKokyakuList == null || seikyusakiKokyakuList.isEmpty())
				&& !kokyaku.isKokyakuKbnFudosan()) {
			// ������ڋq�����݂��Ȃ����A�������g�̌ڋq�敪���s���Y�E�Ǘ���ЂłȂ��ꍇ�́A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0036", "������ڋq", "�i������ڋq�����m�F���ĉ������j"));
		}

		if (!seikyusakiKokyakuList.isEmpty()) {
			// ������ڋq�����݂���ꍇ�A1���ڂ̐�����ڋqID��ݒ�
			model.setSeikyusakiKokyakuId(seikyusakiKokyakuList.get(0).getKokyakuId());
		} else {
			model.setSeikyusakiKokyakuId(kokyaku.getKokyakuId());
		}

		List<RcpMKokyakuFKanri> kokyakuFKanriList = new ArrayList<RcpMKokyakuFKanri>();
		if (!seikyusakiKokyakuList.isEmpty()) {
			// ������ڋq��񃊃X�g��1���ȏ㑶�݂���ꍇ

			for (RcpMKokyaku seikyusakiKokyaku : seikyusakiKokyakuList) {

				// �ڋq�t���Ǘ���Џ��擾
				RcpMKokyakuFKanri kokyakuFKanriInfo = kokyakuFuzuiDao
						.selectByPrimaryKey(seikyusakiKokyaku.getKokyakuId());

				if (kokyakuFKanriInfo != null
						&& (StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress1())
								|| StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress2())
								|| StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress3())
						)
				) {

					// �ڋq�t���Ǘ���Џ�񂪎擾�ł��A
					// �Ή��񍐃��[���A�h���X�P�`�R�̂��Âꂩ��NULL�ȊO�̏ꍇ
					kokyakuFKanriList.add(kokyakuFKanriInfo);
				}
			}
		} else {
			// ������ڋq��񃊃X�g�����݂��Ȃ��ꍇ

			// �ڋq�t���Ǘ���Џ��擾
			RcpMKokyakuFKanri kokyakuFKanriInfo = kokyakuFuzuiDao
					.selectByPrimaryKey(kokyaku.getKokyakuId());

			if (kokyakuFKanriInfo != null
					&& (StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress1())
							|| StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress2())
							|| StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress3())
					)
			) {

				// �ڋq�t���Ǘ���Џ�񂪎擾�ł��A
				// �Ή��񍐃��[���A�h���X�P�`�R�̂��Âꂩ��NULL�ȊO�̏ꍇ
				kokyakuFKanriList.add(kokyakuFKanriInfo);
			}
		}

		// �ڋq�t���Ǘ���Џ��}�X�^Entity��0���̏ꍇ�A�G���[
		if (kokyakuFKanriList.isEmpty()) {
			throw new ValidationException(new ValidationPack().addError("MSG0037",
					"���M�惁�[���A�h���X", "�i�ڋq�t�����y�Ή��񍐒ʒm���z�̃��[���A�h���X���m�F���ĉ������j"));
		}
		model.setKokyakuFKanriList(kokyakuFKanriList);

		// ����
		model.setSubject(createSubject(model));

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// ���M�����[���A�h���X
		model.setSenderMailAddress(userContext.getSystgemContstantAsString(
				RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ADDRESS));
		// BCC���[���A�h���X
		model.setBccMailAddress(userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_BCC_ADDRESS));

		// ���[���{���擾����
		model = getMailInfo(model, MAIL_INFO_KBN_INIT);

		return model;
	}

	/**
	 * ���[�����擾�������s���܂��B
	 *
	 * @param model TORES���J���[�����M��ʃ��f��
	 * @param mailInfoKbn ���[�����쐬�敪
	 * @return TORES���J���[�����M��ʃ��f��
	 */
	private TB013DisclosureMailSendModel getMailInfo(TB013DisclosureMailSendModel model, String mailInfoKbn) {
		try {

			if (MAIL_INFO_KBN_INIT.equals(mailInfoKbn)) {
				// �����\�������̏ꍇ

				List<TB013DisclosureMailSendDto> disclosureMailSendList = new ArrayList<TB013DisclosureMailSendDto>();

				// �擾�����ڋq�t���Ǘ���Ѓ��X�g������
				for (RcpMKokyakuFKanri kokyakuFKanri : model.getKokyakuFKanriList()) {

					// ������ڋq���擾
					RcpMKokyaku seikyusakiKokyaku = kokyakuDao.selectByPrimaryKey(kokyakuFKanri.getKokyakuId());

					// ��Џ��擾
					TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(model.getKaishaId());
					if (kaisha == null) {
						// ��Џ�񂪎擾�ł��Ȃ������ꍇ�A�G���[
						throw new ValidationException(new ValidationPack().addError("MSG0003"));
					}

					// �u��������Map����
					VelocityWrapper wrapper = new VelocityWrapper(
							MailTemplate.TOIAWASE_PUBLISH_INFORMATION_MAIL_FILE_NAME);

					TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

					// �J�ڐ�URL�𐶐�
					String transitionUrl;
					if (model.isFromToiawaseEntry()) {
						// �₢���킹�o�^��ʂ���̑J�ڂ̏ꍇ�A�₢���킹���e�ڍׂւ̃����N���쐬
						transitionUrl = userContext.getSystgemContstantAsString(
								RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL)
										+ "externalLogin.action?actionURL=/inquiryDetailInit&toiawaseNo="
										+ model.getToiawaseNo() + "&dispKbn=2"; 
					} else {
						// �˗��o�^��ʂ���̑J�ڂ̏ꍇ�A�˗����e�ڍׂւ̃����N���쐬
						transitionUrl = userContext.getSystgemContstantAsString(
								RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL)
						+ "externalLogin.action?actionURL=/requestDetail&toiawaseNo="
						+ model.getToiawaseNo() + "&toiawaseRirekiNo="
						+ model.getToiawaseRirekiNo().toString() + "&dispKbn=3";
					} 

					// �u��������
					// �s���Y�E�Ǘ���Ж�
					wrapper.put(MAIL_KEY_REAL_ESTATE_NAME, seikyusakiKokyaku.getKanjiNm());

					// �J�ڐ�URL
					wrapper.put(MAIL_KEY_TRANSITION_URL, transitionUrl);

					// �A����
					wrapper.put(MAIL_KEY_CONTACT_INFORMAITION, kaisha.getRenrakusaki());
					
					// �ԐM���[���A�h���X
					wrapper.put(MAIL_KEY_REPLY_MAIL_ADDRESS, StringUtils.defaultString(kaisha.getMailAddress()));
					
					// ���M����Ж�
					wrapper.put(MAIL_KEY_NOTICE_COMPAY_NAME, kaisha.getTsuchiKaishaNm());

					String mailText = "";
					try {
						// �e���v���[�g����u�����āA�{���ɐݒ�
						mailText = wrapper.merge();
					} catch (ResourceNotFoundException e) {
						throw new ApplicationException(e);
					} catch (ParseErrorException e) {
						throw new ApplicationException(e);
					} catch (MethodInvocationException e) {
						throw new ApplicationException(e);
					} catch (IOException e) {
						throw new ApplicationException(e);
					}

					// �쐬���������c�s�n�ɐݒ�
					TB013DisclosureMailSendDto disclosureMailSend = new TB013DisclosureMailSendDto();
					disclosureMailSend.setSeikyusakiKokyakuId(seikyusakiKokyaku.getKokyakuId());
					disclosureMailSend.setSeikyusakiKokyakuNm(seikyusakiKokyaku.getKokyakuIdWithKanjiNm());
					List<String> taioMailAddressList = new ArrayList<String>();
					if (StringUtils.isNotBlank(kokyakuFKanri.getTaioMailAddress1())) {
						taioMailAddressList.add(kokyakuFKanri.getTaioMailAddress1());
					}
					if (StringUtils.isNotBlank(kokyakuFKanri.getTaioMailAddress2())) {
						taioMailAddressList.add(kokyakuFKanri.getTaioMailAddress2());
					}
					if (StringUtils.isNotBlank(kokyakuFKanri.getTaioMailAddress3())) {
						taioMailAddressList.add(kokyakuFKanri.getTaioMailAddress3());
					}
					String taioMailAddress = StringUtils.join(taioMailAddressList, ",");
					disclosureMailSend.setTaioMailAddress(taioMailAddress);
					disclosureMailSend.setMailText(mailText);
					disclosureMailSendList.add(disclosureMailSend);
				}

				model.setDisclosureMailSendList(disclosureMailSendList);
				model.setDisclosureMailSendListSize(disclosureMailSendList.size());
			} else {
				// ���[�����M�����̏ꍇ

				// ������ڋq���擾
				RcpMKokyaku seikyusakiKokyaku = kokyakuDao.selectByPrimaryKey(model.getSeikyusakiKokyakuId());

				// ��Џ��擾
				TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(model.getKaishaId());
				if (kaisha == null) {
					// ��Џ�񂪎擾�ł��Ȃ������ꍇ�A�G���[
					throw new ValidationException(new ValidationPack().addError("MSG0003"));
				}

				// �u��������Map����
				VelocityWrapper wrapper = new VelocityWrapper(
						MailTemplate.TOIAWASE_PUBLISH_INFORMATION_MAIL_FILE_NAME);

				TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

				// �J�ڐ�URL�𐶐�
				String transitionUrl;
				if (model.isFromToiawaseEntry()) {
					// �₢���킹�o�^��ʂ���̑J�ڂ̏ꍇ�A�₢���킹���e�ڍׂւ̃����N���쐬
					transitionUrl = userContext.getSystgemContstantAsString(
							RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL)
									+ "externalLogin.action?actionURL=/inquiryDetailInit&toiawaseNo="
									+ model.getToiawaseNo() + "&dispKbn=2"; 
				} else {
					// �˗��o�^��ʂ���̑J�ڂ̏ꍇ�A�˗����e�ڍׂւ̃����N���쐬
					transitionUrl = userContext.getSystgemContstantAsString(
							RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL)
					+ "externalLogin.action?actionURL=/requestDetail&toiawaseNo="
					+ model.getToiawaseNo() + "&toiawaseRirekiNo="
					+ model.getToiawaseRirekiNo().toString() + "&dispKbn=3";
				} 

				// �u��������
				// �s���Y�E�Ǘ���Ж�
				wrapper.put(MAIL_KEY_REAL_ESTATE_NAME, seikyusakiKokyaku.getKanjiNm());

				// �J�ڐ�URL
				wrapper.put(MAIL_KEY_TRANSITION_URL, transitionUrl);

				// �A����
				wrapper.put(MAIL_KEY_CONTACT_INFORMAITION, kaisha.getRenrakusaki());
				
				// �ԐM���[���A�h���X
				wrapper.put(MAIL_KEY_REPLY_MAIL_ADDRESS, StringUtils.defaultString(kaisha.getMailAddress()));
				
				// ���M����Ж�
				wrapper.put(MAIL_KEY_NOTICE_COMPAY_NAME, kaisha.getTsuchiKaishaNm());

				model.setWrapper(wrapper);
			}

		} catch (Exception e) {
			// ���̑���O
			throw new ApplicationException(e);
		}

		return model;
	}

	/**
	 * ���[�����M�������s���܂��B
	 *
	 * @param model TORES���J���[�����M��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void executeSendMail(TB013DisclosureMailSendModel model) {
		// ���[���{���擾����
		model = getMailInfo(model, MAIL_INFO_KBN_MAIL_SEND);

		// �O���T�C�g�A�N�Z�X���O�̓o�^
		tbAccesslogDao.insert(TB013DisclosureMailSendModel.GAMEN_NM,
				TB013DisclosureMailSendModel.BUTTON_NM_MAIL_SEND, createKensakuJoken(model));

		try {
			// �V�X�e���}�X�^��������擾

			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// �ԐM���[���A�h���X
			String returnMailAddress = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_RETURN_MAIL_ADDRESS);

			// �u��������Map����
			VelocityWrapper wrapper = model.getWrapper();

			VelocityEmail email = new VelocityEmail();

			// ���o���A�h���X
			email.setFrom(userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ADDRESS));
			// �F��
			email.setAuthenticator(new DefaultAuthenticator(
					userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ACCOUNT),
					userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_PASSWORD)));
			// ����
			for (String address : model.getTaioMailAddressList()) {
				email.addTo(address);
			}

			// ����BCC
			String bcc = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_BCC_ADDRESS);
			if (StringUtils.isNotBlank(bcc)) {
				email.addBcc(StringUtils.split(bcc, ","));
			}
			// �ԐM���[���A�h���X
			email.addReplyTo(returnMailAddress);
			// ����
			email.setSubject(model.getSubject());
			// �u��������Map�̐ݒ�
			email.setVelocityWrapper(wrapper);

			// ���[�����M
			email.send();

			String rirekiNo;
			if (model.isFromToiawaseEntry()){
				rirekiNo = model.getToiawaseNo();
			} else{
				rirekiNo = model.getToiawaseNo() + "-"
					+ StringUtils.leftPad(model.getToiawaseRirekiNo().toString(), 3, "0");
			}

			// ���J���[�����M�������̓o�^
			mailRirekiDao.insertOrUpdate(createRirekiInfo(rirekiNo,model.getSubject()));

		} catch (EmailException e) {
			// ���[�����M�Ɏ��s�����ꍇ
			throw new ApplicationException(e);
		} catch (Exception e) {
			// ���̑���O
			throw new ApplicationException(e);
		}
	}

	/**
	 * ��ʂɕ\�����錏���𐶐����܂��B
	 *
	 * @param model TORES���J���[�����M��ʃ��f��
	 * @return ��ʂɕ\�����錏��
	 */
	private String createSubject(TB013DisclosureMailSendModel model) {
		StringBuilder subject = new StringBuilder("");

		if (StringUtils.isNotBlank(model.getSubject())) {
			// ���Ɍ������ݒ肳��Ă���ꍇ�i����\���łȂ��j�́A���̂܂�
			return model.getSubject();
		}

		RcpTToiawase toiawase = model.getToiawase();

		subject.append("�y�z�i");
		if (RcpTToiawase.IRAI_FLG_ONAJI.equals(toiawase.getIraishaFlg())) {
			// �˗��҃t���O���u�ڋq��{���Ɠ����v�̏ꍇ
			subject.append(StringUtils.defaultString(model.getKokyaku().getJusho5()));
		} else {
			// �˗��҃t���O���u�قȂ�v�̏ꍇ
			subject.append(model.getKokyaku().getKanjiNm());
		}
		subject.append("�E");
		if (RcpTToiawase.IRAI_FLG_ONAJI.equals(toiawase.getIraishaFlg())) {
			// �˗��҃t���O���u�ڋq��{���Ɠ����v�̏ꍇ
			if (model.getKokyakuKLife() != null) {
				subject.append(model.getKokyakuKLife().getRoomNo());
			}
		} else {
			// �˗��҃t���O���u�قȂ�v�̏ꍇ
			subject.append(StringUtils.defaultString(toiawase.getIraishaRoomNo()));
		}

		subject.append("�����E");
		subject.append(model.getToiawaseNo());
		subject.append("�j");

		return subject.toString();
	}
	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model TORES���J���[�����M��ʃ��f��
	 * @return �A�N�Z�X���O�ɓo�^���錟������
	 */
	private String createKensakuJoken(TB013DisclosureMailSendModel model) {
		
		return "shoriKbn=" + model.getShoriKbn().toString() + ","
				+ "toiawaseNo=" + model.getToiawaseNo() + ","
				+ "toiawaseRirekiNo=" + String.valueOf(model.getToiawaseRirekiNo());
	}
	/**
	 * ���J���[�����M�����}�X�^-�o�^���s�����߂̌��J���[�����M�������𐶐����܂��B
	 *
	 * @param rirekiNo �⍇�������m��, subject ���[������
	 * @return ���J���[�����M����
	 */
	private RcpTMailRireki createRirekiInfo(String rirekiNo,String subject) {
		RcpTMailRireki rireki = new RcpTMailRireki();

		rireki.setRirekiNo(rirekiNo);
		rireki.setSubject(subject);
		rireki.setCreId(getUserContext().getLoginId());
		rireki.setLastUpdId(getUserContext().getLoginId());

		return rireki;
	}
}
