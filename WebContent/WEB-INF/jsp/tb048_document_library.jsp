<%--*****************************************************
	文書ライブラリ一覧
	作成者：小林
	作成日：2015/10/28
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
			$(function() {
				//**********************************************
				// リンク先ファイル表示処理
				//**********************************************
				$('.linkLibrary').click(function(e) {
					$('#main').addClass('ignore');

					$('#main_realFileNm').val($(this).attr('filename'));

					$('#main').prop('action', '<s:url action="documentFileDownload" />');
					if (isIPad == true) {
						// iPadの場合は、_blankをターゲット
						$('#main').prop('target', '_blank');
					} else {
						// それ以外の場合は、iframeをターゲット
						$('#main').prop('target', 'tb_document_library_file_frame');
					}
					$('#main').submit();
					$('#main').removeClass('ignore');
				});
			});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">文書ライブラリ一覧</tiles:putAttribute>
	<tiles:putAttribute name ="body">
		<s:form id="main">
			<div class="content">
				<h2>文書ライブラリ一覧</h2>
				<table>
				<tr>
					<th width="10%">顧客</th>
					<td>${f:out(kokyaku.kokyakuIdWithKanjiNm)}</td>
				</tr>
				</table>
				<table>
					<s:iterator status="i" var="publishFile" value="publishFileList">
						<tr>
							<s:if test="#i.index == 0">
								<th width="10%" rowspan="${f:out(listSize)}">文書ライブラリ</th>
							</s:if>
							<td>
								<a href="#" filename="${realFileNm}" class="linkLibrary">${f:out(userFileNm)}</a>
							</td>
						</tr>
					</s:iterator>
				</table>

				<div class="right">
					&nbsp;<input type="button" id="btnClose" class="btnSubmit" value="閉じる">
				</div>
			</div>

			<iframe src="" width="0px" height="0px" name="tb_document_library_file_frame" frameborder="0"></iframe>

			<%-- hidden値 --%>
			<s:hidden key="kokyakuId" />
			<s:hidden key="realFileNm" />

			<s:tokenCheck displayId="TB048" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>