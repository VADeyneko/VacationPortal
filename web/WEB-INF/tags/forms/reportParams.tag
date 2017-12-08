<%-- 
    Document   : reportParams
    Created on : 08.12.2017, 14:01:17
    Author     : Victor Deyneko <VADeyneko@gmail.com>
--%>

 
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />

<c:set var="requestOwner_header">
    <fmt:message key="label.requestOwner_header"/>
</c:set>
<c:set var="requestManager_header">
    <fmt:message key="label.requestManager_header"/>
</c:set>
<c:set var="requestDateBegin_header">
    <fmt:message key="label.requestDateBegin_header"/>
</c:set>
<c:set var="requestDateEnd_header">
    <fmt:message key="label.requestDateEnd_header"/>
</c:set>
<c:set var="requestState_header">
    <fmt:message key="label.requestState_header"/>
</c:set>
<c:set var="vacationType_header">
    <fmt:message key="label.vacationType_header"/>
</c:set>
<c:set var="calendar_header">
    <fmt:message key="label.calendar"/>
</c:set>
<c:set var="requestOwnerComment_header">
    <fmt:message key="label.ownerComment_header"/>
</c:set>
<c:set var="requestManagerComment_header">
    <fmt:message key="label.managerComment_header"/>
</c:set>
 
    <common:error/>
    
    <script>
        </script>
        
 
			
  <form action=""   method="post"  class="form-horizontal   ">      			      
    <div class="form-group form-group-sm ">        
      <div class="  col-xs-2">          
          <label  class=" control-label small" for="datepicker_dBegin"><a href="#" id="date-range0">${requestDateBegin_header} </a></label>
           
           <div >
           <input type="text" id="datepicker_dBegin"    
                   name="dateBegin"      ${formParamProps.getProps("dateBegin")}
                   class="form-control  input-sm"
                   placeholder="${requestDateBegin_header}"
                   value="${action.contains ("requestInsert") ? newRequestDefaultDate  : objToEdit.getFormatedDateBegin()}"
                   required>
            </div>
     </div> 
      <div class="  col-xs-2">              
      <label  class=" control-label small " for="datepicker_dEnd">${requestDateEnd_header}</label> 
         <div  >
         <input type="text" id="datepicker_dEnd"   
               name="dateEnd"     ${formParamProps.getProps("dateEnd")}
               class="form-control  input-sm"
               placeholder="${requestDateEnd_header}"
               value="${action.contains ("requestInsert") ? newRequestDefaultDate  : objToEdit.getFormatedDateEnd()}" 
               required>
         </div> 
      </div>     
               
        <div class="  col-xs-4" >   
               <label for="Select4"  class=" control-label small ">${vacationType_header} :</label>         
        <select  id="Select4" class="form-control col-xs-6  input-sm"   
                 ${formParamProps.getProps("vacationType")} 
                 name="vacationType"  >
         <c:forEach var="type"  items="${vacationTypeList}">                      
            <option value="${type.id}" ${type.id == objToEdit.vacationType.getId()  ? 'selected="selected"' : ''}>${type.name}</option>
         </c:forEach>
        </select>                     
      </div>
         

      <div class="col-xs-4" >    
        <label for="Select3"  class=" control-label small ">${requestState_header} :</label>         
        <select  id="Select3" class="form-control  input-sm"   ${formParamProps.getProps("requestState")}   name="requestState"  >
         <c:forEach var="state"  items="${requestStateList}">                      
            <option  ${  state.id ==  selectedState_Id ? '' : ''}  value="${state.id}" ${ state.id ==  selectedState_Id    ? 'selected="selected"' : ''}>${state.name}</option>
         </c:forEach>
        </select>             
     </div>   
     </div> 
        
        
         
       <%--КАЛЕНДАРИК     --%>  
      <div    class="form-control-static col-xs-6 col-xs-pull-0 "      id="date-range0-container"   >
     <%-- <label for="date-range0-container" class=" control-label col-xs-6   small  "   > ${calendar_header}</label>    --%>
      </div> 
 
              
     <div class="form-group  col-xs-12" >                            
     <button class="btn">
        <fmt:message key="label.apply"/>
    </button>         
   </div>   
</form>