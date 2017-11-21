package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB022InquiryDetailModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB022InquiryDetailService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �₢���킹�ڍ׃A�N�V�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/05/27
 * @version 1.1 2015/10/23 C.Kobayashi ���ǖ��ǋ@�\�ǉ��Ή�
 * @version 1.2 2016/07/13 H.Yamamura �₢���킹������ʂ̖₢���킹�敪�P�`�S�A�����ԍ��������܂��悤�ɏC��
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb022_inquiry_detail.jsp"),
	@Result(name=INPUT, location="tb022_inquiry_detail.jsp")
})
public class TB022InquiryDetailAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB022InquiryDetailModel> , ServiceValidatable{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB022InquiryDetailModel model = new TB022InquiryDetailModel();

	/** �T�[�r�X */
	@Autowired
	private TB022InquiryDetailService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("inquiryDetailInit")
	public String init() throws Exception {

		// �����\������
		model = service.getInitInfo(model);
		if (model == null) {
			// �A�N�Z�X�s�̏ꍇ�́A403�G���[����ʂɕ\��
			return FORBIDDEN_ERROR;
		}

		//�ڋq��{���̌l/�@�l����ߓ��̕\��
		model.setGamenKbn(Constants.GAMEN_KBN_INQUIRY_DETAIL);
		
		// �₢���킹������ʗp�p�����[�^��ݒ�
		model.setCondition(remakeCondition(model));

		return SUCCESS;
	}
	
	/**
	 * �X�V�������s���܂��B
	 * 
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action(
			value="inquiryDetailUpdate",
			interceptorRefs = {
					@InterceptorRef("tokenSessionCheck")
			},
			results = {
					@Result(name=SUCCESS, type="redirectAction",
							location="inquiryDetailInit?" +
									"&toiawaseNo=${toiawaseNo}" +
									"&dispKbn=${dispKbn}" +
									"&condition.toiawaseNo=${condition.toiawaseNo}" +
									"&condition.serviceKbn=${condition.serviceKbn}" +
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
									"&condition.telNo=${condition.telNo}" +
									"&condition.uketsukebiFrom=${condition.uketsukebiFrom}" +
									"&condition.uketsukebiTo=${condition.uketsukebiTo}" +
									"&condition.jokyo=${condition.jokyo}" +
									"&condition.browseStatus=${condition.browseStatus}" +
									"&condition.kubun1=${condition.kubun1}" +
									"&condition.kubun2=${condition.kubun2}" +
									"&condition.kubun3=${condition.kubun3}" +
									"&condition.kubun4=${condition.kubun4}" +
									"&condition.sortOrder=${condition.sortOrder}" +
									"&condition.offset=${condition.offset}"
					)
			}
	)
	public String update() {

		// �X�V����
		service.updateInquiryHistoryDetailInfo(model);

		// �������b�Z�[�W
		addActionMessage("MSG0001", "���ǁ^���Ǐ��̍X�V");

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
	private TB022InquiryDetailModel executeEncode(TB022InquiryDetailModel model) {

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
	private RC031ToiawaseSearchCondition remakeCondition(TB022InquiryDetailModel model) {
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
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB022InquiryDetailModel getModel() {
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
