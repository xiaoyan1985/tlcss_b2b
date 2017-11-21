package jp.co.tokaigroup.tlcss_b2b.master.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB104InformationEntryModel;
import jp.co.tokaigroup.tlcss_b2b.master.service.TB104InformationEntryService;

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
 * ���m�点�o�^�A�N�V�����N���X�B
 *
 * @author v138130
 * @version 4.0 2014/06/20
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb104_information_entry.jsp"),
	@Result(name=INPUT, location="tb104_information_entry.jsp")
})
public class TB104InformationEntryAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB104InformationEntryModel>, ServiceValidatable {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB104InformationEntryModel model = new TB104InformationEntryModel();

	/** �T�[�r�X */
	@Autowired
	private TB104InformationEntryService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="informationEntryInit", params={"actionType", Constants.ACTION_TYPE_INSERT})
	public String init() throws Exception {
		// �����\������
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * �X�V�����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="informationEntryUpdateInit", params={"actionType", Constants.ACTION_TYPE_UPDATE})
	public String updateInit() throws Exception {
		// �X�V��̌�����ʗp�p�����[�^��ݒ�
		// �����{��̍��ڂ� Action�N���X�̃��_�C���N�g���ɐݒ�o���Ȃ��ׁA�����Ŏ��{
		if (StringUtils.isNotBlank(model.getEncodedKokyakuNm())) {
			model.getCondition().setKokyakuNm(model.getEncodedKokyakuNm());
		} else if (StringUtils.isNotBlank(model.getEncodedGyoshaNm())) {
			model.getCondition().setGyoshaNm(model.getEncodedGyoshaNm());
		}

		// �X�V�����\������
		model = service.getUpdateInitInfo(model);

		return SUCCESS;
	}

	/**
	 * �X�V�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="informationEntryUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="informationEntryUpdateInit?" +
									"seqNo=${seqNo}" +
									"&condition.offset=${condition.offset}" +
									"&condition.target=${condition.target}" +
									"&condition.kokyakuId=${condition.kokyakuId}" +
									"&encodedKokyakuNm=${encodedKokyakuNm}" +
									"&condition.gyoshaCd=${condition.gyoshaCd}" +
									"&encodedGyoshaNm=${encodedGyoshaNm}" +
									"&condition.hyojiJoken=${condition.hyojiJoken}")
			}
	)
	public String update() throws Exception {
		if (model.isInsert()) {
			// �o�^����
			model = service.insertInfo(model);

			// �������b�Z�[�W
			addActionMessage("MSG0001", "���m�点�̓o�^");
		} else if (model.isUpdate()) {
			// �X�V����
			service.updateInfo(model);

			// �������b�Z�[�W
			addActionMessage("MSG0001", "���m�点�̍X�V");
		} else {
			// �A�N�V�����^�C�v���z��O�̏ꍇ�́A���ʋƖ��G���[
			throw new ApplicationException("�A�N�V�����^�C�v�s���F�p�����[�^�̃A�N�V�����^�C�v");
		}

		// �X�V�����\�������p�p�����[�^�ݒ�
		model.setSeqNo(model.getInfo().getSeqNo());
		model.setEncodedKokyakuNm(model.encode(model.getCondition().getKokyakuNm()));
		model.setEncodedGyoshaNm(model.encode(model.getCondition().getGyoshaNm()));

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB104InformationEntryModel getModel() {
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

