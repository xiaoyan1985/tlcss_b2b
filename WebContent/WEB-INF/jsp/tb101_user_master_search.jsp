<%--*****************************************************
	ユーザーマスタ検索画面
	作成者：仲野
	作成日：2014/05/27
	更新日：2015/09/15 山村 会社IDの表示処理追加
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			var validation = $("#main")
			.validate({
				rules: {
					"condition.loginId": {byteVarchar: 256},
					"condition.userNm": {byteVarchar: 40}
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
			// 新規登録ボタン押下時
			//******************************************************
			$('#btnNewEntry').click(function(e) {
				// 同一ウィンドウにユーザーマスタ登録画面を表示
				$('#main').addClass('ignore');

				$('#main').prop('action', '<s:url action="userMasterEntryInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();

				$('#main').removeClass('ignore');
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
				$('#main').prop('action', '<s:url action="userMasterSearch" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});
		});

		//*******************************************
		// クリアボタン押下時処理
		//*******************************************
		function fnc_clear() {
			$('input[type=text]').val('');
			$('select').prop('selectedIndex', 0);

			$("[placeholder]", "#main").each(function(i,e) {
				$(e).val($(e).data('placeholder-string'));
				if (isValidPlaceHolder == false) {
					$(e).css('color', 'silver');
				}
			});

			$(":input").removeClass('error');
			$("label.error").html('');
		}

		//*******************************************
		// 修正リンク押下時 実行関数
		//*******************************************
		function fnc_modify_link(seqNo) {
			// 同一ウィンドウにユーザーマスタ登録画面を表示
			$('#main').addClass('ignore');

			$('#main_seqNo').val(seqNo);

			$('#main').prop('action', '<s:url action="userMasterEntryUpdateInit" />');
			$('#main').prop('target', '_self');

			$('#main').submit();

			$('#main').removeClass('ignore');
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">ユーザーマスタメンテナンス</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<%-- 検索条件 ここから --%>
			<div class="content">
				<h2>検索条件</h2>
				<table>
					<tr>
						<th width="10%">ログインID</th>
						<td>
							<s:textfield key="condition.loginId" cssClass="hankaku" cssStyle="width:330px;" maxlength="256" />　<span class="caution">※あいまい検索</span>
						</td>
						<th width="10%">権限</th>
						<td width="30%">
							<s:select key="condition.role" list="roleList" listKey="comCd" listValue="externalSiteVal" emptyOption="true" />
						</td>
					</tr>

					<tr>
						<th>ユーザー名</th>
						<td colspan="3">
							<s:textfield key="condition.userNm" cssClass="zenhankaku" cssStyle="width:330px;" maxlength="40" />　<span class="caution">※あいまい検索</span>
						</td>
					</tr>
					<tr>
						<td colspan="4"><br></td>
					</tr>
				</table>

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
							<th width="30%">ログインID</th>
							<th width="">ユーザー名</th>
							<th width="13%">権限</th>
							<th width="14%">顧客ＩＤ/業者コード/会社ＩＤ</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="resultList" status="i">
						<tr <s:if test="isDeleted()">class="deleted"</s:if><s:else>class="${f:odd(i.index, 'odd,even')}"</s:else>>
							<td><a href="JavaScript:fnc_modify_link('${f:out(seqNo)}');">修正</a></td>
							<td>${f:out(loginId)}</td>
							<td>${f:out(userNm)}</td>
							<td align="center">${f:out(roleNm)}</td>
							<td>${f:out(kokyakuId)}${f:out(gyoshaCd)}${f:out(kaishaId)}</td>
						</s:iterator>
					</tbody>
				</table>

				<c:import url="/WEB-INF/common/jsp/pager.jsp">
					<c:param name="condition" value="condition" />
					<c:param name="href" value="userMasterSearchByPager.action" />
					<c:param name="displayCount" value="0" />
				</c:import>

				<s:hidden key="seqNo" />
			</div>
			</s:if>
			<%-- 検索結果 ここまで --%>

			<s:tokenCheck displayId="TB101" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
