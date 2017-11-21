package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import jp.co.tokaigroup.reception.common.model.RC000CommonModel;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbTPublishFile;

/**
 * 文書ライブラリ一覧モデル。
 *
 * @author C.Kobayashi
 * @version 1.0 2015/10/28
 */
public class TB048DocumentLibraryModel extends RC000CommonModel {
	
	/** 顧客ＩＤ */
	private String kokyakuId;

	/** ユーザー定義のファイル名 */
	private String userFileNm;
	/** 実ファイル名 */
	private String realFileNm;
	
	/** 文書ライブラリリスト */
	private List<TbTPublishFile> publishFileList;

	/** 顧客情報 */
	private RcpMKokyaku kokyaku;
	
	/** 文書ライブラリリストのリスト数 */
	private int listSize;
	
	/**
	 * 顧客ＩＤを取得します。
	 *
	 * @return 顧客ＩＤ
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}
	/**
	 * 顧客ＩＤを設定します。
	 *
	 * @param kokyakuId 顧客ＩＤ
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}

	/**
	 * ユーザー定義のファイル名を取得します。
	 *
	 * @return userFileNm
	 */
	public String getUserFileNm() {
		return userFileNm;
	}
	/**
	 * ユーザー定義のファイル名を設定します。
	 *
	 * @param userFileNm
	 */
	public void setUserFileNm(String userFileNm) {
		this.userFileNm = userFileNm;
	}

	/**
	 * 実ファイル名を取得します。
	 *
	 * @return realFileNm
	 */
	public String getRealFileNm() {
		return realFileNm;
	}
	/**
	 * 実ファイル名を設定します。
	 *
	 * @param realFileNm
	 */
	public void setRealFileNm(String realFileNm) {
		this.realFileNm = realFileNm;
	}

	/**
	 * 文書ライブラリリストを取得します。
	 *
	 * @return 文書ライブラリリスト
	 */
	public List<TbTPublishFile> getPublishFileList() {
		return publishFileList;
	}
	/**
	 * 文書ライブラリリストを設定します。
	 *
	 * @param publishFileList 文書ライブラリリスト
	 */
	public void setPublishFileList(List<TbTPublishFile> publishFileList) {
		this.publishFileList = publishFileList;
	}

	/**
	 * 顧客情報を取得します。
	 *
	 * @return 顧客情報
	 */
	public RcpMKokyaku getKokyaku() {
		return kokyaku;
	}
	/**
	 * 顧客情報を設定します。
	 *
	 * @param kokyaku 顧客情報
	 */
	public void setKokyaku(RcpMKokyaku kokyaku) {
		this.kokyaku = kokyaku;
	}

	/**
	 * 文書ライブラリ表示時のサイズを取得します。
	 * @return 文書ライブラリ表示時のサイズ
	 */
	public int getListSize() {
		return listSize;
	}
	/**
	 * 文書ライブラリ表示時のサイズを設定します。
	 * @param listSize 文書ライブラリ表示時のサイズ
	 */
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

}
