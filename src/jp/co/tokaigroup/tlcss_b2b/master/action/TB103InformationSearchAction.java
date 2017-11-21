package jp.co.tokaigroup.tlcss_b2b.master.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB103InformationSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB103InformationSearchModel;
import jp.co.tokaigroup.tlcss_b2b.master.service.TB103InformationSearchService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ���m�点�����A�N�V�����N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/18
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb103_information_search.jsp"),
	@Result(name=INPUT, location="tb103_information_search.jsp")
})
public class TB103InformationSearchAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB103InformationSearchModel> , ServiceValidatable{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB103InformationSearchModel model = new TB103InformationSearchModel();

	/** �T�[�r�X */
	@Autowired
	private TB103InformationSearchService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("informationSearchInit")
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
	@Action(value="informationSearch",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb103_information_search.jsp")
			}
	)
	public String search() throws Exception {
		try {
			// ��������
			searchCommon();

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
	@Action(value="informationSearchByPager",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb103_information_search.jsp")
			}
	)
	public String searchByPager() throws Exception {
		try {
			TB103InformationSearchCondition condition = model.getCondition();

			// �y�[�W���O�ݒ�̃Z�b�g
			condition.setOffset(condition.getOffset());
			condition.setDisplayToMax(true);
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
				addActionMessage("MSG0006");
			}

		} catch (PagerMaxCountException e) {
			// ��������̏ꍇ
			addActionMessage("MSG0005", String.valueOf(model.getCondition().getMaxCount()));
		}

		return SUCCESS;
	}

	/**
	 * �폜�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="informationDelete",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, location="tb103_information_search.jsp")
			}
	)
	public String delete() throws Exception {
		try {
			// �폜�������s
			service.delete(model);

			// ����ɍX�V�����������ꍇ�A���b�Z�[�W���쐬
			addActionMessage("MSG0001", "���m�点�̍폜");

			// ��������
			searchCommon();

		} catch (PagerMaxCountException e) {
			// ��������̏ꍇ
			addActionMessage("MSG0006", String.valueOf(model.getCondition().getMaxCount()));
		}
		return SUCCESS;
	}

	/**
	 * �����������s���܂��B�i�����A�폜�Ŏg�p�j
	 *
	 * @throws Exception
	 */
	private void searchCommon() throws Exception {
		TB103InformationSearchCondition condition = model.getCondition();

		// ���������ݒ�
		// �V�X�e���}�X�^�萔Map����ő匟���\�����A�ő�\���������擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �ő匟���\�����擾
		int searchMax = userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_INFORMATION_SEARCH_MAX_COUNT);
		// �ő�\�������擾
		int displayMax = userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_INFORMATION_SEARCH_MAX_LIMIT_PER_PAGE);

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

		//�o�^����J�ڎ��A�\��������z��Ɋi�[������
		if(condition.getHyojiJoken() != null){
			if(condition.getHyojiJoken().length == 1) {
				condition.setHyojiJoken(condition.getHyojiJoken()[0].split(", "));
			}
		}
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
			addActionMessage("MSG0006");
		}

	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB103InformationSearchModel getModel() {
		return model;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		service.getInitInfo(model);
	}
}

