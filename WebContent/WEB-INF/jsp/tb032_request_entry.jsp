<%--*****************************************************
	依頼内容詳細・作業内容登録画面
	作成者：仲野
	作成日：2014/07/15
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/smartphone.css">

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

			window.focus();

			var validation = $("#main")
			.validate({
				ignore:
					".ignore *",
				rules: {
					"sagyoJokyo.sagyoKanryoYmd":	{required: '#main_sagyoJokyo_sagyoKanryoFlg:checked', dateYMD: true, byteVarchar: 10},
					"sagyoJokyo.sagyoKanryoJikan":	{required: '#main_sagyoJokyo_sagyoKanryoFlg:checked', time: true, byteVarchar: 5},
					"sagyoJokyo.jokyo":				{required: '#main_sagyoJokyo_sagyoKanryoFlg:checked', byteVarchar: 600},
					"sagyoJokyo.cause":				{required: '#main_sagyoJokyo_sagyoKanryoFlg:checked', byteVarchar: 600},
					"sagyoJokyo.jisshiNaiyo":		{required: '#main_sagyoJokyo_sagyoKanryoFlg:checked', byteVarchar: 600}
				},
				groups: {
					main_sagyoJokyo_sagyoKanryoYmdJikan : "sagyoJokyo.sagyoKanryoYmd sagyoJokyo.sagyoKanryoJikan"
				},
				invalidHandler: function(form, validator) {
					alert(ART0015);

					confirmation = function() {return true;};
				},
				submitHandler: function(form) {
					if (!confirmation()) {
						confirmation = function() {return true;};

						return false;
					}

					if ($('#isPrintExecute').val() != "") {
						// 作業報告書印刷時は、ボタンを使用不可にしないでsubmit
						form.submit();

						confirmation = function() {return true;};

						return false;
					}

					if ($('#main_sagyoJokyo_sagyoKanryoJikan').val() != "") {
						// コロンを取り除く
						$('#main_sagyoJokyo_sagyoKanryoJikan').val(ConvDelCoron($('#main_sagyoJokyo_sagyoKanryoJikan').val()));
					}

					$(":input").prop("disabled", false);
					$("input:button").prop("disabled", true);
					form.submit();
					$(":input").prop("disabled", true);

					confirmation = function() {return true;};
				}
			});

			$(".swipebox").swipebox({
				hideBarsDelay : 0
			});

			//******************************************************
			// 戻るボタン押下時
			//******************************************************
			$('[id^="btnBack"]').click(function(e) {
				$('#main').addClass('ignore');

				$('#isPrintExecute').val("");

				$('#main').prop('action', '<s:url action="requestSearch" />');
				$('#main').prop('target', '_self');

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
			// 閉じるボタン押下時
			//******************************************************
			$('[id^="btnClose"]').click(function(e) {
				window.close();
			});

			//******************************************************
			// 作業完了チェックボックス変更時
			//******************************************************
			$('#main_sagyoJokyo_sagyoKanryoFlg').click(function(e) {
				fnc_sagyo_kanryo_check();
			});

			//******************************************************
			// 作業報告書印刷ボタン押下時
			//******************************************************
			$('#btnPrint').click(function(e) {
				$('#main').addClass('ignore');

				$('#isPrintExecute').val("1");

				$('#main').prop('action', '<s:url action="workReportDownload" />');
				if (isIPad == true) {
					// iPadの場合は、_blankをターゲット
					$('#main').prop('target', '_blank');
				} else {
					// それ以外の場合は、iframeをターゲット
					$('#main').prop('target', 'tb_working_report_frame');
				}

				$('#main').submit();

				$('#main').removeClass('ignore');
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

					return confirm(jQuery.validator.format(INF0001, "作業内容", buttonNm));
				};

				// ファイル名取得処理
				var fileNames = "";
				var imageFilesLength = $("input[name^='imageFiles']").length;
				var itemCnt = 0;
				$("input[name^='imageFiles']").each(function(){
					fileNames = fileNames + $(this).val();
					if (itemCnt != (imageFilesLength - 1)) {
						fileNames = fileNames + ",";
					}
				});

				$("#imageFileNm").val(fileNames);

				$('#isPrintExecute').val("");

				// 作業状況登録・更新処理
				$('#main').prop('action', '<s:url action="requestEntryUpdate" />');
				$('#main').prop('target', '_self');

				$('#main').submit();
			});

			<s:if test="isEditable()">
			fnc_sagyo_kanryo_check();
			</s:if>
		});

		//**********************************************
		// 作業完了チェックボックスの状態による項目制御
		//**********************************************
		function fnc_sagyo_kanryo_check() {
			if ($('#main_sagyoJokyo_sagyoKanryoFlg').attr("checked")) {
				// チェックON時
				$('#main_sagyoJokyo_sagyoKanryoYmd').prop('disabled', false);
				$('#main_sagyoJokyo_sagyoKanryoJikan').prop('disabled', false);
				$('#main_sagyoJokyo_sagyoKanryoYmd').datepicker('enable');
			} else {
				// チェックOFF時
				$('#main_sagyoJokyo_sagyoKanryoYmd').prop('disabled', true);
				$('#main_sagyoJokyo_sagyoKanryoJikan').prop('disabled', true);
				$('#main_sagyoJokyo_sagyoKanryoYmd').datepicker('disable');
			}
		}

		//**********************************************
		// 削除ボタン押下時 実行関数
		//**********************************************
		function fnc_image_delete(uploadFileNm, fileIndex) {
			confirmation = function() {
				return confirm(jQuery.validator.format(INF0001, "画像", "削除"));
			};

			$('#main').addClass('ignore');

			$('#main_uploadFileNm').val(uploadFileNm);
			$('#main_fileIndex').val(fileIndex);

			$('#isPrintExecute').val("");

			// 画像削除処理
			$('#main').prop('action', '<s:url action="requestEntryImageDelete" />');
			$('#main').prop('target', '_self');

			$('#main').submit();

			$('#main').removeClass('ignore');
		}

		//**********************************************
		// その他ファイルリンク押下時 実行関数
		//**********************************************
		function fnc_other_file_display(uploadFileNm) {
			$('#main').addClass('ignore');

			$('#main_uploadFileNm').val(uploadFileNm);

			$('#isPrintExecute').val("1");

			$('#main').prop('action', '<s:url action="requestDetailDownload" />');
			if (isIPad == true) {
				// iPadの場合は、_blankをターゲット
				$('#main').prop('target', '_blank');
			} else {
				// それ以外の場合は、iframeをターゲット
				$('#main').prop('target', 'tb_working_report_frame');
			}

			$('#main').submit();

			$('#main').removeClass('ignore');
		}
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">${f:out(title)}</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main" enctype="multipart/form-data">
			<div class="right">
				<%-- 遷移元の画面によって、表示するボタンを変更 --%>
				<s:if test="isFromRequestSearch()">
				<%-- 依頼検索からの遷移の場合は、戻るボタンを表示 --%>
				<input type="button" id="btnBack1" class="btnSubmit" value=" 戻 る " />
				</s:if>
				<s:elseif test="isFromInquiryDetail()">
				<%-- 問い合わせ内容詳細からの遷移の場合は、閉じるボタンを表示 --%>
				<input type="button" id="btnClose1" class="btnSubmit" value=" 閉じる " />
				</s:elseif>
				<s:elseif test="isFromDirectLogin()">
				<%-- ダイレクトログインからの遷移の場合は、メニューボタンを表示 --%>
				<input type="button" id="btnMenu1" class="btnSubmit" value=" メニュー " />
				</s:elseif>
			</div>

			<s:if test="irai != null">
			<c:import url="/WEB-INF/jsp/tb040_customer_common_info.jsp" />
			</s:if>

			<div class="content">
				<s:if test="irai != null">
				<h2>問い合わせ基本情報</h2>
				<div class="grid grid-pad">
					<div class="col-2-left">受付番号</div>
					<div class="col-2-right">
						${f:out(toiawaseRireki.toiawaseNo)}-${f:out(toiawaseRireki.toiawaseRirekiNo)}
					</div>
					<br>
					<div class="col-2-left">内容</div>
					<div class="col-2-right">
						${f:br(f:out(toiawase.toiawaseNaiyo))}
					</div>
				<br>
				<br>
				<br>
				</div>
				<h2>作業依頼内容</h2>
				<div class="lastUpdatedBy">
					&nbsp;最終更新：${f:dateFormat(irai.updDt, "yyyy/MM/dd HH:mm")}
				</div>
				<div class="grid grid-pad">
					<div class="col-2-left">依頼内容</div>
					<div class="col-2-right">
						<s:if test="irai.iraiNaiyo != null && irai.iraiNaiyo != ''">
						${f:br(f:out(irai.iraiNaiyo))}
						</s:if>
						<s:else>
						&nbsp;
						</s:else>
					</div>

					<div class="col-2-left">訪問希望</div>
					<div class="col-2-right">
						<s:if test="irai.homonKiboYmd != null">
						${f:dateFormat(irai.homonKiboYmd, "yyyy/MM/dd")} ${f:out(irai.homonKiboJikanKbnNm)}
						</s:if>
						<s:else>
						&nbsp;
						</s:else>
					</div>
				</div>
				<div class="right">
					<%-- 遷移元の画面によって、表示するボタンを変更 --%>
					<s:if test="isFromRequestSearch()">
					<%-- 依頼検索からの遷移の場合は、戻るボタンを表示 --%>
					&nbsp;<input type="button" id="btnBack2" class="btnSubmit" value=" 戻 る " />
					</s:if>
					<s:elseif test="isFromInquiryDetail()">
					<%-- 問い合わせ内容詳細からの遷移の場合は、閉じるボタンを表示 --%>
					&nbsp;<input type="button" id="btnClose2" class="btnSubmit" value=" 閉じる " />
					</s:elseif>
					<s:elseif test="isFromDirectLogin()">
					<%-- ダイレクトログインからの遷移の場合は、メニューボタンを表示 --%>
					&nbsp;<input type="button" id="btnMenu2" class="btnSubmit" value=" メニュー " />
					</s:elseif>
				</div>

				<s:if test="isEditable() || (!isEditable() && sagyoJokyo != null)">
				<h2>作業状況</h2>
				<table>
					<tr>
						<td align="right" class="lastUpdatedBy">
							<s:if test="sagyoJokyo != null">
							最終更新：${f:dateFormat(sagyoJokyo.updDt, "yyyy/MM/dd HH:mm")}
							</s:if>
						</td>
					</tr>
				</table>
				<s:if test="isEditable()">
				<div class="left">
					&nbsp;<span class="font10">※画像のみ添付する場合は作業完了チェックをOFFにして下さい。</span>
				</div>
				</s:if>
				<div class="grid grid-pad">
					<%-- 登録画面の場合 --%>
					<s:if test="isEditable()">
					<div class="col-2-left"><span class="font11">* </span>作業完了<span class="required">作業完了時必須</span></div>
					<div class="col-2-right">
						<label><s:checkbox key="sagyoJokyo.sagyoKanryoFlg" fieldValue="1"  value="%{isSagyoKanryoChecked()}" />作業完了</label>
						&nbsp;&nbsp;<span class="font2">完了日 </span><s:textfield key="sagyoJokyo.sagyoKanryoYmd" cssClass="dateYMD" cssStyle="width:100px;" maxlength="10"  placeholder="yyyymmdd" />
						&nbsp;&nbsp;<span class="font2">完了時間 </span><s:textfield key="sagyoJokyo.sagyoKanryoJikan" cssClass="time" cssStyle="width:50px;" maxlength="5"  placeholder="hhmm" pattern="[0-9]*" />
						<label for="main_sagyoJokyo_sagyoKanryoYmdJikan" style="float:right;" class="error" generated="true" /></label>
					</div>
					<div class="col-2-left"><span class="font11">* </span>状況<span class="required">作業完了時必須</span></div>
					<div class="col-2-right">
						<s:textarea key="sagyoJokyo.jokyo" style="width:100%;" cssClass="zenhankaku" rows="2" />
					</div>
					<div class="col-2-left"><span class="font11">* </span>原因<span class="required">作業完了時必須</span></div>
					<div class="col-2-right">
						<s:textarea key="sagyoJokyo.cause" cssStyle="width:100%;" cssClass="zenhankaku" rows="2" />
					</div>
					<div class="col-2-left"><span class="font11">* </span>実施内容<span class="required">作業完了時必須</span></div>
					<div class="col-2-right">
						<s:textarea key="sagyoJokyo.jisshiNaiyo" cssStyle="width:100%;" cssClass="zenhankaku" rows="2" />
					</div>
					</s:if>
					<%-- 詳細画面の場合 --%>
					<s:else>
					<div class="col-2-left">作業完了</div>
					<div class="col-2-right">
						<s:textfield key="sagyoJokyo.sagyoKanryoFlgNm" cssClass="readOnlyText" readonly="true" style="width:50px;" />
						&nbsp;&nbsp;<span class="font2">完了日 </span><s:textfield key="sagyoJokyo.sagyoKanryoYmd" cssClass="readOnlyText" cssStyle="width:100px;" readonly="true" />
						&nbsp;&nbsp;<span class="font2">完了時間 </span><s:textfield key="sagyoJokyo.sagyoKanryoJikanPlusColon" cssClass="readOnlyText" cssStyle="width:50px;" readonly="true" />
					</div>
					<div class="col-2-left">状況</div>
					<div class="col-2-right">
						<s:textarea key="sagyoJokyo.jokyo" cssClass="readOnlyText" readonly="true" style="width:100%;" />
					</div>
					<div class="col-2-left">原因</div>
					<div class="col-2-right">
						<s:textarea key="sagyoJokyo.cause" cssClass="readOnlyText" readonly="true" style="width:100%;" />
					</div>
					<div class="col-2-left">実施内容</div>
					<div class="col-2-right">
						<s:textarea key="sagyoJokyo.jisshiNaiyo" cssClass="readOnlyText" readonly="true" style="width:100%;" />
					</div>
					</s:else>

					<s:iterator value="uploadedFiles" status="i" var="file">
					<s:if test="%{#file != null}">
					<div class="col-2-left">画像${f:out(i.count)}</div>
					<div class="col-2-right">
						<a href="<s:url action='requestEntryDownload' />?toiawaseNo=${toiawaseNo}&toiawaseRirekiNo=${toiawaseRirekiNo}&uploadFileNm=${uploadFileNm}&fileKbn=${fileKbnForLink}&imageToken=${imageToken}" class="swipebox" title="${f:out(baseFileNm)}" target="_blank">${f:out(baseFileNm)}</a>
						<s:if test="isEditable()">
						<span style="float:right;">
							<input type="button" id="btnImageDelte${i.count}" value="削除" onclick="fnc_image_delete('${f:out(uploadFileNm)}', '${f:out(fileIndex)}')" />
						</span>
						<%-- 要素数確保のため、非表示でファイルオブジェクトを作成 --%>
						<s:file name="imageFiles[%{#i.index}]" label="File" cssStyle="display:none;" />
						<s:if test="!#i.last">
						<br>
						<br>
						</s:if>
						</s:if>
					</div>
					</s:if>
					<s:else>
					<div class="col-2-left">画像${f:out(i.count)}</div>
					<div class="col-2-right">
						<s:if test="isEditable()">
						<s:file name="imageFiles[%{#i.index}]" label="File" />
						<s:if test="!#i.last">
						<br>
						<br>
						</s:if>
						</s:if>
						<s:else>
						&nbsp;
						</s:else>
					</div>
					</s:else>
					</s:iterator>
					<s:iterator value="otherUploadedFiles" status="i" var="otherFile">
					<div class="col-2-left">ファイル${f:out(i.count)}</div>
					<div class="col-2-right">
					<s:if test="%{#otherFile != null}">
						<a href="JavaScript:fnc_other_file_display('${f:out(uploadFileNm)}');" title="${f:out(baseFileNm)}">${f:out(baseFileNm)}</a>
					</s:if>
					<s:else>
						&nbsp;
					</s:else>
					</div>
					</s:iterator>
				</div>

				<div class="right">
				<s:if test="!isEditable() && isWorkReportPrintable()">
				&nbsp;<input type="button" id="btnPrint" class="btnSubmit" style="width: 150px;" value="作業報告書印刷">
				</s:if>
				<s:if test="isEditable() && isInsert()">
				&nbsp;<input type="button" id="btnEntry" class="btnSubmit" value=" 登 録 ">
				</s:if>
				<s:elseif test="isEditable() && isUpdate()">
				&nbsp;<input type="button" id="btnEntry" class="btnSubmit" value=" 更 新 ">
				</s:elseif>
				<%-- 遷移元の画面によって、表示するボタンを変更 --%>
				<s:if test="isFromRequestSearch()">
				<%-- 依頼検索からの遷移の場合は、戻るボタンを表示 --%>
				&nbsp;<input type="button" id="btnBack3" class="btnSubmit" value=" 戻 る " />
				</s:if>
				<s:elseif test="isFromInquiryDetail()">
				<%-- 問い合わせ内容詳細からの遷移の場合は、閉じるボタンを表示 --%>
				&nbsp;<input type="button" id="btnClose3" class="btnSubmit" value=" 閉じる " />
				</s:elseif>
				<s:elseif test="isFromDirectLogin()">
				<%-- ダイレクトログインからの遷移の場合は、メニューボタンを表示 --%>
				&nbsp;<input type="button" id="btnMenu3" class="btnSubmit" value=" メニュー " />
				</s:elseif>
				</div>
				</s:if>

				<iframe src="" width="0px" height="0px" name="tb_working_report_frame" frameborder="0"></iframe>

				</s:if>
			</div>

			<s:hidden key="actionType" />
			<s:hidden key="toiawaseNo" />
			<s:hidden key="toiawaseRirekiNo" />
			<s:hidden key="uploadFileNm" />
			<s:hidden key="dispKbn" />
			<s:hidden key="fileIndex" />

			<s:hidden key="sagyoJokyo.kakuninJokyo" />
			<s:hidden key="sagyoJokyo.kakuninshaId" />
			<s:hidden key="sagyoJokyo.kakuninDt" />

			<%-- 依頼検索の検索条件 --%>
			${f:writeHidden2(condition, "condition", excludeField)}

			<input type="hidden" id="imageFileNm" name="imageFileNm" value="" />
			<input type="hidden" id="isPrintExecute" name="isPrintExecute" value="" />
			
			<s:hidden key="gamenKbn" />

			<s:tokenCheck displayId="TB032" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
