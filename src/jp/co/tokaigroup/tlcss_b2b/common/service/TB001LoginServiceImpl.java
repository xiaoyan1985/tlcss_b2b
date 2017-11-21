package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMSystemDao;
import jp.co.tokaigroup.reception.dao.TbMRefKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMRoleUrlDao;
import jp.co.tokaigroup.reception.dao.TbMUserDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.EncryptionUtil;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB001LoginModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ���O�C���T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/04/22
 * @version 1.1 2015/08/25 v145527 �ϑ���Ђh�c�ǉ��Ή�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB001LoginServiceImpl extends TLCSSB2BBaseService implements TB001LoginService {
	/** �O���T�C�g�V�X�e�� ���[�U�[�}�X�^DAO */
	@Autowired
	private TbMUserDao userDao;

	/** �O���T�C�g�V�X�e�� �A�N�Z�X�\�t�q�k�}�X�^DAO */
	@Autowired
	private TbMRoleUrlDao roleUrlDao;

	/** ���Z�v�V���� �V�X�e���}�X�^DAO */
	@Autowired
	private RcpMSystemDao systemDao;

	/** ���Z�v�V���� �O���[�v�ڋq�}�X�^�i�s�n�j�`�h�p�j */
	@Autowired
	private TbMRefKokyakuDao refKokyakuDao;

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/**
	 * ���O�C���������s���܂��B
	 *
	 * @param model ���O�C����ʃ��f��
	 * @return ���O�C����ʃ��f��
	 */
	public TB001LoginModel executeLogin(TB001LoginModel model) {
		// �V�X�e���}�X�^�S���擾
		List<RcpMSystem> systemList = systemDao.selectAll();

		Map<String, String> systemMap = new HashMap<String, String>();
		// �V�X�e���}�X�^�̒l��Map�ɂ���
		for (RcpMSystem system : systemList) {
			systemMap.put(system.getCode(), system.getValue());
		}

		//---------- ���O�C���`�F�b�N���� �������� ----------//
		// ���O�C���h�c���݃`�F�b�N
		TbMUser user = userDao.selectByLoginId(model.getLoginId());
		if (user == null || user.isDeleted()) {
			// ���O�C���h�c�����݂��Ȃ��A�܂��́A�폜����Ă���ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0008"));
		}

		//�������u20�F�s���Y�E�Ǘ���Ёv�̏ꍇ�A���[�U�[�O���[�s���O�����擾����B
		if (user.isRealEstate()) {
			// ���[�U�[�}�X�^�̌ڋq�����擾����B
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(user.getKokyakuId());
			if (kokyaku == null || kokyaku.isKeiyakuKanriEndFlgExpired()) {
				// �ڋq��񂪎擾�ł��Ȃ��A�܂��͌_��Ǘ��I���t���O���u2�F�I���A�ێ����Ԍo�߁v�̏ꍇ
				throw new ValidationException(new ValidationPack().addError("MSG0029"));
			}
			// �ڋq�}�X�^����ݒ肷��
			model.setKokyakuInfo(kokyaku);
			// �Q�ƌڋq���X�g��ݒ肵�܂��B
			model.setRefKokyakuList(refKokyakuDao.selectBy(user.getKokyakuId(), null));
			if (model.getRefKokyakuList() == null || model.getRefKokyakuList().isEmpty()) {
				// �Q�ƌڋq���ꌏ���Ȃ��ꍇ
				throw new ValidationException(new ValidationPack().addError("MSG0029"));
			}
		}

		// �������u10:TOKAI�Ǘ���Ёv�A�u11:TOKAI��ʁv�A�u40:�ϑ����SV�v�A�u41:�ϑ����OP�v�̏ꍇ
		if (user.isAdministrativeInhouse()
			|| user.isGeneralInhouse()
			|| user.isOutsourcerSv()
			|| user.isOutsourcerOp()) {
			
			if (StringUtils.isBlank(user.getKaishaId())) {
				// �ϑ���Ђh�c��NULL�̏ꍇ
				throw new ValidationException(new ValidationPack().addError("MSG0008"));
			}
		}

		// �p�X���[�h�`�F�b�N
		String hashPassword = EncryptionUtil.encrypt(model.getPasswd());

		if (!user.getPasswd().equals(hashPassword)) {
			// ���͂��ꂽ�p�X���[�h�ƃn�b�V�������ꂽ�p�X���[�h����v���Ȃ��ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0008"));
		}
		//---------- ���O�C���`�F�b�N���� �����܂� ----------//

		// �p�X���[�h�����؂�̃`�F�b�N
		// �V�X�e�����t
		Timestamp sysDate = DateUtil.toTimestamp(
				DateUtil.getSysDateString("yyyy/MM/dd"), "yyyy/MM/dd");
		// �p�X���[�h�����I����
		Timestamp passWdKigenDt = user.getPasswdKigenDt();

		model.setUserInfo(user);
		model.setSystemConstants(systemMap);

		// �c��L�������̎擾
		model.setValidDateCount(NumberUtils.toInt(
				DateUtil.differenceDate(passWdKigenDt, sysDate)));

		// �A�N�Z�X�\�t�q�k�}�X�^����A�N�Z�X�\�t�q�kMap�擾
		Map<String, TbMRoleUrl> accessibleMap =
			roleUrlDao.selectAccessibleMap(user.getRole());

		model.setAccessibleMap(accessibleMap);

		return model;
	}
}
