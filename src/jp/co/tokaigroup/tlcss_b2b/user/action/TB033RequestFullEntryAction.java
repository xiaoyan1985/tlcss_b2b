package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB033RequestFullEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB033RequestFullEntryService;

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
 * �˗��o�^�A�N�V�����N���X�B
 * 
 * @author S.Nakano
 * @version 1.0 2015/10/27
 * @version 1.1 2016/07/13 H.Yamamura �₢���킹������ʂ̖₢���킹�敪�P�`�S�A�����ԍ��̒l�������܂��悤�ɏC��
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb033_request_full_entry.jsp"),
	@Result(name=INPUT, location="tb033_request_full_entry.jsp")
})
public class TB033RequestFullEntryAction extends TLCSSB2BBaseActionSupport
		implements ModelDriven<TB033RequestFullEntryModel>, ServiceValidatable {

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB033RequestFullEntryModel model = new TB033RequestFullEntryModel();

	/** �T�[�r�X */
	@Autowired
	private TB033RequestFullEntryService service;
	
	/**
	 * �����\���������s���܂��B
	 * 
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestFullEntryInit")
	public String init() {
		try {
			// �����\������
			model = service.getInitInfo(model);
			
			if (model.getIrai() == null) {
				model.setIrai(new RcpTIrai());
			}
			
			// �����l�Ƃ��āA�����S���Җ��Ƀ��O�C�����[�U�����Z�b�g
			model.getIrai().setTantoshaNm(model.getUserContext().getUserName());
			
			model.setActionType(Constants.ACTION_TYPE_INSERT);
			model.setGamenKbn(Constants.GAMEN_KBN_REQUEST_ENTRY);
		} catch (ForbiddenException e) {
			// �Z�L�����e�B�G���[�̏ꍇ�́A403�G���[����ʂɕ\��
			return FORBIDDEN_ERROR;
		}
		
		return SUCCESS;
	}
	
	/**
	 * �X�V�����\���������s���܂��B
	 * 
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestFullEntryUpdateInit")
	public String updateInit() {
		try {
			// �X�V�����\������
			model = service.getSagyoIraiInfo(model);
			
			model.setActionType(Constants.ACTION_TYPE_UPDATE);
			model.setGamenKbn(Constants.GAMEN_KBN_REQUEST_ENTRY);
			
			// �X�V��̌�����ʗp�p�����[�^��ݒ�
			// �����{��̍��ڂ� Action�N���X�̃��_�C���N�g���ɐݒ�o���Ȃ��ׁA�����Ŏ��{
			model.setCondition(remakeCondition(model));
			// �₢���킹������ʗp�p�����[�^��ݒ�
			model.setToiawaseCondition(remakeToiawaseCondition(model));
		} catch (ForbiddenException e) {
			// �Z�L�����e�B�G���[�̏ꍇ�́A403�G���[����ʂɕ\��
			return FORBIDDEN_ERROR;
		}
		
		return SUCCESS;
	}
	
	/**
	 * �X�V�������s���܂��B
	 * 
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="requestFullEntryUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="requestFullEntryUpdateInit?" +
									"&toiawaseNo=${toiawaseNo}" +
									"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
									"&dispKbn=${dispKbn}" +
									"&rootDispKbn=${rootDispKbn}" +
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
									"&encodedKanjiNm2=${encodedKanjiNm2}" +
									"&toiawaseCondition.toiawaseNo=${toiawaseCondition.toiawaseNo}" +
									"&toiawaseCondition.uketsukebiFrom=${toiawaseCondition.uketsukebiFrom}" +
									"&toiawaseCondition.uketsukebiTo=${toiawaseCondition.uketsukebiTo}" +
									"&toiawaseCondition.sortOrder=${toiawaseCondition.sortOrder}" +
									"&toiawaseCondition.jokyo=${toiawaseCondition.jokyo}" +
									"&toiawaseCondition.serviceKbn=${toiawaseCondition.serviceKbn}" +
									"&toiawaseCondition.telNo=${toiawaseCondition.telNo}" +
									"&toiawaseCondition.offset=${toiawaseCondition.offset}" +
									"&toiawaseCondition.kubun1=${toiawaseCondition.kubun1}" +
									"&toiawaseCondition.kubun2=${toiawaseCondition.kubun2}" +
									"&toiawaseCondition.kubun3=${toiawaseCondition.kubun3}" +
									"&toiawaseCondition.kubun4=${toiawaseCondition.kubun4}" +
									"&encodedToiawaseJusho1=${encodedToiawaseJusho1}" +
									"&encodedToiawaseJusho2=${encodedToiawaseJusho2}" +
									"&encodedToiawaseJusho3=${encodedToiawaseJusho3}" +
									"&encodedToiawaseJusho4=${encodedToiawaseJusho4}" +
									"&encodedToiawaseJusho5=${encodedToiawaseJusho5}" +
									"&encodedToiawaseRoomNo=${encodedToiawaseRoomNo}" +
									"&encodedToiawaseKanaNm1=${encodedToiawaseKanaNm1}" +
									"&encodedToiawaseKanaNm2=${encodedToiawaseKanaNm2}" +
									"&encodedToiawaseKanjiNm1=${encodedToiawaseKanjiNm1}" +
									"&encodedToiawaseKanjiNm2=${encodedToiawaseKanjiNm2}"
					),
					@Result(name=DELETE, location="tb033_request_full_entry.jsp")
			}
	)
	public String update() {
		if (model.isInsert()) {
			// �o�^����
			service.insertSagyoIraiInfo(model);
			
			addActionMessage("MSG0001", "�˗����̓o�^");
		} else if (model.isUpdate()) {
			// �X�V����
			service.updateSagyoIraiInfo(model);
			
			addActionMessage("MSG0001", "�˗����̍X�V");
		} else if (model.isDelete()) {
			// �폜����
			service.deleteSagyoIraiInfo(model);
			
			if (model.isFileDeleteError()) {
				// �t�@�C���폜�G���[�̏ꍇ�́A���O���o��
				log.error(getText("MSG0019"));
			}
			
			model.setDeleteCompleted(true);
			
			addActionMessage("MSG0001", "�˗����̍폜");
		}
		
		// �폜�̏ꍇ��redirect���Ȃ�
		if (model.isDelete()) {
			return DELETE;
		} else {
			// �X�V��̌�����ʗp�p�����[�^��ݒ�
			executeEncode(model);
			
			return SUCCESS;
		}
	}
	
	/**
	 * �Ǝ҈˗����[�����M�������s���܂��B
	 * 
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestFullEntrySendMail")
	public String sendMail() {
		// �Ǝ҈˗����[�����M����
		service.sendGyoshaIraiMail(model);
		
		addActionMessage("MSG0001", "�Z���^�[�Ǝ҈˗����[�����M");
		
		return SUCCESS;
	}
	
	/**
	 * �摜�폜�������s���܂��B
	 * 
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="requestFullEntryImageDelete",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="requestFullEntryUpdateInit?" +
									"&toiawaseNo=${toiawaseNo}" +
									"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
									"&dispKbn=${dispKbn}" +
									"&rootDispKbn=${rootDispKbn}" +
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
									"&encodedKanjiNm2=${encodedKanjiNm2}" +
									"&toiawaseCondition.toiawaseNo=${toiawaseCondition.toiawaseNo}" +
									"&toiawaseCondition.uketsukebiFrom=${toiawaseCondition.uketsukebiFrom}" +
									"&toiawaseCondition.uketsukebiTo=${toiawaseCondition.uketsukebiTo}" +
									"&toiawaseCondition.sortOrder=${toiawaseCondition.sortOrder}" +
									"&toiawaseCondition.jokyo=${toiawaseCondition.jokyo}" +
									"&toiawaseCondition.serviceKbn=${toiawaseCondition.serviceKbn}" +
									"&toiawaseCondition.telNo=${toiawaseCondition.telNo}" +
									"&toiawaseCondition.offset=${toiawaseCondition.offset}" +
									"&toiawaseCondition.kubun1=${toiawaseCondition.kubun1}" +
									"&toiawaseCondition.kubun2=${toiawaseCondition.kubun2}" +
									"&toiawaseCondition.kubun3=${toiawaseCondition.kubun3}" +
									"&toiawaseCondition.kubun4=${toiawaseCondition.kubun4}" +
									"&encodedToiawaseJusho1=${encodedToiawaseJusho1}" +
									"&encodedToiawaseJusho2=${encodedToiawaseJusho2}" +
									"&encodedToiawaseJusho3=${encodedToiawaseJusho3}" +
									"&encodedToiawaseJusho4=${encodedToiawaseJusho4}" +
									"&encodedToiawaseJusho5=${encodedToiawaseJusho5}" +
									"&encodedToiawaseRoomNo=${encodedToiawaseRoomNo}" +
									"&encodedToiawaseKanaNm1=${encodedToiawaseKanaNm1}" +
									"&encodedToiawaseKanaNm2=${encodedToiawaseKanaNm2}" +
									"&encodedToiawaseKanjiNm1=${encodedToiawaseKanjiNm1}" +
									"&encodedToiawaseKanjiNm2=${encodedToiawaseKanjiNm2}"
					)
			}
	)
	public String imageDelete() {
		// �摜�t�@�C���폜����
		service.deleteSagyoJokyoImageFile(model);
		
		if (model.isFileDeleteError()) {
			// �t�@�C���폜�G���[�̏ꍇ�́A���O���o��
			log.error(getText("MSG0019"));
		}
		
		addActionMessage("MSG0001", "�摜�t�@�C���̍폜");
		
		// �X�V��̌�����ʗp�p�����[�^��ݒ�
		executeEncode(model);

		return SUCCESS;
	}
	
	/**
	 * ���̑��t�@�C���폜�������s���܂��B
	 * 
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="requestFullEntryOtherDelete",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="requestFullEntryUpdateInit?" +
									"&toiawaseNo=${toiawaseNo}" +
									"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
									"&dispKbn=${dispKbn}" +
									"&rootDispKbn=${rootDispKbn}" +
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
									"&encodedKanjiNm2=${encodedKanjiNm2}" +
									"&toiawaseCondition.toiawaseNo=${toiawaseCondition.toiawaseNo}" +
									"&toiawaseCondition.uketsukebiFrom=${toiawaseCondition.uketsukebiFrom}" +
									"&toiawaseCondition.uketsukebiTo=${toiawaseCondition.uketsukebiTo}" +
									"&toiawaseCondition.sortOrder=${toiawaseCondition.sortOrder}" +
									"&toiawaseCondition.jokyo=${toiawaseCondition.jokyo}" +
									"&toiawaseCondition.serviceKbn=${toiawaseCondition.serviceKbn}" +
									"&toiawaseCondition.telNo=${toiawaseCondition.telNo}" +
									"&toiawaseCondition.offset=${toiawaseCondition.offset}" +
									"&toiawaseCondition.kubun1=${toiawaseCondition.kubun1}" +
									"&toiawaseCondition.kubun2=${toiawaseCondition.kubun2}" +
									"&toiawaseCondition.kubun3=${toiawaseCondition.kubun3}" +
									"&toiawaseCondition.kubun4=${toiawaseCondition.kubun4}" +
									"&encodedToiawaseJusho1=${encodedToiawaseJusho1}" +
									"&encodedToiawaseJusho2=${encodedToiawaseJusho2}" +
									"&encodedToiawaseJusho3=${encodedToiawaseJusho3}" +
									"&encodedToiawaseJusho4=${encodedToiawaseJusho4}" +
									"&encodedToiawaseJusho5=${encodedToiawaseJusho5}" +
									"&encodedToiawaseRoomNo=${encodedToiawaseRoomNo}" +
									"&encodedToiawaseKanaNm1=${encodedToiawaseKanaNm1}" +
									"&encodedToiawaseKanaNm2=${encodedToiawaseKanaNm2}" +
									"&encodedToiawaseKanjiNm1=${encodedToiawaseKanjiNm1}" +
									"&encodedToiawaseKanjiNm2=${encodedToiawaseKanjiNm2}"
					)
			}
	)
	public String otherDelete() {
		// ���̑��t�@�C���폜����
		service.deleteOtherFile(model);
		
		if (model.isFileDeleteError()) {
			// �t�@�C���폜�G���[�̏ꍇ�́A���O���o��
			log.error(getText("MSG0019"));
		}
		
		addActionMessage("MSG0001", "���̑��t�@�C���̍폜");
		
		// �X�V��̌�����ʗp�p�����[�^��ݒ�
		executeEncode(model);

		return SUCCESS;
	}
	
	/**
	 * �˗�������������s���܂��B
	 * 
	 * @return
	 */
	@Action(
			value="requestFullEntryWorkRequestPrint",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			}
	)
	public String workRequestPrint() {
		// �˗����������
		model.setWorkRequestPrinted(true);
		model.setActionType(Constants.ACTION_TYPE_RESTORE);
		
		try {
			// ��ƈ˗����������
			model = service.createPdf(model);
		} catch (Exception e) {
			log.error(e.getMessage());
			try {
				// ��ʍĕ\�������Ăяo���i�A���[�g���b�Z�[�W��\�����邽�߁AValidationException��throw�ł��Ȃ��j
				model = service.parepareInitInfoForUpdate(model);
			} catch (Exception e2) {
				log.error(e.getMessage());
			}
		}
		model.setActionType(Constants.ACTION_TYPE_UPDATE);
		
		return SUCCESS;
	}
	
	/**
	 * ���������̃G���R�[�h���s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	private TB033RequestFullEntryModel executeEncode(TB033RequestFullEntryModel model) {
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

		// �₢���킹�����p
		RC031ToiawaseSearchCondition toiawaseCondition = model.getToiawaseCondition();
		if (toiawaseCondition == null) {
			toiawaseCondition = new RC031ToiawaseSearchCondition();
		}
		// �Z���P
		model.setEncodedToiawaseJusho1(model.encode(toiawaseCondition.getJusho1()));
		// �Z���Q
		model.setEncodedToiawaseJusho2(model.encode(toiawaseCondition.getJusho2()));
		// �Z���R
		model.setEncodedToiawaseJusho3(model.encode(toiawaseCondition.getJusho3()));
		// �Z���S
		model.setEncodedToiawaseJusho4(model.encode(toiawaseCondition.getJusho4()));
		// �Z���T
		model.setEncodedToiawaseJusho5(model.encode(toiawaseCondition.getJusho5()));
		// �����ԍ�
		model.setEncodedToiawaseRoomNo(model.encode(toiawaseCondition.getRoomNo()));
		// �J�i�����P
		model.setEncodedToiawaseKanaNm1(model.encode(toiawaseCondition.getKanaNm1()));
		// �J�i�����Q
		model.setEncodedToiawaseKanaNm2(model.encode(toiawaseCondition.getKanaNm2()));
		// ���������P
		model.setEncodedToiawaseKanjiNm1(model.encode(toiawaseCondition.getKanjiNm1()));
		// ���������Q
		model.setEncodedToiawaseKanjiNm2(model.encode(toiawaseCondition.getKanjiNm2()));
		
		return model;
	}

	/**
	 * �����������č쐬���܂��B
	 * �����{��̍��ڂ� Action�N���X�̃��_�C���N�g���ɐݒ�o���Ȃ��ׁB
	 *
	 * @param model ��ʃ��f��
	 * @return ���������N���X
	 */
	private RC041IraiSearchCondition remakeCondition(TB033RequestFullEntryModel model) {
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
	 * �₢���킹�����������č쐬���܂��B
	 * �����{��̍��ڂ� Action�N���X�̃��_�C���N�g���ɐݒ�o���Ȃ��ׁB
	 *
	 * @param model ��ʃ��f��
	 * @return ���������N���X
	 */
	private RC031ToiawaseSearchCondition remakeToiawaseCondition(TB033RequestFullEntryModel model) {
		RC031ToiawaseSearchCondition condition = model.getToiawaseCondition();
		if (condition == null) {
			condition = new RC031ToiawaseSearchCondition();
		}

		// �Z���P
		if (StringUtils.isNotBlank(model.getEncodedToiawaseJusho1())) {
			condition.setJusho1(model.getEncodedToiawaseJusho1());
		}
		// �Z���Q
		if (StringUtils.isNotBlank(model.getEncodedToiawaseJusho2())) {
			condition.setJusho2(model.getEncodedToiawaseJusho2());
		}
		// �Z���R
		if (StringUtils.isNotBlank(model.getEncodedToiawaseJusho3())) {
			condition.setJusho3(model.getEncodedToiawaseJusho3());
		}
		// �Z���S
		if (StringUtils.isNotBlank(model.getEncodedToiawaseJusho4())) {
			condition.setJusho4(model.getEncodedToiawaseJusho4());
		}
		// �Z���T
		if (StringUtils.isNotBlank(model.getEncodedToiawaseJusho5())) {
			condition.setJusho5(model.getEncodedToiawaseJusho5());
		}
		// �����ԍ�
		if (StringUtils.isNotBlank(model.getEncodedToiawaseRoomNo())) {
			condition.setRoomNo(model.getEncodedToiawaseRoomNo());
		}
		// �J�i�����P
		if (StringUtils.isNotBlank(model.getEncodedToiawaseKanaNm1())) {
			condition.setKanaNm1(model.getEncodedToiawaseKanaNm1());
		}
		// �J�i�����Q
		if (StringUtils.isNotBlank(model.getEncodedToiawaseKanaNm2())) {
			condition.setKanaNm2(model.getEncodedToiawaseKanaNm2());
		}
		// ���������P
		if (StringUtils.isNotBlank(model.getEncodedToiawaseKanjiNm1())) {
			condition.setKanjiNm1(model.getEncodedToiawaseKanjiNm1());
		}
		// ���������Q
		if (StringUtils.isNotBlank(model.getEncodedToiawaseKanjiNm2())) {
			condition.setKanjiNm2(model.getEncodedToiawaseKanjiNm2());
		}

		return condition;
	}
	
	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	@Override
	public TB033RequestFullEntryModel getModel() {
		return model;
	}
	
	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	@Override
	public void prepareModel() {
		if (model.isInitError()) {
			// �����\���G���[�̍ۂ́A�������Ȃ�
			return;
		}
		
		if (model.isInsert()) {
			model = service.parepareInitInfo(model);
		} else if (model.isUpdate()) {
			model = service.parepareInitInfoForUpdate(model);
		} else if (model.isDelete()) {
			// �A�N�V�����^�C�v��߂�
			model.setActionType(Constants.ACTION_TYPE_UPDATE);
			model = service.parepareInitInfoForUpdate(model);
		}
	}
}
