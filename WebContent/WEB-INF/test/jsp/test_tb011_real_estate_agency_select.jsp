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
				var kokyakuIdResultNm = $("#main_kokyakuIdResultNm").val();
				var kokyakuNmResultNm = $("#main_kokyakuNmResultNm").val();

				<%-- 親画面への値渡し --%>
				parent.opener.$("input[name='" + kokyakuIdResultNm + "']").val($("#kokyakuId_" + index).text());
				parent.opener.$("input[name='" + kokyakuNmResultNm + "']").val($("#kokyakuNm_" + index).text());
			}
			window.close();
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">不動産・管理会社選択スタブ</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<div id="content">
				<table>
					<thead>
						<tr>
							<th width="2%"></th>
							<th width="38%">顧客ＩＤ</th>
							<th width="60%">顧客名称</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="kokyakuList" status="i">
						<tr class="${f:odd(i.index, 'odd,even')}">
							<td><a href="JavaScript:fnc_select_link('${i.index}');">選択</a></td>
							<td><span id="kokyakuId_${i.index}">${f:out(kokyakuId)}</span></td>
							<td><span id="kokyakuNm_${i.index}">${f:out(kanjiNm)}</span></td>
						</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>

			<s:hidden key="kokyakuIdResultNm" />
			<s:hidden key="kokyakuNmResultNm" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>