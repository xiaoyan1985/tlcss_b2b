package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDaoImpl;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB021InquirySearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB021InquirySearchService;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �₢���킹�����A�N�V�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/05/19
 * @version 2.0 2015/10/15 v145527 ���ǖ��ǒǉ�
 * @version 2.1 2015/12/24 H.Yamamura CSV�_�E�����[�h�t���O�̒ǉ�
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb021_inquiry_search.jsp"),
	@Result(name=INPUT, location="tb021_inquiry_search.jsp")
})
public class TB021InquirySearchAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB021InquirySearchModel> {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB021InquirySearchModel model = new TB021InquirySearchModel();

	/** �T�[�r�X */
	@Autowired
	private TB021InquirySearchService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("inquirySearchInit")
	public String init() throws Exception {

		// �����\������
		model = service.getInitInfo(model);
		// �����l�̐ݒ�i�󋵃��W�I�j
		model.getCondition().setJokyo(RC031ToiawaseSearchCondition.JOKYO_RDO_ALL);
		// �����l�̐ݒ�i�{���󋵃��W�I�j
		model.getCondition().setBrowseStatus(RC031ToiawaseSearchCondition.BROWSE_STATUS_KBN_ALL);
		// ���[�U�[�G�[�W�F���g�ݒ�
		model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

		return SUCCESS;
	}

	/**
	 * �����������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="inquirySearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb021_inquiry_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			// ���[�U�[�G�[�W�F���g�ݒ�
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			RC031ToiawaseSearchCondition condition = model.getCondition();

			// �V�X�e���}�X�^�萔Map����ő匟���\�����A�ő�\���������擾
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// �ő匟���\�����擾
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_TOIAWASE_MAX_COUNT);
			// �ő�\�������擾
			int displayMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_TOIAWASE_MAX_LIMIT_PER_PAGE);

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
				condition.setToiawaseKokaiFlg(RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI);
				condition.setSearchFlg(RC031ToiawaseSearchCondition.SEARCH_FLG_GAIBU);
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
			condition.setKanrenJoho(RcpTToiawaseDaoImpl.SEARCH_KANREN_JOHO_TRUE);
			

			//�˗��L���敪�ɋ�l(�S��)�������
			condition.setIraiUmuRdo(RcpTToiawaseDaoImpl.SEARCH_SORT_IRAI_UMU_RDO_ALL);

			model.setCondition(condition);

			// �����������s
			model = service.executeSearch(model, false);

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
	@Action(value="inquirySearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb021_inquiry_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			// ���[�U�[�G�[�W�F���g�ݒ�
			model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

			RC031ToiawaseSearchCondition condition = model.getCondition();
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
			
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
				condition.setToiawaseKokaiFlg(RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI);
				condition.setSearchFlg(RC031ToiawaseSearchCondition.SEARCH_FLG_GAIBU);
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
			condition.setKanrenJoho(RcpTToiawaseDaoImpl.SEARCH_KANREN_JOHO_TRUE);

			//�˗��L���敪�ɋ�l(�S��)�������
			condition.setIraiUmuRdo(RcpTToiawaseDaoImpl.SEARCH_SORT_IRAI_UMU_RDO_ALL);

			model.setCondition(condition);

			// �����������s
			model = service.executeSearch(model, false);
			
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
	public TB021InquirySearchModel getModel() {
		return model;
	}
}
