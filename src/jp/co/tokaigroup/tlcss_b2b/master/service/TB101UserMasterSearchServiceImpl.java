package jp.co.tokaigroup.tlcss_b2b.master.service;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.TbMUserDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB101UserMasterSearchModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ���[�U�[�}�X�^�����T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/26
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB101UserMasterSearchServiceImpl extends TLCSSB2BBaseService
	implements TB101UserMasterSearchService {

	/** �O���T�C�g���[�U�[�}�X�^DAO */
	@Autowired
	private TbMUserDao userDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/**
	 * �����\���������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^������ʃ��f��
	 * @return ���[�U�[�}�X�^������ʃ��f��
	 */
	public TB101UserMasterSearchModel getInitInfo(TB101UserMasterSearchModel model) {
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);

		// �������X�g�̎擾
		List<RcpMComCd> roleList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);

		model.setRoleList(roleList);

		return model;
	}

	/**
	 * �����������s���܂��B
	 *
	 * @param model ���[�U�[�}�X�^������ʃ��f��
	 * @return ���[�U�[�}�X�^������ʃ��f��
	 */
	public TB101UserMasterSearchModel executeSearch(TB101UserMasterSearchModel model) {
		// �����\�������Ď��s�i���X�g���擾�̂��߁j
		model = getInitInfo(model);

		// �����������s
		List<TbMUser> resultList = userDao.selectByCondition(model.getCondition());

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// ����Map
		Map<String, RcpMComCd> roleMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);

		// �a���ϊ�����
		for (int i = 0; i < resultList.size(); i++) {
			TbMUser user = resultList.get(i);

			// ����
			if (roleMap.containsKey(user.getRole())) {
				user.setRoleNm(roleMap.get(user.getRole()).getExternalSiteVal());
			}

			// �Đݒ�
			resultList.set(i, user);
		}

		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB101UserMasterSearchModel.GAMEN_NM,
				Constants.BUTTON_NM_SEARCH, createKensakuJoken(model));

		model.setResultList(resultList);

		return model;
	}

	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model ���[�U�[�}�X�^��ʃ��f��
	 * @return �A�N�Z�X���O�ɓo�^���錟������
	 */
	private String createKensakuJoken(TB101UserMasterSearchModel model) {
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
