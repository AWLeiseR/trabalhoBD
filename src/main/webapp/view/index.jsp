<%-- 
    Document   : index
    Created on : 15/09/2020, 14:22:48
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comp-magazine</title>
    </head>
    <body>
        <h1>Bem-vindo a Comp-magazine!</h1>
        
        <div class="container">
            
           
                
                        <c:forEach var="post" items="${requestScope.postList}">
                            <div class="card">
                                <div class="card-header">
                                    <a href="${pageContext.servletContext.contextPath}/posts/read?id=${post.postagemId}">
                                        <span class="card-title"><c:out value="${post.titulo}"/></span>
                                    </a>
                                </div>
                                <div class="card-body">  
                                    <a href="${pageContext.servletContext.contextPath}/posts/read?id=${post.postagemId}">
                                        <span class="h4"><c:out value="${post.subtitulo}"/></span>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
      
        </div> 
        <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/user">Ver usuários</a>
        <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/posts">Ver postagens</a>
        <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/areas">Ver áreas</a>
    </body>
</html>
