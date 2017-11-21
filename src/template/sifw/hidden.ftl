<input type="hidden"<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.nameValue??>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.id.startsWith("main")>
  <#assign parametersId = parameters.id />
<#else>
  <#assign parametersId = 'main_' + parameters.id />
</#if>
<#if parametersId??>
 id="${parametersId?html}"<#rt/>
</#if>
/>