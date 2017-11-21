<%--*****************************************************
	依頼検索画面
	作成者：仲野
	作成日：2014/05/12
	更新日：2015/11/12 H.Hirai CSVダウンロードボタン追加
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
					"condition.toiawaseNo":{byteVarchar:10},
					"condition.kanaNm1": {byteVarchar: 40},
					"condition.kanaNm2": {byteVarchar: 40},
					"condition.kanjiNm1": {byteVarchar: 40},
					"condition.kanjiNm2": {byteVarchar: 40},
					"condition.telNo": {byteVarchar: 15},
					"condition.jusho1": {byteVarchar: 10},
					"condition.jusho2": {byteVarchar: 30},
					"condition.jusho3": {byteVarchar: 30},
					"condition.jusho4": {byteVarchar: 30},
					"condition.jusho5": {byteVarchar: 40},
					"condition.roomNo": {byteVarchar: 20},
					"condition.uketsukebiFrom": "dateYMD",
					"condition.uketsukebiTo": "dateYMD",
					"condition.iraiKanryobiFrom": "dateYMD",
					"condition.iraiKanryobiTo": "dateYMD"
				},
				groups: {
					main_txt_kana : "condition\.kanaNm1 condition\.kanaNm2",
					main_txt_kanji : "condition\.kanjiNm1 condition\.kanjiNm2",
					main_txt_uketsukebi : "condition\.uketsukebiFrom condition\.uketsukebiTo",
					main_txt_irai_kanryobi : "condition\.iraiKanryobiFrom condition\.iraiKanryobiTo",
					main_txt_shimeym : "condition\.shimeYmFrom condition\.shimeYmTo"
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

			fnc_change_iraiKanryo();
			fnc_change_shimeym();

			//******************************************************
			// クリアボタン押下時
			//******************************************************
			$('#btnClear').click(function(e) {
				fnc_clear();
			});

			//******************************************************
			// 検索ボタン押下時
			//******************************************************
			$('#btnSearch').click(function(e) {
				$('.dateYMD').each(function(i,e) {
					var date = convertDate($(this).val());
					if (date != null) {
						$(this).val($.datepicker.formatDate($(e).datepicker("option", "dateFormat"), date));
					}
				});

				$('#main').prop('action', '<s:url action="requestSearch" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});

			var d = new Date();

			//******************************************************
			// CSVダウンロードボタン押下時
			//******************************************************
			$('#btnDownload').click(function(e) {

				$('#main_buttonId').val('btnDownload');

				$('#main').prop('action', '<s:url action="requestSearchDownload" />');
				$('#main').prop('target', 'tb_request_search_csv_frame');

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
				// 入力チェック
				if ($('#main_condition_uketsukebiFrom').val() == ''
						|| $('#main_condition_uketsukebiFrom').val() == $('#main_condition_uketsukebiFrom').data('placeholder-string')) {
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
				// 入力チェック
				if ($('#main_condition_uketsukebiFrom').val() == ''
					|| $('#main_condition_uketsukebiFrom').val() == $('#main_condition_uketsukebiFrom').data('placeholder-string')) {
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
				// 入力チェック
				if ($('#main_condition_uketsukebiTo').val() == ''
					|| $('#main_condition_uketsukebiTo').val() == $('#main_condition_uketsukebiTo').data('placeholder-string')) {
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
				// 入力チェック
				if ($('#main_condition_uketsukebiTo').val() == ''
					|| $('#main_condition_uketsukebiTo').val() == $('#main_condition_uketsukebiTo').data('placeholder-string')) {
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

			//******************************************************
			// 依頼完了ラジオ選択時
			//******************************************************
			$("input[name='condition.iraiKanryo']").change(function(e) {
				fnc_change_iraiKanryo();
			});

			//******************************************************
			// 締め年月ラジオ選択時
			//******************************************************
			$("input[name='condition.shimeSts']").change(function(e) {
				fnc_change_shimeym();
			});

			//******************************************************
			// 前回ボタン(締め年月)押下時
			//******************************************************
			$("#btnShimeZenkai").click(function(e) {
				fnc_btn_shimeZenkai();
			});
		});

		//*******************************************
		// クリアボタン押下時処理
		//*******************************************
		function fnc_clear() {
			$('input[type=checkbox]').removeAttr('checked');
			$('input[type=text]').val('');
			$("input[type=radio][value='1']").prop('checked', true);
			$("input:radio[name='condition.jokyo'][value='0']").prop('checked', true);
			$('select').prop('selectedIndex', 0);

			$("[placeholder]", "#main").each(function(i,e) {
				$(e).val($(e).data('placeholder-string'));
				if (isValidPlaceHolder == false) {
					$(e).css('color', 'silver');
				}
			});

			$(":input").removeClass('error');
			$("label.error").html('');

			$("input[name='condition.iraiKanryo']").val(['2']);
			$("input[name='condition.shimeSts']").val(['0']);
			$('#main_sel_sort_order').val('3');

			fnc_change_iraiKanryo();
			fnc_change_shimeym();
		}

		var IRAI_KANRYO = "1";
		//*******************************************
		// 依頼完了ラジオ変更時 実行関数
		//*******************************************
		function fnc_change_iraiKanryo() {
			var iraiUmu = $("input:radio[name='condition.iraiKanryo']:checked").val();
			if (iraiUmu == IRAI_KANRYO) {
				// 「完了」選択時は、依頼日From・依頼日To・カレンダーを活性にする
				$('#main_condition_iraiKanryobiFrom').prop('disabled', false);
				$('#main_condition_iraiKanryobiTo').prop('disabled', false);
				$('#main_condition_iraiKanryobiFrom').datepicker('enable');
				$('#main_condition_iraiKanryobiTo').datepicker('enable');
			} else {
				// 「全て」「完了以外」選択時は、依頼日From・依頼日To・カレンダーを非活性にする
				$('#main_condition_iraiKanryobiFrom').prop('disabled', true);
				$('#main_condition_iraiKanryobiTo').prop('disabled', true);
				$('#main_condition_iraiKanryobiFrom').datepicker('disable');
				$('#main_condition_iraiKanryobiTo').datepicker('disable');
			}
		}

		//******************************************************
		// 受付日設定処理
		//******************************************************
		function fnc_set_uketsukebi(from, to) {
			$('#main_condition_uketsukebiFrom').val(from);
			$('#main_condition_uketsukebiTo').val(to);

			$('#main_condition_uketsukebiFrom').val($.datepicker.formatDate($('#main_condition_uketsukebiFrom').datepicker("option", "dateFormat"), from));
			$('#main_condition_uketsukebiTo').val($.datepicker.formatDate($('#main_condition_uketsukebiTo').datepicker("option", "dateFormat"), to));

			$('#main_condition_uketsukebiFrom').css('color', $(document.body).css('color'));
			$('#main_condition_uketsukebiTo').css('color', $(document.body).css('color'));
		}

		//******************************************************
		// 末日取得処理
		//******************************************************
		function fnc_get_month_end_day(year, month) {
			var dt = new Date(year, month, 0);
			return dt.getDate();
		}

		var SHIMEYM_ZUMI = "1";
		//*******************************************
		// 締め年月ラジオ変更時 実行関数
		//*******************************************
		function fnc_change_shimeym() {
			var iraiUmu = $("input:radio[name='condition.shimeSts']:checked").val();
			if (iraiUmu == SHIMEYM_ZUMI) {
				// 「締め済」選択時は、締め年月From・締め年月To・カレンダーを活性にする
				$('#main_condition_shimeYmFrom').prop('disabled', false);
				$('#main_condition_shimeYmTo').prop('disabled', false);
				$('#btnShimeZenkai').prop('disabled', false);
			} else {
				// 「全て」「締め前」選択時は、締め年月From・締め年月To・カレンダーを非活性にする
				$('#main_condition_shimeYmFrom').prop('disabled', true);
				$('#main_condition_shimeYmTo').prop('disabled', true);
				$('#btnShimeZenkai').prop('disabled', true);
			}
		}

		//*******************************************
		// 前回ボタン(締め年月)押下時 実行関数
		//*******************************************
		function fnc_btn_shimeZenkai() {
			var zenkaiShoriYm = $("#main_zenkaiShoriYm").val();
			// ボタン押下時、締め年月From・締め年月Toに前回締め年月を設定
			$('#main_condition_shimeYmFrom').val(zenkaiShoriYm);
			$('#main_condition_shimeYmTo').val(zenkaiShoriYm);

			$('#main_condition_shimeYmFrom').css('color', $('#main_condition_shimeYmFrom').data('placeholder-color'));
			$('#main_condition_shimeYmTo').css('color', $('#main_condition_shimeYmTo').data('placeholder-color'));
		}

		//******************************************************
		// 受付日FROM設定処理
		//******************************************************
		function fnc_set_uketsukebi_from(from) {
			$('#main_condition_uketsukebiFrom').val(from);

			$('#main_condition_uketsukebiFrom').val($.datepicker.formatDate($('#main_condition_uketsukebiFrom').datepicker("option", "dateFormat"), from));

			$('#main_condition_uketsukebiFrom').css('color', $('#main_condition_uketsukebiFrom').data('placeholder-color'));
		}

		//******************************************************
		// 受付日TO設定処理
		//******************************************************
		function fnc_set_uketsukebi_to(to) {
			$('#main_condition_uketsukebiTo').val(to);

			$('#main_condition_uketsukebiTo').val($.datepicker.formatDate($('#main_condition_uketsukebiTo').datepicker("option", "dateFormat"), to));

			$('#main_condition_uketsukebiTo').css('color', $('#main_condition_uketsukebiTo').data('placeholder-color'));
		}

		//*******************************************
		// 詳細リンク押下時 実行関数
		//*******************************************
		function fnc_detail_link(toiawaseNo, toiawaseRirekiNo) {
			// 同一ウィンドウに依頼内容詳細・作業状況登録画面を表示
			$('#main').addClass('ignore');
			$('#toiawaseNo').val(toiawaseNo);
			$('#toiawaseRirekiNo').val(toiawaseRirekiNo);
			<s:if test="%{#session.userContext.isRealEstate()}">
				$('#dispKbn').val('1');
				$('#main').prop('action', '<s:url action="requestDetail" />');
			</s:if>
			<s:elseif test="%{#session.userContext.isConstractor()}">
				$('#dispKbn').val('1');
				$('#main').prop('action', '<s:url action="requestEntryInit" />');
			</s:elseif>
			<s:else>
				$('#dispKbn').val('tb031');
				$('#main').prop('action', '<s:url action="requestFullEntryUpdateInit" />');
			</s:else>
			$('#main').prop('target', '_self');
			$('#main').submit();
			$('#main').removeClass('ignore');
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">依頼検索</tiles:putAttribute>
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
									<td width="45%">
										<s:select key="condition.serviceKbn" list="serviceList" listKey="serviceKbn" listValue="serviceKbnNm" emptyOption="true" />
									</td>
									<th width="10%" rowspan="4">住所</th>
									<td rowspan="4">
										<span class="font5">※全てあいまい検索</span><br>
										<s:textfield key="condition.jusho1" maxlength="10" cssClass="zenhankaku" cssStyle="width:190px;" placeholder="都道府県" />
										<br><s:textfield key="condition.jusho2" maxlength="30" cssClass="zenhankaku" cssStyle="width:190px;" placeholder="市区町村" />
										<br><s:textfield key="condition.jusho3" maxlength="30" cssClass="zenhankaku" cssStyle="width:190px;" placeholder="町/大字" />
										<br><s:textfield key="condition.jusho4" maxlength="30" cssClass="zenhankaku" cssStyle="width:190px;" placeholder="番地(全角)" />
										<br><s:textfield key="condition.jusho5" maxlength="40" cssClass="zenhankaku" cssStyle="width:190px;" placeholder="アパート･マンション(全角)" />
										<br><s:textfield key="condition.roomNo" maxlength="20" cssClass="zenhankaku" cssStyle="width:190px;" placeholder="部屋番号" />
									</td>
								</tr>
								<tr>
									<th>受付番号</th>
									<td>
										<s:textfield key="condition.toiawaseNo" maxlength="10" cssClass="hankaku" cssStyle="width:100px;" placeholder="(半角数字)" />
									</td>
								</tr>
								<tr>
									<th>カナ名称</th>
									<td>
										<s:textfield key="condition.kanaNm1" maxlength="40" cssClass="zenhankaku" cssStyle="width:160px;" placeholder="(全角カナ)" />
										<s:textfield key="condition.kanaNm2" maxlength="40" cssClass="zenhankaku" cssStyle="width:140px;" placeholder="(全角カナ)" />
										<span class="font5">&nbsp;※あいまい検索</span><br>
										<label for="main_txt_kana" class="error" generated="true" /></label>
									</td>
								</tr>
								<tr>
									<th>漢字名称</th>
									<td>
										<s:textfield key="condition.kanjiNm1" maxlength="40" cssClass="zenhankaku" cssStyle="width:160px;" placeholder="(全角)" />
										<s:textfield key="condition.kanjiNm2" maxlength="40" cssClass="zenhankaku" cssStyle="width:140px;" placeholder="(全角)" />
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
									<th>状況</th>
									<td><s:radio key="condition.jokyo" list="jokyoList" listKey="comCd" listValue="externalSiteVal"/></td>
								</tr>
								<tr>
									<th>受付日</th>
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
									<th>作業完了</th>
									<td>
										<s:radio key="condition.iraiKanryo" list="#{2:'全て', 0:'完了以外', 1:'完了'}" />
										<s:textfield key="condition.iraiKanryobiFrom" maxlength="10" cssClass="dateYMD" cssStyle="width:100px;" placeholder="yyyymmdd" />
										～
										<s:textfield key="condition.iraiKanryobiTo" maxlength="10" cssClass="dateYMD" cssStyle="width:100px;" placeholder="yyyymmdd" />
										<label for="main_txt_irai_kanryobi" class="error" generated="true" /></label>
									</td>
									<th>ソート</th>
									<td>
										<s:select key="condition.sortOrder" list="#{3:'受付日の降順', 4:'受付日の昇順'}" emptyOption="false" />
									</td>
								</tr>
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
				<s:hidden key="zenkaiShoriYm" />

				<input type="hidden" id="toiawaseNo" name="toiawaseNo" value="" />
				<input type="hidden" id="toiawaseRirekiNo" name="toiawaseRirekiNo" value="" />
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
							<th width= "2%" rowspan="2">&nbsp;</th>
							<th width="10%">受付番号</th>
							<th width="11%">顧客ID</th>
							<th width="25%">名称</th>
							<th width="" rowspan="2">依頼内容</th>
							<th width="10%" rowspan="2">作業完了</th>
							<th width="10%" rowspan="2">最終履歴</th>
						</tr>
						<tr>
							<th>発注担当</th>
							<th>依頼者区分</th>
							<th>依頼者名</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="resultList" status="i">
							<s:if test="isNotOnGoingKeiyakuEndFlg()">
								<tr class='deleted'>
							</s:if>
							<s:else>
								<tr class="${f:odd(i.index, 'odd,even')}">
							</s:else>
									<td rowspan="2" align="center"><a href="JavaScript:fnc_detail_link('${f:out(toiawaseNo)}', '${f:out(toiawaseRirekiNo)}');""><span class="fontlink2">詳細</span></a></td>
									<td>${f:out(toiawaseNo)}-${f:out(toiawaseRirekiNo)}</td>
									<td>${f:out(kokyakuId)}</td>
									<td>${f:out(kanjiNm1)} ${f:out(kanjiNm2)}</td>
									<td rowspan="2">${f:out(iraiNaiyo)}</td>
									<td align="center" rowspan="2">${f:dateFormat(sagyoKanryoYmd, "yy/MM/dd")} ${f:out(sagyoKanryoJikan)}</td>
									<td rowspan="2" align="center" class="tblDetailForm5">${f:out(jokyoKbnNm)}<br>${f:dateFormat(uketsukeYmdRireki, "yy/MM/dd")} ${f:out(uketsukeJikanRireki)}</td>
								</tr>
							<s:if test="isNotOnGoingKeiyakuEndFlg()">
								<tr class='deleted'>
							</s:if>
							<s:else>
								<tr class="${f:odd(i.index, 'odd,even')}">
							</s:else>
									<td>${f:out(tantoshaIdNm)}</td>
									<td>${f:out(iraishaKbnNm)}</td>
									<td>${f:out(iraishaNm)}</td>
								</tr>
						</s:iterator>
					</tbody>
				</table>

				<c:import url="/WEB-INF/common/jsp/pager.jsp">
					<c:param name="condition" value="condition" />
					<c:param name="href" value="requestSearchByPager.action" />
					<c:param name="displayCount" value="0" />
				</c:import>
			</div>
			</s:if>
			<%-- 検索結果 ここまで --%>

			<iframe src="" width="0px" height="0px" name="tb_request_search_csv_frame" frameborder="0"></iframe>

			<s:tokenCheck displayId="TB031" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
