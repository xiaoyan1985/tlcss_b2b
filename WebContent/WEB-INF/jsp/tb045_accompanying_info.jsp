<%--*****************************************************
	顧客詳細付随情報画面
	作成者：阿部
	作成日：2015/08/03
	更新日：2015/09/14 山村 顧客検索画面からの遷移対応
	        2015/11/04 松葉 物件情報の表示項目修正、指定業者情報追加
	        2016/03/28 J.Matsuba 階数の詳細表示、キーボックス欄追加
	        2016/04/11 H.Yamamura 画面レイアウト不具合修正
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
				
				<table class="tab">
					<tr class="tab">
						<td class="tab" colspan="3">
							<br>
							
							<%-- 管理会社・不動産 --%>
							<s:if test="isRealEstate()">
								<h4>【不動産・管理会社情報】</h4>
								<table class="tab_table">
									<tr class="even">
										<th width="8%">設立</th>
										<td width="26%">&nbsp;${f:out(fuzuiKanriInfo.setsuritsu)}</td>
										<th width="8%">代表者名</th>
										<td width="26%">&nbsp;${f:out(fuzuiKanriInfo.daihyoshaNm)}</td>
										<th width="8%">責任者名</th>
										<td width="24%">&nbsp;${f:out(fuzuiKanriInfo.sekininshaNm)}</td>
									</tr>
									<tr class="even">
										<th>営業時間</th>
										<td>&nbsp;${f:out(fuzuiKanriInfo.eigyoJikan)}</td>
										<th>定休日</th>
										<td>&nbsp;${f:out(fuzuiKanriInfo.teikyubi)}</td>
										<th>E-MAIL</th>
										<td>&nbsp;${f:out(fuzuiKanriInfo.mailAddress)}</td>
									</tr>
								</table>
								<br>
								<h4>【対応報告通知情報】</h4>
								<table class="tab_table">
									<tr>
										<th width="5%">優先</th>
										<th width="24%">担当者名</th>
										<th width="48%">E-MAIL</th>
										<th width="23%">備考</th>
									</tr>
									<tr class="odd">
										<td align="center">&nbsp;${f:out(fuzuiKanriInfo.taioYusenNo1)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.taioTantoshaNm1)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.taioMailAddress1)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.taioBiko1)}</td>
									</tr>
									<tr class="even">
										<td align="center">&nbsp;${f:out(fuzuiKanriInfo.taioYusenNo2)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.taioTantoshaNm2)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.taioMailAddress2)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.taioBiko2)}</td>
									</tr>
									<tr class="odd">
										<td align="center">&nbsp;${f:out(fuzuiKanriInfo.taioYusenNo3)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.taioTantoshaNm3)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.taioMailAddress3)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.taioBiko3)}</td>
									</tr>
								</table>
								<br>
								<h4>【緊急連絡先（最大５件）】</h4>
								<table class="tab_table">
									<tr>
										<th width="5%">優先</th>
										<th width="24%">部署名</th>
										<th width="24%">役職</th>
										<th width="24%">名称</th>
										<th width="23%">連絡先</th>
									</tr>
									<tr class="odd">
										<td align="center">&nbsp;${f:out(fuzuiKanriInfo.kinkyuYusenCd1)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuBushoNm1)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuYakushoku1)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuNm1)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuRenrakuSaki1)}</td>
									</tr>
									<tr class="even">
										<td align="center">&nbsp;${f:out(fuzuiKanriInfo.kinkyuYusenCd2)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuBushoNm2)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuYakushoku2)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuNm2)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuRenrakuSaki2)}</td>
									</tr>
									<tr class="odd">
										<td align="center">&nbsp;${f:out(fuzuiKanriInfo.kinkyuYusenCd3)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuBushoNm3)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuYakushoku3)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuNm3)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuRenrakuSaki3)}</td>
									</tr>
									<tr class="even">
										<td align="center">&nbsp;${f:out(fuzuiKanriInfo.kinkyuYusenCd4)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuBushoNm4)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuYakushoku4)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuNm4)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuRenrakuSaki4)}</td>
									</tr>
									<tr class="odd">
										<td align="center">&nbsp;${f:out(fuzuiKanriInfo.kinkyuYusenCd5)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuBushoNm5)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuYakushoku5)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuNm5)}</td>
										<td>&nbsp;${f:out(fuzuiKanriInfo.kinkyuRenrakuSaki5)}</td>
									</tr>
								</table>
							</s:if>
							<%-- 物件 --%>
							<s:elseif test="isProperty()">
								<h4>【物件情報】</h4>
								<table class="tab_table">
									<tr>
										<th width="10%">構造</th>
										<td width="23%">&nbsp;${f:out(fuzuiBukkenInfo.kozo)}</td>
										<th width="10%">階数</th>
										<td width="23%">
											<s:if test="isEmptyKaisu()">
											</s:if>
											<s:else>
												&nbsp;${f:out(fuzuiBukkenInfo.kaisu)} 階建て
											</s:else>
											<s:if test="isEmptyChijoKaisu()">
												<s:if test="isEmptyChikaKaisu()">
												</s:if>
												<s:else>
													（地下&nbsp;${f:out(fuzuiBukkenInfo.chikaKaisu)}階）
												</s:else>
											</s:if>
											<s:else>
												<s:if test="isEmptyChikaKaisu()">
													（地上&nbsp;${f:out(fuzuiBukkenInfo.chijoKaisu)}階）
												</s:if>
												<s:else>
													（地上&nbsp;${f:out(fuzuiBukkenInfo.chijoKaisu)}階&nbsp;&nbsp;地下&nbsp;${f:out(fuzuiBukkenInfo.chikaKaisu)}階）
												</s:else>
											</s:else>
										</td>
										<th width="10%">戸数</th>
										<td width="12%">
											&nbsp;
											<s:if test="fuzuiBukkenInfo.kosu != null">
												${f:out(fuzuiBukkenInfo.kosu)} 戸
											</s:if>
										</td>
									</tr>
									<tr>
										<th>築年月</th>
										<td>&nbsp;${f:out(fuzuiBukkenInfo.chikuNengetsuDisplay)}</td>
										<th>備考</th>
										<td colspan="3">${f:out(fuzuiBukkenInfo.kanriKeitaiBiko)}</td>
									</tr>
									<tr>
										<th>物件担当</th>
										<td colspan="5">
											<table>
												<tr>
													<td width="52%">
														<span class="font2">担当者１：</span>&nbsp;${f:out(fuzuiBukkenInfo.tantoshaNm)}
													</td>
													<td>
														<span class="font2">連絡先１：</span>&nbsp;${f:out(fuzuiBukkenInfo.renrakusaki)}
													</td>
												</tr>
												<tr>
													<td>
														<span class="font2">担当者２：</span>&nbsp;${f:out(fuzuiBukkenInfo.tantoshaNm2)}
													</td>
													<td>
														<span class="font2">連絡先２：</span>&nbsp;${f:out(fuzuiBukkenInfo.renrakusaki2)}
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<th>ポンプ室</th>
										<td colspan="5">
											&nbsp;${f:out(fuzuiBukkenInfo.pompMemo)}
										</td>
									</tr>
									<tr>
										<th>オートロック</th>
										<td colspan="5">
											&nbsp;${f:out(fuzuiBukkenInfo.autoLockMemo)}
										</td>
									</tr>
									<tr>
										<th>メールＢＯＸ</th>
										<td colspan="5">
											&nbsp;${f:out(fuzuiBukkenInfo.mailBox)}
										</td>
									</tr>
									<tr>
										<th>キーボックス</th>
										<td colspan="5">
											<table>
												<tr>
													<td>
														<span class="font2">暗証番号：</span>${f:out(fuzuiBukkenInfo.keyboxPassword)}
													</td>
												</tr>
												<tr>
													<td>
														<span class="font2">場所：</span>${f:out(fuzuiBukkenInfo.keyboxPlace)}
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<th>共用部管理</th>
										<td colspan="5">
											<table>
												<tr>
													<td width="50%">
														<span class="font2">名称：</span>${f:out(fuzuiBukkenInfo.kyoyoKanriNm)}
													</td>
													<td width="25%">
														<span class="font2">営業日：</span>${f:out(fuzuiBukkenInfo.kyoyoKanriEigyobi)}
													</td>
													<td width="25%">
														<span class="font2">営業時間：</span>${f:out(fuzuiBukkenInfo.kyoyoKanriEigyoJikan)}
													</td>
												</tr>
												<tr>
													<td>
														<span class="font2">TEL：</span>${f:out(fuzuiBukkenInfo.kyoyoKanriTel)}
													</td>
													<td colspan="2">
														<span class="font2">時間外TEL：</span>${f:out(fuzuiBukkenInfo.kyoyoKanriJikangaiTel)}
													</td>
												</tr>
												<tr>
													<td colspan="3">
														<span class="font2">メールアドレス：</span>${f:out(fuzuiBukkenInfo.kyoyoKanriMailAddress)}
													</td>
												</tr>
												<tr>
													<td colspan="3">
														<span class="font2">備考：</span>${f:out(fuzuiBukkenInfo.kyoyoKanriBiko)}
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<th>管理人</th>
										<td colspan="5">
											<table>
												<tr>
													<td width="50%">
														<span class="font2">名称：</span>${f:out(fuzuiBukkenInfo.kanrininNm)}
													</td>
													<td width="25%">
														<span class="font2">営業日：</span>${f:out(fuzuiBukkenInfo.kanrininEigyobi)}
													</td>
													<td width="25%">
														<span class="font2">営業時間：</span>${f:out(fuzuiBukkenInfo.kanrininEigyoJikan)}
													</td>
												</tr>
												<tr>
													<td colspan="3">
														<span class="font2">TEL：</span>${f:out(fuzuiBukkenInfo.kanrininTel)}
													</td>
												</tr>
												<tr>
													<td colspan="3">
														<span class="font2">メールアドレス：</span>${f:out(fuzuiBukkenInfo.kanrininMailAddress)}
													</td>
												</tr>
												<tr>
													<td colspan="3">
														<span class="font2">備考：</span>${f:out(fuzuiBukkenInfo.kanrininBiko)}
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<th>オーナー情報</th>
										<td colspan="5">
											<table width="99%" align="center" class="tblBorderOff">
												<tr>
													<td width="50%">
														<span class="font2">名称：</span>${f:out(fuzuiBukkenInfo.ooyaNm)}
													</td>
													<td>
														<span class="font2">TEL：</span>${f:out(fuzuiBukkenInfo.ooyaTel)}
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="font2">住所：</span>${f:out(fuzuiBukkenInfo.ooyaJusho)}
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<span class="font2">備考：</span>${f:out(fuzuiBukkenInfo.ooyaBiko)}
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<s:if test="!isEmptyBukkenShiteiGyoshaTableList()">
								<br>
								<h4>【指定業者情報】</h4>
								<table class="sharp">
									<s:iterator value="bukkenShiteiGyoshaTableList" status="i">
										<s:if test="#i.index == 0">
										<tr>
											<th width="12%" rowspan="2">業種</th>
											<th width="25%">名称</th>
											<th width="10%">TEL</th>
											<th width="" rowspan="2">備考</th>
										</tr>
										<tr>
											<th width="25%">メールアドレス</th>
											<th width="10%">FAX</th>
										</tr>
										</s:if>

										<tr>
											<td rowspan="2">${f:out(gyoshuNm)}</td>
											<td>${f:out(name)}</td>
											<td>${f:out(telNo)}</td>
											<td rowspan="2">${f:out(biko)}</td>
										</tr>
										<tr>
											<td>${f:out(mailAddress)}</td>
											<td>${f:out(faxNo)}</td>
										</tr>
									</s:iterator>
								</table>
								</s:if>
							</s:elseif>
							<%-- 大家 --%>
							<s:elseif test="isLandlord()">
								<h4>【大家情報】</h4>
								<table class="tab_table">
									<tr>
										<th width="10%">代理人</th>
										<td width="40%">&nbsp;${f:out(fuzuiOoyaInfo.dairininNm)}</td>
										<th width="10%">代理人TEL</th>
										<td width="40%">&nbsp;${f:out(fuzuiOoyaInfo.dairininTel)}</td>
									</tr>
									<tr>
										<th>代理人住所</th>
										<td>&nbsp;${f:out(fuzuiOoyaInfo.dairininJusho)}</td>
										<th>代理人FAX</th>
										<td>&nbsp;${f:out(fuzuiOoyaInfo.dairininFax)}</td>
									</tr>
									<tr>
										<th>緊急連絡先</th>
										<td colspan="3">&nbsp;${f:out(fuzuiOoyaInfo.kinkyuRenrakusaki)}</td>
									</tr>
								</table>
							</s:elseif>
							<%-- 入居者・個人 --%>
							<s:elseif test="isTenant()">
								<h4>【入居者・個人情報】</h4>
								<table class="tab_table">
									<tr>
										<th width="10%">入居日</th>
										<td width="40%">&nbsp;${f:out(fuzuiKojinInfo.nyukyoDt)}</td>
										<th width="10%">退居日</th>
										<td width="40%">&nbsp;${f:out(fuzuiKojinInfo.taikyoDt)}</td>
									</tr>
									<tr>
										<th>緊急連絡先</th>
										<td colspan="3">&nbsp;${f:out(fuzuiKojinInfo.kinkyuRenrakusaki)}</td>
									</tr>
								</table>
							</s:elseif>
						</td>
					</tr>
				</table>
			</div>
		</s:if>
		
		<s:hidden key="kokyakuId" />
		<s:hidden key="gamenKbn" value="tb045" />
		<s:hidden key="dispKbn" value="tb045" />
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
	
	<br>
	
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
		<s:tokenCheck displayId="TB045" />
		<%-- 顧客検索の検索条件 --%>
		${f:writeHidden2(customerCondition, "condition", excludeField)}	
	</s:form>
	
	</tiles:putAttribute>
</tiles:insertDefinition>
