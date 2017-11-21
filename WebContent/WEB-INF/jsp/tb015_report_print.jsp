<%--*****************************************************
	報告書印刷画面
	作成者：山村
	作成日：2015/08/05
	更新日：2016/07/08 H.Hirai 複数請求先対応
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>
<%@page import="org.apache.commons.lang.StringUtils"%>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			// フォーカスを当てる
			window.focus();
			
			// 送付元の初期活性化処理実施
			fnc_rdo_sentaku_moto();
			
			// 請求先顧客リスト初期処理
			fnc_seikyusaki_kokyaku_lst();
			
			// iPadの場合はCSVダウンロードボタンを使用不可に設定
			if (isIPad == true) {
				$("#btn_download").prop("disabled", true);
			}
			
			// 選択ラジオで初期値が設定されている場合に、選択顧客ＩＤを更新
			if ($('input[name="rdoKokyakuIdListRelevance"]:checked').length > 0) {
				$('#main_kokyakuId').val($('input[name="rdoKokyakuIdListRelevance"]:checked').val());
			}
			
			//**********************************************
			// 詳細リンククリック時
			//**********************************************
			$('.linkDetail').click(function(e) {
				// 詳細処理
				$('#main_shosai_kokyakuId').val($(this).attr('kokyakuId'));

				var date = new Date();		// 経過時間を退避
				var time = date.getTime();	// 通算ミリ秒計算
				var win_name = "tb_customert_detail_win" + time;

				var w = createWindow(win_name);

				$('#shosai').prop('action', '<s:url action="contractInfoInit" />');
				$('#shosai').prop('target', w.name);
				$('#shosai').submit();
			});
			
			//******************************************************
			// 顧客選択(関連付け)ボタン押下時
			//******************************************************
			$('#btn_kokyaku_search').click(function(e) {
				// 設定する項目のname値を設定
				$('#main_sentaku_kokyakuIdResultNm').val("sentaku_kokyakuId");
				$('#main_sentaku_kokyakuKaishaNmResultNm').val("");
				$('#main_sentaku_kokyakuNmResultNm').val("sentaku_kokyakuNm");
				$('#main_sentaku_kokyakuJushoResultNm').val("");
				$('#main_sentaku_kokyakuTelResultNm').val("");
				$('#main_sentaku_kokyakuFaxResultNm').val("");

				// 顧客検索呼び出し
				fnc_kokyaku_search();
			});
			
			//******************************************************
			// 顧客選択(送付元)ボタン押下時
			//******************************************************
			$('#btn_moto_kokyaku_search').click(function(e) {
				// 設定する項目のname値を設定
				$('#main_sentaku_kokyakuIdResultNm').val("moto_sentaku_kokyakuId");
				$('#main_sentaku_kokyakuKaishaNmResultNm').val("moto_sentaku_kokyakuKaishaNm");
				$('#main_sentaku_kokyakuNmResultNm').val("moto_sentaku_kokyakuNm");
				$('#main_sentaku_kokyakuJushoResultNm').val("moto_sentaku_kokyakuJusho");
				$('#main_sentaku_kokyakuTelResultNm').val("moto_sentaku_kokyakuTelNo");
				$('#main_sentaku_kokyakuFaxResultNm').val("moto_sentaku_kokyakuFaxNo");

				// 顧客検索呼び出し
				fnc_kokyaku_search();
			});
			
			//******************************************************
			// 顧客選択(関連付け)の顧客IDフォーカス時
			//******************************************************
			$('#main_sentaku_kokyakuId').change(function(e) {
				fnc_rdo_sentaku_disabled("");
				$('#main_kokyakuId').val($('#main_sentaku_kokyakuId').val());
			});

			//******************************************************
			// 顧客選択(送付元)の顧客IDフォーカス時
			//******************************************************
			$('#main_moto_sentaku_kokyakuId').change(function(e) {
				fnc_rdo_sentaku_disabled("moto");
				fnc_rdo_sentaku_moto();
			});

			//******************************************************
			// 送付元変更時
			//******************************************************
			$("input[name='rdoSender']").change(fnc_rdo_sentaku_moto);
			
			//**********************************************
			// 関連付け検索結果の選択ラジオボタンクリック時
			//**********************************************
			$("input[name='rdoKokyakuIdListRelevance']").click(function(e) {
				// 顧客選択ラジオの場合
				if ($(this).val() == 'SELECT_KOKYAKU'){
					$('#main_kokyakuId').val($('#main_sentaku_kokyakuId').val());
				} else {
					$('#main_kokyakuId').val(($(this).val()));
				}
			});
			
			//******************************************************
			// CSVダウンロードボタン押下時
			//******************************************************
			$('#btn_download').click(function(e) {
				// 関連付け一覧にチェックが一つも入っていない場合
				if ($('input[name="rdoKokyakuIdListRelevance"]:checked').length == 0) {
					alert(fncMakeErrMsg(ART0031, "宛先", "", ""));
					return false;
				}
				
				// 送付元の情報をパラメータのhdn値に設定
				fnc_set_sohumoto();

				$('#main').prop('action', '<s:url action="reportPrintCsvDownload" />');
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb_report_print_download_frame');
				}
				$('#main').submit();
			});
			
			//******************************************************
			// 作業報告書画像印刷ボタン押下時
			//******************************************************
			$('#btn_gazou_print').click(function(e) {
				var w = createWindow("tb_work_report_image_win");
				$('#main').prop('action', '<s:url action="workReportImageInit" />');
				$('#main').prop('target', w.name);
				$('#main').submit();
			});
			
			//******************************************************
			// 公開ボタン設定押下時
			//******************************************************
			$('#btn_kokai').click(function(e) {
				// 関連付け一覧にチェックが一つも入っていない場合
				if ($('input[name="rdoKokyakuIdListRelevance"]:checked').length == 0) {
					alert(fncMakeErrMsg(ART0031, "宛先", "", ""));
					return false;
				}

				// 送付元の情報をパラメータのhdn値に設定
				fnc_set_sohumoto();

				var w = createWindow("tb_report_disclosure_win");
				$('#main').prop('action', '<s:url action="reportDisclosureInit" />');
				$('#main').prop('target', 'tb_report_disclosure_win');
				$('#main').submit();
			});
			
			//******************************************************
			// 報告書印刷ボタン押下時
			//******************************************************
			$('#btn_print').click(function(e) {
				// 関連付け一覧にチェックが一つも入っていない場合
				if ($('input[name="rdoKokyakuIdListRelevance"]:checked').length == 0) {
					alert(fncMakeErrMsg(ART0031, "宛先", "", ""));
					return false;
				}
				// 送付元の情報をパラメータのhdn値に設定
				fnc_set_sohumoto();

				$('#main').prop('action', '<s:url action="reportPrintDownload" />');
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb_report_print_download_frame');
				}

				$('#main').submit();
			});
			
			//******************************************************
			// 請求先顧客リスト変更時
			//******************************************************
			$('#main_seikyusakiKokyakuList').change(function(e) {
				fnc_seikyusaki_kokyaku_lst();
			});
			
			//**********************************************
			// 顧客選択の選択ラジオボタン制御
			//**********************************************
			function fnc_rdo_sentaku_disabled(name) {
				if (name == "") {
					// 引数が空文字の場合
					if ($("#main_sentaku_kokyakuId").val()) {
						// 顧客IDが有る場合は活性
						$("input[name=rdoKokyakuIdListRelevance]:last").prop('disabled', false);
						$("input[name=rdoKokyakuIdListRelevance]:last").prop('checked', true);
					}
				} else {
					// 引数が空文字でない場合
					if ($("#main_moto_sentaku_kokyakuId").val()) {
						// 顧客IDが有る場合は活性
						$("input[name=rdoSender]:last").prop('disabled', false);
						$("input[name=rdoSender]:last").prop('checked', true);
					}
				}
			}
			
			//**********************************************
			// 送付元の選択ラジオボタンクリック時
			//**********************************************
			function fnc_rdo_sentaku_moto() {
				// 選択されている行の入力項目を活性化し、それ以外の行の入力項目は非活性
				$('[id^="main_sashidashinin_"]:text').prop('disabled', true);
				$('[id^="main_seikyusakiKokyakuList"]').prop('disabled', true);
				$('[id^="main_seikyusakiKokyaku_"]:text').prop('disabled', true);
				$('[id^="main_ather"]:text').prop('disabled', true);
				$('[id^="main_moto_sentaku_kokyaku"]:text').prop('disabled', true);
				var index = $('input[name="rdoSender"]:checked').val();
				switch (index){
					case "0":
						$('[id^="main_sashidashinin_"]:text').prop('disabled', false);
						break;
					case "1":
						$('[id^="main_seikyusakiKokyakuList"]').prop('disabled', false);
						$('[id^="main_seikyusakiKokyaku_"]:text').prop('disabled', false);
						break;
					case "2":
						$('[id^="main_ather"]:text').prop('disabled', false);
						break;
					case "3":
						$('[id^="main_moto_sentaku_kokyaku"]:text').prop('disabled', false);
						break;
					default:
						// ラジオボタンが選択されていない場合は活性化しない
						break;
				}

				// 顧客IDは非活性にしない
				$('#main_moto_sentaku_kokyakuId').prop('disabled', false);
			}
			
			//**********************************************
			// 送付元の情報をパラメータのhdn値に設定
			//**********************************************
			function fnc_set_sohumoto() {
				// 選択されているラジオボタンの送付元を設定
				var index = $('input[name="rdoSender"]:checked').val();
				switch (index){
					case "0":
						$('#main_senderNm1').val($('#main_sashidashinin_kaishaNm').val());
						$('#main_senderNm2').val($('#main_sashidashinin_nm').val());
						$('#main_senderAddress').val($('#main_sashidashinin_jusho').val());
						$('#main_senderTelNo').val($('#main_sashidashinin_telNo').val());
						$('#main_senderFaxNo').val($('#main_sashidashinin_faxNo').val());
						break;
					case "1":
						$('#main_senderNm1').val($('#main_seikyusakiKokyaku_kaishaNm').val());
						$('#main_senderNm2').val($('#main_seikyusakiKokyaku_kanjiNm').val());
						$('#main_senderAddress').val($('#main_seikyusakiKokyaku_jusho').val());
						$('#main_senderTelNo').val($('#main_seikyusakiKokyaku_telNo1').val());
						$('#main_senderFaxNo').val($('#main_seikyusakiKokyaku_faxNo').val());
						break;
					case "2":
						$('#main_senderNm1').val($('#main_atherKaishaNm').val());
						$('#main_senderNm2').val($('#main_atherNm').val());
						$('#main_senderAddress').val($('#main_atherJusho').val());
						$('#main_senderTelNo').val($('#main_atherTelNo').val());
						$('#main_senderFaxNo').val($('#main_atherFaxNo').val());
						break;
					case "3":
						$('#main_senderNm1').val($('#main_moto_sentaku_kokyakuKaishaNm').val());
						$('#main_senderNm2').val($('#main_moto_sentaku_kokyakuNm').val());
						$('#main_senderAddress').val($('#main_moto_sentaku_kokyakuJusho').val());
						$('#main_senderTelNo').val($('#main_moto_sentaku_kokyakuTelNo').val());
						$('#main_senderFaxNo').val($('#main_moto_sentaku_kokyakuFaxNo').val());
						break;
					default:
						// ラジオボタンが選択されていない場合は空欄を設定
						$('#main_senderNm1').val("");
						$('#main_senderNm2').val("");
						$('#main_senderAddress').val("");
						$('#main_senderTelNo').val("");
						$('#main_senderFaxNo').val("");
						break;
				}

			}
			
			//**********************************************
			// 顧客検索画面呼び出し
			//**********************************************
			function fnc_kokyaku_search() {
				var date = new Date();		// 経過時間を退避
				var time = date.getTime();	// 通算ミリ秒計算
				var win_name = "tb_customer_search_win" + time;

				var w = createWindow(win_name);

				$('#sentaku').prop('action', '<s:url action="customerSearchInit" />');
				$('#sentaku').prop('target', w.name);
				$('#sentaku').submit();
			}

			//******************************************************
			// 請求先リスト変更時処理
			//******************************************************
			function fnc_seikyusaki_kokyaku_lst() {

				// 選択行情報を取得
				var kokyakuCd = $('#main_seikyusakiKokyakuList').val();

				// 顧客情報が存在しない場合、終了
				if (kokyakuCd == null) {
					return;
				}

				// 選択された請求先の情報をテキストに設定
				$('#main_seikyusakiKokyaku_kaishaNm').val($('[name^="seikyusakiKaishaNm_' + kokyakuCd + '"]').val());
				$('#main_seikyusakiKokyaku_kanjiNm').val($('[name^="seikyusakiKanjiNm_' + kokyakuCd + '"]').val());
				$('#main_seikyusakiKokyaku_jusho').val($('[name^="seikyusakiKokyakuJusho_' + kokyakuCd + '"]').val());
				$('#main_seikyusakiKokyaku_telNo1').val($('[name^="seikyusakiKokyakuTelNo1_' + kokyakuCd + '"]').val());
				$('#main_seikyusakiKokyaku_faxNo').val($('[name^="seikyusakiKokyakuFaxNo_' + kokyakuCd + '"]').val());
			}
		});

		//******************************************************
		// 画面値復元処理
		//******************************************************
		function fnc_restore() {
			if(window.opener && !window.opener.closed) {
				// 親ウィンドウが存在すれば、親ウィンドウのリロード処理
				window.opener.fnc_restore();
			}
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="windowTitle">${gamenNm}印刷</tiles:putAttribute>
	<tiles:putAttribute name="title">${gamenNm}印刷</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<s:form id="main" method="POST">
		<div class="contents">
			<table  width="967px">
			<tr>
				<td colspan="2" >&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" ><span class="font2">&nbsp;&nbsp;${gamenNm}の宛先・送付元を選択して下さい。</span><br><br>&nbsp;</td>
			</tr>
			<tr >
				<td ><h2>関連付け一覧</h2>
				<br>※以下の顧客と関連付けられています。</td>
				<td  align="right">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" >
					<span id="spn_rdo_sentaku">
					<table width="99%" align="center" class="tblDetailForm">
						<tr>
							<th width= "2%" >&nbsp;</th>
							<th width= "4%" >&nbsp;</th>
							<th width="11%" >顧客区分</th>
							<th width= "8%" >顧客ID</th>
							<th width= "9%" >個人/法人</th>
							<th width="29%" >名称</th>
							<th width="37%" >住所</th>
						</tr>
					<s:iterator status="i" value="kanrenList">
						<tr <s:if test="isKeiyakuEnd()">bgcolor="CCCCCC"</s:if> <s:else>class="${f:odd(i.index, 'odd,even')}" </s:else>>
							<td align="center"><s:radio list="#{kokyakuId:''}" key="rdoKokyakuIdListRelevance" value="%{getInitKanrenKokyakuId(#i.index)}" /></td>
							<td align="center"><a href="#" kokyakuId="${kokyakuId}" class="linkDetail"><span class="fontlink2">詳細</span></a></td>
							<td>${f:out(kokyakuKbnNm)}</td>
							<td>${f:out(kokyakuId)}</td>
							<td>${f:out(kokyakuShubetsuNm)}</td>
							<td>${f:out(kanjiNm)}</td>
							<td>${f:out(jusho)}</td>
						</tr>
					</s:iterator>
					<tr>
						<td align="center"><s:radio list="#{'SELECT_KOKYAKU':''}" key="rdoKokyakuIdListRelevance" disabled="true"  /></td>
						<td colspan="6">
							<input type="button" value="顧客選択" class="btnDialog" id="btn_kokyaku_search">&nbsp;
							<s:textfield key="sentaku_kokyakuId" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true"/>
							<s:textfield key="sentaku_kokyakuNm" cssClass="readOnlyText" cssStyle="width:700px;" readonly="true"/>
						</td>
					</tr>
					</table>
					</span>
				</td>
			</tr>

			<tr><td colspan="2" >&nbsp;</td></tr>
			<tr >
				<td ><h2>送付元</h2>
				<br>※送付元を変更する場合、入力して下さい。</td>
				<td  align="right">&nbsp;</td>
			</tr>
			<tr><td colspan="2" >
				<span id="spn_rdo_sentaku_moto">
				<table width="99%" align="center" class="tblDetailForm">
					<tr>
						<th width= "2%" rowspan="2">&nbsp;</th>
						<th width="12%" rowspan="2">&nbsp;</th>
						<th width="11%" rowspan="2">顧客ＩＤ</th>
						<th colspan="2">名称</th>
						<th width="15%" >TEL</th>
					</tr>
					<tr>
						<th colspan="2">住所</th>
						<th>FAX</th>
					</tr>
					<tr class="${f:odd(0, 'odd,even')}">
						<td rowspan="2"  align="center"><s:radio list="#{'0':''}" key="rdoSender" /></td>
						<td rowspan="2" >&nbsp;TOKAI</td>
						<td rowspan="2" >&nbsp;</td>
						<td width="30%"><s:textfield key="sashidashinin.kaishaNm" cssClass="zenhankaku" maxlength="50" cssStyle="width:275px;" disabled="true"/></td>
						<td width="30%"><s:textfield key="sashidashinin.nm" cssClass="zenhankaku" maxlength="81" cssStyle="width:275px;" disabled="true"/></td>
						<td><s:textfield key="sashidashinin.telNo" cssClass="hankaku" maxlength="15" cssStyle="width:130px;" disabled="true"/></td>
					</tr>
					<tr class="${f:odd(0, 'odd,even')}">
						<td colspan="2" ><s:textfield key="sashidashinin.jusho" cssClass="zenhankaku" maxlength="140" cssStyle="width:561px;" disabled="true"/></td>
						<td><s:textfield key="sashidashinin.faxNo" cssClass="hankaku" maxlength="15" cssStyle="width:130px;" disabled="true"/></td>
					</tr>
					<tr class="${f:odd(1, 'odd,even')}">
						<td rowspan="2"  align="center"><s:radio list="#{'1':''}" key="rdoSender" /></td>
						<td rowspan="2" >&nbsp;請求先顧客</td>
						<td rowspan="2" >
							<s:if test="isSeikyusakiLstDisplay()">
								<s:select key="seikyusakiKokyakuList" list="seikyusakiKokyakuList" listKey="kokyakuId" listValue="kokyakuId"
												emptyOption="false" cssClass="textIMEDisabled"  disabled="true"/>
							</s:if>
						</td>
						<td><s:textfield key="seikyusakiKokyaku.kaishaNm" cssClass="zenhankaku" maxlength="50" cssStyle="width:275px;" disabled="true"/></td>
						<td><s:textfield key="seikyusakiKokyaku.kanjiNm" cssClass="zenhankaku" maxlength="81" cssStyle="width:275px;" disabled="true"/></td>
						<td><s:textfield key="seikyusakiKokyaku.telNo1" cssClass="hankaku" maxlength="15" cssStyle="width:130px;" disabled="true"/></td>
					</tr>
					<tr class="${f:odd(1, 'odd,even')}">
						<td colspan="2" ><s:textfield key="seikyusakiKokyaku.jusho" cssClass="zenhankaku" maxlength="140" cssStyle="width:561px;" disabled="true"/></td>
						<td><s:textfield key="seikyusakiKokyaku.faxNo" cssClass="hankaku" maxlength="15" cssStyle="width:130px;" disabled="true"/></td>
					</tr>
					<tr class="${f:odd(2, 'odd,even')}">
						<td rowspan="2"  align="center"><s:radio list="#{'2':''}" key="rdoSender" /></td>
						<td rowspan="2" >&nbsp;その他<br>(手入力)</td>
						<td rowspan="2" >&nbsp;</td>
						<td><s:textfield key="atherKaishaNm" cssClass="zenhankaku" maxlength="50" cssStyle="width:275px;" disabled="true"/></td>
						<td><s:textfield key="atherNm" cssClass="zenhankaku" maxlength="81" cssStyle="width:275px;" disabled="true"/></td>
						<td><s:textfield key="atherTelNo" cssClass="hankaku" maxlength="15" cssStyle="width:130px;" disabled="true"/></td>
					</tr>
					<tr class="${f:odd(2, 'odd,even')}">
						<td colspan="2" ><s:textfield key="atherJusho" cssClass="zenhankaku" maxlength="140" cssStyle="width:561px;" disabled="true"/></td>
						<td><s:textfield key="atherFaxNo" cssClass="hankaku" maxlength="15" cssStyle="width:130px;" disabled="true"/></td>
					</tr>
					<tr class="${f:odd(3, 'odd,even')}">
						<td rowspan="2"  align="center"><s:radio list="#{'3':''}" key="rdoSender" disabled="true" /></td>
						<td rowspan="2" ><input type="button" value="顧客選択" class="btnDialog" id="btn_moto_kokyaku_search"></td>
						<td rowspan="2" ><s:textfield key="moto_sentaku_kokyakuId" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" /></td>
						<td><s:textfield key="moto_sentaku_kokyakuKaishaNm" cssClass="zenhankaku" maxlength="50" cssStyle="width:275px;" disabled="true"/></td>
						<td><s:textfield key="moto_sentaku_kokyakuNm" cssClass="zenhankaku" maxlength="81" cssStyle="width:275px;" disabled="true"/></td>
						<td><s:textfield key="moto_sentaku_kokyakuTelNo" cssClass="hankaku" maxlength="15" cssStyle="width:130px;" disabled="true"/></td>
					</tr>
					<tr class="${f:odd(3, 'odd,even')}">
						<td colspan="2" ><s:textfield key="moto_sentaku_kokyakuJusho" cssClass="zenhankaku" maxlength="140" cssStyle="width:561px;" disabled="true"/></td>
						<td><s:textfield key="moto_sentaku_kokyakuFaxNo" cssClass="hankaku" maxlength="15" cssStyle="width:130px;" disabled="true"/></td>
					</tr>
				</table>
				</span>
			</td></tr>

			<s:if test="isFromRequestEntry()">
			<tr><td colspan="2" >&nbsp;</td></tr>
			<tr >
				<td ><h2>参考</h2>
				<br>※作業費登録画面で指定した請求先情報です。</td>
				<td  align="right">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" >
					<table class="tblDetailForm" width="99%" align="center">
						<tr class="${f:odd(0, 'odd,even')}">
							<th>請求先名</th>
							<td colspan=3>${f:out(sagyohi.seikyusakiNm)}</td>
						</tr>
						<tr class="${f:odd(1, 'odd,even')}">
							<th>請求先住所</th>
							<td colspan=3>${f:out(sagyohi.seikyusakiJusho)}</td>
						</tr>
						<tr class="${f:odd(2, 'odd,even')}">
							<th width="10%">請求先TEL</th>
							<td width="40%">${f:out(sagyohi.seikyusakiTel)}</td>
							<th width="10%">請求先FAX</th>
							<td width="40%">${f:out(sagyohi.seikyusakiFax)}</td>
						</tr>
					</table>
				</td>
			</tr>
			</s:if>

			<tr><td colspan="2" >&nbsp;</td></tr>
			<tr>
				<td colspan="2" >
				<table width="99%" align="center" class="tblDetailForm">
					<td ><input type="button" value=" CSVダウンロード " class="btnSubmit" id="btn_download"></td>
					<td align="right" >
						<s:if test="%{ #session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp() }">
							&nbsp;<input type="button" name ="" value="公開設定" class="btnSubmit" id="btn_kokai">
						</s:if>
						&nbsp;<input type="button" name ="" value="${gamenNm}印刷" class="btnSubmit" id="btn_print">
						&nbsp;<input type="button" value=" 閉じる " class="btnSubmit" id="btnClose">
					</td>
				</table></td>
			</tr>
		</table>
		<iframe src="" width="0px" height="0px" name="tb_report_print_download_frame" frameborder="0"></iframe>
		</div>
		<s:hidden key="toiawaseNo"/>
		<s:hidden key="kokyakuId" />
		<s:hidden key="toiawaseRirekiNo"/>
		<s:hidden key="dispKbn" />
		<s:hidden key="toiawaseUpdDt"/>
		<s:hidden key="sagyoJokyoUpdDt"/>
		<!-- 送付元パラメータ -->
		<s:hidden key="senderNm1"/>
		<s:hidden key="senderNm2"/>
		<s:hidden key="senderAddress"/>
		<s:hidden key="senderTelNo"/>
		<s:hidden key="senderFaxNo"/>
		<!-- 帳票区分 -->
		<s:hidden key="printKbn"/>
		<%-- 請求先顧客情報hidden値 --%>
		<s:iterator status="i" value="seikyusakiKokyakuList">
			<input type="hidden" name="seikyusakiKaishaNm_${f:out(kokyakuId)}" value=""/>
			<input type="hidden" name="seikyusakiKanjiNm_${f:out(kokyakuId)}" value="${f:out(kanjiNm)}"/>
			<input type="hidden" name="seikyusakiKokyakuJusho_${f:out(kokyakuId)}" value="${f:out(jusho)}"/>
			<input type="hidden" name="seikyusakiKokyakuTelNo1_${f:out(kokyakuId)}" value="${f:out(telNo1)}"/>
			<input type="hidden" name="seikyusakiKokyakuFaxNo_${f:out(kokyakuId)}" value="${f:out(faxNo)}"/>
		</s:iterator>
	</s:form>

	<s:form id="sentaku" method="POST">
		<%-- 顧客選択用パラメータ --%>
		<s:hidden key="dispKbn" value="tb015" />
		<s:hidden key="kokyakuIdResultNm"/>
		<s:hidden key="kokyakuKaishaNmResultNm"/>
		<s:hidden key="kokyakuNmResultNm"/>
		<s:hidden key="kokyakuJushoResultNm"/>
		<s:hidden key="kokyakuTelResultNm"/>
		<s:hidden key="kokyakuFaxResultNm"/>
		<s:hidden key="viewSelectLinkFlg" value="1" />
	</s:form>

	<s:form id="shosai" method="POST">
		<%-- 詳細用パラメータ --%>
		<s:hidden key="gamenKbn" value="tb046" />
		<s:hidden key="kokyakuId" />
	</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>