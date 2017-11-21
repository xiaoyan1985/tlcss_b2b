<%@ page pageEncoding="UTF-8" %>
<head>
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0">
	<meta name="format-detection" content="telephone=no" />
	<title><tiles:insertAttribute name="title" /></title>
<%@ include file="/WEB-INF/common/jsp/commonScript.jsp" %>

	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/mobile.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swipebox.css">

	<tiles:insertAttribute name="script" />
</head>
