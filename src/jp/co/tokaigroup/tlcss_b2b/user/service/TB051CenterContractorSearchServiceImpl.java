package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMTodofukenDao;
import jp.co.tokaigroup.reception.dao.RcpTGyoshaGyoshuDao;
import jp.co.tokaigroup.reception.dao.RcpTGyoshaTodofukenDao;
import jp.co.tokaigroup.reception.dto.RC061GyoshaSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpMTodofuken;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB051CenterContractorSearchModel;

/**
 * �Z���^�[�ƎҌ��������T�[�r�X�����N���X�B
 *
 * @author v145527
 * @version 1.0 2015/09/29
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB051CenterContractorSearchServiceImpl extends TLCSSB2BBaseService
implements TB051CenterContractorSearchService {
	
	/** ���ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �s���{���}�X�^DAO */
	@Autowired
	private RcpMTodofukenDao todofukenDao;

	/** �Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/** �Ǝғs���{���e�[�u��DAO */
	@Autowired
	private RcpTGyoshaTodofukenDao gyoshaTodofukenDao;

	/** �ƎҋƎ�e�[�u��DAO */
	@Autowired
	private RcpTGyoshaGyoshuDao gyoshaGyoshuDao;

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �Z���^�[�ƎҌ�����ʃ��f��
	 * @return �Z���^�[�ƎҌ�����ʃ��f��
	 */
	public TB051CenterContractorSearchModel getInitInfo(
			TB051CenterContractorSearchModel model) {

		// ���ʃR�[�h�}�X�^����̎擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_GYOSHU);

		// �Ǝ�擾
		List<RcpMComCd> gyoshuList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_GYOSHU);
		model.setGyoshuList(gyoshuList);

		// �s���{�����X�g�̎擾
		List<RcpMTodofuken> todofukenList = todofukenDao.selectAll();
		model.setTodofukenList(todofukenList);

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// �X�֔ԍ�����URL�擾
		model.setYubinNoSearchURL(userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_YUBIN_NO_SEARCH_URL));

		return model;
	}

	/**
	 * �Ǝ҈ꗗ���������܂��B
	 *
	 * @param model �Z���^�[�ƎҌ�����ʃ��f��
	 * @return TB051ContractorSearchModel �Z���^�[�ƎҌ�����ʃ��f��
	 */
	public TB051CenterContractorSearchModel search(
			TB051CenterContractorSearchModel model) {

		// �������擾����
		model = getInitInfo(model);
		
		// �����������s
		List<RC061GyoshaSearchDto> resultList = gyoshaDao.selectByCondition(model.getCondition());
		
		// �Ǝ҃R�[�h�ꗗ�쐬
		List<String> gyoshaCdList = new ArrayList<String>();
		for (RC061GyoshaSearchDto gyoshaSearchDto : resultList) {
			gyoshaCdList.add(gyoshaSearchDto.getGyoshaCd());
		}
		// �a���ϊ��pMap�擾
		// �s���{���ϊ�Map 
		Map<String, String> todofukenMap = gyoshaTodofukenDao.selectJoinNmMap(gyoshaCdList, " ");
		// �Ǝ�ϊ�Map
		Map<String, String> gyoshuMap = gyoshaGyoshuDao.selectJoinNmMap(gyoshaCdList, " ");

		// �a���ϊ�����
		for (RC061GyoshaSearchDto dto : resultList) {
			if (todofukenMap.containsKey(dto.getGyoshaCd())) {
				// �s���{����
				dto.setTodofukenNm(todofukenMap.get(dto.getGyoshaCd()));
			}
			if (gyoshuMap.containsKey(dto.getGyoshaCd())) {
				// �Ǝ햼
				dto.setGyoshuNm(gyoshuMap.get(dto.getGyoshaCd()));
			}
		}

		// �������ʂ̐ݒ�
		model.setResultList(resultList);
		
		return model;
	}

}
