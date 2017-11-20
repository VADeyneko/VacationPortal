<%-- 
    Document   : requestInsert
    Created on : 15.11.2017, 22:50:41
    Author     : admin
--%>

 
 <%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels"/>

<c:set var="title">
    <fmt:message key="label.request-insert"/>
</c:set>

<common:layout title="${title}">
    
    <h3>${title}</h3>

    <forms:requestForm/>
    
</common:layout>