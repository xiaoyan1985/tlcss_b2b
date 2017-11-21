<@s.iterator value="parameters.list">
    <#if parameters.listKey??>
        <#assign itemKey = stack.findValue(parameters.listKey)/>
    <#else>
        <#assign itemKey = stack.findValue('top')/>
    </#if>
    <#assign itemKeyStr = itemKey.toString() />
    <#if parameters.listValue??>
        <#assign itemValue = stack.findString(parameters.listValue)/>
    <#else>
        <#assign itemValue = stack.findString('top')/>
    </#if>
<input type="radio"<#rt/>
<#if parameters.name??>
 name="${parameters.name?html}"<#rt/>
</#if>
 id="${parameters.id?html}${itemKeyStr?html}"<#rt/>
<#if tag.contains(parameters.nameValue?default(''), itemKeyStr)>
 checked="checked"<#rt/>
</#if>
<#if itemKey??>
 value="${itemKeyStr?html}"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.tabindex??>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.cssClass??>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle??>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.onclick??>
 onclick="${parameters.onclick?html}"<#rt/>
</#if>
<#if parameters.onfocus??>
 onfocus="${parameters.onfocus?html}"<#rt/>
</#if>
<#if parameters.onblur??>
 onblur="${parameters.onblur?html}"<#rt/>
</#if>
<#if parameters.onselect??>
 onselect="${parameters.onselect?html}"<#rt/>
</#if>
<#if parameters.onchange??>
 onchange="${parameters.onchange?html}"<#rt/>
</#if>
/><#rt/>
<label for="${parameters.id?html}${itemKeyStr?html}"><#rt/>
    ${itemValue}<#t/>
</label>
</@s.iterator>