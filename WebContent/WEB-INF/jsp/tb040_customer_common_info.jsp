<%--*****************************************************
	顧客基本情報画面
	作成者：岩田
	作成日：2014/05/28
	更新日：2015/07/24 阿部 TORES外部サイト公開対応
	更新日：2015/08/19 阿部 顧客ＩＤ変更機能を追加
	更新日：2015/11/02 松葉 基本情報の項目更新・追加
	更新日：2016/06/17 仲野 請求先顧客情報複数件対応
	更新日：2016/07/19 松葉 請求先顧客情報リスト表示対応
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

		<script language="JavaScript" type="text/javascript">
		<!--//
		$(function() {
			
			fnc_lst_seikyusaki_kokyaku_info();
			
			<%-- 顧客ＩＤの先頭付与文字 --%>
			var CUSTOMER_ID_INITIAL = "C";
			
			<%-- 契約管理終了フラグが「0：継続中」以外は背景色を変える --%>
			<s:if test = "kokyakuEntity.isKeiyakuKanriEndFlgEnd()">
				$('#tblBasicInfo').addClass("tblDetailFormDel");
			</s:if>
			
			<%-- 表示時点の情報を保持 --%>
			var saveKokyakuId = $("#main_kokyakuId").val();
			var saveActionUrl = $("#main").attr('action');
			var saveGamenKbn = $("#main_gamenKbn").val();
			var saveFromDispKbn = $("#main_fromDispKbn").val();
			
			//******************************************************
			// 検索ボタン押下時
			//******************************************************
			$('#btnSearchKokyaku').click(function(e) {
				// パラメータを設定
				$("#main_kokyakuId").val($('#main_searchKokyakuId').val());
				
				// 画面表示した時のactionを設定、
				$('#main').prop('action', saveActionUrl);
				
				$('#main').prop('target', '_self');
				$('#main').submit();
				
				// パラメータを戻す
				$("#main_kokyakuId").val(saveKokyakuId);
			});
			
			//******************************************************
			// 契約情報参照リンク
			//******************************************************
			$('#hrf_contract').click(function(e) {
				
				$('#main').addClass('ignore');
				
				var win = createWindow('tb_contract_detail_win');
				
				$("#main_gamenKbn").val('tb046');
				$("#main_fromDispKbn").val('');
				
				$('#main').prop('action', '<s:url action="contractInfoInit" />');
				$('#main').prop('target', win.name);
				
				$('#main').submit();
				
				$('#main').removeClass('ignore');
				
				// パラメータを戻す
				$("#main_gamenKbn").val(saveGamenKbn);
				$("#main_fromDispKbn").val(saveFromDispKbn);
			});
			
			//******************************************************
			// マニュアル参照リンク
			//******************************************************
			$('#hrf_manual').click(function(e) {
				
				$('#main').addClass('ignore');
				
				var win = createWindow('tb_customer_manual_win' , 1024, 650);
				
				$('#main').prop('action', '<s:url action="customerManualInit" />');
				$('#main').prop('target', win.name);
				
				$('#main').submit();
				
				$('#main').removeClass('ignore');
			});
			
			//******************************************************
			// 文書ライブラリ参照リンク
			//******************************************************
			$('#hrf_library').click(function(e) {
				
				$('#main').addClass('ignore');
				
				var win = createWindow('tb_document_library_win', 1024, 650);
				
				$('#main').prop('action', '<s:url action="documentLibraryInit" />');
				$('#main').prop('target', win.name);
				
				$('#main').submit();
				
				$('#main').removeClass('ignore');
			});
			
			//******************************************************
			// 請求先顧客IDリスト変更時
			//******************************************************
			$('#main_seikyusakiKokyakuList').change(function(e) {
				fnc_lst_seikyusaki_kokyaku_info();
			});
			
			//******************************************************
			// 請求先顧客情報顧客ＩＤリンク
			//******************************************************
			$('[id^="hrf_seikyusaki_kokyaku_"]').click(function(e) {
				
				$('#main').addClass('ignore');
				
				var win = createWindow('tb_contract_detail_win');
				
				$("#main_gamenKbn").val('tb046');
				$("#main_fromDispKbn").val('');
				$("#main_kokyakuId").val($(this).attr('seikyusakiKokyakuId'));
				
				$('#main').prop('action', '<s:url action="contractInfoInit" />');
				$('#main').prop('target', win.name);
				
				$('#main').submit();
				
				$('#main').removeClass('ignore');
				
				// パラメータを戻す
				$("#main_gamenKbn").val(saveGamenKbn);
				$("#main_fromDispKbn").val(saveFromDispKbn);
				$("#main_kokyakuId").val(saveKokyakuId);
			});
		});

		//*******************************************
		// 請求先顧客ＩＤ変更時処理
		//*******************************************
		function fnc_lst_seikyusaki_kokyaku_info() {

			// 選択行情報を取得
			var kokyakuCd = $('#main_seikyusakiKokyakuList').val();

			// 顧客情報が存在しない場合、終了
			if (kokyakuCd == null) {
				return;
			}

			// 請求先顧客情報表示エリア全体を非表示
			$(".seikyusakiKokyakuInfoSection").css("display", "none");

			// 選択行に紐付く請求先顧客情報を表示
			$('[id^="seikyusakiKokyakuInfoSection_' + kokyakuCd + '"]').css("display","");

			// 再表示用の初期値を設定
			$('#main_selectKokyakuId').val(kokyakuCd);
		}
		//-->
		</script>
				
		<div class="content">
			<%-- 依頼内容詳細・作業状況登録、問い合わせ内容詳細は、常に【顧客基本情報】表示を --%>
			<s:if test="isRequestDetailDisplay() || isInquiryDetailDisplay()">
				<h2>顧客基本情報</h2>
			</s:if>
			<s:elseif test="%{isCustomerDetailDisplay() || !#session.userContext.isRealEstate()}">
				<h2>${f:out(subTitle)}</h2>
			</s:elseif>
			<s:else>
				&nbsp;
				
			</s:else>
			
			<%-- 使用画面：付随情報、契約情報、契約情報詳細参照、問い合わせ詳細履歴、依頼履歴 --%>
			<s:if test = "isSearchInputDisplay()">
				<div class="right" id="divSearchKokyaku">
					<span class="font2">顧客ID：</span>C&nbsp;
					<s:textfield name="searchKokyakuId" id="searchKokyakuId" maxlength="9" cssClass="digits required" cssStyle="width:100px;"/>
					<input type="button" id="btnSearchKokyaku" value="検索" class="btnDialog">
					<label for="main_searchKokyakuId" class="error" generated="true"></label>
				</div>
			</s:if>
			
			<%-- 使用画面：問い合わせ登録（顧客ＩＤあり）。顧客ＩＤ無し時は、この共通JSPに組み込めないため、呼び出し元に同じものを定義。 --%>
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
			<table width="90%" id="tblBasicInfo">
				<tr>
					<td colspan="2">
						<table width="100%">
							<tr>
								<th class="w10per">顧客ID</th>
								<td width="15%">${f:out(kokyakuEntity.kokyakuId)}</td>
								<th class="w10per">顧客区分</th>
								<s:if test="%{isProperty()}">
									<td width="15%">${f:out(kokyakuEntity.kokyakuKbnNm)}（${f:out(kokyakuEntity.kanriKeitaiKbnNm)}）</td>
								</s:if>
								<s:else>
									<td width="15%">${f:out(kokyakuEntity.kokyakuKbnNm)}</td>
								</s:else>
								
								<%-- 問い合わせ内容詳細、または、依頼内容詳細・作業状況登録でない、かつは、管理会社以外の場合は表示 --%>
								<s:if test="%{isInquiryDetailDisplay() ||(!isRequestDetailDisplay() && !#session.userContext.isRealEstate())}">
								<th width="8%">個人/法人</th>
								<td width="10%">${f:out(kokyakuEntity.kokyakuShubetsuNm)}</td>
								<th width="8%">締め日</th>
								<td width="5%">${f:out(kokyakuEntity.getShimeDayForDisplay())}</td>
								</s:if>
								<%-- 依頼内容詳細・作業状況登録、または、管理会社は非表示 --%>
								<s:else>
								<td width="31%" colspan="4">&nbsp;</td>
								</s:else>
								
								<td width="19%">
									<%-- 顧客マスタEntityが取得でき、表示対象画面の場合は表示 --%>
									<s:if test = "isContractReferenceDisplay()">
									<a href="#" id="hrf_contract"><span class="fontlink2">契約情報参照</span></a>
									</s:if>
									&nbsp;
								</td>
							</tr>
							<tr>
								<th>名称</th>
								<td colspan="7">
									${f:out(kokyakuEntity.kanjiNm)}(${f:out(kokyakuEntity.kanaNm)})
								</td>
								<td>
									<%-- 依頼内容詳細・作業状況登録、かつ、問い合わせ内容詳細でない場合、かつ、顧客マスタEntityが取得でき、権限が管理会社以外の場合は表示 --%>
									<s:if test="%{(!isRequestDetailDisplay() && !isInquiryDetailDisplay()) && (kokyakuEntity != null && !#session.userContext.isRealEstate())}">
									<a href="#" id="hrf_manual"><span class="fontlink2">マニュアル参照</span></a>
									</s:if>
									&nbsp;
								</td>
							</tr>
							<tr>
								<th>住所</th>
								<td colspan="7">${f:out(kokyakuEntity.yubinNo)}&nbsp;${f:out(kokyakuEntity.jusho)}
								</td>
								<td>
									<%-- 依頼内容詳細・作業状況登録以外かつ顧客マスタEntityが取得でき、権限が管理会社以外の場合は表示 --%>
									<s:if test="%{!isRequestDetailDisplay() && kokyakuEntity != null && !#session.userContext.isRealEstate()}">
									<a href="#" id="hrf_library"><span class="fontlink2">文書ライブラリ参照</span></a>
									</s:if>
									&nbsp;
								</td>
							</tr>
							<tr>
								<th>電話・FAX</th>
								<td colspan="8"><span class="font2">TEL1：</span>${f:out(kokyakuEntity.telNo1)}&nbsp;&nbsp;&nbsp;
												<span class="font2">TEL2：</span>${f:out(kokyakuEntity.telNo2)}&nbsp;&nbsp;&nbsp;
												<span class="font2">FAX：</span>${f:out(kokyakuEntity.faxNo)}
								</td>
							</tr>
							
							<%-- 依頼内容詳細・作業状況登録でない、または、権限が管理会社以外の場合 --%>
							<s:if test="%{(!isRequestDetailDisplay() && !isInquiryDetailDisplay()) && !#session.userContext.isRealEstate()}">
							<tr>
								<th rowspan="3">注意事項</th>
								<td colspan="4"><span class="font2">1：</span>${f:out(kokyakuEntity.attention1)}</td>
								<td colspan="4"><span class="font2">4：</span>${f:out(kokyakuEntity.attention4)}</td>
							</tr>
							<tr>
								<td colspan="4"><span class="font2">2：</span>${f:out(kokyakuEntity.attention2)}</td>
								<td colspan="4"><span class="font2">5：</span>${f:out(kokyakuEntity.attention5)}</td>
							</tr>
							<tr>
								<td colspan="4"><span class="font2">3：</span>${f:out(kokyakuEntity.attention3)}</td>
								<td colspan="4"><span class="font2">6：</span>${f:out(kokyakuEntity.attention6)}</td>
							</tr>
							</s:if>
							
							<%-- 依頼内容詳細・作業状況登録でない、または、権限が管理会社以外、かつ、顧客区分が「1：管理会社(大家含む)」以外、かつ、請求先顧客が取得できた場合 --%>
							<s:if test="%{(!isRequestDetailDisplay() && !isInquiryDetailDisplay()) && !#session.userContext.isRealEstate() && !kokyakuEntity.isKokyakuKbnFudosan() && (kokyakuEntity.seikyusakiKokyakuList != null && !kokyakuEntity.seikyusakiKokyakuList.isEmpty())}">
								<s:if test="kokyakuEntity.seikyusakiKokyakuList.size() >= 1">
									<%-- １件のみの表示 --%>
									<tr>
										<th>請求先顧客</th>
										<td colspan="8" >
											<s:select key="seikyusakiKokyakuList" list="kokyakuEntity.seikyusakiKokyakuList" listKey="kokyakuId" listValue="kokyakuIdWithKanjiNmAndKanaNm"
												emptyOption="false" cssClass="textIMEDisabled" />
											<s:hidden key="selectKokyakuId" />
										</td>
									</tr>
									<s:iterator status="i" var="seikyusakiKokyaku" value="kokyakuEntity.seikyusakiKokyakuList">
										<tr class="seikyusakiKokyakuInfoSection" id="seikyusakiKokyakuInfoSection_${f:out(kokyakuId)}">
											<th>電話・FAX</th>
											<td colspan="8">
												<span class="font2">TEL1：</span>${f:out(seikyusakiKokyaku.telNo1)}&nbsp;&nbsp;&nbsp;
												<span class="font2">TEL2：</span>${f:out(seikyusakiKokyaku.telNo2)}&nbsp;&nbsp;&nbsp;
												<span class="font2">FAX：</span>${f:out(seikyusakiKokyaku.faxNo)}
											</td>
										</tr>
										<tr class="seikyusakiKokyakuInfoSection" id="seikyusakiKokyakuInfoSection_${f:out(kokyakuId)}">
											<th rowspan="3">請求先顧客<br>注意事項</th>
											<td colspan="4"><span class="font2">1：</span>${f:out(seikyusakiKokyaku.attention1)}</td>
											<td colspan="4"><span class="font2">4：</span>${f:out(seikyusakiKokyaku.attention4)}</td>
										</tr>
										<tr class="seikyusakiKokyakuInfoSection" id="seikyusakiKokyakuInfoSection_${f:out(kokyakuId)}">
											<td colspan="4"><span class="font2">2：</span>${f:out(seikyusakiKokyaku.attention2)}</td>
											<td colspan="4"><span class="font2">5：</span>${f:out(seikyusakiKokyaku.attention5)}</td>
										</tr>
										<tr class="seikyusakiKokyakuInfoSection" id="seikyusakiKokyakuInfoSection_${f:out(kokyakuId)}">
											<td colspan="4"><span class="font2">3：</span>${f:out(seikyusakiKokyaku.attention3)}</td>
											<td colspan="4"><span class="font2">6：</span>${f:out(seikyusakiKokyaku.attention6)}</td>
										</tr>
									</s:iterator>
								</s:if>
							</s:if>
							
						</table>
					</td>
				</tr>
			</table>
		</div>
	


