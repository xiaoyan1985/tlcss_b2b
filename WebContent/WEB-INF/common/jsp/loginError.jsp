<%@ page pageEncoding="UTF-8" %>
<tiles:insertDefinition name="simple.layout">
	<tiles:putAttribute name="script">
	</tiles:putAttribute>
	<tiles:putAttribute name="title">ログインエラー</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<table width=60% cellspacing=0 cellpadding=0 class="borderoff" align="center">
			<tr class="borderoff"><td height=20 class="borderoff">&nbsp;</td></tr>
			<tr>
				<td align="center">
					<table cellspacing=1 cellpadding=10 border=1 width = 100% rules = "none" bgcolor="#FFFFFF">
						<tr>
							<td align=center class="borderoffspace">
								<font class="font10">
									ログインに失敗しました。再度ログインしてください。
								</font>
							</td>
						</tr>
						<tr>
							<td align=center class="borderoffspace">
								<br>&nbsp;&nbsp;発生日時&nbsp;：&nbsp;${f:currentDate('yyyy/MM/dd HH:mm:ss')}
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br><br>
	</tiles:putAttribute>
</tiles:insertDefinition>
