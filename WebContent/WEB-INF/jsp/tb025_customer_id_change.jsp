<%--*****************************************************
	顧客ID変更確認
	作成者：山村
	作成日：2015/08/19
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

				//******************************************************
				// 変更ボタン押下時
				//******************************************************
				$('#btnChange').click(function(e) {
					// 確認メッセージの表示
					if (!confirm(jQuery.validator.format(INF0001, "顧客ID", "変更"))) {
						return false;
					}

					$('#main').prop('action', '<s:url action="customerIdChangeUpdate" />');
					$('#main').prop('target', '_self');
					$('#main').submit();
				});
				
				//**********************************************
				// 契約情報参照リンク押下時
				//**********************************************
				$('.showKeiyakuInfo').click(function(e) {
					// 顧客IDの設定
					$('#main_keiyakuInfo_kokyakuId').val($(this).attr('kokyakuId'));

					var date = new Date();		// 経過時間を退避
					var time = date.getTime();	// 通算ミリ秒計算
					var win_name = "tb_contract_info_win_" + time;

					var w = createWindow(win_name);

					$('#keiyakuInfo').prop('action', '<s:url action="contractInfoInit" />');
					$('#keiyakuInfo').prop('target', w.name);
					$('#keiyakuInfo').submit();
				});
				
				//**********************************************
				// 問い合わせ画面更新処理
				//**********************************************
				function fnc_reload_toiawase_window() {
					var targetWindowName = $('#main_toiawaseWindowName').val();
					var w = createWindow(targetWindowName);
					$('#main').prop('action', '<s:url action="inquiryEntryUpdateInit" />');
					$('#main').prop('target', targetWindowName);
					$('#main').submit();
					window.close();
				}
			});
		//-->
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="windowTitle">顧客ID変更確認</tiles:putAttribute>
	<tiles:putAttribute name="title">顧客ID変更確認</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div class="content">
			<s:form id="main" method="POST">
			<s:if test="hasActionMessages() || isInitError()">
				<%-- 正常終了、または、初期処理エラーの場合 --%>
				<table width="967px">
					<tr>
						<td align="right" >
							&nbsp;<input type="button" id="btnClose" value=" 閉じる " class="btnSubmit" />&nbsp;
						</td>
					</tr>
				</table>
			</s:if>
			<s:else>
				<br>
				<h2>顧客ID変更確認</h2>
				<br>
				<table>
					<tr>
						<th width="10%"><br></th>
						<th width="12%">顧客ID</th>
						<th width="20%">顧客区分</th>
						<th width="42%">名称</th>
						<th width="16%"><br></th>
					</tr>
					<tr class="odd">
						<th>変更前</th>
						<td align="center">${f:out(oldKokyakuId)}</td>
					<s:if test="isOldKokyakuIdExsits()">
						<td>${f:out(oldKokyakuInfo.kokyakuKbnNm)}</td>
						<td>${f:out(oldKokyakuInfo.kanjiNm)}</td>
					</s:if>
					<s:else>
						<td>${f:out(oldKokyakuInfoWithoutId.kokyakuKbnNm)}</td>
						<td>${f:out(oldKokyakuInfoWithoutId.kokyakuNm)}</td>
					</s:else>
						<td align="center">
						<s:if test="isOldKokyakuIdExsits()">
							<a href="#" kokyakuId="${oldKokyakuId}" class="showKeiyakuInfo">契約情報参照</a>
						</s:if>
						</td>
					</tr>
				</table>
				<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="font10">▼</span><br>&nbsp;
				<table>
					<tr class="odd">
						<th width="10%">変更後</th>
						<td width="12%" align="center">${f:out(newKokyakuId)}</td>
						<td width="20%">${f:out(newKokyakuInfo.kokyakuKbnNm)}</td>
						<td width="42%">${f:out(newKokyakuInfo.kanjiNm)}</td>
						<td width="16%" align="center"><a href="#" kokyakuId="${newKokyakuId}" class="showKeiyakuInfo">契約情報参照</a></td>
					</tr>
				</table>
				<br>
				<br>
				<span class="font10">上記に従って顧客IDを変更します。よろしい場合は「変更」ボタンをクリックして下さい。</span>
				<br>
				<div align="right" >
					<input type="button" id="btnChange" value=" 変 更 " class="btnSubmit" />&nbsp;
					&nbsp;<input type="button" id="btnClose" value=" 閉じる " class="btnSubmit" />&nbsp;
				</div>
			</s:else>

				<%-- 画面パラメータ --%>
				<s:hidden key="toiawaseNo" />
				<s:hidden key="toiawaseUpdDt" />
				<s:hidden key="kokyakuId" />
				<s:hidden key="oldKokyakuId" />
				<s:hidden key="newKokyakuId" />
				<s:hidden key="toiawaseWindowName" />
				<s:hidden key="dispKbn" />
				<s:hidden key="completeMessageId" />
				<s:hidden key="completeMessageStr" />

				<%-- 処理用データ --%>
				<s:hidden key="oldKokyakuInfo.kokyakuId" />
				<s:hidden key="oldKokyakuInfo.kokyakuKbn" />
				<s:hidden key="oldKokyakuInfo.kanjiNm1" />
				<s:hidden key="oldKokyakuInfo.kanjiNm2" />
				<s:hidden key="newKokyakuInfo.kokyakuId" />
				<s:hidden key="newKokyakuInfo.kokyakuKbn" />
				<s:hidden key="newKokyakuInfo.kanjiNm1" />
				<s:hidden key="newKokyakuInfo.kanjiNm2" />
				<s:hidden key="oldKokyakuInfoWithoutId.toiawaseNo" />
				<s:hidden key="oldKokyakuInfoWithoutId.kokyakuKbn" />
				<s:hidden key="oldKokyakuInfoWithoutId.kokyakuNm" />
				<s:hidden key="oldKokyakuInfoWithoutId.updDt" />
				
				<%-- 検索条件引き継ぎ。 --%>
				${f:writeHidden2(condition, "condition", excludeField)}

			</s:form>
			<s:form id="keiyakuInfo" method="POST">
				<s:hidden key="gamenKbn" value="tb046" />
				<s:hidden key="kokyakuId" />
			</s:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>