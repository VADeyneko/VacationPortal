
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />
  
<c:set var="successfullySaved">
    <fmt:message key="label.successfully-saved"/>
</c:set> 
    
<c:if test="${saved != null}">
    <p class="alert alert-success alert-success fade in">
        ${successfullySaved}
    </p> 
</c:if>
 
    
   