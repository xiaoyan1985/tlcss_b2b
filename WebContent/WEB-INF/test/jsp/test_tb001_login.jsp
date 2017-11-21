<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();
		});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">ログインテスト</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main" method="POST" action="login.action" target="login_win">
			<div id="contents">
				ログインＩＤ：<input type="text" name="loginId" value="" maxlength="256" />
				<br>
				パスワード：<input type="text" name="passwd" value="" maxlength="20" />
				<br>
				遷移先URL：<input type="text" name="actionURL" value="" maxlength="256" />

				<div style="text-align:center;">
					　<input type="submit" id="btnLogin" class="btnSubmit" value="  ログイン  ">
				</div>
			</div>
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>