<%-- 
    Document   : details
    Created on : 27.11.2017, 18:58:09
    Author     : admin
--%>
 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />


<c:set var="title">
    <fmt:message key="label.details"/>
</c:set>

<common:layout title="${title}">
 
  <common:error/>
  <common:topmenu/>
  
    <h3>${title}</h3>
   
    <forms:detail/>
     <c:if test="${formType.equals('request')  }">
              
        <forms:requestHistory/>
       <forms:requestIntersections/>
        <common:detailRequestButtons/>
    </c:if>
    
     <c:if test="${formType.equals('user')}">
         <common:detailButtons/>
    </c:if>
           
</common:layout>