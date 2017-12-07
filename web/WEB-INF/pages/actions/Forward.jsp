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
    <fmt:message key="label.forward-confirm"/>
</c:set>

<common:layout title="${title}">
 
   
  <common:topmenu/>
  
  <div class="alert alert-info alert-dismissable fade in">
       <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
   ${title}  в состояние "${newState.name} " 
 </div>
 
    <forms:requestForm/>
   
</common:layout>