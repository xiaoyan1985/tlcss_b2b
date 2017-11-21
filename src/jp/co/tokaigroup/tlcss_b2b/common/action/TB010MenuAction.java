package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB010MenuModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB010MenuService;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ���j���[�A�N�V�����N���X�B
 *
 * @author k002849
 * @version 4.0 2014/06/05
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb010_menu.jsp"),
	@Result(name=INPUT, location="tb010_menu.jsp")
})
public class TB010MenuAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB010MenuModel> {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB010MenuModel model = new TB010MenuModel();

	/** �T�[�r�X */
	@Autowired
	private TB010MenuService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("menuInit")
	public String init() throws Exception {
		// �����\������
		model = service.getInitInfo(model);

		// ���[�U�[�G�[�W�F���g�ݒ�
		model.setUserAgent(ServletActionContext.getRequest().getHeader("user-agent"));

		return SUCCESS;
	}

	/**
	 * �ڋq�I�����s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="menuChooseId",
			results = {@Result(name=SUCCESS, type="redirectAction",
					location="menuInit")}
	)
	public String chooseId() throws Exception {

		// ���O�C�����[�U���̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �ڋq�}�X�^���擾
		RcpMKokyaku kokyaku = service.getKokyakuInfo(model.getSelectedKokyakuId());
		
		// �I�������ڋqID���Z�b�V�����ɓ����
		userContext.setKokyakuId(model.getSelectedKokyakuId());
		userContext.setKokyakuIdSelected(true);
		userContext.setKokyakuName(kokyaku.getKanjiNm());

		return SUCCESS;
	}

	/**
	 * �ڋq�I����ʂɖ߂�܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="menuBack",
			results = {@Result(name=SUCCESS, type="redirectAction",
					location="menuInit")}
	)
	public String back() throws Exception {

		// ���O�C�����[�U���̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �ڋq�}�X�^���擾
		RcpMKokyaku kokyaku = service.getKokyakuInfo(userContext.getRefKokyakuId());
		
		// �ڋq�h�c�I���𖢑I���ɖ߂��B
		userContext.setKokyakuId(userContext.getRefKokyakuId());
		userContext.setKokyakuIdSelected(false);
		userContext.setKokyakuName(kokyaku.getKanjiNm());

		return SUCCESS;
	}

	/**
	 * �Ǘ��Ώۈꗗ��ʂɖ߂�܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="menuBackForAdmin",
			results = {@Result(name=SUCCESS, type="redirectAction",
					location="managedTargetListInit")}
	)
	public String backForAdmin() throws Exception {

		// ���O�C�����[�U���̎擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// �A�N�Z�X�\�t�q�kMap�擾
		userContext.setAccessibleMap(service.getAccessUrl(userContext.getLoginRole()));

		// �ڋq�h�c�I���𖢑I���ɖ߂��B
		userContext.setKokyakuIdSelected(false);
		userContext.setRole(userContext.getLoginRole());
		userContext.setRefKokyakuId(null);
		userContext.setKokyakuId(null);
		userContext.setKokyakuName(null);

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB010MenuModel getModel() {
		return model;
	}
}
