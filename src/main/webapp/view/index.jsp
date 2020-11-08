<%-- 
    Document   : index
    Created on : 15/09/2020, 14:22:48
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comp-magazine</title>
    </head>
    <body>
        <h1>Bem-vindo a Comp-magazine!</h1>
        <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/user">Ver usuários</a>
        <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/posts">Ver postagens</a>
        <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/areas">Ver áreas</a>

    </body>
</html>
