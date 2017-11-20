

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
    


    <table class="table table-hover">
         <thead>
            <tr>
              <th>${name_table_header}</th>
              <th>${lastname_table_header}</th>
              <th>${email_table_header}</th>
               <th>   </th>
            </tr>
          </thead>
           <tbody>
               <tr>
                   <td><forms:insert/></td>
                   <td>...</td>
                   <td>...</td>
                   <td>...</td>
               </tr>
                  <c:forEach var="users" items="${userlist}">
                <tr>
                  <td> ${users.name}</td>
                  <td> ${users.lastname} </td>
                  <td>${users.credentials.email}</td>
                  <td>   <a href="userEdit?id=${users.id}">
                                     Редактировать</a> |  
                     <a href="userDelete?id=${users.id}">
                                     Удалить</a>   
                           
                  </td>      
                  
                </tr>
                  </c:forEach>
       </tbody>           
    </table>   
               
    </jsp:body>
</common:layout>

 