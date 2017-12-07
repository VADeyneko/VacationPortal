<%-- 
    Document   : userEdit
    Created on : 08.11.2017, 19:55:51
    Author     : admin
--%>

 

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />

<div class="col-xs-12  "  >
  <%--        <p> </p>
    <table class="table   table-striped table-condensed    " >      
        <c:forEach var="details" items="${detailsCollection}"> 
          <tr>
              <td> <b> ${details.getKey()} </b> </td>
              <td>  ${details.getValue()} </td>   
              
           </tr>    
         </c:forEach>              
      </table> --%>
  <div class="col-xs-12  well well-sm ">
   <form class="form-horizontal small">
  <div class="form-group">
       <c:forEach var="details" items="${detailsCollection}"> 
    <label class=" control-label col-sm-3" ><p class=" text-left" >  <b> ${details.getKey()} </b></p>  </label>
    <div class="col-sm-3">
      <p class=" text-left form-control-static"> ${details.getValue()}</p>
    </div>
     </c:forEach>  
  </div>
</form> 
   
</div>   
  
  </div>




