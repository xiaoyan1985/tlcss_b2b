package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuWithNoIdDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuWithNoId;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB025CustomerIdChangeModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �ڋq�h�c�ύX��ʃT�[�r�X�����N���X�B
 *
 * @author k002785
 * @version 1.0 2015/08/19
 * @version 1.1 2016/07/22 J.Matsuba �X�V�`�F�b�N����isValid()�̒ǉ�
 * @version 1.2 2016/08/04 H.Yamamura ���̓`�F�b�N��₢���킹�̃T�[�r�X��ʂōs���悤�ɕύX
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB025CustomerIdChangeServiceImpl  extends TLCSSB2BBaseService
	implements TB025CustomerIdChangeService {

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;
	
	/** ���Z�v�V�����₢���킹�e�[�u��DAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;
	
	/** ���Z�v�V�����h�c�����ڋq�e�[�u��DAO */
	@Autowired
	private RcpTKokyakuWithNoIdDao kokyakuWithNoIdDao;

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao accesslogDao;

	/** �ϑ���Њ֘A�`�F�b�N���W�b�N */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;

	/**
	 * �����\���������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB025CustomerIdChangeModel getInitInfo(TB025CustomerIdChangeModel model) {

		// �Z�b�V���������擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		
		Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);
		
		// �ڋq�敪�a��Map�̎擾
		Map<String, RcpMComCd> kokyakuKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);

		// �˗��ҋ敪�a��Map�̎擾
		Map<String, RcpMComCd> iraishaKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		
		// �ύX�O�ڋqID�����݂���ꍇ
		if (model.isOldKokyakuIdExsits()) {
			// �ύX�O�ڋqID��ҏW
			model.setOldKokyakuId(editKokyakuId(model.getOldKokyakuId()));
			// �ڋq�}�X�^�擾
			RcpMKokyaku oldKokyakuInfo = kokyakuDao.selectByPrimaryKey(model.getOldKokyakuId());

			// �ڋq��񂪑��݂��Ȃ��ꍇ
			if (oldKokyakuInfo == null) {
				// �r���G���[
				model.setInitError(true);
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			// �ڋq�敪����ݒ�i�ڋq�敪Map�ɑΏۂ̌ڋq�敪�����݂���ꍇ�j
			if (kokyakuKbnMap.containsKey(oldKokyakuInfo.getKokyakuKbn())) {
				oldKokyakuInfo.setKokyakuKbnNm(kokyakuKbnMap.get(oldKokyakuInfo.getKokyakuKbn()).getExternalSiteVal());
			}

			model.setOldKokyakuInfo(oldKokyakuInfo);
		} else {
			// ID�����ڋq���擾
			RcpTKokyakuWithNoId kokyakuWithNoId = kokyakuWithNoIdDao.selectByPrimaryKey(model.getToiawaseNo());

			// ID�����ڋq��񂪑��݂��Ȃ��ꍇ
			if (kokyakuWithNoId == null) {
				// �r���G���[
				model.setInitError(true);
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}
			
			// �ڋq�敪�����擾�i�˗��ҋ敪Map�ɑΏۂ̌ڋq�敪�����݂���ꍇ�j
			if (iraishaKbnMap.containsKey(kokyakuWithNoId.getKokyakuKbn())) {
				kokyakuWithNoId.setKokyakuKbnNm(iraishaKbnMap.get(kokyakuWithNoId.getKokyakuKbn()).getExternalSiteVal());
			}

			model.setOldKokyakuInfoWithoutId(kokyakuWithNoId);
		}
		
		// �ύX��ڋqID��ҏW
		model.setNewKokyakuId(editKokyakuId(model.getNewKokyakuId()));
		// �ύX��ڋq���擾
		RcpMKokyaku newKokyakuInfo = kokyakuDao.selectByPrimaryKey(model.getNewKokyakuId());
		// �ڋq��񂪑��݂��Ȃ��ꍇ
		if (newKokyakuInfo == null) {
			// �r���G���[
			model.setInitError(true);
			throw new ValidationException(new ValidationPack().addError("MSG0002"));
		}
		
		// �Z�b�V�����̌������u40:�ϑ����SV�v�܂��́u41:�ϑ����OP�v�̏ꍇ
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// �ϑ��֘A��Ѓ`�F�b�N��NG�̏ꍇ
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), model.getNewKokyakuId())){
				// �ϑ��֘A��Ѓ`�F�b�N�G���[
				model.setInitError(true);
				throw new ValidationException(new ValidationPack().addError("MSG0031", "�Q�ƕs�ڋq��", "�ڋq�h�c"));
			}
		}

		// �ڋq�敪����ݒ�i�ڋq�敪Map�ɑΏۂ̌ڋq�敪�����݂���ꍇ�j
		if (kokyakuKbnMap.containsKey(newKokyakuInfo.getKokyakuKbn())) {
			newKokyakuInfo.setKokyakuKbnNm(kokyakuKbnMap.get(newKokyakuInfo.getKokyakuKbn()).getExternalSiteVal());
		}

		model.setNewKokyakuInfo(newKokyakuInfo);
		
		// �ڋqID�̐ݒ�i�₢���킹�o�^��ʍĕ\���p�ɓ��͂����ڋqID��ݒ�j
		model.setKokyakuId(model.getNewKokyakuId());

		// ��ʃ��f����ԋp
		return model;
	}

	/**
	 * �₢���킹�ڋq�X�V�������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void updateToiawaseKokyakuInfo(TB025CustomerIdChangeModel model) {
		// �X�V�`�F�b�N����
		isValid(model);

		// �₢���킹�e�[�u���̍X�V����
		toiawaseDao.updateKokyakuId(createUpdateToiawaseEntity(model));

		// �ύX�O�ڋqID�����݂��Ȃ��ꍇ
		if (!model.isOldKokyakuIdExsits()) {
			// ID�����ڋq���̑ޔ�
			int ret = kokyakuWithNoIdDao.insertTaihi(model.getOldKokyakuInfoWithoutId());
			if (ret == 0) {
				// �X�V�������O���̏ꍇ�́A�r���G���[
				throw new ValidationException(new ValidationPack().addError("MSG0003"));
			}

			// ID�����ڋq���̍폜
			kokyakuWithNoIdDao.deleteWithoutOptimisticLock(model.getOldKokyakuInfoWithoutId().getToiawaseNo());
		}

		// �A�N�Z�X���O�o�^
		accesslogDao.insert(TB025CustomerIdChangeModel.GAMAN_NM
			,TB025CustomerIdChangeModel.BUTTON_NM_CHANGE
			,createKensakuJoken(model));
	}

	/**
	 * �X�V�`�F�b�N�������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @throws ValidationException �X�P�W���[����񂪑��݂���ꍇ
	 */
	private void isValid(TB025CustomerIdChangeModel model) {
		// �₢���킹�����擾
		RcpTToiawase toiawaseInfo = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		// �₢���킹��񂪑��݂��Ȃ��@�܂��́@�T�[�r�X��ʂ��r���Ǘ��̏ꍇ
		if (toiawaseInfo == null || toiawaseInfo.isBuildingManagement()) {
			// �G���[���b�Z�[�W��\��
			throw new ValidationException(new ValidationPack().addError("MSG0046", "�₢���킹NO", "�ڋqID�̕ύX�����{�ł��܂���"));
		}
	}

	/**
	 * ��������ڋqID�̕ҏW���s���܂��B
	 *
	 * @param kokyakuId �ڋqID
	 * @return �ҏW��ڋqID
	 */
	private String editKokyakuId(String kokyakuId) {
		// �ڋqID�̕�������10�ȏ�̏ꍇ�͕ҏW���ŕԋp
		if (kokyakuId.length() >= 10) {
			return kokyakuId;
		}

		// �ڋqID��9�������̏ꍇ�́A�uC�v�t�� + �u0�v���߂��s���������ŕԋp
		return new StringBuilder("C").append(StringUtils.leftPad(kokyakuId, 9, '0')).toString();
	}
	
	/**
	 * �₢���킹�e�[�u�����̍X�V�����쐬���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return �₢���킹���
	 */
	private RcpTToiawase createUpdateToiawaseEntity(TB025CustomerIdChangeModel model) {
		RcpTToiawase toiawase = new RcpTToiawase();
		toiawase.setToiawaseNo(model.getToiawaseNo());
		toiawase.setKokyakuId(model.getNewKokyakuId());
		toiawase.setUpdDt(model.getToiawaseUpdDt());
		toiawase.setLastUpdId(getUserContext().getLoginId());

		return toiawase;
	}
	
	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model �₢���킹�ڋq�h�c�ύX��ʃ��f��
	 * @return ��������
	 */
	private String createKensakuJoken(TB025CustomerIdChangeModel model) {
		StringBuilder accesslog = new StringBuilder("");

		accesslog.append("toiawaseNo=");
		accesslog.append(model.getToiawaseNo());
		accesslog.append(",");
		accesslog.append("oldKokyakuId=");
		accesslog.append(model.getOldKokyakuId());
		accesslog.append(",");
		accesslog.append("kokyakuId=");
		accesslog.append(model.getNewKokyakuId());

		return accesslog.toString();
	}

}
