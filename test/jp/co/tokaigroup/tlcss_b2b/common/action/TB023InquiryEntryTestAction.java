package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB023InquiryEntryTestModel;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="../test/jsp/test_tb023_inquiry_entry_test.jsp"),
	@Result(name=INPUT, location="../test/jsp/test_tb023_inquiry_entry_test.jsp")
})
public class TB023InquiryEntryTestAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB023InquiryEntryTestModel> {

	/** ロガー */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** 画面モデル */
	private TB023InquiryEntryTestModel model = new TB023InquiryEntryTestModel();

	/**
	 * 初期表示処理を行います。
	 *
	 * @return ログイン画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	@Action("inquiryEntryTestInit")
	public String init() throws Exception {

		return SUCCESS;
	}

	/**
	 * 画面モデルを返します。
	 *
	 * @return model 画面モデル
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB023InquiryEntryTestModel getModel() {
		return model;
	}
}
