<%--*****************************************************
	顧客詳細契約詳細情報画面
	作成者：H.Yamada
	作成日：2015/09/17
	更新日：2016/03/24 J.Matsuba ビル管理用画面の追加
	更新日：2016/03/29 S.Nakano 画面レイアウトの修正
	更新日：2016/04/06 J.Matsuba 画面レイアウト修正（契約内容のタイトル幅、獲得者名欄位置等）
	更新日：2016/07/13 C.Kobayashi 契約内容の共通化（ビル管理の場合の表示対応）
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
			$(document).ready(function() {
				
				window.focus();
				
				$(function() {
					$('#btnReturn1').click(function(e) {
						fnc_back();
					});
					$('#btnReturn2').click(function(e) {
						fnc_back();
					});
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
					$('#main').prop('action','<s:url action="contractInfoInit" />');
				});

				//*******************************************
				// 戻るボタン押下時処理
				//*******************************************
				$('#btnBack').click(function(e) {
					$('#customerSearch').submit();
				});

				//******************************************************
				// 一覧の戻るボタン押下時
				//******************************************************
				function fnc_back() {
					
					$('#main').prop('action', '<s:url action="contractInfoInit" />');
					$('#main').prop('target', '_self');

					$('#main').submit();
				};
			});

		//-->
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="title">${f:out(title)}</tiles:putAttribute>
	<tiles:putAttribute name ="body">
	<s:form id="main" action="contractInfoInit">
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

		<div class="content">
			<%-- 各ページへのタブ --%>
			<%@ include file="/WEB-INF/jsp/tb040_customer_common_tab.jsp" %>

			<%-- 契約情報検索結果　ここから --%>
			<table class="tab">
				<tr class="tab">
					<td class="tab" colspan="3">
						<table class="tab_table">
							<tr>
								<td align="right">
									<input type="button" id="btnReturn1" class="btnSubmit" value=" 戻る ">
								</td>
							</tr>
						</table>
						<h4>【契約情報】</h4>
						<table class="tab_table">
							<tr >
								<td colspan="2" >
									<table width="99%" align="center" >
										<tr>
											<th width="10%">サービス</th>
											<s:if test="isReception()">
												<td width="21%">
													${f:out(keiyakuInfo.serviceKbnNm)}
												</td>
												<th width="10%">No.</th>
												<td width="59%">
													${f:out(keiyakuNo)}
												</td>
											</s:if>
											<s:elseif test="isLifeSupport24() || isBuildingManagement()">
												<td width="32%">
													${f:out(keiyakuInfo.serviceKbnNm)}
												</td>
												<th width="12%">No.</th>
												<td width="46%">
													${f:out(keiyakuNo)}
												</td>
											</s:elseif>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<br>
						<h4>【申込情報】</h4>
						<table class="tab_table">
							<tr>
								<td colspan="2" >
									<table width="99%" align="center" >
										<tr >
											<th width="10%">申込日</th>
											<s:if test="isReception()">
												<td width="21%">${f:dateFormat(keiyakuInfo.moshikomiDt, "yyyy/MM/dd")}</td>
												<th width="10%">受電窓口番号</th>
												<td width="24%" colspan="3">${f:out(keiyakuTelInfo.keiyakuTelNo)} ${f:out(keiyakuTelInfo.keiyakuTelNm)}</td>
											</s:if>
											<s:elseif test="isLifeSupport24() || isBuildingManagement()">
												<td width="32%">${f:dateFormat(keiyakuInfo.moshikomiDt, "yyyy/MM/dd")}</td>
												<th width="12%">受電窓口番号</th>
												<td width="21%" colspan="3">${f:out(keiyakuTelInfo.keiyakuTelNo)} ${f:out(keiyakuTelInfo.keiyakuTelNm)}</td>
											</s:elseif>
										</tr>
										<tr >
											<th>契約開始日</th>
											<td>${f:dateFormat(keiyakuInfo.keiyakuStartDt, "yyyy/MM/dd")}</td>
											<th>契約終了日</th>
											<td>${f:dateFormat(keiyakuInfo.keiyakuEndDt, "yyyy/MM/dd")}</td>
											<th width="10%">請求先顧客ID</th>
											<s:if test="isReception()">
												<td width="25%">${f:out(keiyakuInfo.seikyusakiKokyakuId)}</td>
											</s:if>
											<s:elseif test="isLifeSupport24() || isBuildingManagement()">
												<td width="15%">${f:out(keiyakuInfo.seikyusakiKokyakuId)}</td>
											</s:elseif>
										</tr>
										<tr >
											<th>顧客請求開始</th>
											<td>${f:dateFormat(keiyakuInfo.kokyakuSeikyuStartDt, "yyyy/MM/dd")}</td>
											<th>顧客請求終了</th>
											<td>${f:dateFormat(keiyakuInfo.kokyakuSeikyuEndDt, "yyyy/MM/dd")}</td>
											<th>契約書ＮＯ</th>
											<td>${f:out(keiyakuInfo.keiyakushoNo)}</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<br>
						<s:if test="isReception()">
						<%-- リセプション ここから --%>
							<h4>【契約内容】</h4>
							<table class="tab_table">
								<tr>
									<td colspan="2" >
										<table width="99%" align="center" >
											<tr >
												<th width="10%">回線種類</th>
												<td width="21%">${f:out(rcpInfo.kaisenNm)}</td>
												<th width="10%">時間帯</th>
												<td width="59%">${f:out(rcpInfo.jikanNm)}</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							<br>
						<%-- リセプション ここまで --%>
						</s:if>
						<s:elseif test="isLifeSupport24()">
						<%-- ライフサポート２４ ここから --%>
							<h4>【契約内容】</h4>
							<table class="tab_table">
								<tr>
									<td colspan="2" >
										<table width="99%" align="center" >
											<tr >
												<th width="10%">申込区分</th>
												<td width="32%">
													<span id="spn_rdo_entry_kbn">${f:out(lifeInfo.moshikomiKbnNm)}&nbsp;</span>
												</td>
												<th width="12%">会員番号</th>
												<td width="46%">
													${f:out(lifeInfo.kaiinNo)}&nbsp;&nbsp;${f:out(welboxFlgForDisplay)}
												</td>
											</tr>
											<tr >
												<th>建物形態</th>
												<td>
													${f:out(lifeInfo.tatemonoTypeNm)}
												</td>
												<th>入居</th>
												<td>
													${f:out(lifeInfo.nyukyoKbnNm)}&nbsp;&nbsp;
													${f:dateFormat(lifeInfo.serviceStartDt, "yyyy/MM/dd")} （サービス開始日）
												</td>
											</tr>
											<tr >
												<th>性別</th>
												<td>
													<span id="spn_rdo_sex">${f:out(lifeInfo.sexKbnNm)}&nbsp;</span>
												</td>
												<th>生年月日</th>
												<td>
													${f:dateFormat(lifeInfo.birthDt, "yyyy/MM/dd")}&nbsp;&nbsp;
													${f:out(lifeInfo.moshikomiNenrei)}歳&nbsp;&nbsp;※申込時の年齢
												</td>
											</tr>
											<tr >
												<th>部屋番号</th>
												<td>
													${f:out(lifeInfo.roomNo)}&nbsp;号室
												</td>
												<th>メールアドレス</th>
												<td>
													${f:out(lifeInfo.mailAddress)}&nbsp;（${f:out(lifeInfo.mailAddressKbnNm)}）
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							<br>
							<h4>【緊急連絡先】</h4>
							<table class="tab_table">
								<tr>
									<td colspan="2" >
										<table width="99%" align="center" >
											<tr >
												<th width="10%">フリガナ</th>
												<td width="32%">
													${f:out(lifeInfo.kinkyuKanaNm)}
												</td>
												<th width="12%">連絡先TEL</th>
												<td width="21%">
													${f:out(lifeInfo.kinkyuTel)}
												</td>
												<th width="10%">続柄</th>
												<td width="15%">
													${f:out(lifeInfo.kinkyuZokugaraNm)}
												</td>
											</tr>
											<tr >
												<th>漢字氏名</th>
												<td>
													${f:out(lifeInfo.kinkyuKanjiNm)}
												</td>
												<th>連絡先住所</th>
												<td colspan="3">
													${f:out(lifeInfo.kinkyuJusho)}
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							<br>
							<h4>【同居人】</h4>
							<table class="tab_table">
								<tr>
									<td colspan="2" >
										<table width="99%" align="center" >
											<tr >
												<th width="10%">フリガナ</th>
												<td width="32%">
													${f:out(lifeInfo.dokyoKanaNm1)}
												</td>
												<th width="12%">連絡先TEL</th>
												<td width="21%">
													${f:out(lifeInfo.dokyoTel1)}
												</td>
												<th width= 10%">続柄</th>
												<td width="15%">
													${f:out(lifeInfo.doukyoZokugara1Nm)}
												</td>
											</tr>
											<tr >
												<th>漢字氏名</th>
												<td>
													${f:out(lifeInfo.dokyoKanjiNm1)}
												</td>
												<th>連絡先住所</th>
												<td colspan="3">
													${f:out(lifeInfo.dokyoJusho1)}
												</td>
											</tr>
											<tr >
												<th>フリガナ</th>
												<td>
													${f:out(lifeInfo.dokyoKanaNm2)}
												</td>
												<th>連絡先TEL</th>
												<td>
													${f:out(lifeInfo.dokyoTel2)}
												</td>
												<th>続柄</th>
												<td>
													${f:out(lifeInfo.doukyoZokugara2Nm)}
												</td>
											</tr>
											<tr >
												<th>漢字氏名</th>
												<td>
													${f:out(lifeInfo.dokyoKanjiNm2)}
												</td>
												<th>連絡先住所</th>
												<td colspan="3">
													${f:out(lifeInfo.dokyoJusho2)}
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							<br>
							<h4>【管理会社連絡先】</h4>
							<table class="tab_table">
								<tr>
									<td colspan="2" >
										<table width="99%" align="center" >
											<tr >
												<th width="10%">フリガナ</th>
												<td width="32%">
													${f:out(lifeInfo.kanriKanaNm)}
												</td>
												<th width="12%">管理会社</th>
												<td width="21%">
													${f:out(lifeInfo.kanriKanjiNm)}
												</td>
												<th width="10%">電話</th>
												<td width="15%">
													${f:out(lifeInfo.kanriTel)}
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							<br>
						<%-- ライフサポート２４ ここまで --%>
							<table class="tab_table">
								<tr>
									<td align="right" >
										<input type="button" id="btnReturn2" class="btnSubmit" value=" 戻る ">
									</td>
								</tr>
							</table>
						</s:elseif>
						<s:elseif test="isBuildingManagement()">
						<%-- ビル管理 ここから --%>
							<h4>【契約内容】</h4>
							<table class="tab_table">
								<tr>
									<td colspan="2" >
										<table width="99%" align="center" >
											<tr class="even">
												<th rowspan="2">実施内容</th>
												<td width="10%">&nbsp;<span class="font5">実施内容</span></td>
												<td>&nbsp;${f:out(keiyakuShosaiDto.billInfo.jisshiNaiyo)}</td>
											</tr>
											<tr class="even">
												<td>&nbsp;<span class="font5">消防法</span></td>
												<td>&nbsp;${f:out(keiyakuShosaiDto.billInfo.shobohoNm)}</td>
											</tr>
											<tr class="even">
												<th width="10%">売上月</th>
												<td colspan="2">&nbsp;${f:out(keiyakuShosaiDto.billInfo.keiyakuTypeNm)}</td>
											</tr>
											<tr class="even">
												<th width="10%">月額</th>
												<td colspan="3">
													<table width="60%" align="left" class="tblBorderOff">
														<tr class="tblBorderOff">
															<td width="6%" class="tblBorderOff" align="left"><span class="font5">税抜</span></th>
															<td width="10%" class="tblBorderOff" align="right">
																${f:commaFormat(keiyakuShosaiDto.billInfo.jyuchugaku)}&nbsp;円
															</td>
															<td width="6%" class="tblBorderOff" align="center"><span class="font5">税額</span></th>
															<td width="8%" class="tblBorderOff" align="right">
																${f:commaFormat(keiyakuShosaiDto.billInfo.jyuchugakuZei)}&nbsp;円
															</td>
															<td width="6%" class="tblBorderOff" align="center"><span class="font5">税込</span></th>
															<td width="10%" class="tblBorderOff" align="right">
																${f:commaFormat(keiyakuShosaiDto.billInfo.jyuchugakuZeikomi)}&nbsp;円
															</td>
															<td width="6%" class="tblBorderOff" align="center"><span class="font5">税率</span></th>
															<td width="8%" class="tblBorderOff" align="right">
																<s:if test="keiyakuShosaiDto.billInfo.isZeiritsuZero()">非課税</s:if>
																<s:else>${f:commaFormat(keiyakuShosaiDto.billInfo.zeiRitsu)}&nbsp;%</s:else>
															</td>
															<td width="" class="tblBorderOff"><br></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="even">
												<th width="10%">原価</th>
												<td colspan="3">
													<table width="60%" align="left" class="tblBorderOff">
														<tr class="tblBorderOff">
															<td width="6%" class="tblBorderOff" align="left"><span class="font5">税抜</span></th>
															<td width="10%" class="tblBorderOff" align="right">
																${f:commaFormat(keiyakuShosaiDto.billInfo.genka)}&nbsp;円
															</td>
															<td width="6%" class="tblBorderOff" align="center"><span class="font5">税額</span></th>
															<td width="8%" class="tblBorderOff" align="right">
																${f:commaFormat(keiyakuShosaiDto.billInfo.genkaZei)}&nbsp;円
															</td>
															<td width="6%" class="tblBorderOff" align="center"><span class="font5">税込</span></th>
															<td width="10%" class="tblBorderOff" align="right">
																${f:commaFormat(keiyakuShosaiDto.billInfo.genkaZeikomi)}&nbsp;円
															</td>
															<td width="6%" class="tblBorderOff" align="center"><span class="font5">税率</span></th>
															<td width="8%" class="tblBorderOff" align="right">
																<s:if test="keiyakuShosaiDto.billInfo.isZeiritsuZero()">非課税</s:if>
																<s:else>${f:commaFormat(keiyakuShosaiDto.billInfo.zeiRitsu)}&nbsp;%</s:else>
															</td>
															<td width="" class="tblBorderOff"><br></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="even">
												<th width="10%">実施サイクル</th>
												<td colspan="3">&nbsp;${f:out(keiyakuShosaiDto.billInfo.jisshiCycle)}&nbsp;${f:out(keiyakuShosaiDto.billInfo.jisshiMonth)}</td>
											</tr>
											<tr class="even">
												<th width="10%">実施曜日</th>
												<td>&nbsp;${f:out(keiyakuShosaiDto.billInfo.jisshiWeek)}</td>
											</tr>
											<tr class="even">
												<th>獲得者名</th>
												<td colspan="2">&nbsp;${f:out(keiyakuShosaiDto.billInfo.kakutokushaNm)}</td>
											</tr>
											<tr class="even">
												<th>依頼業者</th>
												<td colspan="2">&nbsp;${f:out(keiyakuShosaiDto.billInfo.iraiGyoshaNm)}</td>
											</tr>
											<tr class="even">
												<th>備考</th>
												<td colspan="2">&nbsp;${f:out(keiyakuShosaiDto.billInfo.biko)}</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							<br>

							<table class="tab_table">
								<tr>
									<td align="right" >
										<input type="button" id="btnReturn2" class="btnSubmit" value=" 戻る ">
									</td>
								</tr>
							</table>
						<%-- ビル管理 ここまで --%>
						</s:elseif>
					</td>
				</tr>
			</table>
		</div>

		<s:hidden key="kokyakuId"/>
		<s:hidden key="keiyakuKokyakuId"/>
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