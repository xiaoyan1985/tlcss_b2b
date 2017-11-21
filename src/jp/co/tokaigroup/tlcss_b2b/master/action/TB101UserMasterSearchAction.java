package jp.co.tokaigroup.tlcss_b2b.master.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB101UserMasterSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB101UserMasterSearchModel;
import jp.co.tokaigroup.tlcss_b2b.master.service.TB101UserMasterSearchService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ���[�U�[�}�X�^�����A�N�V�����N���X�B
 *
 * @author k002849
 * @version 1.0 2014/05/27
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb101_user_master_search.jsp"),
	@Result(name=INPUT, location="tb101_user_master_search.jsp")
})
public class TB101UserMasterSearchAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB101UserMasterSearchModel> {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB101UserMasterSearchModel model = new TB101UserMasterSearchModel();

	/** �T�[�r�X */
	@Autowired
	private TB101UserMasterSearchService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("userMasterSearchInit")
	public String init() throws Exception {
		// �����\������
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * �����������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="userMasterSearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb101_user_master_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			TB101UserMasterSearchCondition condition = model.getCondition();

			// �V�X�e���}�X�^�萔Map����ő匟���\�����A�ő�\���������擾
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// �ő匟���\�����擾
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_USER_MASTER_MAX_COUNT);
			// �ő�\�������擾
			int displayMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_USER_MASTER_MAX_LIMIT_PER_PAGE);

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

			model.setCondition(condition);

			// �����������s
			model = service.executeSearch(model);

			// �������ʂ̔���
			if (model.getResultList() == null || model.getResultList().isEmpty()) {
				// �������ʂ�0���̏ꍇ
				addActionMessage("MSG0002");
			} else if (!condition.isCompleted()) {
				// ���������t���O���ufalse�v�i�Y�������I�[�o�[�j�̏ꍇ
				addActionMessage("MSG0006");
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
	@Action(
			value="userMasterSearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb101_user_master_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			// �����������s
			model = service.executeSearch(model);

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
	public TB101UserMasterSearchModel getModel() {
		return model;
	}
}
