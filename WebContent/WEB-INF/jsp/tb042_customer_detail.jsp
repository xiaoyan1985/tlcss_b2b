<%--*****************************************************
	物件・入居者詳細画面
	作成者：増田成
	作成日：2014/06/06
	更新日：2015/11/04 J.Matsuba 物件情報の項目追加
	更新日：2016/04/07 J.Matsuba 地上階・地下階表示対応、キーボックス欄の表示追加
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

<tiles:insertDefinition name="default.layout">
	<tiles:putAttribute name="script">
		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			$('#btnReturn').click(function(e) {
				fnc_back();
			});
			$('#btnReturn2').click(function(e) {
				fnc_back();
			});
		});

		//*******************************************
		// 戻るボタン押下時 実行関数
		//*******************************************
		function fnc_back() {
			$('#main').prop('action', '<s:url action="customerSearch" />');
			$('#main').prop('target', '_self');

			$('#main').submit();
		}

		//-->
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="title">${f:out(titileNm)}詳細</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<s:form id="main">
			<div class="right">
				<input type="button" id="btnReturn" class="btnSubmit" value=" 戻 る " />
			</div>
			<%-- 基本情報 ここから ※共通部（「顧客区分 3:物件、4:入居者・個人」の場合） --%>
			<s:if test="kokyakuEntity.isKokyakuKbnBukken() || kokyakuEntity.isKokyakuKbnNyukyosha()" >
			<c:import url="/WEB-INF/jsp/tb040_customer_common_info.jsp" />
			</s:if>
			<%-- 基本情報 ここまで --%>
			
			<div class="content">
				<%-- 物件情報 ここから （「顧客区分 3:物件」の場合） --%>
				<s:if test="kokyakuEntity.isKokyakuKbnBukken()" >
				<h2>物件情報</h2>
				<table>
					<tr>
						<th width="10%">構造</th>
						<td width="23%">${f:out(kokyakuBukkenEntity.kozo)}</td>
						<th width="10%">階数</th>
						<td width="23%">
							<s:if test="!isEmptyKaisu()">
								${f:out(kokyakuBukkenEntity.kaisuForDisplay)}
							</s:if>
							<s:if test="isEmptyChijoKaisu()">
								<s:if test="!isEmptyChikaKaisu()">
									（地下&nbsp;${f:out(kokyakuBukkenEntity.chikaKaisu)}階）
								</s:if>
							</s:if>
							<s:else>
								<s:if test="isEmptyChikaKaisu()">
									（地上&nbsp;${f:out(kokyakuBukkenEntity.chijoKaisu)}階）
								</s:if>
								<s:else>
									（地上&nbsp;${f:out(kokyakuBukkenEntity.chijoKaisu)}階&nbsp;&nbsp;地下&nbsp;${f:out(kokyakuBukkenEntity.chikaKaisu)}階）
								</s:else>
							</s:else>
						</td>
						<th width="10%">戸数</th>
						<td width="12%">${f:out(kokyakuBukkenEntity.kosuForDisplay)}</td>
					</tr>
					<tr>
						<th>築年月</th>
						<td>${f:out(kokyakuBukkenEntity.chikuNengetsuDisplay)}</td>
						<th>備考</th>
						<td colspan="3">${f:out(kokyakuBukkenEntity.kanriKeitaiBiko)}</td>
					</tr>
					<th>物件担当</th>
					<td colspan="3">
						<table>
							<tr>
								<td width="60%">
									<span class="font2">担当者１：</span>${f:out(kokyakuBukkenEntity.tantoshaNm)}
								</td>
								<td>
									<span class="font2">連絡先１：</span>${f:out(kokyakuBukkenEntity.renrakusaki)}
								</td>
							</tr>
							<tr>
								<td>
									<span class="font2">担当者２：</span>${f:out(kokyakuBukkenEntity.tantoshaNm2)}
								</td>
								<td>
									<span class="font2">連絡先２：</span>${f:out(kokyakuBukkenEntity.renrakusaki2)}
								</td>
							</tr>
						</table>
					</td>
					<tr>
						<th>ポンプ室</th>
						<td colspan="3">${f:out(kokyakuBukkenEntity.pompMemo)}</td>
					</tr>
					<tr>
						<th>オートロック</th>
						<td colspan="3">${f:out(kokyakuBukkenEntity.autoLockMemo)}</td>
					</tr>
					<tr>
						<th>メールＢＯＸ</th>
						<td colspan="3">${f:out(kokyakuBukkenEntity.mailBox)}</td>
					</tr>
					<tr>
						<th>キーボックス</th>
						<td colspan="3">
							<table>
								<tr>
									<td>
										<span class="font2">暗証番号：</span>${f:out(kokyakuBukkenEntity.keyboxPassword)}
									</td>
								</tr>
								<tr>
									<td>
										<span class="font2">場所：</span>${f:out(kokyakuBukkenEntity.keyboxPlace)}
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th>共用部管理</th>
						<td colspan="3">
							<table>
								<tr>
									<td width="50%">
										<span class="font2">名称：</span>${f:out(kokyakuBukkenEntity.kyoyoKanriNm)}
									</td>
									<td width="25%">
										<span class="font2">営業日：</span>${f:out(kokyakuBukkenEntity.kyoyoKanriEigyobi)}
									</td>
									<td>
										<span class="font2">営業時間：</span>${f:out(kokyakuBukkenEntity.kyoyoKanriEigyoJikan)}
									</td>
								</tr>
								<tr>
									<td>
										<span class="font2">TEL：</span>${f:out(kokyakuBukkenEntity.kyoyoKanriTel)}
									</td>
									<td colspan="2">
										<span class="font2">時間外TEL：</span>${f:out(kokyakuBukkenEntity.kyoyoKanriJikangaiTel)}
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<span class="font2">メールアドレス：</span>${f:out(kokyakuBukkenEntity.kyoyoKanriMailAddress)}
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<span class="font2">備考：</span>${f:out(kokyakuBukkenEntity.kyoyoKanriBiko)}
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th>管理人</th>
						<td colspan="3">
							<table>
								<tr>
									<td width="50%">
										<span class="font2">名称：</span>${f:out(kokyakuBukkenEntity.kanrininNm)}
									</td>
									<td width="25%">
										<span class="font2">営業日：</span>${f:out(kokyakuBukkenEntity.kanrininEigyobi)}
									</td>
									<td>
										<span class="font2">営業時間：</span>${f:out(kokyakuBukkenEntity.kanrininEigyoJikan)}
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<span class="font2">TEL：</span>${f:out(kokyakuBukkenEntity.kanrininTel)}
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<span class="font2">メールアドレス：</span>${f:out(kokyakuBukkenEntity.kanrininMailAddress)}
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<span class="font2">備考：</span>${f:out(kokyakuBukkenEntity.kanrininBiko)}
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th>オーナー情報</th>
						<td colspan="3">
							<table>
								<tr>
									<td width="50%">
										<span class="font2">名称：</span>${f:out(kokyakuBukkenEntity.ooyaNm)}
									</td>
									<td width="50%">
										<span class="font2">TEL：</span>${f:out(kokyakuBukkenEntity.ooyaTel)}
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<span class="font2">住所：</span>${f:out(kokyakuBukkenEntity.ooyaJusho)}
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<span class="font2">備考：</span>${f:out(kokyakuBukkenEntity.ooyaBiko)}
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<s:if test="!isEmptyBukkenShiteiGyoshaTableList()">
				<br>
				<h2>【指定業者情報】</h2>

				<table class="sharp">
					<s:iterator value="bukkenShiteiGyoshaTableList" status="i">
						<s:if test="#i.index == 0">
						<tr>
							<th width="12%" rowspan="2">業種</th>
							<th width="25%">名称</th>
							<th width="10%">TEL</th>
							<th width="" rowspan="2">備考</th>
						</tr>
						<tr>
							<th width="25%">メールアドレス</th>
							<th width="10%">FAX</th>
						</tr>
						</s:if>

						<tr>
							<td rowspan="2">${f:out(gyoshuNm)}</td>
							<td>${f:out(name)}</td>
							<td>${f:out(telNo)}</td>
							<td rowspan="2">${f:out(biko)}</td>
						</tr>
						<tr>
							<td>${f:out(mailAddress)}</td>
							<td>${f:out(faxNo)}</td>
						</tr>
					</s:iterator>
				</table>
				</s:if>
				</s:if>
				<%-- 物件情報 ここまで --%>
				<%-- 入居者・個人情報 ここから （「顧客区分 4:入居者・個人」の場合） --%>
				<s:if test="kokyakuEntity.isKokyakuKbnNyukyosha()" >
				<h2>入居者・個人情報</h2>
				<table>
					<tr>
						<th width="10%">入居日</th>
						<td width="40%">${f:out(kokyakuKojinEntity.nyukyoDt)}</td>
						<th width="10%">退居日</th>
						<td width="40%">${f:out(kokyakuKojinEntity.taikyoDt)}</td>
					</tr>
					<tr>
						<th>緊急連絡先</th>
						<td colspan="3">${f:out(kokyakuKojinEntity.kinkyuRenrakusaki)}</td>
					</tr>
				</table>
				</s:if>
				<%-- 入居者・個人情報 ここまで --%>
				<div class="right">
					<input type="button" id="btnReturn2" class="btnSubmit" value=" 戻 る " />
				</div>
			</div>

			<%-- 問い合わせ検索の検索条件 --%>
			${f:writeHidden2(condition, "condition", excludeField)}
			<%-- 検索画面用hidden値 --%>
			<s:hidden key="kokyakuId" />
			
			<s:hidden key="viewSelectLinkFlg" />
			<s:hidden key="kokyakuIdResultNm" value="%{kokyakuIdResultNm}" />
			<s:hidden key="kokyakuKaishaNmResultNm" value="%{kokyakuKaishaNmResultNm}" />
			<s:hidden key="kokyakuNmResultNm" value="%{kokyakuNmResultNm}" />
			<s:hidden key="kokyakuJushoResultNm" value="%{kokyakuJushoResultNm}" />
			<s:hidden key="kokyakuTelResultNm" value="%{kokyakuTelResultNm}" />
			<s:hidden key="kokyakuFaxResultNm" value="%{kokyakuFaxResultNm}" />

			<s:tokenCheck displayId="TB042" />
		</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
