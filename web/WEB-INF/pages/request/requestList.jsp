<%-- 
    Document   : requestList
    Created on : 15.11.2017, 19:02:15
    Author     : admin
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


      
<fmt:setBundle basename="resources.labels"/>

<c:set var="title">
    <fmt:message key="label.requestList"/>
</c:set>

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
  
<common:layout title="${title}">
    

    <jsp:body>
        <common:topmenu/>
    <h3>${title}</h3>
    


    <table class="table table-hover">
         <thead>
            <tr>               
                <th>${requestOwner_header}</th>
                <th>${requestManager_header}</th>
                <th>${requestDateBegin_header}</th>
                <th>${requestDateEnd_header}</th>
                <th>${requestState_header}</th>
                <th>${vacationType_header}</th>
               <th>   </th>
            </tr>
          </thead>
           <tbody>
               <tr>
                   <td> <a href="requestInsert">   Создать</a>    </td>
                   <td>...</td>
                   <td>...</td>
                   <td>...</td>
                   <td>...</td>
                   <td>...</td>                   
               </tr>
                  <c:forEach var="rqst" items="${requestList}">
                <tr>
                      <td> ${rqst.owner.getFullName()} </td>
                      <td> ${rqst.manager.getFullName()} </td>
                      <td> ${rqst.dateBegin} </td>
                      <td> ${rqst.dateEnd} </td>
                      <td> ${rqst.requestState.getName()} </td>
                      <td> ${rqst.vacationType.getName()} </td>
                  <td>   <a href="requestEdit?id=${rqst.id}">
                                     Редактировать</a> |  
                     <a href="requestDelete?id=${rqst.id}">
                                     Удалить</a> 
                  </td>      
                  
                </tr>
                  </c:forEach>
       </tbody>           
    </table>   
               
    </jsp:body>
</common:layout>

 