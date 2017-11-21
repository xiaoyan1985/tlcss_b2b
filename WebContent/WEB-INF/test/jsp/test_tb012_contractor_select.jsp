<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();
		});

		function fnc_select_link(index) {
			<%-- 親画面の存在チェック --%>
			if(parent.window.opener && ! parent.window.opener.closed){
				<%-- 親画面のname値を取得 --%>
				var gyoshaCdResultNm = $("#main_gyoshaCdResultNm").val();
				var gyoshaNmResultNm = $("#main_gyoshaNmResultNm").val();

				<%-- 親画面への値渡し --%>
				parent.opener.$("input[name='" + gyoshaCdResultNm + "']").val($("#gyoshaCd_" + index).text());
				parent.opener.$("input[name='" + gyoshaNmResultNm + "']").val($("#gyoshaNm_" + index).text());
			}
			window.close();
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">業者選択スタブ</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<div id="content">
				<table>
					<thead>
						<tr>
							<th width="2%"></th>
							<th width="38%">業者コード</th>
							<th width="60%">業者名称</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="gyoshaList" status="i">
						<tr class="${f:odd(i.index, 'odd,even')}">
							<td><a href="JavaScript:fnc_select_link('${i.index}');">選択</a></td>
							<td><span id="gyoshaCd_${i.index}">${f:out(gyoshaCd)}</span></td>
							<td><span id="gyoshaNm_${i.index}">${f:out(gyoshaNm)}</span></td>
						</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>

			<s:hidden key="gyoshaCdResultNm" />
			<s:hidden key="gyoshaNmResultNm" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>