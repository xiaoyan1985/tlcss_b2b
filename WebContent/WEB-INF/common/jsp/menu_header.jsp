<%@ page pageEncoding="UTF-8" %>

<div id="header">
	<table class="borderoff">
		<%-- PC画面用ヘッダ --%>
		<tbody class="pc_header">
			<tr class="borderoff">
				<td class="borderoff"><span class="headTitle">Tokai Relief Support Portal System</span>&nbsp;<span class="dev_notice" <s:if test="%{#session.userContext.isProduction()}">style="visibility: hidden;"</s:if>>■開発■</span></td>
				<td class="borderoff" align="right" width="33%">
					<span class="headUser" title="${sessionScope.userContext.userName}">ようこそ ${sessionScope.userContext.userShortName} さん</span>
					<s:if test="%{#session.userContext.kokyakuName != null}"><br><span class="headUser" title="${sessionScope.userContext.kokyakuName}" style="font-size:8pt;">（管理会社：${sessionScope.userContext.kokyakuShortName}）</span></s:if>
				</td>
				<td class="borderoff pc_logout" align="right">
					<s:if test="%{!#session.userContext.isRealEstate()|| #session.userContext.isKokyakuIdSelected()}">
					<input type="button" id="help" class="btnSubmit" style="width:90px !important;" value="ヘルプ">
					</s:if>
					&nbsp;&nbsp;
					<input type="button" id="logout" class="btnSubmit" style="width:90px !important;" value="ログアウト">
				</td>
			</tr>
		</tbody>
		<%-- スマートフォン、タブレット画面用ヘッダ --%>
		<tbody class="mobile_header">
			<tr class="borderoff">
				<td class="borderoff mobile_title">
					<table class="borderoff">
						<tr class="borderoff">
							<td class="borderoff"><span class="headTitle">Tokai Relief Support Portal System</span></td>
						</tr>
						<tr class="borderoff">
							<td class="borderoff" width="33%">
								<span class="headUser" style="padding-left:63px;"><s:if test="%{#session.userContext != null}"> ようこそ ${sessionScope.userContext.userName} さん</s:if></span>
								<s:if test="%{#session.userContext.kokyakuName != null}"><br><span class="headUser" style="padding-left:63px;">（管理会社：${sessionScope.userContext.kokyakuName}）</span></s:if>
							</td>
						</tr>
					</table>
				</td>
				<td class="borderoff mobile_logout" align="right">
					<table class="borderoff">
						<tr class="borderoff">
							<td class="borderoff" align="right">
								<input type="button" id="help_mobile" class="btnSubmit" style="width:90px !important; height:30px !important; font-size: 9pt !important;" value="ヘルプ">
								&nbsp;&nbsp;
								<input type="button" id="logout_mobile" class="btnSubmit" style="width:90px !important; height:30px !important; font-size: 9pt !important;" value="ログアウト">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>