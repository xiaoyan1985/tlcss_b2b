<%--*****************************************************
	管理情報アップロード画面
	作成者：岩田
	作成日：2014/08/04
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		<%-- 確認ダイアログ(初期値は空) --%>
		var confirmation = function() {return true;};

		$(function() {
			window.focus();

			$("#main").validate({
				ignore:
					".ignore *",
				rules: {
				},
				invalidHandler: function(form, validator) {
					fnc_change_fileType();
					alert(ART0015);
				},
				submitHandler: function(form) {
					if (!confirmation()) {
						return false;
					}

					form.submit();
				}
			});


			//******************************************************
			// フォームを開くボタン押下時
			//******************************************************
			$('#btnOpenForm').click(function(e) {
				confirmation = function() {return true;};

				fnc_remove_rules();

				var form = document.forms[0];
				form.action = '${helpUrl}';
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					form.target = '_blank';
				} else {
					// それ以外の場合は、iframeをターゲット
					form.target = 'tb_customer_upload_frame';
				}

				form.submit();
			});

			//******************************************************
			// エラーファイル確認ボタン押下時
			//******************************************************
			$('#btnErrorDownload').click(function(e) {
				fnc_open_file_link($('#main_errorFileFileName').val(), '1');
			});

			//******************************************************
			// アップロードボタン押下時
			//******************************************************
			$('#btnUpload').click(function(e) {
				fnc_add_rules();
				fnc_change_fileType();
				confirmation = function() {
					return confirm(jQuery.validator.format(INF0001, "アップロード", "実行", ""));
				};
				$('#main').prop('action', '<s:url action="customerUploadUpload" />');
				$('#main').prop('target', '_self');
				$('#main').submit();
			});

		});

		//*******************************************
		// 削除リンク押下時 実行関数
		//*******************************************
		function fnc_delete_link(seqNo) {
			confirmation = function() {
				return confirm(jQuery.validator.format(INF0001, "アップロードファイル", "削除", ""));
			};

			fnc_remove_rules();

			$('#deleteSeqNo').val(seqNo);

			$('#main').prop('action', '<s:url action="customerUploadDelete" />');
			$('#main').prop('target', '_self');

			$('#main').submit();
		}

		//*******************************************
		// ファイルダウンロード 実行関数
		//*******************************************
		function fnc_open_file_link(fileNm, fileDownloadType) {
			confirmation = function() {return true;};

			fnc_remove_rules();

			$('#selectFileNm').val(fileNm);
			$('#fileDownloadType').val(fileDownloadType);

			$('#main').prop('action', '<s:url action="selectFileDownload" />');
			if (isIPad == true) {
				// iPadの場合は、_blankをターゲット
				$('#main').prop('target', '_blank');
			} else {
				// それ以外の場合は、iframeをターゲット
				$('#main').prop('target', 'tb_customer_upload_frame');
			}

			$('#main').submit();
		}

		//*******************************************
		// 種別セレクト選択時
		//*******************************************
		function fnc_change_fileType(){
			if($('#main_fileType').val() == '1' || $('#main_fileType').val() == '2') {
				$('#main_uploadFile').rules("add", "csv");
			}
			if($('#main_fileType').val() == '9') {
				$('#main_uploadFile').rules("remove", "csv");
			}
		}

		//*******************************************
		// アップロード選択時
		//*******************************************
		function fnc_add_rules(){
			$('#main_fileType').rules("add", "required");
			$('#main_uploadFile').rules("add", "csv");
			$('#main_uploadFile').rules("add", "required");
		}

		//*******************************************
		// 各リンク選択時
		//*******************************************
		function fnc_remove_rules(){
			$('#main_fileType').rules("remove", "required");
			$('#main_uploadFile').rules("remove", "csv");
			$('#main_uploadFile').rules("remove", "required");
		}

		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">管理情報アップロード</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main" method="POST" enctype="multipart/form-data">
			<div class="content">
			<s:if test="!isSuccessCsvUpload()">
				<input type="button" id="btnErrorDownload" class="btnSubmit" value="エラーファイル確認"><br><br>
			</s:if>
				①　管理情報をアップロードします。<br>
				　　アップロードシートにて内容入力の後、ＣＳＶ形式で保存してください。
					<input type="button" id="btnOpenForm" class="btnSubmit" value="フォームを開く">
				<br>
				<br>
				②　保存したＣＳＶファイルを選択し、アップロードしてください。<br><br>
				<font color="red">※受領済のアップロードファイルは、一か月後、自動で削除されます。</font>
				<table>
					<tbody>
						<tr>
							<th><span class="font10">＊</span>種別</th>
							<td>
								<s:select key="fileType" list="#{1:'物件情報', 2:'入居者情報', 9:'その他'}" emptyOption="true" />
							</td>
						</tr>
						<tr>
							<th><span class="font10">＊</span>ファイル</th>
							<td><s:file key="uploadFile" cssStyle="width:370px;" /></td>
						</tr>
						<tr>
							<th>コメント</th>
							<td><s:textfield key="fileComment" maxlength="50" cssClass="zenhankaku" cssStyle="width:260px;" placeholder="コメント" /></td>
						</tr>

					</tbody>
				</table><br>
			</div>

			<div class="right">
				&nbsp;<input type="button" id="btnUpload" class="btnSubmit" value=" アップロード " />
				&nbsp;<input type="button" class="btnSubmit" id="btnClose" value=" 閉じる ">
			</div>

			<h2>アップロード履歴一覧</h2>
			<table>
				<thead>
					<tr>
						<th width="15%">アップロード日</th>
						<th width="9%">種別</th>
						<th width="25%">ファイル名</th>
						<th width="">コメント</th>
						<th width="10%">データ授受状況</th>
						<th width="5%"><br></th>
					</tr>
				</thead>
				<tbody>
				<s:iterator value="resultList" status="i">
					<tr class="${f:odd(i.index, 'odd,even')}">
						<td align="center">${f:dateFormat(uploadDt, "yyyy/MM/dd HH:mm")}</th>
						<td align="center">${f:out(shubetsuNm)}</td>
						<td><a href="JavaScript:fnc_open_file_link('${f:out(copyFileNm)}','0');">
							<span class="fontlink2">${f:out(userFileNm)}</span></a></td>
						<td>${f:out(FileComment)}</td>
						<td align="center">${f:out(kakuninJokyoNm)}</td>
						<td>
							<s:if test="isKakuninJokyoUnChecked()">
							<a href="JavaScript:fnc_delete_link('${f:out(seqNo)}');"><span class="fontlink2">削除</span></a>
							</s:if>
						</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
			<iframe src="" width="0px" height="0px" name="tb_customer_upload_frame" frameborder="0"></iframe>
			<br>
			<s:hidden key="errorFilePath" />
			<s:hidden key="errorFileFileName" />
			<input type="hidden" id="deleteSeqNo" name="deleteSeqNo" value="" />
			<input type="hidden" id="selectFileNm" name="selectFileNm" value="" />
			<input type="hidden" id="fileDownloadType" name="fileDownloadType" value="" />
			<s:tokenCheck displayId="TB043" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
