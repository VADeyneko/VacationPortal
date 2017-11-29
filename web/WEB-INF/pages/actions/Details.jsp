<%-- 
    Document   : details
    Created on : 27.11.2017, 18:58:09
    Author     : admin
--%>
 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />


<c:set var="title">
    <fmt:message key="label.details"/>
</c:set>

<common:layout title="${title}">
 
  <common:error/>
  <common:topmenu/>
  
    <h3>${title}</h3>
   
 
    <div class="col-sm-6 col-lg-6  "  >
         <p> </p>
    <table class="table   table-striped table-condensed    " >      
        <c:forEach var="details" items="${detailsCollection}"> 
          <tr>
              <td> <b> ${details.getKey()} </b> </td>
              <td>  ${details.getValue()} </td>                      
           </tr>    
         </c:forEach>              
      </table>
    </div>   
    
   <div class=" btn-group  col-lg-12 col-lg-12 col-sm-offset-0">
       <a href="${formType}Delete?id=${objToEdit.id}" class="btn btn-link " role="button"> <fmt:message key="label.delete"/></a> 
       <a href="${formType}Edit?id=${objToEdit.id}" class="btn btn-link" role="button"> <fmt:message key="label.edit"/></a>
     <a onclick="javascript:history.back(); return false;" class="btn btn-link" role="button"> <fmt:message key="label.back"/></a>
    
     </div> 

           
</common:layout>