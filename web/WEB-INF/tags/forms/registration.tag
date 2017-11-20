 

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />

<c:set var="namePlaceholder">
    <fmt:message key="label.name" />
</c:set>
<c:set var="lastnamePlaceholder">
    <fmt:message key="label.lastname" />
</c:set>
<c:set var="emailPlaceholder">
    <fmt:message key="label.email" />
</c:set>
<c:set var="passwordPlaceholder">
    <fmt:message key="label.password" />
</c:set>
<c:set var="passwordConfirmationPlaceholder">
    <fmt:message key="label.password-confirmation" />
</c:set>

<c:set var="name" value="<%= request.getParameter("name") %>"/>
<c:set var="lastname" value="<%= request.getParameter("lastname") %>"/>
<c:set var="email" value="<%= request.getParameter("email") %>"/>

<form action="<%= request.getContextPath()%>/registration" method="post">
    
    <common:error/>
  
   <div class="form-group">
    <input type="text"
           name="name"
           class="form-control"
           placeholder="${namePlaceholder}"
           value="${name}"
           required>
     </div> 
    <div class="form-group">
    <input type="text"
           name="lastname"
           class="form-control"
           placeholder="${lastnamePlaceholder}"
           value="${lastname}"
           required>
    </div>
           
    <div class="form-group">
    <input type="email"
           name="email"
           class="form-control"
           placeholder="${emailPlaceholder}"
           value="${email}"
           required>
     </div>
           
    <div class="form-group">
    <input type="password"
           name="password"
           class="form-control"
           placeholder="${passwordPlaceholder}"
           required>
    </div>
           
    <div class="form-group">
    <input type="password"
           name="password-confirmation"
           class="form-control"
           placeholder="${passwordConfirmationPlaceholder}"
           required>    
    </div>
    <button class="btn">
        <fmt:message key="label.register"/>
    </button>
    
</form>