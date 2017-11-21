<#if parameters.id.startsWith("main")>
  <#assign parametersId = parameters.id />
<#else>
  <#assign parametersId = 'main_' + parameters.id />
</#if>
<input type="file"<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.get("size")??>
 size="${parameters.get("size")?html}"<#rt/>
</#if>
<#if parameters.nameValue??>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.accept??>
 accept="${parameters.accept?html}"<#rt/>
</#if>
<#if parameters.tabindex??>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.id??>
 id="${parameters.id?html}"<#rt/>
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
<#if parameters.title??>
 title="${parameters.title?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/sifw/scripting-events.ftl" />
<#include "/${parameters.templateDir}/sifw/dynamic-attributes.ftl" />
/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<label for="${parametersId}" class="error" generated="true" />${error?html}</label>
</#list>
</#if>