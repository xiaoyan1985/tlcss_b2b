package jp.co.tokaigroup.tlcss_b2b.common;

import java.util.Hashtable;
import java.util.Map;

import jp.co.tokaigroup.si.fw.util.CommonUtil;
import jp.co.tokaigroup.si.fw.util.Resource;

/**
 * ���[�e�B���e�B�N���X�B
 *
 */
public class TLCSSB2BResource extends Resource {

	/** ���v���p�e�B �ړ��� */
	private static final String PROPERTY_ENV_PREFIX = "TLCSSB2B";

	/** ���v���p�e�B ���� */
	private static final String PROPERTY_ENV_SUFFIX = "_Environment";

	/** ���\�[�X */
	@SuppressWarnings("rawtypes")
	private static Map resource = null;

	/** �v���p�e�B���ړ��� */
	private static String PREFIX = null;

	@SuppressWarnings("rawtypes")
	public TLCSSB2BResource() {
		String workunitName = CommonUtil.getClusterName();

		resource = new Hashtable();
		resource = putProperties(workunitName, resource, PROPERTY_ENV_PREFIX, PROPERTY_ENV_SUFFIX);
	}

	protected String getPrefix() {
		return PREFIX;
	}

	@SuppressWarnings("rawtypes")
	protected Map getProperties() {
		return resource;
	}



}
