<%--*****************************************************
	報告書公開設定
	作成者：山村
	作成日：2015/08/11
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//

		$(function() {
			// フォーカスを当てる
			window.focus();
			
			// 確認ダイアログ
			var confirmation = function() {
				return confirm(jQuery.validator.format(INF0001, "${gamenNm}" , "公開"));
			};
			
			// 帳票出力領域の設定
			$('a.media').media({width:900, height:750});
			
			//******************************************************
			// 公開ボタン押下時
			//******************************************************
			$('#btn_disclosure').click(function(e) {
				if (!confirmation()) {
					return false;
				}
				$('#main').prop('action', '<s:url action="reportDisclosureUpdate" />');
				$('#main').prop('target', '_self');
				$('#main').submit();
			});
			
			<%-- 完了メッセージがある場合、親画面をリロード --%>
			<s:if test="hasActionMessages()">
				if(window.opener && !window.opener.closed) {
					// 親ウィンドウが存在すれば、親ウィンドウのリロード処理
					window.opener.fnc_restore();
				}
			</s:if>
		});

		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="windowTitle">${gamenNm}　公開設定</tiles:putAttribute>
	<tiles:putAttribute name="title">${gamenNm}　公開設定</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<s:form id="main" method="POST">
		<div class="content">
		<table>
			<tr><td colspan="2" >&nbsp;</td></tr>
			<tr>
				<td width="50%" >
					<span class="font2">&nbsp;&nbsp;${f:out(gamenNm)}の内容を確認し、公開設定を行って下さい。</span><br><br>&nbsp;
				</td>
				<td align="right" >
					&nbsp;<input type="button" value="公開" class="btnSubmit" id="btn_disclosure">&nbsp;
					&nbsp;<input type="button" value=" 閉じる " class="btnSubmit" id="btnClose">
				</td>
			</tr>
		</table>
		<br>
		
		<iframe src="<s:url action="reportDisclosureDownload" />?${pdfDownloadParamter}" id="print_if" style="width:100%; height:750px;"></iframe>
		
		</div>
		<s:hidden key="toiawaseNo"/>
		<s:hidden key="toiawaseRirekiNo"/>
		<s:hidden key="kokyakuId" />
		<s:hidden key="toiawaseUpdDt"/>
		<s:hidden key="sagyoJokyoUpdDt"/>
		<s:hidden key="senderNm1"/>
		<s:hidden key="senderNm2"/>
		<s:hidden key="senderAddress"/>
		<s:hidden key="senderTelNo"/>
		<s:hidden key="senderFaxNo"/>
		<s:hidden key="printKbn"/>
		<s:hidden key="pdfUrl"/>
	</s:form>

	</tiles:putAttribute>
</tiles:insertDefinition>