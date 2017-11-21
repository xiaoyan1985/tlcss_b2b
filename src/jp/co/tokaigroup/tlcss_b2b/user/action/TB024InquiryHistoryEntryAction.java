package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.util.Calendar;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpTToiawaseRireki;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.exception.ForbiddenException;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB024InquiryHistoryEntryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB024InquiryHistoryEntryService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �₢���킹����o�^�A�N�V�����N���X�B
 *
 * @author v145527
 * @version 1.0 2015/8/28
 * @version 1.1 2016/07/13 H.Yamamura �₢���킹������ʂ̖₢���킹�敪�P�`�S�A�����ԍ��̒l�������܂��悤�ɏC��
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb024_inquiry_history_entry.jsp"),
	@Result(name=INPUT, location="tb024_inquiry_history_entry.jsp")
 })

public class TB024InquiryHistoryEntryAction extends TLCSSB2BBaseActionSupport implements
ModelDriven<TB024InquiryHistoryEntryModel>, ServiceValidatable{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB024InquiryHistoryEntryModel model = new TB024InquiryHistoryEntryModel();

	/** �T�[�r�X */
	@Autowired
	private TB024InquiryHistoryEntryService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="inquiryHistoryEntryInit")
	
	public String init() throws Exception {
		
		try {
			// �����\���p�����[�^�`�F�b�N
			isValidParameter();
			
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
		
		

		
		model.setGamenKbn(Constants.GAMEN_KBN_INQUIRY_HISTORY_ENTRY);

		// �����l�̐ݒ�
		RcpTToiawaseRireki toiawaseRireki = new RcpTToiawaseRireki();
		
		// ���[�U���擾
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// ��t�����V�X�e�����t
		toiawaseRireki.setUketsukeYmd(DateUtil.toSqlTimestamp(DateUtils.truncate(DateUtil.getSysDateTime(), Calendar.DATE)));
		
		// �S���Җ������O�C���Җ�
		toiawaseRireki.setTantoshaNm(userContext.getUserName());

		model.setToiawaseRirekiInfo(toiawaseRireki);
		
		// �A�N�V�����^�C�v�̐ݒ�
		model.setActionType(Constants.ACTION_TYPE_INSERT);

		// ���O�C�����[�U���̎擾
		model.TLCSSB2BUserContext(userContext);

		return SUCCESS;
	}
	
	/**
	 * �p�����[�^�`�F�b�N���s���܂��B
	 */
	private void isValidParameter() {
		// �����\���p�����[�^�`�F�b�N
		if (StringUtils.isBlank(model.getToiawaseNo())) {
			// �p�����[�^�̖₢���킹�m�n���擾�ł��Ȃ��ꍇ�G���[
			throw new ApplicationException("�₢���킹�m�n�s���F�p�����[�^�̖₢���킹�m�n" );
		}
		if (model.getToiawaseUpdDt() == null) {
			// �p�����[�^�̖₢���킹�X�V����NULL�̏ꍇ�G���[
			throw new ApplicationException("�₢���킹�X�V���s���F�p�����[�^�̖₢���킹�X�V��" );
		}
	}

	/**
	 * �X�V�����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(value="inquiryHistoryEntryUpdateInit")
	public String updateInit() throws Exception {
		
		try {
			// �����\���p�����[�^�`�F�b�N
			isValidParameter();
			
			// �X�V�����\������
			model = service.getInitInfoForUpdate(model);

			if (model.getToiawaseInfo().isRegistKbnToExternalCooperationData()) {
				// �₢���킹���̓o�^�敪���u�O���A�g�f�[�^�v�̏ꍇ�́A���b�Z�[�W��\��
				addActionError("MSG0038", "�O���A�g�f�[�^");
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

		// �A�N�V�����^�C�v�̐ݒ�
		model.setActionType(Constants.ACTION_TYPE_UPDATE);
		// ��ʋ敪�̐ݒ�
		model.setGamenKbn(Constants.GAMEN_KBN_INQUIRY_HISTORY_ENTRY);

		// ���O�C�����[�U���̎擾
		model.TLCSSB2BUserContext((TLCSSB2BUserContext) getUserContext());
		
		return SUCCESS;
	}

	/**
	 * �₢���킹�������̓o�^�A�X�V�A�폜���s���܂��B
	 *
	 * @return �₢���킹����o�^��ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="inquiryHistoryEntryUpdate",
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="inquiryHistoryEntryUpdateInit?" +
								"toiawaseNo=${toiawaseNo}" +
								"&toiawaseRirekiNo=${toiawaseRirekiNo}" +
								"&toiawaseUpdDt=${toiawaseUpdDt}" +
								"&actionType=${actionType}" +
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
								"&condition.offset=${condition.offset}" +
								"&condition.toiawaseRirekiNo=${condition.toiawaseRirekiNo}"
					),
					@Result(name=DELETE, location="tb024_inquiry_history_entry.jsp")
			}
	)
	public String update() throws Exception {
		
		try{
			// �A�N�V�����^�C�v�ɂ���āA�����؂�ւ�
			if (model.isInsert()) {
				// �o�^����
				service.insertToiawaseRirekiInfo(model);
				// �A�N�V�����^�C�v�̐ݒ�
				model.setActionType(Constants.ACTION_TYPE_UPDATE);
			
				// �������b�Z�[�W
				addActionMessage("MSG0001", "�₢���킹�������̓o�^");
			} else if (model.isUpdate()) {
				// �X�V����
				service.updateToiawaseRirekiInfo(model);
	
				// �������b�Z�[�W
				addActionMessage("MSG0001", "�₢���킹�������̍X�V");
			} else if (model.isDelete()) {
				// �폜����
				service.deleteToiawaseRirekiInfo(model);
	
				// �������b�Z�[�W
				addActionMessage("MSG0001", "�₢���킹�������̍폜");
				
				// �폜�����t���O��ݒ�
				model.setDeleteCompleted(true);
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
	 * ���������̃G���R�[�h���s���܂��B
	 *
	 * @param model ��ʃ��f��
	 * @return ��ʃ��f��
	 */
	private TB024InquiryHistoryEntryModel executeEncode(TB024InquiryHistoryEntryModel model) {

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
	private RC031ToiawaseSearchCondition remakeCondition(TB024InquiryHistoryEntryModel model) {
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
	public TB024InquiryHistoryEntryModel getModel() {
		return model;
	}

	/**
	 * ��ʃ��f����p�ӂ��܂��B
	 *
	 * @see jp.co.tokaigroup.si.fw.action.ServiceValidatable#prepareModel()
	 */
	public void prepareModel() {
		if (model.isDelete()) {
			model.setActionType(Constants.ACTION_TYPE_UPDATE);
		}

		if (!model.isInitError()) {
			// �������G���[�łȂ���΁A���{�i�������̏ꍇ�Ɏ��{����ƁA�V�X�e���G���[�ɂȂ邽�߁j
			model = service.getInitInfo(model);
		}

		// ���O�C�����[�U���̎擾
		model.TLCSSB2BUserContext((TLCSSB2BUserContext) getUserContext());
	}

}
