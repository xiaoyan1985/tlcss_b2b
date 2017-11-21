<%--*****************************************************
	顧客詳細：依頼履歴情報参照画面
	作成者：小林
	作成日：2015/7/30
	更新日：2015/09/14 山村 顧客検索画面からの遷移対応
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function(){
			window.focus();
			
			$("#main").validate({
				
				ignore: ".ignore *",

				invalidHandler: function(form, validator) {
					alert(ART0015);
				}
			});
			
			//*******************************************
			// 問い合わせ登録ボタン押下時処理
			//*******************************************
			$('#btnInquiryEntry').click(function(e) {
				
				$('#main').addClass('ignore');

				var w = createWindow("tb_inquiry_entry_win");
				
				$('#main').prop('action','<s:url action="inquiryEntryInit" />');
				$('#main').prop('target', w.name);

				$('#main').submit();
				$('#main').removeClass('ignore');
			});
			
			//*******************************************
			// 詳細リンク押下時処理
			//*******************************************
			$('.linkShosai').click(function(e){
				var toiawaseNo = $(this).attr('toiawaseNo');
				var toiawaseRirekiNo = $(this).attr('toiawaseRirekiNo');

				$("#main_toiawaseNo").val(toiawaseNo);
				$("#main_toiawaseRirekiNo").val(toiawaseRirekiNo);

				$('#main').addClass('ignore');

				var w = createWindow("tb_request_search_win");
				$('#main').prop('action','<s:url action="requestFullEntryUpdateInit" />');
				$('#main').prop('target', w.name);

				$('#main').submit();
				$('#main').removeClass('ignore');
			});

			//*******************************************
			// 戻るボタン押下時処理
			//*******************************************
			$('#btnBack').click(function(e) {
				$('#customerSearch').submit();
			});
		});
		//-->
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="title">${f:out(title)}</tiles:putAttribute>
	<tiles:putAttribute name ="body">
	<s:form id="main">
		<%-- 顧客基本情報 --%>
		<%@ include file="/WEB-INF/jsp/tb040_customer_common_info.jsp" %>
		<div align="right">
			<s:if test="%{ #session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp() }">
				<input type="button" id="btnInquiryEntry" value="問い合わせ登録" class="btnSubmit" />
			</s:if>
			&nbsp;
		<s:if test="isBackButtonView()">
			<input type="button" id="btnBack" value=" 戻 る "class="btnSubmit">
		</s:if>
		<s:else>
			<input type="button" id="btnClose" name ="btn_win_close" value=" 閉じる "class="btnSubmit">
		</s:else>
		</div>

		<%-- 顧客情報が取得できなかった場合、詳細は表示しない --%>
		<s:if test="kokyakuEntity != null">

			<div class="content">
				<%-- 各ページへのタブ --%>
				<%@ include file="/WEB-INF/jsp/tb040_customer_common_tab.jsp" %>
				<%-- 問い合わせ履歴情報検索結果　ここから --%>
					<table class="tab">
						<tr class="tab">
							<td class="tab" colspan="3">
								 <br>
								 <h4>【検索結果】</h4>
								 <div class="clearfix">
									<span style="float:left;">${condition.count}件</span>
								</div>

								<table class="tab_table">
									<tr>
										<th width="2%" rowspan="2"><br></th>
										<th width="12%">受付番号</th>
										<th width="11%">顧客ID</th>
										<th width="25%">名称</th>
										<th width="30%" rowspan="2">依頼内容</th>
										<th width="10%" rowspan="2">作業完了</th>
										<th width="10%" rowspan="2">最終履歴</th>
									</tr>
									<tr>
										<th>発注担当</th>
										<th>依頼者区分</th>
										<th>依頼者名</th>
									</tr>
									<s:iterator value="result" status="i">
										<tr class="${f:odd(i.index, 'odd,even')}">
											<td rowspan="2" align="center">
												<a href="#" toiawaseNo="${toiawaseNo}" toiawaseRirekiNo="${toiawaseRirekiNo}" class="linkShosai"><span class="fontlink2">詳細</span></a>
											</td>
											<td>${f:out(toiawaseNo)}-${f:out(toiawaseRirekiNo)}</td>
											<td>${f:out(kokyakuId)}</td>
											<td>${f:out(kanjiNm1)} ${f:out(kanjiNm2)}</td>
											<td rowspan="2">&nbsp;${f:out(iraiNaiyo)}</td>
											<td align="center" rowspan="2">${f:dateFormat(sagyoKanryoYmd, "yy/MM/dd")} ${f:formatTime(sagyoKanryoJikan)}</td>
											<s:if test="isFirstReception()">
												<td rowspan="2" align="center" class="tblDetailForm5">
											</s:if>
											<s:elseif test="isContractorArrangement() || isChecking()">
												<td rowspan="2" align="center" class="tblDetailForm4">
											</s:elseif>
											<s:elseif test="isWorkCompleted()">
												<td rowspan="2" align="center" class="tblDetailForm7">
											</s:elseif>
											<s:else>
												<td rowspan="2" align="center">
											</s:else>
												${f:out(jokyoKbnNm)}<br>
												${f:dateFormat(uketsukeYmdRireki, "yy/MM/dd")} ${f:formatTime(uketsukeJikanRireki)}
											</td>
										</tr>
										<tr class="${f:odd(i.index, 'odd,even')}">
											<td>${f:out(tantoshaIdNm)}</td>
											<td>${f:out(iraishaKbnNm)}</td>
											<td>${f:out(iraishaNm)}</td>
										</tr>
									</s:iterator>
								</table>
								<br>
								 <div class="clearfix">
									<span style="float:left;">${condition.count}件</span>
								</div>
								<br>
							</td>
						</tr>
					</table>
					<br>
				</div>
			</s:if>

		<s:hidden key="kokyakuId" />
		<s:hidden key="gamenKbn" value="tb050" />
		<s:hidden key="dispKbn" value="tb050" />
		<input type="hidden" name="condition" value="condition" />
		<s:hidden key="toiawaseNo" value=""/>
		<s:hidden key="toiawaseRirekiNo" value=""/>
		<s:hidden key="fromDispKbn" />
		<s:hidden key="viewSelectLinkFlg" />
		<s:hidden key="kokyakuIdResultNm" value="%{kokyakuIdResultNm}" />
		<s:hidden key="kokyakuKaishaNmResultNm" value="%{kokyakuKaishaNmResultNm}" />
		<s:hidden key="kokyakuNmResultNm" value="%{kokyakuNmResultNm}" />
		<s:hidden key="kokyakuJushoResultNm" value="%{kokyakuJushoResultNm}" />
		<s:hidden key="kokyakuTelResultNm" value="%{kokyakuTelResultNm}" />
		<s:hidden key="kokyakuFaxResultNm" value="%{kokyakuFaxResultNm}" />
		<%-- 顧客検索の検索条件 --%>
		${f:writeHidden2(customerCondition, "customerCondition", excludeField)}	
	</s:form>

	<s:form id="fuzui" action="accompanyingInfoInit" target="_self" method="POST">
		<s:hidden key="kokyakuId"/>
		<s:hidden key="gamenKbn" value="tb045"/>
		<s:hidden key="fromDispKbn"/>
		<s:hidden key="viewSelectLinkFlg" />
		<s:hidden key="kokyakuIdResultNm" value="%{kokyakuIdResultNm}" />
		<s:hidden key="kokyakuKaishaNmResultNm" value="%{kokyakuKaishaNmResultNm}" />
		<s:hidden key="kokyakuNmResultNm" value="%{kokyakuNmResultNm}" />
		<s:hidden key="kokyakuJushoResultNm" value="%{kokyakuJushoResultNm}" />
		<s:hidden key="kokyakuTelResultNm" value="%{kokyakuTelResultNm}" />
		<s:hidden key="kokyakuFaxResultNm" value="%{kokyakuFaxResultNm}" />
		<%-- 顧客検索の検索条件 --%>
		${f:writeHidden2(customerCondition, "customerCondition", excludeField)}	
	</s:form>

	<s:form id="contract" action="contractInfoInit" target="_self" method="POST">
		<s:hidden key="kokyakuId" />
		<s:hidden key="gamenKbn" value="tb046"/>
		<s:hidden key="fromDispKbn"/>
		<s:hidden key="viewSelectLinkFlg" />
		<s:hidden key="kokyakuIdResultNm" value="%{kokyakuIdResultNm}" />
		<s:hidden key="kokyakuKaishaNmResultNm" value="%{kokyakuKaishaNmResultNm}" />
		<s:hidden key="kokyakuNmResultNm" value="%{kokyakuNmResultNm}" />
		<s:hidden key="kokyakuJushoResultNm" value="%{kokyakuJushoResultNm}" />
		<s:hidden key="kokyakuTelResultNm" value="%{kokyakuTelResultNm}" />
		<s:hidden key="kokyakuFaxResultNm" value="%{kokyakuFaxResultNm}" />
		<%-- 顧客検索の検索条件 --%>
		${f:writeHidden2(customerCondition, "customerCondition", excludeField)}	
	</s:form>

	<s:form id="toiawase" action="inquiryHistoryInfoInit" target="_self" method="POST">
		<s:hidden key="condition.kokyakuId" value="%{kokyakuId}" />
		<s:hidden key="kokyakuId" />
		<s:hidden key="gamenKbn" value="tb049"/>
		<s:hidden key="fromDispKbn"/>
		<s:hidden key="viewSelectLinkFlg" />
		<s:hidden key="kokyakuIdResultNm" value="%{kokyakuIdResultNm}" />
		<s:hidden key="kokyakuKaishaNmResultNm" value="%{kokyakuKaishaNmResultNm}" />
		<s:hidden key="kokyakuNmResultNm" value="%{kokyakuNmResultNm}" />
		<s:hidden key="kokyakuJushoResultNm" value="%{kokyakuJushoResultNm}" />
		<s:hidden key="kokyakuTelResultNm" value="%{kokyakuTelResultNm}" />
		<s:hidden key="kokyakuFaxResultNm" value="%{kokyakuFaxResultNm}" />
		<%-- 顧客検索の検索条件 --%>
		${f:writeHidden2(customerCondition, "customerCondition", excludeField)}	
	</s:form>
	
	<s:form id="customerSearch" name="customerSearch" action="customerSearch" target="_self" method="POST">
		<s:hidden key="fromDispKbn"/>
		<s:hidden key="viewSelectLinkFlg" />
		<s:hidden key="kokyakuIdResultNm" value="%{kokyakuIdResultNm}" />
		<s:hidden key="kokyakuKaishaNmResultNm" value="%{kokyakuKaishaNmResultNm}" />
		<s:hidden key="kokyakuNmResultNm" value="%{kokyakuNmResultNm}" />
		<s:hidden key="kokyakuJushoResultNm" value="%{kokyakuJushoResultNm}" />
		<s:hidden key="kokyakuTelResultNm" value="%{kokyakuTelResultNm}" />
		<s:hidden key="kokyakuFaxResultNm" value="%{kokyakuFaxResultNm}" />
		<s:tokenCheck displayId="TB050" />
		<%-- 顧客検索の検索条件 --%>
		${f:writeHidden2(customerCondition, "condition", excludeField)}	
	</s:form>

	</tiles:putAttribute>
</tiles:insertDefinition>