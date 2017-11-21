package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.context.UserContextSupport;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB032RequestEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB032RequestEntryService;

import org.apache.commons.lang.StringUtils;
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
 * �˗����e�ڍׁE��Ə󋵓o�^�A�N�V�����N���X�B
 *
 * @author k002849
 * @version 4.0 2014/07/15
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb032_request_entry.jsp"),
	@Result(name=INPUT, location="tb032_request_entry.jsp")
})
public class TB032RequestEntryAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB032RequestEntryModel>, ServiceValidatable {
	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB032RequestEntryModel model;

	/** �T�[�r�X */
	@Autowired
	private TB032RequestEntryService service;

	/**
	 * ���f���̏����������B
	 * ���A�b�v���[�h�t�@�C���̗v�f�������f���������Ɏw�肵�Ȃ���΁A�o�^�ł��Ȃ����߁A
	 * �����ŁA���f���̐����B
	 */
	@PostConstruct
	public void modelInit() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (session == null) {
			return;
		}

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) session.getAttribute(UserContextSupport.KEY);
		if (userContext == null) {
			return;
		}

		try {
			model = new TB032RequestEntryModel(userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_IRAI_FILE_APPENEDED_MAX));
		} catch (ApplicationException e) {
			// �V�X�e���}�X�^��055:TB032�˗����e�ڍׁE��Ə󋵓o�^�Y�t�\�ő匏�����Ȃ��ƁA
			// �V�X�e��500�G���[�ƂȂ邽�߁A������catch���A�f�t�H���g�R���X�g���N�^��model�𐶐�
			model = new TB032RequestEntryModel();
		}

	}

	/**
	 * �����\�������i�ڍו\���j���s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestDetail")
	public String detail() throws Exception {
		// �����������i�ڍׁj���s
		model = service.getDetailInitInfo(model);
		if (model == null) {
			// �A�N�Z�X�s�̏ꍇ�́A403�G���[����ʂɕ\��
			return FORBIDDEN_ERROR;
		}
		if (model.isNotPublish()) {
			// ����J�̏�񂪂���ꍇ�́A403�G���[��ʂɑJ��
			return FORBIDDEN_ERROR;
		}

		// �X�V��̌�����ʗp�p�����[�^��ݒ�
		// �����{��̍��ڂ� Action�N���X�̃��_�C���N�g���ɐݒ�o���Ȃ��ׁA�����Ŏ��{
		model.setCondition(remakeCondition(model));
		// ��ʋ敪�̐ݒ�
		model.setGamenKbn(Constants.GAMEN_KBN_REQUEST_DETAIL);
		
		return SUCCESS;
	}

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestEntryInit")
	public String entryInit() throws Exception {
		// �����������i�o�^�j���s
		model = service.getEntryInitInfo(model);
		if (model == null) {
			// �A�N�Z�X�s�̏ꍇ�́A403�G���[����ʂɕ\��
			return FORBIDDEN_ERROR;
		}

		if (model.isNotPublish()) {
			// ����J�̏�񂪂���ꍇ�́A403�G���[��ʂɑJ��
			return FORBIDDEN_ERROR;
		}

		// �X�V��̌�����ʗp�p�����[�^��ݒ�
		// �����{��̍��ڂ� Action�N���X�̃��_�C���N�g���ɐݒ�o���Ȃ��ׁA�����Ŏ��{
		model.setCondition(remakeCondition(model));

		if (model.getSagyoJokyo() == null) {
			// ��Ə󋵏�񂪂Ȃ���΁A�V�K�o�^���[�h
			model.setActionType(Constants.ACTION_TYPE_INSERT);
		} else {
			// ��Ə󋵏�񂪂���΁A�X�V���[�h
			model.setActionType(Constants.ACTION_TYPE_UPDATE);
		}
		
		// ��ʋ敪�̐ݒ�
		model.setGamenKbn(Constants.GAMEN_KBN_REQUEST_DETAIL);

		return SUCCESS;
	}

	/**
	 * �摜�폜�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="requestEntryImageDelete",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="requestEntryInit?" +
								"toiawaseNo=${toiawaseNo}" +
								"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
								"&dispKbn=${dispKbn}" +
								"&condition.offset=${condition.offset}" +
								"&condition.serviceKbn=${condition.serviceKbn}" +
								"&condition.toiawaseNo=${condition.toiawaseNo}" +
								"&condition.telNo=${condition.telNo}" +
								"&condition.uketsukebiFrom=${condition.uketsukebiFrom}" +
								"&condition.uketsukebiTo=${condition.uketsukebiTo}" +
								"&condition.iraiKanryo=${condition.iraiKanryo}" +
								"&condition.iraiKanryobiFrom=${condition.iraiKanryobiFrom}" +
								"&condition.iraiKanryobiTo=${condition.iraiKanryobiTo}" +
								"&encodedJusho1=${encodedJusho1}" +
								"&encodedJusho2=${encodedJusho2}" +
								"&encodedJusho3=${encodedJusho3}" +
								"&encodedJusho4=${encodedJusho4}" +
								"&encodedJusho5=${encodedJusho5}" +
								"&encodedKanaNm1=${encodedKanaNm1}" +
								"&encodedKanaNm2=${encodedKanaNm2}" +
								"&encodedKanjiNm1=${encodedKanjiNm1}" +
								"&encodedKanjiNm2=${encodedKanjiNm2}"
							)
			}
	)
	public String imageDelete() throws Exception {
		// �摜�폜����
		service.deleteImageInfo(model);

		if (!model.isSuccessDeleteImage()) {
			// �t�@�C���폜���s���̃��O
			log.error(getText("MSG0019"));
		}

		// �������b�Z�[�W
		addActionMessage("MSG0001", "�摜�t�@�C���̍폜");

		// �X�V��̌�����ʗp�p�����[�^��ݒ�
		executeEncode(model);

		return SUCCESS;
	}

	/**
	 * �X�V�������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O
	 */
	@Action(
			value="requestEntryUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="requestEntryInit?" +
								"toiawaseNo=${toiawaseNo}" +
								"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
								"&dispKbn=${dispKbn}" +
								"&condition.offset=${condition.offset}" +
								"&condition.serviceKbn=${condition.serviceKbn}" +
								"&condition.toiawaseNo=${condition.toiawaseNo}" +
								"&condition.telNo=${condition.telNo}" +
								"&condition.uketsukebiFrom=${condition.uketsukebiFrom}" +
								"&condition.uketsukebiTo=${condition.uketsukebiTo}" +
								"&condition.iraiKanryo=${condition.iraiKanryo}" +
								"&condition.iraiKanryobiFrom=${condition.iraiKanryobiFrom}" +
								"&condition.iraiKanryobiTo=${condition.iraiKanryobiTo}" +
								"&encodedJusho1=${encodedJusho1}" +
								"&encodedJusho2=${encodedJusho2}" +
								"&encodedJusho3=${encodedJusho3}" +
								"&encodedJusho4=${encodedJusho4}" +
								"&encodedJusho5=${encodedJusho5}" +
								"&encodedKanaNm1=${encodedKanaNm1}" +
								"&encodedKanaNm2=${encodedKanaNm2}" +
								"&encodedKanjiNm1=${encodedKanjiNm1}" +
								"&encodedKanjiNm2=${encodedKanjiNm2}"
					)
			}
	)
	public String update() throws Exception {
		if (model.isInsert()) {
			// �o�^����
			service.insertGyoshaSagyoJokyoInfo(model);

			// �������b�Z�[�W
			addActionMessage("MSG0001", "��Ɠ��e�̓o�^");
		} else if (model.isUpdate()) {
			// �X�V����
			service.updateGyoshaSagyoJokyoInfo(model);

			// �������b�Z�[�W
			addActionMessage("MSG0001", "��Ɠ��e�̍X�V");
		}

		// �X�V��̌�����ʗp�p�����[�^��ݒ�
		executeEncode(model);

		return SUCCESS;
	}

	/**
	 * ���������̃G���R�[�h���s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	private TB032RequestEntryModel executeEncode(TB032RequestEntryModel model) {
		// �X�V��̌�����ʗp�p�����[�^��ݒ�
		RC041IraiSearchCondition condition = model.getCondition();
		if (condition == null) {
			condition = new RC041IraiSearchCondition();
		}
		// �Z���P
		model.setEncodedJusho1(model.encode(condition.getJusho1()));
		// �Z���Q
		model.setEncodedJusho2(model.encode(condition.getJusho2()));
		// �Z���R
		model.setEncodedJusho3(model.encode(condition.getJusho3()));
		// �Z���S
		model.setEncodedJusho4(model.encode(condition.getJusho4()));
		// �Z���T
		model.setEncodedJusho5(model.encode(condition.getJusho5()));
		// �J�i�����P
		model.setEncodedKanaNm1(model.encode(condition.getKanaNm1()));
		// �J�i�����Q
		model.setEncodedKanaNm2(model.encode(condition.getKanaNm2()));
		// ���������P
		model.setEncodedKanjiNm1(model.encode(condition.getKanjiNm1()));
		// ���������Q
		model.setEncodedKanjiNm2(model.encode(condition.getKanjiNm2()));

		return model;
	}

	/**
	 * �����������č쐬���܂��B
	 * �����{��̍��ڂ� Action�N���X�̃��_�C���N�g���ɐݒ�o���Ȃ��ׁB
	 *
	 * @param model ��ʃ��f��
	 * @return ���������N���X
	 */
	private RC041IraiSearchCondition remakeCondition(TB032RequestEntryModel model) {
		RC041IraiSearchCondition condition = model.getCondition();
		if (condition == null) {
			condition = new RC041IraiSearchCondition();
		}

		// �Z���P
		if (StringUtils.isNotBlank(model.getEncodedJusho1())) {
			condition.setJusho1(model.getEncodedJusho1());
		}
		// �Z���Q
		if (StringUtils.isNotBlank(model.getEncodedJusho2())) {
			condition.setJusho2(model.getEncodedJusho2());
		}
		// �Z���R
		if (StringUtils.isNotBlank(model.getEncodedJusho3())) {
			condition.setJusho3(model.getEncodedJusho3());
		}
		// �Z���S
		if (StringUtils.isNotBlank(model.getEncodedJusho4())) {
			condition.setJusho4(model.getEncodedJusho4());
		}
		// �Z���T
		if (StringUtils.isNotBlank(model.getEncodedJusho5())) {
			condition.setJusho5(model.getEncodedJusho5());
		}
		// �J�i�����P
		if (StringUtils.isNotBlank(model.getEncodedKanaNm1())) {
			condition.setKanaNm1(model.getEncodedKanaNm1());
		}
		// �J�i�����Q
		if (StringUtils.isNotBlank(model.getEncodedKanaNm2())) {
			condition.setKanaNm2(model.getEncodedKanaNm2());
		}
		// ���������P
		if (StringUtils.isNotBlank(model.getEncodedKanjiNm1())) {
			condition.setKanjiNm1(model.getEncodedKanjiNm1());
		}
		// ���������Q
		if (StringUtils.isNotBlank(model.getEncodedKanjiNm2())) {
			condition.setKanjiNm2(model.getEncodedKanjiNm2());
		}

		return condition;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB032RequestEntryModel getModel() {
		return model;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		model = service.getEntryPrepareInitInfo(model);
	}
}
