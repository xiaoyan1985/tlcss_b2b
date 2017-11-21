<%--*****************************************************
	顧客詳細：依頼履歴情報参照画面
	作成者：小林
	作成日：2015/7/30
	更新日：2015/09/14 山村 顧客検索画面からの遷移対応
	更新日：2016/03/25 J.Matsuba サービス種別がビル管理時のプラン名表示処理追加
	更新日：2016/04/07 J.Matsuba No.欄表示およびリンク修正、ビル管理時のプラン名毎月表示修正
	更新日：2016/08/05 H.Yamamura ビル管理契約の表示処理を追加
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
			// Noリンク押下時処理
			//*******************************************
			$('.linkShosai').click(function(e){
				$('#main').addClass('ignore');

				var keiyakuKokyakuId = $(this).attr('keiyakuKokyakuId');
				var keiyakuNo = $(this).attr('keiyakuNo');

				$("#main_keiyakuKokyakuId").val(keiyakuKokyakuId);
				$("#main_keiyakuNo").val(keiyakuNo);

				$('#main').prop('action', '<s:url action="contractDetailInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
				$('#main').removeClass('ignore');
			});

			//******************************************************
			// 【契約一覧】詳細リンク押下時
			//******************************************************
			$('.linkKeiyakuDetail').click(function(e) {

				var toiawaseNo = $(this).attr('toiawaseNo');
				var keiyakuKokyakuId = $(this).attr('keiyakuKokyakuId');
				var keiyakuNo =  $(this).attr('keiyakuNo');

				$("#main_toiawaseNo").val(toiawaseNo);
				$("#main_keiyakuKokyakuId").val(keiyakuKokyakuId);
				$("#main_keiyakuNo").val(keiyakuNo);

				$('#main').addClass('ignore');

				$('#main').prop('action','<s:url action="contractInfoInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
				$('#main').removeClass('ignore');
			});

			//******************************************************
			// 【契約対象一覧】詳細リンク押下時
			//******************************************************
			$('.linkKeiyakuTargetDetail').click(function(e) {

				$('#main').addClass('ignore');

				var kokyakuId = $(this).attr('kokyakuId');
				var saveKokyakuId = $("#main_kokyakuId").val();
				var saveFromDispKbn = $("#main_fromDispKbn").val();

				$("#main_kokyakuId").val(kokyakuId);
				$("#main_fromDispKbn").val('');

				date = new Date();		// 経過時間を退避
				time = date.getTime();	// 通算ミリ秒計算

				var w = createWindow("tb_contract_info_win" + time);
				// action部分のURL設定
				$('#main').prop('action', '<s:url action="contractInfoInit" />');
				$('#main').prop('target', w.name);

				$('#main').submit();
				$('#main').removeClass('ignore');

				// パラメータを戻す
				$("#main_fromDispKbn").val(saveFromDispKbn);
				$("#main_kokyakuId").val(saveKokyakuId);
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

				<%-- 契約情報検索結果　ここから --%>
				<table class="tab">
					<tr class="tab">
						<td class="tab" colspan="3">
							<br>
							<h4>【契約一覧】</h4>
							<table class="tab_table">
								<tr>
									<th width= "3%" rowspan="2">No.</th>
									<th width="30%" rowspan="2">契約者</th>
									<th width="14%" rowspan="2">サービス</th>
									<th width= "8%" rowspan="2">申込日</th>
									<th width= "8%">契約開始日</th>
									<th width="" rowspan="2">プラン名</th>
									<th width= "3%" rowspan="2"><br></th>
								</tr>
								<tr>
									<th>契約終了日</th>
								</tr>
								<s:iterator status="i" value="keiyakuList">
									<s:if test="isKeiyakuFinished()">
										<tr bgcolor="CCCCCC">
									</s:if>
									<s:else>
										<tr class="${f:odd(i.index, 'odd,even')}">
									</s:else>
											<td rowspan="2" align="center">

												<a href="#" keiyakuKokyakuId="${kokyakuId}" keiyakuNo="${keiyakuNo}" class="linkShosai">
													<span class="fontlink2">&nbsp;${f:out(rowNum)}</span>
												</a>
											</td>
											<td>${f:out(kokyakuId)}</td>
											<td rowspan="2">${f:out(serviceKbnNm)}</td>
											<td rowspan="2" align="center">${f:dateFormat(moshikomiDt, "yyyy/MM/dd")}</td>
											<td align="center">${f:dateFormat(keiyakuStartDt, "yyyy/MM/dd")}</td>
											<td rowspan="2">
												<s:if test="isReception()">
													${f:out(planNmForDisplay)}
												</s:if>
												<s:elseif test="isLifeSupport24()">
													<br>
												</s:elseif>
												<s:elseif test="isBuildingManagement()">
													${f:out(jisshiNaiyo)}<br>
													実施月：${f:out(planNmForDisplay)}
												</s:elseif>
											</td>
											<td rowspan="2" align="center">
												<s:if test="isReception() || isBuildingManagement()">
													<a href="#" keiyakuNo="${keiyakuNo}" keiyakuUpdDt="${keiyakuUpdDt}" keiyakuKokyakuId="${kokyakuId}" class="linkKeiyakuDetail">
														<span class="fontlink2">詳細</span>
													</a>
												</s:if>
												<s:else>
													<br>
												</s:else>
											</td>
										</tr>
									<s:if test="isKeiyakuFinished()">
										<tr bgcolor="CCCCCC">
									</s:if>
									<s:else>
										<tr class="${f:odd(i.index, 'odd,even')}">
									</s:else>
											<td>${f:out(kanjiNm)}</td>
											<td align="center">${f:dateFormat(keiyakuEndDt, "yyyy/MM/dd")}</td>
										</tr>
								</s:iterator>
							</table>
							<br>
							<s:if test="isDisplayKeiyakuTargetList()">
								<h4>${f:out(keiyakuTaisho)}</h4>
								<table class="tab_table">
									<tr>
										<th width= "4%"><br></th>
										<th width="11%">顧客区分</th>
										<th width= "8%">顧客ID</th>
										<th width= "7%">個人/法人</th>
										<th width="20%">名称</th>
										<th width="32%">住所</th>
										<th width= "9%">管理開始日</th>
										<th width= "9%">管理終了日</th>
									</tr>
									<s:iterator status="i" value="keiyakuTargetList">
										<s:if test ="isKanriFinished()">
											<tr bgcolor="CCCCCC">
										</s:if>
										<s:else>
											<tr class="${f:odd(i.index, 'odd,even')}">
										</s:else>
												<td align="center">
													<a href="#" kokyakuId="${kokyakuId}" class="linkKeiyakuTargetDetail"><span class="fontlink2">詳細</span></a>
												</td>
												<td>${f:out(kokyakuKbnNm)}</td>
												<td>${f:out(kokyakuId)}</td>
												<td>${f:out(kokyakuShubetsuNm)}</td>
												<td>${f:out(kanjiNm1)} ${f:out(kanjiNm2)}</td>
												<td>${f:out(jusho1)} ${f:out(jusho2)} ${f:out(jusho3)} ${f:out(jusho4)} ${f:out(jusho5)}</td>
												<td align="center">${f:dateFormat(kanriStartDt, "yyyy/MM/dd")}</td>
												<td align="center">${f:dateFormat(kanriEndDt, "yyyy/MM/dd")}</td>
											</tr>
									</s:iterator>
								</table>
							</s:if>
							<br>
						</tr>
					</td>
				</table>
				<br>
			</div>
		</s:if>

		<s:hidden key="kokyakuId" value="%{kokyakuId}" />
		<s:hidden key="keiyakuKokyakuId" value="" />
		<s:hidden key="keiyakuNo" value="" />
		<s:hidden key="gamenKbn" value="tb046" />
		<s:hidden key="dispKbn" value="tb046" />
		<s:hidden key="condition" value="condition" />
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
		<%-- 顧客検索の検索条件 --%>
		${f:writeHidden2(customerCondition, "condition", excludeField)}	
		<s:tokenCheck displayId="TB046" />
	</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>		