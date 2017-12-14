<%-- 
    Document   : appSettings
    Created on : 12.12.2017, 20:12:11
    Author     : Victor Deyneko <VADeyneko@gmail.com>
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


      
<fmt:setBundle basename="resources.labels"/>

<c:set var="title">
    <fmt:message key="label.app-settings"/>
</c:set>
 

<common:layout title="${title}">
    

       <jsp:body>
           <forms:appSettings/>
     
         
    </jsp:body>
</common:layout>

 