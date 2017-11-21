<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>開発用ログイン</title>
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/mobile.css">

<%@ include file="/WEB-INF/common/jsp/commonScript.jsp" %>

<script language="javascript">
<!--//
	$(document).ready(function(){
		$("#userContext\\.loginId").focus();

		$("#main").validate({
			invalidHandler: function(form, validator) {
				alert("入力エラーがあります。");
			},
			submitHandler: function(form) {
				form.target = "_self";
				form.submit();
			}
		});
	});

//-->
</script>
</head>
<body>
<div id="wrapper">
<s:form id="main" action="dummyLoginExecute.action">
	<div id="contents">
		<h1>開発用ログイン</h1>
		<div id="contents">
			<table>
				<tbody>
				<tr>
					<th align="left" width="30%">ログインＩＤ</th>
					<td><s:textfield id="userContext.loginId" key="userContext.loginId" value="test" cssClass="required hankaku" cssStyle="width:100px;" maxlength="10" />&nbsp;</td>
				</tr>
				<tr>
					<th align="left" width="30%">ログインユーザ名</th>
					<td><s:textfield id="userContext.userName" key="userContext.userName" value="テストユーザー" cssClass="required zenkaku" cssStyle="width:300px;" maxlength="10" />&nbsp;</td>
				</tr>
				</tbody>
			</table>
			<br>
			<br>
			<div style="text-align:center;">
			　<input type="submit" id="login" value="ログイン" class="btnSubmit">&nbsp;
			　<input type="button" name ="" value=" 閉じる " onClick="parent.window.close()" class="btnSubmit">
			</div>
		</div>
	</div>
</div>
</s:form>
</body>
</html>



