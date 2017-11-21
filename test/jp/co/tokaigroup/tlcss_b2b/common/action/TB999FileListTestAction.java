package jp.co.tokaigroup.tlcss_b2b.common.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.File;
import java.util.List;

import jp.co.tokaigroup.tlcss_b2b.common.model.TB999FileListTestModel;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
@Results({
	@Result(name=SUCCESS, location="../test/jsp/test_tb999_file_list_test.jsp"),
	@Result(name=INPUT, location="../test/jsp/test_tb999_file_list_test.jsp")
})
public class TB999FileListTestAction extends TLCSSB2BBaseActionSupport implements
		ModelDriven<TB999FileListTestModel> {

	/** ���K�[ */
	@SuppressWarnings("unused")
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/** ��ʃ��f�� */
	private TB999FileListTestModel model = new TB999FileListTestModel();

	/**
	 * �����\���������s���܂��B
	 *
	 * @return ���O�C����ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("fileListTestInit")
	public String init() throws Exception {
		model.setDeployFileList(new File[3]);

		return SUCCESS;
	}

	/**
	 * �A�b�v���[�h�������s���܂��B
	 *
	 * @return ���O�C����ʃ��f��
	 * @throws Exception ���s����O�����������ꍇ
	 */
	@Action("fileListTestUpload")
	public String upload() throws Exception {
		List<File> fileList = model.getUploadFileList();

		if (!(fileList == null || fileList.isEmpty())) {
			for (int i = 0; i < fileList.size(); i++) {
				if (fileList.get(i) == null) {
					continue;
				}

				System.out.println("idx:" + i + " " + fileList.get(i).getAbsolutePath());
			}
			for (File file : fileList) {

				System.out.println(file.getAbsolutePath());
			}
		}

		File[] fileList2 = model.getDeployFileList();
		if (!(fileList2 == null || fileList2.length == 0)) {
			for (int i = 0; i < fileList2.length; i++) {
				if (fileList2[i] == null) {
					continue;
				}

				System.out.println("idx:" + i + " " + fileList2[i].getAbsolutePath());
			}
		}
		return SUCCESS;
	}

	/**
	 * ��ʃ��f����Ԃ��܂��B
	 *
	 * @return model ��ʃ��f��
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public TB999FileListTestModel getModel() {
		return model;
	}
}
