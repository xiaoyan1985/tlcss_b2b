package jp.co.tokaigroup.tlcss_b2b.user.logic;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFBukkenDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFBukken;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.tags.Functions;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.logic.OutsourcerValidationLogic;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * �ڋq��{���ڍ׃��W�b�N�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/05/29
 * @version 1.1 2015/11/04 J.Matsuba �a���ϊ�Map�̏C���A�ڋq�t���������擾�����̒ǉ�
 * @version 1.2 2016/06/17 S.Nakano ������ڋq��񕡐����Ή�
 * @version 1.3 2016/07/19 J.Matsuba ������ڋq��񃊃X�g�\���Ή�
 */
@Service
public class TB040CustomerCommonInfoLogicImpl extends TLCSSB2BBaseService
		implements TB040CustomerCommonInfoLogic {

	/** ���Z�v�V�����ڋq�e�[�u��DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** ���Z�v�V�����ڋq�t���������}�X�^DAO */
	@Autowired
	private RcpMKokyakuFBukkenDao kokyakuFBukkenDao;

	/** �ϑ���Њ֘A�`�F�b�N���W�b�N */
	@Autowired
	private OutsourcerValidationLogic outsourcerValidationLogic;
	
	/**
	 * �ڋq��{���̎擾���s���܂��B
	 *
	 * @param kokyakuId �ڋqID
	 * @return �ڋq�}�X�^
	 */
	public RcpMKokyaku getKokyakuInfo(String kokyakuId) throws ValidationException {
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		// �Z�L�����e�B�`�F�b�N
		if (userContext.isOutsourcerOp() || userContext.isOutsourcerSv()) {
			// ����-�ϑ���Њ֘A�`�F�b�N�̌��ʂ�NG�̏ꍇ�͗�O
			if (!outsourcerValidationLogic.isValid(userContext.getKaishaId(), kokyakuId)) {
				throw new ForbiddenException("�ϑ���Ɋ֘A���Ȃ��ڋq�h�c�ł��B"); 
			}
		}
		
		//�ڋq��{���擾
		RcpMKokyaku kokyakuEntity = null;
		if(StringUtils.isNotBlank(kokyakuId)){
			kokyakuEntity = kokyakuDao.selectByPrimaryKey(kokyakuId);
		}
		if(kokyakuEntity == null){
			// �ڋq��񂪎擾�ł��Ȃ��ꍇ�G���[
			throw new ValidationException(new ValidationPack().addError("MSG0015", "�Y���ڋqID"));
		}

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN,
				RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND,
				RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM,
				RcpMComCd.RCP_M_COM_CD_SERVICE_SHUBETSU);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// �ڋq�敪Map
		Map<String, RcpMComCd> kokyakuKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KBN);
		// �ڋq���Map
		Map<String, RcpMComCd> kokyakuShubetsuMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_KOKYAKU_KIND);
		// �Ǘ��`��Map
		Map<String, RcpMComCd> kanriKeitaiMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_MGR_FORM);

		// �a���ϊ�����
		if (StringUtils.isNotBlank(kokyakuEntity.getKokyakuKbn()) &&
				kokyakuKbnMap.containsKey(kokyakuEntity.getKokyakuKbn())) {
			// �ڋq�敪��
			kokyakuEntity.setKokyakuKbnNm(kokyakuKbnMap.get(kokyakuEntity.getKokyakuKbn()).getExternalSiteVal());
		}
		if (StringUtils.isNotBlank(kokyakuEntity.getKokyakuShubetsu()) &&
				kokyakuShubetsuMap.containsKey(kokyakuEntity.getKokyakuShubetsu())) {
			// �ڋq���
			kokyakuEntity.setKokyakuShubetsuNm(kokyakuShubetsuMap.get(kokyakuEntity.getKokyakuShubetsu()).getExternalSiteVal());
		}

		kokyakuEntity.setYubinNo(Functions.formatYubinNo(kokyakuEntity.getYubinNo()));

		// ������ڋq���擾
		List<RcpMKokyaku> seikyusakiKokyakuList = kokyakuDao.selectSeikyusakiKokyakuList(kokyakuId);

		kokyakuEntity.setSeikyusakiKokyakuList(seikyusakiKokyakuList);

		// ���ӎ����̕\�����ԃ`�F�b�N
		setAttention(kokyakuEntity);

		// �ڋq�t���������擾
		if (kokyakuEntity.isKokyakuKbnBukken()) {

			RcpMKokyakuFBukken kokyakuFBukkenEntity = kokyakuFBukkenDao.selectByPrimaryKey(kokyakuId);

			// �a���ϊ�����
			if (kokyakuFBukkenEntity != null
					&& StringUtils.isNotBlank(kokyakuFBukkenEntity.getKanriKeitaiKbn())
					&& kanriKeitaiMap.containsKey(kokyakuFBukkenEntity.getKanriKeitaiKbn())) {
				// �Ǘ��`��
				kokyakuEntity.setKanriKeitaiKbnNm(kanriKeitaiMap.get(kokyakuFBukkenEntity.getKanriKeitaiKbn()).getExternalSiteVal());
			}
		}

		return kokyakuEntity;
	}

	/**
	 * ���ӎ����̃Z�b�g�������s���܂��B
	 *
	 * @param kokyakuEntity �ڋq�}�X�^Entity
	 */
	private void setAttention(RcpMKokyaku kokyakuEntity) {

		// ����p�V�X�e�����t�擾
		Timestamp sysdate = DateUtil.toTimestamp(DateUtil.getSysDateString(), "yyyy/MM/dd");

		// �ڋq��{���.���ӎ����S
		if (!judgesHyojiKikan(sysdate
				, kokyakuEntity.getAttention4StartDt()
				, kokyakuEntity.getAttention4EndDt())) {
			// �\�����ԊO�̂��߁Anull���Z�b�g
			kokyakuEntity.setAttention4(null);
		}

		// �ڋq��{���.���ӎ����T
		if (!judgesHyojiKikan(sysdate
				, kokyakuEntity.getAttention5StartDt()
				, kokyakuEntity.getAttention5EndDt())) {
			// �\�����ԊO�̂��߁Anull���Z�b�g
			kokyakuEntity.setAttention5(null);
		}

		// �ڋq��{���.���ӎ����U
		if (!judgesHyojiKikan(sysdate
				, kokyakuEntity.getAttention6StartDt()
				, kokyakuEntity.getAttention6EndDt())) {
			// �\�����ԊO�̂��߁Anull���Z�b�g
			kokyakuEntity.setAttention6(null);
		}

		if (kokyakuEntity.getSeikyusakiKokyakuList() != null && !kokyakuEntity.getSeikyusakiKokyakuList().isEmpty()) {
			// ������ڋq��񕪈ȉ��������s��
			for (RcpMKokyaku seikyusakiKokyaku : kokyakuEntity.getSeikyusakiKokyakuList()) {

				// ������ڋq���.���ӎ����S
				if (!judgesHyojiKikan(sysdate
						, seikyusakiKokyaku.getAttention4StartDt()
						, seikyusakiKokyaku.getAttention4EndDt())) {
					// �\�����ԊO�̂��߁Anull���Z�b�g
					seikyusakiKokyaku.setAttention4(null);
				}

				// ������ڋq���.���ӎ����T
				if (!judgesHyojiKikan(sysdate
						, seikyusakiKokyaku.getAttention5StartDt()
						, seikyusakiKokyaku.getAttention5EndDt())) {
					// �\�����ԊO�̂��߁Anull���Z�b�g
					seikyusakiKokyaku.setAttention5(null);
				}
				// ������ڋq���.���ӎ����U
				if (!judgesHyojiKikan(sysdate
						, seikyusakiKokyaku.getAttention6StartDt()
						, seikyusakiKokyaku.getAttention6EndDt())) {
					// �\�����ԊO�̂��߁Anull���Z�b�g
					seikyusakiKokyaku.setAttention6(null);
				}
			}
		}
	}

	/**
	 * �\�����Ԃ̕\�����菈�����s���܂��B
	 *
	 * @param sysdate �V�X�e�����t
	 * @param startDt �\���J�n��
	 * @param endDt �\���I����
	 * @return true�F�\������
	 */
	private boolean judgesHyojiKikan(Timestamp sysdate, Timestamp startDt, Timestamp endDt) {

		if (startDt != null && endDt == null) {
			// �\���J�n����null�ȊO���\���I������null

			if (sysdate.compareTo(startDt) < 0) {
				return false;
			}
		} else if (startDt == null && endDt != null) {
			// �\���J�n����null���\���I������null�ȊO

			if (sysdate.compareTo(endDt) > 0) {
				return false;
			}
		} else if (startDt != null && endDt != null) {
			// �\���J�n����null�ȊO���\���I������null�ȊO

			if (sysdate.compareTo(startDt) < 0 || sysdate.compareTo(endDt) > 0) {
				return false;
			}
		}

		return true;
	}

}
