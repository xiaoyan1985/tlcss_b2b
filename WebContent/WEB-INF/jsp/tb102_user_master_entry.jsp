<%--*****************************************************
	ユーザーマスタ登録画面
	作成者：仲野
	作成日：2014/05/28
	更新日：2014/06/19 岩田 初期表示時の削除欄の消去
	更新日：2015/09/08 山村 委託会社選択追加対応
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		var ROLE_ADMINISTRATOR = "10";
		var ROLE_GENERAL_INHOUSE = "11";
		var ROLE_REAL_ESTATE = "20";
		var ROLE_CONTRACTOR = "30";
		var ROLE_OUTSOURCER_SV = "40";
		var ROLE_OUTSOURCER_OP = "41";
		var HYOJI_FLG_OFF = "0";
		var ISLEAF_TRUE = "1";
		var ENTRY_OK ="1";

		$(function() {
			window.focus();

			<%-- 確認ダイアログ(初期値は空) --%>
			var confirmation = function() {return true;};

			$("#main").validate({
				ignore:
					".ignore *",
				rules: {
					"user.loginId":			{required: true, email:true, byteVarchar:256},
					"user.userNm":			{required: true, byteVarchar:40},
					"user.role":			{required: true}
				},
				invalidHandler: function(form, validator) {
					alert(ART0015);
				},
				submitHandler: function(form) {
					if (!confirmation()) {
						return false;
					}

					// 選択ボタン押下時以外は、全項目活性＆ボタン使用不可
					if ($('#hdn_on_select').val() != '1') {
						$(":input").prop("disabled", false);
						$("input:button").prop("disabled", true);
					}
					form.submit();
					// 選択ボタン押下時以外は、全項目非活性
					if ($('#hdn_on_select').val() != '1') {
						$(":input").prop("disabled", true);
					}
				}
			});

			fnc_role_change();
			controlDisableKokyakuNodeList();

			//******************************************************
			// 再発行ボタン押下時
			//******************************************************
			$('#btnReissue').click(function(e) {
				confirmation = function() {return true;};

				// 初期値設定
				$("#chk_passwd_reissue").attr("checked", true);

				$("#dialog-modal").dialog({
					modal: true,
					buttons: {
						 "再発行": function() {
							$(this).dialog("close");

							$('#main').addClass('ignore');

							// パスワード再発行処理
							$('#main').prop('action', '<s:url action="userMasterEntryReissue" />');
							$('#main').prop('target', '_self');

							if ($("#chk_passwd_reissue").prop('checked') == false) {
								$("#chk_passwd_reissue").val('');
							}

							$("#main_sendMailFlg").val($("#chk_passwd_reissue").val());

							$('#hdn_on_select').val('');

							$('#main').submit();

							$('#main').removeClass('ignore');
						},
						Cancel: function() {
							$( this ).dialog("close");
						}
					}
				});
			});

			//******************************************************
			// 権限リスト変更時
			//******************************************************
			$('#main_user_role').change(function() {
				fnc_role_change();
			});

			//******************************************************
			// 業者選択ボタン押下時
			//******************************************************
			$('#btnSelectCustomer').click(function(e) {
				confirmation = function() {return true;};

				var winName = "";
				var action = "";
				if ($('#main_user_role').val() == ROLE_ADMINISTRATOR || $('#main_user_role').val() == ROLE_GENERAL_INHOUSE) {
					// TOKAI管理者または、TOKAI一般の場合
					winName = 'tb_select_real_estate_win';
					action = '<s:url action="realEstateAgencyInit" />';
					$('#main_kokyakuListNmResultNm').val('selectTokaiKokyakuInfo');
				} else if ($('#main_user_role').val() == ROLE_REAL_ESTATE) {
					// 不動産・管理会社の場合
					winName = 'tb_select_real_estate_win';
					action = '<s:url action="realEstateAgencyInit" />';
					$('#main_kokyakuListNmResultNm').val('');

					$('#main_kokyakuIdResultNm').val('user.kokyakuId');
					$('#main_kokyakuNmResultNm').val('user.kokyakuNm');
				} else if($('#main_user_role').val() == ROLE_CONTRACTOR) {
					// 依頼業者の場合
					winName = 'tb_select_contractor_win';
					action = '<s:url action="contractorInit" />';
					$('#main_kokyakuListNmResultNm').val('');

					$('#main_gyoshaCdResultNm').val('user.gyoshaCd');
					$('#main_gyoshaNmResultNm').val('user.gyoshaNm');
				} else if ($('#main_user_role').val() == ROLE_OUTSOURCER_SV
					|| $('#main_user_role').val() == ROLE_OUTSOURCER_OP) {
					// 委託会社の場合
					winName = 'tb_outsourcer_win';
					action = '<s:url action="outsourcerInit" />';
					$('#main_kokyakuListNmResultNm').val('');

					$('#main_kaishaIdResultNm').val('user.kaishaId');
					$('#main_kaishaNmResultNm').val('user.kaishaNm');
				}

				var w = createWindow(winName);

				$('#main').addClass('ignore');

				$('#main').prop('action', action);
				$('#main').prop('target', winName);

				$('#hdn_on_select').val('1');

				$('#main').submit();

				$('#main').removeClass('ignore');
			});

			//******************************************************
			// 登録ボタン押下時
			//******************************************************
			$('#btnEntry').click(function(e) {
				if ($('#main_user_role').val() == ROLE_ADMINISTRATOR || $('#main_user_role').val() == ROLE_GENERAL_INHOUSE) {
					// TOKAI社員の場合 TOKAI顧客リストテキスト一覧保存
					var obj = $('#selectTokaiKokyakuInfo').children();
					var tokaiKokyakuListText = '';
					for (var i=0; i< obj.length; i++) {
						if (tokaiKokyakuListText == '') {
							tokaiKokyakuListText = obj.eq(i).text();
						} else {
							tokaiKokyakuListText = tokaiKokyakuListText + ',' + obj.eq(i).text();
						}
					}
					$('#main_tokaiKokyakuListTexts').val(tokaiKokyakuListText);
				}

				confirmation = function() {
					var buttonNm = "";

					if ($('#main_actionType').val() == 'insert') {
						buttonNm = "登録";
					} else if ($('#main_actionType').val() == 'update') {
						buttonNm = "更新";
					}

					return confirm(jQuery.validator.format(INF0001, "ユーザー情報", buttonNm));
				};

				// 選択された権限に合わせ他の権限での必要項目削除
				if ($('#main_user_role').val() == ROLE_ADMINISTRATOR || $('#main_user_role').val() == ROLE_GENERAL_INHOUSE) {
					// TOKAI社員の場合
					$('#main_user_kokyakuId').val('');
					$('#main_user_kokyakuNm').val('');
					$('#main_user_gyoshaCd').val('');
					$('#main_user_gyoshaNm').val('');
					$('#main_user_kaishaId').val('');
					$('#main_user_kaishaNm').val('');
				}  else if ($('#main_user_role').val() == ROLE_REAL_ESTATE) {
					// 不動産・管理会社の場合
					$('#main_user_gyoshaCd').val('');
					$('#main_user_gyoshaNm').val('');
					$('#main_user_kaishaId').val('');
					$('#main_user_kaishaNm').val('');
				} else if($('#main_user_role').val() == ROLE_CONTRACTOR) {
					// 依頼業者の場合
					$('#main_user_kokyakuId').val('');
					$('#main_user_kokyakuNm').val('');
					$('#main_user_kaishaId').val('');
					$('#main_user_kaishaNm').val('');
				} else if ($('#main_user_role').val() == ROLE_OUTSOURCER_SV
					|| $('#main_user_role').val() == ROLE_OUTSOURCER_OP) {
					// 委託会社の場合
					$('#main_user_kokyakuId').val('');
					$('#main_user_kokyakuNm').val('');
					$('#main_user_gyoshaCd').val('');
					$('#main_user_gyoshaNm').val('');
				}

				// ユーザー情報登録・更新処理
				$('#main').prop('action', '<s:url action="userMasterEntryUpdate" />');
				$('#main').prop('target', '_self');

				$('#hdn_on_select').val('');

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
					$('#main').prop('action', '<s:url action="userMasterSearchInit" />');
				} else {
					// 検索処理に遷移
					$('#main').prop('action', '<s:url action="userMasterSearch" />');
				}

				$('#main').prop('target', '_self');

				$('#hdn_on_select').val('');

				$('#main').submit();

				$('#main').removeClass('ignore');
			});
			//******************************************************
			// TOKAI顧客リスト削除ボタン押下時
			//******************************************************
			$('#btnDeleteSelectCustomer').click(function(e) {
				var form = document.main;
				var buyList = document.main.selectTokaiKokyakuInfo;

				selectLen = buyList.length;			//項目数
				selectchk = buyList.selectedIndex;

				if(selectchk == -1){ //選択されている項目があるときだけ処理する
					alert(jQuery.validator.format(ART0029,"削除対象を"));
				} else {
					buyListstr = "";
					num = 0;
					valueTbl = new Array(selectLen);
					textTbl  = new Array(selectLen);

					for (i=0; i<selectLen; i++){
						//選択されてるかどうか false：選択されてない　true：選択されてる
						if ( (buyList.options[i].selected  == true) &&
								(buyList.options[i].value     != ""  ) ){
							buyListstr+=buyList.options[i].text+"：";
							buyListstr+=buyList.options[i].value;
							buyListstr+="\n";
							buyList.options[i].selected  = false;
						} else{
							//削除しない項目だけとっておく
							valueTbl[num] = buyList.options[i].value;
							textTbl[num]  = buyList.options[i].text;
							num++;
						}
					}

					// selectフィールドの中身を、削除しない項目だけに書き換える
					for (i=0; i<num; i++){
						buyList.options[i].text = textTbl[i];
						buyList.options[i].value = valueTbl[i];
					}
					buyList.length = num;
				}
			});

			//******************************************************
			// TOKAI顧客リスト「上へ」ボタン押下時
			//******************************************************
			$('#btnUpSelectCustomer').click(function(e) {
				var selectedOptions = $('#selectTokaiKokyakuInfo').children('option:selected');
				var prev = $(selectedOptions).first().prev();

				// 顧客ＩＤリストが選択されていなかった場合
				if (selectedOptions.val() == null) {
					alert(jQuery.validator.format(ART0029,"移動対象を"));
					return false;
				}

				// 移動処理実行
				$(selectedOptions).insertBefore(prev);
			});

			//******************************************************
			// TOKAI顧客リスト「下へ」ボタン押下時
			//******************************************************
			$('#btnDownSelectCustomer').click(function(e) {
				var selectedOptions = $('#selectTokaiKokyakuInfo').children('option:selected');
				var next = $(selectedOptions).last().next();

				// 顧客ＩＤリストが選択されていなかった場合
				if (selectedOptions.val() == null) {
					alert(jQuery.validator.format(ART0029,"移動対象を"));
					return false;
				}

				// 移動処理実行
				$(selectedOptions).insertAfter(next);
			});

			//******************************************************
			// 「下位層表示」変更時
			//******************************************************
			$('[id^="main_kokyakuNodeList_"]:input').change(function(e) {
				controlDisableKokyakuNodeList();
			});

		});

		//*******************************************
		// TOKAI顧客リスト追加時 実行関数
		//*******************************************
		function fnc_role_change() {

			$("tr[id *= 'tokai']").hide();
			$("tr[id *= 'realEstate']").hide();
			$("#realEstate").hide();
			$("#contractor").hide();
			$("#outsourcer").hide();

			if ($('#main_user_role').val() == '' ) {
				// 未選択の場合

				$('#btnSelectCustomer').prop('disabled', true);

				$("#main_tokaiKokyakuListTexts").rules("remove", "required");
				$("#main_user_kokyakuId").rules("remove", "required");
				$("#main_user_gyoshaCd").rules("remove", "required");
				$("#main_user_kaishaId").rules("remove", "required");

			} else if ($('#main_user_role').val() == ROLE_ADMINISTRATOR || $('#main_user_role').val() == ROLE_GENERAL_INHOUSE) {
				// TOKAI社員の場合
				$("tr[id *= 'tokai']").show();

				$('#btnSelectCustomer').prop('disabled', false);

				$("#main_tokaiKokyakuListTexts").rules("add", {
					required: true
				});
				$("#main_user_kokyakuId").rules("remove", "required");
				$("#main_user_gyoshaCd").rules("remove", "required");
				$("#main_user_kaishaId").rules("remove", "required");

			} else if ($('#main_user_role').val() == ROLE_REAL_ESTATE) {
				// 不動産・管理会社の場合
				$("tr[id *= 'realEstate']").show();
				$("#realEstate").show();

				$('#btnSelectCustomer').prop('disabled', false);

				$("#main_tokaiKokyakuListTexts").rules("remove", "required");
				$("#main_user_gyoshaCd").rules("remove", "required");
				$("#main_user_kaishaId").rules("remove", "required");

				$("#main_user_kokyakuId").rules("add", {
					required: true
				});

			} else if($('#main_user_role').val() == ROLE_CONTRACTOR) {
				// 依頼業者の場合
				$("#contractor").show();

				$('#btnSelectCustomer').prop('disabled', false);

				$("#main_tokaiKokyakuListTexts").rules("remove", "required");
				$("#main_user_kokyakuId").rules("remove", "required");
				$("#main_user_kaishaId").rules("remove", "required");

				$("#main_user_gyoshaCd").rules("add", {
					required: true
				});
			} else if ($('#main_user_role').val() == ROLE_OUTSOURCER_SV
				|| $('#main_user_role').val() == ROLE_OUTSOURCER_OP) {
				// 委託会社の場合
				$("#outsourcer").show();
				
				$('#btnSelectCustomer').prop('disabled', false);
				
				$("#main_tokaiKokyakuListTexts").rules("remove", "required");
				$("#main_user_kokyakuId").rules("remove", "required");
				$("#main_user_gyoshaCd").rules("remove", "required");
				
				$("#main_user_kaishaId").rules("add", {
					required: true
				});
			}
		}

		//*******************************************
		// TOKAI顧客リスト追加時 実行関数
		//*******************************************
		function addKokyakuList(kokyakuInfo) {
			if (!document.createElement) return;

			// 重複チェック
			var obj = $('#selectTokaiKokyakuInfo').children();
			for( var i = 0; i < obj.length; i++ ){
				if (kokyakuInfo == obj.eq(i).text()) {
					// 選択画面からの取得データが既にある場合、追加せず処理終了
					return;
				}
			}

			var opt = document.createElement("option");
			opt.text = kokyakuInfo;

			document.main.selectTokaiKokyakuInfo.add(opt);
		}

		//*******************************************************
		// 顧客検索画面が閉じられた時、顧客階層情報を設定
		//*******************************************************
		function setKokyakuNodeList() {
			// 権限「管理会社」の時のみ実行
			if ($('#main_user_role').val() == ROLE_REAL_ESTATE) {
				// TOKAI顧客リストテキスト一覧保存（画面再表示時のデータ保存用）
				var obj = $('#selectTokaiKokyakuInfo').children();
				var tokaiKokyakuListText = '';
				for (var i=0; i< obj.length; i++) {
					if (tokaiKokyakuListText == '') {
						tokaiKokyakuListText = obj.eq(i).text();
					} else {
						tokaiKokyakuListText = tokaiKokyakuListText + ',' + obj.eq(i).text();
					}
				}
				$('#main_tokaiKokyakuListTexts').val(tokaiKokyakuListText);

				// サーバ遷移設定
				$('#main').addClass('ignore');
				$('#main').prop('action', '<s:url action="userMasterSetRefKokyaku" />');
				$('#main').prop('target', '_self');
				$('#hdn_on_select').val('');
				$('#main').submit();
				$('#main').removeClass('ignore');
			}
		}

		//*******************************************************
		// 表示フラグ変更時、顧客階層情報の活性・非活性を設定
		//*******************************************************
		function controlDisableKokyakuNodeList() {
			var level = 0;

			// 全項目活性化
			for (var i=0; i < ${kokyakuNodeSize}; i++) {
				$('#main_kokyakuNodeList_'+ i + '__hyojiFlg').prop('disabled', false);
				$('#main_kokyakuNodeList_'+ i + '__entryFlg').val(ENTRY_OK);
			}

			// 非活性,「0:非表示」設定
			for (var i=0; i < ${kokyakuNodeSize}; i++) {
				if ($('#main_kokyakuNodeList_'+ i + '__hyojiFlg').val() == HYOJI_FLG_OFF
						&& $('#main_kokyakuNodeList_'+ i + '__isLeaf').val() != ISLEAF_TRUE) {
					// 表示フラグ「非表示」,かつ末端ではない場合
					level = $('#main_kokyakuNodeList_'+ i + '__kaisoLevel').val();
					// 数値以外の場合は0とする。
					if (level == "" || (level.match(/[^0-9]/) != null)) {
						level = 0;
					}

					for (var j=i+1; j < ${kokyakuNodeSize}; j++) {
						if (level < $('#main_kokyakuNodeList_'+ j + '__kaisoLevel').val()) {
							$('#main_kokyakuNodeList_'+ j + '__hyojiFlg').prop('disabled', true).val(HYOJI_FLG_OFF);
							$('#main_kokyakuNodeList_'+ j + '__entryFlg').val('');
						} else {
							// 階層が下がる場合、処理終了
							break;
						}
					}
					i = j-1;
				}
			}
		}

		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">ユーザーマスタメンテナンス</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<%-- 検索条件 ここから --%>
			<div class="content">
				<h2>ユーザー登録</h2>
				<s:if test="isInsert() || (isUpdate() && user != null)">
				<table>
					<tbody>
						<tr>
							<th width="20%"><span class="font10">＊</span>ログインID</th>
							<td>
								<s:if test="isInsert()">
								<s:textfield key="user.loginId" cssClass="hankaku" cssStyle="width:300px;" maxlength="256" placeholder="メールアドレス" />
								</s:if>
								<s:elseif test="isUpdate()">
								<s:textfield key="user.loginId" cssClass="readOnlyText" cssStyle="width:300px;" maxlength="256" />
								</s:elseif>
								<s:if test="isDuplicateContentExists()">
								<br><span style="color:red;">${f:br(f:out(ducplicateContentErrorMessage))}</span>
								</s:if>
							</td>
						</tr>
						<tr>
							<th>暫定パスワード</th>
							<td>
								<s:if test="isInsert()">
								<label><s:checkbox key="sendMailFlg" fieldValue="1" value="%{isCheckedSendMailFlg()}" />暫定パスワードをメール通知する</label>
								</s:if>
								<s:elseif test="isUpdate()">
								${f:out(user.tmpPasswd)}
								<span style="float:right;">
									&nbsp;<input type="button" id="btnReissue" class="btnSubmit" value=" 再発行 " />
								</span>
								</s:elseif>
							</td>
						</tr>
						<tr>
							<th><span class="font10">＊</span>ユーザー名</th>
							<td><s:textfield key="user.userNm" cssClass="zenhankaku" cssStyle="width:330px;" maxlength="40" /></td>
						</tr>
						<tr>
							<th><span class="font10">＊</span>権限</th>
							<td>
							<table>
								<tr>
									<td>
										<s:select key="user.role" list="roleList" listKey="comCd" listValue="externalSiteVal" emptyOption="true" />
										<input type="button" id="btnSelectCustomer" class="btnDialog" value=" 選 択 " />
										<span id="realEstate" style="display:none;">
											参照顧客ＩＤ：
											<s:textfield key="user.kokyakuId" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
											<s:textfield key="user.kokyakuNm" cssClass="readOnlyText" cssStyle="width:340px;" readonly="true" />
										</span>
										<span id="contractor" style="display:none;">
											業者コード：
											<s:textfield key="user.gyoshaCd" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
											<s:textfield key="user.gyoshaNm" cssClass="readOnlyText" cssStyle="width:340px;" readonly="true" />
										</span>
										<span id="outsourcer" style="display:none;">
											会社ＩＤ：
											<s:textfield key="user.kaishaId" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
											<s:textfield key="user.kaishaNm" cssClass="readOnlyText" cssStyle="width:340px;" readonly="true" />
										</span>
									</td>
								</tr>
								<tr id="tokai" style="display:none;">
									<td colspan="2">選択一覧</td>
								</tr>
								<tr id="tokai" style="display:none;">
									<td valign="top" width="360">
										<select id ="selectTokaiKokyakuInfo" size="6" style="width:360px;" multiple="true">
										<s:iterator value="tokaiKokyakuList" status="i">
											<option value="${i.index}">${f:out(kokyakuIdWithKanjiNm)}</option>
										</s:iterator>
										</select>
										<s:hidden key="tokaiKokyakuListTexts" value="" />
									</td>
									<td valign="top">
										<table>
											<tr>
												<td><input type="button" class="btnDialog" id="btnDeleteSelectCustomer" value=" 削 除 "></td>
											</tr>
											<tr>
												<td><br></td>
											</tr>
											<tr>
												<td><input type="button" class="btnDialog" id="btnUpSelectCustomer" value=" 上へ "></td>
											</tr>
											<tr>
												<td><input type="button" class="btnDialog" id="btnDownSelectCustomer" value=" 下へ "></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr id="realEstate" style="display:none;">
									<td colspan="2">
										<table>
											<tr>
												<th width="85%">表示制御</th>
												<th width="15%">下位表示</th>
											</tr>
											<s:iterator value="kokyakuNodeList" status="i">
												<tr <s:if test="isDisplayGray()"> class="odd" </s:if>>
													<td>${f:out(kanjiNmWithYohaku)}</td>
													<td><s:select key="kokyakuNodeList[%{#i.index}].hyojiFlg" list="#{1:'表示', 0:'非表示'}" /></td>
												</tr>
												<s:hidden key="kokyakuNodeList[%{#i.index}].kaisoLevel" />
												<s:hidden key="kokyakuNodeList[%{#i.index}].kokyakuId" />
												<s:hidden key="kokyakuNodeList[%{#i.index}].isLeaf" />
												<s:hidden key="kokyakuNodeList[%{#i.index}].entryFlg" />
											</s:iterator>
										</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<s:if test="isUpdate()">
						<tr>
							<th>削除チェック</th>
							<td>
								<label><s:checkbox key="user.delFlg" fieldValue="1" value="%{isCheckedDelFlg()}" />&nbsp;削除する</label>
							</td>
						</tr>
						</s:if>
						<tr>
							<th>パスワード変更日</th>
							<td>${f:dateFormat(user.passwdUpdDt, "yyyy/MM/dd")}</td>
						</tr>
						<tr>
							<th>パスワード有効期限</th>
							<td>${f:dateFormat(user.passwdKigenDt, "yyyy/MM/dd")}</td>
						</tr>
						<tr>
							<th>登録日</th>
							<td>${f:dateFormat(user.creDt, "yyyy/MM/dd")}</td>
						</tr>
						<tr>
							<th>登録者</th>
							<td>${f:out(user.creUserNm)}</td>
						</tr>
						<tr>
							<th>更新日</th>
							<td>${f:dateFormat(user.updDt, "yyyy/MM/dd")}</td>
						</tr>
						<tr>
							<th>更新者</th>
							<td>${f:out(user.lastUpdUserNm)}</td>
						</tr>
					</tbody>
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
				<s:if test="isUpdate()">
				<div id="dialog-modal" title="パスワードの再発行" style="display:none">
					パスワードを再発行します。よろしいですか？<br><br>
					<label><input type="checkbox" id="chk_passwd_reissue" name="chk_passwd_reissue" value="1" checked />暫定パスワードをメール通知する</label>
				</div>
				</s:if>
				</s:if>
				<s:else>
				<div class="right">
					&nbsp;<input type="button" id="btnBack" class="btnSubmit" value=" 戻る " />
				</div>
				</s:else>
			</div>

			<s:hidden key="actionType" />
			<s:hidden key="seqNo" />

			<s:hidden key="user.seqNo" />
			<s:hidden key="user.passwd" />
			<s:if test="isUpdate()">
			<s:hidden key="user.tmpPasswd" />
			</s:if>
			<s:hidden key="user.passwdUpdDt" />
			<s:hidden key="user.passwdKigenDt" />
			<s:hidden key="user.tmpPasswdFlg" />
			<s:hidden key="user.creDt" />
			<s:hidden key="user.creId" />
			<s:hidden key="user.creUserNm" />
			<s:hidden key="user.updDt" />
			<s:hidden key="user.lastUpdId" />
			<s:hidden key="user.lastUpdUserNm" />

			<s:if test="isUpdate()">
			<s:hidden key="sendMailFlg" />
			</s:if>

			<%-- 問い合わせ検索の検索条件 --%>
			${f:writeHidden2(condition, "condition", excludeField)}

			<%-- 選択画面の戻り --%>
			<s:hidden key="kokyakuIdResultNm" value="" />
			<s:hidden key="kokyakuNmResultNm" value="" />
			<s:hidden key="gyoshaCdResultNm" value="" />
			<s:hidden key="gyoshaNmResultNm" value="" />
			<s:hidden key="kaishaIdResultNm" value="" />
			<s:hidden key="kaishaNmResultNm" value="" />
			<s:hidden key="kokyakuListNmResultNm" value="" />

			<%-- 選択ボタン押下時フラグ --%>
			<input type="hidden" id="hdn_on_select" name="hdn_on_select" value="" />

			<s:tokenCheck displayId="TB102" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>

