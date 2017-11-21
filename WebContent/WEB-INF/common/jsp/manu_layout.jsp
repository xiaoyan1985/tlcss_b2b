<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//Dtd html 4.01 transitional//EN">
<html lang="ja">
<head>
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0">
	<meta name="format-detection" content="telephone=no" />
	<title><tiles:insertAttribute name="title" /></title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/mobile.css">

<%@ include file="/WEB-INF/common/jsp/commonScript.jsp" %>
	<tiles:insertAttribute name="script" />
</head>

<body>
<div id="wrapper">
	<tiles:insertAttribute name="header" />
	<div id="contents">
		<div id="successMsgArea">
			<s:if test="hasActionMessages()">
				<span class="successMsg"><s:actionmessage escape="false" /></span>
			</s:if>
		</div>
		<div id="errorMsgArea">
			<s:if test="hasActionErrors()">
				<span class="errorMsg"><s:actionerror escape="false" /></span>
			</s:if>
		</div>
		<tiles:insertAttribute name="body" />
		<tiles:insertAttribute name="footer" />
	</div>
</div>
</body>
</html>
