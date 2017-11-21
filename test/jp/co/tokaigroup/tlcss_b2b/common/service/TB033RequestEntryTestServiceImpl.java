package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.SosMUserInfoDao;
import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.SosMUserInfo;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB033RequestEntryTestModel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(value="txManager", readOnly = true)
@Scope("prototype")
public class TB033RequestEntryTestServiceImpl extends TLCSSB2BBaseService 
		implements TB033RequestEntryTestService {

	/** ���Z�v�V�������ʃ}�X�^DAO */
	@Autowired
	private RcpMComCdDao comCdDao;

	/** �˗����sDAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** NATOS�p�X���[�h�lDAO */
	@Autowired
	private NatosMPasswordDao natosPasswordDao;

	/** SOS���[�U�t�����lDAO */
	@Autowired
	private SosMUserInfoDao sosUserInfoDao;

	/** �₢���킹���sDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** �Ǝ҃}�X�^DAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;



	/**
	 * �������擾�������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB033RequestEntryTestModel getInitInfo(
			TB033RequestEntryTestModel model) {
		// ���ʃR�[�h�}�X�^���X�gMap�̎擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		// ���ʃR�[�h�}�X�^���X�gMap���K��\������X�g���擾
		model.setHomonYoteiYmdList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI));

		// ���[�U�t�����}�X�^-�E���敪�w�茟�����烆�[�U���X�g���擾
		List<String> shokumuKbnList = new ArrayList<String>();
		shokumuKbnList.add(Constants.SHOKUMU_KBN_SOS_KANRISHA);
		shokumuKbnList.add(Constants.SHOKUMU_KBN_SV);
		shokumuKbnList.add(Constants.SHOKUMU_KBN_OP);
		List<SosMUserInfo> userInfoList = sosUserInfoDao.selectByShokumuKbnList(shokumuKbnList);
		List<String> loginIdList = new ArrayList<String>();
		if (userInfoList != null && userInfoList.size() != 0) {
			for (SosMUserInfo userInfo : userInfoList) {
				loginIdList.add(userInfo.getUserId());
			}
		} else {
			loginIdList.add("");
		}

		// �����S���҃��X�g�擾�B�擾�������[�U���X�g�������Ƀp�X���[�h�}�X�^Entity���X�g�̎擾
		List<NatosMPassword> passwordList = natosPasswordDao.selectByList(loginIdList, model.getIrai().getTantoshaId());
		model.setNatosMPasswordList(passwordList);

		// �₢���킹���Entity�̎擾
		RcpTToiawase  toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// �������ʂ�null�̏ꍇ�A�ȉ��̃G���[���b�Z�[�W���o��
			throw new ValidationException(new ValidationPack().addError("MSG0003", ""));
		}
		model.setToiawase(toiawase);

		if (StringUtils.isNotBlank(toiawase.getKokyakuId())) {
			// �ڋq���̎擾
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(toiawase.getKokyakuId());
			if (kokyaku == null) {
				// �ڋq�}�X�^�̏�񂪎擾�ł��Ȃ��ꍇ�́A�r���G���[
				throw new ValidationException(new ValidationPack().addError("MSG0003", ""));
			}
			model.setKokyaku(kokyaku);
		}

		if (model.isExceptionFlg()) {
			// ��O�������́A�˗������擾���Ȃ�
			return model;
		}

		// �˗����̎擾
		if (model.isUpdate()) {
			RcpTIrai irai = iraiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
			if (irai == null) {
				// �������ʂ�null�̏ꍇ�A�ȉ��̃G���[���b�Z�[�W���o��
				throw new ValidationException(new ValidationPack().addError("MSG0003", ""));
			}
			model.setIrai(irai);

			// �p�X���[�h�}�X�^���(�ŏI�X�V��ID)�̎擾
			NatosMPassword natosPassword = natosPasswordDao.selectByPrimaryKey(irai.getLastUpdId());
			if (natosPassword == null) {
				natosPassword = new NatosMPassword();
				natosPassword.setUserNm(irai.getLastUpdId());
			}
			model.setNatosMPassword(natosPassword);

			// �p�X���[�h�}�X�^���(�ŏI�����ID)�̎擾
			NatosMPassword natosPasswordLastPrintId = natosPasswordDao.selectByPrimaryKey(irai.getLastPrintId());
			if (natosPasswordLastPrintId == null) {
				natosPasswordLastPrintId = new NatosMPassword();
				natosPasswordLastPrintId.setUserNm(irai.getLastUpdId());
			}
			model.setNatosPasswordLastPrintId(natosPasswordLastPrintId);

			// �˗��Ǝ҃}�X�^���X�g�̎擾
			RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(irai.getIraiGyoshaCd());
			model.setGyosha(gyosha);

		}
		return model;
	}

	/**
	 * �˗����擾�������s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	public TB033RequestEntryTestModel getIraiInfo(
			TB033RequestEntryTestModel model) {
		return model;
	}

}
