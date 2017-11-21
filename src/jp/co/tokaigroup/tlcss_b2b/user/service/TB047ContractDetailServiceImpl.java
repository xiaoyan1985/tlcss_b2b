package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKeiyakuTelDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKLifeDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKRcpDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKeiyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dto.RC020KokyakuShosaiKeiyakuShosaiDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKeiyakuTel;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKLife;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKRcp;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKeiyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.kokyaku.logic.RC020KokyakuShosaiKeiyakuShosaiLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB047ContractDetailModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �ڋq�ڍ׌_��ڍ׏��T�[�r�X�����N���X�B
 *
 * @author k003858
 * @version 1.0
 * @version 1.1 2016/03/24 J.Matsuba �r���Ǘ��p��ʕ\���ɂ��ǉ�
 * @version 1.2 2016/03/29 S.Nakano ���{�j���擾�A�󒍊z�^�C�g���擾�ȂǏC��
 * @version 1.3 2016/07/14 C.Kobayashi �r���Ǘ����擾�̃��W�b�N���Ή�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB047ContractDetailServiceImpl extends TLCSSB2BBaseService
		implements TB047ContractDetailService {
	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** ���Z�v�V�����T�[�r�X�}�X�^DAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/** ���Z�v�V�����ڋq�_����}�X�^DAO */
	@Autowired
	private RcpMKokyakuKeiyakuDao kokyakuKeiyakuDao;

	/** �ڋq�_�񃊃Z�v�V�����}�X�^DAO */
	@Autowired
	private RcpMKokyakuKRcpDao kokyakuKRcpDao;

	/** �ڋq�_�񃉃C�t�T�|�[�g�}�X�^DAO */
	@Autowired
	private RcpMKokyakuKLifeDao kokyakuKLifeDao;

	/** �_��d�b�ԍ��}�X�^DAO */
	@Autowired
	private RcpMKeiyakuTelDao keiyakuTelDao;

	/** �ڋq�ڍ׌_��ڍ׃��W�b�N */
	@Autowired
	private RC020KokyakuShosaiKeiyakuShosaiLogic keiyakuShosai;

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �ڋq�ڍ׌_��ڍ׏���ʃ��f��
	 * @return �ڋq�ڍ׌_��ڍ׏���ʃ��f��
	 */
	public TB047ContractDetailModel getInitInfo(TB047ContractDetailModel model) {
		
		// �ڋq��{�����擾����
		RcpMKokyaku kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		model.setKokyakuEntity(kokyakuEntity);
		

			// �ڋq�_����̎擾
			RcpMKokyakuKeiyaku keiyakuInfo =
				kokyakuKeiyakuDao.selectByPrimaryKey(model.getKeiyakuKokyakuId(), String.valueOf(model.getKeiyakuNo()));

			if (keiyakuInfo == null) {
				return model;
			}

			// �T�[�r�X�}�X�^���擾
			RcpMService serviceInfo = null;
			// �T�[�r�X�}�X�^����̎擾
			if (StringUtils.isNotBlank(keiyakuInfo.getServiceKbn())) {
				serviceInfo = serviceDao.selectByPrimaryKey(keiyakuInfo.getServiceKbn());

				keiyakuInfo.setServiceKbnNm(serviceInfo.getServiceKbnNm());
			}
			model.setServiceInfo(serviceInfo);

			// ���ʃR�[�h�}�X�^����̎擾
			Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
					RcpMComCd.RCP_M_COM_CD_KBN_CLAIM_DEPT, RcpMComCd.RCP_M_COM_CD_KBN_CLAIM_METHOD,
					RcpMComCd.RCP_M_COM_CD_KBN_ACCOUNT_KBN, RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND,
					RcpMComCd.RCP_M_COM_CD_KBN_CHARGE_TARGET, RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE,
					RcpMComCd.RCP_M_COM_CD_KBN_ENTRY_KBN, RcpMComCd.RCP_M_COM_CD_KBN_BUILD_FORM,
					RcpMComCd.RCP_M_COM_CD_KBN_ADMISSION_COST, RcpMComCd.RCP_M_COM_CD_KBN_NYUKYO_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_SEX, RcpMComCd.RCP_M_COM_CD_KBN_MAIL_ADDRESS_KBN,
					RcpMComCd.RCP_M_COM_CD_KBN_RELATION, RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE,
					RcpMComCd.RCP_M_COM_CD_KBN_KANRIHI_FURIKAE_KBN);

			// �a���ϊ��pMap
			Map<String, Map<String, RcpMComCd>> convertMap =
				comCdDao.convertMapAsKeyToEntity(comKbnMap);

			if (serviceInfo != null && serviceInfo.isReception()) {
				// �T�[�r�X�敪��1:���Z�v�V�����̏ꍇ
				// �ڋq�_�񃊃Z�v�V�������̎擾
				RcpMKokyakuKRcp rcpInfo =
					kokyakuKRcpDao.selectByPrimaryKey(model.getKeiyakuKokyakuId(), String.valueOf(model.getKeiyakuNo()));

				// �a���ϊ�
				model.setRcpInfo(convertOfRcpInfo(rcpInfo, convertMap));
			} else if (serviceInfo != null && serviceInfo.isLifeSupport24()) {
				// �T�[�r�X�敪��2:���C�t�T�|�[�g�Q�S�̏ꍇ
				// �ڋq�_�񃉃C�t�T�|�[�g���̎擾
				RcpMKokyakuKLife lifeInfo =
					kokyakuKLifeDao.selectByPrimaryKey(model.getKeiyakuKokyakuId(), model.getKeiyakuNo());

				// �a���ϊ�
				model.setLifeInfo(convOfLifeInfo(lifeInfo, convertMap));
			} else if (serviceInfo != null && serviceInfo.isBuildingManagement()) {
				// �T�[�r�X�敪��3:�r���Ǘ��̏ꍇ
				// �ڋq�_��r���Ǘ����̎擾
				RC020KokyakuShosaiKeiyakuShosaiDto keiyakuShosaiDto = 
					keiyakuShosai.getKokyakuKeiyakuBuildingInfo(model.getKokyakuId(), model.getKeiyakuNo());
				model.setKeiyakuShosaiDto(keiyakuShosaiDto);
			}

			// �a���ϊ�
			model.setKeiyakuInfo(convOfKeiyakuInfo(keiyakuInfo, convertMap));

			// �_��d�b�ԍ��}�X�^�f�[�^�i�_��d�b�ԍ��w��j�̎擾
			RcpMKeiyakuTel keiyakuTel =
				keiyakuTelDao.selectByKeiyakuTelNo(model.getKeiyakuInfo().getMadoguchiTel());
			model.setKeiyakuTelInfo(keiyakuTel);

		return model;
	}

	/**
	 * �ڋq�_����̘a���ϊ����s���܂��B
	 *
	 * @param keiyakuInfo �ڋq�_����}�X�^�G���e�B�e�B
	 * @param convertMap �a���ϊ��pMap
	 * @return RcpMKokyakuKeiyaku �ڋq�_����}�X�^�G���e�B�e�B
	 */
	public RcpMKokyakuKeiyaku convOfKeiyakuInfo(RcpMKokyakuKeiyaku keiyakuInfo, Map<String, Map<String, RcpMComCd>> convertMap) {

		// ������̘a���ϊ�
		Map<String, RcpMComCd> seikyusakiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_CLAIM_DEPT);
		// �������@�̘a���ϊ�
		Map<String, RcpMComCd> seikyuKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_CLAIM_METHOD);
		// �����敪�̘a���ϊ�
		Map<String, RcpMComCd> kozaNoKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_ACCOUNT_KBN);
		// �Ǘ���U�փt���O�̘a���ϊ�
		Map<String, RcpMComCd> kanrihiFurikaeMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KANRIHI_FURIKAE_KBN);

		// ������擾
		if (seikyusakiMap != null && seikyusakiMap.containsKey(keiyakuInfo.getSeikyusakiCd())) {
			keiyakuInfo.setSeikyusakiNm(seikyusakiMap.get(keiyakuInfo.getSeikyusakiCd()).getComVal());
		}

		// �������@�擾
		if (seikyuKbnMap != null && seikyuKbnMap.containsKey(keiyakuInfo.getSeikyuKbn())) {
			keiyakuInfo.setSeikyuKbnNm(seikyuKbnMap.get(keiyakuInfo.getSeikyuKbn()).getComVal());
		}

		// �����敪�擾
		if (kozaNoKbnMap != null && kozaNoKbnMap.containsKey(keiyakuInfo.getKozaNoKbn())) {
			keiyakuInfo.setKozaNoKbnNm(kozaNoKbnMap.get(keiyakuInfo.getKozaNoKbn()).getComVal());
		}

		// �Ǘ���U�փt���O�擾
		if (kanrihiFurikaeMap != null && kanrihiFurikaeMap.containsKey(keiyakuInfo.getKanrihiFurikaeFlg())) {
			keiyakuInfo.setKanrihiFurikaeFlgNm(kanrihiFurikaeMap.get(keiyakuInfo.getKanrihiFurikaeFlg()).getComVal());
		}

		return keiyakuInfo;
	}

	/**
	 * �ڋq�_�񃊃Z�v�V�������̘a���ϊ����s���܂��B
	 *
	 * @param rcpInfo �ڋq�_�񃊃Z�v�V�������}�X�^�G���e�B�e�B
	 * @param convertMap �a���ϊ��pMap
	 * @return RcpMKokyakuKRcp �ڋq�_�񃊃Z�v�V�������}�X�^�G���e�B�e�B
	 */
	public RcpMKokyakuKRcp convertOfRcpInfo(RcpMKokyakuKRcp rcpInfo, Map<String, Map<String, RcpMComCd>> convertMap) {

		if (rcpInfo != null) {

			// �����ނ̘a���ϊ�
			Map<String, RcpMComCd> kaisenCdMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_LINE_KIND);
			// �ۋ��Ώۂ̘a���ϊ�
			Map<String, RcpMComCd> kakinCdMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_CHARGE_TARGET);
			// ��{�����敪�̘a���ϊ�
			Map<String, RcpMComCd> kihonRyokinKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_BASIC_PRICE);
			// ���ԑт̘a���ϊ�
			Map<String, RcpMComCd> jikanCdMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TIME_ZONE);

			// �����ގ擾
			if (kaisenCdMap != null && kaisenCdMap.containsKey(rcpInfo.getKaisenCd())) {
				rcpInfo.setKaisenNm(kaisenCdMap.get(rcpInfo.getKaisenCd()).getComVal());
			}

			// �ۋ��Ώێ擾
			if (kakinCdMap != null && kakinCdMap.containsKey(rcpInfo.getKakinCd())) {
				rcpInfo.setKakinNm(kakinCdMap.get(rcpInfo.getKakinCd()).getComVal());
			}

			// ��{�����敪�擾
			if (kihonRyokinKbnMap != null && kihonRyokinKbnMap.containsKey(rcpInfo.getKihonRyokinKbn())) {
				rcpInfo.setKihonRyokinKbnNm(kihonRyokinKbnMap.get(rcpInfo.getKihonRyokinKbn()).getComVal());
			}

			// ���ԑю擾
			if (jikanCdMap != null && kakinCdMap.containsKey(rcpInfo.getJikanCd())) {
				rcpInfo.setJikanNm(jikanCdMap.get(rcpInfo.getJikanCd()).getComVal());
			}
		}

		return rcpInfo;
	}

	/**
	 * �ڋq�_�񃉃C�t�T�|�[�g���̘a���ϊ����s���܂��B
	 *
	 * @param lifeInfo �ڋq�_����}�X�^�G���e�B�e�B
	 * @param convertMap �a���ϊ��pMap
	 * @return RcpMKokyakuKeiyaku �ڋq�_����}�X�^�G���e�B�e�B
	 */
	public RcpMKokyakuKLife convOfLifeInfo(RcpMKokyakuKLife lifeInfo, Map<String, Map<String, RcpMComCd>> convertMap) {

		if (lifeInfo != null) {

			// �\���敪�̘a���ϊ�
			Map<String, RcpMComCd> moshikomiKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_ENTRY_KBN);
			// �����`�ԋ敪�̘a���ϊ�
			Map<String, RcpMComCd> tatemonoTypeMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_BUILD_FORM);
			// �����p�敪�̘a���ϊ�
			Map<String, RcpMComCd> nyukaiHiyoKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_ADMISSION_COST);
			// �����敪�̘a���ϊ�
			Map<String, RcpMComCd> nyukyoKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_NYUKYO_KBN);
			// ���ʂ̘a���ϊ�
			Map<String, RcpMComCd> sexKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_SEX);
			// ���[���A�h���X�敪�̘a���ϊ�
			Map<String, RcpMComCd> mailAddressKbnMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_MAIL_ADDRESS_KBN);
			// �����i�����l�������P�C�����l�������Q�j�̘a���ϊ�
			Map<String, RcpMComCd> zokugaraMap =
				convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_RELATION);

			// �\���敪�擾
			if (moshikomiKbnMap != null && moshikomiKbnMap.containsKey(lifeInfo.getMoshikomiKbn())) {
				lifeInfo.setMoshikomiKbnNm(moshikomiKbnMap.get(lifeInfo.getMoshikomiKbn()).getComVal());
			}

			// �����`�ԋ敪�擾
			if (tatemonoTypeMap != null && tatemonoTypeMap.containsKey(lifeInfo.getTatemonoType())) {
				lifeInfo.setTatemonoTypeNm(tatemonoTypeMap.get(lifeInfo.getTatemonoType()).getComVal());
			}

			// �����p�敪�擾
			if (nyukaiHiyoKbnMap != null && nyukaiHiyoKbnMap.containsKey(lifeInfo.getNyukaiHiyoKbn())) {
				lifeInfo.setNyukaiHiyoKbnNm(nyukaiHiyoKbnMap.get(lifeInfo.getNyukaiHiyoKbn()).getComVal());
			}

			// �����敪�擾
			if (nyukyoKbnMap != null && nyukyoKbnMap.containsKey(lifeInfo.getNyukyoKbn())) {
				lifeInfo.setNyukyoKbnNm(nyukyoKbnMap.get(lifeInfo.getNyukyoKbn()).getComVal());
			}

			// ���ʎ擾
			if (sexKbnMap != null && sexKbnMap.containsKey(lifeInfo.getSexKbn())) {
				lifeInfo.setSexKbnNm(sexKbnMap.get(lifeInfo.getSexKbn()).getComVal());
			}

			// ���[���A�h���X�敪�擾
			if (mailAddressKbnMap != null && mailAddressKbnMap.containsKey(lifeInfo.getMailAddressKbn())) {
				lifeInfo.setMailAddressKbnNm(mailAddressKbnMap.get(lifeInfo.getMailAddressKbn()).getComVal());
			}

			// �����擾
			if (zokugaraMap != null && zokugaraMap.containsKey(lifeInfo.getKinkyuTsudukiKbn())) {
				lifeInfo.setKinkyuZokugaraNm(zokugaraMap.get(lifeInfo.getKinkyuTsudukiKbn()).getComVal());
			}

			// �����l�������P�擾
			if (zokugaraMap != null && zokugaraMap.containsKey(lifeInfo.getDokyoTsudukiKbn1())) {
				lifeInfo.setDoukyoZokugara1Nm(zokugaraMap.get(lifeInfo.getDokyoTsudukiKbn1()).getComVal());
			}

			// �����l�������Q�擾
			if (zokugaraMap != null && zokugaraMap.containsKey(lifeInfo.getDokyoTsudukiKbn2())) {
				lifeInfo.setDoukyoZokugara2Nm(zokugaraMap.get(lifeInfo.getDokyoTsudukiKbn2()).getComVal());
			}
		}

		return lifeInfo;
	}
}