<%--*****************************************************
	ログイン画面
	作成者：仲野
	作成日：2014/04/22
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="simple.layout">
	<tiles:putAttribute name="script">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/smartphone.css">

		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			$("#main").validate({
				rules: {
					loginId:	{required: true, byteVarchar: 256},
					passwd:		{required: true, byteVarchar: 20}
				},
				invalidHandler: function(form, validator) {
					alert(ART0015);
				},
				submitHandler: function(form) {
					$(":input").prop("disabled", false);
					$("input:button").prop("disabled", true);
					form.submit();
					$(":input").prop("disabled", true);
				}
			});

			//******************************************************
			// ログインボタン押下時
			//******************************************************
			$('#btnLogin').click(function(e) {
				$('#main').submit();
			});

			//******************************************************
			// クリアボタン押下時
			//******************************************************
			$('#btnClear').click(function(e) {
				$('#main_loginId').val('');
				$('#main_passwd').val('');
			});

			// タイトルの初期化処理
			fnc_init_login_title();
		});

		//******************************************************
		// ログインタイトル初期処理 実行関数
		//******************************************************
		function fnc_init_login_title() {
			if (isSmartPhone == false) {
				// パソコンからのアクセスの場合、<br>を追加
				$("#login_title").prepend("<br><br><br>");
				$("#login_title").append("<br><br>");
				$("#successMsgArea").before("<br>");
				$("#errorMsgArea").after("<br>");
			}
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">Tokai Relief Support Portal System</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main" method="POST" action="login.action" target="_self">
			<div id="contents">
				<div id="login_title">
					<center><font class="login_title">Tokai Relief Support Portal System</font></center>
				</div>
			</div>

			<div id="successMsgArea" style="text-align:center;">
				<s:if test="hasActionMessages()">
					<span class="successMsg"><s:actionmessage escape="false" /></span>
				</s:if>
			</div>
			<div id="errorMsgArea" style="text-align:center;">
				<s:if test="hasActionErrors()">
					<span class="errorMsg"><s:actionerror escape="false" /></span>
				</s:if>
			</div>

			<div id="contents">
				<div id="content">
					<div class="login grid-pad">
						<div class="col-login-left">ログインＩＤ</div>
						<div class="col-login-right"><s:textfield key="loginId" cssClass="hankaku"  maxlength="256" autocapitalize="off" style="width: 235px;" /></div>
						<div class="col-login-left">パスワード</div>
						<div class="col-login-right"><s:password key="passwd" cssClass="hankaku" maxlength="20" autocapitalize="off" style="width: 235px;" /></div>
					</div>

					<br><br>
					<div style="text-align:center;">
						<input type="button" id="btnLogin" class="btnSubmit" value="  ログイン  ">
						&nbsp;<input type="button" id="btnClear" class="btnSubmit" value=" クリア ">
					</div>
					<br>
					<%@ include file="/WEB-INF/common/jsp/footer.jsp" %>
				</div>
			</div>

			<s:iterator value="%{#parameters}" var="map">
				<s:if test="#map.key != 'loginId' && #map.key != 'passwd' && #map.key != 'currentLoginId' && #map.key != 'struts.token.name' && #map.key != 'token' && #map.key != 'displayId'">
				<input type="hidden" name="${map.key}" value="<s:property value="%{#parameters[#map.key]}" />" />
				</s:if>
			</s:iterator>

			<s:hidden key="dispMode" />

			<s:tokenCheck displayId="TB001" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>