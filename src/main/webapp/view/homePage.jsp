<%-- 
    Document   : homePage
    Created on : 07/12/2020, 16:41:46
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
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
             <h1 class="display-4">Comp-magazine!</h1>
             <p class="lead">Conteúdo de computação feito por computeiros.</p>
            </div>
            <div class="container">
                <div class="row">
                  <div class="col-sm">
                    <ul class="list-group">
                        <c:forEach var="area" items="${requestScope.areasList}">
                            
                            <a href="">
                                <li class="list-group-item"><c:out value="${area.nome}"/></li>
                            </a>
                            
                        </c:forEach>
                    </ul>
                  </div>
                  <div class="col-sm">
                    <c:forEach var="post" items="${requestScope.postList}">
                        <div class="card">
                            <div class="card-header">
                                <a href="${pageContext.servletContext.contextPath}/posts/read?id=${post.postagemId}">
                                    <span class="card-title h3"><c:out value="${post.titulo}"/></span>
                                </a>
                            </div>
                            <div class="card-body">  
                                <a href="${pageContext.servletContext.contextPath}/posts/read?id=${post.postagemId}">
                                    <span class="h4"><c:out value="${post.subtitulo}"/></span>
                                </a>
                            </div>
                            <div class="card-body">  
                                <a href="${pageContext.servletContext.contextPath}/posts/read?id=${post.postagemId}">
                                    <span class="h4"><c:out value="${post.descricao}"/></span>
                                </a>
                            </div>
                        </div>
                                <br/>
                    </c:forEach>
                  </div>
                  <div class="col-sm">
                    One of three columns
                  </div>
                </div>
            </div>
        </div>
    </body>
</html>
