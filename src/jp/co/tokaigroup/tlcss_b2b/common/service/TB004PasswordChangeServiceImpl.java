package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.reception.dao.TbMUserDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.EncryptionUtil;
import jp.co.tokaigroup.si.fw.util.StringUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB004PasswordChangeModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �p�X���[�h�ύX�T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 4.1 2014/06/09
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB004PasswordChangeServiceImpl extends TLCSSB2BBaseService implements TB004PasswordChangeService {
	/** �p�X���[�h�ύX���̃A�N�Z�X���O�̌������� */
	private static final String INSERT_ACCESS_LOG_KENSAKU_JOKEN = "-";

	/** �O���T�C�g�V�X�e�� ���[�U�[�}�X�^DAO */
	@Autowired
	private TbMUserDao userDao;

	/** �O���T�C�g�V�X�e�� �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/**
	 * �p�X���[�h�X�V�������s���܂��B
	 *
	 * @param model �p�X���[�h�ύX��ʃ��f��
	 */
	@Transactional(value="txManager")
	public void updatePassword(TB004PasswordChangeModel model) {
		// �p�X���[�h�̑Ó����`�F�b�N
		validatePassword(model);

		// �V�p�X���[�h���n�b�V����
		String hashPassword = EncryptionUtil.encrypt(model.getNewPassword());

		// ���[�U�[�}�X�^�̍X�V
		userDao.updateForPasswordChange(createUserInfoForPasswordChange(hashPassword));

		// �O���T�C�g�A�N�Z�X���O�̓o�^
		tbAccesslogDao.insert(TB004PasswordChangeModel.GAMEN_NM,
				Constants.BUTTON_NM_CHANGE, INSERT_ACCESS_LOG_KENSAKU_JOKEN);
	}

	/**
	 * �p�X���[�h�̑Ó����`�F�b�N���s���܂��B
	 *
	 * @param model �p�X���[�h�ύX��ʃ��f��
	 */
	private void validatePassword(TB004PasswordChangeModel model) {
		// ���݂̃p�X���[�h���̓`�F�b�N
		TbMUser user = userDao.selectByLoginId(getUserContext().getLoginId());

		// ���݂̃p�X���[�h���n�b�V����
		String hashPassword = EncryptionUtil.encrypt(model.getCurrentPassword());
		if (!user.getPasswd().equals(hashPassword)) {
			// �p�X���[�h���Ԉ���Ă����ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0013"));
		}
		//�V�p�X���[�h�����O�C��ID�Ɠ����ꍇ
		if (model.getNewPassword().equals(getUserContext().getLoginId())) {
			throw new ValidationException(new ValidationPack().addError("MSG0018"));
		}
		//�V�p�X���[�h���A�p���A�����A�L������2��ވȏ�A�܂܂�Ă��邩�𔻒�
		if (StringUtil.countCharacterType(model.getNewPassword()) < 2){
			//�V�����p�X���[�h�̓��̓`�F�b�N��NG�̏ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0017"));
		}
	}

	/**
	 * �p�X���[�h�ύX���s�����߂̃��[�U�[�}�X�^���𐶐����܂��B
	 *
	 * @param newPassword �V�p�X���[�h(�n�b�V������)
	 * @return ���[�U�[�}�X�^���
	 */
	private TbMUser createUserInfoForPasswordChange(String newPassword) {
		TbMUser user = new TbMUser();

		user.setLoginId(getUserContext().getLoginId());
		user.setPasswd(newPassword);

		return user;
	}

}
