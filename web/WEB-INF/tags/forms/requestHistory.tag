<%-- 
    Document   : requestHistory
    Created on : 05.12.2017, 15:17:18
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
<c:set var="dateTimeCreated_header">
    <fmt:message key="label.date-time"/>
</c:set>
<c:set var="history_title">
    <fmt:message key="label.requestHistory"/>
</c:set>

   <div class="col-xs-12 "   >
<div class=" panel panel-info panel-collapse"   >
         
    <div class="panel-heading"> <a data-toggle="collapse" data-target="#hist1"> ${history_title}(${objToEdit.getHistory().size()})</a></div>
         <div id="hist1" class="panel-body small collapse ">
    <table class="table   table-condensed    " > 
        <thead>
            <tr>
                <th>${dateTimeCreated_header}</th>
                <th>${requestState_header}</th>
                <th>${requestManager_header}</th>
            </tr>
        </thead>
        <c:forEach var="req" items="${objToEdit.getHistory()}"> 
          <tr>
              <td> <a href="requestEdit?id=${req.id}"> ${req.getFormatedDateTimeCreated()} </a> </td>
              <td>  <a href="requestEdit?id=${req.id}"> ${req.requestState.name} </a></td>       
              <td> <a href="requestEdit?id=${req.id}">   ${req.manager.name}  </a>  </td>
             
           </tr>    
         </c:forEach>     
           <tr>
              <td>  ${objToEdit.getFormatedLastModyfied()}  </td>
              <td>  ${objToEdit.requestState.name} </td>       
              <td>   ${objToEdit.manager.name} </b>  </td>
            
           </tr> 
      </table>
     </div>
</div>            
              
</div>
              
               

<%--
<div class="col-xs-6 col-xs-push well"   >
         <p> ${history_title} </p>
         <div class="col-xs-12 small ">
    <table class="table   table-condensed    " > 
        <thead>
            <tr>
                <th>${dateTimeCreated_header}</th>
                <th>${requestState_header}</th>
                <th>${requestManager_header}</th>
            </tr>
        </thead>
        <c:forEach var="req" items="${objToEdit.getHistory()}"> 
          <tr>
              <td> <a href="requestEdit?id=${req.id}"> ${req.getFormatedDateTimeCreated()} </a> </td>
              <td>  <a href="requestEdit?id=${req.id}"> ${req.requestState.name} </a></td>       
              <td> <a href="requestEdit?id=${req.id}">   ${req.manager.name}  </a>  </td>
             
           </tr>    
         </c:forEach>     
           <tr>
              <td>  ${objToEdit.getFormatedLastModyfied()}  </td>
              <td>  ${objToEdit.requestState.name} </td>       
              <td>   ${objToEdit.manager.name}      </td>
            
           </tr> 
      </table>
     </div>
</div>   
    --%>