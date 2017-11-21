package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpTKokyakuManualDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuManual;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.dto.TB027CommonManualListDto;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB027CustomerManualModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �ڋq�}�j���A���ꗗ�T�[�r�X�����N���X�B
 *
 * @author ���t
 * @version 1.0 2015/08/05
 * @version 1.1 2016/07/07 H.Hirai �����敡���Ή�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB027CustomerManualServiceImpl extends TLCSSB2BBaseService
		implements TB027CustomerManualService {
	/** ���Z�v�V�����ڋq�}�j���A���e�[�u��DAO */
	@Autowired
	private RcpTKokyakuManualDao kokyakuManualDao;

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** �ϑ���Њ֘A�`�F�b�N���W�b�N */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;

	/**
	 * �����\���������s���܂��B
	 *
	 * @param model �ڋq�}�j���A���ꗗ��ʃ��f��
	 * @return �ڋq�}�j���A���ꗗ��ʃ��f��
	 */
	public TB027CustomerManualModel getInitInfo(TB027CustomerManualModel model) {
		// �Z�b�V��������Y�t�\�ő匏�����擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		int maxAppendableCount = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_KOKYAKU_MANUAL_APPENEDED_TO_MAX);

		// �ڋq��{���擾
		RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(model.getKokyakuId());
		if (kokyaku == null) {
			// �ڋq��{��񂪎擾�ł��Ȃ��ꍇ�́A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// �ڋq�}�j���A�����擾
		List<RcpTKokyakuManual> kobetsuManualList =
			kokyakuManualDao.selectBy(model.getKokyakuId(), null);

		if (kobetsuManualList != null) {
			if (kobetsuManualList.size() > maxAppendableCount) {
				// �Y�t�\�ő匏����胊�X�g�̐��������ꍇ
				// �Y�t�\�ő匏�����̂݁A���X�g��\��
				kobetsuManualList = kobetsuManualList.subList(0, maxAppendableCount);
			} else if (kobetsuManualList.size() < maxAppendableCount) {
				// �Y�t�\�ő匏���ɒB���Ă��Ȃ��ꍇ�́A��̏����쐬
				for (int i = kobetsuManualList.size(); i < maxAppendableCount; i++) {
					kobetsuManualList.add(new RcpTKokyakuManual());
				}
			}
		}

		// ������ڋq��񃊃X�g�擾
		List<RcpMKokyaku> seikyusakiKokyakuList =
			kokyakuDao.selectSeikyusakiKokyakuList(model.getKokyakuId());

		// �ڋq�}�j���A�����X�g�c�s�n���X�g
		List<TB027CommonManualListDto> commonManualDtoList = new ArrayList<TB027CommonManualListDto>();
		// �擾����������ڋq��񃊃X�g���}�j���A�����擾
		for (RcpMKokyaku seikyusakiKokyakuInfo : seikyusakiKokyakuList) {

			String seikyusakiKokyakuId = seikyusakiKokyakuInfo.getKokyakuId();

			// �ڋq�}�j���A����񃊃X�g
			List<RcpTKokyakuManual> commonManualInfoList = null;
			// ������ڋq���̌ڋq�h�c�ƃp�����[�^�̌ڋq�h�c���قȂ�ꍇ�A���ʃ}�j���A����񃊃X�g���擾
			if (seikyusakiKokyakuInfo != null
					&& !(model.getKokyakuId().equals(seikyusakiKokyakuId))) {

				// ���ʃ}�j���A����񃊃X�g�擾
				commonManualInfoList = kokyakuManualDao.selectBy(
						seikyusakiKokyakuId, null);

				// �Y�t�\�ő匏���ɒB���Ă��Ȃ��ꍇ�́A��̏����쐬
				if (commonManualInfoList != null) {
					for (int i = commonManualInfoList.size(); i < maxAppendableCount; i++) {
						commonManualInfoList.add(new RcpTKokyakuManual());
					}
				}
			}

			// �擾�������ʃ}�j���A������ݒ�
			TB027CommonManualListDto commonManualDto = new TB027CommonManualListDto();
			commonManualDto.setKokyakuId(seikyusakiKokyakuId);
			commonManualDto.setCommonManualList(commonManualInfoList);
			commonManualDtoList.add(commonManualDto);
		}

		model.setKokyaku(kokyaku);
		model.setSeikyusakiKokyakuList(seikyusakiKokyakuList);
		model.setKobetsuManualList(kobetsuManualList);
		model.setCommonManualDtoList(commonManualDtoList);

		return model;
	}

	/**
	 * �}�j���A���t�@�C���_�E�����[�h�̃`�F�b�N���s���܂��B
	 *
	 * @param model �ڋq�}�j���A���ꗗ��ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean isValidKokyakuManualInfo(TB027CustomerManualModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �Z�b�V�����̌������u40:�ϑ����SV�v�܂��́u41:�ϑ����OP�v�̏ꍇ
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			return outsourcerValidationLogic.isValid(userContext.getKaishaId(), model.getTargetKokyakuId());
		} else {
			return true;
		}
	}
}
