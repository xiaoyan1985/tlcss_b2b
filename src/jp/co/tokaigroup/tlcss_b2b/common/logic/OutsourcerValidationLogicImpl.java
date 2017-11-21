package jp.co.tokaigroup.tlcss_b2b.common.logic;

import jp.co.tokaigroup.reception.dao.RcpMKokyakuKanrenDao;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * �ϑ���Њ֘A�`�F�b�N���W�b�N
 *
 * @author k003316
 * @version 1.0 2015/07/28
 */
@Service
public class OutsourcerValidationLogicImpl implements OutsourcerValidationLogic {

	/** ���Z�v�V�����ڋq�֘A�t���}�X�^DAO */
	@Autowired
	private RcpMKokyakuKanrenDao kokyakuKanrenDao;

	/**
	 * �ϑ���ЎQ�ƌڋq�}�X�^�ɓo�^�ς̌ڋq�h�c�̊֘A�ڋq�h�c�ł��邱�Ƃ��`�F�b�N����B
	 * 
	 * @param kaishaId ��Ђh�c
	 * @param kokyakuId �ڋq�h�c
	 * @return true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean isValid(String kaishaId, String kokyakuId) {
		
		// �p�����[�^�`�F�b�N
		if (StringUtils.isBlank(kaishaId)) {
			throw new ApplicationException("��Ђh�c�s���F�p�����[�^�̉�Ђh�c");
		}
		
		if (StringUtils.isBlank(kokyakuId)) {
			throw new ApplicationException("�ڋq�h�c�s���F�p�����[�^�̌ڋq�h�c");
		}
		
		// �֘A�t���`�F�b�N
		return kokyakuKanrenDao.isValidRefKanren(kaishaId, kokyakuId);
	}
}
