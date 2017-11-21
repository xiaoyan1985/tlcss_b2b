package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;


import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;


import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTUploadRirekiDao;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbTUploadRireki;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;

import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;

import jp.co.tokaigroup.tlcss_b2b.user.logic.TB043CustomerUploadLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 管理情報アップロードサービス実装クラス。
 *
 * @author v140546
 * @version 1.0 2014/08/04
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB043CustomerUploadServiceImpl extends TLCSSB2BBaseService
		implements TB043CustomerUploadService {

	/** CSV取込処理実行可能件数（最小） */
	private static final int EXECUTABLE_CSV_TORIKOMI_MIN = 1;

	/** アップロード履歴DAO */
	@Autowired
	private TbTUploadRirekiDao tbTUploadRirekiDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** リセプションアクセスログDAO */
	@Autowired
	private TbTAccesslogDao accesslogDao;

	/** 管理情報アップロードロジック */
	@Autowired
	private TB043CustomerUploadLogic customerUploadLogic;

	/**
	 * 初期表示を行います。
	 *
	 * @param model 管理情報アップロード画面モデル
	 * @return 管理情報アップロード画面モデル
	 */
	public TB043CustomerUploadModel getInitInfo(TB043CustomerUploadModel model)
	throws ValidationException {

		List<TbTUploadRireki> tbTUploadRirekiList = new ArrayList<TbTUploadRireki>();

		// 和名変換用Map取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(
				RcpMComCd.RCP_M_COM_CD_KBN_CSV_UPLOAD,
				RcpMComCd.RCP_M_COM_CD_FLG_JUJU_JOKYO);
		Map<String, Map<String, RcpMComCd>> convertMap =
			comCdDao.convertMapAsKeyToEntity(comKbnMap);

		// CSVアップロードMap
		Map<String, RcpMComCd> CsvUploadMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_KBN_CSV_UPLOAD);
		// 授受状況確認Map
		Map<String, RcpMComCd> JujuJokyoMap =
			convertMap.get(RcpMComCd.RCP_M_COM_CD_FLG_JUJU_JOKYO);

		//アップロード履歴一覧の取得
		for(TbTUploadRireki tbTUploadRireki:tbTUploadRirekiDao.selectByKokyakuId(model.getUserContext().getKokyakuId())) {
			if (StringUtils.isNotBlank(tbTUploadRireki.getShubetsu())) {
				tbTUploadRireki.setShubetsuNm(CsvUploadMap.get(tbTUploadRireki.getShubetsu()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(tbTUploadRireki.getJujuJokyo())) {
				tbTUploadRireki.setKakuninJokyoNm(JujuJokyoMap.get(tbTUploadRireki.getJujuJokyo()).getExternalSiteVal());
			}
			if (StringUtils.isNotBlank(tbTUploadRireki.getRealFileNm())) {
				tbTUploadRireki.setCopyFileNm(new File(tbTUploadRireki.getRealFileNm()).getName());
			}
			tbTUploadRirekiList.add(tbTUploadRireki);
		}

		model.setResultList(tbTUploadRirekiList);

		return model;
	}

	/**
	 * 管理情報アップロードのCSV取込処理を行います。
	 *
	 * @param model 管理情報アップロード画面モデル
	 */
	@Transactional(value="txManager")
	public TB043CustomerUploadModel executeCsvUpload(TB043CustomerUploadModel model) {

		// 初期表示処理
		model = getInitInfo(model);

		// 結果の初期設定
		model.setCsvUploadFlg(TB043CustomerUploadModel.CSV_UPLOAD_FLG_NORMAL);

		if(TB043CustomerUploadModel.FILE_TYPE_BUKKEN.equals(model.getFileType())
				|| TB043CustomerUploadModel.FILE_TYPE_NYUKYOSHA.equals(model.getFileType())) {
			// 件数チェック
			validateExecutableCsvTorikomi(model.getUploadFile());
			// CSV取込処理実行
			model = customerUploadLogic.executeCsvImport(model);

		} else if (TB043CustomerUploadModel.FILE_TYPE_SONOTA.equals(model.getFileType())) {
			// CSV取込処理実行
			customerUploadLogic.insertTbTUploadRirekiAndCopyFile(model);
		}

		return model;
	}

	/**
	 * アップロード履歴の削除処理を行います。
	 *
	 * @param model 管理情報アップロード画面モデル
	 */
	@Transactional(value="txManager")
	public TB043CustomerUploadModel deleteUploadRireki(TB043CustomerUploadModel model) {

		//検索処理
		TbTUploadRireki tbTUploadRireki = tbTUploadRirekiDao.selectByPrimaryKey(model.getDeleteSeqNo());

		if (tbTUploadRireki == null) {
			//該当のアップロード履歴情報がない場合
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}else if (TbTUploadRireki.KAKUNIN_JOKYO_KOKAIZUMI.equals(tbTUploadRireki.getJujuJokyo())) {
			//授受状況が受取済みの場合
			throw new ValidationException(new ValidationPack().addError("MSG0028"));
		}

		//削除処理
		if (tbTUploadRirekiDao.deleteBy(model.getDeleteSeqNo()) == 0) {
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		//アクセスログ登録
		accesslogDao.insert(TB043CustomerUploadModel.GAMEN_NM,
							TB043CustomerUploadModel.BUTTON_NM_DELETE,
							"seqNo=" + model.getDeleteSeqNo());

		//アップロードされたファイルの削除
		File deleteFile = new File(tbTUploadRireki.getRealFileNm());
		FileUtils.deleteQuietly(deleteFile);

		return model;
	}

	/**
	 * CSV取込処理が実行可能か判定します。
	 *
	 * @param csvDataList CSVデータリスト
	 */
	private void validateExecutableCsvTorikomi(File uploadFile) {

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		int max = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_B2B_CSV_TORIKOMI_TO_MAX);

		// ファイルから行数を取得
		BufferedReader reader = null;
		int readLines = 0;
		try {
			reader = new BufferedReader(new FileReader(uploadFile));

			// タイトル行分はスキップ
			reader.readLine();
			while (reader.readLine() != null) {
				if (readLines > max) {
					// 既に上限値を超えたら、ループを抜ける
					break;
				}
				readLines++;
			}
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);
		} catch (IOException e) {
			throw new ApplicationException(e);
		}

		if (!(EXECUTABLE_CSV_TORIKOMI_MIN <= readLines && readLines <= max)) {
			// 最小件数以下、最大件数以上の場合
			throw new ValidationException(new ValidationPack().addError("MSG0025", Integer.toString(max)));
		}
	}
}

