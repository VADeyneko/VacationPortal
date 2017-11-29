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
<c:set var="requestOwnerComment_header">
    <fmt:message key="label.ownerComment_header"/>
</c:set>
<c:set var="requestManagerComment_header">
    <fmt:message key="label.managerComment_header"/>
</c:set>
 
    <common:error/>
    
  <common:topmenu/>
 
			
  <form action="<%= request.getContextPath()%>/${action}?id=${objToEdit.id}"   method="post"  class="form-horizontal col-sm-12 c col-md-12 main ">      			      
    <div class="form-group ">
       
        <div class="col-xs-6">       
            <div class="form-group  col-xs-12">
          
            <label   for="datepicker_dBegin">${requestDateBegin_header}</label>
           
           <div >
           <input type="text" id="datepicker_dBegin"    
                   name="dateBegin"     readonly="true"
                     
                   ${dsbl_all}     
                   class="form-control"
                   placeholder="${requestDateBegin_header}"
                   value="${action.contains ("requestInsert") ? newRequestDefaultDate  : objToEdit.getFormatedDateBegin()}"
                   required>
            </div>
     </div> 
      <div class="form-group  col-xs-12">              
      <label   for="datepicker_dEnd">${requestDateEnd_header}</label> 
         <div  >
         <input type="text" id="datepicker_dEnd"   
               name="dateEnd"    readonly="true"         
               ${dsbl_all}    
               class="form-control"
               placeholder="${requestDateEnd_header}"
               value=value="${action.contains ("requestInsert") ? newRequestDefaultDate  : objToEdit.getFormatedDateEnd()}" 
               required>
         </div> 
      </div>     
               
        <div class="form-group col-xs-12" >   
               <label for="Select4"  class=" control-label ">${vacationType_header} :</label>         
        <select  id="Select4" class="form-control col-xs-6"    ${dsbl_all}  name="vacationType"  >
         <c:forEach var="type"  items="${vacationTypeList}">                      
            <option value="${type.id}" ${type.id == objToEdit.vacationType.getId()  ? 'selected="selected"' : ''}>${type.name}</option>
         </c:forEach>
        </select>                     
      </div>
         

      <div class="form-group col-xs-12" >    
        <label for="Select3" >${requestState_header} :</label>         
        <select  id="Select3" class="form-control"  readonly="true"  ${dsbl_all}  name="requestState"  >
         <c:forEach var="state"  items="${requestStateList}">                      
            <option  ${action != "requestInsert"  ? 'disabled="disabled"' : ''}  value="${state.id}" ${state.id == objToEdit.requestState.getId() ? 'selected="selected"' : ''}>${state.name}</option>
         </c:forEach>
        </select>             
    </div>   
         
      </div>         
       
      <%--КАЛЕНДАРИК --%>         
      <div class="col-xs-6  "  id="date-range0-container" > </div>	
      
   </div>    
        
               
   
               
   
  
     
     <div class="form-group ">    
    <label for="managerComment">${requestManagerComment_header}</label>      
     <textarea  rows="1"
           id="managerComment1" 
           name="managerComment"
           ${dsbl_all} 
           class="form-control"                  
           >${objToEdit.managerComment} 
     </textarea>            
     </div>     

     <div class="form-group ">    
     <label for="ownerComment">${requestOwnerComment_header}</label>
     <textarea  rows="1"
           id="ownerComment1" 
           name="ownerComment"
           ${dsbl_all} 
           class="form-control"
           placeholder="${requestOwnerComment_header}"            
           >${objToEdit.ownerComment} 
     </textarea>     
     </div>           
           
          <div class="form-group " >    
        <label for="Select2" >${requestManager_header} :</label>         
        <select  id="Select2" class="form-control"    ${dsbl_all}   name="requestManager"  >
         <c:forEach var="manager"  items="${userList}">                      
            <option value="${manager.id}" ${manager.id == reqOwner.manager.getId() ? 'selected="selected"' : ''}>${manager.getFullName()}</option>
         </c:forEach>
        </select>             
    </div>     
                 
     <div class="form-group" >    
        <label for="Select1" >${requestOwner_header} :</label>         
        <select  id="Select1" class="form-control"  readonly="true"    name="requestOwner"    >
         <c:forEach var="requestOwner"  items="${userList}">                      
             <option   ${action != "requestInsert"  ? 'disabled="disabled"' : ''} value="${requestOwner.id}" ${requestOwner.id == reqOwner.getId() ? 'selected="selected"' : ''}>${requestOwner.getFullName()}</option>
         </c:forEach>
        </select>             
    </div>      
     
         <button class="btn">
        <fmt:message key="label.apply"/>
         </button>
     
        <a onclick="javascript:history.back(); return false;"> <fmt:message key="label.back"/></a>
 
</form>