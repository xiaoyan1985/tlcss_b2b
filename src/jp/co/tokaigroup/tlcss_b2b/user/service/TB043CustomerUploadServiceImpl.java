package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;


import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;


import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTUploadRirekiDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbTUploadRireki;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;

import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;

import jp.co.tokaigroup.tlcss_b2b.user.logic.TB043CustomerUploadLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * �Ǘ����A�b�v���[�h�T�[�r�X�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/08/04
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB043CustomerUploadServiceImpl extends TLCSSB2BBaseService
		implements TB043CustomerUploadService {

	/** CSV�捞�������s�\�����i�ŏ��j */
	private static final int EXECUTABLE_CSV_TORIKOMI_MIN = 1;

	/** �A�b�v���[�h����DAO */
	@Autowired
	private TbTUploadRirekiDao tbTUploadRirekiDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** ���Z�v�V�����A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao accesslogDao;

	/** �Ǘ����A�b�v���[�h���W�b�N */
	@Autowired
	private TB043CustomerUploadLogic customerUploadLogic;

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �Ǘ����A�b�v���[�h��ʃ��f��
	 * @return �Ǘ����A�b�v���[�h��ʃ��f��
	 */
	public TB043CustomerUploadModel getInitInfo(TB043CustomerUploadModel model)
	throws ValidationException {

		List<TbTUploadRireki> tbTUploadRirekiList = new ArrayList<TbTUploadRireki>();

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_CSV_UPLOAD,
				RcpMComCd.RCP_M_COM_CD_FLG_JUJU_JOKYO);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// CSV�A�b�v���[�hMap
		Map<String, RcpMComCd> CsvUploadMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_CSV_UPLOAD);
		// ����󋵊m�FMap
		Map<String, RcpMComCd> JujuJokyoMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_FLG_JUJU_JOKYO);

		//�A�b�v���[�h�����ꗗ�̎擾
		for(TbTUploadRireki tbTUploadRireki:tbTUploadRirekiDao.selectByKokyakuId(model.getUserContext().getKokyakuId())) {
			if (StringUtils.isNotBlank(tbTUploadRireki.getShubetsu())) {
				tbTUploadRireki.setShubetsuNm(CsvUploadMap.get(tbTUploadRireki.getShubetsu()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(tbTUploadRireki.getJujuJokyo())) {
				tbTUploadRireki.setKakuninJokyoNm(JujuJokyoMap.get(tbTUploadRireki.getJujuJokyo()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(tbTUploadRireki.getRealFileNm())) {
				tbTUploadRireki.setCopyFileNm(new File(tbTUploadRireki.getRealFileNm()).getName());
			}
			tbTUploadRirekiList.add(tbTUploadRireki);
		}

		model.setResultList(tbTUploadRirekiList);

		return model;
	}

	/**
	 * �Ǘ����A�b�v���[�h��CSV�捞�������s���܂��B
	 *
	 * @param model �Ǘ����A�b�v���[�h��ʃ��f��
	 */
	@Transactional(value="txManager")
	public TB043CustomerUploadModel executeCsvUpload(TB043CustomerUploadModel model) {

		// �����\������
		model = getInitInfo(model);

		// ���ʂ̏����ݒ�
		model.setCsvUploadFlg(TB043CustomerUploadModel.CSV_UPLOAD_FLG_NORMAL);

		if(TB043CustomerUploadModel.FILE_TYPE_BUKKEN.equals(model.getFileType())
				|| TB043CustomerUploadModel.FILE_TYPE_NYUKYOSHA.equals(model.getFileType())) {
			// �����`�F�b�N
			validateExecutableCsvTorikomi(model.getUploadFile());
			// CSV�捞�������s
			model = customerUploadLogic.executeCsvImport(model);

		} else if (TB043CustomerUploadModel.FILE_TYPE_SONOTA.equals(model.getFileType())) {
			// CSV�捞�������s
			customerUploadLogic.insertTbTUploadRirekiAndCopyFile(model);
		}

		return model;
	}

	/**
	 * �A�b�v���[�h�����̍폜�������s���܂��B
	 *
	 * @param model �Ǘ����A�b�v���[�h��ʃ��f��
	 */
	@Transactional(value="txManager")
	public TB043CustomerUploadModel deleteUploadRireki(TB043CustomerUploadModel model) {

		//��������
		TbTUploadRireki tbTUploadRireki = tbTUploadRirekiDao.selectByPrimaryKey(model.getDeleteSeqNo());

		if (tbTUploadRireki == null) {
			//�Y���̃A�b�v���[�h������񂪂Ȃ��ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}else if (TbTUploadRireki.KAKUNIN_JOKYO_KOKAIZUMI.equals(tbTUploadRireki.getJujuJokyo())) {
			//����󋵂����ς݂̏ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0028"));
		}

		//�폜����
		if (tbTUploadRirekiDao.deleteBy(model.getDeleteSeqNo()) == 0) {
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		//�A�N�Z�X���O�o�^
		accesslogDao.insert(TB043CustomerUploadModel.GAMEN_NM,
							TB043CustomerUploadModel.BUTTON_NM_DELETE,
							"seqNo=" + model.getDeleteSeqNo());

		//�A�b�v���[�h���ꂽ�t�@�C���̍폜
		File deleteFile = new File(tbTUploadRireki.getRealFileNm());
		FileUtils.deleteQuietly(deleteFile);

		return model;
	}

	/**
	 * CSV�捞���������s�\�����肵�܂��B
	 *
	 * @param csvDataList CSV�f�[�^���X�g
	 */
	private void validateExecutableCsvTorikomi(File uploadFile) {

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		int max = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CSV_TORIKOMI_TO_MAX);

		// �t�@�C������s�����擾
		BufferedReader reader = null;
		int readLines = 0;
		try {
			reader = new BufferedReader(new FileReader(uploadFile));

			// �^�C�g���s���̓X�L�b�v
			reader.readLine();
			while (reader.readLine() != null) {
				if (readLines > max) {
					// ���ɏ���l�𒴂�����A���[�v�𔲂���
					break;
				}
				readLines++;
			}
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);
		} catch (IOException e) {
			throw new ApplicationException(e);
		}

		if (!(EXECUTABLE_CSV_TORIKOMI_MIN <= readLines && readLines <= max)) {
			// �ŏ������ȉ��A�ő匏���ȏ�̏ꍇ
			throw new ValidationException(new ValidationPack().addError("MSG0025", Integer.toString(max)));
		}
	}
}

