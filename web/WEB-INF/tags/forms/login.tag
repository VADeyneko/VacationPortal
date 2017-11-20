

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>

<fmt:setBundle basename="resources.labels"/>

<c:set var="emailPlaceholder">
    <fmt:message key="label.email"/>
</c:set>
<c:set var="passwordPlaceholder">
    <fmt:message key="label.password"/>
</c:set>

<c:set var="email" value="<%= request.getParameter("email") %>" />

<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-md-offset-2 col-lg-offset-4">
<form action="<%=request.getContextPath() %>/login" method="post">

    <common:error/>
    
    <div class="form-group ">
    <input type="email" 
           name="email" 
           class="form-control" 
           value="${email}"
           placeholder="${emailPlaceholder}" 
           required>
    </div>
    <div class="form-group">       
    <input type="password" 
           name="password" 
           class="form-control"
           placeholder="${passwordPlaceholder}" 
           required> 
   </div>
           
    <a href="<%= request.getContextPath() %>/registration">
        <fmt:message key="label.registration"/>
    </a> 

    <button class="btn btn-default ">
        <fmt:message key="label.sign-in"/>
    </button>

</form>
    
</div>    
</div>   
    