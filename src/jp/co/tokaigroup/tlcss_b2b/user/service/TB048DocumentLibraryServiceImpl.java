package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.TbTPublishFileDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbTPublishFile;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB048DocumentLibraryModel;

/**
 * �������C�u�����ꗗ�T�[�r�X�����N���X�B
 *
 * @author C.Kobayashi
 * @version 1.0 2015/10/28
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB048DocumentLibraryServiceImpl extends TLCSSB2BBaseService
	implements TB048DocumentLibraryService {

	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** TORES�������C�u�����e�[�u��DAO */
	@Autowired
	private TbTPublishFileDao publishFileDao;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return TORES�������C�u�����ꗗ��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	public TB048DocumentLibraryModel getInitInfo(TB048DocumentLibraryModel model) {
		// �����\���p�����[�^�`�F�b�N
		if (StringUtils.isBlank(model.getKokyakuId())) {
			// �p�����[�^�̌ڋq�h�c���擾�ł��Ȃ��ꍇ�́A�p�����[�^�G���[
			throw new ApplicationException("�p�����[�^�̌ڋq�h�c���󗓂ł��B");
		}

		// �Z�b�V��������Y�t�\�ő匏�����擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		int maxAppendableCount = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_PUBLISH_FILE_TO_MAX);

		// �ڋq��{���擾
		RcpMKokyaku kokyaku = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		if (kokyaku == null) {
			// �ڋq��{��񂪎擾�ł��Ȃ��ꍇ�́A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// �������C�u�����ꗗ�擾
		List<TbTPublishFile> publishFileList = publishFileDao.selectBy(model.getKokyakuId(), null);
		
		// �������C�u�����ꗗ�\�����Ƀ��X�g�̐����g�����߁A�l��ޔ�
		model.setListSize(publishFileList.size());
		
		if (publishFileList != null) {
			if (publishFileList.size() > maxAppendableCount) {
				// �Y�t�\�ő匏����胊�X�g�̐��������ꍇ
				// �Y�t�\�ő匏�����̂݁A���X�g��\��
				publishFileList = publishFileList.subList(0, maxAppendableCount);
			} else if (publishFileList.size() < maxAppendableCount) {
				// �Y�t�\�ő匏���ɒB���Ă��Ȃ��ꍇ�́A��̏����쐬
				for (int i = publishFileList.size(); i < maxAppendableCount; i++) {
					publishFileList.add(new TbTPublishFile());
				}
			}
		}

		model.setKokyaku(kokyaku);
		model.setPublishFileList(publishFileList);

		return model;
	}
}
