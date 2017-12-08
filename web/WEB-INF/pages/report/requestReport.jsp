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



<common:layout title="${title}">
    

    <jsp:body>
        <common:topmenu/>
        <div class=" panel panel-info panel-collapse"   >
         
    <div class="panel-heading"> <a data-toggle="collapse" data-target="#hist1"> Параметры</a></div>
         <div id="hist1" class="panel-body   collapse ">
        <forms:requestForm/>
         </div>
        </div>
    </jsp:body>
</common:layout>