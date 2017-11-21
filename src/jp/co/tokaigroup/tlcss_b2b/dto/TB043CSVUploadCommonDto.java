package jp.co.tokaigroup.tlcss_b2b.dto;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import jp.co.tokaigroup.si.fw.message.ValidationPack;

/**
 * ＣＳＶ一括取込画面共通モデル
 *
 * @author v140546
 * @version 1.0 2014/08/05
 */
public abstract class TB043CSVUploadCommonDto {

	/**
	 * 郵便番号を取得します。
	 *
	 * @return 郵便番号
	 */
	public String getYubinNo() {
		return null;
	}

	/**
	 * CSVデータを検証します。
	 *
	 * @param csvData CSVデータ
	 * @param lineNo 行番号
	 * @return 検証結果クラス
	 */
	public <T> ValidationPack validate(T csvData, int lineNo) {
		ValidationPack vp = new ValidationPack();

		// 名称変換コンバーター
		Map<String, String> propertyToNameConvertor =
			createPropertyToNameConvertor(getCsvColumnsKey(), getCsvColumnsName());

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(csvData);

		if (!(violations.isEmpty())) {
			// 入力エラーが存在する場合
			for (ConstraintViolation<T> violation : violations) {
				// メッセージ生成
				String msg = MessageFormat.format("{0}行目,【{1}】{2}",
						new Object[] {
							String.valueOf(lineNo),
							propertyToNameConvertor.get(violation.getPropertyPath().toString()),
							violation.getMessage()
				});

				vp.addErrorAsMessage(msg);
			}
		}

		return vp;
	}

	/**
	 * CSV取込時に使用する名称変換コンバーターを生成します。
	 *
	 * @param keys 項目キーリスト
	 * @param names 項目名リスト
	 * @return 名称変換コンバーター
	 */
	private Map<String, String> createPropertyToNameConvertor(String[] keys, String[] names) {
		Map<String, String> propertyToNameConvertor = new HashMap<String, String>();

		if ((keys == null || keys.length == 0) || (names == null || keys.length == 0)) {
			return propertyToNameConvertor;
		}

		for (int i = 0; i < keys.length; i++) {
			propertyToNameConvertor.put(keys[i], names[i]);
		}

		return propertyToNameConvertor;
	}

	/**
	 * CSVカラム列（キー）を取得します。
	 *
	 * @return CSVカラム列（キー）
	 */
	public abstract String[] getCsvColumnsKey();

	/**
	 * CSVカラム列（名前）を取得します。
	 *
	 * @return CSVカラム列（名前）
	 */
	public abstract String[] getCsvColumnsName();
}
