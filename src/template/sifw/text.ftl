<#if parameters.id.startsWith("main")>
  <#assign parametersId = parameters.id />
<#else>
  <#assign parametersId = 'main_' + parameters.id />
</#if>
<input type="text"<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.maxlength??>
 maxlength="${parameters.maxlength?html}"<#rt/>
</#if>
<#if parameters.nameValue??>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex??>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parametersId??>
 id="${parametersId?html}"<#rt/>
</#if>
<#assign hasFieldErrors = parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]??/>
<#if parameters.cssClass??>
 class="${parameters.cssClass?html}<#rt/>
<#if hasFieldErrors>
	<#list fieldErrors[parameters.name] as error>
 error"<#rt/>
	</#list>
<#else>
"<#rt/>
</#if>
<#else>
<#if hasFieldErrors>
	<#list fieldErrors[parameters.name] as error>
 class="error"<#rt/>
	</#list>
</#if>
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
<#if onfocus??>
 onfocus="${onfocus?html}"<#rt/>
</#if>
<#if parameters.onblur??>
 onblur="${parameters.onblur?html}"<#rt/>
</#if>
<#if onblur??>
 onblur="${onblur?html}"<#rt/>
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
/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<label for="${parametersId}" class="error" generated="true" />${error?html}</label>
</#list>
</#if>


