<%-- 
    Document   : main
    Created on : 15/04/2021, 11:13:00
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
         <%@include file="/view/includes/head.jsp" %>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/dashboardpost">
                    relatório postagens
                </a>
                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/dashboardview">
                     relatório Visualizações
                </a>
                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/dashboarduser">
                    relatório Usuários
                </a>
            </div></br>
            <div class="row">
                <div class="col">
                    <div class="card">
                        <div class="card-header">
                            <div class="h4">Total de post:</div>
                        </div>
                        <div class="card-body">
                            <p> <c:out value="${requestScope.totalPost}"/></p>
                        </div>
                    </div>
                </div>
                    <div class="col">
                        <div class="card">
                            <div class="card-header">
                                <div class="h4">Total de usuarios: </div>
                            </div>
                            <div class="card-body">
                                <p> <c:out value="${requestScope.totalUsers}"/></p>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card">
                            <div class="card-header">
                                <div class="h4">Total de views:</div>
                            </div>
                            <div class="card-body">
                                <p> <c:out value="${requestScope.totalView}"/></p>
                            </div>
                        </div>
                    </div> 
            </div>      
        </div>
                    
        <%@include file="/view/includes/scripts.jsp" %>
    </body>    
</html>
