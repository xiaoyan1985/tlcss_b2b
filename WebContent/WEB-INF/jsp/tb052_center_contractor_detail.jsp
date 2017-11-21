<%--*****************************************************
	センター業者詳細画面
	作成者：小林
	作成日：2015/10/08
	更新日：
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
			$(function() {
				window.focus();

				//*******************************************
				// 戻るボタン押下時
				//*******************************************
				$('[id^="btnBack"]').click(function(e) {

					$('#main').prop('action', '<s:url action="centerContractorSearch" />');
					$('#main').prop('target', '_self');

					$('#main').submit();
				});
			});
		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">センター業者詳細</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:if test="!isInitError()"><%-- 初期表示エラーが発生していない場合は画面表示 start --%>
			<s:form id="main">
				<div class ="content">
					<div class="right">
						<input type="button" class="btnSubmit" id="btnBack1" value=" 戻 る ">
					</div>
	
					<h2>センター業者情報</h2>
					<table width="100%" align="center" <s:if test="gyosha.isDeleted()">class="tblDetailFormDel"</s:if>>
						<tr height="25">
							<th width="11%" colspan="2">業者コード</th>
							<td>
								&nbsp;${f:out(gyosha.gyoshaCd)}
								<s:if test="gyosha.isDeleted()">
									<span class="font12" style="float:right">※削除済</span>
								</s:if>
							</td>
						</tr>
						<tr>
							<th colspan="2">業者名</th>
							<td>&nbsp;${f:out(gyosha.gyoshaNm)}</td>
						</tr>
						<tr>
							<th colspan="2">担当者</th>
							<td>
								<table width="100%">
									<tr>
										<td width="30%">
											<span class="font2">&nbsp;部署：</span>&nbsp;${f:out(gyosha.tantoBusho)}
										</td>
										<td width="30%">
											<span class="font2">担当者：</span>&nbsp;${f:out(gyosha.tantoshaNm)}
										</td>
										<td width="40%">
											<span class="font2">役職：</span>&nbsp;${f:out(gyosha.tantoshaYakushoku)}
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th colspan="2">郵便番号</th>
							<td>
								&nbsp;${f:formatYubinNo(gyosha.yubinNo)}
							</td>
						</tr>
						<tr>
							<th colspan="2">住所</th>
							<td>
								&nbsp;${f:out(gyosha.fullJusho)}
							</td>
						</tr>
						<tr>
							<th colspan="2">事務用TEL</th>
							<td>
								<table width="100%">
									<tr>
										<td width="30%">
											<span class="font2">&nbsp;TEL：</span>&nbsp;${f:out(gyosha.jimuTel)}
										</td>
										<td width="70%">
											<span class="font2">メールアドレス：</span>&nbsp;${f:out(gyosha.jimuMailAddress)}
										</td>
									</tr>
									<tr>
										<td  colspan="2" width="100%">
											<span class="font2">&nbsp;FAX：</span>&nbsp;${f:out(gyosha.jimuFax)}
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th colspan="2">作業用TEL</th>
							<td>
								<table width="100%">
									<tr>
										<td width="30%">
											<span class="font2">&nbsp;TEL：</span>&nbsp;${f:out(gyosha.sagyoTel)}
										</td>
										<td width="70%">
											<span class="font2">メールアドレス：</span>&nbsp;${f:out(gyosha.sagyoMailAddress)}
										</td>
									</tr>
									<tr>
										<td  colspan="2" width="100%">
											<span class="font2">&nbsp;FAX：</span>&nbsp;${f:out(gyosha.sagyoFax)}
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th colspan="2">
								作業依頼メール
								<br>自動送付
							</th>
							<td>
								&nbsp;${f:out(gyosha.iraiMailAtesakiFlgNm)}
								<s:if test="gyosha.isToSendAutomatically(gyosha.iraiMailAtesakiFlg)">
									&nbsp;→&nbsp;自動送付宛先メールアドレス&nbsp;
									<s:if test="gyosha.isSameSagyoTel(gyosha.iraiMailAtesakiKbn)">
										${f:out(gyosha.iraiMailAtesakiKbnNm)}
									</s:if>
									<s:else>
										${f:out(gyosha.iraiMailAtesakiAddress)}
									</s:else>
								</s:if>
							</td>
						</tr>
						<tr>
							<th colspan="2">営業時間</th>
							<td>
								<table width="100%">
									<tr>
										<td width="30%">
											<span class="font2">&nbsp;営業時間：&nbsp;</span> ${f:out(gyosha.eigyoJikanForDisplay)}
										</td>
										<td width="70%">
											<span class="font2">特記事項：</span>&nbsp;${f:out(gyosha.eigyoJikanBiko)}
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th colspan="2">営業日</th>
							<td>
								<table width="100%">
									<tr>
										<td width="30%">
											<span class="font2">&nbsp;営業日：</span>&nbsp;
											<s:if test="gyosha.isWorkMonday()">
												月&nbsp;
											</s:if>
											<s:if test="gyosha.isWorkTuesday()">
												火&nbsp;
											</s:if>
											<s:if test="gyosha.isWorkWednesday()">
												水&nbsp;
											</s:if>
											<s:if test="gyosha.isWorkThursday()">
												木&nbsp;
											</s:if>
											<s:if test="gyosha.isWorkFriday()">
												金&nbsp;
											</s:if>
											<s:if test="gyosha.isWorkSaturday()">
												土&nbsp;
											</s:if>
											<s:if test="gyosha.isWorkSunday()">
												日&nbsp;
											</s:if>
										</td>
										<td width="70%">
											<span class="font2">特記事項：</span>&nbsp;${f:out(gyosha.eigyobiBiko)}
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th colspan="2">業者メモ</th>
							<td>
								&nbsp;${f:out(gyosha.gyoshaMemo)}
							</td>
						</tr>
						<tr>
							<th rowspan="2">対応可能</th>
							<th>エリア</th>
							<td>
								<table width="100%">
									<s:iterator status="i" value="todofukenAreaList">
										<tr height="20">
											<td width="10%">
												&nbsp;${f:out(todofukenAreaList[i.index].comVal)}
											</td>
											<td width="90%">
												&nbsp;：&nbsp;${f:out(todofukenAreaMap[todofukenAreaList[i.index].comCd])}
											</td>
										</tr>
									</s:iterator>
								</table>
							</td>
						</tr>
						<tr>
							<th>メモ</th>
							<td>
								&nbsp;${f:out(gyosha.todofukenBiko)}
							</td>
						</tr>
						<tr>
							<th colspan="2">業種</th>
							<td>
								<s:if test="gyosha.gyoshuNm != null">
									&nbsp;${f:out(gyosha.gyoshuNm)}
								</s:if>
								<s:if test="gyosha.gyoshuMemo != null">
									&nbsp;${f:out(gyosha.gyoshuMemo)}
								</s:if>
							</td>
						</tr>
						<tr>
							<th colspan="2">削除・復活理由</th>
							<td>
								&nbsp;${f:out(gyosha.reasonMemo)}
							</td>
						</tr>
					</table>
					<div class="right">
						<input type="button" class="btnSubmit" id="btnBack2" value=" 戻 る ">
					</div>
				</div>
	
				<%-- 業者検索の検索条件 --%>
				 ${f:writeHidden2(condition, "condition", excludeField)}
				<%-- パラメータ値 --%>
				<s:hidden key="gyoshaCd" />
				<s:hidden key="dispKbn" />
				<s:hidden key="gyoshaCdResultNm" />
				<s:hidden key="gyoshaNmResultNm" />
	
				<s:tokenCheck displayId="TB052" />
			</s:form>
		</s:if><%-- 初期表示エラーが発生していない場合は画面表示 end --%>
		<s:else>
			<%-- 初期表示エラーの場合、戻るボタンのみ表示 --%>
		<br><br>
		<div align="center">
			<s:form id="main">
				<input type="button" class="btnSubmit" id="btnBack2" value=" 戻 る ">
				
				<%-- 業者検索の検索条件 --%>
				 ${f:writeHidden2(condition, "condition", excludeField)}
				<%-- パラメータ値 --%>
				<s:hidden key="gyoshaCd" />
				<s:hidden key="dispKbn" />
				<s:hidden key="gyoshaCdResultNm" />
				<s:hidden key="gyoshaNmResultNm" />
				 
				 <s:tokenCheck displayId="TB052" />
			</s:form>
		</div>
		<br><br>
	</s:else>
	</tiles:putAttribute>
</tiles:insertDefinition>