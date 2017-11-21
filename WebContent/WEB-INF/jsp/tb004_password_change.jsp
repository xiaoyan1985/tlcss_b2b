﻿<%--*****************************************************
	パスワード変更画面
	作成者：仲野
	作成日：2014/04/28
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/smartphone.css">

		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			<%-- 確認ダイアログ(初期値は空) --%>
			var confirmation = function() {return true;};

			$("#main").validate({
				ignore:
					".ignore *",
				rules: {
					currentPassword:		{required: true, rangelength: [8, 20], byteVarchar: 20},
					newPassword:			{required: true, rangelength: [8, 20], byteVarchar: 20, notEqualTo: "#main_currentPassword"},
					confirmNewPassword:		{required: true, rangelength: [8, 20], byteVarchar: 20, equalTo: "#main_newPassword"}
				},
				messages: {
					newPassword:			{notEqualTo: ART0023},
					confirmNewPassword:		{equalTo: ART0024}
				},
				invalidHandler: function(form, validator) {
					alert(ART0015);
				},
				submitHandler: function(form) {
					if (!confirmation()) {
						return false;
					}
					$(":input").prop("disabled", false);
					$("input:button").prop("disabled", true);
					form.submit();
					$(":input").prop("disabled", true);
				}
			});

			//******************************************************
			// 変更ボタン押下時
			//******************************************************
			$('#btnChange').click(function(e) {
				confirmation = function() {
					return confirm(jQuery.validator.format(INF0001, "パスワード", "変更"));
				};
				// 同一ウィンドウにメニュー画面を表示
				$('#main').prop('action', '<s:url action="passwordChangeUpdate" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});

			//******************************************************
			// メニューボタン押下時
			//******************************************************
			$('#btnMenu').click(function(e) {
				confirmation = function() {return true;};

				// 同一ウィンドウにメニュー画面を表示
				$('#main').addClass('ignore');

				$('#main').prop('action', '<s:url action="menuInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});
		});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">パスワード変更</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<br>
			<div class="grid grid-pad">
				<div class="col-2-left2">ログインＩＤ</div>
				<div class="col-2-right2">${f:out(userContext.loginId)}</div>
				<div class="col-2-left2"><span class="font10">* </span>現在のパスワード<span class="required">必須</span></div>
				<div class="col-2-right2">
					<s:password key="currentPassword" cssClass="hankaku" cssStyle="width:150px;" maxlength="20" autocapitalize="off" />
				</div>

				<div class="col-2-left2"><span class="font10">* </span>新パスワード<span class="required">必須</span> </div>
				<div class="col-2-right2">
					<s:password key="newPassword" cssClass="hankaku" cssStyle="width:150px;" maxlength="20" autocapitalize="off" />
				</div>
				<div class="col-2-left2"><span class="font10">* </span>新パスワード（確認）<span class="required">必須</span></div>
				<div class="col-2-right2">
					<s:password key="confirmNewPassword" cssClass="hankaku" cssStyle="width:150px;" maxlength="20" autocapitalize="off" />
				</div>

			</div>
			<span class="required_remark">
				<span class="font10">*</span> <span class="font3">必須</span><br>
			</span>
			<br>

			<span class="remarks">
				※現在のパスワードを変更する場合は、新パスワード、新パスワード（確認）も入力して下さい。
				<br>
				※新パスワードは以下のルールで入力して下さい。
				<br>　・8文字以上20文字以内であること
				<br>　・英字、数字、記号から2種類以上利用すること
				<br>　・現在のパスワードと異なること
			</span>


			<br><br>
			<div style="text-align: right;">
			&nbsp;<input type="button" id="btnChange" class="btnSubmit" value=" 変 更 " />
			<s:if test="isPasswordLimitNear()">
			&nbsp;<input type="button" id="btnMenu" class="btnSubmit" value=" メニュー ">
			</s:if>
			<s:if test="isFromMenu()">
			&nbsp;<input type="button" id="btnClose" class="btnSubmit" value="  閉じる ">
			</s:if>
			</div>
			<br>

			<s:iterator value="%{#parameters}" var="map">
				<s:if test="#map.key != 'loginId' && #map.key != 'passwd' && #map.key != 'currentLoginId' && #map.key != 'dispMode' && #map.key != 'currentPassword'&& #map.key != 'newPassword'&& #map.key != 'confirmNewPassword' && #map.key != 'struts.token.name' && #map.key != 'token' && #map.key != 'displayId'">
				<input type="hidden" name="${map.key}" value="<s:property value="%{#parameters[#map.key]}" />" />
				</s:if>
			</s:iterator>

			<s:hidden key="dispMode" />

			<s:tokenCheck displayId="TB004" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>

