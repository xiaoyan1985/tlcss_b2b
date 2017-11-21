package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB011RealEstateAgencySelectTestModel;

/**
 * �s���Y�E�Ǘ���БI����ʁi�X�^�u�j�T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB011RealEstateAgencySelectTestServiceImpl extends TLCSSB2BBaseService
	implements TB011RealEstateAgencySelectTestService {
	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/**
	 * �ڋq��񃊃X�g���擾���܂��B
	 *
	 * @param model �s���Y�E�Ǘ���БI����ʁi�X�^�u�j���f��
	 * @return �s���Y�E�Ǘ���БI����ʁi�X�^�u�j���f��
	 */
	public TB011RealEstateAgencySelectTestModel getKokyakuList(TB011RealEstateAgencySelectTestModel model) {
		List<RcpMKokyaku> kokyakuList =
			kokyakuDao.selectByCondition(model.getCondition(), "0");

		model.setKokyakuList(kokyakuList);

		return model;
	}

}
