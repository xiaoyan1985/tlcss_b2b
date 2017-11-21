<%--*****************************************************
	物件・入居者検索画面
	作成者：増田成
	作成日：2014/06/03
	更新日：2015/09/14 山村 タイトル名変更対応
			2015/09/16 小林 問い合わせ登録ボタン追加
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
				ignore:
					".ignore *",
				rules: {
					"condition.yubinNo": {byteVarchar: 7},
					"condition.kanaNm1": {byteVarchar: 40},
					"condition.kanaNm2": {byteVarchar: 40},
					"condition.kanjiNm1": {byteVarchar: 40},
					"condition.kanjiNm2": {byteVarchar: 40},
					"condition.jusho1": {byteVarchar: 10},
					"condition.jusho2": {byteVarchar: 30},
					"condition.jusho3": {byteVarchar: 30},
					"condition.jusho4": {byteVarchar: 30},
					"condition.jusho5": {byteVarchar: 40},
					"condition.roomNo": {byteVarchar: 20},
					"condition.telNo": {byteVarchar: 15}
				},
				stepValidation: true,
				customAddError: function() {
					if ($("#alert").length < 1) {
						alert(ART0015);
					}
				},
				submitHandler: function(form) {
					var buttonId = $('#main_buttonId').val();
					
					// クリア、検索、閉じるボタンの場合
					if (buttonId == 'btnClear'
						|| buttonId == 'btnSearch'
						|| buttonId == 'btnClose') {
						
						$(":input").prop("disabled", false);
						$("input:button").prop("disabled", true);
						form.submit();
						$(":input").prop("disabled", true);
						
					} else {
					// その他の場合
						form.submit();
					}
					
					$('#main_buttonId').val('');
				}
			});
			//*******************************************
			// 選択リンク押下時処理
			//*******************************************
			$('.selectKokyaku').click(function(e) {
				<%-- 親画面の存在チェック --%>
				if(parent.window.opener && ! parent.window.opener.closed){
					<%-- 親画面のname値を取得 --%>
					var kokyakuIdResultNm = $("#main_kokyakuIdResultNm").val();
					var kokyakuKaishaNmResultNm = $("#main_kokyakuKaishaNmResultNm").val();
					var kokyakuNmResultNm = $("#main_kokyakuNmResultNm").val();
					var kokyakuJushoResultNm = $("#main_kokyakuJushoResultNm").val();
					var kokyakuTelResultNm = $("#main_kokyakuTelResultNm").val();
					var kokyakuFaxResultNm = $("#main_kokyakuFaxResultNm").val();

					<%-- 親画面への値渡し --%>
					parent.opener.$("input[name='" + kokyakuIdResultNm + "']").val($(this).attr('kokyakuId'));
					parent.opener.$("input[name='" + kokyakuNmResultNm + "']").val($(this).attr('kanjiNm'));
					<%-- name値があれば設定 --%>
					if (kokyakuJushoResultNm != "") {
						parent.opener.$("input[name='" + kokyakuJushoResultNm + "']").val($(this).attr('jusho'));
					}
					if (kokyakuTelResultNm != "") {
						parent.opener.$("input[name='" + kokyakuTelResultNm + "']").val($(this).attr('telNo1'));
					}
					if (kokyakuFaxResultNm != "") {
						parent.opener.$("input[name='" + kokyakuFaxResultNm + "']").val($(this).attr('telNo2'));
					}

					<%-- 顧客IDをチェンジ --%>
					parent.opener.$("input[name='" + kokyakuIdResultNm + "']").trigger('change');

					<%-- 送付元顧客選択ボタンなら名称1にフォーカス --%>
					if (kokyakuKaishaNmResultNm != "") {
						parent.opener.$("input[name='" + kokyakuKaishaNmResultNm + "']").focus();
					}

				}
				parent.window.close();
			});

			//******************************************************
			// クリアボタン押下時
			//******************************************************
			$('#btnClear').click(function(e) {
				
				$('#main_buttonId').val('btnClear');
				
				fnc_clear();
				
			});

			//******************************************************
			// 検索ボタン押下時
			//******************************************************
			$('#btnSearch').click(function(e) {
				
				$('#main_buttonId').val('btnSearch');
				
				$('#main').prop('action', '<s:url action="customerSearch" />');
				$('#main').prop('target', '_self');

				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
			});
			
			//******************************************************
			// 問い合わせ登録ボタン押下時
			//******************************************************
			$('#btnInquiryEntry').click(function(e) {
				
				$('#main_buttonId').val('btnInquiryEntry');
				
				var w = createWindow("tb_inquiry_entry_win");
				$('#main').prop('action', '<s:url action="inquiryEntryInit" />');
				$('#main').prop('target', w.name);

				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
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
		// 詳細リンク押下時 実行関数
		//*******************************************
		function fnc_detail_link(kokyakuId) {
			$('#kokyakuId').val(kokyakuId);
		<s:if test="%{#session.userContext.isRealEstate()}">
			$('#main_gamenKbn').val("");
			$('#main_fromDispKbn').val("");
			// 同一ウィンドウに物件・入居者詳細画面を表示
			$('#main').prop('action', '<s:url action="customerDetailInit" />');
		</s:if>
		<s:else>
			$('#main_gamenKbn').val("tb045");
			$('#main_fromDispKbn').val("tb041");
			// 同一ウィンドウに顧客詳細　付随情報参照画面を表示
			$('#main').prop('action', '<s:url action="accompanyingInfoInit" />');
		</s:else>
			$('#main').prop('target', '_self');
			$('#main').submit();
		}
		//-->
		</script>
	</tiles:putAttribute>
	<s:if test="%{#session.userContext.isRealEstate()}">
	<tiles:putAttribute name="title">物件・入居者検索</tiles:putAttribute>
	</s:if>
	<s:else>
	<tiles:putAttribute name="title">顧客検索</tiles:putAttribute>
	</s:else>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<%-- 検索条件 ここから --%>
			<div class="content">
				<h2>検索条件</h2>
				<table>
					<tr>
						<th width="10%">サービス</th>
						<td width="40%">
							<s:select key="condition.keiyakuInf" list="serviceList" listKey="serviceKbn" listValue="serviceKbnNm" emptyOption="true" />
						</td>
						<th width="10%">郵便番号</th>
						<td width="40%">
							<s:textfield key="condition.yubinNo" cssClass="hankaku" cssStyle="width:70px;" maxlength="7" placeholder="(半角数字)" />
							&nbsp;<span class="caution">※前方一致検索(ハイフン無し)</sapn>
							&nbsp;<a href="${f:out(yubinNoSearchURL)}" target="_blank"><span class="fontlink1">※郵便番号検索</span></a>
						</td>
					</tr>
					<tr>
						<th>カナ名称</th>
						<td>
							<s:textfield key="condition.kanaNm1" cssClass="zenhankaku" cssStyle="width:140px;" maxlength="40" placeholder="(全角カナ)" />
							<s:textfield key="condition.kanaNm2" cssClass="zenhankaku" cssStyle="width:120px;" maxlength="40" placeholder="(全角カナ)" />
							&nbsp;<span class="caution">※あいまい検索</span>
						</td>
						<th rowspan="4">住所</th>
						<td rowspan="4">
							<span class="caution">※全てあいまい検索</span>
							<br><s:textfield key="condition.jusho1" maxlength="10" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="都道府県(全角)" />
							<br><s:textfield key="condition.jusho2" maxlength="30" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="市区町村(全角)" />
							<br><s:textfield key="condition.jusho3" maxlength="30" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="町/大字(全角)" />
							<br><s:textfield key="condition.jusho4" maxlength="30" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="番地(全角)" />
							<br><s:textfield key="condition.jusho5" maxlength="40" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="アパート･マンション(全角)" />
							<br><s:textfield key="condition.roomNo" maxlength="20" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="部屋番号" />
						</td>
					</tr>
					<tr>
						<th>漢字名称</th>
						<td>
							<s:textfield key="condition.kanjiNm1" cssClass="zenhankaku" cssStyle="width:140px;" maxlength="40" placeholder="(全角)" />
							<s:textfield key="condition.kanjiNm2" cssClass="zenhankaku" cssStyle="width:120px;" maxlength="40" placeholder="(全角)" />
							&nbsp;<span class="caution">※あいまい検索</span>
						</td>
					</tr>
					<tr>
						<th>電話番号</th>
						<td>
							<s:textfield key="condition.telNo" cssClass="hankaku" cssStyle="width:140px;" maxlength="15" placeholder="(半角数字)" />
							&nbsp;<span class="caution">(ハイフン無し)</span>
						</td>
					</tr>
					<tr>
						<th>ソート</th>
						<td>
							<s:select key="condition.sortOrder" list="#{1:'住所・カナ名称順', 2:'カナ名称順'}" emptyOption="false" />
						</td>
					</tr>
				</table>
				<br>
				<div class="right">
					<s:if test="%{ #session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp() }">
						&nbsp;<input type="button" id="btnInquiryEntry" class="btnSubmit" value=" 問い合わせ登録 " />
					</s:if>
					<s:else>
						&nbsp;
					</s:else>
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
							<th width="2%" rowspan="2"></th>
							<th width="8%" rowspan="2">顧客ID</th>
							<th width="12%" rowspan="2">顧客区分</th>
							<th width="28%">漢字名称</th>
							<th rowspan="2">住所</th>
							<th width="10%" rowspan="2">電話番号</th>
						<s:if test="isSelectLinkView()" >
							<th width="2%" rowspan="2"><br></th>
						</s:if>
						</tr>
						<tr>
							<th>カナ名称</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="resultList" status="i">
						<tr <s:if test="isKeiyakuKanriEndFlgEnd()">class="deleted"</s:if><s:else>class="${f:odd(i.index, 'odd,even')}"</s:else>>
							<td rowspan="2"><a href="JavaScript:fnc_detail_link('${f:out(kokyakuId)}');">詳<br>細</a></td>
							<td rowspan="2" align="center">${f:out(kokyakuId)}</td>
							<td rowspan="2">${f:out(kokyakuKbnNm)}</td>
							<td>${f:out(kanjiNm)}</td>
							<td rowspan="2">${f:out(jusho)}</td>
							<td>${f:out(telNo1)}</td>
							<s:if test="isSelectLinkView()" >
								<td rowspan="2"><a href="#" kokyakuId="${f:out(kokyakuId)}" kanjiNm="${f:out(kanjiNm)}" jusho="${f:out(jusho)}" telNo1="${f:out(telNo1)}" telNo2="${f:out(telNo2)}" class="selectKokyaku">選<br>択</a></td>
							</s:if>
						</tr>
						<tr <s:if test="isKeiyakuKanriEndFlgEnd()">class="deleted"</s:if><s:else>class="${f:odd(i.index, 'odd,even')}"</s:else>>
							<td>${f:out(kanaNm)}</td>
							<td>${f:out(telNo2)}</td>
						</tr>
						</s:iterator>
					</tbody>
				</table>

				<c:import url="/WEB-INF/common/jsp/pager.jsp">
					<c:param name="condition" value="condition" />
					<c:param name="href" value="customerSearchByPager.action" />
					<c:param name="displayCount" value="0" />
				</c:import>

				<input type="hidden" id="kokyakuId" name="kokyakuId" value="" />
			</div>
			</s:if>
			<%-- 検索結果 ここまで --%>

			<s:tokenCheck displayId="TB041" />
			<s:hidden key="gamenKbn" value="" />
			<s:hidden key="fromDispKbn" value="" />
			<s:hidden key="viewSelectLinkFlg" />
			<s:hidden key="kokyakuIdResultNm" value="%{kokyakuIdResultNm}" />
			<s:hidden key="kokyakuKaishaNmResultNm" value="%{kokyakuKaishaNmResultNm}" />
			<s:hidden key="kokyakuNmResultNm" value="%{kokyakuNmResultNm}" />
			<s:hidden key="kokyakuJushoResultNm" value="%{kokyakuJushoResultNm}" />
			<s:hidden key="kokyakuTelResultNm" value="%{kokyakuTelResultNm}" />
			<s:hidden key="kokyakuFaxResultNm" value="%{kokyakuFaxResultNm}" />
			
			<%-- 顧客検索の検索条件 --%>
			${f:writeHidden2(condition, "customerCondition", excludeField)}
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
