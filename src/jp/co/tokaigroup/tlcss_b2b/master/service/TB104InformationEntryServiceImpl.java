package jp.co.tokaigroup.tlcss_b2b.master.service;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTInformationDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB104InformationEntryModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ���m�点�o�^�T�[�r�X�����N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/20
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB104InformationEntryServiceImpl extends TLCSSB2BBaseService
		implements TB104InformationEntryService {

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** ���m�点�e�[�u��DAO */
	@Autowired
	private TbTInformationDao infoDao;

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** ���Z�v�V�����Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/** ��Ѓ}�X�^DAO */
	@Autowired
	private TbMKaishaDao kaishaDao;
	
	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/**
	 * �����\���������s���܂��B
	 *
	 * @param model ���m�点�o�^��ʃ��f��
	 * @return ���m�点�o�^��ʃ��f��
	 */
	public TB104InformationEntryModel getInitInfo(TB104InformationEntryModel model) {
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET);

		// �������X�g�̎擾
		List<RcpMComCd> targetList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET);

		model.setTargetList(targetList);

		return model;
	}

	/**
	 * ���m�点���擾�������s���܂��B
	 *
	 * @param model ���m�点�o�^��ʃ��f��
	 * @return ���m�点�o�^��ʃ��f��
	 */
	public TB104InformationEntryModel getUpdateInitInfo(TB104InformationEntryModel model) {
		// �����\�������Ăяo��
		model = this.getInitInfo(model);

		// ���m�点���̎擾
		TbTInformation info = infoDao.selectByPrimaryKey(model.getSeqNo());
		if (info == null) {
			// ���m�点��񂪑��݂��Ȃ��ꍇ�́A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// ���̎擾����
		if (StringUtils.isNotBlank(info.getKokyakuId())) {
			// �ڋqID��null�ȊO�̏ꍇ�A�ڋq�����擾
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(info.getKokyakuId());
			if (kokyaku == null) {
				throw new ApplicationException("�ڋq�}�X�^��񂪑��݂��܂���B");
			}
			info.setKokyakuNm(kokyaku.getKanjiNm1() + ((StringUtils.isNotBlank(kokyaku.getKanjiNm2()) ? (" " + kokyaku.getKanjiNm2()) : "" )));

		} else if (StringUtils.isNotBlank(info.getGyoshaCd())) {
			// �Ǝ҃R�[�h��null�ȊO�̏ꍇ�A�Ǝҏ����擾
			RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(info.getGyoshaCd());
			if (gyosha == null) {
				throw new ApplicationException("�Ǝ҃}�X�^��񂪑��݂��܂���B");
			}
			info.setGyoshaNm(gyosha.getGyoshaNm());

		} else if (StringUtils.isNotBlank(info.getKaishaId())) {
			// ��Ђh�c��null�ȊO�̏ꍇ�A��Џ����擾
			TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(info.getKaishaId());
			if (kaisha == null) {
				throw new ApplicationException("��Ѓ}�X�^��񂪑��݂��܂���B");
			}
			info.setKaishaNm(kaisha.getKaishaNm());
		}
		model.setInfo(info);

		return model;
	}

	/**
	 * ���m�点���o�^�������s���܂��B
	 *
	 * @param model ���m�点�o�^��ʃ��f��
	 */
	public TB104InformationEntryModel insertInfo(TB104InformationEntryModel model) {
		// �o�^�`�F�b�N���� ������͕s�v

		TbTInformation info = model.getInfo();
		// �A�Ԏ擾����
		info.setSeqNo(infoDao.nextVal());

		// ���m�点���o�^
		infoDao.insert(info);

		// �A�N�Z�X���O�ւ̓o�^����
		tbAccesslogDao.insert(TB104InformationEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_INSERT, createKensakuJoken(model));

		model.setInfo(info);

		return model;
	}

	/**
	 * ���m�点���X�V�������s���܂��B
	 *
	 * @param model ���m�点�o�^��ʃ��f��
	 */
	public void updateInfo(TB104InformationEntryModel model) {
		// �o�^�`�F�b�N���� ������͕s�v

		TbTInformation info = model.getInfo();

		// ���m�点���X�V
		int result = infoDao.update(info);

		if (result == 0) {
			// �X�V����0���̏ꍇ�́A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// �A�N�Z�X���O�ւ̓o�^����
		tbAccesslogDao.insert(TB104InformationEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_UPDATE, createKensakuJoken(model));

	}

	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model ���[�U�[�}�X�^�o�^��ʃ��f��
	 * @return �A�N�Z�X���O�ɓo�^���錟������
	 */
	private String createKensakuJoken(TB104InformationEntryModel model) {
		NullExclusionToStringBuilder entryContents =
			new NullExclusionToStringBuilder(
				model.getInfo(),
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// ���O���鍀��
		entryContents.setExcludeFieldNames(Constants.EXCLUDE_FIELD_NAMES);

		return entryContents.toString();
	}

}
