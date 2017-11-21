package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn1Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn2Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn3Dao;
import jp.co.tokaigroup.reception.dao.RcpMToiawaseKbn4Dao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dto.RC031ToiawaseSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn1;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn2;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn3;
import jp.co.tokaigroup.reception.entity.RcpMToiawaseKbn4;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB021InquirySearchModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �₢���킹�����T�[�r�X�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 1.1 2015/10/16 v145527
 * @version 1.2 2015/12/24 H.Yamamura �p�X���[�h�}�X�^�擾���@�ύX
 * @version 1.3 2016/06/17 H.Hirai ����������Ή�
 * @version 1.4 2016/07/11 H.Yamamura �₢���킹�敪�P�`�S�̃��X�g��ǉ�
 * @version 1.5 2016/08/22 J.Matsuba ���������̃T�[�r�X���w�肳��Ă���ꍇ�̏������C��
 * @version 1.6 2016/10/21 H.Yamamura �T�[�r�X��ʂ̐ݒ���@��ύX
 * @version 1.7 2017/09/12 J.Matsuba ������ڋq���擾�����̕ύX�A��З����Ή�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB021InquirySearchServiceImpl extends TLCSSB2BBaseService
		implements TB021InquirySearchService {

	/** ���Z�v�V�����₢���킹�e�[�u��DAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** ���Z�v�V�����󋵋敪�}�X�^DAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** NATOS�p�X���[�h�lDAO */
	@Autowired
	private NatosMPasswordDao natosPswdDao;

	/** �ڋq�_��lDAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;
	
	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** �T�[�r�X�lDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/** �₢���킹�敪1�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn1Dao toiawaseKbn1;

	/** �₢���킹�敪2�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn2Dao toiawaseKbn2;

	/** �₢���킹�敪3�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn3Dao toiawaseKbn3;

	/** �₢���킹�敪4�}�X�^DAO */
	@Autowired
	private RcpMToiawaseKbn4Dao toiawaseKbn4;
	
	/** ��Ѓ}�X�^DAO */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/** �₢���킹�敪�}�X�^�̃\�[�g�� */
	private static final String TOIAWASE_KBN_ORDER_BY = " ORDER BY HYOJI_JUN ASC, KBN ASC";

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �₢���킹������ʃ��f��
	 * @return �₢���킹������ʃ��f��
	 */
	public TB021InquirySearchModel getInitInfo(TB021InquirySearchModel model) {

		// �V�X�e���}�X�^�萔Map����ő匟���\�����A�ő�\���������擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		//�T�[�r�X�擾
		if (userContext.isInhouse()){
			model.setServiceMEntityList(serviceDao.selectServiceList());
		} else {
			model.setServiceMEntityList(kokyakuKeiyakuDao.selectKokyakuKeiyakuService(userContext.getKokyakuId()));
		}

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_JOKYO,
				RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU);
		
		// �󋵃��X�g
		List<RcpMComCd> jokyoList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_JOKYO);
		model.setJokyoList(jokyoList);
		
		// �{���󋵃��X�g
		List<RcpMComCd> browseStatusList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU);
		RcpMComCd all = new RcpMComCd();
		all.setExternalSiteVal("�S��");
		all.setComCd("");
		browseStatusList.add(0, all);
		model.setBrowseStatusList(browseStatusList);

		// �₢���킹�敪1���X�g�̎擾
		// �\�[�g����ݒ�
		toiawaseKbn1.setOrderBy(TOIAWASE_KBN_ORDER_BY);
		List<RcpMToiawaseKbn1> toiawaseKbn1List = toiawaseKbn1.selectAll();
		model.setToiawase1List(toiawaseKbn1List);

		// �₢���킹�敪2���X�g�̎擾
		// �\�[�g����ݒ�
		toiawaseKbn2.setOrderBy(TOIAWASE_KBN_ORDER_BY);
		List<RcpMToiawaseKbn2> toiawaseKbn2List = toiawaseKbn2.selectAll();
		model.setToiawase2List(toiawaseKbn2List);

		// �₢���킹�敪3���X�g�̎擾
		// �\�[�g����ݒ�
		toiawaseKbn3.setOrderBy(TOIAWASE_KBN_ORDER_BY);
		List<RcpMToiawaseKbn3> toiawaseKbn3List = toiawaseKbn3.selectAll();
		model.setToiawase3List(toiawaseKbn3List);

		// �₢���킹�敪4���X�g�̎擾
		// �\�[�g����ݒ�
		toiawaseKbn4.setOrderBy(TOIAWASE_KBN_ORDER_BY);
		List<RcpMToiawaseKbn4> toiawaseKbn4List = toiawaseKbn4.selectAll();
		model.setToiawase4List(toiawaseKbn4List);

		return model;
	}

	/**
	 * �������������s���܂��B
	 *
	 * @param model �₢���킹������ʃ��f��
	 * @param csvDownloadFlg CSV�_�E�����[�h�t���O�itrue�FCSV�_�E�����[�h�j
	 * @return �₢���킹������ʃ��f��
	 */
	public TB021InquirySearchModel executeSearch(TB021InquirySearchModel model, boolean csvDownloadFlg) {

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		//�����\�����s��
		model = getInitInfo(model);
		
		// �T�[�r�X��� NULL�擾�t���O��false�ŏ����ݒ�
		model.getCondition().setServiceShubetsuNullFlg(false);

		if (StringUtils.isNotBlank(model.getCondition().getServiceKbn())) {
			// ���������̃T�[�r�X���w�肳��Ă���ꍇ
			RcpMService service = serviceDao.selectByPrimaryKey(model.getCondition().getServiceKbn());
			List<String> serviceShubetsuList = new ArrayList<String>();

			serviceShubetsuList.add(service.getServiceShubetsu());
			if (RcpMService.SERVICE_KBN_RECEPTION.equals(service.getServiceShubetsu())
				|| RcpMService.SERVICE_KBN_LIFE_SUPPORT24.equals(service.getServiceShubetsu())) {
				// �擾�����T�[�r�X��ʂ����Z�v�V�����@�܂��́@���C�t�T�|�[�g�Q�S�̏ꍇ�A�s���ANULL�������ɒǉ�
				serviceShubetsuList.add(RcpTToiawase.SERVICE_SHUBETSU_FUMEI);
				model.getCondition().setServiceShubetsuNullFlg(true);
			}
			model.getCondition().setServiceShubetsuList(serviceShubetsuList);
		}

		// �����������s
		List<RC031ToiawaseSearchDto> resultList = toiawaseDao.selectByCondition(model.getCondition());

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_UMU,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_SEX_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI,
				RcpMComCd.RCP_M_COM_CD_KBN_PUBLISH_FLG,
				RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU,
				RcpMComCd.RCP_M_COM_CD_KBN_REGIST_KBN);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �ڋq�敪Map
		Map<String, RcpMComCd> kokyakuKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		// �ڋq���Map
		Map<String, RcpMComCd> kokyakuShubetsuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// �˗��ҋ敪Map
		Map<String, RcpMComCd> iraishaKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// �˗��L���敪Map
		Map<String, RcpMComCd> iraiUmuKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_UMU);
		// �˗��҃t���OMap
		Map<String, RcpMComCd> iraishaFlgMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA);
		// �˗��Ґ���
		Map<String, RcpMComCd> iraishaSexKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_SEX_KBN);
		// ��t�`��Map
		Map<String, RcpMComCd> uketukeKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		// ���JMap
		Map<String, RcpMComCd> koukaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_PUBLISH_FLG);
		// �{���󋵃t���OMap
		Map<String, RcpMComCd> browseStatusMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KIDOKU_MIDOKU);
		// �o�^�敪Map
		Map<String, RcpMComCd> registKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_REGIST_KBN);

		// �₢���킹�敪Map
		Map<String, String> toiawaseKbn1Map = toiawaseKbn1.selectAllAsMap();
		Map<String, String> toiawaseKbn2Map = toiawaseKbn2.selectAllAsMap();
		Map<String, String> toiawaseKbn3Map = toiawaseKbn3.selectAllAsMap();
		Map<String, String> toiawaseKbn4Map = toiawaseKbn4.selectAllAsMap();

		// �󋵋敪�}�X�^����̎擾
		Map<String, String> jokyoKbnMap;
		if (userContext.isRealEstate()) {
			// �������Ǘ���Ђ̏ꍇ�͊O���T�C�g�p
			jokyoKbnMap = jokyoKbnDao.selectAllExtJokyoKbnNmAsMap();
		} else {
			// �������Ǘ���ЈȊO�͑S���擾
			jokyoKbnMap = jokyoKbnDao.selectAllAsMap();
		}
		
		// �O���T�C�g�p�����t���OMap���擾
		Map<String, String> jokyoKbnExtKanryoFlgMap = jokyoKbnDao.selectAllExtKanryoFlgAsMap();

		// �p�X���[�h�}�X�^�E�Ǝ҃}�X�^�����p�̃��X�g����
		List<String> userIdList = new ArrayList<String>();
		for (RC031ToiawaseSearchDto dto : resultList) {
			if (StringUtils.isNotBlank(dto.getUketsukeshaId()) &&
				!userIdList.contains(dto.getUketsukeshaId())) {
				// �p�X���[�h�}�X�^�����p�̃��X�g�Ɋ܂܂�Ă��Ȃ���΁A���X�g�ɒǉ�
				userIdList.add(dto.getUketsukeshaId());
			}
			// ��ʍŏI�X�V��ID���܂܂�Ă��Ȃ���΁A���X�g�ɒǉ�
			if (StringUtils.isNotBlank(dto.getGamenLastUpdId())
				&& ! userIdList.contains(dto.getGamenLastUpdId())) {
				userIdList.add(dto.getGamenLastUpdId());
			}
			// ����o�^��ID���܂܂�Ă��Ȃ���΁A���X�g�ɒǉ�
			if (StringUtils.isNotBlank(dto.getCreId())
				&& ! userIdList.contains(dto.getCreId())) {
				userIdList.add(dto.getCreId());
			}
			// �ŏI�X�V��ID���܂܂�Ă��Ȃ���΁A���X�g�ɒǉ�
			if (StringUtils.isNotBlank(dto.getLastUpdId())
				&& ! userIdList.contains(dto.getLastUpdId())) {
				userIdList.add(dto.getLastUpdId());
			}
		}

		// ���[�UMap
		Map<String, String> userMap = new HashMap<String, String>();
		if (!(userIdList == null || userIdList.isEmpty())) {
			// CSV�_�E�����[�h�̏ꍇ
			if (csvDownloadFlg) {
				// �p�X���[�h�}�X�^�S���擾
				userMap = natosPswdDao.convertMap(natosPswdDao.selectAll());
			} else {
				// �p�X���[�h�}�X�^�擾
				userMap =
					natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));
			}
		}
		
		// ���Map
		Map<String, String> kaishaMap = kaishaDao.selectAllAsMapForRyakuNm();
		
		// �ڋq�h�c���X�g����
		List<String> kokyakuIdList = new ArrayList<String>();
		for (RC031ToiawaseSearchDto dto : resultList) {
			if (StringUtils.isNotBlank(dto.getKokyakuId()) &&
				!kokyakuIdList.contains(dto.getKokyakuId()) &&
				!RcpMKokyaku.KOKYAKU_KBN_FUDOSAN.equals(dto.getKokyakuKbn())) {
				// �������ʂ̌ڋq�h�c���󗓂łȂ��A���A�ڋq�h�c���X�g�Ɋi�[����Ă��Ȃ��A
				// ���A�������ʂ̌ڋq�敪���s���Y�E�Ǘ���ЂłȂ��ꍇ�ɁA�ڋq�h�c���X�g�ɓo�^
				kokyakuIdList.add(dto.getKokyakuId());
			}

			// ��������擾
			if (StringUtils.isNotBlank(dto.getKokyakuId())) {
				// �������ʂ̌ڋq�h�c���󗓈ȊO�̏ꍇ
				RcpMKokyaku seikyusakiKokyaku = kokyakuDao.selectSeikyusakiKokyakuAsToiawaseServiceShubetsu(
						dto.getKokyakuId(), dto.getServiceShubetsu());

				if (seikyusakiKokyaku != null) {
					// �擾����������ڋq��Entity��NULL�łȂ��ꍇ
					dto.setSeikyusakiKokyakuId(seikyusakiKokyaku.getKokyakuId());
					dto.setSeikyusakiKanjiNm1(seikyusakiKokyaku.getKanjiNm1());
					dto.setSeikyusakiKanjiNm2(seikyusakiKokyaku.getKanjiNm2());
				}
			}
		}

		// �a���ϊ�����
		for (RC031ToiawaseSearchDto dto : resultList) {
	
			// �ڋq�h�c�����݂���ꍇ
			if (StringUtils.isNotBlank(dto.getKokyakuId())) {
				// �ڋq�敪Map�Řa���ϊ�
				if (kokyakuKbnMap.containsKey(dto.getKokyakuKbn())) {
					// �ڋq�敪��
					dto.setKokyakuKbnNm(kokyakuKbnMap.get(dto.getKokyakuKbn()).getExternalSiteVal());
				}
			} else {
				// �˗��ҋ敪Map�Řa���ϊ�
				if (iraishaKbnMap.containsKey(dto.getKokyakuKbn())) {
					// �ڋq�敪��
					dto.setKokyakuKbnNm(iraishaKbnMap.get(dto.getKokyakuKbn()).getExternalSiteVal());
				}
			}
			
			if (kokyakuShubetsuMap.containsKey(dto.getKokyakuShubetsu())) {
				// �ڋq��ʖ�
				dto.setKokyakuShubetsuNm(kokyakuShubetsuMap.get(dto.getKokyakuShubetsu()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getIraishaKbn()) &&
				iraishaKbnMap.containsKey(dto.getIraishaKbn())) {
				// �˗��ҋ敪��
				dto.setIraishaKbnNm(iraishaKbnMap.get(dto.getIraishaKbn()).getExternalSiteVal());
			}

			if (iraiUmuKbnMap.containsKey(dto.getIraiUmuKbn())) {
				// �˗��L���敪��
				dto.setIraiUmuKbnNm(iraiUmuKbnMap.get(dto.getIraiUmuKbn()).getExternalSiteVal());
			}

			if (iraishaFlgMap.containsKey(dto.getIraishaFlg())) {
				// �˗��҃t���O��
				dto.setIraishaFlgNm(iraishaFlgMap.get(dto.getIraishaFlg()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getIraishaSexKbn()) &&
					iraishaSexKbnMap.containsKey(dto.getIraishaSexKbn())) {
				// �˗��Ґ���
				dto.setIraishaSexKbnNm(iraishaSexKbnMap.get(dto.getIraishaSexKbn()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getUketsukeKeitaiKbn()) &&
				uketukeKeitaiMap.containsKey(dto.getUketsukeKeitaiKbn())) {
				// ��t�`�Ԗ�
				dto.setUketsukeKeitaiKbnNm(uketukeKeitaiMap.get(dto.getUketsukeKeitaiKbn()).getExternalSiteVal());
			}

			if (koukaiMap.containsKey(dto.getHokokushoKokaiFlg())) {
				// �񍐏����J�t���O��
				dto.setHokokushoKokaiFlgNm(koukaiMap.get(dto.getHokokushoKokaiFlg()).getExternalSiteVal());
			}
			if (koukaiMap.containsKey(dto.getToiawaseKokaiFlg())) {
				// �₢���킹���J�t���O��
				dto.setToiawaseKokaiFlgNm(koukaiMap.get(dto.getToiawaseKokaiFlg()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getBrowseStatusFlg()) &&
				browseStatusMap.containsKey(dto.getBrowseStatusFlg())) {
				// �{���󋵋敪��
				dto.setBrowseStatusFlgNm(browseStatusMap.get(dto.getBrowseStatusFlg()).getExternalSiteVal());
			} else if (StringUtils.isBlank(dto.getBrowseStatusFlg())) {
				// �{���󋵋敪���𖢓ǂŐݒ�
				dto.setBrowseStatusFlgNm(browseStatusMap.get(RcpTToiawase.BROWSE_STATUS_FLG_MIDOKU).getExternalSiteVal());
			}

			if (registKbnMap.containsKey(dto.getRegistKbn())) {
				// �o�^�敪��
				dto.setRegistKbnNm(registKbnMap.get(dto.getRegistKbn()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getJokyoKbn()) &&
				jokyoKbnMap.containsKey(dto.getJokyoKbn())) {
				// �󋵋敪��
				dto.setJokyoKbnNm(jokyoKbnMap.get(dto.getJokyoKbn()));
			}

			if (StringUtils.isNotBlank(dto.getJokyoKbn()) &&
				jokyoKbnExtKanryoFlgMap.containsKey(dto.getJokyoKbn())) {
				// �O���T�C�g�p�����t���O�̐ݒ�
				dto.setJokyoKbnExtKanryoFlg(jokyoKbnExtKanryoFlgMap.get(dto.getJokyoKbn()));
			}

			// �₢���킹��t�Җ����擾�ł����ꍇ
			if (StringUtils.isNotBlank(dto.getToiawaseUketsukeshaNm())) {
				// ��t�Җ�
				dto.setUketsukeshaNm(dto.getToiawaseUketsukeshaNm());
			} else {
				if (StringUtils.isNotBlank(dto.getUketsukeshaId()) &&
					userMap.containsKey(dto.getUketsukeshaId())) {
					// ��t�Җ�
					dto.setUketsukeshaNm(userMap.get(dto.getUketsukeshaId()));
				}
			}

			if (StringUtils.isNotBlank(dto.getToiawaseKbn1()) &&
					toiawaseKbn1Map.containsKey(dto.getToiawaseKbn1())) {
				// �₢���킹�敪1��
				dto.setToiawaseKbn1Nm(toiawaseKbn1Map.get(dto.getToiawaseKbn1()));
			}

			if (StringUtils.isNotBlank(dto.getToiawaseKbn2()) &&
					toiawaseKbn2Map.containsKey(dto.getToiawaseKbn2())) {
				// �₢���킹�敪2
				dto.setToiawaseKbn2Nm(toiawaseKbn2Map.get(dto.getToiawaseKbn2()));
			}

			if (StringUtils.isNotBlank(dto.getToiawaseKbn3()) &&
					toiawaseKbn3Map.containsKey(dto.getToiawaseKbn3())) {
				// �₢���킹�敪3��
				dto.setToiawaseKbn3Nm(toiawaseKbn3Map.get(dto.getToiawaseKbn3()));
			}

			if (StringUtils.isNotBlank(dto.getToiawaseKbn4()) &&
					toiawaseKbn4Map.containsKey(dto.getToiawaseKbn4())) {
				// �₢���킹�敪4��
				dto.setToiawaseKbn4Nm(toiawaseKbn4Map.get(dto.getToiawaseKbn4()));
			}

			// �񍐑Ώۃt���O�� 
			if (StringUtils.isNotBlank(dto.getHoukokuTargetFlg())) {
				if (RcpTToiawase.HOKOKU_TARGET_FLG_ON.equals(dto.getHoukokuTargetFlg())) {
					dto.setHoukokuTargetFlgNm(RcpTToiawase.HOKOKU_TARGET_FLG_ON_NM);
				} else {
					dto.setHoukokuTargetFlgNm(RcpTToiawase.HOKOKU_TARGET_FLG_OFF_NM);
				}
			}

			if (kaishaMap.containsKey(dto.getCreKaishaId())) {
				// �o�^��Ж�
				dto.setCreKaishaNm(kaishaMap.get(dto.getCreKaishaId()));
			}

			if (kaishaMap.containsKey(dto.getUpdKaishaId())) {
				// �X�V��Ж�
				dto.setUpdKaishaNm(kaishaMap.get(dto.getUpdKaishaId()));
			}
			
			if (userMap.containsKey(dto.getGamenLastUpdId())) {
				// ��ʍŏI�X�V�Җ�
				dto.setGamenLastUpdNm(userMap.get(dto.getGamenLastUpdId()));
			}

			// �o�^�Җ����擾�ł����ꍇ
			if (StringUtils.isNotBlank(dto.getCreNm())) {
				// ����o�^�Җ��Ɏ擾�����o�^�Җ���ݒ�
				dto.setDisplayCreNm(dto.getCreNm());
			} else {
				if (userMap.containsKey(dto.getCreId())) {
					// ����o�^�Җ�
					dto.setDisplayCreNm(userMap.get(dto.getCreId()));
				}
			}

			// �ŏI�X�V�Җ����擾�ł����ꍇ
			if (StringUtils.isNotBlank(dto.getLastUpdNm())) {
				// �ŏI�X�V�Ҙa���Ɏ擾�����ŏI�X�V�Җ���ݒ�
				dto.setDisplayLastUpdNm(dto.getLastUpdNm());
			} else {
				if (userMap.containsKey(dto.getLastUpdId())) {
					// �ŏI�X�V�Җ��i�a���ϊ��j
					dto.setDisplayLastUpdNm(userMap.get(dto.getLastUpdId()));
				}
			}
			
			//���ԕ\�L�̏C��(hhmm��hh:mm)
			dto.setUketsukeJikanRireki(DateUtil.hhmmPlusColon(dto.getUketsukeJikanRireki()));
			dto.setUketsukeJikan(DateUtil.hhmmPlusColon(dto.getUketsukeJikan()));
		}

		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB021InquirySearchModel.GAMEN_NM,
				Constants.BUTTON_NM_SEARCH, createKensakuJoken(model));

		model.setResultList(resultList);

		return model;
	}

	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model �₢���킹������ʃ��f��
	 * @return �A�N�Z�X���O�ɓo�^���錟������
	 */
	private String createKensakuJoken(TB021InquirySearchModel model) {
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
