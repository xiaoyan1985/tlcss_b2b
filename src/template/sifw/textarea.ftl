<#assign onblur = "Javascript:this.value=this.value.replace(/\t/g, ' ');"/>
<textarea<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
 cols="${parameters.cols?default("")?html}"<#rt/>
 rows="${parameters.rows?default("")?html}"<#rt/>
<#if parameters.wrap??>
 wrap="${parameters.wrap?html}"<#rt/>
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
<#if parameters.id??>
 id="${parameters.id?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/sifw/css.ftl" />
<#if parameters.title??>
 title="${parameters.title?html}"<#rt/>
</#if>
<#if onblur??>
 onblur="${onblur?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/sifw/scripting-events.ftl" />
<#include "/${parameters.templateDir}/sifw/dynamic-attributes.ftl" />
><#rt/>
<#if parameters.nameValue??>
<@s.property value="parameters.nameValue"/><#t/>
</#if>
</textarea>
<#assign hasFieldErrors = parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]??/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<label for="${parameters.id}" class="error" generated="true" />${error?html}</label>
</#list>
</#if>