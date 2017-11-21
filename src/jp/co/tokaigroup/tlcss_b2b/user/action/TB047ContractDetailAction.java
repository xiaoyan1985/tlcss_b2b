package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB047ContractDetailModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB047ContractDetailService;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �ڋq�ڍ׌_��ڍ׏��A�N�V�����N���X�B
 *
 * @author k003858
 * @version 1.0 2015/09/17
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb047_contract_detail.jsp")
 })
public class TB047ContractDetailAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB047ContractDetailModel> {
	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB047ContractDetailModel model = new TB047ContractDetailModel();

	/** �T�[�r�X */
	@Autowired
	private TB047ContractDetailService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return �ڋq�ڍ׌_��ڍ׏���ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("contractDetailInit")
	public String init() throws Exception {
		
		// �����\������
		if (StringUtils.isBlank(model.getKeiyakuKokyakuId())) {
			throw new ApplicationException("�ڋqID�s���F�p�����[�^�̌ڋqID");
		}

		if (model.getKeiyakuNo() == null) {
			throw new ApplicationException("�_��NO�s���F�p�����[�^�̌_��NO");
		}

		if (StringUtils.isNotBlank(model.getKokyakuId()) &&
			model.getKokyakuId().length() < RcpMKokyaku.KOKYAKU_ID_COLUMN_LENGTH) {
			// �ڋq�h�c��MAX�l�ɖ����Ȃ��ꍇ�ɂ́AC+�O0�l�߂��ĕ\��
			model.setKokyakuId("C" + StringUtils.leftPad(model.getKokyakuId(), RcpMKokyaku.KOKYAKU_ID_COLUMN_LENGTH - 1, '0'));
		}
		
		model = service.getInitInfo(model);

		// �������ʂ̔���
		if (model.getKeiyakuInfo() == null) {
			// �������ʂ�0���̏ꍇ
			addActionMessage("MSG0015", "�Y���̌_����");
		}

		// ���[�U�R���e�L�X�g�擾
		model.setContext((TLCSSB2BUserContext) getUserContext());

		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ContractDetailModel ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB047ContractDetailModel getModel() {
		return model;
	}

}