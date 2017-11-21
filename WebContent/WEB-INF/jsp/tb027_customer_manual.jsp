<%--*****************************************************
	顧客マニュアル一覧画面
	作成者：松葉
	作成日：2015/08/05
	更新日：2016/07/07 H.Hirai 複数請求先対応
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
			$(function() {

				fnc_lst_common_manual();

				//**********************************************
				// リンク先ファイル表示処理
				//**********************************************
				$('.linkManual').click(function(e) {
					$('#main').addClass('ignore');

					$('#main_targetKokyakuId').val($(this).attr('targetKokyakuId'));
					$('#main_uploadFileNm').val($(this).attr('uploadFileNm'));

					$('#main').prop('action', '<s:url action="customerManualDownload" />');
					if (isIPad == true) {
						// iPadの場合は、_blankをターゲット
						$('#main').prop('target', '_blank');
					} else {
						// それ以外の場合は、iframeをターゲット
						$('#main').prop('target', 'tb_customer_manual_frame');
					}

					$('#main').submit();

					$('#main').removeClass('ignore');
				});

				//******************************************************
				// 共通マニュアルリスト変更時
				//******************************************************
				$('#main_seikyusakiKokyakuList').change(function(e) {
					fnc_lst_common_manual();
				});
			});

		//******************************************************
		// 共通マニュアルリスト変更時処理
		//******************************************************
		function fnc_lst_common_manual() {

			// 選択行情報を取得
			var kokyakuCd = $('#main_seikyusakiKokyakuList').val();

			// 顧客情報が存在しない場合、終了
			if (kokyakuCd == null) {
				return;
			}

			// 共通マニュアル情報表示エリア全体を非表示
			$(".commonManualSection").css("display","none");

			// 選択行に紐付く共通マニュアル情報を表示
			$('[id^="commonManualSection_' + kokyakuCd + '"]').css("display","");
		}

		//-->
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="windowTitle">顧客マニュアル一覧</tiles:putAttribute>
	<tiles:putAttribute name="title">顧客マニュアル一覧</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div class="content">
			<s:form id="main" method="POST">
				<h2>個別マニュアル</h2>
				<table>
					<tr>
						<th width="10%">顧客</th>
						<td>${f:out(kokyaku.kokyakuIdWithKanjiNm)}</td>
					</tr>
				</table>
				<table>
					<s:iterator status="i" var="kobetsuManual" value="kobetsuManualList">
						<tr>
							<s:if test="#i.index == 0">
								<th width="10%" rowspan="${f:out(kokyakuManualListSize)}">マニュアル</th>
							</s:if>
							<td width="90%">
								<a href="#" targetKokyakuId="${kokyaku.kokyakuId}" uploadFileNm="${uploadFileNm}" class="linkManual">${f:out(baseFileNm)}</a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<s:if test="isCommonManualDisplay()">
					<div class="content">
						<h2>共通マニュアル</h2>
						<table>
							<tr>
								<th width="10%">顧客</th>
								<td width="90%">
									<s:select key="seikyusakiKokyakuList" list="seikyusakiKokyakuList" listKey="kokyakuId" listValue="kokyakuIdWithKanjiNm"
										emptyOption="false" cssClass="textIMEDisabled" />
								</td>
							</tr>
						</table>
						<table>
							<s:iterator status="j" value="commonManualDtoList">
								<tr class="commonManualSection" id="commonManualSection_${f:out(kokyakuId)}">
									<td class="padoff">
										<table class="padoff" width="100%">
											<s:iterator status="i" value="commonManualList">
												<tr>
													<s:if test="#i.index == 0">
														<th width="10%" rowspan="${f:out(kokyakuManualListSize)}">マニュアル</th>
													</s:if>
													<td width="90%">
														<a href="#" targetKokyakuId="${seikyusakiKokyaku.kokyakuId}" uploadFileNm="${uploadFileNm}" class="linkManual">${f:out(baseFileNm)}</a>
													</td>
												</tr>
											</s:iterator>
										</table>
									</td>
								</tr>
							</s:iterator>
						</table>
					</div>
				</s:if>

				<iframe src="" width="0px" height="0px" name="tb_customer_manual_frame" frameborder="0"></iframe>

				<s:hidden key="kokyakuId" />
				<s:hidden key="dispKbn" />
				<s:hidden key="uploadFileNm" />
				<s:hidden key="targetKokyakuId" />

				<br>
				<div class="right">
					&nbsp;<input type="button" id="btnClose" class="btnSubmit" value="閉じる" />
				</div>
			</s:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>