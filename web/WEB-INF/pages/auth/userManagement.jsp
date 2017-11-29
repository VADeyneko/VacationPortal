

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@taglib prefix="forms" tagdir="/WEB-INF/tags/forms/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


      
<fmt:setBundle basename="resources.labels"/>

<c:set var="title">
    <fmt:message key="label.user-management"/>
</c:set>

<c:set var="email_table_header">
    <fmt:message key="label.email"/>
</c:set>
<c:set var="name_table_header">
    <fmt:message key="label.name"/>
</c:set>
<c:set var="lastname_table_header">
    <fmt:message key="label.lastname"/>
</c:set>
 

<common:layout title="${title}">
    

       <jsp:body>
        <common:topmenu/>
    <h3>${title}</h3>
    
    <div><forms:insert/></div>

    <table class="table table-hover">
         <thead>
            <tr>
              <th>${name_table_header}</th>
              <th>${lastname_table_header}</th>
              <th>${email_table_header}</th>
               <th> Менеджер  </th>
               <th> Роль </th>
            </tr>
          </thead>
           <tbody>            
                  <c:forEach var="users" items="${userlist}">
                <tr>
                  <td> <a href="${formType}Details?id=${users.id}"> ${users.name} </a>  </td>
                  <td> <a href="${formType}Details?id=${users.id}"> ${users.lastname} </a>  </td>   
                  <td> <a href="${formType}Details?id=${users.id}"> ${users.credentials.email} </a>  </td>                    
                  <td> <a href="${formType}Details?id=${users.id}"> ${users.getManager().getFullName()} </a>  </td>                    
                  <td> <a href="${formType}Details?id=${users.id}"> ${users.getUserGroup().getGroupLabelName()} </a>  </td>                    
                          
                </tr>
                  </c:forEach>
       </tbody>           
    </table>   
               
    </jsp:body>
</common:layout>

 