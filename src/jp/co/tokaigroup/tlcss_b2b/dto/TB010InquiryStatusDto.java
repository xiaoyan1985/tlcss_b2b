package jp.co.tokaigroup.tlcss_b2b.dto;

import java.math.BigDecimal;
import java.util.List;

import jp.co.tokaigroup.reception.dto.RC014KeiyakuListDto;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;

/**
 * メニュー画面 問い合わせ状況ＤＴＯ。
 *
 * @author k002849
 * @version 4.0 2014/06/05
 */
public class TB010InquiryStatusDto extends RC014KeiyakuListDto {
	/** 対応中件数 */
	private BigDecimal inCompleteCount;
	/** 対応済件数 */
	private BigDecimal completedCount;

	/** 契約数 */
	private BigDecimal keiyakuCount;
	/** 戸数 */
	private BigDecimal kosuCount;

	/** 顧客契約マスタリスト */
	private List<String> kokyakuKeiyakuList;

	/** 参照顧客マスタ */
	private TbMRefKokyaku refKokyaku;

	/**
	 * 対応中件数を取得します。
	 *
	 * @return 対応中件数
	 */
	public BigDecimal getInCompleteCount() {
		return inCompleteCount;
	}
	/**
	 * 対応中件数を設定します。
	 *
	 * @param inCompleteCount 対応中件数
	 */
	public void setInCompleteCount(BigDecimal inCompleteCount) {
		this.inCompleteCount = inCompleteCount;
	}

	/**
	 * 対応済件数を取得します。
	 *
	 * @return complatedCount
	 */
	public BigDecimal getCompletedCount() {
		return completedCount;
	}
	/**
	 * 対応済件数を設定します。
	 *
	 * @param complatedCount 対応済件数
	 */
	public void setCompletedCount(BigDecimal completedCount) {
		this.completedCount = completedCount;
	}

	/**
	 * 全対応件数を取得します。
	 *
	 * @return 全対応件数
	 */
	public BigDecimal getAllCount() {
		return this.inCompleteCount.add(this.completedCount);
	}

	/**
	 * 契約数を取得します。
	 *
	 * @return 契約数
	 */
	public BigDecimal getKeiyakuCount() {
		return keiyakuCount;
	}
	/**
	 * 契約数を設定します。
	 *
	 * @param keiyakuCount 契約数
	 */
	public void setKeiyakuCount(BigDecimal keiyakuCount) {
		this.keiyakuCount = keiyakuCount;
	}

	/**
	 * 戸数を取得します。
	 *
	 * @return 戸数
	 */
	public BigDecimal getKosuCount() {
		return kosuCount;
	}
	/**
	 * 戸数を設定します。
	 *
	 * @param kosuCount 戸数
	 */
	public void setKosuCount(BigDecimal kosuCount) {
		this.kosuCount = kosuCount;
	}
	/**
	 * 顧客契約マスタリストを取得します。
	 *
	 * @return kokyakuKeiyakuList
	 */
	public List<String> getKokyakuKeiyakuList() {
		return kokyakuKeiyakuList;
	}
	/**
	 * 顧客契約マスタリストを設定します。
	 *
	 * @param kokyakuKeiyakuList セットする kokyakuKeiyakuList
	 */
	public void setKokyakuKeiyakuList(List<String> kokyakuKeiyakuList) {
		this.kokyakuKeiyakuList = kokyakuKeiyakuList;
	}

	/**
	 * 参照顧客マスタを取得します。
	 *
	 * @return refKokyaku
	 */
	public TbMRefKokyaku getRefKokyaku() {
		return refKokyaku;
	}
	/**
	 * 参照顧客マスタを設定します。
	 *
	 * @param refKokyaku セットする refKokyaku
	 */
	public void setRefKokyaku(TbMRefKokyaku refKokyaku) {
		this.refKokyaku = refKokyaku;
	}

	/**
	 * 顧客契約マスタリストが一件以上あるかを判定します。
	 *
	 * @return TRUE:一件以上存在 FALSE:0件
	 */
	public boolean hasKeiyaku() {
		return (kokyakuKeiyakuList != null && ! kokyakuKeiyakuList.isEmpty());
	}
}
