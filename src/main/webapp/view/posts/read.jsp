<%-- 
    Document   : read
    Created on : 15/09/2020, 01:25:33
    Author     : Alan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/includes/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View postagem</title>
    </head>
    <body>
        <div class="container">
            <div class="card">
                <div class="card-header">
                    <span class="h4"><c:out value="${post.titulo}"/></span>
                </div>
                <div class="card-body">
                    <p>
                    <span class="h4"><c:out value="${post.subtitulo}"/></span>
                    </p>
                    <p>
                    <span class="h4"><c:out value="${post.descricao}"/></span>
                    </p>
                    <p> 
                        <span class="card-text"><c:out value="${post.conteudo}"/></span>
                    </p>
                </div>
                     <div class="card-footer">
                        <span class="card-text">Id: <c:out value="${post.postagemId}"/></span>
                        <span class="card-text">Visualizações: <c:out value="${post.visualizacoes}"/></span>
                        <span class="card-text">CreateAt: <c:out value="${post.createAt}"/></span>
                        <span class="card-text">UpdateAt: <c:out value="${post.alteradoAt}"/></span>
                     </div>
            </div>
                         
            <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/posts">
                <span>Voltar a pagina principal</span>
            </a>
        </div>
    </body>
</html>