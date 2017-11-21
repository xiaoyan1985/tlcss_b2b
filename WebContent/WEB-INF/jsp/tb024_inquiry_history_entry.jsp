<%--*****************************************************
	問い合わせ履歴登録画面
	作成者：小林
	作成日：2015/08/28
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
	<s:if test="!isInitError() && !isDeleteCompleted()"><%-- 初期表示エラーが発生していない、かつ、削除完了でない場合はjavascript定義 start --%>
		<script language="JavaScript" type="text/javascript">
		<!--//
		
		<%-- 確認ダイアログ(初期値は空) --%>
		var confirmation = function() {return true;};

		$(function() {
			
			<%-- 初期表示前に受付時間をcommon.jsのblur処理を呼び出してコロンで整形 --%>
			$("#main_toiawaseRirekiInfo_uketsukeJikan").val(ConvCoron(ZenToHan_Num($("#main_toiawaseRirekiInfo_uketsukeJikan").val())));
			
			<%-- tb040_customer_common_info.jsp内の項目は、フィールド自体にチェック属性を定義してあるので注意すること。 --%>
			var validation = $("#main").validate({
				ignore:
					".ignore *",
					rules: {
						
						"toiawaseRirekiInfo.uketsukeYmd"			: {required: true, dateYMD: true},
						"toiawaseRirekiInfo.uketsukeJikan"			: {required: true, time: true, minlength: 4, byteVarchar: 5},
						"toiawaseRirekiInfo.tantoshaNm"				: {required: true, byteVarchar: 40},
						"toiawaseRirekiInfo.tantoshaNmToTantoshaId"	: {required: true},
						
						"toiawaseRirekiInfo.toiawaseKbn1"			: {required: function() { return ($('#main_toiawaseRirekiInfo_toiawaseKbn2').val() != ''
																							   || $('#main_toiawaseRirekiInfo_toiawaseKbn3').val() != ''
																							   || $('#main_toiawaseRirekiInfo_toiawaseKbn4').val() != '');}},
																							   
						"toiawaseRirekiInfo.toiawaseKbn2"			: {required: function() { return ($('#main_toiawaseRirekiInfo_toiawaseKbn3').val() != ''
																							   || $('#main_toiawaseRirekiInfo_toiawaseKbn4').val() != '');}},
																							   
						"toiawaseRirekiInfo.toiawaseKbn3"			: {required: function() { return ($('#main_toiawaseRirekiInfo_toiawaseKbn4').val() != '');}},
						
						"toiawaseRirekiInfo.toiawaseNaiyo"			: {required: true, byteVarchar: 600},
						"toiawaseRirekiInfo.jokyoKbn"				: {required: true}
					},
					messages: {
						"toiawaseRirekiInfo.toiawaseKbn1"			: {required: ART0033},
						"toiawaseRirekiInfo.toiawaseKbn2"			: {required: ART0033},
						"toiawaseRirekiInfo.toiawaseKbn3"			: {required: ART0033}
					},
					groups: {
						main_toiawaseRirekiInfo_toiawaseKbn1		: "toiawaseRirekiInfo\.toiawaseKbn1",
						main_toiawaseRirekiInfo_toiawaseKbn2		: "toiawaseRirekiInfo\.toiawaseKbn2",
						main_toiawaseRirekiInfo_toiawaseKbn3		: "toiawaseRirekiInfo\.toiawaseKbn3",
						main_toiawaseRirekiInfo_toiawase			: "toiawaseRirekiInfo\.toiawaseNaiyoSimple toiawaseRirekiInfo\.toiawaseNaiyo",
						main_toiawaseRirekiInfo_jokyo				: "toiawaseRirekiInfo\.jokyoKbn"
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
						$('#main_toiawaseRirekiInfo_uketsukeJikan').val(ConvDelCoron($('#main_toiawaseRirekiInfo_uketsukeJikan').val()));
						// 戻る、登録（更新）、削除
						if (buttonId == 'btnBack'
							|| buttonId == 'btnEntry'
							|| buttonId == 'btnDelete') {
							
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
				// 戻るボタン押下時
				//******************************************************
				$('[id^="btnBack"]').click(function(e) {
					
					$('#main_buttonId').val('btnBack');
					
					$('#main').prop('action', '<s:url action="inquiryEntryUpdateInit" />');
					$('#main').prop('target', '_self');
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
				});
				
				//******************************************************
				// 区分１～４変更時
				//******************************************************
				$('select[id^=main_toiawaseRireki_toiawaseKbn]').change(function(e) {
					
					// 下位入力チェックのメッセージが残ってしまう可能性があるため、フォーカスを移動し、チェック
					// 対処不要な区分もあるが、一括で実施してしまう
					$('select[id^=main_toiawaseRireki_toiawaseKbn]').focus();
					
					// 発生元へフォーカスを戻す
					$('[id=' + e.target.id + ']').focus();
				});

				//******************************************************
				// マニュアル参照ボタン押下時
				//******************************************************
				$('#btnManual').click(function(e) {
					
					$('#main_buttonId').val('btnManual');
					$('#main_toiawaseKbn1').val($('#main_toiawaseRirekiInfo_toiawaseKbn1').val());
					$('#main_toiawaseKbn2').val($('#main_toiawaseRirekiInfo_toiawaseKbn2').val());
					$('#main_toiawaseKbn3').val($('#main_toiawaseRirekiInfo_toiawaseKbn3').val());
					$('#main_toiawaseKbn4').val($('#main_toiawaseRirekiInfo_toiawaseKbn4').val());
					
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
				// 登録・更新ボタン押下時
				//******************************************************
				$('#btnEntry').click(function(e) {
					
					$('#main_buttonId').val('btnEntry');
	
					// 入力チェック制御
					$("#divNewKokyakuId").addClass("ignore");
					
					var buttonName = $(this).attr('buttonName');
					confirmation = function() {
						return confirm(jQuery.validator.format(INF0001, "問い合わせ履歴情報", buttonName));
					};
					
					$('#main').prop('action', '<s:url action="inquiryHistoryEntryUpdate" />');
					$('#main').prop('target', '_self');
					
					$('#main').submit();
				});

				//******************************************************
				// 削除ボタン押下時
				//******************************************************
				$('#btnDelete').click(function(e) {
					
					$('#main_buttonId').val('btnDelete');
					
					$('#main_actionType').val('delete');
					
					confirmation = function() {
						return confirm(jQuery.validator.format(INF0001, "問い合わせ履歴情報", '削除'));
					};
					
					$('#main').prop('action', '<s:url action="inquiryHistoryEntryUpdate" />');
					$('#main').prop('target', '_self');
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
				});
				
				//******************************************************
				// 【問い合わせ】公開メールボタン押下時
				//******************************************************
				$('#btnPublishInfoSendMail').click(function(e) {
					
					$('#main_buttonId').val('btnPublishInfoSendMail');
					
					var w = createWindow("tb_disclosure_mail_send_win");
					$('#main').prop('action', '<s:url action="disclosureMailSendInit" />');
					$('#main').prop('target', w.name);
					
					$('#main').addClass('ignore');
					$('#main').submit();
					$('#main').removeClass('ignore');
				});

				//**********************************************
				// IDなし顧客顧客時の設定
				//**********************************************
				function fnc_control_toiawase_rireki_hokoku_print_flg() {
					$('#main_toiawaseRireki_houkokuPrintFlg').prop('disabled', true);
					$('#main_toiawaseRireki_houkokuPrintFlg').prop('checked', false);
				}
				
				$('#noFocus').val('');
		});
		
		//******************************************************
		// 画面値復元処理
		//******************************************************
		function fnc_restore() {
			// 画面リロード時にフォーカスしないようにする
			$('#noFocus').val('1');
			
			$('#main').prop('action', '<s:url action="inquiryHistoryEntryUpdateInit" />');
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

	<tiles:putAttribute name="title">問い合わせ履歴登録</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<s:if test="!isInitError() && !isDeleteCompleted()"><%-- 初期表示エラーが発生していない、かつ、削除完了でない場合は画面表示 start --%>
	
		<s:form id="main">
			<div class="right">
				<input type="button" id="btnBack1" value=" 戻 る " class="btnSubmit" />
			</div>

			<%-- パラメータの顧客IDが存在する場合は、顧客詳細 --%>
			<s:if test="isKokyakuIdExists()">
				<%@ include file="/WEB-INF/jsp/tb040_customer_common_info.jsp" %>
			</s:if>
			<%-- パラメータの顧客IDが存在しない場合は、ID無し顧客情報 --%>
			<s:else>
				<div class="content">
					<h2>顧客基本情報</h2>
					<table width="90%">
						<tr>
							<th width="10%">区分</th>
							<td>
								${f:out(kokyakuInfoWithoutId.kokyakuKbnNm)}
							</td>
						</tr>
						<tr>
							<th>名称</th>
							<td>
								${f:out(kokyakuInfoWithoutId.kokyakuNm)}
							</td>
						</tr>
						<tr>
							<th>住所</th>
							<td>
								${f:out(kokyakuInfoWithoutId.kokyakuJusho)}
							</td>
						</tr>
						<tr>
							<th>電話番号</th>
							<td>
								${f:out(kokyakuInfoWithoutId.kokyakuTel)}
							</td>
						</tr>
					</table>
				</div>
			</s:else>
			
			<div class="content">
				<h2>問い合わせ基本情報</h2>
				<div class="right">
					最終更新：
					${f:dateFormat(toiawaseInfo.gamenUpdDt, "yyyy/MM/dd HH:mm:ss")}&nbsp;&nbsp;&nbsp;
					<s:if test = "lastUpdateName != null">
						${f:out(lastUpdateName)}
					</s:if>
					<s:else>
						${f:out(toiawaseInfo.lastUpdIdToNm)}
					</s:else>
				</div>
				<table width="90%">
					<tr>
						<th width="10%">受付番号</th>
						<td colspan="7">
							${f:out(toiawaseInfo.toiawaseNo)}
						</td>
					<tr>
						<th>依頼者</th>
						<td colspan="5">
							${f:out(toiawaseInfo.iraishaNm)}
						</td>
					</tr>
					<tr>
						<th width="10%"><span id="id_uketsuke_dt">受付日</span></th>
						<td width="20%">${f:dateFormat(toiawaseInfo.uketsukeYmd, "yyyy/MM/dd")}</td>
						<th width= "8%">受付時間</th>
						<td width="20%">${f:out(toiawaseInfo.uketsukeJikan)}</td>
						<th width= "8%">受付者</th>
						<td width="34%"><span class="font1">${f:out(toiawaseInfo.uketsukeNm)}</span></td>
					</tr>
					<tr>
						<th>区分</th>
						<td colspan="4">
							${f:out(toiawaseInfo.toiawaseKbn1Nm)}
							&nbsp;
							${f:out(toiawaseInfo.toiawaseKbn2Nm)}
							&nbsp;
							${f:out(toiawaseInfo.toiawaseKbn3Nm)}
							&nbsp;
							${f:out(toiawaseInfo.toiawaseKbn4Nm)}
						</td>
						<td align="right"><br></td>
					</tr>
					<tr>
						<th>問い合わせ<br>内容</th>
						<td colspan="5">
							${f:out(toiawaseInfo.uketsukeKeitaiKbnNm)}
							<br>${f:out(toiawaseInfo.toiawaseNaiyo)}</td>
					</tr>
					<tr>
						<th>依頼有無</th>
						<td colspan="3">
							${f:out(toiawaseInfo.iraiUmuKbnNm)}
						</td>
						<th>最終履歴</th>
						<td>
							${f:out(toiawaseInfo.lastRirekiForDisplay)}
						</td>
					</tr>
				</table>
				<br>
			</div>
			
			<div class="content">
				<h2>問い合わせ履歴</h2>
				<s:if test = "isInsert()">
					※履歴Noは自動的に採番されます。
				</s:if>
				<s:else>
					【受付番号　No.${f:out(toiawaseNo)}-${f:out(toiawaseRirekiNo)}】
					<span style="float:right">
						最終更新：
						${f:dateFormat(toiawaseRirekiInfo.updDt, "yyyy/MM/dd HH:mm:ss")}
						&nbsp;
						<s:if test = "toiawaseRirekiInfo.lastUpdNm != null">
							${f:out(toiawaseRirekiInfo.lastUpdNm)}
						</s:if>
						<s:else>
							${f:out(toiawaseRirekiInfo.lastUpdIdToNm)}
						</s:else>
					</span>
				</s:else>
				<table class="tblBorderOff" width="90%">
					<tr id="tr_toiawase_rireki">
						<th width="10%">
							<span class="font10">* </span>
							<span>日時</span>
						</th>
						<td>
							<s:textfield key="toiawaseRirekiInfo.uketsukeYmd" cssClass="dateYMD" cssStyle="width:100px;" maxlength="10" placeholder="yyyymmdd" />
							&nbsp;&nbsp;
							<s:textfield key="toiawaseRirekiInfo.uketsukeJikan" cssClass="time" cssStyle="width:50px;" maxlength="5" placeholder="hhmm" />
							&nbsp;&nbsp;
							<span class="font2">担当者</span>&nbsp;
							<s:if test="toiawaseRirekiInfo.isRegistKbnToTlcss()">
								<s:textfield key="toiawaseRirekiInfo.tantoshaNm" cssClass="readOnlyText" readonly="true" maxlength="40" style="width:280px;" />
							</s:if>
							<s:else>
								<s:textfield key="toiawaseRirekiInfo.tantoshaNm" maxlength="40" style="width:280px;" cssClass="zenhankaku" />
								<label for="main_toiawaseRirekiInfo_uketsukeJikan" class="error" generated="true"></label>
								<label for="main_toiawaseRirekiInfo_uketsuke" class="error" generated="true"></label>
							</s:else>
						</td>
					</tr>
					<tr>
						<th>区分</th>
						<td>
							<table>
								<tr>
									<td width="30%">
										<s:select key="toiawaseRirekiInfo.toiawaseKbn1" list="toiawaseKbn1List" listKey="kbn" listValue="kbnNm" emptyOption="true" cssStyle="width:220px" />
									</td>
									<td width="45%">
										<label for="main_toiawaseRireki_toiawaseKbn1" class="error" generated="true"></label>
									</td>
									<td rowspan="2" rowspan="2" align="right" valign="top">
										<input type="button" id="btnManual" class="btnSubmit" value="マニュアル参照" />
									</td>
								</tr>
								<tr>
									<td>
										<s:select key="toiawaseRirekiInfo.toiawaseKbn2" list="toiawaseKbn2List" listKey="kbn" listValue="kbnNm" emptyOption="true" cssStyle="width:220px" />
									</td>
									<td>
										<label for="toiawaseRirekiInfo.toiawaseKbn2" class="error" generated="true"></label>
									</td>
									<td>
										<br>
									</td>
								</tr>
								<tr>
									<td>
										<s:select key="toiawaseRirekiInfo.toiawaseKbn3" list="toiawaseKbn3List" listKey="kbn" listValue="kbnNm" emptyOption="true" cssStyle="width:220px" />
									</td>
									<td>
										<label for="main_toiawaseInfo_toiawaseKbn3" class="error" generated="true"></label>
									</td>
									<td>
										<br>
									</td>
								</tr>
								<tr>
									<td>
										<s:select key="toiawaseRirekiInfo.toiawaseKbn4" list="toiawaseKbn4List" listKey="kbn" listValue="kbnNm" emptyOption="true" cssStyle="width:220px" />
									</td>
									<td>
										<br>
									</td>
									<td>
										<br>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th>
							<span class="font10">* </span>
							<span>内容</span>
						</th>
						<td>
							<s:textarea key="toiawaseRirekiInfo.toiawaseNaiyo" cssStyle="width:100%;" rows="4" cssClass="zenhankaku" />
							<label for="main_toiawaseRireki_toiawase" class="error" generated="true"></label>
						</td>
					</tr>
					<tr>
						<th><span class="font10">* </span>状況</th>
						<td>
							<s:select key="toiawaseRirekiInfo.jokyoKbn" list="jokyoKbnList" listKey="jokyoKbn" listValue="jokyoKbnNm" emptyOption="true" cssStyle="width:220px" />
							&nbsp;&nbsp;&nbsp;
							<s:if test="toiawaseInfo.kokyakuId == null || toiawaseInfo.kokyakuId == ''">
								<label>
									<s:checkbox key="toiawaseRirekiInfo.houkokuPrintFlg" disabled="true"  />
									報告書に印字する
								</label>
							</s:if>
							<s:else>
								<label>
									<s:checkbox key="toiawaseRirekiInfo.houkokuPrintFlg" fieldValue="1" value="%{isHoukokuPrintFlgChecked()}" />
									報告書に印字する
								</label>
							</s:else>
						</td>
					</tr>
					<tr>
						<th>問い合わせ<br>履歴公開</th>
						<td>
							<s:checkbox key="toiawaseRirekiInfo.toiawaseRirekiKokaiFlg" fieldValue="1" value="%{isToiawaseRirekiKokaiFlgChecked()}" />
							&nbsp;問い合わせ履歴情報を公開する　→　<span class="font10">チェックONで登録すると、問い合わせ履歴が公開されます。</span>
						</td>
					</tr>
					<s:if test="isUpdate()">
						<tr>
							<th>メール送信<br>状況</th>
							<td colspan="7">
								<span style="float:right">
						<s:if test="%{ (#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp()) && isPublishMailButtonVisible() }">
							<s:if test="mailRireki != null">
									<input type="button" id="btnPublishInfoSendMail" class="btnSubmit" value="公開メール送信" />
							</s:if>
							<s:else>
									<input type="button" id="btnPublishInfoSendMail" class="btnNotSent" value="公開メール送信" />
							</s:else>
						</s:if>
								</span>
								件名　　：${f:out(mailRireki.subject)}<br>
								送信日時：${f:dateFormat(mailRireki.updDt, "yyyy/MM/dd HH:mm:ss")}
							</td>
						</tr>
					</s:if>
				</table>
				<span class="required_remark">
					<span class="font10">*</span> <span class="font3">常に必須</span><br>
				</span>
				&nbsp;
				<s:if test="isDeleteButtonVisible()">
					<s:if test="toiawaseRirekiInfo.isRegistKbnToExternalCooperationData() || isShimeYmExists()">
						&nbsp;<input type="button" class="btnSubmit" id="btnDelete" value=" 削 除 " disabled="disabled" />
					</s:if>
					<s:else>
						&nbsp;<input type="button" class="btnSubmit" id="btnDelete" value=" 削 除 " />
					</s:else>
				</s:if>
				<span style="float:right">
					&nbsp;
					<s:if test="isUpdate()">
						<s:if test = "%{(#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp())}">
							<s:if test="toiawaseRirekiInfo.isRegistKbnToExternalCooperationData() || isShimeYmExists()">
								&nbsp;<input type="button" class="btnSubmit" id="btnEntry" value=" 更 新 " buttonName="更新" disabled="disabled" />
							</s:if>
							<s:else>
								&nbsp;<input type="button" class="btnSubmit" id="btnEntry" value=" 更 新 " buttonName="更新" />
							</s:else>
						</s:if>
					</s:if>
					<s:else>
						<s:if test = "%{(#session.userContext.isAdministrativeInhouse() || #session.userContext.isOutsourcerSv() || #session.userContext.isOutsourcerOp())}">
							<s:if test="isShimeYmExists()">
								&nbsp;<input type="button" class="btnSubmit" id="btnEntry" value=" 登 録 " buttonName="登録" disabled="disabled" />
							</s:if>
							<s:else>
								&nbsp;<input type="button" class="btnSubmit" id="btnEntry" value=" 登 録 " buttonName="登録" />
							</s:else>
							
						</s:if>
					</s:else>
					&nbsp;<input type="button" id="btnBack2" value=" 戻 る " class="btnSubmit" />
				</span>
			</div>

			<iframe src="" width="0px" height="0px" name="tb_inquiry_kbn_manual_frame" frameborder="0"></iframe>

			<%-- パラメータ。イベントが多く、再描画などもあるため、値の変動には注意すること。 --%>
			<s:hidden key="actionType" />
			<s:hidden key="dispKbn" />
			<s:hidden key="kokyakuId" />
			<s:hidden key="toiawaseNo" />
			<s:hidden key="toiawaseRirekiNo" />
			<s:hidden key="toiawaseUpdDt" />
			
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
			<s:hidden key="toiawaseInfo.gamenUpdDt" />
			<s:hidden key="toiawaseInfo.lastUpdId" />
			<s:hidden key="toiawaseInfo.lastUpdNm" />
			<s:hidden key="toiawaseInfo.lastUpdIdToNm" />
			<s:hidden key="toiawaseInfo.hokokushoKokaiFlg"/>
			<s:hidden key="toiawaseInfo.registKbn"/>
			
			<s:hidden key="toiawaseRirekiInfo.toiawaseNo"/>
			<s:hidden key="toiawaseRirekiInfo.toiawaseRirekiNo"/>
			<s:hidden key="toiawaseRirekiInfo.updDt"/>
			<s:hidden key="toiawaseRirekiInfo.lastUpdNm"/>
			<s:hidden key="toiawaseRirekiInfo.lastUpdIdToNm"/>
			<s:hidden key="toiawaseRirekiInfo.registKbn"/>
			<s:if test="!toiawaseInfo.isRegistKbnToTlcss()">
				<s:hidden key="toiawaseRirekiInfo.tantoshaId"/>
			</s:if>
			<%-- submitの制御フラグ --%>
			<s:hidden key="buttonId" />
			
			<s:hidden key="targetKokyakuId" value="" />
			<s:hidden key="toiawaseWindowName" />
			
			<%-- 検索条件引き継ぎ。 --%>
			${f:writeHidden2(condition, "condition", excludeField)}

			<%-- 問い合わせ区分マニュアル --%>
			<s:hidden key="toiawaseKbn1" value="" />
			<s:hidden key="toiawaseKbn2" value="" />
			<s:hidden key="toiawaseKbn3" value="" />
			<s:hidden key="toiawaseKbn4" value="" />
			
			<%-- その他 --%>
			<s:hidden key="gamenKbn" value="tb024" />
			<s:hidden key="shoriKbn" value="2" />
			<s:hidden key="beforeToiawaseRirekiKokaiFlg"/>
			
			<input type="hidden" id="noFocus" name="noFocus" value="<s:property value="%{#parameters['noFocus']}" />" />
			
			<s:tokenCheck displayId="TB024" />
		</s:form>
		
		</s:if><%-- 初期表示エラーが発生していない、かつ、削除完了でない場合は画面表示 end --%>
		<s:else>
			<script language="JavaScript" type="text/javascript">
			<!--//
				$(function() {
					//******************************************************
					// 戻るボタン押下時
					//******************************************************
					$('#btnBack').click(function(e) {
						$('#main').prop('action', '<s:url action="inquiryEntryUpdateInit" />');
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
					 
					 <s:tokenCheck displayId="TB024" />
					 
					 <s:hidden key="toiawaseNo" />
					 <s:hidden key="dispKbn" />
				</s:form>
			</div>
			<br><br>
		</s:else>
	</tiles:putAttribute>
</tiles:insertDefinition>