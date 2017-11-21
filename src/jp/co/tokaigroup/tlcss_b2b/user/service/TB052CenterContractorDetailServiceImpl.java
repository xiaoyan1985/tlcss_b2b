package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpTGyoshaGyoshuDao;
import jp.co.tokaigroup.reception.dao.RcpTGyoshaTodofukenDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB052CenterContractorDetailModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �Z���^�[�Ǝҏڍ׃T�[�r�X�����N���X�B
 *
 * @author v145527
 * @version 1.0 2015/10/08
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB052CenterContractorDetailServiceImpl extends TLCSSB2BBaseService
implements TB052CenterContractorDetailService {

	/** ���ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMGyoshaDao rcpMGyoshaDao;

	/** �Ǝғs���{���e�[�u��DAO */
	@Autowired
	private RcpTGyoshaTodofukenDao rcpTGyoshaTodofukenDao;

	/** �ƎҋƎ�e�[�u��DAO */
	@Autowired
	private RcpTGyoshaGyoshuDao rcpTGyoshaGyoshuDao;

	/** �p�X���[�h�lDAO */
	@Autowired
	private NatosMPasswordDao natosMPasswordDao;

	/**
	 * �����\�����s���܂��B
	 *
	 * @param model �Z���^�[�Ǝҏڍ׉�ʃ��f��
	 * @return �Z���^�[�Ǝҏڍ׉�ʃ��f��
	 */
	public TB052CenterContractorDetailModel getInitInfo(
			TB052CenterContractorDetailModel model) {
		
		// �Ǝҏ��擾
		RcpMGyosha gyosha = rcpMGyoshaDao.selectByPrimaryKey(model.getGyoshaCd());
		if (gyosha == null) {
			// �Ǝҏ�񂪎擾�ł��Ȃ��ꍇ�A�r���G���[�Ƃ���
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		
		//----- ��ʍ��ڂ̎擾 -----//
		// ���ʃR�[�h�}�X�^����̎擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
			RcpMComCd.RCP_M_COM_CD_KBN_TODOFUKEN,
			RcpMComCd.RCP_M_COM_CD_KBN_GYOSHU,
			RcpMComCd.RCP_M_COM_CD_KBN_IRAI_MAIL_ATESAKI_FLG,
			RcpMComCd.RCP_M_COM_CD_KBN_IRAI_MAIL_ATESAKI_KBN);

		// �s���{���i�n��j���X�g�̎擾
		List<RcpMComCd> todofukenAreaList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TODOFUKEN);
		model.setTodofukenAreaList(todofukenAreaList);

		// �a���擾�p�̃}�b�v�̎擾
		Map<String, Map<String, RcpMComCd>> comCdMap = comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �Ǝ҃R�[�h�ꗗ�쐬
		List<String> gyoshaCdList = new ArrayList<String>();
		gyoshaCdList.add(gyosha.getGyoshaCd());

		// �X�V��Map
		Map<String, String> userMap = natosMPasswordDao.selectAllAsMap();
		// �s���{��(�n��)�ϊ�Map
		Map<String, String> todofukenAreaMap = rcpTGyoshaTodofukenDao.selectNmMapByArea(model.getGyoshaCd(), " ");
		// �Ǝ�ϊ�Map
		Map<String, String> gyoshuMap = rcpTGyoshaGyoshuDao.selectJoinNmMap(gyoshaCdList, " ");
		// ��ƈ˗����[���������t�t���OMap
		Map<String, RcpMComCd> iraiMailAtesakiFlgMap = comCdMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_MAIL_ATESAKI_FLG);
		// ��ƈ˗����[������敪Map
		Map<String, RcpMComCd> iraiMailAtesakiKbnMap = comCdMap.get(RcpMComCd.RCP_M_COM_CD_KBN_IRAI_MAIL_ATESAKI_KBN);
		
		// �a���ϊ�����
		// �ŏI�X�V��
		gyosha.setLastUpdIdNm(userMap.get(gyosha.getLastUpdId()));
		// �s���{���i�n��j
		gyosha.setTodofukenChikiMap(todofukenAreaMap);
		// �Ǝ�
		gyosha.setGyoshuNm(gyoshuMap.get(gyosha.getGyoshaCd()));
		// �������t�L��
		if (iraiMailAtesakiFlgMap != null && iraiMailAtesakiFlgMap.containsKey(gyosha.getIraiMailAtesakiFlg())) {
			gyosha.setIraiMailAtesakiFlgNm(iraiMailAtesakiFlgMap.get(gyosha.getIraiMailAtesakiFlg()).getComVal());
		}
		// ���惁�[���A�h���X�敪
		if (StringUtils.isNotBlank(gyosha.getIraiMailAtesakiKbn())
			&& iraiMailAtesakiKbnMap != null
			&& iraiMailAtesakiKbnMap.containsKey(gyosha.getIraiMailAtesakiKbn())) {
			gyosha.setIraiMailAtesakiKbnNm(iraiMailAtesakiKbnMap.get(gyosha.getIraiMailAtesakiKbn()).getComVal());
		}
		
		model.setTodofukenAreaMap(todofukenAreaMap);

		model.setGyosha(gyosha);



		return model;
	}

}
