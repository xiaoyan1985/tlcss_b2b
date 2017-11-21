package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.tokaigroup.reception.common.CsvHeaderMaker;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDaoImpl;
import jp.co.tokaigroup.reception.dto.RC031ToiawaseSearchDto;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.toiawase.model.RC031ToiawaseSearchCondition;
import jp.co.tokaigroup.si.fw.action.CSVDownloadAction;
import jp.co.tokaigroup.si.fw.db.pager.PagerMaxCountException;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.StringUtil;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB021InquirySearchModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB021InquirySearchService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;


/**
 * �₢���킹�����_�E�����[�h�A�N�V�����N���X�B
 *
 * @author v145527
 * @version 1.0 2015/10/16
 * @version 1.1 2015/11/16 H.Hirai �s��C��
 * @version 1.2 2015/12/24 H.Yamamura CSV�_�E�����[�h�t���O�̒ǉ�
 */

@Controller
@Scope("prototype")
@Results({
	@Result(name= SUCCESS, location="tb021_inquiry_search.jsp")
})
public class TB021InquirySearchDownloadAction extends CSVDownloadAction
	implements ModelDriven<TB021InquirySearchModel>{

	/** ���K�[ */
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB021InquirySearchModel model = new TB021InquirySearchModel();

	/** �T�[�r�X */
	@Autowired
	private TB021InquirySearchService service;

	/**
	 * CSV���_�E�����[�h���܂��B
	 * 
	 * @return ��������
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("inquirySearchDownload")
	public String download() throws Exception {
		
		try{
			// ���������擾
			RC031ToiawaseSearchCondition condition = model.getCondition();
			
			// �V�X�e���}�X�^�萔Map����ő匟���\�������擾
			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
			
			// �ő匟���\����
			int searchMax = userContext.getSystgemContstantAsInt(
					RcpMSystem.RCP_M_SYSTEM_BSB_TOIAWASE_CSV_DOWNLOAD_TO_MAX);

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
				condition.setToiawaseKokaiFlg(RcpTToiawase.TOIAWASE_KOKAI_FLG_KOKAI);
				condition.setSearchFlg(RC031ToiawaseSearchCondition.SEARCH_FLG_GAIBU);
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
			
			//�˗��L���敪�ɋ�l(�S��)�������
			condition.setIraiUmuRdo(RcpTToiawaseDaoImpl.SEARCH_SORT_IRAI_UMU_RDO_ALL);
			// �֘A�����擾���Ȃ��悤�ɂ���
			condition.setKanrenJoho(RcpTToiawaseDaoImpl.SEARCH_KANREN_JOHO_TRUE);
			
			model.setCondition(condition);

			// �����������s
			model = service.executeSearch(model, true);

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
		RC031ToiawaseSearchDto dto = (RC031ToiawaseSearchDto) line;

		List<String> lineList = new ArrayList<String>();

		// �₢���킹��{���e�[�u������o�́i���̈�j
		// �₢���킹�m�n
		lineList.add(dto.getToiawaseNo());
		// ��t��
		if (dto.getUketsukeYmd() != null) {
			lineList.add(DateUtil.formatTimestamp(dto.getUketsukeYmd(), "yyyy/MM/dd"));
		} else {
			lineList.add("");
		}
		// ��t����
		if (StringUtils.isNotBlank(dto.getUketsukeJikan())) {
			lineList.add(DateUtil.hhmmPlusColon(dto.getUketsukeJikan()));
		} else {
			lineList.add("");
		}
		// ��t�҂h�c
		if (StringUtils.isNotBlank(dto.getToiawaseUketsukeshaNm())) {
			lineList.add(dto.getToiawaseUketsukeshaNm());
		} else {
			lineList.add(concatCodeValue(dto.getUketsukeshaId(), dto.getUketsukeshaNm()));
		}
		// ������ڋq���o��
		lineList.add(dto.getSeikyusakiKokyakuId());
		lineList.add(StringUtil.plusSpace(dto.getSeikyusakiKanjiNm1(), dto.getSeikyusakiKanjiNm2()));

		lineList.add(dto.getKokyakuId());

		// �ڋq�}�X�^����o��
		// �ڋq�敪
		lineList.add(concatCodeValue(dto.getKokyakuKbn(), dto.getKokyakuKbnNm()));
		// �ڋq���
		if (StringUtils.isNotBlank(dto.getKokyakuShubetsu())) {
			lineList.add(concatCodeValue(dto.getKokyakuShubetsu(), dto.getKokyakuShubetsuNm()));
		} else {
			lineList.add("");
		}
		// ���������P
		lineList.add(dto.getKanjiNm1());
		// ���������Q
		lineList.add(dto.getKanjiNm2());
		// �Z���P
		lineList.add(dto.getJusho1());
		// �Z���Q
		lineList.add(dto.getJusho2());
		// �Z���R
		lineList.add(dto.getJusho3());

		// �₢���킹��{���e�[�u������o�́i���̓�j
		// �˗��҃t���O
		if (StringUtils.isNotBlank(dto.getIraishaFlg())) {
			lineList.add(concatCodeValue(dto.getIraishaFlg(), dto.getIraishaFlgNm()));
		} else {
			lineList.add("");
		}
		// �˗��ҋ敪
		if (StringUtils.isNotBlank(dto.getIraishaKbn())) {
			lineList.add(concatCodeValue(dto.getIraishaKbn(), dto.getIraishaKbnNm()));
		} else {
			lineList.add("");
		}
		// �˗��Ҏ���
		lineList.add(dto.getIraishaNm());
		// �˗���TEL
		lineList.add(dto.getIraishaTel());
		// �˗��ҕ����ԍ�
		lineList.add(dto.getIraishaRoomNo());
		// �˗��Ґ���
		if (StringUtils.isNotBlank(dto.getIraishaSexKbn())) {
			lineList.add(concatCodeValue(dto.getIraishaSexKbn(), dto.getIraishaSexKbnNm()));
		} else {
			lineList.add("");
		}
		// �˗��҃���
		lineList.add(dto.getIraishaMemo());
		// �₢���킹�敪�P
		if (StringUtils.isNotBlank(dto.getToiawaseKbn1())) {
			lineList.add(concatCodeValue(dto.getToiawaseKbn1(), dto.getToiawaseKbn1Nm()));
		} else {
			lineList.add("");
		}
		// �₢���킹�敪�Q
		if (StringUtils.isNotBlank(dto.getToiawaseKbn2())) {
			lineList.add(concatCodeValue(dto.getToiawaseKbn2(), dto.getToiawaseKbn2Nm()));
		} else {
			lineList.add("");
		}
		// �₢���킹�敪�R
		if (StringUtils.isNotBlank(dto.getToiawaseKbn3())) {
			lineList.add(concatCodeValue(dto.getToiawaseKbn3(), dto.getToiawaseKbn3Nm()));
		} else {
			lineList.add("");
		}
		// �₢���킹�敪�S
		if (StringUtils.isNotBlank(dto.getToiawaseKbn4())) {
			lineList.add(concatCodeValue(dto.getToiawaseKbn4(), dto.getToiawaseKbn4Nm()));
		} else {
			lineList.add("");
		}
		// ��t�`��
		if (StringUtils.isNotBlank(dto.getUketsukeKeitaiKbn())) {
			lineList.add(concatCodeValue(dto.getUketsukeKeitaiKbn(), dto.getUketsukeKeitaiKbnNm()));
		} else {
			lineList.add("");
		}
		// �₢���킹���e�i�ȈՁj
		lineList.add(dto.getToiawaseNaiyoSimple());
		// �₢���킹���e
		lineList.add(dto.getToiawaseNaiyo());
		// �˗��җL���敪
		lineList.add(concatCodeValue(dto.getIraiUmuKbn(), dto.getIraiUmuKbnNm()));

		// �₢���킹�����e�[�u������o��
		// �ŏI�󋵋敪
		if (StringUtils.isNotBlank(dto.getJokyoKbn())) {
			lineList.add(concatCodeValue(dto.getJokyoKbn(), dto.getJokyoKbnNm()));
		} else {
			lineList.add("");
		}
		// �ŏI������t
		if (dto.getUketsukeYmdRireki() != null) {
			lineList.add(DateUtil.formatTimestamp(dto.getUketsukeYmdRireki(), "yyyy/MM/dd"));
		} else {
			lineList.add("");
		}
		// �ŏI��������
		lineList.add(DateUtil.hhmmPlusColon(dto.getUketsukeJikanRireki()));

		// �₢���킹��{���e�[�u������o�́i���̎O�j
		// ���ߔN��
		lineList.add(DateUtil.yyyymmPlusSlash(dto.getShimeYm()));
		// �R�[���������N���A�񍐑Ώۃt���O�A��ʍŏI�X�V�҂h�c�ǉ� 2013/11/27
		lineList.add(DateUtil.yyyymmPlusSlash(dto.getCallSeikyuYm()));
		lineList.add(concatCodeValue(dto.getHoukokuTargetFlg(), dto.getHoukokuTargetFlgNm()));
		if (StringUtils.isNotBlank(dto.getGamenLastUpdId())) {
			lineList.add(concatCodeValue(dto.getGamenLastUpdId(), dto.getGamenLastUpdNm()));
		} else {
			lineList.add("");
		}
		// ��ʍX�V��
		lineList.add(DateUtil.formatTimestamp(dto.getGamenUpdDt()));
		// �₢���킹���J�t���O
		lineList.add(concatCodeValue(dto.getToiawaseKokaiFlg(), dto.getToiawaseKokaiFlgNm()));
		// �񍐏����J�t���O
		lineList.add(concatCodeValue(dto.getHokokushoKokaiFlg(), dto.getHokokushoKokaiFlgNm()));
		// ����o�^�҂h�c 2015/10/16 NULL�ANOTNULL�̏ꍇ�ǉ�
		if (StringUtils.isNotBlank(dto.getCreNm())) {
			lineList.add(concatCodeValue(dto.getCreId(), dto.getCreNm()));
		} else {
			lineList.add(concatCodeValue(dto.getCreId(), dto.getDisplayCreNm()));
		}
		// �o�^��
		lineList.add(DateUtil.formatTimestamp(dto.getCreDt()));
		// �ŏI�X�V�҂h�c 2015/10/16 NULL�ANOTNULL�̏ꍇ�ǉ�
		if (StringUtils.isNotBlank(dto.getLastUpdNm())) {
			lineList.add(concatCodeValue(dto.getLastUpdId(), dto.getLastUpdNm()));
		} else {
			lineList.add(concatCodeValue(dto.getLastUpdId(), dto.getDisplayLastUpdNm()));
		}
		// �X�V��
		lineList.add(DateUtil.formatTimestamp(dto.getUpdDt()));
		// �O���A�g ��ƕ񍐏��t�@�C���� 2015/10/16�ǉ�
		lineList.add(dto.getExtHokokushoFileNm());
		// �o�^��Ђh�c 2015/10/16�ǉ�
		lineList.add(concatCodeValue(dto.getCreKaishaId(), dto.getCreKaishaNm()));
		// �ŏI�X�V��Ђh�c 2015/10/16�ǉ�
		lineList.add(concatCodeValue(dto.getUpdKaishaId(), dto.getUpdKaishaNm()));
		// �o�^�敪 2015/10/16�ǉ�
		lineList.add(concatCodeValue(dto.getRegistKbn(), dto.getRegistKbnNm()));

		return lineList;
	}

	/**
	 * CSV�t�@�C���I�u�W�F�N�g���擾���܂��B
	 * 
	 * @return CSV�t�@�C���I�u�W�F�N�g�i�t�@�C�����ݒ�j
	 */
	protected String getCsvFileNm() {
		// �t�H�[�}�b�g�̐ݒ�
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		// �V�X�e�������̎擾
		Date date = new Date();
		
		return "inquiry_csv" + sdf.format(date) + ".csv";
	}

	/**
	 * CSV�^�C�g�����擾���܂��B
	 * 
	 * @return CSV�^�C�g���s
	 */
	protected String[] getTitle() {
		try {
			return CsvHeaderMaker.makeToiawaseCsvHeader();

		} catch (IOException e) {
			throw new ApplicationException(e);
		}
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

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 * 
	 * @return �₢���킹������ʃ��f��
	 */
	public TB021InquirySearchModel getModel() {
		return model;
	}

}
