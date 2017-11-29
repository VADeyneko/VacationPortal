<%-- 
    Document   : insert
    Created on : 11.11.2017, 21:35:36
    Author     : admin
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


      
<fmt:setBundle basename="resources.labels"/>

<form action="${formType}Insert">
    <button class="btn"  >
        <fmt:message key="label.insert"/>
    </button> 
 </form>   
