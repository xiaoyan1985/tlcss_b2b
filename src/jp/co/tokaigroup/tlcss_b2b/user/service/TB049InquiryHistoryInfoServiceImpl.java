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
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn1Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn2Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn3Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn4Dao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dto.RC031ToiawaseSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMJokyoKbn;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB049InquiryHistoryInfoModel;

/**
 * �ڋq�ڍז₢���킹�����T�[�r�X�����N���X�B
 *
 * @author v145527 ����
 * @version 1.0 2015/07/24
 *
 */

@Service
@Transactional(value="txManager", readOnly = true)
public class TB049InquiryHistoryInfoServiceImpl extends TLCSSB2BBaseService
implements TB049InquiryHistoryInfoService {
	
	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;
	
	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO */
	@Autowired
	private RcpMComCdDao comCdDao;

	/** �₢���킹�敪1�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn1Dao toiawaseKbn1Dao;

	/** �₢���킹�敪2�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn2Dao toiawaseKbn2Dao;

	/** �₢���킹�敪3�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn3Dao toiawaseKbn3Dao;

	/** �₢���킹�敪4�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn4Dao toiawaseKbn4Dao;

	/** �󋵋敪�}�X�^DAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;
	
	/** �ڋq�}�X�^DAO */
	@Autowired
	private RcpTToiawaseDao rcpTToiawaseDao;

	/** �p�X���[�h�lDAO */
	@Autowired
	private NatosMPasswordDao natosMPasswordDao;

	@Override
	/**
	 * �����\���������s���܂��B
	 *
	 * @param model �ڋq�ڍז₢���킹�������f��
	 * @return �ڋq�ڍז₢���킹�������f��
	 */
	public TB049InquiryHistoryInfoModel getInitInfo(
			TB049InquiryHistoryInfoModel model) {
		
		// �������擾����
		// �ڋq��{���擾
		RcpMKokyaku kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		// �₢���킹��񌟍�
		model = searchToiawase(model);
		// model ��Entity�f�[�^��ݒ�
		model.setKokyakuEntity(kokyakuEntity);
		return model;
	}
	
	/**
	 * �₢���킹�ꗗ���������܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return TB049InquiryHistoryInfoModel ��ʃ��f��
	 */
	private TB049InquiryHistoryInfoModel searchToiawase(TB049InquiryHistoryInfoModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		RC031ToiawaseSearchCondition condition = model.getCondition();
		
		// TB049�ڋq�ڍז₢���킹�����ő匟���\����
		condition.setLimit(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_INQUIRY_HISTORY_TO_MAX));
		condition.setMaxCount(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_INQUIRY_HISTORY_TO_MAX));

		// �ꗗ����
		condition.setKokyakuId(model.getKokyakuId().replaceAll("C", ""));	// �ڋqID�̐擪�ꌅ�iC�j������
		condition.setIraiUmuRdo("");
		List<RC031ToiawaseSearchDto> toiawaseSearchList = rcpTToiawaseDao.selectByCondition(condition);

		// ���ʃR�[�h�}�X�^����̎擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);

		Map<String, Map<String, RcpMComCd>> comKeyMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);
		// �˗��ҋ敪�ϊ�Map
		Map<String, RcpMComCd> convMapIraiKbn = comKeyMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// �₢���킹�敪1Map
		Map<String, String> convMapT1 = toiawaseKbn1Dao.selectAllAsMap();
		// �₢���킹�敪2Map
		Map<String, String> convMapT2 = toiawaseKbn2Dao.selectAllAsMap();
		// �₢���킹�敪3Map
		Map<String, String> convMapT3 = toiawaseKbn3Dao.selectAllAsMap();
		// �₢���킹�敪4Map
		Map<String, String> convMapT4 = toiawaseKbn4Dao.selectAllAsMap();
		// �󋵋敪Map
		List<RcpMJokyoKbn> jokyoKbnList = jokyoKbnDao.selectAll();
		Map<String, String> convMapJokyoKbn = jokyoKbnDao.convertMap(jokyoKbnList);
		// ��t��Map
		List<String> userIdList = new ArrayList<String>();
		for (RC031ToiawaseSearchDto dto : toiawaseSearchList) {
			if (StringUtils.isNotBlank(dto.getUketsukeshaId())
				&& ! userIdList.contains(dto.getUketsukeshaId())) {
				// �p�X���[�h�}�X�^�����p�̃��X�g�Ɋ܂܂�Ă��Ȃ���΁A���X�g�ɒǉ�
				userIdList.add(dto.getUketsukeshaId());
			}
		}
		Map<String, String> convMapPassword = getMPasswordAsMap(userIdList);

		// �a���ϊ�
		for (RC031ToiawaseSearchDto toiawaseDto : toiawaseSearchList) {
			// �˗��ҋ敪�ϊ�Map
			if (convMapIraiKbn != null && StringUtils.isNotBlank(toiawaseDto.getIraishaKbn())) {
				toiawaseDto.setIraishaKbnNm(convertToValue(convMapIraiKbn, toiawaseDto.getIraishaKbn()));
			}
			// �₢���킹�敪1Map
			if (convMapT1 != null && StringUtils.isNotBlank(toiawaseDto.getToiawaseKbn1())) {
				toiawaseDto.setToiawaseKbn1Nm(convMapT1.get(toiawaseDto.getToiawaseKbn1()));
			}
			// �₢���킹�敪2Map
			if (convMapT2 != null && StringUtils.isNotBlank(toiawaseDto.getToiawaseKbn2())) {
				toiawaseDto.setToiawaseKbn2Nm(convMapT2.get(toiawaseDto.getToiawaseKbn2()));
			}
			// �₢���킹�敪3Map
			if (convMapT3 != null && StringUtils.isNotBlank(toiawaseDto.getToiawaseKbn3())) {
				toiawaseDto.setToiawaseKbn3Nm(convMapT3.get(toiawaseDto.getToiawaseKbn3()));
			}
			// �₢���킹�敪4Map
			if (convMapT4 != null && StringUtils.isNotBlank(toiawaseDto.getToiawaseKbn4())) {
				toiawaseDto.setToiawaseKbn4Nm(convMapT4.get(toiawaseDto.getToiawaseKbn4()));
			}
			// �󋵋敪Map
			if (convMapJokyoKbn != null && StringUtils.isNotBlank(toiawaseDto.getJokyoKbn())) {
				toiawaseDto.setJokyoKbnNm(convMapJokyoKbn.get(toiawaseDto.getJokyoKbn()));
			}
			// ���O�C����Map
			if (StringUtils.isBlank(toiawaseDto.getToiawaseUketsukeshaNm())) {
				// ��t�Җ���NULL�̏ꍇ�A��t�҂h�c��a���ϊ����ĕ\��
				if (convMapPassword != null && StringUtils.isNotBlank(toiawaseDto.getUketsukeshaId())
						&& convMapPassword.containsKey(toiawaseDto.getUketsukeshaId())) {
					toiawaseDto.setUketsukeshaNm(convMapPassword.get(toiawaseDto.getUketsukeshaId()));
				}
			} else {
				// ��t�Җ���NULL�łȂ���΁A��t�Җ���\��
				toiawaseDto.setUketsukeshaNm(toiawaseDto.getToiawaseUketsukeshaNm());
			}
		}
		model.setResult(toiawaseSearchList);

		return model;
	}

	/**
	 * ���ʃR�[�h�}�X�^�̘a���ϊ�Map���擾���܂��B
	 *
	 * @param cdKbn ���Z�v�V�������ʃR�[�h�}�X�^ �R�[�h�敪
	 * @return Map<String, Map<String, RcpMComCd>> �a���ϊ�Map
	 */
	public Map<String, Map<String, RcpMComCd>> getMPasswordAsMap(String cdKbn) {

		// ���ʃR�[�h�}�X�^����̎擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(cdKbn);

		// �a���ϊ��pMap
		Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);

		return convertMap;
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