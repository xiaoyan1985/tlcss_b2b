package jp.co.tokaigroup.tlcss_b2b.master.model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.TbTInformation;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB103InformationSearchCondition;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * お知らせ登録画面モデル。
 *
 * @author v138130
 * @version 4.0 2014/06/20
 */
public class TB104InformationEntryModel extends TB000CommonModel {
	/** 画面名 */
	public static final String GAMEN_NM = "お知らせ登録";

	/** hidden出力除外項目 */
	private static final String[] EXCLUDE_FIELD_NAME = {"limit", "maxCount", "count", "displayToMax", "completed"};

	/** お知らせ情報 */
	private TbTInformation info;

	/** 表示対象用リスト */
	private List<RcpMComCd> targetList;

	// 以下、画面パラメータ
	/** 連番 */
	private BigDecimal seqNo;
	/** お知らせ検索条件 */
	private TB103InformationSearchCondition condition = new TB103InformationSearchCondition();

	/** 顧客名（更新-更新初期表示時のアクションクラスのパラメータ用） */
	private String encodedKokyakuNm;

	/** 業者名（更新-更新初期表示時のアクションクラスのパラメータ用） */
	private String encodedGyoshaNm;


	/**
	 * お知らせ情報を取得します。
	 *
	 * @return info
	 */
	public TbTInformation getInfo() {
		return info;
	}
	/**
	 * お知らせ情報を設定します。
	 *
	 * @param info お知らせ情報
	 */
	public void setInfo(TbTInformation info) {
		this.info = info;
	}
	/**
	 * 表示対象用リストを取得します。
	 *
	 * @return 表示対象用リスト
	 */
	public List<RcpMComCd> getTargetList() {
		return targetList;
	}
	/**
	 * 表示対象用リストを設定します。
	 *
	 * @param serviceList 表示対象用リスト
	 */
	public void setTargetList(List<RcpMComCd> targetList) {
		this.targetList = targetList;
	}
	/**
	 * 連番を取得します。
	 *
	 * @return 連番
	 */
	public BigDecimal getSeqNo() {
		return seqNo;
	}
	/**
	 * 連番を設定します。
	 *
	 * @param seqNo 連番
	 */
	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * お知らせ検索条件を取得します。
	 *
	 * @return お知らせ検索条件
	 */
	public TB103InformationSearchCondition getCondition() {
		return condition;
	}
	/**
	 * お知らせ検索条件を設定します。
	 *
	 * @param condition お知らせ検索条件
	 */
	public void setCondition(TB103InformationSearchCondition condition) {
		this.condition = condition;
	}


	/**
	 * 顧客名（更新-更新初期表示時のアクションクラスのパラメータ用）を取得します。
	 *
	 * @return 顧客名（更新-更新初期表示時のアクションクラスのパラメータ用）
	 */
	public String getEncodedKokyakuNm() {
		return encodedKokyakuNm;
	}

	/**
	 * 顧客名（更新-更新初期表示時のアクションクラスのパラメータ用）を設定します。
	 *
	 * @param encodedKokyakuNm 顧客名
	 */
	public void setEncodedKokyakuNm(String encodedKokyakuNm) {
		try {
			byte[] data = encodedKokyakuNm.getBytes(CharEncoding.ISO_8859_1);
			this.encodedKokyakuNm = new String(data, CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 業者名（更新-更新初期表示時のアクションクラスのパラメータ用）を取得します。
	 *
	 * @return 業者名（更新-更新初期表示時のアクションクラスのパラメータ用）
	 */
	public String getEncodedGyoshaNm() {
		return encodedGyoshaNm;
	}

	/**
	 * 業者名（更新-更新初期表示時のアクションクラスのパラメータ用）を設定します。
	 *
	 * @param encodedGyoshaNm 業者名（更新-更新初期表示時のアクションクラスのパラメータ用）
	 */
	public void setEncodedGyoshaNm(String encodedGyoshaNm) {
		try {
			byte[] data = encodedGyoshaNm.getBytes(CharEncoding.ISO_8859_1);
			this.encodedGyoshaNm = new String(data, CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * hidden出力除外項目をカンマ区切りで取得します。
	 *
	 * @return hidden出力除外項目（カンマ区切り）
	 */
	public String getExcludeField() {
		return StringUtils.join(EXCLUDE_FIELD_NAME, ",");
	}

	public String getDecodePrm(String prm) {
		try {
			return URLDecoder.decode(prm, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return prm;
		}
	}

	/**
	 * "UTF-8"のエンコーディング結果を取得します。
	 *
	 * @param value エンコーディング対象
	 * @return エンコーディング結果（valueがnullの場合、""を返す）
	 */
	public String encode(String value) {
		try {
			return StringUtils.isNotBlank(value) ? (URLEncoder.encode(value, CharEncoding.UTF_8)) : "";
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}

}
