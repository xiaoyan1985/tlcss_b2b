<#include "/${parameters.templateDir}/sifw/form-common.ftl" />
<#if parameters.onreset??>
 onreset="${parameters.onreset?html}"<#rt/>
</#if>
>
<input type="hidden" name="currentLoginId" value="<@s.property value="#session['userContext'].loginId"/>" />
