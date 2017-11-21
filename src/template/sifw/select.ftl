<#setting number_format="#.#####">
<#if parameters.id.startsWith("main")>
  <#assign parametersId = parameters.id />
<#else>
  <#assign parametersId = 'main_' + parameters.id />
</#if>
<select<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.tabindex??>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parametersId??>
 id="${parametersId?html}"<#rt/>
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
<#if (parameters.dynamicAttributes?? && parameters.dynamicAttributes?size > 0)><#t/>
    <#assign aKeys = parameters.dynamicAttributes.keySet()><#t/>
    <#list aKeys as aKey><#t/>
 ${aKey}="${parameters.dynamicAttributes[aKey]?html}"<#rt/>
    </#list><#t/>
</#if><#t/>
>
<#if parameters.headerKey?? && parameters.headerValue??>
    <option value="${parameters.headerKey?html}"
    <#if tag.contains(parameters.nameValue, parameters.headerKey) == true>
    selected="selected"
    </#if>
    >${parameters.headerValue?html}</option>
</#if>
<#if parameters.emptyOption?default(false)>
    <option value="">（未選択）</option>
</#if>
<@s.iterator value="parameters.list">
        <#if parameters.listKey??>
            <#if stack.findValue(parameters.listKey)??>
              <#assign itemKey = stack.findValue(parameters.listKey)/>
              <#assign itemKeyStr = stack.findString(parameters.listKey)/>
            <#else>
              <#assign itemKey = ''/>
              <#assign itemKeyStr = ''/>
            </#if>
        <#else>
            <#assign itemKey = stack.findValue('top')/>
            <#assign itemKeyStr = stack.findString('top')>
        </#if>
        <#if parameters.listValue??>
            <#if stack.findString(parameters.listValue)??>
              <#assign itemValue = stack.findString(parameters.listValue)/>
            <#else>
              <#assign itemValue = ''/>
            </#if>
        <#else>
            <#assign itemValue = stack.findString('top')/>
        </#if>
    <option value="${itemKeyStr?html}"<#rt/>
        <#if tag.contains(parameters.nameValue, itemKey) == true>
 selected="selected"<#rt/>
        </#if>
    >${itemValue?html}</option><#lt/>
</@s.iterator>
</select>