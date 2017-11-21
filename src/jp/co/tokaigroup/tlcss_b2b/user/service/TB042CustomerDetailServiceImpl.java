package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMBukkenShiteiGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFBukkenDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFKojinDao;
import jp.co.tokaigroup.reception.dao.RcpTBukkenShiteiGyoshaDao;
import jp.co.tokaigroup.reception.entity.RcpMBukkenShiteiGyosha;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFBukken;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKojin;
import jp.co.tokaigroup.reception.entity.RcpTBukkenShiteiGyosha;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB042CustomerDetailModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �����E�����ҏڍ׃T�[�r�X�����N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/06
 * @version 4.1 2015/11/04 J.Matsuba �a���ϊ�Map�擾�̏C��
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB042CustomerDetailServiceImpl extends TLCSSB2BBaseService
		implements TB042CustomerDetailService {

	/** �ڋq��{���擾���W�b�N */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** �ڋq�t���������}�X�^DAO */
	@Autowired
	private RcpMKokyakuFBukkenDao kokyakuBukkenDao;

	/** �����w��Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMBukkenShiteiGyoshaDao bukkenShiteiGyoshaMasterDao;

	/** �����w��Ǝ҃e�[�u��DAO */
	@Autowired
	private RcpTBukkenShiteiGyoshaDao bukkenShiteiGyoshaTableDao;

	/** �ڋq�t���l���}�X�^DAO */
	@Autowired
	private RcpMKokyakuFKojinDao kokyakuKojinDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �����w��Ǝ҃}�X�^�̃\�[�g�� */
	private static final String BUKKEN_SHITEI_GYOSHA_ORDER_BY = " ORDER BY HYOJI_JUN ASC";

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �����E�����ҏڍ׉�ʃ��f��
	 * @return �����E�����ҏڍ׉�ʃ��f��
	 * @throws ValidationException �ڋq��񂪎擾�o���Ȃ��ꍇ
	 */
	public TB042CustomerDetailModel getInitInfo(TB042CustomerDetailModel model)
	throws ValidationException {

		// �P�D�����\���p�����[�^�`�F�b�N
		if (StringUtils.isBlank(model.getKokyakuId())) {
			// �p�����[�^�̌ڋq�h�c���擾�ł��Ȃ��ꍇ�G���[
			throw new ApplicationException("�ڋqID�s���F�p�����[�^�̌ڋqID" );
		}

		// �Q�D�������擾����
		// �ݒ�pEntity
		RcpMKokyaku kokyakuEntity = new RcpMKokyaku();						// �ڋq�}�X�^Entity�i�f�[�^�擾�p�j
		RcpMKokyakuFBukken kokyakuBukkenEntity = new RcpMKokyakuFBukken();	// �ڋq�t���������}�X�^Entity�i�f�[�^�擾�p�j
		RcpMKokyakuFKojin kokyakuKojinEntity = new RcpMKokyakuFKojin();		// �ڋq�t���l���}�X�^Entity�i�f�[�^�擾�p�j

		// ��ʕ\������
		// �ڋq��{���擾
		kokyakuEntity = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());

		// �ڋq�t�����擾
		String kokyakuId = model.getKokyakuId();	// �p�����[�^�̌ڋqID

		if (RcpMKokyaku.KOKYAKU_KBN_BUKKEN.equals(kokyakuEntity.getKokyakuKbn())) {
			// �ڋq�}�X�^Entity�̌ڋq�敪���u3�F�����v�̏ꍇ�A�ڋq�t���������}�X�^Entity���擾����
			kokyakuBukkenEntity = kokyakuBukkenDao.selectByPrimaryKey(kokyakuId);

			// �\�[�g���̐ݒ�
			bukkenShiteiGyoshaMasterDao.setOrderBy(BUKKEN_SHITEI_GYOSHA_ORDER_BY);

			// �����w��Ǝ҃}�X�^Entity���X�g�̎擾
			List<RcpMBukkenShiteiGyosha> bukkenShiteiGyoshaMasterList = bukkenShiteiGyoshaMasterDao.selectAll();

			// �����w��Ǝ҃e�[�u��Entity���X�g�̎擾
			List<RcpTBukkenShiteiGyosha> bukkenShiteiGyoshaTableList = bukkenShiteiGyoshaTableDao.selectBy(null, kokyakuId);

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

		} else if (RcpMKokyaku.KOKYAKU_KBN_NYUKYOSHA.equals(kokyakuEntity.getKokyakuKbn())) {
			// �ڋq�}�X�^Entity�̌ڋq�敪���u4�F�����ҁE�l�v�̏ꍇ�A�ڋq�t���l���}�X�^Entity���擾����
			kokyakuKojinEntity = kokyakuKojinDao.selectByPrimaryKey(kokyakuId);

		} else {
			// ��L�ȊO�̏ꍇ�A�G���[��ʂɑJ��
			throw new ApplicationException("�ڋq�敪���s���ł��B" );
		}

		// �a���ϊ������́A�ڋq�t���������}�X�^Entity�����݂���̂ݏꍇ�s��
		// ���ڋq�敪���u3�F�����v���A�f�[�^�擾�o�����ꍇ
		if (kokyakuBukkenEntity != null) {
			// �a���ϊ��pMap�擾
			Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM);
			Map<String, Map<String, RcpMComCd>> convertMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);

			// �a���ϊ�����
			Map<String, RcpMComCd> mgrFormMap = convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM);
			String kanriKeitaiKbn = kokyakuBukkenEntity.getKanriKeitaiKbn();
			if (StringUtils.isNotBlank(kanriKeitaiKbn) && mgrFormMap.containsKey(kanriKeitaiKbn)) {
				// �Ǘ��`�Ԃ̖��̂��擾
				kokyakuBukkenEntity.setKanriKeitaiKbnNm(mgrFormMap.get(kanriKeitaiKbn).getExternalSiteVal());
			}

			// �z�N���̕\���p�ݒ�(�uYYYY/MM�v�`���֕ϊ�)
			kokyakuBukkenEntity.setChikuNengetsuDisplay(DateUtil.yyyymmPlusSlash(kokyakuBukkenEntity.getChikuNengetsu()));
		}

		// model ��Entity�f�[�^��ݒ�
		model.setKokyakuEntity(kokyakuEntity);
		model.setKokyakuBukkenEntity(kokyakuBukkenEntity);
		model.setKokyakuKojinEntity(kokyakuKojinEntity);

		return model;
	}

}

