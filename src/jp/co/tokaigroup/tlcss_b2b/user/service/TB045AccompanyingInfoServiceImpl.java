package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMBukkenShiteiGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFBukkenDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFKanriDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFKojinDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFOoyaDao;
import jp.co.tokaigroup.reception.dao.RcpTBukkenShiteiGyoshaDao;
import jp.co.tokaigroup.reception.entity.RcpMBukkenShiteiGyosha;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFBukken;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKanri;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKojin;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFOoya;
import jp.co.tokaigroup.reception.entity.RcpTBukkenShiteiGyosha;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB045AccompanyingInfoModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �ڋq�t�����o�^�T�[�r�X�����N���X�B
 *
 * @author k003316
 * @version 1.0 2015/08/03
 * @version 1.1 2015/11/04 J.Matsuba �����w��Ǝ҂Ɋւ��鏈���ǉ�
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB045AccompanyingInfoServiceImpl extends TLCSSB2BBaseService
		implements TB045AccompanyingInfoService {
	
	/** ���Z�v�V�������ʃ}�X�^DAO */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;
	
	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;
	
	/** �ڋq�t���������}�X�^DAO */
	@Autowired
	private RcpMKokyakuFBukkenDao kokyakuFBukkenDao;

	/** �����w��Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMBukkenShiteiGyoshaDao bukkenShiteiGyoshaMasterDao;

	/** �����w��Ǝ҃e�[�u��DAO */
	@Autowired
	private RcpTBukkenShiteiGyoshaDao bukkenShiteiGyoshaTableDao;

	/** �ڋq�t���Ǘ���Џ��}�X�^DAO */
	@Autowired
	private RcpMKokyakuFKanriDao kokyakuFKanriDao;

	/** �ڋq�t���l���}�X�^DAO */
	@Autowired
	private RcpMKokyakuFKojinDao kokyakuFKojinDao;

	/** �ڋq�t����Ə��}�X�^DAO */
	@Autowired
	private RcpMKokyakuFOoyaDao kokyakuFOoyaDao;

	/** �����w��Ǝ҃}�X�^�̃\�[�g�� */
	private static final String BUKKEN_SHITEI_GYOSHA_ORDER_BY = " ORDER BY HYOJI_JUN ASC";

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �ڋq�ڍוt����񃂃f��
	 * @return �ڋq�ڍוt����񃂃f��
	 */
	public TB045AccompanyingInfoModel getInitInfo(TB045AccompanyingInfoModel model) {
		
		// �ڋq��{�����擾����
		RcpMKokyaku kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		model.setKokyakuEntity(kokyakuEntity);
		
		// �ڋq�敪���Ǘ���Ђ̏ꍇ
		if (model.isRealEstate()) {
			
			// �ڋq�t���Ǘ����
			RcpMKokyakuFKanri kokyakuFKanri = kokyakuFKanriDao.selectByPrimaryKey(model.getKokyakuId());
			model.setFuzuiKanriInfo(kokyakuFKanri);
			
		// �ڋq�敪�������̏ꍇ
		} else if (model.isProperty()) {
			
			// �ڋq�t������
			RcpMKokyakuFBukken kokyakuFBukken = kokyakuFBukkenDao.selectByPrimaryKey(model.getKokyakuId());
			
			// �\���p�̒z�N���ɕϊ�
			if (kokyakuFBukken != null && StringUtils.isNotBlank(kokyakuFBukken.getChikuNengetsu())) {
				
				kokyakuFBukken.setChikuNengetsuDisplay(
						DateUtil.yyyymmPlusSlash(kokyakuFBukken.getChikuNengetsu()));
			}
			
			model.setFuzuiBukkenInfo(kokyakuFBukken);

			// �\�[�g���̐ݒ�
			bukkenShiteiGyoshaMasterDao.setOrderBy(BUKKEN_SHITEI_GYOSHA_ORDER_BY);

			// �����w��Ǝ҃}�X�^Entity���X�g�̎擾
			List<RcpMBukkenShiteiGyosha> bukkenShiteiGyoshaMasterList = bukkenShiteiGyoshaMasterDao.selectAll();

			// �����w��Ǝ҃e�[�u��Entity���X�g�̎擾
			List<RcpTBukkenShiteiGyosha> bukkenShiteiGyoshaTableList = bukkenShiteiGyoshaTableDao.selectBy(null, model.getKokyakuId());

			// �\���pEntity���X�g
			List<RcpTBukkenShiteiGyosha> displayBukkenShiteiGyoshaTableList = new ArrayList<RcpTBukkenShiteiGyosha>();

			// �����w��Ǝ҃}�X�^�ƕ����w��Ǝ҃e�[�u���̕R�t��
			for (RcpMBukkenShiteiGyosha bukkenShiteiGyoshaMaster : bukkenShiteiGyoshaMasterList) {
				for (RcpTBukkenShiteiGyosha bukkenShiteiGyoshaTable : bukkenShiteiGyoshaTableList) {
					if (bukkenShiteiGyoshaMaster.getGyoshuCd().equals(bukkenShiteiGyoshaTable.getGyoshuCd())) {
						// �Ǝ�R�[�h���������ꍇ�A�\���pEntity�Ɋe�����i�[
						RcpTBukkenShiteiGyosha displayBukkenShiteiGyosha = new RcpTBukkenShiteiGyosha();

						displayBukkenShiteiGyosha.setGyoshuNm(bukkenShiteiGyoshaMaster.getGyoshuNm());
						displayBukkenShiteiGyosha.setName(bukkenShiteiGyoshaTable.getName());
						displayBukkenShiteiGyosha.setTelNo(bukkenShiteiGyoshaTable.getTelNo());
						displayBukkenShiteiGyosha.setFaxNo(bukkenShiteiGyoshaTable.getFaxNo());
						displayBukkenShiteiGyosha.setMailAddress(bukkenShiteiGyoshaTable.getMailAddress());
						displayBukkenShiteiGyosha.setBiko(bukkenShiteiGyoshaTable.getBiko());

						// �\���pList�Ɋi�[
						displayBukkenShiteiGyoshaTableList.add(displayBukkenShiteiGyosha);

						break;
					}
				}
			}

			model.setBukkenShiteiGyoshaTableList(displayBukkenShiteiGyoshaTableList);

		// �ڋq�敪����Ƃ̏ꍇ
		} else if (model.isLandlord()) {
			
			// �ڋq�t����Ə��̎擾
			RcpMKokyakuFOoya kokyakuFOoya = kokyakuFOoyaDao.selectByPrimaryKey(model.getKokyakuId());
			model.setFuzuiOoyaInfo(kokyakuFOoya);
			
		// �ڋq�敪�������ҁE�l�̏ꍇ
		} else if (model.isTenant()) {
			
			// �ڋq�t���l
			RcpMKokyakuFKojin kokyakuFKojin = kokyakuFKojinDao.selectByPrimaryKey(model.getKokyakuId());
			model.setFuzuiKojinInfo(kokyakuFKojin);
			
		// �ڋq�敪���z��O�̏ꍇ
		} else {
			throw new ApplicationException("�ڋq�敪�s���F" + model.getKokyakuEntity().getKokyakuKbn());
		}
		
		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM);
		Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �Ǘ��`�Ԃ̘a���ϊ�
		Map<String, RcpMComCd> mgrFormMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM);
		
		// ������񂪎擾�o���Ă���ꍇ�͕ϊ�
		if (model.isProperty() && model.getFuzuiBukkenInfo() != null) {
			
			RcpMKokyakuFBukken bukkenInfo = model.getFuzuiBukkenInfo();
			// �Ǘ��`�Ԗ��̐ݒ�
			if (mgrFormMap.containsKey(bukkenInfo.getKanriKeitaiKbn())) {
				bukkenInfo.setKanriKeitaiKbnNm(mgrFormMap.get(bukkenInfo.getKanriKeitaiKbn()).getExternalSiteVal());
			}
			model.setFuzuiBukkenInfo(bukkenInfo);
		}
		
		return model;
	}
}