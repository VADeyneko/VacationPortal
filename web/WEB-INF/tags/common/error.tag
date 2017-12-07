
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${error != null}">
    <p class="alert alert-danger alert-dismissable fade in">
        ${error}
    </p> 
</c:if>
 
    
   