package jp.co.tokaigroup.tlcss_b2b.dto;

import jp.co.tokaigroup.reception.entity.RcpTFileUpload;
import jp.co.tokaigroup.reception.entity.TbTFileUpload;
import jp.co.tokaigroup.si.fw.util.CommonUtil;

/**
 * ファイルアップロードDTO。
 *
 * @author k002849
 * @version 4.0 2014/07/15
 */
public class TB032FileUploadDto extends TbTFileUpload {
	/**
	 * デフォルトコンストラクタ。
	 */
	public TB032FileUploadDto() {
		super();
	}

	/**
	 * コンストラクタ。
	 * 依頼ファイルアップロードテーブルの情報を基に設定します。
	 *
	 * @param fileUpload 依頼ファイルアップロードテーブルEntity
	 */
	public TB032FileUploadDto(RcpTFileUpload fileUpload) {
		CommonUtil.copyProperties(fileUpload, this);
	}

	/**
	 * コンストラクタ。
	 * 業者回答依頼ファイルアップロードテーブルの情報を基に設定します。
	 *
	 * @param fileUpload 業者回答依頼ファイルアップロードテーブルEntity
	 */
	public TB032FileUploadDto(TbTFileUpload fileUpload) {
		CommonUtil.copyProperties(fileUpload, this);
	}

	/**
	 * 業者回答依頼ファイルアップロードテーブルの情報に変換した結果を取得します。
	 *
	 * @return 業者回答依頼ファイルアップロードテーブルEntity
	 */
	public TbTFileUpload toTbTFileUpload() {
		TbTFileUpload fileUpload = new TbTFileUpload();

		CommonUtil.copyProperties(this, fileUpload);

		return fileUpload;
	}
}
