package jp.co.tokaigroup.tlcss_b2b.user.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTUploadRirekiDao;
import jp.co.tokaigroup.reception.entity.TbTUploadRireki;
import jp.co.tokaigroup.reception.torikomi.model.RC701KokyakuKihonCSVTorikomiBean;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.CSVReader;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.dto.TB043CSVUploadCommonDto;
import jp.co.tokaigroup.tlcss_b2b.dto.TB043CSVUploadResultDto;
import jp.co.tokaigroup.tlcss_b2b.dto.TB043CustomerUploadForRealEstateDto;
import jp.co.tokaigroup.tlcss_b2b.dto.TB043CustomerUploadForTanantDto;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * �Ǘ����A�b�v���[�h���W�b�N�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/08/04
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB043CustomerUploadLogicImpl  extends TLCSSB2BBaseService
		implements TB043CustomerUploadLogic{

	/** �捞�t�@�C���p�X */
	private static final String UPLOAD_PATH_CUSTOMER = ResourceFactory.getResource().getString("UPLOAD_PATH_CUSTOMER");
	/** �捞�G���[�t�@�C���p�X */
	private static final String UPLOAD_PATH_CUSTOMER_ERROR = ResourceFactory.getResource().getString("UPLOAD_PATH_CUSTOMER_ERROR");
	/** �G���[�t�@�C���ڔ��� */
	private static final String UPLOAD_ERROR_FILE_SUFFIX = "_ERROR.csv";
	/** CSV�t�@�C���G���R�[�f�B���O */
	private static final String CSV_FILE_ENCODING = "Windows-31J";

	/** �A�b�v���[�h����DAO */
	@Autowired
	private TbTUploadRirekiDao tbTUploadRirekiDao;

	/** ���Z�v�V�����A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/**
	 * �Ǘ����A�b�v���[�h()��CSV�捞�������s���܂��B
	 *
	 * @param shubetsu ���
	 * @param uploadFile �A�b�v���[�h�t�@�C��
	 * @param uploadFileNm �A�b�v���[�h�t�@�C����
	 * @param loginId ���O�C���h�c
	 * @param fileComment �t�@�C���R�����g
	 * @return ��������DTO
	 */
	public TB043CustomerUploadModel executeCsvImport(TB043CustomerUploadModel model) {
		// CSV���[�_�[
		CSVReader reader = null;
		// DTO���X�g
		List<TB043CSVUploadCommonDto> inputDtoList = new ArrayList<TB043CSVUploadCommonDto>();
		// �G���[���
		ValidationPack errorInfo = new ValidationPack();
		// �G���[�t�@�C����
		String errFileNm = model.getUserContext().getKokyakuId() + "_" + model.getUploadFileFileName() + UPLOAD_ERROR_FILE_SUFFIX;
		// �G���[�t�@�C���p�X
		String errFilePath = UPLOAD_PATH_CUSTOMER_ERROR + System.getProperty("file.separator") + errFileNm;

		// ����DTO�̏����l�ݒ�
		TB043CSVUploadResultDto resultDto = new TB043CSVUploadResultDto();
		resultDto.setSuccess(true);

		try {
			// CSV�t�@�C���Ǎ���(�t�@�C����Shift_JIS)
			reader = new CSVReader(new InputStreamReader(
					new FileInputStream(model.getUploadFile()), CSV_FILE_ENCODING));

			// �^�C�g���s�͓ǂݔ�΂�
			reader.readNext();

			// ��s���ǂݍ���
			String[] csvData = null;
			// �^�C�g���s�𔲂����āA2�s�ڂ���̂��߁A2����X�^�[�g
			int lineNo = 2;
			while ((csvData = reader.readNext()) != null) {
				// �G���[�t���O�i�s�P�ʁj
				boolean isRowError = false;
				//---------- �`�F�b�N���� ----------//

				// ���ڐ��`�F�b�N
				ValidationPack columnVp = new ValidationPack();
				//�����̏ꍇ
				if(TB043CustomerUploadModel.FILE_TYPE_BUKKEN.equals(model.getFileType())) {
					if (csvData == null || csvData.length == 0 || TB043CustomerUploadForRealEstateDto.ITEM_COUNT != csvData.length) {
						columnVp.addError("MSG0026", Integer.toString(lineNo), String.valueOf(TB043CustomerUploadForRealEstateDto.ITEM_COUNT), "");
					}
				//�����҂̏ꍇ
				} else {
					if (csvData == null || csvData.length == 0 || TB043CustomerUploadForTanantDto.ITEM_COUNT != csvData.length) {
						columnVp.addError("MSG0026", Integer.toString(lineNo), String.valueOf(TB043CustomerUploadForTanantDto.ITEM_COUNT), "");
					}
				}
				if (columnVp.hasErrors()) {
					// ���ڐ��`�F�b�N�G���[������ꍇ
					errorInfo.addValidationPack(columnVp);
					lineNo++;

					isRowError = true;

					continue;
				}

				// DTO�ɕϊ�
				TB043CSVUploadCommonDto inputDto = null;

				if (TB043CustomerUploadModel.FILE_TYPE_BUKKEN.equals(model.getFileType())) {
					RC701KokyakuKihonCSVTorikomiBean<TB043CustomerUploadForRealEstateDto> bean =
						new RC701KokyakuKihonCSVTorikomiBean<TB043CustomerUploadForRealEstateDto>();

					inputDto = bean.convertToDto(csvData, TB043CustomerUploadForRealEstateDto.class);
					// ������E�����`�F�b�N
					if (!(inputDto instanceof TB043CustomerUploadForRealEstateDto)) {
						throw new ApplicationException("�Ǘ����A�b�v���[�h(�������)�̌^�ϊ��G���[");
					}

				} else {
					RC701KokyakuKihonCSVTorikomiBean<TB043CustomerUploadForTanantDto> bean =
						new RC701KokyakuKihonCSVTorikomiBean<TB043CustomerUploadForTanantDto>();

					inputDto = bean.convertToDto(csvData, TB043CustomerUploadForTanantDto.class);
					// ������E�����`�F�b�N
					if (!(inputDto instanceof TB043CustomerUploadForTanantDto)) {
						throw new ApplicationException("�Ǘ����A�b�v���[�h(�����ҏ��)�̌^�ϊ��G���[");
					}

				}

				// �o���f�[�g
				ValidationPack inputVp = inputDto.validate(inputDto, lineNo);
				if (inputVp.hasErrors()) {
					// ������E�����`�F�b�N�G���[������ꍇ
					errorInfo.addValidationPack(inputVp);

					isRowError = true;
				}

				if (!isRowError) {
					// �s�P�ʂł̃G���[���Ȃ��ꍇ
					// ����̃��R�[�h�̂ݒǉ�
					inputDtoList.add(inputDto);
				}

				lineNo++;
			}
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);
		} catch (IOException e) {
			throw new ApplicationException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					// close��Exception�͖���
				}
			}
		}

		// ��ł����������s���Ă����ꍇ�A�������I��
		if (errorInfo.hasErrors()) {
			// �G���[���ʂ�ݒ肵�A�����I��
			model.setCsvUploadFlg(TB043CustomerUploadModel.CSV_UPLOAD_FLG_ILLEGAL);

			// �G���[���ʂ�ݒ肵�A�����I��
			model.setErrorFilePath(errFilePath);
			model.setErrorInfo(errorInfo);
			model.setErrorFileFileName(errFileNm);
			return model;
		}


		insertTbTUploadRirekiAndCopyFile(model);

		return model;
	}

	/**
	 * �A�b�v���[�h�����e�[�u���o�^�����A�T�[�o�[�ւ̃t�@�C���R�s�[���s���܂��B
	 *
	 * @param shubetsu
	 * @param uploadFile
	 * @param uploadFileNm
	 * @param loginId
	 * @param fileComment
	 */
	public void insertTbTUploadRirekiAndCopyFile(TB043CustomerUploadModel model) {

		//---------- �A�b�v���[�h�����e�[�u���o�^���� ----------//
		TbTUploadRireki tbTUploadRireki = new TbTUploadRireki();
		// �A�ԐV�K�쐬
		BigDecimal newSeqNo = tbTUploadRirekiDao.nextVal();
		DecimalFormat df = new DecimalFormat("0000000");
		//�t�@�C���̊g���q���Ƃ�
		String suffix = FilenameUtils.getExtension(model.getUploadFileFileName());
		//�R�s�[��t�@�C���̍쐬
		String copyFileNm = model.getUserContext().getKokyakuId() + "_" + df.format(newSeqNo) + "." + suffix;
		String copyFilePath = UPLOAD_PATH_CUSTOMER + System.getProperty("file.separator") + "/" + copyFileNm;
		File copyFile = new File(copyFilePath);
		//���ݎ��Ԃ̎擾
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		//�A�b�v���[�h�����f�[�^�Z�b�g
		tbTUploadRireki.setSeqNo(newSeqNo);
		tbTUploadRireki.setKokyakuId(model.getUserContext().getKokyakuId());
		tbTUploadRireki.setUploadDt(nowTime);
		tbTUploadRireki.setShubetsu(model.getFileType());
		tbTUploadRireki.setUserFileNm(model.getUploadFileFileName());
		tbTUploadRireki.setCopyFileNm(copyFileNm);
		tbTUploadRireki.setRealFileNm(FilenameUtils.getName(new File(copyFilePath).getName()));
		tbTUploadRireki.setFileComment(model.getFileComment());
		tbTUploadRireki.setJujuJokyo(TbTUploadRireki.KAKUNIN_JOKYO_MIKAKUNIN);
		tbTUploadRireki.setCreId(model.getUserContext().getKokyakuId());
		tbTUploadRireki.setCreDt(nowTime);
		tbTUploadRireki.setUpdDt(nowTime);


		//�A�b�v���[�h�����e�[�u���f�[�^�o�^
		tbTUploadRirekiDao.insert(tbTUploadRireki);

		//---------- �A�N�Z�X���O�o�^���� ----------//
		insertAccessLog(tbTUploadRireki, TB043CustomerUploadModel.GAMEN_NM,
				TB043CustomerUploadModel.BUTTON_NM_UPLOAD);

		//�T�[�o�ɃR�s�[����
		try {
			FileUtils.copyFile(model.getUploadFile(), copyFile);
		} catch (IOException e) {
			throw new ApplicationException("�t�@�C���̃R�s�[�̎��s�B");
		}
	}

	/**
	 * �A�N�Z�X���O�ɏ������e��o�^���܂��B
	 *
	 * @param shoriRireki ���������e�[�u�����
	 * @param buttonNm �{�^����
	 * @return �o�^����
	 */
	private int insertAccessLog(TbTUploadRireki tbTUploadRireki, String gamenNm, String buttonNm) {
		NullExclusionToStringBuilder entryEntity =
			new NullExclusionToStringBuilder(
				tbTUploadRireki,
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// ���O���鍀��
		List<String> excludeFiledList = new ArrayList<String>(Arrays.asList(Constants.EXCLUDE_FIELD_NAMES));

		entryEntity.setExcludeFieldNames((String[]) excludeFiledList.toArray(new String[0]));

		// �A�N�Z�X���O�o�^
		int entryCnt = tbAccesslogDao.insert(
				gamenNm,
				buttonNm,
				entryEntity.toString());

		return entryCnt;
	}
}
