<%-- 
    Document   : login
    Created on : 01.11.2017, 13:16:43
    Author     : admin
--%>

 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels"/>

<c:set var="title">
    <fmt:message key="label.login"/>
</c:set>

<common:layout title="${title}">
    <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-md-offset-2 col-lg-offset-5">
    <h2>${title}</h2>
    </div>
   <forms:login/>
    
</common:layout>