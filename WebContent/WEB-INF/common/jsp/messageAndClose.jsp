<%@ page pageEncoding="UTF-8" %>
<tiles:insertDefinition name="simple.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(document).ready(function() {
			var msg = '';

			<s:if test="hasActionMessages()">
			msg = <s:actionmessage escape="true" theme="sifwjs" />;
			</s:if>
			<s:elseif test="hasActionErrors()">
			msg = <s:actionerror escape="true" theme="sifwjs" />;
			</s:elseif>

			if (msg != "") {
				alert(msg);
			}

			window.close();
		});
		//-->
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
	</tiles:putAttribute>
</tiles:insertDefinition>
