<%--*****************************************************
	問い合わせ登録画面
	作成者：阿部
	作成日：2015/08/17
	更新日：2016/07/15 H.Yamamura サービス種別項目追加
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
	<s:if test="!isInitError() && !isDeleteCompleted()"><%-- 初期表示エラーが発生していない、かつ、削除完了でない場合はjavascript定義 start --%>

		<script language="JavaScript" type="text/javascript">
		<!--//

		<%-- 確認ダイアログ(初期値は空) --%>
		var confirmation = function() {return true;};
		
		<%-- 依頼者フラグ 異なるのコード値　※コード値が変更されたら、ここも変更すること --%>
		var IRAISHA_FLG_DIFFRENT = "1";
		
		$(function() {
			
			<%-- 遷移元画面が依頼登録の場合、登録フォームは非活性 --%>
			<s:if test="isFromRequestEntry()">
				fnc_inputform_disabled();
			</s:if>
			
			<%-- 初期表示前に受付時間をcommon.jsのblur処理を呼び出してコロンで整形 --%>
			$("#main_toiawaseInfo_uketsukeJikan").val(ConvCoron(ZenToHan_Num($("#main_toiawaseInfo_uketsukeJikan").val())));
			
			// 新規の場合、登録ボタン使用可否。
			<s:if test = "isInsert()">
				<s:if test="%{!#session.userContext.isAdministrativeInhouse() && !#session.userContext.isOutsourcerSv() && !#session.userContext.isOutsourcerOp()}">
					$("#btnEntry").prop("disabled", true);
				</s:if>
			</s:if>
			
			// 更新の場合、更新ボタン使用可否。
			<s:if test = "isUpdate()">
				<s:if test="%{ (!#session.userContext.isAdministrativeInhouse() && !#session.userContext.isOutsourcerSv() && !#session.userContext.isOutsourcerOp()) || !isUpdateButtonAvailable() }">
					$("#btnEntry").prop("disabled", true);
				</s:if>
			</s:if>
			
			// 依頼者フラグによる制御
			fnc_select_iraisha_flg();

			var kokyakuId = $('#main_kokyakuId').val();
			//  顧客IDが空の場合、更新表示でも入電報告書ボタン･件数報告フラグ･問い合わせ公開フラグは使用不可
			if (kokyakuId != null && kokyakuId != "") {
//				$('#btnPrintNyudenHoukoku').prop('disabled', false); 
			} else {
				$('#main_toiawaseInfo_houkokuTargetFlg').prop('disabled', true);
				$('#main_toiawaseInfo_houkokuTargetFlg').prop('checked', false);
				$('#main_toiawaseInfo_toiawaseKokaiFlg').prop('disabled', true);
				$('#main_toiawaseInfo_toiawaseKokaiFlg').prop('checked', false);
			}
			
			if (($('#main_toiawaseInfo_callSeikyuYm').val()) != '') {
				// コール請求年月日が入力されているとき、受付日、受付時間を使用不可にする
				$('#main_toiawaseInfo_uketsukeYmd').prop('disabled', true);
				$('#main_toiawaseInfo_uketsukeJikan').prop('disabled', true);
				
				// 受付日のカレンダーを使用不可に設定
				$('#main_toiawaseInfo_uketsukeYmd').datepicker('disable');
			}
			
			<s:if test="isKokyakuInfoWithoutIdVisible()">
				fnc_control_hokokusho_kokai_with_no_id_info();
			</s:if>
			<s:else>
				<s:if test="isHoukokushoKokaiFlgChecked()">
					fnc_control_hokokusho_kokai('1');
				</s:if>
				<s:else>
					fnc_control_hokokusho_kokai('0');
				</s:else>
			</s:else>
			
			<%-- tb040_customer_common_info.jsp内の項目は、フィールド自体にチェック属性を定義してあるので注意すること。 --%>
			var validation = $("#main").validate({
				ignore:
					".ignore *",
				rules: {
					newToiawaseNo							: {required: true, digits: true, byteVarchar: 10, notEqualTo: "#main_toiawaseNo"},
					
					"kokyakuInfoWithoutId.kokyakuKbn"		: {required: true},
					"kokyakuInfoWithoutId.kokyakuNm"		: {required: true, byteVarchar: 50},
					"kokyakuInfoWithoutId.kokyakuJusho"		: {required: true, byteVarchar:100},
					"kokyakuInfoWithoutId.kokyakuTel"		: {byteVarchar: 50},
					
					"toiawaseInfo.iraishaFlg"				: {required: true},
					"toiawaseInfo.iraishaKbn"				: {required: true},
					"toiawaseInfo.iraishaNm"				: {required: true, byteVarchar: 50},
					"toiawaseInfo.iraishaTel"				: {digits: true, byteVarchar: 50},
					"toiawaseInfo.iraishaRoomNo"			: {byteVarchar: 10},
					"toiawaseInfo.iraishaMemo"				: {byteVarchar: 200},
					"toiawaseInfo.iraishaSexKbn"			: {required: true},
					"toiawaseInfo.uketsukeYmd"				: {required: true, dateYMD: true, byteVarchar: 10},
					"toiawaseInfo.uketsukeJikan"			: {required: true, time: true, byteVarchar: 5},
					"toiawaseInfo.toiawaseKbn1"				: {required: true},
					"toiawaseInfo.toiawaseKbn2"				: {required: true},
					"toiawaseInfo.toiawaseKbn3"				: {required: function() { return ($('#main_toiawaseInfo_toiawaseKbn4').val() != ''); }},
					"toiawaseInfo.uketsukeKeitaiKbn"		: {required: true},
					"toiawaseInfo.toiawaseNaiyoSimple"		: {required: true, byteVarchar: 100},
					"toiawaseInfo.toiawaseNaiyo"			: {required: true, byteVarchar: 600},
					"toiawaseInfo.iraiUmuKbn"				: {required: true},
					"toiawaseInfo.serviceShubetsu"			: {required: true}
				},
				messages: {
					"newToiawaseNo"							: {notEqualTo: ART0032},
					"toiawaseInfo.toiawaseKbn3"				: {required: ART0033}
				},
				groups: {
					main_toiawaseInfo_toiawaseKbn1	: "toiawaseInfo\.toiawaseKbn1",
					main_toiawaseInfo_toiawaseKbn2	: "toiawaseInfo\.toiawaseKbn2",
					main_toiawaseInfo_toiawaseKbn3	: "toiawaseInfo\.toiawaseKbn3",
					main_toiawaseInfo_iraisha		: "toiawaseInfo\.iraishaFlg toiawaseInfo\.iraishaKbn toiawaseInfo\.iraishaNm toiawaseInfo\.iraishaTel toiawaseInfo\.iraishaRoomNo toiawaseInfo\.iraishaSexKbn",
					main_toiawaseInfo_uketsuke		: "toiawaseInfo\.uketsukeYmd toiawaseInfo\.uketsukeJikan",
					main_toiawaseInfo_toiawase		: "toiawaseInfo\.uketsukeKeitaiKbn toiawaseInfo\.toiawaseNaiyoSimple toiawaseInfo\.toiawaseNaiyo",
					main_toiawaseInfo_iraiUmuKbn	: "toiawaseInfo\.iraiUmuKbn"
				},
				invalidHandler: function(form, validator) {
					alert(ART0015);
					$('#main_buttonId').val('');
					confirmation = function() {return true;};
				},
				submitHandler: function(form) {
					
					if (!confirmation()) {
						$('#main_buttonId').val('');
						if ($('#main_actionType').val() == 'delete') {
							// アクションタイプが削除の場合は、アクションタイプをupdateに戻す
							$('#main_actionType').val('update');
						}
						confirmation = function() {return true;};
						return false;
					}
					
					var buttonId = $('#main_buttonId').val();
					
					// 登録（更新）は受付時間のコロンを取り除く
					if (buttonId == 'btnEntry') {
						$('#main_toiawaseInfo_uketsukeJikan').val(ConvDelCoron($('#main_toiawaseInfo_uketsukeJikan').val()));
					}
					
					// 顧客ＩＤ変更
					if (buttonId == 'btnChangeKokyaku') {
						var win = createWindow('tb_cumeter_id_change_win', 1024, 650);
						form.submit();
					// 全履歴移動
					} else if (buttonId == 'btnRirekiMove') {
						var win = createWindow('tb_inquiry_history_move_win', 1024, 650);
						form.submit();
					// 戻る、登録（更新）、削除、履歴追加、履歴変更
					} else if (buttonId == 'btnBack'
							|| buttonId == 'btnEntry'
							|| buttonId == 'btnDelete'
							|| buttonId == 'btnRirekiAdd'
							|| buttonId == 'linkRirekiChange') {
						
						$(":input").prop("disabled", false);
						$("input:button").prop("disabled", true);
						form.submit();
						$(":input").prop("disabled", true);
					// その他
					} else {
						form.submit();
					}
					
					$('#main_buttonId').val('');
					confirmation = function() {return true;};
				}
			});
			
			//******************************************************
			// 変更ボタン押下時
			//******************************************************
			$('#btnChangeKokyaku').click(function(e) {
				
				$('#main_buttonId').val('btnChangeKokyaku');
				
				// 入力チェック制御
				$("#divNewKokyakuId").removeClass("ignore");
				$("#tblCustomerInfo").addClass("ignore");
				$("#tblToiawaseInfo").addClass("ignore");
				$("#divNewToiawaseNo").addClass("ignore");
				
				// エラー箇所、エラーメッセージをクリア
				$('#tblCustomerInfo :input').removeClass('error');
				$('#tblCustomerInfo label[class="error"]').hide();
				$('#tblToiawaseInfo :input').removeClass('error');
				$('#tblToiawaseInfo label[class="error"]').hide();
				$('#divNewToiawaseNo :input').removeClass('error');
				$('#divNewToiawaseNo label[class="error"]').hide();
				
				// パラメータを設定
				$("#oldKokyakuId").val($("#main_kokyakuId").val());
				$("#newKokyakuId").val($("#main_changeKokyakuId").val());
				
				// ウィンドウ名設定
				$('#main_toiawaseWindowName').val(window.name);
				
				$('#main').prop('action', '<s:url action="customerIdChangeInit" />');
				$('#main').prop('target', 'tb_cumeter_id_change_win');
				
				$('#main').submit();
			});
			
			//******************************************************
			// 閉じるボタン押下時
			//******************************************************
			$('[id^="btnClose"]').click(function(e) {
				window.close();
			});
			
			//******************************************************
			// 戻るボタン押下時
			//******************************************************
			$('[id^="btnBack"]').click(function(e) {
				
				$('#main_buttonId').val('btnBack');
				
				$('#main').prop('action', '<s:url action="inquirySearch" />');
				$('#main').prop('target', '_self');
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
			});
			
			//******************************************************
			// 【問い合わせ】依頼者ラジオ変更時
			//******************************************************
			$('input:radio[name="toiawaseInfo.iraishaFlg"]').change(function(e) {
				
				$('#tblIraishaInfo *').children().removeClass('error');
				$('label[for=main_toiawaseInfo_iraisha]').hide();
				
				fnc_select_iraisha_flg();
			});
			
			//******************************************************
			// 【問い合わせ】マニュアル参照ボタン押下時
			//******************************************************
			$('#btnManual').click(function(e) {
				
				$('#main_buttonId').val('btnManual');
				$('#main_toiawaseKbn1').val($('#main_toiawaseInfo_toiawaseKbn1').val());
				$('#main_toiawaseKbn2').val($('#main_toiawaseInfo_toiawaseKbn2').val());
				$('#main_toiawaseKbn3').val($('#main_toiawaseInfo_toiawaseKbn3').val());
				$('#main_toiawaseKbn4').val($('#main_toiawaseInfo_toiawaseKbn4').val());
				
				$('#main').prop('action', '<s:url action="inquiryKbnManualDownload" />');
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb_inquiry_kbn_manual_frame');
				}
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');

				$('#main_toiawaseKbn1').val('');
				$('#main_toiawaseKbn2').val('');
				$('#main_toiawaseKbn3').val('');
				$('#main_toiawaseKbn4').val('');
			});
			
			//******************************************************
			// 【問い合わせ】入電報告書公開中リンク押下時
			//******************************************************
			$('.linkReport').click(function(e) {
				
				$('#main_targetKokyakuId').val($('#main_kokyakuId').val());
				$('#main_shoriKbn').val('1');
				
				$('#main').prop('action', '<s:url action="inquiryEntryDownload" />');
				
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb_incoming_call_report_frame');
				}
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
				
				$('#main_targetKokyakuId').val('');
			});
			
			//******************************************************
			// 【問い合わせ】入電報告書印刷ボタン押下時
			//******************************************************
			$('#btnPrintNyudenHoukoku').click(function(e) {
				// 報告書印刷画面へ
				var form = document.hokokusho;
				form.action = '<s:url action="reportPrintInit" />';
				var w = createWindow("tb_report_print_win");
				form.target = w.name;
				form.submit();
			});
			
			//******************************************************
			// 【問い合わせ】作業報告書印刷ボタン押下時
			//******************************************************
			$('#btnPrintWorkReport').click(function(e) {
				
				$('#main_targetKokyakuId').val($('#main_kokyakuId').val());
				$('#main_toiawaseRirekiNo').val('1');
				$('#main_shoriKbn').val('2');
				
				$('#main').prop('action', '<s:url action="inquiryEntryDownload" />');
				
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb_incoming_call_report_frame');
				}
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
				
				$('#main_targetKokyakuId').val('');
			});
			
			//******************************************************
			// 【問い合わせ】登録・更新ボタン押下時
			//******************************************************
			$('#btnEntry').click(function(e) {
				
				$('#main_buttonId').val('btnEntry');
				
				// 入力チェック制御
				$("#divNewKokyakuId").addClass("ignore");
				$("#tblCustomerInfo").removeClass("ignore");
				$("#tblToiawaseInfo").removeClass("ignore");
				$("#divNewToiawaseNo").addClass("ignore");
				
				// エラー箇所、エラーメッセージをクリア
				$('#divNewKokyakuId :input').removeClass('error');
				$('#divNewKokyakuId label[class="error"]').hide();
				
				$('#divNewToiawaseNo :input').removeClass('error');
				$('#divNewToiawaseNo label[class="error"]').hide();
				
				var buttonName = $(this).attr('buttonName');
				confirmation = function() {
					return confirm(jQuery.validator.format(INF0001, "問い合わせ基本情報", buttonName));
				};
				
				// ファイル名取得処理
				var fileNames = "";
				var toiawaseFilesLength = $("input[name^='toiawaseFiles']").length;
				var itemCnt = 0;
				$("input[name^='toiawaseFiles']").each(function(){
					fileNames = fileNames + $(this).val();
					if (itemCnt != (toiawaseFilesLength - 1)) {
						fileNames = fileNames + ",";
					}
				});

				$("#main_toiawaseFileNm").val(fileNames);
				
				$('#main').prop('action', '<s:url action="inquiryEntryUpdate" />');
				$('#main').prop('target', '_self');
				
				$('#main').submit();
			});
			
			//******************************************************
			// 【問い合わせ】削除ボタン押下時
			//******************************************************
			$('#btnDelete').click(function(e) {
				
				$('#main_buttonId').val('btnDelete');
				
				confirmation = function() {
					return confirm(jQuery.validator.format(INF0001, "問い合わせ基本情報", '削除'));
				};
				
				$('#main_actionType').val('delete');
				
				$('#main').prop('action', '<s:url action="inquiryEntryUpdate" />');
				$('#main').prop('target', '_self');
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
			});

			//******************************************************
			// 【履歴一覧】全履歴移動ボタン押下時
			//******************************************************
			$('#btnRirekiMove').click(function(e) {
				
				$('#main_buttonId').val('btnRirekiMove');
				
				// 入力チェック制御
				$("#divNewKokyakuId").addClass("ignore");
				$("#tblCustomerInfo").addClass("ignore");
				$("#tblToiawaseInfo").addClass("ignore");
				$("#divNewToiawaseNo").removeClass("ignore");
				
				// エラー箇所、エラーメッセージをクリア
				$('#divNewKokyakuId :input').removeClass('error');
				$('#divNewKokyakuId label[class="error"]').hide();
				
				$('#tblToiawaseInfo :input').removeClass('error');
				$('#tblToiawaseInfo label[class="error"]').hide();
				
				// ウィンドウ名設定
				$('#main_toiawaseWindowName').val(window.name);
				
				$('#main').prop('action', '<s:url action="inquiryHistoryMoveInit" />');
				$('#main').prop('target', 'tb_inquiry_history_move_win');
				
				$('#main').submit();
			});
			
			//******************************************************
			// 【履歴一覧】依頼登録ボタン押下時
			//******************************************************
			$('#btnIraiEntry').click(function(e) {
				
				$('#main_buttonId').val('btnIraiEntry');
				
				$('#requestEntry').prop('action', '<s:url action="requestFullEntryInit" />');
				$('#requestEntry').prop('target', '_self');
				
				$('#main_requestEntry_toiawaseRirekiNo').val('');
				
				$('#requestEntry').addClass('ignore');
				$('#requestEntry').submit();
				$('#requestEntry').removeClass('ignore');
			});
			
			//******************************************************
			// 【履歴一覧】履歴追加ボタン押下時
			//******************************************************
			$('#btnRirekiAdd').click(function(e) {
				
				$('#main_buttonId').val('btnRirekiAdd');
				
				$('#main').prop('action', '<s:url action="inquiryHistoryEntryInit" />');
				$('#main').prop('target', '_self');
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
			});
			
			//******************************************************
			// 【履歴一覧】変更リンク押下時
			//******************************************************
			$('.linkRirekiChange').click(function(e) {
				
				$('#main_buttonId').val('linkRirekiChange');
				
				$('#main_toiawaseRirekiNo').val($(this).attr('toiawaseRirekiNo'));
				
				$('#main').prop('action', '<s:url action="inquiryHistoryEntryUpdateInit" />');
				$('#main').prop('target', '_self');
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
				
				$('#main_toiawaseRirekiNo').val('');
			});
			
			//******************************************************
			// 【履歴一覧】依頼リンク押下時
			//******************************************************
			$('.linkIraiChange').click(function(e) {
				
				$('#main_buttonId').val('linkIraiChange');
				
				$('#requestEntry').prop('action', '<s:url action="requestFullEntryUpdateInit" />');
				$('#requestEntry').prop('target', '_self');
				
				$('#main_requestEntry_toiawaseRirekiNo').val($(this).attr('toiawaseRirekiNo'));
				
				$('#requestEntry').addClass('ignore');
				$('#requestEntry').submit();
				$('#requestEntry').removeClass('ignore');
				
				$('#main_requestEntry_toiawaseRirekiNo').val('');
			});
			
			//******************************************************
			// 【問い合わせ】公開メールボタン押下時
			//******************************************************
			$('#btnSendMail').click(function(e) {
				
				$('#main_buttonId').val('btnSendMail');
				$('#main_toiawaseWindowName').val(window.name);
				$('#main_shoriKbn').val('1');
				
				var w = createWindow("tb_disclosure_mail_send_win");
				$('#main').prop('action', '<s:url action="disclosureMailSendInit" />');
				$('#main').prop('target', w.name);
				
				$('#main_toiawaseRirekiNo').val('');
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
			});
			
			//******************************************************
			// ファイルリンク押下時
			//******************************************************
			$('.linkFileDownload').click(function(e) {
				$('#main_toiawaseNo').val($(this).attr('toiawaseNo'));
				$('#main_fileIndex').val($(this).attr('fileIndex'));
				$('#main_buttonId').val('linkFileDownload');
				
				$('#main').prop('action', '<s:url action="inquiryEntryFileDownload" />');
				
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb_incoming_call_report_frame');
				}
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
				
				$('#main_uploadFileNm').val('');
			});
			
			//******************************************************
			// 削除（ファイル）ボタン押下時
			//******************************************************
			$('[id^="btnFileDelete"]').click(function(e) {
				$('#main_buttonId').val('btnDelete');
				
				confirmation = function() {
					return confirm(jQuery.validator.format(INF0001, "ファイル", '削除'));
				};
				
				$('#main_fileIndex').val($(this).attr('fileIndex'));
				$('#main_uploadFileNm').val($(this).attr('uploadFileNm'));
				
				$('#main').prop('action', '<s:url action="inquiryFileDelete" />');
				$('#main').prop('target', '_self');
				
				$('#main').addClass('ignore');
				$('#main').submit();
				$('#main').removeClass('ignore');
				
				$('#main_fileIndex').val('');
				$('#main_uploadFileNm').val('');
			});
			
			//**********************************************
			// 依頼者フラグ選択処理
			//**********************************************
			function fnc_select_iraisha_flg() {
				var iraishaFlgChkVal = $("input:radio[name='toiawaseInfo.iraishaFlg']:checked").val();

				if (iraishaFlgChkVal == IRAISHA_FLG_DIFFRENT) {
					// 「異なる」選択時、使用可能とする
					$('#main_toiawaseInfo_iraishaKbn').prop('disabled', false);
					$('#main_toiawaseInfo_iraishaNm').prop('disabled', false);
					$('#main_toiawaseInfo_iraishaTel').prop('disabled', false);
					$('#main_toiawaseInfo_iraishaRoomNo').prop('disabled', false);
					$('#main_toiawaseInfo_iraishaSexKbn').prop('disabled', false);
				} else {
					// それ以外の場合、使用不可とする
					$('#main_toiawaseInfo_iraishaKbn').prop('disabled', true);
					$('#main_toiawaseInfo_iraishaNm').prop('disabled', true);
					$('#main_toiawaseInfo_iraishaTel').prop('disabled', true);
					$('#main_toiawaseInfo_iraishaRoomNo').prop('disabled', true);
					$('#main_toiawaseInfo_iraishaSexKbn').prop('disabled', true);
				}
			}
			
			//**********************************************
			// 入電報告書の公開･非公開の表示
			//**********************************************
			function fnc_control_hokokusho_kokai(kokaiFlg) {
				$('#no_id_hokokusho_kokai_label').hide();
				if (kokaiFlg == '0') {
					// 未公開の場合
					$('#tb_hokokusho_kokai_check').hide();
					$('#tb_hokokusho_kokai_label').show();
				} else {
					// 公開済の場合
					$('#tb_hokokusho_kokai_check').show();
					$('#tb_hokokusho_kokai_label').hide();
				}
			}
			
			//**********************************************
			// IDなし顧客顧客時の設定
			//**********************************************
			function fnc_control_hokokusho_kokai_with_no_id_info() {
				$('#no_id_hokokusho_kokai_label').show();
				$('#tb_hokokusho_kokai_label').hide();
				$('#tb_hokokusho_kokai_check').hide();

				$('#main_toiawaseInfo_houkokuTargetFlg').prop('disabled', true);
				$('#main_toiawaseInfo_houkokuTargetFlg').prop('checked', false);
			}
			
			//**********************************************
			// 登録フォーム非活性処理
			//**********************************************
			function fnc_inputform_disabled() {
				// 全入力項目、ボタンの制御不可。
				$('#main_kokyakuInfoWithoutId_kokyakuKbn').prop('disabled', true);
				$('#main_kokyakuInfoWithoutId_kokyakuNm').prop('disabled', true);
				$('#main_kokyakuInfoWithoutId_kokyakuJusho').prop('disabled', true);
				$('#main_kokyakuInfoWithoutId_kokyakuTel').prop('disabled', true);
			}
			
			$('#noFocus').val('');
		});
		
		//******************************************************
		// 画面値復元処理
		//******************************************************
		function fnc_restore() {
			// 画面リロード時にフォーカスしないようにする
			$('#noFocus').val('1');
			
			$('#main').prop('action', '<s:url action="inquiryEntryUpdateInit" />');
			$('#main').prop('target', '_self');
			
			$('#main_actionType').val('restore');
			
			$('#main').addClass('ignore');
			$('#main').submit();
			$('#main').removeClass('ignore');
			
			$('#noFocus').val('');
		}
		
		//-->
		</script>
		
	</s:if><%-- 初期表示エラーが発生していない、かつ、削除完了でない場合はjavascript定義 end --%>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">問い合わせ登録</tiles:putAttribute>
	<tiles:putAttribute name="body">
	
	<s:if test="!isInitError() && !isDeleteCompleted()"><%-- 初期表示エラーが発生していない、かつ、削除完了でない場合は画面表示 start --%>
	
		<s:form id="main" enctype="multipart/form-data">
			
			<%-- パラメータの顧客IDが存在する場合は、顧客詳細 --%>
			<s:if test="isKokyakuIdExists()">
				<%@ include file="/WEB-INF/jsp/tb040_customer_common_info.jsp" %>
			</s:if>
			<%-- パラメータの顧客IDが存在しない場合は、ID無し顧客情報 --%>
			<s:else>
				<div id="content">
					<h2>顧客基本情報</h2>
					
					<%-- 顧客ＩＤ無し時は、共通JSPに組み込めないため、呼び出し元に同じものを定義。 --%>
					<s:if test = "isInquiryEntryDisplay() && isUpdate() && !isShimeYmExists() ">
						<s:if test = "%{#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv()}">
							<div class="right" id="divNewKokyakuId">
								<span class="font2">顧客IDを変更する→移動先顧客ID：C</span>&nbsp;
								<s:textfield name="changeKokyakuId" id="changeKokyakuId" maxlength="9" cssClass="digits required" cssStyle="width:100px;"/>
								<s:if test="toiawaseInfo.isRegistKbnToExternalCooperationData()">
									<input type="button" id="btnChangeKokyaku" value="変更" class="btnDialog" disabled="true">
								</s:if>
								<s:else>
									<input type="button" id="btnChangeKokyaku" value="変更" class="btnDialog">
								</s:else>
								<label for="main_changeKokyakuId" class="error" generated="true"></label>
								<input type="hidden" id="newKokyakuId" name="newKokyakuId" value="" />
								<input type="hidden" id="oldKokyakuId" name="oldKokyakuId" value="" />
							</div>
						</s:if>
					</s:if>
					
					<table width="90%" id="tblCustomerInfo">
						<tr>
							<th width="10%"><span class="font10">* </span>区分</th>
							<td>
								<s:select key="kokyakuInfoWithoutId.kokyakuKbn" list="kokyakuIraishaKbnList" listKey="comCd" listValue="comVal" emptyOption="true" />
							</td>
						</tr>
						<tr>
							<th><span class="font10">* </span>名称</th>
							<td>
								<s:textfield key="kokyakuInfoWithoutId.kokyakuNm" style="width:100%;" maxlength="50" cssClass="zenhankaku" />
							</td>
						</tr>
						<tr>
							<th><span class="font10">* </span>住所</th>
							<td>
								<s:textfield key="kokyakuInfoWithoutId.kokyakuJusho"  cssStyle="width:100%;" maxlength="100" cssClass="zenhankaku" />
							</td>
						</tr>
						<tr>
							<th>電話番号</th>
							<td>
								<s:textfield key="kokyakuInfoWithoutId.kokyakuTel" cssStyle="width:100%;" maxlength="50" cssClass="hankaku" />
							</td>
						</tr>
					</table>
				</div>
			</s:else>
			
			<%-- 戻る、閉じるボタン --%>
			<div align="right">
				<s:if test="isFromInquirySearch()">
					<input type="button" id="btnBack1" value="戻る" class="btnSubmit" />
				</s:if>
				<s:else>
					<input type="button" id="btnClose1" name ="btn_win_close" value=" 閉じる "class="btnSubmit">
				</s:else>
			</div>
			
			<%-- 問い合わせ --%>
			<div id="content">
				<h2>問い合わせ基本情報</h2>
				<s:if test="isUpdate() && isKokyakuIdExists()">
					<span style="float:left">コール数請求年月： ${callSeikyuYmForDisplay}　締め年月： ${shimeYmForDisplay}</span>
					<div class="right">
						最終更新：
						${f:dateFormat(toiawaseInfo.gamenUpdDt, "yyyy/MM/dd HH:mm:ss")}&nbsp;&nbsp;&nbsp;
						${f:out(lastUpdateName)}
					</div>
				</s:if>
				<table width="90%" id="tblToiawaseInfo">
					<tr>
						<th width="10%">受付番号</th>
						<td colspan="7">
							<s:if test="isInsert()">
								※受付番号は自動的に採番されます。
							</s:if>
							<s:else>
								${f:out(toiawaseInfo.toiawaseNo)}
							</s:else>
						</td>
					</tr>
					<tr>
						<th><span class="font10">* </span>依頼者</th>
						<td colspan="7">
							<table id="tblIraishaInfo">
								<tr>
									<td class="padoff">
										<label><span id="spn_rdo_iraisha_flg">
										<s:radio key="toiawaseInfo.iraishaFlg" list="iraishaFlgList" listKey="comCd" listValue="comVal" theme="receptionjs" />
										</span></label>
										（異なる場合、区分、名称、性別は必須）
									</td>
								</tr>
								<tr>
									<td class="padoff">
										<s:select key="toiawaseInfo.iraishaKbn" list="toiawaseIraishaKbnList" listKey="comCd" listValue="comVal" emptyOption="true" style="width:150px;" />
										&nbsp;
										<span class="font2">名称</span>
										<s:textfield key="toiawaseInfo.iraishaNm" cssStyle="width:150px;" maxlength="50" cssClass="zenhankaku" />
										&nbsp;
										<span class="font2">TEL</span>
										<s:textfield key="toiawaseInfo.iraishaTel" cssStyle="width:125px;" maxlength="50" cssClass="hankaku" />&nbsp;
										&nbsp;
										<span class="font2">部屋番号</span>
										<s:textfield key="toiawaseInfo.iraishaRoomNo" cssStyle="width:70px;" maxlength="10" cssClass="zenhankaku" />&nbsp;
										&nbsp;
										<span class="font2">性別</span>
										<s:select key="toiawaseInfo.iraishaSexKbn" list="iraishaSexKbnList" listKey="comCd" listValue="comVal" emptyOption="true" />&nbsp;
									</td>
								</tr>
							</table>
							<label for="main_toiawaseInfo_iraisha" class="error" generated="true"></label>
						</td>
					</tr>
					<tr>
						<th>依頼者メモ</th>
						<td colspan="7">
							<s:textarea key="toiawaseInfo.iraishaMemo" cssStyle="width:100%;" rows="2" cssClass="zenhankaku"/>
						</td>
					</tr>
					<tr>
						<th>
							<span class="font10">* </span>受付日時
						</th>
						<td colspan="7">
							<s:textfield key="toiawaseInfo.uketsukeYmd" cssClass="dateYMD" cssStyle="width:100px;" maxlength="10" placeholder="yyyymmdd" />
							&nbsp;&nbsp;
							<s:textfield key="toiawaseInfo.uketsukeJikan" cssClass="time" cssStyle="width:50px;" maxlength="5" placeholder="hhmm" />
							&nbsp;&nbsp;
							<span class="font2">受付者</span>
							<span class="font1">&nbsp;${f:out(uketsukeshaNmForDisplay)}</span>
							<label for="main_toiawaseInfo_uketsuke" class="error" generated="true"></label>
						</td>
					</tr>
					<tr>
						<th><span class="font10">* </span>区分</th>
						<td colspan="7" class="padoff">
							<table class="padoff">
								<tr>
									<td width="30%" class="padoff">
										<s:select key="toiawaseInfo.toiawaseKbn1" list="toiawaseKbn1List" listKey="kbn" listValue="kbnNm" emptyOption="true" cssStyle="width:220px" />
									</td>
									<td width="45%" class="padoff">
										<label for="main_toiawaseInfo_toiawaseKbn1" class="error" generated="true"></label>
									</td>
									<td width="25%" rowspan="2" align="right" valign="top" class="padoff">
										<input type="button" id="btnManual" class="btnSubmit" value="マニュアル参照">
									</td>
								</tr>
								<tr>
									<td class="padoff">
										<s:select key="toiawaseInfo.toiawaseKbn2" list="toiawaseKbn2List" listKey="kbn" listValue="kbnNm" emptyOption="true" cssStyle="width:220px" />
									</td>
									<td class="padoff">
										<label for="main_toiawaseInfo_toiawaseKbn2" class="error" generated="true"></label>
									</td>
									<td class="padoff">
										<br>
									</td>
								</tr>
								<tr>
									<td class="padoff">
										<s:select key="toiawaseInfo.toiawaseKbn3" list="toiawaseKbn3List" listKey="kbn" listValue="kbnNm" emptyOption="true" cssStyle="width:220px" />
									</td>
									<td class="padoff">
										<label for="main_toiawaseInfo_toiawaseKbn3" class="error" generated="true"></label>
									</td>
									<td class="padoff">
										<br>
									</td>
								</tr>
								<tr>
									<td class="padoff">
										<s:select key="toiawaseInfo.toiawaseKbn4" list="toiawaseKbn4List" listKey="kbn" listValue="kbnNm" emptyOption="true" cssStyle="width:220px" />
									</td>
									<td class="padoff">
										<label for="main_toiawaseInfo_toiawaseKbn4" class="error" generated="true"></label>
									</td>
									<td class="padoff">
										<br>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th><span class="font10">* </span>問い合わせ<br>内容</th>
						<td colspan="7">
							<table>
								<tr>
									<td class="padoff">
										<s:select key="toiawaseInfo.uketsukeKeitaiKbn" list="uketsukeKeitaiKbnList" listKey="comCd" listValue="comVal" emptyOption="true" cssStyle="width:100px" />
										<s:textfield key="toiawaseInfo.toiawaseNaiyoSimple" maxlength="100" cssStyle="width:650px;" cssClass="zenhankaku" />
										<span class="font5">(簡易)</span>
									</td>
								</tr>
								<tr>
									<td class="padoff">
										<s:textarea key="toiawaseInfo.toiawaseNaiyo" cssStyle="width:100%;" rows="4" cssClass="zenhankaku" />
										<label for="main_toiawaseInfo_toiawase" class="error" generated="true"></label>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th><span class="font10">* </span>依頼有無</th>
						<td colspan="3">
							<label>
							<span id="spn_rdo_irai_umu_kbn"><s:radio key="toiawaseInfo.iraiUmuKbn" list="iraiUmuList"  listKey="comCd" listValue="comVal" />&nbsp;</span>
							</label>
							<label for="main_toiawaseInfo_iraiUmuKbn" class="error" generated="true"></label>
						</td>
						<th>件数報告<br>対象</th>
						<td colspan="3">
							<label><s:checkbox key="toiawaseInfo.houkokuTargetFlg" fieldValue="1" value="%{isHoukokuTargetFlgChecked()}" />&nbsp;件数報告対象に含む</label>
						</td>
					</tr>
					<tr>
						<th>問い合わせ<br>情報公開</th>
						<td colspan="7">
							<s:checkbox key="toiawaseInfo.toiawaseKokaiFlg" fieldValue="1"  value="%{isToiawaseKokaiFlgChecked()}" />
							<label for="main_toiawaseInfo_toiawaseKokaiFlg">&nbsp;問い合わせ情報を公開する</label>　→　<span class="font10">チェックONで登録すると、問い合わせ情報が公開されます。</span>
						</td>
					</tr>
					<c:if test="${fn:length(uploadedFiles) > 0}">
						<s:iterator value="uploadedFiles" status="i" var="file">
						<tr>
							<s:if test="#i.count == 1">
								<th rowspan="${fn:length(uploadedFiles)}">ファイル</th>
							</s:if>
							<td colspan="7">
								<s:if test="%{#file != null}">
									<a href="#" toiawaseNo="${toiawaseNo}" fileIndex="${fileIndex}" class="linkFileDownload" title="${f:out(baseFileNm)}">${f:out(baseFileNm)}</a>
									<span style="float:right;">
										<input type="button" id="btnFileDelete${i.count}" value="削除" fileIndex="${fileIndex}" uploadFileNm="${uploadFileNm}" />
									</span>
									<%-- 要素数確保のため、非表示でファイルオブジェクトを作成 --%>
									<s:file name="toiawaseFiles[%{#i.index}]" label="File" cssStyle="display:none;" />
								</s:if>
								<s:else>
									<s:file name="toiawaseFiles[%{#i.index}]" label="File" />
								</s:else>
							</td>
						</tr>
						</s:iterator>
					</c:if>
					<s:if test="isUpdate()">
						<tr>
							<th>メール送信<br>状況</th>
							<td colspan="7">
								<span style="float:right">
						<s:if test="%{ (#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp()) && isPublishMailButtonVisible() }">
							<s:if test="mailRireki != null">
									<input type="button" id="btnSendMail" class="btnSubmit" value="公開メール送信" />
							</s:if>
							<s:else>
									<input type="button" id="btnSendMail" class="btnNotSent" value="公開メール送信" />
							</s:else>
						</s:if>
								</span>
								件名　　：${f:out(mailRireki.subject)}<br>
								送信日時：${f:dateFormat(mailRireki.updDt, "yyyy/MM/dd HH:mm:ss")}
							</td>
						</tr>

						<tr>
							<th>報告書公開</th>
							<td id="tb_hokokusho_kokai_check" colspan="3">
								<s:if test="!isBeforeToiawasePublished() && isBeforeHokokushoPublished()">
									<a href="#" class="linkReport">※入電報告書 参照</a>
								</s:if>
								<s:else>
									<a href="#" class="linkReport">※入電報告書 公開中</a>
								</s:else>
								<s:checkbox key="kokaiTyushiFlg" fieldValue="1" value="%{isStopPublishChecked()}" />公開を止める
							</td>
							<td id="tb_hokokusho_kokai_label" colspan="3">入電報告書は公開していません。(報告書印刷画面で設定します)</td>
							<td id="no_id_hokokusho_kokai_label" colspan="3">ID無し顧客の入電報告書は公開出来ません。</td>
							<th>最終履歴</th>
							<td colspan="3">${f:out(toiawaseInfo.lastRirekiForDisplay)}</td>
						</tr>
					</s:if>
					<tr>
						<th><span class="font10">* </span>サービス<br>種別</th>
						<td colspan="7">
							<s:select key="toiawaseInfo.serviceShubetsu" list="serviceShubetsuList" listKey="comCd" listValue="comVal" emptyOption="true" cssStyle="width:220px" />
						</td>
					</tr>
					
				</table>
				<span class="required_remark">
					<span class="font10">*</span> <span class="font3">常に必須</span>
					<br>
				</span>
				<s:if test="isUpdate()">
					&nbsp;
					<s:if test="%{(#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv())}">
						<s:if test="toiawaseInfo.isRegistKbnToExternalCooperationData() || isShimeYmExists()">
							&nbsp;<input type="button" class="btnSubmit" id="btnDelete" value=" 削 除 " disabled="disabled" />
						</s:if>
						<s:else>
							&nbsp;<input type="button" class="btnSubmit" id="btnDelete" value=" 削 除 " />
						</s:else>
					</s:if>
				</s:if>
				
				<span style="float:right">
					<s:if test="toiawaseInfo.isRegistKbnToExternalCooperationData()">
						&nbsp;
					</s:if>
					<s:elseif test="isNyudenHoukokuPrintButtonDisabled()">
						&nbsp;<input type="button" class="btnSubmit" id="btnPrintNyudenHoukoku" value="入電報告書印刷" disabled="disabled" />
					</s:elseif>
					<s:elseif test="isUpdate()">
						&nbsp;<input type="button" class="btnSubmit" id="btnPrintNyudenHoukoku" value="入電報告書印刷" />
					</s:elseif>
					
					<s:if test="isWorkReportButtonVisible()">
						&nbsp;<input type="button" class="btnSubmit" id="btnPrintWorkReport" value="作業報告書印刷" />
					</s:if>
					
					<s:if test="isUpdate()">
						<s:if test="%{(#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp())}">
							<s:if test="toiawaseInfo.isRegistKbnToExternalCooperationData() || isShimeYmExists()">
								&nbsp;<input type="button" class="btnSubmit" id="btnEntry" value=" 更 新 " buttonName="更新" disabled="disabled" />
							</s:if>
							<s:else>
								&nbsp;<input type="button" class="btnSubmit" id="btnEntry" value=" 更 新 " buttonName="更新" />
							</s:else>
						</s:if>
					</s:if>
					<s:else>
						<s:if test="%{(#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp())}">
							&nbsp;<input type="button" class="btnSubmit" id="btnEntry" value=" 登 録 " buttonName="登録" />
						</s:if>
					</s:else>
				</span>
			</div>
			<br>
			
			<%-- 問い合わせ履歴 --%>
			<s:if test="isUpdate()">
				<div id="content">
					<h2>問い合わせ履歴</h2>
					<s:if test = "%{ (#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv()) && !isShimeYmExists() }">
						<div align="left" id="divNewToiawaseNo">
							<span class="font2">全履歴を移動する→移動先問い合わせNo：</span>
							<s:textfield key="newToiawaseNo" maxlength="10"  style="width:120px;" cssClass="hankaku" />
							<s:if test="toiawaseInfo.isRegistKbnToExternalCooperationData()">
								<input type="button" id="btnRirekiMove" class="btnDialogWide" value="全履歴移動" disabled="true" />
							</s:if>
							<s:else>
								<input type="button" id="btnRirekiMove" class="btnDialogWide" value="全履歴移動" />
							</s:else>
							<label for="main_newToiawaseNo" class="error" generated="true"></label>
						</div>
					</s:if>
					
					<div align="right">
					<s:if test = "%{ (#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv()) && canMoveToIraiEntry()}">
						<s:if test="toiawaseInfo.isRegistKbnToExternalCooperationData()">
							<input type="button" id="btnIraiEntry" class="btnSubmit" value="依頼登録" disabled="true"/>
						</s:if>
						<s:else>
							<input type="button" id="btnIraiEntry" class="btnSubmit" value="依頼登録" />
						</s:else>
					</s:if>
						
					<s:if test = "%{ (#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp()) && !isShimeYmExists() }">
						<s:if test="toiawaseInfo.isRegistKbnToExternalCooperationData()">
							<input type="button" id="btnRirekiAdd" class="btnSubmit" value="履歴追加" disabled="true" />
						</s:if>
						<s:else>
							<input type="button" id="btnRirekiAdd" class="btnSubmit" value="履歴追加" />
						</s:else>
					</s:if>
					</div>
					
					<table width="90%">
						<tr>
							<th width="4%">&nbsp;</th>
							<th width="6%">履歴No</th>
							<th width="4%">公開</th>
							<th width="10%">日時</th>
							<th width="15%">担当者</th>
							<th width="48%">内容</th>
							<th width="9%">状況</th>
							<th width="4%">&nbsp;</th>
						</tr>
						<s:iterator status="i" value="toiawaseRirekiList">
							<tr <s:if test="!canHokokuPrint()">class="deleted"</s:if>>
								<td align="center">
									<a href="#" toiawaseRirekiNo="${toiawaseRirekiNo}" class="linkRirekiChange">
									<span class="fontlink2">変更</span></a>
								</td>
								<td align="center">${f:out(toiawaseRirekiNo)}</td>
								<td align="center">${f:out(toiawaseRirekiCheck)}</td>
								<td align="center">${f:out(uketsukeDateForDisplay)}</td>
								<td>${f:out(tantoshaNm)}</td>
								<td>${f:out(toiawaseNaiyo)}</td>
								<td>${f:out(jokyoKbnNm)}&nbsp;</td>
								<td align="center">
									<s:if test = "canMoveToIraiEntry() && isIraiExsits()">
										<a href="#" toiawaseRirekiNo="${toiawaseRirekiNo}" class="linkIraiChange">
										<span class="fontlink2">依頼</span></a>
									</s:if>
								</td>
							</tr>
						</s:iterator>
					</table>
					
					<div class="left">
						<font class="font15">■</font><span class="font3">：報告書に印字しない</span>
					</div>
					<div class="right">
						<s:if test="isFromInquirySearch()">
							<input type="button" id="btnBack2" value="戻る" class="btnSubmit" />
						</s:if>
						<s:else>
							<input type="button" id="btnClose2" name ="btn_win_close" value=" 閉じる "class="btnSubmit">
						</s:else>
					</div>
				</div>
			</s:if>
			
			<iframe src="" width="0px" height="0px" name="tb_incoming_call_report_frame" frameborder="0"></iframe>
			<iframe src="" width="0px" height="0px" name="tb_inquiry_kbn_manual_frame" frameborder="0"></iframe>
			
			<%-- パラメータ。イベントが多く、再描画などもあるため、値の変動には注意すること。 --%>
			<s:hidden key="actionType" />
			<s:hidden key="dispKbn" />
			<s:hidden key="kokyakuId" />
			<s:hidden key="toiawaseNo" />
			<s:hidden key="toiawaseUpdDt" />
			
			<%-- 検索条件引き継ぎ。 --%>
			${f:writeHidden2(condition, "condition", excludeField)}
			
			<%-- 再描画パラメータ --%>
			<s:hidden key="kokyakuInfoWithoutId.updDt" />
			<s:hidden key="kokyakuInfoWithoutId.toiawaseNo" />
			
			<s:hidden key="toiawaseInfo.toiawaseNo" />
			<s:hidden key="toiawaseInfo.uketsukeshaId" />
			<s:hidden key="toiawaseInfo.uketsukeNm" />
			<s:hidden key="toiawaseInfo.lastJokyoKbnNm" />
			<s:hidden key="toiawaseInfo.lastRirekiYmd" />
			<s:hidden key="toiawaseInfo.lastRirekiJikan" />
			<s:hidden key="toiawaseInfo.shimeYm" />
			<s:hidden key="toiawaseInfo.callSeikyuYm" />
			<s:hidden key="toiawaseInfo.gamenUpdDt" />
			<s:hidden key="toiawaseInfo.lastUpdId" />
			<s:hidden key="toiawaseInfo.lastUpdNm" />
			<s:hidden key="toiawaseInfo.lastUpdIdToNm" />
			<s:hidden key="toiawaseInfo.hokokushoKokaiFlg"/>
			<s:hidden key="toiawaseInfo.registKbn"/>
			
			<s:hidden key="iraiUmuKbn"/><%-- 画面表示時の依頼有無区分(変更前) --%>
			<s:hidden key="befereToiawaseKokaiFlg"/>
			<s:hidden key="beforeHokokushoKokaiFlg"/>
			
			<%-- submitの制御フラグ --%>
			<s:hidden key="buttonId" />
			
			<s:hidden key="targetKokyakuId" value="" />
			<s:hidden key="toiawaseRirekiNo" value="" />
			<s:hidden key="toiawaseWindowName" />

			<%-- 問い合わせ区分マニュアル --%>
			<s:hidden key="toiawaseKbn1" value="" />
			<s:hidden key="toiawaseKbn2" value="" />
			<s:hidden key="toiawaseKbn3" value="" />
			<s:hidden key="toiawaseKbn4" value="" />

			<%-- その他 --%>
			<s:hidden key="gamenKbn" value="tb023" />
			<s:hidden key="shoriKbn" />
			<s:hidden key="fileIndex" />
			<s:hidden key="uploadFileNm" />
			<s:hidden key="toiawaseFileNm" />
			<s:hidden key="toiawaseRirekiNo" />
			
			<input type="hidden" id="noFocus" name="noFocus" value="<s:property value="%{#parameters['noFocus']}" />" />
			
			<s:tokenCheck displayId="TB023" />
		</s:form>
		
		<%-- 報告書印刷画面への引数 --%>
		<s:form id="hokokusho" method="POST">
			<s:hidden key="toiawaseNo" />
			<s:hidden key="toiawaseUpdDt" />
			<s:hidden key="kokyakuId"/>
			<s:hidden key="dispKbn" value="tb023"/>
		</s:form>
		
		<%-- 依頼登録画面への引数 --%>
		<s:form id="requestEntry" method="POST">
			<s:hidden key="toiawaseNo" />
			<s:hidden key="toiawaseRirekiNo" value="" />
			<s:hidden key="dispKbn" value="tb023"/>
			<s:hidden key="rootDispKbn" value="%{dispKbn}"/>
			
			<%-- 問い合わせ検索の検索条件 --%>
			${f:writeHidden2(condition, "toiawaseCondition", excludeField)}
		</s:form>
		<br>
		<br>
	</s:if><%-- 初期表示エラーが発生していない、かつ、削除完了でない場合は画面表示 end --%>
	<s:else>
		
		<script language="JavaScript" type="text/javascript">
		<!--//
			$(function() {
				//******************************************************
				// 戻るボタン押下時
				//******************************************************
				$('#btnBack').click(function(e) {
					$('#main').prop('action', '<s:url action="inquirySearch" />');
					$('#main').prop('target', '_self');
					
					$('#main').submit();
				});
			});
		//-->
		</script>
		<%-- 初期表示エラーか、削除完了の場合、閉じるボタンのみ表示 --%>
		<br><br>
		<div align="center">
			<s:form id="main">
				<s:if test="isFromInquirySearch()">
					<input type="button" id="btnBack" value="戻る" class="btnSubmit">
				</s:if>
				<s:else>
					<input type="button" id="btnClose" name ="btn_win_close" value=" 閉じる " class="btnSubmit">
				</s:else>
				
				<%-- 検索条件引き継ぎ。 --%>
				 ${f:writeHidden2(condition, "condition", excludeField)}
				 
				 <s:tokenCheck displayId="TB023" />
			</s:form>
		</div>
		<br><br>
	</s:else>
	</tiles:putAttribute>
</tiles:insertDefinition>
