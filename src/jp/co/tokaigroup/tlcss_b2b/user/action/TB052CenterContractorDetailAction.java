package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB052CenterContractorDetailModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB052CenterContractorDetailService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �Z���^�[�Ǝҏڍ׃A�N�V�����N���X�B
 *
 * @author v145527
 * @version 1.0 2015/10/08
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb052_center_contractor_detail.jsp")
 })
public class TB052CenterContractorDetailAction extends TLCSSB2BBaseActionSupport
implements ModelDriven<TB052CenterContractorDetailModel>, ServiceValidatable {
	
	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB052CenterContractorDetailModel model = new TB052CenterContractorDetailModel();

	/** �T�[�r�X */
	@Autowired
	private TB052CenterContractorDetailService service;
	
	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("centerContractorDetailInit")
	public String init() throws Exception {
		
		try {
			// �����\���p�����[�^�`�F�b�N
			if (StringUtils.isBlank(model.getGyoshaCd())) {
				// �p�����[�^�̖₢���킹�m�n���擾�ł��Ȃ��ꍇ�G���[
				throw new ApplicationException("�Ǝ҃R�[�h�s���F�p�����[�^�̋Ǝ҃R�[�h" );
			}

			// �������擾����
			model = service.getInitInfo(model);

		} catch (ValidationException e) {
			// �����\���G���[�t���O��ݒ肵�Ajavascript�Ɠ��e��\�����Ȃ��悤�ɂ���
			model.setInitError(true);
			// ���b�Z�[�W�������邽�߁AException������o���čĐݒ�
			addActionError(e.getValidationPack().getActionErrors().get(0).getMessageId()
					, e.getValidationPack().getActionErrors().get(0).getParams());
		}
		
		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB052CenterContractorDetailModel getModel() {

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
