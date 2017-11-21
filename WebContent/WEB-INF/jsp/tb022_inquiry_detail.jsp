<%--*****************************************************
	問い合わせ詳細画面
	作成者：岩田
	作成日：2014/05/27
	更新日：2015/09/17 松葉 作業報告書ボタン、ファイル表示追加
	更新日：2015/10/22 小林 既読未読設定追加
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//

		<%-- 確認ダイアログ(初期値は空) --%>
		var confirmation = function() {return true;};

		$(function() {

			var validation = $("#main").validate({
				submitHandler: function(form) {
					if (!confirmation()) {
						confirmation = function() {return true;};

						return false;
					}

					// 更新ボタン押下時のみ非活性処理を実施
					if ($('#buttonId').val() == "btnUpdate") {
						$(":input").prop("disabled", false);
						$("input:button").prop("disabled", true);
						form.submit();
						$(":input").prop("disabled", true);

						confirmation = function() {return true;};

						return false;
					} else {

						form.submit();

						confirmation = function() {return true;};
						
						return false;
					}
				}
			});

			//*******************************************
			// 戻るボタン押下時 実行関数
			//*******************************************
			$('#btnReturn').click(function(e) {
				$('#main').prop('action', '<s:url action="inquirySearch" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});
			//*******************************************
			// 戻るボタン押下時 実行関数
			//*******************************************
			$('#btnReturn2').click(function(e) {
				$('#main').prop('action', '<s:url action="inquirySearch" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});
			//*******************************************
			// 入電報告書印刷ボタン押下時 実行関数
			//*******************************************
			$('#btnNyudenPrint').click(function(e) {
				$('#main').prop('action', '<s:url action="incomingCallReportDownload" />');
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb_incoming_call_report_frame');
				}

				$('#main').submit();
			});
			//******************************************************
			// 作業報告書印刷ボタン押下時
			//******************************************************
			$('#btnSagyoPrint').click(function(e) {
				$('#main').addClass('ignore');

				$('#isPrintExecute').val("1");

				$('#main').prop('action', '<s:url action="inquiryEntryDownload" />');
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb_work_report_frame');
				}

				$('#toiawaseRirekiNo').val('1');
				$('#targetKokyakuId').val($('#main_kokyakuId').val());
				$('#shoriKbn').val('2');
				
				$('#main').submit();

				$('#main').removeClass('ignore');
			});
			//******************************************************
			// メニューボタン押下時
			//******************************************************
			$('[id^="btnMenu"]').click(function(e) {
				$('#main').addClass('ignore');

				$('#isPrintExecute').val("");

				$('#main').prop('action', '<s:url action="menuInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();

				$('#main').removeClass('ignore');
			});
			
			//******************************************************
			// 更新ボタン押下時
			//******************************************************
			$('#btnUpdate').click(function(e) {
				
				confirmation = function() {
					return confirm(jQuery.validator.format(INF0001, "未読／既読情報", "更新"));
				};
				
				$('#main').prop('action', '<s:url action="inquiryDetailUpdate" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});

			//******************************************************
			// ファイルリンク押下時
			//******************************************************
			$('.linkFileDownload').click(function(e) {
				$('#main_toiawaseNo').val($(this).attr('toiawaseNo'));
				$('#main_fileIndex').val($(this).attr('fileIndex'));

				$('#main').prop('action', '<s:url action="inquiryEntryFileDownload" />');

				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb_inquiry_file_frame');
				}

				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');

				$('#main_fileIndex').val('');
			});
		});

		//*******************************************
		// 依頼リンク押下時 実行関数
		//*******************************************
		function fnc_irai_link(toiawaseNo, toiawaseRirekiNo) {
			// 同一ウィンドウに問い合わせ内容詳細・作業状況登録画面を表示
			$('#main').addClass('ignore');

			var orgDispKbn = $('#main_dispKbn').val();
			$('#main_toiawaseNo').val(toiawaseNo);
			$('#toiawaseRirekiNo').val(toiawaseRirekiNo);
			$('#main_dispKbn').val('2');

			var w = createWindow("tb_request_search_win");

			<s:if test="#session.userContext.isRealEstate()">
			$('#main').prop('action', '<s:url action="requestDetail" />');
			</s:if>
			<s:else>
			$('#main').prop('action', '<s:url action="requestEntryInit" />');
			</s:else>

			$('#main').prop('target', w.name);

			$('#main').submit();

			$('#main').removeClass('ignore');
			
			$('#main_dispKbn').val(orgDispKbn);
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">問い合わせ詳細</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<%-- 顧客基本情報 ここから --%>
			<br>
			<div class="right">
				<s:if test="isFromInquirySearch()">
				<%-- 問い合わせ検索からの遷移の場合は、戻るボタンを表示 --%>
					<input type="button" id="btnReturn" class="btnSubmit" value=" 戻 る " />
				</s:if>
				<s:elseif test="isFromDirectLogin()">
				<%-- ダイレクトログインからの遷移の場合は、メニューボタンを表示 --%>
					<input type="button" id="btnMenu1" class="btnSubmit" value=" メニュー " />
				</s:elseif>
			</div>
			<s:if test = "toiawaseEntity != null">
				<%@ include file="/WEB-INF/jsp/tb040_customer_common_info.jsp" %>
			<%-- 顧客基本情報 ここまで --%>

			<%-- 問い合わせ基本情報 ここから --%>
			<div class="content">
				<h2>問い合わせ基本情報</h2>
				<table width="90%">
					<tr>
						<td align="right">
							最終更新：${f:dateFormat(toiawaseEntity.gamenUpdDt, "yyyy/MM/dd HH:mm")}
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table>
								<tr>
									<th>受付番号</th>
									<td colspan="5">${f:out(toiawaseEntity.toiawaseNo)}</td>
								<tr>
									<th>依頼者</th>
									<td colspan="5">
										${f:out(toiawaseEntity.iraishaNm)}
									</td>
								</tr>
								<tr>
									<th width="10%"><span id="id_uketsuke_dt">受付日</span></th>
									<td width="20%">${f:dateFormat(toiawaseEntity.uketsukeYmd, "yyyy/MM/dd")}</td>
									<th width= "8%">受付時間</th>
									<td width="20%">${f:out(toiawaseEntity.uketsukeJikan)}</td>
									<th width= "8%">受付者</th>
									<td width="34%"><span class="font1">&nbsp;${f:out(toiawaseEntity.uketsukeNm)}</span></td>
								</tr>
								<tr>
									<th>区分</th>
									<td colspan="4">
										${f:out(toiawaseEntity.toiawaseKbn1Nm)}
										&nbsp;
										${f:out(toiawaseEntity.toiawaseKbn2Nm)}
										&nbsp;
										${f:out(toiawaseEntity.toiawaseKbn3Nm)}
										&nbsp;
										${f:out(toiawaseEntity.toiawaseKbn4Nm)}
										&nbsp;
									</td>
									<td align="right"><br></td>

								</tr>
								<tr>
									<th>問い合わせ<br>内容</th>
									<td colspan="5">${f:out(toiawaseEntity.uketsukeKeitaiKbnNm)}
													<br>${f:br(f:out(toiawaseEntity.toiawaseNaiyo))}</td>
								</tr>
								<tr>
									<th>依頼有無</th>
									<td colspan="3">
										${f:out(toiawaseEntity.iraiUmuKbnNm)}
									</td>
									<th>最終履歴</th>
									<s:if test ="toiawaseRirekiEntity != null">
										<td>${f:out(toiawaseRirekiEntity.jokyoKbnNm)}（${f:dateFormat(toiawaseRirekiEntity.uketsukeYmd, "yyyy/MM/dd")}&nbsp;${f:out(toiawaseRirekiEntity.uketsukeJikan)}）</td>
									</s:if>
									<s:else>
										<td></td>
									</s:else>
								</tr>
								<s:iterator value="uploadedFiles" status="i" var="file">
									<tr>
										<s:if test="#i.count == 1">
											<th rowspan="${fn:length(uploadedFiles)}">ファイル</th>
										</s:if>
										<td colspan="5">
											<s:if test="%{#file != null}">
												<a href="#" toiawaseNo="${toiawaseNo}" fileIndex="${fileIndex}" class="linkFileDownload" title="${f:out(baseFileNm)}">${f:out(baseFileNm)}</a><br>
											</s:if>
											<s:else>
												<br>
											</s:else>
										</td>
									</tr>
								</s:iterator>

							</table>
						</td>
					</tr>
				</table>
				<br>
				<div class="right">
				<s:if test = "isReporting(toiawaseEntity.hokokushoKokaiFlg)">
					<input type="button" id="btnNyudenPrint" class="btnSubmit" value="入電報告書印刷">
				</s:if>
				<s:if test="canPrintWorkReport()">
					<input type="button" id="btnSagyoPrint" class="btnSubmit" value="作業報告書印刷">
				</s:if>
					<s:if test="isFromInquirySearch()">
					<%-- 問い合わせ検索からの遷移の場合は、戻るボタンを表示 --%>
						<input type="button" id="btnReturn2" class="btnSubmit" value=" 戻 る " />
					</s:if>
					<s:elseif test="isFromDirectLogin()">
					<%-- ダイレクトログインからの遷移の場合は、メニューボタンを表示 --%>
						<input type="button" id="btnMenu2" class="btnSubmit" value=" メニュー " />
					</s:elseif>
				</div>
			</div>
			<%-- 問い合わせ基本情報 ここまで --%>

			<%-- 問い合わせ履歴 ここから --%>
			<div class="content">
				<h2>問い合わせ履歴</h2>
				<div class="right">
					<s:if test="%{#session.userContext.isRealEstateLoggedIn()}">
						<s:if test="isToiawaseRirekiList()">
							<input type="button" id="btnUpdate" class="btnSubmit" value=" 更 新 " />
						</s:if>
					</s:if>
				</div>
				<table width="90%">
					<tr>
						<td colspan="2">
							<table>
							<thead>
								<tr>
									<th width= "6%">履歴No</th>
									<th width="10%">日時</th>
									<th width="10%">担当者</th>
									<th width="">内容</th>
									<th width="10%">状況</th>
									<th width= "4%">&nbsp;</th>
									<th width= "6%">未既読</th>
								</tr>
							</thead>
							<tbody>
							<s:iterator value="toiawaseRirekiList" status="i">
								<tr class="${f:odd(i.index, 'odd,even')}">
									<td align="center" <s:if test="isUnread()">class="unread"</s:if>>${f:out(toiawaseRirekiNo)}</td>
									<td align="center" <s:if test="isUnread()">class="unread"</s:if>>${f:dateFormat(uketsukeYmd, "yyyy/MM/dd")}&nbsp;${f:out(uketsukeJikan)}</td>
									<td <s:if test="isUnread()">class="unread"</s:if>>${f:out(tantoshaNm)}</td>
									<td <s:if test="isUnread()">class="unread"</s:if>>${f:br(f:out(toiawaseNaiyo))}</td>
									<td <s:if test="isUnread()">class="unread"</s:if>>${f:out(jokyoKbnNm)}</td>
									<td align="center">
										<s:if test = "iraiExsits">
											<a href="JavaScript:fnc_irai_link('${f:out(toiawaseNo)}','${f:out(toiawaseRirekiNo)}');"><span class="fontlink2">依頼</span></a>
										</s:if>
									</td>
									<td <s:if test="isUnread()">class="unread"</s:if>>
										<s:if test="%{#session.userContext.isRealEstateLoggedIn()}">
											<label><s:checkbox key="browseStatusFlgList[%{#i.index}]" fieldValue="1"  value="%{isRead()}"/>&nbsp;既読</label>
										</s:if>
										<s:else>
											${f:out(browseStatusFlgNm)}
										</s:else>
									</td>
									<s:hidden key="toiawaseRirekiNoList[%{#i.index}]" value="%{toiawaseRirekiNo}"/>
								</tr>
							</s:iterator>
							</tbody>
							</table>
						</td>
					</tr>
				</table>
			</div>

			<iframe src="" width="0px" height="0px" name="tb_incoming_call_report_frame" frameborder="0"></iframe>
			<iframe src="" width="0px" height="0px" name="tb_work_report_frame" frameborder="0"></iframe>
			<iframe src="" width="0px" height="0px" name="tb_inquiry_file_frame" frameborder="0"></iframe>

			</s:if>
			<%-- 問い合わせ履歴 ここまで --%>
			<s:hidden key="toiawaseNo" />

			<%-- 検索条件引き継ぎ。 --%>
			${f:writeHidden2(condition, "condition", excludeField)}

			<s:hidden key="fileIndex" />

			<%-- 引き渡し用hidden項目 --%>
			<input type="hidden" id="toiawaseRirekiNo" name="toiawaseRirekiNo" value="" />
			<input type="hidden" id="targetKokyakuId" name="targetKokyakuId" value="" />
			<input type="hidden" id="shoriKbn" name="shoriKbn" value="" />
			<input type="hidden" id="actionType" name="actionType" value="" />
			<s:hidden key="dispKbn" />
			<s:hidden key="gamenKbn" />

			<s:tokenCheck displayId="TB022" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
