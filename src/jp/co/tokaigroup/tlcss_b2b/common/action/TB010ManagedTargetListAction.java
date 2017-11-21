package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010ManagedTargetListModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB010ManagedTargetListService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �Ǘ��Ώۈꗗ�A�N�V�����N���X�B
 *
 * @author v140546
 * @version 4.0 2014/09/25
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb010_managed_target_list.jsp"),
	@Result(name=INPUT, location="tb010_managed_target_list.jsp")
})
public class TB010ManagedTargetListAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB010ManagedTargetListModel> , ServiceValidatable{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB010ManagedTargetListModel model = new TB010ManagedTargetListModel();

	/** �T�[�r�X */
	@Autowired
	private TB010ManagedTargetListService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("managedTargetListInit")
	public String init() throws Exception {
		// �����\������
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * �ڋq�I�����s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="managedTargetListChooseId",
			results = {@Result(name=SUCCESS, type="redirectAction",
					location="menuInit")}
	)
	public String chooseId() throws Exception {

		// �ڋq�I���������s���܂��B
		model = service.selectKokyakuId(model);

		// ���O�C�����[�U���̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		userContext.setRefKokyakuId(model.getSelectedKokyakuId());
		userContext.setRole(TLCSSB2BUserContext.ROLE_REAL_ESTATE);
		userContext.setAccessibleMap(model.getAccessibleMap());
		userContext.setKokyakuId(model.getSelectedKokyakuId());
		userContext.setKokyakuName(model.getKokyakuInfo().getKanjiNm());
		
		if (model.getRefKokyakuList().size() == 1 && model.getRefKokyakuList().get(0).isLowerDisplay()) {
		// �擾�����ڋq�h�c��1�����A���ʑw�\���t���O���u1�F�\���v�̏ꍇ
			userContext.setKokyakuIdSelected(true);
			userContext.setSingleFlg(true);
		} else {
			userContext.setKokyakuIdSelected(false);
			userContext.setSingleFlg(false);
		}

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB010ManagedTargetListModel getModel() {
		return model;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
	}
}
