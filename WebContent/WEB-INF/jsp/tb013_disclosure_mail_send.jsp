<%--*****************************************************
	TORES公開メール送信画面
	作成者：山田
	作成日：2015/09/08
	更新日：2015/10/22 松葉 問い合わせ履歴登録 メール送信再表示時イベント追加
			2016/07/14 H.Hirai 複数請求先対応
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
			$(function() {
				<%-- 確認ダイアログ(初期値は空) --%>
				var confirmation = function() {return true;};

				$("#main").validate({
					ignore:
						".ignore *",
					rules: {
						"subject": {required: true, byteVarchar: 100}
					},
					invalidHandler: function(form, validator) {
						alert(ART0015);
					},
					submitHandler: function(form) {
						if (!confirmation()) {
							return false;
						}
						$(":input").prop("disabled", false);
						$("input:button").prop("disabled", true);
						form.submit();
						$(":input").prop("disabled", true);
					}
				});

				// 送信先リスト初期値設定処理
				fnc_default_disclosure_mail_send_list();
				// 送信先リスト初期処理
				fnc_disclosure_mail_send_list();

				//******************************************************
				// メール送信ボタン押下時
				//******************************************************
				$('#btnSendMail').click(function(e) {
					var result;
					confirmation = function() {
						return result = confirm(jQuery.validator.format(INF0001, "メール", "送信"));
					};
					$('#main_actionType').val('mailSend');
					$('#main').prop('action', '<s:url action="disclosureMailSendSendMail" />');
					$('#main').prop('target', '_self');

					$('#main').submit();
				});
				
				//******************************************************
				// 送信先リスト変更時
				//******************************************************
				$('#main_disclosureMailSendList').change(function(e) {
					fnc_disclosure_mail_send_list();
				});
				
				<%-- 完了メッセージがある場合、親画面をリロード --%>
				<s:if test="hasActionMessages()">
					if(window.opener && !window.opener.closed) {
						// 親ウィンドウが存在すれば、親ウィンドウのリロード処理
						window.opener.fnc_restore();
					}
				</s:if>
				
				<%-- リロード時を配慮し、最後にフォーカスする --%>
				window.focus();
			});

		//******************************************************
		// 送信先リスト変更時処理
		//******************************************************
		function fnc_disclosure_mail_send_list() {

			// 選択行情報を取得
			var kokyakuCd = $('#main_disclosureMailSendList').val();

			// 顧客情報が存在しない場合、終了
			if (kokyakuCd == null) {
				return;
			}

			// 送信先メールアドレス、BCC、本文エリア全体を非表示
			$(".sendMailToSection").css("display","none");
			$(".sendMailBccSection").css("display","none");
			$(".mailTextSection").css("display","none");

			// 選択行に紐付く送信先メールアドレス、BCC、本文を表示
			$('[id^="sendMailToSection_' + kokyakuCd + '"]').css("display","");
			$('[id^="sendMailBccSection_' + kokyakuCd + '"]').css("display","");
			$('[id^="mailTextSection_' + kokyakuCd + '"]').css("display","");

			// 選択行に紐付く請求先顧客ＩＤ、送信先メールアドレスを設定
			var seikyusakiKokyakuId = $('[name^="sentakuSeikyusakiKokyakuId_' + kokyakuCd + '"]').val();
			var taioHokokuMailAddress = $('[name^="sentakuTaioHokokuMailAddress_' + kokyakuCd + '"]').val();
			$('#main_seikyusakiKokyakuId').val(seikyusakiKokyakuId);
			$('#main_taioMailAddress').val(taioHokokuMailAddress);

			// 再表示用の初期値を設定
			$('#main_selectKokyakuId').val(kokyakuCd);
		}

		//******************************************************
		// 送信先リスト初期値設定処理
		//******************************************************
		function fnc_default_disclosure_mail_send_list() {

			var selectKokyakuId = $('#main_selectKokyakuId').val();
			if (selectKokyakuId != "") {
				// 初期値が設定されている場合、リストにその値を設定
				$('#main_disclosureMailSendList').val(selectKokyakuId);
			}
		}
		//-->
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="windowTitle">公開メール送信</tiles:putAttribute>
	<tiles:putAttribute name="title">公開メール送信</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div class="content">
			<span class="font2">&nbsp;&nbsp;公開情報をメール送信します。<br>&nbsp;&nbsp;運用フローに応じて件名を修正し、「メール送信」ボタンをクリックして下さい。</span><br><br>&nbsp;
			<s:form id="main" method="POST">
			<h2>メール送信先</h2>
			<table  width="100%">
				<tr>
					<th width="10%">送信先</th>
					<td>
						<s:if test="isSendMailDisplay()">
							<s:select key="disclosureMailSendList" list="disclosureMailSendList" listKey="seikyusakiKokyakuId"
								listValue="seikyusakiKokyakuNm" emptyOption="false" cssClass="textIMEDisabled"/>
						</s:if>
					</td>
				</tr>
			</table>
			<br>
			<br>
			<h2>メール送信内容</h2>
				<table width="100%">
					<tr>
						<th width="10%">件名</th>
						<td>
							<s:textfield key="subject" cssStyle="width:100%;" cssClass="zenhankaku" maxlength="100" />
						</td>
					</tr>
					<tr>
						<th>送信元<br>メールアドレス</th>
						<td>${f:out(senderMailAddress)}</td>
					</tr>
					<s:if test="isSendMailDisplay()">
						<s:iterator status="i" value="disclosureMailSendList">
							<tr class="sendMailToSection" id="sendMailToSection_${f:out(seikyusakiKokyakuId)}">
								<th>送信先<br>メールアドレス</th>
								<td>${f:out(taioMailAddress)}</td>
							</tr>
							<tr class="sendMailBccSection" id="sendMailBccSection_${f:out(seikyusakiKokyakuId)}">
								<th>送信先<br>メールアドレス<br>(bcc)</th>
								<td>${f:out(bccMailAddress)}</td>
							</tr>
							<tr class="mailTextSection" id="mailTextSection_${f:out(seikyusakiKokyakuId)}">
								<th>本文</th>
								<td>
									${f:br(mailText)}
								</td>
							</tr>
						</s:iterator>
					</s:if>
					<s:else>
						<tr>
							<th>送信先<br>メールアドレス</th>
							<td></td>
						</tr>
						<tr>
							<th>送信先<br>メールアドレス<br>(bcc)</th>
							<td></td>
						</tr>
						<tr>
							<th>本文</th>
							<td></td>
						</tr>
					</s:else>
				</table>
				<div align="right">
					<s:if test="(actionType==null || actionType=='') && hasActionErrors()">
					&nbsp;<input type="button" id="btnSendMail" value="メール送信" class="btnSubmit" disabled>&nbsp;
					</s:if>
					<s:else>
					&nbsp;<input type="button" id="btnSendMail" value="メール送信" class="btnSubmit">&nbsp;
					</s:else>
					&nbsp;<input type="button" id="btnClose" value=" 閉じる " class="btnSubmit">
				</div>

				<s:hidden key="toiawaseNo" />
				<s:hidden key="toiawaseRirekiNo" />
				<s:hidden key="toiawaseUpdDt" />
				<s:hidden key="shoriKbn" />
				<s:hidden key="seikyusakiKokyakuId" />
				<s:hidden key="taioMailAddress" />
				<s:hidden key="kaishaId" />
				<s:hidden key="actionType" />
				<%-- 再描画時に必要な送信先情報リスト --%>
				<s:iterator begin="1" end="%{disclosureMailSendListSize}" step="1" status="i">
					<s:hidden key="disclosureMailSendList[%{#i.index}].seikyusakiKokyakuId" />
					<s:hidden key="disclosureMailSendList[%{#i.index}].seikyusakiKokyakuNm" />
					<s:hidden key="disclosureMailSendList[%{#i.index}].taioMailAddress" />
				</s:iterator>
				<s:hidden key="disclosureMailSendListSize" />
				<s:hidden key="selectKokyakuId" />

				<%-- 請求先顧客情報hidden値 --%>
				<s:iterator status="i" value="disclosureMailSendList">
					<input type="hidden" name="sentakuSeikyusakiKokyakuId_${f:out(seikyusakiKokyakuId)}"
						value="${f:out(seikyusakiKokyakuId)}"/>
					<input type="hidden" name="sentakuTaioHokokuMailAddress_${f:out(seikyusakiKokyakuId)}"
						value="${f:out(taioMailAddress)}"/>
				</s:iterator>
			</s:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>