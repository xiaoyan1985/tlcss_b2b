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
 * 受付一覧印刷ロジック実行クラス。
 *
 * @author v140546
 * @version 1.0 2014/07/09
 * @version 1.1 2016/06/15 H.Hirai 明細部印刷時の条件に「ビル管理」を追加
 * @version 1.2 2016/10/25 H.Yamamura サービス種別リスト、サービス種別NULL取得フラグを追加
 *
 */
@Service
public class TB044ReceptionListPrintLogicImpl extends BaseListCreatorLogic
	implements TB044ReceptionListPrintLogic {

	/** 問い合わせDAO */
	@Autowired
	private RcpTToiawaseDao rcpTToiawaseDao;

	/** 顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** サービスＭDAO */
	@Autowired
	private RcpMServiceDao serviceDao;

	/**
	 * 受付一覧出力データを作成します。
	 *
	 * @param targetDtFrom 対象期間From
	 * @param targetDtTo 対象期間To
	 * @param seikyusakiKokyakuId 請求先顧客ＩＤ
	 * @param serviceKbn サービス区分
	 * @return  PDF出力するデータリスト
	 */
	public List<String[]> getReceiptListDataList(Timestamp targetDtFrom, Timestamp targetDtTo, String seikyusakiKokyakuId, String serviceKbn) {

		// サービス種別リスト
		List<String> serviceShubetsuList = new ArrayList<String>();
		// サービス種別　NULL取得フラグ
		boolean serviceShubetsuNullFlg = false;
		if (StringUtils.isNotBlank(serviceKbn)) {
			// 検索条件のサービスが指定されている場合
			RcpMService service = serviceDao.selectByPrimaryKey(serviceKbn);

			serviceShubetsuList.add(service.getServiceShubetsu());
			if (RcpMService.SERVICE_KBN_RECEPTION.equals(service.getServiceShubetsu())
				|| RcpMService.SERVICE_KBN_LIFE_SUPPORT24.equals(service.getServiceShubetsu())) {
				// 取得したサービス種別がリセプション　または　ライフサポート２４の場合、不明、NULLを条件に追加
				serviceShubetsuList.add(RcpTToiawase.SERVICE_SHUBETSU_FUMEI);
				serviceShubetsuNullFlg = true;
			}
		}
		
		// 出力対象データ取得
		List<TB044ReceptionListPrintDto> resultList = rcpTToiawaseDao.selectMonthlyReport(targetDtFrom, targetDtTo, seikyusakiKokyakuId, serviceKbn, serviceShubetsuList, serviceShubetsuNullFlg);
		// 件数が０件の場合、エラーを返す。
		if (resultList == null || resultList.isEmpty()) {
			// 該当データ存在なし
			throw new ValidationException(new ValidationPack().addError("MSG0002"));
		}

		// 請求先顧客情報の取得
		RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(seikyusakiKokyakuId);

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// 受付形態区分Map
		Map<String, RcpMComCd> uketsukeFormKbnMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_TOIAWASE_UKETSUKE_KEITAI);

		for (int i = 0; i < resultList.size(); i++) {
			TB044ReceptionListPrintDto dto = resultList.get(i);
			if (StringUtils.isNotBlank(dto.getUketsukeKeitaiKbn()) &&
					uketsukeFormKbnMap.containsKey(dto.getUketsukeKeitaiKbn())) {
				// 受付形態
				dto.setUketsukeKeitai(uketsukeFormKbnMap.get(dto.getUketsukeKeitaiKbn()).getExternalSiteVal());
			}
		}

		// 全てのデータをセットする
		return (createCsvOutputMeisaiData(targetDtFrom, targetDtTo, kokyaku, resultList));
	}

	/**
	 * PDF出力を行います。
	 *
	 * @param allDataList PDF出力するデータリスト
	 * @return PDF出力URL
	 */
	public String outputPdf(List<String[]> allDataList) {
		// CSV出力
		String csvFileName = super.writeLinesToCsvFile(allDataList, TB044ReceptionListPrintDto.REPORT_NM);
		// PDF出力
		return super.invokePrprintBySsh(TB044ReceptionListPrintDto.REPORT_NM, csvFileName, true, "");
	}

	/**
	 * CSVに出力するデータを作成します。
	 * 明細部は履歴件数分、行出力します。
	 *
	 * @param outDto 帳票(出力用)DTOのリスト
	 * @return CSVに出力するデータ
	 */
	private List<String[]> createCsvOutputMeisaiData(Timestamp targetDtFrom, Timestamp targetDtTo, RcpMKokyaku kokyaku, List<TB044ReceptionListPrintDto> outDtoList) {
		List<String[]> allDataList = new ArrayList<String[]>();

		if (outDtoList != null) {
			for (TB044ReceptionListPrintDto outDto : outDtoList) {
				List<String> lineDataList = new ArrayList<String>();

				// 先頭に帳票定義体名を設定
				lineDataList.add(TB044ReceptionListPrintDto.REPORT_NM);

				// 表紙部
				lineDataList.add(DateUtil.formatTimestamp(targetDtFrom, "yyyy/MM/dd"));
				lineDataList.add(DateUtil.formatTimestamp(targetDtTo, "yyyy/MM/dd"));
				lineDataList.add(kokyaku.getKanjiNm());

				// 明細部
				lineDataList.add(DateUtil.formatTimestamp(outDto.getUketsukeYmd(), "MM/dd") + " " + DateUtil.hhmmPlusColon(outDto.getUketsukeJikan()));
				lineDataList.add(outDto.getToiawaseNo());
				// サービス種別がレセプション または ビル管理の場合
				if (RcpMService.SERVICE_SHUBETSU_RECEPTION.equals(outDto.getServiceShubetsu())
						|| RcpMService.SERVICE_SHUBETSU_BUILDING.equals(outDto
								.getServiceShubetsu())) {
					lineDataList.add(outDto.getKanjiNm());
					lineDataList.add(StringUtils.defaultString(outDto.getIraishaRoomNo()));
					lineDataList.add(StringUtils.defaultString(outDto.getIraishaNm()));
				// サービス種別がライフサポート２４の場合
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