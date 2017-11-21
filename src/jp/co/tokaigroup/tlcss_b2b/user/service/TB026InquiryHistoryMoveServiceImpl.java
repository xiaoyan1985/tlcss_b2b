package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpTFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuWithNoIdDao;
import jp.co.tokaigroup.reception.dao.RcpTOtherFileUploadDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyoJokyoDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyohiDao;
import jp.co.tokaigroup.reception.dao.RcpTSagyohiMeisaiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseRirekiDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTOtherFileUpload;
import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.RcpTSagyohi;
import jp.co.tokaigroup.reception.entity.RcpTSagyohiMeisai;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB026InquiryHistoryMoveModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �₢���킹�����ړ���ʃT�[�r�X�����N���X�B
 *
 * @author J.Matsuba
 * @version 1.0 2015/08/18
 * @version 1.1 2016/07/22 J.Matsuba �X�V�`�F�b�N����isValid()�̒ǉ�
 * @version 1.2 2016/08/04 H.Yamamura ���̓`�F�b�N��₢���킹�̃T�[�r�X��ʂōs���悤�ɕύX
 *
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB026InquiryHistoryMoveServiceImpl extends TLCSSB2BBaseService
		implements TB026InquiryHistoryMoveService {
	/** ���Z�v�V�����₢���킹�e�[�u��DAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** ���Z�v�V�����₢���킹�����e�[�u��DAO */
	@Autowired
	private RcpTToiawaseRirekiDao toiawaseRirekiDao;

	/** ���Z�v�V�����h�c�����ڋq�e�[�u��DAO */
	@Autowired
	private RcpTKokyakuWithNoIdDao kokyakuWithNoIdDao;

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** ���Z�v�V�����˗��e�[�u��DAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/** ���Z�v�V������Ə󋵃e�[�u��DAO */
	@Autowired
	private RcpTSagyoJokyoDao sagyoJokyoDao;

	/** ���Z�v�V������Ɣ�׃e�[�u��DAO */
	@Autowired
	private RcpTSagyohiMeisaiDao sagyohiMeisaiDao;

	/** ���Z�v�V�����˗��t�@�C���A�b�v���[�h�e�[�u��DAO */
	@Autowired
	private RcpTFileUploadDao fileUploadDao;

	/** ���Z�v�V�����˗��t�@�C���A�b�v���[�h�e�[�u��DAO */
	@Autowired
	private RcpTOtherFileUploadDao otherFileUploadDao;

	/** ���Z�v�V������Ɣ�e�[�u��DAO */
	@Autowired
	private RcpTSagyohiDao sagyohiDao;

	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao accesslogDao;
	
	/** �ϑ���Њ֘A�`�F�b�N���W�b�N */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;

	/**
	 * �����\���������s���܂��B
	 *
	 * @param model �₢���킹�����ړ���ʃ��f��
	 * @return �₢���킹�����ړ���ʃ��f��
	 */
	public TB026InquiryHistoryMoveModel getInitInfo(TB026InquiryHistoryMoveModel model) {
		// �ύX�O���擾
		// �₢���킹���擾
		RcpTToiawase oldToiawase =
			toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (oldToiawase == null) {
			// �ύX�O�̖₢���킹��񂪂Ȃ��ꍇ
			model.setInitError(true);
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		model.setOldToiawaseInfo(oldToiawase);

		if (model.isOldKokyakuWithoutId()) {
			// ID�����ڋq���擾�̏ꍇ
			RcpTKokyakuWithNoId oldKokyakuWithNoId =
				kokyakuWithNoIdDao.selectByPrimaryKey(model.getToiawaseNo());

			model.setOldKokyakuInfoWithoutId(oldKokyakuWithNoId);
		} else {
			// �ڋq�}�X�^���擾�̏ꍇ
			RcpMKokyaku oldKokyaku =
				kokyakuDao.selectByPrimaryKey(oldToiawase.getKokyakuId());

			model.setOldKokyakuInfo(oldKokyaku);
		}

		// �ύX����擾
		// �₢���킹���擾
		String newToiawaseNo = StringUtils.leftPad(model.getNewToiawaseNo(), 10, '0');

		RcpTToiawase newToiawase =
			toiawaseDao.selectByPrimaryKey(newToiawaseNo);
		if (newToiawase == null) {
			// �ύX��̖₢���킹��񂪂Ȃ��ꍇ
			model.setInitError(true);
			throw new ValidationException(new ValidationPack().addError("MSG0002"));

		}

		// �ړ���̖₢���킹�����ߍς̏ꍇ�A�o�^�s��
		if (StringUtils.isNotBlank(newToiawase.getShimeYm())) {
			model.setInitError(true);
			throw new ValidationException(new ValidationPack().addError("MSG0035"));
		}

		// �Z�b�V���������擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// �Z�b�V�����̌������u40:�ϑ����SV�v�܂��́u41:�ϑ����OP�v�̏ꍇ
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// �ϑ��֘A��Ѓ`�F�b�N��NG�̏ꍇ
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), newToiawase.getKokyakuId())){
				// �ϑ��֘A��Ѓ`�F�b�N�G���[
				model.setInitError(true);
				throw new ValidationException(new ValidationPack().addError("MSG0031", "�Q�ƕs�ڋq��", "�₢���킹���"));
			}
		}
		
		model.setNewToiawaseInfo(newToiawase);

		if (model.isNewKokyakuWithoutId()) {
			// ID�����ڋq���擾�̏ꍇ
			RcpTKokyakuWithNoId newKokyakuWithNoId =
				kokyakuWithNoIdDao.selectByPrimaryKey(newToiawaseNo);

			model.setNewKokyakuInfoWithoutId(newKokyakuWithNoId);
		} else {
			// �ڋq�}�X�^���擾�̏ꍇ
			RcpMKokyaku newKokyaku =
				kokyakuDao.selectByPrimaryKey(newToiawase.getKokyakuId());

			model.setNewKokyakuInfo(newKokyaku);
		}

		return model;
	}

	/**
	 * �₢���킹�����ړ��������s���܂��B
	 *
	 * @param model�₢���킹�����ړ���ʃ��f��
	 */
	@Transactional(value="txManager")
	public void updateInquiryHistoryMoveInfo(TB026InquiryHistoryMoveModel model) {
		// �X�V�`�F�b�N����
		isValid(model);

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// �ړ����̖₢���킹�e�[�u���X�V
		RcpTToiawase oldToiawaseInfo = model.getOldToiawaseInfo();
		oldToiawaseInfo.setLastUpdId(userContext.getLoginId());
		oldToiawaseInfo.setLastUpdNm(userContext.getUserName());

		if (toiawaseDao.updateUpdDt(oldToiawaseInfo) == 0) {
			// �X�V�������O���̏ꍇ�́A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003", ""));
		}

		// �ړ���̖₢���킹�e�[�u���X�V
		RcpTToiawase newToiawaseInfo = model.getNewToiawaseInfo();
		newToiawaseInfo.setLastUpdId(userContext.getLoginId());
		newToiawaseInfo.setLastUpdNm(userContext.getUserName());

		if (toiawaseDao.updateUpdDt(model.getNewToiawaseInfo()) == 0) {
			// �X�V�������O���̏ꍇ�́A�r���G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003", ""));
		}

		//---------- �e�e�[�u���ړ����� �������� ----------//
		// �₢���킹�������쐬
		// �ړ���̍ő嗚��NO�擾
		String newToiawaseNo = StringUtils.leftPad(model.getNewToiawaseNo(), 10, '0');
		BigDecimal newMaxRirekiNo = toiawaseRirekiDao.selectMaxRirekiNo(newToiawaseNo);

		// �₢���킹�����e�[�u���쐬
		moveToToiawaseRireki(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo, model);

		// �˗��e�[�u���쐬
		moveToIrai(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);

		// ��Ə󋵃e�[�u���쐬
		moveToSagyoJokyo(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);

		// �˗��t�@�C���A�b�v���[�h�e�[�u���쐬
		moveToFileUpload(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);

		// �˗����̑��t�@�C���A�b�v���[�h�e�[�u���쐬
		moveToOtherFileUpload(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);

		// ��Ɣ�e�[�u���쐬
		moveToSagyohi(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);

		// ��Ɣ�׃e�[�u���쐬
		moveToSagyohiMeisai(model.getToiawaseNo(), newToiawaseNo, newMaxRirekiNo);
		//---------- �e�e�[�u���ړ����� �����܂� ----------//

		//---------- �e�e�[�u���폜���� �������� ----------//
		// ��Ɣ�׃e�[�u���폜
		sagyohiMeisaiDao.deleteBy(model.getToiawaseNo(), null, null);

		// ��Ɣ�e�[�u���폜
		sagyohiDao.deleteBy(model.getToiawaseNo(), null);

		// �˗����̑��t�@�C���A�b�v���[�h�e�[�u���폜
		otherFileUploadDao.deleteBy(model.getToiawaseNo(), null, null);

		// �˗��t�@�C���A�b�v���[�h�e�[�u���폜
		fileUploadDao.deleteBy(model.getToiawaseNo(), null, null);

		// ��Ə󋵃e�[�u���폜
		sagyoJokyoDao.deleteBy(model.getToiawaseNo(), null);

		// �˗��e�[�u���폜
		iraiDao.deleteBy(model.getToiawaseNo(), null);

		// �₢���킹�����e�[�u���폜
		toiawaseRirekiDao.deleteBy(model.getToiawaseNo(), null);
		//---------- �e�e�[�u���폜���� �����܂� ----------//

		// �ړ������h�c�����ڋq�̏ꍇ�́A�h�c�����ڋq�e�[�u���̑ޔ��E�폜
		if (model.isOldKokyakuWithoutId()) {
			// �ޔ�����
			kokyakuWithNoIdDao.insertTaihiWithoutOptimisticLock(model.getOldKokyakuInfoWithoutId());
			// �폜����
			kokyakuWithNoIdDao.deleteWithoutOptimisticLock(model.getToiawaseNo());
		}

		// �₢���킹�e�[�u���̑ޔ��E�폜
		// �ޔ��������̍쐬
		RcpTToiawase toiawase = new RcpTToiawase();
		toiawase.setToiawaseNo(model.getToiawaseNo());

		// �ޔ�����
		toiawaseDao.insertTaihiWithoutOptimisticLock(toiawase);
		// �폜����
		toiawaseDao.deleteWithoutOptimisticLock(model.getToiawaseNo());
		
		// �₢���킹�����Ǐ��X�V
		toiawaseDao.updateBrowseStatusFlg(newToiawaseNo, false);

		// �A�N�Z�X���O�o�^
		accesslogDao.insert(TB026InquiryHistoryMoveModel.GAMEN_NM,
				TB026InquiryHistoryMoveModel.BUTTON_NM_MOVE, createSearchCondition(model));
	}

	/**
	 * �X�V�`�F�b�N�������s���܂��B
	 *
	 * @param model �₢���킹�����ړ���ʃ��f��
	 * @throws ValidationException �ړ����܂��͈ړ���X�P�W���[�����1���łȂ��ꍇ
	 */
	private void isValid(TB026InquiryHistoryMoveModel model) {
		// �ړ��O�̖₢���킹�����擾
		RcpTToiawase toiawaseInfo = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		// �₢���킹��񂪑��݂��Ȃ��@�܂��́@�T�[�r�X��ʂ��r���Ǘ��̏ꍇ
		if (toiawaseInfo == null || toiawaseInfo.isBuildingManagement()) {
			// �G���[���b�Z�[�W��\��
			throw new ValidationException(new ValidationPack().addError("MSG0046", "�ړ����₢���킹NO", "�����ړ������{�ł��܂���"));
		}
		
		// �ړ���̖₢���킹�����擾
		RcpTToiawase newToiawaseInfo = toiawaseDao.selectByPrimaryKey(model.getNewToiawaseInfo().getToiawaseNo());
		// �₢���킹��񂪑��݂��Ȃ��@�܂��́@�T�[�r�X��ʂ��r���Ǘ��̏ꍇ
		if (newToiawaseInfo == null || newToiawaseInfo.isBuildingManagement()) {
			// �G���[���b�Z�[�W��\��
			throw new ValidationException(new ValidationPack().addError("MSG0046", "�ړ���₢���킹NO", "�����ړ������{�ł��܂���"));
		}
	}

	/**
	 * �₢���킹�������̈ړ����s���܂��B
	 *
	 * @param oldToiawaseNo �ړ����₢���킹NO
	 * @param newToiawaseNo �ړ���₢���킹NO
	 * @param newMaxRirekiNo �ړ���̖₢���킹����NO�ő�l
	 */
	private void moveToToiawaseRireki(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo, TB026InquiryHistoryMoveModel model) {
		// �ړ����̖₢���킹�������擾
		List<RcpTToiawaseRireki> moveToiawaseRirekiList =
			toiawaseRirekiDao.selectBy(oldToiawaseNo, null);
		// �A�N�Z�X���O�p�ɋL�ڂ���₢���킹����NO
		List<BigDecimal> entryRirekiNoList = new ArrayList<BigDecimal>();
		for (RcpTToiawaseRireki toiawaseRireki : moveToiawaseRirekiList) {
			// �₢���킹NO�̍ăZ�b�g
			toiawaseRireki.setToiawaseNo(newToiawaseNo);

			// �₢���킹����NO�̐���
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					toiawaseRireki.getToiawaseRirekiNo(), newMaxRirekiNo);

			// �₢���킹����NO�̍ăZ�b�g
			toiawaseRireki.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// �ړ������₢���킹����NO�̕ۑ�
			entryRirekiNoList.add(entryToiawaseRirekiNo);

			// �₢���킹�������̓o�^
			toiawaseRirekiDao.insertAsEntity(toiawaseRireki);
		}

		model.setEntryRirekiNoList(entryRirekiNoList);
	}

	/**
	 * �˗����̈ړ����s���܂��B
	 *
	 * @param oldToiawaseNo �ړ����₢���킹NO
	 * @param newToiawaseNo �ړ���₢���킹NO
	 * @param newMaxRirekiNo �ړ���̖₢���킹����NO�ő�l
	 */
	private void moveToIrai(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTIrai> moveIraiList = iraiDao.selectBy(oldToiawaseNo, null);
		for (RcpTIrai irai : moveIraiList) {
			// �₢���킹NO�̍ăZ�b�g
			irai.setToiawaseNo(newToiawaseNo);

			// �₢���킹����NO�̐���
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					irai.getToiawaseRirekiNo(), newMaxRirekiNo);

			// �₢���킹����NO�̍ăZ�b�g
			irai.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// �˗����̓o�^
			iraiDao.insertAsEntity(irai);
		}
	}

	/**
	 * ��Ə󋵏��̈ړ����s���܂��B
	 *
	 * @param oldToiawaseNo �ړ����₢���킹NO
	 * @param newToiawaseNo �ړ���₢���킹NO
	 * @param newMaxRirekiNo �ړ���̖₢���킹����NO�ő�l
	 */
	private void moveToSagyoJokyo(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTSagyoJokyo> moveSagyoJokyoList =
			sagyoJokyoDao.selectBy(oldToiawaseNo, null);
		for (RcpTSagyoJokyo sagyoJokyo : moveSagyoJokyoList) {
			// �₢���킹NO�̍ăZ�b�g
			sagyoJokyo.setToiawaseNo(newToiawaseNo);

			// �₢���킹����NO�̐���
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					sagyoJokyo.getToiawaseRirekiNo(), newMaxRirekiNo);

			// �₢���킹����NO�̍ăZ�b�g
			sagyoJokyo.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// ��Ə󋵏��̓o�^
			sagyoJokyoDao.insertAsEntity(sagyoJokyo);
		}
	}

	/**
	 * �˗��t�@�C���A�b�v���[�h���̈ړ����s���܂��B
	 *
	 * @param oldToiawaseNo �ړ����₢���킹NO
	 * @param newToiawaseNo �ړ���₢���킹NO
	 * @param newMaxRirekiNo �ړ���̖₢���킹����NO�ő�l
	 */
	private void moveToFileUpload(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTFileUpload> moveFileUploadList =
			fileUploadDao.selectBy(oldToiawaseNo, null, null);
		for (RcpTFileUpload fileUpload : moveFileUploadList) {
			// �₢���킹NO�̍ăZ�b�g
			fileUpload.setToiawaseNo(newToiawaseNo);

			// �₢���킹����NO�̐���
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					fileUpload.getToiawaseRirekiNo(), newMaxRirekiNo);

			// �₢���킹����NO�̍ăZ�b�g
			fileUpload.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// �˗��t�@�C���A�b�v���[�h���̓o�^
			fileUploadDao.insertAsEntity(fileUpload);
		}
	}

	/**
	 * �˗����̑��t�@�C���A�b�v���[�h���̈ړ����s���܂��B
	 *
	 * @param oldToiawaseNo �ړ����₢���킹NO
	 * @param newToiawaseNo �ړ���₢���킹NO
	 * @param newMaxRirekiNo �ړ���̖₢���킹����NO�ő�l
	 */
	private void moveToOtherFileUpload(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTOtherFileUpload> moveOtherFileUploadList =
			otherFileUploadDao.selectBy(oldToiawaseNo, null, null);
		for (RcpTOtherFileUpload otherFileUpload : moveOtherFileUploadList) {
			// �₢���킹NO�̍ăZ�b�g
			otherFileUpload.setToiawaseNo(newToiawaseNo);

			// �₢���킹����NO�̐���
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					otherFileUpload.getToiawaseRirekiNo(), newMaxRirekiNo);

			// �₢���킹����NO�̍ăZ�b�g
			otherFileUpload.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// �˗��t�@�C���A�b�v���[�h���̓o�^
			otherFileUploadDao.insertAsEntity(otherFileUpload);
		}
	}

	/**
	 * ��Ɣ���̈ړ����s���܂��B
	 *
	 * @param oldToiawaseNo �ړ����₢���킹NO
	 * @param newToiawaseNo �ړ���₢���킹NO
	 * @param newMaxRirekiNo �ړ���̖₢���킹����NO�ő�l
	 */
	private void moveToSagyohi(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTSagyohi> moveSagyohiList =
			sagyohiDao.selectBy(oldToiawaseNo, null);
		for (RcpTSagyohi sagyohi : moveSagyohiList) {
			// �₢���킹NO�̍ăZ�b�g
			sagyohi.setToiawaseNo(newToiawaseNo);

			// �₢���킹����NO�̐���
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					sagyohi.getToiawaseRirekiNo(), newMaxRirekiNo);

			// �₢���킹����NO�̍ăZ�b�g
			sagyohi.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// ��Ɣ���̓o�^
			sagyohiDao.insertAsEntity(sagyohi);
		}
	}

	/**
	 * ��Ɣ�׏��̈ړ����s���܂��B
	 *
	 * @param oldToiawaseNo �ړ����₢���킹NO
	 * @param newToiawaseNo �ړ���₢���킹NO
	 * @param newMaxRirekiNo �ړ���̖₢���킹����NO�ő�l
	 */
	private void moveToSagyohiMeisai(String oldToiawaseNo, String newToiawaseNo, BigDecimal newMaxRirekiNo) {
		List<RcpTSagyohiMeisai> moveSagyohiMeisaiList =
			sagyohiMeisaiDao.selectBy(oldToiawaseNo, null, null);
		for (RcpTSagyohiMeisai sagyohiMeisai : moveSagyohiMeisaiList) {
			// �₢���킹NO�̍ăZ�b�g
			sagyohiMeisai.setToiawaseNo(newToiawaseNo);

			// �₢���킹����NO�̐���
			BigDecimal entryToiawaseRirekiNo = createEntryToiawaseRirekiNo(
					sagyohiMeisai.getToiawaseRirekiNo(), newMaxRirekiNo);

			// �₢���킹����NO�̍ăZ�b�g
			sagyohiMeisai.setToiawaseRirekiNo(entryToiawaseRirekiNo);

			// �˗��t�@�C���A�b�v���[�h���̓o�^
			sagyohiMeisaiDao.insertAsEntity(sagyohiMeisai);
		}
	}

	/**
	 * �o�^����₢���킹����NO���쐬���܂��B
	 *
	 * @param oldToiawaseRirekiNo �ړ����₢���킹����NO
	 * @param maxToiawaseRirekiNo �ړ���̍ő�₢���킹����NO
	 * @return �ړ���ɓo�^����₢���킹����NO
	 */
	private BigDecimal createEntryToiawaseRirekiNo(BigDecimal oldToiawaseRirekiNo, BigDecimal maxToiawaseRirekiNo) {
		// �o�^����₢���킹����NO
		BigDecimal entryToiawaseRirekiNo =
			new BigDecimal(oldToiawaseRirekiNo.intValue());
		entryToiawaseRirekiNo = entryToiawaseRirekiNo.add(maxToiawaseRirekiNo);

		return entryToiawaseRirekiNo;
	}

	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model �₢���킹�����ړ���ʃ��f��
	 * @return ��������
	 */
	private String createSearchCondition(TB026InquiryHistoryMoveModel model) {
		StringBuilder accesslog = new StringBuilder("");

		accesslog.append("oldToiawaseNo=");
		accesslog.append(model.getToiawaseNo());
		accesslog.append(",");
		accesslog.append("newToiawaseNo=");
		accesslog.append(StringUtils.leftPad(model.getNewToiawaseNo(), 10, '0'));
		accesslog.append(",");
		accesslog.append("newToiawaseRirekiNo=");

		StringBuilder entryToiawaseRirekNo = new StringBuilder("");
		for (int i = 0; i < model.getEntryRirekiNoList().size(); i++) {
			BigDecimal toiawaseRirekiNo = model.getEntryRirekiNoList().get(i);

			if (StringUtils.isNotBlank(entryToiawaseRirekNo.toString())) {
				entryToiawaseRirekNo.append(",");
			}

			entryToiawaseRirekNo.append(toiawaseRirekiNo.toString());
		}

		accesslog.append(entryToiawaseRirekNo.toString());

		return accesslog.toString();
	}
}
