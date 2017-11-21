<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
	</tiles:putAttribute>
	<tiles:putAttribute name="title">システムエラー</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div id="contents">
			<center>
				<br><br><br>
				<br><br><br>
				<span class="errorMsg">エラーが発生しました。システム管理者に連絡して下さい。 </span>
				<br>&nbsp;&nbsp;発生日時&nbsp;：&nbsp;${f:currentDate('yyyy/MM/dd HH:mm:ss')}
				<br><br><br>
				<br><br><br>
				<input type="button" class="btnSubmit" value=" 閉じる " onclick="window.close()">
				<br><br><br>
			</center>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
