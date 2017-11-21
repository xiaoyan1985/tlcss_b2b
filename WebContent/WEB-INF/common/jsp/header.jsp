<%@ page pageEncoding="UTF-8" %>

<div id="header">
	<table class="borderoff">
		<%-- PC画面用ヘッダ --%>
		<tbody class="pc_header">
			<tr class="borderoff">
				<td class="borderoff"><span class="headTitle">Tokai Relief Support Portal System</span>&nbsp;<span class="dev_notice" <s:if test="%{#session.userContext.isProduction()}">style="visibility: hidden;"</s:if>>■開発■</span></td>
				<td class="borderoff" align="right" width="33%">
					<span class="headUser" title="${sessionScope.userContext.userName}"><s:if test="%{#session.userContext != null}"> ようこそ ${sessionScope.userContext.userShortName} さん</s:if></span>
					<s:if test="%{#session.userContext.kokyakuName != null}"><br><span class="headUser" title="${sessionScope.userContext.kokyakuName}" style="font-size:8pt;">（管理会社：${sessionScope.userContext.kokyakuShortName}）</span></s:if>
				</td>
				<td class="borderoff pc_logout" align="right">
					<input type="button" id="logout" value="ログアウト" style="visibility: hidden;">
				</td>
			</tr>
		</tbody>
		<%-- スマートフォン、タブレット画面用ヘッダ --%>
		<tbody class="mobile_header">
			<tr class="borderoff">
				<td class="borderoff"><span class="headTitle">Tokai Relief Support Portal System</span></td>
			</tr>
			<tr class="borderoff">
				<td class="borderoff">
					<span class="headUser" style="padding-left:63px;"><s:if test="%{#session.userContext != null}"> ようこそ ${sessionScope.userContext.userName} さん</s:if></span>
					<s:if test="%{#session.userContext.kokyakuName != null}"><br><span class="headUser" style="padding-left:63px;">（管理会社：${sessionScope.userContext.kokyakuName}）</span></s:if>
				</td>
			</tr>
		</tbody>
	</table>
</div>