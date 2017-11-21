package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB041CustomerSearchModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �����E�����Ҍ����T�[�r�X�����N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/03
 * @version 4.1 2016/07/28 S.Nakano �������s���Y�E�Ǘ���Ђ̏ꍇ�̌_��T�[�r�X���X�g�擾���C��
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB041CustomerSearchServiceImpl  extends TLCSSB2BBaseService
			implements TB041CustomerSearchService {

	/** �ڋq�_��lDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** �ڋq�lDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** ���Z�v�V�����T�[�r�X�}�X�^DAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/**
	 * �������擾�������s���܂��B
	 *
	 * @param model �����E�����Ҍ�����ʃ��f��
	 * @return �����E�����Ҍ�����ʃ��f��
	 */
	public TB041CustomerSearchModel getInitInfo(TB041CustomerSearchModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// �X�֔ԍ�����URL�擾
		model.setYubinNoSearchURL(userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_YUBIN_NO_SEARCH_URL));

		// �T�[�r�X���X�g�擾
		List<RcpMService> serviceList = new ArrayList<RcpMService>();
		if (userContext.isInhouse()) {
			// �������Ǘ��҂̏ꍇ�́A�T�[�r�X�S���擾
			serviceList = serviceDao.selectServiceList();
		} else if (userContext.isRealEstate()) {
			// �������s���Y�E�Ǘ���Ђ̏ꍇ�A������ڋq�h�c�ɕR�t���T�[�r�X���擾
			if (userContext.isKokyakuIdSelected()) {
				// �ڋq�h�c�I���ς̏ꍇ
				serviceList = kokyakuKeiyakuDao.selectKokyakuKeiyakuService(userContext.getKokyakuId());
			} else {
				// �ڋq�h�c�I���ςłȂ��ꍇ
				serviceList = kokyakuKeiyakuDao.selectKokyakuKeiyakuServiceForParentKokyakuId(userContext.getKokyakuId());
			}
		} else if (userContext.isConstractor()) {
			// �������˗��Ǝ҂̏ꍇ�A�˗��Ǝ҃R�[�h�ɕR�t���T�[�r�X���擾
			serviceList = kokyakuKeiyakuDao.selectKokyakuKeiyakuServiceForGyosha(userContext.getGyoshaCd());
		}

		model.setServiceList(serviceList);

		return model;
	}


	/**
	 * �����������s���܂��B
	 *
	 * @param model �����E�����Ҍ�����ʃ��f��
	 * @return �����E�����Ҍ�����ʃ��f��
	 */
	public TB041CustomerSearchModel search(TB041CustomerSearchModel model) {
		// �������擾����
		model = getInitInfo(model);

		// �O���T�C�g���J�ɑΉ�
		model.getCondition().setExternalSiteKokai(true);
		// �ڋq�}�X�^�f�[�^���o����A�ڋq�}�X�^�������ʃ��X�g���擾
		List<RcpMKokyaku> resultList = kokyakuDao.selectByCondition(model.getCondition(), RC011KokyakuSearchCondition.CSVFLG_OFF);

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		Map<String, Map<String, RcpMComCd>> convertMap =
				comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �a���ϊ�����
		Map<String, RcpMComCd> kokyakuKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		for (int i = 0; i < resultList.size(); i++) {
			RcpMKokyaku kokyaku = resultList.get(i);

			if (StringUtils.isNotBlank(kokyaku.getKokyakuKbn()) &&
					kokyakuKbnMap.containsKey(kokyaku.getKokyakuKbn())) {
				kokyaku.setKokyakuKbnNm(kokyakuKbnMap.get(kokyaku.getKokyakuKbn()).getExternalSiteVal());
			}
			resultList.set(i, kokyaku);
		}

		// �A�N�Z�X���O�̓o�^����
		tbAccesslogDao.insert(TB041CustomerSearchModel.GAMEN_NM,
				Constants.BUTTON_NM_SEARCH, createKensakuJoken(model));

		model.setResultList(resultList);

		return model;
	}


	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model �����E�����Ҍ�����ʃ��f��
	 * @return ��������
	 */
	private String createKensakuJoken(TB041CustomerSearchModel model) {
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
