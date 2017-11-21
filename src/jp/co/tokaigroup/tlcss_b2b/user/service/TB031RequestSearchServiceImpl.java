package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMJokyoKbnDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTSeikyuDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dto.RC041IraiSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchModel;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB031RequestSearchModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �˗������T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/09
 * @version 1.1 2015/08/11 v145527
 * @version 1.2 2015/11/12 H.Hirai CSV�_�E�����[�h�@�\�ǉ��Ή�
 * @version 1.3 2015/12/24 H.Yamamura �p�X���[�h�}�X�^�擾���@�ύX
 * @version 1.4 2016/08/22 J.Matsuba ���������̃T�[�r�X���w�肳��Ă���ꍇ�̏������C��
 * @version 1.5 2016/10/21 H.Yamamura �T�[�r�X��ʂ̐ݒ���@��ύX
 * @version 1.6 2017/09/13 C.Kobayashi ��З����Ή�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB031RequestSearchServiceImpl extends TLCSSB2BBaseService
		implements TB031RequestSearchService {

	/** ���Z�v�V�����˗��e�[�u��DAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/** ���Z�v�V�����������e�[�u��DAO */
	@Autowired
	private RcpTSeikyuDao seikyuDao;

	/** ���Z�v�V�����ڋq�_��}�X�^DAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao keiyakuDao;

	/** ���Z�v�V�����󋵋敪�}�X�^DAO */
	@Autowired
	private RcpMJokyoKbnDao jokyoKbnDao;

	/** ���Z�v�V�����T�[�r�X�}�X�^DAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �O���T�C�g�A�N�Z�X���ODAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** NATOS�p�X���[�h�lDAO */
	@Autowired
	private NatosMPasswordDao natosPswdDao;

	/** �Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/** ��Ѓ}�X�^DAO */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �˗�������ʃ��f��
	 * @return �˗�������ʃ��f��
	 */
	public TB031RequestSearchModel getInitInfo(TB031RequestSearchModel model) {
		// �O����ߔN���擾
		List<String> zenkaiShoriYmList = seikyuDao.selectShimeYm(null, null);
		if (zenkaiShoriYmList == null || zenkaiShoriYmList.isEmpty()) {
			model.setZenkaiShoriYm("");
		} else {
			String zenkaiShoriYm = zenkaiShoriYmList.get(0).replaceAll("/", "");
			model.setZenkaiShoriYm(zenkaiShoriYm);
		}

		// �T�[�r�X���X�g�擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		List<RcpMService> serviceList = new ArrayList<RcpMService>();
		if (userContext.isInhouse()) {
			// �������Ǘ��҂̏ꍇ�́A�T�[�r�X�S���擾
			serviceList = serviceDao.selectServiceList();
		} else if (userContext.isRealEstate()) {
			// �������s���Y�E�Ǘ���Ђ̏ꍇ�A������ڋq�h�c�ɕR�t���T�[�r�X���擾
			serviceList = keiyakuDao.selectKokyakuKeiyakuService(userContext.getKokyakuId());
		} else if (userContext.isConstractor()) {
			// �������˗��Ǝ҂̏ꍇ�A�˗��Ǝ҃R�[�h�ɕR�t���T�[�r�X���擾
			serviceList = keiyakuDao.selectKokyakuKeiyakuServiceForGyosha(userContext.getGyoshaCd());
		}
		model.setServiceList(serviceList);

		// �󋵃��X�g�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_JOKYO);
		List<RcpMComCd> jokyoList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_JOKYO);
		model.setJokyoList(jokyoList);

		return model;
	}

	/**
	 * �������������s���܂��B
	 *
	 * @param model �˗�������ʃ��f��
	 * @return �˗�������ʃ��f��
	 */
	public TB031RequestSearchModel executeSearch(TB031RequestSearchModel model) {
		// �����\�������Ď��s�i���X�g���擾�̂��߁j
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
		List<RC041IraiSearchDto> resultList = iraiDao.selectByCondition(model.getCondition(), model.getCondition().getCsvFlg());

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND,
				RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI,
				RcpMComCd.RCP_M_COM_CD_FLG_KANRYO,
				RcpMComCd.RCP_M_COM_CD_KBN_PUBLISH_FLG);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �ڋq�敪Map
		Map<String, RcpMComCd> kokyakuKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		// �ڋq���Map
		Map<String, RcpMComCd> kokyakuKindMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// �˗��ҋ敪Map
		Map<String, RcpMComCd> iraishaKbnMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAISHA_KBN);
		// �K��\���Map
		Map<String, RcpMComCd> homonYoteiMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		// �����t���OMap
		Map<String, RcpMComCd> kanryoFlgMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_FLG_KANRYO);
		// ���J�t���OMap
		Map<String, RcpMComCd> kokaiFlgMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_PUBLISH_FLG);

		// �󋵋敪Map
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		Map<String, String> jokyoKbnMap = null;
		if (userContext.isRealEstate()) {
			// �Z�b�V�����̌������Ǘ���Ђ̏ꍇ
			// �󋵋敪�}�X�^�]�O���T�C�g�p����Map����̎擾
			jokyoKbnMap = jokyoKbnDao.selectAllExtJokyoKbnNmAsMap();
		} else {
			// �Z�b�V�����̌������Ǘ���ЈȊO�̏ꍇ
			// �󋵋敪�}�X�^�]�a���ϊ�Map����̎擾
			jokyoKbnMap = jokyoKbnDao.selectAllAsMap();
		}
		// �O���T�C�g�p�����t���OMap���擾
		Map<String, String> jokyoKbnExtKanryoFlgMap = jokyoKbnDao.selectAllExtKanryoFlgAsMap();

		// ��Ѓ}�X�^���Map�擾
		Map<String, String> kaishaMap = kaishaDao.selectAllAsMapForRyakuNm();

		// �p�X���[�h�}�X�^�E�Ǝ҃}�X�^�����p�̃��X�g����
		List<String> userIdList = new ArrayList<String>();
		List<String> gyoshaCdList = new ArrayList<String>();
		for (RC041IraiSearchDto dto : resultList) {
			if (StringUtils.isNotBlank(dto.getTantoshaId()) &&
				!userIdList.contains(dto.getTantoshaId())) {
				// �p�X���[�h�}�X�^���X�g�ɒǉ��F�����S����ID
				userIdList.add(dto.getTantoshaId());
			}

			if (StringUtils.isNotBlank(dto.getIraiGyoshaCd()) &&
				!gyoshaCdList.contains(dto.getIraiGyoshaCd())) {
				// �Ǝ҃}�X�^�����p�̃��X�g�Ɋ܂܂�Ă��Ȃ���΁A���X�g�ɒǉ�
				gyoshaCdList.add(dto.getIraiGyoshaCd());
			}

			if (RC041IraiSearchModel.CSV_OUT.equals(model.getCondition().getCsvFlg())) {
				// CSV�_�E�����[�h�������̂ݎ��{
				if (StringUtils.isNotBlank(dto.getUketsukeshaId()) &&
						!userIdList.contains(dto.getUketsukeshaId())) {
					// �p�X���[�h�}�X�^���X�g�ɒǉ��F��t��ID
					userIdList.add(dto.getUketsukeshaId());
				}

				if (StringUtils.isNotBlank(dto.getCreId()) &&
						!userIdList.contains(dto.getCreId())) {
					// �p�X���[�h�}�X�^���X�g�ɒǉ��F�˗�����o�^��ID
					userIdList.add(dto.getCreId());
				}

				if (StringUtils.isNotBlank(dto.getLastUpdId()) &&
						!userIdList.contains(dto.getLastUpdId())) {
					// �p�X���[�h�}�X�^���X�g�ɒǉ��F�˗��ŏI�X�V��ID
					userIdList.add(dto.getLastUpdId());
				}

				if (StringUtils.isNotBlank(dto.getLastPrintId()) &&
						!userIdList.contains(dto.getLastPrintId())) {
					// �p�X���[�h�}�X�^���X�g�ɒǉ��F�˗��ŏI�X�V��ID
					userIdList.add(dto.getLastPrintId());
				}

				if (StringUtils.isNotBlank(dto.getCreIdSagyoJokyo()) &&
						!userIdList.contains(dto.getCreIdSagyoJokyo())) {
					// �p�X���[�h�}�X�^���X�g�ɒǉ��F��Ə󋵏���o�^��ID
					userIdList.add(dto.getCreIdSagyoJokyo());
				}

				if (StringUtils.isNotBlank(dto.getLastUpdIdSagyoJokyo()) &&
						!userIdList.contains(dto.getLastUpdIdSagyoJokyo())) {
					// �p�X���[�h�}�X�^���X�g�ɒǉ��F��Ə󋵍ŏI�X�V��ID
					userIdList.add(dto.getLastUpdIdSagyoJokyo());
				}
			}
		}

		Map<String, String> userMap = new HashMap<String, String>();
		if (!(userIdList == null || userIdList.isEmpty())) {
			// CSV�o�͂̏ꍇ
			if (RC041IraiSearchModel.CSV_OUT.equals(model.getCondition().getCsvFlg())) {
				// �p�X���[�h�}�X�^�S���擾
				userMap = natosPswdDao.convertMap(natosPswdDao.selectAll());
			} else {
				// �p�X���[�h�}�X�^�擾
				userMap = natosPswdDao.convertMap(natosPswdDao.selectByList(userIdList, null));
			}
		}

		Map<String, String> gyoshaMap = new HashMap<String, String>();
		if (!(gyoshaCdList == null || gyoshaCdList.isEmpty())) {
			// �Ǝ҃}�X�^�擾
			gyoshaMap = gyoshaDao.convertMap(gyoshaDao.selectByGyoshaCdList(gyoshaCdList));
		}

		// �a���ϊ�����
		for (int i = 0; i < resultList.size(); i++) {
			RC041IraiSearchDto dto = resultList.get(i);

			if (StringUtils.isNotBlank(dto.getIraishaKbn()) &&
				iraishaKbnMap.containsKey(dto.getIraishaKbn())) {
				// �˗��ҋ敪��
				dto.setIraishaKbnNm(iraishaKbnMap.get(dto.getIraishaKbn()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(dto.getJokyoKbn()) &&
				jokyoKbnMap.containsKey(dto.getJokyoKbn())) {
				// �󋵋敪��
				dto.setJokyoKbnNm(jokyoKbnMap.get(dto.getJokyoKbn()));
			}
			
			if (jokyoKbnExtKanryoFlgMap != null && StringUtils.isNotBlank(dto.getJokyoKbn()) &&
				jokyoKbnExtKanryoFlgMap.containsKey(dto.getJokyoKbn())) {
				// �O���T�C�g�p�����t���O�̐ݒ�
				dto.setJokyoKbnExtKanryoFlg(jokyoKbnExtKanryoFlgMap.get(dto.getJokyoKbn()));
			}

			// �S���Җ������݂���ꍇ
			if (StringUtils.isNotBlank(dto.getTantoshaNm())) {
				// �����S���Җ�
				dto.setTantoshaIdNm(dto.getTantoshaNm());
			} else {
				if (StringUtils.isNotBlank(dto.getTantoshaId()) &&
						userMap.containsKey(dto.getTantoshaId())) {
					// �����S���Җ�
					dto.setTantoshaIdNm(userMap.get(dto.getTantoshaId()));
				}
			}

			if (RC041IraiSearchModel.CSV_OUT.equals(model.getCondition().getCsvFlg())) {
				// CSV�_�E�����[�h�������̂ݎ��{

				if (StringUtils.isNotBlank(dto.getUketsukeshaId()) &&
						userMap.containsKey(dto.getUketsukeshaId())) {
					// ��t�Ҙa��
					dto.setUketsukeshaDisplayNm(userMap.get(dto.getUketsukeshaId()));
				}

				// �ڋqID�����݂���ꍇ
				if (StringUtils.isNotBlank(dto.getKokyakuId())) {
					if (StringUtils.isNotBlank(dto.getKokyakuKbn()) &&
							kokyakuKbnMap.containsKey(dto.getKokyakuKbn())) {
						// �ڋq�敪��
						dto.setKokyakuKbnNm(kokyakuKbnMap.get(dto.getKokyakuKbn()).getExternalSiteVal());
					}
				} else {
					if (StringUtils.isNotBlank(dto.getKokyakuKbn()) &&
							iraishaKbnMap.containsKey(dto.getKokyakuKbn())) {
						// �ڋq�敪��
						dto.setKokyakuKbnNm(iraishaKbnMap.get(dto.getKokyakuKbn()).getExternalSiteVal());
					}
				}

				if (StringUtils.isNotBlank(dto.getKokyakuShubetsu()) &&
						kokyakuKindMap.containsKey(dto.getKokyakuShubetsu())) {
					// �ڋq��ʖ�
					dto.setKokyakuShubetsuNm(kokyakuKindMap.get(dto.getKokyakuShubetsu()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getIraiGyoshaCd()) &&
						gyoshaMap.containsKey(dto.getIraiGyoshaCd())) {
					// �˗��ƎҖ�
					dto.setIraiGyoshaNm(gyoshaMap.get(dto.getIraiGyoshaCd()));
				}

				if (StringUtils.isNotBlank(dto.getHomonKiboJikanKbn()) &&
						homonYoteiMap.containsKey(dto.getHomonKiboJikanKbn())) {
					// �K���]���ԋ敪��
					dto.setHomonKiboJikanKbnNm(homonYoteiMap.get(dto.getHomonKiboJikanKbn()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getGyoshaKaitoJikanKbn()) &&
						homonYoteiMap.containsKey(dto.getGyoshaKaitoJikanKbn())) {
					// �Ǝ҉񓚖K��\�莞�ԋ敪��
					dto.setGyoshaKaitoJikanKbnNm(homonYoteiMap.get(dto.getGyoshaKaitoJikanKbn()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getIraiKokaiFlg()) &&
						kokaiFlgMap.containsKey(dto.getIraiKokaiFlg())) {
					// �˗����J�t���O��
					dto.setIraiKokaiFlgNm(kokaiFlgMap.get(dto.getIraiKokaiFlg()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getCreId()) &&
						userMap.containsKey(dto.getCreId())) {
					// �˗��o�^�Ҙa��
					dto.setCreDisplayNm(userMap.get(dto.getCreId()));
				}

				if (StringUtils.isNotBlank(dto.getLastUpdId()) &&
						userMap.containsKey(dto.getLastUpdId())) {
					// �˗��ŏI�X�V�Ҙa��
					dto.setLastUpdDisplayNm(userMap.get(dto.getLastUpdId()));
				}

				if (StringUtils.isNotBlank(dto.getCreKaishaId()) &&
						kaishaMap.containsKey(dto.getCreKaishaId())) {
					// �o�^��Иa��
					dto.setCreKaishaDisplayNm(kaishaMap.get(dto.getCreKaishaId()));
				}

				if (StringUtils.isNotBlank(dto.getUpdKaishaId()) &&
						kaishaMap.containsKey(dto.getUpdKaishaId())) {
					// �ŏI�X�V��Иa��
					dto.setUpdKaishaDisplayNm(kaishaMap.get(dto.getUpdKaishaId()));
				}

				if (StringUtils.isNotBlank(dto.getLastPrintId()) &&
						userMap.containsKey(dto.getLastPrintId())) {
					// �ŏI����Ҙa��
					dto.setLastPrintDisplayNm(userMap.get(dto.getLastPrintId()));
				}

				if (StringUtils.isNotBlank(dto.getSagyoKanryoFlg()) &&
						kanryoFlgMap.containsKey(dto.getSagyoKanryoFlg())) {
					// ��Ɗ����敪��
					dto.setSagyoKanryoFlgNm(kanryoFlgMap.get(dto.getSagyoKanryoFlg()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getSagyoJokyoKokaiFlg()) &&
						kokaiFlgMap.containsKey(dto.getSagyoJokyoKokaiFlg())) {
					// ��Ə󋵌��J�t���O��
					dto.setSagyoJokyoKokaiFlgNm(kokaiFlgMap.get(dto.getSagyoJokyoKokaiFlg()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getHokokushoKokaiFlg()) &&
						kokaiFlgMap.containsKey(dto.getHokokushoKokaiFlg())) {
					// �񍐏����J�t���O��
					dto.setHokokushoKokaiFlgNm(kokaiFlgMap.get(dto.getHokokushoKokaiFlg()).getExternalSiteVal());
				}

				if (StringUtils.isNotBlank(dto.getCreIdSagyoJokyo()) &&
						userMap.containsKey(dto.getCreIdSagyoJokyo())) {
					// ��Ə󋵓o�^�Ҙa��
					dto.setCreDisplayNmSagyoJokyo(userMap.get(dto.getCreIdSagyoJokyo()));
				}

				if (StringUtils.isNotBlank(dto.getLastUpdIdSagyoJokyo()) &&
						userMap.containsKey(dto.getLastUpdIdSagyoJokyo())) {
					// ��Ə󋵍ŏI�X�V�Ҙa��
					dto.setLastUpdDisplayNmSagyoJokyo(userMap.get(dto.getLastUpdIdSagyoJokyo()));
				}
			}

			// ��t��������
			dto.setUketsukeJikanRireki(DateUtil.hhmmPlusColon(dto.getUketsukeJikanRireki()));
			// ��Ɗ�������
			dto.setSagyoKanryoJikan(DateUtil.hhmmPlusColon(dto.getSagyoKanryoJikan()));

			// �ăZ�b�g
			resultList.set(i, dto);
		}

		// �A�N�Z�X���O�o�^
		tbAccesslogDao.insert(TB031RequestSearchModel.GAMEN_NM,
				Constants.BUTTON_NM_SEARCH, createKensakuJoken(model));

		model.setResultList(resultList);

		return model;
	}

	/**
	 * �A�N�Z�X���O�ɓo�^���錟�������𐶐����܂��B
	 *
	 * @param model �˗�������ʃ��f��
	 * @return �A�N�Z�X���O�ɓo�^���錟������
	 */
	private String createKensakuJoken(TB031RequestSearchModel model) {
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
