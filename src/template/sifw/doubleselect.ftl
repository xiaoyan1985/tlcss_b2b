<#include "/${parameters.templateDir}/sifw/select.ftl" />

<#if ! parameters.formName??>
  <#assign formName = "main"/>
  <#if parameters.id.startsWith("main")>
    <#assign parametersId = parameters.id />
  <#else>
    <#assign parametersId = 'main_' + parameters.id />
  </#if>
<#else>
  <#assign formName = parameters.formName />
  <#assign parametersId = parameters.id />
</#if>

<#assign startCount = 0/>
<#if parameters.headerKey?? && parameters.headerValue??>
    <#assign startCount = startCount + 1/>
</#if>
<#if parameters.emptyOption??>
    <#assign startCount = startCount + 1/>
</#if>
<#if parameters.label??>
&nbsp;
<span class="font5">${parameters.label}</span>
</#if>
<select<#rt/>
 name="${parameters.doubleName?default("")?html}"<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.doubleTabindex??>
 tabindex="${parameters.doubleTabindex?html}"<#rt/>
</#if>
<#if parameters.doubleId??>
 id="${parameters.doubleId?html}"<#rt/>
</#if>
<#if parameters.doubleCss??>
 class="${parameters.doubleCss?html}"<#rt/>
</#if>
<#if parameters.doubleStyle??>
 style="${parameters.doubleStyle?html}"<#rt/>
</#if>
<#if parameters.doubleDisabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
>
</select>
<#assign hasFieldErrors = parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]??/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<label for="${parametersId}" class="error" generated="true" />${error?html}</label>
</#list>
</#if>
<script type="text/javascript">
<#assign itemCount = startCount/>
    var ${parametersId}Group = new Array(${parameters.listSize} + ${startCount});
    for (i = 0; i < (${parameters.listSize} + ${startCount}); i++)
    ${parametersId}Group[i] = new Array();

<@s.iterator value="parameters.list">
    <#if parameters.listKey??>
        <#assign itemKey = stack.findValue(parameters.listKey)/>
    <#else>
        <#assign itemKey = stack.findValue('top')/>
    </#if>
    <#if parameters.listValue??>
        <#assign itemValue = stack.findString(parameters.listValue)/>
    <#else>
        <#assign itemValue = stack.findString('top')/>
    </#if>
    <#assign doubleItemCount = 0/>
	<#if parameters.doubleEmptyOption??>
    ${parametersId}Group[${itemCount}][${doubleItemCount}] = new Option("", "");
	    <#assign doubleItemCount = doubleItemCount + 1/>
	</#if>
    <@s.iterator value="${parameters.doubleList}">
        <#if parameters.doubleListKey??>
            <#assign doubleItemKey = stack.findValue(parameters.doubleListKey)/>
        <#else>
            <#assign doubleItemKey = stack.findValue('top')/>
        </#if>
        <#assign doubleItemKeyStr = doubleItemKey.toString() />
        <#if parameters.doubleListValue??>
            <#assign doubleItemValue = stack.findString(parameters.doubleListValue)/>
        <#else>
            <#assign doubleItemValue = stack.findString('top')/>
        </#if>
    ${parametersId}Group[${itemCount}][${doubleItemCount}] = new Option("${doubleItemValue}", "${doubleItemKeyStr}");

        <#assign doubleItemCount = doubleItemCount + 1/>
    </@s.iterator>
    <#assign itemCount = itemCount + 1/>
</@s.iterator>

    var ${parametersId}Temp = document.${formName}.${parameters.doubleId};
<#assign itemCount = startCount/>
<#assign redirectTo = 0/>
<@s.iterator value="parameters.list">
    <#if parameters.listKey??>
        <#assign itemKey = stack.findValue(parameters.listKey)/>
    <#else>
        <#assign itemKey = stack.findValue('top')/>
    </#if>
    <#if tag.contains(parameters.nameValue, itemKey)>
        <#assign redirectTo = itemCount/>
    </#if>
    <#assign itemCount = itemCount + 1/>
</@s.iterator>
    ${parameters.id}Redirect(${redirectTo});
    function ${parameters.id}Redirect(x) {
    	var selected = false;
        for (m = ${parametersId}Temp.options.length - 1; m >= 0; m--) {
            ${parametersId}Temp.remove(m);
        }

        for (i = 0; i < ${parametersId}Group[x].length; i++) {
            ${parametersId}Temp.options[i] = new Option(${parametersId}Group[x][i].text, ${parametersId}Group[x][i].value);
            <#if parameters.doubleNameValue?exists>
                   <#if parameters.doubleMultiple?exists>
                         for (j = 0; j < ${parameters.doubleNameValue}.length; j++) {
                             if (${parametersId}Temp.options[i].value == ${parameters.doubleNameValue}[j]) {
                               ${parametersId}Temp.options[i].selected = true;
                                selected = true;
                             }
                        }
                   <#else>
                        if (${parametersId}Temp.options[i].value == '${parameters.doubleNameValue}') {
                            ${parametersId}Temp.options[i].selected = true;
                            selected = true;
                        }
                   </#if>
             </#if>
         }

		if (x == 0) {
			${parametersId}Temp.options[0] = new Option("", "");
		}

        if ((${parametersId}Temp.options.length > 0) && (! selected)) {
           	${parametersId}Temp.options[0].selected = true;
        }
    }
</script>
