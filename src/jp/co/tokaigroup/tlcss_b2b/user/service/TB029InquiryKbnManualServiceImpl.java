package jp.co.tokaigroup.tlcss_b2b.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.dao.RcpMToiawaseManualDao;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseManual;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB029InquiryKbnManualModel;

/**
 * �₢���킹�敪�}�j���A���ꗗ�T�[�r�X�����N���X�B
 *
 * @author H.Yamamura
 * @version 1.0 2015/11/20
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB029InquiryKbnManualServiceImpl extends TLCSSB2BBaseService
		implements TB029InquiryKbnManualService {

	/** �₢���킹�敪�}�j���A���}�X�^DAO */
	@Autowired
	private RcpMToiawaseManualDao toiawaseManualDao;

	/**
	 * �_�E�����[�h����t�@�C�������擾���܂��B
	 *
	 * @param model �₢���킹�敪�}�j���A���ꗗ��ʃ��f��
	 * @return �t�@�C����
	 */
	public String getFileNm(TB029InquiryKbnManualModel model) {
		// �}�j���A�������擾
		RcpMToiawaseManual toiawaseManual = 
			toiawaseManualDao.selectByPrimaryKey(model.getToiawaseManualKey());

		// �}�j���A����񂪎擾�ł��Ȃ��ꍇ
		if (toiawaseManual == null) {
			// null��ԋp
			return null;
		} else {
			// �A�b�v���[�h�t�@�C������ԋp
			return toiawaseManual.getUploadFileNm();
		}
	}
}
