package jp.co.tokaigroup.tlcss_b2b.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB014WorkReportModel;

/**
 * ��ƕ񍐏�����T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 4.0 2014/07/22
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB014WorkReportServiceImpl extends TLCSSB2BBaseService
		implements TB014WorkReportService {

	/** ���Z�v�V�����˗��e�[�u��DAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/**
	 * ��ƕ񍐏��̃_�E�����[�h�`�F�b�N���s���܂��B
	 *
	 * @param model ��ƕ񍐏�������f��
	 * @return true:�`�F�b�NOK�Afalse:�`�F�b�NNG
	 */
	public boolean isOwn(TB014WorkReportModel model) {
		// �˗����̃`�F�b�N���s��
		return iraiDao.isOwn(model.getToiawaseNo(),
				model.getToiawaseRirekiNo(),
				model.getKokyakuId(),
				model.getGyoshaCd());
	}

}
