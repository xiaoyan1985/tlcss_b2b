package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.RcpTKeiyakuTargetDao;
import jp.co.tokaigroup.reception.dto.RC014KeiyakuListDto;
import jp.co.tokaigroup.reception.dto.RC014KeiyakuTargetListDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKeiyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.kokyaku.model.RC014KokyakuKeiyakuListCondition;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB046ContractInfoModel;

/**
 * �_����T�[�r�X�����N���X�B
 *
 * @author v145527 ����
 * @version 1.0 2015/08/04
 * @version 1.1 2016/03/25 J.Matsuba �r���Ǘ��̏ꍇ�́A���{���ݒ菈���ǉ�
 * @version 1.2 2016/04/07 J.Matsuba ���{���A�������{�̏ꍇ�u�����v�ƕ\������悤�C��
 * @version 1.3 2016/08/05 H.Yamamura �r���Ǘ��_��̕�����ǉ�
 *
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB046ContractInfoServiceImpl extends TLCSSB2BBaseService
implements TB046ContractInfoService{

	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** ���Z�v�V�����ڋq�_����}�X�^DAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** ���Z�v�V�����ڋq�_����}�X�^DAO */
	@Autowired
	private RcpTKeiyakuTargetDao keiyakuTargetDao;

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** ���Z�v�V�������ʃ}�X�^DAO */
	@Autowired
	private RcpMComCdDao comCdDao;

	/** ���Z�v�V�����T�[�r�X�}�X�^DAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �_�����ʃ��f��
	 * @return �_�����ʃ��f��
	 */
	@Override
	public TB046ContractInfoModel getInitInfo(
			TB046ContractInfoModel model) {

		// �������擾����
		// �ڋq��{���擾
		RcpMKokyaku kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		// �_���񌟍�
		model = searchKeiyaku(model);
		// model ��Entity�f�[�^��ݒ�
		model.setKokyakuEntity(kokyakuEntity);
		return model;
	}
		
	/**
	 * �_����_��ꗗ���������܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return TB046ContractInfoModel ��ʃ��f��
	 */
	private TB046ContractInfoModel searchKeiyaku(TB046ContractInfoModel model) {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		RC014KokyakuKeiyakuListCondition condition = model.getCondition();
		
		// TB046�ڋq�ڍ׌_����_��ꗗ�ő匟���\����
		condition.setLimit(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TO_MAX));
		condition.setMaxCount(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TO_MAX));

		// �_��ꗗ�擾
		condition.setKokyakuId(model.getKokyakuId().replaceAll("C", ""));	// �ڋqID�̐擪�ꌅ�iC�j������
		List<RC014KeiyakuListDto> keiyakuList =
			kokyakuKeiyakuDao.selectKeiyakuShosaiList(model.getKokyakuId());

		if (keiyakuList != null && !keiyakuList.isEmpty()) {
			int limit = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TO_MAX);
			if (limit < keiyakuList.size()) {
				// �ő匟���\������茟�����ʂ̐��������ꍇ
				// �ő匟���\�������̂݁A�������ʂ�\��
				keiyakuList = keiyakuList.subList(0, limit);
			}
		}

		if (keiyakuList != null && !keiyakuList.isEmpty()) {
			// �ڋq���كt���O
			boolean isDiffrentKokyaku = false;
			String keiyakuKokyakuId = "";
			for (RC014KeiyakuListDto dto : keiyakuList) {
				if (!model.getKokyakuId().equals(dto.getSeikyusakiKokyakuId())) {
					// �p�����[�^�̌ڋq�h�c�ƈقȂ�ڋq�h�c���������ꍇ
					isDiffrentKokyaku = true;
					keiyakuKokyakuId = dto.getSeikyusakiKokyakuId();
					break;
				}
			}

			if (isDiffrentKokyaku && StringUtils.isNotBlank(keiyakuKokyakuId)) {
				// �p�����[�^�̌ڋq�h�c�ƈقȂ�ڋq�h�c���������ꍇ�́A
				// �_��҂̌ڋq�����擾
				RcpMKokyaku keiyakuKokyaku = kokyakuDao.selectByPrimaryKey(keiyakuKokyakuId);

				if (keiyakuKokyaku != null) {
					model.setKeiyakuKokyakuInfo(keiyakuKokyaku);
				}
			}
		}
		
		// �_��Ώۈꗗ
		List<RC014KeiyakuTargetListDto> keiyakuTargetList = null;

		if (model.getKeiyakuNo() != null) {
			// �p�����[�^�̌_��m�n���������ꍇ
			RcpMKokyakuKeiyaku keiyakuInfo =
				kokyakuKeiyakuDao.selectByPrimaryKey(model.getKeiyakuKokyakuId(), Integer.toString(model.getKeiyakuNo()));

			if (keiyakuInfo != null) {
				model.setKeiyakuUpdDt(keiyakuInfo.getUpdDt());

				// �T�[�r�X�}�X�^-�v���C�}���[���� ���� �T�[�r�X�}�X�^Entity ���擾
				RcpMService serviceInfo = serviceDao.selectByPrimaryKey(keiyakuInfo.getServiceKbn());
				if (serviceInfo != null && serviceInfo.isReception() || serviceInfo.isBuildingManagement()) {
					// �T�[�r�X��ʂ����Z�v�V�����̏ꍇ
					// TB046�ڋq�ڍ׌_����_��Ώۈꗗ�ő匟���\����
					condition.setLimit(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TARGET_TO_MAX));
					condition.setMaxCount(userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TARGET_TO_MAX));
					// �_��Ώۈꗗ�擾
					keiyakuTargetList =
						keiyakuTargetDao.selectKeiyakuTargetList(model.getKeiyakuKokyakuId(), model.getKeiyakuNo());

					if (keiyakuTargetList != null && !keiyakuTargetList.isEmpty()) {
						int limit = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_DETAIL_CONTRACT_TARGET_TO_MAX);
						if (limit < keiyakuTargetList.size()) {
							// �ő匟���\������茟�����ʂ̐��������ꍇ
							// �ő匟���\�������̂݁A�������ʂ�\��
							keiyakuTargetList = keiyakuTargetList.subList(0, limit);
						}
					}
				}
			}
		}
		
		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND,
					RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE,
					RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND,
					RcpMComCd.RCP_M_COM_CD_KBN_ENTRY_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_ADMISSION_COST,
					RcpMComCd.RCP_M_COM_CD_KBN_CLAIM_DEPT,
					RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE,
					RcpMComCd.RCP_M_COM_CD_KEIYAKU_KEITAI,
					RcpMComCd.RCP_M_COM_CD_JISSHI_KAISU);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �T�[�r�X�a���ϊ��pMap�擾
		List<RcpMService> serviceList = serviceDao.selectAll();
		Map<String, String> serviceMap = serviceDao.convertMap(serviceList);
		// �T�[�r�X��ʕϊ��pMap�擾
		Map<String, String> serviceShubetsuMap = serviceDao.convertServiceShubetsuMap(serviceList);
		// �����ނ̘a���ϊ�
		Map<String, RcpMComCd> lineKindMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND);
		// �ڋq�敪�̘a���ϊ�
		Map<String, RcpMComCd> kokyakuKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		// �ڋq��ʂ̘a���ϊ�
		Map<String, RcpMComCd> kokyakuShubetsuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// �\���敪�̘a���ϊ�
		Map<String, RcpMComCd> entryKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_ENTRY_KBN);
		// �����p�̘a���ϊ�
		Map<String, RcpMComCd> admissionCostMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_ADMISSION_COST);
		// ���ԑт̘a���ϊ�
		Map<String, RcpMComCd> timeZoneMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE);
		// ��{�����敪�̘a���ϊ�
		Map<String, RcpMComCd> kihonRyokinMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE);
		// �_��`�Ԃ̘a���ϊ�
		Map<String, RcpMComCd> keiyakuKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KEIYAKU_KEITAI);
		// ���{�敪�̘a���ϊ�
		Map<String, RcpMComCd> jishiKaisuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_JISSHI_KAISU);

		if (keiyakuList != null && !keiyakuList.isEmpty()) {
			// �a���ϊ�
			for (RC014KeiyakuListDto keiyakuDto : keiyakuList) {

				// �T�[�r�X
				keiyakuDto.setServiceKbnNm(serviceMap.get(keiyakuDto.getServiceKbn()));

				// �T�[�r�X���
				keiyakuDto.setServiceShubetsu(serviceShubetsuMap.get(keiyakuDto.getServiceKbn()));

				// �����p�A�\���敪
				if (keiyakuDto.isLifeSupport24()) {
					// ���C�t�T�|�[�g�Q�S�̏ꍇ�ɁA�a���ϊ�
					String admissionCostNm = "";
					if (admissionCostMap.containsKey(keiyakuDto.getNyukaiHiyoKbn())) {
						admissionCostNm = admissionCostMap.get(keiyakuDto.getNyukaiHiyoKbn()).getComVal();
					}
					keiyakuDto.setNyukaiHiyoKbnNm(admissionCostNm);

					String entryKbnNm = "";
					if (entryKbnMap.containsKey(keiyakuDto.getMoshikomiKbn())) {
						entryKbnNm = entryKbnMap.get(keiyakuDto.getMoshikomiKbn()).getComVal();
					}
					keiyakuDto.setMoshikomiKbnNm(entryKbnNm);
				}

				// �����ށA���ԑсA��{�����敪
				if (keiyakuDto.isReception()) {
					// ���Z�v�V�����̏ꍇ�ɁA�a���ϊ�

					String lineKindNm = "";
					if (lineKindMap.containsKey(keiyakuDto.getKaisenCd())) {
						lineKindNm = lineKindMap.get(keiyakuDto.getKaisenCd()).getComVal();
					}
					keiyakuDto.setKaisenCdNm(lineKindNm);

					String timeZoneNm = "";
					if (timeZoneMap.containsKey(keiyakuDto.getJikanCd())) {
						timeZoneNm = timeZoneMap.get(keiyakuDto.getJikanCd()).getComVal();
					}
					keiyakuDto.setJikanCdNm(timeZoneNm);

					String kihonRyokinKbnNm = "";
					if (kihonRyokinMap.containsKey(keiyakuDto.getKihonRyokinKbn())) {
						kihonRyokinKbnNm = kihonRyokinMap.get(keiyakuDto.getKihonRyokinKbn()).getComVal();
					}
					keiyakuDto.setKihonRyokinKbnNm(kihonRyokinKbnNm);
				}
				
				// �_��`�ԁA���{�敪
				if (keiyakuDto.isBuildingManagement()) {
					// �r���Ǘ��̏ꍇ�ɁA�a���ϊ�
					// �_��`��
					String keiyakuKeitaiNm = "";
					if (keiyakuKeitaiMap.containsKey(keiyakuDto.getKeiyakuType())) {
						keiyakuKeitaiNm = keiyakuKeitaiMap.get(keiyakuDto.getKeiyakuType()).getComVal();
					}
					keiyakuDto.setKeiyakuTypeNm(keiyakuKeitaiNm);

					// ���{�敪
					String jishiKaisuNm = "";
					if (jishiKaisuMap.containsKey(keiyakuDto.getJisshiKbn())) {
						jishiKaisuNm = jishiKaisuMap.get(keiyakuDto.getJisshiKbn()).getComVal();
					}
					keiyakuDto.setJisshiKbnNm(jishiKaisuNm);
				}
			}
		}

		if (keiyakuTargetList != null && !keiyakuTargetList.isEmpty()) {
			// �a���ϊ�
			for (RC014KeiyakuTargetListDto keiyakuTargetDto : keiyakuTargetList) {

				// �ڋq�敪
				String kokyakuKbnNm = "";
				if (kokyakuKbnMap.containsKey(keiyakuTargetDto.getKokyakuKbn())) {
					kokyakuKbnNm = kokyakuKbnMap.get(keiyakuTargetDto.getKokyakuKbn()).getComVal();
				}
				keiyakuTargetDto.setKokyakuKbnNm(kokyakuKbnNm);

				// �ڋq���
				String kokyakuShubetsuNm = "";
				if (kokyakuShubetsuMap.containsKey(keiyakuTargetDto.getKokyakuShubetsu())) {
					kokyakuShubetsuNm = kokyakuShubetsuMap.get(keiyakuTargetDto.getKokyakuShubetsu()).getComVal();
				}
				keiyakuTargetDto.setKokyakuShubetsuNm(kokyakuShubetsuNm);
			}
		}
		
		// �_��Ώۈꗗ�ɕ\�����镶����ݒ�
		if (model.getKeiyakuNo() != null) {
			// �p�����[�^�̌_��No���擾�ł����ꍇ
			for(RC014KeiyakuListDto kokyakuKeiyakuDto : keiyakuList) {
				// �����̌ڋqID�ƌ_��NO����v����ꍇ
				if (model.getKeiyakuNo() == kokyakuKeiyakuDto.getKeiyakuNo().intValue()
						&& kokyakuKeiyakuDto.getKokyakuId().equals(model.getKeiyakuKokyakuId())) {
					String keiyakuTaishoIchiran = "";
					if (kokyakuKeiyakuDto.isReception()) {
						// �T�[�r�X��ʂ��u1:���Z�v�V�����v�̏ꍇ
						keiyakuTaishoIchiran = "�y"
							+ kokyakuKeiyakuDto.getServiceKbnNm()
							+ "�@"
							+ kokyakuKeiyakuDto.getKaisenCdNm()
							+ "�@"
							+ kokyakuKeiyakuDto.getJikanCdNm()
							+ "�@�_��Ώہz";
					} else if (kokyakuKeiyakuDto.isBuildingManagement()) {
						// �T�[�r�X��ʂ��u3:�r���Ǘ��v�̏ꍇ
						keiyakuTaishoIchiran = "�y"
							+ kokyakuKeiyakuDto.getServiceKbnNm()
							+ "�@�_��Ώہz";
					}
					model.setKeiyakuTaisho(keiyakuTaishoIchiran);
				}
			}
		}

		model.setKeiyakuList(keiyakuList);
		model.setKeiyakuTargetList(keiyakuTargetList);

		return model;
	
	}

}
