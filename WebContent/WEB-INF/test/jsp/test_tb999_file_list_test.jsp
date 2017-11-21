<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			//******************************************************
			// アップロードボタン押下時
			//******************************************************
			$('#btnUpload').click(function(e) {
				$('#main').prop('action', '<s:url action="fileListTestUpload" />');
				$('#main').prop('target', '_self');

				var fileCount = 0;
				$("input[name^='deployFileList']").each(function(){
					fileCount = fileCount + 1;
				});

				var fileNames = "";
				for (i = 0; i < fileCount; i++) {
					alert($("input[name='deployFileList[" + i + "]'").val());
					if ($("input[name='deployFileList[" + i + "]'").val() == "") {
						continue;
					}

					if (fileNames != "") {
						fileNames = fileNames + ","
					}

					fileNames = fileNames + $("input[name='deployFileList[" + i + "]'").val();
				}

				$("#deployFileNames").val(fileNames);

				$('#main').submit();
			});
		});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">ファイルテスト</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main" method="POST" enctype="multipart/form-data">
			<div id="content">
				<table>
					<thead>
						<tr>
							<th width="100%">ファイル</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator status="i" value="(3).{ #this }">
						<tr class="${f:odd(i.index, 'odd,even')}">
							<td><s:file cssStyle="width:330px;" name="uploadFileList" label="File" /></td>
						</tr>
						</s:iterator>

						<tr><td></td></tr>
						<tr><td></td></tr>

						<s:iterator status="i" value="(3).{ #this }">
						<tr class="${f:odd(i.index, 'odd,even')}">
							<%-- <td><s:file cssStyle="width:330px;" id="deployFileList[%{#i.index}]" key="deployFileList" label="File" /></td>--%>
							<td><s:file cssStyle="width:330px;" name="deployFileList[%{#i.index}]" label="File" /></td>
						</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>

			<input type="button" id="btnUpload" class="btnSubmit" value=" アップロード " />

			<input type="hidden" id="deployFileNames" name="deployFileNames" value="" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>