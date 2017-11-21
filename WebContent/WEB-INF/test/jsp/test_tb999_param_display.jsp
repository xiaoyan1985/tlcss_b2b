<%@ page pageEncoding="UTF-8" %>

<%@ taglib uri="/WEB-INF/tld/struts-tags.tld" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ taglib uri="/WEB-INF/tld/f.tld" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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

	<tiles:putAttribute name="title">パラメータ表示テスト</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main" method="POST" action="login.action" target="login_win">
			<div id="contents">
			<table>
				<tr>
					<td>パラメータ名</td>
					<td>値</td>
				</tr>
			<s:iterator value="%{#parameters}" var="map">
				<tr>
					<td><s:property value="%{#map.key}" /></td>
					<td><s:property value="%{#parameters[#map.key]}" /></td>
				</tr>
			</s:iterator>
			</table>
			</div>
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>