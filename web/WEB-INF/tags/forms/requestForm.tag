<%-- 
    Document   : requestForm
    Created on : 15.11.2017, 22:52:07
    Author     : admin
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
    
  <common:topmenu/>
 
			
  <form action="<%= request.getContextPath()%>/${action}?id=${objToEdit.id}"   method="post"  class="form-horizontal  ">      			      
    <div class="form-group ">
       
        <div class="col-xs-12 ">       
            <div class="  col-xs-2">
          
            <label  class=" control-label small" for="datepicker_dBegin">${requestDateBegin_header}</label>
           
           <div >
           <input type="text" id="datepicker_dBegin"    
                   name="dateBegin"     readonly="true"
                     
                   ${dsbl_all}     
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
               name="dateEnd"    readonly="true"         
               ${dsbl_all}    
               class="form-control  input-sm"
               placeholder="${requestDateEnd_header}"
               value="${action.contains ("requestInsert") ? newRequestDefaultDate  : objToEdit.getFormatedDateEnd()}" 
               required>
         </div> 
      </div>     
               
        <div class="  col-xs-4" >   
               <label for="Select4"  class=" control-label small ">${vacationType_header} :</label>         
        <select  id="Select4" class="form-control col-xs-6  input-sm"    ${dsbl_all}  name="vacationType"  >
         <c:forEach var="type"  items="${vacationTypeList}">                      
            <option value="${type.id}" ${type.id == objToEdit.vacationType.getId()  ? 'selected="selected"' : ''}>${type.name}</option>
         </c:forEach>
        </select>                     
      </div>
         

      <div class="col-xs-4" >    
        <label for="Select3"  class=" control-label small ">${requestState_header} :</label>         
        <select  id="Select3" class="form-control  input-sm"  readonly="true"    name="requestState"  >
         <c:forEach var="state"  items="${requestStateList}">                      
            <option  ${  state.id ==  selectedState_Id ? '' : 'disabled="disabled"'}  value="${state.id}" ${ state.id ==  selectedState_Id    ? 'selected="selected"' : ''}>${state.name}</option>
         </c:forEach>
        </select>             
    </div>   
      
     <div class="form-group col-xs-6">    
      <label for="ownerComment"  class=" control-label small ">${requestOwnerComment_header}</label>
     <textarea  class="form-control  input-sm "  rows="2"  
           id="ownerComment1"   
           name="ownerComment"
           ${isOwnerCommentReadonly}                  
           >${objToEdit.ownerComment} 
     </textarea>  
     
      <label for="managerComment"  class=" control-label small ">${requestManagerComment_header}</label>      
     <textarea class="form-control   input-sm "     rows="2"
           id="managerComment1"
            ${isCommentRequired}
           name="managerComment"   ${isManagerCommentReadonly} 
           >${objToEdit.managerComment} 
     </textarea>     
     
      <label for="Select2"  class=" control-label small  " >${requestManager_header} :</label>         
        <select  id="Select2" class="form-control  input-sm "    ${dsbl_all}   name="requestManager"  >
         <c:forEach var="manager"  items="${managerList}">                      
            <option value="${manager.id}" ${manager.id == reqOwner.manager.getId() ? 'selected="selected"' : ''}>${manager.getFullName()}</option>
         </c:forEach>
        </select>    
         
       <label for="Select1"  class=" control-label small " >${requestOwner_header} :</label>         
        <select  id="Select1" class="form-control  input-sm"  readonly="true"    name="requestOwner"    >
         <c:forEach var="requestOwner"  items="${ownerList}">                      
             <option   ${action.equals( "requestInsert") || requestOwner.id == reqOwner.getId() ? '': 'disabled="disabled"' } value="${requestOwner.id}" ${requestOwner.id == reqOwner.getId() ? 'selected="selected"' : ''}>${requestOwner.getFullName()}</option>
         </c:forEach>
        </select>        
    
     </div>           
           
       <%--КАЛЕНДАРИК  --%>  
        
      <div  class="form-control-static col-xs-6 col-xs-push-1  "    id="date-range0-container" disabled >
      <label for="date-range0-container" class=" control-label col-xs-6   small  "   > ${calendar_header}</label>    
      </div>
      
      
                 
     <div class="form-group  col-xs-12" >                            
     <button class="btn">
        <fmt:message key="label.${action.toLowerCase().substring(7)}"/>
    </button>     
     
        <a onclick="javascript:history.back(); return false;"> <fmt:message key="label.back"/></a>
   </div>   
</form>