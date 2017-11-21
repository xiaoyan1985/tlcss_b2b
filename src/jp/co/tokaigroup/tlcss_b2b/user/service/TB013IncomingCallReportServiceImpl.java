package jp.co.tokaigroup.tlcss_b2b.user.service;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB013IncomingCallReportModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * ���d�񍐏�����T�[�r�X�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 1.1 2015/12/11 S.Nakano isOwn���\�b�h�폜�AgetDownloadInfo���\�b�h�ǉ�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB013IncomingCallReportServiceImpl extends TLCSSB2BBaseService
		implements TB013IncomingCallReportService {

	/** ���Z�v�V�����₢���킹�e�[�u��DAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/**
	 * �_�E�����[�h���̎擾���s���܂��B
	 * 
	 * @param model ���d�񍐏�������f��
	 * @return ���d�񍐏�������f��
	 */
	public TB013IncomingCallReportModel getDowloadInfo(TB013IncomingCallReportModel model) {
		// �Z�b�V�������̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		model.setDownloadable(false);
		
		// TOKAI�Ǘ��҈ȊO�̏ꍇ�A�₢���킹���̔�����s���B
		if (!userContext.isInhouse()) {
			if (!toiawaseDao.isOwn(model.getToiawaseNo(), userContext.getRefKokyakuId())) {
				// �₢���킹���`�F�b�NNG�̏ꍇ�A�_�E�����[�h�G���[
				return model;
			}
		}
		
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// �₢���킹��񂪎擾�ł��Ȃ��ꍇ�A�_�E�����[�h�G���[
			return model;
		}
		
		model.setDownloadable(true);
		model.setToiawase(toiawase);
		
		return model;
	}
}