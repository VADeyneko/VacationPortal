<%-- 
    Document   : requestReport
    Created on : 07.12.2017, 20:44:27
    Author     : Victor Deyneko <VADeyneko@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
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
<c:set var="reportParams_header">
    <fmt:message key="label.report-params"/>
</c:set>

<common:layout title="${title}">
    

    <jsp:body>
        <common:topmenu/>
        <div class=" panel panel-info panel-collapse"  ${isParameterDivHidden} >
         
    <div class="panel-heading" > <a data-toggle="collapse" data-target="#hist1" > ${reportParams_header}</a></div>
         <div id="hist1" class="panel-body   collapse ">
        <forms:requestForm/>
         </div>
        </div>
         
         
          <table class="table table-hover ">
         <thead>
            <tr>   
                 <th>${vacationType_header}</th>
                 <th>${requestDateBegin_header}</th>
                <th>${requestDateEnd_header}</th>
                  <th>${requestState_header}</th>
                <th>${requestManager_header}</th>               
               <th>${requestOwner_header}</th>  
            </tr>
          </thead>
           <tbody>

                  <c:forEach var="rqst" items="${reportList}">
                <tr>
                    <td>  ${rqst.vacationType.getName()} </td>
                    <td>  ${rqst.getFormatedDateBegin()}  </td>
                    <td>  ${rqst.getFormatedDateEnd()}   </td>
                    <td>  ${rqst.requestState.getName()} </td>                    
                     <td> ${rqst.manager.getFullName()}    </td>     
                     <td>${rqst.owner.getFullName()}   </td> 
                </tr>
                  </c:forEach>
       </tbody>           
    </table>  
            
             <a onclick="javascript:history.back(); return false;"> <fmt:message key="label.back"/></a>
    </jsp:body>
</common:layout>