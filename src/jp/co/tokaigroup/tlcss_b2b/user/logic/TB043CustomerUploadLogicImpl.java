package jp.co.tokaigroup.tlcss_b2b.user.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dao.TbTUploadRirekiDao;
import jp.co.tokaigroup.reception.entity.TbTUploadRireki;
import jp.co.tokaigroup.reception.torikomi.model.RC701KokyakuKihonCSVTorikomiBean;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.CSVReader;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.si.fw.util.ResourceFactory;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.dto.TB043CSVUploadCommonDto;
import jp.co.tokaigroup.tlcss_b2b.dto.TB043CSVUploadResultDto;
import jp.co.tokaigroup.tlcss_b2b.dto.TB043CustomerUploadForRealEstateDto;
import jp.co.tokaigroup.tlcss_b2b.dto.TB043CustomerUploadForTanantDto;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB043CustomerUploadModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 管理情報アップロードロジック実装クラス。
 *
 * @author v140546
 * @version 1.0 2014/08/04
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB043CustomerUploadLogicImpl  extends TLCSSB2BBaseService
		implements TB043CustomerUploadLogic{

	/** 取込ファイルパス */
	private static final String UPLOAD_PATH_CUSTOMER = ResourceFactory.getResource().getString("UPLOAD_PATH_CUSTOMER");
	/** 取込エラーファイルパス */
	private static final String UPLOAD_PATH_CUSTOMER_ERROR = ResourceFactory.getResource().getString("UPLOAD_PATH_CUSTOMER_ERROR");
	/** エラーファイル接尾辞 */
	private static final String UPLOAD_ERROR_FILE_SUFFIX = "_ERROR.csv";
	/** CSVファイルエンコーディング */
	private static final String CSV_FILE_ENCODING = "Windows-31J";

	/** アップロード履歴DAO */
	@Autowired
	private TbTUploadRirekiDao tbTUploadRirekiDao;

	/** リセプションアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/**
	 * 管理情報アップロード()のCSV取込処理を行います。
	 *
	 * @param shubetsu 種別
	 * @param uploadFile アップロードファイル
	 * @param uploadFileNm アップロードファイル名
	 * @param loginId ログインＩＤ
	 * @param fileComment ファイルコメント
	 * @return 処理結果DTO
	 */
	public TB043CustomerUploadModel executeCsvImport(TB043CustomerUploadModel model) {
		// CSVリーダー
		CSVReader reader = null;
		// DTOリスト
		List<TB043CSVUploadCommonDto> inputDtoList = new ArrayList<TB043CSVUploadCommonDto>();
		// エラー情報
		ValidationPack errorInfo = new ValidationPack();
		// エラーファイル名
		String errFileNm = model.getUserContext().getKokyakuId() + "_" + model.getUploadFileFileName() + UPLOAD_ERROR_FILE_SUFFIX;
		// エラーファイルパス
		String errFilePath = UPLOAD_PATH_CUSTOMER_ERROR + System.getProperty("file.separator") + errFileNm;

		// 結果DTOの初期値設定
		TB043CSVUploadResultDto resultDto = new TB043CSVUploadResultDto();
		resultDto.setSuccess(true);

		try {
			// CSVファイル読込み(ファイルはShift_JIS)
			reader = new CSVReader(new InputStreamReader(
					new FileInputStream(model.getUploadFile()), CSV_FILE_ENCODING));

			// タイトル行は読み飛ばし
			reader.readNext();

			// 一行ずつ読み込み
			String[] csvData = null;
			// タイトル行を抜かして、2行目からのため、2からスタート
			int lineNo = 2;
			while ((csvData = reader.readNext()) != null) {
				// エラーフラグ（行単位）
				boolean isRowError = false;
				//---------- チェック処理 ----------//

				// 項目数チェック
				ValidationPack columnVp = new ValidationPack();
				//物件の場合
				if(TB043CustomerUploadModel.FILE_TYPE_BUKKEN.equals(model.getFileType())) {
					if (csvData == null || csvData.length == 0 || TB043CustomerUploadForRealEstateDto.ITEM_COUNT != csvData.length) {
						columnVp.addError("MSG0026", Integer.toString(lineNo), String.valueOf(TB043CustomerUploadForRealEstateDto.ITEM_COUNT), "");
					}
				//入居者の場合
				} else {
					if (csvData == null || csvData.length == 0 || TB043CustomerUploadForTanantDto.ITEM_COUNT != csvData.length) {
						columnVp.addError("MSG0026", Integer.toString(lineNo), String.valueOf(TB043CustomerUploadForTanantDto.ITEM_COUNT), "");
					}
				}
				if (columnVp.hasErrors()) {
					// 項目数チェックエラーがある場合
					errorInfo.addValidationPack(columnVp);
					lineNo++;

					isRowError = true;

					continue;
				}

				// DTOに変換
				TB043CSVUploadCommonDto inputDto = null;

				if (TB043CustomerUploadModel.FILE_TYPE_BUKKEN.equals(model.getFileType())) {
					RC701KokyakuKihonCSVTorikomiBean<TB043CustomerUploadForRealEstateDto> bean =
						new RC701KokyakuKihonCSVTorikomiBean<TB043CustomerUploadForRealEstateDto>();

					inputDto = bean.convertToDto(csvData, TB043CustomerUploadForRealEstateDto.class);
					// 文字種・桁数チェック
					if (!(inputDto instanceof TB043CustomerUploadForRealEstateDto)) {
						throw new ApplicationException("管理情報アップロード(物件情報)の型変換エラー");
					}

				} else {
					RC701KokyakuKihonCSVTorikomiBean<TB043CustomerUploadForTanantDto> bean =
						new RC701KokyakuKihonCSVTorikomiBean<TB043CustomerUploadForTanantDto>();

					inputDto = bean.convertToDto(csvData, TB043CustomerUploadForTanantDto.class);
					// 文字種・桁数チェック
					if (!(inputDto instanceof TB043CustomerUploadForTanantDto)) {
						throw new ApplicationException("管理情報アップロード(入居者情報)の型変換エラー");
					}

				}

				// バリデート
				ValidationPack inputVp = inputDto.validate(inputDto, lineNo);
				if (inputVp.hasErrors()) {
					// 文字種・桁数チェックエラーがある場合
					errorInfo.addValidationPack(inputVp);

					isRowError = true;
				}

				if (!isRowError) {
					// 行単位でのエラーがない場合
					// 正常のレコードのみ追加
					inputDtoList.add(inputDto);
				}

				lineNo++;
			}
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);
		} catch (IOException e) {
			throw new ApplicationException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					// closeのExceptionは無視
				}
			}
		}

		// 一つでも処理を失敗していた場合、処理を終了
		if (errorInfo.hasErrors()) {
			// エラー結果を設定し、処理終了
			model.setCsvUploadFlg(TB043CustomerUploadModel.CSV_UPLOAD_FLG_ILLEGAL);

			// エラー結果を設定し、処理終了
			model.setErrorFilePath(errFilePath);
			model.setErrorInfo(errorInfo);
			model.setErrorFileFileName(errFileNm);
			return model;
		}


		insertTbTUploadRirekiAndCopyFile(model);

		return model;
	}

	/**
	 * アップロード履歴テーブル登録処理、サーバーへのファイルコピーを行います。
	 *
	 * @param shubetsu
	 * @param uploadFile
	 * @param uploadFileNm
	 * @param loginId
	 * @param fileComment
	 */
	public void insertTbTUploadRirekiAndCopyFile(TB043CustomerUploadModel model) {

		//---------- アップロード履歴テーブル登録処理 ----------//
		TbTUploadRireki tbTUploadRireki = new TbTUploadRireki();
		// 連番新規作成
		BigDecimal newSeqNo = tbTUploadRirekiDao.nextVal();
		DecimalFormat df = new DecimalFormat("0000000");
		//ファイルの拡張子をとる
		String suffix = FilenameUtils.getExtension(model.getUploadFileFileName());
		//コピー先ファイルの作成
		String copyFileNm = model.getUserContext().getKokyakuId() + "_" + df.format(newSeqNo) + "." + suffix;
		String copyFilePath = UPLOAD_PATH_CUSTOMER + System.getProperty("file.separator") + "/" + copyFileNm;
		File copyFile = new File(copyFilePath);
		//現在時間の取得
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		//アップロード履歴データセット
		tbTUploadRireki.setSeqNo(newSeqNo);
		tbTUploadRireki.setKokyakuId(model.getUserContext().getKokyakuId());
		tbTUploadRireki.setUploadDt(nowTime);
		tbTUploadRireki.setShubetsu(model.getFileType());
		tbTUploadRireki.setUserFileNm(model.getUploadFileFileName());
		tbTUploadRireki.setCopyFileNm(copyFileNm);
		tbTUploadRireki.setRealFileNm(FilenameUtils.getName(new File(copyFilePath).getName()));
		tbTUploadRireki.setFileComment(model.getFileComment());
		tbTUploadRireki.setJujuJokyo(TbTUploadRireki.KAKUNIN_JOKYO_MIKAKUNIN);
		tbTUploadRireki.setCreId(model.getUserContext().getKokyakuId());
		tbTUploadRireki.setCreDt(nowTime);
		tbTUploadRireki.setUpdDt(nowTime);


		//アップロード履歴テーブルデータ登録
		tbTUploadRirekiDao.insert(tbTUploadRireki);

		//---------- アクセスログ登録処理 ----------//
		insertAccessLog(tbTUploadRireki, TB043CustomerUploadModel.GAMEN_NM,
				TB043CustomerUploadModel.BUTTON_NM_UPLOAD);

		//サーバにコピーする
		try {
			FileUtils.copyFile(model.getUploadFile(), copyFile);
		} catch (IOException e) {
			throw new ApplicationException("ファイルのコピーの失敗。");
		}
	}

	/**
	 * アクセスログに処理内容を登録します。
	 *
	 * @param shoriRireki 処理履歴テーブル情報
	 * @param buttonNm ボタン名
	 * @return 登録件数
	 */
	private int insertAccessLog(TbTUploadRireki tbTUploadRireki, String gamenNm, String buttonNm) {
		NullExclusionToStringBuilder entryEntity =
			new NullExclusionToStringBuilder(
				tbTUploadRireki,
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// 除外する項目
		List<String> excludeFiledList = new ArrayList<String>(Arrays.asList(Constants.EXCLUDE_FIELD_NAMES));

		entryEntity.setExcludeFieldNames((String[]) excludeFiledList.toArray(new String[0]));

		// アクセスログ登録
		int entryCnt = tbAccesslogDao.insert(
				gamenNm,
				buttonNm,
				entryEntity.toString());

		return entryCnt;
	}
}
