package jp.co.tokaigroup.tlcss_b2b.master.service;

import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTInformationDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB104InformationEntryModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * お知らせ登録サービス実装クラス。
 *
 * @author v138130
 * @version 4.0 2014/06/20
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB104InformationEntryServiceImpl extends TLCSSB2BBaseService
		implements TB104InformationEntryService {

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** お知らせテーブルDAO */
	@Autowired
	private TbTInformationDao infoDao;

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** リセプション業者マスタDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/** 会社マスタDAO */
	@Autowired
	private TbMKaishaDao kaishaDao;
	
	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/**
	 * 初期表示処理を行います。
	 *
	 * @param model お知らせ登録画面モデル
	 * @return お知らせ登録画面モデル
	 */
	public TB104InformationEntryModel getInitInfo(TB104InformationEntryModel model) {
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET);

		// 権限リストの取得
		List<RcpMComCd> targetList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_NOTICE_TARGET);

		model.setTargetList(targetList);

		return model;
	}

	/**
	 * お知らせ情報取得処理を行います。
	 *
	 * @param model お知らせ登録画面モデル
	 * @return お知らせ登録画面モデル
	 */
	public TB104InformationEntryModel getUpdateInitInfo(TB104InformationEntryModel model) {
		// 初期表示処理呼び出し
		model = this.getInitInfo(model);

		// お知らせ情報の取得
		TbTInformation info = infoDao.selectByPrimaryKey(model.getSeqNo());
		if (info == null) {
			// お知らせ情報が存在しない場合は、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// 名称取得処理
		if (StringUtils.isNotBlank(info.getKokyakuId())) {
			// 顧客IDがnull以外の場合、顧客情報を取得
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(info.getKokyakuId());
			if (kokyaku == null) {
				throw new ApplicationException("顧客マスタ情報が存在しません。");
			}
			info.setKokyakuNm(kokyaku.getKanjiNm1() + ((StringUtils.isNotBlank(kokyaku.getKanjiNm2()) ? (" " + kokyaku.getKanjiNm2()) : "" )));

		} else if (StringUtils.isNotBlank(info.getGyoshaCd())) {
			// 業者コードがnull以外の場合、業者情報を取得
			RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(info.getGyoshaCd());
			if (gyosha == null) {
				throw new ApplicationException("業者マスタ情報が存在しません。");
			}
			info.setGyoshaNm(gyosha.getGyoshaNm());

		} else if (StringUtils.isNotBlank(info.getKaishaId())) {
			// 会社ＩＤがnull以外の場合、会社情報を取得
			TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(info.getKaishaId());
			if (kaisha == null) {
				throw new ApplicationException("会社マスタ情報が存在しません。");
			}
			info.setKaishaNm(kaisha.getKaishaNm());
		}
		model.setInfo(info);

		return model;
	}

	/**
	 * お知らせ情報登録処理を行います。
	 *
	 * @param model お知らせ登録画面モデル
	 */
	public TB104InformationEntryModel insertInfo(TB104InformationEntryModel model) {
		// 登録チェック処理 ※今回は不要

		TbTInformation info = model.getInfo();
		// 連番取得処理
		info.setSeqNo(infoDao.nextVal());

		// お知らせ情報登録
		infoDao.insert(info);

		// アクセスログへの登録処理
		tbAccesslogDao.insert(TB104InformationEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_INSERT, createKensakuJoken(model));

		model.setInfo(info);

		return model;
	}

	/**
	 * お知らせ情報更新処理を行います。
	 *
	 * @param model お知らせ登録画面モデル
	 */
	public void updateInfo(TB104InformationEntryModel model) {
		// 登録チェック処理 ※今回は不要

		TbTInformation info = model.getInfo();

		// お知らせ情報更新
		int result = infoDao.update(info);

		if (result == 0) {
			// 更新件数0件の場合は、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// アクセスログへの登録処理
		tbAccesslogDao.insert(TB104InformationEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_UPDATE, createKensakuJoken(model));

	}

	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 * @return アクセスログに登録する検索条件
	 */
	private String createKensakuJoken(TB104InformationEntryModel model) {
		NullExclusionToStringBuilder entryContents =
			new NullExclusionToStringBuilder(
				model.getInfo(),
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// 除外する項目
		entryContents.setExcludeFieldNames(Constants.EXCLUDE_FIELD_NAMES);

		return entryContents.toString();
	}

}
