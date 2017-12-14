<%-- 
    Document   : appSettings
    Created on : 12.12.2017, 20:21:02
    Author     : Victor Deyneko <VADeyneko@gmail.com>
--%>

 
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
 
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />
  
<c:set var="notDefined">
    <fmt:message key="label.not-defined"/>
</c:set> 
<c:set var="title">
    <fmt:message key="label.app-settings"/>
</c:set>
<c:set var="dateBegin_head">
    <fmt:message key="label.dPeriodBegin"/>
</c:set>
<c:set var="dateEnd_head">
    <fmt:message key="label.dPeriodEnd"/>
</c:set> 
<c:set var="maxDaysAllowed">
    <fmt:message key="label.maxDaysAllowed"/>
</c:set>
<c:set var="minDaysAllowed">
    <fmt:message key="label.minDaysAllowed"/>
</c:set> 

    <common:error/>
    <c:if test="${error == null}">
        <common:saved/>
    </c:if>
    
  <common:topmenu/>
 
  <h1>${title} </h1>
			
  <form action="<%= request.getContextPath()%>/appSettings"   method="post"  class="form-horizontal  ">    
  <div class="form-group col-xs-8 well well-sm">    
   <div class="  col-xs-6">              
      <label  class=" control-label small left " for="minDaysAllowed1"  >${minDaysAllowed}</label> 
         <div  >
         <input type="text"  id="minDaysAllowed1"
               name="minDaysAllowed"     
               ${formParamProps.getProps("minDaysAllowed")}
               class="form-control  input-sm "
               placeholder="${minDaysAllowed_header}"
               value= "${appSettings.minDaysAllowed}" 
               required>
         </div> 
      </div>              
 
  <div class="  col-xs-6">              
      <label  class=" control-label small left " for="maxDaysAllowed1"  >${maxDaysAllowed}</label> 
         <div  >
         <input type="text"  id="maxDaysAllowed1"
               name="maxDaysAllowed"     
               ${formParamProps.getProps("maxDaysAllowed")}
               class="form-control  input-sm"
               placeholder="${maxDaysAllowed_header}"
               value= "${appSettings.maxDaysAllowed}" 
               required>
         </div> 
      </div>          
               
      
      <div class="  col-xs-6">          
            <label  class=" control-label small left" for="datepicker_dBegin">${dateBegin_head}</label>
           
           <div >
           <input type="text" id="datepicker_dBegin"    
                   name="dateBegin"     readonly="true"                     
                   ${formParamProps.getProps("dateBegin")}
                   class="form-control  input-sm"
                   placeholder="${requestDateBegin_header}"
                   value="${appSettings.getFormatteddPeriodBegin()}"
                   required>
            </div>
     </div> 
      <div class="  col-xs-6">              
      <label  class=" control-label small left " for="datepicker_dEnd">${dateEnd_head}</label> 
         <div  >
         <input type="text" id="datepicker_dEnd"   
               name="dateEnd"    readonly="true"         
               ${formParamProps.getProps("dateEnd")}
               class="form-control  input-sm"
               placeholder="${requestDateEnd_header}"
               value= "${appSettings.getFormatteddPeriodEnd()}" 
               required>
         </div> 
      </div>     
  
     </div>           
           
       <%--КАЛЕНДАРИК  --%>         
      <div  class="form-control-static col-xs-6 col-xs-pull-0 "    id="date-range0-container" ${formParamProps.getProps("date-range0-container" )}  >
      <label for="date-range0-container" class=" control-label col-xs-6   small  "   > ${calendar_header}</label>    
      </div>
 
              
     <div class="form-group  col-xs-12">                            
     <button class="btn"<c:if test="${error != null}">  disabled </c:if>  >
        <fmt:message key="label.apply"/>
    </button>     
     
        <a onclick="javascript:history.back(); return false;"> <fmt:message key="label.back"/></a>
   </div>   
   
   
</form>