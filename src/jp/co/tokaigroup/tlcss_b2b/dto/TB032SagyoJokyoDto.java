package jp.co.tokaigroup.tlcss_b2b.dto;

import java.sql.Timestamp;

import jp.co.tokaigroup.reception.entity.RcpTSagyoJokyo;
import jp.co.tokaigroup.reception.entity.TbTSagyoJokyo;
import jp.co.tokaigroup.si.fw.util.CommonUtil;
import jp.co.tokaigroup.si.fw.util.DateUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 作業状況DTO。
 *
 * @author k002849
 * @version 4.0 2014/07/15
 */
public class TB032SagyoJokyoDto extends RcpTSagyoJokyo {
	/** 確認状況 */
	private String kakuninJokyo;
	/** 確認者ＩＤ */
	private String kakuninshaId;
	/** 確認日 */
	private Timestamp kakuninDt;

	/** 作業完了フラグ名称 */
	private String sagyoKanryoFlgNm;

	/**
	 * デフォルトコンストラクタ。
	 */
	public TB032SagyoJokyoDto() {
		super();
	}

	/**
	 * コンストラクタ。
	 * 作業状況テーブルの情報を基に設定します。
	 *
	 * @param sagyoJokyo 作業状況テーブルEntity
	 */
	public TB032SagyoJokyoDto(RcpTSagyoJokyo sagyoJokyo) {
		CommonUtil.copyProperties(sagyoJokyo, this);
	}

	/**
	 * コンストラクタ。
	 * 業者回答作業状況テーブルの情報を基に設定します。
	 *
	 * @param sagyoJokyo 業者回答作業状況テーブルEntity
	 */
	public TB032SagyoJokyoDto(TbTSagyoJokyo sagyoJokyo) {
		CommonUtil.copyProperties(sagyoJokyo, this);
	}

	/**
	 * 業者回答作業状況テーブルの情報に変換した結果を取得します。
	 *
	 * @return 業者回答作業状況テーブルEntity
	 */
	public TbTSagyoJokyo toTbTSagyoJokyo() {
		TbTSagyoJokyo sagyoJokyo = new TbTSagyoJokyo();

		CommonUtil.copyProperties(this, sagyoJokyo);

		return sagyoJokyo;
	}

	/**
	 * 作業状況テーブルの情報に変換した結果を取得します。
	 *
	 * @return 作業状況テーブルEntity
	 */
	public RcpTSagyoJokyo toRcpTSagyoJokyo() {
		RcpTSagyoJokyo sagyoJokyo = new RcpTSagyoJokyo();

		CommonUtil.copyProperties(this, sagyoJokyo);

		return sagyoJokyo;
	}

	/**
	 * 確認状況を取得します。
	 *
	 * @return 確認状況
	 */
	public String getKakuninJokyo() {
		return kakuninJokyo;
	}
	/**
	 * 確認状況を設定します。
	 *
	 * @param kakuninJokyo 確認状況
	 */
	public void setKakuninJokyo(String kakuninJokyo) {
		this.kakuninJokyo = kakuninJokyo;
	}

	/**
	 * 確認者ＩＤを取得します。
	 *
	 * @return 確認者ＩＤ
	 */
	public String getKakuninshaId() {
		return kakuninshaId;
	}
	/**
	 * 確認者ＩＤを設定します。
	 *
	 * @param kakuninshaId 確認者ＩＤ
	 */
	public void setKakuninshaId(String kakuninshaId) {
		this.kakuninshaId = kakuninshaId;
	}

	/**
	 * 確認日を取得します。
	 *
	 * @return 確認日
	 */
	public Timestamp getKakuninDt() {
		return kakuninDt;
	}
	/**
	 * 確認日を設定します。
	 *
	 * @param kakuninDt 確認日
	 */
	public void setKakuninDt(Timestamp kakuninDt) {
		this.kakuninDt = kakuninDt;
	}

	/**
	 * 作業完了フラグ名称を取得します。
	 *
	 * @return 作業完了フラグ名称
	 */
	public String getSagyoKanryoFlgNm() {
		return sagyoKanryoFlgNm;
	}
	/**
	 * 作業完了フラグ名称を設定します。
	 *
	 * @param sagyoKanryoFlgNm 作業完了フラグ名称
	 */
	public void setSagyoKanryoFlgNm(String sagyoKanryoFlgNm) {
		this.sagyoKanryoFlgNm = sagyoKanryoFlgNm;
	}

	/**
	 * 作業完了時間をコロン付きで取得します。
	 *
	 * @return 作業完了時間をコロン付きで整形した文字列
	 */
	public String getSagyoKanryoJikanPlusColon() {
		if (StringUtils.isBlank(getSagyoKanryoJikan())) {
			return "";
		}

		return DateUtil.hhmmPlusColon(getSagyoKanryoJikan());
	}
}
