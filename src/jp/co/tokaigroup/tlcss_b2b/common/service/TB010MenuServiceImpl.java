package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.ClosingProcessQuery;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.TbMHolidayDao;
import jp.co.tokaigroup.reception.dao.TbMItakuRefKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbMRefKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMRoleUrlDao;
import jp.co.tokaigroup.reception.dao.TbTInformationDao;
import jp.co.tokaigroup.reception.dao.TbTPublishFileDao;
import jp.co.tokaigroup.reception.dto.InquiryStatus;
import jp.co.tokaigroup.reception.dto.RC014KeiyakuListDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKeiyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.TbMHoliday;
import jp.co.tokaigroup.reception.entity.TbMItakuRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.reception.entity.TbTPublishFile;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.CommonUtil;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010DocumentFileDownloadModel;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010MenuModel;
import jp.co.tokaigroup.tlcss_b2b.dto.TB010InquiryStatusDto;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ���j���[�T�[�r�X�����N���X�B
 *
 * @author k002849
 * @version 4.0 2014/06/05
 * @version 4.1 2015/10/28 C.Kobayashi �ϑ��֘A��Ѓ`�F�b�N�̒ǉ�
 * @version 4.2 2016/09/13 J.Matsuba �Ǘ���Ѓ��[�U�̃��j���[�\�������C��
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB010MenuServiceImpl extends TLCSSB2BBaseService implements TB010MenuService {

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** ���Z�v�V�����ڋq�_��}�X�^DAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** ���Z�v�V�����₢���킹�e�[�u��DAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** ���Z�v�V�����˗��e�[�u��DAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/** ���Z�v�V�����Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	@Autowired
	/** ���ߏ����N�G�� */
	private ClosingProcessQuery closingProcessQuery;

	/** �O���T�C�g�V�X�e�� ���m�点�e�[�u��DAO */
	@Autowired
	private TbTInformationDao informationDao;

	/** �O���T�C�g�V�X�e�� �j���}�X�^DAO */
	@Autowired
	private TbMHolidayDao holidayDao;

	/** ���Z�v�V�����T�[�r�X�}�X�^DAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** ���J�t�@�C���e�[�u��DAO�i�O���T�C�g�p�j */
	@Autowired
	private TbTPublishFileDao publishFileDao;

	/** ���Z�v�V���� �Q�ƌڋq�}�X�^ */
	@Autowired
	private TbMRefKokyakuDao refKokyakuDao;

	/** �O���T�C�g�V�X�e�� �A�N�Z�X�\�t�q�k�}�X�^DAO */
	@Autowired
	private TbMRoleUrlDao roleUrlDao;

	/** ��Ѓ}�X�^DAO */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/** �ϑ���ЎQ�ƌڋq�}�X�^DAO */
	@Autowired
	private TbMItakuRefKokyakuDao itakuRefKokyakuDao;
	
	/** �ϑ���Њ֘A�`�F�b�N���W�b�N */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	
	
	/**
	 * �����\���������s���܂��B
	 *
	 * @param model ���j���[��ʃ��f��
	 * @return ���j���[��ʃ��f��
	 */
	public TB010MenuModel getInitInfo(TB010MenuModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// ���m�点���X�g�̎擾
		List<TbTInformation> informationList =
			informationDao.getInformationList();

		if (userContext.isRealEstate()) {
			// �Z�b�V�����̌������u20:�s���Y�E�Ǘ���Ёv�̏ꍇ
			// �ڋq�}�X�^���擾
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(userContext.getKokyakuId());
			if (kokyaku == null) {
				// �ڋq�}�X�^��񂪂Ȃ��ꍇ�́A�G���[
				throw new ApplicationException("�ڋq�}�X�^��񂪎擾�ł��܂���B");
			}
			model.setKokyaku(kokyaku);
			if (userContext.isKokyakuIdSelected()) {
				// �Z�b�V�����̌ڋq�h�c�I���ς��I���ς̏ꍇ
				model = getRealEstateInfo(model);
			} else {
				model = getRealEstateInfoForKokyakuSelect(model);
			}

			// �ϑ���ЎQ�ƌڋq��񃊃X�g���擾���A�ݒ�
			List<TbMItakuRefKokyaku> itakuRefKokyakuList = itakuRefKokyakuDao.selectBy(null, userContext.getKokyakuId());
			model.setItakuRefKokyakuList(itakuRefKokyakuList);

			// �ĕ\���^�C�}�[���Ԃ��擾���ݒ�i�����~���b�ɕϊ��j
			int reloadTime = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_MENU_RELOAD_TIME) * 60000;
			model.setReloadTime(String.valueOf(reloadTime));
		} else if (userContext.isConstractor()) {
			// �Z�b�V�����̌������u30:�˗��Ǝҁv�̏ꍇ
			model = getConstractInfo(model);
		} else if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// �Z�b�V�����̌������u40:�ϑ����SV�v�u41:�ϑ����OP�v�̏ꍇ
			TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(userContext.getKaishaId());
			if (kaisha == null) {
				throw new ApplicationException("��Ѓ}�X�^��񂪎擾�ł��܂���B");
			}
			model.setKaisha(kaisha);
		}

		model.setInfomationList(informationList);

		model.setUserContext(userContext);

		return model;
	}

	/**
	 * ���[�U�[���s���Y�E�Ǘ���Ђ̏ꍇ�ɕ\����������擾���܂��B
	 *
	 * @param model ���j���[��ʃ��f��
	 * @return ���j���[��ʃ��f��
	 */
	private TB010MenuModel getRealEstateInfo(TB010MenuModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND,
				RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE,
				RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE,
				RcpMComCd.RCP_M_COM_CD_KEIYAKU_KEITAI,
				RcpMComCd.RCP_M_COM_CD_JISSHI_KAISU);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// ������Map
		Map<String, RcpMComCd> lineKindMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND);
		// ��{�����敪Map
		Map<String, RcpMComCd> basicPriceMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE);
		// ���ԑ�Map
		Map<String, RcpMComCd> timeZoneMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE);
		// �_��`��Map
		Map<String, RcpMComCd> keiyakuKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KEIYAKU_KEITAI);
		// ���{��Map
		Map<String, RcpMComCd> jisshiKaisuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_JISSHI_KAISU);

		// �T�[�r�X�}�X�^�a���ϊ��pMap�擾
		List<RcpMService> serviceList = serviceDao.selectAll();
		Map<String, String> serviceMap = serviceDao.convertMap(serviceList);

		// �W�v�J�n���A�W�v�I�����̐ݒ�
		Timestamp summaryStartDt = getSummaryStartDt(model.getKokyaku());
		Timestamp summaryEndDt = DateUtil.toTimestamp(
				DateUtil.getSysDateString("yyyyMMdd"), "yyyyMMdd");

		model.setSummaryStartDt(summaryStartDt);
		model.setSummaryEndDt(summaryEndDt);


		// �_���񃊃X�g�̎擾
		List<RC014KeiyakuListDto> keiyakuList =
			kokyakuKeiyakuDao.selectKeiyakuListByMenu(userContext.getKokyakuId());

		// �_���񃊃X�g�i�r���Ǘ��j�̎擾
		List<RC014KeiyakuListDto> keiyakuBuildingList =
			kokyakuKeiyakuDao.selectKeiyakuListForBuildingByMenu(userContext.getKokyakuId());

		if (keiyakuList == null) {
			// �_���񃊃X�g��NULL�̏ꍇ
			keiyakuList = new ArrayList<RC014KeiyakuListDto>();
		}

		// �_���񃊃X�g�Ɍ_���񃊃X�g�i�r���Ǘ��j��������
		for (RC014KeiyakuListDto dto : keiyakuBuildingList) {
			int count = 0;

			for (RC014KeiyakuListDto dto2 : keiyakuList) {
				if (Integer.parseInt(dto2.getHyojiJun().toString()) > Integer.parseInt(dto.getHyojiJun().toString())) {
					break;
				}
				count++;
			}

			keiyakuList.add(count, dto);
		}

		List<TB010InquiryStatusDto> inquiryStatusList =
			new ArrayList<TB010InquiryStatusDto>();

		Long allSumIncompleteCount = new Long(0);
		Long allSumCompletedCount = new Long(0);

		for (RC014KeiyakuListDto dto : keiyakuList) {
			// �a���ϊ�����
			if (StringUtils.isNotBlank(dto.getKaisenCd()) &&
				lineKindMap.containsKey(dto.getKaisenCd())) {
				// ������
				dto.setKaisenCdNm(lineKindMap.get(dto.getKaisenCd()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(dto.getKihonRyokinKbn()) &&
				basicPriceMap.containsKey(dto.getKihonRyokinKbn())) {
				// ��{�����敪
				dto.setKihonRyokinKbnNm(basicPriceMap.get(dto.getKihonRyokinKbn()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(dto.getJikanCd()) &&
				timeZoneMap.containsKey(dto.getJikanCd())) {
				// ���ԑ�
				dto.setJikanCdNm(timeZoneMap.get(dto.getJikanCd()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(dto.getKeiyakuType()) &&
					keiyakuKeitaiMap.containsKey(dto.getKeiyakuType())) {
				// �_��`��
				dto.setKeiyakuTypeNm(keiyakuKeitaiMap.get(dto.getKeiyakuType()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(dto.getJisshiKaisu()) &&
					jisshiKaisuMap.containsKey(dto.getJisshiKaisu())) {
				// ���{��
				dto.setJisshiKaisuNm(jisshiKaisuMap.get(dto.getJisshiKaisu()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(dto.getServiceKbn()) &&
				serviceMap.containsKey(dto.getServiceKbn())) {
				// �T�[�r�X�敪
				dto.setServiceKbnNm(serviceMap.get(dto.getServiceKbn()));
			}

			TB010InquiryStatusDto inquiryStatusDto = new TB010InquiryStatusDto();

			// �I�u�W�F�N�g���R�s�[
			CommonUtil.copyProperties(dto, inquiryStatusDto);

			// �Ή��������A�Ή��ό����̎擾
			List<InquiryStatus> completedList = new ArrayList<InquiryStatus>();

			completedList = toiawaseDao.countInquiryStatus(
					InquiryStatus.EXT_SEARCH_TYPE_SERVICE,
					userContext.getKokyakuId(),
					dto.getKeiyakuNo(),
					summaryStartDt,
					summaryEndDt,
					dto.getServiceKbn(),
					null,
					dto.getServiceShubetsu());

			long sumIncomplete = 0L;
			long sumCompleted = 0L;
			for (InquiryStatus status : completedList) {

				if (status.isIncomplete()) {
					// �Ή����̍��v�l
					sumIncomplete += status.getCount().longValue();
				} else if (status.isCompleted()) {
					// �Ή��ς̍��v�l
					sumCompleted += status.getCount().longValue();
				}
			}

			inquiryStatusDto.setInCompleteCount(new BigDecimal(sumIncomplete));
			inquiryStatusDto.setCompletedCount(new BigDecimal(sumCompleted));

			allSumIncompleteCount = allSumIncompleteCount + sumIncomplete;
			allSumCompletedCount = allSumCompletedCount + sumCompleted;

			if (dto.isReception()) {
				// �T�[�r�X�����Z�v�V�����̏ꍇ
				RcpMKokyakuKeiyaku keiyaku =
					closingProcessQuery.countKosuOnContractTarget(
							userContext.getKokyakuId(), dto.getKeiyakuNo());

				if (keiyaku != null) {
					// �_�񌏐����P���ȏ�̏ꍇ
					inquiryStatusDto.setKeiyakuCount(keiyaku.getKokyakuIdCount());
					inquiryStatusDto.setKosuCount(keiyaku.getKakinKeiyakuKosu());
				} else {
					// �_�񌏐����O���̏ꍇ
					inquiryStatusDto.setKeiyakuCount(BigDecimal.ZERO);
					inquiryStatusDto.setKosuCount(BigDecimal.ZERO);
				}
			} else if (dto.isLifeSupport24()) {
				// �T�[�r�X�����C�t�T�|�[�g�Q�S�̏ꍇ
				BigDecimal keiyakuCount =
					closingProcessQuery.selectConstractCountOnContractTarget(
							userContext.getKokyakuId(), dto.getServiceKbn());

				inquiryStatusDto.setKeiyakuCount(keiyakuCount);
			}

			inquiryStatusList.add(inquiryStatusDto);
		}

		model.setSumIncompleteCount(new BigDecimal(allSumIncompleteCount));
		model.setSumCompleteCount(new BigDecimal(allSumCompletedCount));

		// �₢���킹�e�[�u�����疢�ǌ������擾
		model.setUnreadCount(toiawaseDao.selectUnreadCount(userContext.getKokyakuId(), summaryStartDt, summaryEndDt));

		// ��t�����ꗗ�̎擾
		List<InquiryStatus> inquiryHistoryList = toiawaseDao.countInquiryStatus(
						InquiryStatus.EXT_SEARCH_TYPE_UKETSUKE,
						userContext.getKokyakuId(),
						null,
						DateUtil.toSqlTimestamp(DateUtils.truncate(
								DateUtils.addYears(DateUtil.getSysDate(), -1), Calendar.DAY_OF_MONTH)),
						DateUtil.toSqlTimestamp(DateUtils.truncate(
								DateUtils.addYears(DateUtil.getSysDate(), +1), Calendar.DAY_OF_MONTH)),
						null,
						null,
						null);

		// �j���ꗗ�̎擾
		List<TbMHoliday> holidayList = holidayDao.selectAll();

		model.setInqueryStatusList(inquiryStatusList);
		model.setInquiryHistoryList(inquiryHistoryList);
		model.setHolidayList(holidayList);

		// �������C�u�����̎擾
		model.setPublishFileList(publishFileDao.selectBy(userContext.getKokyakuId(), null));

		return model;
	}

	/**
	 * ���[�U�[���s���Y�E�Ǘ���Ђ������ڋq�̏ꍇ�ɕ\����������擾���܂��B
	 *
	 * @param model ���j���[��ʃ��f��
	 * @return ���j���[��ʃ��f��
	 */
	private TB010MenuModel getRealEstateInfoForKokyakuSelect(TB010MenuModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// ��ʂɕԂ��₢���킹�󋵃��X�g��ݒ肵�܂��B
		List<TB010InquiryStatusDto> inquiryStatusDtoList = new ArrayList<TB010InquiryStatusDto>();
		Long sumIncomplete = new Long(0);
		Long sumCompleted = new Long(0);

		// �Q�ƌڋq�}�X�^���X�g���擾����B
		List<TbMRefKokyaku> refKokyakuList = refKokyakuDao.selectBy(userContext.getRefKokyakuId(), null);

		for (TbMRefKokyaku refKokyaku : refKokyakuList) {
			// �ڋq�}�X�^Entity���擾����B
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(refKokyaku.getKokyakuId());

			if (!kokyaku.isKeiyakuKanriEndFlgExpired()) {
			// �_��Ǘ��I���t���O���u2�F�I���A�ێ����Ԍo�߁v�ȊO�̂��̂̂ݕ\������B

				// �₢���킹��DTO��ݒ肷��B
				TB010InquiryStatusDto inquiryStatusDto = new TB010InquiryStatusDto();

				// �ڋq�_��}�X�^���X�g���擾����B
				inquiryStatusDto.setKokyakuKeiyakuList(kokyakuKeiyakuDao.selectSeikyumotoKokyakuId(refKokyaku.getKokyakuId()));

				// �������擾����B
				List<InquiryStatus> inquiryHistoryList = new ArrayList<InquiryStatus>();
				if (TbMRefKokyaku.LEAF_FLG_NOT_LEAF.equals(refKokyaku.getLeafFlg())) {
					// ���[�łȂ��ꍇ
					if (refKokyaku.isLowerDisplay()) {
						// ���ʕ\���̏ꍇ
						// �Ǘ���Јꗗ���擾����
						inquiryHistoryList = toiawaseDao.countInquiryStatus(
							InquiryStatus.EXT_SEARCH_TYPE_TOIAWASE,
							refKokyaku.getKokyakuId(),
							null,
							DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getFirstDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
							DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getLastDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
							null,
							null,
							null);
					} else {
						// ���ʔ�\���̏ꍇ
						// �Ǘ���Јꗗ���擾����
						inquiryHistoryList = toiawaseDao.countInquiryStatus(
							InquiryStatus.EXT_SEARCH_TYPE_SUBTOTAL,
							refKokyaku.getKokyakuId(),
							null,
							DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getFirstDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
							DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getLastDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
							null,
							null,
							null);
					}
				} else {
					// ���[�̏ꍇ
					// �Ǘ���Јꗗ���擾����
					inquiryHistoryList = toiawaseDao.countInquiryStatus(
						InquiryStatus.EXT_SEARCH_TYPE_TOIAWASE,
						refKokyaku.getKokyakuId(),
						null,
						DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getFirstDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
						DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getLastDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"),
						null,
						null,
						null);
				}

				inquiryStatusDto.setSeikyusakiKokyakuId(refKokyaku.getKokyakuId());
				inquiryStatusDto.setKanjiNm(kokyaku.getKanjiNm());
				inquiryStatusDto.setRefKokyaku(refKokyaku);

				for (InquiryStatus status : inquiryHistoryList) {
					if (status.isIncomplete()) {
						// �Ή����̏ꍇ
						inquiryStatusDto.setInCompleteCount(status.getCount());
						if (!refKokyaku.isLowerDisplay() || inquiryStatusDto.hasKeiyaku()) {
							// �Ή����̍��v�l
							sumIncomplete += status.getCount().longValue();
						}
					} else if (status.isCompleted()) {
						// �Ή��ς̏ꍇ
						inquiryStatusDto.setCompletedCount(status.getCount());
						if (!refKokyaku.isLowerDisplay() || inquiryStatusDto.hasKeiyaku()) {
							// �Ή��ς̍��v�l
							sumCompleted += status.getCount().longValue();
						}
					}
				}

				// �Ή��ς��O���̏ꍇ
				if (inquiryStatusDto.getCompletedCount() == null) {
					inquiryStatusDto.setCompletedCount(BigDecimal.ZERO);
				}
				// �Ή������O���̏ꍇ
				if (inquiryStatusDto.getInCompleteCount() == null) {
					inquiryStatusDto.setInCompleteCount(BigDecimal.ZERO);
				}

				inquiryStatusDtoList.add(inquiryStatusDto);
			}
		}
		
		// ��t�����ꗗ�̎擾
		List<InquiryStatus> inquiryHistoryList = toiawaseDao.countInquiryStatus(
						InquiryStatus.EXT_SEARCH_TYPE_UKETSUKE,
						null,
						null,
						DateUtil.toSqlTimestamp(DateUtils.truncate(
								DateUtils.addYears(DateUtil.getSysDate(), -1), Calendar.DAY_OF_MONTH)),
						DateUtil.toSqlTimestamp(DateUtils.truncate(
								DateUtils.addYears(DateUtil.getSysDate(), +1), Calendar.DAY_OF_MONTH)),
						null,
						userContext.getKokyakuId(),
						null);

		// �j���ꗗ�̎擾
		List<TbMHoliday> holidayList = holidayDao.selectAll();

		// �������C�u�������̎擾
		List<TbTPublishFile> publishFileList =
			publishFileDao.selectBy(userContext.getRefKokyakuId(), null);

		model.setSummaryStartDt(DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getFirstDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"));
		model.setSummaryEndDt(DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.getLastDayOfMonth(DateUtil.getSysDateTime()), "yyyyMMdd"),"yyyyMMdd"));
		model.setSumIncompleteCount(new BigDecimal(sumIncomplete));
		model.setSumCompleteCount(new BigDecimal(sumCompleted));
		model.setInqueryStatusList(inquiryStatusDtoList);
		model.setPublishFileList(publishFileList);

		model.setInquiryHistoryList(inquiryHistoryList);
		model.setHolidayList(holidayList);
		
		return model;
	}

	/**
	 * ���[�U�[���˗��Ǝ҂̏ꍇ�ɕ\����������擾���܂��B
	 *
	 * @param model ���j���[��ʃ��f��
	 * @return ���j���[��ʃ��f��
	 */
	private TB010MenuModel getConstractInfo(TB010MenuModel model) {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI,
				RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �K��\��敪Map
		Map<String, RcpMComCd> homonYoteiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		// ��t�`��Map
		Map<String, RcpMComCd> uketsukeKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);

		// �Ή����Č��ꗗ���擾
		List<RcpTIrai> iraiList =
			iraiDao.selectForOnGoinigRequests(userContext.getGyoshaCd());

		for (int i = 0; i < iraiList.size(); i++) {
			RcpTIrai irai = iraiList.get(i);

			if (StringUtils.isNotBlank(irai.getHomonKiboJikanKbn()) &&
				homonYoteiMap.containsKey(irai.getHomonKiboJikanKbn())) {
				// �K��\��敪
				irai.setHomonKiboJikanKbnNm(homonYoteiMap.get(
						irai.getHomonKiboJikanKbn()).getExternalSiteVal());
			}

			if (StringUtils.isNotBlank(irai.getUketsukeKeitaiKbn()) &&
				uketsukeKeitaiMap.containsKey(irai.getUketsukeKeitaiKbn())) {
				// ��t�`��
				irai.setUketsukeKeitaiKbnNm(uketsukeKeitaiMap.get(
						irai.getUketsukeKeitaiKbn()).getExternalSiteVal());
			}

			iraiList.set(i, irai);
		}

		// �Ǝҏ����擾
		RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(userContext.getGyoshaCd());

		model.setRequestList(iraiList);
		model.setGyosha(gyosha);

		return model;
	}

	/**
	 * �A�N�Z�X�\�t�q�kMap�擾�������s���܂��B
	 *
	 * @param role ����
	 * @return �A�N�Z�X�\�t�q�kMap
	 */
	public Map<String,TbMRoleUrl> getAccessUrl(String role) {
		// �A�N�Z�X�\�t�q�k�}�X�^����A�N�Z�X�\�t�q�kMap�擾
		return (roleUrlDao.selectAccessibleMap(role));
	}

	/**
	 * �W�v�J�n�����擾���܂��B
	 *
	 * @param kokyaku �ڋq�}�X�^���
	 * @return �W�v�J�n��
	 */
	private Timestamp getSummaryStartDt(RcpMKokyaku kokyaku) {
		// �W�v�J�n���̎擾
		Timestamp summaryStartDt = null;

		// �V�X�e����
		BigDecimal today =
			new BigDecimal(DateUtil.formatTimestamp(DateUtil.getSysDateTime(), "dd"));

		if (kokyaku.getShimeDay() == null || RcpMKokyakuKeiyaku.LAST_DAY_OF_SHIME_DAY == kokyaku.getShimeDay().intValue()) {
			// ���ߓ���NULL�A�܂��́A�����̏ꍇ�́A��������ݒ�
			summaryStartDt = DateUtil.getFirstDayOfMonth(DateUtil.formatTimestamp(
					DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
		} else if (today.compareTo(kokyaku.getShimeDay()) <= 0) {
			// �V�X�e�����������ߓ��̏ꍇ
			Date lastMonth = DateUtils.addMonths(DateUtil.getSysDate(), -1);

			// ���������擾
			Integer lastDate = Integer.parseInt(
					DateUtil.formatTimestamp(new Timestamp(
							DateUtil.getLastDayOfMonth(lastMonth.getTime())), "dd"));
			if ((kokyaku.getShimeDay().intValue() + 1) > lastDate.intValue()) {
				// ���ߓ��{�P���������𒴂��Ă����ꍇ�A��������ݒ�
				summaryStartDt = DateUtil.getFirstDayOfMonth(DateUtil.formatTimestamp(
						DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
			} else {
				// ����ȊO�̏ꍇ�́A�O���{�i���ߓ��{�P�j��ݒ�
				summaryStartDt = DateUtil.toTimestamp(
						DateUtil.formatTimestamp(DateUtil.toSqlTimestamp(lastMonth), "yyyyMM") +
						StringUtils.left(Integer.toString(kokyaku.getShimeDay().intValue() + 1), 2),
						"yyyyMMdd");
			}
		} else {
			// ����ȊO�i�V�X�e���������ߓ��j�̏ꍇ

			// ���������擾
			Integer lastDate = Integer.parseInt(
					DateUtil.formatTimestamp(new Timestamp(
							DateUtil.getLastDayOfMonth(DateUtil.getSysDateTime().getTime())), "dd"));

			if ((kokyaku.getShimeDay().intValue() + 1) > lastDate.intValue()) {
				// ���ߓ��{�P���������𒴂��Ă����ꍇ�A��������ݒ�
				summaryStartDt = DateUtil.getFirstDayOfMonth(DateUtil.formatTimestamp(
						DateUtil.getSysDateTime(), "yyyyMMdd"), "yyyyMMdd");
			} else {
				// ����ȊO�̏ꍇ�́A�����{�i���ߓ��{�P�j��ݒ�
				summaryStartDt = DateUtil.toTimestamp(
						DateUtil.formatTimestamp(DateUtil.getSysDateTime(), "yyyyMM") +
						StringUtils.left(Integer.toString(kokyaku.getShimeDay().intValue() + 1), 2),
						"yyyyMMdd");
			}
		}

		return summaryStartDt;
	}
	
	/**
	 * �ڋq�}�X�^����ڋq�����擾���܂��B
	 * 
	 * @param selectedKokyakuId �I�������ڋq���
	 * @return �ڋq�}�X�^���
	 */
	public RcpMKokyaku getKokyakuInfo(String selectedKokyakuId) {
		// �ڋq�}�X�^����ڋq���擾
		RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(selectedKokyakuId);
		if (kokyaku == null) {
			// �擾�ł��Ȃ������ꍇ�́A�V�X�e���G���[
			throw new ApplicationException("�ڋq�}�X�^��񂪎擾�ł��܂���B �ڋq�h�c=" + selectedKokyakuId);
		}
		
		return kokyaku;
	}
	
	/**
	 * �ϑ���Њ֘A�`�F�b�N���s���܂��B
	 *
	 * @param model���j���[�_�E�����[�h��ʃ��f��
	 * @return �`�F�b�N���� true:�`�F�b�NOK
	 */
	public boolean isValidDocumentDownload(TB010DocumentFileDownloadModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �Z�b�V�����̌������u40:�ϑ����SV�v�܂��́u41:�ϑ����OP�v�̏ꍇ
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			return outsourcerValidationLogic.isValid(userContext.getKaishaId(), model.getRealFileNm().substring(0,10));
		} else {
			return true;
		}
	}
}
