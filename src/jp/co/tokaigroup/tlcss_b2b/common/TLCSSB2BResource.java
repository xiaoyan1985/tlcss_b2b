package jp.co.tokaigroup.tlcss_b2b.common;

import java.util.Hashtable;
import java.util.Map;

import jp.co.tokaigroup.si.fw.util.CommonUtil;
import jp.co.tokaigroup.si.fw.util.Resource;

/**
 * ユーティリティクラス。
 *
 */
public class TLCSSB2BResource extends Resource {

	/** 環境プロパティ 接頭語 */
	private static final String PROPERTY_ENV_PREFIX = "TLCSSB2B";

	/** 環境プロパティ 末尾 */
	private static final String PROPERTY_ENV_SUFFIX = "_Environment";

	/** リソース */
	@SuppressWarnings("rawtypes")
	private static Map resource = null;

	/** プロパティ名接頭語 */
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
