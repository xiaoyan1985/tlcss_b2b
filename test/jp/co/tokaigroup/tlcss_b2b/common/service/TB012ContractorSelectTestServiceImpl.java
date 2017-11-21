package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.List;

import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dto.RC061GyoshaSearchDto;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB012ContractorSelectTestModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �ƎґI����ʁi�X�^�u�j�T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/29
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB012ContractorSelectTestServiceImpl extends TLCSSB2BBaseService
		implements TB012ContractorSelectTestService {
	/** ���Z�v�V�����Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/**
	 * �Ǝ҃��X�g���擾���܂��B
	 *
	 * @param model �ƎґI����ʁi�X�^�u�j���f��
	 * @return �ƎґI����ʁi�X�^�u�j���f��
	 */
	public TB012ContractorSelectTestModel getGyoshaList(TB012ContractorSelectTestModel model) {
		List<RC061GyoshaSearchDto> resultList = gyoshaDao.selectByCondition(model.getCondition());

		model.setGyoshaList(resultList);

		return model;
	}
}
