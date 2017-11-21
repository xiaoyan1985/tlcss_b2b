<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="menu.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$.ajaxSetup({ cache: false });
		$(document).ready(function() {
			window.focus();
			$("#logout").click(function() {
				document.main.action = '<s:url action="logout" />';
				document.main.target = "_self";
				document.main.submit();
			});
		});

		function openScreen(wname, url) {
			var w = createWindow(wname);

			var form = document.forms[0];
			form.action = url;
			form.target = wname;
			form.submit();
		}
		//-->
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="title">TLCサポートシステム　不動産・管理会社、業者向けサイト</tiles:putAttribute>
	<tiles:putAttribute name="body">

		<s:form id="main">
			<div id="contents">
				<h2>メニュー</h2>
				<div id="contents">
					<table class="borderoff">
						<tr class="borderoff">
							<td valign="top" class="borderoff">
								<ul style="padding-left:30px;list-style:none;">
									<li><a href="javascript:openScreen('password_change_win', '<s:url action='passwordChangeInit?dispMode=0' />');" onclick="">パスワード変更</a></li>
									<li><a href="javascript:openScreen('tb_request_entry_win', '<s:url action='requestSearchInit' />');" onclick="">依頼検索</a></li>
									<li><a href="javascript:openScreen('tb_inquiry_entry_win', '<s:url action='inquirySearchInit' />');" onclick="">問い合わせ検索</a></li>
									<li><a href="javascript:openScreen('tb_user_master_entry_win', '<s:url action='userMasterSearchInit' />');" onclick="">ユーザーマスタ検索</a></li>
									<li><a href="javascript:openScreen('tb_customer_search_win', '<s:url action='customerSearchInit' />');" onclick="">物件・入居者検索</a></li>
									<li><a href="javascript:openScreen('tb_real_estate_agency_search_win', '<s:url action='realEstateAgencyInit' />');" onclick="">不動産･管理会社検索</a></li>
									<li><a href="javascript:openScreen('tb_contractor_search_win', '<s:url action='centerContractorSearchInit' />');" onclick="">業者検索</a></li>
									<li><a href="javascript:openScreen('tb_file_list_win', '<s:url action='fileListTestInit' />');" onclick="">ファイルリストテスト</a></li>
									<li><a href="javascript:openScreen('tb_inquiry_entry_win', '<s:url action='inquiryEntryTestInit' />');" onclick="">問い合わせ登録スタブ</a></li>
									<li><a href="javascript:openScreen('tb_inquiry_history_entry_win', '<s:url action='inquiryHistoryEntryTestInit' />');" onclick="">問い合わせ履歴登録スタブ</a></li>
									<li><a href="javascript:openScreen('tb_request_entry_test_win', '<s:url action='requestEntryTestInit' />');" onclick="">依頼登録スタブ</a></li>
									<li><a href="javascript:openScreen('tb_report_print_win', '<s:url action='reportPrintTestInit' />');" onclick="">報告書印刷スタブ</a></li>
								</ul>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
