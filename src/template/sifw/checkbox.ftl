<input type="checkbox" name="${parameters.name?html}" value="${parameters.fieldValue?html}"<#rt/>
<#if parameters.id.startsWith("main")>
  <#assign parametersId = parameters.id />
<#else>
  <#assign parametersId = 'main_' + parameters.id />
</#if>
<#if parameters.nameValue?? && parameters.nameValue>
 checked="checked"<#rt/>
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
<#include "/${parameters.templateDir}/reception/css.ftl" />
<#if parameters.title??>
 title="${parameters.title?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/reception/scripting-events.ftl" />
<#include "/${parameters.templateDir}/reception/dynamic-attributes.ftl" />
/><#rt/>
<input type="hidden" id="__checkbox_${parametersId?html}" name="__checkbox_${parameters.name?html}" value="${parameters.fieldValue?html}"<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
 /><#rt/>
 <#assign hasFieldErrors = parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]??/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<label for="${parameters.id}" class="error" generated="true" />${error?html}</label>
</#list>
</#if>