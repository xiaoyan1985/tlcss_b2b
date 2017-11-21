<%--*****************************************************
	問い合わせ検索画面
	作成者：岩田
	作成日：2014/05/19
	更新日：2015/10/19 小林
	      ：2016/07/12 H.Yamamura 問い合わせ区分１～４追加
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			window.focus();

			// CSVダウンロードが表示される条件かつiPadの場合はCSVダウンロードボタンを使用不可に設定
			<s:if test="%{#session.userContext.isAdministrativeInhouse() || #session.userContext.isGeneralInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp()}">
				if (isIPad == true) {
					$("#btnDownload").prop("disabled", true);
				}
			</s:if>

			var validation = $("#main")
			.validate({
				rules: {
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
					"condition.uketsukebiFrom": "dateYMD",
					"condition.uketsukebiTo": "dateYMD"
				},
				groups: {
					main_txt_kana : "condition\.kanaNm1 condition\.kanaNm2",
					main_txt_kanji : "condition\.kanjiNm1 condition\.kanjiNm2",
					main_txt_uketsukebi : "condition\.uketsukebiFrom condition\.uketsukebiTo"
				},
				invalidHandler: function(form, validator) {
					alert(ART0015);
				},
				submitHandler: function(form) {
					var buttonId = $('#main_buttonId').val();

					// 検索ボタンの場合
					if (buttonId == 'btnSearch') {
						$(":input").prop("disabled", false);
						$("input:button").prop("disabled", true);
						form.submit();
						$(":input").prop("disabled", true);
					// その他のボタンの場合
					} else {
						form.submit();
					}
				}
			});

			//*******************************************
			// クリアボタン押下時処理
			//*******************************************
			$('#btnClear').click(function(e) {

				$('#main_buttonId').val('btnClear');

				$('input[type=checkbox]').removeAttr('checked');
				$('input[type=text]').val('');
				$("input[type=radio][value='0']").prop('checked', true);
				$('select').prop('selectedIndex', 0);

				$("[placeholder]", "#main").each(function(i,e) {
					$(e).val($(e).data('placeholder-string'));
					if (isValidPlaceHolder == false) {
						$(e).css('color', 'silver');
					}
				});

				$(":input").removeClass('error');
				$("label.error").html('');

				$('#main_sel_sort_order').val('3');

				<s:if test="%{#session.userContext.isRealEstate()}">
					// 未読／既読の全てラジオチェックをONに変更
					$("#main_condition_browseStatus").prop('checked', true);
				</s:if>

			});

			//******************************************************
			// 検索ボタン押下時
			//******************************************************
			$('#btnSearch').click(function(e) {

				$('#main_buttonId').val('btnSearch');

				$('.dateYMD').each(function(i,e) {
					var date = convertDate($(this).val());
					if (date != null) {
						$(this).val($.datepicker.formatDate($(e).datepicker("option", "dateFormat"), date));
					}
				});

				$('#main').prop('action', '<s:url action="inquirySearch" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});

			var d = new Date();

			//******************************************************
			// CSVダウンロードボタン押下時
			//******************************************************
			$('#btnDownload').click(function(e) {
				
				$('#main_buttonId').val('btnDownload');

				$('#main').prop('action', '<s:url action="inquirySearchDownload" />');
				$('#main').prop('target', 'tb_inquiry_search_csv_frame');

				$('#main').submit();

				// プレースホルダーの表示が消えてしまうため、元に戻す
				$("[placeholder]", "#main").each(function(i,e) {
					if ($(e).val() == "") {
						// 入力値が空欄の場合のみ、プレースホルダーの表示を戻す
						$(e).val($(e).data('placeholder-string'));
						if (isValidPlaceHolder == false) {
							$(e).css('color', 'silver');
						}
					}
				});
			});

			//******************************************************
			// 受付日FROM －ボタン押下時
			//******************************************************
			$('#btnUkeFromMainus').click(function(e) {

				if ($('#main_condition_uketsukebiFrom').val() == '' || $('#main_condition_uketsukebiFrom').val() == $('#main_condition_uketsukebiFrom').data('placeholder-string')) {
					// 空欄だった場合は、何もしない
					return false;
				}
				if (DateChk($('#main_condition_uketsukebiFrom').val()) == -1) {
					// 日付として妥当でない場合は、何もしない
					return false;
				}
				// 受付日From、受付日Toにセット
				var dt = convertDate($('#main_condition_uketsukebiFrom').val());
				var month = dt.getMonth();
				var day = dt.getDate() - 1 ;
				var lt = new Date(dt.getFullYear(), month, day);
				fnc_set_uketsukebi_from(lt);
			});

			//******************************************************
			// 受付日FROM ＋ボタン押下時
			//******************************************************
			$('#btnUkeFromPlus').click(function(e) {

				if ($('#main_condition_uketsukebiFrom').val() == '' || $('#main_condition_uketsukebiFrom').val() == $('#main_condition_uketsukebiFrom').data('placeholder-string')) {
					// 空欄だった場合は、何もしない
					return false;
				}
				if (DateChk($('#main_condition_uketsukebiFrom').val()) == -1) {
					// 日付として妥当でない場合は、何もしない
					return false;
				}
				// 受付日From、受付日Toにセット
				var dt = convertDate($('#main_condition_uketsukebiFrom').val());
				var month = dt.getMonth();
				var day = dt.getDate() + 1 ;
				var lt = new Date(dt.getFullYear(), month, day);
				fnc_set_uketsukebi_from(lt);
			});

			//******************************************************
			// 受付日FROM 本日ボタン押下時
			//******************************************************
			$('#btnUkeFromToday').click(function(e) {

				// 受付日From、受付日Toにセット
				fnc_set_uketsukebi_from(d);
			});

			//******************************************************
			// 受付日TO －ボタン押下時
			//******************************************************
			$('#btnUkeToMainus').click(function(e) {

				if ($('#main_condition_uketsukebiTo').val() == '' || $('#main_condition_uketsukebiTo').val() == $('#main_condition_uketsukebiTo').data('placeholder-string')) {
					// 空欄だった場合は、何もしない
					return false;
				}
				if (DateChk($('#main_condition_uketsukebiTo').val()) == -1) {
					// 日付として妥当でない場合は、何もしない
					return false;
				}
				// 受付日From、受付日Toにセット
				var dt = convertDate($('#main_condition_uketsukebiTo').val());
				var month = dt.getMonth();
				var day = dt.getDate() - 1 ;
				var lt = new Date(dt.getFullYear(), month, day);
				fnc_set_uketsukebi_to(lt);
			});

			//******************************************************
			// 受付日TO ＋ボタン押下時
			//******************************************************
			$('#btnUkeToPlus').click(function(e) {

				if ($('#main_condition_uketsukebiTo').val() == '' || $('#main_condition_uketsukebiTo').val() == $('#main_condition_uketsukebiTo').data('placeholder-string')) {
					// 空欄だった場合は、何もしない
					return false;
				}
				if (DateChk($('#main_condition_uketsukebiTo').val()) == -1) {
					// 日付として妥当でない場合は、何もしない
					return false;
				}
				// 受付日From、受付日Toにセット
				var dt = convertDate($('#main_condition_uketsukebiTo').val());
				var month = dt.getMonth();
				var day = dt.getDate() + 1 ;
				var lt = new Date(dt.getFullYear(), month, day);
				fnc_set_uketsukebi_to(lt);
			});

			//******************************************************
			// 受付日TO 本日ボタン押下時
			//******************************************************
			$('#btnUkeToToday').click(function(e) {

				// 受付日From、受付日Toにセット
				fnc_set_uketsukebi_to(d);
			});

			// 受付日FROM初期化
			if (DateChk2($('#main_condition_uketsukebiFrom').val()) != -1) {
				$('#main_condition_uketsukebiFrom').val(ConvSlash($('#main_condition_uketsukebiFrom').val(), 1));
			}
			// 受付日FROM初期化
			if (DateChk2($('#main_condition_uketsukebiTo').val()) != -1) {
				$('#main_condition_uketsukebiTo').val(ConvSlash($('#main_condition_uketsukebiTo').val(), 1));
			}
			
			//*******************************************
			// 詳細リンク押下時、詳細画面へ遷移する
			//*******************************************
			$('.linkInquiryDetail').click(function(e) {
				// 同一ウィンドウに問い合わせ内容詳細・作業状況登録画面を表示
				$('#main').addClass('ignore');

				$("#toiawaseNo").val($(this).attr('toiawaseNo'));
				$('#dispKbn').val('1');

				$('#main').prop('action', '<s:url action="inquiryDetailInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();

				$('#main').removeClass('ignore');
			});
			
			//*******************************************
			// 詳細リンク押下時、登録画面へ遷移する
			//*******************************************
			$('.linkInquiryEntry').click(function(e) {
				
				$('#main').addClass('ignore');

				$("#toiawaseNo").val($(this).attr('toiawaseNo'));
				$('#dispKbn').val('tb021');
				
				$('#main').prop('action', '<s:url action="inquiryEntryUpdateInit" />');
				$('#main').prop('target', '_self');

				$('#main').submit();

				$('#main').removeClass('ignore');
			});
		});

		//******************************************************
		// 受付日FROM設定処理
		//******************************************************
		function fnc_set_uketsukebi_from(from) {
			$('#main_condition_uketsukebiFrom').val(from);

			$('#main_condition_uketsukebiFrom').val($.datepicker.formatDate($('#main_condition_uketsukebiFrom').datepicker("option", "dateFormat"), from));

			$('#main_condition_uketsukebiFrom').css('color', $(document.body).css('color'));
		}

		//******************************************************
		// 受付日TO設定処理
		//******************************************************
		function fnc_set_uketsukebi_to(to) {
			$('#main_condition_uketsukebiTo').val(to);

			$('#main_condition_uketsukebiTo').val($.datepicker.formatDate($('#main_condition_uketsukebiTo').datepicker("option", "dateFormat"), to));

			$('#main_condition_uketsukebiTo').css('color', $(document.body).css('color'));
		}

		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">問い合わせ検索</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<%-- 検索条件 ここから --%>
			<div class="content">
				<h2>検索条件</h2>
				<table>
					<tr>
						<td colspan="2">
							<table width="100%">
								<tr>
									<th width="10%">サービス</th>
									<td width="50%">
										<s:select key="condition.serviceKbn" list="serviceMEntityList" listKey="serviceKbn" listValue="serviceKbnNm" emptyOption="true" />
									<th  width="10%" rowspan="5">住所</th>
									<td rowspan="5">
										<span class="font5">※全てあいまい検索</span><br>
										<s:textfield key="condition.jusho1" maxlength="10" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="都道府県(全角)" />
										<br><s:textfield key="condition.jusho2" maxlength="30" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="市区町村(全角)" />
										<br><s:textfield key="condition.jusho3" maxlength="30" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="町/大字(全角)" />
										<br><s:textfield key="condition.jusho4" maxlength="30" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="番地(全角)" />
										<br><s:textfield key="condition.jusho5" maxlength="40" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="アパート･マンション(全角)" />
										<br><s:textfield key="condition.roomNo" maxlength="20" cssClass="zenhankaku" cssStyle="width:220px;" placeholder="部屋番号" />
									</td>
								</tr>
								<tr>
									<th>受付番号</th>
									<td>
										<s:textfield key="condition.toiawaseNo" maxlength="10" cssClass="hankaku" cssStyle="width:100px;" placeholder="(半角英数字)" />
									</td>
								</tr>
								<tr>
									<th>カナ名称</th>
									<td>
										<s:textfield key="condition.kanaNm1" maxlength="40" cssClass="zenhankaku" cssStyle="width:160px;" placeholder="(全角カナ)" />
										<s:textfield key="condition.kanaNm2" maxlength="40" cssClass="zenhankaku" cssStyle="width:140px;" placeholder="(全角カナ)" />
										<s:if test="isIPad()">
											<span class="iPad_display"><br></span>
										</s:if>
										<span class="font5">&nbsp;※あいまい検索</span><br>
										<label for="main_txt_kana" class="error" generated="true" /></label>
									</td>
								</tr>
								<tr>
									<th>漢字名称</th>
									<td>
										<s:textfield key="condition.kanjiNm1" maxlength="40" cssClass="zenhankaku" cssStyle="width:160px;" placeholder="(全角)" />
										<s:textfield key="condition.kanjiNm2" maxlength="40" cssClass="zenhankaku" cssStyle="width:140px;" placeholder="(全角)" />
										<s:if test="isIPad()">
											<span class="iPad_display"><br></span>
										</s:if>
										<span class="font5">&nbsp;※あいまい検索</span><br>
										<label for="main_txt_kanji" class="error" generated="true" /></label>
									</td>
								</tr>
								<tr>
									<th>電話番号</th>
									<td>
										<s:textfield key="condition.telNo" maxlength="15" cssClass="hankaku" cssStyle="width:140px;" placeholder="(半角数字)" />
										<span class="font5">(ハイフンなし)</span>
									</td>
								</tr>
								<tr>
									<th >受付日</th>
									<td colspan="3">
										<s:textfield key="condition.uketsukebiFrom" maxlength="10" cssClass="dateYMD" cssStyle="width:90px;" placeholder="yyyymmdd" />
										<input type="button" id="btnUkeFromMainus" value="－">
										<input type="button" id="btnUkeFromToday" value="本日" />
										<input type="button" id="btnUkeFromPlus" value="＋">
										～
										<s:textfield key="condition.uketsukebiTo" maxlength="10" cssClass="dateYMD" cssStyle="width:90px;" placeholder="yyyymmdd" />
										<input type="button" id="btnUkeToMainus" value="－">
										<input type="button" id="btnUkeToToday" value="本日" />
										<input type="button" id="btnUkeToPlus" value="＋">
										<label for="main_txt_uketsukebi" class="error" generated="true" /></label>
									</td>
								</tr>
								<tr>
									<th >区分</th>
									<td colspan="3">
										<s:select key="condition.kubun1" list="toiawase1List" listKey="kbn" listValue="kbnNm" emptyOption="true" id="sel_toiawase_1" />
										<s:select key="condition.kubun2" list="toiawase2List" listKey="kbn" listValue="kbnNm" emptyOption="true" id="sel_toiawase_2" />
										<s:select key="condition.kubun3" list="toiawase3List" listKey="kbn" listValue="kbnNm" emptyOption="true" id="sel_toiawase_3" />
										<s:select key="condition.kubun4" list="toiawase4List" listKey="kbn" listValue="kbnNm" emptyOption="true" id="sel_toiawase_4" />
									</td>
								</tr>
								<tr>
									<th>状況</th>
									<td><s:radio key="condition.jokyo" list="jokyoList" listKey="comCd" listValue="externalSiteVal"/></td>
									<th>ソート</th>
									<td>
										<s:select key="condition.sortOrder" list="#{0:'受付日の降順', 1:'受付日の昇順'}" emptyOption="false" />
									</td>
								</tr>
								<s:if test="%{#session.userContext.isRealEstate()}">
									<tr>
										<th width="10%">未読／既読</th>
										<td colspan="3">
											<s:radio key="condition.browseStatus" list="browseStatusList" listKey="comCd" listValue="externalSiteVal" />
										</td>
									</tr>
								</s:if>
							</table>
						</td>
					</tr>
				</table>

				<div class="right">
					<span style="float:left">
						<s:if test="%{#session.userContext.isAdministrativeInhouse() || #session.userContext.isGeneralInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp()}">
							&nbsp;<input type="button" id="btnDownload" class="btnSubmit" value=" CSVダウンロード " />
						</s:if>
					</span>
					&nbsp;<input type="button" id="btnSearch" class="btnSubmit" value=" 検 索 " />
					&nbsp;<input type="button" id="btnClear" class="btnSubmit" value=" クリア " />
					&nbsp;<input type="button" id="btnClose" class="btnSubmit" value=" 閉じる " />
				</div>

				<input type="hidden" id="toiawaseNo" name="toiawaseNo" value="" />
				<input type="hidden" id="dispKbn" name="dispKbn" value="" />

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
							<th width= "2%" rowspan="3">&nbsp;</th>
							<th width="10%">受付番号</th>
							<th width="10%">顧客ID</th>
							<th width="25%">名称</th>
							<th width="43%" rowspan="3">問い合わせ内容</th>
							<s:if test="%{#session.userContext.isRealEstate()}">
								<th width="10%">未読／既読</th>
							</s:if>
							<s:else>
								<th rowspan="3">最終履歴</th>
							</s:else>
							
						</tr>
						<tr>
							<th>受付日時</th>
							<th>依頼者区分</th>
							<th>依頼者名</th>
							<s:if test="%{#session.userContext.isRealEstate()}">
								<th rowspan="2">最終履歴</th>
							</s:if>
						</tr>
						<tr>
							<th>受付者</th>
							<th colspan="2">区分</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="resultList" status="i">
							<s:if test="isNotOnGoingKeiyakuEndFlg()">
								<tr class="deleted <s:if test="%{#session.userContext.isRealEstate() && isUnread()}">unread</s:if>">
							</s:if>
							<s:else>
								<tr class="${f:odd(i.index, 'odd,even')} <s:if test="%{#session.userContext.isRealEstate() && isUnread()}">unread</s:if>">
							</s:else>
									<td rowspan="3" align="center">
										<%-- 権限により遷移先を、登録と詳細で振り分ける --%>
										<s:if test="%{#session.userContext.isAdministrativeInhouse() || #session.userContext.isGeneralInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp()}">
											<a href="#" toiawaseNo="${toiawaseNo}" class="linkInquiryEntry">
											<span class="fontlink2">詳細</span></a>
										</s:if>
										<s:else>
											<a href="#" toiawaseNo="${toiawaseNo}" class="linkInquiryDetail">
											<span class="fontlink2">詳細</span></a>
										</s:else>
									</td>
									<td align="center">${f:out(toiawaseNo)}</td>
									<td>${f:out(kokyakuId)}</td>
									<td>${f:out(kanjiNm1)} ${f:out(kanjiNm2)}</td>
									<td rowspan="3">${f:out(toiawaseNaiyo)}</td>
									<s:if test="%{#session.userContext.isRealEstate()}">
										<td align="center">${f:out(browseStatusFlgNm)}</td>
									</s:if>
									<s:else>
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
											${f:out(jokyoKbnNm)}<br>${f:dateFormat(uketsukeYmdRireki,"yy/MM/dd")} ${f:out(uketsukeJikanRireki)}
										</td>
									</s:else>
								
							<s:if test="isNotOnGoingKeiyakuEndFlg()">
								<tr class="deleted <s:if test="%{#session.userContext.isRealEstate() && isUnread()}">unread</s:if>">
							</s:if>
							<s:else>
								<tr class="${f:odd(i.index, 'odd,even')} <s:if test="%{#session.userContext.isRealEstate() && isUnread()}">unread</s:if>">
							</s:else>
									<td align="center">
										${f:dateFormat(uketsukeYmd,"yy/MM/dd")} ${f:out(uketsukeJikan)}
									</td>
									<td>${f:out(iraishaKbnNm)}</td>
									<td>${f:out(iraishaNm)}</td>
									<s:if test="%{#session.userContext.isRealEstate()}">
										<s:if test="isFirstReception()">
											<td rowspan="2" align="center" class="tblDetailForm5">
										</s:if>
										<s:elseif test="isInComplete()">
											<td rowspan="2" align="center" class="tblDetailForm4">
										</s:elseif>
										<s:else>
											<td rowspan="2" align="center">
										</s:else>
											${f:out(jokyoKbnNm)}<br>${f:dateFormat(uketsukeYmdRireki,"yy/MM/dd")} ${f:out(uketsukeJikanRireki)}
										</td>
									</s:if>
								</tr>
							<s:if test="isNotOnGoingKeiyakuEndFlg()">
								<tr class="deleted <s:if test="%{#session.userContext.isRealEstate() && isUnread()}">unread</s:if>">
							</s:if>
							<s:else>
								<tr class="${f:odd(i.index, 'odd,even')} <s:if test="%{#session.userContext.isRealEstate() && isUnread()}">unread</s:if>">
							</s:else>
									<td align="center">${f:out(uketsukeshaNm)}</td>
									<td colspan="2">
										${f:out(toiawaseKbn1Nm)} ${f:out(toiawaseKbn2Nm)} ${f:out(toiawaseKbn3Nm)} ${f:out(toiawaseKbn4Nm)}
									</td>
								</tr>
						</s:iterator>
					</tbody>
				</table>

				<c:import url="/WEB-INF/common/jsp/pager.jsp">
					<c:param name="condition" value="condition" />
					<c:param name="href" value="inquirySearchByPager.action" />
					<c:param name="displayCount" value="0" />
				</c:import>
			</div>
			</s:if>
			<%-- 検索結果 ここまで --%>
			
			<iframe src="" width="0px" height="0px" name="tb_inquiry_search_csv_frame" frameborder="0"></iframe>

			<s:tokenCheck displayId="TB021" />
			<s:hidden key="buttonId" value="" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
