<%-- 
    Document   : userEdit
    Created on : 08.11.2017, 19:55:51
    Author     : admin
--%>

 

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />

<c:set var="namePlaceholder">
    <fmt:message key="label.name" />
</c:set>
<c:set var="lastnamePlaceholder">
    <fmt:message key="label.lastname" />
</c:set>
<c:set var="emailPlaceholder">
    <fmt:message key="label.email" />
</c:set>
<c:set var="passwordPlaceholder">
    <fmt:message key="label.password" />
</c:set>
<c:set var="passwordConfirmationPlaceholder">
    <fmt:message key="label.password-confirmation" />
</c:set>
<c:set var="userGroupLabelName">
    <fmt:message key="label.usergroup" />
</c:set>
<c:set var="managerSelectLabelName">
    <fmt:message key="label.manager" />
</c:set>

    <common:error/>
    
  <common:topmenu/>
  <form action="<%= request.getContextPath()%>/${action}?id=${objToEdit.id}"  method="post"  class="col-sm-9 c col-md-10 main ">
   <div class="form-group ">
    <input type="text"
           name="name"
           ${dsbl_all}
           class="form-control"
           placeholder="${namePlaceholder}"
           value="${objToEdit.name}"
           required>
     </div> 
    <div class="form-group">
    <input type="text"
           name="lastname"
           ${dsbl_all}
           class="form-control"
           placeholder="${lastnamePlaceholder}"
           value="${objToEdit.lastname}"
           required>
    </div>
           
    <div class="form-group">
    <input type="email"
           name="email"
           ${dsbl_all}
           class="form-control"
           placeholder="${emailPlaceholder}"
           value="${objToEdit.credentials.email}"
           required>
     </div>
           
    <div class="form-group">
    <input type="password"
           name="password"
            ${dsbl_all}
           class="form-control"
           placeholder="${passwordPlaceholder}"           
           value="${objToEdit.credentials.password}"
           required>
    </div>
           
    <div class="form-group">
    <input type="password"
           name="password-confirmation"
            ${dsbl_all}
           class="form-control"
           placeholder="${passwordConfirmationPlaceholder}"
           value="${objToEdit.credentials.password}"
           required>    
    </div>
 
           
    <div class="form-group" >    
        <label for="Select1" >${userGroupLabelName} :</label>         
        <select  id="Select1" class="form-control"    ${dsbl_all}  name="userGroup"   >
         <c:forEach var="userGroup"  items="${userGroupList}">                      
            <option value="${userGroup.id}" ${userGroup.id == objToEdit.userGroup.id ? 'selected="selected"' : ''}>${userGroup.groupLabelName}</option>
         </c:forEach>
        </select>             
    </div>      
        
     <div class="form-group" >    
        <label for="Select2" >${managerSelectLabelName} :</label>         
        <select  id="Select2" class="form-control"    ${dsbl_all}  name="manager"  >
         <c:forEach var="manager"  items="${managerList}">                      
            <option value="${manager.id}" ${manager.id == objToEdit.manager.id ? 'selected="selected"' : ''}>${manager.getFullName()}</option>
         </c:forEach>
        </select>             
    </div>          
        
    <button class="btn">
        <fmt:message key="label.${action.toLowerCase().substring(4)}"/>
    </button>     
     
    <a onclick="javascript:history.back(); return false;"> <fmt:message key="label.back"/></a>
</form>