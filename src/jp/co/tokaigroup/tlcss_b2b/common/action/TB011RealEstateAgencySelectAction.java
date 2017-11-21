package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.si.fw.action.ServiceValidatable;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB011RealEstateAgencySelectCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB011RealEstateAgencySelectModel;
import jp.co.tokaigroup.tlcss_b2b.common.service.TB011RealEstateAgencySelectService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * �s���Y��Ǘ���БI���A�N�V�����N���X�B
 *
 * @author v140546
 * @version 1.0 2014/06/04
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb011_real_estate_agency_select.jsp")
})
public class TB011RealEstateAgencySelectAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB011RealEstateAgencySelectModel> , ServiceValidatable{

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB011RealEstateAgencySelectModel model = new TB011RealEstateAgencySelectModel();

	/** �T�[�r�X */
	@Autowired
	private TB011RealEstateAgencySelectService service;

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("realEstateAgencyInit")
	public String init() throws Exception {
		//�����\�����A�R���f�V�����������l�ɖ߂�
		model.setCondition(new TB011RealEstateAgencySelectCondition());

		// �J�ڌ������[�U�}�X�^�o�^��ʂ́u�����FTOKAI�Ǘ��ҁETOKAI��ʁv�̏ꍇ
		if (StringUtils.isNotBlank(model.getKokyakuListNmResultNm())
				&& "selectTokaiKokyakuInfo".equals(model.getKokyakuListNmResultNm())) {
			// TOKAI�t���O��true�ɐݒ�
			model.getCondition().setTokai(true);
		}

		return SUCCESS;
	}

	/**
	 * �����������s���܂��B
	 *
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("realEstateAgencySearch")
	public String search() throws Exception {

			TB011RealEstateAgencySelectCondition condition = model.getCondition();

			// �V�X�e���}�X�^�萔Map����ő匟���\�����A�ő�\���������擾
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// �ő匟���\�����擾
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_B2B_REAL_ESTATE_MAX_COUNT);

			condition.setMaxCount(searchMax);

			model.setCondition(condition);

		try {
			// �����������s
			model = service.executeSearch(model);

		} catch (PagerMaxCountException e) {
			// ��������̏ꍇ
			addActionMessage("MSG0005", String.valueOf(model.getCondition().getMaxCount()));
		}

		// �������ʂ̔���
		if (model.getResultList() == null || model.getResultList().isEmpty()) {
			// �������ʂ�0���̏ꍇ
			addActionMessage("MSG0002");
		} else {
			// �������ʂ��擾�ł����ꍇ
			if (model.getCondition().isCompleted()) {
				addActionMessage("MSG0004", String.valueOf(model.getResultList().size()));
			}else{
				// ��������𒴂����ꍇ
				addActionMessage("MSG0006");
			}
		}

		return SUCCESS;
	}


	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB011RealEstateAgencySelectModel getModel() {
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
