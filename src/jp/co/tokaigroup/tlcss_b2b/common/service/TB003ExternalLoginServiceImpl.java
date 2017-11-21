package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.List;

import jp.co.tokaigroup.reception.dao.TbMExtUrlDao;
import jp.co.tokaigroup.reception.entity.TbMExtUrl;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB003ExternalLoginModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �O�����O�C���T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/04/25
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB003ExternalLoginServiceImpl extends TLCSSB2BBaseService implements TB003ExternalLoginService {
	/** �O���T�C�g�V�X�e�� �O�����O�C���\�t�q�k�}�X�^DAO */
	@Autowired
	private TbMExtUrlDao extUrlDao;
	/**
	 * �p�����[�^�`�F�b�N���������s���܂��B
	 *
	 * @param model �O�����O�C����ʃ��f��
	 */
	public void validateParameter(TB003ExternalLoginModel model) {
		// �p�����[�^�`�F�b�N
		if (StringUtils.isBlank(model.getActionURL())) {
			// �J�ڐ�URL���擾�ł��Ȃ��ꍇ
			throw new ApplicationException("�J�ڐ�URL�s���Fnull");
		}

		// �J�ڐ�URL�̑Ó����`�F�b�N
		List<TbMExtUrl> extUrlList = extUrlDao.selectAll();
		if (extUrlList == null || extUrlList.isEmpty()) {
			// �O�����O�C���\�t�q�k�}�X�^���O���̏ꍇ�́A�G���[��ʂɑJ��
			throw new ApplicationException("�O�����O�C���\�t�q�k�}�X�^�ɒl���ݒ肳��Ă��܂���B");
		}

		// �p�����[�^�̑J�ڐ�URL���܂܂�Ă��邩�`�F�b�N
		boolean isExtUrlExists = false;
		for (TbMExtUrl extUrl : extUrlList) {
			if (model.getActionURL().equals(extUrl.getActionUrl())) {
				// ���݂��Ă����ꍇ�A�`�F�b�NOK�Ƃ���
				isExtUrlExists = true;
				break;
			}
		}

		if (!isExtUrlExists) {
			// �p�����[�^�̑J�ڐ�URL���܂܂�Ă��Ȃ������ꍇ�́A�G���[��ʂɑJ��
			throw new ApplicationException("�J�ڐ�URL�s���F�p�����[�^�̑J�ڐ�URL");
		}
	}
}
