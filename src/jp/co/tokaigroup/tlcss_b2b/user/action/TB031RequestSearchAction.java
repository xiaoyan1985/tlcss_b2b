package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTIraiDaoImpl;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB031RequestSearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB031RequestSearchService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �˗������A�N�V�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/12
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb031_request_search.jsp"),
	@Result(name=INPUT, location="tb031_request_search.jsp")
})
public class TB031RequestSearchAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB031RequestSearchModel> {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB031RequestSearchModel model = new TB031RequestSearchModel();

	/** �T�[�r�X */
	@Autowired
	private TB031RequestSearchService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestSearchInit")
	public String init() throws Exception {
		// �����\������
		model = service.getInitInfo(model);

		// �����l�Z�b�g�i���Z�v�V�����̈˗������Ƃ͈Ⴂ�A�����ʂ̂��߁AJSP�ł̓Z�b�g���Â炢���߁A�����ŃZ�b�g�j
		RC041IraiSearchCondition condition = model.getCondition();
		condition.setIraiKanryo(RcpTIraiDaoImpl.SEARCH_IRAI_KANRYO_RDO_ALL);	// ��Ɗ���
		condition.setShimeSts(RcpTIraiDaoImpl.SEARCH_SHIME_RDO_MAE);			// ���ߔN��
		condition.setJokyo(RC041IraiSearchCondition.JOKYO_RDO_ALL);	// �󋵃��W�I

		model.setCondition(condition);

		return SUCCESS;
	}

	/**
	 * �����������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="requestSearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb031_request_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			RC041IraiSearchCondition condition = model.getCondition();

			// �V�X�e���}�X�^�萔Map����ő匟���\�����A�ő�\���������擾
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// �ő匟���\�����擾
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_MAX_COUNT);
			// �ő�\�������擾
			int displayMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_MAX_LIMIT_PER_PAGE);

			if ((condition.getOffset() == 0) ||
				(condition.getOffset() > 0 && condition.getLimit() != -1)) {
				// ���񌟍��i�I�t�Z�b�g���O�j�A
				// �Q��ڈȍ~�̌����i�I�t�Z�b�g���P�ȏ�A�ő�\��������-1�łȂ��j�́A
				// �I�t�Z�b�g��1�ɃZ�b�g
				condition.setOffset(1);
			}
			condition.setDisplayToMax(true);
			condition.setLimit(displayMax);
			condition.setMaxCount(searchMax);

			// ���������ݒ�
			if (userContext.isRealEstate()) {
				// �Z�b�V�����̌������Ǘ���Ђ̏ꍇ
				if (userContext.isKokyakuIdSelected()) {
					// �ڋq�I���ς̏ꍇ
					// ������ڋq�h�c�ɂ́A�Z�b�V�����̌ڋq�h�c��ݒ�
					condition.setSeikyusakiKokyakuId(userContext.getKokyakuId());
					// �e�ڋq�h�c�ɂ́ANULL��ݒ�
					condition.setParentKokyakuId(null);
				} else {
					// ����ȊO�̏ꍇ�́ANULL��ݒ�
					condition.setSeikyusakiKokyakuId(null);
					// �e�ڋq�h�c�ɂ́A�Z�b�V�����̌ڋq�h�c��ݒ�
					condition.setParentKokyakuId(userContext.getKokyakuId());
				}
				condition.setIraiKokaiFlg(RcpTIrai.KOKAI_FLG_KOUKAIZUMI);
				condition.setSearchFlg(RC041IraiSearchCondition.SEARCH_FLG_GAIBU);
			} else {
				// �Z�b�V�����̌������Ǘ���ЈȊO�̏ꍇ�́A�����ݒ肵�Ȃ�
				condition.setSeikyusakiKokyakuId(null);
				condition.setParentKokyakuId(null);
				condition.setSearchFlg(null);
			}
			// ��Ђh�c��ݒ�
			if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
				condition.setOutsourcerKaishaId(userContext.getKaishaId());
			} else {
				condition.setOutsourcerKaishaId(null);
			}
			condition.setIraiGyosha(userContext.getGyoshaCd());
			condition.setKanrenJoho(RcpTIraiDaoImpl.SEARCH_KANREN_JOHO_TRUE);

			model.setCondition(condition);

			// �����������s
			model = service.executeSearch(model);

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
	@Action(value="requestSearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb031_request_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			RC041IraiSearchCondition condition = model.getCondition();
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// �Z�b�V�����̌������Ǘ���ЁA���A�ڋq�I���ς̏ꍇ
			if (userContext.isRealEstate()) {
				// �Z�b�V�����̌������Ǘ���Ђ̏ꍇ
				if (userContext.isKokyakuIdSelected()) {
					// �ڋq�I���ς̏ꍇ
					// ������ڋq�h�c�ɂ́A�Z�b�V�����̌ڋq�h�c��ݒ�
					condition.setSeikyusakiKokyakuId(userContext.getKokyakuId());
					// �e�ڋq�h�c�ɂ́ANULL��ݒ�
					condition.setParentKokyakuId(null);
				} else {
					// ����ȊO�̏ꍇ�́ANULL��ݒ�
					condition.setSeikyusakiKokyakuId(null);
					// �e�ڋq�h�c�ɂ́A�Z�b�V�����̌ڋq�h�c��ݒ�
					condition.setParentKokyakuId(userContext.getKokyakuId());
				}
				condition.setIraiKokaiFlg(RcpTIrai.KOKAI_FLG_KOUKAIZUMI);
				condition.setSearchFlg(RC041IraiSearchCondition.SEARCH_FLG_GAIBU);
			} else {
				// �Z�b�V�����̌������Ǘ���ЈȊO�̏ꍇ�́A�����ݒ肵�Ȃ�
				condition.setSeikyusakiKokyakuId(null);
				condition.setParentKokyakuId(null);
				condition.setSearchFlg(null);
			}
			// ��Ђh�c��ݒ�
			if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
				condition.setOutsourcerKaishaId(userContext.getKaishaId());
			} else {
				condition.setOutsourcerKaishaId(null);
			}
			condition.setIraiGyosha(userContext.getGyoshaCd());
			condition.setKanrenJoho(RcpTIraiDaoImpl.SEARCH_KANREN_JOHO_TRUE);


			model.setCondition(condition);

			// �����������s
			model = service.executeSearch(model);

			if (!condition.isCompleted()) {
				// ���������t���O���ufalse�v�i�Y�������I�[�o�[�j�̏ꍇ
				addActionMessage("MSG0039", String.valueOf(model.getCondition().getActualCount()));
			}
		} catch (PagerMaxCountException e) {
			// ��������̏ꍇ
			addActionMessage("MSG0005", String.valueOf(model.getCondition().getMaxCount()));
		}

		// �������ʂ̔���
		if (model.getResultList() == null || model.getResultList().isEmpty()) {
			// �������ʂ�0���̏ꍇ
			addActionMessage("MSG0002");
		}

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB031RequestSearchModel getModel() {
		return model;
	}
}
