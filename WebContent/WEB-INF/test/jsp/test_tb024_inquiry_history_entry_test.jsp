<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			//******************************************************
			// 問い合わせ履歴移動ボタン押下時
			//******************************************************
			$('#btnToiawaseRirekiMoveView').click(function(e) {
				$('#main').addClass('ignore');
				
				$('#main_toiawaseWindowName').val(window.name);

				$('#main').prop('action', '<s:url action="inquiryHistoryMoveInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();

				$('#main').removeClass('ignore');
			});

			//******************************************************
			// 顧客ID変更ボタン押下時
			//******************************************************
			$('#btnKokyakuIdChangeView').click(function(e) {
				$('#main_toiawaseWindowName').val(window.name);
				$('#main').prop('action', '<s:url action="customerIdChangeInit" />');
				$('#main').prop('target', 'tb_customer_id_change_win');
				$('#main').submit();
			});
			
			//******************************************************
			// 問い合わせ履歴登録（新規）
			//******************************************************
			$('#btnToInquiryEntry').click(function(e) {
				
				$('#main_inquiry_entry').prop('action', '<s:url action="inquiryHistoryEntryInit" />');
				$('#main_inquiry_entry').prop('target', '_self');

				$('#main_inquiry_entry').submit();
			});
			//******************************************************
			// 問い合わせ履歴登録（更新）
			//******************************************************
			$('#btnToInquiryUpdate').click(function(e) {
				
				$('#main_inquiry_entry').prop('action', '<s:url action="inquiryHistoryEntryUpdateInit" />');
				$('#main_inquiry_entry').prop('target', '_self');

				$('#main_inquiry_entry').submit();
			});
		});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">問い合わせ履歴登録テスト</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main" method="POST" enctype="multipart/form-data">
			<div id="content">
				<table>
					<tr>
						<th>
							<span class="font2">移動元問い合わせＮＯ</span>
						</th>
						<td>
							<s:textfield key="toiawaseNo" cssClass="hankaku" cssStyle="width:200px;" maxlength="10" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="font2">入力問い合わせＮＯ</span>
						</th>
						<td>
							<s:textfield key="newToiawaseNo" cssClass="hankaku" cssStyle="width:200px;" maxlength="10" />
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
							<span class="font2">変更前顧客ID</span>
						</th>
						<td>
							<s:textfield key="oldKokyakuId" cssClass="hankaku" cssStyle="width:200px;" maxlength="10" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="font2">入力顧客ID</span>
						</th>
						<td>
							<s:textfield key="newKokyakuId" cssClass="hankaku" cssStyle="width:200px;" maxlength="10" />
						</td>
					</tr>
				</table>
			</div>

			<input type="button" id="btnToiawaseRirekiMoveView" class="btnSubmit" value=" 問い合わせ履歴移動 " />
			<input type="button" id="btnKokyakuIdChangeView" class="btnSubmit" value=" 顧客ID変更 " />

			<s:hidden key="dispKbn" />
			<s:hidden key="toiawaseWindowName" />
		</s:form>
		
		<br><br>
		
		<%-- 問い合わせ登録 form --%>
		<s:form id="main_inquiry_entry" method="POST" enctype="multipart/form-data">
			<div id="content">
				<table>
					<tr>
						<th><span class="font2">顧客ＩＤ</span></th>
						<td><s:textfield key="kokyakuId" cssClass="hankaku" cssStyle="width:200px;" maxlength="10" /></td>
						<td>「新規・更新ともに、NULL、NOT NULLのパターンあり」</td>
					</tr>
					<tr>
						<th><span class="font2">問い合わせＮＯ</span></th>
						<td><s:textfield key="toiawaseNo" cssClass="hankaku" cssStyle="width:200px;" maxlength="10" /></td>
						<td>「NULL：新規登録時、NOT NULL：更新時」</td>
					</tr>
					<tr>
						<th><span class="font2">問い合わせ履歴ＮＯ</span></th>
						<td><s:textfield key="toiawaseRirekiNo" cssClass="hankaku" cssStyle="width:200px;" maxlength="10" /></td>
						<td>「NULL：新規登録時、NOT NULL：更新時」</td>
					</tr>
					<tr>
						<th><span class="font2">問い合わせ更新日</span></th>
						<td><s:textfield key="toiawaseUpdDt" cssClass="hankaku" cssStyle="width:200px;" maxlength="19" /></td>
						<td>「必須項目」「yyyy/mm/dd hh:mm:ss」</td>
					</tr>
					<tr>
						<th><span class="font2">遷移元画面区分</span></th>
						<td><s:textfield key="dispKbn" cssClass="hankaku" cssStyle="width:200px;" maxlength="5" /></td>
						<td>「tb021：問い合わせ検索」</td>
					</tr>
				</table>
			</div>
			<input type="button" id="btnToInquiryEntry" class="btnSubmit" value="問い合わせ履歴登録(新規)" />
			<input type="button" id="btnToInquiryUpdate" class="btnSubmit" value="問い合わせ履歴登録(更新)" />
		</s:form>
		
		<br><br>
		
	</tiles:putAttribute>
</tiles:insertDefinition>