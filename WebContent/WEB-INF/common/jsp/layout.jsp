<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//Dtd html 4.01 transitional//EN">
<html lang="ja">
<%@ include file="/WEB-INF/common/jsp/commonHead.jsp" %>
<body>
<div id="wrapper">
	<tiles:insertAttribute name="header" />
	<div id="contents">
		<h1><tiles:insertAttribute name="title" /></h1>
		<br>
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
