<%--*****************************************************
	管理対象一覧画面
	作成者：岩田
	作成日：2014/09/25
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="menu.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			//******************************************************
			// ログアウトボタン押下時
			//******************************************************
			$(':button[id^="logout"]').click(function(e) {
				alert(jQuery.validator.format(ART0025, ""));

				// ログイン画面に遷移
				$('#main').prop('action', '<s:url action="logout" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});

			//******************************************************
			// ヘルプボタン押下時
			//******************************************************
			$(':button[id^="help"]').click(function(e) {
				var form = document.forms[0];
				form.action = '${helpUrl}';
				form.target = '_blank';
				form.submit();
			});
			//******************************************************
			// 戻るボタン押下時 (顧客選択画面に戻る)
			//******************************************************
			$(':button[id^="back"]').click(function(e) {

				// 顧客選択画面に遷移
				$('#main').prop('action', '<s:url action="menuInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});
		});

		//*******************************************
		// 顧客選択(不動産・管理会社時) 実行関数
		//*******************************************
		function fnc_select_kokyaku_id(selectedKokyakuId) {
			$('#main').addClass('ignore');

			$('#selectedKokyakuId').val(selectedKokyakuId);

			$('#main').prop('action', '<s:url action="managedTargetListChooseId" />');
			$('#main').prop('target', '_self');

			$('#main').submit();
			$('#main').removeClass('ignore');
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">管理対象一覧</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
		<div id="content">
			<h1>管理対象一覧</h1>
			<br>
				<span style="float:right;">
					<input type="button" id="back" class="btnSubmit" value=" 戻 る " />
				</span>
			<br><br>
			<table>
				<thead>
					<tr>
						<th width="" >管理会社</th>
					</tr>
				</thead>
				<tbody>
				<s:iterator value="grpKokyakuList" status="i">
					<tr class="${f:odd(i.index, 'odd,even')}">
						<td><a href="JavaScript:fnc_select_kokyaku_id('${refKokyakuId}');">${f:out(kanjiKokyakuNm)}</a></td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
		</div>
		<input type="hidden" id="selectedKokyakuId" name="selectedKokyakuId" value="" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>

