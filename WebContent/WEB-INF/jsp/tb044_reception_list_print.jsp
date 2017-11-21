<%--*****************************************************
	受付一覧画面
	作成者：岩田
	作成日：2014/07/08
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			$("#main").validate({
				ignore:
					".ignore *",
				rules: {
					"kokyakuId":			{required: true},
					"kokyakuNm":			{required: true},
					"targetDtFrom":			{required: true, dateYMD:true},
					"targetDtTo":			{required: true, dateYMD:true}
				},
				groups: {
					main_hyoji_Dt :"targetDtFrom targetDtTo",
					main_kokyaku_Dt :"kokyakuId kokyakuNm"
				},
				invalidHandler: function(form, validator) {
					alert(ART0015);
				},
				submitHandler: function(form) {
					form.submit();
				}
			});

			//******************************************************
			// 印刷ボタン押下時
			//******************************************************
			$('#btnPrint').click(function(e) {

				// 同一ウィンドウにメニュー画面を表示
				$('#main').prop('action', '<s:url action="receptionListPrint" />');
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb044_reception_list_print_frame');
				}

				$('#main').submit();
			});

			//******************************************************
			// 選択ボタン押下時
			//******************************************************
			$('#btnSelectCustomer').click(function(e) {
				confirmation = function() {return true;};

				$('#main_kokyakuIdResultNm').val('kokyakuId');
				$('#main_kokyakuNmResultNm').val('kokyakuNm');

				var w = createWindow('tb_select_real_estate_win');
				$('#main').addClass('ignore');
				$('#main').prop('action', '<s:url action="realEstateAgencyInit" />');
				$('#main').prop('target', 'tb_select_real_estate_win');
				$('#main').submit();
				$('#main').removeClass('ignore');
			});
		});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">受付一覧印刷</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
		<div class="content">
			<table>
			<tr>
			<%-- ユーザーの権限が管理者の場合 --%>
			<s:if test="%{#session.userContext.isInhouse()}">
				<th width="20%"><span class="font10">* </span>管理会社</th>
					<%-- 初期表示、権限リスト変更時にテキストボックスの表示／非表示をJavaScriptで制御 --%>
				<td><span id="lbl_select_content"></span>
					<s:textfield key="kokyakuId" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
					<s:textfield key="kokyakuNm" cssClass="readOnlyText" cssStyle="width:200px;" readonly="true" />
					&nbsp;<input type="button" id="btnSelectCustomer" class="btnDialog" value=" 選 択 " />
					<br><label for="main_kokyaku_Dt" style="float:left;" class="error" generated="true" /></label>
				</td>
			</s:if>
			</tr>
			<tr>
				<th>サービス</th>
				<td><s:select key="serviceKbn" list="serviceMEntityList" listKey="serviceKbn" listValue="serviceKbnNm" emptyOption="true" /></td>
			</tr>
			<tr>
				<th><span class="font10">* </span>対象期間</th>
				<td><s:textfield key="targetDtFrom" maxlength="10" cssClass="dateYMD" cssStyle="width:90px;" placeholder="yyyymmdd" />～
					<s:textfield key="targetDtTo" maxlength="10" cssClass="dateYMD" cssStyle="width:90px;" placeholder="yyyymmdd" />
					<br><label for="main_hyoji_Dt" style="float:left;" class="error" generated="true" /></label>
				</td>
			</tr>
			</table>
			<span class="required_remark">
				<span class="font10">*</span> <span class="font3">必須</span><br>
			</span>
			<br>

			<div style="text-align: right;">
			&nbsp;<input type="button" id="btnPrint" class="btnSubmit" value=" 印 刷 " />
			&nbsp;<input type="button" id="btnClose" class="btnSubmit" value="  閉じる ">
			</div>

			<iframe src="" width="0px" height="0px" name="tb044_reception_list_print_frame" frameborder="0"></iframe>

			<s:hidden key="kokyakuIdResultNm" value="" />
			<s:hidden key="kokyakuNmResultNm" value="" />
			<s:hidden key="userContext" value="" />

			<s:tokenCheck displayId="TB044" />
		</div>
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>

