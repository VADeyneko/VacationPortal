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

  <form action="<%= request.getContextPath()%>/requestForward?id=${objToEdit.id}&toState=${nextRequestState.id}"   method="post"  class="form-horizontal col-sm-12 c col-md-12 main ">      			      
 
   <div class=" btn-group  col-lg-12 col-lg-12 col-sm-offset-0">
       <button class="btn">
        <fmt:message key="label.confirm"/>
        </button>  
       <a onclick="javascript:history.back(); return false;" class="btn btn-link" role="button"> <fmt:message key="label.back"/></a>
    
     </div> 
  </form>   