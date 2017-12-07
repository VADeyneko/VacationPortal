<%-- 
    Document   : restricted
    Created on : 07.12.2017, 17:56:01
    Author     : Victor Deyneko <VADeyneko@gmail.com>
--%>

 
 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels"/>

<c:set var="title">
    <fmt:message key="label.access-restricted"/>
</c:set>

<common:layout title="${title}">
    <jsp:body>
      
  <div class="alert alert-danger alert-dismissable">
      <strong class="text-center"> <h2> ${title} </h2></strong>
</div>
 
    </jsp:body>
</common:layout>