<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		
		$(function() {
			window.focus();
			
			// jQuery("A[href*='.pdf']").not(jQuery("A[href*='.pdf']").find('img')).addClass("pdf").attr("target","_blank");

			var validation = $("#main").validate({
					ignore:
						".ignore *",
					rules: {
						
						"irai.tantoshaId"		: {required : true},
						"gyosha.gyoshaNm"		: {required : true},
						"irai.iraiNaiyo"		: {required : true, byteVarchar: 600},
						"irai.homonKiboYmd"		: {dateYMD : true, byteVarchar: 8},
						"irai.homonKiboBiko"	: {byteVarchar: 100},
						"irai.gyoshaKaitoYmd"	: {dateYMD : true, byteVarchar: 8},
						"irai.gyoshaKaitoBiko"	: {byteVarchar: 100}
					},
					groups: {
						main_irai_tantosha				: "irai\.tantoshaId",
						main_gyosha_gyosha				: "gyosha\.gyoshaNm",
						main_houmon_kibou				: "irai\.homonKiboYmd irai\.homonKiboBiko irai\.gyoshaKaitoYmd irai\.gyoshaKaitoBiko"
					},
					invalidHandler: function(form, validator) {
						alert(ART0015);
					},
					submitHandler: function(form) {
						form.submit();
					}
				});

				$(".swipebox").swipebox({
					hideBarsDelay : 0
				});
	
				$('#btnAnswer').click(function(e) {
					$('#main').submit();
				});
				
				//******************************************************
				// 登録ボタン押下時
				//******************************************************
				$('#btnEntry').click(function(e) {
					
					$('#main_buttonId').val('btnEntry');
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
				});
				
				//******************************************************
				// 業者選択ボタン押下時
				//******************************************************
				$('#btnGyosha').click(function(e) {
					
					$('#main_buttonId').val('btnGyosha');
					// 業者選択処理
					// var form = document.gyosha;
					$('#main_dispKbn').val('tb033');
					
					var w = createWindow("tb_contractor_search_win");
					$('#main').prop('action', '<s:url action="centerContractorSearchInit" />');
					$('#main').prop('target', w.name);
	
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
	
				});
				
				//******************************************************
				// 戻るボタン押下時
				//******************************************************
				$('[id^="btnBack"]').click(function(e) {
					
					$('#main_buttonId').val('btnBack');
					
					$('#main').prop('action', '<s:url action="requestSearchInit" />');
					$('#main').prop('target', '_self');
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
				});
			});
			
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">依頼登録テスト</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main" method="POST" enctype="multipart/form-data">
			<div class="content">
				<label><span class="font10">※この画面は業者検索の動作確認用テスト画面です。<br>
					&nbsp;&nbsp;&nbsp;問い合わせ基本情報は固定値となります。</span></label>
					<br><br>
				<h2>問い合わせ基本情報</h2>
				<table width="90%" id="tblToiawaseInfo">
					<tr>
						<th width="10%">受付番号</th>
						<td>
							1000000001-1
						</td>
					</tr>
					<tr>
						<th>内容</th>
						<td>
							エントランスの自動ドアの反応が悪い
						</td>
					</tr>
				</table>
				<br>
				<h2>作業依頼内容</h2>
				<table width="90%" id="tblToiawaseInfo">
					<tr>
						<th width="10%"><span class="font10">* </span>発注担当</th>
						<td>
							<s:textfield key="irai.tantoshaId" id="main_irai_tantoshaId" value="担当　一郎" style="width:280px;" />
							<!--
							<select name="irai.tantoshaId" id="main_irai_tantoshaId">
								<option value="" selected="selected">（未選択）</option>
								<option value="015646">担当　一郎</option>
							</select>
							-->
							<label for="main_irai_tantosha" style="float:right;" class="error" generated="true" /></label>
						</td>
					</tr>
					<tr>
						<th><span class="font10">* </span>依頼業者</th>
						<td>
							<input type="button" name ="" value="業者選択" class="btnDialog" id="btnGyosha">
							<input type="text" name="gyosha.gyoshaNm" readonly="readonly" id="main_gyosha_gyoshaNm" class="readOnlyText"/>
							<label for="main_gyosha_gyosha" style="float:right;" class="error" generated="true" /></label>
						</td>
					</tr>
					<tr>
						<th><span class="font10">* </span>依頼内容</th>
						<td>
							<s:textarea id="main_irai_iraiNaiyo" name="irai.iraiNaiyo" style="width:100%" class="zenhankaku" rows="2" onblur="Javascript:this.value=this.value.replace(/	/g, ' ');"></s:textarea>
						</td>
					</tr>
					<tr>
						<th>訪問希望<br>訪問予定</th>
						<td>
							<span class="font2">訪問希望：</span>
							<s:textfield key="irai.homonKiboYmd" maxlength="8" value="" id="main_irai_homonKiboYmd" class="dateYMD" style="width:100px;" placeholder="yyyymmdd" />
							&nbsp;
							<select name="irai.homonKiboJikanKbn" id="main_irai_homonKiboJikanKbn">
								<option value="" selected="selected">（未選択）</option>
								<option value="01">至急</option>
								<option value="02">午前</option>
								<option value="03">昼前</option>
								<option value="04">午後一</option>
								<option value="05">夕方</option>
								<option value="06">午後随時</option>
								<option value="07">一日随時</option>
								<option value="99">その他</option>
							</select>
							<s:textfield key="irai.homonKiboBiko" maxlength="100" value="" id="main_irai_homonKiboBiko" style="width:450px;" />
							<br>
							<span class="font2">業者回答：</span>
							<s:textfield key="irai.gyoshaKaitoYmd" maxlength="8" value="" id="main_irai_gyoshaKaitoYmd" class="dateYMD" style="width:100px;" placeholder="yyyymmdd" />
							&nbsp;
							<select name="irai.gyoshaKaitoJikanKbn" id="main_irai_gyoshaKaitoJikanKbn">
								<option value="">（未選択）</option>
								<option value="01">至急</option>
								<option value="02">午前</option>
								<option value="03">昼前</option>
								<option value="04">午後一</option>
								<option value="05">夕方</option>
								<option value="06">午後随時</option>
								<option value="07">一日随時</option>
								<option value="99">その他</option>
							</select>
							<s:textfield key="irai.gyoshaKaitoBiko" maxlength="100" value="" id="main_irai_gyoshaKaitoBiko" style="width:450px;" />
							<br>
							<label for="main_houmon_kibou" class="error" generated="true" /></label>
						</td>
					</tr>
					<tr>
						<th>作業状況公開</th>
						<td>
						<s:checkbox key="irai.iraiokaiFlg" value="1" id="main_irai_iraiokaiFlg" checked="checked" />
						依頼情報を公開する　→　<span class="font10">チェックONで登録すると、依頼情報が公開されます。</span>
						</td>
					</tr>
			
				</table>
				<span class="required_remark">
					<span class="font10">*</span> <span class="font3">常に必須</span><br>
				</span>
				<br>
				<div class="right">
					&nbsp;<input type="button" id="btnEntry" class="btnSubmit" value=" 登 録 " />
					&nbsp;<input type="button" id="btnBack" class="btnSubmit" value=" 戻 る " />
				</div>
			</div>
			<s:hidden key="dispKbn" />
			<s:hidden key="gyoshaCdResultNm" />
			<s:hidden key="gyoshaNmResultNm" />
			<s:hidden key="kokyakuId" value="%{toiawase.kokyakuId}"/>
			<s:hidden key="gamenKbn" value="tb033" />
			
			<s:tokenCheck displayId="TB033" />

		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>