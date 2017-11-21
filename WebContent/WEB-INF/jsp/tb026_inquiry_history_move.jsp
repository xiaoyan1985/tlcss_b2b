<%--*****************************************************
	問い合わせ履歴移動
	作成者：松葉
	作成日：2015/08/11
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
			$(function() {

				<s:if test="hasActionMessages()">
					// 正常終了の場合
					fnc_reload_toiawase_window();
				</s:if>

				$("#main").validate({
					ignore: ".ignore *"
				});

				//******************************************************
				// 移動ボタン押下時
				//******************************************************
				$('#btnMove').click(function(e) {
					if (!confirm(jQuery.validator.format(INF0001, "問い合わせ履歴", "移動"))) {
						return false;
					}

					$('#main').prop('action', '<s:url action="inquiryHistoryMoveUpdate" />');
					$('#main').prop('target', '_self');

					$('#main').submit();
				});

				//**********************************************
				// 問い合わせ参照リンク押下時
				//**********************************************
				$('.toiawaseEntry').click(function(e) {
					$('#main').addClass('ignore');

					// 値を退避
					var orgToiawaseNo = $('#main_toiawaseNo').val();
					var orgToiawaseUpdDt = $('#main_toiawaseUpdDt').val();

					// 問い合わせ番号の取得
					var toiawaseNo = $(this).attr('toiawaseNo');
					$('#main_toiawaseNo').val(toiawaseNo);

					// 問い合わせ更新日の取得
					var toiawaseUpdDt = $(this).attr('toiawaseUpdDt');
					$('#main_toiawaseUpdDt').val(toiawaseUpdDt);

					// 遷移元画面区分の設定
					$('#main_dispKbn').val("tb026");

					var date = new Date();		// 経過時間を退避
					var time = date.getTime();	// 通算ミリ秒計算
					var win_name = "tb_inquiry_entry_win" + time;

					// 新しいウィンドウで開く処理
					var w = createWindow(win_name);
					$('#main').prop('action', '<s:url action="inquiryEntryUpdateInit" />');
					$('#main').prop('target', win_name);

					$('#main_toiawaseWindowName').val(win_name);
					

					$('#main').submit();

					// 値を元に戻す
					$('#main_toiawaseNo').val(orgToiawaseNo);
					$('#main_toiawaseUpdDt').val(orgToiawaseUpdDt);

					$('#main').removeClass('ignore');
				});

				//**********************************************
				// 問い合わせ画面更新処理
				//**********************************************
				function fnc_reload_toiawase_window() {
					var targetWindowName = $('#main_toiawaseWindowName').val();
					var w = createWindow(targetWindowName);
					$('#main').prop('action', '<s:url action="inquiryEntryUpdateInit" />');
					$('#main').prop('target', targetWindowName);

					$('#main_toiawaseNo').val($('#main_newToiawaseNo').val());
					$('#main').submit();
					window.close();
				}
			});
		//-->
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="windowTitle">問い合わせ履歴移動確認</tiles:putAttribute>
	<tiles:putAttribute name="title">問い合わせ履歴移動確認</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div class="content">
			<s:form id="main" method="POST">
				<s:if test="hasActionMessages() || isInitError()">
					<%-- 正常終了、または、初期処理エラーの場合 --%>
					<table width="967px" class="borderoff">
						<tr>
							<td align="right" class="borderoff">
								&nbsp;<input type="button" id="btnClose" value=" 閉じる " class="btnSubmit" />&nbsp;
							</td>
						</tr>
					</table>
				</s:if>
				<s:else>
					<br>
					<h2>問い合わせ履歴移動確認</h2>
					<br>
					<table>
						<tr>
							<th width="10%"><br></th>
							<th width="10%">受付番号</th>
							<th width="10%">顧客ID</th>
							<th width="18%">名称</th>
							<th width="36%">問い合わせ内容</th>
							<th width="16%">&nbsp;</th>
						</tr>
						<%-- 変更前情報 ここから --%>
						<tr class="odd">
							<th>変更前</th>
							<td align="center">${f:out(toiawaseNo)}</td>
							<td align="center">${f:out(oldToiawaseInfo.kokyakuId)}</td>
							<s:if test="isKokyakuWithNoIdByBefore()">
								<td>&nbsp;${f:out(oldKokyakuWithNoIdInfo.kokyakuNm)}</td>
							</s:if>
							<s:else>
								<td>&nbsp;${f:out(oldKokyakuInfo.kanjiNm)}</td>
							</s:else>
							<td>&nbsp;${f:out(oldToiawaseInfo.toiawaseNaiyo)}</td>
							<s:if test="isKokyakuWithNoIdByBefore()">
								<td>
									<br>
								</td>
							</s:if>
							<s:else>
								<td align="center">
									<a href="#" toiawaseNo="${f:out(oldToiawaseInfo.toiawaseNo)}" toiawaseUpdDt="${f:dateFormat(oldToiawaseInfo.updDt, "yyyy/MM/dd HH:mm:ss")}" class="toiawaseEntry">問い合わせ参照</a>
								</td>
							</s:else>
						</tr>
						<%-- 変更前情報 ここまで --%>
					</table>

					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="font10">▼</span><br>&nbsp;

					<table>
						<%-- 変更後情報 ここから --%>
						<tr class="odd">
							<th width="10%">変更後</th>
							<td width="10%" align="center">${f:out(newToiawaseInfo.toiawaseNo)}</td>
							<td width="10%" align="center">${f:out(newToiawaseInfo.kokyakuId)}</td>
							<s:if test="isKokyakuWithNoIdByAfter()">
								<td width="18%">&nbsp;${f:out(newKokyakuWithNoIdInfo.kokyakuNm)}</td>
							</s:if>
							<s:else>
								<td width="18%">&nbsp;${f:out(newKokyakuInfo.kanjiNm)}</td>
							</s:else>
							<td width="36%">&nbsp;${f:out(newToiawaseInfo.toiawaseNaiyo)}</td>
							<td width="16%" align="center">
								<a href="#" toiawaseNo="${f:out(newToiawaseInfo.toiawaseNo)}" toiawaseUpdDt="${f:dateFormat(newToiawaseInfo.updDt, "yyyy/MM/dd HH:mm:ss")}" class="toiawaseEntry">問い合わせ参照</a>
							</td>
						</tr>
						<%-- 変更後情報 ここまで --%>
					</table>
					<br>
					<br>
					<span class="font10">上記に従って問い合わせ履歴を移動します。よろしい場合は「移動」ボタンをクリックして下さい。</span>
					<br>
					<div align="right">
						<input type="button" id="btnMove" class="btnSubmit" value=" 移 動 " />&nbsp;
						&nbsp;<input type="button" id="btnClose" class="btnSubmit" value=" 閉じる " />&nbsp;
					</div>
					<br>
				</s:else>

				<%-- 画面パラメータ --%>
				<s:hidden key="toiawaseNo" />
				<s:hidden key="toiawaseUpdDt" />
				<s:hidden key="dispKbn" />
				<s:hidden key="newToiawaseNo" />
				<s:hidden key="kokyakuId" />
				<s:hidden key="toiawaseWindowName" />
				<s:hidden key="completeMessageId" />
				<s:hidden key="completeMessageStr" />

				<%-- 処理用データ --%>
				<s:hidden key="oldToiawaseInfo.toiawaseNo" />
				<s:hidden key="oldToiawaseInfo.kokyakuId" />
				<s:hidden key="oldToiawaseInfo.toiawaseNaiyo" />
				<s:hidden key="oldToiawaseInfo.updDt" />
				<s:hidden key="newToiawaseInfo.toiawaseNo" />
				<s:hidden key="newToiawaseInfo.kokyakuId" />
				<s:hidden key="newToiawaseInfo.toiawaseNaiyo" />
				<s:hidden key="newToiawaseInfo.updDt" />
				<s:hidden key="oldKokyakuInfo.kokyakuId" />
				<s:hidden key="oldKokyakuInfo.kanjiNm1" />
				<s:hidden key="oldKokyakuInfo.kanjiNm2" />
				<s:hidden key="newKokyakuInfo.kokyakuId" />
				<s:hidden key="newKokyakuInfo.kanjiNm1" />
				<s:hidden key="newKokyakuInfo.kanjiNm2" />
				<s:hidden key="oldKokyakuInfoWithoutId.toiawaseNo" />
				<s:hidden key="oldKokyakuInfoWithoutId.kokyakuNm" />
				<s:hidden key="oldKokyakuInfoWithoutId.updDt" />
				<s:hidden key="newKokyakuInfoWithoutId.toiawaseNo" />
				<s:hidden key="newKokyakuInfoWithoutId.kokyakuNm" />
				<s:hidden key="newKokyakuInfoWithoutId.updDt" />

				<%-- 検索条件引き継ぎ --%>
				${f:writeHidden2(condition, "condition", excludeField)}
			</s:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>