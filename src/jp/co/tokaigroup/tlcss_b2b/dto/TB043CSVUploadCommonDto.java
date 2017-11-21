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
 * �b�r�u�ꊇ�捞��ʋ��ʃ��f��
 *
 * @author v140546
 * @version 1.0 2014/08/05
 */
public abstract class TB043CSVUploadCommonDto {

	/**
	 * �X�֔ԍ����擾���܂��B
	 *
	 * @return �X�֔ԍ�
	 */
	public String getYubinNo() {
		return null;
	}

	/**
	 * CSV�f�[�^�����؂��܂��B
	 *
	 * @param csvData CSV�f�[�^
	 * @param lineNo �s�ԍ�
	 * @return ���،��ʃN���X
	 */
	public <T> ValidationPack validate(T csvData, int lineNo) {
		ValidationPack vp = new ValidationPack();

		// ���̕ϊ��R���o�[�^�[
		Map<String, String> propertyToNameConvertor =
			createPropertyToNameConvertor(getCsvColumnsKey(), getCsvColumnsName());

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(csvData);

		if (!(violations.isEmpty())) {
			// ���̓G���[�����݂���ꍇ
			for (ConstraintViolation<T> violation : violations) {
				// ���b�Z�[�W����
				String msg = MessageFormat.format("{0}�s��,�y{1}�z{2}",
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
	 * CSV�捞���Ɏg�p���閼�̕ϊ��R���o�[�^�[�𐶐����܂��B
	 *
	 * @param keys ���ڃL�[���X�g
	 * @param names ���ږ����X�g
	 * @return ���̕ϊ��R���o�[�^�[
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
	 * CSV�J������i�L�[�j���擾���܂��B
	 *
	 * @return CSV�J������i�L�[�j
	 */
	public abstract String[] getCsvColumnsKey();

	/**
	 * CSV�J������i���O�j���擾���܂��B
	 *
	 * @return CSV�J������i���O�j
	 */
	public abstract String[] getCsvColumnsName();
}
