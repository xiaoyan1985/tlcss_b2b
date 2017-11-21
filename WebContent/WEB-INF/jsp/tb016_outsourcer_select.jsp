<%--*****************************************************
	委託会社選択画面
	作成者：山村
	作成日：2015/09/08
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="simple.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			// 入力チェック
			var validation = $("#main")
			.validate({
				rules: {
					"condition.kaishaNm": {byteVarchar: 100}
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

			//*******************************************
			// 委託会社リンク押下時
			//*******************************************
			$('.setKaishaInfo').click(function(e) {
				// 親画面の存在チェック（親画面が存在する場合のみ実施）
				if(parent.window.opener && ! parent.window.opener.closed){
					// 親画面のname値を取得
					var kaishaIdResultNm = $("#main_kaishaIdResultNm").val();
					var kaishaNmResultNm = $("#main_kaishaNmResultNm").val();

					// 親画面への値渡し
					parent.opener.$("input[name='" + kaishaIdResultNm + "']").val($("#kaishaId_" + $(this).attr('targetNumber')).text());
					parent.opener.$("input[name='" + kaishaNmResultNm + "']").val($("#kaishaNm_" + $(this).attr('targetNumber')).text());
				}
				// ウィンドウを閉じる
				window.close();
			});

			//******************************************************
			// クリアボタン押下時
			//******************************************************
			$('#btnClear').click(function(e) {
				$('input[type=text]').val('');

				$("[placeholder]", "#main").each(function(i,e) {
					$(e).val($(e).data('placeholder-string'));
					$(e).css('color', 'silver');
				});

				$(":input").removeClass('error');
				$("label.error").html('');
			});

			//******************************************************
			// 検索ボタン押下時
			//******************************************************
			$('#btnSearch').click(function(e) {
				$('#main').prop('action', '<s:url action="outsourcerSearch" />');
				$('#main').prop('target', '_self');
				$('#main').submit();
			});
		});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">委託会社選択</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
		<div id="contents">
			<h1>委託会社選択</h1>
			<br>
			<div id="successMsgArea">
			<s:if test="hasActionMessages()">
				<span class="successMsg"><s:actionmessage escape="false" /></span>
			</s:if>
			</div>
			<div id="errorMsgArea">
			<s:if test="hasActionErrors()">
				<span class="errorMsg"><s:actionerror escape="false" /></span>
			</s:if>
			</div>
			<%-- 検索条件 ここから --%>
			<div class="content">
				<h2>委託会社検索</h2>
				<table>
					<tr>
						<td colspan="2">
							<table width="100%">
							<tbody>
								<tr>
									<th width="20%">会社ＩＤ</th>
									<td><s:textfield key="condition.kaishaId" maxlength="4" cssClass="hankaku" cssStyle="width:100px;" placeholder="(半角数字)" />
									　<span class="font5">※後方一致検索</span></td>
								</tr>
								<tr>
									<th width="20%">会社名</th>
									<td>
										<s:textfield key="condition.kaishaNm" maxlength="100" cssClass="zenhankaku" cssStyle="width:250px;" placeholder="(全角)" />
										　<span class="font5">あいまい検索</span>
									</td>
								</tr>
							</tbody>
							</table>
						</td>
					</tr>
				</table>

				<div class="right">
					&nbsp;<input type="button" id="btnSearch" class="btnSubmit" value=" 検 索 " />
					&nbsp;<input type="button" id="btnClear" class="btnSubmit" value=" クリア " />
					&nbsp;<input type="button" id="btnClose" class="btnSubmit" value=" 閉じる " />
				</div>

				<input type="hidden" id="toiawaseNo" name="toiawaseNo" value="" />

			</div>
			<%-- 検索条件 ここまで --%>

			<%-- 検索結果 ここから --%>
			<s:if test="resultList != null">
			<div class="content">
				<h2>委託会社一覧</h2>
				<table>
					<thead>
						<tr>
							<th width="5%" rowspan="2">NO</th>
							<th width="10%" rowspan="2">会社ＩＤ</th>
							<th>会社名</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="resultList" status="i">
						<tr class="${f:odd(i.index, 'odd,even')}">
							<th>${i.index + 1}</th>
							<td><a href="#" targetNumber="${i.index}" class="setKaishaInfo">
							<span class="fontlink2" id="kaishaId_${i.index}">${f:out(kaishaId)}</span></a></td>
							<td><span id="kaishaNm_${i.index}">${f:out(kaishaNm)}</span></td>
						</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			</s:if>
			<%-- 検索結果 ここまで --%>
		</div>
			<s:hidden key="kaishaIdResultNm" />
			<s:hidden key="kaishaNmResultNm" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
