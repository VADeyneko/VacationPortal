<%-- 
    Document   : topmenu
    Created on : 08.11.2017, 14:23:17
    Author     : admin
--%>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.labels" />

<c:set var="login_lbl">
    <fmt:message key="label.sign-in"/>
</c:set> 
<c:set var="logout_lbl">
    <fmt:message key="label.sign-out"/>
</c:set> 
  
   <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
            <a class="navbar-brand" href="#"> Здравствуйте, ${sessionUser.getFullName()}</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
              <li><a href="logout" ><span class="glyphicon glyphicon-log-in"></span> ${logout_lbl}</a></li>       
          </ul>
         
        </div>
      </div>
    </nav>


     <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
           <ul class="nav nav-sidebar">
               <p></p>
          </ul>
          <ul class="nav nav-sidebar">
            <c:forEach var="menuItem" items="<%=session.getAttribute("sessionMenuItems")%>">
                <li><a href=${menuItem.menuPath}>${menuItem.labelName}</a></li>
            </c:forEach>
           
          </ul>
          
        </div>
      </div>    
  </div> 
  
          