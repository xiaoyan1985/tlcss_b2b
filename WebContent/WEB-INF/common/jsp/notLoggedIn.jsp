<%@ page pageEncoding="UTF-8" %>
<tiles:insertDefinition name="simple.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
			$(document).ready(function() {
				alert("タイムアウトしました。\n当システムで利用していた全ての画面を閉じて、\n新しい画面でログインし直してください。");

				// ページ名の取得
				var referer = '${header["referer"]}';
				var lastPos = referer.lastIndexOf('/');
				var pageName = referer.substr(lastPos + 1);
				var actionUrl = '${request.actionUrl}';

				if (pageName == '' || pageName.indexOf('login') != -1 || (actionUrl.indexOf('logout') != -1 && pageName.indexOf('menu') != -1)) {
					// 遷移元のページが空欄(1度のログインで成功)、または、ログイン画面の場合、
					// メニュー画面でログアウトボタン押下時の場合、
					// ログイン画面を表示
					$('#main').prop('action', '<s:url action="loginInit" />');
					$('#main').prop('target', '_self');
					$('#main').submit();
				} else {
					// それ以外の場合、画面を閉じる
					window.close();
				}
			});
		//-->
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="title">セッションエラー</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
		</s:form>
	</tiles:putAttribute>

</tiles:insertDefinition>
