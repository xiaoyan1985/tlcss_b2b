package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.tokaigroup.reception.common.CsvHeaderMaker;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTIraiDaoImpl;
import jp.co.tokaigroup.reception.dto.RC041IraiSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchCondition;
import jp.co.tokaigroup.reception.irai.model.RC041IraiSearchModel;
import jp.co.tokaigroup.si.fw.action.CSVDownloadAction;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB031RequestSearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB031RequestSearchService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;


/**
 * �˗������_�E�����[�h�A�N�V�����N���X�B
 *
 * @author H.Hirai
 * @version 1.0 2015/11/12
 * 
 */

@Controller
@Scope("prototype")
@Results({
	@Result(name= SUCCESS, location="tb031_request_search.jsp")
})
public class TB031RequestSearchDownloadAction extends CSVDownloadAction
	implements ModelDriven<TB031RequestSearchModel>{

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB031RequestSearchModel model = new TB031RequestSearchModel();

	/** �T�[�r�X */
	@Autowired
	private TB031RequestSearchService service;

	/**
	 * CSV���_�E�����[�h���܂��B
	 * 
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("requestSearchDownload")
	public String download() throws Exception {
		
		try{
			// ���������擾
			RC041IraiSearchCondition condition = model.getCondition();
			
			// �V�X�e���}�X�^�萔Map����ő匟���\�������擾
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
			
			// �ő匟���\����
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_BSB_IRAI_CSV_DOWNLOAD_TO_MAX);

			// �I�t�Z�b�g��1�ɃZ�b�g
			condition.setOffset(1);

			// 1�y�[�W�̍ő匏����ݒ�
			condition.setLimit(searchMax);
			condition.setMaxCount(searchMax);
			condition.setDisplayToMax(false);

			// ���������ݒ�
			if (userContext.isRealEstate()) {
				// �Z�b�V�����̌������Ǘ���Ђ̏ꍇ
				if (userContext.isKokyakuIdSelected()) {
					// �ڋq�I���ς̏ꍇ
					// ������ڋq�h�c�ɂ́A�Z�b�V�����̌ڋq�h�c��ݒ�
					condition.setSeikyusakiKokyakuId(userContext.getKokyakuId());
					// �e�ڋq�h�c�ɂ́ANULL��ݒ�
					condition.setParentKokyakuId(null);
				} else {
					// ����ȊO�̏ꍇ�́ANULL��ݒ�
					condition.setSeikyusakiKokyakuId(null);
					// �e�ڋq�h�c�ɂ́A�Z�b�V�����̌ڋq�h�c��ݒ�
					condition.setParentKokyakuId(userContext.getKokyakuId());
				}
				condition.setIraiKokaiFlg(RcpTIrai.KOKAI_FLG_KOUKAIZUMI);
				condition.setSearchFlg(RC041IraiSearchCondition.SEARCH_FLG_GAIBU);
			} else {
				// �Z�b�V�����̌������Ǘ���ЈȊO�̏ꍇ�́A�����ݒ肵�Ȃ�
				condition.setSeikyusakiKokyakuId(null);
				condition.setParentKokyakuId(null);
				condition.setSearchFlg(null);
			}
			// ��Ђh�c��ݒ�
			if (userContext.isOutsourcerSv() || userContext.isOutsourcerOp()) {
				condition.setOutsourcerKaishaId(userContext.getKaishaId());
			} else {
				condition.setOutsourcerKaishaId(null);
			}
			condition.setIraiGyosha(userContext.getGyoshaCd());
			condition.setKanrenJoho(RcpTIraiDaoImpl.SEARCH_KANREN_JOHO_TRUE);
			condition.setCsvFlg(RC041IraiSearchModel.CSV_OUT);

			model.setCondition(condition);

			// �����������s
			model = service.executeSearch(model);

			// �_�E�����[�h����
			super.download(model.getResultList());

		} catch (PagerMaxCountException e) {
			// ��������̏ꍇ
			addActionError("MSG0006");
			return MESSAGE_AND_CLOSE;

		} catch (Exception e) {
			log.warn("CSV�_�E�����[�h���s�B", e);
			addActionError("MSG0040", "CSV�_�E�����[�h");
			return MESSAGE_AND_CLOSE;
		}
		return DOWNLOAD;
	}

	/**
	 * ���X�g�f�[�^���擾���܂��B
	 *
	 * @return ���X�g�f�[�^
	 */
	protected <T> List<String> getLineData(T line) {

		RC041IraiSearchDto dto = (RC041IraiSearchDto) line;

		List<String>lineList = new ArrayList<String>();

		// �˗��e�[�u������o�́i�P�^�Q�j
		lineList.add(dto.getToiawaseNo());

		// �₢���킹�e�[�u������o��
		lineList.add(DateUtil.formatTimestamp(dto.getUketsukeYmd(), "yyyy/MM/dd"));
		lineList.add(DateUtil.hhmmPlusColon(dto.getUketsukeJikan()));
		if (StringUtils.isNotBlank(dto.getUketsukeshaNm())) {
			lineList.add(dto.getUketsukeshaNm());
		} else {
			lineList.add(concatCodeValue(dto.getUketsukeshaId(), dto.getUketsukeshaDisplayNm()));
		}

		lineList.add(dto.getKokyakuId());

		// �ڋq�}�X�^����o��
		if (StringUtils.isNotBlank(dto.getKokyakuKbn())) {
			lineList.add(concatCodeValue(dto.getKokyakuKbn(), dto.getKokyakuKbnNm()));
		} else {
			lineList.add("");
		}
		if (StringUtils.isNotBlank(dto.getKokyakuShubetsu())) {
			lineList.add(concatCodeValue(dto.getKokyakuShubetsu(), dto.getKokyakuShubetsuNm()));
		} else {
			lineList.add("");
		}
		lineList.add(dto.getKanjiNm1());
		lineList.add(dto.getKanjiNm2());
		lineList.add(dto.getJusho1());
		lineList.add(dto.getJusho2());
		lineList.add(dto.getJusho3());

		// �˗��e�[�u������o�́i�Q�^�Q�j
		lineList.add(dto.getToiawaseRirekiNo());
		if (StringUtils.isNotBlank(dto.getTantoshaNm())) {
			lineList.add(dto.getTantoshaNm());
		} else {
			lineList.add(concatCodeValue(dto.getTantoshaId(), dto.getTantoshaIdNm()));
		}
		if (StringUtils.isNotBlank(dto.getIraiGyoshaCd())) {
			lineList.add(concatCodeValue(dto.getIraiGyoshaCd(), dto.getIraiGyoshaNm()));
		} else {
			lineList.add("");
		}
		lineList.add(dto.getIraiNaiyo());
		lineList.add(DateUtil.formatTimestamp(dto.getHomonKiboYmd(), "yyyy/MM/dd"));
		if (StringUtils.isNotBlank(dto.getHomonKiboJikanKbn())) {
			lineList.add(concatCodeValue(dto.getHomonKiboJikanKbn(), dto.getHomonKiboJikanKbnNm()));
		} else {
			lineList.add("");
		}
		lineList.add(dto.getHomonKiboBiko());
		lineList.add(DateUtil.formatTimestamp(dto.getGyoshaKaitoYmd(), "yyyy/MM/dd"));
		if (StringUtils.isNotBlank(dto.getGyoshaKaitoJikanKbn())) {
			lineList.add(concatCodeValue(dto.getGyoshaKaitoJikanKbn(), dto.getGyoshaKaitoJikanKbnNm()));
		} else {
			lineList.add("");
		}
		lineList.add(dto.getGyoshaKaitoBiko());
		lineList.add(concatCodeValue(dto.getIraiKokaiFlg(), dto.getIraiKokaiFlgNm()));
		if (StringUtils.isNotBlank(dto.getCreNm())) {
			lineList.add(concatCodeValue(dto.getCreId(), dto.getCreNm()));
		} else {
			lineList.add(concatCodeValue(dto.getCreId(), dto.getCreDisplayNm()));
		}
		lineList.add(DateUtil.formatTimestamp(dto.getCreDt()));
		if (StringUtils.isNotBlank(dto.getLastUpdNm())) {
			lineList.add(concatCodeValue(dto.getLastUpdId(), dto.getLastUpdNm()));
		} else {
			lineList.add(concatCodeValue(dto.getLastUpdId(), dto.getLastUpdDisplayNm()));
		}
		lineList.add(DateUtil.formatTimestamp(dto.getUpdDt()));
		lineList.add(concatCodeValue(dto.getCreKaishaId(), dto.getCreKaishaDisplayNm()));
		lineList.add(concatCodeValue(dto.getUpdKaishaId(), dto.getUpdKaishaDisplayNm()));
		if (StringUtils.isNotBlank(dto.getLastPrintNm())) {
			lineList.add(concatCodeValue(dto.getLastPrintId(), dto.getLastPrintNm()));
		} else {
			lineList.add(concatCodeValue(dto.getLastPrintId(), dto.getLastPrintDisplayNm()));
		}

		// ��Ə󋵃e�[�u������o��
		if (StringUtils.isNotBlank(dto.getSagyoKanryoFlg())) {
			lineList.add(concatCodeValue(dto.getSagyoKanryoFlg(), dto.getSagyoKanryoFlgNm()));
		} else {
			lineList.add("");
		}
		lineList.add(DateUtil.formatTimestamp(dto.getSagyoKanryoYmd(), "yyyy/MM/dd"));
		lineList.add(DateUtil.hhmmPlusColon(dto.getSagyoKanryoJikan()));
		lineList.add(dto.getJokyo());
		lineList.add(dto.getCause());
		lineList.add(dto.getJisshiNaiyo());
		lineList.add(concatCodeValue(dto.getSagyoJokyoKokaiFlg(), dto.getSagyoJokyoKokaiFlgNm()));
		lineList.add(concatCodeValue(dto.getHokokushoKokaiFlg(), dto.getHokokushoKokaiFlgNm()));
		if (StringUtils.isNotBlank(dto.getCreNmSagyoJokyo())) {
			lineList.add(concatCodeValue(dto.getCreIdSagyoJokyo(), dto.getCreNmSagyoJokyo()));
		} else {
			lineList.add(concatCodeValue(dto.getCreIdSagyoJokyo(), dto.getCreDisplayNmSagyoJokyo()));
		}
		lineList.add(DateUtil.formatTimestamp(dto.getCreDtSagyoJokyo()));
		if (StringUtils.isNotBlank(dto.getLastUpdNmSagyoJokyo())) {
			lineList.add(concatCodeValue(dto.getLastUpdIdSagyoJokyo(), dto.getLastUpdNmSagyoJokyo()));
		} else {
			lineList.add(concatCodeValue(dto.getLastUpdIdSagyoJokyo(), dto.getLastUpdDisplayNmSagyoJokyo()));
		}
		lineList.add(DateUtil.formatTimestamp(dto.getUpdDtSagyoJokyo()));

		return lineList;
	}

	/**
	 * CSV�t�@�C���I�u�W�F�N�g�擾
	 *
	 * @return �t�@�C���I�u�W�F�N�g(�t�@�C�����ݒ�)
	 */
	protected String getCsvFileNm(){
		// �t�@�C�����擾
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date1 = new Date();
		return "request_csv" + sdf1.format(date1) + ".csv";
	}

	/**
	 * CSV�^�C�g���擾
	 *
	 * @return CSV�^�C�g���s
	 */
	protected String[] getTitle() {
		try {
			return CsvHeaderMaker.makeIraiCsvHeader();
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 */
	public TB031RequestSearchModel getModel() {
		return model;
	}

	/**
	 * �R�[�h�ƒl�𔼊p�R�����ŘA�������l��ԋp���܂��B
	 *
	 * @param code �R�[�h
	 * @param value �l
	 * @return �R�[�h�ƒl�𔼊p�R�����ŘA�������l
	 */
	private String concatCodeValue(String code, String value) {
		if (StringUtils.isBlank(code)) {
			return "";
		}

		return code + ":" + StringUtils.defaultString(value);
	}

}
