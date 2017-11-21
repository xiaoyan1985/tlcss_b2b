package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB012ContractorSelectModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �s���Y��Ǘ���БI���T�[�r�X�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB012ContractorSelectServiceImpl extends TLCSSB2BBaseService
		implements TB012ContractorSelectService {

	/** �T�[�r�X�lDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/**
	 * �������������s���܂��B
	 *
	 * @param model �₢���킹������ʃ��f��
	 * @return �₢���킹������ʃ��f��
	 */
	public TB012ContractorSelectModel executeSearch(TB012ContractorSelectModel model) throws ValidationException {

		// �����������s
		model.setResultList(gyoshaDao.selectContractor(model.getCondition()));

		return model;
	}

}
