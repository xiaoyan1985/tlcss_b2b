<%--*****************************************************
	顧客詳細画面タブ
	作成者：小林
	作成日：2015/07/27
	更新日：2015/08/03 阿部 formの除去、パラメータ調整。
	        2015/09/15 松葉 依頼履歴タブの非表示
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>

		<script language="JavaScript" type="text/javascript">
		<!--//

			$(function(){

				//******************************************************
				// タブ押下時
				//******************************************************

				// 付随情報タブ
				$('#tabItem1').click(function(e){
					$('#fuzui').submit();
				});						
				// 契約情報タブ
				$('#tabItem2').click(function(e){
					$('#contract').submit();
				});	
				// 問い合わせ履歴タブ
				$('#tabItem3').click(function(e){
					$('#toiawase').submit();
				});	
				// 依頼履歴タブ
					$('#tabItem4').click(function(e){
					$('#irai').submit();
				});	
			});
		//-->
		</script>

			<table class="tab">
				<tr class="tabMenu">
				<%-- 付随情報参照画面の場合 --%>
				<s:if test="isAccompanyingDisplay()">
					<th class="tab_selected" width="25%">
						<span class="font3">付随情報</span>
					</th>
					<th class="tab_left" width="25%">
						<a class="tab" href="#" id="tabItem2">契約情報</a>
					</th>
					<th class="tab_right">
						<a class="tab" href="#" id="tabItem3">問い合わせ履歴</a>
					</th>
					
					<th class="tab_right" width="25%">
						<a class="tab" href="#" id="tabItem4">依頼履歴</a>
					</th>
				</s:if>
				<%-- 契約情報参照画面の場合 --%>
				<s:elseif test="isContractDisplay()">
					<th class="tab_both" width="25%">
						<a class="tab" href="#" id="tabItem1">付随情報</a>
					</th>
					<th class="tab_selected" width="25%">
						<span class="font3">契約情報</span>
					</th>
					<th class="tab_both">
						<a class="tab" href="#" id="tabItem3">問い合わせ履歴</a>
					</th>
					<th class="tab_right" width="25%">
						<a class="tab" href="#" id="tabItem4">依頼履歴</a>
					</th>
				</s:elseif>
				<%-- 問い合わせ履歴参照画面の場合 --%>
				<s:elseif test="isInquiryHistoryDisplay()">
					<th class="tab_left" width="25%">
						<a class="tab" href="#" id="tabItem1">付随情報</a>
					</th>
					<th class="tab_right" width="25%">
						<a class="tab" href="#" id="tabItem2">契約情報</a>
					</th>
					<th class="tab_selected">
						<span class="font3">問い合わせ履歴</span>
					</th>
					<th class="tab_right" width="25%">
						<a class="tab" href="#" id="tabItem4">依頼履歴</a>
					</th>
				</s:elseif>
				<%-- 依頼履歴参照画面の場合 --%>
				<s:elseif test="isRequestHistoryDisplay()">
					<th class="tab_left" width="25%">
						<a class="tab" href="#" id="tabItem1">付随情報</a>
					</th>
					<th class="tab" width="25%">
						<a class="tab" href="#" id="tabItem2">契約情報</a>
					</th>
					<th class="tab_right" width="25%">
						<a class="tab" href="#" id="tabItem3">問い合わせ履歴</a>
					</th>
					<th class="tab_selected" width="25%">
						<span class="font3">依頼履歴</span>
					</th>
				</s:elseif>
				</tr>
			</table>
