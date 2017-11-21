<%@ page pageEncoding="UTF-8" %>
<%@ page import = "jp.co.tokaigroup.si.fw.db.pager.PagerViewHelper" %>
<%@ page import = "jp.co.tokaigroup.si.fw.db.pager.PagerCondition" %>
<%@ page import = "jp.co.tokaigroup.si.fw.filter.CsrfPreventionFilter"%>
<%@ page import = "org.apache.commons.lang.StringUtils"%>
<%
	String conditionName = request.getParameter("condition");
	String href          = request.getParameter("href");
	String displayCount  = request.getParameter("displayCount");

	PagerCondition condition = (PagerCondition) request.getAttribute(conditionName);
	PagerViewHelper helper   = new PagerViewHelper(condition);
%>

<%--
<% if ("1".equals(displayCount)) { %>
<tr><td class="borderoff"><span class="font1">【検索結果 <%= helper.getCount() %> 件】</span></td></tr>
<% } %>
 --%>

<div class="clearfix">
	<span style="float:left;"><%= helper.getCount() %>件</span>
	<div class="pager">
		<ul>
<% for (int i = 0; i < helper.getLastPageIndex(); i++) { %>
	<% if (i == helper.getPageIndex()) { %>
			<li><span class="current"><%= i + 1 %></span></li>
	<% } %>
	<% if (i != helper.getPageIndex()) { %>
			<li><a href="javascript:switchPage(<%= (i * helper.getLimit()) + 1 %>);" target="_self"><%= i + 1 %></a><li>
	<% } %>
<% } %>
		</ul>
	</div>
</div>

<% if (href != null) { %>
	<%-- ページ切替のためのパラメータ --%>
	<s:hidden key="offset" />
	<s:hidden key="maxCount" />
	<s:hidden key="limit" />
	<s:hidden key="displayToMax" />

	<s:hidden key="condition.offset" />
	<s:hidden key="condition.maxCount" />
	<s:hidden key="condition.limit" />
	<s:hidden key="condition.displayToMax" />

	<%-- ページ切替のリクエストを送るスクリプト --%>
	<script language="JavaScript" type="text/javascript">
	<!--

		function switchPage(offset) {
			$('#main_condition_offset').val(offset);

			<%-- プレースホルダーがある項目は、入力がない場合空にする --%>
			var elements = $("[placeholder]", "#main").get();
			for (i=0; i<elements.length; i++) {
				if (elements[i].value === $.data(elements[i], 'placeholder-string') ) {
					elements[i].value = '';
					$(elements[i]).css('color', $.data(elements[i], 'placeholder-color'));
				}
			}

			document.main.action = '<%= href %>';
			document.main.target = '_self';
			document.main.submit();
		}
	// -->
	</script>
<% } %>