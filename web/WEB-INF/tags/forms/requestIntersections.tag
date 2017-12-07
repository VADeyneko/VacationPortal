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
<c:set var="intersections_title">
    <fmt:message key="label.requestIntersections"/>
</c:set>
  
   <div class="col-xs-12 "   >
<div class=" panel panel-danger panel-collapse"   >
         
    <div class="panel-heading"> <a data-toggle="collapse" data-target="#inter1"> ${intersections_title} (${intersectionList.size()})</a></div>
         <div id="inter1" class="panel-body small collapse " >
    <table class="table   table-condensed    " > 
        <thead>
            <tr>
                <th>${requestDateBegin_header}</th>
                <th>${requestDateEnd_header}</th>
                <th>${requestState_header}</th>                
                <th>${requestOwner_header}</th>
                <th>${requestManager_header}</th>
            </tr>
        </thead>
        <c:forEach var="intersec" items="${intersectionList}"> 
          <tr>
              <td> <a href="requestEdit?id=${intersec.id}"> ${intersec.getFormatedDateBegin()} </a> </td>           
              <td> <a href="requestEdit?id=${intersec.id}"> ${intersec.getFormatedDateEnd()} </a> </td>
              <td>  <a href="requestEdit?id=${intersec.id}"> ${intersec.requestState.name} </a></td>       
              <td> <a href="requestEdit?id=${intersec.id}">   ${intersec.manager.name}  </a>  </td>
               <td> <a href="requestEdit?id=${intersec.id}">   ${intersec.owner.name}  </a>  </td>            
           </tr>    
         </c:forEach>                
      </table>
     </div>
</div>            
</div>