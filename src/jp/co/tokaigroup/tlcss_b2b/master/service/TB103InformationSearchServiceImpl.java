package jp.co.tokaigroup.tlcss_b2b.master.service;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTInformationDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB103InformationSearchModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ���m�点������ʃT�[�r�X�����N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/18
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB103InformationSearchServiceImpl  extends TLCSSB2BBaseService
			implements TB103InformationSearchService {

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** ���m�点�e�[�u��DAO */
	@Autowired
	private TbTInformationDao infoDao;

	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/**
	 * �������擾�������s���܂��B
	 *
	 * @param model ���m�点������ʃ��f��
	 * @return ���m�点������ʃ��f��
	 */
	public TB103InformationSearchModel getInitInfo(TB103InformationSearchModel model) {
		// ���ʃR�[�h�}�X�^����Map���擾���A�\���Ώۗp���X�g��ݒ肷��
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET);
		model.setTargetList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET));

		return model;
	}

	/**
	 * �����������s���܂��B
	 *
	 * @param model ���m�点������ʃ��f��
	 * @return ���m�点������ʃ��f��
	 */
	public TB103InformationSearchModel search(TB103InformationSearchModel model) {
		// �������擾����
		model = getInitInfo(model);

		// ���m�点�e�[�u���������ʃ��X�g���擾
		List<TbTInformation> resultList = infoDao.selectByCondition(model.getCondition());
		// sql���o�̊������̂�TbTInformation�ɍ��ڒǉ����邱��

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET);
		Map<String, Map<String, RcpMComCd>> convertMap =
				comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �a���ϊ�����
		Map<String, RcpMComCd> targetMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET);
		for (int i = 0; i < resultList.size(); i++) {
			TbTInformation info = resultList.get(i);
			String targetDisplay = "";

			// �\���Ώۂ̖��̐ݒ�
			if (StringUtils.isNotBlank(info.getKanjiNm1())) {
				// ���������P�����݂���ꍇ
				targetDisplay = info.getKokyakuId() + "<br>" + info.getKanjiNm1() + ((StringUtils.isNotBlank(info.getKanjiNm2()) ? (" " + info.getKanjiNm2()) : "" ));
			} else if (StringUtils.isNotBlank(info.getGyoshaNm())) {
				// �ƎҖ������݂���ꍇ
				targetDisplay = info.getGyoshaCd() + "<br>" + info.getGyoshaNm();
			} else if (StringUtils.isNotBlank(info.getKaishaNm())) {
				// ��Ж������݂���ꍇ
				targetDisplay = info.getKaishaId() + "<br>" + info.getKaishaNm();
			} else if (StringUtils.isNotBlank(info.getTarget()) && targetMap.containsKey(info.getTarget())) {
				// ���������P�A�Ɩ��������݂��Ȃ��ꍇ
				targetDisplay = targetMap.get(info.getTarget()).getExternalSiteVal();
			}
			info.setTargetDisplay(targetDisplay);
			resultList.set(i, info);
		}

		// �A�N�Z�X���O�̓o�^����
		tbAccesslogDao.insert(TB103InformationSearchModel.GAMEN_NM,
				Constants.BUTTON_NM_SEARCH, createKensakuJoken(model));

		model.setResultList(resultList);

		return model;
	}

	/**
	 * �폜�������s���܂��B
	 *
	 * @param model ���m�点������ʃ��f��
	 * @return ���m�点������ʃ��f��
	 */
	public void delete(TB103InformationSearchModel model) {
		// ���m�点���̍폜����
		int result = infoDao.deleteBy(model.getSeqNo());
		if (result == 0) {
			// ���m�点��񂪑��݂��Ȃ��ꍇ�́A�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// �A�N�Z�X���O�̓o�^����
		tbAccesslogDao.insert(TB103InformationSearchModel.GAMEN_NM,
				Constants.BUTTON_NM_DELETE, createKensakuJoken(model));

		return;
	}


	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model ���m�点������ʃ��f��
	 * @return ��������
	 */
	private String createKensakuJoken(TB103InformationSearchModel model) {
		NullExclusionToStringBuilder searchEntry =
			new NullExclusionToStringBuilder(
				model.getCondition(),
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// ���O���鍀��
		searchEntry.setExcludeFieldNames(Constants.EXCLUDE_FIELD_NAMES);

		return searchEntry.toString();
	}

}
