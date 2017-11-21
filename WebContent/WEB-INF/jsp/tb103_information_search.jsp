<%--*****************************************************
	お知らせ検索画面
	作成者：増田成
	作成日：2014/06/18
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		var TARGET_REAL_ESTATE = "20";
		var TARGET_CONTRACTOR = "30";
		var TARGET_OUTSOURCER = "40";

		$(function() {
			window.focus();

			<%-- 確認ダイアログ(初期値は空) --%>
			var confirmation = function() {return true;};

			var validation = $("#main")
			.validate({
				ignore:
					".ignore *",
				stepValidation: true,
				customAddError: function() {
					if ($("#alert").length < 1) {
						alert(ART0015);
					}
				},
				submitHandler: function(form) {
					if (!confirmation()) {
						return false;
					}
					form.submit();
				}
			});

			fnc_target_change(0);

			//******************************************************
			// 新規登録ボタン押下時
			//******************************************************
			$('#btnNewEntry').click(function(e) {
				$('#main').prop('action', '<s:url action="informationEntryInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});
			//******************************************************
			// 検索ボタン押下時
			//******************************************************
			$('#btnSearch').click(function(e) {
				$('#main').prop('action', '<s:url action="informationSearch" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});
			//******************************************************
			// クリアボタン押下時
			//******************************************************
			$('#btnClear').click(function(e) {
				$('input[type=radio]').attr("checked",false);
				$('input[type=text]').val("");
				$('input[type=checkbox]').attr("checked",false);

				$("[placeholder]", "#main").each(function(i,e) {
					$(e).val($(e).data('placeholder-string'));
					if (isValidPlaceHolder == false) {
						$(e).css('color', 'silver');
					}
				});

				$(":input").removeClass('error');
				$("label.error").html('');

				fnc_target_change(1);
			});
			//******************************************************
			// 「選択」ボタン押下時 ※表示対象
			//******************************************************
			$('#btnTargetSelect').click(function(e) {
				var target = $('input[name="condition.target"]:checked').val();
				var kokyakuId = $('#main_condition_kokyakuId').val();
				var gyoshaCd = $('#main_condition_gyoshaCd').val();

				var winName = "";
				var action = "";

				if (target == TARGET_REAL_ESTATE) {
					// 不動産・管理会社の場合
					winName = 'tb_select_real_estate_win';
					action = '<s:url action="realEstateAgencyInit" />';

					$('#main_kokyakuIdResultNm').val('condition.kokyakuId');
					$('#main_kokyakuNmResultNm').val('condition.kokyakuNm');

					// 選択画面へ遷移時のみ設定変更（先頭１文字を除くID）
					$('#main_condition_kokyakuId').val(kokyakuId.substring(1,9));
				} else if(target == TARGET_CONTRACTOR) {
					// 依頼業者の場合
					winName = 'tb_select_contractor_win';
					action = '<s:url action="contractorInit" />';

					$('#main_gyoshaCdResultNm').val('condition.gyoshaCd');
					$('#main_gyoshaNmResultNm').val('condition.gyoshaNm');

					// 選択画面へ遷移時のみ設定変更（先頭１文字を除くコード）
					$('#main_condition_gyoshaCd').val(gyoshaCd.substring(1,9));
				} else if (target == TARGET_OUTSOURCER) {
					// 委託会社の場合
					winName = 'tb_select_outsourcer_win';
					action = '<s:url action="outsourcerInit" />';

					$('#main_kaishaIdResultNm').val('condition.kaishaId');
					$('#main_kaishaNmResultNm').val('condition.kaishaNm');
				}

				var w = createWindow(winName);

				$('#main').addClass('ignore');

				$('#main').prop('action', action);
				$('#main').prop('target', winName);

				$('#main').submit();

				$('#main').removeClass('ignore');

				// 選択画面へ遷移後に設定を戻す（先頭１文字を含むID,コード）
				if (target == TARGET_REAL_ESTATE) {
					$('#main_condition_kokyakuId').val(kokyakuId);
				} else if(target == TARGET_CONTRACTOR) {
					$('#main_condition_gyoshaCd').val(gyoshaCd);
				}
			});
			//******************************************************
			// 「クリア」ボタン押下時 ※表示対象
			//******************************************************
			$('#btnTargetClear').click(function(e) {
				fnc_target_change(1);
			});
			//******************************************************
			// 表示対象ラジオボタン変更時
			//******************************************************
			$('input[name="condition.target"]:radio').change(function (e) {
				fnc_target_change(1);
			});
			
			//*******************************************
			// 修正リンク押下時処理
			//*******************************************
			$('.linkEdit').click(function(e) {
				// 同一ウィンドウにお知らせ登録画面を表示
				$('#main').addClass('ignore');

				$('#seqNo').val($(this).attr('seqNo'));

				$('#main').prop('action', '<s:url action="informationEntryUpdateInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();

				$('#main').removeClass('ignore');
			});
			
			//*******************************************
			// 削除リンク押下時処理
			//*******************************************
			$('.linkDelete').click(function(e) {
				if (!confirm(jQuery.validator.format(INF0001, "お知らせ", "削除"))) {
					return false;
				}

				$('#seqNo').val($(this).attr('seqNo'));

				// お知らせ削除処理
				$('#main').prop('action', '<s:url action="informationDelete" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});
			
			//*******************************************
			// 表示条件ラジオボタン変更時 実行関数
			//*******************************************
			function fnc_target_change(delFlg) {
				var target = $('input[name="condition.target"]:checked').val();

				// 削除フラグが1の場合、顧客ＩＤ、業者コード、顧客名、業者名を初期化
				if (delFlg == 1) {
					$('#main_condition_kokyakuId').val('');
					$('#main_condition_kokyakuNm').val('');
					$('#main_condition_gyoshaCd').val('');
					$('#main_condition_gyoshaNm').val('');
					$('#main_condition_kaishaId').val('');
					$('#main_condition_kaishaNm').val('');
				}

				// 表示条件設定（表示条件選択別）
				if (target == TARGET_REAL_ESTATE) {
					// 「不動産・管理会社」選択時
					$('#main_condition_kokyakuId').css('display', 'inline');
					$('#main_condition_kokyakuNm').css('display', 'inline');
					$('#main_condition_gyoshaCd').css('display', 'none');
					$('#main_condition_gyoshaNm').css('display', 'none');
					$('#main_condition_kaishaId').css('display', 'none');
					$('#main_condition_kaishaNm').css('display', 'none');
					$('#btnTargetSelect').prop('disabled', false);
					$('#btnTargetClear').prop('disabled', false);
				} else if (target == TARGET_CONTRACTOR) {
					// 「業者」選択時
					$('#main_condition_kokyakuId').css('display', 'none');
					$('#main_condition_kokyakuNm').css('display', 'none');
					$('#main_condition_gyoshaCd').css('display', 'inline');
					$('#main_condition_gyoshaNm').css('display', 'inline');
					$('#main_condition_kaishaId').css('display', 'none');
					$('#main_condition_kaishaNm').css('display', 'none');
					$('#btnTargetSelect').prop('disabled', false);
					$('#btnTargetClear').prop('disabled', false);
				} else if (target == TARGET_OUTSOURCER) {
					// 「委託会社」選択時
					$('#main_condition_kokyakuId').css('display', 'none');
					$('#main_condition_kokyakuNm').css('display', 'none');
					$('#main_condition_gyoshaCd').css('display', 'none');
					$('#main_condition_gyoshaNm').css('display', 'none');
					$('#main_condition_kaishaId').css('display', 'inline');
					$('#main_condition_kaishaNm').css('display', 'inline');
					$('#btnTargetSelect').prop('disabled', false);
					$('#btnTargetClear').prop('disabled', false);
				} else {
					// 「全て」または未選択時
					$('#main_condition_kokyakuId').css('display', 'none');
					$('#main_condition_kokyakuNm').css('display', 'none');
					$('#main_condition_gyoshaCd').css('display', 'none');
					$('#main_condition_gyoshaNm').css('display', 'none');
					$('#main_condition_kaishaId').css('display', 'none');
					$('#main_condition_kaishaNm').css('display', 'none');
					$('#btnTargetSelect').prop('disabled', true);
					$('#btnTargetClear').prop('disabled', true);
				}
			}
		});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">お知らせメンテナンス</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<%-- 検索条件 ここから --%>
			<div class="content">
				<h2>検索条件</h2>
				<table>
					<tr>
						<th width="10%">表示対象</th>
						<td width="90%">
							<s:radio key="condition.target" list="targetList" listKey="comCd" listValue="externalSiteVal" />
							<%-- 初期表示、権限リスト変更時にテキストボックスの表示／非表示をJavaScriptで制御 --%>
								<s:textfield key="condition.kokyakuId" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
								<s:textfield key="condition.kokyakuNm" cssClass="readOnlyText" cssStyle="width:200px;" readonly="true" />
								<s:textfield key="condition.gyoshaCd" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
								<s:textfield key="condition.gyoshaNm" cssClass="readOnlyText" cssStyle="width:200px;" readonly="true" />
								<s:textfield key="condition.kaishaId" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
								<s:textfield key="condition.kaishaNm" cssClass="readOnlyText" cssStyle="width:200px;" readonly="true" />
							&nbsp;<input type="button" id="btnTargetSelect" class="btnDialog" value=" 選 択 " />
							&nbsp;<input type="button" id="btnTargetClear" class="btnDialog" value=" クリア " />
						</td>
					</tr>
					<tr>
						<th>表示条件</th>
						<td>
							<s:checkbox key="condition.hyojiJoken" fieldValue="1" value="%{condition.isCheckedFlg('1')}" />&nbsp;期限切れを表示
							<s:checkbox key="condition.hyojiJoken" fieldValue="2" value="%{condition.isCheckedFlg('2')}" />&nbsp;表示中（本日含む）
							<s:checkbox key="condition.hyojiJoken" fieldValue="3" value="%{condition.isCheckedFlg('3')}" />&nbsp;表示予定（未来）

						</td>
					</tr>
				</table>
				<br>
				<div class="right">
					&nbsp;<input type="button" id="btnNewEntry" class="btnSubmit" value=" 新規登録 " />
					&nbsp;<input type="button" id="btnSearch" class="btnSubmit" value=" 検 索 " />
					&nbsp;<input type="button" id="btnClear" class="btnSubmit" value=" クリア " />
					&nbsp;<input type="button" id="btnClose" class="btnSubmit" value=" 閉じる " />
				</div>
			</div>
			<%-- 検索条件 ここまで --%>

			<%-- 検索結果 ここから --%>
			<s:if test="isResultDisplay()">
			<div class="content">
				<h2>検索結果</h2>
				<c:import url="/WEB-INF/common/jsp/pager.jsp">
					<c:param name="condition" value="condition" />
					<c:param name="displayCount" value="0" />
				</c:import>

				<table>
					<thead>
						<tr>
							<th width="4%"></th>
							<th width="10%">表示対象</th>
							<th width="10%">登録日</th>
							<th width="20%">表示期間</th>
							<th width="52%">表示内容</th>
							<th width="4%"></th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="resultList" status="i">
						<tr <s:if test="isHyojiKikan()">class="highlight"</s:if><s:else>class="${f:odd(i.index, 'odd,even')}"</s:else>>
							<td><a href="#" seqNo="${f:out(seqNo)}" class="linkEdit">修正</a></td>
							<td align="center">${f:br(targetDisplay)}</td>
							<td align="center">${f:dateFormat(creDt, "yyyy/MM/dd")}</td>
							<td align="center">${f:dateFormat(startDt, "yyyy/MM/dd")}～${f:dateFormat(endDt, "yyyy/MM/dd")}</td>
							<td>${f:out(message)}</td>
							<td><a href="#" seqNo="${f:out(seqNo)}" class="linkDelete">削除</a></td>
						</tr>
						</s:iterator>
					</tbody>
				</table>

				<c:import url="/WEB-INF/common/jsp/pager.jsp">
					<c:param name="condition" value="condition" />
					<c:param name="href" value="informationSearchByPager.action" />
					<c:param name="displayCount" value="0" />
				</c:import>

				<input type="hidden" id="seqNo" name="seqNo" value="" />
			</div>
			</s:if>
			<%-- 検索結果 ここまで --%>

			<%-- 選択画面の戻り --%>
			<s:hidden key="kokyakuIdResultNm" value="" />
			<s:hidden key="kokyakuNmResultNm" value="" />
			<s:hidden key="gyoshaCdResultNm" value="" />
			<s:hidden key="gyoshaNmResultNm" value="" />
			<s:hidden key="kaishaIdResultNm" value="" />
			<s:hidden key="kaishaNmResultNm" value="" />

			<s:tokenCheck displayId="TB103" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
