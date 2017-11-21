<%--*****************************************************
	お知らせ登録画面
	作成者：増田成
	作成日：2014/06/20
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		var TARGET_REAL_ESTATE = "20";
		var TARGET_CONTRACTOR = "30";
		var TARGET_OUTSOURCER = "40";

		$(function() {
			window.focus();

			<%-- 確認ダイアログ(初期値は空) --%>
			var confirmation = function() {return true;};

			var validation = $("#main")
			.validate({
				ignore:
					".ignore *",
				rules: {
					"info.target":			{required: true},
					"info.startDt":			{required: true, dateYMD:true},
					"info.endDt":			{required: true, dateYMD:true},
					"info.message":			{required: true, byteVarchar:600}
				},
				stepValidation: true,
				invalidHandler: function(form, validator) {
					alert(ART0015);
				},
				groups: {
					main_info_target :"info.target",
					main_hyoji_Dt :"info.startDt info.endDt"
				},
				submitHandler: function(form) {
					if (!confirmation()) {
						return false;
					}
					form.submit();
				}
			});

			fnc_target_change(0);

			//******************************************************
			// 「選択」ボタン押下時 ※表示対象
			//******************************************************
			$('#btnTargetSelect').click(function(e) {
				confirmation = function() {return true;};

				var target = $('input[name="info.target"]:checked').val();
				var winName = "";
				var action = "";
				if (target == TARGET_REAL_ESTATE) {
					// 不動産・管理会社の場合
					winName = 'tb_select_real_estate_win';
					action = '<s:url action="realEstateAgencyInit" />';

					$('#main_kokyakuIdResultNm').val('info.kokyakuId');
					$('#main_kokyakuNmResultNm').val('info.kokyakuNm');
				} else if(target == TARGET_CONTRACTOR) {
					// 依頼業者の場合
					winName = 'tb_select_contractor_win';
					action = '<s:url action="contractorInit" />';

					$('#main_gyoshaCdResultNm').val('info.gyoshaCd');
					$('#main_gyoshaNmResultNm').val('info.gyoshaNm');
				} else if (target == TARGET_OUTSOURCER) {
					// 委託会社の場合
					winName = 'tb_select_outsourcer_win';
					action = '<s:url action="outsourcerInit" />';

					$('#main_kaishaIdResultNm').val('info.kaishaId');
					$('#main_kaishaNmResultNm').val('info.kaishaNm');
				}

				var w = createWindow(winName);

				$('#main').addClass('ignore');

				$('#main').prop('action', action);
				$('#main').prop('target', winName);

				$('#main').submit();

				$('#main').removeClass('ignore');
			});
			//******************************************************
			// 「クリア」ボタン押下時 ※表示対象
			//******************************************************
			$('#btnTargetClear').click(function(e) {
				fnc_target_change(1);
			});
			//******************************************************
			// 登録・更新ボタン押下時
			//******************************************************
			$('#btnEntry').click(function(e) {
				confirmation = function() {
					var buttonNm = "";

					if ($('#main_actionType').val() == 'insert') {
						buttonNm = "登録";
					} else if ($('#main_actionType').val() == 'update') {
						buttonNm = "更新";
					}

					return confirm(jQuery.validator.format(INF0001, "お知らせ情報", buttonNm));
				};

				// ユーザー情報登録・更新処理
				$('#main').prop('action', '<s:url action="informationEntryUpdate" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});
			//******************************************************
			// 戻るボタン押下時
			//******************************************************
			$('#btnBack').click(function(e) {
				confirmation = function() {return true;};

				$('#main').addClass('ignore');

				// ユーザー検索画面に戻る
				if ($("input[name='condition.offset']").val() == '' || $("input[name='condition.offset']").val() == '0') {
					// （新規登録から遷移した場合は、offsetが0）初期表示に遷移
					$('#main').prop('action', '<s:url action="informationSearchInit" />');
				} else {
					// 検索処理に遷移
					$('#main').prop('action', '<s:url action="informationSearch" />');
				}

				$('#main').prop('target', '_self');

				$('#hdn_on_select').val('');

				$('#main').submit();

				$('#main').removeClass('ignore');
			});
			//******************************************************
			// 表示対象ラジオボタン変更時
			//******************************************************
			$('input[name="info.target"]:radio').change(function (e) {
				fnc_target_change(1);
			});
			
			//*******************************************
			// 表示条件ラジオボタン変更時 実行関数
			//*******************************************
			function fnc_target_change(delFlg) {
				var target = $('input[name="info.target"]:checked').val();

				// 削除フラグが1の場合、顧客ＩＤ、業者コード、会社ＩＤ、顧客名、業者名、会社名を初期化
				if (delFlg == 1) {
					$('#main_info_kokyakuId').val('');
					$('#main_info_kokyakuNm').val('');
					$('#main_info_gyoshaCd').val('');
					$('#main_info_gyoshaNm').val('');
					$('#main_info_kaishaId').val('');
					$('#main_info_kaishaNm').val('');
				}

				// 表示条件設定（表示条件選択別）
				if (target == TARGET_REAL_ESTATE) {
					// 「不動産・管理会社」選択時
					$('#main_info_kokyakuId').css('display', 'inline');
					$('#main_info_kokyakuNm').css('display', 'inline');
					$('#main_info_gyoshaCd').css('display', 'none');
					$('#main_info_gyoshaNm').css('display', 'none');
					$('#main_info_kaishaId').css('display', 'none');
					$('#main_info_kaishaNm').css('display', 'none');
					$('#btnTargetSelect').prop('disabled', false);
					$('#btnTargetClear').prop('disabled', false);
				} else if (target == TARGET_CONTRACTOR) {
					// 「業者」選択時
					$('#main_info_kokyakuId').css('display', 'none');
					$('#main_info_kokyakuNm').css('display', 'none');
					$('#main_info_gyoshaCd').css('display', 'inline');
					$('#main_info_gyoshaNm').css('display', 'inline');
					$('#main_info_kaishaId').css('display', 'none');
					$('#main_info_kaishaNm').css('display', 'none');
					$('#btnTargetSelect').prop('disabled', false);
					$('#btnTargetClear').prop('disabled', false);
				} else if (target == TARGET_OUTSOURCER) {
					// 「委託会社」選択時
					$('#main_info_kokyakuId').css('display', 'none');
					$('#main_info_kokyakuNm').css('display', 'none');
					$('#main_info_gyoshaCd').css('display', 'none');
					$('#main_info_gyoshaNm').css('display', 'none');
					$('#main_info_kaishaId').css('display', 'inline');
					$('#main_info_kaishaNm').css('display', 'inline');
					$('#btnTargetSelect').prop('disabled', false);
					$('#btnTargetClear').prop('disabled', false);
				} else {
					// 「全て」または未選択時
					$('#main_info_kokyakuId').css('display', 'none');
					$('#main_info_kokyakuNm').css('display', 'none');
					$('#main_info_gyoshaCd').css('display', 'none');
					$('#main_info_gyoshaNm').css('display', 'none');
					$('#main_info_kaishaId').css('display', 'none');
					$('#main_info_kaishaNm').css('display', 'none');
					$('#btnTargetSelect').prop('disabled', true);
					$('#btnTargetClear').prop('disabled', true);
				}
			}
		});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">お知らせメンテナンス</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<div class="content">
				<h2>お知らせ登録</h2>
				<table>
					<tr>
						<th width="10%"><span class="font10">＊</span>表示対象</th>
						<td width="90%">
							<s:radio key="info.target" list="targetList" listKey="comCd" listValue="externalSiteVal" />
							<%-- 初期表示、権限リスト変更時にテキストボックスの表示／非表示をJavaScriptで制御 --%>
								<s:textfield key="info.kokyakuId" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
								<s:textfield key="info.kokyakuNm" cssClass="readOnlyText" cssStyle="width:200px;" readonly="true" />
								<s:textfield key="info.gyoshaCd" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
								<s:textfield key="info.gyoshaNm" cssClass="readOnlyText" cssStyle="width:200px;" readonly="true" />
								<s:textfield key="info.kaishaId" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
								<s:textfield key="info.kaishaNm" cssClass="readOnlyText" cssStyle="width:200px;" readonly="true" />
							&nbsp;<input type="button" id="btnTargetSelect" class="btnDialog" value=" 選 択 " />
							&nbsp;<input type="button" id="btnTargetClear" class="btnDialog" value=" クリア " />
							<br><label for="main_info_target" style="float:left;" class="error" generated="true" /></label>
						</td>
					</tr>
					<tr>
						<th><span class="font10">＊</span>表示期間</th>
						<td>
							<s:textfield key="info.startDt" maxlength="10" cssClass="compareDate dateYMD" dateFrom="main_info_startDt" dateTo="main_info_endDt" cssStyle="width:90px;" placeholder="yyyymmdd" />～
							<s:textfield key="info.endDt" maxlength="10" cssClass="dateYMD" cssStyle="width:90px;" placeholder="yyyymmdd" />
							<br><label for="main_hyoji_Dt" style="float:left;" class="error" generated="true" /></label>
						</td>
					</tr>
					<tr>
						<th><span class="font10">＊</span>表示内容</th>
						<td>
							<s:textarea key="info.message" maxlength="600" cssClass="textareaIMEActiveNotNull" cssStyle="width:100%;" rows="5" />
						</td>
					</tr>
				</table>
				<span class="required_remark">
					<span class="font10">*</span> <span class="font3">常に必須</span><br>
				</span>
				<br>
				<div class="right">
					<s:if test="isInsert()">
					&nbsp;<input type="button" id="btnEntry" class="btnSubmit" value=" 登 録 " />
					</s:if>
					<s:if test="isUpdate()">
					&nbsp;<input type="button" id="btnEntry" class="btnSubmit" value=" 更 新 " />
					</s:if>
					&nbsp;<input type="button" id="btnBack" class="btnSubmit" value=" 戻る " />
				</div>
			</div>

			<s:hidden key="info.seqNo" />
			<s:hidden key="info.updDt" />
			<s:hidden key="actionType" />
			<s:hidden key="seqNo" />

			<%-- お知らせ検索の検索条件 --%>
			<s:hidden key="condition.target" />
			<s:hidden key="condition.kokyakuId" />
			<s:hidden key="condition.kokyakuNm" />
			<s:hidden key="condition.gyoshaCd" />
			<s:hidden key="condition.gyoshaNm" />
			<s:hidden key="condition.kaishaId" />
			<s:hidden key="condition.kaishaNm" />
			<s:hidden key="condition.hyojiJoken" />
			<s:hidden key="condition.offset" />

			<%-- 選択画面の戻り --%>
			<s:hidden key="kokyakuIdResultNm" value="" />
			<s:hidden key="kokyakuNmResultNm" value="" />
			<s:hidden key="gyoshaCdResultNm" value="" />
			<s:hidden key="gyoshaNmResultNm" value="" />
			<s:hidden key="kaishaIdResultNm" value="" />
			<s:hidden key="kaishaNmResultNm" value="" />

			<s:tokenCheck displayId="TB104" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
