<%--*****************************************************
	顧客詳細 問い合わせ履歴情報参照画面
	作成者：小林
	作成日：2015/7/24
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

				$("#main_toiawaseNo").val(toiawaseNo);

				$('#main').addClass('ignore');

				var w = createWindow("tb_inquiry_entry_win");
				$('#main').prop('action','<s:url action="inquiryEntryUpdateInit" />');
				$('#main').prop('target', w.name);

				$('#main').submit();
				$('#main').removeClass('ignore');
				
				// 設定した問い合わせNOの値をクリア（問い合わせ登録ボタン押下時を考慮）
				$("#main_toiawaseNo").val("");
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
										<th width= "2%" rowspan="3">&nbsp;</th>
										<th width="10%">受付番号</th>
										<th width="12%">顧客ID</th>
										<th width="26%">名称</th>
										<th width="40%" rowspan="3">問い合わせ内容</th>
										<th width="10%" rowspan="3">最終履歴</th>
									</tr>
									<tr>
										<th>受付日時</th>
										<th>依頼者区分</th>
										<th>依頼者名</th>
									</tr>
									<tr>
										<th>受付者</th>
										<th colspan="2">区分</th>
									</tr>
									<s:iterator value="result" status="i">
										<s:if test="isHoukokuTarget(#i.index)">
											<tr class="${f:odd(i.index, 'odd,even')}">
										</s:if>
										<s:else>
											<tr class="notHoukokuPrint">
										</s:else>
												<td rowspan="3" align="center">
													<a href="#" toiawaseNo="${toiawaseNo}" class="linkShosai"><span class="fontlink2">詳細</span></a>
												</td>
												<td align="center">${f:out(toiawaseNo)}</td>
												<td align="center">${f:out(kokyakuId)}</td>
												<td>${f:out(kanjiNm1)} ${f:out(kanjiNm2)}</td>
												<td rowspan="3">${f:out(toiawaseNaiyo)}</td>
											<s:if test="isFirstReception()">
												<td rowspan="3" align="center" class="tblDetailForm5">
											</s:if>
											<s:elseif test="isContractorArrangement() || isChecking()">
												<td rowspan="3" align="center" class="tblDetailForm4">
											</s:elseif>
											<s:elseif test="isWorkCompleted()">
												<td rowspan="3" align="center" class="tblDetailForm7">
											</s:elseif>
											<s:else>
												<td rowspan="3" align="center">
											</s:else>
													${f:out(jokyoKbnNm)}<br>${f:dateFormat(uketsukeYmdRireki, "yy/MM/dd")}
													${f:formatTime(uketsukeJikanRireki)}
												</td>
											</tr>
										<s:if test="isHoukokuTarget(#i.index)">
											<tr class="${f:odd(i.index, 'odd,even')}">
										</s:if>
										<s:else>
											<tr class="notHoukokuPrint">
										</s:else>
												<td align="center">${f:dateFormat(uketsukeYmd, "yy/MM/dd")} ${f:formatTime(uketsukeJikan)}</td>
												<td align="center">${f:out(iraishaKbnNm)}</td>
												<td>${f:out(iraishaNm)}</td>
											</tr>
										<s:if test="isHoukokuTarget(#i.index)">
											<tr class="${f:odd(i.index, 'odd,even')}">
										</s:if>
										<s:else>
											<tr class="notHoukokuPrint">
										</s:else>
												<td align="center">${f:out(uketsukeshaNm)}</td>
												<td colspan="2">${f:out(toiawaseKbn1Nm)} ${f:out(toiawaseKbn2Nm)} ${f:out(toiawaseKbn3Nm)} ${f:out(toiawaseKbn4Nm)}</td>
											</tr>
									</s:iterator>
								</table>
								<br>
								&nbsp;<font color="#99cccc">■</font><span class="font3">：報告件数に含まない</span>
								<br>
								<br>
								<div class="clearfix">
									<span style="float:left;">${condition.count}件</span>
									<br>
									<br>
								</div>
							</td>
						</tr>
					</table>
					<br>
				</div>
			</s:if>

		<s:hidden key="condition" value="condition" />
		<s:hidden key="kokyakuId" />
		<s:hidden key="gamenKbn" value="tb049" />
		<s:hidden key="dispKbn" value="tb049" />
		<s:hidden key="toiawaseNo" value=""/>
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

	<s:form id="irai" name="irai" action="requestHistoryInfoInit" target="_self" method="POST">
		<s:hidden key="condition.kokyakuId" value="%{kokyakuId}" />
		<s:hidden key="kokyakuId" />
		<s:hidden key="gamenKbn" value="tb050"/>
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
		<s:tokenCheck displayId="TB049" />
		<%-- 顧客検索の検索条件 --%>
		${f:writeHidden2(customerCondition, "condition", excludeField)}	
	</s:form>

	</tiles:putAttribute>
</tiles:insertDefinition>