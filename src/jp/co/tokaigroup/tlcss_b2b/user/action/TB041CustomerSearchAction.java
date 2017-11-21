package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import static com.opensymphony.xwork2.Action.INPUT;

import java.util.ArrayList;
import java.util.List;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB041CustomerSearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB041CustomerSearchService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �����E�����Ҍ����A�N�V�����N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/03
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb041_customer_search.jsp"),
	@Result(name=INPUT, location="tb041_customer_search.jsp")
})
public class TB041CustomerSearchAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB041CustomerSearchModel> , ServiceValidatable{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB041CustomerSearchModel model = new TB041CustomerSearchModel();

	/** �T�[�r�X */
	@Autowired
	private TB041CustomerSearchService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("customerSearchInit")
	public String init() throws Exception {

		// �������擾����
		model = service.getInitInfo(model);


		return SUCCESS;
	}

	/**
	 * �����������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="customerSearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb041_customer_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			RC011KokyakuSearchCondition condition = model.getCondition();

			// ���������ݒ�
			// �V�X�e���}�X�^�萔Map����ő匟���\�����A�ő�\���������擾
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// �ő匟���\�����擾
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_MAX_COUNT);
			// �ő�\�������擾
			int displayMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_CUSTOMER_MAX_LIMIT_PER_PAGE);

			// �y�[�W���O�ݒ�̃Z�b�g
			if ((condition.getOffset() == 0) ||
					(condition.getOffset() > 0 && condition.getLimit() != -1)) {
				// ���񌟍��i�I�t�Z�b�g���O�j�A
				// �Q��ڈȍ~�̌����i�I�t�Z�b�g���P�ȏ�A�ő�\��������-1�łȂ��j�́A
				// �I�t�Z�b�g��1�ɃZ�b�g
				condition.setOffset(1);
			}
			condition.setLimit(displayMax);
			condition.setMaxCount(searchMax);
			condition.setDisplayToMax(true);
			// ���������̐ݒ�
			model.setCondition(setCondition(condition));

			// �����������s
			model = service.search(model);

			// �������ʂ̔���
			if (model.getResultList() == null || model.getResultList().isEmpty()) {
				// �������ʂ�0���̏ꍇ
				addActionMessage("MSG0002");
			} else if (!condition.isCompleted()) {
				// ���������t���O���ufalse�v�i�Y�������I�[�o�[�j�̏ꍇ
				addActionMessage("MSG0039", String.valueOf(model.getCondition().getActualCount()));
			}

		} catch (PagerMaxCountException e) {
			// ��������̏ꍇ
			addActionMessage("MSG0005", String.valueOf(model.getCondition().getMaxCount()));
		}

		return SUCCESS;
	}

	/**
	 * �y�[�W�����N�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="customerSearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb041_customer_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			RC011KokyakuSearchCondition condition = model.getCondition();

			// �y�[�W���O�ݒ�̃Z�b�g
			condition.setOffset(condition.getOffset());
			condition.setDisplayToMax(true);
			// ���������̐ݒ�
			model.setCondition(setCondition(condition));

			// �����������s
			model = service.search(model);

			// �������ʂ̔���
			if (model.getResultList() == null || model.getResultList().isEmpty()) {
				// �������ʂ�0���̏ꍇ
				addActionMessage("MSG0002");
			} else if (!condition.isCompleted()) {
				// ���������t���O���ufalse�v�i�Y�������I�[�o�[�j�̏ꍇ
				addActionMessage("MSG0039", String.valueOf(model.getCondition().getActualCount()));
			}

		} catch (PagerMaxCountException e) {
			// ��������̏ꍇ
			addActionMessage("MSG0005", String.valueOf(model.getCondition().getMaxCount()));
		}

		return SUCCESS;
	}


	/**
	 * ���������̐ݒ���s���܂��B�i���ʏ����j
	 *
	 * @return
	 * @throws Exception ���s����O�����������ꍇ
	 */
	private RC011KokyakuSearchCondition setCondition(RC011KokyakuSearchCondition cond) throws Exception {
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �@������ڋqID,�֘A�t���`�F�b�N,�e�ڋqID
		// �Z�b�V�����̌������Ǘ���Ђ̏ꍇ
		if (userContext.isRealEstate()) {
			// �ڋq�I���ς̏ꍇ
			if (userContext.isKokyakuIdSelected()) {
				cond.setSeikyusakiKokyakuId(userContext.getKokyakuId());
				cond.setParentKokyakuId(null);
			} else {
				cond.setSeikyusakiKokyakuId(null);
				cond.setParentKokyakuId(userContext.getKokyakuId());
			}
		} else {
			cond.setSeikyusakiKokyakuId(null);
			cond.setParentKokyakuId(null);
		}
		cond.setKanrenJoho(RC011KokyakuSearchCondition.KANREN_JOHO_ON);

		// �A�ڋq�敪(�u3:�����v,�u4:�����ҁE�l�v)
		List<String> kokyakuKbnList = new ArrayList<String>();
		kokyakuKbnList.add(RcpMKokyaku.KOKYAKU_KBN_BUKKEN);
		kokyakuKbnList.add(RcpMKokyaku.KOKYAKU_KBN_NYUKYOSHA);
		// ���O�C�����[�U�������u�Ǘ���Ёv�ȊO�̏ꍇ
		if (!userContext.isRealEstate()) {
			kokyakuKbnList.add(RcpMKokyaku.KOKYAKU_KBN_FUDOSAN);
		}
		cond.setKokyakuKbnList(kokyakuKbnList);
		
		// ���O�C�����[�U�������u�ϑ����SV�v�������́u�ϑ����OP�v�̏ꍇ
		if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
			// ���ID��ݒ�
			cond.setKaishaId(userContext.getKaishaId());
		}

		return cond;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB041CustomerSearchModel getModel() {
		return model;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		model = service.getInitInfo(model);
	}
}

