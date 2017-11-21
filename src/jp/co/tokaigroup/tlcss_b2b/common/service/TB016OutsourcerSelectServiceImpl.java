package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB016OutsourcerSelectModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �ϑ���БI���T�[�r�X�����N���X�B
 *
 * @author k002785
 * @version 1.0 2015/09/08
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB016OutsourcerSelectServiceImpl extends TLCSSB2BBaseService
		implements TB016OutsourcerSelectService {

	/** ��Ѓ}�X�^ */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/**
	 * �������������s���܂��B
	 *
	 * @param model �ϑ���БI����ʃ��f��
	 * @return �ϑ���БI����ʃ��f��
	 */
	public TB016OutsourcerSelectModel executeSearch(TB016OutsourcerSelectModel model) {

		// �����������s
		model.setResultList(kaishaDao.selectOutsourcerList(model.getCondition()));

		// ���f����ԋp
		return model;
	}
}
