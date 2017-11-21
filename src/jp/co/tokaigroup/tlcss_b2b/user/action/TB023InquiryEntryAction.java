package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.context.UserContextSupport;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB023InquiryEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB023InquiryEntryService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �₢���킹�o�^�A�N�V�����N���X�B
 *
 * @author k003316
 * @version 1.0 2015/08/11
 * @version 1.1 2016/07/13 H.Yamamura �₢���킹������ʂ̖₢���킹�敪�P�`�S�A�����ԍ��̒l�������܂��悤�ɏC��
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb023_inquiry_entry.jsp"),
	@Result(name=INPUT, location="tb023_inquiry_entry.jsp")
})
public class TB023InquiryEntryAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB023InquiryEntryModel>, ServiceValidatable {
	
	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB023InquiryEntryModel model = new TB023InquiryEntryModel();

	/** �T�[�r�X */
	@Autowired
	private TB023InquiryEntryService service;

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
			model = new TB023InquiryEntryModel(userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_BSB_TOIAWASE_FILE_TO_MAX));
		} catch (ApplicationException e) {
			// �V�X�e���}�X�^��055:TB032�˗����e�ڍׁE��Ə󋵓o�^�Y�t�\�ő匏�����Ȃ��ƁA
			// �V�X�e��500�G���[�ƂȂ邽�߁A������catch���A�f�t�H���g�R���X�g���N�^��model�𐶐�
			model = new TB023InquiryEntryModel();
		}
	}
	
	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="inquiryEntryInit",
			params={"actionType", Constants.ACTION_TYPE_INSERT}
	)
	public String init() throws Exception {
		
		try {
			// �����\�����擾
			model = service.getInitInfo(model);
			
		} catch (ForbiddenException e) {
			// �Z�L�����e�B�G���[�̏ꍇ�́A403�G���[����ʂɕ\��
			return FORBIDDEN_ERROR;
			
		} catch (ValidationException e) {
			// �����\���G���[�t���O��ݒ肵�Ajavascript�Ɠ��e��\�����Ȃ��悤�ɂ���
			model.setInitError(true);
			// ���b�Z�[�W�������邽�߁AException������o���čĐݒ�
			addActionError(e.getValidationPack().getActionErrors().get(0).getMessageId()
					, e.getValidationPack().getActionErrors().get(0).getParams());
		}
		
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		
		RcpTToiawase toiawase = new RcpTToiawase();
		toiawase.setUketsukeshaNm(userContext.getUserName());
		toiawase.setUketsukeYmd(DateUtil.toSqlTimestamp(DateUtils.truncate(DateUtil.getSysDateTime(), Calendar.DATE)));
		toiawase.setServiceShubetsu(model.getInitServiceShubetsu());
		
		model.setToiawaseInfo(toiawase);
		model.setGamenKbn(Constants.GAMEN_KBN_INQUIRY_ENTRY);
		
		return SUCCESS;
	}
	
	/**
	 * �X�V�����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="inquiryEntryUpdateInit",
			params={"actionType", Constants.ACTION_TYPE_UPDATE}
	)
	public String updateInit() throws Exception {
		
		try {
			if (StringUtils.isNotBlank(model.getToiawaseNo()) &&
				model.getToiawaseNo().length() < RcpTToiawase.TOIAWASE_NO_COLUMN_LENGTH) {
				// �₢���킹�m�n��MAX�l�ɖ����Ȃ��ꍇ�ɂ́AC+�O0�l�߂��ĕ\��
				model.setToiawaseNo(StringUtils.leftPad(model.getToiawaseNo(), RcpTToiawase.TOIAWASE_NO_COLUMN_LENGTH, '0'));
			}
			
			// �₢���킹���擾
			model = service.getInitInfoForUpdate(model);
			
			if (model.getToiawaseInfo().isRegistKbnToExternalCooperationData()) {
				// �₢���킹���̓o�^�敪���u�O���A�g�f�[�^�v�̏ꍇ�́A���b�Z�[�W��\��
				addActionError("MSG0038", "�O���A�g�f�[�^");
			}
			if (StringUtils.isNotBlank(model.getToiawaseInfo().getShimeYm())) {
				// ���ߍς̖₢���킹���̏ꍇ�́A���b�Z�[�W��\��
				addActionError("MSG0038", DateUtil.yyyymmPlusSlash(model.getToiawaseInfo().getShimeYm()) + "���ߍ�");
			}
			
			// �q��ʂ̏������������Ă���ꍇ
			if (StringUtils.isNotBlank(model.getCompleteMessageId())) {
				addActionMessage(model.getCompleteMessageId(), model.getCompleteMessageStr());
			}
		} catch (ForbiddenException e) {
			// �Z�L�����e�B�G���[�̏ꍇ�́A403�G���[����ʂɕ\��
			return FORBIDDEN_ERROR;
			
		} catch (ValidationException e) {
			// �����\���G���[�t���O��ݒ肵�Ajavascript�Ɠ��e��\�����Ȃ��悤�ɂ���
			model.setInitError(true);
			// ���b�Z�[�W�������邽�߁AException������o���čĐݒ�
			addActionError(e.getValidationPack().getActionErrors().get(0).getMessageId()
					, e.getValidationPack().getActionErrors().get(0).getParams());
		}
		
		// �₢���킹������ʗp�p�����[�^��ݒ�
		model.setCondition(remakeCondition(model));
		
		model.setGamenKbn(Constants.GAMEN_KBN_INQUIRY_ENTRY);
		model.setActionType(Constants.ACTION_TYPE_UPDATE);
		
		return SUCCESS;
	}
	
	/**
	 * �₢���킹���̓o�^�A�X�V�A�폜���s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="inquiryEntryUpdate",
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="inquiryEntryUpdateInit?" +
									"&kokyakuId=${kokyakuId}" +
									"&toiawaseNo=${toiawaseNo}" +
									"&toiawaseUpdDt=${toiawaseUpdDt}" +
									"&dispKbn=${dispKbn}" +
									"&condition.toiawaseNo=${condition.toiawaseNo}" +
									"&condition.uketsukebiFrom=${condition.uketsukebiFrom}" +
									"&condition.uketsukebiTo=${condition.uketsukebiTo}" +
									"&encodedJusho1=${encodedJusho1}" +
									"&encodedJusho2=${encodedJusho2}" +
									"&encodedJusho3=${encodedJusho3}" +
									"&encodedJusho4=${encodedJusho4}" +
									"&encodedJusho5=${encodedJusho5}" +
									"&encodedRoomNo=${encodedRoomNo}" +
									"&encodedKanaNm1=${encodedKanaNm1}" +
									"&encodedKanaNm2=${encodedKanaNm2}" +
									"&encodedKanjiNm1=${encodedKanjiNm1}" +
									"&encodedKanjiNm2=${encodedKanjiNm2}" +
									"&condition.sortOrder=${condition.sortOrder}" +
									"&condition.jokyo=${condition.jokyo}" +
									"&condition.serviceKbn=${condition.serviceKbn}" +
									"&condition.telNo=${condition.telNo}" +
									"&condition.kubun1=${condition.kubun1}" +
									"&condition.kubun2=${condition.kubun2}" +
									"&condition.kubun3=${condition.kubun3}" +
									"&condition.kubun4=${condition.kubun4}" +
									"&condition.offset=${condition.offset}"
					),
					@Result(name=DELETE, location="tb023_inquiry_entry.jsp")
			}
	)
	public String update() throws Exception {
		
		try {
			// �A�N�V�����^�C�v�ɂ���āA�����؂�ւ�
			if (model.isInsert()) {
				// �o�^
				service.insertToiawaseInfo(model);
				addActionMessage("MSG0001", "�₢���킹���̓o�^");
				// ���_�C���N�g�O�ɕύX�̂���p�����[�^��ݒ�
				model.setToiawaseNo(model.getToiawaseInfo().getToiawaseNo());
				
			} else if (model.isUpdate()) {
				// �X�V
				service.updateToiawaseInfo(model);
				addActionMessage("MSG0001", "�₢���킹���̍X�V");
				
			} else if (model.isDelete()) {
				// �폜
				service.deleteToiawaseInfo(model);
				addActionMessage("MSG0001", "�₢���킹���̍폜");
				// �폜�����t���O��ݒ�
				model.setDeleteCompleted(true);
				
				if (!model.isFileDeleteSuccess()) {
					// �t�@�C���폜�Ɏ��s�����ꍇ�́A���O�Ƀ��b�Z�[�W���o��
					log.error(getText("MSG0019"));
				}
			} else {
				// �s���̃A�N�V�����^�C�v
				throw new ApplicationException("�A�N�V�����^�C�v�s���F" + model.getActionType());
			}
			
		} catch (ForbiddenException e) {
			// �Z�L�����e�B�G���[�̏ꍇ�́A403�G���[����ʂɕ\��
			return FORBIDDEN_ERROR;
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
	 * �₢���킹�t�@�C���폜�������s���܂��B
	 * 
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="inquiryFileDelete",
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="inquiryEntryUpdateInit?" +
									"&kokyakuId=${kokyakuId}" +
									"&toiawaseNo=${toiawaseNo}" +
									"&toiawaseUpdDt=${toiawaseUpdDt}" +
									"&dispKbn=${dispKbn}" +
									"&condition.toiawaseNo=${condition.toiawaseNo}" +
									"&condition.uketsukebiFrom=${condition.uketsukebiFrom}" +
									"&condition.uketsukebiTo=${condition.uketsukebiTo}" +
									"&encodedJusho1=${encodedJusho1}" +
									"&encodedJusho2=${encodedJusho2}" +
									"&encodedJusho3=${encodedJusho3}" +
									"&encodedJusho4=${encodedJusho4}" +
									"&encodedJusho5=${encodedJusho5}" +
									"&encodedRoomNo=${encodedRoomNo}" +
									"&encodedKanaNm1=${encodedKanaNm1}" +
									"&encodedKanaNm2=${encodedKanaNm2}" +
									"&encodedKanjiNm1=${encodedKanjiNm1}" +
									"&encodedKanjiNm2=${encodedKanjiNm2}" +
									"&condition.sortOrder=${condition.sortOrder}" +
									"&condition.jokyo=${condition.jokyo}" +
									"&condition.serviceKbn=${condition.serviceKbn}" +
									"&condition.telNo=${condition.telNo}" +
									"&condition.kubun1=${condition.kubun1}" +
									"&condition.kubun2=${condition.kubun2}" +
									"&condition.kubun3=${condition.kubun3}" +
									"&condition.kubun4=${condition.kubun4}" +
									"&condition.offset=${condition.offset}"
					)
			}
	)
	public String fileDelete() throws Exception {
		// �p�����[�^�`�F�b�N
		if (model.getFileIndex() == null) {
			// �t�@�C���C���f�b�N�X���Ȃ��ꍇ�A�G���[
			throw new ApplicationException("�t�@�C���C���f�b�N�X�s���F�p�����[�^�̃t�@�C���C���f�b�N�X");
		}
		if (StringUtils.isBlank(model.getUploadFileNm())) {
			// �A�b�v���[�h�t�@�C�������Ȃ��ꍇ�A�G���[
			throw new ApplicationException("�A�b�v���[�h�t�@�C�����s���F�p�����[�^�̃A�b�v���[�h�t�@�C����");
		}
		
		// �₢���킹�t�@�C���폜����
		service.deleteToiawaseFileInfo(model);
		
		if (!model.isFileDeleteSuccess()) {
			// �t�@�C���폜�Ɏ��s�����ꍇ�́A���O�Ƀ��b�Z�[�W���o��
			log.error(getText("MSG0019"));
		}
		
		addActionMessage("MSG0001", "�t�@�C���̍폜");

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
	private TB023InquiryEntryModel executeEncode(TB023InquiryEntryModel model) {

		// �₢���킹�����p
		RC031ToiawaseSearchCondition condition = model.getCondition();
		if (condition == null) {
			condition = new RC031ToiawaseSearchCondition();
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
		// �����ԍ�
		model.setEncodedRoomNo(model.encode(condition.getRoomNo()));
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
	 * �₢���킹�����������č쐬���܂��B
	 * �����{��̍��ڂ� Action�N���X�̃��_�C���N�g���ɐݒ�o���Ȃ��ׁB
	 *
	 * @param model ��ʃ��f��
	 * @return ���������N���X
	 */
	private RC031ToiawaseSearchCondition remakeCondition(TB023InquiryEntryModel model) {
		RC031ToiawaseSearchCondition condition = model.getCondition();
		if (condition == null) {
			condition = new RC031ToiawaseSearchCondition();
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
		// �����ԍ�
		if (StringUtils.isNotBlank(model.getEncodedRoomNo())) {
			condition.setRoomNo(model.getEncodedRoomNo());
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
	public TB023InquiryEntryModel getModel() {
		return model;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		
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