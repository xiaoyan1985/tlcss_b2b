﻿<%--*****************************************************
	メニュー 共通メニュー
	作成者：仲野
	作成日：2014/06/05
	更新日：2015/08/27 小林 委託会社SV、委託会社OPの追加
	        2015/09/15 松葉 権限による非表示(依頼検索ボタン、業者検索ボタン)
	        2016/09/13 松葉 管理会社ユーザでのログイン時、依頼検索ボタンの表示制御を追加
*******************************************************--%>
<%@ page pageEncoding="UTF-8" %>
			<h2>メニュー</h2>
			<div>
				<table class="borderoff">
					<tr class="borderoff" style="padding:10px;">
						<%-- ユーザーの権限が社内、不動産・管理会社、委託会社SV、委託会社OPの場合 --%>
						<s:if test="userContext.isInhouse() || userContext.isRealEstate() || userContext.isOutsourcerSv() || userContext.isOutsourcerOp()">
						<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
							<a class="btnMenu1" href="javascript:openScreen('tb_inquiry_search_win', '<s:url action="inquirySearchInit" />');"><span class="menuTitle1">問い合わせ検索</span></a>
						</td>
						<%-- ユーザーの権限が管理会社かつ、顧客IDが委託会社参照顧客マスタに存在する場合 --%>
						<s:if test="userContext.isRealEstate() && existsItakuRefKokyaku()">
						<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
							<a class="btnMenu1" href="javascript:openScreen('tb_customer_search_win', '<s:url action="customerSearchInit" />');"><span class="menuTitle1">物件・入居者検索</span></a>
						</td>
						</s:if>
						<s:else>
						<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
							<a class="btnMenu1" href="javascript:openScreen('tb_request_search_win', '<s:url action="requestSearchInit" />');"><span class="menuTitle1">依頼検索</span></a>
						</td>
						</s:else>
						<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
							<%-- ユーザーの権限が管理会社の場合 --%>
							<s:if test="userContext.isRealEstate() && isIPad()">
								<br>
							</s:if>
							<s:else>
								<s:if test="userContext.isRealEstate() && !existsItakuRefKokyaku()">
									<a class="btnMenu1" href="javascript:openScreen('tb_customer_search_win', '<s:url action="customerSearchInit" />');"><span class="menuTitle1">物件・入居者検索</span></a>
								</s:if>
								<s:elseif test="userContext.isRealEstate()">
									<br>
								</s:elseif>
								<s:else>
									<a class="btnMenu1" href="javascript:openScreen('tb_customer_search_win', '<s:url action="customerSearchInit" />');"><span class="menuTitle1">顧客検索</span></a>
								</s:else>
							</s:else>
						</td>
						</s:if>
						<%-- ユーザーの権限が依頼業者の場合 --%>
						<s:elseif test="userContext.isConstractor()">
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<a class="btnMenu1" href="javascript:openScreen('tb_request_search_win', '<s:url action="requestSearchInit" />');"><span class="menuTitle1">依頼検索</span></a>
						</td>
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<br>
						</td>
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<br>
						</td>
						</s:elseif>
					</tr>
					<%-- iPadでログインしている際は、枠がはみ出てしまうため、ボタン２つで折り返し --%>
					<s:if test="userContext.isRealEstate() && !existsItakuRefKokyaku() && isIPad()">
					<tr class="borderoff">
						<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
							<s:if test="userContext.isRealEstate()">
								<a class="btnMenu1" href="javascript:openScreen('tb_customer_search_win', '<s:url action="customerSearchInit" />');"><span class="menuTitle1">物件・入居者検索</span></a>
							</s:if>
							<s:else>
								<a class="btnMenu1" href="javascript:openScreen('tb_customer_search_win', '<s:url action="customerSearchInit" />');"><span class="menuTitle1">顧客検索</span></a>
							</s:else>
						</td>
						<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
							<br>
						</td>
						<td width="33%" valign="middle" class="borderoff" style="padding:5px;">
							<br>
						</td>
					</tr>
					</s:if>
					<%-- ユーザーの権限が社内、不動産・管理会社、委託会社SV、委託会社OPの場合 --%>
					<s:if test="userContext.isInhouse() || userContext.isRealEstate() || userContext.isOutsourcerSv() || userContext.isOutsourcerOp()">
					<tr class="borderoff">
						<%-- ユーザーの権限が社内、委託会社SV、委託会社OPの場合 --%>
						<s:if test="userContext.isInhouse() || userContext.isOutsourcerSv() || userContext.isOutsourcerOp()">
							<td width="33%" valign="top" class="borderoff" style="padding:5px;">
								<a class="btnMenu2" href="javascript:openScreen('tb_center_contractor_search_win', '<s:url action="centerContractorSearchInit" />');"><span class="menuTitle1">センター業者検索</span></a>
							</td>
						</s:if>
						<%-- ユーザーの権限が社内、不動産・管理会社の場合 --%>
						<s:if test="userContext.isInhouse() || userContext.isRealEstate()">
							<td width="33%" valign="top" class="borderoff" style="padding:5px;">
								<a class="btnMenu2" href="javascript:openScreen('tb_reception_list_print_win', '<s:url action="receptionListInit" />');"><span class="menuTitle1">受付一覧印刷</span></a>
							</td>
						</s:if>
						<s:else>
							<td width="33%" valign="top" class="borderoff" style="padding:5px;">
								<br>
							</td>
						</s:else>
						<%-- ユーザーの権限が不動産・管理会社の場合 --%>
						<s:if test="userContext.isRealEstate()">
							<td width="33%" valign="top" class="borderoff" style="padding:5px;">
								<a class="btnMenu2" href="javascript:openScreen('tb_customer_upload_win', '<s:url action="customerUploadInit" />');"><span class="menuTitle2">管理情報<br>アップロード</span></a>
							</td>
						</s:if>
						<s:else>
							<td width="33%" valign="top" class="borderoff" style="padding:5px;">
								<br>
							</td>
						</s:else>
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<br>
						</td>
					</tr>
					</s:if>
					<tr class="borderoff">
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<a class="btnMenu3" href="javascript:openScreenY('tb_password_change_win', '<s:url action="passwordChangeInit" />', 650);"><span class="menuTitle1">パスワード変更</span></a>
						</td>
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<br>
						</td>
						<s:if test="userContext.isGeneralInhouse()">
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<a class="btnMenu4" href="javascript:openScreen('_self', '<s:url action="managedTargetListInit" />');"><span class="menuTitle1">管理対象一覧</span></a>
						</td>
						</s:if>
						<s:else>
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<br>
						</td>
						</s:else>
					</tr>
					<%-- ユーザーの権限が管理者の場合 --%>
					<s:if test="userContext.isAdministrativeInhouse()">
					<tr class="borderoff">
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<a class="btnMenu3" href="javascript:openScreen('tb_user_master_search_win', '<s:url action="userMasterSearchInit" />');"><span class="menuTitle2">ユーザーマスタ<br>メンテナンス</span></a>
						</td>
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<a class="btnMenu3" href="javascript:openScreen('tb_information_search_win', '<s:url action="informationSearchInit" />');"><span class="menuTitle2">お知らせ<br>メンテナンス</span></a>
						</td>
						<td width="33%" valign="top" class="borderoff" style="padding:5px;">
							<a class="btnMenu4" href="javascript:openScreen('_self', '<s:url action="managedTargetListInit" />');"><span class="menuTitle1">管理対象一覧</span></a>
						</td>
					</tr>
					</s:if>
				</table>
			</div>
