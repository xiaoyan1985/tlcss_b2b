<%--*****************************************************
	センター業者検索画面
	作成者：小林
	作成日：2015/09/29
	更新日：
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
						"condition.gyoshaNm": {byteVarchar: 100},
						"condition.yubinNo": {byteVarchar: 7},
						"condition.jusho1": {byteVarchar: 10},
						"condition.jusho2": {byteVarchar: 30},
						"condition.jusho3": {byteVarchar: 30}
					},
					stepValidation: true,
					customAddError: function() {
						if ($("#alert").length < 1) {
							alert(ART0015);
						}
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
					$('input[type=checkbox]').removeAttr('checked');
					$('input[type=text]').val('');
					$("input[type=radio][value='1']").prop('checked', true);
					$('#main_chk_eigyochu_gyosha_nomi').prop('checked', true);
					$('select').prop('selectedIndex', 0);
				
					$("[placeholder]", "#main").each(function(i,e) {
						$(e).val($(e).data('placeholder-string'));
						$(e).css('color', 'silver');
					});
				});

				//******************************************************
				// 検索ボタン押下時
				//******************************************************
				$('#btnSearch').click(function(e) {
					
					$('#main_buttonId').val('btnSearch');

					$('#main').prop('action', '<s:url action="centerContractorSearch" />');
					$('#main').prop('target', '_self');

					$('#main').submit();
				});

				//*******************************************
				// 詳細リンク押下時
				//*******************************************
				$('.linkShosai').click(function(e) {

					var gyoshaCd = $(this).attr('gyoshaCd');

					$("#main_gyoshaCd").val(gyoshaCd);

					$('#main').prop('action', '<s:url action="centerContractorDetailInit" />');
					$('#main').prop('target', '_self');

					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore'); 
				});

				//*******************************************
				// 選択リンク押下時
				//*******************************************
				$('.linkSentaku').click(function(e) {

					$('#main').addClass('ignore');

					var gyoshaCd = $(this).attr('gyoshaCd');
					var gyoshaNm = $(this).attr('gyoshaNm');

					<%-- 親画面の存在チェック --%>
					if(parent.window.opener && ! parent.window.opener.closed){
						<%-- 親画面のname値を取得 --%>
						var gyoshaCdResultNm = $("#main_gyoshaCdResultNm").val();
						var gyoshaNmResultNm = $("#main_gyoshaNmResultNm").val();

						<%-- 親画面への値渡し --%>
						parent.opener.$("input[name='" + gyoshaCdResultNm + "']").val(gyoshaCd);
						parent.opener.$("input[name='" + gyoshaNmResultNm + "']").val(gyoshaNm);

						if (parent.opener.$("input[name='" + gyoshaCdResultNm + "']").hasClass("err")) {
							parent.opener.$("input[name='" + gyoshaCdResultNm + "']").removeClass("err");
							var id = parent.opener.$("input[name='" + gyoshaCdResultNm + "']").attr("id");
							parent.opener.$("div[id*=err_" + id + "]").fadeOut();
						}
						if (parent.opener.$("input[name='" + gyoshaNmResultNm + "']").hasClass("err")) {
							parent.opener.$("input[name='" + gyoshaNmResultNm + "']").removeClass("err");
							var id = parent.opener.$("input[name='" + gyoshaNmResultNm + "']").attr("id");
							parent.opener.$("div[id*=err_" + id + "]").fadeOut();
						}

						<%-- 依頼登録の場合、作業依頼メールボタン及びメッセージを消去 --%>
						if ($("#main_dispKbn").val() == 'tb033') {
							parent.opener.$("#spn_irai_mail_message").hide();
							parent.opener.$("#btnGyoshaIraiMailSend").hide();
						}
					}
					parent.window.close();
				});
			});

			//******************************************************
			// クリアボタン押下時処理
			//******************************************************
			function fnc_clear() {
				$('input[type=checkbox]').removeAttr('checked');
				$('input[type=text]').val('');
				$("input[type=radio][value='1']").prop('checked', true);
				$('#main_chk_eigyochu_gyosha_nomi').prop('checked', true);
				$('select').prop('selectedIndex', 0);

				$("[placeholder]", "#main").each(function(i,e) {
				$(e).val($(e).data('placeholder-string'));
				$(e).css('color', 'silver');
				});

			}


		//-->
		</script>

	</tiles:putAttribute>

	<tiles:putAttribute name="title">センター業者検索</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<%-- 検索条件 ここから --%>
			<div class="content">
				<h2>検索条件</h2>
				<br>
				<table>
					<tr>
						<th width="10%">業者名</th>
						<td width="41%">
						<s:textfield key="condition.gyoshaNm" maxlength="100" cssClass="zenhankaku" cssStyle="width:250px;" placeholder="(全角)" />
							&nbsp;<span class="font5">※あいまい検索</span>
						</td>
						<th width= "9%">有効性</th>
						<td width="40%">
							<label><s:checkbox key="condition.yukosei" fieldValue="1"  value="%{isYukoseiChecked()}" />&nbsp;削除済の業者を含む</label>
						</td>
					</tr>

					<tr>
						<th>対応可能エリア</th>
						<td>
							<s:select key="condition.todofuken" list="todofukenList" listKey="todofukenCd" listValue="todofukenNm" emptyOption="true" cssStyle="width:220px" />
						</td>
						<th rowspan="3">住所</th>
						<td rowspan="3">
							<span class="font5">※全てあいまい検索</span><br>

							<s:textfield key="condition.jusho1" maxlength="10" cssClass="zenhankaku" style="width:190px;" placeholder="都道府県(全角)"/>
							<s:textfield key="condition.jusho2" maxlength="30" cssClass="zenhankaku" style="width:190px;" placeholder="市区町村(全角)"/>
							<s:textfield key="condition.jusho3" maxlength="30" cssClass="zenhankaku" style="width:190px;" placeholder="町/大字(全角)"/>
						</td>
					</tr>
					<tr>
						<th>郵便番号</th>
						<td>
							<s:textfield key="condition.yubinNo" maxlength="7" cssClass="hankaku" style="width:90px;" placeholder="(半角数字)"/>
							&nbsp;<span class="font5">（ハイフン無し）&nbsp;前方一致検索</span>
							&nbsp;<a href="${f:out(yubinNoSearchURL)}" target="_blank"><span class="fontlink1">※郵便番号検索</span></a>
						</td>
					</tr>
					<tr>
						<th>営業日</th>
						<td>
							<label><s:checkbox key="condition.eigyobiMonday" fieldValue="1" value="%{isMondayChecked()}" />&nbsp;月</label>&nbsp;&nbsp;
							<label><s:checkbox key="condition.eigyobiTuesday" fieldValue="1" value="%{isTuesdayChecked()}" />&nbsp;火</label>&nbsp;&nbsp;
							<label><s:checkbox key="condition.eigyobiWednesday" fieldValue="1" value="%{isWednesdayChecked()}" />&nbsp;水</label>&nbsp;&nbsp;
							<label><s:checkbox key="condition.eigyobiThursday" fieldValue="1" value="%{isThursdayChecked()}" />&nbsp;木</label>&nbsp;&nbsp;
							<label><s:checkbox key="condition.eigyobiFriday" fieldValue="1" value="%{isFridayChecked()}" />&nbsp;金</label>&nbsp;&nbsp;
							<label><s:checkbox key="condition.eigyobiSaturday" fieldValue="1" value="%{isSaturdayChecked()}" />&nbsp;土</label>&nbsp;&nbsp;
							<label><s:checkbox key="condition.eigyobiSunday" fieldValue="1" value="%{isSundayChecked()}" />&nbsp;日</label>&nbsp;&nbsp;
							<br>&nbsp;<span class="font5">※営業日にチェック</span>
						</td>
					</tr>
					<tr>
						<th>業種</th>
						<td colspan="3">
							<table width="100%">
								<tr>
									<s:iterator status="i" value="gyoshuList">
										<td>
											<s:checkbox key="condition.gyoshuList" fieldValue="%{gyoshuList[#i.index].comCd}" disabled="false" value="isGyoshuListSelected(#i.index)" />${gyoshuList[i.index].comVal}
										</td>
										<%-- ６項目ごとに改行 --%>
										<s:if test="%{#i.count % 6 == 0}">
											</tr>
											<s:if test="!#i.last"><tr></s:if>
										</s:if>
									</s:iterator>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<br>
				<div class="right">
					&nbsp;<input type="button" class="btnSubmit" id="btnSearch" value=" 検 索 ">
					&nbsp;<input type="button" class="btnSubmit" id="btnClear" value=" クリア ">
					&nbsp;<input type="button" class="btnSubmit" id="btnClose" value=" 閉じる ">
				</div>
			</div>

			<%-- 検索結果 ここから --%>
			<s:if test="isResultDisplay()">
				<div class="content">
					<h2>検索結果</h2>
					<c:import url="/WEB-INF/common/jsp/pager.jsp">
						<c:param name="condition" value="condition" />
						<c:param name="displayCount" value="0" />
					</c:import>
					
					<table width="100%">
						<thead>
							<tr bgcolor="FFCC66">
								<th width= "2%" rowspan="2"><br></th>
								<th width= "6%" rowspan="2">業者CD</th>
								<th width="25%">業者名</th>
								<th width="8%">事務用TEL</th>
								<th width="8%">作業用TEL</th>
								<th width="16%">営業時間</th>
								<th width="27%">対応可能エリア</th>
								<s:if test="isFromRequestEntry()">
									<th width="2%" rowspan="2"><br></th>
								</s:if>
							</tr>
							<tr>
								<th colspan="3">住所</th>
								<th>営業日</th>
								<th>業種</th>
							</tr>
						</thead>
	
						<tbody>
							<s:iterator value="resultList" status="i">
								<tr <s:if test="isDeleted()">class="deleted"</s:if><s:else>class="${f:odd(i.index, 'odd,even')}"</s:else>>
									<td rowspan="2">
										<a href="#" gyoshaCd="${gyoshaCd}" class="linkShosai">
										<span class="fontlink2">詳<br>細</span></a>
									</td>
									<td rowspan="2">${f:out(gyoshaCd)}</td>
									<td>${f:out(gyoshaNm)}</td>
									<td>${f:out(jimuTel)}</td>
									<td>${f:out(sagyoTel)}</td>
									<td>${f:out(eigyoJikanForDisplay)}</td>
									<td>${f:out(todofukenNm)}</td>
									<s:if test="isFromRequestEntry()">
										<td rowspan="2">
											<s:if test= "!isDeleted()">
												<a href="#" gyoshaCd="${gyoshaCd}" gyoshaNm="${f:out(gyoshaNm)}" class="linkSentaku"><span class="fontlink2">選<br>択</span></a>
											</s:if>
										</td>
									</s:if>
								</tr>
								<tr <s:if test="isDeleted()">class="deleted"</s:if><s:else>class="${f:odd(i.index, 'odd,even')}"</s:else>>
									<td colspan="3">${f:out(jusho1)}&nbsp;${f:out(jusho2)}&nbsp;${f:out(jusho3)}&nbsp;${f:out(jusho4)}&nbsp;${f:out(jusho5)}</td>
									<td>
										<s:if test="isWorkMonday()">
											月&nbsp;
										</s:if>
										<s:if test="isWorkTuesday()">
											火&nbsp;
										</s:if>
										<s:if test="isWorkWednesday()">
											水&nbsp;
										</s:if>
										<s:if test="isWorkThursday()">
											木&nbsp;
										</s:if>
										<s:if test="isWorkFriday()">
											金&nbsp;
										</s:if>
										<s:if test="isWorkSaturday()">
											土&nbsp;
										</s:if>
										<s:if test="isWorkSunday()">
											日&nbsp;
										</s:if>
									</td>
									<td>${f:out(gyoshuNm)}</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>

					<c:import url="/WEB-INF/common/jsp/pager.jsp">
						<c:param name="condition" value="condition" />
						<c:param name="href" value="centerContractorSearchByPager.action" />
						<c:param name="displayCount" value="0" />
					</c:import>
				</div>
			</s:if>

			<!-- 検索結果　ここまで -->

			<%-- パラメータ値 --%>
			<s:hidden key="dispKbn" />
			<s:hidden key="gyoshaCdResultNm" />
			<s:hidden key="gyoshaNmResultNm" />
			<s:hidden key="gyoshaCd" />
			
			<s:tokenCheck displayId="TB051" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>