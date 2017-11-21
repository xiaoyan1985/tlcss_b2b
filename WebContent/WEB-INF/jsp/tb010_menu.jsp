﻿<%--*****************************************************
	メニュー画面
	作成者：仲野
	作成日：2014/06/05
	更新日：2015/07/06 仲野 管理会社の場合、常に問い合わせ検索・依頼検索ボタンを表示するように修正
	更新日：2015/07/13 仲野 管理会社（親）画面でもカレンダー表示するように修正
	更新日：2015/07/17 阿部 管理会社（親）でログイン時、物件・入居者検索ボタンを追加
	更新日：2015/08/26 小林 委託会社SV、委託会社OPの追加
	更新日：2015/10/20 松葉 未読情報の追加
	更新日：2016/07/20 松葉 管理会社（管理対象1件のみ）画面にビル管理の情報を表示するように修正
	更新日：2016/08/29 松葉 管理会社（親）でログイン時、iPadで表示した場合のはみ出しを修正
	更新日：2016/09/13 松葉 管理会社ユーザでのログイン時、依頼検索ボタンの表示制御を追加
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="menu.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {

			<s:if test="%{#session.userContext.isRealEstate()}">
				setTimeout("fnc_reload()", ${reloadTime});
			</s:if>

			<%-- 文書ライブラリアイコン表示 --%>
			$("a[href$='.pdf');']").append("&nbsp;<img src='images/icon_pdf.gif' border='0' width='16' height='16' />");
			$("a[href$='.xls');']").append("&nbsp;<img src='images/icon_xls.gif' border='0' width='16' height='16' />");
			$("a[href$='.xlsx');']").append("&nbsp;<img src='images/icon_xls.gif' border='0' width='16' height='16' />");
			$("a[href$='.xlsx']").append("&nbsp;<img src='images/icon_xls.gif' border='0' width='16' height='16' />");
			$("a[href$='.doc');']").append("&nbsp;<img src='images/icon_doc.gif' border='0' width='16' height='16' />");
			$("a[href$='.docx');']").append("&nbsp;<img src='images/icon_doc.gif' border='0' width='16' height='16' />");

			<%-- 確認ダイアログ(初期値は空) --%>
			var confirmation = function() {return true;};

			var events = {
				<s:iterator value="inquiryHistoryList" status="i">
				'${f:dateFormat(uketsukeYmd, "yyyyMMdd")}':{type:1, title:"問い合わせ　${count}件"}<s:if test="#i.last == false">,</s:if>
				</s:iterator>
			};

			var holidays = {
				<s:iterator value="holidayList" status="i">
				'${f:dateFormat(holiday, "yyyyMMdd")}':'${f:dateFormat(holiday, "yyyyMMdd")}'<s:if test="#i.last == false">,</s:if>
				</s:iterator>
			};

			$("#datepicker").datepicker({
				changeMonth: true,
				changeYear: true,
				dateFormat: "yymmdd",
				firstDay: 1,
				showButtonPanel: true,
				duration:"fast",
				showAnim:"",
				minDate: '-1y',
				maxDate: '+1y',
				beforeShowDay: function(date) {
					var result;
					var title = "";
					var clazz = "";

					var eventday = events[$.datepicker.formatDate('yymmdd', date)];
					if (eventday) {
						clazz = " date-eventday" + eventday.type;
						title = eventday.title;
					}

					var holiday = holidays[$.datepicker.formatDate('yymmdd', date)];
					if (holiday) {
						result = [true, "date-holiday"+ clazz, title];
					} else {
						switch (date.getDay()) {
							case 0:
								result = [true, "date-holiday" + clazz, title];
								break;
							case 6:
								result = [true, "date-saturday"+ clazz, title];
								break;
							default:
								result = [true, clazz, title];
								break;
						}
					}

					return result;
				},
				onSelect: function(dateText, inst) {
					$(this).css('color', 'black');
					if (events[dateText]) {
						// 問い合わせ検索画面を呼び出す
						fnc_inquiry_search_display('', dateText, dateText, '0', '');
					}
				}
			});

			//******************************************************
			// ログアウトボタン押下時
			//******************************************************
			$(':button[id^="logout"]').click(function(e) {
				alert(jQuery.validator.format(ART0025, ""));

				// ログイン画面に遷移
				$('#main').prop('action', '<s:url action="logout" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});

			//******************************************************
			// ヘルプボタン押下時
			//******************************************************
			$(':button[id^="help"]').click(function(e) {
				var form = document.forms[0];
				form.action = '${helpUrl}';
				form.target = '_blank';
				form.submit();
			});

			//******************************************************
			// 戻るボタン押下時 (顧客選択画面に戻る)
			//******************************************************
			$(':button[id^="back"]').click(function(e) {

				// 顧客選択画面に遷移
				$('#main').prop('action', '<s:url action="menuBack" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});

			//******************************************************
			// 戻るボタン押下時 (管理対象一覧画面に戻る)
			//******************************************************
			$(':button[id^="back2"]').click(function(e) {

				// 顧客選択画面に遷移
				$('#main').prop('action', '<s:url action="menuBackForAdmin" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});
		});

		//*******************************************
		// メニューボタン 実行関数（横指定なし）
		//*******************************************
		function openScreen(wname, url) {
			var w = createWindow(wname);

			var form = document.forms[0];
			form.action = url;
			form.target = wname;
			form.submit();
		}

		//*******************************************
		// メニューボタン 実行関数（横指定あり）
		//*******************************************
		function openScreenY(wname, url, y) {
			var w = createWindow(wname, 1024, y);

			var form = document.forms[0];
			form.action = url;
			form.target = wname;
			form.submit();
		}

		//*******************************************
		// 問い合わせ検索画面表示 実行関数（閲覧状況含む）
		//*******************************************
		function fnc_inquiry_search_display(serviceKbn, uketsukebiFrom, uketsukebiTo, extKanryoFlg, browseStatus) {
			// 検索条件の設定
			$('#main_condition_serviceKbn').val(serviceKbn);
			$('#main_condition_uketsukebiFrom').val(uketsukebiFrom);
			$('#main_condition_uketsukebiTo').val(uketsukebiTo);
			$('#main_condition_jokyo').val(extKanryoFlg);
			$('#main_condition_browseStatus').val(browseStatus);

			// 問い合わせ検索画面を開く
			openScreen('tb_inquiry_search_win', '<s:url action="inquirySearch" />');

			// パラメータを送ったら、空欄にする（次の操作のため）
			$('#main_condition_serviceKbn').val('');
			$('#main_condition_uketsukebiFrom').val('');
			$('#main_condition_uketsukebiTo').val('');
			$('#main_condition_jokyo').val('');
			$('#main_condition_browseStatus').val('');
		}

		//*******************************************
		// 依頼登録画面表示 実行関数
		//*******************************************
		function fnc_request_entry_display(toiawaseNo, toiawaseRirekiNo) {
			$('#toiawaseNo').val(toiawaseNo);
			$('#toiawaseRirekiNo').val(toiawaseRirekiNo);
			$('#dispKbn').val('2');

			// 依頼登録画面を開く
			<s:if test="userContext.isRealEstate()">
			openScreen('tb_request_search_win', '<s:url action="requestDetail" />');
			</s:if>
			<s:else>
			openScreen('tb_request_search_win', '<s:url action="requestEntryInit" />');
			</s:else>
		}

		//*******************************************
		// 顧客選択(不動産・管理会社時) 実行関数
		//*******************************************
		function fnc_select_kokyaku_id(selectedKokyakuId) {
			$('#main').addClass('ignore');

			$('#selectedKokyakuId').val(selectedKokyakuId);

			$('#main').prop('action', '<s:url action="menuChooseId" />');
			$('#main').prop('target', '_self');

			$('#main').submit();
			$('#main').removeClass('ignore');
		}

		//*******************************************
		// 文書ライブラリ ファイル選択実行関数
		//*******************************************
		function fnc_select_file(realFileNm) {
			$('#main').addClass('ignore');

			$('#realFileNm').val(realFileNm);

			$('#main').prop('action', '<s:url action="documentFileDownload" />');
			if (isIPad == true) {
				// iPadの場合は、_blankをターゲット
				$('#main').prop('target', '_blank');
			} else {
				// それ以外の場合は、iframeをターゲット
				$('#main').prop('target', 'tb_document_file_download_frame');
			}

			$('#main').submit();
		}

		//*******************************************
		// 画面再描画関数
		//*******************************************
		function fnc_reload() {
			$('#noFocus').val('1');
			$('#main').prop('action', '<s:url action="menuInit" />');
			$('#main').prop('target', '_self');
			$('#main').submit();
		}

		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">メニュー</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<s:if test="userContext.isInhouse() || userContext.isOutsourcerSv() || userContext.isOutsourcerOp()">
			<%@ include file="/WEB-INF/jsp/tb010_menu_links.jsp" %>
			</s:if>
			<s:elseif test="userContext.isRealEstate()">
				<div id="content">
					${f:out(systemTime)}時点の情報です。
				<s:if test="userContext.isKokyakuIdSelected()">
					<s:if test="!userContext.isSingleFlg()">
					<span style="float:right;">	<input type="button" id="back" class="btnSubmit" value=" 戻 る "></span>
					</s:if>
					<s:if test="userContext.isInhouseLoggedIn() && userContext.isSingleFlg()">
					<span style="float:right;">	<input type="button" id="back2" class="btnSubmit" value=" 戻 る "></span>
					</s:if>
					<table>
						<tr>
							<td valign="top">
								<h2>問い合わせ履歴</h2>
								<div id="datepicker" class="inquiry-cal"></div>
								<br>
								<table>
									<thead>
										<tr>
											<th width="" style="font-size: 8pt;">サービス</th>
											<th width="20%" style="font-size: 8pt;">契約数</th>
											<th width="20%" style="font-size: 8pt;">戸数</th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="inqueryStatusList" status="i">
										<tr>
											<td style="font-size: 8pt;">${f:out(serviceKbnNm)}</td>
											<s:if test="!isBuildingManagement()">
											<td align="right" style="font-size: 8pt;">${f:decimalFormat(keiyakuCount)}</td>
											</s:if>
											<s:else>
											<td><br></td>
											</s:else>
											<td align="right" style="font-size: 8pt;">${not empty kosuCount ? f:decimalFormat(kosuCount) : ""}</td>
										</tr>
										</s:iterator>
									</tbody>
								</table>

								<table>
									<thead>
										<tr>
											<th width="" style="font-size: 8pt;">未読情報</th>
											<th width="20%" style="font-size: 8pt;">件数</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td style="font-size: 8pt;">問い合わせ情報</td>
											<td align="right" style="font-size: 6pt;">
											<a href="JavaScript:fnc_inquiry_search_display('${serviceKbn}', '${f:dateFormat(summaryStartDt, "yyyyMMdd")}', '${f:dateFormat(summaryEndDt, "yyyyMMdd")}' ,'0', '0');" style="font-size: 8pt;">${f:decimalFormat(unreadCount)}</a></td>
										</tr>
									</tbody>
								</table>

							</td>

							<td valign="top">
								<h2>${f:dateFormat(summaryStartDt, "yyyy/MM/dd")} ～　${f:dateFormat(summaryEndDt, "yyyy/MM/dd")}　時点の状況</h2>
								<table>
									<tr>
										<td valign="top">
											<table>
												<thead>
													<tr>
														<th width="30%" >サービス</th>
														<th width="" >プラン</th>
														<th width="8%" >対応中</th>
														<th width="8%" >対応済</th>
														<th width="9%" >全対応<br>件数</th>
													</tr>
												</thead>
												<tbody>
													<s:iterator value="inqueryStatusList" status="i">
													<tr>
														<td><a href="JavaScript:fnc_inquiry_search_display('${serviceKbn}', '${f:dateFormat(summaryStartDt, "yyyyMMdd")}', '${f:dateFormat(summaryEndDt, "yyyyMMdd")}' ,'0', '');">
															${f:out(serviceKbnNm)}</a></td>
														<td style="font-size: 8pt;"><s:if test="isBuildingManagement()"><br></s:if><s:else>${f:out(planNmForDisplay)}</s:else></td>
														<td align="right">
															<a href="JavaScript:fnc_inquiry_search_display('${serviceKbn}', '${f:dateFormat(summaryStartDt, "yyyyMMdd")}', '${f:dateFormat(summaryEndDt, "yyyyMMdd")}','1', '');">
															${f:decimalFormat(inCompleteCount)}</a></td>
														<td align="right">
															<a href="JavaScript:fnc_inquiry_search_display('${serviceKbn}', '${f:dateFormat(summaryStartDt, "yyyyMMdd")}', '${f:dateFormat(summaryEndDt, "yyyyMMdd")}','2', '');">
															${f:decimalFormat(completedCount)}</a></td>
														<td align="right"><b>${f:decimalFormat(allCount)}</b></td>
													</tr>
													</s:iterator>
													<tr>
														<td><br></td>
														<th>合計</th>
														<td align="right"><a href="JavaScript:fnc_inquiry_search_display('', '${f:dateFormat(summaryStartDt, "yyyyMMdd")}', '${f:dateFormat(summaryEndDt, "yyyyMMdd")}','1', '');">
															<b>${f:decimalFormat(sumIncompleteCount)}</b></a></td>
														<td align="right"><a href="JavaScript:fnc_inquiry_search_display('', '${f:dateFormat(summaryStartDt, "yyyyMMdd")}', '${f:dateFormat(summaryEndDt, "yyyyMMdd")}','2', '');">
															<b>${f:decimalFormat(sumCompleteCount)}</b></a></td>
														<td align="right"><b>${f:decimalFormat(sumCorrespondence)}</b></td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<%@ include file="/WEB-INF/jsp/tb010_menu_links.jsp" %>
											<br>
											<h2>文書ライブラリ</h2>
											<div id="menu">
												<table class="borderoff">
													<s:iterator value="publishFileList" status="i">
													<tr>
														<td><a href="JavaScript:fnc_select_file('${realFileNm}');">
															${f:out(userFileNm)}</a></td>
													</tr>
													</s:iterator>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<s:hidden key="condition.serviceKbn" />
					<s:hidden key="condition.uketsukebiFrom" />
					<s:hidden key="condition.uketsukebiTo" />
					<s:hidden key="condition.jokyo" />
					<s:hidden key="condition.browseStatus" />
					<input type="hidden" id="realFileNm" name="realFileNm" value="" />
				</s:if>
				<s:else>
					<s:if test="userContext.isInhouseLoggedIn()">
					<span style="float:right;">	<input type="button" id="back2" class="btnSubmit" value=" 戻 る "></span>
					</s:if>
					<table>
						<tr>
						<td valign="top">
							<h2>問い合わせ履歴</h2>
							<div id="datepicker" class="inquiry-cal"></div>
							<br>
						</td>
						<td valign="top">
							<h2>${f:dateFormat(summaryStartDt, "yyyy/MM/dd")} ～　${f:dateFormat(summaryEndDt, "yyyy/MM/dd")}　時点の状況</h2>
							<table>
								<thead>
									<tr>
										<th width="" >名称</th>
										<th width="12%" >対応中</th>
										<th width="12%" >対応済</th>
										<th width="12%" >全対応件数</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator var="dto" value="inqueryStatusList" status="i">
									<tr>
									<s:if test="#dto.getRefKokyaku().isLowerDisplay() && #dto.hasKeiyaku()">
										<td><a href="JavaScript:fnc_select_kokyaku_id('${seikyusakiKokyakuId}');">${f:out(refKokyaku.kaisoIndention)}${f:out(kanjiNm)}</a></td>
									</s:if>
									<s:else>
										<td>${f:out(refKokyaku.kaisoIndention)}${f:out(kanjiNm)}</td>
									</s:else>
									<s:if test="!#dto.getRefKokyaku().isLowerDisplay() || #dto.hasKeiyaku()">
										<td align="right">${f:decimalFormat(inCompleteCount)}</td>
										<td align="right">${f:decimalFormat(completedCount)}</td>
										<td align="right">${f:decimalFormat(allCount)}</td>
									</s:if>
									<s:else>
										<td align="right"></td>
										<td align="right"></td>
										<td align="right"></td>
									</s:else>
									</tr>
									</s:iterator>
									<tr>
										<th>合計</th>
										<td align="right">${f:decimalFormat(sumIncompleteCount)}</td>
										<td align="right">${f:decimalFormat(sumCompleteCount)}</td>
										<td align="right">${f:decimalFormat(sumCorrespondence)}</td>
									</tr>
								</tbody>
							</table>
							<br>
							<h2>メニュー</h2>
							<table class="borderoff">
								<tr class="borderoff" style="padding:10px;">
									<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
										<a class="btnMenu1" href="javascript:openScreen('tb_inquiry_search_win', '<s:url action="inquirySearchInit" />');"><span class="menuTitle1">問い合わせ検索</span></a>
									</td>
									<s:if test="existsItakuRefKokyaku()">
									<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
										<a class="btnMenu1" href="javascript:openScreen('tb_customer_search_win', '<s:url action="customerSearchInit" />');"><span class="menuTitle1">物件・入居者検索</span></a>
									</td>
									<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
										<br>
									</td>
									</s:if>
									<s:else>
									<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
										<a class="btnMenu1" href="javascript:openScreen('tb_request_search_win', '<s:url action="requestSearchInit" />');"><span class="menuTitle1">依頼検索</span></a>
									</td>
									<%-- iPadでログインしている際は、枠がはみ出てしまうため、ボタン２つで折り返し --%>
									<s:if test="isIPad()">
									<br>
									</s:if>
									<s:else>
									<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
										<a class="btnMenu1" href="javascript:openScreen('tb_customer_search_win', '<s:url action="customerSearchInit" />');"><span class="menuTitle1">物件・入居者検索</span></a>
									</td>
									</s:else>
									</s:else>
								</tr>
								<%-- iPadでログインしている際は、枠がはみ出てしまうため、ボタン２つで折り返し --%>
								<s:if test="!existsItakuRefKokyaku() && isIPad()">
								<tr class="borderoff" style="padding:10px;">
									<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
										<a class="btnMenu1" href="javascript:openScreen('tb_customer_search_win', '<s:url action="customerSearchInit" />');"><span class="menuTitle1">物件・入居者検索</span></a>
									</td>
									<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
										<br>
									</td>
									<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
										<br>
									</td>
								</tr>
								</s:if>
								<tr class="borderoff" style="padding:10px;">
									<td width="33%" valign="top" class="borderoff" style="padding:5px;">
										<a class="btnMenu3" href="javascript:openScreenY('tb_password_change_win', '<s:url action="passwordChangeInit" />', 650);"><span class="menuTitle1">パスワード変更</span></a>
									</td>
									<td width="33%" valign="top" class="borderoff" style="padding:5px;">
										<br>
									</td>
									<td width="33%" valign="top" class="borderoff" style="padding:5px;">
										<br>
									</td>
								</tr>
							</table>

							<br>
							<h2>文書ライブラリ</h2>
							<div id="menu">
								<table class="borderoff">
									<s:iterator value="publishFileList" status="i">
									<tr>
										<td><a href="JavaScript:fnc_select_file('${realFileNm}');">${f:out(userFileNm)}</a></td>
									</tr>
									</s:iterator>
								</table>
							</div>
							<s:hidden key="condition.serviceKbn" />
							<s:hidden key="condition.uketsukebiFrom" />
							<s:hidden key="condition.uketsukebiTo" />
							<s:hidden key="condition.jokyo" />
							<input type="hidden" id="selectedKokyakuId" name="selectedKokyakuId" value="" />
							<input type="hidden" id="realFileNm" name="realFileNm" value="" />
						</td>
					</tr>
				</table>
				</s:else>
				</div>
			</s:elseif>
			<s:elseif test="userContext.isConstractor()">
			<div id="content">
				${f:out(systemTime)}時点の情報です。
				<h2>対応中の案件</h2>
				<table>
					<thead>
						<tr>
							<th width="2%" rowspan="2"><br></th>
							<th width="11%">受付番号</th>
							<th width="15%">受付形態</th>
							<th width="20%">名称</th>
							<th width="" rowspan="2">依頼内容</th>
						</tr>
						<tr>
							<th>受付日</th>
							<th>訪問希望日</th>
							<th>依頼者名</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="requestList" status="i">
						<tr class="${f:odd(i.index, 'odd,even')}">
							<td rowspan="2"><a href="JavaScript:fnc_request_entry_display('${toiawaseNo}', '${f:out(toiawaseRirekiNo)}');">詳<br>細</a></td>
							<td>${f:out(toiawaseAndRirekiNo)}</td>
							<td>${f:out(uketsukeKeitaiKbnNm)}</td>
							<td>${f:out(kanjiNm)}</td>
							<td rowspan="2">${f:out(iraiNaiyoByRemoveNewLine)}</td>
						</tr>
						<tr class="${f:odd(i.index, 'odd,even')}">
							<td>${f:dateFormat(uketsukeYmd, "yyyy/MM/dd")}</td>
							<td>${f:dateFormat(homonKiboYmd, "yyyy/MM/dd")} ${f:out(homonKiboJikanKbnNm)}</td>
							<td>${f:out(iraishaNm)}</td>
						</tr>
						</s:iterator>
					</tbody>
				</table>

				<input type="hidden" id="toiawaseNo" name="toiawaseNo" value="" />
				<input type="hidden" id="toiawaseRirekiNo" name="toiawaseRirekiNo" value="" />
				<input type="hidden" id="dispKbn" name="dispKbn" value="" />
			</div>
			<div id="content">
				<%@ include file="/WEB-INF/jsp/tb010_menu_links.jsp" %>
			</div>
			</s:elseif>

			<br>
			<div id="content">
				<h2>お知らせ</h2>
				<div class="information">
					<table width="100%" cellspacing="2" cellpadding="2" align="center" border="0">
						<s:iterator value="infomationList" status="i">
						<tr>
							<td width="4%" align="center" valign="top" style="color:red ;font-weight:bold;">
							<s:if test="isNewArrival(newArrivalNoticeDate)">new</s:if>
							</td>
							<td width="10%" valign="top" align="left">${f:dateFormat(startDt, "yyyy/MM/dd")}</td>
							<s:if test="isTargetAll() || isTargetRealStateAll() || isTargetContractorAll() || isTargetOutsourcerAll()">
							<td width="20%" valign="top" align="left">利用者様へのご連絡</td>
							</s:if>
							<s:else>
							<td width="20%" valign="top" align="left">${f:out(informationUserName)} 様へのご連絡</td>
							</s:else>
							<td valign="top">
								${f:br(f:out(message))}
							</td>
							<td valign="top">
								&nbsp;
							</td>
						</tr>
						</s:iterator>
					</table>
				</div>
			</div>

			<iframe src="" width="0px" height="0px" name="tb_document_file_download_frame" frameborder="0"></iframe>

			<s:tokenCheck displayId="TB010" />
			<input type="hidden" id="noFocus" name="noFocus" value="<s:property value="%{#parameters['noFocus']}" />" />
			<br>
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>

