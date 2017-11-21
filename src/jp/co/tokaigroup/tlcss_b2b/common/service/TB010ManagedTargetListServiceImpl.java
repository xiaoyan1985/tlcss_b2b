package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMGrpKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMRefKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMRoleUrlDao;
import jp.co.tokaigroup.reception.dao.TbMUserDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMGrpKokyaku;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010ManagedTargetListModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * �Ǘ��Ώۈꗗ�T�[�r�X�����N���X�B
 *
 * @author v140546
 * @version 4.0 2014/09/25
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB010ManagedTargetListServiceImpl extends TLCSSB2BBaseService implements TB010ManagedTargetListService {

	/** ���Z�v�V�����ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** �O���T�C�g�V�X�e�� ���[�U�[�}�X�^DAO */
	@Autowired
	private TbMUserDao userDao;

	/** ���Z�v�V���� �O���[�v�ڋq�}�X�^�i�s�n�j�`�h�p�jDAO */
	@Autowired
	private TbMGrpKokyakuDao grpKokyakuDao;

	/** ���Z�v�V���� �Q�ƌڋq�}�X�^ */
	@Autowired
	private TbMRefKokyakuDao refKokyakuDao;

	/** �O���T�C�g�V�X�e�� �A�N�Z�X�\�t�q�k�}�X�^DAO */
	@Autowired
	private TbMRoleUrlDao roleUrlDao;

	/**
	 * �����������s���܂��B
	 *
	 * @param model �Ǘ��Ώۈꗗ���f��
	 */
	public TB010ManagedTargetListModel getInitInfo(TB010ManagedTargetListModel model) {

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// ���[�U�[�}�X�^������擾
		TbMUser user = userDao.selectByLoginId(userContext.getLoginId());
		if(user == null){
			//���[�U�[�}�X�^���擾�ł��Ȃ��ꍇ�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0002"));
		}

		// �O���[�v�ڋq�}�X�^������擾
		model.setGrpKokyakuList(new ArrayList<TbMGrpKokyaku>());
		List<TbMGrpKokyaku> grpKokyakuList = grpKokyakuDao.selectBy(user.getSeqNo(), null);
		if(grpKokyakuList == null || grpKokyakuList.size() == 0){
			//�O���[�v�ڋq�}�X�^���擾�ł��Ȃ��ꍇ�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0002"));
		}

		for (TbMGrpKokyaku grpKokyaku : grpKokyakuList) {
			grpKokyaku.setKanjiKokyakuNm(kokyakuDao.selectByPrimaryKey(grpKokyaku.getRefKokyakuId()).getKanjiNm());
			model.getGrpKokyakuList().add(grpKokyaku);
		}
		return model;
	}

	/**
	 * �ڋq�I���������s���܂��B
	 *
	 * @param model �Ǘ��Ώۈꗗ���f��
	 */
	public TB010ManagedTargetListModel selectKokyakuId(TB010ManagedTargetListModel model) {

		// �Q�ƌڋq�}�X�^������擾
		model.setRefKokyakuList(refKokyakuDao.selectBy(model.getSelectedKokyakuId(), null));

		// �A�N�Z�X�\�t�q�k�}�X�^����A�N�Z�X�\�t�q�kMap�擾
		Map<String, TbMRoleUrl> accessibleMap =
			roleUrlDao.selectAccessibleMap(TLCSSB2BUserContext.ROLE_REAL_ESTATE);

		// �ڋq�}�X�^����ڋq���擾
		RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(model.getSelectedKokyakuId());
		if (kokyaku == null) {
			// �擾�ł��Ȃ������ꍇ�́A�V�X�e���G���[
			throw new ApplicationException("�ڋq�}�X�^��񂪎擾�ł��܂���B �ڋq�h�c=" + model.getSelectedKokyakuId());
		}
		
		model.setAccessibleMap(accessibleMap);
		model.setKokyakuInfo(kokyaku);

		return model;
	}
}
