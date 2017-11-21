<%--*****************************************************
	業者選択画面
	作成者：岩田
	作成日：2014/06/05
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="simple.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			var validation = $("#main")
			.validate({
				rules: {
					"condition.gyoshaNm": {byteVarchar: 100}
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
			// クリアボタン押下時
			//******************************************************
			$('#btnClear').click(function(e) {
				fnc_clear();
			});

			//******************************************************
			// 検索ボタン押下時
			//******************************************************
			$('#btnSearch').click(function(e) {
				$('.dateYMD').each(function(i,e) {
					var date = convertDate($(this).val());
					if (date != null) {
						$(this).val($.datepicker.formatDate($(e).datepicker("option", "dateFormat"), date));
					}
				});

				$('#main').prop('action', '<s:url action="contractorSearch" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});

		});

		//*******************************************
		// クリアボタン押下時処理
		//*******************************************
		function fnc_clear() {
			$('input[type=checkbox]').removeAttr('checked');
			$('input[type=text]').val('');
			$("input[type=radio][value='1']").prop('checked', true);
			$('select').prop('selectedIndex', 0);

			$("[placeholder]", "#main").each(function(i,e) {
				$(e).val($(e).data('placeholder-string'));
				$(e).css('color', 'silver');
			});

			$(":input").removeClass('error');
			$("label.error").html('');


		}

		//*******************************************
		// 業者リンク押下時 実行関数
		//*******************************************
		function fnc_detail_link(index) {
			<%-- 親画面の存在チェック --%>
			if(parent.window.opener && ! parent.window.opener.closed){
				<%-- 親画面のname値を取得 --%>
				var gyoshaCdResultNm = $("#main_gyoshaCdResultNm").val();
				var gyoshaNmResultNm = $("#main_gyoshaNmResultNm").val();

				<%-- 親画面への値渡し --%>
				parent.opener.$("input[name='" + gyoshaCdResultNm + "']").val($("#gyoshaCd_" + index).text());
				parent.opener.$("input[name='" + gyoshaNmResultNm + "']").val($("#gyoshaNm_" + index).text());
			}
			window.close();
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">業者選択</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
		<div id="contents">
			<h1>業者選択</h1>
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
				<h2>業者検索</h2>
				<table>
					<tr>
						<td colspan="2">
							<table width="100%">
							<tbody>
								<tr>
									<th width="20%">業者CD</th>
									<td>G&nbsp;<s:textfield key="condition.gyoshaCd" maxlength="5" cssClass="hankaku" cssStyle="width:100px;" placeholder="(半角数字)" />
									　<span class="font5">※後方一致検索</span></td>
								</tr>
								<tr>
									<th width="20%">業者名</th>
									<td>
										<s:textfield key="condition.gyoshaNm" maxlength="100" cssClass="zenhankaku" cssStyle="width:250px;" placeholder="(全角)" />
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
				<h2>業者一覧</h2>
				<table>
					<thead>
						<tr>
							<th width="5%" rowspan="2">NO</th>
							<th width="10%" rowspan="2">業者CD</th>
							<th width="">業者名</th>
						</tr>
						<tr>
							<th>住所</td>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="resultList" status="i">
						<tr class="odd">
							<th rowspan="2">${i.index + 1}</th>
							<td rowspan="2"><a href="JavaScript:fnc_detail_link('${i.index}');"">
							<span class="fontlink2" id="gyoshaCd_${i.index}">${f:out(gyoshaCd)}</span></a></td>
							<td><span id="gyoshaNm_${i.index}">${f:out(gyoshaNm)}</span></td>
						</tr>
						<tr class="odd">
							<td>${f:out(jusho1)} ${f:out(jusho2)} ${f:out(jusho3)} ${f:out(jusho4)} ${f:out(jusho5)}</td>
						</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			</s:if>
			<%-- 検索結果 ここまで --%>
		</div>
			<s:hidden key="gyoshaCdResultNm" />
			<s:hidden key="gyoshaNmResultNm" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
