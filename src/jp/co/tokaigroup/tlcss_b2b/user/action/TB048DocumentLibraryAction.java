package jp.co.tokaigroup.tlcss_b2b.user.action;

import static com.opensymphony.xwork2.Action.SUCCESS;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB048DocumentLibraryModel;
import jp.co.tokaigroup.tlcss_b2b.user.service.TB048DocumentLibraryService;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 文書ライブラリ一覧アクションクラス。
 *
 * @author C.Kobayashi
 * @version 1.0 2015/10/28
 */
@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="tb048_document_library.jsp")
})
public class TB048DocumentLibraryAction extends TLCSSB2BBaseActionSupport
	implements ModelDriven<TB048DocumentLibraryModel>{
	
	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB048DocumentLibraryModel model = new TB048DocumentLibraryModel();

	/** サービス */
	@Autowired
	private TB048DocumentLibraryService service;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return 文書ライブラリ一覧画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("documentLibraryInit")
	public String init() throws Exception {

		// 初期表示処理
		model = service.getInitInfo(model);

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return 文書ライブラリ一覧画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB048DocumentLibraryModel getModel() {
		return model;
	}

}
