<%-- 
    Document   : registration
    Created on : 02.11.2017, 15:29:36
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels"/>

<c:set var="title">
    <fmt:message key="label.registration"/>
</c:set>

<common:layout title="${title}">
    
    <h3>${title}</h3>

    <forms:registration/>
    
</common:layout>