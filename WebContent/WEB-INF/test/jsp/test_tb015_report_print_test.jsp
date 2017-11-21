<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			//******************************************************
			// 報告書印刷ボタン押下時
			//******************************************************
			$('#btnReportPrintView').click(function(e) {
				$('#main').addClass('ignore');

				$('#main').prop('action', '<s:url action="reportPrintInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();

				$('#main').removeClass('ignore');
			});
		});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">報告書印刷テスト</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main" method="POST" enctype="multipart/form-data">
			<div id="content">
				<table>
					<tr>
						<th>
							<span class="font2">問い合わせＮＯ</span>
						</th>
						<td>
							<s:textfield key="toiawaseNo" cssClass="hankaku" cssStyle="width:200px;" maxlength="10" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="font2">問い合わせ履歴ＮＯ</span>
						</th>
						<td>
							<s:textfield key="toiawaseRirekiNo" cssClass="hankaku" cssStyle="width:200px;" maxlength="10" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="font2">問い合わせ更新日</span>
						</th>
						<td>
							<s:textfield key="toiawaseUpdDt" cssStyle="width:200px;" maxlength="19" placeholder="yyyy/MM/dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="font2">作業状況更新日</span>
						</th>
						<td>
							<s:textfield key="sagyoJokyoUpdDt" cssStyle="width:200px;" maxlength="19" placeholder="yyyy/MM/dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="font2">遷移元画面区分</span>
						</th>
						<td>
							<s:textfield key="dispKbn" cssClass="hankaku" cssStyle="width:200px;" maxlength="5" />
						</td>
					</tr>
				</table>
			</div>

			<input type="button" id="btnReportPrintView" class="btnSubmit" value=" 報告書印刷 " />
		</s:form>
		
		<br><br>
		
	</tiles:putAttribute>
</tiles:insertDefinition>