package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dto.RC041IraiSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB050RequestHistoryInfoModel;

/**
 * �ڋq�ڍ׈˗������T�[�r�X�����N���X�B
 *
 * @author v145527 ����
 * @version 1.0 2015/07/30
 *
 */

@Service
@Transactional(value="txManager", readOnly = true)
public class TB050RequestHistoryInfoServiceImpl extends TLCSSB2BBaseService
implements TB050RequestHistoryInfoService {
	
	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;
	
	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO */
	@Autowired
	private RcpMComCdDao comCdDao;

	/** �󋵋敪�}�X�^DAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;

	/** �ڋq�}�X�^DAO */
	@Autowired
	private RcpTIraiDao rcpTIraiDao;

	/** �p�X���[�h�lDAO */
	@Autowired
	private NatosMPasswordDao natosMPasswordDao;

	/** ���Z�v�V�����Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	
	public TB050RequestHistoryInfoModel getInitInfo(
			TB050RequestHistoryInfoModel model) {
		
		// �������擾����
		// �ڋq��{���擾
		RcpMKokyaku kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		// �˗���񌟍�
		model = searchIrai(model);
		// model ��Entity�f�[�^��ݒ�
		model.setKokyakuEntity(kokyakuEntity);
		return model;
		
	}

	/**
	 * �˗��ꗗ���������܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return TB050RequestHistoryInfoModel ��ʃ��f��
	 */
	private TB050RequestHistoryInfoModel searchIrai(TB050RequestHistoryInfoModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		RC041IraiSearchCondition condition = model.getCondition();
		
		// TB050�ڋq�ڍ׈˗������ő匟���\����
		condition.setLimit(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_REQUEST_HISTORY_TO_MAX));
		condition.setMaxCount(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_REQUEST_HISTORY_TO_MAX));

		// �ꗗ����
		condition.setKokyakuId(model.getKokyakuId().replaceAll("C", ""));	// �ڋqID�̐擪�ꌅ�iC�j������
		List<RC041IraiSearchDto> iraiSearchList = rcpTIraiDao.selectByCondition(condition, "");
		
		// ���ʃR�[�h�}�X�^����̎擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN, RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND, RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI, RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);
		Map<String, Map<String, RcpMComCd>> comKeyMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �����S��Map
		List<String> userIdList = new ArrayList<String>();
		for (RC041IraiSearchDto dto : iraiSearchList) {
			if (StringUtils.isNotBlank(dto.getTantoshaId())
				&& !userIdList.contains(dto.getTantoshaId())) {
				// �p�X���[�h�}�X�^�����p�̃��X�g�Ɋ܂܂�Ă��Ȃ���΁A���X�g�ɒǉ�
				userIdList.add(dto.getTantoshaId());
			}
		}
		Map<String, String> convMapHachuTanto = getMPasswordAsMap(userIdList);
		// �˗��ҋ敪�ϊ�Map
		Map<String, RcpMComCd> convMapIraiKbn = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// �󋵋敪Map
		List<RcpMJokyoKbn> jokyoKbnList = jokyoKbnDao.selectAll();
		Map<String, String> convMapJokyoKbn = jokyoKbnDao.convertMap(jokyoKbnList);
		
		// CSV�p 
		// �ڋq�敪�ϊ�Map
		Map<String, RcpMComCd> convMapkokyakuKbn = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		// �ڋq��ʕϊ�Map
		Map<String, RcpMComCd> convMapkokyakuKind = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// �K��\�莞�ԋ敪�ϊ�Map
		Map<String, RcpMComCd> convMapHomonKbn = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		// ��Ɗ����敪�ϊ�Map
		Map<String, RcpMComCd> convMapSagyoKbn = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);

		// �Ǝ҃}�X�^�ϊ�Map
		List<RcpMGyosha> gyoshaList = gyoshaDao.selectAll();
		Map<String, String> convMapGyosha = gyoshaDao.convertMap(gyoshaList);

	
		// �a���ϊ�
		for (RC041IraiSearchDto iraiDto : iraiSearchList) {
			
			// �S���Җ������݂���ꍇ
			if (StringUtils.isNotBlank(iraiDto.getTantoshaNm())) {
				// �����S���Җ�
				iraiDto.setTantoshaIdNm(iraiDto.getTantoshaNm());
			}  else {
				// �����S��Map
				if (convMapHachuTanto != null && StringUtils.isNotBlank(iraiDto.getTantoshaId())) {
					iraiDto.setTantoshaIdNm(convMapHachuTanto.get(iraiDto.getTantoshaId()));
				}
			}
			// �˗��ҋ敪�ϊ�Map
			if (convMapIraiKbn != null && StringUtils.isNotBlank(iraiDto.getIraishaKbn())) {
				iraiDto.setIraishaKbnNm(convertToValue(convMapIraiKbn, iraiDto.getIraishaKbn()));
			}
			// �󋵋敪Map
			if (convMapJokyoKbn != null && StringUtils.isNotBlank(iraiDto.getJokyoKbn())) {
				iraiDto.setJokyoKbnNm(convMapJokyoKbn.get(iraiDto.getJokyoKbn()));
			}
			// �ڋq�敪�ϊ�Map
			if (convMapkokyakuKbn != null && StringUtils.isNotBlank(iraiDto.getKokyakuKbn())) {
				iraiDto.setKokyakuKbnNm(convertToValue(convMapkokyakuKbn, iraiDto.getKokyakuKbn()));
			}
			// �ڋq��ʕϊ�Map
			if (convMapkokyakuKind != null && StringUtils.isNotBlank(iraiDto.getKokyakuShubetsu())) {
				iraiDto.setKokyakuShubetsuNm(convertToValue(convMapkokyakuKind, iraiDto.getKokyakuShubetsu()));
			}
			// �K��\�莞�ԋ敪�ϊ�Map
			if (convMapHomonKbn != null && StringUtils.isNotBlank(iraiDto.getHomonKiboJikanKbn())) {
				iraiDto.setHomonKiboJikanKbnNm(convertToValue(convMapHomonKbn, iraiDto.getHomonKiboJikanKbn()));
			}
			// ��Ɗ����敪�ϊ�Map
			if (convMapSagyoKbn != null && StringUtils.isNotBlank(iraiDto.getSagyoKanryoFlg())) {
				iraiDto.setSagyoKanryoFlgNm(convertToValue(convMapSagyoKbn, iraiDto.getSagyoKanryoFlg()));
			}
			// �˗��Ǝ҃R�[�h�ϊ�
			if (convMapGyosha != null && StringUtils.isNotBlank(iraiDto.getIraiGyoshaCd())) {
				iraiDto.setIraiGyoshaNm(convMapGyosha.get(iraiDto.getIraiGyoshaCd()));
			}
		}

		
		model.setResult(iraiSearchList);

		return model;
	}

	/**
	 * �p�X���[�h�l�̘a���ϊ�Map���擾���܂��B
	 *
	 * @param cdKbn �p�X���[�h�l �R�[�h�敪
	 * @return Map<String, String> �a���ϊ�Map
	 */
	private Map<String, String> getMPasswordAsMap(List<String> userIdList) {

		Map<String, String> userMap = new HashMap<String, String>();
		if (! userIdList.isEmpty()) {
			// �p�X���[�h�l����̎擾
			userMap = natosMPasswordDao.convertMap(natosMPasswordDao.selectByList(userIdList, null));
		}

		return userMap;
	}

	/**
	 * ���ʃR�[�h�}�X�^�̘a���ϊ����s���܂��B
	 *
	 * @param cdKbn ���Z�v�V�������ʃR�[�h�}�X�^ �R�[�h�敪
	 * @param cd ���Z�v�V�������ʃR�[�h�}�X�^ �R�[�h
	 * @param convertMap �R�[�h�ϊ�Map
	 * @return String �ϊ��㕶����
	 */
	private String convertToValue(Map<String, RcpMComCd> convertMap, String cd) {

		// �a���擾
		RcpMComCd comCd = convertMap.get(cd);
		String wamei = "";
		if (StringUtils.isNotBlank(comCd.getComVal())) {
			wamei = comCd.getComVal();
		}

		return wamei;
	}
	
}
