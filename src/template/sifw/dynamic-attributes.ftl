<#if (parameters.dynamicAttributes?? && parameters.dynamicAttributes?size > 0)><#t/>
    <#assign aKeys = parameters.dynamicAttributes.keySet()><#t/>
    <#list aKeys as aKey><#t/>
 ${aKey}="${parameters.dynamicAttributes[aKey]?html}"<#rt/>
    </#list><#t/> 
</#if><#t/>