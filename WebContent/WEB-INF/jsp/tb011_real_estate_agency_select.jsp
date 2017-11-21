<%--*****************************************************
	管理会社選択画面
	作成者：岩田
	作成日：2014/06/04
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
					"condition.kokyakuId": {byteVarchar: 9},
					"condition.kanaNm1": {byteVarchar: 40},
					"condition.kanaNm2": {byteVarchar: 40}
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

				$('#main').prop('action', '<s:url action="realEstateAgencySearch" />');
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
		// 顧客リンク押下時 実行関数
		//*******************************************
		function fnc_detail_link(index) {
			<%-- 親画面の存在チェック --%>
			if(parent.window.opener && ! parent.window.opener.closed){

				if ($("#main_kokyakuListNmResultNm").val() == null || $("#main_kokyakuListNmResultNm").val() == '') {
					<%-- 親画面のname値を取得 --%>
					var kokyakuIdResultNm = $("#main_kokyakuIdResultNm").val();
					var kokyakuNmResultNm = $("#main_kokyakuNmResultNm").val();

					<%-- 親画面への値渡し --%>
					parent.opener.$("input[name='" + kokyakuIdResultNm + "']").val($("#kokyakuId_" + index).text());
					parent.opener.$("input[name='" + kokyakuNmResultNm + "']").val($("#kokyakuNm_" + index).text());

					<%-- 親画面にreload()ファンクションがある場合、実行 --%>
					if (window.opener.setKokyakuNodeList) {
						window.opener.setKokyakuNodeList();
					}
				} else {
					<%-- 親画面のname値を取得 --%>
					var kokyakuListNmResultNm = $("#main_kokyakuListNmResultNm").val();
					<%-- 親画面への値渡し --%>
					window.opener.addKokyakuList($("#kokyakuId_" + index).text() + " " + $("#kokyakuNm_" + index).text());
				}
			}
			window.close();
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">管理会社選択</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
		<div id="contents">
			<h1>管理会社選択</h1>
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
				<h2>管理会社検索</h2>
				<table>
					<tr>
						<td colspan="2">
							<table width="100%">
							<tbody>
								<tr>
									<th width="20%">顧客ＩＤ</th>
									<td>C&nbsp;<s:textfield key="condition.kokyakuId" maxlength="9" cssClass="hankaku" cssStyle="width:100px;" placeholder="(半角数字)" />
									　<span class="font5">※後方一致検索</span></td>
								</tr>
								<tr>
									<th width="20%">カナ名称</th>
									<td>
										<s:textfield key="condition.kanaNm1" maxlength="40" cssClass="zenhankaku" cssStyle="width:160px;" placeholder="(全角カナ)" />
										<s:textfield key="condition.kanaNm2" maxlength="40" cssClass="zenhankaku" cssStyle="width:140px;" placeholder="(全角カナ)" />
										　<span class="font5">あいまい検索</span>
									</td>
								</tr>
							</tbody>
							</table>
							<s:if test="condition.isTokai()">※TORESに登録されている管理会社のみ検索します。</s:if>
							<s:textfield key="condition.isTokai" cssClass="readOnlyText" readonly="true" />
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
				<h2>管理会社一覧</h2>
				<table>
					<thead>
						<tr>
							<th width="5%" rowspan="2">NO</th>
							<th width="10%" rowspan="2">顧客ＩＤ</th>
							<th width="">漢字名称</th>
						</tr>
						<tr>
							<th>住所</td>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="resultList" status="i">
						<tr class="${f:odd(i.index, 'odd,even')}">
							<th rowspan="2">${i.index + 1}</th>
							<td rowspan="2"><a href="JavaScript:fnc_detail_link('${i.index}');">
							<span class="fontlink2" id="kokyakuId_${i.index}">${f:out(kokyakuId)}</span></a></td>
							<td><span id="kokyakuNm_${i.index}">${f:out(kanjiNm)}</span></td>
						</tr>
						<tr class="${f:odd(i.index, 'odd,even')}">
							<td>${f:out(jusho1)} ${f:out(jusho2)} ${f:out(jusho3)} ${f:out(jusho4)} ${f:out(jusho5)} ${f:out(roomNo)}</td>
						</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			</s:if>
			<%-- 検索結果 ここまで --%>
		</div>
			<s:hidden key="kokyakuIdResultNm" />
			<s:hidden key="kokyakuNmResultNm" />
			<s:hidden key="kokyakuListNmResultNm" />
			<s:hidden key="condition.tokai" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
