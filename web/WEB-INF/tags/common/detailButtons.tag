<%-- 
    Document   : detailButtons
    Created on : 30.11.2017, 17:24:50
    Author     : admin
--%>
 
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />

   <div class=" btn-group  col-lg-12 col-lg-12 col-sm-offset-0">
       <a href="${formType}Delete?id=${objToEdit.id}" class="btn btn-link " role="button"> <fmt:message key="label.delete"/></a> 
       <a href="${formType}Edit?id=${objToEdit.id}" class="btn btn-link" role="button"> <fmt:message key="label.edit"/></a>
      <a onclick="javascript:history.back(); return false;" class="btn btn-link" role="button"> <fmt:message key="label.back"/></a>
    
     </div> 