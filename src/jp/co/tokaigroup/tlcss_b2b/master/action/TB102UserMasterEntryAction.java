package jp.co.tokaigroup.tlcss_b2b.master.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.message.MessageBean;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB102UserMasterEntryModel;
import jp.co.tokaigroup.tlcss_b2b.master.service.TB102UserMasterEntryService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * ���[�U�[�}�X�^�o�^�A�N�V�����N���X�B
 *
 * @author k002849
 * @version 1.1 2014/06/18
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb102_user_master_entry.jsp"),
	@Result(name=INPUT, location="tb102_user_master_entry.jsp")
})
public class TB102UserMasterEntryAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB102UserMasterEntryModel>, ServiceValidatable {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB102UserMasterEntryModel model = new TB102UserMasterEntryModel();

	/** �T�[�r�X */
	@Autowired
	private TB102UserMasterEntryService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="userMasterEntryInit", params={"actionType", Constants.ACTION_TYPE_INSERT})
	public String init() throws Exception {
		// �����\������
		model = service.getInitInfo(model);

		// ���[�����M�t���O�̏����l��ݒ�
		model.setSendMailFlg(TB102UserMasterEntryModel.SEND_MAIL_FLG_ON);

		return SUCCESS;
	}

	/**
	 * �X�V�����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="userMasterEntryUpdateInit", params={"actionType", Constants.ACTION_TYPE_UPDATE})
	public String updateInit() throws Exception {
		// �X�V��̌�����ʗp�p�����[�^��ݒ�
		// �����{��̍��ڂ� Action�N���X�̃��_�C���N�g���ɐݒ�o���Ȃ��ׁA�����Ŏ��{
		if (StringUtils.isNotBlank(model.getEncodedUserNm())) {
			model.getCondition().setUserNm(model.getEncodedUserNm());
		}

		// �X�V�����\������
		model = service.getUserInfo(model);

		if (model.isDuplicateContentExists()) {
			// �d�����ڂ�����ꍇ�́A���b�Z�[�W�𐶐�
			StringBuilder message = new StringBuilder("");
			for (MessageBean messageBean : model.getDuplicateContetErrorMessageList()) {
				if (StringUtils.isNotBlank(message.toString())) {
					// �Q�ȏチ�b�Z�[�W������ꍇ�́A���s���o��
					message.append(System.getProperty("line.separator"));
				}

				message.append(getText(messageBean.getMessageId(), messageBean.getParams()));
			}

			model.setDucplicateContentErrorMessage(message.toString());
		}

		// ���[�����M�t���O�̏����l��ݒ�
		model.setSendMailFlg(TB102UserMasterEntryModel.SEND_MAIL_FLG_ON);

		return SUCCESS;
	}

	/**
	 * �X�V�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="userMasterEntryUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="userMasterEntryUpdateInit?" +
									"seqNo=${seqNo}" +
									"&condition.offset=${condition.offset}" +
									"&condition.loginId=${condition.loginId}" +
									"&condition.role=${condition.role}" +
									"&encodedUserNm=${encodedUserNm}")
			}
	)
	public String update() throws Exception {
		if (model.isInsert()) {
			// �폜�t���O�ɖ��폜���Z�b�g
			model.getUser().setDelFlg(TbMUser.DEL_FLG_NOT_DELETE);
			// �o�^����
			service.insertUserInfo(model);

			// �������b�Z�[�W
			addActionMessage("MSG0001", "���[�U�[���̓o�^");
		} else if (model.isUpdate()) {
			// �X�V����
			service.updateUserInfo(model);

			// �������b�Z�[�W
			addActionMessage("MSG0001", "���[�U�[���̍X�V");
		} else {
			// �A�N�V�����^�C�v���z��O�̏ꍇ�́A���ʋƖ��G���[
			throw new ApplicationException("�A�N�V�����^�C�v�s���F�p�����[�^�̃A�N�V�����^�C�v");
		}

		// �X�V�����\�������p�p�����[�^�ݒ�
		model.setEncodedUserNm(model.encode(model.getCondition().getUserNm()));

		return SUCCESS;
	}

	/**
	 * �p�X���[�h�Ĕ��s�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="userMasterEntryReissue",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="userMasterEntryUpdateInit?" +
									"seqNo=${seqNo}" +
									"&condition.offset=${condition.offset}" +
									"&condition.loginId=${condition.loginId}" +
									"&condition.role=${condition.role}" +
									"&encodedUserNm=${encodedUserNm}")
			}
	)
	public String reissue() throws Exception {
		// �p�X���[�h�Ĕ��s����
		service.reissuePasswordInfo(model);

		// �������b�Z�[�W
		addActionMessage("MSG0001", "�p�X���[�h�̍Ĕ��s");

		// �X�V�����\�������p�p�����[�^�ݒ�
		model.setEncodedUserNm(model.encode(model.getCondition().getUserNm()));

		return SUCCESS;
	}

	/**
	 * �Q�ƌڋq�ݒ���s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("userMasterSetRefKokyaku")
	public String setRefKokyaku() throws Exception {
		// �����\������
		model = service.setRefKokyaku(model);

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB102UserMasterEntryModel getModel() {
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
