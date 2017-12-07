<%-- 
    Document   : detailButtons
    Created on : 30.11.2017, 17:24:50
    Author     : admin
--%>
 
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />

<div class="col-xs-12 panel">
   <div class=" btn-group  col-lg-12 col-lg-12 col-sm-offset-0">
    
   
       <c:if test="${ objToEdit.requestState.getId() == 1   }"> 
        
       <a href="${formType}Delete?id=${objToEdit.id}" class="btn btn-link " role="button"> <fmt:message key="label.delete"/></a> 
       <a href="${formType}Edit?id=${objToEdit.id}" class="btn btn-link" role="button"> <fmt:message key="label.edit"/></a>
     </c:if> 
    
      <c:forEach var="state" items="${possibleStates}">   
              <a href="${formType}Forward?id=${objToEdit.id}&toState=${state.id}" class="btn btn-primary" role="button">   <fmt:message key="label.to"/>  <span class="glyphicon ${state.glyphoiconName}"></span>  ${state.name}</a>      
         </c:forEach>    
     
       
       <a onclick="javascript:history.back(); return false;" class="btn btn-link" role="button"> <fmt:message key="label.back"/></a>    
     </div> 
</div>     