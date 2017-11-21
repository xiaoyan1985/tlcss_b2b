package jp.co.tokaigroup.tlcss_b2b.user.logic;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMServiceDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMService;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.print.logic.BaseListCreatorLogic;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.tlcss_b2b.dto.TB044ReceptionListPrintDto;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * ��t�ꗗ������W�b�N���s�N���X�B
 *
 * @author v140546
 * @version 1.0 2014/07/09
 * @version 1.1 2016/06/15 H.Hirai ���ו�������̏����Ɂu�r���Ǘ��v��ǉ�
 * @version 1.2 2016/10/25 H.Yamamura �T�[�r�X��ʃ��X�g�A�T�[�r�X���NULL�擾�t���O��ǉ�
 *
 */
@Service
public class TB044ReceptionListPrintLogicImpl extends BaseListCreatorLogic
	implements TB044ReceptionListPrintLogic {

	/** �₢���킹DAO */
	@Autowired
	private RcpTToiawaseDao rcpTToiawaseDao;

	/** �ڋq�}�X�^DAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** ���Z�v�V�������ʃR�[�h�}�X�^DAO�i�O���T�C�g�p�j */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** �T�[�r�X�lDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/**
	 * ��t�ꗗ�o�̓f�[�^���쐬���܂��B
	 *
	 * @param targetDtFrom �Ώۊ���From
	 * @param targetDtTo �Ώۊ���To
	 * @param seikyusakiKokyakuId ������ڋq�h�c
	 * @param serviceKbn �T�[�r�X�敪
	 * @return  PDF�o�͂���f�[�^���X�g
	 */
	public List<String[]> getReceiptListDataList(Timestamp targetDtFrom, Timestamp targetDtTo, String seikyusakiKokyakuId, String serviceKbn) {

		// �T�[�r�X��ʃ��X�g
		List<String> serviceShubetsuList = new ArrayList<String>();
		// �T�[�r�X��ʁ@NULL�擾�t���O
		boolean serviceShubetsuNullFlg = false;
		if (StringUtils.isNotBlank(serviceKbn)) {
			// ���������̃T�[�r�X���w�肳��Ă���ꍇ
			RcpMService service = serviceDao.selectByPrimaryKey(serviceKbn);

			serviceShubetsuList.add(service.getServiceShubetsu());
			if (RcpMService.SERVICE_KBN_RECEPTION.equals(service.getServiceShubetsu())
				|| RcpMService.SERVICE_KBN_LIFE_SUPPORT24.equals(service.getServiceShubetsu())) {
				// �擾�����T�[�r�X��ʂ����Z�v�V�����@�܂��́@���C�t�T�|�[�g�Q�S�̏ꍇ�A�s���ANULL�������ɒǉ�
				serviceShubetsuList.add(RcpTToiawase.SERVICE_SHUBETSU_FUMEI);
				serviceShubetsuNullFlg = true;
			}
		}
		
		// �o�͑Ώۃf�[�^�擾
		List<TB044ReceptionListPrintDto> resultList = rcpTToiawaseDao.selectMonthlyReport(targetDtFrom, targetDtTo, seikyusakiKokyakuId, serviceKbn, serviceShubetsuList, serviceShubetsuNullFlg);
		// �������O���̏ꍇ�A�G���[��Ԃ��B
		if (resultList == null || resultList.isEmpty()) {
			// �Y���f�[�^���݂Ȃ�
			throw new ValidationException(new ValidationPack().addError("MSG0002"));
		}

		// ������ڋq���̎擾
		RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(seikyusakiKokyakuId);

		// �a���ϊ��pMap�擾
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// ��t�`�ԋ敪Map
		Map<String, RcpMComCd> uketsukeFormKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);

		for (int i = 0; i < resultList.size(); i++) {
			TB044ReceptionListPrintDto dto = resultList.get(i);
			if (StringUtils.isNotBlank(dto.getUketsukeKeitaiKbn()) &&
					uketsukeFormKbnMap.containsKey(dto.getUketsukeKeitaiKbn())) {
				// ��t�`��
				dto.setUketsukeKeitai(uketsukeFormKbnMap.get(dto.getUketsukeKeitaiKbn()).getExternalSiteVal());
			}
		}

		// �S�Ẵf�[�^���Z�b�g����
		return (createCsvOutputMeisaiData(targetDtFrom, targetDtTo, kokyaku, resultList));
	}

	/**
	 * PDF�o�͂��s���܂��B
	 *
	 * @param allDataList PDF�o�͂���f�[�^���X�g
	 * @return PDF�o��URL
	 */
	public String outputPdf(List<String[]> allDataList) {
		// CSV�o��
		String csvFileName = super.writeLinesToCsvFile(allDataList, TB044ReceptionListPrintDto.REPORT_NM);
		// PDF�o��
		return super.invokePrprintBySsh(TB044ReceptionListPrintDto.REPORT_NM, csvFileName, true, "");
	}

	/**
	 * CSV�ɏo�͂���f�[�^���쐬���܂��B
	 * ���ו��͗����������A�s�o�͂��܂��B
	 *
	 * @param outDto ���[(�o�͗p)DTO�̃��X�g
	 * @return CSV�ɏo�͂���f�[�^
	 */
	private List<String[]> createCsvOutputMeisaiData(Timestamp targetDtFrom, Timestamp targetDtTo, RcpMKokyaku kokyaku, List<TB044ReceptionListPrintDto> outDtoList) {
		List<String[]> allDataList = new ArrayList<String[]>();

		if (outDtoList != null) {
			for (TB044ReceptionListPrintDto outDto : outDtoList) {
				List<String> lineDataList = new ArrayList<String>();

				// �擪�ɒ��[��`�̖���ݒ�
				lineDataList.add(TB044ReceptionListPrintDto.REPORT_NM);

				// �\����
				lineDataList.add(DateUtil.formatTimestamp(targetDtFrom, "yyyy/MM/dd"));
				lineDataList.add(DateUtil.formatTimestamp(targetDtTo, "yyyy/MM/dd"));
				lineDataList.add(kokyaku.getKanjiNm());

				// ���ו�
				lineDataList.add(DateUtil.formatTimestamp(outDto.getUketsukeYmd(), "MM/dd") + " " + DateUtil.hhmmPlusColon(outDto.getUketsukeJikan()));
				lineDataList.add(outDto.getToiawaseNo());
				// �T�[�r�X��ʂ����Z�v�V���� �܂��� �r���Ǘ��̏ꍇ
				if (RcpMService.SERVICE_SHUBETSU_RECEPTION.equals(outDto.getServiceShubetsu())
						|| RcpMService.SERVICE_SHUBETSU_BUILDING.equals(outDto
								.getServiceShubetsu())) {
					lineDataList.add(outDto.getKanjiNm());
					lineDataList.add(StringUtils.defaultString(outDto.getIraishaRoomNo()));
					lineDataList.add(StringUtils.defaultString(outDto.getIraishaNm()));
				// �T�[�r�X��ʂ����C�t�T�|�[�g�Q�S�̏ꍇ
				} else if (RcpMService.SERVICE_SHUBETSU_LIFE_SUPPORT24.equals(outDto.getServiceShubetsu())) {
					lineDataList.add(StringUtils.defaultString(outDto.getJusho5()));
					lineDataList.add(outDto.getRoomNo());
					lineDataList.add(outDto.getKanjiNm());
				}
				lineDataList.add(outDto.getUketsukeKeitai());
				lineDataList.add(outDto.getToiawaseNaiyoSimple());

				allDataList.add(lineDataList.toArray(new String[0]));
			}
		}
		return allDataList;
	}
}