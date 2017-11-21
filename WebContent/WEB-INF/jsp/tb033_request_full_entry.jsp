<%--*****************************************************
	依頼登録画面
	作成者：仲野
	作成日：2015/10/28
	更新日：2016/02/12 C.Kobayashi ファイルアップロード時のプライマリーキー変更対応
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
			<%-- 確認ダイアログ(初期値は空) --%>
			var confirmation = function() {return true;};

			$(function() {
				// 完了時間をコロン付きにするために整形
				if ($('#main_sagyoJokyo_sagyoKanryoJikan').size() != 0 &&
					$('#main_sagyoJokyo_sagyoKanryoJikan').val() != "" &&
					$('#main_sagyoJokyo_sagyoKanryoJikan').val() != $('#main_sagyoJokyo_sagyoKanryoJikan').data('placeholder-string')) {
					$('#main_sagyoJokyo_sagyoKanryoJikan').val(ConvCoron(ZenToHan_Num($('#main_sagyoJokyo_sagyoKanryoJikan').val())));
				}
	
				var validation = $("#main")
				.validate({
					ignore:
						".ignore *",
					rules: {
						"irai.tantoshaNm": 					{required: true, byteVarchar: 40},
						"iraiGyosha.gyoshaNm": 				{required: true},
						"irai.iraiNaiyo": 					{required: true, byteVarchar: 600},
						"irai.homonKiboBiko": 				{byteVarchar: 100},
						"irai.gyoshaKaitoBiko": 			{byteVarchar: 100},
						"sagyoJokyo.sagyoKanryoYmd":		{required: '#main_sagyoJokyo_sagyoKanryoFlg:checked', dateYMD: true, byteVarchar: 10},
						"sagyoJokyo.sagyoKanryoJikan":		{required: '#main_sagyoJokyo_sagyoKanryoFlg:checked', time: true, byteVarchar: 5},
						"sagyoJokyo.jokyo":					{required: '#main_sagyoJokyo_sagyoKanryoFlg:checked', byteVarchar: 600},
						"sagyoJokyo.cause":					{required: '#main_sagyoJokyo_sagyoKanryoFlg:checked', byteVarchar: 600},
						"sagyoJokyo.jisshiNaiyo":			{required: '#main_sagyoJokyo_sagyoKanryoFlg:checked', byteVarchar: 600},
						<s:iterator value="uploadedFiles" status="i">
							"sagyoJokyoImageFiles[${f:out(i.index)}]": {
								required: function() {
									if ($('input[name="sagyoJokyoImageFileUploadFileNms[${f:out(i.index)}]"]').val() != '') {
										// リンク表示（ファイル名がある）の場合は、必須項目としない
										return false;
									}
									return $('[name="sagyoJokyoImageFileComments[${f:out(i.index)}]"]').val() != '';
								}
							},
							"sagyoJokyoImageFileComments[${f:out(i.index)}]":	{byteVarchar: 200},
						</s:iterator>
						<s:iterator value="uploadedOtherFiles" status="i">
							"otherFiles[${f:out(i.index)}]": {
								required: function() {
									if ($('input[name="otherFileUploadFileNms[${f:out(i.index)}]"]').val() != '') {
										// リンク表示（ファイル名がある）の場合は、必須項目としない
										return false;
									}
									return $('[name="otherFileComments[${f:out(i.index)}]"]').val() != '';
								}
							},
							"otherFileComments[${f:out(i.index)}]":	{byteVarchar: 200},
						</s:iterator>
						"historyAutoRegistJokyoKbn":		{required: '#main_historyAutoRegistFlg:checked'}
					},
					groups: {
						main_irai_homonKiboYmd				: "irai\.homonKiboBiko",
						main_irai_homonKiboBiko				: "irai\.homonKiboBiko",
						main_irai_gyoshaKaitoYmd			: "irai\.gyoshaKaitoYmd",
						main_irai_gyoshaKaitoBiko			: "irai\.gyoshaKaitoBiko",
						main_sagyoJokyo_sagyoKanryo			: "sagyoJokyo\.sagyoKanryoYmd sagyoJokyo\.sagyoKanryoJikan",
					},
					invalidHandler: function(form, validator) {
						alert(ART0015);
	
						confirmation = function() {return true;};
					},
					submitHandler: function(form) {
						if (!confirmation()) {
							confirmation = function() {return true;};
							
							if ($('#buttonId').val() == "btnDelete") {
								// 削除ボタン押下時にキャンセルの場合は、アクションタイプを「update」に戻す
								$('#main_actionType').val('update');
							}
							
							return false;
						}
						
						if ($('#buttonId').val() == "btnBack"
							|| $('#buttonId').val() == "btnEntry"
							|| $('#buttonId').val() == "btnUpdate"
							|| $('#buttonId').val() == "btnDelete"
							|| $('#buttonId').val() == "btnImageDelete"
							|| $('#buttonId').val() == "btnOtherDelete") {
							
							if ($('#buttonId').val() == "btnEntry" || $('#buttonId').val() == "btnUpdate") {
								if ($('#main_sagyoJokyo_sagyoKanryoJikan').val() != "") {
									// コロンを取り除く
									$('#main_sagyoJokyo_sagyoKanryoJikan').val(ConvDelCoron($('#main_sagyoJokyo_sagyoKanryoJikan').val()));
								}
							}
							
							$(":input").prop("disabled", false);
							$("input:button").prop("disabled", true);
							form.submit();
							$(":input").prop("disabled", true);
							
							confirmation = function() {return true;};
							
							return false;
						} else if ($('#buttonId').val() == "btnGyoshaIraiMailSend") {
							// 業者依頼メール送信時は、ボタンを使用不可にしないでsubmit＆新規ウィンドウ表示
							createWindow("tb_request_full_entry_send_mail_win", 1024, 420);
							
							// 別ウィンドウに表示するため、元の値を保持し、後で戻す
							var bkDispKbn = $('#main_dispKbn').val();
							
							$('#main_actionType').val('sendMail');
							$('#main_dispKbn').val('');
							
							form.submit();

							confirmation = function() {return true;};
							
							$('#main_actionType').val('update');
							$('#main_dispKbn').val(bkDispKbn);
							
							return false;
						} else {
							// その他の場合は、ボタンを使用不可にしないでsubmit
							form.submit();

							confirmation = function() {return true;};

							return false;
						}
					}
				});
	
				$(".swipebox1").swipebox({
					hideBarsDelay : 0
				});
				
				$(".swipebox2").swipebox({
					hideBarsDelay : 0
				});
	
				//******************************************************
				// 戻るボタン押下時
				//******************************************************
				$('[id^="btnBack"]').click(function(e) {
					$('#buttonId').val("btnBack");
					
					<%-- 依頼検索から遷移した場合と、問い合わせ登録から遷移してきた場合は、Formが違うので処理を分ける --%>
					<s:if test="isFromInquiryEntry()">
						<%-- 問い合わせ登録画面から遷移してきた場合 --%>
						$('#inquiryEntry').prop('action', '<s:url action="inquiryEntryUpdateInit" />');
						$('#inquiryEntry').prop('target', '_self');
						
						$('#inquiryEntry').addClass('ignore');
						$('#inquiryEntry').submit();
						$('#inquiryEntry').removeClass('ignore');
					</s:if>
					<s:else>
						<%-- 依頼検索画面から遷移してきた場合 --%>
						$('#main').prop('action', '<s:url action="requestSearch" />');
						$('#main').prop('target', '_self');
	
						$('#main').addClass('ignore');
						$('#main').submit();
						$('#main').removeClass('ignore');
					</s:else>
					$('#buttonId').val('');
				});
				
				//******************************************************
				// 閉じるボタン押下時
				//******************************************************
				$('[id^="btnClose"]').click(function(e) {
					window.close();
				});

				//******************************************************
				// 業者選択ボタン押下時
				//******************************************************
				$("#btnContractorSelect").click(function(e) {
					$('#gyosha').addClass('ignore');

					var w = createWindow("tb_center_contractor_search_win");
					
					$('#gyosha').prop('action','<s:url action="centerContractorSearchInit" />');
					$('#gyosha').prop('target', w.name);

					$('#gyosha').submit();
					$('#gyosha').removeClass('ignore');
				});
				
				//******************************************************
				// 業者依頼メール送信ボタン押下時
				//******************************************************
				$('#btnGyoshaIraiMailSend').click(function(e) {
					// 確認ダイアログ
					confirmation = function() {
						return confirm(jQuery.validator.format(INF0001, "業者依頼メール", '送信'));
					};

					$('#buttonId').val("btnGyoshaIraiMailSend");
					
					// 作業依頼メール送信
					$('#main').prop('action', '<s:url action="requestFullEntrySendMail" />');
					$('#main').prop('target', "tb_request_full_entry_send_mail_win");

					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
					
					$('#buttonId').val('');
				});
				
				//******************************************************
				// 公開メールボタン押下時
				//******************************************************
				$('#btnPublishMailSend').click(function(e) {
					$('#buttonId').val("btnPublishMailSend");
					
					var w = createWindow("tb_disclosure_mail_send_win");
					
					$('#main').prop('action', '<s:url action="disclosureMailSendInit" />');
					$('#main').prop('target', w.name);
					
					$('#shoriKbn').val('3');
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
					
					$('#shoriKbn').val('');
					$('#buttonId').val('');
				});
				
				//******************************************************
				// 依頼書印刷メールボタン押下時
				//******************************************************
				$('#btnSagyoIraiPrint').click(function(e) {
					$('#buttonId').val("btnSagyoIraiPrint");
					
					$('#main').prop('action', '<s:url action="requestFullEntryWorkRequestPrint" />');
					$('#main').prop('target', '_self');
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
					
					$('#buttonId').val('');
				});
				
				//******************************************************
				// 業者回答欄「<<」ボタン押下時
				//******************************************************
				$('#btnGyoshaKaitoCopy').click(function(e) {
					if (!confirm(jQuery.validator.format(INF0001, "作業状況に業者回答", "コピー"))) {
						return;
					}
					
					// 業者回答欄の項目を作業状況にコピー（空欄のものは空欄にする）
					// 作業完了フラグ
					if ($('#main_gyoshaSagyoJokyo_sagyoKanryoFlg').val() == '1') {
						// 作業完了フラグ「1:完了」の場合は、チェックON
						$('#main_sagyoJokyo_sagyoKanryoFlg').attr("checked", true);

					} else {
						// それ以外の場合は、チェックOFF
						$('#main_sagyoJokyo_sagyoKanryoFlg').attr("checked", false);
					}
					fnc_sagyo_kanryo_check();

					// 作業完了日
					$('#main_sagyoJokyo_sagyoKanryoYmd').val($('#spn_gyosha_sagyo_kanryo_ymd').text());
					$('#main_sagyoJokyo_sagyoKanryoYmd').css('color', '#7A6A6C');
					if ($('#main_sagyoJokyo_sagyoKanryoYmd').val() == '') {
						$('#main_sagyoJokyo_sagyoKanryoYmd').val($('#main_sagyoJokyo_sagyoKanryoYmd').data('placeholder-string'));
					}
					// 作業完了時間
					$('#main_sagyoJokyo_sagyoKanryoJikan').val($('#spn_gyosha_sagyo_kanryo_jikan').text());
					$('#main_sagyoJokyo_sagyoKanryoJikan').css('color', '#7A6A6C');
					if ($('#main_sagyoJokyo_sagyoKanryoJikan').val() == '') {
						$('#main_sagyoJokyo_sagyoKanryoJikan').val($('#main_sagyoJokyo_sagyoKanryoJikan').data('placeholder-string'));
					}
					// 状況
					$('#main_sagyoJokyo_jokyo').val($('#spn_gyosha_jokyo').text());
					// 原因
					$('#main_sagyoJokyo_cause').val($('#spn_gyosha_cause').text());
					// 実施内容
					$('#main_sagyoJokyo_jisshiNaiyo').val($('#spn_gyosha_jisshiNaiyo').text());
				});
				
				//******************************************************
				// 作業完了チェックボックス変更時
				//******************************************************
				$('#main_sagyoJokyo_sagyoKanryoFlg').click(function(e) {
					fnc_sagyo_kanryo_check();
					
					if ($('#main_sagyoJokyo_sagyoKanryoFlg').attr("checked")) {
						// チェックONの場合
						if ($('#main_sagyoJokyo_sagyoKanryoYmd').val() == ''
							|| $('#main_sagyoJokyo_sagyoKanryoYmd').val() == $('#main_sagyoJokyo_sagyoKanryoYmd').data('placeholder-string')) {
							//作業完了チェックボックスON時に完了日が空欄の場合、システム日付を設定
							$("#main_sagyoJokyo_sagyoKanryoYmd").val("${sysDate}");
							$("#main_sagyoJokyo_sagyoKanryoYmd").css('color', '#7A6A6C');
						}
					} else {
						// チェックOFFの場合
						// 入力不可となるため、一旦、作業完了日、作業完了時間のエラーを削除
						$('#divSagyoKanryo :input').removeClass('error');
						$('#divSagyoKanryo label[class="error"]').hide();
					}
				});
				
				//******************************************************
				// 「問い合わせ履歴を自動登録する」チェックボックス変更時
				//******************************************************
				$('#main_historyAutoRegistFlg').click(function(e) {
					fnc_auto_history_regist_check();
				});
				
				//******************************************************
				// 削除（画像１～３）ボタン押下時
				//******************************************************
				$('[id^="btnImageDelete"]').click(function(e) {
					var lineNo = $(this).attr('lineNo');
					// 確認ダイアログ
					confirmation = function() {
						return confirm(jQuery.validator.format(INF0001, "画像" + lineNo + "ファイル", '削除'));
					};
					
					$('#buttonId').val("btnImageDelete");
					
					$("#main_uploadFileNm").val($(this).attr('uploadFileNm'));
					$("#main_fileIndex").val($(this).attr('fileIndex'));
					
					$('#main').prop('action','<s:url action="requestFullEntryImageDelete" />');
					$('#main').prop('target', '_self');
					
					$('#main').addClass('ignore');	
					$('#main').submit();
					$('#main').removeClass('ignore');
					
					$('#buttonId').val('');
				});
				
				//******************************************************
				// 削除（その他１～３）ボタン押下時
				//******************************************************
				$('[id^="btnOtherDelete"]').click(function(e) {
					var lineNo = $(this).attr('lineNo');
					// 確認ダイアログ
					confirmation = function() {
						return confirm(jQuery.validator.format(INF0001, "その他" + lineNo + "ファイル", '削除'));
					};
					
					$('#buttonId').val("btnOtherDelete");
					
					$("#main_uploadFileNm").val($(this).attr('uploadFileNm'));
					$("#main_fileIndex").val($(this).attr('fileIndex'));
					
					$('#main').prop('action','<s:url action="requestFullEntryOtherDelete" />');
					$('#main').prop('target', '_self');
					
					$('#main').addClass('ignore');	
					$('#main').submit();
					$('#main').removeClass('ignore');
					
					$('#buttonId').val('');
				});
				
				//**********************************************
				// その他リンク押下時
				//**********************************************
				$('.linkOtherFile').click(function(e) {
					$('#buttonId').val("linkOtherFile");
					$('#main_uploadFileNm').val($(this).attr('uploadFileNm'));

					$('#main').prop('action', '<s:url action="requestDetailDownload" />');
					if (isIPad == true) {
						// iPadの場合は、_blankをターゲット
						$('#main').prop('target', '_blank');
					} else {
						// それ以外の場合は、iframeをターゲット
						$('#main').prop('target', 'tb_request_file_frame');
					}
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
					
					$('#buttonId').val('');
				});
				
				//******************************************************
				// 作業報告書公開中リンク押下時
				//******************************************************
				$('.linkWorkReport').click(function(e) {
					$('#buttonId').val("linkWorkReport");
					
					$('#targetKokyakuId').val($('#main_toiawase_kokyakuId').val());
					$('#shoriKbn').val('2');
					
					$('#main').prop('action', '<s:url action="inquiryEntryDownload" />');
					
					if (isIPad == true) {
						// iPadの場合は、_blankをターゲット
						$('#main').prop('target', '_blank');
					} else {
						// それ以外の場合は、iframeをターゲット
						$('#main').prop('target', 'tb_request_file_frame');
					}
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
					
					$('#targetKokyakuId').val('');
					$('#shoriKbn').val('');
					$('#buttonId').val('');
				});
				
				//******************************************************
				// 削除ボタン押下時
				//******************************************************
				$('#btnDelete').click(function(e) {
					confirmation = function() {
						return confirm(jQuery.validator.format(INF0001, "依頼情報", "削除"));
					};
					
					$('#buttonId').val("btnDelete");
					$('#main_actionType').val('delete');

					$('#main').prop('action', '<s:url action="requestFullEntryUpdate" />');
					$('#main').prop('target', '_self');
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
					
					$('#buttonId').val('');
				});
				
				//******************************************************
				// 作業報告書印刷ボタン押下時
				//******************************************************
				$('#btnWorkReportPrint').click(function(e) {
					var w = createWindow("tb_report_print_win");
					
					$('#hokokusho').prop('action', '<s:url action="reportPrintInit" />');
					$('#hokokusho').prop('target', w.name);
					
					$('#hokokusho').addClass('ignore');
					$('#hokokusho').submit();
					$('#hokokusho').removeClass('ignore');
				});
				
				//******************************************************
				// 登録ボタン押下時
				//******************************************************
				$('#btnEntry').click(function(e) {
					confirmation = function() {
						var buttonNm = "";
	
						if ($('#main_actionType').val() == 'insert') {
							buttonNm = "登録";
						} else if ($('#main_actionType').val() == 'update') {
							buttonNm = "更新";
						}
	
						return confirm(jQuery.validator.format(INF0001, "依頼情報", buttonNm));
					};
					
					// ファイル名取得処理
					var fileNames = "";
					var imageFilesLength = $("input[name^='sagyoJokyoImageFiles']").length;
					var itemCnt = 0;
					$("input[name^='sagyoJokyoImageFiles']").each(function(){
						fileNames = fileNames + $(this).val();
						if (itemCnt != (imageFilesLength - 1)) {
							fileNames = fileNames + ",";
						}
						itemCnt = itemCnt + 1;
					});

					$("#sagyoJokyoImageFileNm").val(fileNames);
					
					var otherFileNames = "";
					var otherFilesLength = $("input[name^='otherFiles']").length;
					var otherItemCnt = 0;
					$("input[name^='otherFiles']").each(function(){
						otherFileNames = otherFileNames + $(this).val();
						if (otherItemCnt != (otherFilesLength - 1)) {
							otherFileNames = otherFileNames + ",";
						}
						otherItemCnt = otherItemCnt + 1;
					});

					$("#otherFileNm").val(otherFileNames);
					$('#buttonId').val("btnEntry");
					
					$('#main').prop('action', '<s:url action="requestFullEntryUpdate" />');
					$('#main').prop('target', '_self');
	
					$('#main').submit();
					
					$('#buttonId').val('');
				});
				
				//******************************************************
				// 更新ボタン押下時
				//******************************************************
				$('#btnUpdate').click(function(e) {
					confirmation = function() {
						return confirm(jQuery.validator.format(INF0001, "依頼情報", "更新"));
					};
					
					// ファイル名取得処理
					var fileNames = "";
					var imageFilesLength = $("input[name^='sagyoJokyoImageFiles']").length;
					var itemCnt = 0;
					$("input[name^='sagyoJokyoImageFiles']").each(function(){
						fileNames = fileNames + $(this).val();
						if (itemCnt != (imageFilesLength - 1)) {
							fileNames = fileNames + ",";
						}
						itemCnt = itemCnt + 1;
					});

					$("#sagyoJokyoImageFileNm").val(fileNames);
					
					var otherFileNames = "";
					var otherFilesLength = $("input[name^='otherFiles']").length;
					var otherItemCnt = 0;
					$("input[name^='otherFiles']").each(function(){
						otherFileNames = otherFileNames + $(this).val();
						if (otherItemCnt != (otherFilesLength - 1)) {
							otherFileNames = otherFileNames + ",";
						}
						otherItemCnt = otherItemCnt + 1;
					});

					$("#otherFileNm").val(otherFileNames);
					$('#buttonId').val("btnUpdate");
					
					$('#main').prop('action', '<s:url action="requestFullEntryUpdate" />');
					$('#main').prop('target', '_self');
					
					$('#main').submit();
					$('#buttonId').val('');
				});
				
				//**********************************************
				// 作業完了チェックボックスの状態による項目制御
				//**********************************************
				function fnc_sagyo_kanryo_check() {
					// 作業完了処理
					if ($('#main_sagyoJokyo_sagyoKanryoFlg').attr("checked")) {
						// 完了日、完了時間、カレンダー、「問い合わせ履歴を自動登録する」チェックの制御可
						$('#main_sagyoJokyo_sagyoKanryoYmd').prop('disabled', false);
						$('#main_sagyoJokyo_sagyoKanryoJikan').prop('disabled', false);
						$('#main_sagyoJokyo_sagyoKanryoYmd').datepicker('enable');
						$('#main_historyAutoRegistFlg').prop('disabled', false);
						if ($('#main_historyAutoRegistFlg').attr("checked")) {
							$('#main_historyAutoRegistJokyoKbn').prop('disabled', false);
						}
					} else {
						// 完了日、完了時間、カレンダー、「問い合わせ履歴を自動登録する」チェックの制御不可
						$('#main_sagyoJokyo_sagyoKanryoYmd').prop('disabled', true);
						$('#main_sagyoJokyo_sagyoKanryoJikan').prop('disabled', true);
						$('#main_sagyoJokyo_sagyoKanryoYmd').datepicker('disable');
						$('#main_historyAutoRegistFlg').prop('disabled', true);
						$('#main_historyAutoRegistJokyoKbn').prop('disabled', true);
					}
				}
				
				//******************************************************
				// 「問い合わせ履歴を自動登録する」の状態による項目制御
				//******************************************************
				function fnc_auto_history_regist_check() {
					if ($('#main_historyAutoRegistFlg').attr("checked")) {
						// 「問い合わせ履歴を自動登録する」チェックボックスONの場合、制御可
						$('#main_historyAutoRegistJokyoKbn').prop('disabled', false);
					} else {
						// 「問い合わせ履歴を自動登録する」チェックボックスOFFの場合、制御不可
						$('#main_historyAutoRegistJokyoKbn').prop('disabled', true);
					}
				}
				
				//******************************************************
				// 依頼書印刷処理呼び出し
				//******************************************************
				function fnc_work_request_report_download() {
					$('#main').prop('action', '<s:url action="requestFullEntryPrintDownload" />');
					
					if (isIPad == true) {
						// iPadの場合は、_blankをターゲット
						$('#main').prop('target', '_blank');
					} else {
						// それ以外の場合は、iframeをターゲット
						$('#main').prop('target', 'tb_request_file_frame');
					}
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
				}
				
				<s:if test="isUpdate()">
				fnc_sagyo_kanryo_check();
				fnc_auto_history_regist_check();
				</s:if>
				$('#noFocus').val('');
				
				<s:if test="isWorkRequestPrinted()">
					<%-- 依頼書印刷処理後は、ダウンロード処理実行 --%>
					fnc_work_request_report_download();
				</s:if>
			});
			
			//******************************************************
			// 画面値復元処理
			//******************************************************
			function fnc_restore() {
				// 画面リロード時にフォーカスしないようにする
				$('#noFocus').val('1');
				
				$('#main').prop('action', '<s:url action="requestFullEntryUpdateInit" />');
				$('#main').prop('target', '_self');
				
				$('#main_actionType').val('restore');
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
				
				$('#noFocus').val('');
			}
		//-->
		</script>
	</tiles:putAttribute>
	
	<s:if test="isSendMail()">
		<tiles:putAttribute name="title">センター業者メール送信</tiles:putAttribute>
	</s:if>
	<s:else>
		<tiles:putAttribute name="title">依頼登録</tiles:putAttribute>
	</s:else>
	<tiles:putAttribute name="body">
		<s:if test="isInitError() || isDeleteCompleted() || isSendMail()">
			<%-- 初期表示エラー、削除完了、業者依頼メール送信完了の場合、閉じるボタンのみ表示 --%>
			<br><br>
			<div align="center">
				<s:form id="main">
					<s:if test="isFromRequestSearch() || isFromInquiryEntry()">
						<input type="button" id="btnBack" value="戻る" class="btnSubmit">
					</s:if>
					<s:else>
						<input type="button" id="btnClose" value=" 閉じる " class="btnSubmit">
					</s:else>
					
					<%-- 検索条件引き継ぎ。 --%>
					 ${f:writeHidden2(condition, "condition", excludeField)}
					 
					 <s:tokenCheck displayId="TB033" />
				</s:form>
				<s:form id="inquiryEntry" method="POST">
					<s:hidden key="toiawaseNo" />
					<s:hidden key="toiawaseRirekiNo" />
					<s:hidden key="dispKbn" value="%{rootDispKbn}" />
					
					<%-- 問い合わせ検索の検索条件 --%>
					${f:writeHidden2(toiawaseCondition, "condition", excludeField)}
				</s:form>
			</div>
			<br><br>
		</s:if>
		<s:else>
			<%-- それ以外（正常系、初期表示エラーでないエラー）の場合 --%>
		<s:form id="main" enctype="multipart/form-data">
			<s:if test="toiawase.kokyakuId != null && toiawase.kokyakuId != ''">
				<%-- 顧客ＩＤ有りの場合 --%>
				<c:import url="/WEB-INF/jsp/tb040_customer_common_info.jsp" />
			</s:if>
			<s:else>
				<%-- 顧客ＩＤ無しの場合 --%>
				<div id="content">
					<h2>顧客基本情報</h2>
					<table width="90%">
						<tr>
							<th width="10%">区分</th>
							<td>${f:out(kokyakuWithoutId.kokyakuKbnNm)}</td>
						</tr>
						<tr>
							<th>名称</th>
							<td>${f:out(kokyakuWithoutId.kokyakuNm)}</td>
						</tr>
						<tr>
							<th>住所</th>
							<td>${f:out(kokyakuWithoutId.kokyakuJusho)}</td>
						</tr>
						<tr>
							<th>電話番号</th>
							<td>${f:out(kokyakuWithoutId.kokyakuTel)}</td>
						</tr>
					</table>
				</div>
			</s:else>
			<div class="right">
				<%-- 遷移元の画面によって、表示するボタンを変更 --%>
				<s:if test="isFromRequestSearch() || isFromInquiryEntry()">
					<%-- 依頼検索、問い合わせ登録からの遷移の場合は、戻るボタンを表示 --%>
					<input type="button" id="btnBack1" class="btnSubmit" value=" 戻 る " />
				</s:if>
				<s:else>
					<%-- 上記以外の場合は、閉じるボタンを表示 --%>
					<input type="button" id="btnClose1" class="btnSubmit" value=" 閉じる " />
				</s:else>
			</div>

			<div class="content">
				<h2>問い合わせ基本情報</h2>
				<table width="90%">
					<tr>
						<th width="10%">受付番号</th>
						<td>${f:out(toiawaseNoForDisplay)}</td>
					</tr>
					<tr>
						<th>内容</th>
						<td>${f:br(f:out(toiawase.toiawaseNaiyo))}</td>
					</tr>
				</table>
				<br>
				<h2>作業依頼内容</h2>
				<s:if test="isUpdate()">
					<div class="right">
						&nbsp;最終更新：${f:dateFormat(lastUpdDtForDisplay, "yyyy/MM/dd HH:mm:ss")}&nbsp;&nbsp;&nbsp;${f:out(lastUpdNmForDisplay)}
					</div>
				</s:if>
				
				<table width="90%">
					<tr>
						<th width="10%"><span class="font10">* </span>発注担当</th>
						<td>
							<s:if test="irai.tantoshaNm == null || irai.tantoshaNm == ''">
								&nbsp;${f:out(irai.tantoshaNmForId)}
							</s:if>
							<s:else>
								<s:textfield key="irai.tantoshaNm" cssStyle="width:280px;" cssClass="zenhankaku" />
							</s:else>
						</td>
					</tr>
					<tr>
						<th><span class="font10">* </span>依頼業者</th>
						<td valign="middle">
							<input type="button" id="btnContractorSelect" value="業者選択" class="btnDialog" />
							<s:hidden key="irai.iraiGyoshaCd"/>
							<s:textfield key="iraiGyosha.gyoshaNm" readonly="true" cssClass="readOnlyText" />
							<s:if test="isGyoshaIraiMailCommentDisplay()">
								<span style="float:right">
									&nbsp;&nbsp;&nbsp;<span class="font6" id="spn_irai_mail_message">※依頼公開時、センター業者へメール送信可能</span>
									<s:if test="irai.isPublished()">
										&nbsp;<input type="button"id="btnGyoshaIraiMailSend" value="業者依頼メール送信" class="btnSubmit" />
									</s:if>
								</span>
							</s:if>
						</td>
					</tr>
					<tr>
						<th><span class="font10">* </span>依頼内容</th>
						<td>
							<s:textarea key="irai.iraiNaiyo" cssStyle="width:100%" rows="2" cssClass="required zenhankaku byteVarchar600" />
						</td>
					</tr>
					<tr>
						<th>訪問希望<br>訪問予定</th>
						<td>
							<table class="padoff">
								<tr class="padoff">
									<td class="padoff">
										<span class="font2">訪問希望：</span>
										<s:textfield key="irai.homonKiboYmd" maxlength="10" cssStyle="width:100px;" cssClass="dateYMD" placeholder="yyyymmdd" />
										&nbsp;
										<s:select key="irai.homonKiboJikanKbn" list="homonKiboList" listKey="comCd" listValue="comVal" emptyOption="true" />
										<s:textfield key="irai.homonKiboBiko" maxlength="100" cssStyle="width:450px;" cssClass="zenhankaku" />
									</td>
								</tr>
								<label for="main_irai_homonKiboYmd" style="float:left;" class="error" generated="true" /></label>
								<label for="main_irai_homonKiboBiko" style="float:left;" class="error" generated="true" /></label>
							</table>
							<table class="padoff">
								<tr class="padoff">
									<td class="padoff">
										<span class="font2">業者回答：</span>
										<s:textfield key="irai.gyoshaKaitoYmd" maxlength="10" cssStyle="width:100px;" cssClass="dateYMD" placeholder="yyyymmdd" />
										&nbsp;
										<s:select key="irai.gyoshaKaitoJikanKbn" list="gyoshaKaitoList" listKey="comCd" listValue="comVal" emptyOption="true" />
										<s:textfield key="irai.gyoshaKaitoBiko" maxlength="100" cssStyle="width:450px;" cssClass="zenhankaku" />									</td>
								</tr>
								<label for="main_irai_gyoshaKaitoYmd" style="float:left;" class="error" generated="true" /></label>
								<label for="main_irai_gyoshaKaitoBiko" style="float:left;" class="error" generated="true" /></label>
							</table>
						</td>
					</tr>
					<tr>
						<th>依頼情報公開</th>
						<td>
							<label>
								<s:checkbox key="irai.iraiKokaiFlg" fieldValue="1" value="%{irai.isPublished()}" />依頼情報を公開する　→　
							</label>
							<span class="font10">チェックONで登録すると、依頼情報が公開されます。</span>
						</td>
					</tr>
					<s:if test="isUpdate()">
						<tr>
							<th>メール送信<br>状況</th>
							<td colspan="3">
								<span style="float:right">
									<s:if test="isPublishMailButtonVisible()">
										<s:if test="mailRireki != null">
												<input type="button" id="btnPublishMailSend" class="btnSubmit" value="公開メール送信" />
										</s:if>
										<s:else>
												<input type="button" id="btnPublishMailSend" class="btnNotSent" value="公開メール送信" />
										</s:else>
									</s:if>
								</span>
								件名　　：${f:out(mailRireki.subject)}<br>
								送信日時：${f:dateFormat(mailRireki.updDt,  "yyyy/MM/dd HH:mm:ss")}
							</td>
						</tr>
						<tr>
							<th>最終印刷者</th>
							<td colspan="3">
								<span style="float:right">
									<input type="button" id="btnSagyoIraiPrint" class="btnSubmit" value="依頼書印刷" />
								</span>
								日時　　：${f:dateFormat(irai.printDt,  "yyyy/MM/dd HH:mm:ss")}<br>
								印刷者名：${f:out(LastPrintNmForDisplay)}
							</td>
						</tr>
					</s:if>
				</table>
				<br>
				
				<s:if test="isUpdate()">
					<h2>作業状況</h2>
					<table>
						<tr>
							<th colspan="2">作業状況</th>
							<th>
								<s:if test="isContractorAnswerCopyButtonVisible()">
									<input type="button" id="btnGyoshaKaitoCopy" value="<<" class="btn_normal" title="業者回答の内容で作業状況を上書きコピーします。">
								</s:if>
							</th>
							<th>業者回答</th>
						</tr>
						<tr>
							<th width="10%">作業完了</th>
							<td width="45%">
								<div id="divSagyoKanryo">
									<label><s:checkbox key="sagyoJokyo.sagyoKanryoFlg" fieldValue="1" value="%{isSagyoKanryoFlgChecked()}" />作業完了</label>
									&nbsp;&nbsp;<span class="font2">完了日 </span><s:textfield key="sagyoJokyo.sagyoKanryoYmd" cssStyle="width:100px" cssClass="dateYMD" maxlength="10" placeholder="yyyymmdd" />&nbsp;
									&nbsp;&nbsp;<span class="font2">完了時間 </span><s:textfield key="sagyoJokyo.sagyoKanryoJikan" cssStyle="width:50px" cssClass="time" maxlength="5" placeholder="hhmm" />
									<label for="main_sagyoJokyo_sagyoKanryo" style="float:left;" class="error" generated="true" /></label>
								</div>
							</td>
							<th width="5%">
								作業<br>完了
							</th>
							<td>
								${f:out(gyoshaSagyoJokyo.sagyoKanryoFlgNm)}
								&nbsp;&nbsp;<span id="spn_gyosha_sagyo_kanryo_ymd">${f:dateFormat(gyoshaSagyoJokyo.sagyoKanryoYmd, "yyyy/MM/dd")}</span>
								&nbsp;&nbsp;<span id="spn_gyosha_sagyo_kanryo_jikan">${f:out(gyoshaSagyoJokyo.sagyoKanryoJikanPlusColon)}</span>
							</td>
						</tr>
						<tr>
							<th>状況</th>
							<td>
								<s:textarea key="sagyoJokyo.jokyo" cssClass="zenhankaku byteVarchar600" cssStyle="width:100%" rows="2" />
							</td>
							<th>状況</th>
							<td>
								<span id="spn_gyosha_jokyo">${f:out(gyoshaSagyoJokyo.jokyo)}</span>
								&nbsp;
							</td>
						</tr>
						<tr>
							<th>原因</th>
							<td>
								<s:textarea key="sagyoJokyo.cause" cssClass="zenhankaku byteVarchar600" cssStyle="width:100%" rows="2" />
							</td>
							<th>原因</th>
							<td>
								<span id="spn_gyosha_cause">${f:out(gyoshaSagyoJokyo.cause)}</span>
								&nbsp;
							</td>
						</tr>
						<tr>
							<th>実施内容</th>
							<td>
								<s:textarea key="sagyoJokyo.jisshiNaiyo" cssClass="zenhankaku byteVarchar600" cssStyle="width:100%" rows="2" />
							</td>
							<th>実施<br>内容</th>
							<td>
								<span id="spn_gyosha_jisshiNaiyo">${f:out(gyoshaSagyoJokyo.jisshiNaiyo)}</span>
								&nbsp;
							</td>
						</tr>
						<s:iterator value="uploadedFiles" status="i" var="file">
							<tr>
								<th>画像${f:out(i.count)}</th>
								<td>
									<s:if test="%{#file != null}">
										<a href="<s:url action='requestFullEntryImageDownload' />?toiawaseNo=${toiawaseNo}&toiawaseRirekiNo=${toiawaseRirekiNo}&imageToken=${imageToken}&fileIndex=${fileIndex}&uploadFileNm=${uploadFileNm}" class="swipebox1" title="${f:out(baseFileNm)}" uploadFileNm="${uploadFileNm}" imageToken="${imageToken}" >${f:out(baseFileNm)}</a>
										<span style="float:right;">
											<s:if test="userContext.isAdministrativeInhouse() || userContext.isOutsourcerSv()">
												<input type="button" id="btnImageDelete${f:out(i.count)}" value="削除" uploadFileNm="${uploadFileNm}" fileIndex="${fileIndex}" lineNo="${(i.count)}">
											</s:if>
										</span>
										<%-- 要素数確保のため、非表示でファイルオブジェクトを作成 --%>
										<br>
										<s:file name="sagyoJokyoImageFiles[%{#i.index}]" label="File" cssStyle="display:none;" />
										<s:textarea key="sagyoJokyoImageFileComments[%{#i.index}]" cssStyle="width:87%" rows="2" />
									</s:if>
									<s:else>
										<s:file name="sagyoJokyoImageFiles[%{#i.index}]" label="File" />
										<br>
										<s:textarea key="sagyoJokyoImageFileComments[%{#i.index}]" cssStyle="width:87%" rows="2" />
									</s:else>
								</td>
								<th>画像${f:out(i.count)}</th>
								<td>
									<a href="<s:url action='requestEntryDownload' />?toiawaseNo=${gyoshaUploadFileList[i.index].toiawaseNo}&toiawaseRirekiNo=${gyoshaUploadFileList[i.index].toiawaseRirekiNo}&uploadFileNm=${gyoshaUploadFileList[i.index].uploadFileNm}&fileKbn=1&imageToken=${imageToken}" class="swipebox2" title="${f:out(gyoshaUploadFileList[i.index].baseFileNm)}" target="_blank">${f:out(gyoshaUploadFileList[i.index].baseFileNm)}</a>
								</td>
							</tr>
							<s:hidden key="sagyoJokyoImageFileUploadFileNms[%{#i.index}]" />
							<s:hidden key="sagyoJokyoImageFileFileIndexes[%{#i.index}]" />
						</s:iterator>
						<s:iterator value="uploadedOtherFiles" status="i" var="otherFile">
							<tr>
								<th>その他${f:out(i.count)}</th>
								<td>
									<s:if test="%{#otherFile != null}">
										<a href="#" title="${f:out(baseFileNm)}" uploadFileNm="${uploadFileNm}" class="linkOtherFile">${f:out(baseFileNm)}</a>
										<span style="float:right;">
											<s:if test="userContext.isAdministrativeInhouse() || userContext.isOutsourcerSv()">
												<input type="button" id="btnOtherDelete${f:out(i.count)}" value="削除" uploadFileNm="${uploadFileNm}" fileIndex="${fileIndex}" lineNo="${(i.count)}">
											</s:if>
										</span>
										<%-- 要素数確保のため、非表示でファイルオブジェクトを作成 --%>
										<br>
										<s:file name="otherFiles[%{#i.index}]" label="File" cssStyle="display:none;" />
										<s:textarea key="otherFileComments[%{#i.index}]" cssStyle="width:87%" rows="2" />
									</s:if>
									<s:else>
										<s:file name="otherFiles[%{#i.index}]" label="File" />
										<br>
										<s:textarea key="otherFileComments[%{#i.index}]" cssStyle="width:87%" rows="2" />
									</s:else>
								</td>
								<td colspan="2"></td>
							</tr>
							<s:hidden key="otherFileUploadFileNms[%{#i.index}]" />
							<s:hidden key="otherFileUploadFileFileIndexes[%{#i.index}]" />
						</s:iterator>
						<tr>
							<th>作業状況公開</th>
							<td colspan="3">
								<s:checkbox key="sagyoJokyo.sagyoJokyoKokaiFlg" fieldValue="1" value="%{sagyoJokyo.isPublished()}" />
								作業状況を公開する　→　<span class="font10">チェックONで登録すると、作業状況が公開されます。</span>
							</td>
						</tr>
						<s:if test="sagyoJokyo != null">
							<tr>
								<th>報告書公開</th>
								<td colspan="3">
									<s:if test="sagyoJokyo.isReportPublished()">
										<a href="#" class="linkWorkReport">※作業報告書 公開中</a>&nbsp;&nbsp;
										<s:checkbox key="stopPublishFlg" fieldValue="1" value="%{isStopPublishChecked()}" />公開を止める
									</s:if>
									<s:else>
										作業報告書は公開していません。(報告書印刷画面で設定します)
									</s:else>
								</td>
							</tr>
						</s:if>
						<tr>
							<th>履歴自動登録</th>
							<td colspan="3">
								<label><s:checkbox key="historyAutoRegistFlg" fieldValue="1" value="%{isToiawaseHistoryAutoRegistExcecutable()}" />問い合わせ履歴を自動登録する</label>
								&nbsp;→&nbsp;<span class="font2">状況</span>
								<s:select key="historyAutoRegistJokyoKbn" list="jokyoKbnList" listKey="jokyoKbn" listValue="jokyoKbnNm" emptyOption="true" />
							</td>
						</tr>
					</table>
				</s:if>
				<span class="required_remark">
					<span class="font10">*</span> <span class="font3">常に必須</span><br>
				</span>
				<br>
				<span style="float:left;">
					<s:if test="isDeleteButtonVisible()">
						<s:if test="toiawase.shimeYm != null && toiawase.shimeYm != ''">
							<%-- 問い合わせ情報の締め年月がNOT NULLの場合は、使用不可 --%>
							&nbsp;<input type="button" id="btnDelete" class="btnSubmit" value=" 削 除 " disabled="disabled">
						</s:if>
						<s:else>
							&nbsp;<input type="button" id="btnDelete" class="btnSubmit" value=" 削 除 ">
						</s:else>
					</s:if>
				</span>
		
				<div class="right">
					<s:if test="isWorkReportPrintButtonVisible()">
						&nbsp;<input type="button" id="btnWorkReportPrint" class="btnSubmit" value=" 作業報告書印刷 ">
					</s:if>
					<s:if test="isEntryButtonVisible()">
						<s:if test="toiawase.shimeYm != null && toiawase.shimeYm != ''">
							&nbsp;<input type="button" id="btnEntry" class="btnSubmit" value=" 登 録 " disabled="disabled">
						</s:if>
						<s:else>
							&nbsp;<input type="button" id="btnEntry" class="btnSubmit" value=" 登 録 ">
						</s:else>
					</s:if>
					<s:if test="isUpdateButtonVisible()">
						<s:if test="toiawase.shimeYm != null && toiawase.shimeYm != ''">
							&nbsp;<input type="button" id="btnUpdate" class="btnSubmit" value=" 更 新 " disabled="disabled">
						</s:if>
						<s:else>
							&nbsp;<input type="button" id="btnUpdate" class="btnSubmit" value=" 更 新 ">
						</s:else>
					</s:if>
					<%-- 遷移元の画面によって、表示するボタンを変更 --%>
					<s:if test="isFromRequestSearch() || isFromInquiryEntry()">
						<%-- 依頼検索、問い合わせ登録からの遷移の場合は、戻るボタンを表示 --%>
						&nbsp;<input type="button" id="btnBack2" class="btnSubmit" value=" 戻 る " />
					</s:if>
					<s:else>
						<%-- 上記以外の場合は、閉じるボタンを表示 --%>
						&nbsp;<input type="button" id="btnClose2" class="btnSubmit" value=" 閉じる " />
					</s:else>
				</div>
			</div>
			
			<iframe src="" width="0px" height="0px" name="tb_request_file_frame" frameborder="0"></iframe>

			<s:hidden key="actionType" />
			<s:hidden key="toiawaseNo" />
			<s:hidden key="toiawaseRirekiNo" />
			<s:hidden key="toiawaseUpdDt" />
			<s:hidden key="dispKbn" />
			<s:hidden key="uploadFileNm" />
			<s:hidden key="imageToken" />
			<s:hidden key="fileIndex" />
			<s:hidden key="iraiUpdDt" />
			<s:hidden key="sagyoJokyoUpdDt" />
			<s:hidden key="beforeIraiKokaiFlg" />
			<s:hidden key="toiawase.kokyakuId" />
			<s:hidden key="toiawase.toiawaseNaiyo" />
			<s:hidden key="irai.tantoshaId" />
			<s:hidden key="irai.lastUpdId" />
			<s:hidden key="irai.lastPrintId" />
			<s:hidden key="irai.updDt" />
			<s:hidden key="irai.lastUpdNm" />
			<s:hidden key="irai.lastUpdNmForId" />
			<s:hidden key="sagyoJokyo.toiawaseNo" />
			<s:hidden key="sagyoJokyo.lastUpdId" />
			<s:hidden key="sagyoJokyo.updDt" />
			<s:hidden key="sagyoJokyo.hokokushoKokaiFlg" />
			<s:hidden key="sagyoJokyo.lastUpdNm" />
			<s:hidden key="sagyoJokyo.lastUpdNmForId" />
			<s:hidden key="gyoshaSagyoJokyo.toiawaseNo" />
			<s:hidden key="gyoshaSagyoJokyo.jokyo" />
			<s:hidden key="gyoshaSagyoJokyo.cause" />
			<s:hidden key="gyoshaSagyoJokyo.jisshiNaiyo" />
			<s:hidden key="gyoshaSagyoJokyo.sagyoKanryoFlg" />
			<s:hidden key="gyoshaSagyoJokyo.sagyoKanryoFlgNm" />
			<s:hidden key="gyoshaSagyoJokyo.sagyoKanryoYmd" />
			<s:hidden key="gyoshaSagyoJokyo.sagyoKanryoJikan" />
			<s:hidden key="rootDispKbn" />
			<s:hidden key="kokyakuId" value="%{toiawase.kokyakuId}" />
			<s:hidden key="gamenKbn" value="tb033" />
			<s:hidden key="makePdfPath" />
			<s:hidden key="pdfNm" />
			
			<input type="hidden" id="shoriKbn" name="shoriKbn" value="" />
			<input type="hidden" id="fileKbn" name="fileKbn" value="" />
			<input type="hidden" id="targetKokyakuId" name="targetKokyakuId" value="" />
			<input type="hidden" id="sagyoJokyoImageFileNm" name="sagyoJokyoImageFileNm" value="" />
			<input type="hidden" id="buttonId" name="buttonId" value="" />
			<input type="hidden" id="otherFileNm" name="otherFileNm" value="" />
			
			<input type="hidden" id="noFocus" name="noFocus" value="<s:property value="%{#parameters['noFocus']}" />" />
			<%-- 依頼検索の検索条件 --%>
			${f:writeHidden2(condition, "condition", excludeField)}
			
			<%-- 問い合わせ検索の検索条件 --%>
			${f:writeHidden2(toiawaseCondition, "toiawaseCondition", excludeField)}

			<s:tokenCheck displayId="TB033" />
		</s:form>
		
		<s:form id="gyosha" method="POST">
			<input type="hidden" id="dispKbn" name="dispKbn" value="tb033" />
			<input type="hidden" id="gyoshaCdResultNm" name="gyoshaCdResultNm" value="irai.iraiGyoshaCd" />
			<input type="hidden" id="gyoshaNmResultNm" name="gyoshaNmResultNm" value="iraiGyosha.gyoshaNm" />
		</s:form>
		
		<s:form id="hokokusho" method="POST">
			<s:hidden key="toiawaseNo" />
			<s:hidden key="toiawaseRirekiNo" />
			<s:hidden key="toiawaseUpdDt" />
			<s:hidden key="kokyakuId" value="%{toiawase.kokyakuId}" />
			<s:hidden key="sagyoJokyoUpdDt" />
			<input type="hidden" id="dispKbn" name="dispKbn" value="tb033" />
		</s:form>
		
		<s:form id="inquiryEntry" method="POST">
			<s:hidden key="toiawaseNo" />
			<s:hidden key="toiawaseRirekiNo" />
			<s:hidden key="dispKbn" value="%{rootDispKbn}" />
			
			<%-- 問い合わせ検索の検索条件 --%>
			${f:writeHidden2(toiawaseCondition, "condition", excludeField)}
		</s:form>
		</s:else>
	</tiles:putAttribute>
</tiles:insertDefinition>
