package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.gyosha.model.RC061GyoshaSearchCondition;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB051CenterContractorSearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB051CenterContractorSearchService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �Z���^�[�ƎҌ����A�N�V�����N���X�B
 *
 * @author v145527
 * @version 1.0 2015/09/29
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb051_center_contractor_search.jsp"),
})
public class TB051CenterContractorSearchAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB051CenterContractorSearchModel> {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB051CenterContractorSearchModel model = new TB051CenterContractorSearchModel();

	/** �T�[�r�X */
	@Autowired
	private TB051CenterContractorSearchService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("centerContractorSearchInit")
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
	@Action(value="centerContractorSearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb051_center_contractor_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			RC061GyoshaSearchCondition condition = model.getCondition();

			// ���������ݒ�
			
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
			// �V�X�e���}�X�^�萔Map����ő匟���\�����A�ő�\���������擾
			// �ő匟���\�����擾
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_CONTRACTOR_SEARCH_MAX_COUNT);
			// �ő�\�������擾
			int displayMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_CONTRACTOR_SEARCH_MAX_LIMIT_PER_PAGE);

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
			
			// ��Ђh�c�ɂ́A�Z�b�V�����̉�Ђh�c��ݒ�
			condition.setKaishaId(userContext.getKaishaId());
			
			// ���������̐ݒ�
			model.setCondition(condition);

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
	 * �y�[�W�����N�̌��������s���܂��B
	 *
	 * @return �˗�������ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="centerContractorSearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb051_center_contractor_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			// �����������擾
			RC061GyoshaSearchCondition condition = model.getCondition();
			// �y�[�W���O�ݒ�̃Z�b�g
			condition.setOffset(condition.getOffset());
			condition.setDisplayToMax(true);
			
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
			
			// ��Ђh�c�ɂ́A�Z�b�V�����̉�Ђh�c��ݒ�
			condition.setKaishaId(userContext.getKaishaId());
			
			// ���������̐ݒ�
			model.setCondition(condition);
			
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
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB051CenterContractorSearchModel getModel() {
		return model;
	}

}
